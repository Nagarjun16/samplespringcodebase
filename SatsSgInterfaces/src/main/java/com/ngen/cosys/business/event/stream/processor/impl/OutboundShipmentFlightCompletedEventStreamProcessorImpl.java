package com.ngen.cosys.business.event.stream.processor.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
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
import org.springframework.util.StringUtils;

import com.ngen.cosys.aed.service.OutboundShipmentEAcceptanceService;
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.payload.OutboundFlightCompleteStoreEvent;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

// AED6 OutGoing Message (Flight Complete)
@Component("OutboundFlightComplete")
public class OutboundShipmentFlightCompletedEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory
         .getLogger(OutboundShipmentFlightCompletedEventStreamProcessorImpl.class);

   @Autowired
   OutboundShipmentEAcceptanceService aedService;

   @Autowired
   ConnectorPublisher router;

   @Autowired
   ConvertJSONToObject convertJSONToObject;

   @Autowired
   ApplicationLoggerService loggerService;

   @Autowired
   ConnectorLoggerService connectorLoggerService;

   @Override
   public void process(Message<?> payload) throws IOException {

      SatsSgInterfacePayload satsSgInterfacePayload = (SatsSgInterfacePayload) payload.getPayload();

      OutboundFlightCompleteStoreEvent event = (OutboundFlightCompleteStoreEvent) convertJSONToObject
            .convertMapToObject(satsSgInterfacePayload.getPayload(), OutboundFlightCompleteStoreEvent.class);

      // Get the shipment info
      Map<String, Object> flightInfoMap = new HashMap<>();
      String carrierCode = null;
      String flightNumber = null;
      LocalDateTime flightDate = LocalDateTime.now();
      try {
         flightInfoMap = this.aedService.getFlightInfo(event.getFlightId());
         carrierCode = (String) flightInfoMap.get("CarrierCode");
         flightNumber = (String) flightInfoMap.get("FlightNumber");
         Timestamp tempDate = (Timestamp) flightInfoMap.get("FlightDate");
         flightDate=tempDate.toLocalDateTime();
      } catch (CustomException ex) {
         // Do nothing
      }

      // Trigger business specific service class for message publishing
      try {
         // Create the payload
    	 LOGGER.info("AED6 MESSAGE ENTRY POINT :: ",flightNumber);
         String payloadMessage = aedService.getOutboundShipmentFlightCompleted(event);
         // If not payload empty then attempt to send
         if (!StringUtils.isEmpty(payloadMessage)) {
            // Default message status
            String status = "SENT";
            // Log the first entry
            BigInteger referenceId = logOutgoingMessage(carrierCode, flightNumber, flightDate, (String) payloadMessage,status);
            // Build the payload
            Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(referenceId, false, "AED", TenantContext.getTenantId());
            // invoke ESB to submit the message to external system
            ResponseEntity<Object> response =null;
            try {            	
            	response = router.sendJobDataToConnector(payloadMessage, "AED_GHAFLIGHTSCHD",
            			MediaType.APPLICATION_JSON, payloadHeaders);
            } catch (Exception e) {
				status = "ESBERROR";
				logOutgoingMessage(carrierCode, flightNumber, flightDate, referenceId, status);
			}
            // If there are any response errors then log the status as ERROR
            if (Objects.nonNull(response) && Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())
                  && (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException)) {
               status = "ERROR";
            }
            // Update the status of message
            logOutgoingMessage(carrierCode, flightNumber, flightDate, referenceId, status);
         }else {
        	 LOGGER.info("AED6 MESSAGE WITH NO PAYLOAD :: ",flightNumber);
         }

      } catch (CustomException e) {
         LOGGER.error("No payload message for the given event", e);
      }
   }

   private BigInteger logOutgoingMessage(String carrierCode, String flightNumber, LocalDateTime flightDate,
         String payload,String status) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("AED");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("AED");
      outgoingMessage.setSubMessageType("AED6");
      outgoingMessage.setCarrierCode(carrierCode);
      outgoingMessage.setFlightNumber(flightNumber);
      outgoingMessage.setFlightOriginDate(flightDate);
      outgoingMessage.setShipmentNumber(null);
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus(status);
      outgoingMessage.setMessage(payload);
      loggerService.logInterfaceOutgoingMessage(outgoingMessage);
      return outgoingMessage.getOutMessageId();
   }

   private void logOutgoingMessage(String carrierCode, String flightNumber, LocalDateTime flightDate,
         BigInteger referenceId, String status) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setOutMessageId(referenceId);
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("AED");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("AED");
      outgoingMessage.setSubMessageType("AED6");
      outgoingMessage.setCarrierCode(carrierCode);
      outgoingMessage.setFlightNumber(flightNumber);
      outgoingMessage.setFlightOriginDate(flightDate);
      outgoingMessage.setShipmentNumber(null);
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus(status);
      connectorLoggerService.logOutgoingMessage(outgoingMessage);
   }

}