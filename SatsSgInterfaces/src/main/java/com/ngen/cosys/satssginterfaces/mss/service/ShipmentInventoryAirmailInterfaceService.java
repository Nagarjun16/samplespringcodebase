/**
 * This is a class which holds entire functionality for managing of inventory information based on document/found pieces for shipment.
 * 
 *  Usage:
 *  	1. Auto-wire this class service interface
 *      2. invoke the createInventory() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownAirmailInterfaceModel;

public interface ShipmentInventoryAirmailInterfaceService {

	/**
	 * Method which creates the shipment inventory for a given shipment/number and
	 * shipment date
	 * 
	 * @param inboundBreakdownModel
	 *            - Model which contains actual inventory information along with
	 *            associated SHC/House
	 * @throws CustomException
	 */
	void createInventory(InboundBreakdownAirmailInterfaceModel inboundBreakdownModel) throws CustomException;

}