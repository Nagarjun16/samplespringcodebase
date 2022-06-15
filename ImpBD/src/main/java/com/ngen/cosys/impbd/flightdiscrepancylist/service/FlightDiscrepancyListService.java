package com.ngen.cosys.impbd.flightdiscrepancylist.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.flightdiscrepancylist.model.FlightDiscrepancyListModel;

public interface FlightDiscrepancyListService {

   /**
    * Method which helps get discrepancy information for a given flight
    * 
    * @param searchDiscrepancyList
    * @return FlightDiscrepancyListModel - Object which contains discrepancy
    *         (Irregularity/Damage) Information
    * @throws CustomException
    */
   FlightDiscrepancyListModel fetch(FlightDiscrepancyListModel searchDiscrepancyList) throws CustomException;

   /**
    * Update FDL message version if send FDL has been initiated
    * 
    * @param requestModel
    * @throws CustomException
    */
   void updateFDLVersion(FlightDiscrepancyListModel requestModel) throws CustomException;
   
   FlightDiscrepancyListModel getFDLVersion(FlightDiscrepancyListModel searchDiscrepancyList) throws CustomException;

}