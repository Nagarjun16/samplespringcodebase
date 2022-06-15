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
package com.ngen.cosys.icms.service;

import java.io.StringWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.icms.controller.OutboundIcmsController;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.ICMSResponseModel;
import com.ngen.cosys.icms.model.BookingInterface.ShipmentBookingRemark;
import com.ngen.cosys.icms.model.bookingicms.BookingFlightDetails;
import com.ngen.cosys.icms.model.bookingicms.BookingShipmentDetails;
import com.ngen.cosys.icms.model.bookingicms.ConstantValueDetails;
import com.ngen.cosys.icms.model.bookingicms.DimensionDetails;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingRequestType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.CancelBookingResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingDetailsRequest;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.SaveBookingDetailsResponseType;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ValidateBookingRequest;
import com.ngen.cosys.icms.schema.bookingapi.business.capacity.booking.types.standard.ValidateBookingResponseType;
import com.ngen.cosys.icms.service.builder.CancelBookingBuilder;
import com.ngen.cosys.icms.service.builder.SaveBookingBuilder;
import com.ngen.cosys.icms.service.builder.ValidateBookingBuilder;
import com.ngen.cosys.icms.service.messageLog.OutgoingMessageLogService;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.SoapServiceUtil;

/**
 * Service class to call validate,save,cancel booking api
 *
 */
@Component
public class FlightBookingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundIcmsController.class);

	@Autowired
	ValidateBookingBuilder validateBookingBuilder;

	@Autowired
	SaveBookingBuilder saveBookingBuilder;

	@Autowired
	CancelBookingBuilder cancelBookingBuilder;

	@Autowired
	SoapServiceUtil soapServiceUtil;

	@Autowired
	OutgoingMessageLogService messageLogService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	FlightBookingDao flightBookingDao;

	/**
	 * This method used to call validateBooking API in ICMS
	 * 
	 * @param bookingdetails
	 * @return
	 */
	public ValidateBookingResponseType validateBooking(BookingShipmentDetails bookingDetails) {
		LOGGER.info("Method Start FlightBookingService-> validateBookingDetails()-> bookingDetails :"
				+ bookingDetails.toString());
		ValidateBookingResponseType validateBookingResponse = new ValidateBookingResponseType();
		
		String requestXml = "";
		ICMSResponseModel icmsResponseModel = new ICMSResponseModel();
		try {
			icmsResponseModel = setICMSResponseModel(bookingDetails, BookingConstant.VALIDATE_MESSAGE_TYPE,
					BookingConstant.SUB_MESSAGE_TYPE_REQUEST);
			setConstantsValuesToSaveBookingDetails(bookingDetails);
			setFlightRemarksAndFlightSegmentDate(bookingDetails);
			bookingDetails.setVolumeUnit(convertCosysUnitCodeTOICMSVolumeUnitCode(bookingDetails.getVolumeUnit()));
			if (bookingDetails.getDimensionDetails() == null || bookingDetails.getDimensionDetails().isEmpty()) {
				List<DimensionDetails> dimensionList = new ArrayList<>();
				DimensionDetails dimension = new DimensionDetails();
				dimension.setHeightPerpiece(Integer.parseInt(bookingDetails.getDummyDimensionValue()));
				dimension.setLengthPerPiece(Integer.parseInt(bookingDetails.getDummyDimensionValue()));
				dimension.setWidthPerpiece(Integer.parseInt(bookingDetails.getDummyDimensionValue()));
				dimension.setNumberOfPieces((bookingDetails.getTotalNumberOfPieces().intValue()));
				dimension.setWeight(bookingDetails.getTotalWeight());
				dimensionList.add(dimension);
				bookingDetails.setDimensionDetails(dimensionList);
			}
			fullRoutingFlightBooking(bookingDetails);
			setBookingStatus(bookingDetails);
			ValidateBookingRequest validateBookingRequestType = validateBookingBuilder
					.createValidateRequest(bookingDetails);
			requestXml = validateAndMarshal(validateBookingRequestType);

			Map<String, String> responseMap = soapServiceUtil.callIcmsService(requestXml, "",
					BookingConstant.VALIDATE_REQUEST);
			String responseXml = responseMap.get("responseXml");
			icmsResponseModel.setSubMessageType(BookingConstant.SUB_MESSAGE_TYPE_RESPONSE);
			messageLogService.updateProcessStatusInMessageLog(responseXml, icmsResponseModel);
			if (responseMap.get("statusCode").equals("200")) {
				validateBookingResponse = validateBookingBuilder.createValidateResposne(responseXml.toString());
			}
			icmsResponseModel.setHttpStatus(HttpStatus.OK);
		} catch (JAXBException | UnsupportedOperationException e) {
			icmsResponseModel.setHttpStatus(HttpStatus.BAD_REQUEST);
			icmsResponseModel.setErrorMessage(e.getMessage());
			LOGGER.error("Method End FlightBookingService Internal-> validateBookingDetails()-> Exception", e);
		} catch (Exception e) {
			icmsResponseModel.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			icmsResponseModel.setErrorMessage(e.getMessage());
			LOGGER.error("Method End FlightBookingService Internal-Exception-> validateBookingDetails()-> Exception",
					e);
		} finally {
			try {

				icmsResponseModel.setSubMessageType(BookingConstant.SUB_MESSAGE_TYPE_REQUEST);
				messageLogService.updateProcessStatusOutMessageLog(requestXml, icmsResponseModel);
			} catch (Exception e) {
				icmsResponseModel.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				icmsResponseModel.setErrorMessage(e.getMessage());
				LOGGER.error("Method End FlightBookingService finally-> validateBookingDetails()-> Exception", e);
			}
		}

		LOGGER.info("Method End FlightBookingService-> validateBookingDetails()-> bookingDetails :"
				+ validateBookingResponse.toString());
		return validateBookingResponse;
	}

	/**
	 * This method used to call validateBooking API in ICMS
	 * 
	 * @param bookingdetails
	 * @return
	 */
	public SaveBookingDetailsResponseType saveBookingDetails(BookingShipmentDetails bookingDetails) {
		LOGGER.info("Method Start FlightBookingService-> saveBookingDetails()->" + bookingDetails);
		SaveBookingDetailsResponseType saveBookingResponse = null;
	
		ICMSResponseModel icmsResponseModel = new ICMSResponseModel();
		String requestXml = "";
		try {
			icmsResponseModel = setICMSResponseModel(bookingDetails, BookingConstant.SAVE_MESSAGE_TYPE,
					BookingConstant.SUB_MESSAGE_TYPE_REQUEST);
			setConstantsValuesToSaveBookingDetails(bookingDetails);
			bookingDetails.setVolumeUnit(convertCosysUnitCodeTOICMSVolumeUnitCode(bookingDetails.getVolumeUnit()));
			setFlightRemarksAndFlightSegmentDate(bookingDetails);
			List<DimensionDetails> dimensionList = new ArrayList<>();
			if (bookingDetails.getDimensionDetails() == null || bookingDetails.getDimensionDetails().isEmpty()) {
				DimensionDetails dimension = new DimensionDetails();
				dimension.setHeightPerpiece(Integer.parseInt(bookingDetails.getDummyDimensionValue()));
				dimension.setLengthPerPiece(Integer.parseInt(bookingDetails.getDummyDimensionValue()));
				dimension.setWidthPerpiece(Integer.parseInt(bookingDetails.getDummyDimensionValue()));
				dimension.setNumberOfPieces(bookingDetails.getTotalNumberOfPieces().intValue());
				dimension.setWeight(bookingDetails.getTotalWeight());
				dimensionList.add(dimension);
				bookingDetails.setDimensionDetails(dimensionList);
			}
			fullRoutingFlightBooking(bookingDetails);
			setBookingStatus(bookingDetails);
			SaveBookingDetailsRequest saveBookingDetailsRequestType = saveBookingBuilder
					.createSaveRequest(bookingDetails);
			requestXml = saveAndMarshal(saveBookingDetailsRequestType);
			System.out.println("OUTPUT");
			System.out.println(requestXml);
			icmsResponseModel.setCreatedBy(bookingDetails.getCreatedBy());
			Map<String, String> responseMap = soapServiceUtil.callIcmsService(requestXml, "",
					BookingConstant.SAVE_REQUEST);
			icmsResponseModel.setSubMessageType(BookingConstant.SUB_MESSAGE_TYPE_RESPONSE);
			messageLogService.updateProcessStatusInMessageLog(responseMap.get("responseXml"), icmsResponseModel);
			icmsResponseModel.setHttpStatus(HttpStatus.OK);
		} catch (JAXBException | UnsupportedOperationException e) {
			icmsResponseModel.setHttpStatus(HttpStatus.BAD_REQUEST);
			icmsResponseModel.setErrorMessage(e.getMessage());
			LOGGER.error("Method End FlightBookingService Internal -> saveBookingDetails()-> Exception", e);
		} catch (Exception e) {
			icmsResponseModel.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			icmsResponseModel.setErrorMessage(e.getMessage());
			LOGGER.error("Method End FlightBookingService Internal Exception-> saveBookingDetails()-> Exception", e);
		} finally {
			try {
				icmsResponseModel.setSubMessageType(BookingConstant.SUB_MESSAGE_TYPE_REQUEST);
				messageLogService.updateProcessStatusOutMessageLog(requestXml, icmsResponseModel);
			} catch (Exception e) {
				icmsResponseModel.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				icmsResponseModel.setErrorMessage(e.getMessage());
				LOGGER.error("Method End FlightBookingService finally-> saveBookingDetails()-> Exception", e);
			}
		}
		return saveBookingResponse;
	}

	private void setBookingStatus(BookingShipmentDetails bookingDetails) {
		for (BookingFlightDetails bookingFlightDetails : bookingDetails.getBookingFlightDetails()) {
			if(bookingFlightDetails.getBookingStatus()!=null && bookingFlightDetails.getBookingStatus().equals("EM")) {
			bookingFlightDetails.setBookingStatus("Q");
			}else if(bookingFlightDetails.getBookingStatus()!=null) {
				String flightBookingStatus=flightBookingDao.getBookingStatus(bookingFlightDetails.getBookingStatus());
				bookingFlightDetails.setBookingStatus(flightBookingStatus);
				}
		}
	}

	/**
	 * This method used to call validateBooking API in ICMS
	 * 
	 * @param bookingdetails
	 * @return
	 */
	public CancelBookingResponseType cancelBookingDetails(BookingShipmentDetails bookingDetails) {
		LOGGER.info("Method Start FlightBookingService-> cancelBookingDetails()->bookingDetails "
				+ bookingDetails.toString());
		CancelBookingResponseType cancelBookingResponse = null;
		ICMSResponseModel icmsResponseModel = new ICMSResponseModel();
		String requestXml = "";
		try {
			icmsResponseModel = setICMSResponseModel(bookingDetails, BookingConstant.CANCEL_MESSAGE_TYPE,
					BookingConstant.SUB_MESSAGE_TYPE_REQUEST);
			setConstantsValuesToCancelBookingDetails(bookingDetails);
			CancelBookingRequestType cancelBookingRequestType = cancelBookingBuilder
					.createCancelRequest(bookingDetails);
			requestXml = cancelAndMarshal(cancelBookingRequestType);

			Map<String, String> responseMap = soapServiceUtil.callIcmsService(requestXml, "",
					BookingConstant.CANCEL_REQUEST);
			icmsResponseModel.setSubMessageType(BookingConstant.SUB_MESSAGE_TYPE_RESPONSE);
			messageLogService.updateProcessStatusInMessageLog(responseMap.get("responseXml"), icmsResponseModel);
			icmsResponseModel.setHttpStatus(HttpStatus.OK);
		} catch (JAXBException | UnsupportedOperationException e) {
			icmsResponseModel.setHttpStatus(HttpStatus.BAD_REQUEST);
			icmsResponseModel.setErrorMessage(e.getMessage());
			LOGGER.error("Method End FlightBookingService Internal-> cancelBookingDetails()-> Exception", e);
		} catch (Exception e) {
			icmsResponseModel.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			icmsResponseModel.setErrorMessage(e.getMessage());
			LOGGER.error("Method End FlightBookingService Internal Exception-> cancelBookingDetails()-> Exception", e);
		} finally {
			try {
				icmsResponseModel.setSubMessageType(BookingConstant.SUB_MESSAGE_TYPE_REQUEST);
				messageLogService.updateProcessStatusOutMessageLog(requestXml, icmsResponseModel);
			} catch (Exception e) {
				icmsResponseModel.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				icmsResponseModel.setErrorMessage(e.getMessage());
				LOGGER.error("Method End FlightBookingService finally-> cancelBookingDetails()-> Exception", e);
			}
		}
		return cancelBookingResponse;
	}

	/**
	 * Converts validate booking model to xml string
	 * 
	 * @param validateBookingRequestType
	 * @return
	 * @throws JAXBException
	 */
	public String validateAndMarshal(ValidateBookingRequest validateBookingRequestType) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ValidateBookingRequest.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(validateBookingRequestType, stringWriter);
		// validation against XSD
		return stringWriter.toString();

	}

	/**
	 * Converts save booking request model to xml string
	 * 
	 * @param saveBookingTemplateRequestType
	 * @return
	 * @throws JAXBException
	 */
	private String saveAndMarshal(SaveBookingDetailsRequest saveBookingDetailsRequestType) throws JAXBException {
		StringWriter stringWriter = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(SaveBookingDetailsRequest.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		marshaller.marshal(saveBookingDetailsRequestType, stringWriter);
		// validation against XSD
		return stringWriter.toString();
	}

	/**
	 * Converts cancel booking request model to xml string
	 * 
	 * @param cancelBookingRequestType
	 * @return
	 * @throws JAXBException
	 */
	private String cancelAndMarshal(CancelBookingRequestType cancelBookingRequestType) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(CancelBookingRequestType.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(cancelBookingRequestType, stringWriter);
		// validation against XSD
		return stringWriter.toString();
	}

	/**
	 * set values to icms response model
	 * 
	 * @param bookingDetails
	 */
	private ICMSResponseModel setICMSResponseModel(BookingShipmentDetails bookingDetails, String messageType,
			String subMessageType) {
		ICMSResponseModel icmsResponseModel = new ICMSResponseModel();
		if (bookingDetails != null) {
			icmsResponseModel
					.setShipmentNumber(bookingDetails.getShipmentPrefix() + bookingDetails.getMasterDocumentNumber());
			icmsResponseModel.setMessageType(messageType);
			icmsResponseModel.setSubMessageType(subMessageType);
			icmsResponseModel.setCreatedBy(bookingDetails.getCreatedBy());
		}
		return icmsResponseModel;
	}

	private void setFlightRemarksAndFlightSegmentDate(BookingShipmentDetails bookingDetails) throws ParseException {

		Map<String, Object> remarkParam = new HashMap<>();
		remarkParam.put("shipmentNumber",
				bookingDetails.getShipmentPrefix() + bookingDetails.getMasterDocumentNumber());
		remarkParam.put("shipmentDate", bookingDetails.getShipmentDate());
		getShipmentRemarkFromShipmentMaster(bookingDetails, remarkParam);

		List<ShipmentBookingRemark> flightBookingRemarks = flightBookingDao.getShipmentFlightRemarks(remarkParam);
		List<BookingFlightDetails> bookingFlightDetails = bookingDetails.getBookingFlightDetails();
		if (bookingFlightDetails != null) {
			for (BookingFlightDetails flight : bookingFlightDetails) {
				StringBuilder flightRemark = new StringBuilder();
				// for (ShipmentBookingRemark remark : flightBookingRemarks) {
				getFlightRemarkAndSegmentDate(flight, flightRemark, flightBookingRemarks, remarkParam);
				// }
				if (!flightRemark.toString().isEmpty())
					flight.setRemark(flightRemark.toString());
			}
		}

	}

	/**
	 * @param flight
	 * @param flightRemark
	 * @param remark
	 * @throws ParseException
	 */
	private void getFlightRemarkAndSegmentDate(BookingFlightDetails flight, StringBuilder flightRemark,
			List<ShipmentBookingRemark> remarkList, Map<String, Object> remarkParam) throws ParseException {
		int flightId = 0;
		if (flight.getCarrierCode() != null && flight.getFlightNumber() != null && flight.getFlightDate() != null) {
			Map<String, String> queryParam = new HashMap<>();
			queryParam.put(BookingConstant.FLIGHT_NUMBER, flight.getCarrierCode() + flight.getFlightNumber());
			queryParam.put(BookingConstant.FLIGHT_DATE, flight.getFlightDate());
			queryParam.put(BookingConstant.ORIGIN, flight.getSegmentOrigin());
			queryParam.put(BookingConstant.DESTINATION, flight.getSegmentDestination());
			List<com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails> bookingFlightDetails=flightBookingDao.getFlightId(queryParam);
			flightId = bookingFlightDetails.get(0).getFlightId();
			if (flightId > 0) {
				queryParam.put("flightId", String.valueOf(flightId));
				queryParam.put("origin", flight.getSegmentOrigin());
				queryParam.put("destination", flight.getSegmentDestination());
				String segmentDepartureDate = flightBookingDao.getSegmentDepartureDate(queryParam);
				flight.setSegmentDepartureDate(commonUtil.convertDateString(segmentDepartureDate, "yyyy-MM-dd HH:mm:ss",
						"dd-MMM-yyyy HH:mm:ss"));
				for (ShipmentBookingRemark remark : remarkList) {
					if (flightId == remark.getFlightId()) {
						if (flightRemark.length() == 0)
							flightRemark.append(remark.getShipmentRemarks());
						else
							flightRemark.append(";").append(remark.getShipmentRemarks());
					}
				}

				remarkParam.put("flightId", flightId);
				getExpBookingFlightRemark(remarkParam, flightRemark);
			}
		}
	}

	/**
	 * @param flightId
	 * @param flightRemark
	 */
	private void getExpBookingFlightRemark(Map<String, Object> remarkParam, StringBuilder flightRemark) {
		List<String> remarks = flightBookingDao.getExpBookingFlightRemarks(remarkParam);
		if (!remarks.isEmpty()) {
			for (String expRemark : remarks) {
				if (flightRemark.length() == 0)
					flightRemark.append(expRemark);
				else
					flightRemark.append(";").append(expRemark);
			}
		}
	}

	/**
	 * @param bookingDetails
	 * @param map
	 */
	private void getShipmentRemarkFromShipmentMaster(BookingShipmentDetails bookingDetails, Map<String, Object> map) {
		List<ShipmentBookingRemark> shipmentBookingRemarks = flightBookingDao.getShipmentRemarks(map);
		StringBuilder bookingRemark = new StringBuilder();
		for (ShipmentBookingRemark remark : shipmentBookingRemarks) {
			if (remark.getFlightId() == 0) {
				if (bookingRemark.length() == 0)
					bookingRemark.append(remark.getShipmentRemarks());
				else
					bookingRemark.append(";").append(remark.getShipmentRemarks());
			}
		}
		if (!bookingRemark.toString().isEmpty())
			bookingDetails.setBookingRemarks(bookingRemark.toString());
	}

	private BookingShipmentDetails getShipmentDetails(BookingShipmentDetails bookingDetails) {
		BookingShipmentDetails shipmentDetails = flightBookingDao.getShipmentDetails(bookingDetails);
		return shipmentDetails;
	}

	private void setConstantsValuesToSaveBookingDetails(BookingShipmentDetails bookingDetails) {
		List<ConstantValueDetails> constantValueList = flightBookingDao.getConstantValues();
		BookingShipmentDetails shipmentDetails = getShipmentDetails(bookingDetails);
		if(shipmentDetails!=null && shipmentDetails.getLastUpdateTime()!=null) {
			bookingDetails.setLastUpdateTime(shipmentDetails.getLastUpdateTime());
		}
		for (ConstantValueDetails constantValue : constantValueList) {
			switch (constantValue.getParameterCode()) {
			case BookingConstant.CONSTANT_FOR_MESSAGE_TYPE:
				bookingDetails.setMessageType(constantValue.getParameterValue());
				break;
			case BookingConstant.CONSTANT_FOR_SOURCESYSTEM:
				bookingDetails.setSourceSystem(constantValue.getParameterValue());
				bookingDetails.setUserId(constantValue.getParameterValue());
				break;
			case BookingConstant.CONSTANT_FOR_AGENTCODE:
				if (shipmentDetails != null && shipmentDetails.getAgentCode() != null) {
					bookingDetails.setAgentCode(shipmentDetails.getAgentCode());
				} else {
					bookingDetails.setAgentCode(constantValue.getParameterValue());
				}
				break;
			case BookingConstant.CONSTANT_FOR_CUSTOMERCODE:
				if (shipmentDetails != null && shipmentDetails.getCustomerCode() != null) {
					bookingDetails.setCustomerCode(shipmentDetails.getCustomerCode());
				} else {
					bookingDetails.setCustomerCode(constantValue.getParameterValue());
				}
				break;
			case BookingConstant.CONSTANT_FOR_COMMODITYCODE:
				bookingDetails.setCommodityCode(constantValue.getParameterValue());
				break;
			case BookingConstant.CONSTANT_FOR_BOOKINGSOURCE:
				bookingDetails.setBookingSource(constantValue.getParameterValue());
				break;
			case BookingConstant.CONSTANT_FOR_CURRENCY:
				bookingDetails.setCurrency(constantValue.getParameterValue());
				break;
			case BookingConstant.CONSTANT_FOR_DIMENSION:
				bookingDetails.setDummyDimensionValue(constantValue.getParameterValue());
				break;
			case BookingConstant.CONSTANT_FOR_DIMENSIONUNIT:
				bookingDetails.setDimensionUnit(constantValue.getParameterValue());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * set constant values to cancel booking detail model
	 * 
	 * @param bookingDetails
	 */
	private void setConstantsValuesToCancelBookingDetails(BookingShipmentDetails bookingDetails) {
		List<ConstantValueDetails> constantValueList = flightBookingDao.getConstantValues();
		BookingShipmentDetails shipmentDetails = getShipmentDetails(bookingDetails);
		for (ConstantValueDetails constantValue : constantValueList) {
			switch (constantValue.getParameterCode()) {
			case BookingConstant.CONSTANT_FOR_MESSAGE_TYPE:
				bookingDetails.setMessageType(constantValue.getParameterValue());
				break;
			case BookingConstant.CONSTANT_FOR_SOURCESYSTEM:
				bookingDetails.setSourceSystem(constantValue.getParameterValue());
				bookingDetails.setUserId(constantValue.getParameterValue());
				break;
			case BookingConstant.CONSTANT_FOR_AGENTCODE:
				if (shipmentDetails != null && shipmentDetails.getAgentCode() != null) {
					bookingDetails.setAgentCode(shipmentDetails.getAgentCode());
				} else {
					bookingDetails.setAgentCode(constantValue.getParameterValue());
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * full routing condition for flight booking
	 * 
	 * @param bookingShipment
	 */
	private void fullRoutingFlightBooking(BookingShipmentDetails bookingShipment) {
		List<BookingFlightDetails> flightList = bookingShipment.getBookingFlightDetails();
		if (!CollectionUtils.isEmpty(flightList)) {
			checkFullRoutingCheckForEachPart(bookingShipment, flightList);
		}

	}

	/**
	 * check full routing check for each part
	 * 
	 * @param bookingShipment
	 * @param originDest
	 * @param isOriginPresent
	 * @param isDestnPresent
	 * @param flightList
	 */
	private void checkFullRoutingCheckForEachPart(BookingShipmentDetails bookingShipment,
			List<BookingFlightDetails> flightList) {
		LOGGER.info("Method start checkFullRoutingCheckForEachPart -> bookingShipment ->" + bookingShipment.toString());
		boolean isOriginPresent = false;
		boolean isDestnPresent = false;
		

		for(BookingFlightDetails flight : flightList) {
			if (flight.getSegmentOrigin().equalsIgnoreCase(bookingShipment.getShipmentOrigin())) {
				isOriginPresent = true;
			}
			if (flight.getSegmentDestination().equalsIgnoreCase(bookingShipment.getShipmentDestination())) {
				isDestnPresent = true;
			}
		}
		
		if (!(isOriginPresent && isDestnPresent)) {
			List<com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails> intermidiateFlightList = flightBookingDao
					.fetchIntermidiateFlightDetails(bookingShipment.getShipmentPrefix(),
							bookingShipment.getMasterDocumentNumber());
			List<com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails> outWardFlightList = new ArrayList<>();
			if (!CollectionUtils.isEmpty(intermidiateFlightList)) {
				if(!isDestnPresent) {
					for(com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails intermidiateFlight : intermidiateFlightList ) {
						if(intermidiateFlight.getSegmentDestination().equals(bookingShipment.getShipmentDestination())) {
							outWardFlightList.add(intermidiateFlight);
						}
					}
				}
				if(!isOriginPresent) {
					for(com.ngen.cosys.icms.schema.flightbooking.BookingFlightDetails intermidiateFlight : intermidiateFlightList ) {
						if(intermidiateFlight.getSegmentOrigin().equals(bookingShipment.getShipmentOrigin())) {
							outWardFlightList.add(intermidiateFlight);
						}
					}
				}
				
				outWardFlightList.forEach(intermidiateFlight -> {
					BookingFlightDetails flight = new BookingFlightDetails();
					flight.setSegmentOrigin(intermidiateFlight.getSegmentOrigin());
					flight.setSegmentDestination(intermidiateFlight.getSegmentDestination());
					flight.setCarrierCode(intermidiateFlight.getCarrierCode());
					if (intermidiateFlight.getFlightNumber() != null) {
						flight.setFlightNumber(String.valueOf(intermidiateFlight.getFlightNumber()));
					}
					if(intermidiateFlight.getSegmentDepartureDate() != null) {
						flight.setSegmentDepartureDate(commonUtil.convertDateString(intermidiateFlight.getSegmentDepartureDate(), "yyyy-MM-dd HH:mm:ss",
								"dd-MMM-yyyy HH:mm:ss"));
					}
					flight.setPiece(BigInteger.valueOf(intermidiateFlight.getPieces()));
					flight.setWeight(intermidiateFlight.getWeight());
					flight.setVolume(intermidiateFlight.getVolume());
					flight.setFlightDate(intermidiateFlight.getFlightDate());
					flight.setBookingStatus(intermidiateFlight.getFlightBookingStatus());
					flightList.add(flight);
				});
			} else {
				if(!isDestnPresent) {
					List<BookingFlightDetails> newFlightList = new ArrayList<>();
					for(BookingFlightDetails bookingFlight : flightList ) {
						if(bookingFlight.getSegmentOrigin().equals(BookingConstant.TENANT_SINGAPORE)) {
							BookingFlightDetails flight = new BookingFlightDetails();
							flight.setSegmentOrigin(bookingFlight.getSegmentDestination());
							flight.setSegmentDestination(bookingShipment.getShipmentDestination());
							flight.setCarrierCode(bookingFlight.getCarrierCode());
							flight.setPiece(BigInteger.valueOf(BookingConstant.ZERO));
							flight.setWeight(BookingConstant.DOUBLE_ZERO);
							newFlightList.add(flight);
						}
					}
					flightList.addAll(newFlightList);
				}
				if(!isOriginPresent) {
					List<BookingFlightDetails> newFlightList = new ArrayList<>();
					for(BookingFlightDetails bookingFlight : flightList ) {
						if(bookingFlight.getSegmentDestination().equals(BookingConstant.TENANT_SINGAPORE)) {
							BookingFlightDetails flight = new BookingFlightDetails();
							flight.setSegmentOrigin(bookingShipment.getShipmentOrigin());
							flight.setSegmentDestination(bookingFlight.getSegmentOrigin());
							flight.setCarrierCode(bookingFlight.getCarrierCode());
							flight.setPiece(BigInteger.valueOf(BookingConstant.ZERO));
							flight.setWeight(BookingConstant.DOUBLE_ZERO);
							newFlightList.add(flight);
						}
					}
					flightList.addAll(newFlightList);
				}

			}
		}
	}

	private String convertCosysUnitCodeTOICMSVolumeUnitCode(String cosysVolumeUnitCode) {
		if (cosysVolumeUnitCode.equalsIgnoreCase(BookingConstant.VOLUME_UNIT_MC)) {
			return BookingConstant.ICMS_VOLUME_UNIT_B;
		} else if (cosysVolumeUnitCode.equalsIgnoreCase(BookingConstant.VOLUME_UNIT_CF)) {
			return BookingConstant.ICMS_VOLUME_UNIT_F;
		} else if (cosysVolumeUnitCode.equalsIgnoreCase(BookingConstant.VOLUME_UNIT_CI)) {
			return BookingConstant.ICMS_VOLUME_UNIT_I;
		} else if (cosysVolumeUnitCode.equalsIgnoreCase(BookingConstant.VOLUME_UNIT_CC)) {
			return BookingConstant.ICMS_VOLUME_UNIT_C;
		} else {
			LOGGER.info(
					"Method End -> ShipmentBookingDetailBuilder -> convertVolumeUnitCodeTOCosysVolumeUnitCode -> else"
							+ cosysVolumeUnitCode);
			return BookingConstant.ICMS_VOLUME_UNIT_B;
		}

	}
}
