package com.ngen.cosys.shipment.revive.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.revive.dao.ReviveShipmentDAO;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentModel;
import com.ngen.cosys.shipment.revive.model.ReviveShipmentSummary;

@Service
@Transactional
public class ReviveShipmentServiceImpl implements ReviveShipmentService {

   @Autowired
   ReviveShipmentDAO dao;

   @Override
   public List<ReviveShipmentModel> getReviveShipmentInfo(ReviveShipmentSummary requestModel) throws CustomException {
      return this.dao.getReviveShipmentInfo(requestModel);
   }

   @Override
   public void onRevive(ReviveShipmentModel requestModel) throws CustomException {

      if (StringUtils.isEmpty(requestModel.getWarehouseLocation())
            && StringUtils.isEmpty(requestModel.getShipmentLocation())) {
         requestModel.addError("awb.enter.shp.loc.or.whs.loc", "", ErrorType.ERROR);
         throw new CustomException(requestModel.getMessageList());
      }
      
     if(StringUtils.isEmpty(requestModel.getReasonForRevive()))
     {
    	 requestModel.addError("awb.enter.revive.reason","",ErrorType.ERROR);
    	 throw new CustomException(requestModel.getMessageList());
     }
      // Create the inventory
      this.dao.createInventory(requestModel);

      // Delete the freight out
      this.dao.deleteFreightOut(requestModel);

      // Delete the TRM in case of DNATA
      this.dao.deleteTRMInfo(requestModel);
      
       this.dao.createRemarks(requestModel);
   }

}