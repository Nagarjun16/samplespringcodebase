package com.ngen.cosys.ics.processor.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.events.esb.connector.payload.MessagePayload;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ICSULDFlightAssignmentResponsePayload;
import com.ngen.cosys.ics.service.ICSULDFlightAssignmentService;

@Component("OutboundULDAssignedToFlight")
public class OutboundULDAssignedToFlightEventEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor {

   @Autowired
   ConnectorPublisher router;

   @Autowired
   ICSULDFlightAssignmentService icsService;

   @Override
   public void process(Message<?> payload) {
      SatsSgInterfacePayload payloadData = (SatsSgInterfacePayload) payload.getPayload();

      @SuppressWarnings("unchecked")
      Map<String, Object> data = (Map<String, Object>) payloadData.getPayload();
      MessagePayload messagePayload = new MessagePayload();
      try {
         ICSULDFlightAssignmentResponsePayload prepareMessage = icsService.prepareMessage(data);
         messagePayload.setPayload(prepareMessage);
         messagePayload.setQname("queuename");
      } catch (CustomException e) {
         e.printStackTrace();
      }
      router.sendJobDataToConnector(messagePayload, "AED_GHAFLIGHTSCHD", null, null);

   }

}