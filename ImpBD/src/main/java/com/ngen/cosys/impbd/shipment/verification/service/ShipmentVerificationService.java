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
package com.ngen.cosys.impbd.shipment.verification.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;

public interface ShipmentVerificationService {

	/**
	 * Method to which creates Shipment Verification Information for a given
	 * AWB/Mail/Coureir
	 * 
	 * @param shipmentVerification
	 * @return ShipmentVerification
	 * @throws CustomException
	 */
	ShipmentVerificationModel createShipmentVerification(ShipmentVerificationModel shipmentVerification)
			throws CustomException;

	void createDgCheckList(ShipmentVerificationModel shipmentVerModel) throws CustomException;

}