package com.ngen.cosys.ics.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.BLEEquipmentRampProcessRequestModel;

public interface BLEEquipmentRampProcessService {

   public String isPalletDollyExist(BLEEquipmentRampProcessRequestModel request) throws CustomException;

   public Integer performEquipmentRampProcess(BLEEquipmentRampProcessRequestModel request) throws CustomException;

}
