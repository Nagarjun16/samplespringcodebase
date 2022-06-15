/**
 * This is a class which holds entire functionality for managing of shipment verification
 * 
 * Usage:
 *  	1. Auto-wire this class service interface
 *      2. invoke the createShipmentVerification() method
 *      
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentVerificationAirmailInterfaceModel;

public interface ShipmentVerificationAirlineInterfaceService {

	/**
	 * Method to which creates Shipment Verification Information for a given
	 * AWB/Mail/Coureir
	 * 
	 * @param shipmentVerification
	 * @return ShipmentVerification
	 * @throws CustomException
	 */
	ShipmentVerificationAirmailInterfaceModel createShipmentVerification(ShipmentVerificationAirmailInterfaceModel shipmentVerification)
			throws CustomException;

	void createDgCheckList(ShipmentVerificationAirmailInterfaceModel shipmentVerModel) throws CustomException;

}