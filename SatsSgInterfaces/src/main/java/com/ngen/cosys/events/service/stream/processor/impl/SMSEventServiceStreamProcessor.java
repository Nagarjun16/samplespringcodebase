/**
 * 
 * SMSEventServiceStreamProcessor.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          02 JUL, 2017   NIIT      -
 */
package com.ngen.cosys.events.service.stream.processor.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

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
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.SMSEvent;
import com.ngen.cosys.events.service.stream.processor.logger.ProcessorLoggerService;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

/**
 * This SMS Service Event class used for Send SMS Payload data to ESB Connector
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component(EventTypes.Names.SMS_EVENT)
public class SMSEventServiceStreamProcessor implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(SMSEventServiceStreamProcessor.class);
   
   @Autowired
   ConvertJSONToObject convertJSONToObject;
   
   @Autowired
   ConnectorPublisher router;
   
   @Autowired
   ProcessorLoggerService logger;
   
   /**
    * @see com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor#process(org.springframework.messaging.Message)
    * 
    */
   @Override
   public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {
      //
      LOGGER.debug("SMS Event Stream Processor :: Payload Received from SCS {}, Headers Information : {} ",
            payload.getPayload(), payload.getHeaders());
      SatsSgInterfacePayload payloadObject = (SatsSgInterfacePayload) payload.getPayload();
      // Convert that payload to business payload
      SMSEvent smsEvent = (SMSEvent) convertJSONToObject.convertMapToObject(payloadObject.getPayload(), SMSEvent.class);
      Object xmlPayload = JacksonUtility.convertObjectToXMLString(smsEvent);
      //smsEvent = (SMSEvent) JacksonUtility.convertXMLStringToObject(xmlString, SMSEvent.class);
      //
      String systemName = "SMS";
      boolean loggerEnabled = false;
      BigInteger messageId = null;
      Map<String, String> payloadHeaders = null;
      ResponseEntity<Object> response = null;
      try {
         if (!loggerEnabled) {
            // Insert SMS logger Enabled service with SMS Payload
            messageId = logger.logProcessorEventMessage(smsEvent);
         }
         payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, systemName, TenantContext.getTenantId());
         response = router.sendInterfacePayloadToConnector(xmlPayload, systemName, MediaType.APPLICATION_XML, payloadHeaders);
         //
         if (Objects.nonNull(response)) {
            String exceptionMsg = null;
            if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
               CustomException ex = null;
               if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
                  ex = (CustomException) response.getBody();
                  exceptionMsg = ConnectorUtils.getErrorMessage(ex.getErrorCode());
                  messageId = (BigInteger) ex.getPlaceHolders()[1];
                  
                  if (!loggerEnabled) {
                     logger.updateProcessorMessageLog("EXCEPTION", exceptionMsg, messageId);
                  }
               } else {
                  // Partial Success Case
                  String responseMsg = ConnectorUtils.getHttpStatusMessage(response.getStatusCode());
                  logger.updateProcessorMessageLog("FAILED", responseMsg, messageId);
               }
            } else {
               // Success Case
               logger.updateProcessorMessageLog("SENT", null, messageId);
            }
         }
      } catch (Exception ex) {
         if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Exception occurred while processing SMS Payload :: {} ", String.valueOf(ex));
         } else {
            LOGGER.debug("Exception occurred while processing SMS Payload :: {} ", String.valueOf(ex));
         }
      }
   }

}
