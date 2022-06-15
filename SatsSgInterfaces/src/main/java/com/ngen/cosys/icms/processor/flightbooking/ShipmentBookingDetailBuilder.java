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


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingDimensions;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightPartBookingDetails;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentFlightBookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingCommodityDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails;
import com.ngen.cosys.icms.schema.flightbooking.DimensionDetaills;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;

/**
 * This class helps to build shipment booking detail model
 *
 */

@Component
public class ShipmentBookingDetailBuilder {
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	BookingInsertHelper bookingInsertHelper;
	
	@Autowired
	PartBookingHelper partBookingHelper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightBookingProcessor.class);
	
	/**
     * Mapping part values for import and export case
     * @param flightDetailsList
     * @param shipmentPartList
     */
	public ShipmentBookingDetails getShipmentPartListForImportExport(List<BookingFlightDetails> flightDetailsList,BookingDetails bookingDetails,ShipmentBookingDetails shipmentBookingDetails) {
		List<ShipmentFlightPartBookingDetails> shipmentPartList = new ArrayList<>();
		flightDetailsList.forEach(flight -> {
			double volume = 0.01;
    		ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
    		part.setPartPieces(flight.getPieces());
    		part.setPartWeight(flight.getWeight());
    		shipmentBookingDetails.setAllocatedPiece(shipmentBookingDetails.getAllocatedPiece() + flight.getPieces());
    		shipmentBookingDetails.setAllocatedWeight(shipmentBookingDetails.getAllocatedWeight() + flight.getWeight());
    		volume = calculatePartVolume(flight.getVolume(), flight.getPieces(), flight.getPieces());
    		shipmentBookingDetails.setAllocatedVolume(shipmentBookingDetails.getAllocatedVolume() + volume);
    		if(volume<0.01) {
    			part.setVolume(0.01);
    		}else {
    			part.setVolume(volume);
    		}
    		part.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(bookingDetails.getVolumeUnit()));
    		part.setCreatedBy(BookingConstant.CREATEDBY);
    		
    		List<ShipmentFlightBookingDetails> partFlightList = new ArrayList<>();
    		//add flight details
    		partFlightList.add(mapValuesToFlightModel(flight,bookingDetails.getVolumeUnit()));
    		
    		part.setShipmentFlightBookingList(partFlightList);
    		shipmentPartList.add(part);
    	});
		System.out.println("part list for import export ::"+shipmentPartList.toString());
		shipmentBookingDetails.setPartBookingList(shipmentPartList);
		return shipmentBookingDetails;
	}
	
	/**
	 * convert icms volume unit code to cosys unit code
	 * @param icmsVolumeUnitCode
	 * @return
	 */
	public String convertVolumeUnitCodeTOCosysVolumeUnitCode(String icmsVolumeUnitCode) {
		if(icmsVolumeUnitCode.equalsIgnoreCase(BookingConstant.ICMS_VOLUME_UNIT_B)) {
			return BookingConstant.VOLUME_UNIT_MC;
		}else if(icmsVolumeUnitCode.equalsIgnoreCase(BookingConstant.ICMS_VOLUME_UNIT_F)) {
			return BookingConstant.VOLUME_UNIT_CF;
		}else if(icmsVolumeUnitCode.equalsIgnoreCase(BookingConstant.ICMS_VOLUME_UNIT_I)) {
			return BookingConstant.VOLUME_UNIT_CI;
		}
		else if(icmsVolumeUnitCode.equalsIgnoreCase(BookingConstant.ICMS_VOLUME_UNIT_C)) {
			return BookingConstant.VOLUME_UNIT_CC;
		}
		else {
			LOGGER.info("Method End -> ShipmentBookingDetailBuilder -> convertVolumeUnitCodeTOCosysVolumeUnitCode -> else"+icmsVolumeUnitCode);
			return BookingConstant.VOLUME_UNIT_MC;
		}
	}
	
	/**
	 * Map values to flight model
	 * @param incomingFlight
	 * @return
	 */
	ShipmentFlightBookingDetails mapValuesToFlightModel(BookingFlightDetails incomingFlight,String volumeUnitCode) {
		ShipmentFlightBookingDetails flight = new ShipmentFlightBookingDetails();
		flight.setFlightKey(incomingFlight.getCarrierCode() + incomingFlight.getFlightNumber());
		flight.setCarrierCode(incomingFlight.getCarrierCode());
		flight.setFlightNumber(incomingFlight.getFlightNumber());
		flight.setBookingStatusCode(incomingFlight.getFlightBookingStatus());
		flight.setBookingPieces(incomingFlight.getPieces());
		flight.setBookingWeight(incomingFlight.getWeight());
		flight.setFlightBoardPoint(incomingFlight.getSegmentOrigin());
		flight.setFlightOffPoint(incomingFlight.getSegmentDestination());
		flight.setFlightOriginDate(commonUtil.convertStringToLocalDate(incomingFlight.getFlightDate(), ValidationConstant.XML_DATE_FORMAT));
		if(incomingFlight.getCosysSegmentArrivalDate() != null && !incomingFlight.getCosysSegmentArrivalDate().isEmpty()) {
			flight.setArrivalTime(commonUtil.convertStringToLocalTime(incomingFlight.getCosysSegmentArrivalDate(), ValidationConstant.XML_DATETIME_FORMAT));
		}
		if(incomingFlight.getCosysSegmentDepartureDate() != null && !incomingFlight.getCosysSegmentDepartureDate().isEmpty()) {
			flight.setDepartureTime(commonUtil.convertStringToLocalTime(incomingFlight.getCosysSegmentDepartureDate(), ValidationConstant.XML_DATETIME_FORMAT));
		}
		flight.setVolume(incomingFlight.getVolume());
		flight.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(volumeUnitCode));
		flight.setCreatedBy(BookingConstant.CREATEDBY);
		flight.setRemarks(incomingFlight.getRemarks());
		flight.setFlightId(incomingFlight.getFlightId());
		return flight;				
	}
	
	/**
     * map carrier flight details to part 
     * @param shipmentPartList
     * @param carrierFlights
     */
	public ShipmentBookingDetails mapCarrierFlightsToPart(ShipmentBookingDetails shipmentBookingDetails,List<BookingFlightDetails> carrierFlights) {
		List<ShipmentFlightPartBookingDetails> shipmentPartList = shipmentBookingDetails.getPartBookingList();
		ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
		int partCount = 0;
		for (BookingFlightDetails carrier : carrierFlights) {
			if(partCount>0) {
				part.setPartPieces(part.getPartPieces() + carrier.getPieces());
				part.setPartWeight(part.getPartWeight() + carrier.getWeight());
			}else {
				part.setPartPieces(carrier.getPieces());
				part.setPartWeight(carrier.getWeight());
			}
			
			part.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(shipmentBookingDetails.getVolumeUnitCode()));
			partCount++;
		}

		shipmentBookingDetails.setAllocatedPiece(shipmentBookingDetails.getAllocatedPiece() + part.getPartPieces());
		shipmentBookingDetails.setAllocatedWeight(shipmentBookingDetails.getAllocatedWeight() + part.getPartWeight());
		if(shipmentBookingDetails.getAllocatedPiece() < shipmentBookingDetails.getPieces()) {
			part.setPartPieces((shipmentBookingDetails.getPieces() - shipmentBookingDetails.getAllocatedPiece()) + part.getPartPieces());
		}
		if(shipmentBookingDetails.getAllocatedWeight() < shipmentBookingDetails.getGrossWeight()) {
			part.setPartWeight((shipmentBookingDetails.getGrossWeight() - shipmentBookingDetails.getAllocatedWeight()) + part.getPartWeight());
		}
		if(shipmentBookingDetails.getAllocatedVolume() < shipmentBookingDetails.getVolumeWeight()) {
			double volume =shipmentBookingDetails.getVolumeWeight() - shipmentBookingDetails.getAllocatedVolume();
			part.setVolume(volume);
		}
		if(part.getVolume()<0.01) {
			part.setVolume(0.01);
		}
		shipmentPartList.add(part);
		shipmentBookingDetails.setPartBookingList(shipmentPartList);
		return shipmentBookingDetails;
	}
	
	/**
	 * map incoming flights to part
	 * @param shipmentPartList
	 * @param incomingFlight
	 * @param outgoingFlight
	 */
	public void mapInFlightsToPartDetailsForTransshipment(List<ShipmentFlightPartBookingDetails> shipmentPartList,
			BookingFlightDetails incomingFlight, BookingFlightDetails outgoingFlight,ShipmentBookingDetails shipmentBookingDetails) {
		ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
		part.setPartPieces(incomingFlight.getBalancePiece());
		part.setPartWeight(incomingFlight.getBalanceWeight());
		double volume = calculatePartVolume(incomingFlight.getVolume(), incomingFlight.getPieces(), part.getPartPieces());
		part.setVolume(volume);
		part.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(shipmentBookingDetails.getVolumeUnitCode()));
		shipmentBookingDetails.setAllocatedPiece(shipmentBookingDetails.getAllocatedPiece() + incomingFlight.getPieces());
		shipmentBookingDetails.setAllocatedWeight(shipmentBookingDetails.getAllocatedWeight() + incomingFlight.getWeight());
		shipmentBookingDetails.setAllocatedVolume(shipmentBookingDetails.getAllocatedVolume() + incomingFlight.getVolume());
		
		List<ShipmentFlightBookingDetails> partFlightList = new ArrayList<>();
		//add incoming flight details
		partFlightList.add(mapValuesToFlightModel(incomingFlight,shipmentBookingDetails.getVolumeUnitCode()));
		partFlightList.add(mapValuesToFlightModel(outgoingFlight,shipmentBookingDetails.getVolumeUnitCode()));
		
		part.setShipmentFlightBookingList(partFlightList);
		//set balance volume for outgoing flight
		outgoingFlight.setBalanceVolume(shipmentBookingDetails.getVolumeWeight() - volume);
		shipmentPartList.add(part);
		
		
	}
	
	/**
	 * Map incoming flight details to Part
	 * @param shipmentPartList
	 * @param balanceInList
	 */
	void mapInFlightDetailsToPart(List<ShipmentFlightPartBookingDetails> shipmentPartList,
			BookingFlightDetails flight,ShipmentBookingDetails shipmentBookingDetails) {
			ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
			part.setPartPieces(flight.getBalancePiece());
			part.setPartWeight(flight.getBalanceWeight());
			shipmentBookingDetails.setAllocatedPiece(shipmentBookingDetails.getAllocatedPiece() + part.getPartPieces());
			shipmentBookingDetails.setAllocatedWeight(shipmentBookingDetails.getAllocatedWeight() + part.getPartWeight());
			shipmentBookingDetails.setAllocatedVolume(shipmentBookingDetails.getAllocatedVolume() + part.getVolume());
			double volume = calculatePartVolume(flight.getVolume(), flight.getPieces(), part.getPartPieces());
			part.setVolume(volume);
			part.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(shipmentBookingDetails.getVolumeUnitCode()));
			
			List<ShipmentFlightBookingDetails> partFlightList = new ArrayList<>();
			//add incoming flight details
			partFlightList.add(mapValuesToFlightModel(flight,shipmentBookingDetails.getVolumeUnitCode()));
			
			part.setShipmentFlightBookingList(partFlightList);
			shipmentPartList.add(part);
	}
	/**
	 * Map balance piece and weight of outgoing flight as part
	 * @param shipmentPartList
	 * @param inFlight
	 * @param outFlight
	 */
	void mapBalancePeiceOutFlightDetailsToPartForTransshipment(List<ShipmentFlightPartBookingDetails> shipmentPartList, BookingFlightDetails inFlight,
			BookingFlightDetails outFlight,ShipmentBookingDetails shipmentBookingDetails) {
		ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
		part.setPartPieces(outFlight.getBalancePiece());
		part.setPartWeight(outFlight.getBalanceWeight());
		double volume = calculatePartVolume(outFlight.getVolume(), outFlight.getPieces(), part.getPartPieces());
		part.setVolume(volume);
		part.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(shipmentBookingDetails.getVolumeUnitCode()));
		shipmentBookingDetails.setAllocatedPiece(shipmentBookingDetails.getAllocatedPiece() + part.getPartPieces());
		shipmentBookingDetails.setAllocatedWeight(shipmentBookingDetails.getAllocatedWeight() + part.getPartWeight());
		shipmentBookingDetails.setAllocatedVolume(shipmentBookingDetails.getAllocatedVolume() + part.getVolume());
		
		List<ShipmentFlightBookingDetails> partFlightList = new ArrayList<>();
		//add incoming flight details
		partFlightList.add(mapValuesToFlightModel(inFlight,shipmentBookingDetails.getVolumeUnitCode()));
		partFlightList.add(mapValuesToFlightModel(outFlight,shipmentBookingDetails.getVolumeUnitCode()));
		
		part.setShipmentFlightBookingList(partFlightList);
		shipmentPartList.add(part);
	}
	
	/**
	 * Map outgoing flight details as part
	 * @param shipmentPartList
	 * @param incomingFlight
	 * @param outgoingFlight
	 */
	void mapOutgoingFlightsToPartForTransshipment(List<ShipmentFlightPartBookingDetails> shipmentPartList,
			BookingFlightDetails incomingFlight, BookingFlightDetails outgoingFlight,ShipmentBookingDetails shipmentBookingDetails) {
		
		ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
		part.setPartPieces(outgoingFlight.getBalancePiece());
		part.setPartWeight(outgoingFlight.getBalanceWeight());
		double volume = calculatePartVolume(outgoingFlight.getVolume(), outgoingFlight.getPieces(), part.getPartPieces());
		part.setVolume(volume);
		part.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(shipmentBookingDetails.getVolumeUnitCode()));
		shipmentBookingDetails.setAllocatedPiece(shipmentBookingDetails.getAllocatedPiece() + part.getPartPieces());
		shipmentBookingDetails.setAllocatedWeight(shipmentBookingDetails.getAllocatedWeight() + part.getPartWeight());
		shipmentBookingDetails.setAllocatedVolume(shipmentBookingDetails.getAllocatedVolume() + part.getVolume());
		
		List<ShipmentFlightBookingDetails> partFlightList = new ArrayList<>();
		//add incoming flight details
		partFlightList.add(mapValuesToFlightModel(incomingFlight,shipmentBookingDetails.getVolumeUnitCode()));
		partFlightList.add(mapValuesToFlightModel(outgoingFlight,shipmentBookingDetails.getVolumeUnitCode()));
		
		part.setShipmentFlightBookingList(partFlightList);
		//set balance volume to incoming flight
		incomingFlight.setBalanceVolume(shipmentBookingDetails.getVolumeWeight() - volume);
		shipmentPartList.add(part);
	}
	
	/**
	 * Map shipment Booking Details
	 * @param bookingDetails
	 * @param shipmentBookingDetails
	 * @return
	 */
	ShipmentBookingDetails mapShipmentDetailsToShipmentBookingDetails(BookingDetails bookingDetails, ShipmentBookingDetails shipmentBookingDetails) {
		shipmentBookingDetails.setShipmentDate(bookingDetails.getShipmentDate());
		shipmentBookingDetails.setShipmentNumber((String.valueOf(bookingDetails.getShipmentIdentifierDetails().getShipmentPrefix()) + String.valueOf(bookingDetails.getShipmentIdentifierDetails().getMasterDocumentNumber())));
		shipmentBookingDetails.setOrigin(bookingDetails.getShipmentDetails().getShipmentOrigin());
		shipmentBookingDetails.setPieces(bookingDetails.getShipmentDetails().getTotalNumberOfPieces());
		shipmentBookingDetails.setGrossWeight(bookingDetails.getShipmentDetails().getTotalWeight());
		shipmentBookingDetails.setShipmentRoute(bookingDetails.getShipmentDetails().getShipmentOrigin()+bookingDetails.getShipmentDetails().getShipmentDestination());
		
		String shipmentDescription = "";
		if(!CollectionUtils.isEmpty(bookingDetails.getBookingCommodityDetails())) {
			shipmentDescription = bookingDetails.getBookingCommodityDetails().get(0).getShipmentDescription();
		}
		shipmentBookingDetails.setNatureOfGoodsDescription(shipmentDescription);
		shipmentBookingDetails.setVolumeWeight(bookingDetails.getShipmentDetails().getTotalVolume());
		shipmentBookingDetails.setWeightUnitCode(bookingDetails.getWeightUnit());
		shipmentBookingDetails.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(bookingDetails.getVolumeUnit()));
		shipmentBookingDetails.setDestination(bookingDetails.getShipmentDetails().getShipmentDestination());
		shipmentBookingDetails.setUbrNumber(bookingDetails.getUbrNumber());
		shipmentBookingDetails.setServiceFlag(BookingConstant.ZERO);
		if(bookingDetails.getServiceCargoClass()!=null || bookingDetails.getServiceCode()!=null ) {
			if(bookingDetails.getServiceCargoClass().equals("T") || bookingDetails.getServiceCargoClass().equals("F")) {
				shipmentBookingDetails.setServiceFlag(BookingConstant.ONE);
			}	
		}
		shipmentBookingDetails.setSccCodes(bookingDetails.getSccCodes());
		shipmentBookingDetails.setCreatedBy(BookingConstant.CREATEDBY);
		if(bookingDetails.getBookingRemarks() != null && bookingDetails.getBookingRemarks().length()>1000) {
			throw new MessageProcessingException("Booking remark shouldn't be greater than 1000 characters");
		}else {
			shipmentBookingDetails.setBookingRemarks(bookingDetails.getBookingRemarks());
		}
		if(bookingDetails.getHandlingInformation() != null && bookingDetails.getHandlingInformation().length()>1000) {
			throw new MessageProcessingException("Handling information remark shouldn't be greater than 1000 characters");
		}else {
			shipmentBookingDetails.setHandlingInformationRemark(bookingDetails.getHandlingInformation());
		}
		shipmentBookingDetails.setAllocatedPiece(bookingDetails.getWorkedOnPiece());
		shipmentBookingDetails.setAllocatedWeight(bookingDetails.getWorkedOnWeight());
		mapDimensionDetails(bookingDetails, shipmentBookingDetails);
		return shipmentBookingDetails;
	}

	/**
	 * @param bookingDetails
	 * @param shipmentBookingDetails
	 */
	private void mapDimensionDetails(BookingDetails bookingDetails, ShipmentBookingDetails shipmentBookingDetails) {
		List<ShipmentBookingDimensions> dimensionList = new ArrayList<>();
		for(BookingCommodityDetails commodity : bookingDetails.getBookingCommodityDetails()) {
			List<DimensionDetaills> bookingDimensionList = commodity.getDimensionDetaills();
			if(bookingDimensionList != null && !bookingDimensionList.isEmpty()) {
				for (DimensionDetaills dim : bookingDimensionList) {
					ShipmentBookingDimensions dimension = new ShipmentBookingDimensions();
					dimension.setHeight((int)dim.getHeightPerpiece());
					dimension.setWeight(dim.getWeight());
					dimension.setVolume((float)dim.getVolume());
					dimension.setPieces(dim.getNumberOfPieces());
					dimension.setUnitCode(bookingDetails.getDimensionUnit());
					dimension.setWidth((int)dim.getWidthPerPiece());
					dimension.setLength((int)dim.getLengthPerPiece());
					if(bookingDetails.getDimensionUnit()!= null)
						if(bookingDetails.getDimensionUnit().equalsIgnoreCase(BookingConstant.ICMS_DIMENSION_C)) {
							dimension.setUnitCode(BookingConstant.DIMENSION_CMT);
						}else if(bookingDetails.getDimensionUnit().equalsIgnoreCase(BookingConstant.ICMS_DIMENSION_I)) {
							dimension.setUnitCode(BookingConstant.DIMENSION_INH);
						}
					dimensionList.add(dimension);
				}
			}
		}
		shipmentBookingDetails.setShipmentBookingDimensionList(dimensionList);
	}
	
	/**
	 * @param shipmentBookingDetails
	 */
	void checkAndCreatePartBasedOnRemainingPiece(ShipmentBookingDetails shipmentBookingDetails) {
		if(shipmentBookingDetails.getPieces()>shipmentBookingDetails.getAllocatedPiece()) {
    		
    		ShipmentFlightPartBookingDetails part = new ShipmentFlightPartBookingDetails();
    		part.setPartPieces(shipmentBookingDetails.getPieces() - shipmentBookingDetails.getAllocatedPiece());
			part.setPartWeight(shipmentBookingDetails.getGrossWeight() - shipmentBookingDetails.getAllocatedWeight());
			part.setVolumeUnitCode(convertVolumeUnitCodeTOCosysVolumeUnitCode(shipmentBookingDetails.getVolumeUnitCode()));
			if(shipmentBookingDetails.getAllocatedVolume() < shipmentBookingDetails.getVolumeWeight()) {
				part.setVolume(shipmentBookingDetails.getVolumeWeight() - shipmentBookingDetails.getAllocatedVolume());
			}
			List<ShipmentFlightPartBookingDetails> shipmentPart = shipmentBookingDetails.getPartBookingList();
			shipmentPart.add(part);
			shipmentBookingDetails.setPartBookingList(shipmentPart);
    	} else if (shipmentBookingDetails.getPieces()<shipmentBookingDetails.getAllocatedPiece()) {
    		
    	}
	}
	
	/**
	 * calculate part volume based outgoing piece and volume
	 * @param totalVolume
	 * @param totalPieces
	 * @param partPieces
	 * @return
	 */
	 double calculatePartVolume(double totalVolume, double totalPieces, double partPieces) {
		 LOGGER.info("Method start calculateVolume->totalVolume ->"+totalVolume+" totalPieces -> "+totalPieces+" partPieces -> "+partPieces);
		double result = 0.01;
		if(totalVolume!=0.0 && totalPieces!=0.0 && partPieces!=0.0) {
			result=(totalVolume/totalPieces)*partPieces;
			if(result<0.01) {
				result = 0.01;
			}
		}
		return result;
	}
}
