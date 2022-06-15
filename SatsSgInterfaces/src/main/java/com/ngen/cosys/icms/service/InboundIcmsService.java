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
package com.ngen.cosys.icms.service;



import java.io.StringReader;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.icms.model.ICMSResponseModel;
import com.ngen.cosys.icms.schema.flightbooking.BookingDetails;
import com.ngen.cosys.icms.schema.flightbooking.PublishBookingDetails;
import com.ngen.cosys.icms.model.operationFlight.OperationalFlightInfo;
import com.ngen.cosys.icms.model.scheduleFlight.ScheduleFlightInfo;
import com.ngen.cosys.icms.processor.flightbooking.FlightBookingProcessor;
import com.ngen.cosys.icms.processor.operationFlight.MapXmlToFlightInfoModel;
import com.ngen.cosys.icms.processor.operationFlight.OperationalFlightProcessor;
import com.ngen.cosys.icms.processor.scheduleFlight.MapXmlToScheduleFlightInfoModel;
import com.ngen.cosys.icms.processor.scheduleFlight.ScheduleFlightProcessor;
import com.ngen.cosys.icms.schema.operationFlight.FlightOperation;
import com.ngen.cosys.icms.schema.operationFlight.OperationalFlightPublish;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightSchedule;
import com.ngen.cosys.icms.schema.scheduleFlight.FlightSchedulePublish;
import com.ngen.cosys.icms.util.CommonUtil;
import com.ngen.cosys.icms.util.ValidationConstant;
import com.ngen.cosys.icms.validation.xmlValidator;
import com.ngen.cosys.satssginterfaces.message.exception.MessageProcessingException;
import com.ngen.cosys.icms.exception.MessageParseException;

/**
 * This Service class will parse the input xml string.
 * And separate SSM and ASM message
 */

@Service
public class InboundIcmsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundIcmsService.class);
	
	@Autowired 
	Validator validator;
	
	@Autowired 
	ScheduleFlightProcessor scheduleFlightProcessor;
	
	@Autowired 
	OperationalFlightProcessor operationalFlightProcessor;
		
	@Autowired
	private ApplicationLoggerService loggerService;
	
	@Autowired
	private OperationalFlightPublish operationalFlightPublish;
	
	@Autowired
	private FlightSchedulePublish flightSchedulePublish;
	
	@Autowired 
	FlightBookingProcessor flightBookingProcessor;
	
	@Autowired
	MapXmlToScheduleFlightInfoModel mapXmlToScheduleFlightInfoModel;
	
	//@Autowired
	//ICMSResponseModel icmsResponseModel;
	
	@Autowired
	private MapXmlToFlightInfoModel mapXmlToFlightInfoModel;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PublishBookingDetails publishBookingDetails;
	
    /**
     * This method helps to parse and process the incoming message
     * @param requestPayload
     * @param queryParam 
     * @return
     */
    public ICMSResponseModel processIncomingMessage(String requestPayload,BigInteger messageId, HashMap<String, String> queryParam){
    	LOGGER.info("Method Start InboundIcmsService-> processIncomingMessage()-> RequestPayload :"+requestPayload);
    	String tagName=null;
    	ICMSResponseModel icmsResponseModel = new ICMSResponseModel();
        try {
        	//Read xml file and get Root element name to find operational or schedule message
        	tagName = commonUtil.getTagNamefromXmlString(requestPayload);
        	if(ValidationConstant.FLIGHT_SCHEDULE_TYPE.equalsIgnoreCase(tagName)) {
        		validateAndUnmarshalScheduleFlightInfoRequest(requestPayload);
        		scheduleFlightProcessor.businessValidations(flightSchedulePublish);
        		ScheduleFlightInfo scheduleFlightInfo = mapXmlToScheduleFlightInfoModel.createScheduleFlightInfoModel(flightSchedulePublish);
        		icmsResponseModel = scheduleFlightProcessor.processScheduleFlightMessage(scheduleFlightInfo); 
        	}
        	else if(ValidationConstant.OPERATIONAL_FLIGHT.equalsIgnoreCase(tagName)){
        		validateAndUnmarshalOperationalFlightInfoRequest(requestPayload);
				operationalFlightProcessor.businessValidations(operationalFlightPublish);        		
				OperationalFlightInfo flightInfo = mapXmlToFlightInfoModel.createOperationalFlightInfoModel(operationalFlightPublish);
        		icmsResponseModel=operationalFlightProcessor.processOperationalFlightMessage(flightInfo);        	
        	}
			else if(ValidationConstant.FLIGHT_BOOKING.equalsIgnoreCase(tagName)){
        		validateAndUnmarshalFlightBookingRequest(requestPayload, messageId);
				flightBookingProcessor.xsdValidations(publishBookingDetails);        		
        		icmsResponseModel=flightBookingProcessor.processBookingPublishMessage(publishBookingDetails); 
        		System.out.println("Method End InboundIcmsService-> processIncomingMessage()->success");
        	}        	
			else {
        		LOGGER.info("Method End InboundIcmsService-> processIncomingMessage()-> Check the xml");
        		icmsResponseModel.setHttpStatus(HttpStatus.BAD_REQUEST);
        		icmsResponseModel.setErrorMessage("Invalid XML");
			} 
        }catch(FactoryConfigurationError | XMLStreamException | ParseException | MessageParseException | MessageProcessingException | CustomException e) {
        	LOGGER.error("Method End InboundIcmsService Internal-> processIncomingMessage()-> Exception",e);
        	icmsResponseModel.setHttpStatus(HttpStatus.BAD_REQUEST);
        	icmsResponseModel.setErrorMessage(e.getMessage());
        }
//        catch(MessageProcessingException | CustomException e) {
//        	LOGGER.error("Method End InboundIcmsService-> processIncomingMessage()-> Exception"+e.getMessage());
//        	e.printStackTrace();
//        	icmsResponseModel.setHttpStatus(HttpStatus.OK);
//        	icmsResponseModel.setErrorMessage(e.getMessage());
//        }
        catch(Exception e) {
        	LOGGER.error("Method End InboundIcmsService-> processIncomingMessage()-> Exception",e);
        	icmsResponseModel.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        	icmsResponseModel.setErrorMessage(e.getMessage());
        }
        finally {
        	try {
        		if(ValidationConstant.FLIGHT_SCHEDULE_TYPE.equalsIgnoreCase(tagName) && flightSchedulePublish != null) { 			
        			setICMSResponseModel(flightSchedulePublish.getObjEntity().getPublishData().getFlightschedule(),icmsResponseModel);
        		}else if(ValidationConstant.OPERATIONAL_FLIGHT.equalsIgnoreCase(tagName) && operationalFlightPublish != null) {
        			setICMSResponseModel(operationalFlightPublish.getObjEntity().getPublishData().getFlightoperation(),icmsResponseModel);
        		}else if(ValidationConstant.FLIGHT_BOOKING.equalsIgnoreCase(tagName) && publishBookingDetails!=null)  {
        			setICMSResponseModel(publishBookingDetails.getPublishData().getBookingDetails(),icmsResponseModel);
				}
        		updateProcessStatusInMessageLog(requestPayload, icmsResponseModel, messageId,queryParam);
        	}catch(Exception e) {
        		LOGGER.error("Method End InboundIcmsService finally-> processIncomingMessage()-> Exception",e);
        	}
        }
        
        return icmsResponseModel;
    }
	
	

	/**
	 * unmarshal xml string to OperationalFlightPublish
	 * @param requestPayload
	 * @throws JAXBException
	 */
	private OperationalFlightPublish validateAndUnmarshalOperationalFlightInfoRequest(String requestPayload) throws JAXBException {
		//xml parsing to model
		StringReader strReader = new StringReader(requestPayload);
		JAXBContext jaxbContext = JAXBContext.newInstance(OperationalFlightPublish.class);  
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();	
		StringBuilder errorMessage = new StringBuilder();
		operationalFlightPublish = (OperationalFlightPublish) unmarshal.unmarshal(strReader); 
		LOGGER.info("Method validateAndUnmarshalOperationalFlightInfoRequest-> parseXmlToOperationalFlightInfo() -> operationalFlightPublish "+operationalFlightPublish.toString());
		System.out.println("Method validateAndUnmarshalOperationalFlightInfoRequest-> parseXmlToOperationalFlightInfo() -> operationalFlightPublish "+operationalFlightPublish.toString());
		
		//set values to icms model
		//setICMSResponseModel(operationalFlightPublish.getObjEntity().getPublishData().getFlightoperation());
		
		//xml validation
		Set<ConstraintViolation<OperationalFlightPublish>> violations = this.validator.validate(operationalFlightPublish, xmlValidator.class);
		Iterator<ConstraintViolation<OperationalFlightPublish>> iterator = violations.iterator();
        while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(ValidationConstant.SEMICOLON);
        }
		if(!violations.isEmpty()) {
			throw new MessageParseException(errorMessage.toString());
		}
		return operationalFlightPublish;
	}

	/**
	 * unmarshal xml string to FlightSchedulePublish
	 * @param requestPayload
	 * @throws JAXBException
	 * @throws MessageParseException 
	 */
	private FlightSchedulePublish validateAndUnmarshalScheduleFlightInfoRequest(String requestPayload) throws JAXBException, MessageParseException {
		//xml parsing to model
		StringReader strReader = new StringReader(requestPayload);
		JAXBContext jaxbContext = JAXBContext.newInstance(FlightSchedulePublish.class);  
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();
		StringBuilder errorMessage = new StringBuilder();
		flightSchedulePublish = (FlightSchedulePublish) unmarshal.unmarshal(strReader);
		LOGGER.info("Method InboundIcmsService-> parseXmlToScheduleFlightInfo() -> flightSchedulePublish "+flightSchedulePublish.toString());
		
		//xml validation
		Set<ConstraintViolation<FlightSchedulePublish>> violations = this.validator.validate(flightSchedulePublish, xmlValidator.class);
		Iterator<ConstraintViolation<FlightSchedulePublish>> iterator = violations.iterator();
        while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(ValidationConstant.SEMICOLON);
        }
		if(!violations.isEmpty()) {
			throw new MessageParseException(errorMessage.toString());
		}
		return flightSchedulePublish;
	}
	/**
	 * unmarshal xml string to FlightBooking
	 * @param requestPayload
	 * @throws JAXBException
	 * @throws MessageParseException 
	 * @throws XMLStreamException 
	 */
	private PublishBookingDetails validateAndUnmarshalFlightBookingRequest(String requestPayload,BigInteger messageId) throws JAXBException, MessageParseException, XMLStreamException {
		//xml parsing to model
		StringReader strReader = new StringReader(requestPayload);
		System.out.println("str reader::"+requestPayload.toString());
		JAXBContext jaxbContext = JAXBContext.newInstance(PublishBookingDetails.class);  
		Unmarshaller unmarshal = jaxbContext.createUnmarshaller();
		StringBuilder errorMessage = new StringBuilder();
		 publishBookingDetails = (PublishBookingDetails) unmarshal.unmarshal(strReader);

		System.out.println("header::0"+publishBookingDetails.getPublishHeader().toString());
		LOGGER.info("Method InboundIcmsService-> parseXmlToFlightBookingInfo() -> FlightBooking "+publishBookingDetails.toString());
		System.out.println("Method InboundIcmsService-> parseXmlToFlightBookingInfo() -> FlightBooking "+publishBookingDetails.toString());
		
		//xml validation
		Set<ConstraintViolation<PublishBookingDetails>> violations = this.validator.validate(publishBookingDetails, xmlValidator.class);
		Iterator<ConstraintViolation<PublishBookingDetails>> iterator = violations.iterator();
        while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(ValidationConstant.SEMICOLON);
        }
		if(!violations.isEmpty()) {
			throw new MessageParseException(errorMessage.toString());
		}
		return publishBookingDetails;
	}	
	/**
	 * set values to ICMS response model
	 * @param scheduleFlightInfo
	 * @param icmsResponse
	 * @param e
	 */
	private void setICMSResponseModel(FlightSchedule flightSchedule,ICMSResponseModel icmsResponseModel) {
		if(flightSchedule != null) {
			icmsResponseModel.setCarrier(flightSchedule.getCarrierCode());
			icmsResponseModel.setFlightNo(flightSchedule.getFlightscheduleNumber());
			if(ValidationConstant.STATUS_LIVE.equalsIgnoreCase(flightSchedule.getFlightscheduleStatus()) || ValidationConstant.STATUS_NOP.equalsIgnoreCase(flightSchedule.getFlightscheduleStatus()) 
		    		|| ValidationConstant.STATUS_TBA.equalsIgnoreCase(flightSchedule.getFlightscheduleStatus()) || ValidationConstant.STATUS_MODIFIED.equalsIgnoreCase(flightSchedule.getFlightscheduleStatus()) 
		    		|| ValidationConstant.STATUS_PUB.equalsIgnoreCase(flightSchedule.getFlightscheduleStatus())) {
				icmsResponseModel.setFlightStatus(ValidationConstant.STATUS_LIVE.toUpperCase());
			}
		    else if(ValidationConstant.STATUS_CAN.equalsIgnoreCase(flightSchedule.getFlightscheduleStatus()) || ValidationConstant.STATUS_TBC.equalsIgnoreCase(flightSchedule.getFlightscheduleStatus())) {
		    	icmsResponseModel.setFlightStatus(ValidationConstant.STATUS_CAN.toUpperCase());
		    }else {
		    	icmsResponseModel.setFlightStatus(flightSchedule.getFlightscheduleStatus().toUpperCase());
		    }
			icmsResponseModel.setMessageType(ValidationConstant.FLIGHT_SCHEDULE);
		}
	}
	
	/**
	 * set values to ICMS response model
	 * @param flightoperation
	 */
	private void setICMSResponseModel(FlightOperation flightoperation,ICMSResponseModel icmsResponseModel) {
		if(flightoperation != null) {
			icmsResponseModel.setCarrier(flightoperation.getCarrierCode());
			icmsResponseModel.setFlightNo(flightoperation.getFlightNumber());
			icmsResponseModel.setFlightDate(commonUtil.convertStringToLocalDate(flightoperation.getFlightDate(), ValidationConstant.XML_DATE_FORMAT).atTime(00, 00, 00));
			
			icmsResponseModel.setMessageType(ValidationConstant.OPERATIONAL_FLIGHT);
			if (ValidationConstant.STATUS_ACTIVE.equalsIgnoreCase(flightoperation.getFlightStatus())
					|| ValidationConstant.STATUS_TBA.equalsIgnoreCase(flightoperation.getFlightStatus())) {
				icmsResponseModel.setFlightStatus(ValidationConstant.STATUS_ACTIVE.toUpperCase());
			}else {
				icmsResponseModel.setFlightStatus(flightoperation.getFlightStatus().toUpperCase());
			}
		}
		
	}
	
	/**
	 * set values to ICMS response model
	 * @param bookingFlightDetails
	 */
	private void setICMSResponseModel(BookingDetails bookingDetails,ICMSResponseModel icmsResponseModel) {
		if(bookingDetails != null) {
			if(bookingDetails.getShipmentIdentifierDetails() != null) {
				
				String shipmentNo = String.valueOf(bookingDetails.getShipmentIdentifierDetails().getShipmentPrefix()) + String.valueOf(bookingDetails.getShipmentIdentifierDetails().getMasterDocumentNumber());
				icmsResponseModel.setShipmentNumber(shipmentNo);
			}
			
		}
		icmsResponseModel.setMessageType(ValidationConstant.FLIGHT_BOOKING_MESSAGE.toUpperCase());
	}
	
	/**
	 * insert status of message request in message log table
	 * @param requestPayload
	 * @param icmsResponse
	 * @param messageId
	 * @param queryParam 
	 * @param messageType
	 */
	private void updateProcessStatusInMessageLog(String requestPayload, ICMSResponseModel icmsResponse, BigInteger messageId, HashMap<String, String> queryParam) {
		LOGGER.info("Method Start InboundIcmsService-> updateProcessStatusInMessageLog()-> ICMSResponseModel :"+icmsResponse);
	    IncomingMessageLog incomingMessage;
		if (icmsResponse != null && icmsResponse.getHttpStatus().equals(HttpStatus.OK)) {
		//	icmsResponse.setMessageErrorLog(new IncomingMessageErrorLog());
		//	icmsResponse.setErrorMessage("");
		    incomingMessage = updateIncomingMessageLog(messageId, ValidationConstant.PROCESSED,icmsResponse, requestPayload,queryParam);
//		    if(icmsResponse.getErrorMessage() != null) {
//		    	insertIncomingErrorMessage(incomingMessage.getInMessageId(),icmsResponse.getErrorMessage());
//		    }
		}
		else {
		    incomingMessage = updateIncomingMessageLog(messageId, ValidationConstant.REJECTED, icmsResponse, requestPayload,queryParam);
		    insertIncomingErrorMessage(incomingMessage.getInMessageId(),icmsResponse.getErrorMessage(),icmsResponse);
		}
		LOGGER.info("Method End InboundIcmsService-> updateProcessStatusInMessageLog()");
	}
    
	/**
	 * update the message status in message log
	 * @param messageId
	 * @param status
	 * @param submessageType
	 * @param payload
	 * @param queryParam.get("isediscreen") 
	 * @param carrier
	 * @param flightNum
	 * @param flightDate
	 * @param messageType
	 */
    private IncomingMessageLog updateIncomingMessageLog(BigInteger messageId, String status, ICMSResponseModel icmsResponse, String payload, HashMap<String, String> queryParam) {
        IncomingMessageLog incomingMessage = new IncomingMessageLog();
        incomingMessage.setInMessageId(messageId);
        incomingMessage.setChannelReceived(ValidationConstant.CHANNEL_RECEIVED);
        if(queryParam.get(ValidationConstant.IS_EDI_SCREEN)!=null && queryParam.get(ValidationConstant.IS_EDI_SCREEN).equals(ValidationConstant.TRUE)) {
            incomingMessage.setInterfaceSystem(ValidationConstant.MANUAL+ValidationConstant.START_BRACKET+queryParam.get(ValidationConstant.LOGIN_USER)+ValidationConstant.END_BRACKET);
        }else {
            incomingMessage.setInterfaceSystem(ValidationConstant.SYSTEM);
        }
        incomingMessage.setMessageType(icmsResponse.getMessageType()); 
        incomingMessage.setSubMessageType(icmsResponse.getFlightStatus()); //flightScheduleStatus
        incomingMessage.setCarrierCode(icmsResponse.getCarrier());
        incomingMessage.setFlightNumber(icmsResponse.getFlightNo());
        incomingMessage.setFlightOriginDate(icmsResponse.getFlightDate());
        incomingMessage.setShipmentNumber(icmsResponse.getShipmentNumber());
        incomingMessage.setShipmentDate(null);
        incomingMessage.setReceivedOn(LocalDateTime.now());
        incomingMessage.setVersionNo(1);
        incomingMessage.setSequenceNo(1);
        incomingMessage.setMessageContentEndIndicator(null);
        incomingMessage.setMessage(payload);
        incomingMessage.setStatus(status);
        icmsResponse.setMessageLog(incomingMessage);
        loggerService.logInterfaceIncomingMessage(incomingMessage);
        return incomingMessage;
    }
    
    /**
     * insert error in error message table
     * @param messageId
     * @param httpStatus
     * @param errorMessage
     * @return
     */
    public BigInteger insertIncomingErrorMessage(BigInteger messageId, String errorMessage,ICMSResponseModel icmsResponse) {
    	LOGGER.info("Method Start InboundIcmsService-> insertIncomingErrorMessage()->messgaeId"+messageId);
        IncomingMessageErrorLog incomingErrorMessage = new IncomingMessageErrorLog();
        incomingErrorMessage.setInMessageId(messageId);
        incomingErrorMessage.setErrorCode(ValidationConstant.ERROR_CODE);
        incomingErrorMessage.setLineItem(null);
	    if (Objects.nonNull(errorMessage)) {
	    	String[] errorMsg = null;
	        if(errorMessage.contains("  ;  ")) {
	        	errorMsg = errorMessage.split("  ;  ");
	        }
	        int count=1;
	        if(errorMsg != null) {
	        	for (String error : errorMsg) {
	        		incomingErrorMessage.setMessage(error);
	        		incomingErrorMessage.setErrorCode(String.valueOf(count));
	        	    icmsResponse.setMessageErrorLog(incomingErrorMessage);
	        	    loggerService.logInterfaceIncomingErrorMessage(incomingErrorMessage);
	        	    count++;
	        	}
	        }else {
	        	incomingErrorMessage.setMessage(ConnectorUtils.getErrorMessage(errorMessage));
	        	icmsResponse.setMessageErrorLog(incomingErrorMessage);
		        loggerService.logInterfaceIncomingErrorMessage(incomingErrorMessage);
	        }
	    }
        return incomingErrorMessage.getInMessageId();
    }

    
}
