/**
 * This is a class which holds entire functionality for managing of shipment verification
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.verification.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;

public interface ShipmentVerificationDAO {

	/**
	 * Method to create an shipment verification record
	 * 
	 * @param shipmentVerification
	 * @throws CustomException
	 */
	void create(ShipmentVerificationModel shipmentVerification) throws CustomException;

	/**
	 * Method to update shipment verification record
	 * 
	 * @param shipmentVerification
	 * @throws CustomException
	 */
	void update(ShipmentVerificationModel shipmentVerification) throws CustomException;

	/**
	 * Get shipment verification information
	 * 
	 * @param shipmentVerification
	 * @return ShipmentVerificationModel
	 * @throws CustomException
	 */
	ShipmentVerificationModel get(ShipmentVerificationModel shipmentVerification) throws CustomException;

	Integer updateDgList(ShipmentVerificationModel shipmentVerification) throws CustomException;

	void insertDgList(ShipmentVerificationModel shipmentVerification)throws CustomException;

}