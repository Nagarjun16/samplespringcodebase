package com.ngen.cosys.ics.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.esb.route.ICSEndPointURIGenerator;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.esb.connector.router.ESBConnectorService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ICSUpdateOperativeFlightRequestModel;
import com.ngen.cosys.ics.model.ICSUpdateOperativeFlightResponseModel;
import com.ngen.cosys.ics.service.ICSUpdateOperativeFlightService;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;


@NgenCosysAppInfraAnnotation(path = "/api/ics")
public class ICSUpdateOperativeFlightController {
	
	 @Autowired
	   private ESBConnectorService connerctorService;

	   @Autowired
	   private ConnectorLoggerService logger;

	   @Autowired
	   private ApplicationLoggerService loggerService;

	   @Autowired
	   private ICSEndPointURIGenerator urlGenerator;

	   @Autowired
	   private Validator validator;
	   
	   @Autowired
	   private ICSUpdateOperativeFlightService icsUpdateOperativeFlightService;
	   
	   private ICSUpdateOperativeFlightRequestModel operativeFlightDetails;
	   
	   private static final Logger LOGGER = LoggerFactory.getLogger(FetchICSLocationController.class);
	   
	   
	   @PostMapping(value = "/perform-batch-operation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public ResponseEntity<ICSUpdateOperativeFlightResponseModel> performBatchOperation() {

		      final String endPointURL = urlGenerator.getICSConnectorURL("operative-flight-details");
		      ICSUpdateOperativeFlightResponseModel responseModel = null;
		      BigInteger referenceId = null;
		      ResponseEntity<Object> response = null;
		      try {
		         operativeFlightDetails = icsUpdateOperativeFlightService.getUpdatedOperativeFlightDetails();
		         Object xmlPayload = JacksonUtility.convertObjectToXMLString(operativeFlightDetails);
		         LOGGER.info(xmlPayload.toString());
		         boolean loggerEnabled = true;
		         BigInteger messageId = null;
		         BigInteger errorMessageId = null;
		         if (!loggerEnabled) {
		      //      messageId = logOutgoingMessage((String) xmlPayload);
		         }
		         Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, "ICS", TenantContext.getTenantId());
		         response = connerctorService.route(xmlPayload, endPointURL, MediaType.APPLICATION_XML, payloadHeaders);

		         if (Objects.nonNull(response)) {
		            String exceptionMsg = null;
		            if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
		               CustomException ex = null;
		               if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
		                  ex = (CustomException) response.getBody();
		                  exceptionMsg = ex.getErrorCode();
		                  messageId = (BigInteger) ex.getPlaceHolders()[1];
		                  if (Objects.nonNull(ex.getPlaceHolders()[2])) {
		                     errorMessageId = (BigInteger) ex.getPlaceHolders()[2];
		                  }
		                  if (!loggerEnabled) {
		                     // LogINTO outgoing error message table

		                  }
		                  //
		                  responseModel = new ICSUpdateOperativeFlightResponseModel();
		                  responseModel.setStatus("FAILURE");
		               } else {
		                  // Partial Success Case
		                  if (loggerEnabled) {
		                     messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
		                     if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
		                        errorMessageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
		                     }
		                  }
		                  responseModel = new ICSUpdateOperativeFlightResponseModel();
		                  responseModel.setStatus("FAILURE PARTIAL");
		               }
		            } else {
		               // Success Case
		               if (loggerEnabled) {
		                  messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
		                  if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
		                     errorMessageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
		                  }
		               }
		               responseModel = new ICSUpdateOperativeFlightResponseModel();
		               responseModel.setStatus("SUCCESS");
		            }
		         }

		         logOutgoingMessage(referenceId, "SENT");
		      } catch (CustomException e) {
		         // Log Outgoing message
		         if (referenceId != null) {
		            logOutgoingMessage(referenceId, "EXCEPTION");
		         }
		      }
		      return new ResponseEntity<>(responseModel, HttpStatus.OK);
		   }

	   private void logOutgoingMessage(BigInteger referenceId, String status) {
		      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		      outgoingMessage.setOutMessageId(referenceId);
		      outgoingMessage.setChannelSent("HTTP");
		      outgoingMessage.setInterfaceSystem("ICS");
		      outgoingMessage.setSenderOriginAddress("COSYS");
		      outgoingMessage.setMessageType("OPERATION");
		      outgoingMessage.setSubMessageType("XML");
		      outgoingMessage.setCarrierCode(null);
		      outgoingMessage.setFlightNumber(null);
		      outgoingMessage.setFlightOriginDate(null);
		      outgoingMessage.setShipmentNumber(null);
		      outgoingMessage.setShipmentDate(null);
		      outgoingMessage.setRequestedOn(LocalDateTime.now());
		      outgoingMessage.setSentOn(LocalDateTime.now());
		      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
		      outgoingMessage.setVersionNo(1);
		      outgoingMessage.setSequenceNo(1);
		      outgoingMessage.setMessageContentEndIndicator(null);
		      outgoingMessage.setStatus(status);
		      logger.logOutgoingMessage(outgoingMessage);
		   }

		   private BigInteger logOutgoingMessage(String payload) {
		      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		      outgoingMessage.setChannelSent("HTTP");
		      outgoingMessage.setInterfaceSystem("ICS");
		      outgoingMessage.setSenderOriginAddress("COSYS");
		      outgoingMessage.setMessageType("OPERATION");
		      outgoingMessage.setSubMessageType("XML");
		      outgoingMessage.setCarrierCode(null);
		      outgoingMessage.setFlightNumber(null);
		      outgoingMessage.setFlightOriginDate(null);
		      outgoingMessage.setShipmentNumber(null);
		      outgoingMessage.setShipmentDate(null);
		      outgoingMessage.setRequestedOn(LocalDateTime.now());
		      outgoingMessage.setSentOn(LocalDateTime.now());
		      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
		      outgoingMessage.setVersionNo(1);
		      outgoingMessage.setSequenceNo(1);
		      outgoingMessage.setMessageContentEndIndicator(null);
		      outgoingMessage.setStatus("PROCESSED");
		      outgoingMessage.setMessage(payload);
		      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
		      return outgoingMessage.getOutMessageId();
		   }


}
