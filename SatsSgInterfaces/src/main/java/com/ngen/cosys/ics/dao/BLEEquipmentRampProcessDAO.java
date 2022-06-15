package com.ngen.cosys.ics.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.BLEEquipmentRampProcessRequestModel;

public interface BLEEquipmentRampProcessDAO {

   // Fetch PDMasterId Based On pdID
   public String isPalletDollyExist(BLEEquipmentRampProcessRequestModel request) throws CustomException;

   // Fetch TripInfoID Based On PalletID
   public String fetchTripInfoIdBasedOnPalletID(BLEEquipmentRampProcessRequestModel request) throws CustomException;

   // Update Only Incase Of Stage = RAMP & moveType = OU in
   public Integer updateRampProcessForRAMPOUT(BLEEquipmentRampProcessRequestModel request) throws CustomException;

   // fetch PDMasterId based on palletId
   public String fetchPDMasterIdBasedOnPalletID(BLEEquipmentRampProcessRequestModel request) throws CustomException;

   // Insert into PD_PDInOutMovement
   public Integer insertPDInOutMovements(BLEEquipmentRampProcessRequestModel request) throws CustomException;

}
