/**
 * 
 * FAXEventServiceStreamProcessor.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          02 JUL, 2017   NIIT      -
 */
package com.ngen.cosys.events.service.stream.processor.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.FAXEvent;
import com.ngen.cosys.events.payload.FAXEvent.AWBDetail;
import com.ngen.cosys.events.service.stream.processor.logger.ProcessorLoggerService;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

/**
 * This Fax Service Event class used for Send FAX Payload data to ESB Connector
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component(EventTypes.Names.FAX_EVENT)
public class FAXEventServiceStreamProcessor implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(FAXEventServiceStreamProcessor.class);
   
   @Autowired
   private ConvertJSONToObject convertJSONToObject;
   
   @Autowired
   private ConnectorPublisher router;
   
   @Autowired
   private ProcessorLoggerService logger;
   
   /**
    * @see com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor#process(org.springframework.messaging.Message)
    * 
    */
   @Override
   public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {
      //
      LOGGER.debug("FAX Event Stream Processor :: Payload Received from SCS :: {}, Headers Information : {} ",
            payload.getPayload(), payload.getHeaders());
      
      SatsSgInterfacePayload payloadObject = (SatsSgInterfacePayload) payload.getPayload();
      // Convert that payload to business payload
      //FAXEvent faxEvent = (FAXEvent) convertJSONToObject.convertMapToObject(payloadObject.getPayload(), FAXEvent.class);
      //
      FAXEvent faxEvent = (FAXEvent) getEventPayload(payloadObject);
      String jsonPayload = (String) JacksonUtility.convertObjectToJSONString(payloadObject);
      String systemName = "FAX";
      boolean loggerEnabled = false;
      BigInteger messageId = null;
      Map<String, String> payloadHeaders = null;
      ResponseEntity<Object> response = null;
      try {
         if (!loggerEnabled) {
            // Insert FAX logger Enabled service with FAX Payload
            messageId = logger.logProcessorEventMessage(faxEvent);
         }
         payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, systemName, TenantContext.getTenantId());
         response = router.sendInterfacePayloadToConnector(jsonPayload, systemName, MediaType.APPLICATION_JSON,
               payloadHeaders);
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
            LOGGER.error("Exception occurred while processing FAX Payload :: {} ", String.valueOf(ex));
         } else {
            LOGGER.debug("Exception occurred while processing FAX Payload :: {} ", String.valueOf(ex));
         }
      }
   }
   
   /**
    * @param payload
    * @return
    */
   @SuppressWarnings("unchecked")
   private FAXEvent getEventPayload(SatsSgInterfacePayload payload) {
      //
      FAXEvent faxEvent = null;
      if (Objects.nonNull(payload) && payload.getPayload() instanceof Map) {
         //
         faxEvent = new FAXEvent();
         Map<String, Object> mapObject = (Map<String, Object>) payload.getPayload();
         for (Iterator<Entry<String, Object>> iterator = mapObject.entrySet().iterator(); iterator.hasNext();) {
            //
            Map.Entry<String, Object> entry = iterator.next();
            if (Objects.isNull(faxEvent.getMessageSequenceNo())) {
               Integer sequenceNo = (Integer) getValue(entry, "msgSeqNo");
               faxEvent.setMessageSequenceNo(Objects.isNull(sequenceNo) ? null : BigInteger.valueOf(sequenceNo));
            }
            if (Objects.isNull(faxEvent.getDateTime())) {
               String dateTimeString = (String) getValue(entry, "datetime");
               LocalDateTime dateTime = Objects.isNull(dateTimeString) ? null : LocalDateTime.parse(dateTimeString);
               faxEvent.setDateTime(Objects.isNull(dateTime) ? null : dateTime);
            }
            if (Objects.isNull(faxEvent.getAwbPrefix())) {
               faxEvent.setAwbPrefix((String) getValue(entry, "awbPfx"));
            }
            if (Objects.isNull(faxEvent.getAwbNumber())) {
               faxEvent.setAwbNumber((String) getValue(entry, "awbNum"));
            }
            if (Objects.isNull(faxEvent.getTotalPieces())) {
               Integer pieces = (Integer) getValue(entry, "totPieces");
               faxEvent.setTotalPieces(Objects.isNull(pieces) ? null : BigInteger.valueOf(pieces));
            }
            if (Objects.isNull(faxEvent.getTotalWeight())) {
               Double weight = (Double) getValue(entry, "totWeight");
               faxEvent.setTotalWeight(Objects.isNull(weight) ? null : BigDecimal.valueOf(weight));
            }
            if (Objects.isNull(faxEvent.getOrigin())) {
               faxEvent.setOrigin((String) getValue(entry, "origin"));
            }
            if (Objects.isNull(faxEvent.getDestination())) {
               faxEvent.setDestination((String) getValue(entry, "destination"));
            }
            if (Objects.isNull(faxEvent.getConsigneeName())) {
               faxEvent.setConsigneeName((String) getValue(entry, "consigneeName"));
            }
            if (Objects.isNull(faxEvent.getContactNo())) {
               faxEvent.setContactNo((String) getValue(entry, "contactNo"));
            }
            if (CollectionUtils.isEmpty(faxEvent.getAwbDetails())
                  && Objects.equals(entry.getKey(), "awbDetails") && Objects.nonNull(entry.getValue())) {
               faxEvent.setAwbDetails((List<AWBDetail>) getAwbDetails(entry));
            }
         }
      }
      return faxEvent;
   }
   
   /**
    * @param entry
    * @return
    */
   private Object getValue(Map.Entry<String, Object> entry, String fieldName) {
      if (Objects.equals(entry.getKey(), fieldName) && Objects.nonNull(entry.getValue())) {
         return entry.getValue();
      }
      return null;
   }
   
   /**
    * @param entry
    * @return
    */
   @SuppressWarnings("unchecked")
   private Object getAwbDetails(Map.Entry<String, Object> entry) {
      //
      List<AWBDetail> awbDetails = new ArrayList<>();
      FAXEvent faxEvent = new FAXEvent();
      AWBDetail awbDetail = null;
      //
      for (Object element : (ArrayList<?>) entry.getValue()) {
         awbDetail = faxEvent.new AWBDetail();
         for (Map.Entry<String, Object> mapEntry : ((Map<String, Object>) element).entrySet()) {
            //
            if (Objects.isNull(awbDetail.getFlightCarrier())) {
               awbDetail.setFlightCarrier((String) getValue(mapEntry, "flightCar"));
            }
            if (Objects.isNull(awbDetail.getFlightNumber())) {
               awbDetail.setFlightNumber((String) getValue(mapEntry, "flightNum"));
            }
            if (Objects.isNull(awbDetail.getFlightDate())) {
               String dateString = (String) getValue(entry, "flightDate");
               LocalDate date = Objects.isNull(dateString) ? null : LocalDate.parse(dateString);
               awbDetail.setFlightDate(Objects.isNull(date) ? null : date);
            }
            if (Objects.isNull(awbDetail.getPieces())) {
               Integer pieces = (Integer) getValue(entry, "pieces");
               awbDetail.setPieces(Objects.isNull(pieces) ? null : BigInteger.valueOf(pieces));
            }
            if (Objects.isNull(awbDetail.getWeight())) {
               Integer weight = (Integer) getValue(entry, "weight");
               awbDetail.setWeight(Objects.isNull(weight) ? null : BigDecimal.valueOf(weight));
            }
            if (Objects.isNull(awbDetail.getFsuStatus())) {
               awbDetail.setFsuStatus((String) getValue(mapEntry, "fsuStatus"));
            }
            if (Objects.isNull(awbDetail.getPdfContent())) {
               awbDetail.setPdfContent((String) getValue(mapEntry, "pdfContent"));
            }
         }
         awbDetails.add(awbDetail);
      }
      return awbDetails;
   }
}