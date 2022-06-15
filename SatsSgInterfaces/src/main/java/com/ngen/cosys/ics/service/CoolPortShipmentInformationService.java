package com.ngen.cosys.ics.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.CoolPortShipmentRequestModel;
import com.ngen.cosys.ics.model.CoolPortShipmentResponseModel;

public interface CoolPortShipmentInformationService {
   
   public CoolPortShipmentResponseModel fetchShipmentInformation(CoolPortShipmentRequestModel request) throws CustomException;
   

}
