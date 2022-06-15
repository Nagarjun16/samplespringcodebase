package com.ngen.cosys.ics.processor.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.ics.service.ICSPreannouncementService;

@Component("OutboundPreannouncement")
public class ICSOutboundPreannouncementUldMessagesEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(ICSOutboundPreannouncementUldMessagesEventStreamProcessorImpl.class);

   @Autowired
   private ICSPreannouncementService icsPreannouncementService;

   @Autowired
   private ConnectorPublisher router;

   @Autowired
   private ConnectorLoggerService logger;

   @Override
   public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {

      SatsSgInterfacePayload payloaddata = (SatsSgInterfacePayload) payload.getPayload();
      @SuppressWarnings("unchecked")
      Map<String, Object> data = (Map<String, Object>) payloaddata.getPayload();

      for (Entry<String, Object> entry : data.entrySet()) {
         System.out.println(entry.getKey() + " :: " + entry.getValue());
      }

      // Trigger business specific service class for message publishing
      /* try {
        InboundULDFinalizedStoreEvent eventdata = new InboundULDFinalizedStoreEvent();
          for (Entry<String, Object> entry : data.entrySet()) {
             if (entry.getKey().equals("flightId")) {
                eventdata.setFlightId(new BigInteger(String.valueOf(entry.getValue())));
             }
          }
         // Select to get Flight Key
         RequestPreannouncementUldMessagesModel model = icsPreannouncementService.selectFlightKey(eventdata.getFlightId());
         Object payloadMessage = icsPreannouncementService.preannouncementUldMessage(model);
         Object jsonPayload = JacksonUtility.convertObjectToJSONString(payloadMessage);
         Object object = router.sendJobDataToConnector(jsonPayload, "MSS_TMSG");
      
         Map<String, String> result = (Map<String, String>) object;
         String refeId = null;
         for (Entry<String, String> entry : result.entrySet()) {
            if (entry.getKey().equals("referenceId")) {
               refeId = String.valueOf(entry.getValue());
            }
         }
         if (refeId != null) {
            // Update Outgoing message log
            logger.logOutgoingMessage(prepareOutgoingMessageLog(refeId));
            // Update Event table for this event
         }
      } catch (CustomException e) {
         if (LOGGER.isErrorEnabled()) {
            LOGGER.error("No payload message for the given evet", e);
         }
      }*/
   }

   private OutgoingMessageLog prepareOutgoingMessageLog(String referenceId) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setOutMessageId(BigInteger.valueOf(Integer.parseInt(referenceId)));
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("MQ");
      outgoingMessage.setSenderOriginAddress("MSS");
      outgoingMessage.setMessageType("TGFMCPRE");
      outgoingMessage.setSubMessageType("TMS");
      outgoingMessage.setCarrierCode(null);
      outgoingMessage.setFlightNumber(null);
      outgoingMessage.setFlightOriginDate(null);
      outgoingMessage.setShipmentNumber(null);
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(null);
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus("SENT");
      return outgoingMessage;
   }
}