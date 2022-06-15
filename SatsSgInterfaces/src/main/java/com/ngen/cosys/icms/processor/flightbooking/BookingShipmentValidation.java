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

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;

import reactor.util.CollectionUtils;

@Component
public class BookingShipmentValidation {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingShipmentValidation.class);

	@Autowired
	private FlightBookingDao flightBookingDao;
	
	@Autowired
	BookingAWBDetails bookingAWBDetails;

	@Autowired
	CommonUtil commonUtil;

	public ShipmentBookingDetails validateShipmentBooking(BookingDetails bookingDetails,
			ShipmentBookingDetails singleBookingShipment) throws ParseException {
		List<Boolean> isWorkedOnList = new ArrayList<>();
		List<Long> workedOnPartFlightID = new ArrayList<>();
		List<String> workedOnSuffixList = new ArrayList<>();
		if (singleBookingShipment!=null) {
			List<ShipmentFlightPartBookingDetails> newPartBookingList = new ArrayList<>();
			List<BookingFlightDetails> removeFlightList = new ArrayList<>();
			for (ShipmentFlightPartBookingDetails partBookingDetails : singleBookingShipment.getPartBookingList()) {
				boolean partWorkedOn = true;
				partBookingDetails.setShipmentDate(bookingDetails.getShipmentDetails().getShippingDate());
				if (!validatePartWorkedOnConditions(singleBookingShipment, partBookingDetails)) {
					partWorkedOn = false;
					newPartBookingList.add(partBookingDetails);
					isWorkedOnList.add(partWorkedOn);
				} else {
					bookingDetails.setWorkedOnPiece(bookingDetails.getWorkedOnPiece() + partBookingDetails.getPartPieces());
					bookingDetails.setWorkedOnWeight(bookingDetails.getWorkedOnWeight() + partBookingDetails.getPartWeight());
					singleBookingShipment.setPieces(singleBookingShipment.getPieces() - partBookingDetails.getPartPieces());
					singleBookingShipment.setGrossWeight(singleBookingShipment.getGrossWeight() - partBookingDetails.getPartWeight());
					removeWorkedOnFlightDetails(bookingDetails, removeFlightList, partBookingDetails,workedOnPartFlightID);
					workedOnSuffixList.add(partBookingDetails.getPartSuffix());
					isWorkedOnList.add(partWorkedOn);
				}
			}
			// If all the part Worked-On (loaded/manifested or departed) then reject message
			int partsCount = isWorkedOnList.size();
			int workedOnPartsCount = 0;
			for (Boolean isWorkedOn : isWorkedOnList) {
				if (isWorkedOn) {
					workedOnPartsCount = workedOnPartsCount +1;
				}
			}
			if (partsCount == workedOnPartsCount) {
				throw new MessageProcessingException("All parts are Worked-On (loaded/manifested or departed)");
			}
			singleBookingShipment.setPartBookingList(newPartBookingList);
			singleBookingShipment.setWorkedOnPartSuffixList(workedOnSuffixList);
			removeWorkedOnFlights(bookingDetails, removeFlightList);
			setWorkedOnFlightFlagOtherParts(singleBookingShipment, workedOnPartFlightID);
		}
		return singleBookingShipment;

	}


	/**
	 * @param bookingDetails
	 * @param removeFlightList
	 */
	private void removeWorkedOnFlights(BookingDetails bookingDetails, List<BookingFlightDetails> removeFlightList) {
		if(!CollectionUtils.isEmpty(removeFlightList)) {
			bookingDetails.getBookingFlightDetails().removeAll(removeFlightList);
		}
	}


	/**
	 * @param singleBookingShipment
	 * @param workedOnPartFlightID
	 */
	private void setWorkedOnFlightFlagOtherParts(ShipmentBookingDetails singleBookingShipment,
			List<Long> workedOnPartFlightID) {
		if(!CollectionUtils.isEmpty(workedOnPartFlightID) && !CollectionUtils.isEmpty(singleBookingShipment.getPartBookingList())) {
			for (Long flightId : workedOnPartFlightID) {
				for(ShipmentFlightPartBookingDetails part : singleBookingShipment.getPartBookingList()) {
					if(!CollectionUtils.isEmpty(part.getShipmentFlightBookingList())) {
						for(ShipmentFlightBookingDetails flight : part.getShipmentFlightBookingList()) {
							if(flight.getFlightBookingId()!= null && flight.getFlightBookingId().equals(flightId)) {
								flight.setWorkedOn(true);
								break;
							}
						}
					}
				}
			}
		}
	}


	/**
	 * @param bookingDetails
	 * @param removeFlightList
	 * @param partBookingDetails
	 */
	private void removeWorkedOnFlightDetails(BookingDetails bookingDetails, List<BookingFlightDetails> removeFlightList,
			ShipmentFlightPartBookingDetails partBookingDetails,List<Long> workedOnPartFlightID) {
		if(!CollectionUtils.isEmpty(partBookingDetails.getShipmentFlightBookingList())) {
			for (ShipmentFlightBookingDetails existingWorkedOnFlight : partBookingDetails.getShipmentFlightBookingList()) {
				if(!CollectionUtils.isEmpty(bookingDetails.getBookingFlightDetails())) {
					checkAndRemoveWorkedOnPieceAndWeight(bookingDetails, removeFlightList, partBookingDetails,
							workedOnPartFlightID, existingWorkedOnFlight);
				}
			}
		}
	}


	/**
	 * @param bookingDetails
	 * @param removeFlightList
	 * @param partBookingDetails
	 * @param workedOnPartFlightID
	 * @param existingWorkedOnFlight
	 */
	private void checkAndRemoveWorkedOnPieceAndWeight(BookingDetails bookingDetails,
			List<BookingFlightDetails> removeFlightList, ShipmentFlightPartBookingDetails partBookingDetails,
			List<Long> workedOnPartFlightID, ShipmentFlightBookingDetails existingWorkedOnFlight) {
		for(BookingFlightDetails messageFlight : bookingDetails.getBookingFlightDetails()) {
			if(!StringUtils.isEmpty(messageFlight.getCarrierCode()) && !StringUtils.isEmpty(messageFlight.getFlightNumber())
					&& !StringUtils.isEmpty(messageFlight.getFlightDate())) {
				LocalDate messageFlightDate = commonUtil.convertStringToLocalDate(messageFlight.getFlightDate(), ValidationConstant.XML_DATE_FORMAT);
				if(existingWorkedOnFlight.getFlightKey().equalsIgnoreCase(messageFlight.getCarrierCode()+messageFlight.getFlightNumber())
						&& existingWorkedOnFlight.getFlightOriginDate().isEqual(messageFlightDate)) {
					calculateRemainingPieceAndWeight(bookingDetails,removeFlightList, partBookingDetails,
							existingWorkedOnFlight, messageFlight,workedOnPartFlightID);
					break;
				}
			
			}
		}
	}


	/**
	 * @param removeFlightList
	 * @param partBookingDetails
	 * @param existingWorkedOnFlight
	 * @param messageFlight
	 */
	private void calculateRemainingPieceAndWeight(BookingDetails bookingDetails,List<BookingFlightDetails> removeFlightList,
			ShipmentFlightPartBookingDetails partBookingDetails, ShipmentFlightBookingDetails existingWorkedOnFlight,
			BookingFlightDetails messageFlight,List<Long> workedOnPartFlightID) {
		int remainingPieces = messageFlight.getPieces() - partBookingDetails.getPartPieces();
		double remainingWeight = messageFlight.getWeight() - partBookingDetails.getPartWeight();
		if(remainingPieces > 0) {
			messageFlight.setPieces(remainingPieces);
			messageFlight.setWeight(remainingWeight);
			workedOnPartFlightID.add(existingWorkedOnFlight.getFlightBookingId());
		}
		else {
			removeFlightList.add(messageFlight);
		}
	}


	public Boolean validatePartWorkedOnConditions(ShipmentBookingDetails singleBookingShipment,
			ShipmentFlightPartBookingDetails partBookingDetails) {
		Boolean isPartWorkedOn=false;
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(BookingConstant.SHIPMENT_NUMBER, singleBookingShipment.getShipmentNumber());
		queryMap.put(BookingConstant.SHIPMENT_DATE, singleBookingShipment.getShipmentDate());
		queryMap.put(BookingConstant.PART_SUFFIX, partBookingDetails.getPartSuffix());
		queryMap.put(BookingConstant.TENANT_AIRPORT, singleBookingShipment.getTenantAirport());
		queryMap.put(BookingConstant.TENANAT_CITY, singleBookingShipment.getTenantCity());
		Boolean isLoaded=flightBookingDao.checkShipmentLoaded(queryMap);
		Boolean isManifested=flightBookingDao.checkShipmentManifested(queryMap);
		Boolean isDeparted=flightBookingDao.checkShipmentsDeparted(queryMap);
		LOGGER.info("isLoaded : " + isLoaded + ", isLoaded : " + isLoaded 	+ ", isDeparted : " + isDeparted );
		if(isLoaded || isManifested || isDeparted) {
			isPartWorkedOn=true;
			singleBookingShipment.setAnyWorkedOnPartPresent(true);
		}
		return isPartWorkedOn;
	}

}
