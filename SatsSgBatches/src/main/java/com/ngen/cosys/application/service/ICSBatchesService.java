package com.ngen.cosys.application.service;

import java.time.LocalDateTime;

import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ICSOperativeFlightRequestModel;

public interface ICSBatchesService {

   public ICSOperativeFlightRequestModel getOperativeFlightDetails() throws CustomException;
   
   public ICSOperativeFlightRequestModel getAdhocPushFlightDetails() throws CustomException;

   void logOutgoingMessage(OutgoingMessageLog outgoingMessage);
   
   public void updateSystenParamCreatedDateTime(LocalDateTime currentTime) throws CustomException;
}