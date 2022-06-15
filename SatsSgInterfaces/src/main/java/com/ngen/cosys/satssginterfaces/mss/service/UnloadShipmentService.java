/**
 * UnloadShipmentService.java
 * 
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version Date Author Reason 1.0  2 February,2018 NIIT -
 */
package com.ngen.cosys.satssginterfaces.mss.service;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentRequest;

/**
 * This class takes care of the responsibilities related to the
 * UnloadShipment Service operations that comes from the Controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface UnloadShipmentService {
	
	/**
	 * This method unloads the shipment from the ULD
	 * 
	 * @param shpment
	 * @return
	 * @throws CustomException
	 */
	UnloadShipmentRequest  unloadShipment(UnloadShipmentRequest unloadShipment) throws CustomException; 
	
	/*FlightDetails getFlightDetailsByULD(UnloadShipmentSearch uld) throws CustomException; 
	List<FlightDetails> getFlightDetailsByShipmentNumber(UnloadShipmentSearch shipmentDetail) throws CustomException; 
	UnloadShipmentRequest  getShipmentInfoAndFlightDetails(FlightDetails flightDetails)throws CustomException; */ 
	
	
	

}
