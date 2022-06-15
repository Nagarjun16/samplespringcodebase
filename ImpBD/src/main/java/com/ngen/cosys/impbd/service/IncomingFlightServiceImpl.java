package com.ngen.cosys.impbd.service;

import java.util.List;

import org.springframework.util.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.dao.IncomingFlightDAO;
import com.ngen.cosys.impbd.model.DisplayIncomigFlightConfigurationTime;
import com.ngen.cosys.impbd.model.IncomingFlightModel;
import com.ngen.cosys.impbd.model.IncomingFlightQuery;

/**
 * Service implementation for Display Incoming Flight
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class IncomingFlightServiceImpl implements IncomingFlightService {

   @Autowired
   private IncomingFlightDAO incomingFlightDAO;

   /*
    * Get list of Display Incoming Flight based on search query
    * 
    */
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public List<IncomingFlightModel> fetch(IncomingFlightQuery incomingFlight) throws CustomException {
      List<IncomingFlightModel> returnObj = incomingFlightDAO.fetch(incomingFlight);
      if (CollectionUtils.isEmpty(returnObj)) {
         throw new CustomException("NOFLIGHT", "No flights arriving for specific input", ErrorType.ERROR);
      }
      return returnObj;
   }
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public  List<String> fetchTelexMessage(IncomingFlightQuery incomingFlight) throws CustomException{
	   List<String> returnObj = incomingFlightDAO.fetchTelexMessage(incomingFlight);
	   return returnObj;
   }
@Override
public DisplayIncomigFlightConfigurationTime fetchTime(DisplayIncomigFlightConfigurationTime incomingFlight) throws CustomException {
	
	return incomingFlightDAO.fetchTime(incomingFlight);
}

@Override
public List<IncomingFlightModel> fetchMyFlights(DisplayIncomigFlightConfigurationTime incomingFlight)
		throws CustomException {
	
	
	return incomingFlightDAO.getMyFlights(incomingFlight);
}
}
