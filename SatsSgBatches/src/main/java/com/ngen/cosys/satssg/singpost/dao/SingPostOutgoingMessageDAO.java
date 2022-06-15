package com.ngen.cosys.satssg.singpost.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.FlightTouchDown;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;

public interface SingPostOutgoingMessageDAO {
   
   List<MailBagRequestModel> pushBagHandoverToAirlineStatus(Object value) throws CustomException;
   
   List<MailBagRequestModel> pushMailBagReceivingScanStatus(Object value) throws CustomException;
   
   List<FlightTouchDown> pushFlightTouchDownStatus(Object value) throws CustomException;
   
   List<MailBagRequestModel> pushHandoverToDNATAStatus(Object value) throws CustomException;
   
   List<MailBagRequestModel> pushOffloadStatus(Object value) throws CustomException;
   
   void logDataIntoAirmailEventTable(List<MailBagRequestModel> requestModel) throws CustomException;

}
