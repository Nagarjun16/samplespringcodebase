/**
 * 
 * WeighingScaleProcessor.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.service.wscale.processor;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.service.stream.processor.logger.ProcessorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.service.wscale.model.WeighingScalePayload;

import io.swagger.annotations.ApiOperation;

/**
 * This class is used for Weighing Scale Processor 
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation(path = "/api/service")
public class WeighingScaleProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(WeighingScaleProcessor.class);
   
   @Autowired
   BeanFactory beanFactory;
   
   @Autowired
   ProcessorLoggerService logger;
   
   @Autowired
   ConnectorPublisher router;
   
   @SuppressWarnings("unchecked")
   @ApiOperation(value = "Process Weighing Scale Payload to get weight of the Input Params")
   @PostRequest(value = "/weighing-scale", method = RequestMethod.POST)
   public BaseResponse<String> processWeighingScale(@RequestBody WeighingScalePayload payload) throws CustomException {
      //
      BaseResponse<String> result = beanFactory.getBean(BaseResponse.class);
      LOGGER.warn("Weighing Scale Payload -- IP Address :: {}, Port :: {}", String.valueOf(payload.getWscaleIP()),
            String.valueOf(payload.getWscalePort()));
      String response = weighingScalePayloadToConnector(payload.getWscaleIP(), payload.getWscalePort());
      result.setData(response);
      LOGGER.warn("Weiging Scale Response -- {}", response);
      //
      return result;
   }
   
   /**
    * @param wscaleIP
    * @param wscalePort
    * @return
    * @throws CustomException 
    */
   @SuppressWarnings("unchecked")
   private String weighingScalePayloadToConnector(String wscaleIP, String wscalePort) throws CustomException {
      //
      String systemName = "WSCALE";
      boolean loggerEnabled = false;
      BigInteger messageId = null;
      Map<String, String> payloadHeaders = null;
      ResponseEntity<Object> response = null;
      String weightResponse = null;
      //
      try {
         // Prepare Message Payload 
         WeighingScalePayload wScalepayload = getWeighingScalePayload(wscaleIP, wscalePort);
         
         if (!loggerEnabled) {
            messageId = logWeighingScalePayload(wScalepayload);
         }
         payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, systemName,
               wScalepayload.getTenantID());
         LOGGER.warn("Weighing Scale Processor - Call initiated :: {} ");
         response = router.sendInterfacePayloadToConnector(wScalepayload, systemName, MediaType.APPLICATION_JSON,
               payloadHeaders);
         String status = Objects.nonNull(response) ? String.valueOf(response.getStatusCode()) : "WSCALE_ERROR";
         LOGGER.warn("Weighing Scale Processor - Response received :: Status : {} ", status);
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
                  messageId = getMessageID(loggerEnabled, messageId, response);
                  String responseMsg = ConnectorUtils.getHttpStatusMessage(response.getStatusCode());
                  logger.updateProcessorMessageLog("FAILED", responseMsg, messageId);
               }
            } else {
               // Success Case
               messageId = getMessageID(loggerEnabled, messageId, response);
               Map<String, String> wscaleResponse = null;
               if (response.getBody() instanceof Map) {
                  wscaleResponse = (Map<String, String>) response.getBody();
                  //
                  String msgStatus = null;
                  if (wscaleResponse.containsKey("status")
                        && Objects.equals("SUCCESS", wscaleResponse.get("status").toUpperCase())) {
                     weightResponse = wscaleResponse.get("weight");
                     /*if (Objects.nonNull(wscaleResponse.get("unit"))) {
                        weightResponse += wscaleResponse.get("unit");
                     }*/
                     msgStatus = "SUCCESS";
                  } else {
                     msgStatus = "ERROR";
                     weightResponse = wscaleResponse.get("weight");
                  }
                  logger.updateProcessorMessageLog(msgStatus, null, messageId);
               }
            }
         }
      } catch (Exception ex) {
         if (LOGGER.isErrorEnabled()) {
            LOGGER.error("Exception occurred while processing Weighing Scale Payload :: {} ", String.valueOf(ex));
         } else {
            LOGGER.warn("Exception occurred while processing Weighing Scale Payload :: {} ", String.valueOf(ex));
         }
         String errorMessage = String.valueOf(ex);
         if (!StringUtils.isEmpty(errorMessage)) {
            if (errorMessage.length() > 1000) {
               errorMessage.substring(0, 990);
            }
         }
         logger.updateProcessorMessageLog("EXCEPTION", errorMessage, messageId);
      }
      return weightResponse;
   }
   
   /**
    * @param wscaleIP
    * @param wscalePort
    * @return
    * @throws CustomException 
    */
   private WeighingScalePayload getWeighingScalePayload(String wscaleIP, String wscalePort) throws CustomException {
      //
      WeighingScalePayload payload = new WeighingScalePayload();
      payload.setWscaleIP(wscaleIP);
      payload.setWscalePort(wscalePort);
      payload.setTenantID(TenantContext.getTenantId());
      //
      return payload;
   }
   
   /**
    * @param payload
    * @return
    * @throws CustomException
    */
   private BigInteger logWeighingScalePayload(WeighingScalePayload payload) throws CustomException {
      return logger.logProcessorEventMessage(payload);
   }
   
   /**
    * @param loggerEnabled
    * @param messageId
    * @param response
    * @return
    */
   private BigInteger getMessageID(boolean loggerEnabled, BigInteger messageId, ResponseEntity<Object> response) {
      //
      if (loggerEnabled) {
         return ConnectorUtils.getMessageID(response);
      }
      return messageId;
   }
   
}
