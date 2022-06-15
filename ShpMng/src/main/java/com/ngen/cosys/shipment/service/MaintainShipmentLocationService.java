package com.ngen.cosys.shipment.service;

import java.util.Date;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.SearchShipmentLocation;
import com.ngen.cosys.shipment.model.ShipmentInventory;
import com.ngen.cosys.shipment.model.ShipmentMaster;

public interface MaintainShipmentLocationService {

   ShipmentMaster getSearchedLoc(SearchShipmentLocation paramAWB) throws CustomException;

   void getSplittedLoc(ShipmentMaster paramAWB) throws CustomException;

   void getMergedLoc(ShipmentMaster paramAWB) throws CustomException;

   void getUpdatedLoc(ShipmentMaster paramAWB) throws CustomException;

   void getDeletedInventory(ShipmentMaster paramAWB) throws CustomException;

   /**
    * Method to trigger inventory creation for sending external interface
    * integration
    * 
    * @param paramAWB
    * @throws CustomException
    */
   void raiseEventForInventoryOnImportShipments(ShipmentMaster paramAWB) throws CustomException;

   /**
    * Check whether inventory exists for an Shipment OR not
    * 
    * @param paramAWB
    * @return boolean - true if exists otherwise false
    * @throws CustomException
    */
   boolean isLocationExists(ShipmentMaster paramAWB) throws CustomException;

/**
 * @param shipmnetNumber
 * @param shipmentDate
 * @param partSuffix
 */
   ShipmentInventory getInboundDetailsForPartsuffix(ShipmentInventory shipmentInventory ) throws CustomException;

}