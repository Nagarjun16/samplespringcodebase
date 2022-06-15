package com.ngen.cosys.application.dao;

import java.util.List;

import com.ngen.cosys.application.model.AutoFreightoutInventoryModel;
import com.ngen.cosys.application.model.AutoFreightoutModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface AutoFreightoutJobDAO {

   List<AutoFreightoutModel> getShipments() throws CustomException;

   List<AutoFreightoutInventoryModel> getInventoryDetails(AutoFreightoutModel requestModel) throws CustomException;

   void freightoutInventory(AutoFreightoutInventoryModel inventoryModel) throws CustomException;

   void updateInboundWorksheetShipmentStatus(AutoFreightoutModel requestModel) throws CustomException;

}