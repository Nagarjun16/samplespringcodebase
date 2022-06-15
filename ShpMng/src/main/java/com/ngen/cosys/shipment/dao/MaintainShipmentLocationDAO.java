package com.ngen.cosys.shipment.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.ngen.cosys.events.payload.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.SearchShipmentLocation;
import com.ngen.cosys.shipment.model.ShipmentInventory;
import com.ngen.cosys.shipment.model.ShipmentInventoryWorkingListModel;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.uldinfo.model.UldInfoModel;

public interface MaintainShipmentLocationDAO {

   ShipmentMaster getSearchedLoc(SearchShipmentLocation paramAWB) throws CustomException;

   boolean fetchAllMasterShcs(String shcInvShc) throws CustomException;

   Integer getAbandonedFlag(SearchShipmentLocation paramAWB) throws CustomException;

   void getMergedLoc(ShipmentMaster paramAWB) throws CustomException;

   ShipmentInventory checkForFlightId(ShipmentInventory value) throws CustomException;

   void getUpdatedLoc(ShipmentInventory paramFlightresult) throws CustomException;
   
   void getUpdatedInventoryLoc(ShipmentInventory paramFlightresult) throws CustomException;
   
   void getUpdatedLocSHC(ShipmentInventory paramFlightresult) throws CustomException;
   
   void getUpdateExportRecords(ShipmentInventory paramFlightresult) throws CustomException;
   
   void getInsertUTLPieces(ShipmentInventory obj1) throws CustomException;
   
   void getinsertUTLRecordsForFlight(ShipmentInventory obj1) throws CustomException;

   List<String> getChegeableLocationSHC(ShipmentInventory value) throws CustomException;

   List<String> getInventoryBillingGroup(ShipmentInventory value) throws CustomException;
   
   List<String> getInventoryBillingGroupHandlingCodes(ShipmentInventory value) throws CustomException;

   void getupdatedDeliveredOn(ShipmentMaster paramAWB) throws CustomException;

   boolean validateShipmentNumber(SearchShipmentLocation paramAWB) throws CustomException;
   
   boolean validateShipmentForWeighing(SearchShipmentLocation paramAWB) throws CustomException;
   
   boolean validateShipmentNumberForAcceptance(SearchShipmentLocation paramAWB) throws CustomException;
   
   boolean checkIfInventoryIsUpdated(ShipmentInventory paramAWB) throws CustomException;
   
   void getDeletedInventorySHCs(ShipmentInventory paramAWB) throws CustomException;

   void getDeletedInventory(ShipmentInventory paramAWB) throws CustomException;

   /**
    * Method to get flight info for a given inventory line item
    * 
    * @param requestModel
    * @return UldInfoModel = Not null if flight info not emtpy otherwise empty
    * @throws CustomException
    */
   UldInfoModel getInventoryFlightInfo(ShipmentInventory requestModel) throws CustomException;

   /**
    * Method to Create Shipment Verification Info
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createShipmentVerification(ShipmentInventory requestModel) throws CustomException;

   /**
    * Method to Create/Update Break Down ULD Trolley info
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createShipmentVerificationULDTrolleyInfo(List<ShipmentInventory> requestModel) throws CustomException;

   /**
    * Method to Create Break Down Storage Info
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createShipmentVerificationStorageInfo(List<ShipmentInventory> requestModel) throws CustomException;

   /**
    * Method to Get Shipment Verification Id
    * 
    * @param requestModel
    * @throws CustomException
    */
   BigInteger getShipmentVerification(ShipmentInventory requestModel) throws CustomException;

   /**
    * Method to delete the Break Down Storage Info
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteShipmentVerificationStorageInfo(List<ShipmentInventory> requestModel) throws CustomException;

   /**
    * Method to delete Break Down ULD Trolley Info
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteShipmentVerificationULDTrolleyInfo(ShipmentInventory requestModel) throws CustomException;

   /**
    * Method to delete the Shipment Verification Info
    * 
    * @param requestModel
    * @throws CustomException
    */
   void deleteShipmentVerification(ShipmentInventory requestModel) throws CustomException;

   /**
    * Method to check location pieces is greater than or equal to manifest pieces
    * 
    * @param payload
    * @return Boolean - TRUE if greater or equal other wise false
    * @throws CustomException
    */
   Boolean checkLocationPiecesIsGreaterOREqualToManifest(InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent payload)
         throws CustomException;
   
   ShipmentInventoryWorkingListModel checkIrregularityForShipmentByFlight(InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent payload)
	         throws CustomException;

   String getConditionType(String key) throws CustomException;

   /**
    * Method to create shipment in export working list for transshipment shipments
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createExportWorkingListShipment(ShipmentInventoryWorkingListModel requestModel) throws CustomException;

   /**
    * Check whether inventory exists for an Shipment OR not
    * 
    * @param paramAWB
    * @return boolean - true if exists otherwise false
    * @throws CustomException
    */
   boolean isLocationExists(ShipmentMaster paramAWB) throws CustomException;

   /**
    * Method to get TRM is issued OR not
    * 
    * @param shipmentVerificationModel
    * @return Boolean - TRUE if issued and FALSE if not issued
    * @throws CustomException
    */
   Boolean isTRMIssued(ShipmentInventory shipmentVerificationModel) throws CustomException;

   Boolean checkForAWBFlightDetailsForImport(ShipmentInventory value) throws CustomException;

   /**
    * Method to get manifest/found and existing break down pieces
    * 
    * @param shipmentVerificationModel
    * @return ShipmentInventory - piece information for deriving whether to amend
    *         OR not break down information
    * 
    * @throws CustomException
    */
   ShipmentInventory getImportPiecesInformation(ShipmentInventory shipmentVerificationModel) throws CustomException;
   
   Boolean isShipmentLoaded(ShipmentInventory shipmentVerificationModel) throws CustomException;
   
   void getinsertMergedLocation(ShipmentInventory paramAWBresult) throws CustomException;

   void getMergedLocation(ShipmentInventory paramAWBresult) throws CustomException; 
   
   void getinsertStorageInfoAfterSplitting(ShipmentInventory paramAWBresult) throws CustomException; 
   
   void getInsertBreakDownStorageSHCInfoSplitting(ShipmentInventory paramAWBresult) throws CustomException; 
   
   void getupdateStorageInfonSplit(ShipmentInventory paramFlightresult) throws CustomException;

   public boolean isFlighHandledInSystem(String awbPreffix) throws CustomException;

   /**
    * Method to Check Single Transaction point
    * 
    * @param paramAWB
    * @return LocalDateTime
    * 
    * @throws CustomException
    */
   LocalDateTime getLastUpdatedDateTime(ShipmentMaster paramAWB) throws CustomException;

   boolean isDataSyncCREnabled() throws CustomException;
   
   boolean checkIfPresentInAcceptance(ShipmentMaster paramAWB) throws CustomException;

boolean checkValidWarehouseForUser(ShipmentInventory data) throws CustomException;

/**
 * @param shipmnetNumber
 * @param shipmentDate
 * @param partSuffix
 * @return
 * @throws CustomException 
 */
ShipmentInventory getInboundDetailsForPartsuffix(ShipmentInventory shipmentInventory) throws CustomException;

boolean checkIfWarehouseLocationExist(ShipmentInventory data) throws CustomException;

void deleteStorageInfo(ShipmentInventory requestModel) throws CustomException;

Boolean getCustomsInspectionCheck(ShipmentMaster paramAWB) throws CustomException;
	
String getCustomsInspectionLocationFromSystemParam() throws CustomException;

}