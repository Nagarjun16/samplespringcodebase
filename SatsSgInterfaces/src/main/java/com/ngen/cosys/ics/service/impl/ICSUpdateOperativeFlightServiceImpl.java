/**
 * 
 */
package com.ngen.cosys.ics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ICSUpdateOperativeFlightDAO;
import com.ngen.cosys.ics.model.ICSUpdateOperativeFlightRequestModel;
import com.ngen.cosys.ics.service.ICSUpdateOperativeFlightService;



/**
 * @author Ashwin.Bantoo
 *
 */
@Service
public class ICSUpdateOperativeFlightServiceImpl implements ICSUpdateOperativeFlightService {
	
	@Autowired
	   private ICSUpdateOperativeFlightDAO fltDao;

	   @Autowired
	   private ApplicationLoggerService applicationLoggerService;

	   public void logOutgoingMessage(OutgoingMessageLog outgoingMessage) {
	      applicationLoggerService.logOutgoingMessage(outgoingMessage);
	   }

	   @Override
	   public ICSUpdateOperativeFlightRequestModel getUpdatedOperativeFlightDetails() throws CustomException {
		   ICSUpdateOperativeFlightRequestModel operativeFlightDetails = fltDao.getUpdatedOperativeFlightDetails();
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
