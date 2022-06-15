package com.ngen.cosys.impbd.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.DisplayIncomigFlightConfigurationTime;
import com.ngen.cosys.impbd.model.IncomingFlightModel;
import com.ngen.cosys.impbd.model.IncomingFlightQuery;

public interface IncomingFlightDAO {
	/**
	 * Fetches the list of Incoming Flights.
	 * 
	 * @param incomingFlightModel
	 * @return
	 * @throws CustomException
	 */
	List<IncomingFlightModel> fetch(IncomingFlightQuery incomingFlightModel) throws CustomException;
	List<String> fetchTelexMessage (IncomingFlightQuery incomingFlightQuery) throws CustomException;
	DisplayIncomigFlightConfigurationTime fetchTime(DisplayIncomigFlightConfigurationTime incomingFlightModel) throws CustomException;
	
	List<IncomingFlightModel> getMyFlights(DisplayIncomigFlightConfigurationTime incomingFlightModel) throws CustomException;
}
