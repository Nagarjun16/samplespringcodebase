/**
 * This is a repository interface class for creating delivery request based on
 * EDelivery Request shipments
 */
package com.ngen.cosys.application.dao;

import java.util.List;

import com.ngen.cosys.events.payload.CreateDeliveryRequestStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;

public interface AutoCreateDeliveryRequestDAO {

   /**
    * Method to schedule delivery requests
    * 
    * @return List<CreateDeliveryRequestStoreEvent> - List of shipments for which
    *         delivery requests needs to be created
    * @throws CustomException
    */
   List<CreateDeliveryRequestStoreEvent> scheduleDeliveryRequests() throws CustomException;
   
    String getPrinterName(CreateDeliveryRequestStoreEvent request) throws CustomException ;

}