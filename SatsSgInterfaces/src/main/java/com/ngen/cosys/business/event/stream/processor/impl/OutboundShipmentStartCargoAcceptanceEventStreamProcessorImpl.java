package com.ngen.cosys.business.event.stream.processor.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
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
import com.ngen.cosys.events.payload.OutboundShipmentStartCargoAcceptanceStoreEvent;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

// AED1 OutGoing Message (Cargo E-Acceptance Message)
@Component("OutboundShipmentStartCargoAcceptance")
public class OutboundShipmentStartCargoAcceptanceEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory
         .getLogger(OutboundShipmentStartCargoAcceptanceEventStreamProcessorImpl.class);

   @Autowired
   ConvertJSONToObject convertJSONToObject;

   @Autowired
   ConnectorPublisher router;

   @Autowired
   OutboundShipmentEAcceptanceService aedService;

   @Autowired
   ApplicationLoggerService loggerService;

   @Autowired
   ConnectorLoggerService connectorLoggerService;

   @Override
   public void process(Message<?> payload) throws IOException {

      LOGGER.info("Payload For Constructing the Message AED1");

      SatsSgInterfacePayload satsSgInterfacePayload = (SatsSgInterfacePayload) payload.getPayload();

      OutboundShipmentStartCargoAcceptanceStoreEvent event = (OutboundShipmentStartCargoAcceptanceStoreEvent) convertJSONToObject
            .convertMapToObject(satsSgInterfacePayload.getPayload(),
                  OutboundShipmentStartCargoAcceptanceStoreEvent.class);

      // Trigger business specific service class for message publishing
		try {
			// Create the payload
			String payloadMessage = aedService.getOutboundShipmentCargoAcceptancePayload(event);

			// If not payload empty then attempt to send
			if (!StringUtils.isEmpty(payloadMessage)) {
				// Default message status
				String status = "SENT";
				// Log the first entry
				BigInteger referenceId = logOutgoingMessage(event, (String) payloadMessage, status);
				// Build the payload
				Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(referenceId, false, "AED", TenantContext.getTenantId());
				// invoke ESB to submit the message to external system
				ResponseEntity<Object> response = null;
				try {
					response = router.sendJobDataToConnector(payloadMessage, "AED_GHAMAWBNO",
							MediaType.APPLICATION_JSON, payloadHeaders);
				} catch (Exception e) {
					status = "ESBERROR";
					logOutgoingMessage(event, referenceId, status);
				}
				// If there are any response errors then log the status as ERROR
				if (Objects.nonNull(response) && Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())
						&& (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException)) {
					status = "ERROR";
				}
				// Update the status of message
				logOutgoingMessage(event, referenceId, status);
			}
		} catch (CustomException e) {
         LOGGER.error("No payload message for the given evet", e);
      }
   }

   private BigInteger logOutgoingMessage(OutboundShipmentStartCargoAcceptanceStoreEvent event, String payload,String status) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("AED");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("AED");
      outgoingMessage.setSubMessageType("AED1");
      outgoingMessage.setShipmentNumber(event.getShipmentNumber());
      outgoingMessage.setShipmentDate(LocalDateTime.of(event.getShipmentDate().getYear(),
            event.getShipmentDate().getMonth(), event.getShipmentDate().getDayOfMonth(), 0, 0));
   // fetching the flight details
   		OutgoingMessageLog flightInfo=null;
   		try {
   			flightInfo = aedService.getFlightDetailsForLogging(outgoingMessage);
   		} catch (CustomException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		if (flightInfo != null) {
   			outgoingMessage.setCarrierCode(flightInfo.getCarrierCode());
   			outgoingMessage.setFlightNumber(flightInfo.getFlightNumber());
   			outgoingMessage.setFlightOriginDate(flightInfo.getFlightOriginDate());
   		} else {
   			outgoingMessage.setCarrierCode(null);
   			outgoingMessage.setFlightNumber(null);
   			outgoingMessage.setFlightOriginDate(null);
   		}
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

   private void logOutgoingMessage(OutboundShipmentStartCargoAcceptanceStoreEvent event, BigInteger referenceId,
         String status) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setOutMessageId(referenceId);
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("AED");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("AED");
      outgoingMessage.setSubMessageType("AED1");
      outgoingMessage.setShipmentNumber(event.getShipmentNumber());
      outgoingMessage.setShipmentDate(LocalDateTime.of(event.getShipmentDate().getYear(),
            event.getShipmentDate().getMonth(), event.getShipmentDate().getDayOfMonth(), 0, 0));
   // fetching the flight details
   		OutgoingMessageLog flightInfo=null;
   		try {
   			flightInfo = aedService.getFlightDetailsForLogging(outgoingMessage);
   		} catch (CustomException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		if (flightInfo != null) {
   			outgoingMessage.setCarrierCode(flightInfo.getCarrierCode());
   			outgoingMessage.setFlightNumber(flightInfo.getFlightNumber());
   			outgoingMessage.setFlightOriginDate(flightInfo.getFlightOriginDate());
   		} else {
   			outgoingMessage.setCarrierCode(null);
   			outgoingMessage.setFlightNumber(null);
   			outgoingMessage.setFlightOriginDate(null);
   		}
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