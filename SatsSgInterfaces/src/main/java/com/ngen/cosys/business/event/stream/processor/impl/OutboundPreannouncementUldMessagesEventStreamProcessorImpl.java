package com.ngen.cosys.business.event.stream.processor.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.payload.InboundULDFinalizedStoreEvent;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssginterfaces.mss.model.RequestPreannouncementUldMessagesModel;
import com.ngen.cosys.satssginterfaces.mss.service.MssService;

@Component("InboundULDFinalized")
public class OutboundPreannouncementUldMessagesEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory
         .getLogger(OutboundPreannouncementUldMessagesEventStreamProcessorImpl.class);

   @Autowired
   private MssService mssService;

   @Autowired
   private ConnectorPublisher router;
   
   @Autowired
   private ConnectorLoggerService logger;
   
   @Autowired
   private ApplicationLoggerService loggerService;

   @SuppressWarnings("unchecked")
   @Override
   public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {
      
      SatsSgInterfacePayload payloaddata = (SatsSgInterfacePayload) payload.getPayload();
      Map<String, Object> data = (Map<String, Object>) payloaddata.getPayload();

      // Trigger business specific service class for message publishing
      try {
    	  InboundULDFinalizedStoreEvent eventdata = new InboundULDFinalizedStoreEvent();
          for (Entry<String, Object> entry : data.entrySet()) {
             if (entry.getKey().equals("flightId")) {
                eventdata.setFlightId(new BigInteger(String.valueOf(entry.getValue())));
             }
          }
         // Select to get Flight Key
         RequestPreannouncementUldMessagesModel model = mssService.selectFlightKey(eventdata.getFlightId());
         Object payloadMessage = mssService.preannouncementUldMessage(model);
         Object jsonPayload = JacksonUtility.convertObjectToJSONString(payloadMessage);
         
         BigInteger messageId = null;
         BigInteger errorMessageId = null;
         boolean loggerEnabled = false;
         boolean errorType = false;
         if (!loggerEnabled) {
            messageId = this.insertOutgoingMessage(jsonPayload, "TGFMCPRE");
         }
         Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled,
               null, TenantContext.getTenantId());
         
         ResponseEntity<Object> response = router.sendJobDataToConnector(jsonPayload, "MSS_TMSG", MediaType.APPLICATION_JSON, payloadHeaders);
         
         if (Objects.nonNull(response)) {
            String exceptionMsg = null;
            if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
               CustomException ex = null;
               if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
                  errorType = true;
                  ex = (CustomException) response.getBody();
                  exceptionMsg = ex.getErrorCode();
                  if ((Map<String, Object>) ex.getPlaceHolders()[1] instanceof Map) {
                     System.out.println("IF PART");
                     Map<String, Object> resultPayload = (Map<String, Object>) ex.getPlaceHolders()[1];
                     if (!loggerEnabled) {
                        // LogINTO outgoing error message table
                        errorMessageId = insertOutgoingErrorMessage(messageId, null, exceptionMsg);
                     } else {
                        messageId = new BigInteger(ConnectorUtils.getObjectValue(ESBRouterTypeUtils.MESSAGE_ID.getName(), resultPayload));
                        errorMessageId = new BigInteger(ConnectorUtils.getObjectValue(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName(), resultPayload));
                     }
                  } else {
                     System.out.println("ELSE PART");
                     System.out.println("type of placeholder value " + ex.getPlaceHolders()[1]);
                  }
               } else {
                  // Partial Success Case
                  if (loggerEnabled) {
                     messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                     if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
                        errorMessageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
                     }
                  } else {
                     errorMessageId = insertOutgoingErrorMessage(messageId, response.getStatusCode(), null);
                     System.out.println("Error message is logged");
                  }
                  System.out.println("Partial Success");
               }
            } else {
               // Success Case
               if (loggerEnabled) {
                  messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                  if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
                     errorMessageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
                  }
               }
               System.out.println("Success");
               //
            }
         }
         
         
         /*Map<String, String> result = (Map<String, String>) object;
         String refeId = null;
         for (Entry<String, String> entry : result.entrySet()) {
            if (entry.getKey().equals("messageId")) {
               refeId = String.valueOf(entry.getValue());
            }
         }
         if (refeId != null) {
            // Update Outgoing message log
            logger.logOutgoingMessage(prepareOutgoingMessageLog(refeId));
            // Update Event table for this event
         }*/
      } catch (CustomException e) {
         if (LOGGER.isErrorEnabled()) {
            LOGGER.error("No payload message for the given evet", e);
         }
      }
   }
   
   private BigInteger insertOutgoingMessage(Object payload , String messageName) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setChannelSent("MQ");
      outgoingMessage.setInterfaceSystem("MSS");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("MSS");
      outgoingMessage.setSubMessageType("TGFMCPRE");
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
      outgoingMessage.setMessage((String) payload);
      outgoingMessage.setStatus("PROCESSED");
      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
      //
      return outgoingMessage.getOutMessageId();
   }
   
   public void updateMessageStatus(BigInteger messageId, boolean errorType) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setOutMessageId(messageId);
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      if (errorType) {
         outgoingMessage.setStatus("FAILURE");
      } else {
         outgoingMessage.setStatus("SENT");
      }
      loggerService.logOutgoingMessage(outgoingMessage);
   }
   
   public BigInteger insertOutgoingErrorMessage(BigInteger messageId, HttpStatus httpStatus,
         String errorMessage) {
      OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();
      //
      outgoingErrorMessage.setOutMessageId(messageId);
      outgoingErrorMessage.setErrorCode("EXCEPTION");
      if (Objects.nonNull(httpStatus)) {
         outgoingErrorMessage.setMessage(ConnectorUtils.getHttpStatusMessage(httpStatus));
      } else {
         if (Objects.nonNull(errorMessage)) {
            outgoingErrorMessage.setMessage(ConnectorUtils.getErrorMessage(errorMessage));
         }
      }
      outgoingErrorMessage.setLineItem(null);
      loggerService.logInterfaceOutgoingErrorMessage(outgoingErrorMessage);
      //
      return outgoingErrorMessage.getOutMessageId();
   }
   
}