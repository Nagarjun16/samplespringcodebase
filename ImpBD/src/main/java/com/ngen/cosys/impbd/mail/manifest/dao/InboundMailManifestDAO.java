/**
 * InboundMailManifetDAO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          08 Feb, 2018    NIIT      -
 */
package com.ngen.cosys.impbd.mail.manifest.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestModel;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestShipmentInfoModel;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestShipmentInventoryInfoModel;
import com.ngen.cosys.impbd.mail.manifest.model.TransferCN46FromManifestModel;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;

public interface InboundMailManifestDAO {
	InboundMailManifestModel flightId(InboundMailManifestModel reqparam) throws CustomException;

	/**
	 * get code InboundMailManifest dao
	 * 
	 * @return fetch the data on the call of search
	 * @throws CustomException
	 */
	InboundMailManifestModel search(InboundMailManifestModel reqParam) throws CustomException;

	/**
	 * get code InboundMailManifest dao
	 * 
	 * @return insert the data on the call of transfer to cn46
	 * @throws CustomException
	 */
	TransferCN46FromManifestModel transferToCN46(TransferCN46FromManifestModel reqParam) throws CustomException;

	TransferCN46FromManifestModel transfer(TransferCN46FromManifestModel reqParam) throws CustomException;

	/**
	 * get code InboundMailManifest dao
	 * 
	 * @return if the value already exist in the db
	 * @throws CustomException
	 */
	Integer checkTransferToCN46(InboundMailManifestShipmentInfoModel reqParam) throws CustomException;

	Integer checkId(InboundMailManifestModel reqParam) throws CustomException;

	Boolean checkDocumentCompleted(InboundMailManifestModel reqParam) throws CustomException;

	Boolean checkBreakDownComplete(InboundMailManifestModel reqParam) throws CustomException;

	InboundMailManifestModel documentComplete(InboundMailManifestModel reqParam) throws CustomException;

	InboundMailManifestModel breakDownComplete(InboundMailManifestModel reqParam) throws CustomException;

	InboundMailManifestModel afterBreakDownComplete(InboundMailManifestModel reqParam) throws CustomException;

	Boolean checkBreakdownIfAlreadyDone(InboundMailManifestModel reqParam) throws CustomException;

	InboundMailManifestModel reOpenBreakDown(InboundMailManifestModel reqParam) throws CustomException;

	InboundMailManifestModel reopenBreakdownIfAlreadyDone(InboundMailManifestModel reqParam) throws CustomException;

	InboundMailManifestModel afterDocumentComplete(InboundMailManifestModel reqParam) throws CustomException;

	Boolean checkDocumentIfAlreadyDone(InboundMailManifestModel reqParam) throws CustomException;

	InboundMailManifestModel reOpenDocument(InboundMailManifestModel reqParam) throws CustomException;

	InboundMailManifestModel reopenDocumentIfAlreadyDone(InboundMailManifestModel reqParam) throws CustomException;

	int updateLocation(InboundMailManifestShipmentInventoryInfoModel reqParam) throws CustomException;

	List<ShipmentIrregularityModel> irregularityInfo(InboundMailManifestModel reqParam) throws CustomException;

	Integer checkDateAta(InboundMailManifestModel reqParam) throws CustomException;

	List<InwardServiceReportShipmentDiscrepancyModel> nonIrregularityData(
			List<InwardServiceReportShipmentDiscrepancyModel> requestModel) throws CustomException;

	void unFinalizeServiceReport(InboundMailManifestModel reqParam) throws CustomException;

	BigInteger getInventory(InboundMailManifestShipmentInventoryInfoModel updateLocationModel) throws CustomException;

	BigInteger getHouseInventory(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException;

	void deleteHouse(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException;

	void deductExistingInventoryPieceWeight(InboundMailManifestShipmentInventoryInfoModel inventory)
			throws CustomException;

	void addNewInventoryPieceWeight(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException;

	void createInventory(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException;

	void createHouseInventory(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException;

	BigInteger getExistingInventory(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException;

	String getStoreLocationType(String storageLocation) throws CustomException;

	List<TransferCN46FromManifestModel> getTransferToCn46Data(InboundMailManifestShipmentInfoModel requestModel)
			throws CustomException;

	Integer getExistingRecordOfAirmanifets(TransferCN46FromManifestModel reqModel) throws CustomException;

	Integer checkMailBagLoaded(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException;

	String loadedHouse(InboundMailManifestShipmentInventoryInfoModel mailBags) throws CustomException;

	String checkContentCode(String storageLocation) throws CustomException;

	int getLoadedSHC(String storageLocation) throws CustomException;

	String getContainerDestination(String storageLocation) throws CustomException;

	void updateNextDestinationOfMailBags(List<InboundMailManifestShipmentInventoryInfoModel> updateLocationModel)
			throws CustomException;

}
