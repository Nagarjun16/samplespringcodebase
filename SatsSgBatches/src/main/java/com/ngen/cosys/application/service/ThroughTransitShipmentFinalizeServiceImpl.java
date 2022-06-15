/**
 * Implementation component which has business methods to process through
 * transit shipments finalized
 */
package com.ngen.cosys.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.application.dao.ThroughTransitShipmentFinalizeDAO;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.DataSyncStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.events.producer.DataSyncStoreEventProducer;
import com.ngen.cosys.events.producer.InboundShipmentBreakDownCompleteStoreEventProducer;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class ThroughTransitShipmentFinalizeServiceImpl implements ThroughTransitShipmentFinalizeService {

   @Autowired
   private ThroughTransitShipmentFinalizeDAO dao;

   @Autowired
   private InboundShipmentBreakDownCompleteStoreEventProducer producer;

   @Autowired
   private DataSyncStoreEventProducer dataEventProducer;

   @Override
   public void process() throws CustomException {

      // 1. Get the list of shipments for which TTWL has been finalized and loaded
      List<InboundShipmentBreakDownCompleteStoreEvent> shipments = this.dao.get();

      // 2. Fire event for each shipment
      if (!CollectionUtils.isEmpty(shipments)) {
         for (InboundShipmentBreakDownCompleteStoreEvent event : shipments) {
            event.setCreatedBy("THROUGH TRANSIT BATCH JOB");
            event.setCreatedOn(LocalDateTime.now());
            event.setCompletedBy("THROUGH TRANSIT BATCH JOB");
            event.setCompletedAt(LocalDateTime.now());
            event.setFunction("Through Transit Shipment Finalize Job");
            event.setEventName(EventTypes.Names.INBOUND_SHIPMENT_BREAK_DOWN_COMPLETE_EVENT);

            this.producer.publish(event);

            // Raise an event for data synch..
            if (this.dao.isDataSyncCREnabled() && this.dao.isFlighHandledInSystem(event)) {

               // Get the part suffix of shipments
               List<String> partSuffixes = this.dao.getDataSyncPartSuffix(event.getShipmentId());

               if (!CollectionUtils.isEmpty(partSuffixes)) {
                  for (String partSuffix : partSuffixes) {
                     DataSyncStoreEvent eventPayload = new DataSyncStoreEvent();
                     eventPayload.setShipmentId(event.getShipmentId());
                     eventPayload.setStatus(EventStatus.NEW.getStatus());
                     eventPayload.setConfirmedAt(LocalDateTime.now());
                     eventPayload.setConfirmedBy("BATCH");
                     eventPayload.setCreatedBy("BATCH");
                     eventPayload.setCreatedOn(LocalDateTime.now());
                     eventPayload.setFunction("Data Sync Job");
                     eventPayload.setEventName(EventTypes.Names.DATA_SYNC_EVENT);
                     eventPayload.setPartSuffix(partSuffix);
                     eventPayload.setFlightId(event.getFlightId());
                     dataEventProducer.publish(eventPayload);
                  }
               }
            }
         }
      }
   }

}