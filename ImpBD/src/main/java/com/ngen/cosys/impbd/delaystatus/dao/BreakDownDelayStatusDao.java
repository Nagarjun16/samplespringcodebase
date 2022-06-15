package com.ngen.cosys.impbd.delaystatus.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.delaystatus.model.DelayStatusSearch;
import com.ngen.cosys.impbd.delaystatus.model.FlightTonnageDifference;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.impbd.summary.model.Email;

public interface BreakDownDelayStatusDao {

   List<BreakDownSummaryModel> fetchDelayList(DelayStatusSearch flightData) throws CustomException;

   /**
    * Method to get inbound ULD finalized status
    * 
    * @param flightInfo
    * @return boolean - true if finalized other wise false
    * @throws CustomException
    */
   boolean checkInboundULDFinalized(DelayStatusSearch flightInfo) throws CustomException;

   /**
    * Method to get ramp check-in complete or not
    * 
    * @param flightInfo
    * @return boolean - true if check-in complete other wise false
    * @throws CustomException
    */
   boolean checkRampCheckInComplete(DelayStatusSearch flightInfo) throws CustomException;

   /**
    * Method to get flight complete or not
    * 
    * @param flightInfo
    * @return boolean - true if check-in complete other wise false
    * @throws CustomException
    */
   boolean checkFlightComplete(DelayStatusSearch flightInfo) throws CustomException;

   void closeFlight(DelayStatusSearch flightData) throws CustomException;

   void reopenFlight(DelayStatusSearch flightData) throws CustomException;
   
   FlightTonnageDifference fetchFlightTonnageWeight(DelayStatusSearch flightData) throws CustomException;
   
   List<Email> fetchFlightCloseAdminEmail()throws CustomException;
   

}