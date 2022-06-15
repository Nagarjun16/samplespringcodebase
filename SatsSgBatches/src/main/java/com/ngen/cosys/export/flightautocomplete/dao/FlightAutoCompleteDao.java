/**
 * This is an interface for repository which holds all methods for performing
 * CRUD operations on Auto Flight Complete
 */
package com.ngen.cosys.export.flightautocomplete.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteDetails;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteShipmentDetails;
import com.ngen.cosys.export.flightautocomplete.model.FlightAutoCompleteShipmentHouseDetails;
import com.ngen.cosys.framework.exception.CustomException;

public interface FlightAutoCompleteDao {

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
    * Method which marks the flight as complete.
    * 
    * @param request
    *           - Flight which needs to be marked as complete
    * @throws CustomException
    */
   void markFlightComplete(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method which marks the flight as DEP
    * 
    * @param request
    *           - Flight which needs to be marked as DEP
    * @throws CustomException
    */
   void markFlightStats(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method to update flight info to null from ULD which have been assigned on
    * flight
    * 
    * @param request
    * @throws CustomException
    */
   void deAssociateULDFromFlight(FlightAutoCompleteDetails request) throws CustomException;

   /**
    * Method to move shipment info from inventory to freight out
    * 
    * @param request
    *           - Shipment for which freight out nneds to be done
    * @throws CustomException
    */
   void moveInventoryToFreightOut(FlightAutoCompleteShipmentDetails request) throws CustomException;

   /**
    * Method to check shipment is departed completely to check the status
    * 
    * @param shipmentId
    * @return
    * @throws CustomException
    */
   boolean checkShipmentFullDepartedStatus(BigInteger shipmentId) throws CustomException;
   
   /**
    * Method to mark shipment as Departed if no inventory found
    * 
    * @param request
    * @throws CustomException
    */
   void updateShipmentStatus(FlightAutoCompleteShipmentDetails request) throws CustomException;

   /**
    * Method to mark shipment house as Departed if no inventory found
    * 
    * @param request
    * @throws CustomException
    */
   void updateShipmentHouseStatus(List<FlightAutoCompleteShipmentHouseDetails> request) throws CustomException;

   /**
    * Method to get communication email id's for triggering email by airline
    * 
    * @param request
    * @return List<String> - List of email id's
    * @throws CustomException
    */
   List<String> getCommunicationEmailIds(FlightAutoCompleteDetails request) throws CustomException;

/**
 * check for Japan customs for checking configured sector for message triggering
 * @param flightId
 * @param tenantId
 * @param carrierCode
 * @return
 * @throws CustomException
 */
BigInteger checkJapanCustomsRequirement(BigInteger flightId, String tenantAirport, String carrierCode) throws CustomException;

Boolean checkVolumeNeedsTobeDerived(FlightAutoCompleteDetails request)throws CustomException;

List<FlightAutoCompleteShipmentDetails> getManifestShipmentInfo(FlightAutoCompleteDetails request)throws CustomException;

void updateManifestShipmentVolume(FlightAutoCompleteShipmentDetails request)throws CustomException;

void createUldOutMovement(FlightAutoCompleteDetails request) throws CustomException;


}