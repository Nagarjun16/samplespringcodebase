package com.ngen.cosys.ics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.BLEEquipmentRampProcessDAO;
import com.ngen.cosys.ics.model.BLEEquipmentRampProcessRequestModel;
import com.ngen.cosys.ics.service.BLEEquipmentRampProcessService;

@Service
public class BLEEquipmentRampProcessServiceImpl implements BLEEquipmentRampProcessService {

   @Autowired
   private BLEEquipmentRampProcessDAO dao;

   @Override
   public String isPalletDollyExist(BLEEquipmentRampProcessRequestModel request) throws CustomException {
      return dao.isPalletDollyExist(request);
   }

   @Override
   public Integer performEquipmentRampProcess(BLEEquipmentRampProcessRequestModel request) throws CustomException {

      // Fetch TripInfoID
      String tripInfoId = dao.fetchTripInfoIdBasedOnPalletID(request);
      request.setTripInfoId(tripInfoId);

      // insert into PD_PDInOutMovements
      String pdMasterId = dao.fetchPDMasterIdBasedOnPalletID(request);
      request.setPdMasterId(pdMasterId);

      // check stage = RAMP && movetype = OU
      if (request.getMoveType().equalsIgnoreCase("OU") && request.getStage().equalsIgnoreCase("RAMP") && !StringUtils.isEmpty(tripInfoId)) {

         // Update Equipment_Release_Trip_Info
         dao.updateRampProcessForRAMPOUT(request);

         // Insert Into PD_PDInOutMovements
         return dao.insertPDInOutMovements(request);
      }
      if (request.getMoveType().equalsIgnoreCase("IN") && request.getStage().equalsIgnoreCase("EQUIPMENT") && !StringUtils.isEmpty(tripInfoId)) {

         // Update Equipment_Release_Trip_Info
         dao.updateRampProcessForRAMPOUT(request);

         // Insert Into PD_PDInOutMovements
         return dao.insertPDInOutMovements(request);
      } else {
         // Insert Into PD_PDInOutMovements
         return dao.insertPDInOutMovements(request);
      }

   }
}
