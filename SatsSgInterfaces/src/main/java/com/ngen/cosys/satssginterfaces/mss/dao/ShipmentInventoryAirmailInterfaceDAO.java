/**
 * This is a repository interface for persisting shipment inventory/shipment master house and shc
 */
package com.ngen.cosys.satssginterfaces.mss.dao;

import java.math.BigInteger;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentHouseAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentInventoryAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentShcAirmailInterfaceModel;

public interface ShipmentInventoryAirmailInterfaceDAO {

	/**
	 * Method to get the inventory id for a given shipment location information
	 * 
	 * @param inventory
	 * @return BigInteger - inventory id
	 * @throws CustomException
	 */
	BigInteger getInventory(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory) throws CustomException;

	/**
	 * Method to create the shipment inventory
	 * 
	 * @param inventory
	 * @throws CustomException
	 */
	void createInventory(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory) throws CustomException;

	/**
	 * Method to increase piece/weight of a inventory
	 * 
	 * @param inventory
	 * @throws CustomException
	 */
	void updateInventory(InboundBreakdownShipmentInventoryAirmailInterfaceModel inventory) throws CustomException;

	/**
	 * Check shc association exists for a inventory
	 * 
	 * @param shc
	 * @return Boolean - if exists true otherwise false
	 * @throws CustomException
	 */
	Boolean getShc(InboundBreakdownShipmentShcAirmailInterfaceModel shc) throws CustomException;

	/**
	 * Method to create shipment inventory shc
	 * 
	 * @param shc
	 * @throws CustomException
	 */
	void createInventoryShc(InboundBreakdownShipmentShcAirmailInterfaceModel shc) throws CustomException;

	/**
	 * Method to get the shipment house id
	 * 
	 * @param house
	 * @return BigInteger - return the shipment house id
	 * @throws CustomException
	 */
	BigInteger getShipmentMasterHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException;

	/**
	 * Method to create a house for an shipment
	 * 
	 * @param house
	 * @throws CustomException
	 */
	void createShipmentMasterHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException;

	/**
	 * Method to update a house for an shipment
	 * 
	 * @param house
	 * @throws CustomException
	 */
	void updateShipmentMasterHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException;

	/**
	 * Method to check whether an association of a house with inventory exists or
	 * not
	 * 
	 * @param house
	 * @return Boolean - if exists true otherwise false
	 * @throws CustomException
	 */
	Boolean getInventoryHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException;

	/**
	 * Method to associate inventory with house
	 * 
	 * @param house
	 * @throws CustomException
	 */
	void createInventoryHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException;

	/**
	 * Method to update inventory house
	 * 
	 * @param house
	 * @throws CustomException
	 */
	void updateInventoryHouse(InboundBreakdownShipmentHouseAirmailInterfaceModel house) throws CustomException;

}