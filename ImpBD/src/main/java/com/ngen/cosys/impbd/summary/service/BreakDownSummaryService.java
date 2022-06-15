/**
 * This is a service component which provides methods for handling break down
 * summary
 */
package com.ngen.cosys.impbd.summary.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.summary.model.BreakDownSummary;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.impbd.summary.model.Email;

public interface BreakDownSummaryService {

   /**
    * Method to get break down tonnage information for an inbound flight
    * 
    * @param requestModel
    * @return BreakDownSummaryModel - Flight and it's associated ULD/Trolley and other
    *         tonnage information
    * @throws CustomException
    */
   BreakDownSummaryModel get(BreakDownSummaryModel requestModel) throws CustomException;

   /**
    * Method to create break down summary on flight complete
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createBreakDownSummaryOnFlightComplete(BreakDownSummary requestModel) throws CustomException;

   /**
    * Method to create break down summary for a given inbound flight
    * 
    * @param requestModel
    * @return BreakDownSummary - create break down summary
    * @throws CustomException
    */
   BreakDownSummary createBreakDownSummary(BreakDownSummary requestModel) throws CustomException;
   
     List<Email> fetchEmails(String s)  throws CustomException;

   /**
    * Method to update feedback rating for an service provider on their work done
    * 
    * @param breakDownSummary
    * @throws CustomException
    */
   void updateFeedBack(BreakDownSummary requestModel) throws CustomException;

   /**
    * Method to calculate flight delay for inbound flight
    * 
    * @param flightInfo
    * @return BigInteger - Delay in minutes
    * @throws CustomException
    */
   BigInteger calculateFlightDelay(BreakDownSummaryModel requestModel) throws CustomException;

}