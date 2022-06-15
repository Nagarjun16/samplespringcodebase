package com.ngen.cosys.application.service;

import java.util.List;

import com.ngen.cosys.application.model.AutoFreightoutInventoryModel;
import com.ngen.cosys.application.model.AutoFreightoutModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface AutoFreightoutService {

   List<AutoFreightoutModel> getShipments() throws CustomException;

   List<AutoFreightoutInventoryModel> getInventoryDetails(AutoFreightoutModel requestModel) throws CustomException;

   void freightoutToInventory(AutoFreightoutInventoryModel requestModel) throws CustomException;

   void updateInboundWorksheetShipmentStatus(AutoFreightoutModel requestModel) throws CustomException;

}