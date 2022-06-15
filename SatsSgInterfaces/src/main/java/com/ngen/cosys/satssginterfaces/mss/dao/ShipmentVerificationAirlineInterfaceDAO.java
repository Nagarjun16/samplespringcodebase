/**
 * This is a class which holds entire functionality for managing of shipment verification
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentVerificationAirmailInterfaceModel;

public interface ShipmentVerificationAirlineInterfaceDAO {

	/**
	 * Method to create an shipment verification record
	 * 
	 * @param shipmentVerification
	 * @throws CustomException
	 */
	void create(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException;

	/**
	 * Method to update shipment verification record
	 * 
	 * @param shipmentVerification
	 * @throws CustomException
	 */
	void update(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException;

	/**
	 * Get shipment verification information
	 * 
	 * @param shipmentVerification
	 * @return ShipmentVerificationModel
	 * @throws CustomException
	 */
	ShipmentVerificationAirmailInterfaceModel get(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException;

	Integer updateDgList(ShipmentVerificationAirmailInterfaceModel shipmentVerification) throws CustomException;

	void insertDgList(ShipmentVerificationAirmailInterfaceModel shipmentVerification)throws CustomException;

}