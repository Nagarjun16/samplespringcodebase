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
package com.ngen.cosys.impbd.shipment.remarks.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.remarks.model.ShipmentRemarksModel;

public interface ShipmentRemarksService {

	/**
	 * Method to create a remark for an shipment
	 * 
	 * @param shipmentRemarksModel
	 * @throws CustomException
	 */
	void createShipmentRemarks(ShipmentRemarksModel shipmentRemarksModel) throws CustomException;

}