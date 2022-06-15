package com.ngen.cosys.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.application.dao.AutoFreightoutJobDAO;
import com.ngen.cosys.application.model.AutoFreightoutInventoryModel;
import com.ngen.cosys.application.model.AutoFreightoutModel;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class AutoFreightoutServiceImpl implements AutoFreightoutService {

   @Autowired
   private AutoFreightoutJobDAO dao;

   @Override
   public List<AutoFreightoutModel> getShipments() throws CustomException {
      return this.dao.getShipments();
   }

   @Override
   public List<AutoFreightoutInventoryModel> getInventoryDetails(AutoFreightoutModel requestModel)
         throws CustomException {
      return this.dao.getInventoryDetails(requestModel);
   }

   @Override
   public void freightoutToInventory(AutoFreightoutInventoryModel requestModel) throws CustomException {
      this.dao.freightoutInventory(requestModel);
   }

   @Override
   public void updateInboundWorksheetShipmentStatus(AutoFreightoutModel requestModel) throws CustomException {
      this.dao.updateInboundWorksheetShipmentStatus(requestModel);
   }

}