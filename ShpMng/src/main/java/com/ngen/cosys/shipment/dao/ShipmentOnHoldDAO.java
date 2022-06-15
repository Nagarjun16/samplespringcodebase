package com.ngen.cosys.shipment.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.SearchAWB;
import com.ngen.cosys.shipment.model.ShipmentInventory;
import com.ngen.cosys.shipment.model.ShipmentMaster;

public interface ShipmentOnHoldDAO {

   List<ShipmentMaster> getLockDetails(SearchAWB paramAWB) throws CustomException;

   boolean validateShipmentNumber(SearchAWB paramAWB) throws CustomException;

   void updateLockDetails(ShipmentMaster shipmentMaster) throws CustomException;

   /**
    * Method to max case number
    * 
    * @return BigInteger
    * @throws CustomException
    */
   BigInteger getMaxCaseNumber() throws CustomException;

   void insertTracingRecords(ShipmentMaster shipmentMaster) throws CustomException;

   void insertTracingShipmentInfo(ShipmentInventory shipmentMaster) throws CustomException;

   boolean isHoldExists(ShipmentMaster paramAWB) throws CustomException;
   
   //Issue 11615
    void updateTracingRecords(ShipmentInventory shipmentMaster) throws CustomException;
    void insertTracingRecordActivity(ShipmentInventory shipmentMaster) throws CustomException;
    BigInteger getTracingShipmentInfoId(ShipmentInventory shipmentMaster) throws CustomException;

}