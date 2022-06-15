/*******************************************************************************
 * Copyright (c) 2021 Coforge PVT LTD
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartBookingDetails;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightLegInfo;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightBookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightPairDetails;
import com.ngen.cosys.icms.schema.flightbooking.FlightDetails;
import com.ngen.cosys.icms.schema.flightbooking.FlightPair;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;

import reactor.util.CollectionUtils;

/**
 * Creating part booking details
 */
@Component
public class PartBookingHelper {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	ShipmentBookingDetailBuilder shipmentBookingBuilder;
	
	@Autowired
	private FlightBookingDao flightBookingDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartBookingHelper.class);
	
	/**
	 * Process part booking for Export/Import/TransShipment cases
	 * @param shipmentType
	 * @param bookingDetails
	 * @return
	 */
    ShipmentBookingDetails processPartBooking(String shipmentType,BookingDetails bookingDetails) {
    	LOGGER.info("Method start processPartBooking() -> bookingDetails ->"+bookingDetails.toString());
    	ShipmentBookingDetails shipmentBookingDetails = new ShipmentBookingDetails();
    	shipmentBookingBuilder.mapShipmentDetailsToShipmentBookingDetails(bookingDetails,shipmentBookingDetails);
    	if(!CollectionUtils.isEmpty(bookingDetails.getBookingFlightDetails())) {
	    	List<BookingFlightDetails> flightDetailsList = bookingDetails.getBookingFlightDetails();
	        if(BookingConstant.TRANSSHIPMENT.equals(shipmentType)) {
	        	 getShipmentPartListForTransShipment(bookingDetails,shipmentBookingDetails);
	        }
	        else if((BookingConstant.IMPORTSHIPMENT.equals(shipmentType) || (BookingConstant.EXPORTSHIPMENT.equals(shipmentType)))) {
	        	//create parts for carrier booking
				List<BookingFlightDetails> carrierFlights = flightDetailsList.stream()
				        .filter(flight -> flight.getFlightNumber() == null ||flight.getFlightNumber().isEmpty() )
				        .collect(Collectors.toList());
				flightDetailsList.removeAll(carrierFlights);
				if(!flightDetailsList.isEmpty()) {
					 //get cosys flight arrival and departure time
					 getCOSYSFlightDetails(flightDetailsList);
					 shipmentBookingBuilder.getShipmentPartListForImportExport(flightDetailsList,bookingDetails,shipmentBookingDetails);
				}
				if(!carrierFlights.isEmpty()) {
					shipmentBookingBuilder.mapCarrierFlightsToPart(shipmentBookingDetails,carrierFlights);
				}
				//check for remaining piece
				createPartForRemainingPieces(shipmentBookingDetails);
	        }
	        System.out.println("part::"+shipmentBookingDetails.getPartBookingList().toString());  	        
	        System.out.println("Shipment Details::"+shipmentBookingDetails);
	        LOGGER.info("Method start processPartBooking() -> shipmentBookingDetails ->"+shipmentBookingDetails.toString());
    	}else {
    		throw new MessageProcessingException("All flights are invalid/Worked On");
    	}
    	LOGGER.info("Method End processPartBooking() -> bookingDetails ->"+shipmentBookingDetails.toString());
    	return shipmentBookingDetails;
    }



	/**
	 * create part for Remaining Pieces
	 * @param shipmentBookingDetails
	 */
	private void createPartForRemainingPieces(ShipmentBookingDetails shipmentBookingDetails) {
		if(shipmentBookingDetails.getAllocatedPiece()<shipmentBookingDetails.getPieces()) {
			List<ShipmentFlightPartBookingDetails> shipmentPartList = shipmentBookingDetails.getPartBookingList();
			ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
			part.setPartPieces(shipmentBookingDetails.getPieces() - shipmentBookingDetails.getAllocatedPiece());
			part.setPartWeight(shipmentBookingDetails.getGrossWeight() - shipmentBookingDetails.getAllocatedWeight());
			part.setVolumeUnitCode(shipmentBookingBuilder.convertVolumeUnitCodeTOCosysVolumeUnitCode(shipmentBookingDetails.getVolumeUnitCode()));
			part.setVolume(0.01);
			shipmentPartList.add(part);
			shipmentBookingDetails.setPartBookingList(shipmentPartList);
		}
	}

	
    
	/**
	 * create part list for transshipment
	 * @param bookingDetails
	 * @param shipmentPartList
	 */
    private ShipmentBookingDetails getShipmentPartListForTransShipment(BookingDetails bookingDetails,ShipmentBookingDetails shipmentBookingDetails) {
    	int totalIncomingFlightPieces = 0;
    	int totalOutgoingFlightPieces = 0;
    	int totalCarrierFlightPieces = 0;
    	boolean carrierFlightFlag = false;
    	try {
		List<BookingFlightDetails> flightDetailsList = bookingDetails.getBookingFlightDetails();
		BookingFlightPairDetails pairDetails = bookingDetails.getBookingFlightPairDetails();
		if(flightDetailsList != null && !flightDetailsList.isEmpty()) {
			List<BookingFlightDetails> incomingFlights = flightDetailsList.stream()
			        .filter(flight -> flight.getSegmentDestination().equals(BookingConstant.TENANT_SINGAPORE) && flight.getFlightNumber() != null)
			        .collect(Collectors.toList());
			List<BookingFlightDetails> outgoingFlights = flightDetailsList.stream()
			        .filter(flight -> flight.getSegmentOrigin().equals(BookingConstant.TENANT_SINGAPORE) && flight.getFlightNumber() != null )
			        .collect(Collectors.toList());
			//create parts for carrier booking
			List<BookingFlightDetails> carrierFlights = flightDetailsList.stream()
			        .filter(flight ->  flight.getFlightNumber() == null || flight.getFlightNumber().isEmpty())
			        .collect(Collectors.toList());
			LOGGER.info("Method getShipmentPartListForTransShipment() -> incoming flight list size -> "+incomingFlights.size() + " outgoingFlightList size-> "+ 
			        outgoingFlights.size()+" carrierFlightsList size -> "+carrierFlights.size());
			flightDetailsList.removeAll(carrierFlights);
			//get cosys flight arrival and departure time
			getCOSYSFlightDetails(flightDetailsList);
			totalIncomingFlightPieces = getTotalPiecesFromFlights(totalIncomingFlightPieces, incomingFlights);
			if(!CollectionUtils.isEmpty(carrierFlights)) {
				for(BookingFlightDetails carrierFlight : carrierFlights) {
					if(carrierFlight.getSegmentDestination().equals(BookingConstant.TENANT_SINGAPORE)) {
						totalCarrierFlightPieces += carrierFlight.getPieces();
					}
				}
			}
			totalOutgoingFlightPieces = getTotalPiecesFromFlights(totalOutgoingFlightPieces, outgoingFlights);
			int shipmentPieces = shipmentBookingDetails.getPieces() - shipmentBookingDetails.getAllocatedPiece();
			if((!carrierFlights.isEmpty() && totalIncomingFlightPieces > totalOutgoingFlightPieces && totalIncomingFlightPieces == shipmentPieces)
					|| totalCarrierFlightPieces == totalOutgoingFlightPieces) {
				carrierFlightFlag = true;
			}
			processTranshipmentPartLogic(bookingDetails, shipmentBookingDetails, flightDetailsList, pairDetails,
					incomingFlights, outgoingFlights,carrierFlightFlag,carrierFlights);
			if(!carrierFlights.isEmpty()) {
				shipmentBookingDetails = shipmentBookingBuilder.mapCarrierFlightsToPart(shipmentBookingDetails,carrierFlights);
			}
		}
    	}
    	catch(Exception e ) {
    		LOGGER.error("Method End getShipmentPartListForTransShipment() Exception",e);
    		throw new MessageProcessingException("Error while processing the message");
    	}
		return shipmentBookingDetails;
	}


    /**
     * get all pieces from flights
     * @param totalIncomingFlightPieces
     * @param incomingFlights
     * @return
     */
	private int getTotalPiecesFromFlights(int totalIncomingFlightPieces,
			List<BookingFlightDetails> incomingFlights) {
		if(!CollectionUtils.isEmpty(incomingFlights)) {
			for(BookingFlightDetails flight : incomingFlights) {
				totalIncomingFlightPieces += flight.getPieces();
			}
		}
		return totalIncomingFlightPieces;
	}

    private void getCOSYSFlightDetails(List<BookingFlightDetails> bookingFlightList){
    	for(BookingFlightDetails flight : bookingFlightList) {
    		Map<String,String> queryParam = new HashMap<>();
    		queryParam.put("flightId",String.valueOf(flight.getFlightId()));
    		queryParam.put("origin", flight.getSegmentOrigin());
    		queryParam.put("destination",flight.getSegmentDestination());
    		OperationalFlightLegInfo operationalFlight = flightBookingDao.getSegmentDepartureAndArrivalDate(queryParam);
    		if(operationalFlight != null) {
    			flight.setCosysSegmentArrivalDate(operationalFlight.getDateSTA());
    			flight.setCosysSegmentDepartureDate(operationalFlight.getDateSTD());
    		}
    		
    	}
    }

	/**
	 * @param bookingDetails
	 * @param shipmentBookingDetails
	 * @param flightDetailsList
	 * @param pairDetails
	 * @param incomingFlights
	 * @param outgoingFlights
	 */
	private void processTranshipmentPartLogic(BookingDetails bookingDetails,
			ShipmentBookingDetails shipmentBookingDetails, List<BookingFlightDetails> flightDetailsList,
			BookingFlightPairDetails pairDetails, List<BookingFlightDetails> incomingFlights,
			List<BookingFlightDetails> outgoingFlights,boolean carrierFlightFlag,List<BookingFlightDetails> carrierFlightList) {
		if(incomingFlights.size() == 1 && outgoingFlights.size() == 1) {
			 oneInOneOutForTransShipment(incomingFlights,outgoingFlights,shipmentBookingDetails,carrierFlightFlag,carrierFlightList);
		}else if(incomingFlights.size() > 1 && outgoingFlights.size() == 1) {
			multipleInOneOutForTransShipment(incomingFlights,outgoingFlights,shipmentBookingDetails,carrierFlightFlag,carrierFlightList);
		}else if(incomingFlights.size() == 1 && outgoingFlights.size() > 1) {
			oneInMultipleOutForTransShipment(incomingFlights,outgoingFlights,bookingDetails,shipmentBookingDetails,carrierFlightFlag,carrierFlightList);
		}else if(incomingFlights.size() > 1 && outgoingFlights.size() > 1) {
			multipleInMultipleOutForTransShipment(incomingFlights,outgoingFlights,pairDetails,shipmentBookingDetails,carrierFlightFlag,carrierFlightList);
		}else {
			callImportExportPartShipmentMethod(bookingDetails, shipmentBookingDetails, flightDetailsList,
					incomingFlights, outgoingFlights, carrierFlightFlag, carrierFlightList);
		}
	}


	/**
	 * call import/Export part creation for Transhipment booking with either incoming flights or outgoing flights
	 * @param bookingDetails
	 * @param shipmentBookingDetails
	 * @param flightDetailsList
	 * @param incomingFlights
	 * @param outgoingFlights
	 * @param carrierFlightFlag
	 * @param carrierFlightList
	 */
	private void callImportExportPartShipmentMethod(BookingDetails bookingDetails,
			ShipmentBookingDetails shipmentBookingDetails, List<BookingFlightDetails> flightDetailsList,
			List<BookingFlightDetails> incomingFlights, List<BookingFlightDetails> outgoingFlights,
			boolean carrierFlightFlag, List<BookingFlightDetails> carrierFlightList) {
		if((!incomingFlights.isEmpty() && outgoingFlights.isEmpty()) || (incomingFlights.isEmpty() && !outgoingFlights.isEmpty())) {
			shipmentBookingBuilder.getShipmentPartListForImportExport(flightDetailsList,bookingDetails,shipmentBookingDetails);
			if(carrierFlightFlag) {
				carrierFlightList.clear();
			}
		}else {
			throw new MessageProcessingException("Incorrect flight booking details");
		}
	}
    
	
	/**
	 * create part list for a incoming flight and a outgoing flight 
	 * @param incomingFlights
	 * @param outgoingFlights
	 * @param shipmentPartList
	 * @return
	 */
	private ShipmentBookingDetails oneInOneOutForTransShipment(List<BookingFlightDetails> incomingFlights,List<BookingFlightDetails> outgoingFlights,ShipmentBookingDetails shipmentBookingDetails,
			boolean carrierFlightFlag,List<BookingFlightDetails> carrierFlightList){
		List<ShipmentFlightPartBookingDetails> shipmentPartList = new ArrayList<>();
		
		incomingFlights.forEach(incomingFlight -> {
			outgoingFlights.forEach(outgoingFlight -> {
				System.out.println("check ::"+ StringUtils.isEmpty(outgoingFlight.getSegmentDepartureDate()));
				if(StringUtils.isEmpty(incomingFlight.getCosysSegmentArrivalDate()) || StringUtils.isEmpty(outgoingFlight.getCosysSegmentDepartureDate())) {
					throw new MessageProcessingException("Flight STA/STD can't be Empty");
				}
				else if(!StringUtils.isEmpty(incomingFlight.getCosysSegmentArrivalDate()) && !StringUtils.isEmpty(outgoingFlight.getCosysSegmentDepartureDate())) {
				LocalDateTime incomingFlightSTA = commonUtil.convertStringToLocalTime(incomingFlight.getCosysSegmentArrivalDate(), ValidationConstant.XML_DATETIME_FORMAT);
				LocalDateTime outgoingFlightSTD = commonUtil.convertStringToLocalTime(outgoingFlight.getCosysSegmentDepartureDate(), ValidationConstant.XML_DATETIME_FORMAT);
				LOGGER.info("Method oneInOneOutForTransShipment() -> incomingFlightSTA ->"+incomingFlightSTA+" outgoingFlightSTD ->"+outgoingFlightSTD );
				if(!incomingFlightSTA.isBefore(outgoingFlightSTD)) {
					throw new MessageProcessingException("Incoming flight STA is greater than outgoing flight STD");
				}else {
					//set parts based on incoming flight
					createPartBasedOnCarrierFlag(shipmentBookingDetails, carrierFlightFlag, carrierFlightList,
							shipmentPartList, incomingFlight, outgoingFlight);
				}
				}
			});
		});
		LOGGER.info("Method End oneInOneOutForTransShipment-> shipmentPartList ->"+shipmentPartList.toString());
		shipmentBookingDetails.setPartBookingList(shipmentPartList);
		return shipmentBookingDetails;
	}


	/**
	 * create part based on carrier flights
	 * @param shipmentBookingDetails
	 * @param carrierFlightFlag
	 * @param carrierFlightList
	 * @param shipmentPartList
	 * @param incomingFlight
	 * @param outgoingFlight
	 */
	private void createPartBasedOnCarrierFlag(ShipmentBookingDetails shipmentBookingDetails, boolean carrierFlightFlag,
			List<BookingFlightDetails> carrierFlightList, List<ShipmentFlightPartBookingDetails> shipmentPartList,
			BookingFlightDetails incomingFlight, BookingFlightDetails outgoingFlight) {
		if(!carrierFlightFlag) {
			incomingFlight.setBalancePiece(incomingFlight.getPieces()); 
			incomingFlight.setBalanceWeight(incomingFlight.getWeight());
			shipmentBookingBuilder.mapInFlightsToPartDetailsForTransshipment(shipmentPartList, incomingFlight, outgoingFlight,shipmentBookingDetails);
		}else {
			createPartWithCarrierFlights(shipmentBookingDetails, carrierFlightList, shipmentPartList,
					incomingFlight, outgoingFlight);
		}
	}


	/**
	 * 
	 * @param shipmentBookingDetails
	 * @param carrierFlightList
	 * @param shipmentPartList
	 * @param incomingFlight
	 * @param outgoingFlight
	 */
	private void createPartWithCarrierFlights(ShipmentBookingDetails shipmentBookingDetails,
			List<BookingFlightDetails> carrierFlightList, List<ShipmentFlightPartBookingDetails> shipmentPartList,
			BookingFlightDetails incomingFlight, BookingFlightDetails outgoingFlight) {
		int carrierPieces = 0;
		double carrierWeight = 0.0;
		for(BookingFlightDetails carrierFlight : carrierFlightList) {
			carrierPieces += carrierFlight.getPieces();
			carrierWeight += carrierFlight.getWeight();
		}
		
		incomingFlight.setBalancePiece(incomingFlight.getPieces() - carrierPieces); 
		incomingFlight.setBalanceWeight(incomingFlight.getWeight()- carrierWeight);
		shipmentBookingBuilder.mapInFlightsToPartDetailsForTransshipment(shipmentPartList, incomingFlight, outgoingFlight,shipmentBookingDetails);
		incomingFlight.setBalancePiece(carrierPieces); 
		incomingFlight.setBalanceWeight(carrierWeight);
		shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, incomingFlight, shipmentBookingDetails);
		carrierFlightList.clear();
	}
	
	/**
	 * Method to create parts for multiple incoming flight and one outgoing flight
	 * @param incomingFlights
	 * @param outgoingFlights
	 * @param shipmentPartList
	 * @return
	 */
	private ShipmentBookingDetails multipleInOneOutForTransShipment(List<BookingFlightDetails> incomingFlights,List<BookingFlightDetails> outgoingFlights,ShipmentBookingDetails shipmentBookingDetails,
			boolean carrierFlightFlag,List<BookingFlightDetails> carrierFlightList){
		List<ShipmentFlightPartBookingDetails> shipmentPartList = new ArrayList<>();
		validatePairedFlight(outgoingFlights,incomingFlights);
		if(!incomingFlights.isEmpty()) {
			//create parts weight/piece based on incoming flight piece and weight
			incomingFlights.forEach(incomingFlight -> {
				outgoingFlights.forEach(outgoingFlight -> {
					if(outgoingFlight.getPieces()>0) {
						incomingFlight.setBalancePiece(incomingFlight.getPieces()); 
						incomingFlight.setBalanceWeight(incomingFlight.getWeight());
						shipmentBookingBuilder.mapInFlightsToPartDetailsForTransshipment(shipmentPartList, incomingFlight, outgoingFlight,shipmentBookingDetails);
						outgoingFlight.setPieces(outgoingFlight.getPieces() - incomingFlight.getPieces());
					}else {
						incomingFlight.setBalancePiece(incomingFlight.getPieces()); 
						incomingFlight.setBalanceWeight(incomingFlight.getWeight());
						shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, incomingFlight ,shipmentBookingDetails);
						if(carrierFlightFlag) {
							carrierFlightList.clear();
						}
					}
				});
			});
		}else {
			throw new MessageProcessingException("Incoming flight STA is greater than outgoing flight STD");
		}
		LOGGER.info("Method End multipleInOneOutForTransShipment->shipmentPartList ->"+shipmentPartList.toString());
		shipmentBookingDetails.setPartBookingList(shipmentPartList);
		return shipmentBookingDetails;
	}
	/**
	 * Method to create parts for one incoming flight and multiple outgoing flight
	 * @param incomingFlights
	 * @param outgoingFlights
	 * @param bookingDetails
	 * @param shipmentPartList
	 * @return
	 */
	private ShipmentBookingDetails oneInMultipleOutForTransShipment(List<BookingFlightDetails> incomingFlights,List<BookingFlightDetails> outgoingFlights,BookingDetails bookingDetails,
			ShipmentBookingDetails shipmentBookingDetails,boolean carrierFlightFlag,List<BookingFlightDetails> carrierFlightList){
		List<ShipmentFlightPartBookingDetails> shipmentPartList = new ArrayList<>();
		validatePairedOutgoingFlight(incomingFlights, outgoingFlights);
		if(!outgoingFlights.isEmpty()) {
			//sort outgoing flight based on depature date
			sortFlightListOnDepatureDate(outgoingFlights);
			BookingFlightDetails incomingFlight = incomingFlights.get(0);
			outgoingFlights.forEach(outgoingFlight -> {
				if(incomingFlight.getPieces()>0) {
					ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
						part.setPartPieces(outgoingFlight.getPieces());
						part.setPartWeight(outgoingFlight.getWeight());
						double partVolume = shipmentBookingBuilder.calculatePartVolume(outgoingFlight.getVolume(), outgoingFlight.getPieces(), part.getPartPieces());
						part.setVolume(partVolume);
						part.setVolumeUnitCode(shipmentBookingBuilder.convertVolumeUnitCodeTOCosysVolumeUnitCode(shipmentBookingDetails.getVolumeUnitCode()));
						shipmentBookingDetails.setAllocatedPiece(shipmentBookingDetails.getAllocatedPiece() + part.getPartPieces());
						shipmentBookingDetails.setAllocatedWeight(shipmentBookingDetails.getAllocatedWeight() + part.getPartWeight());
						shipmentBookingDetails.setAllocatedVolume(shipmentBookingDetails.getAllocatedVolume() + part.getVolume());
						List<ShipmentFlightBookingDetails> partFlightList = new ArrayList<>();
						//add incoming flight details
						partFlightList.add(shipmentBookingBuilder.mapValuesToFlightModel(incomingFlight,shipmentBookingDetails.getVolumeUnitCode()));
						partFlightList.add(shipmentBookingBuilder.mapValuesToFlightModel(outgoingFlight,shipmentBookingDetails.getVolumeUnitCode()));
						incomingFlight.setPieces(incomingFlight.getPieces()-outgoingFlight.getPieces());
						part.setShipmentFlightBookingList(partFlightList);
						shipmentPartList.add(part);
				}
			});
			if(!CollectionUtils.isEmpty(carrierFlightList)) {
				if(carrierFlightFlag) {
					int carrierPieces = 0;
					double carrierWeight = 0.0;
					for(BookingFlightDetails carrierFlight : carrierFlightList) {
						carrierPieces += carrierFlight.getPieces();
						carrierWeight += carrierFlight.getWeight();
					}
					incomingFlight.setBalancePiece(carrierPieces); 
					incomingFlight.setBalanceWeight(carrierWeight);
					shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, incomingFlight, shipmentBookingDetails);
					carrierFlightList.clear();
				}
			}
		}else {
			throw new MessageProcessingException("Incoming flight STA is greater than outgoing flight STD");
		}
		shipmentBookingDetails.setPartBookingList(shipmentPartList);
		return shipmentBookingDetails;
	}

	/**
	 * Validate paired flights
	 * @param fixedFlights
	 * @param comparedFlights
	 */
	private List<BookingFlightDetails> validatePairedFlight(List<BookingFlightDetails> fixedFlights,
			List<BookingFlightDetails> comparedFlights) {
		List<BookingFlightDetails> removeFlights = new ArrayList<>();
		for (BookingFlightDetails comparedFlight : comparedFlights) {
			for (BookingFlightDetails fixedFlight : fixedFlights) {
				if(StringUtils.isEmpty(comparedFlight.getCosysSegmentArrivalDate()) || StringUtils.isEmpty(fixedFlight.getCosysSegmentDepartureDate())) {
					throw new MessageProcessingException("Flight STA/STD can't be Empty");
				}
				else if(!StringUtils.isEmpty(comparedFlight.getCosysSegmentArrivalDate()) && !StringUtils.isEmpty(fixedFlight.getCosysSegmentDepartureDate())) {
					LocalDateTime comparedFlightSTA = commonUtil.convertStringToLocalTime(comparedFlight.getCosysSegmentArrivalDate(), ValidationConstant.XML_DATETIME_FORMAT);
					LocalDateTime fixedFlightSTD = commonUtil.convertStringToLocalTime(fixedFlight.getCosysSegmentDepartureDate(), ValidationConstant.XML_DATETIME_FORMAT);
					if(!comparedFlightSTA.isBefore(fixedFlightSTD)) {
					removeFlights.add(comparedFlight);
					}
				}
			}
		}
		if(!removeFlights.isEmpty()) comparedFlights.removeAll(removeFlights);
		return comparedFlights;
	}
	
	/**
	 * validate arrival and depature time for one incoming flight and multiple outgoing flights
	 * @param fixedFlights
	 * @param comparedFlights
	 */
	private List<BookingFlightDetails> validatePairedOutgoingFlight(List<BookingFlightDetails> incomingFlights,
			List<BookingFlightDetails> outgoingFlights) {
		List<BookingFlightDetails> removeFlights = new ArrayList<>();
		for (BookingFlightDetails outgoingFlight : outgoingFlights) {
			for (BookingFlightDetails incomingFlight : incomingFlights) {
				if(StringUtils.isEmpty(incomingFlight.getCosysSegmentArrivalDate()) || StringUtils.isEmpty(outgoingFlight.getCosysSegmentDepartureDate())) {
					throw new MessageProcessingException("Flight STA/STD can't be Empty");
				}
				else if(!StringUtils.isEmpty(incomingFlight.getCosysSegmentArrivalDate()) && !StringUtils.isEmpty(outgoingFlight.getCosysSegmentDepartureDate())) {
					LocalDateTime incomingFlightSTA = commonUtil.convertStringToLocalTime(incomingFlight.getCosysSegmentArrivalDate(), ValidationConstant.XML_DATETIME_FORMAT);
					LocalDateTime outgoingFlightSTD = commonUtil.convertStringToLocalTime(outgoingFlight.getCosysSegmentDepartureDate(), ValidationConstant.XML_DATETIME_FORMAT);
					if(outgoingFlightSTD.isBefore(incomingFlightSTA)) {
						removeFlights.add(outgoingFlight);
					}
				}
			}
		}
		if(!removeFlights.isEmpty()) outgoingFlights.removeAll(removeFlights);
		return outgoingFlights;
	}
	/**
	 * set remaining piece
	 * @param bookingDetails
	 * @param shipmentPartList
	 */
	private void setRemainingPieceAndWeight(BookingDetails bookingDetails,
			List<ShipmentFlightPartBookingDetails> shipmentPartList,ShipmentBookingDetails shipmentBookingDetails) {
		int allocatedPiece = shipmentBookingDetails.getAllocatedPiece();
		double allocatedWeight = shipmentBookingDetails.getAllocatedWeight();
		if(bookingDetails.getShipmentDetails().getTotalNumberOfPieces() != allocatedPiece) {
			int balancePiece = bookingDetails.getShipmentDetails().getTotalNumberOfPieces() - allocatedPiece;
			double balanceWeight = bookingDetails.getShipmentDetails().getTotalWeight() -allocatedWeight;
			//Adding balance piece/weight with part piece/weight
			int partPiece = shipmentPartList.get(shipmentPartList.size()-1).getPartPieces() + balancePiece;
			double partWeight = shipmentPartList.get(shipmentPartList.size()-1).getPartWeight() + balanceWeight;
			shipmentPartList.get(shipmentPartList.size()-1).setPartPieces(partPiece);
			shipmentPartList.get(shipmentPartList.size()-1).setPartWeight(partWeight);
		}
	}
	
	/**
	 * sort flight list based on departure date
	 * @param outgoingFlights
	 */
	private void sortFlightListOnDepatureDate(List<BookingFlightDetails> outgoingFlights) {
		Collections.sort(outgoingFlights, new Comparator<BookingFlightDetails>() {
			  public int compare(BookingFlightDetails o1, BookingFlightDetails o2) {
			      if (o1.getCosysSegmentDepartureDate() == null || o2.getCosysSegmentDepartureDate() == null) {
			        return 0;
			      }
			      else {
			    	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ValidationConstant.XML_DATETIME_FORMAT);
		    	  	  LocalDateTime l1 = LocalDateTime.parse(o1.getCosysSegmentDepartureDate(),formatter);
		    	  	  LocalDateTime l2 = LocalDateTime.parse(o2.getCosysSegmentDepartureDate(),formatter);
		    	  	  return l1.compareTo(l2);
			  		}
			  }
			});
	}
	
	/**
	 * sort flight list based on arrival date
	 * @param outgoingFlights
	 */
	private void sortFlightListOnArrivalDate(List<BookingFlightDetails> incomingFlights) {
		Collections.sort(incomingFlights, new Comparator<BookingFlightDetails>() {
			  public int compare(BookingFlightDetails o1, BookingFlightDetails o2) {
			      if (o1.getCosysSegmentArrivalDate() == null || o2.getCosysSegmentArrivalDate() == null) {
			        return 0;
			  }
		      else {
		    	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ValidationConstant.XML_DATETIME_FORMAT);
	    	  	  LocalDateTime l1 = LocalDateTime.parse(o1.getCosysSegmentDepartureDate(),formatter);
	    	  	  LocalDateTime l2 = LocalDateTime.parse(o2.getCosysSegmentDepartureDate(),formatter);
	    	  	  return l1.compareTo(l2);
		  		}
			  }
			});
	}
	/**
	 * Method to create parts for multiple incoming flight and multiple outgoing flight scenario
	 * @param incomingFlights
	 * @param outgoingFlights
	 * @param pairDetails
	 * @param shipmentPartList
	 * @return
	 */
	private ShipmentBookingDetails multipleInMultipleOutForTransShipment(List<BookingFlightDetails> incomingFlights,List<BookingFlightDetails> outgoingFlights,BookingFlightPairDetails pairDetails,
			ShipmentBookingDetails shipmentBookingDetails,boolean carrierFlightFlag,List<BookingFlightDetails> carrierFlightList){
		List<ShipmentFlightPartBookingDetails> shipmentPartList = new ArrayList<>();
		if(pairDetails != null) {
			List<FlightPair> sinPairList = getSinPairList(pairDetails.getFlightPair());
			LOGGER.info("Method Start multipleInMultipleOutForTransShipment() -> Sin pair list size::"+sinPairList.size());
			for (FlightPair pairType : sinPairList) {
				List<FlightDetails> flightPairList = pairType.getFlightDetails();
	
				BookingFlightDetails inFlight = null;
				BookingFlightDetails outFlight = null;
				for (FlightDetails flightDetails : flightPairList) {
					if(BookingConstant.TENANT_SINGAPORE.equalsIgnoreCase(flightDetails.getSegmentDestination())) {
						inFlight = getIncomingFlightDetail(incomingFlights, inFlight, flightDetails);
					}
					if(BookingConstant.TENANT_SINGAPORE.equalsIgnoreCase(flightDetails.getSegmentOrigin())) {
						outFlight = getOutgoingFlightDetail(outgoingFlights, outFlight, flightDetails);
					}
				}
				createPartsBasedOnPairDetails(incomingFlights, outgoingFlights, shipmentPartList, shipmentBookingDetails, inFlight, outFlight);	
				
			}
			LOGGER.info("Method Start multipleInMultipleOutForTransShipment() ->After flight pair part created incoming flights list ->"+incomingFlights.toString());
			LOGGER.info("Method Start multipleInMultipleOutForTransShipment() ->After flight pair part created outgoing flights list ->"+outgoingFlights.toString());
		}
		mapToUnpairedFlightPieceToBalancePiece(incomingFlights, outgoingFlights);
		//logic for remaining piece and weight
		mapBalancePiecesToPart(shipmentPartList, outgoingFlights, incomingFlights,shipmentBookingDetails,carrierFlightFlag,carrierFlightList);
		shipmentBookingDetails.setPartBookingList(shipmentPartList);
		return shipmentBookingDetails;
	}

	private BookingFlightDetails getOutgoingFlightDetail(List<BookingFlightDetails> outgoingFlights,
			BookingFlightDetails outFlight, FlightDetails flightDetails) {
		
			for (BookingFlightDetails outgoingFlight : outgoingFlights) {
				String flightNumber = outgoingFlight.getCarrierCode() + outgoingFlight.getFlightNumber();
				LocalDate outgoingFlightDate = commonUtil.convertStringToLocalDate(outgoingFlight.getFlightDate(), ValidationConstant.XML_DATE_FORMAT);
				LocalDate flightDate = commonUtil.convertStringToLocalDate(outgoingFlight.getFlightDate(), ValidationConstant.XML_DATE_FORMAT);
				if(outgoingFlight.getSegmentOrigin().equalsIgnoreCase(flightDetails.getSegmentOrigin())
						&& outgoingFlight.getSegmentDestination().equalsIgnoreCase(flightDetails.getSegmentDestination()) &&
						 outgoingFlightDate.isEqual(flightDate) && 
						 flightDetails.getFlightNumber().equalsIgnoreCase(flightNumber)) {
					outFlight = outgoingFlight;
					if(outFlight.getBalancePiece() == 0) {
						outFlight.setBalancePiece(outFlight.getPieces());
						outFlight.setBalanceWeight(outFlight.getWeight());
					}
					break;
				}
			}
		return outFlight;
	}
	/**
	 * get incoming flight detail
	 * @param incomingFlights
	 * @param inFlight
	 * @param flightDetails
	 * @return
	 */
	private BookingFlightDetails getIncomingFlightDetail(List<BookingFlightDetails> incomingFlights,
			BookingFlightDetails inFlight, FlightDetails flightDetails) {
		for (BookingFlightDetails incomingFlight : incomingFlights) {	
				String flightNumber = incomingFlight.getCarrierCode() + incomingFlight.getFlightNumber();
				LocalDate incomingFlightDate = commonUtil.convertStringToLocalDate(incomingFlight.getFlightDate(), ValidationConstant.XML_DATE_FORMAT);
				LocalDate flightDate = commonUtil.convertStringToLocalDate(incomingFlight.getFlightDate(), ValidationConstant.XML_DATE_FORMAT);
				if(flightDetails.getFlightNumber().equalsIgnoreCase(flightNumber)
						&& incomingFlightDate.isEqual(flightDate)) {
					inFlight = incomingFlight;
					if(inFlight.getBalancePiece() == 0) {
						inFlight.setBalancePiece(inFlight.getPieces()); 
						inFlight.setBalanceWeight(inFlight.getWeight());
					}
					break;
				}
		}
		return inFlight;
	}
	
	/**
	 * map flight piece,weight,volume to balance piece, weight,volume for the flights not present in pairing list
	 * @param incomingFlights
	 * @param outgoingFlights
	 */
	private void mapToUnpairedFlightPieceToBalancePiece(List<BookingFlightDetails> incomingFlights,
			List<BookingFlightDetails> outgoingFlights) {
		incomingFlights.forEach(inFlight -> {
			if(!inFlight.isPairPresent()) {
				inFlight.setBalancePiece(inFlight.getPieces()); 
				inFlight.setBalanceWeight(inFlight.getWeight());
				}});
		outgoingFlights.forEach(outFlight -> {
			if(!outFlight.isPairPresent()) {
				outFlight.setBalancePiece(outFlight.getPieces());
				outFlight.setBalanceWeight(outFlight.getWeight());
				}});
	}
	
	/**
	 * create part details based on pair details present in booking publish model
	 * @param incomingFlights
	 * @param outgoingFlights
	 * @param shipmentPartList
	 * @param volumeUnitCode
	 * @param inFlight
	 * @param outFlight
	 */
	private void createPartsBasedOnPairDetails(List<BookingFlightDetails> incomingFlights,
			List<BookingFlightDetails> outgoingFlights, List<ShipmentFlightPartBookingDetails> shipmentPartList,
			ShipmentBookingDetails shipmentBookingDetails, BookingFlightDetails inFlight,
			BookingFlightDetails outFlight) {
		if(inFlight != null && outFlight != null) {
			LOGGER.info("Method createPartsBasedOnPairDetails() -> inFlight balance piece ->"+inFlight.getBalancePiece()+" outFlight balance piece ->"+outFlight.getBalancePiece());
			if(inFlight.getBalancePiece() == outFlight.getBalancePiece()) {
				shipmentBookingBuilder.mapInFlightsToPartDetailsForTransshipment(shipmentPartList,inFlight,outFlight,shipmentBookingDetails);
				//Remove flight detail from in and out flight list as we mapped all piece and wieght
				incomingFlights.remove(inFlight);
				outgoingFlights.remove(outFlight);
			}
			else if(inFlight.getBalancePiece() < outFlight.getBalancePiece()) {
				outgoingFlights.remove(outFlight);
				outFlight.setBalancePiece(outFlight.getBalancePiece() - inFlight.getBalancePiece());
				double weightPerPiece = inFlight.getBalanceWeight() / inFlight.getBalancePiece();
				double balanceOutWeight = weightPerPiece * outFlight.getBalancePiece();
				outFlight.setBalanceWeight(balanceOutWeight);
				outFlight.setPairPresent(true);
				outgoingFlights.add(outFlight);
				shipmentBookingBuilder.mapInFlightsToPartDetailsForTransshipment(shipmentPartList,inFlight,outFlight,shipmentBookingDetails);
				incomingFlights.remove(inFlight);
			} 
			else if(inFlight.getBalancePiece()>outFlight.getBalancePiece()) {
				calculateOutFlightWeightBasedOnInFlightWeight(inFlight,outFlight);
				incomingFlights.remove(inFlight);
				inFlight.setBalancePiece(inFlight.getBalancePiece() - outFlight.getBalancePiece());
				inFlight.setBalanceWeight(inFlight.getBalanceWeight() - outFlight.getBalanceWeight());
				inFlight.setPairPresent(true);
				incomingFlights.add(inFlight);
				shipmentBookingBuilder.mapOutgoingFlightsToPartForTransshipment(shipmentPartList,inFlight,outFlight,shipmentBookingDetails);
				outgoingFlights.remove(outFlight);
			}else {
				throw new MessageProcessingException("Invalid flight pairing");
			}
		}
	}
	/**
	 * map balance pieces in Flight to part
	 * @param shipmentPartList
	 * @param balanceOutList
	 * @param balanceInList
	 */
	
	// Refactor only after first run - Possibility of merging to earlier pieces of code.
	private void mapBalancePiecesToPart(List<ShipmentFlightPartBookingDetails> shipmentPartList,
			List<BookingFlightDetails> outgoingFlightList, List<BookingFlightDetails> incomingFlightList,ShipmentBookingDetails shipmentBookingDetails,
			boolean carrierFlightFlag,List<BookingFlightDetails> carrierFlightList) {
		List<BookingFlightDetails> removeIncomingFlightList = new ArrayList<>();
		List<BookingFlightDetails> removeOutgoingFlightList = new ArrayList<>();
		if(!incomingFlightList.isEmpty() && !outgoingFlightList.isEmpty()) {
			//sort balance incoming flights
			sortFlightListOnArrivalDate(incomingFlightList);
			//sort balance outgoing flights
			sortFlightListOnDepatureDate(outgoingFlightList);
			
			//check for exact matching piece and weight
			checkExactMatchForBalancePieces(shipmentPartList, outgoingFlightList, incomingFlightList,shipmentBookingDetails);
			
			if(!incomingFlightList.isEmpty() && !outgoingFlightList.isEmpty()) {
				//check incoming piece is greater then - minus with outgoing
				for(BookingFlightDetails inFlight : incomingFlightList) {
					for(BookingFlightDetails outFlight : outgoingFlightList){
						if(inFlight.getBalancePiece() > outFlight.getBalancePiece()) {
							if(StringUtils.isEmpty(inFlight.getCosysSegmentArrivalDate()) || StringUtils.isEmpty(outFlight.getCosysSegmentDepartureDate())) {
								throw new MessageProcessingException("Flight STA/STD can't be Empty");
							}
							else if(!StringUtils.isEmpty(inFlight.getCosysSegmentArrivalDate()) && !StringUtils.isEmpty(outFlight.getCosysSegmentDepartureDate())) {
								LocalDateTime inFlightSTA = commonUtil.convertStringToLocalTime(inFlight.getCosysSegmentArrivalDate(), ValidationConstant.XML_DATETIME_FORMAT);
								LocalDateTime outFlightSTD = commonUtil.convertStringToLocalTime(outFlight.getCosysSegmentDepartureDate(), ValidationConstant.XML_DATETIME_FORMAT);
								if(outFlightSTD.isAfter(inFlightSTA)) {
									if(outFlight.getBalancePiece()>0) {
										inFlight.setBalancePiece(inFlight.getBalancePiece() - outFlight.getBalancePiece());
										inFlight.setBalanceWeight(inFlight.getBalanceWeight() - outFlight.getBalanceWeight());
										shipmentBookingBuilder.mapBalancePeiceOutFlightDetailsToPartForTransshipment(shipmentPartList,inFlight,outFlight,shipmentBookingDetails);
										removeOutgoingFlightList.add(outFlight);
										outFlight.setBalancePiece(0);
										outFlight.setBalanceWeight(0);
										break;
									}
									
								}
							}
						}
					}
				}
				outgoingFlightList.removeAll(removeOutgoingFlightList);
				
				//check for exact match and outgoing piece is greater
				if(!incomingFlightList.isEmpty() && !outgoingFlightList.isEmpty()) {
					checkExactMatchForBalancePieces(shipmentPartList, outgoingFlightList,incomingFlightList,shipmentBookingDetails);
					if(!incomingFlightList.isEmpty() && !outgoingFlightList.isEmpty()) {
						for(BookingFlightDetails inFlight : incomingFlightList) {
							for(BookingFlightDetails outFlight : outgoingFlightList){
								if(inFlight.getBalancePiece() < outFlight.getBalancePiece()) {
									if(StringUtils.isEmpty(inFlight.getCosysSegmentArrivalDate()) || StringUtils.isEmpty(outFlight.getCosysSegmentDepartureDate())) {
										throw new MessageProcessingException("Flight STA/STD can't be Empty");
									}
									else if(!StringUtils.isEmpty(inFlight.getCosysSegmentArrivalDate()) && !StringUtils.isEmpty(outFlight.getCosysSegmentDepartureDate())) {
										LocalDateTime inFlightSTA = commonUtil.convertStringToLocalTime(inFlight.getCosysSegmentArrivalDate(), ValidationConstant.XML_DATETIME_FORMAT);
										LocalDateTime outFlightSTD = commonUtil.convertStringToLocalTime(outFlight.getCosysSegmentDepartureDate(), ValidationConstant.XML_DATETIME_FORMAT);
										if(outFlightSTD.isAfter(inFlightSTA)) {
											if(inFlight.getBalancePiece()>0) {
												outFlight.setBalancePiece(outFlight.getBalancePiece() - inFlight.getBalancePiece());
												outFlight.setBalanceWeight(outFlight.getBalanceWeight() - inFlight.getBalanceWeight());
												shipmentBookingBuilder.mapInFlightsToPartDetailsForTransshipment(shipmentPartList,inFlight,outFlight,shipmentBookingDetails);
												removeIncomingFlightList.add(inFlight);
												inFlight.setBalancePiece(0);
												inFlight.setBalanceWeight(0);
												break;
											}
										}
									}
								}
							}
						}
						incomingFlightList.removeAll(removeIncomingFlightList);
						if(!incomingFlightList.isEmpty() && !outgoingFlightList.isEmpty()) {
							checkExactMatchForBalancePieces(shipmentPartList, outgoingFlightList, incomingFlightList,shipmentBookingDetails);
						}else if(!incomingFlightList.isEmpty() && outgoingFlightList.isEmpty()) {
							incomingFlightList.forEach(inFlight -> {
								shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, inFlight ,shipmentBookingDetails);
							});
						}
					}else if(!incomingFlightList.isEmpty() && outgoingFlightList.isEmpty()) {
						incomingFlightList.forEach(inFlight -> {
							shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, inFlight ,shipmentBookingDetails);
						});
					}
					
				}else if(!incomingFlightList.isEmpty()) {
					incomingFlightList.forEach(inFlight -> {
						shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, inFlight ,shipmentBookingDetails);
					});
				}
			}
			else if(!incomingFlightList.isEmpty() && outgoingFlightList.isEmpty()) {
				if(carrierFlightFlag) {
					carrierFlightList.clear();
				}
					incomingFlightList.forEach(inFlight -> {
						shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, inFlight ,shipmentBookingDetails);
					});
				
				
			}
		}else if(!incomingFlightList.isEmpty() && outgoingFlightList.isEmpty()) {
			if(carrierFlightFlag) {
				carrierFlightList.clear();
			}
				incomingFlightList.forEach(inFlight -> {
					shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, inFlight ,shipmentBookingDetails);
				});
			
			
		}
	}

	/**
	 * check for exact piece and weight matching
	 * @param shipmentPartList
	 * @param balanceOutList
	 * @param balanceInList
	 * @param volumeUnitCode
	 */
	private void checkExactMatchForBalancePieces(List<ShipmentFlightPartBookingDetails> shipmentPartList,
			List<BookingFlightDetails> outgoingFlightList, List<BookingFlightDetails> incomingFlightList,ShipmentBookingDetails shipmentBookingDetails) {
		List<BookingFlightDetails> removeFlightInIncomingFlightList = new ArrayList<>();
		List<BookingFlightDetails> removeFlightInOutgoingFlightList = new ArrayList<>();
		
		for(BookingFlightDetails outFlight : outgoingFlightList) {
			for(BookingFlightDetails inFlight : incomingFlightList) {
				if(!inFlight.isPairPresent()) {
					if(inFlight.getBalancePiece() == outFlight.getBalancePiece()) {
						if(StringUtils.isEmpty(inFlight.getCosysSegmentArrivalDate()) || StringUtils.isEmpty(outFlight.getCosysSegmentDepartureDate())) {
							throw new MessageProcessingException("Flight STA/STD can't be Empty");
						}
						else if(!StringUtils.isEmpty(inFlight.getCosysSegmentArrivalDate()) && !StringUtils.isEmpty(outFlight.getCosysSegmentDepartureDate())) {
							
							LocalDateTime inFlightSTA = commonUtil.convertStringToLocalTime(inFlight.getCosysSegmentArrivalDate(), ValidationConstant.XML_DATETIME_FORMAT);
							LocalDateTime outFlightSTD = commonUtil.convertStringToLocalTime(outFlight.getCosysSegmentDepartureDate(), ValidationConstant.XML_DATETIME_FORMAT);
							if(outFlightSTD.isAfter(inFlightSTA)) {
								shipmentBookingBuilder.mapInFlightsToPartDetailsForTransshipment(shipmentPartList,inFlight,outFlight,shipmentBookingDetails);
								inFlight.setPairPresent(true);
								outFlight.setPairPresent(true);
								removeFlightInIncomingFlightList.add(inFlight);
								removeFlightInOutgoingFlightList.add(outFlight);
								break;
							}else {
								shipmentBookingBuilder.mapInFlightDetailsToPart(shipmentPartList, inFlight ,shipmentBookingDetails);
								inFlight.setPairPresent(true);
								outFlight.setPairPresent(true);
								removeFlightInIncomingFlightList.add(inFlight);
								removeFlightInOutgoingFlightList.add(outFlight);
								break;
							}
						}
					}
				}
			}
		}
		
		incomingFlightList.removeAll(removeFlightInIncomingFlightList);
		outgoingFlightList.removeAll(removeFlightInOutgoingFlightList);
		
	}
	
	/**
	 * get sin pair list
	 * @param pairList
	 * @return
	 */
	private List<FlightPair> getSinPairList(List<FlightPair> pairList) {
		List<FlightPair> sinPairList = new ArrayList<>();
		for (FlightPair pair : pairList) {
			int sinPairCount = 0;
			for (FlightDetails flightPair : pair.getFlightDetails()) {
				if(flightPair.getSegmentOrigin().equalsIgnoreCase(BookingConstant.TENANT_SINGAPORE) || flightPair.getSegmentDestination().equalsIgnoreCase(BookingConstant.TENANT_SINGAPORE)) sinPairCount++;
			}
			if(sinPairCount == 2) {
				sinPairList.add(pair);
			}
		}
		return sinPairList;
	}
	/**
	 * calculate outgoing flight weight based on incoming flight weight
	 * @param inFlight
	 * @param outFlight
	 */
	private void calculateOutFlightWeightBasedOnInFlightWeight(BookingFlightDetails inFlight,BookingFlightDetails outFlight) {
		double weightPerPiece = inFlight.getWeight() / inFlight.getPieces();
		double outWeight = weightPerPiece * outFlight.getPieces();
		outFlight.setWeight(outWeight);
	}
	

    
}
