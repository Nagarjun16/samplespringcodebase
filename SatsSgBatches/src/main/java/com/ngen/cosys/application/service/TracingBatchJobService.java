package com.ngen.cosys.application.service;

import java.util.List;

import com.ngen.cosys.application.model.TracingShipmentModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface TracingBatchJobService {

   /**
    * Method to get undelivered shipment for creating tracing
    * 
    * @return List<TracingShipmentModel> - List of shipments to create tracing
    *         record
    */
   List<TracingShipmentModel> getUndeliverdShipments() throws CustomException;

   /**
    * Method to create tracing record
    * 
    * @throws CustomException
    * @return TracingShipmentModel - requestModel which has shipment info
    */
   void createTracing(TracingShipmentModel requestModel) throws CustomException;

}