package com.ngen.cosys.icms.service.messageLog;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.icms.dao.flightbooking.FlightBookingDao;
import com.ngen.cosys.icms.model.ICMSResponseModel;
import com.ngen.cosys.icms.util.BookingConstant;
import com.ngen.cosys.icms.util.ValidationConstant;
@Component
public class OutgoingMessageLogService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OutgoingMessageLogService.class);
	
	@Autowired
	private ApplicationLoggerService loggerService;
	
	@Autowired
	FlightBookingDao flightBookingDao;
	
	/**
	 * method to call outgoing message log and error log
	 * @param requestPayload
	 * @param icmsResponse
	 */
	public void updateProcessStatusOutMessageLog(String requestPayload, ICMSResponseModel icmsResponse) {
		LOGGER.info("Method Start OutgoingMessageLogService -> icmsResponse ->"+icmsResponse.toString());
		if (icmsResponse != null && icmsResponse.getHttpStatus().equals(HttpStatus.OK)) {
			BigInteger messageId = logOutgoingMessage(requestPayload,icmsResponse, BookingConstant.STATUS_SENT);
			if(messageId != null) {
				Map<String,Object> mapRecipient = new HashMap<String,Object>();
				mapRecipient.put("address", ValidationConstant.SYSTEM);
				mapRecipient.put("referenceId", messageId);
				mapRecipient.put("createdUserId", BookingConstant.CREATEDBY);
				flightBookingDao.createOutgoingMessageRecipients(mapRecipient);
			}
		}
		else {
		    BigInteger messageId = logOutgoingMessage(requestPayload, icmsResponse, BookingConstant.STATUS_ERROR);
		    if(messageId != null) {
		    	insertOutgoingErrorMessage(messageId,icmsResponse.getErrorMessage());
		    }
		}
	}
	
	/**
	 * insert record in error table
	 * @param messageId
	 * @param errorMessage
	 */
	private void insertOutgoingErrorMessage(BigInteger messageId, String errorMessage) {
	      OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();
	      outgoingErrorMessage.setOutMessageId(messageId);
	      outgoingErrorMessage.setErrorCode("EXCEPTION");
	      if (Objects.nonNull(errorMessage)) {
	            outgoingErrorMessage.setMessage(ConnectorUtils.getErrorMessage(errorMessage));
	      }
	      outgoingErrorMessage.setLineItem(null);
		System.out.println(outgoingErrorMessage.toString());
	      loggerService.logInterfaceOutgoingErrorMessage(outgoingErrorMessage);
	 }
	 
	/**
	 * insert record in outgoing message log
	 * @param payload
	 * @param icmsResponse
	 * @param status
	 * @return
	 */
	 private BigInteger logOutgoingMessage(String payload,ICMSResponseModel icmsResponse,String status) {
			OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
			outgoingMessage.setChannelSent(ValidationConstant.CHANNEL_RECEIVED);
			outgoingMessage.setInterfaceSystem(ValidationConstant.SYSTEM);
			outgoingMessage.setSenderOriginAddress(BookingConstant.SATS);
			outgoingMessage.setMessageType(icmsResponse.getMessageType());
			outgoingMessage.setSubMessageType(icmsResponse.getSubMessageType());
			outgoingMessage.setCarrierCode(null);
			outgoingMessage.setFlightNumber(null);
			outgoingMessage.setFlightOriginDate(null);
			outgoingMessage.setShipmentNumber(icmsResponse.getShipmentNumber());
			outgoingMessage.setShipmentDate(null);
			outgoingMessage.setRequestedOn(LocalDateTime.now());
			outgoingMessage.setSentOn(LocalDateTime.now());
			outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
			outgoingMessage.setVersionNo(1);
			outgoingMessage.setSequenceNo(1);
			outgoingMessage.setMessageContentEndIndicator(null);
			outgoingMessage.setStatus(status);
			outgoingMessage.setMessage(payload);
			if (null != icmsResponse.getCreatedBy()) {
				outgoingMessage.setCreatedBy(icmsResponse.getCreatedBy());
			} else {
				outgoingMessage.setCreatedBy("SYSADMIN");
			}
			flightBookingDao.insertInterfaceOutgoingMessageICMS(outgoingMessage);
			return outgoingMessage.getOutMessageId();
		}
	 
	 /**
		 * insert status of message request in message log table
		 * @param requestPayload
		 * @param icmsResponse
		 * @param messageId
		 * @param messageType
		 */
		public void updateProcessStatusInMessageLog(String requestPayload, ICMSResponseModel icmsResponse) {
			LOGGER.info("Method Start InboundIcmsService-> updateProcessStatusInMessageLog()-> ICMSResponseModel :"+icmsResponse);
			if(requestPayload==null || requestPayload.isEmpty()) {
				requestPayload="ERROR";
			}
			updateIncomingMessageLog(ValidationConstant.PROCESSED, icmsResponse, requestPayload);
		}
	 	/**
		 * update the message status in message log
		 * @param messageId
		 * @param status
		 * @param submessageType
		 * @param payload
		 * @param carrier
		 * @param flightNum
		 * @param flightDate
		 * @param messageType
		 */
	    private IncomingMessageLog updateIncomingMessageLog(String status, ICMSResponseModel icmsResponse, String payload) {
	        IncomingMessageLog incomingMessage = new IncomingMessageLog();
	       // incomingMessage.setInMessageId(messageId);
	        incomingMessage.setChannelReceived(ValidationConstant.CHANNEL_RECEIVED);
	        incomingMessage.setInterfaceSystem(ValidationConstant.SYSTEM);
	        incomingMessage.setMessageType(icmsResponse.getMessageType()); 
	        incomingMessage.setSubMessageType(icmsResponse.getSubMessageType());
	        incomingMessage.setCarrierCode(null);
	        incomingMessage.setFlightNumber(null);
	        incomingMessage.setFlightOriginDate(null);
	        incomingMessage.setShipmentNumber(icmsResponse.getShipmentNumber());
	        incomingMessage.setShipmentDate(null);
	        incomingMessage.setReceivedOn(LocalDateTime.now());
	        incomingMessage.setVersionNo(1);
	        incomingMessage.setSequenceNo(1);
	        incomingMessage.setMessageContentEndIndicator(null);
	        incomingMessage.setMessage(payload);
	        incomingMessage.setStatus(status);
	        System.out.println("incomingMessage::"+incomingMessage);
	        loggerService.logInterfaceIncomingMessage(incomingMessage);
	        return incomingMessage;
	    }

}
