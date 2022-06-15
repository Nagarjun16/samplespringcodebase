package com.ngen.cosys.application.dao;

import java.time.LocalDateTime;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ICSOperativeFlightRequestModel;

public interface ICSDAO {

   public ICSOperativeFlightRequestModel getOperativeFlightDetails() throws CustomException;
   
   public ICSOperativeFlightRequestModel getAdhocPushFlightDetails() throws CustomException;
   
   public void updateSystenParamCreatedDateTime(LocalDateTime currentTime) throws CustomException;

}
