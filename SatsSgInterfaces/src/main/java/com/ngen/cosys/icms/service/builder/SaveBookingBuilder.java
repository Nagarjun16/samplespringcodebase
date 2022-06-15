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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.util.StringUtils;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.bookingicms.BookingShipmentDetails;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingFlightDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingFlightPairDetails;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.BookingType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.DimensionDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FlightPairDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.FlightPairType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingDetailsRequest;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingRequestData;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ShipmentDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ShipmentIdentifierDetailType;
import com.ngen.cosys.icms.schema.bookingapi.business.shared.defaults.types.standard.MessageHeaderType;
import com.ngen.cosys.icms.schema.flightbooking.BookingCommodityDetails;
import com.ngen.cosys.icms.schema.flightbooking.DimensionDetaills;
import com.ngen.cosys.icms.util.CommonUtil;

import reactor.util.CollectionUtils;

/**
 * This class convert Json object to Booking shipment model Map booking shipment
 * model to save booking request model and response model
 *
 */
@Component
public class SaveBookingBuilder {
	//private static final Logger LOGGER = LoggerFactory.getLogger(SaveBookingBuilder.class);
	@Autowired
	private FlightBookingDao flightBookingDao;
	@Autowired
	private CommonUtil commonUtil;
	/**
	 * create save request model from json string
	 * 
	 * @param bookingDetails
	 * @return
	 */
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
	public static final String XML_DATE_FORMAT = "dd-MMM-yyyy";
	public SaveBookingDetailsRequest createSaveRequest(BookingShipmentDetails bookingDetails) throws ParseException {
		//LOGGER.info("Method start createSaveRequest -> bookingDetails ->"+bookingDetails.toString());
		SaveBookingDetailsRequest saveBookingDetailsRequestType = new SaveBookingDetailsRequest();

		MessageHeaderType messageHeaderType = new MessageHeaderType();
		messageHeaderType.setMessageType(bookingDetails.getMessageType());
		messageHeaderType.setSourceSystem(bookingDetails.getSourceSystem());
		messageHeaderType.setUserId(bookingDetails.getUserId());

		saveBookingDetailsRequestType.setMessageHeader(messageHeaderType);

		SaveBookingRequestData saveBookingRequestData = new SaveBookingRequestData();
		saveBookingRequestData.setRequestID(String.valueOf(System.nanoTime()));
		
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
		
		setCommodityDetails(bookingDetails, bookingType);
		
		setFlightDetails(bookingDetails, bookingType);
		
		setFlightPairingDetails(bookingDetails, bookingType);
		
		saveBookingRequestData.setBookingDetails(bookingType);
		
		saveBookingDetailsRequestType.setRequestData(saveBookingRequestData);
		System.out.println("After mapping values to save request::"+saveBookingDetailsRequestType.toString());
		//LOGGER.info("Method start createSaveRequest -> After mapping values to save request ->"+saveBookingDetailsRequestType.toString());
		return saveBookingDetailsRequestType;
	}

	/**
	 * set flight pair details
	 * @param bookingDetails
	 * @param bookingType
	 */
	private void setFlightPairingDetails(BookingShipmentDetails bookingDetails, BookingType bookingType) {
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
		if(bookingDetails.getBookingFlightDetails() != null) {
			bookingDetails.getBookingFlightDetails().forEach(flight -> {
				BookingFlightDetailType flightType = new BookingFlightDetailType();
				flightType.setSegmentOrigin(flight.getSegmentOrigin());
				flightType.setSegmentDestination(flight.getSegmentDestination());
				flightType.setSegmentDepartureDate(flight.getSegmentDepartureDate());
				flightType.setCarrierCode(flight.getCarrierCode());
				flightType.setFlightNumber(flight.getFlightNumber());
				flightType.setPieces(flight.getPiece());
				flightType.setWeight(BigDecimal.valueOf(flight.getWeight()).setScale(3, RoundingMode.HALF_UP));
				flightType.setVolume(BigDecimal.valueOf(flight.getVolume()).setScale(3, RoundingMode.HALF_UP));
				flightType.setFlightBookingStatus(flight.getBookingStatus());
				if (null != flight.getFlightDate()) {
					flightType.setFlightDate(commonUtil.convertDateString(flight.getFlightDate(), DB_DATE_FORMAT, XML_DATE_FORMAT));
				}
				else {
					SimpleDateFormat formatter = new SimpleDateFormat(XML_DATE_FORMAT);  
					Date currentDate = new Date();  					
					flightType.setFlightDate(formatter.format(currentDate));
				}
				flightDetailList.add(flightType);
			});
			bookingType.setBookingFlightDetails(flightDetailList);
		}
	}
	/**
	 * set commodity details
	 * @param bookingDetails
	 * @param bookingType
	 */
	private void setCommodityDetails(BookingShipmentDetails bookingDetails, BookingType bookingType) {
		List<BookingDetailType> commodityList = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		map.put("prefix", bookingDetails.getShipmentPrefix());
		map.put("masterNo", bookingDetails.getMasterDocumentNumber());

		List<BookingCommodityDetails> intermidiateCommodity = flightBookingDao.fetchIntermidiateCommodityDetails(map);
		if(!CollectionUtils.isEmpty(intermidiateCommodity)) {
			
			for(BookingCommodityDetails commodity : intermidiateCommodity) {
				int intermidiateCommodityListSize = intermidiateCommodity.size();
				BookingDetailType commodityType = new BookingDetailType();
				commodityType.setCommodityCode(commodity.getCommodityCode());
				commodityType.setPieces(BigInteger.valueOf(commodity.getPieces()));
				commodityType.setWeight(BigDecimal.valueOf(commodity.getWeight()).setScale(3, RoundingMode.HALF_UP));
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
				}
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
		}else {
			BookingDetailType commodityType = new BookingDetailType();
			commodityType.setCommodityCode(bookingDetails.getCommodityCode());
			commodityType.setPieces(bookingDetails.getPieces());
			commodityType.setWeight(BigDecimal.valueOf(bookingDetails.getWeight()).setScale(3, RoundingMode.HALF_UP));
			commodityType.setVolume(BigDecimal.valueOf(bookingDetails.getTotalVolume()).setScale(3,RoundingMode.HALF_UP));
			commodityType.setShipmentDescription(bookingDetails.getShipmentDescription());
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
	 * set Shipment Details
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
	 * set shipment identifier details
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
	 * convert to save response model from xml string
	 * 
	 * @param responseXml
	 * @return
	 * @throws JAXBException
	 */
	public SaveBookingDetailsResponseType createSaveResposne(String responseXml) throws JAXBException {
		// Marshal XML to JaxbObject
		// Map JaxbObject to JSON object
		//LOGGER.info("SaveBookingBuilder -> createSaveResposne method start -> response xml :" + responseXml);
		StringReader strReader = new StringReader(responseXml);
		JAXBContext jaxbContext = JAXBContext.newInstance(SaveBookingDetailsResponseType.class);
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();
		SaveBookingDetailsResponseType saveBookingResponse = (SaveBookingDetailsResponseType) unmarshal
				.unmarshal(strReader);
		//LOGGER.info("saveBookingBuilder -> createSaveResposne method End -> saveBookingResponse :"
				//+ saveBookingResponse.toString());
		return saveBookingResponse;
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
			sccCodeList = flightBookingDao.fetchSccCodeFromBookingShc(map);
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

}
