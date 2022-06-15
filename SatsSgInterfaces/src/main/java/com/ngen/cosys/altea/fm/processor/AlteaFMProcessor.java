/**
 * AlteaFMProcessor.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.processor;

import java.math.BigInteger;
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
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.altea.fm.common.DCSFMConstants;
import com.ngen.cosys.altea.fm.logger.AlteaFMLogger;
import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFiguresReply;
import com.ngen.cosys.altea.fm.util.JacksonUtil;
import com.ngen.cosys.esb.route.ConnectorService;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.service.util.constants.InterfaceSystem;

/**
 * This interface is used for DCS FM Update Cargo Figures 
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
public class AlteaFMProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(AlteaFMProcessor.class);
   
   @Autowired
   ApplicationLoggerService loggerService;
   
   @Autowired
   ConnectorService connectorService;
   
   /**
    * @param dcsfmUpdateCargofigures
    * @param eventSource
    */
   public void processToAlteaFM(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures, String eventSource) {
      LOGGER.debug("AlteaFM Processor initiate API call :: Request Time : {}", LocalDateTime.now());
      ResponseEntity<String> response = null;
      String payload = JacksonUtil.convertObjectToXMLString(dcsfmUpdateCargofigures);
      Map<String, String> payloadHeaders = getRequestHeaders(DCSFMConstants.ALTEAWEB, TenantZone.SIN.getAirportCode());
      BigInteger messageLogId = outgoingMessageLogInsert(dcsfmUpdateCargofigures, payload, eventSource);
      String alteaFMURL = connectorService.getServiceURL(InterfaceSystem.ALTEA_FM);
      EventStatus responseStatus = null;
      try {
         response = routeString(payload, alteaFMURL, MediaType.APPLICATION_XML, payloadHeaders);
         if (Objects.nonNull(response)) {
            if (Objects.nonNull(response.getBody()) && Objects.nonNull(response.getStatusCode())) {
               // Response Received
               if (response.getStatusCode().is2xxSuccessful()) {
                  // Success case
                  DCSFMUpdateCargoFiguresReply alteaFMResponse = (DCSFMUpdateCargoFiguresReply) JacksonUtil
                        .convertXMLStringToObject(response.getBody(), DCSFMUpdateCargoFiguresReply.class);
                  if (Objects.nonNull(alteaFMResponse)) {
                     // Error Response Handle
                     LOGGER.warn("Altea FM Error Response received - Details :: FlightId - {}",
                           alteaFMResponse.getFlightId());
                     // TODO:
                  }
                  responseStatus = EventStatus.PROCESSED;
               } else if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                  LOGGER.debug("Altea FM Response HTTP Status code :: {}", String.valueOf(response.getStatusCodeValue()));
                  responseStatus = EventStatus.FAILED;
               }
            }
            // Outgoing Message log update
            outgoingMessageLogUpdate(dcsfmUpdateCargofigures, messageLogId, responseStatus);
         } else {
            LOGGER.debug("Altea FM Response received with EMPTY Payload : {}");
         }
      } catch (Exception ex) {
         LOGGER.debug("Altea FM Processor exception occurred : {}", ex);
         // Outgoing Message log update
         outgoingMessageLogUpdate(dcsfmUpdateCargofigures, messageLogId, EventStatus.FAILED);
      }
   }
   
   /**
    * @param systemName
    * @param tenantID
    * @return
    */
   private static Map<String, String> getRequestHeaders(String systemName, String tenantID) {
      // Request Headers
      Map<String, String> requestHeaders = new HashMap<>();
      if (!StringUtils.isEmpty(systemName)) {
         requestHeaders.put(DCSFMConstants.INTERFACE_SYSTEM, systemName);
      }
      return requestHeaders;
   }
   
   /**
    * @param payload
    * @param mediaType
    * @param requestHeaders
    * @param security
    * @return
    */
   private static HttpEntity<String> getMessagePayload(String payload, MediaType mediaType,
         Map<String, String> requestHeaders, boolean security) {
      //
      final HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(mediaType));
      headers.setContentType(mediaType);
      //
      if (!CollectionUtils.isEmpty(requestHeaders)) {
         for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
            if (Objects.nonNull(entry.getKey())) {
               headers.set(entry.getKey(), entry.getValue());
            }
         }
      }
      if (security) {
         headers.set(HttpHeaders.AUTHORIZATION, null);
      }
      //
      return new HttpEntity<>(payload, headers);
   }
   
   /**
    * @param payload
    * @param alteaFMURL
    * @param mediaType
    * @param payloadHeaders
    * @return
    */
   private ResponseEntity<String> routeString(String payload, String alteaFMURL, MediaType mediaType,
         Map<String, String> payloadHeaders) {
      //
      ResponseEntity<String> result = null;
      if (Objects.nonNull(payload)) {
         RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
         result = restTemplate.exchange(alteaFMURL, HttpMethod.POST,
               getMessagePayload(payload, mediaType, payloadHeaders, true), String.class);
      }
      return result;
   }
   
   /**
    * @param dcsfmUpdateCargofigures
    * @param payload
    * @param eventSource
    * @return
    */
   private BigInteger outgoingMessageLogInsert(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures, String payload,
         String eventSource) {
      LOGGER.debug("Altea FM Message Log for INSERT :: {}");
      OutgoingMessageLog outgoingMessage = AlteaFMLogger.getOutgoingMessageLog(dcsfmUpdateCargofigures, eventSource,
            LocalDateTime.now());
      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
      return outgoingMessage.getOutMessageId();
   }
   
   /**
    * @param dcsfmUpdateCargofigures
    * @param messageLogId
    * @param responseStatus
    */
   private void outgoingMessageLogUpdate(DCSFMUpdateCargoFigures dcsfmUpdateCargofigures, BigInteger messageLogId,
         EventStatus responseStatus) {
      LOGGER.debug("Altea FM Message Log for UPDATE - MessageLogId :: {}", String.valueOf(messageLogId));
      OutgoingMessageLog outgoingMessage = AlteaFMLogger.getOutgoingMessageLogForUpdate(dcsfmUpdateCargofigures,
            messageLogId, LocalDateTime.now(), responseStatus);
      loggerService.logOutgoingMessage(outgoingMessage);
   }
   
}
