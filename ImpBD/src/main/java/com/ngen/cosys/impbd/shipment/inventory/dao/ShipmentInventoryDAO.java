/**
 * This is a repository interface for persisting shipment inventory/shipment master house and shc
 */
package com.ngen.cosys.impbd.shipment.inventory.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownHAWBModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentHouseModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundUldFlightModel;

public interface ShipmentInventoryDAO {

	/**
	 * Method to get the inventory id for a given shipment location information
	 * 
	 * @param inventory
	 * @return BigInteger - inventory id
	 * @throws CustomException
	 */
	BigInteger getInventory(InboundBreakdownShipmentInventoryModel inventory) throws CustomException;

	/**
	 * Method to create the shipment inventory
	 * 
	 * @param inventory
	 * @throws CustomException
	 */
	void createInventory(InboundBreakdownShipmentInventoryModel inventory) throws CustomException;

	/**
	 * Method to increase piece/weight of a inventory
	 * 
	 * @param inventory
	 * @throws CustomException
	 */
	void updateInventory(InboundBreakdownShipmentInventoryModel inventory) throws CustomException;

	/**
	 * Check shc association exists for a inventory
	 * 
	 * @param shc
	 * @return Boolean - if exists true otherwise false
	 * @throws CustomException
	 */
	Boolean getShc(InboundBreakdownShipmentShcModel shc) throws CustomException;

	/**
	 * Method to create shipment inventory shc
	 * 
	 * @param shc
	 * @throws CustomException
	 */
	void createInventoryShc(InboundBreakdownShipmentShcModel shc) throws CustomException;

	/**
	 * Method to get the shipment house id
	 * 
	 * @param house
	 * @return BigInteger - return the shipment house id
	 * @throws CustomException
	 */
	BigInteger getShipmentMasterHouse(InboundBreakdownShipmentHouseModel house) throws CustomException;

	/**
	 * Method to create a house for an shipment
	 * 
	 * @param house
	 * @throws CustomException
	 */
	void createShipmentMasterHouse(InboundBreakdownShipmentHouseModel house) throws CustomException;

	/**
	 * Method to update a house for an shipment
	 * 
	 * @param house
	 * @throws CustomException
	 */
	void updateShipmentMasterHouse(InboundBreakdownShipmentHouseModel house) throws CustomException;

	/**
	 * Method to check whether an association of a house with inventory exists or
	 * not
	 * 
	 * @param house
	 * @return Boolean - if exists true otherwise false
	 * @throws CustomException
	 */
	Boolean getInventoryHouse(InboundBreakdownShipmentHouseModel house) throws CustomException;

	/**
	 * Method to associate inventory with house
	 * 
	 * @param house
	 * @throws CustomException
	 */
	void createInventoryHouse(InboundBreakdownShipmentHouseModel house) throws CustomException;

	/**
	 * Method to update inventory house
	 * 
	 * @param house
	 * @throws CustomException
	 */
	void updateInventoryHouse(InboundBreakdownShipmentHouseModel house) throws CustomException;
	
	
	List<InboundUldFlightModel>getFlightInforForULd(InboundUldFlightModel breakdownModel)throws CustomException;
	
	/**
	 * Delete inventory by line item level
	 * @param breakdownModel
	 * @throws CustomException
	 */
	void deleteInventory(InboundBreakdownShipmentInventoryModel breakdownModel)throws CustomException;
	
	/**
	 * Create HAWB info for Found Cargo
	 * @param hawbInfo
	 * @throws CustomException
	 */
	void createHouseAWBInfo(InboundBreakdownHAWBModel hawbInfo)throws CustomException;
	
	/**
	 * 
	 * @param requestData
	 * @return
	 * @throws CustomException
	 */
	InboundBreakdownShipmentModel getShipmentHousesBreakDownPices(InboundBreakdownShipmentModel requestData) throws CustomException;

}