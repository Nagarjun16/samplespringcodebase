package com.ngen.cosys.application.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.application.model.TracingShipmentModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface TracingBatchJobDAO {

   List<TracingShipmentModel> fetchUndeliverdShipments() throws CustomException;

   void insertTracing(TracingShipmentModel shipments) throws CustomException;

   void updateTracing(TracingShipmentModel shipments) throws CustomException;

   void insertActivity(TracingShipmentModel shipments) throws CustomException;

   BigInteger getMaxCaseNumber() throws CustomException;

   void updateShipmentMaster(TracingShipmentModel shipments) throws CustomException;
   
   boolean checkActivityExist(TracingShipmentModel shipments) throws CustomException;

}