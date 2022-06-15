/**
 * This is an interface for exposing methods which does auto complete for an
 * export flight
 */
package com.ngen.cosys.export.flightautocomplete.service;

import java.util.List;

import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteDetails;
import com.ngen.cosys.framework.exception.CustomException;

public interface FlightAutoCompleteService {

   /**
    * Method to get list of flights which are matching the criteria
    * 
    * @return List<FlightAutoCompleteDetails> - Flights which needs to be completed
    * @throws CustomException
    */
   List<FlightAutoCompleteDetails> getFlightList() throws CustomException;

   /**
    * Method which marks the flight as first complete. This ensures subsequent
    * batch job would not pick up
    * 
    * @param request
    *           - Flight which needs to be marked for First Flight Complete
    * @throws CustomException
    */
   void markFirstFlightComplete(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method which un-marks the flight first complete in case of exception. This is
    * to ensure next batch job run it picks up the flight
    * 
    * @param request
    *           - Flight which needs to be un-marked for First Flight Complete
    * @throws CustomException
    */
   void unmarkFirstFlightComplete(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method which freights out all shipments which has been manifested on flight
    * 
    * @param request
    *           - Flight for which freight out needs to be performed
    * @throws CustomException
    */
   void freightOut(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method which freights out all shipments which has been manifested on flight
    * 
    * @param request
    *           - Flight for which freight out needs to be performed
    * @throws CustomException
    */
   void submitShipmentsToCustoms(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method to publish flight complete events
    * 
    * @param request
    *           - Publish events for flight and it's associated shipments
    * @throws CustomException
    */
   void publishEvents(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method which marks the flight as complete.
    * 
    * @param request
    *           - Flight which needs to be marked as complete
    * @throws CustomException
    */
   void markFlightComplete(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method to trigger email on FFM info
    * 
    * @param request
    * @throws CustomException
    */
   void sendEmailOnFFMInfo(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method to trigger email on FWB info
    * 
    * @param request
    * @throws CustomException
    */
   void sendEmailOnFWBInfo(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method to trigger email on RTD info
    * 
    * @param request
    * @throws CustomException
    */
   void sendEmailOnRTDInfo(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method to get communication email id's for triggering email by airline
    * 
    * @param request
    * @return List<String> - List of email id's
    * @throws CustomException
    */
   List<String> getCommunicationEmailIds(FlightAutoCompleteDetails request) throws CustomException;

    void calculateVolume(FlightAutoCompleteDetails request)throws CustomException;

}