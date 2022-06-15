package com.ngen.cosys.impbd.shipment.breakdown.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentHouseModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;

public interface InboundBreakdownDAO {

	InboundBreakdownModel get(InboundBreakdownModel inboundBreakdownModel) throws CustomException;

	Boolean checkBreakDownStorageInfo(InboundBreakdownShipmentInventoryModel inboundBreakdownShipmentInventoryModel)
			throws CustomException;

	Integer insertBreakDownStorageInfo(InboundBreakdownShipmentInventoryModel inboundBreakdownShipmentInventoryModel)
			throws CustomException;

	Integer updateBreakDownStorageInfo(InboundBreakdownShipmentInventoryModel inboundBreakdownShipmentInventoryModel)
			throws CustomException;

	InboundBreakdownShipmentInventoryModel selectInboundBreakdownShipmentInventoryModel(
			InboundBreakdownShipmentInventoryModel inboundBreakdownShipmentInventoryModel) throws CustomException;

	Boolean checkBreakDownShipmentHouseModel(InboundBreakdownShipmentHouseModel inboundBreakdownShipmentHouseModel)
			throws CustomException;

	Integer insertBreakDownShipmentHouseModel(InboundBreakdownShipmentHouseModel inboundBreakdownShipmentHouseModel)
			throws CustomException;

	Integer updateBreakDownShipmentHouseModel(InboundBreakdownShipmentHouseModel inboundBreakdownShipmentHouseModel)
			throws CustomException;

	Boolean checkBreakDownStorageSHCInfo(InboundBreakdownShipmentShcModel inboundBreakdownShipmentShcModel)
			throws CustomException;

	Integer insertBreakDownStorageSHCInfo(InboundBreakdownShipmentShcModel inboundBreakdownShipmentShcModel)
			throws CustomException;

	Integer updateBreakDownStorageSHCInfo(InboundBreakdownShipmentShcModel inboundBreakdownShipmentShcModel)
			throws CustomException;

	Integer insertBreakDownULDTrolleyInfo(InboundBreakdownShipmentModel inboundBreakdownShipmentModel)
			throws CustomException;

	Integer updateBreakDownULDTrolleyInfo(InboundBreakdownShipmentModel inboundBreakdownShipmentModel)
			throws CustomException;

	InboundBreakdownShipmentModel selectBreakDownULDTrolleyInfo(
			InboundBreakdownShipmentModel inboundBreakdownShipmentModel) throws CustomException;

	InboundBreakdownShipmentModel checkTransferType(InboundBreakdownShipmentModel shipmentData) throws CustomException;

	InboundBreakdownShipmentModel checkHandlingMode(InboundBreakdownShipmentModel shipmentData) throws CustomException;

	void updateAgentPlanningWorksheetShipmentStatus(ShipmentMaster requestModel) throws CustomException;

	// Check Shipment type

	InboundBreakdownShipmentModel fetchShipmentType(InboundBreakdownShipmentModel inboundBreakdownModel)
			throws CustomException;

	void deleteStorageInfo(InboundBreakdownShipmentInventoryModel inventoryData) throws CustomException;

	void deleteTrolleyInfo(InboundBreakdownShipmentModel inventoryData) throws CustomException;

	void deleteInventoryInfo(InboundBreakdownShipmentModel inventoryData) throws CustomException;
	
	boolean checkInventoryExists(InboundBreakdownShipmentInventoryModel inventoryData) throws CustomException;

	void deleteStorageSHCInfo(InboundBreakdownShipmentInventoryModel inventoryData) throws CustomException;

	void deleteInventorySHCInfo(InboundBreakdownShipmentModel inventoryData) throws CustomException;

	List<InboundBreakdownShipmentModel> fetchInventoryId(InboundBreakdownShipmentModel shipmentId)
			throws CustomException;
	
	List<InboundBreakdownShipmentModel> fetchHouseInventoryId(InboundBreakdownShipmentModel shipmentId)
			throws CustomException;

	InboundBreakdownShipmentModel fetchHouseDetails(InboundBreakdownShipmentModel inboundBreakdownModel)
			throws CustomException;

	// check BD Completed

	BigInteger checkDeliveryInitiated(InboundBreakdownShipmentInventoryModel inboundBreakdownModel)
			throws CustomException;
	
	BigInteger checkTrmInitiated(InboundBreakdownShipmentInventoryModel inboundBreakdownModel)
			throws CustomException;

	/**
	 * Method to check shipment inventory + freight out pieces/weight is greater
	 * than shipment master piece/weight
	 * 
	 * @param requestModel
	 *            - Contains shipment and flight piece/weight information
	 * @return Boolean - TRUE if greater otherwise false
	 * @throws CustomException
	 */
	Boolean isInventoryPieceWeightGreaterThanShipmentMasterPieceWeight(InboundBreakdownShipmentModel requestModel)
			throws CustomException;

	InboundBreakdownShipmentModel getUtilisedPieces(InboundBreakdownShipmentModel requestModel) throws CustomException;

	InboundBreakdownShipmentModel getFreightOutUtilisedPieces(InboundBreakdownShipmentModel requestModel)
			throws CustomException;

	List<String> getChegeableLocationSHC(InboundBreakdownShipmentInventoryModel value) throws CustomException;

	List<String> getInventoryBillingGroup(InboundBreakdownShipmentInventoryModel value) throws CustomException;

	List<String> getInventoryBillingGroupHandlingCodes(InboundBreakdownShipmentInventoryModel value)
			throws CustomException;
	
	/**
	 * 
	 * @param value
	 * @return DeliveryRequestOrderNumber and AssignUldTrollyInfo
	 * @throws CustomException
	 */
	InboundBreakdownShipmentInventoryModel validateInventory(InboundBreakdownShipmentInventoryModel value)
			throws CustomException;

	/**
	 * @param shipmentModel
	 * @return BigInterger
	 */
	BigInteger getTotalRcfPicesFormStatusUpdateEvent(InboundBreakdownShipmentModel shipmentModel)
			throws CustomException;

	LocalDateTime getLastUpdatedDateTime (InboundBreakdownShipmentModel shipmentModel)
			throws CustomException;
	
	boolean checkValidWarehouseForUser(InboundBreakdownShipmentInventoryModel data) throws CustomException;
	
	boolean checkIfWarehouseLocationExist(InboundBreakdownShipmentInventoryModel data) throws CustomException;
	
	BigInteger isInventoryExistInLocation(InboundBreakdownShipmentInventoryModel s) throws CustomException;
	
	Boolean isRampUldVerified(InboundBreakdownShipmentInventoryModel inventory) throws CustomException;
	
	InboundBreakdownShipmentModel getTotalHousePiecesWeight(InboundBreakdownShipmentModel shipmentModel) throws CustomException;
	
}
