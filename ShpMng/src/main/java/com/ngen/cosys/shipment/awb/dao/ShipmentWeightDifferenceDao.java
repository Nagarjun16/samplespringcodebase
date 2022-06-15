/**
 * This is an repository component for implementing functionality for weight
 * sync between Shipment Master and Shipment Inventory
 */
package com.ngen.cosys.shipment.awb.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.awb.model.ShipmentWeightDifferenceModel;

public interface ShipmentWeightDifferenceDao {

   /**
    * Sync weight from shipment master to last line item of inventory and break
    * down weight
    * 
    * @param requestModel
    * @throws CustomException
    */
   void syncWeightWithInventory(ShipmentWeightDifferenceModel requestModel) throws CustomException;

}