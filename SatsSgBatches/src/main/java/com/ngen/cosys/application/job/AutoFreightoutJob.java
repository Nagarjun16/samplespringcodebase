package com.ngen.cosys.application.job;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.model.AutoFreightoutInventoryModel;
import com.ngen.cosys.application.model.AutoFreightoutModel;
import com.ngen.cosys.application.service.AutoFreightoutService;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.InboundShipmentDeliveredStoreEvent;
import com.ngen.cosys.events.producer.InboundShipmentDeliveredStoreEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class AutoFreightoutJob extends AbstractCronJob {

   private static final Logger LOG = LoggerFactory.getLogger(AutoFreightoutJob.class);

   @Autowired
   private AutoFreightoutService service;

   @Autowired
   InboundShipmentDeliveredStoreEventProducer producer;

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         // Get the list of shipments
         List<AutoFreightoutModel> shipments = this.service.getShipments();

         for (AutoFreightoutModel freightoutModel : shipments) {

            List<AutoFreightoutInventoryModel> inventoryList = this.service.getInventoryDetails(freightoutModel);

            for (AutoFreightoutInventoryModel inventoryModel : inventoryList) {
               inventoryModel.setDeliveryId(freightoutModel.getDeliveryId());
               this.service.freightoutToInventory(inventoryModel);
            }
            
            this.service.updateInboundWorksheetShipmentStatus(freightoutModel);
            
            InboundShipmentDeliveredStoreEvent event = new InboundShipmentDeliveredStoreEvent();
            event.setCreatedBy("BATCH USER");
            event.setCreatedOn(LocalDateTime.now());
            event.setDeliveredAt(LocalDateTime.now());
            event.setDeliveredBy("BATCH USER");
            event.setDoNumber(freightoutModel.getDeliveryOrderNo());
            event.setStatus(EventStatus.NEW.getStatus());
            event.setShipmentNumber(freightoutModel.getShipmentNumber());
            event.setShipmentDate(freightoutModel.getShipmentDate());
            event.setShipmentId(freightoutModel.getShipmentId());
            event.setShipmentType(freightoutModel.getShipmentType());
            event.setCarrier(freightoutModel.getCarrierCode());
            event.setFunction("Issue DO");
            event.setEventName(EventTypes.Names.INBOUND_SHIPMENT_DELIVERED_EVENT);

            this.producer.publish(event);

         }

      } catch (CustomException ex) {
         LOG.error("Unable to  Autofreightout for this shipment", ex);
      }
   }

}
