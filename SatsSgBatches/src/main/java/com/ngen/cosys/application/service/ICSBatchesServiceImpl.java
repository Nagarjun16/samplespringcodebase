package com.ngen.cosys.application.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.dao.ICSDAO;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ICSOperativeFlightRequestModel;

@Service
public class ICSBatchesServiceImpl implements ICSBatchesService {

   @Autowired
   private ICSDAO dao;

   @Autowired
   private ApplicationLoggerService applicationLoggerService;

   public void logOutgoingMessage(OutgoingMessageLog outgoingMessage) {
      applicationLoggerService.logOutgoingMessage(outgoingMessage);
   }
   
	public void updateSystenParamCreatedDateTime(LocalDateTime currentTime) throws CustomException{
	   
	   dao.updateSystenParamCreatedDateTime(currentTime);
   }

   @Override
   public ICSOperativeFlightRequestModel getOperativeFlightDetails() throws CustomException {
      ICSOperativeFlightRequestModel operativeFlightDetails = dao.getOperativeFlightDetails();
      operativeFlightDetails.getOperativeFlightList().forEach(flight -> {
         String offPointsCSV = flight.getOffPointsCSV();
         if (!offPointsCSV.isEmpty()) {
            String[] split = offPointsCSV.replaceAll(", $", "").split(",");
            int stringLength = split.length;
            if (stringLength != 0) {
               for (int i = 0; i < stringLength && stringLength < 6; i++) {
                  if (isIndexExists(0, stringLength)) {
                     flight.setOffPoint2(split[0].trim());
                  }
                  if (isIndexExists(1, stringLength)) { 
                     flight.setOffPoint3(split[1].trim());
                  }
                  if (isIndexExists(2, stringLength)) {
                     flight.setOffPoint4(split[2].trim());
                  }
                  if (isIndexExists(3, stringLength)) {
                     flight.setOffPoint5(split[3].trim());
                  }
                  if (isIndexExists(4, stringLength)) {
                     flight.setOffPoint6(split[4].trim());
                  }
               }
            }
         }
      });
      return operativeFlightDetails;
   }
   
   @Override
   public ICSOperativeFlightRequestModel getAdhocPushFlightDetails() throws CustomException {
      ICSOperativeFlightRequestModel operativeFlightDetails = dao.getAdhocPushFlightDetails();
      operativeFlightDetails.getOperativeFlightList().forEach(flight -> {
         String offPointsCSV = flight.getOffPointsCSV();
         if (!offPointsCSV.isEmpty()) {
            String[] split = offPointsCSV.replaceAll(", $", "").split(",");
            int stringLength = split.length;
            if (stringLength != 0) {
               for (int i = 0; i < stringLength && stringLength < 6; i++) {
                  if (isIndexExists(0, stringLength)) {
                     flight.setOffPoint2(split[0].trim());
                  }
                  if (isIndexExists(1, stringLength)) { 
                     flight.setOffPoint3(split[1].trim());
                  }
                  if (isIndexExists(2, stringLength)) {
                     flight.setOffPoint4(split[2].trim());
                  }
                  if (isIndexExists(3, stringLength)) {
                     flight.setOffPoint5(split[3].trim());
                  }
                  if (isIndexExists(4, stringLength)) {
                     flight.setOffPoint6(split[4].trim());
                  }
               }
            }
         }
      });
      return operativeFlightDetails;
   }

   public boolean isIndexExists(int index, long list) {
      if (index <= list - 1)
         return true;
      return false;

   }
}