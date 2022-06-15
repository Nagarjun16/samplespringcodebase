/**
 * This is a interface which holds entire functionality for managing of
 * irregularity information based on document/found pieces for shipment.
 * 
 * Usage: 1. Auto-wire this class service interface 2. invoke the
 * createIrregularity() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.irregularity.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;

public interface ShipmentIrregularityService {

   /**
    * Method to create irregularity record for an shipment
    * 
    * @param shipmentIrregularityModel
    * @throws CustomException
    */
   void createIrregularity(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException;

   /**
    * Method to close an irregularity for an shipment
    * 
    * @param shipmentIrregularityModel
    * @throws CustomException
    */
   void closeIrregularity(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException;
   
   
	/**
	 * Method to validate manifested shipment with sum of house break down pieces +
	 * missing - found pieces if it does not matches then consider such shipment to
	 * irregularity incomplete
	 * 
	 * @param shipmentIrregularityModel
	 * @return Boolean - true if incomplete other wise complete
	 * @throws CustomException
	 */
	Boolean validatePieceInfoForMorethanOneHouseShipment(ShipmentIrregularityModel shipmentIrregularityModel)
			throws CustomException;

}