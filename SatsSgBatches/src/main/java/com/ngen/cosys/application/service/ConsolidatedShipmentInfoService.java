package com.ngen.cosys.application.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.FlightModel;

public interface ConsolidatedShipmentInfoService {

   List<FlightModel> getFlightInfo() throws CustomException;
   String getSendmrsUrl() throws CustomException;
   
   public List<FlightModel> getACESExportSQFlights() throws CustomException;
   public List<FlightModel> getACESExportOALFlights() throws CustomException;
   public List<FlightModel> getACESImportSQFlights() throws CustomException;
   public List<FlightModel> getACESImportOALFlights() throws CustomException;
}