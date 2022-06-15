package com.ngen.cosys.shipment.revive.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentModel;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentSummary;

public interface ReviveShipmentDAO {

   /**
    * Get the revive freight out
    * 
    * @param reviveShipmentModel
    * @return List<ReviveShipmentModel> - Get the list of freight out data
    * @throws CustomException
    */
   List<ReviveShipmentModel> getReviveShipmentInfo(ReviveShipmentSummary reviveShipmentModel) throws CustomException;

   /**
    * Create the inventory
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createInventory(ReviveShipmentModel requestModel) throws CustomException;

   /**
    * Delete freight out
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteFreightOut(ReviveShipmentModel requestModel) throws CustomException;

   /**
    * Delete TRM info in case of DANATA issued
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteTRMInfo(ReviveShipmentModel requestModel) throws CustomException;
   
   
   void createRemarks(ReviveShipmentModel requestModel) throws CustomException;

}