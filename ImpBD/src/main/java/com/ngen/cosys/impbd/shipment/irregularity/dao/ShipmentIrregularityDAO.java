package com.ngen.cosys.impbd.shipment.irregularity.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;

public interface ShipmentIrregularityDAO {

	void insert(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException;

	ShipmentIrregularityModel get(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException;

	void delete(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException;
	
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