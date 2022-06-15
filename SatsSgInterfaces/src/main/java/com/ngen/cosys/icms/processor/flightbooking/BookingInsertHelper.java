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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


import com.ngen.cosys.export.commonbooking.model.PartSuffix;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingRemark;
import com.ngen.cosys.icms.model.BookingInterface.BookingDeltaDetails;
import com.ngen.cosys.icms.model.BookingInterface.SHCDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDimensions;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.SHCCodes;
import com.ngen.cosys.icms.schema.flightbooking.ShipmentDetails;
import com.ngen.cosys.icms.schema.flightbooking.ShipmentIdentifierDetails;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;
import com.ngen.cosys.throughtransit.model.ThroughTransitShipmentAutoCreationModel;
import com.ngen.cosys.throughtransit.service.ThroughTransitAutoCreationService;
import com.ngen.cosys.transfertype.model.TransferTypeModel;
import com.ngen.cosys.transfertype.service.SetupTransferTypeService;
import com.ngen.cosys.transfertype.service.SetupTransferTypeServiceImpl.Equation;
import com.ngen.cosys.transfertype.util.FindShipmentPairEqualToGivenNumberForTransfer;

/**
 *This class helps to find TT flag value and to insert datas to booking tables
 */
@Component
public class BookingInsertHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingInsertHelper.class);

	@Autowired
	FlightBookingDao flightBookingDao;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	BookingShipmentValidation bookingShipmentValidation;
	
	@Autowired
	ShipmentBookingDetailBuilder shipmentBookingDetailBuilder;
	
	@Autowired
	SetupTransferTypeService setupTransferTypeService;
	
	@Autowired
	private ThroughTransitAutoCreationService autoCreateThroughTransitPair;
	
	@Autowired
	FindShipmentPairEqualToGivenNumberForTransfer findShipmentPairEqualToGivenNumberForTransfer;
	
	@Autowired
	ValidateEmbargoRule validateEmbargoRule;


	/**
	 * Method for processing TT update Flag
	 * 
	 * @param publishBookingDetails
	 * @param shipmentType
	 * @param singleShipmentBooking
	 * @throws CustomException 
	 */
	public void processTTFlag(String shipmentType,
			ShipmentBookingDetails singleShipmentBooking) throws CustomException {
		
		if (shipmentType.equals(BookingConstant.TRANSSHIPMENT)) {
			String transferType= "";
			for (ShipmentFlightPartBookingDetails shipmentDetails : singleShipmentBooking.getPartBookingList()) {
				ShipmentFlightBookingDetails incomingFlight = null;
				ShipmentFlightBookingDetails outgoingFlight = null;

				if(!CollectionUtils.isEmpty(shipmentDetails.getShipmentFlightBookingList())) {
					for (ShipmentFlightBookingDetails flightDetails : shipmentDetails.getShipmentFlightBookingList()) {
						Map<String,String> queryParam = new HashMap<>();
			    		queryParam.put("flightId",String.valueOf(flightDetails.getFlightId()));
			    		queryParam.put("flightBoardPoint", flightDetails.getFlightBoardPoint());
			    		queryParam.put("flightOffPoint",flightDetails.getFlightOffPoint());
			    		BigInteger segmentID = flightBookingDao.fetchFlightSegmentId(queryParam);
			    		flightDetails.setFlightSegmentId(segmentID);
						if (!"SIN".equals(flightDetails.getFlightBoardPoint()) && "SIN".equals(flightDetails.getFlightOffPoint())) {
							incomingFlight = flightDetails;
							if (incomingFlight.getFlightId() == 0) {
								incomingFlight = null;
							}
						} else if ("SIN".equals(flightDetails.getFlightBoardPoint()) && !"SIN".equals(flightDetails.getFlightOffPoint())) {
							outgoingFlight = flightDetails;
							if (outgoingFlight.getFlightId() == 0) {
								outgoingFlight = null;
							}
						}
					}
					if(incomingFlight != null && outgoingFlight != null) {
						long minDifference = ChronoUnit.MINUTES.between(incomingFlight.getArrivalTime(),outgoingFlight.getDepartureTime());
						BigInteger minDiff = BigInteger.valueOf(minDifference);
						List<TransferTypeModel> transferTypeList = flightBookingDao.getTransferTypes();
						for (TransferTypeModel type : transferTypeList) {
		                     if ("TT".equalsIgnoreCase(type.getTransferType())) {
		                        transferType = getTransferType(type, minDiff);
		                        if (!StringUtils.isEmpty(transferType)) {
		                           break;
		                        }
		                     }
		                  }


						if (!StringUtils.isEmpty(transferType)) {
							 shipmentDetails.setThroughTransitFlag(true);
							 shipmentDetails.setTransferType(transferType);
							 for (ShipmentFlightBookingDetails flightDetails : shipmentDetails.getShipmentFlightBookingList()) {
								 flightDetails.setTransferType(transferType);
								 flightDetails.setThroughTransitFlag(true);
							 }
							 ThroughTransitShipmentAutoCreationModel requestModel = new ThroughTransitShipmentAutoCreationModel();
							 requestModel.setShipmentNumber(singleShipmentBooking.getShipmentNumber());
							 requestModel.setShipmentDate(singleShipmentBooking.getShipmentDate());
							 requestModel.setTransferType(transferType);
							 requestModel.setNatureOfGoodsDescription(singleShipmentBooking.getNatureOfGoodsDescription());
							 requestModel.setOutboundFlightId(BigInteger.valueOf(outgoingFlight.getFlightId()));
							 requestModel.setOutboundFlightSegmentId(outgoingFlight.getFlightSegmentId());
							 requestModel.setInboundFlightId(BigInteger.valueOf(incomingFlight.getFlightId()));
							 requestModel.setInboundFlightSegmentId(incomingFlight.getFlightSegmentId());
							 requestModel.setInboundFlightBoardPoint(incomingFlight.getFlightBoardPoint());
							 requestModel.setCreatedOn(LocalDateTime.now());
							 requestModel.setModifiedOn(LocalDateTime.now());
							 requestModel.setBookingStatusCode("SS");
							 requestModel.setFlightChanged(false);
							 requestModel.setPieces(BigInteger.valueOf(shipmentDetails.getPartPieces()));
							 requestModel.setWeight(BigDecimal.valueOf(shipmentDetails.getPartWeight()));
							 
							 autoCreateThroughTransitPair.autoCreateAdvice(requestModel);
						}
						
					}
				}
			}
		}
	}

	/**
	 * Method to insert in business tables
	 * 
	 * @param bookingDetails
	 * @param singleShipmentBooking
	 * @param shipmentType
	 */
	public void processAWB(BookingDetails bookingDetails, ShipmentBookingDetails singleShipmentBooking,
			String shipmentType) {
		ShipmentIdentifierDetails shipmentIdentifierDetails = bookingDetails.getShipmentIdentifierDetails();
		ShipmentDetails shipmentDetails = bookingDetails.getShipmentDetails();
		String awbNumber = String.valueOf(shipmentIdentifierDetails.getShipmentPrefix())
				+ String.valueOf(shipmentIdentifierDetails.getMasterDocumentNumber());

		List<Boolean> documentArrived = flightBookingDao.hasDocumentArrived(awbNumber);
		Boolean documentArrive=false;
		if(documentArrived!=null) {
			for (Boolean doc: documentArrived) {
				if (doc) {
					documentArrive = true;
				}
			}
		}
		List<Boolean> acceptanceWeightList = flightBookingDao.hasAcceptanceWeight(awbNumber);
		if ((null != documentArrived) && documentArrive) {
			singleShipmentBooking.setAwbFreeze(true);
		}
		if (acceptanceWeightList != null) {
			for (Boolean acceptanceWeight : acceptanceWeightList) {
				singleShipmentBooking.setAcceptanceFlag(acceptanceWeight);
				if (acceptanceWeight) {
					singleShipmentBooking.setAwbFreeze(true);
				}
			}
		}
		if (!singleShipmentBooking.isAwbFreeze()) {
			if (BookingConstant.TRANSSHIPMENT.equals(shipmentType)) {
				singleShipmentBooking.setOrigin(shipmentDetails.getShipmentOrigin());
				singleShipmentBooking.setDestination(shipmentDetails.getShipmentDestination());
				// singleShipmentBooking.setTotalFlightWeight(shipmentDetails.getTotalWeight());
				// singleShipmentBooking.setTotalPieces(BigInteger.valueOf(shipmentDetails.getTotalNumberOfPieces()));
			} else if (BookingConstant.EXPORTSHIPMENT.equals(shipmentType)) {
				singleShipmentBooking.setOrigin(shipmentDetails.getShipmentOrigin());
			} else if (BookingConstant.IMPORTSHIPMENT.equals(shipmentType)) {
				singleShipmentBooking.setDestination(shipmentDetails.getShipmentDestination());
			}
		}
	}

	/**
	 * create part suffix
	 * 
	 * @param prevSuffix
	 * @param partSuffix
	 * @return
	 */
	private String getPartSuffix(String prevSuffix, PartSuffix partSuffix) {
		String primaryIdentifier = partSuffix.getPrimaryIdentifier();
		String startWith = partSuffix.getStartPrefix();
		String excludeWith = partSuffix.getExcludePrefix();

		if (prevSuffix.isEmpty()) {
			return primaryIdentifier;
		} else {
			if (primaryIdentifier.equals(prevSuffix)) {
				char prevSuffixChar = startWith.charAt(0);
				String[] excludeArray = excludeWith.split(",");
				for (String excludeSuffix : excludeArray) {
					if (excludeSuffix.equals(String.valueOf(prevSuffixChar))) {
						prevSuffixChar += 1;
					}
				}
				return  String.valueOf(prevSuffixChar);
			} else {
				char prevSuffixChar = prevSuffix.charAt(0);
				prevSuffixChar += 1;
				String[] excludeArray = excludeWith.split(",");
				for (String excludeSuffix : excludeArray) {
					if (excludeSuffix.equals(String.valueOf(prevSuffixChar))) {
						prevSuffixChar += 1;
					}
				}
				return String.valueOf(prevSuffixChar);
			}
		}
	}

	/**
	 * insert shipment booking , part ,flight ,dimension details
	 * 
	 * @param shipmentDetails
	 * @param existingShipment
	 * @throws CustomException
	 */
	void insertBooking(ShipmentBookingDetails shipmentDetails, ShipmentBookingDetails existingShipment) {
		try {
			checkForAWBFreeze(shipmentDetails, existingShipment);
			List<ShipmentFlightPartBookingDetails> partDetailsList = shipmentDetails.getPartBookingList();
			removeTelexChars(shipmentDetails);
			setNOGToUpperCase(shipmentDetails);
			if (partDetailsList == null) {
				throw new MessageProcessingException("part details cannot be null");
			} else if (partDetailsList.size() == 1) {
				if (existingShipment != null ) {
					insertOrUpdateBookingForTotalShipment(shipmentDetails, existingShipment);
				}else {
					// check for primary part creation for total booking
					createShipmentBookingDetails(shipmentDetails,existingShipment);
				}
				
			} else {
				if (existingShipment != null) {
					existingShipment.setShipmentDate(shipmentDetails.getShipmentDate());
					Map<String, String> map = new HashMap<>();
					map.put("awbNumber", shipmentDetails.getShipmentNumber());
					map.put("shipmentDate", String.valueOf(shipmentDetails.getShipmentDate()));
					int existingInventoryCount = flightBookingDao.checkInventoryExist(map);
					if (existingInventoryCount == 0 && !shipmentDetails.isAwbFreeze()) {
						setCancelledBookingStatus(shipmentDetails, existingShipment );
						deleteShipmentBookingDetails(existingShipment);
						createShipmentBookingDetails(shipmentDetails,existingShipment);
	
					} else {
						deleteNonWorkedOnParts(existingShipment);
						shipmentDetails.setBookingId(existingShipment.getBookingId());
						updateShipmentBookingDetails(shipmentDetails,existingShipment);
					}
				} else {
					createShipmentBookingDetails(shipmentDetails,existingShipment);
				}
	
			}
		}catch(Exception e) {
			LOGGER.error("Error while inserting records to Booking table -> ",e);
			throw new MessageProcessingException("Unable to process the message");
		}

	}

	/**
	 * @param shipmentDetails
	 * @param existingShipment
	 * @throws CustomException
	 */
	private void insertOrUpdateBookingForTotalShipment(ShipmentBookingDetails shipmentDetails,
			ShipmentBookingDetails existingShipment) throws CustomException {
		if(!existingShipment.isAnyWorkedOnPartPresent()) {
			existingShipment.setShipmentDate(shipmentDetails.getShipmentDate());
			setCancelledBookingStatus(shipmentDetails, existingShipment );
			deleteShipmentBookingDetails(existingShipment);
			// check for primary part creation for total booking
			createShipmentBookingDetails(shipmentDetails,existingShipment);
		}else {
			deleteNonWorkedOnParts(existingShipment);
			shipmentDetails.setBookingId(existingShipment.getBookingId());
			updateShipmentBookingDetails(shipmentDetails,existingShipment);
		}
	}

	/**
	 * @param shipmentDetails
	 * @param existingShipment
	 */
	private void checkForAWBFreeze(ShipmentBookingDetails shipmentDetails, ShipmentBookingDetails existingShipment) {
		if(shipmentDetails.isAwbFreeze() && (null != existingShipment)) {
				existingShipment.setAwbFreeze(true);
				shipmentDetails.setBookingId(existingShipment.getBookingId());
				if (shipmentDetails.isAcceptanceFlag()) {
					existingShipment.setAcceptanceFlag(true);
				}	
				updateBookingPieceAndWeightAfterAwbFreeze(shipmentDetails,existingShipment);
		}
	}

	/**
	 * update shipment booking records
	 * 
	 * @param shipmentDetails
	 * @throws CustomException
	 */
	private void updateShipmentBookingDetails(ShipmentBookingDetails shipmentDetails, ShipmentBookingDetails existingShipment) throws CustomException {
		LOGGER.info("Method start updateShipmentBookingDetails -> Awb freeze ->"+ shipmentDetails.isAwbFreeze());
		if (!shipmentDetails.isAwbFreeze()) {
			flightBookingDao.updateBooking(shipmentDetails);
		} 
		setOutWordFlag(shipmentDetails);
		PartSuffix partSuffix = flightBookingDao.getPartSuffix(BookingConstant.CARRIERCODE);
		String excludeSuffix = partSuffix.getExcludePrefix();
		String prevSuffix = "";
		if (!CollectionUtils.isEmpty(shipmentDetails.getPartBookingList())) {
			prevSuffix = excludeWorkedOnSuffix(existingShipment, partSuffix, excludeSuffix, prevSuffix);
			for (ShipmentFlightPartBookingDetails part : shipmentDetails.getPartBookingList()) {
				prevSuffix = getPartSuffix(prevSuffix, partSuffix);
				part.setPartSuffix(prevSuffix);
				// create part booking
				part.setBookingId(shipmentDetails.getBookingId());
				part.setShipmentNumber(shipmentDetails.getShipmentNumber());
				part.setPartWeightAudit(part.getPartWeight());
				if (shipmentDetails.getSccCodes() != null) {
					part.setConcatenatedShc(shipmentDetails.getSccCodes());
				}
				flightBookingDao.createPart(part);
				List<SHCDetails> shcDetailsList = processSHCCodes(shipmentDetails);
				if (part.getPartBookingId() != null && shcDetailsList != null) {
					shcDetailsList.forEach(e1 -> e1.setPartBookingId(part.getPartBookingId()));
					flightBookingDao.createPartBookingSHC(shcDetailsList);
				}
				// create flight booking
				createPartFlightDetails(shipmentDetails, part, shcDetailsList);
			}
		}
		createDimensions(shipmentDetails);
		createShipmentRemark(shipmentDetails);
	}

	/**
	 * @param existingShipment
	 * @param partSuffix
	 * @param excludeSuffix
	 * @param prevSuffix
	 * @return
	 */
	private String excludeWorkedOnSuffix(ShipmentBookingDetails existingShipment, PartSuffix partSuffix,
			String excludeSuffix, String prevSuffix) {
		if(!CollectionUtils.isEmpty(existingShipment.getWorkedOnPartSuffixList())) {
			for (String suffix : existingShipment.getWorkedOnPartSuffixList()) {
				if (suffix.equals("P")) {
					prevSuffix = "P";
				}
				else {
					excludeSuffix = excludeSuffix.concat("," + suffix);
				}
			}
		}
		partSuffix.setExcludePrefix(excludeSuffix);
		return prevSuffix;
	}

	/**
	 * create shipment booking detail records
	 * 
	 * @param shipmentDetails
	 * @throws CustomException
	 */
	private void createShipmentBookingDetails(ShipmentBookingDetails shipmentDetails,ShipmentBookingDetails existingShipment) throws CustomException {

		String prevSuffix = "";
		PartSuffix partSuffix = flightBookingDao.getPartSuffix(BookingConstant.CARRIERCODE);
		// creating booking
		if(!shipmentDetails.isAwbFreeze()) {
			flightBookingDao.createBooking(shipmentDetails);
		}
		LOGGER.info("After inserting record to booking table::BookingId->" + shipmentDetails.getBookingId());
		System.out.println("After inserting record to booking table::BookingId->" + shipmentDetails.getBookingId());
		createDimensions(shipmentDetails);
		setOutWordFlag(shipmentDetails);
		removeTelexChars(shipmentDetails);
		setNOGToUpperCase(shipmentDetails);
		
		
		// set flight key List
		if (!CollectionUtils.isEmpty(shipmentDetails.getPartBookingList())) {
			for (ShipmentFlightPartBookingDetails part : shipmentDetails.getPartBookingList()) {
				// set part suffix logic
				prevSuffix = getPartSuffix(prevSuffix, partSuffix);
				part.setPartSuffix(prevSuffix);
				// create part booking
				part.setBookingId(shipmentDetails.getBookingId());
				part.setCreatedBy(BookingConstant.CREATEDBY);
				part.setShipmentNumber(shipmentDetails.getShipmentNumber());
				part.setPartWeightAudit(part.getPartWeight());
				if (shipmentDetails.getSccCodes() != null) {
					part.setConcatenatedShc(shipmentDetails.getSccCodes());
				}
				flightBookingDao.createPart(part);
				List<SHCDetails> shcDetailsList = processSHCCodes(shipmentDetails);
				if (part.getPartBookingId() != null && shcDetailsList != null) {
					shcDetailsList.forEach(e1 -> e1.setPartBookingId(part.getPartBookingId()));
					flightBookingDao.createPartBookingSHC(shcDetailsList);
				}
				// create flight booking
				createPartFlightDetails(shipmentDetails, part, shcDetailsList);
			}
		}
		
		createShipmentRemark(shipmentDetails);
	
	}
	public void setNOGToUpperCase( ShipmentBookingDetails book) throws CustomException {
		if (null != book.getNatureOfGoodsDescription()) {
			String nog = book.getNatureOfGoodsDescription().toUpperCase();
			book.setNatureOfGoodsDescription(nog);
		}
	}
	
	public void removeTelexChars(ShipmentBookingDetails book) throws CustomException {
		if (null != book.getNatureOfGoodsDescription()) {
			String nog = book.getNatureOfGoodsDescription();
			nog=nog.replaceAll("[^a-zA-Z0-9\\s]", "").trim();
			if(nog.length() >20) {
				nog= nog.substring(0, 20);
			}
			book.setNatureOfGoodsDescription(nog);
		}
	}
	
	public void setOutWordFlag(ShipmentBookingDetails book) throws CustomException {
		if (!CollectionUtils.isEmpty(book.getPartBookingList())) {
			for (ShipmentFlightPartBookingDetails part : book.getPartBookingList()) {
				boolean outWordFlag = false;
				int partCount = 1;
				if (!CollectionUtils.isEmpty(part.getShipmentFlightBookingList())) {
					for (ShipmentFlightBookingDetails flight : part.getShipmentFlightBookingList()) {
						partCount++;
						if (MultiTenantUtility.isTenantAirport(flight.getFlightBoardPoint())) {
							flight.setOutwardBookingFlag(1);
							outWordFlag = true;
						} else if (MultiTenantUtility.isTenantAirport(flight.getFlightOffPoint())) {
							flight.setOutwardBookingFlag(0);
						} else {
							if (outWordFlag) {
								flight.setOutwardBookingFlag(partCount);

							} else {
								flight.setOutwardBookingFlag(0);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * ` Method to create Single shipment booking without parts
	 * 
	 * @param shcDetailsList
	 * 
	 * @param singleShipmentBooking
	 * @param dimentionCount
	 * @param basePort
	 * @throws CustomException
	 * 
	 */
	public void createPartFlightDetails(ShipmentBookingDetails booking, ShipmentFlightPartBookingDetails partBooking,
			List<SHCDetails> shcDetailsList) throws CustomException {
		if(!CollectionUtils.isEmpty(partBooking.getShipmentFlightBookingList())) {
			for (ShipmentFlightBookingDetails flight : partBooking.getShipmentFlightBookingList()) {
				flight.setShipmentNumber(booking.getShipmentNumber());
				if(flight.getFlightBoardPoint().equalsIgnoreCase("SIN")) {
					validateEmbargoRule.methodToValidateEmbargoRules(booking,flight,shcDetailsList);
				}
				flight.setBookingId(booking.getBookingId());
				
				// get flightId from operative flights
				int flightId = flight.getFlightId();
				if (flightId != 0) {
					flight.setFlightId(flightId);
					Long flightBookingId = flightBookingDao.fetchFlightBookingId(flight);
					// Creating flight booking
					if (flightBookingId == null) {
						flightBookingDao.createFlightBooking(flight);
						if (shcDetailsList != null) {
							shcDetailsList.forEach(e1 -> e1.setFlightBookingId(flight.getFlightBookingId()));
							flightBookingDao.createFlightBookingSHC(shcDetailsList);
						}
						if (!StringUtils.isEmpty(flight.getRemarks())) {
							ShipmentBookingRemark remarks = new ShipmentBookingRemark();
							remarks.setRemarkType(BookingConstant.FLIGHTREMARKTYPE);
							remarks.setCreatedBy(BookingConstant.CREATEDBY);
							remarks.setShipmentNumber(booking.getShipmentNumber());
							remarks.setShipmentDate(booking.getShipmentDate());
							remarks.setShipmentId(booking.getShipmentId());						
							remarks.setShipmentRemarks(flight.getRemarks());
							remarks.setFlightId(flight.getFlightId());
							remarks.setShipmentType(BookingConstant.SHIPMENT_TYPE);
							flightBookingDao.insertShipmentRemarksWithFlightId(remarks);
							
						}
						createPartAssosiation(partBooking, flight);
					} else {
						flight.setFlightBookingId(flightBookingId);
						createPartAssosiation(partBooking, flight);
					}
				}
			}
		}
	}
	
	private String createRemarksForTT (ShipmentFlightBookingDetails details) {
		StringBuilder remarks = new StringBuilder();
		String flightKey = details.getFlightKey();
		String segment = details.getFlightOffPoint();
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd MMM")
                .parseDefaulting(ChronoField.YEAR, 2022)
                .toFormatter(Locale.US);
        String newDateFormat = formatter.format(details.getFlightOriginDate());
        remarks.append("TT X " +flightKey+ " " + newDateFormat +" "+ segment);
        

		return remarks.toString();
	}

	/**
	 * @param partBooking
	 * @param flight
	 */
	private void createPartAssosiation(ShipmentFlightPartBookingDetails partBooking,
			ShipmentFlightBookingDetails flight) {
		// create part association
		ShipmentFlightPartDetails flightPartDetails = new ShipmentFlightPartDetails();
		flightPartDetails.setPartBookingId(partBooking.getPartBookingId());
		flightPartDetails.setFlightBookingId(flight.getFlightBookingId());
		flightPartDetails.setBookingStatusCode(flight.getBookingStatusCode());
		flightPartDetails.setCreatedBy(BookingConstant.CREATEDBY);
		flightBookingDao.createFlightPartDetail(flightPartDetails);
	}

	/**
	 * insert record in shipment remark table
	 * 
	 * @param booking
	 * @param flight
	 * @param remarks
	 * @throws CustomException
	 */
	private void createShipmentRemark(ShipmentBookingDetails booking)
			throws CustomException {
		if (!StringUtils.isEmpty(booking.getBookingRemarks()) || !StringUtils.isEmpty(booking.getHandlingInformationRemark())) {
			ShipmentBookingRemark remarks = new ShipmentBookingRemark();
			remarks.setShipmentNumber(booking.getShipmentNumber());
			remarks.setShipmentDate(booking.getShipmentDate());
			remarks.setShipmentId(booking.getShipmentId());
			remarks.setCreatedBy(BookingConstant.CREATEDBY);
			remarks.setShipmentType(BookingConstant.SHIPMENT_TYPE);
			if(booking.getBookingRemarks() != null && !booking.getBookingRemarks().isEmpty()) {
				remarks.setShipmentRemarks(booking.getBookingRemarks());
				remarks.setRemarkType(BookingConstant.REMARKTYPE);
				splitAndInsertRemark(remarks);
			}
			
			if(booking.getHandlingInformationRemark() != null && !booking.getHandlingInformationRemark().isEmpty()) {
				remarks.setShipmentRemarks(booking.getHandlingInformationRemark());
				remarks.setRemarkType(BookingConstant.HANDLINGREMARKTYPE);
				splitAndInsertRemark(remarks);
			}
			
			
		}
	}

	/**
	 * @param remarks
	 * @throws CustomException
	 */
	private void splitAndInsertRemark(ShipmentBookingRemark remarks) throws CustomException {
		if(remarks.getShipmentRemarks().length() < 600) {
			flightBookingDao.insertShipmentRemarks(remarks);
		}else {
			String remark = remarks.getShipmentRemarks();
				
			String remark1 = remark.substring(0,600);
			int pos = remark1.lastIndexOf(" ",600);
			if(pos == -1) {
				pos = 600;
			}
			System.out.println("pos::"+pos);
			remark1 = remark.substring(0, pos);
			System.out.println("remark1  "+remark1+" remark1 length::"+remark1.length());
			remarks.setShipmentRemarks(remark1);
			flightBookingDao.insertShipmentRemarks(remarks);
			remark = remark.substring(pos+1);
			System.out.println("remark  "+remark+" remark lenth::"+remark.length());
			remarks.setShipmentRemarks(remark);
			flightBookingDao.insertShipmentRemarks(remarks);
			
		}
	}
	
	/**
	 * @param flightBookId
	 * @param dimList
	 * @throws CustomException
	 */
	private void createDimensions(ShipmentBookingDetails booking) throws CustomException {
		List<ShipmentBookingDimensions> dimList = booking.getShipmentBookingDimensionList();
		int dimensionCount = 0;
		if (dimList != null) {
			for (ShipmentBookingDimensions dimension : dimList) {
				dimension.setBookingId(booking.getBookingId());
				dimensionCount++;
				dimension.setTransactionSequenceNo(dimensionCount);
				dimension.setShipmentNumber(booking.getShipmentNumber());
				dimension.setShipmentDate(booking.getShipmentDate());
				dimension.setCreatedBy(BookingConstant.CREATEDBY);
				//call common method to set user type
				dimension.setUserType("ICMS");
			}
			flightBookingDao.createDimension(dimList);
		}
	}

	/**
	 * delete all shipment booking details
	 * 
	 * @param booking
	 * @throws CustomException
	 */
	void deleteShipmentBookingDetails(ShipmentBookingDetails booking) throws CustomException {

		for (ShipmentFlightPartBookingDetails part : booking.getPartBookingList()) {
			for (ShipmentFlightBookingDetails flightObj : part.getShipmentFlightBookingList()) {

				Long flightBookingId = flightObj.getFlightBookingId();

				deleteFlightRemarks(booking, flightBookingId);

				flightBookingDao.deleteFlightPartAssociation(flightBookingId);
				if(flightBookingId != null) {
					List<BigInteger> flightDimIds = flightBookingDao.getFlightDimensionsId(flightBookingId);
					if (!CollectionUtils.isEmpty(flightDimIds)) {
						flightBookingDao.deleteFlightDimensions(flightDimIds);
					}
					flightBookingDao.deleteFlightBooking(flightBookingId.intValue());
				}

			}
			Long partBookingId = part.getPartBookingId();
			// deleting Part and it's children
			if (partBookingId != null) {
				flightBookingDao.deletePartDimensionByPartBookingId(partBookingId);
				flightBookingDao.deletePart(part);
			}
		}
		deleteDimension(booking);
		if(!booking.isAwbFreeze()) {
			flightBookingDao.deleteBooking(booking.getBookingId().intValue());
		}
	}

	/**
	 * delete non worked on part details from existing booking
	 * 
	 * @param shipmentBooking
	 * @return
	 * @throws CustomException
	 */
	private void deleteNonWorkedOnParts(ShipmentBookingDetails shipmentBooking)
			throws CustomException {
		for (ShipmentFlightPartBookingDetails part : shipmentBooking.getPartBookingList()) {
			if(!CollectionUtils.isEmpty(part.getShipmentFlightBookingList())) {
				for (ShipmentFlightBookingDetails flightObj : part.getShipmentFlightBookingList()) {
					Long flightBookingId = flightObj.getFlightBookingId();
					
					deleteFlightRemarks(shipmentBooking, flightBookingId);
					if(!flightObj.isWorkedOn()) {
						flightBookingDao.deleteFlightPartAssociation(flightBookingId);
						if(flightBookingId != null) {
							List<BigInteger> flightDimIds = flightBookingDao.getFlightDimensionsId(flightBookingId);
							if (!CollectionUtils.isEmpty(flightDimIds)) {
								flightBookingDao.deleteFlightDimensions(flightDimIds);
							}
							flightBookingDao.deleteFlightBooking(flightBookingId.intValue());
						}
					}else {
						Map<String,Long> partParam = new HashMap<>();
						partParam.put("flightBookingId", flightBookingId);
						partParam.put("partBookingId", part.getPartBookingId());
						flightBookingDao.deleteFlightPartAssociation(partParam);
					}
				}
			}
			Long partBookingId = part.getPartBookingId();
			// deleting Part and it's children
			if (partBookingId != null) {
				flightBookingDao.deletePartDimensionByPartBookingId(partBookingId);
				flightBookingDao.deletePart(part);
			}
		}
		deleteDimension(shipmentBooking);
	}

	/**
	 * @param shipmentBooking
	 * @throws CustomException
	 */
	private void deleteDimension(ShipmentBookingDetails shipmentBooking) throws CustomException {
		Map<String,Object> dimensionMap = new HashMap<>();
		dimensionMap.put("shipmentNumber",shipmentBooking.getShipmentNumber());
		dimensionMap.put("shipmentDate", shipmentBooking.getShipmentDate());
		List<BigInteger> dimIds = flightBookingDao.getDimensionsId(dimensionMap);
		if (!CollectionUtils.isEmpty(dimIds)) {
			flightBookingDao.deleteDimensions(dimIds);
		}
	}

	/**
	 * @param shipmentBooking
	 * @param flightBookingId
	 * @throws CustomException
	 */
	private void deleteFlightRemarks(ShipmentBookingDetails shipmentBooking, Long flightBookingId)
			throws CustomException {
		if (flightBookingId != null) {
			Map<String,String> queryParam = new HashMap<>();
			queryParam.put("shipmentNumber", shipmentBooking.getShipmentNumber());
			queryParam.put("shipmentDate",shipmentBooking.getShipmentDate().toString());
			flightBookingDao.deleteRemarks(queryParam);
		}
	}

	/**
	 * Method to process up to 9 SHCs by priority
	 * 
	 * @param bookingDetails
	 * @param shcDetailsList
	 * @param bookingCommodityDetails
	 * @return
	 */
	public List<SHCDetails> processSHCCodes(ShipmentBookingDetails bookingDetails) {
		List<SHCDetails> shcDetailsList = new ArrayList<>();
		LOGGER.info("processSHC=" + bookingDetails.getSccCodes());
		if (bookingDetails.getSccCodes() != null && !bookingDetails.getSccCodes().isEmpty()) {
			String shcCodes = bookingDetails.getSccCodes().trim();
			List<String> sccList = new ArrayList<>(Arrays.asList(shcCodes.split(",")));
			List<SHCDetails> finalSHCDetails = new ArrayList<>();
			LOGGER.info("processSHC=" + sccList.toString() + "size" + sccList.size());
			List<SHCCodes> masterShcCodesList = flightBookingDao.fetchSHCMaster();
			if (sccList.size() > 8) {
				shcDetailsList = createSCCCodeListBasedOnPriority(shcDetailsList, sccList, finalSHCDetails,masterShcCodesList);
			} else if (sccList.size() > 0) {
				//createSccCodeList(shcDetailsList, sccList);
				prepareSHCDetailsList(shcDetailsList, sccList, masterShcCodesList);
			}
			LOGGER.info("shcDetailsList=" + shcDetailsList);
			return shcDetailsList;
		} else {
			LOGGER.info("SCCCODES TAG IS EMPTY");
			return null;
		}

	}

	private List<SHCDetails> createSCCCodeListBasedOnPriority(List<SHCDetails> shcDetailsList, List<String> sccList,
			List<SHCDetails> finalSHCDetails, List<SHCCodes> masterShcCodesList) {
		prepareSHCDetailsList(shcDetailsList, sccList, masterShcCodesList);
		// sort handling code on the basis of handling priority
		shcDetailsList.sort((final SHCDetails s1, final SHCDetails s2) -> String
				.valueOf(s1.getSpecialHandlingPriority()).compareTo(String.valueOf(s2.getSpecialHandlingPriority())));
		int count = 0;
		shcDetailsList = prepareSHCDetailsListBasedOnPriority(shcDetailsList, finalSHCDetails, count);
		return shcDetailsList;
	}

	private List<SHCDetails> prepareSHCDetailsListBasedOnPriority(List<SHCDetails> shcDetailsList,
			List<SHCDetails> finalSHCDetails, int count) {
		for (SHCDetails sHCDetails : shcDetailsList) {
			count++;
			if (count < 10) {
				SHCDetails shcDetails = new SHCDetails();
				shcDetails.setSpecialHandlingCode(sHCDetails.getSpecialHandlingCode());
				shcDetails.setSpecialHandlingPriority(sHCDetails.getSpecialHandlingPriority());
				finalSHCDetails.add(shcDetails);
			}
		}
		shcDetailsList = finalSHCDetails;
		return shcDetailsList;
	}

	private void prepareSHCDetailsList(List<SHCDetails> shcDetailsList, List<String> sccList,
			List<SHCCodes> shcCodesList) {
		for (SHCCodes shc : shcCodesList) {
			for (String scc : sccList) {
				if (scc.equals(shc.getSpecialHandlingCode())) {
					SHCDetails shcDetails = new SHCDetails();
					shcDetails.setSpecialHandlingCode(shc.getSpecialHandlingCode());
					shcDetails.setSpecialHandlingPriority(shc.getSpecialHandlingPriority());
					shcDetails.setCreatedBy(BookingConstant.CREATEDBY);
					shcDetailsList.add(shcDetails);
				}
			}
		}
	}
	private void setCancelledBookingStatus(ShipmentBookingDetails shipmentDetails, ShipmentBookingDetails existingShipment) {
		LOGGER.info("Cancel Booking Status method ->");
		LOGGER.info("shipmentDetails:" +shipmentDetails.toString());
		LOGGER.info("existingDetails:" +existingShipment.toString());
		List<BookingDeltaDetails> deltaList = new ArrayList<>();
		List<BookingDeltaDetails> newDeltaList = new ArrayList<>();
		getDeltaList(existingShipment, deltaList);
		LOGGER.info("Delta Previous FlightList:" +deltaList.toString());
		getDuplicateRecordFromDeltaList(shipmentDetails, deltaList, newDeltaList);
		
		deltaList.removeAll(newDeltaList);
		LOGGER.info("Delta Previous FlightList:" +newDeltaList.toString());
		LOGGER.info("Final Delta FlightList:" +deltaList.toString());
		List<BookingDeltaDetails> bookingDeltaList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(deltaList)) {
			List<Integer> flightIdList = new ArrayList<>();
			for(BookingDeltaDetails deltaFlight : deltaList) {
				if(!flightIdList.contains(deltaFlight.getFlightId())) {
					flightIdList.add(deltaFlight.getFlightId());
					bookingDeltaList.add(deltaFlight);
				}
			}
			if(!CollectionUtils.isEmpty(bookingDeltaList)) {
				flightBookingDao.setCancelledBooking(bookingDeltaList);
			}
		}
	}

	/**
	 * @param shipmentDetails
	 * @param deltaList
	 * @param newDeltaList
	 */
	private void getDuplicateRecordFromDeltaList(ShipmentBookingDetails shipmentDetails,
			List<BookingDeltaDetails> deltaList, List<BookingDeltaDetails> newDeltaList) {
		for (ShipmentFlightPartBookingDetails newFlightPart: shipmentDetails.getPartBookingList()) {
			if(!CollectionUtils.isEmpty(newFlightPart.getShipmentFlightBookingList())) {
				for (ShipmentFlightBookingDetails newFlightDetails : newFlightPart.getShipmentFlightBookingList()) {
					for (BookingDeltaDetails delta : deltaList) {
						if (delta.getFlightId() == newFlightDetails.getFlightId()) {
							newDeltaList.add(delta);
						}
					}
				}
			}
		}
	}

	/**
	 * @param existingShipment
	 * @param deltaList
	 */
	private void getDeltaList(ShipmentBookingDetails existingShipment, List<BookingDeltaDetails> deltaList) {
		for (ShipmentFlightPartBookingDetails existingFlightPart : existingShipment.getPartBookingList()) {
			if(!CollectionUtils.isEmpty(existingFlightPart.getShipmentFlightBookingList())) {
				for (ShipmentFlightBookingDetails flightDetails: existingFlightPart.getShipmentFlightBookingList()) {
					if(!StringUtils.isEmpty(flightDetails.getFlightKey()) && !StringUtils.isEmpty(flightDetails.getFlightBoardPoint()) && !StringUtils.isEmpty(flightDetails.getFlightOffPoint())) {
						BookingDeltaDetails delta = new BookingDeltaDetails();
						delta.setBookingVersion(flightBookingDao.fetchBookingVersion(existingShipment.getShipmentNumber()) + 1);
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
	}
	
	private void updateBookingPieceAndWeightAfterAwbFreeze(ShipmentBookingDetails shipmentDetails,ShipmentBookingDetails existingShipmentDetails) {
		if(shipmentDetails.getPartBookingList().size() == 1) {
			shipmentDetails.getPartBookingList().get(0).setPartPieces(existingShipmentDetails.getPieces());
			shipmentDetails.getPartBookingList().get(0).setPartWeight(existingShipmentDetails.getGrossWeight());
			List<ShipmentFlightBookingDetails> newBookingFlightList = shipmentDetails.getPartBookingList().get(0).getShipmentFlightBookingList();
			if(!CollectionUtils.isEmpty(newBookingFlightList)) {
				for(ShipmentFlightBookingDetails flight : newBookingFlightList) {
					flight.setBookingPieces(existingShipmentDetails.getPieces());
					flight.setBookingWeight(existingShipmentDetails.getGrossWeight());
				}
			}	
		}
		else if(shipmentDetails.getPartBookingList().size() > 1) {
			int totalPartPiece = 0;
			double totalPartWeight = 0.0 ;
			int partListSize = shipmentDetails.getPartBookingList().size();
		
			for(ShipmentFlightPartBookingDetails part :shipmentDetails.getPartBookingList()) {
				totalPartPiece = totalPartPiece + part.getPartPieces();
				totalPartWeight = totalPartWeight + part.getPartWeight();
			}
			
			setValuesForGreaterTotalPiece(shipmentDetails, existingShipmentDetails, totalPartPiece, totalPartWeight,
					partListSize);
			
			setValuesForLesserTotalPiece(shipmentDetails, existingShipmentDetails, totalPartPiece, totalPartWeight);
			
		}
	}

	/**
	 * @param shipmentDetails
	 * @param existingShipmentDetails
	 * @param totalPartPiece
	 * @param totalPartWeight
	 */
	private void setValuesForLesserTotalPiece(ShipmentBookingDetails shipmentDetails,
			ShipmentBookingDetails existingShipmentDetails, int totalPartPiece, double totalPartWeight) {
		if(totalPartPiece < existingShipmentDetails.getPieces()) {
			ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
			part.setPartPieces(existingShipmentDetails.getPieces() - totalPartPiece);
			part.setPartWeight(existingShipmentDetails.getGrossWeight() - totalPartWeight);
			part.setVolumeUnitCode(shipmentDetails.getPartBookingList().get(0).getVolumeUnitCode());
			part.setVolume(0.01);
			List<ShipmentFlightPartBookingDetails> partList = shipmentDetails.getPartBookingList();
			partList.add(part);
			shipmentDetails.setPartBookingList(partList);
			
		}
	}

	/**
	 * @param shipmentDetails
	 * @param existingShipmentDetails
	 * @param totalPartPiece
	 * @param totalPartWeight
	 * @param partListSize
	 */
	private void setValuesForGreaterTotalPiece(ShipmentBookingDetails shipmentDetails,
			ShipmentBookingDetails existingShipmentDetails, int totalPartPiece, double totalPartWeight,
			int partListSize) {
		if(totalPartPiece > existingShipmentDetails.getPieces()) {
			ShipmentFlightPartBookingDetails lastPart = shipmentDetails.getPartBookingList().get(partListSize - 1);
			lastPart.setPartPieces(lastPart.getPartPieces() - (totalPartPiece - existingShipmentDetails.getPieces()));
			lastPart.setPartWeight(lastPart.getPartWeight() - (totalPartWeight - existingShipmentDetails.getGrossWeight()));
			if(!CollectionUtils.isEmpty(lastPart.getShipmentFlightBookingList())) {
				for(ShipmentFlightBookingDetails flight : lastPart.getShipmentFlightBookingList()) {
					flight.setBookingPieces(lastPart.getPartPieces() - (totalPartPiece - existingShipmentDetails.getPieces()));
					flight.setBookingWeight(lastPart.getPartWeight() - (totalPartWeight - existingShipmentDetails.getGrossWeight()));
				}
			}
		}
	}
	  /**
	    * Method to derive transfer type other than through transit
	    * 
	    * @param type
	    * @param diffMin
	    * @return String transferType
	    */
	   public String getTransferType(TransferTypeModel type, BigInteger diffMin) {
	      //
	      String transType = null;

	      // Set To Minutes
	      BigInteger toMinutes = BigInteger.ZERO;

	      if (!ObjectUtils.isEmpty(type.getToMinutes())) {
	         toMinutes = type.getToMinutes();
	      }

	      // Set From Minutes
	      BigInteger fromMinutes = BigInteger.ZERO;

	      if (!ObjectUtils.isEmpty(type.getFromMinutes())) {
	         fromMinutes = type.getFromMinutes();
	      }

	      // Set the tolerance time
	      BigInteger timeToTolerance = BigInteger.ZERO;

	      if (!ObjectUtils.isEmpty(type.getTimeTolrenance())) {
	         timeToTolerance = type.getTimeTolrenance();
	      }

	      switch (type.getEquation()) {
	      case Equation.Type.LESS_THAN:
	         if (fromMinutes.intValue() == 0 && toMinutes.intValue() > 0
	               && diffMin.intValue() < toMinutes.add(timeToTolerance).intValue()) {
	            transType = type.getCode();
	         }
	         break;
	      case Equation.Type.LESS_THAN_EQUALS:
	         if (fromMinutes.intValue() == 0 && toMinutes.intValue() > 0
	               && diffMin.intValue() <= toMinutes.add(timeToTolerance).intValue()) {
	            transType = type.getCode();
	         }
	         break;
	      case Equation.Type.EQUALS:
	         if (fromMinutes.intValue() > 0 && toMinutes.intValue() == 0
	               && (fromMinutes.intValue() == diffMin.intValue() && toMinutes.intValue() == diffMin.intValue())) {
	            transType = type.getCode();
	         }
	         break;
	      case Equation.Type.BETWEEN:
	         if (diffMin.intValue() >= fromMinutes.subtract(timeToTolerance).intValue()
	               && diffMin.intValue() <= toMinutes.add(timeToTolerance).intValue()) {
	            transType = type.getCode();
	         }
	         break;
	      case Equation.Type.GREATER_THAN:
	         if (fromMinutes.intValue() > 0 && toMinutes.intValue() == 0
	               && diffMin.intValue() > fromMinutes.subtract(timeToTolerance).intValue()) {
	            transType = type.getCode();
	         }
	         break;
	      case Equation.Type.GREATER_THAN_EQUALS:
	         if (fromMinutes.intValue() > 0 && toMinutes.intValue() == 0
	               && diffMin.intValue() >= fromMinutes.subtract(timeToTolerance).intValue()) {
	            transType = type.getCode();
	         }
	         break;
	      default:
	         break;
	      }
	      //
	      return transType;
	   }
}
