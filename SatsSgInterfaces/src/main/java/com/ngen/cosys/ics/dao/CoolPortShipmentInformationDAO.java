package com.ngen.cosys.ics.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.CoolPortShipmentRequestModel;
import com.ngen.cosys.ics.model.CoolPortShipmentResponseModel;

public interface CoolPortShipmentInformationDAO {
   
   public CoolPortShipmentResponseModel fetchShipmentInformation(CoolPortShipmentRequestModel request) throws CustomException;

}
