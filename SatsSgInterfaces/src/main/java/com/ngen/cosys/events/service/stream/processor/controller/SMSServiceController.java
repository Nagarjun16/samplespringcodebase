package com.ngen.cosys.events.service.stream.processor.controller;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.payload.SMSEvent;
import com.ngen.cosys.events.service.stream.processor.logger.ProcessorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "api/sms")
public class SMSServiceController {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(SMSServiceController.class);

   @Autowired
   ConnectorPublisher router;
   
   @Autowired
   ProcessorLoggerService logger;
   
   @ApiOperation("Send SMS")
   @PostRequest(value = "/service/send-sms", method = RequestMethod.POST)
   public ResponseEntity<String> sendEmail(@RequestBody SMSEvent smsEvent) throws CustomException {
      //
      String systemName = "SMS";
      boolean loggerEnabled = false;
      BigInteger messageId = null;
      Map<String, String> payloadHeaders = null;
      ResponseEntity<Object> response = null;
      String responseHolder = null;
      //
      try {
         if (!loggerEnabled) {
            // Insert SMS logger Enabled service with SMS Payload
            messageId = logger.logProcessorEventMessage(smsEvent);
         }
         Object xmlPayload = JacksonUtility.convertObjectToXMLString(smsEvent);
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
                  //
                  responseHolder = "EXCEPTION";
                  if (!loggerEnabled) {
                     logger.updateProcessorMessageLog(responseHolder, exceptionMsg, messageId);
                  }
               } else {
                  // Partial Success Case
                  responseHolder = "FAILED";
                  String responseMsg = ConnectorUtils.getHttpStatusMessage(response.getStatusCode());
                  logger.updateProcessorMessageLog(responseHolder, responseMsg, messageId);
               }
            } else {
               // Success Case
               responseHolder = "SENT";
               logger.updateProcessorMessageLog(responseHolder, null, messageId);
            }
         }
      } catch (Exception ex) {
         if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Exception occurred while processing SMS Payload :: {} ", String.valueOf(ex));
         } else {
            LOGGER.debug("Exception occurred while processing SMS Payload :: {} ", String.valueOf(ex));
         }
      }
      return new ResponseEntity<>(responseHolder, HttpStatus.OK);
   }
   
}
