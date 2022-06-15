/**
 * {@link IVRSProcessor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.processor;

import static com.ngen.cosys.events.enums.EventStatus.ERROR;
import static com.ngen.cosys.events.enums.EventStatus.INITIATED;
import static com.ngen.cosys.events.enums.EventStatus.PROCESSED;
import static com.ngen.cosys.events.enums.EventStatus.REJECTED;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.service.ivrs.config.IVRSConfig;
import com.ngen.cosys.service.ivrs.constants.IVRSConstants;
import com.ngen.cosys.service.ivrs.logger.IVRSLogger;
import com.ngen.cosys.service.ivrs.model.IVRSRequest;
import com.ngen.cosys.service.ivrs.model.IVRSResponse;
import com.ngen.cosys.service.ivrs.utils.IVRSUtils;

/**
 * IVRS Processor
 * 
 * @author NIIT Technologies Ltd.
 */
@Component
public class IVRSProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(IVRSProcessor.class);
   
   
   @Autowired
   IVRSConfig ivrsConfig;
   
   @Autowired
   IVRSLogger ivrsLogger;
   
   /**
    * @param payload
    * @param interfaceSystem
    * @param messageType
    * @throws CustomException
    */
   public void process(IVRSRequest payload, String interfaceSystem, String messageType)
         throws CustomException {
      LOGGER.info("IVRS Process the request :: Message Identifier - {}", payload.getMessageSequenceNo());
      String shipmentNumber = payload.getAwbPrefix() + payload.getAwbSuffix();
      String connectorURL = ivrsConfig.getInterfaceURL(interfaceSystem);
      LocalDateTime requestTime = LocalDateTime.now();
      LocalDateTime responseTime = null;
      OutgoingMessageLog outgoingMessage = ivrsLogger.getOutgoingMessageLog(payload, interfaceSystem, messageType,
            requestTime);
      IVRSResponse responseMessage = null;
      try {
         // Log the message
         ivrsLogger.logOutgoingMessage(outgoingMessage);
         String requestPayload = IVRSUtils.convertObjectToJSONString(payload);
         ResponseEntity<String> response = route(requestPayload, connectorURL, interfaceSystem, TenantContext.getTenantId());
         responseTime = LocalDateTime.now();
         if (Objects.nonNull(response)) {
            if (Objects.nonNull(response.getStatusCode())) {
               if (response.getStatusCode().is2xxSuccessful()) {
                  responseMessage = (IVRSResponse) IVRSUtils.convertToIVRSResponse(response.getBody(),
                        IVRSResponse.class);
                  if (Objects.nonNull(responseMessage)) {
                     if (IVRSConstants.SUCCESS_RESPONSE.equalsIgnoreCase(responseMessage.getStatus())
                           || IVRSConstants.SUCCESS_RESPONSE.equalsIgnoreCase(responseMessage.getAckFlg())) {
                        LOGGER.warn("IVRS Processor Success Response Received :: Shipment Number - {}", shipmentNumber);
                        ivrsLogger.logOutgoingMessageDetails(outgoingMessage, responseTime, PROCESSED, INITIATED, null);
                     } else if (IVRSConstants.FAILURE_RESPONSE.equalsIgnoreCase(responseMessage.getStatus())
                           || IVRSConstants.FAILURE_RESPONSE.equalsIgnoreCase(responseMessage.getAckFlg())) {
                        LOGGER.warn(
                              "IVRS Processor Failure Response Received :: Shipment Number - {}, Error Code - {}, Error Message - {}",
                              shipmentNumber, responseMessage.getErrorCode(), responseMessage.getErrorDescription());
                        String errorMessage = IVRSUtils.getFailureErrorMessage(responseMessage.getErrorCode(), 500);
                        ivrsLogger.logOutgoingMessageDetails(outgoingMessage, responseTime, REJECTED, REJECTED, errorMessage);
                     }
                  } else {
                     LOGGER.warn("IVRS Processor 2XX Response Body EMPTY - {}");
                  }
               } else {
                  LOGGER.warn("IVRS Processor NOT 2XX Response Received :: Http Status Code - {}",
                        response.getStatusCode());
                  ivrsLogger.logOutgoingMessageDetails(outgoingMessage, LocalDateTime.now(), ERROR, ERROR,
                        IVRSUtils.getHttpStatusMessage(response.getStatusCode()));
               }
            }
         } else {
            LOGGER.warn("IVRS Processor Response NOT Received :: ShipmentNumber - {}, Message Identifier - {}",
                  shipmentNumber, payload.getMessageSequenceNo());
         }
      } catch (Exception ex) {
         LOGGER.error("IVRS Processor Exception Occurred :: ShipmentNumber - {} Exception - {}", shipmentNumber,
               String.valueOf(ex));
         ivrsLogger.logOutgoingMessageDetails(outgoingMessage, LocalDateTime.now(), ERROR, ERROR, String.valueOf(ex));
      }
   }
   
   /**
    * @param payload
    * @param connectorURL
    * @param interfaceSystem
    * @param tenantID
    * @return
    */
   public ResponseEntity<String> route(String payload, String connectorURL, String interfaceSystem, String tenantID) {
      RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
      ResponseEntity<String> responseEntity = restTemplate.exchange(connectorURL, HttpMethod.POST,
            getMessagePayload(payload, MediaType.APPLICATION_JSON, getPayloadHeaders(interfaceSystem, tenantID, false)),
            String.class);
      return responseEntity;
   }
   
   /**
    * GET Message Payload
    * 
    * @param payload
    * @param mediaType
    * @param payloadHeaders
    * @return
    */
   private HttpEntity<String> getMessagePayload(Object payload, MediaType mediaType,
         Map<String, String> payloadHeaders) {
      final HttpHeaders headers = new HttpHeaders();
      //
      headers.setAccept(Arrays.asList(mediaType));
      headers.setContentType(mediaType);
      if (!CollectionUtils.isEmpty(payloadHeaders)) {
         for (Map.Entry<String, String> entry : payloadHeaders.entrySet())
            if (Objects.nonNull(entry.getKey()))
               headers.set(entry.getKey(), entry.getValue());
      }
      //
      return new HttpEntity<>(payload.toString(), headers);
   }
   
   /**
    * @param interfaceSystem
    * @param tenantID
    * @param loggerEnabled
    * @return
    */
   private Map<String, String> getPayloadHeaders(String interfaceSystem, String tenantID, boolean loggerEnabled) {
      Map<String, String> requestHeaders = new HashMap<>();
      requestHeaders.put(IVRSConstants.LOGGER_ENABLED, String.valueOf(loggerEnabled));
      if (Objects.nonNull(interfaceSystem)) {
         requestHeaders.put(IVRSConstants.INTERFACE_SYSTEM, interfaceSystem);
      }
      if (Objects.nonNull(tenantID)) {
         requestHeaders.put(IVRSConstants.TENANT_ID, tenantID);
      }
      return requestHeaders;
   }
   
}
