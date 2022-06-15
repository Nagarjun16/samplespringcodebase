package com.ngen.cosys.shipment.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.SearchAWB;
import com.ngen.cosys.shipment.model.ShipmentMaster;

/**
 * This interface takes care of the responsibilities related to the Shipments which
 * are on hold. 
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ShipmetnOnHoldService {
	
	/**
	 * find list of all flights with combination of flights and date 
	 * which falls in the range of the entry.
	 * @param reqFlight
	 * @return List of Shipments in inventory
	 * @throws CustomException
	 */
	List<ShipmentMaster> getLockDetails(SearchAWB paramAWB) throws CustomException;
	
	/**
	 * find list of all flights with combination of flights and date 
	 * which falls in the range of the entry.
	 * @param reqFlight
	 * @return List of Shipments in inventory with updated Hold flag
	 * @throws CustomException
	 */
	void updateLockDetails(ShipmentMaster shipmentMaster) throws CustomException;
	
	
	ShipmentMaster generateCTOcase(ShipmentMaster shipmentMaster) throws CustomException;
	
	/**
	 * Check whether inventory exists for an Shipment OR not
	 * 
	 * @param paramAWB
	 * @return boolean - true if exists otherwise false
	 * @throws CustomException
	*/
	boolean isHoldExists(ShipmentMaster paramAWB) throws CustomException;
	
}