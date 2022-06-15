package com.ngen.cosys.impbd.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.DisplayIncomigFlightConfigurationTime;
import com.ngen.cosys.impbd.model.IncomingFlightModel;
import com.ngen.cosys.impbd.model.IncomingFlightQuery;

public interface IncomingFlightService {
   /**
    * This method responsible for Fetching The Export Prelodge Shipment Details.
    * 
    * @param ExportPrelodgeService
    * @return
    * @throws CustomException
    */
   List<IncomingFlightModel> fetch(IncomingFlightQuery incomingFlight) throws CustomException;
   List<String> fetchTelexMessage(IncomingFlightQuery incomingFlight) throws CustomException;
   
   DisplayIncomigFlightConfigurationTime fetchTime(DisplayIncomigFlightConfigurationTime incomingFlight) throws CustomException;
   
   List<IncomingFlightModel> fetchMyFlights(DisplayIncomigFlightConfigurationTime incomingFlight) throws CustomException;
}