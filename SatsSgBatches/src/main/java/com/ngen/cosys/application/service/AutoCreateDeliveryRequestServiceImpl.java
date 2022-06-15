/**
 * This is a service implementation class for creating delivery request based on
 * shipment which has a EDelivery Request
 */
package com.ngen.cosys.application.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.dao.AutoCreateDeliveryRequestDAO;
import com.ngen.cosys.events.payload.CreateDeliveryRequestStoreEvent;
import com.ngen.cosys.events.producer.CreateDeliveryRequestStoreEventProducer;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class AutoCreateDeliveryRequestServiceImpl implements AutoCreateDeliveryRequestService {

   @Autowired
   private AutoCreateDeliveryRequestDAO dao;

   @Autowired
   private CreateDeliveryRequestStoreEventProducer producer;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.service.AutoCreateDeliveryRequestService#
    * scheduleDeliveryRequests()
    */
   @Override
   public void scheduleDeliveryRequests() throws CustomException {

      // 1. Get the list of shipments which requires delivery request
      List<CreateDeliveryRequestStoreEvent> requests = this.dao.scheduleDeliveryRequests();
      
      

      // 2. Raise the event for delivery request
      for (CreateDeliveryRequestStoreEvent event : requests) { 
         event.setRequestedFrom("WEBPORTAL");
         event.setCreatedBy("BATCHJOB");
         event.setCreatedOn(LocalDateTime.now());
         event.setLastModifiedBy("BATCHJOB");
         event.setLastModifiedOn(LocalDateTime.now());
       
         producer.publish(event);
      }
   }

}