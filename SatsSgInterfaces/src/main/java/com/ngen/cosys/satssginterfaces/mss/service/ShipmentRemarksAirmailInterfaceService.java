/**
 * This is a class which holds entire functionality for managing of remarks information for shipment
 * 
 *  Usage:
 *  	1. Auto-wire this class service interface
 *      2. invoke the createShipmentRemarks() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentRemarksAirmailInterfaceModel;

public interface ShipmentRemarksAirmailInterfaceService {

	/**
	 * Method to create a remark for an shipment
	 * 
	 * @param shipmentRemarksModel
	 * @throws CustomException
	 */
	void createShipmentRemarks(ShipmentRemarksAirmailInterfaceModel shipmentRemarksModel) throws CustomException;

}