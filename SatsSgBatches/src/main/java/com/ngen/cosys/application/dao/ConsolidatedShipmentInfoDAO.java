package com.ngen.cosys.application.dao;

import java.io.Serializable;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.FlightModel;

public interface ConsolidatedShipmentInfoDAO extends Serializable {
   /**
    * @param addCustomMRSShipmentInfo
    * @throws CustomException
//    */
    
   public List<FlightModel> getFlightInfo() throws CustomException;


   public String getSendmrsUrl() throws CustomException;

   public List<FlightModel> getACESExportSQFlights() throws CustomException;


   public List<FlightModel> getACESExportOALFlights() throws CustomException;
	
	
   public List<FlightModel> getACESImportSQFlights() throws CustomException;
	
	
   public List<FlightModel> getACESImportOALFlights() throws CustomException;

}