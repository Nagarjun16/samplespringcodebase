package com.ngen.cosys.events.stream.processor.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.events.enums.EventStreamHeaders;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.validator.enums.ShipmentType;


@Component(EventTypes.Names.INBOUND_SHIPMENT_BREAK_DOWN_COMPLETE_EVENT)
public class InboundShipmentBreakDownCompleteEventStreamProcessor implements BaseBusinessEventStreamProcessor {

   @Autowired
   ConvertJSONToObject convertJSONToObject;

   @Override
   public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {

      // Get the payload
      SatsSgInterfacePayload satsSgInterfacePayload = (SatsSgInterfacePayload) payload.getPayload();

      // Get the specific event
      
      InboundShipmentBreakDownCompleteStoreEvent event = (InboundShipmentBreakDownCompleteStoreEvent) convertJSONToObject
            .convertMapToObject(satsSgInterfacePayload.getPayload(), InboundShipmentBreakDownCompleteStoreEvent.class);
      //event.getFlightId()
      //event.getFlightId()
      //(String) payload.getHeaders().get(EventStreamHeaders.Names.TENANT_ID)
      

   }

}
