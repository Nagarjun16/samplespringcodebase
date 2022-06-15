package com.ngen.cosys.ics.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.payload.InboundShipmentPickOrderRequestStoreEvent;
import com.ngen.cosys.events.producer.InboundShipmentPickOrderRequestStoreEventProducer;

@NgenCosysAppInfraAnnotation(path = "/api/ics")
public class RetrieveULDAtRequestedLaneController {

   @Autowired
   private InboundShipmentPickOrderRequestStoreEventProducer producer;

   @PostMapping(value = "/fetch-uld-at-requested-lane", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void fetchULDAtRequestedLane() {
      InboundShipmentPickOrderRequestStoreEvent event = new InboundShipmentPickOrderRequestStoreEvent();
      event.setEventInboundShipmentPickOrderRequestStoreId(new BigInteger("3"));
      event.setPoNumber("PONO180001");
      event.setShipmentId(new BigInteger("771"));
      event.setUldNumber("AKE100011SQ");
      event.setOutputExitLocation("ICS001");
      event.setTruckDockLane("TD1");
      event.setCustomerCode("DHL");
      event.setRequestedAt(LocalDateTime.now());
      event.setRequestedBy("REQ");
      event.setStatus(EventStatus.NEW.getStatus());
      event.setCreatedBy("SYSADMIN");
      event.setCreatedOn(LocalDateTime.now());
      event.setLastModifiedBy("NGCDMR");
      event.setLastModifiedOn(LocalDateTime.now());
      producer.publish(event);
   }
}