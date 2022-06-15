/**
 * This is a class which holds business methods for creating Shipment Master Entity
 * 
 *  Usage:
 *  	1. Auto-wire this class service interface
 *      2. invoke the createShipment() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterface;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentVerificationAirmailInterfaceModel;

public interface ShipmentMasterAirmailInterfaceService {

	/**
	 * Method to create Shipment Record from either Inbound Break Down/Document
	 * Verification/Inbound Print Bar Code
	 * 
	 * @param shipmentMaster
	 *            - Function just passes shipment number/date and if applicable
	 *            document receive information
	 *
	 * 
	 * @throws CustomException
	 */
	void createShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException;

	void updateShipmentDocumentReceivedOn(ShipmentVerificationAirmailInterfaceModel shipmentVerModel) throws CustomException;

	

}