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
package com.ngen.cosys.icms.service.builder;


import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.bookingicms.*;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingFlightDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingFlightPairDetails;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.DimensionDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FlightPairDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FlightPairType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ShipmentDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ShipmentIdentifierDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ValidateBookingRequest;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ValidateBookingRequestData;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ValidateBookingResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;
import com.ngen.cosys.icms.schema.flightbooking.BookingCommodityDetails;
import com.ngen.cosys.icms.schema.flightbooking.DimensionDetaills;
import com.ngen.cosys.icms.util.CommonUtil;

import reactor.util.CollectionUtils;


/**
 *This class convert Json object to Booking shipment model 
 *and map booking shipment model to validate booking request model and response model
 *
 */
@Component
public class ValidateBookingBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidateBookingBuilder.class);
	@Autowired
	FlightBookingDao flightBookingDao;
	@Autowired
	private CommonUtil commonUtil;
	
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
	public static final String XML_DATE_FORMAT = "dd-MMM-yyyy";
	/**
	 * create validate booking request model from json string
	 * @param bookingDetails
	 * @return
	 */
	public ValidateBookingRequest createValidateRequest(BookingShipmentDetails bookingDetails) {
		LOGGER.info("Method start createValidateRequest -> bookingDetails ->"+bookingDetails.toString());
		System.out.println(bookingDetails.toString());
		ValidateBookingRequest validateBookingRequestType= new ValidateBookingRequest();
		
		ValidateBookingRequestData validateBookingRequestData=new ValidateBookingRequestData(); 
		
		BookingType bookingType=new BookingType();
		bookingType.setWeightUnit(bookingDetails.getWeightUnit());
		bookingType.setVolumeUnit(bookingDetails.getVolumeUnit());
		bookingType.setDimensionUnit(bookingDetails.getDimensionUnit());
		if(!StringUtils.isEmpty(bookingDetails.getSvcIndicator())) {
			bookingType.setServiceCargoClass(bookingDetails.getSvcIndicator());
		}
		//scccodes
		getSccCodeValues(bookingDetails, bookingType);
		
		setShipmentIdentifierDetails(bookingDetails, bookingType);
		
		setShipmentDetails(bookingDetails, bookingType);
		
		setShipmentCommodityDetails(bookingDetails, bookingType);
		
		setFlightDetails(bookingDetails, bookingType);
		
		setFlightPairDetails(bookingDetails, bookingType);
		
		System.out.println("After mapping values to validate request::"+bookingType.toString());
		validateBookingRequestData.setBookingDetails(bookingType);

		validateBookingRequestData.setRequestID(String.valueOf(System.nanoTime()));
		validateBookingRequestType.setRequestData(validateBookingRequestData);
	
		MessageHeaderType messageHeader = new MessageHeaderType();
		messageHeader.setMessageType(bookingDetails.getMessageType());
		messageHeader.setSourceSystem(bookingDetails.getSourceSystem());
		messageHeader.setUserId(bookingDetails.getUserId());
		validateBookingRequestType.setMessageHeader(messageHeader);
		LOGGER.info("Method End createValidateRequest -> After mapping"+validateBookingRequestType.toString());
		return  validateBookingRequestType;
	}

	/**
	 * @param bookingDetails
	 * @param bookingType
	 */
	private void getSccCodeValues(BookingShipmentDetails bookingDetails, BookingType bookingType) {
		Map<String,String> map = new HashMap<>();
		List<String> sccCodeList = new ArrayList<>();
		map.put("shipmentNumber", bookingDetails.getShipmentPrefix() + bookingDetails.getMasterDocumentNumber());
		map.put("shipmentDate", bookingDetails.getShipmentDate());
		long shipmentId=flightBookingDao.getShipmentId(map);
		if(shipmentId > 0) {
			sccCodeList = flightBookingDao.fetchSccCodeFromShc(shipmentId);	
		}else {
			sccCodeList = bookingDetails.getSccCodeList();
		}
		String sccCode = "";
		if(!CollectionUtils.isEmpty(sccCodeList)) {
			for (String scc : sccCodeList) {
				if(!sccCode.isEmpty()) {
					sccCode = sccCode +","+ scc ;
				}else {
					sccCode = scc;
				}
			}
			bookingType.setSccCodes(sccCode);
		}
	}
	
	/**
	 * set flight pair details
	 * @param bookingDetails
	 * @param bookingType
	 */
	private void setFlightPairDetails(BookingShipmentDetails bookingDetails, BookingType bookingType) {
		BookingFlightPairDetails flightPairType = new BookingFlightPairDetails();
		List<FlightPairType> pairList = new ArrayList<>();
		if(bookingDetails.getFlightPair() != null) {
			if(bookingDetails.getFlightPair().getFlightPairDetails() != null && !CollectionUtils.isEmpty(bookingDetails.getFlightPair().getFlightPairDetails())) {
				bookingDetails.getFlightPair().getFlightPairDetails().forEach(pair -> {
					FlightPairType pairType = new FlightPairType();
					List<FlightPairDetailType> flightPairDetailList = new ArrayList<>();
					pair.getFlightDetails().forEach(pairDetail -> {
						FlightPairDetailType pairDetailType = new FlightPairDetailType();
						pairDetailType.setFlightCarrierCode(pairDetail.getCarrierCode());
						pairDetailType.setFlightNumber(pairDetail.getFlightNumber());
						if(pairDetail.getFlightDate() != null) {
							pairDetailType.setFlightDate(commonUtil.convertDateString(pairDetail.getFlightDate(), DB_DATE_FORMAT, XML_DATE_FORMAT));
						}
						pairDetailType.setSegmentOrigin(pairDetail.getSegmentOrigin());
						pairDetailType.setSegmentDestination(pairDetail.getSegmentDestination());
						flightPairDetailList.add(pairDetailType);
					});
					pairType.setFlightDetails(flightPairDetailList);
					pairList.add(pairType);
				});
			}
		}
		if(!CollectionUtils.isEmpty(pairList)) {
			flightPairType.setFlightPair(pairList);
			bookingType.setBookingFlightPairDetails(flightPairType);
		}
	}
	/**
	 * set flight details
	 * @param bookingDetails
	 * @param bookingType
	 */
	private void setFlightDetails(BookingShipmentDetails bookingDetails, BookingType bookingType) {
		List<BookingFlightDetailType> flightDetailList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(bookingDetails.getBookingFlightDetails())) {
			bookingDetails.getBookingFlightDetails().forEach(flight -> {
				BookingFlightDetailType flightType = new BookingFlightDetailType();
				flightType.setSegmentOrigin(flight.getSegmentOrigin());
				flightType.setSegmentDestination(flight.getSegmentDestination());
				if (null != flight.getSegmentDepartureDate()) {
					flightType.setSegmentDepartureDate(flight.getSegmentDepartureDate());
				}
				flightType.setCarrierCode(flight.getCarrierCode());
				flightType.setFlightNumber(flight.getFlightNumber());
				flightType.setPieces(flight.getPiece());
				flightType.setWeight(BigDecimal.valueOf(flight.getWeight()).setScale(3, RoundingMode.HALF_UP));
				flightType.setVolume(BigDecimal.valueOf(flight.getVolume()).setScale(3, RoundingMode.HALF_UP));
				flightType.setFlightBookingStatus(flight.getBookingStatus());
				if (null != flight.getFlightDate()) {
					flightType.setFlightDate(commonUtil.convertDateString(flight.getFlightDate(), DB_DATE_FORMAT, XML_DATE_FORMAT));
				}else {
					SimpleDateFormat formatter = new SimpleDateFormat(XML_DATE_FORMAT);  
					Date currentDate = new Date();  					
					flightType.setFlightDate(formatter.format(currentDate));
				}
				flightDetailList.add(flightType);
			});
		}
		bookingType.setBookingFlightDetails(flightDetailList);
	}
	/**
	 * set shipment commodity details
	 * @param bookingDetails
	 * @param bookingType
	 */
	private void setShipmentCommodityDetails(BookingShipmentDetails bookingDetails, BookingType bookingType) {
		List<BookingDetailType> commodityList = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		map.put("prefix", bookingDetails.getShipmentPrefix());
		map.put("masterNo", bookingDetails.getMasterDocumentNumber());
		List<BookingCommodityDetails> intermidiateCommodity = flightBookingDao.fetchIntermidiateCommodityDetails(map);
		
		if(!CollectionUtils.isEmpty(intermidiateCommodity)) {
			int intermidiateCommodityListSize = intermidiateCommodity.size();
			for(BookingCommodityDetails commodity : intermidiateCommodity) {
				BookingDetailType commodityType = new BookingDetailType();
				commodityType.setCommodityCode(commodity.getCommodityCode());
				commodityType.setDisplayWeight(BigDecimal.valueOf(commodity.getDisplayWeight()).setScale(3,RoundingMode.HALF_UP));
				commodityType.setShipmentDescription(bookingDetails.getShipmentDescription());
				commodityType.setSccCode(commodity.getSccCode());
				List<DimensionDetaills> commodityDimensionList = flightBookingDao.fetchIntermidiateCommodityDimensionDetails(commodity.getBookingCommodityDetailsId());
				if(!CollectionUtils.isEmpty(commodityDimensionList)) {
					List<DimensionDetailType> dimensionList = new ArrayList<>();
					commodityDimensionList.forEach(dimension -> {
						DimensionDetailType dimensionType = new DimensionDetailType();
						dimensionType.setLengthPerPiece(BigDecimal.valueOf(dimension.getLengthPerPiece()));
						dimensionType.setHeightPerpiece(BigDecimal.valueOf(dimension.getHeightPerpiece()));
						dimensionType.setWidthPerPiece(BigDecimal.valueOf(dimension.getWidthPerPiece()));
						dimensionType.setWeight(BigDecimal.valueOf(dimension.getWeight()).setScale(3, RoundingMode.HALF_UP));
						dimensionType.setNumberOfPieces(BigInteger.valueOf(dimension.getNumberOfPieces()));
						dimensionType.setVolume(BigDecimal.valueOf(dimension.getVolume()));
						dimensionType.setDisplayHeightPerpiece(BigDecimal.valueOf(dimension.getDisplayHeightPerpiece()));
						dimensionType.setDisplayLengthPerPiece(BigDecimal.valueOf(dimension.getDisplayLengthPerPiece()));
						dimensionType.setDisplayWidthPerPiece(BigDecimal.valueOf(dimension.getDisplayWidthPerPiece()));
						dimensionType.setDisplayWeight(BigDecimal.valueOf(dimension.getDisplayWeight()));
						dimensionList.add(dimensionType);
					});
					commodityType.setDimensionDetaills(dimensionList);
					commodityList.add(commodityType);
					if(intermidiateCommodityListSize>1) {
						commodityType.setPieces(BigInteger.valueOf(commodity.getPieces()));
						commodityType.setWeight(BigDecimal.valueOf(commodity.getWeight()).setScale(3, RoundingMode.HALF_UP));
						commodityType.setVolume(BigDecimal.valueOf(commodity.getVolume()).setScale(3,RoundingMode.HALF_UP));
					}else {
						commodityType.setPieces(bookingDetails.getPieces());
						commodityType.setWeight(BigDecimal.valueOf(bookingDetails.getWeight()).setScale(3, RoundingMode.HALF_UP));
						commodityType.setVolume(BigDecimal.valueOf(bookingDetails.getTotalVolume()).setScale(3,RoundingMode.HALF_UP));
					}
				}
				
			}
		}else {
			BookingDetailType commodityType = new BookingDetailType();
			commodityType.setCommodityCode(bookingDetails.getCommodityCode());
			commodityType.setPieces(bookingDetails.getPieces());
			commodityType.setWeight(BigDecimal.valueOf(bookingDetails.getWeight()).setScale(3, RoundingMode.HALF_UP));
			commodityType.setShipmentDescription(bookingDetails.getShipmentDescription());
			commodityType.setVolume(BigDecimal.valueOf(bookingDetails.getTotalVolume()).setScale(3,RoundingMode.HALF_UP));
			List<DimensionDetailType> dimensionList = new ArrayList<>();
			bookingDetails.getDimensionDetails().forEach(dimension -> {
				DimensionDetailType dimensionType = new DimensionDetailType();
				dimensionType.setLengthPerPiece(BigDecimal.valueOf(dimension.getLengthPerPiece()));
				dimensionType.setHeightPerpiece(BigDecimal.valueOf(dimension.getHeightPerpiece()));
				dimensionType.setWidthPerPiece(BigDecimal.valueOf(dimension.getWidthPerpiece()));
				dimensionType.setWeight(BigDecimal.valueOf(dimension.getWeight()).setScale(3, RoundingMode.HALF_UP));
				dimensionType.setNumberOfPieces(BigInteger.valueOf(dimension.getNumberOfPieces()));
				dimensionList.add(dimensionType);
			});
			commodityType.setDimensionDetaills(dimensionList);
			commodityList.add(commodityType);
		}
		
		
	
		bookingType.setBookingCommodityDetails(commodityList);
	}
	/**
	 * set shipment details from booking shipment
	 * @param bookingDetails
	 * @param bookingType
	 */
	private void setShipmentDetails(BookingShipmentDetails bookingDetails, BookingType bookingType) {
		ShipmentDetailType shipmentDetail = new ShipmentDetailType();
		shipmentDetail.setShipmentOrigin(bookingDetails.getShipmentOrigin());
		shipmentDetail.setShipmentDestination(bookingDetails.getShipmentDestination());
		shipmentDetail.setAgentCode(bookingDetails.getAgentCode());
		shipmentDetail.setCustomerCode(bookingDetails.getCustomerCode());
		shipmentDetail.setBookingSource(bookingDetails.getBookingSource());
		shipmentDetail.setCurrency(bookingDetails.getCurrency());
		shipmentDetail.setBookingSource(bookingDetails.getBookingSource());
		shipmentDetail.setTotalNumberOfPieces(bookingDetails.getTotalNumberOfPieces());
		shipmentDetail.setTotalWeight(BigDecimal.valueOf(bookingDetails.getTotalWeight()).setScale(3, RoundingMode.HALF_UP));
		shipmentDetail.setTotalVolume(BigDecimal.valueOf(bookingDetails.getTotalVolume()).setScale(3, RoundingMode.HALF_UP));
		shipmentDetail.setLastUpdateTime(bookingDetails.getLastUpdateTime());
		bookingType.setShipmentDetails(shipmentDetail);
	}
	
	/**
	 * map shipment identifier details from booking details
	 * @param bookingDetails
	 * @param bookingType
	 */
	private void setShipmentIdentifierDetails(BookingShipmentDetails bookingDetails, BookingType bookingType) {
		ShipmentIdentifierDetailType shipmentIdentifier = new ShipmentIdentifierDetailType();
		shipmentIdentifier.setShipmentPrefix(bookingDetails.getShipmentPrefix());
		shipmentIdentifier.setMasterDocumentNumber(bookingDetails.getMasterDocumentNumber());
		bookingType.setShipmentIdentifierDetails(shipmentIdentifier);
	}
	
	/**
	 * create validate response from xml string
	 * @param responseXml
	 * @return
	 * @throws JAXBException
	 */
	public ValidateBookingResponseType createValidateResposne(String responseXml) throws JAXBException {
		// Marshal XML to JaxbObject
		// Map JaxbObject to JSON object
		LOGGER.info("ValidateBookingBuilder -> createValidateResposne method start -> response xml :"+responseXml);
		System.out.println("ValidateBookingBuilder -> createValidateResposne method start -> response xml :"+responseXml);
		responseXml = responseXml.replaceAll("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body>","");
		responseXml = responseXml.replaceAll("</soap:Body></soap:Envelope>","");		
		StringReader strReader = new StringReader(responseXml);
		JAXBContext jaxbContext = JAXBContext.newInstance(ValidateBookingResponseType.class);  
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();	
		ValidateBookingResponseType validateBookingResponse = (ValidateBookingResponseType) unmarshal.unmarshal(strReader); 
		System.out.println("ValidateBookingBuilder -> createValidateResposne method End -> validateBookingResponse :"+validateBookingResponse.toString());
		LOGGER.info("ValidateBookingBuilder -> createValidateResposne method End -> validateBookingResponse :"+validateBookingResponse.toString());
		return validateBookingResponse;
		
	}

	
	
	
	

}
