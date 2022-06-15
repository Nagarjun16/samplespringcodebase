package com.ngen.cosys.satssginterfaces.mss.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterface;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceCustomerAddressInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceCustomerContactInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceCustomerInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceHandlingArea;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceRoutingInfo;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceShc;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentMasterAirmailInterfaceShcHandlingGroup;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentOtherChargeInfoAirmailInterface;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentVerificationAirmailInterfaceModel;


public interface ShipmentMasterAirmailInterfaceDAO {

	/**
	 * Method to get FWB information for a given AWB
	 * 
	 * @param shipmentMaster
	 * @return ShipmentMaster - FWB Info
	 * @throws CustomException
	 */
	public ShipmentMasterAirmailInterface getFwbInfo(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException;

	/**
	 * Method to get shipment master info
	 * 
	 * @param shipmentMaster
	 * @return ShipmentMaster
	 * @throws CustomException
	 */
	public ShipmentMasterAirmailInterface getShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException;

	/**
	 * Method to check whether a shipment is an part shipment or not
	 * 
	 * @param shipmentMaster
	 * @return Boolean - If true then this is an part shipment otherwise false
	 * @throws CustomException
	 */
	public Boolean isPartShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException;

	/**
	 * Method to check whether a shipment is an service shipment or not
	 * 
	 * @param shipmentMaster
	 * @return Boolean - If true then this is an service shipment otherwise false
	 * @throws CustomException
	 */
	public Boolean isSVCShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException;

	/**
	 * Method to create shipment master
	 * 
	 * @param shipmentMaster
	 * @throws CustomException
	 */
	public void createShipment(ShipmentMasterAirmailInterface shipmentMaster) throws CustomException;

	/**
	 * Method to create shipment customer address info
	 * 
	 * @param shipmentMasterCustomerAddressInfo
	 * @throws CustomException
	 */
	public void createShipmentMasterCustomerAddressInfo(
			ShipmentMasterAirmailInterfaceCustomerAddressInfo shipmentMasterCustomerAddressInfo) throws CustomException;

	/**
	 * Method to create shipment customer contact info
	 * 
	 * @param shipmentMasterCustomerContactInfo
	 * @throws CustomException
	 */
	public void createShipmentMasterCustomerContactInfo(List<ShipmentMasterAirmailInterfaceCustomerContactInfo> contacts)
			throws CustomException;

	/**
	 * Method to create shipment customer info
	 * 
	 * @param shipmentMasterCustomerInfo
	 * @throws CustomException
	 */
	public void createShipmentMasterCustomerInfo(ShipmentMasterAirmailInterfaceCustomerInfo shipmentMasterCustomerInfo)
			throws CustomException;

	/**
	 * @param shipmentMasterHandlingArea
	 * @return
	 * @throws CustomException
	 */
	public void createShipmentMasterHandlingArea(ShipmentMasterAirmailInterfaceHandlingArea shipmentMasterHandlingArea)
			throws CustomException;

	/**
	 * Method to create shipment master routing info
	 * 
	 * @param shipmentMasterRoutingInfo
	 * @throws CustomException
	 */
	public void createShipmentMasterRoutingInfo(List<ShipmentMasterAirmailInterfaceRoutingInfo> routing) throws CustomException;

	/**
	 * Method to create shipment master shc
	 * 
	 * @param shipmentMasterShc
	 * @throws CustomException
	 */
	public void createShipmentMasterShc(List<ShipmentMasterAirmailInterfaceShc> shcs) throws CustomException;

	/**
	 * Method to create shipment master shc handling group
	 * 
	 * @param shipmentMasterShcHandlingGroup
	 * @throws CustomException
	 */
	public void createShipmentMasterShcHandlingGroup(List<ShipmentMasterAirmailInterfaceShcHandlingGroup> shcHandlingGroup)
			throws CustomException;

	/**
	 * Method to create shipment other charge info
	 * 
	 * @param shipmentOtherChargeInfo
	 * @throws CustomException
	 */
	public void createShipmentOtherChargeInfo(ShipmentOtherChargeInfoAirmailInterface shipmentOtherChargeInfo) throws CustomException;

	public void updateShipmentDocumentReceivedOn(ShipmentVerificationAirmailInterfaceModel shipmentVerModel) throws CustomException;
	
}