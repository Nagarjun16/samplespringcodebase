package com.ngen.cosys.billing.sap.airline.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.scheduler.esb.connector.ConnectorService;
import com.ngen.cosys.scheduler.esb.connector.ConnectorUtils;
@Service
public class SapAirlineServiceImpl  implements SapAirlineService{
	private static Logger logger = LoggerFactory.getLogger(SapAirlineServiceImpl.class);
	@Autowired
	private ConnectorService esbConnectorService;
	
	@Autowired
	private ApplicationLoggerService loggerService;
	
	
	@Override
	public BigInteger logOutgoingMessage(String interfaceName, String messageType, String carrierCode,
			String flightNumber, LocalDateTime flightDate, String shipmentNumber, LocalDateTime shipmentDate,
			String payload, String status,String interfaceFileName,String hawbNumber) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setChannelSent("FTP");
		outgoingMessage.setInterfaceSystem(interfaceName);
		outgoingMessage.setMessageType("SAP");
		outgoingMessage.setSubMessageType(messageType);
		outgoingMessage.setCarrierCode(carrierCode);
		outgoingMessage.setFlightNumber(flightNumber);
		outgoingMessage.setFlightOriginDate(flightDate);
		outgoingMessage.setShipmentNumber(shipmentNumber);
		outgoingMessage.setShipmentDate(shipmentDate);
		outgoingMessage.setRequestedOn(LocalDateTime.now());
		outgoingMessage.setSentOn(LocalDateTime.now());
		outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
		outgoingMessage.setVersionNo(null);
		outgoingMessage.setSequenceNo(1);
		outgoingMessage.setMessageContentEndIndicator(null);
		outgoingMessage.setStatus(status);
		outgoingMessage.setMessage(payload);
		outgoingMessage.setInterfaceFileName(interfaceFileName);
		outgoingMessage.setHawbNumber(hawbNumber);
		loggerService.logInterfaceOutgoingMessage(outgoingMessage);
		return outgoingMessage.getOutMessageId();
	}
	@Override
	public void logOugoingErrorMessage(BigInteger messageLogId, String errorMessage) {
		OutgoingMessageErrorLog outgoingMessageErrorLog = new OutgoingMessageErrorLog();
		outgoingMessageErrorLog.setOutMessageId(messageLogId);
		outgoingMessageErrorLog.setErrorCode("1");
		outgoingMessageErrorLog.setMessage(errorMessage);
		loggerService.logInterfaceOutgoingErrorMessage(outgoingMessageErrorLog);
	}
	@Override
	public BigInteger logInterfaceIncomingMessage(String interfaceName, String messageType, String carrierCode,
			String flightNumber, LocalDateTime flightDate, String shipmentNumber, LocalDateTime shipmentDate,
			String payload, String status,String interfaceFileName,String hawbNumber) {
		IncomingMessageLog incomingMessageLog = new IncomingMessageLog();
		incomingMessageLog.setChannelReceived("SAP");
		incomingMessageLog.setInterfaceSystem(interfaceName);
		incomingMessageLog.setMessageType(messageType);
		incomingMessageLog.setSubMessageType(null);
		incomingMessageLog.setCarrierCode(carrierCode);
		incomingMessageLog.setFlightNumber(flightNumber);
		incomingMessageLog.setFlightOriginDate(flightDate);
		incomingMessageLog.setShipmentNumber(shipmentNumber);
		incomingMessageLog.setShipmentDate(shipmentDate);
		incomingMessageLog.setReceivedOn(LocalDateTime.now());
		incomingMessageLog.setMessage(payload);
		incomingMessageLog.setVersionNo(null);
		incomingMessageLog.setSequenceNo(1);
		incomingMessageLog.setMessageContentEndIndicator(null);
		incomingMessageLog.setStatus(status);
		incomingMessageLog.setInterfaceFileName(interfaceFileName);
		incomingMessageLog.setHawbNumber(hawbNumber);
		loggerService.logInterfaceIncomingMessage(incomingMessageLog);
		return incomingMessageLog.getInMessageId();
	}
	@Override
	public void logInterfaceIncomingErrorMessage(BigInteger messageLogId, String errorMessage, String lineItem) {
		IncomingMessageErrorLog incomingMessageErrorLog = new IncomingMessageErrorLog();
		incomingMessageErrorLog.setInMessageId(messageLogId);
		incomingMessageErrorLog.setErrorCode("1");
		incomingMessageErrorLog.setMessage(errorMessage);
		incomingMessageErrorLog.setLineItem(lineItem);
		loggerService.logInterfaceIncomingErrorMessage(incomingMessageErrorLog);
	}
	@Override
	public void updateOutgoingMessageLog(BigInteger messageLogId, String carrierCode, String flightNumber,
			LocalDateTime flightDate, String status) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setOutMessageId(messageLogId);
		outgoingMessage.setCarrierCode(carrierCode);
		outgoingMessage.setFlightNumber(flightNumber);
		outgoingMessage.setFlightOriginDate(flightDate);
		outgoingMessage.setStatus(status);
		outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());

		loggerService.logOutgoingMessage(outgoingMessage);
	}
	@Override
	public String getStringFromLocalDate(LocalDateTime date,String Dataformat) {
	      String sDate = null;
	      final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Dataformat);
	      try {
	         sDate =date!=null? dateFormatter.format(date):sDate;
	      } catch (Exception e) {
	    	  logger.error("Exception while formatting Date to String", e);
	      }
	      return sDate;
	   }
	
	@Override
	public String sendCosysMessageToSAP(String fileName, String message,String MessageType) throws CustomException {
		BigInteger messageLogId = logOutgoingMessage("SAP",
				MessageType,null, null, null,null,
				null,message,"SENT",fileName,null);
		// Invoke ESB Connector
		String status = SENT;
		// Set the http headers
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
		headers.setContentType(MediaType.APPLICATION_XML);

		Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(
				messageLogId, false, "SAP",
				TenantContext.getTenantId(), true, "FTP", fileName);
		payloadHeaders.put(ESBRouterTypeUtils.REQUESTED_MEDIA_TYPE.getName(),MediaType.APPLICATION_XML.toString());

		if (!CollectionUtils.isEmpty(payloadHeaders)) {
			for (Map.Entry<String, String> entry : payloadHeaders.entrySet()) {
				if (Objects.nonNull(entry.getKey())) {
					headers.set(entry.getKey(), entry.getValue());
				}
			}
		}
		try {
			HttpEntity<String> httpEntity = new HttpEntity<>(message.toString(),
					headers);

			RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
			ResponseEntity<Object> response = null;
			response = restTemplate.exchange(esbConnectorService.getESBConnectorSFTPOutURI(),
					HttpMethod.POST, httpEntity, Object.class);
			if (Objects.nonNull(response)) {
				if (logger.isDebugEnabled()) {
					logger.debug(
							"SAP Airline Interface "+MessageType+" Invoking ESB Conector  - Response from ESB Connector:: {} ",
							response.getBody());
				}

				// HTTP 200
				if (!Objects.equals(HttpStatus.OK, response.getStatusCode())
						|| !response.getStatusCode().is2xxSuccessful()) {
					status = ERROR;

				}

			} else {
				status = ERROR;
			}

		} catch (Throwable e) {
			status =ERROR;
		}
		if (!status.equalsIgnoreCase(SENT)) {
			// Update the status of message
			updateOutgoingMessageLog(messageLogId,
					null, null,
					null, status);
		}
		return status;
	}
	
}
