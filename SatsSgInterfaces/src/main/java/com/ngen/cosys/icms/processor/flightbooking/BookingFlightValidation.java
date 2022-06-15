/*******************************************************************************
 * Copyright (c) 2021 Coforge Technologies PVT LTD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.ngen.cosys.icms.processor.flightbooking;

import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.dao.operationFlight.OperationalFlightDao;
import com.ngen.cosys.icms.model.BookingInterface.BookingDeltaDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightBookingDetails;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightSegmentInfo;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;

@Component
public class BookingFlightValidation {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookingFlightValidation.class);

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	private FlightBookingDao flightBookingDao;

	@Autowired
	private OperationalFlightDao operationalFlightDao;

	@Autowired
	BookingInsertHelper bookingInsertHelper;

	@Autowired
	BookingShipmentValidation bookingShipmentValidation;
	
	private static final String MANIFESTED = "manifested";
	private static final String DEPARTED = "departed";
	private static final String DOCUMENTVERIFIED = "document verified";
	private static final String FLTCOMPELTE = "flight completed";

	List<BookingFlightDetails> validateFlightBooking(BookingDetails bookingDetails,
			ShipmentBookingDetails existingShipmentBookingDetails) throws ParseException, CustomException {
		List<BookingFlightDetails> newBookingFlightList = new ArrayList<>();
		List<BookingFlightDetails> bookingFlightList = bookingDetails.getBookingFlightDetails();
		List<BookingFlightDetails> flightInfo = new ArrayList<BookingFlightDetails>();
		setShipmentNumber(bookingDetails, bookingFlightList);
		LOGGER.info("Old BookingFlightDetails: " + bookingFlightList);
		// If Booking status is 'X' then cancel AWB Booking
		if (!cancelAWBBookingForBookingStatusX(bookingDetails, existingShipmentBookingDetails)) {			
			// check flights have flight booking status other than 'R'
			boolean isAllFlightStatusNotR = checkingAllFlightStatusNotR(bookingFlightList);
			// check any outgoing flight have flight booking status C
			boolean isAnyOutgoingFlightStatusC = checkingAnyOutgoingFlightStatusC(bookingFlightList);
			int invalidFlight = 0;
			int calncelledFlight = 0;
			int closedForBooking = 0;
			int notSINSegment = 0;
			for (BookingFlightDetails bookingFlightDetails : bookingFlightList) {
				if (checkSINTenant(bookingFlightDetails)) {
					int bookingFlightId = getExistingFlightBookingId(bookingFlightDetails,
							existingShipmentBookingDetails);
					flightInfo = getExistingFlightId(bookingFlightDetails);
					int flightId = 0;
					if (!flightInfo.isEmpty() && flightInfo != null) {
						flightId = flightInfo.get(0).getFlightId();
					}
					if (flightId > 0 || bookingFlightDetails.getFlightNumber() == null) {
						if (!checkFlightCancelled(bookingFlightDetails)) {
							if (validateNotClosedForBooking(bookingFlightDetails)) {
								processActiveFlight(existingShipmentBookingDetails, newBookingFlightList,
										isAllFlightStatusNotR, isAnyOutgoingFlightStatusC, bookingFlightDetails,
										bookingFlightId);
							} else if (validateClosedForBooking(bookingFlightDetails)) {
								bookingFlightDetails.setErrorMessage("FLIGHT CLOSED FOR BOOKING");
								closedForBooking++;
							}
						} else {
							bookingFlightDetails.setErrorMessage("FLIGHT CANCELLED");
							calncelledFlight++;
						}
					} else {
						bookingFlightDetails.setCosysSegmentArrivalDate(bookingFlightDetails.getSegmentArrivalDate());
						bookingFlightDetails.setCosysSegmentDepartureDate(bookingFlightDetails.getSegmentDepartureDate());
						bookingFlightDetails.setErrorMessage("INVALID OPERATIVE FLIGHT");
						invalidFlight++;
					}
				} else {
					bookingFlightDetails.setErrorMessage("INVALID FLIGHT SEGMENT");
					notSINSegment++;
				}
			}
			checkNotSINSegFlightlist(newBookingFlightList, bookingFlightList, notSINSegment);
			checkCancelledFlightlist(newBookingFlightList, bookingFlightList, calncelledFlight);
			checkClosedForBooking(newBookingFlightList, bookingFlightList, closedForBooking);
			checkInvalidFlightList(newBookingFlightList, bookingFlightList, invalidFlight);
			validateFreightNAFlights(newBookingFlightList);
			LOGGER.info("New BookingFlightDetails: " + newBookingFlightList);
			for(BookingFlightDetails flights: bookingFlightList) {
				mapICMSFlightBookingStatusToCOSYSStatus(flights);
			}
		}
		return bookingFlightList;
	}

	private void checkNotSINSegFlightlist(List<BookingFlightDetails> newBookingFlightList,
			List<BookingFlightDetails> bookingFlightList, int notSINSegment) {
		if (bookingFlightList.size() == notSINSegment && CollectionUtils.isEmpty(newBookingFlightList)) {
			throw new MessageProcessingException("Booking message doesn't have SIN flights");
		}
	}

	private void setShipmentNumber(BookingDetails bookingDetails, List<BookingFlightDetails> bookingFlightList) {
		if (bookingDetails.getShipmentIdentifierDetails() != null
				&& bookingDetails.getShipmentIdentifierDetails().getShipmentPrefix() != null
				&& bookingDetails.getShipmentIdentifierDetails().getMasterDocumentNumber() != null
				&& !bookingDetails.getShipmentIdentifierDetails().getShipmentPrefix().isEmpty()
				&& !bookingDetails.getShipmentIdentifierDetails().getMasterDocumentNumber().isEmpty()) {
			String prefix = String.valueOf(bookingDetails.getShipmentIdentifierDetails().getShipmentPrefix());
			String documentNumber = String
					.valueOf(bookingDetails.getShipmentIdentifierDetails().getMasterDocumentNumber());
			String shipmentNumber = prefix + documentNumber;
			if (bookingFlightList != null && !bookingFlightList.isEmpty())
				bookingFlightList.forEach(a -> a.setShipmentNumber(shipmentNumber));
		}
	}

	private boolean validateNotClosedForBooking(BookingFlightDetails bookingFlightDetails) {
		Boolean result = false;
		String closedForbooking = flightBookingDao.checkFlightClosedForBooking(bookingFlightDetails);
		if ((closedForbooking == null || closedForbooking.equals("0"))
				|| flightBookingDao.checkFlightBookingExist(bookingFlightDetails)) {
			bookingFlightDetails.setCosysSegmentArrivalDate(bookingFlightDetails.getSegmentArrivalDate());
			bookingFlightDetails.setCosysSegmentDepartureDate(bookingFlightDetails.getSegmentDepartureDate());
			result = true;
		}
		return result;
	}

	private boolean validateClosedForBooking(BookingFlightDetails bookingFlightDetails) {
		Boolean result = false;
		String closedForbooking = flightBookingDao.checkFlightClosedForBooking(bookingFlightDetails);
		if (!flightBookingDao.checkFlightBookingExist(bookingFlightDetails)
				&& (closedForbooking != null || closedForbooking.equals("1"))) {
			bookingFlightDetails.setCosysSegmentArrivalDate(bookingFlightDetails.getSegmentArrivalDate());
			bookingFlightDetails.setCosysSegmentDepartureDate(bookingFlightDetails.getSegmentDepartureDate());
			result = true;
		}
		return result;
	}

	private void checkClosedForBooking(List<BookingFlightDetails> newBookingFlightList,
			List<BookingFlightDetails> bookingFlightList, int closedForBooking) {
		if (bookingFlightList.size() == closedForBooking && CollectionUtils.isEmpty(newBookingFlightList)) {
			StringBuilder invalidFlights = new StringBuilder();
			for (BookingFlightDetails flights : bookingFlightList) {
				invalidFlights.append("Flight(" + flights.getCarrierCode() + flights.getFlightNumber() + "/"
						+ flights.getFlightDate().replace("-", "") + ") " + "is closed for booking  ;  ");
				flights.setErrorMessage("Flight(" + flights.getCarrierCode() + flights.getFlightNumber() + "/"
						+ flights.getFlightDate().replace("-", "") + ") " + "is closed for booking  ;  ");
			}
			throw new MessageProcessingException(invalidFlights.toString());
		}
	}

	private void checkCancelledFlightlist(List<BookingFlightDetails> newBookingFlightList,
			List<BookingFlightDetails> bookingFlightList, int calncelledFlight) {
		if (bookingFlightList.size() == calncelledFlight && CollectionUtils.isEmpty(newBookingFlightList)) {
			StringBuilder invalidFlights = new StringBuilder();
			for (BookingFlightDetails flights : bookingFlightList) {
				invalidFlights.append("Flight(" + flights.getCarrierCode() + flights.getFlightNumber() + "/"
						+ flights.getFlightDate().replace("-", "") + ") " + "is invalid  ;  ");
				flights.setErrorMessage("Flight(" + flights.getCarrierCode() + flights.getFlightNumber() + "/"
						+ flights.getFlightDate().replace("-", "") + ") " + "is invalid  ;  ");
			}
			throw new MessageProcessingException(invalidFlights.toString());
		}
	}

	private void processActiveFlight(ShipmentBookingDetails existingShipmentBookingDetails,
			List<BookingFlightDetails> newBookingFlightList, boolean isAllFlightStatusNotR,
			boolean isAnyOutgoingFlightStatusC, BookingFlightDetails bookingFlightDetails, int bookingFlightId)
			throws ParseException, CustomException {
		if (isAllFlightStatusNotR) {
			processFlightValidations(newBookingFlightList, bookingFlightDetails, isAnyOutgoingFlightStatusC);
		} else {
			cancelExistingBooking(bookingFlightId, bookingFlightDetails, existingShipmentBookingDetails);
		}
	}

	private Boolean checkFlightCancelled(BookingFlightDetails bookingFlightDetails) throws ParseException {
		Boolean flightCancelled = false;
		OperationalFlightInfo flightInfo = new OperationalFlightInfo();
		flightInfo.setFlightKey(bookingFlightDetails.getCarrierCode() + bookingFlightDetails.getFlightNumber());
		flightInfo.setFlightDate(covertStringToLocalDate(bookingFlightDetails.getFlightDate()));
		int count = operationalFlightDao.validationForCancelFlight(flightInfo);
		if (count == 1) {
			bookingFlightDetails.setCosysSegmentArrivalDate(bookingFlightDetails.getSegmentArrivalDate());
			bookingFlightDetails.setCosysSegmentDepartureDate(bookingFlightDetails.getSegmentDepartureDate());
			flightCancelled = true;
		}
		return flightCancelled;
	}

	private void checkInvalidFlightList(List<BookingFlightDetails> newBookingFlightList,
			List<BookingFlightDetails> bookingFlightList, int invalidFlight) {
		if (bookingFlightList.size() == invalidFlight && CollectionUtils.isEmpty(newBookingFlightList)) {
			validateInvalidFlights(bookingFlightList);
		} else if (invalidFlight == 0 && CollectionUtils.isEmpty(newBookingFlightList)) {
			validateWorkedOnFlights(bookingFlightList);
		} else {
			validateFlightSegments(newBookingFlightList, bookingFlightList);
		}
	}

	private void validateInvalidFlights(List<BookingFlightDetails> bookingFlightList) {
		StringBuilder invalidFlights = new StringBuilder();
		for (BookingFlightDetails flights : bookingFlightList) {
			invalidFlights.append("Flight(" + flights.getCarrierCode() + flights.getFlightNumber() + "/"
					+ flights.getFlightDate().replace("-", "") + ") " + "is invalid  ;  ");
			flights.setErrorMessage("Flight(" + flights.getCarrierCode() + flights.getFlightNumber() + "/"
					+ flights.getFlightDate().replace("-", "") + ") " + "is invalid  ;  ");
		}
		throw new MessageProcessingException(invalidFlights.toString());
	}

	private void validateWorkedOnFlights(List<BookingFlightDetails> bookingFlightList) {
		StringBuilder workedOnFlights = new StringBuilder();
		for (BookingFlightDetails flights : bookingFlightList) {
			workedOnFlights.append("Flight(" + flights.getCarrierCode() + flights.getFlightNumber() + "/"
					+ flights.getFlightDate().replace("-", "") + ") has " + flights.getWorkedOnStatus() +"  ;  ");
			flights.setErrorMessage("Flight(" + flights.getCarrierCode() + flights.getFlightNumber() + "/"
					+ flights.getFlightDate().replace("-", "") + ") " + "is worked-on  ;  ");
		}
		throw new MessageProcessingException(workedOnFlights.toString());
	}

	private void validateFlightSegments(List<BookingFlightDetails> newBookingFlightList,
			List<BookingFlightDetails> bookingFlightList) {
		StringBuilder invalidFlightSegment = new StringBuilder();
		int validSegment = 0;
		int count = 0;
		List<BookingFlightDetails> validSegmentList = new ArrayList<>();
		for (BookingFlightDetails bookingFlightDetails : bookingFlightList) {
			List<String> originList = new ArrayList<>();
			List<String> destinationList = new ArrayList<>();
			List<BookingFlightDetails> flightDetailsList = getExistingFlightId(bookingFlightDetails);
			for (BookingFlightDetails flightDetails : flightDetailsList) {
				if (checkNotNullFlightDetails(bookingFlightDetails, flightDetails)) {
					originList.add(flightDetails.getSegmentOrigin());
					destinationList.add(flightDetails.getSegmentDestination());
				}
			}
			if (!flightDetailsList.isEmpty() && !originList.isEmpty() && !destinationList.isEmpty()) {
				if (bookingFlightDetails.getSegmentOrigin() != null
						&& bookingFlightDetails.getSegmentDestination() != null
						&& (!originList.contains(bookingFlightDetails.getSegmentOrigin())
								|| !destinationList.contains(bookingFlightDetails.getSegmentDestination()))) {
					bookingFlightDetails.setFlightId(0);
					bookingFlightDetails.setCosysSegmentArrivalDate(bookingFlightDetails.getSegmentArrivalDate());
					bookingFlightDetails.setCosysSegmentDepartureDate(bookingFlightDetails.getSegmentDepartureDate());
					invalidFlightSegment.append(
							"Flight(" + bookingFlightDetails.getCarrierCode() + bookingFlightDetails.getFlightNumber()
									+ "/" + bookingFlightDetails.getFlightDate().replace("-", "") + ") "
									+ "has invalid segment  ;  ");
					bookingFlightDetails.setErrorMessage("Flight(" + bookingFlightDetails.getCarrierCode() + bookingFlightDetails.getFlightNumber()
									+ "/" + bookingFlightDetails.getFlightDate().replace("-", "") + ") "
									+ "has invalid segment  ;  ");
				} else {
					if (bookingFlightDetails.getFlightNumber() != null) {
						String flightKey = bookingFlightDetails.getCarrierCode()
								+ bookingFlightDetails.getFlightNumber();
						String flightDate = bookingFlightDetails.getFlightDate();
						List<BookingFlightDetails> flightDetailsInfo = newBookingFlightList.stream()
								.filter(a -> ((a.getCarrierCode() + a.getFlightNumber()).equals(flightKey)
										&& a.getFlightDate().equals(flightDate)))
								.collect(Collectors.toList());
						if (!flightDetailsInfo.isEmpty()) {
							validSegment++;
							validSegmentList.add(bookingFlightDetails);
						}
					}
				}
			}else if (validSegment == 0 && flightDetailsList.isEmpty() && bookingFlightDetails.getFlightNumber()!=null) {
				bookingFlightDetails.setFlightId(0);
				bookingFlightDetails.setCosysSegmentArrivalDate(bookingFlightDetails.getSegmentArrivalDate());
				bookingFlightDetails.setCosysSegmentDepartureDate(bookingFlightDetails.getSegmentDepartureDate());
				invalidFlightSegment.append(
						"Flight(" + bookingFlightDetails.getCarrierCode() + bookingFlightDetails.getFlightNumber() + "/"
								+ bookingFlightDetails.getFlightDate().replace("-", "") + ") " + "is invalid  ;  ");
				bookingFlightDetails.setErrorMessage("Flight(" + bookingFlightDetails.getCarrierCode() + bookingFlightDetails.getFlightNumber() + "/"
								+ bookingFlightDetails.getFlightDate().replace("-", "") + ") " + "is invalid  ;  ");
			}
			if(bookingFlightDetails.getFlightNumber()==null) {
				validSegmentList.add(bookingFlightDetails);
			}
		}
		if (invalidFlightSegment.length() != 0 && validSegment == 0){
			
			throw new MessageProcessingException(invalidFlightSegment.toString());
		}
		newBookingFlightList.removeAll(newBookingFlightList);
		newBookingFlightList.addAll(validSegmentList);
	}

	private boolean checkNotNullFlightDetails(BookingFlightDetails bookingFlightDetails,
			BookingFlightDetails flightDetails) {
		return flightDetails != null && flightDetails.getSegmentOrigin() != null
				&& flightDetails.getSegmentDestination() != null;
	}

	private boolean checkingAnyOutgoingFlightStatusC(List<BookingFlightDetails> bookingFlightList) {
		return bookingFlightList.stream()
				.anyMatch(p -> BookingConstant.FLIGHT_STATUS_C.equals(p.getFlightBookingStatus())
						&& BookingConstant.TENANT_SINGAPORE.equals(p.getSegmentOrigin()));
	}

	private int getExistingFlightBookingId(BookingFlightDetails bookingFlightDetails,
			ShipmentBookingDetails existingShipmentBookingDetails) throws ParseException {
		int flightBookingId = 0;
		if (existingShipmentBookingDetails != null && existingShipmentBookingDetails.getPartBookingList() != null) {
			for (ShipmentFlightPartBookingDetails shipmentFlightPartBookingDetails : existingShipmentBookingDetails
					.getPartBookingList()) {
				for (ShipmentFlightBookingDetails shipmentFlightBookingDetails : shipmentFlightPartBookingDetails
						.getShipmentFlightBookingList()) {
					if (matchFlightDetails(bookingFlightDetails, shipmentFlightBookingDetails)) {
						flightBookingId = shipmentFlightBookingDetails.getFlightBookingId().intValue();
					}
				}
			}
		}
		return flightBookingId;
	}

	private boolean matchFlightDetails(BookingFlightDetails bookingFlightDetails,
			ShipmentFlightBookingDetails shipmentFlightBookingDetails) throws ParseException {
		return shipmentFlightBookingDetails.getFlightKey() != null
				&& shipmentFlightBookingDetails.getFlightOriginDate() != null
				&& bookingFlightDetails.getCarrierCode() != null && bookingFlightDetails.getFlightNumber() != null
				&& bookingFlightDetails.getFlightDate() != null
				&& shipmentFlightBookingDetails.getFlightKey()
						.equals(bookingFlightDetails.getCarrierCode() + bookingFlightDetails.getFlightNumber())
				&& shipmentFlightBookingDetails.getFlightOriginDate()
						.equals(covertStringToLocalDate(bookingFlightDetails.getFlightDate()));
	}

	private boolean checkSINTenant(BookingFlightDetails bookingFlightDetails) {
		return BookingConstant.TENANT_SINGAPORE.equals(bookingFlightDetails.getSegmentOrigin())
				|| BookingConstant.TENANT_SINGAPORE.equals(bookingFlightDetails.getSegmentDestination());
	}

	private boolean checkingAllFlightStatusNotR(List<BookingFlightDetails> bookingFlightList) {
		return bookingFlightList.stream()
				.anyMatch(p -> !BookingConstant.FLIGHT_BOOKING_STATUS_R.equals(p.getFlightBookingStatus()));
	}

	private Boolean cancelAWBBookingForBookingStatusX(BookingDetails bookingDetails,
			ShipmentBookingDetails existingShipmentBookingDetails) throws ParseException, CustomException {
		Boolean isBookingStatus = false;
		bookingDetails.setIsShipmentCancelled(false);
		if (bookingDetails.getBookingFlightDetails() != null
				&& BookingConstant.BOOKING_STATUS_X.equals(bookingDetails.getBookingStatus())) {
			isBookingStatus = true;
			for (BookingFlightDetails bookingFlightDetails : bookingDetails.getBookingFlightDetails()) {
				int bookingFlightId = getExistingFlightBookingId(bookingFlightDetails, existingShipmentBookingDetails);
				if (bookingFlightId > 0) {
					for (ShipmentFlightPartBookingDetails partBookingDetails : existingShipmentBookingDetails
							.getPartBookingList()) {
						partBookingDetails.setShipmentDate(bookingDetails.getShipmentDetails().getShippingDate());
						if (!bookingShipmentValidation.validatePartWorkedOnConditions(existingShipmentBookingDetails,
								partBookingDetails)) {
							bookingInsertHelper.deleteShipmentBookingDetails(existingShipmentBookingDetails);
							bookingDetails.setIsShipmentCancelled(true);
						} else {
							throw new MessageProcessingException(
									"Shipment is Worked-On (loaded/manifested or departed)");
						}
					}
				} else {
					throw new MessageProcessingException("Flight Booking does not exist");
				}
			}
			if (bookingDetails.getIsShipmentCancelled()) {
				setCancelledBookingStatus(existingShipmentBookingDetails);
			}
		}
		return isBookingStatus;
	}

	private void cancelExistingBooking(int bookingFlightId, BookingFlightDetails bookingFlightDetails,
			ShipmentBookingDetails existingShipmentBookingDetails) throws CustomException, ParseException {
		if (bookingFlightId > 0) {
			if (!validateExportFlightWorkedOnConditions(bookingFlightDetails)) {
				bookingInsertHelper.deleteShipmentBookingDetails(existingShipmentBookingDetails);
			} else {
				throw new MessageProcessingException("All flights are Worked On");
			}
		} else {
			throw new MessageProcessingException("Flight Booking does not exist");
		}
	}

	private void processFlightValidations(List<BookingFlightDetails> newBookingFlightList,
			BookingFlightDetails bookingFlightInfo, boolean isAnyOutgoingFlightStatusC) throws ParseException {
		if (!BookingConstant.FLIGHT_BOOKING_STATUS_R.equals(bookingFlightInfo.getFlightBookingStatus())) {
			if (bookingFlightInfo.getFlightNumber() != null && !bookingFlightInfo.getFlightNumber().isEmpty()) {				// outgoing flight
				if (BookingConstant.TENANT_SINGAPORE.equals(bookingFlightInfo.getSegmentOrigin())) {
					if (!validateExportFlightWorkedOnConditions(bookingFlightInfo)) {
						processFlightBasedOnFlightStatusC(newBookingFlightList, bookingFlightInfo,
								isAnyOutgoingFlightStatusC);
					}else {
						bookingFlightInfo.setCosysSegmentArrivalDate(bookingFlightInfo.getSegmentArrivalDate());
						bookingFlightInfo.setCosysSegmentDepartureDate(bookingFlightInfo.getSegmentDepartureDate());
						bookingFlightInfo.setFlightId(0);
						if(bookingFlightInfo.getWorkedOnStatus() != null) {
							if(MANIFESTED.equalsIgnoreCase(bookingFlightInfo.getWorkedOnStatus())) {
								bookingFlightInfo.setErrorMessage("FLIGHT MANIFESTED");
							} else if (DEPARTED.equalsIgnoreCase(bookingFlightInfo.getWorkedOnStatus())) {
								bookingFlightInfo.setErrorMessage("FLIGHT DEPARTED");
							}
						}
					}
				} else if (BookingConstant.TENANT_SINGAPORE.equals(bookingFlightInfo.getSegmentDestination())) {
					// incoming flight
					if (!validateImportFlightWorkedOnConditions(bookingFlightInfo)) {
						newBookingFlightList.add(bookingFlightInfo);
					}else {
						bookingFlightInfo.setCosysSegmentArrivalDate(bookingFlightInfo.getSegmentArrivalDate());
						bookingFlightInfo.setCosysSegmentDepartureDate(bookingFlightInfo.getSegmentDepartureDate());
						bookingFlightInfo.setFlightId(0);
						if(bookingFlightInfo.getWorkedOnStatus() != null) {
							if(DOCUMENTVERIFIED.equalsIgnoreCase(bookingFlightInfo.getWorkedOnStatus())) {
								bookingFlightInfo.setErrorMessage("DOCUMENT VERIFIED");
							} else if (FLTCOMPELTE.equalsIgnoreCase(bookingFlightInfo.getWorkedOnStatus())) {
								bookingFlightInfo.setErrorMessage("FLIGHT COMPLETED");
							}
						}
					}
				}
			} else {
				newBookingFlightList.add(bookingFlightInfo);
			}
		}

	}

	private void processFlightBasedOnFlightStatusC(List<BookingFlightDetails> newBookingFlightList,
			BookingFlightDetails bookingFlightInfo, boolean isAnyOutgoingFlightStatusC) {
		if (!BookingConstant.FLIGHT_STATUS_W.equals(bookingFlightInfo.getFlightBookingStatus())
				&& isAnyOutgoingFlightStatusC) {
			newBookingFlightList.add(bookingFlightInfo);
		} else {
			newBookingFlightList.add(bookingFlightInfo);
		}
	}

	private List<BookingFlightDetails> getExistingFlightId(BookingFlightDetails bookingFlightInfo) {
		Map<String, String> queryParam = new HashMap<>();
		queryParam.put(BookingConstant.FLIGHT_NUMBER,
				bookingFlightInfo.getCarrierCode() + bookingFlightInfo.getFlightNumber());
		queryParam.put(BookingConstant.FLIGHT_DATE, bookingFlightInfo.getFlightDate());
		queryParam.put(BookingConstant.ORIGIN, bookingFlightInfo.getSegmentOrigin());
		queryParam.put(BookingConstant.DESTINATION, bookingFlightInfo.getSegmentDestination());
		List<BookingFlightDetails> bookingFlightDetails = flightBookingDao.getFlightId(queryParam);
		return bookingFlightDetails;
	}

	private void mapICMSFlightBookingStatusToCOSYSStatus(BookingFlightDetails bookingFlightDetails) {
		String flightBookingStatus = flightBookingDao
				.getFlightBookingStatus(bookingFlightDetails.getFlightBookingStatus());
		if(flightBookingStatus == null && bookingFlightDetails.getFlightBookingStatus().length() ==2 ) {
			flightBookingStatus = bookingFlightDetails.getFlightBookingStatus();
		}
		bookingFlightDetails.setFlightBookingStatus(flightBookingStatus);

	}

	private boolean validateExportFlightWorkedOnConditions(BookingFlightDetails bookingFlightDetails)
			throws ParseException {
		Boolean flag = false;
		List<BookingFlightDetails> flightDetails = getExistingFlightId(bookingFlightDetails);
		int flightId = 0;
		if (!flightDetails.isEmpty() && flightDetails != null) {
			flightId = flightDetails.get(0).getFlightId();
		}
		if (flightId > 0) {
			LOGGER.info("Inside Method validateExportFlightWorkedOnConditions -> flight id ->" + flightId);
			bookingFlightDetails.setFlightId(flightId);
			OperationalFlightSegmentInfo operationalFlightSegmentInfo = createSegmentData(bookingFlightDetails,
					flightId);
			int isValidateManifest = 0;
			if (ValidationConstant.PORT_SIN.equalsIgnoreCase(operationalFlightSegmentInfo.getSegmentOrigin())) {
				isValidateManifest = this.operationalFlightDao.validateDepartureManifest(operationalFlightSegmentInfo);
			}
			String isValidateEXPFlightDeparted = this.operationalFlightDao
					.validateEXPFlightDeparted(operationalFlightSegmentInfo);
			LOGGER.info("isValidateManifest : " + isValidateManifest + ", isValidateEXPFlightDeparted : "
					+ isValidateEXPFlightDeparted + ", flightId : " + flightId);
			if (isValidateManifest > 0 ) { 
				flag = true;
				bookingFlightDetails.setWorkedOnStatus(MANIFESTED);
			}
			if (isValidateEXPFlightDeparted != null) {
				flag = true;
				bookingFlightDetails.setWorkedOnStatus(DEPARTED);
			}
		}

		return flag;
	}

	private OperationalFlightSegmentInfo createSegmentData(BookingFlightDetails bookingFlightDetails, int flightId) {
		OperationalFlightSegmentInfo operationalFlightSegmentInfo = new OperationalFlightSegmentInfo();
		operationalFlightSegmentInfo.setFlightId(new BigInteger(String.valueOf(flightId)));
		operationalFlightSegmentInfo.setSegmentOrigin(bookingFlightDetails.getSegmentOrigin());
		operationalFlightSegmentInfo.setSegmentDestination(bookingFlightDetails.getSegmentDestination());
		operationalFlightSegmentInfo
				.setFlightKey(bookingFlightDetails.getCarrierCode() + bookingFlightDetails.getFlightNumber());
		operationalFlightSegmentInfo.setFlightDate(bookingFlightDetails.getFlightDate());
		return operationalFlightSegmentInfo;
	}

	private boolean validateImportFlightWorkedOnConditions(BookingFlightDetails bookingFlightDetails)
			throws ParseException {
		Boolean flag = false;
		List<BookingFlightDetails> flightDetails = getExistingFlightId(bookingFlightDetails);
		int flightId = 0;
		if (!flightDetails.isEmpty() && flightDetails != null) {
			flightId = flightDetails.get(0).getFlightId();
		}
		if (flightId > 0) {
			LOGGER.info("Inside method validateImportFlightWorkedOnConditions() -> flight id ->" + flightId);
			bookingFlightDetails.setFlightId(flightId);
			OperationalFlightSegmentInfo operationalFlightSegmentInfo = createSegmentData(bookingFlightDetails,
					flightId);
			String isDocumentVerified = this.operationalFlightDao
					.validateIMPFlightDocumentVerified(operationalFlightSegmentInfo);
			String isValidateIMPFlightCompleted = this.operationalFlightDao
					.validateIMPFlightCompleted(operationalFlightSegmentInfo);
			LOGGER.info("isDocumentVerified : " + isDocumentVerified + ", isValidateIMPFlightCompleted : "
					+ isValidateIMPFlightCompleted + ", flightId : " + flightId);
			if (isDocumentVerified != null) {
				flag = true;
				bookingFlightDetails.setWorkedOnStatus(DOCUMENTVERIFIED);
			}
			if (isValidateIMPFlightCompleted != null) {
				flag = true;
				bookingFlightDetails.setWorkedOnStatus(FLTCOMPELTE);
			}
		}

		return flag;
	}

	public LocalDate covertStringToLocalDate(String date) throws ParseException {
		String fromFormat = ValidationConstant.XML_DATE_FORMAT;
		String toFormat = ValidationConstant.DATE_FORMAT;
		String flightDate = commonUtil.convertDateString(date, fromFormat, toFormat);
		LocalDate localDate = commonUtil.convertStringToLocalDate(flightDate, toFormat);
		return localDate;
	}

	private void validateFreightNAFlights(List<BookingFlightDetails> flightList) throws CustomException {
		List<BookingFlightDetails> newBookingFlightList = new ArrayList<>();
		int freightNAFlight = 0;
		for (BookingFlightDetails flightDetails : flightList) {
			List<BookingFlightDetails> flightDetail = getExistingFlightId(flightDetails);
			int flightId = 0;
			if (!flightDetail.isEmpty() && null != flightDetail) {
				flightId = flightDetail.get(0).getFlightId();
			}
			if (flightId > 0) {
				if (!flightBookingDao.isFreightNAFlight(flightDetails)) {
					newBookingFlightList.add(flightDetails);
				} else {
					flightDetails.setFlightId(0);
					flightDetails.setCosysSegmentArrivalDate(flightDetails.getSegmentArrivalDate());
					flightDetails.setCosysSegmentDepartureDate(flightDetails.getSegmentDepartureDate());
					flightDetails.setErrorMessage("FREIGHT NA");
					freightNAFlight++;
				}

			} else if (flightDetails.getFlightNumber() == null) {
				newBookingFlightList.add(flightDetails);
			}
		}
		if (freightNAFlight == flightList.size()) {
			StringBuilder invalidFlights = new StringBuilder();
			for (BookingFlightDetails flightDetails : flightList) {
				invalidFlights.append("Flight(" + flightDetails.getCarrierCode() + flightDetails.getFlightNumber() + "/"
						+ flightDetails.getFlightDate().replace("-", "") + ") " + "is Freight NA  ;  ");
				flightDetails.setErrorMessage("Flight(" + flightDetails.getCarrierCode() + flightDetails.getFlightNumber() + "/"
						+ flightDetails.getFlightDate().replace("-", "") + ") " + "is Freight NA  ;  ");
			}
			throw new MessageProcessingException(invalidFlights.toString());
		}
		flightList.clear();
		flightList.addAll(newBookingFlightList);
	}

	private void setCancelledBookingStatus(ShipmentBookingDetails existingShipment) {
		LOGGER.info("Cancel Booking Status method ->");
		List<BookingDeltaDetails> deltaList = new ArrayList<>();
		for (ShipmentFlightPartBookingDetails existingFlightPart : existingShipment.getPartBookingList()) {
			if (!CollectionUtils.isEmpty(existingFlightPart.getShipmentFlightBookingList())) {
				for (ShipmentFlightBookingDetails flightDetails : existingFlightPart.getShipmentFlightBookingList()) {
					if (!StringUtils.isEmpty(flightDetails.getFlightKey())
							&& !StringUtils.isEmpty(flightDetails.getFlightBoardPoint())
							&& !StringUtils.isEmpty(flightDetails.getFlightOffPoint())) {
						BookingDeltaDetails delta = new BookingDeltaDetails();
						Integer bookingVersion = flightBookingDao
								.fetchBookingVersion(existingShipment.getShipmentNumber());
						delta.setBookingVersion(bookingVersion + 1);
						delta.setBookingStatusCode("XX");
						delta.setBookingChanges("CANCELLED");
						delta.setFlightId(flightDetails.getFlightId());
						delta.setShipmentBookingPieces(flightDetails.getBookingPieces());
						delta.setShipmentBookingWeight(flightDetails.getBookingWeight());
						delta.setFlightDate(flightDetails.getFlightOriginDate());
						delta.setFlightBoardPoint(flightDetails.getFlightBoardPoint());
						delta.setFlightOffPoint(flightDetails.getFlightOffPoint());
						delta.setShipmentNumber(existingShipment.getShipmentNumber());
						delta.setFlightKey(flightDetails.getFlightKey());
						delta.setPartSuffix(existingFlightPart.getPartSuffix());
						delta.setShipmentDate(existingShipment.getShipmentDate());
						delta.setCreatedBy(BookingConstant.CREATEDBY);
						deltaList.add(delta);
					}
				}
			}
		}
		if (!CollectionUtils.isEmpty(deltaList)) {
			flightBookingDao.setCancelledBooking(deltaList);
		}
	}
}
