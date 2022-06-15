package com.ngen.cosys.shipment.dao;

import java.math.BigInteger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.events.payload.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent;
import com.ngen.cosys.export.booking.model.SingleShipmentBooking;
import com.ngen.cosys.export.commonbooking.model.PartSuffix;
import com.ngen.cosys.export.commonbooking.service.CommonBookingService;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.constant.ImportExportIndicator;
import com.ngen.cosys.shipment.enums.CRUDStatus;
import com.ngen.cosys.shipment.model.ArrivalFlightInfo;
import com.ngen.cosys.shipment.model.SearchShipmentLocation;
import com.ngen.cosys.shipment.model.ShipmentInventory;
import com.ngen.cosys.shipment.model.ShipmentInventoryShcModel;
import com.ngen.cosys.shipment.model.ShipmentInventoryWorkingListModel;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.uldinfo.model.UldInfoModel;

@Repository("maintainShipmentLocationDAO")
public class MaintainShipmentLocationDAOImpl extends BaseDAO implements MaintainShipmentLocationDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionShipment;
	
	@Autowired
	private CommonBookingService commonBookingService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#getSearchedLoc(com.
	 * ngen.cosys.shipment.model.SearchShipmentLocation)
	 */
	@Override
	public ShipmentMaster getSearchedLoc(SearchShipmentLocation paramAWB) throws CustomException {
		//Added as part of JV01-121
		ShipmentMaster shipment = new ShipmentMaster();
		if (Optional.ofNullable(paramAWB.getHwbNumber()).isPresent() && !StringUtils.isEmpty(paramAWB.getHwbNumber())) {
			shipment = super.fetchObject("searchShipmentHouseLocation", paramAWB, sqlSessionShipment);
			if (Optional.ofNullable(shipment.getHouseInformation()).isPresent()
					&& Optional.ofNullable(shipment.getHouseInformation().getHwbNumber()).isPresent()) {
				shipment.setHwbNumber(shipment.getHouseInformation().getHwbNumber());
			}
		}
		else {
			shipment = super.fetchObject("searchShipmentLocation", paramAWB, sqlSessionShipment);
		}
		// If the object is not empty
		if (!ObjectUtils.isEmpty(shipment)) {
			shipment.setTerminal(paramAWB.getTerminal());

			if (StringUtils.isEmpty(shipment.getShipmentTypeflag())) {
				this.setImportExportFlag(shipment);
			}
			if (shipment.getShipmentTypeflag().equalsIgnoreCase("IMPORT") || shipment.getShipmentTypeflag().equalsIgnoreCase("TRANS")) {
				List<ArrivalFlightInfo> arrivalFlightInfo = super.fetchList("fetchFlightforImport", paramAWB,
						sqlSessionShipment);
				shipment.setArrivalFltlist(arrivalFlightInfo);
				// If record for Flight then setting Flight Id to fetch Last Date Time
				if (!CollectionUtils.isEmpty(arrivalFlightInfo)) {
					shipment.setFlightId(arrivalFlightInfo.get(0).getFlight_ID());
					shipment.setShipmentDate(paramAWB.getShipmentDate());
				}
				// For Fetching and setting in Modifies On the Last Date Time for single point of Transaction			
				shipment.setLastUpdatedTime(super.fetchObject("getLastUpdatedTimeforShipmentVerificationSearch", shipment, sqlSessionShipment));
			}
		}
		//Code changes for partsuffix CR
		//get the booking details for the shipment if any
	List<SingleShipmentBooking>	bookingDetails =super.fetchList("searchBooking", paramAWB, sqlSessionShipment);
	if(!CollectionUtils.isEmpty(bookingDetails)) {
		Set<String> shipmentDuplicateSet = new HashSet<>();
		 bookingDetails.stream()
		            .filter(e -> shipmentDuplicateSet.add(e.getPartSuffix()))
		            .collect(Collectors.toList());
		shipment.setBookingPieces(bookingDetails.get(0).getPieces());
		shipment.setBookingWeight(bookingDetails.get(0).getGrossWeight());
		shipment.setBookingDetails(bookingDetails);
	}
	//Get the documnet verification details if any.
	List<SingleShipmentBooking> documentVerifictionList = super.fetchList("sqlGetDocumentVerificationDetails", paramAWB, sqlSessionShipment); //
	if(!CollectionUtils.isEmpty(documentVerifictionList)) {
		shipment.setDocArrivalInfoList(documentVerifictionList);
	}
	
	List<String> suffixList = super.fetchList("getBookingPartSuffix", paramAWB.getShipmentNumber(), sqlSessionShipment); //
	shipment.setPartSuffixList(suffixList);
	//Changed to accomodate changes done by Nishant
	PartSuffix partSuffix = new PartSuffix();
	partSuffix.setShipmentNumber(shipment.getShipmentNumber());
	partSuffix.setShipmentDate(shipment.getShipmentDate());
	if(!shipment.getShipmentType().equalsIgnoreCase("MAIL")) {
		shipment.setFlagForPartSuffixDropdown(this.commonBookingService.methodToCheckSQOrOalShipment(partSuffix));
	}

	shipment.getShipmentInventories().forEach(shp  -> {
		if( shp.getAutoloadFlag() && !StringUtils.isEmpty(shp.getPartSuffix())) {
			shp.setAutoloadFlag(true);
		}else shp.setAutoloadFlag(false);
	
		
	}
	);
	for(int j=0;j<shipment.getShipmentInventories().size();j++) {
		if (!StringUtils.isEmpty(shipment.getShipmentInventories().get(j).getWarehouseLocation())) {
	           
            try {
            	ShipmentInventory data = new ShipmentInventory();
            	data.setWarehouseLocation(shipment.getShipmentInventories().get(j).getWarehouseLocation());
            	data.setLoggedInUser(paramAWB.getLoggedInUser());
            	//Check if it is a location in the warehouse, then only give it access
            	if(checkIfWarehouseLocationExist(data)) {
            		//If location is present in db then access is given, based on the Terminal Access Logic
            		shipment.getShipmentInventories().get(j).setAccessLocation(!checkValidWarehouseForUser(data));
            	}
            	else {
            		//If location does not exist in db then we have access to it
            		shipment.getShipmentInventories().get(j).setAccessLocation(false);
            	}
			//	System.out.println(shipment.getShipmentInventories().get(j).getAccessLocation()+"Value");
			
			} catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
		
	}
	
	
		return shipment;
	}

	@Override
	public boolean fetchAllMasterShcs(String shcInvShc) throws CustomException {
		return super.fetchObject("searchMasterSHCs", shcInvShc, sqlSessionShipment);
	}

	@Override
	public Integer getAbandonedFlag(SearchShipmentLocation paramAWB) throws CustomException {
		return super.fetchObject("searchAbandonedFlag", paramAWB, sqlSessionShipment);
	}

	private void setImportExportFlag(ShipmentMaster existingDM) {
		if (MultiTenantUtility.isTenantCityOrAirport(existingDM.getOrigin())) {
			existingDM.setShipmentTypeflag(ImportExportIndicator.EXPORT.toString());
		} else if (MultiTenantUtility.isTenantCityOrAirport(existingDM.getDestination())) {
			existingDM.setShipmentTypeflag(ImportExportIndicator.IMPORT.toString());
		} else {
			existingDM.setShipmentTypeflag(ImportExportIndicator.TRANS.toString());
		}
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#getMergedLoc(java.
	 * util.List)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_LOCATION)
	public void getMergedLoc(ShipmentMaster paramAWB) throws CustomException {
		for (ShipmentInventory paramAWBresult : paramAWB.getShipmentInventories()) {
			switch (paramAWBresult.getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				paramAWBresult.setShcListInv((ArrayList<ShipmentInventoryShcModel>) paramAWBresult.getShcListInv());
				if (paramAWBresult.getFlightId() == 0) {
					super.insertData("insertMergedLocationForFlight", paramAWBresult, sqlSessionShipment);
				} else
					super.insertData("insertMergedLocation", paramAWBresult, sqlSessionShipment);
				for (ShipmentInventoryShcModel a : paramAWBresult.getShcListInv()) {
					paramAWBresult.setSpecialHandlingCodeInv(a.getShcInv());
					if (paramAWBresult.getSpecialHandlingCodeInv() != null) {
						super.insertData("insertInventorySHC", paramAWBresult, sqlSessionShipment);
					}
				}

				break;
			case CRUDStatus.CRUDType.DELETE:
				super.deleteData("deleteInventorySHC", paramAWBresult, sqlSessionShipment);
				super.deleteData("deletelocations", paramAWBresult, sqlSessionShipment);
				break;
			default:
				break;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#getUpdatedLoc(java.
	 * util.List)
	 */
	@Override
	@Transactional
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_LOCATION)
	public void getUpdatedLoc(ShipmentInventory paramFlightresult) throws CustomException {
	      super.insertData("insertAddedRecords", paramFlightresult, sqlSessionShipment);
	}
	
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_LOCATION)
	public void getUpdatedInventoryLoc(ShipmentInventory paramFlightresult) throws CustomException {
		//Added as part of JV01-121
		if(Optional.ofNullable(paramFlightresult.getHouseId()).isPresent()) {
		  super.updateData("updateRecords", paramFlightresult, sqlSessionShipment);	
	   }
	   else {
		   super.updateData("updateRecords1", paramFlightresult, sqlSessionShipment);	
	   }
	}


	@Override
	public List<String> getChegeableLocationSHC(ShipmentInventory value) throws CustomException {
		return super.fetchList("chargeableSHCs", value, sqlSessionShipment);
	}

	@Override
	public List<String> getInventoryBillingGroup(ShipmentInventory value) throws CustomException {
		return super.fetchList("inventoryBillingGroups", value, sqlSessionShipment);
	}

	@Override
	public List<String> getInventoryBillingGroupHandlingCodes(ShipmentInventory value) throws CustomException {

		return super.fetchList("inventoryBillingGroupsSpecialHandlingCodeLocation", value, sqlSessionShipment);
	}

	@Override
	public void getupdatedDeliveredOn(ShipmentMaster paramAWB) throws CustomException {
		super.updateData("updateDeliveredOn", paramAWB, sqlSessionShipment);

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * validateShipmentNumber(com.ngen.cosys.shipment.model.SearchShipmentLocation)
	 */
	@Override
	public boolean validateShipmentNumber(SearchShipmentLocation paramAWB) throws CustomException {
		return paramAWB.getShipmentNumber()
				.equalsIgnoreCase((String) super.fetchObject("searchForshipmentNumber", paramAWB, sqlSessionShipment));

	}
	
	@Override
	public boolean validateShipmentForWeighing(SearchShipmentLocation paramAWB) throws CustomException {
		int shpCount = this.fetchObject("checkshipmentIfweighingDone", paramAWB, sqlSessionShipment);
		return (shpCount > 0 ? true : false);
	}
	
	@Override
	public boolean validateShipmentNumberForAcceptance(SearchShipmentLocation paramAWB) throws CustomException {
		int shpCount = this.fetchObject("checkshipmentIfInAcceptance", paramAWB, sqlSessionShipment);
		return (shpCount > 0 ? true : false);
	}	

	@Override
	public boolean checkIfInventoryIsUpdated(ShipmentInventory paramAWB) throws CustomException {
		int updatedInvt = this.fetchObject("checkIfInventoryIsUpdated", paramAWB, sqlSessionShipment);
		return (updatedInvt > 0 ? true : false);
	}
	
	@Override
	public void getDeletedInventorySHCs(ShipmentInventory paramAWB) throws CustomException {
	     super.deleteData("deleteInventorySHC", paramAWB, sqlSessionShipment);
	}
	
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_LOCATION)
	public void getDeletedInventory(ShipmentInventory paramAWB) throws CustomException {
		super.deleteData("deletelocations", paramAWB, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * getInventoryFlightInfo(com.ngen.cosys.shipment.model.ShipmentInventory)
	 */
	@Override
	public UldInfoModel getInventoryFlightInfo(ShipmentInventory requestModel) throws CustomException {
		return this.fetchObject("sqlGetShipmentInventoryFlightInfo", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * createShipmentVerification(com.ngen.cosys.shipment.model.ShipmentInventory)
	 */
	@Override
	public void createShipmentVerification(ShipmentInventory requestModel) throws CustomException {
		BigInteger shipmentVerificationId = super.fetchObject("sqlGetShipmentInventoryVerification", requestModel,
				sqlSessionShipment);
		// Get the total Location + Freight Out piece/weight
		ShipmentInventory inventoryFreightOutPiecesWeight = super.fetchObject(
				"sqlGetTotalInventoryPieceWeightByInboundFlight", requestModel, sqlSessionShipment);
		if (!ObjectUtils.isEmpty(inventoryFreightOutPiecesWeight)) {
			requestModel.setPiecesInv(inventoryFreightOutPiecesWeight.getPiecesInv());
			requestModel.setWeightInv(inventoryFreightOutPiecesWeight.getWeightInv());
			//Added as part of JV01-121
			requestModel.setChargeableWeightInv(inventoryFreightOutPiecesWeight.getChargeableWeightInv());
		}

		if (!ObjectUtils.isEmpty(shipmentVerificationId)) {
			requestModel.setParentReferenceId(shipmentVerificationId);
			this.updateData("sqlUpdateShipmentInventoryVerification", requestModel, sqlSessionShipment);
		} else {
			this.insertData("sqlInsertShipmentInventoryVerification", requestModel, sqlSessionShipment);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * createShipmentVerificationULDTrolleyInfo(java.util.List)
	 */
	@Override
	public void createShipmentVerificationULDTrolleyInfo(List<ShipmentInventory> requestModel) throws CustomException {
		// Iterate through the inventory line items and capture in ULD/Trolley Info
		for (ShipmentInventory t : requestModel) {

			// Check whether the Shipment Location is itself used as an Manifested ULD
			Boolean isManifestedULD = this.fetchObject("sqlCheckShipmentLocationIsEqualManifestedULD", t,
					sqlSessionShipment);

			boolean currentShipmentLocationIsEmpty = false;
			// Set the shipment location if it is empty now and old is not empty
			if (!StringUtils.isEmpty(t.getOldShipmentLocation()) && StringUtils.isEmpty(t.getShipmentLocation())) {
				t.setShipmentLocation(t.getOldShipmentLocation());
				t.setBreakDownUld(t.getOldShipmentLocation().toUpperCase());
				currentShipmentLocationIsEmpty = true;
			}

			if (!StringUtils.isEmpty(t.getReferenceDetails())) {
				t.setBreakDownUld(t.getReferenceDetails());
			} else {
				if (!ObjectUtils.isEmpty(isManifestedULD) && isManifestedULD) {
					t.setBreakDownUld(t.getShipmentLocation().toUpperCase());
				} else if (!currentShipmentLocationIsEmpty) {
					t.setBreakDownUld("Bulk");
				}
			}

			// Check record exists for the inventory line if exist update
			BigInteger uldTrolleyInfoforInv = this.fetchObject("sqlCheckTrollyInfoExsitance", t, sqlSessionShipment);
			// Check record exists or not
			BigInteger uldTrolleyInfoId = this.fetchObject("sqlGetShipmentInventoryVerificationULDTrolleyInfo", t,
					sqlSessionShipment);
			t.setReferenceId(uldTrolleyInfoId);
			if (ObjectUtils.isEmpty(uldTrolleyInfoId) && ObjectUtils.isEmpty(uldTrolleyInfoforInv)) {
				this.insertData("sqlInsertShipmentInventoryVerificationULDTrolleyInfo", t, sqlSessionShipment);
			} else {
				this.updateData("sqlUpdateShipmentInventoryVerificationULDTrolleyInfo", t, sqlSessionShipment);
				if (!ObjectUtils.isEmpty(uldTrolleyInfoforInv)) {
					t.setReferenceId(uldTrolleyInfoforInv);
				} else {
					t.setReferenceId(uldTrolleyInfoId);
				}
			}

			// Re-set the shipment location to empty if it was originally empty
			if (currentShipmentLocationIsEmpty) {
				t.setShipmentLocation(null);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * createShipmentVerificationStorageInfo(java.util.List)
	 */
	@Override
	public void createShipmentVerificationStorageInfo(List<ShipmentInventory> requestModel) throws CustomException {
		// Iterate through inventory line items and capture in storage info
		for (ShipmentInventory t : requestModel) {
			
			//If not loaded then update the break down storage info
			if(ObjectUtils.isEmpty(t.getAutoloadFlag()) || (!ObjectUtils.isEmpty(t.getAutoloadFlag()) && !t.getAutoloadFlag())) {
				// If Shipment Location OR Warehouse Location is null then set it to null
				String newShipmentLocation = !StringUtils.isEmpty(t.getShipmentLocation())
						? t.getShipmentLocation().toUpperCase()
						: t.getShipmentLocation();
				String newWarehouseLocation = !StringUtils.isEmpty(t.getWarehouseLocation())
						? t.getWarehouseLocation().toUpperCase()
						: t.getWarehouseLocation();

				if (StringUtils.isEmpty(t.getOldShipmentLocation())) {
					t.setOldShipmentLocation("**");
				}

				if (StringUtils.isEmpty(t.getOldWarehouseLocation())) {
					t.setOldWarehouseLocation("**");
				}

				if (StringUtils.isEmpty(t.getShipmentLocation())) {
					t.setShipmentLocation("**");
				}

				if (StringUtils.isEmpty(t.getWarehouseLocation())) {
					t.setWarehouseLocation("**");
				}

				if (!t.getOldShipmentLocation().equalsIgnoreCase(t.getShipmentLocation())) {
					t.setShipmentLocation(t.getOldShipmentLocation());
				}

				if (!t.getOldWarehouseLocation().equalsIgnoreCase(t.getWarehouseLocation())) {
					t.setWarehouseLocation(t.getOldWarehouseLocation());
				}

				// Check record exists or not
				List<ShipmentInventory> storageInfoIds = this.fetchList("sqlGetShipmentInventoryVerificationStorageInfo", t,
						sqlSessionShipment);
				BigInteger storageInfoId=null;

				// Reset to back to actual ShipmentLocation/WarehouseLocation
				t.setShipmentLocation(newShipmentLocation);
				t.setWarehouseLocation(newWarehouseLocation);

				if (!CollectionUtils.isEmpty(storageInfoIds)) {
					if(storageInfoIds.size() > 1) {
						for (ShipmentInventory shipmentInventory : storageInfoIds) {
							if(shipmentInventory.getStoragePieces().compareTo(t.getPiecesInv()) == 0 ||
									shipmentInventory.getStoragePieces().compareTo(t.getOldPiecesInv())== 0) {
								t.setStorageInfoId(shipmentInventory.getStorageInfoId());
								storageInfoId=shipmentInventory.getStorageInfoId();
							}
						}
					}else {
					  t.setStorageInfoId(storageInfoIds.get(0).getStorageInfoId());
					  storageInfoId=storageInfoIds.get(0).getStorageInfoId();
					}
					this.updateData("sqlUpdateShipmentInventoryVerificationStorageInfo", t, sqlSessionShipment);
				} else {
					this.insertData("sqlInsertShipmentInventoryVerificationStorageInfo", t, sqlSessionShipment);
					storageInfoId = t.getStorageInfoId();
				}

				// Delete all SHC
				this.deleteData("sqlDeleteShipmentInventoryVerificationStorageSHCInfo", t, sqlSessionShipment);

				// Insert SHC's
				if (!CollectionUtils.isEmpty(t.getShcListInv())) {
					for (ShipmentInventoryShcModel shc : t.getShcListInv()) {
						ShipmentInventory breakDownStorageSHCInfo = new ShipmentInventory();
						breakDownStorageSHCInfo.setSpecialHandlingCode(shc.getShcInv());
						breakDownStorageSHCInfo.setStorageInfoId(storageInfoId);
	 
						// Check if SHC exists
						if(!ObjectUtils.isEmpty(storageInfoId)) {
							Boolean shcExists = this.fetchObject("sqlGetShipmentInventoryVerificationStorageSHCInfo",
									breakDownStorageSHCInfo, sqlSessionShipment);
							if (!ObjectUtils.isEmpty(shcExists) && !shcExists) {
								this.insertData("sqlInsertShipmentInventoryVerificationStorageSHCInfo", breakDownStorageSHCInfo,
										sqlSessionShipment);
							}
						}
					}
				}
			}			
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * getShipmentVerification(com.ngen.cosys.shipment.model.ShipmentInventory)
	 */
	@Override
	public BigInteger getShipmentVerification(ShipmentInventory requestModel) throws CustomException {
		return this.fetchObject("sqlGetShipmentInventoryVerification", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * deleteShipmentVerificationStorageInfo(java.util.List)
	 */
	@Override
	public void deleteShipmentVerificationStorageInfo(List<ShipmentInventory> requestModel) throws CustomException {
		// Iterate through inventory line items and capture in storage info
		for (ShipmentInventory t : requestModel) {

			super.deleteData("deleteStorageSHCinfo", t, sqlSessionShipment);

			super.deleteData("deleteStorageInfo", t, sqlSessionShipment);

		}
	}

	@Override
	public void deleteStorageInfo(ShipmentInventory requestModel) throws CustomException {
		// Iterate through inventory line items and capture in storage info

			super.deleteData("deleteStorageSHCinfo", requestModel, sqlSessionShipment);

			super.deleteData("deleteStorageInfo", requestModel, sqlSessionShipment);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * deleteShipmentVerificationULDTrolleyInfo(com.ngen.cosys.shipment.model.
	 * ShipmentInventory)
	 */
	@Override
	public void deleteShipmentVerificationULDTrolleyInfo(ShipmentInventory requestModel) throws CustomException {
		this.deleteData("sqlDeleteShipmentInventoryVerificationULDTrolleyInfo", requestModel, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * deleteShipmentVerification(com.ngen.cosys.shipment.model.ShipmentInventory)
	 */
	@Override
	public void deleteShipmentVerification(ShipmentInventory requestModel) throws CustomException {
		// Get the total Location + Freight Out piece/weight
		ShipmentInventory inventoryFreightOutPiecesWeight = super.fetchObject(
				"sqlGetTotalInventoryPieceWeightByInboundFlight", requestModel, sqlSessionShipment);
		if (!ObjectUtils.isEmpty(inventoryFreightOutPiecesWeight)) {
			requestModel.setPiecesInv(inventoryFreightOutPiecesWeight.getPiecesInv());
			requestModel.setWeightInv(inventoryFreightOutPiecesWeight.getWeightInv());
		}
		if (!ObjectUtils.isEmpty(requestModel.getParentReferenceId())) {
			// Update the piece/weight
			this.updateData("sqlUpdateShipmentInventoryVerification", requestModel, sqlSessionShipment);
			// Delete the Shipment Verification Info if no break down uld/trolley info found
			this.deleteData("sqlDeleteShipmentInventoryVerification", requestModel, sqlSessionShipment);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * checkLocationPiecesIsGreaterOREqualToManifest(com.ngen.cosys.events.payload.
	 * InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent)
	 */
	@Override
	public Boolean checkLocationPiecesIsGreaterOREqualToManifest(
			InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent payload) throws CustomException {
		return this.fetchObject("sqlCheckLocationPiecesIsGreaterOREqualToManifest", payload, sqlSessionShipment);
	}

	@Override
	public String getConditionType(String key) throws CustomException {
		return super.fetchObject("sqlCheckForConditionType", key, sqlSessionShipment);
	}

	@Override
	public ShipmentInventory checkForFlightId(ShipmentInventory value) throws CustomException {
		return super.fetchObject("resultFlightID", value, sqlSessionShipment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * createExportWorkingListShipment(com.ngen.cosys.shipment.model.
	 * ShipmentInventoryWorkingListModel)
	 */
	@Override
	public void createExportWorkingListShipment(ShipmentInventoryWorkingListModel requestModel) throws CustomException {
		// Get the booking info
		List<ShipmentInventoryWorkingListModel> bookedShipments = this.fetchList("sqlQueryShipmentInventoryBookingInfo",
				requestModel, sqlSessionShipment);

         // Check if the bookedShipments is empty then try by Part Suffix
         if (CollectionUtils.isEmpty(bookedShipments) && !CollectionUtils.isEmpty(requestModel.getPartSuffix())) {
            bookedShipments = this.fetchList("sqlQueryShipmentInventoryBookingInfoByPartSuffix", requestModel,
                  sqlSessionShipment);
         }
		
		if (!CollectionUtils.isEmpty(bookedShipments)) {
			for (ShipmentInventoryWorkingListModel shipmentInventoryWorkingListModel : bookedShipments) {
				// Set the shipment id
				shipmentInventoryWorkingListModel.setShipmentId(requestModel.getShipmentId());

				// Check whether flight info exists
				BigInteger workingListId = this.fetchObject("sqlGetShipmentInventoryWorkingListFlightId",
						shipmentInventoryWorkingListModel, sqlSessionShipment);

				// If empty then insert the flight info
				if (ObjectUtils.isEmpty(workingListId)) {
					this.insertData("sqlInsertShipmentInventoryWorkingListFlight", shipmentInventoryWorkingListModel,
							sqlSessionShipment);
				} else {
					// Set the working list id
					shipmentInventoryWorkingListModel.setId(workingListId);
				}

				// Check whether shipment info exists
				BigInteger workingListShipmentId = this.fetchObject("sqlGetShipmentInventoryWorkingListShipmentId",
						shipmentInventoryWorkingListModel, sqlSessionShipment);

				// If empty then insert the shipment info
				if (ObjectUtils.isEmpty(workingListShipmentId)
						|| (!ObjectUtils.isEmpty(workingListShipmentId) && workingListShipmentId.intValue() == 0)) {
					this.insertData("sqlInsertShipmentInventoryWorkingListShipment", shipmentInventoryWorkingListModel,
							sqlSessionShipment);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#isLocationExists(com.
	 * ngen.cosys.shipment.model.ShipmentMaster)
	 */
	@Override
	public boolean isLocationExists(ShipmentMaster paramAWB) throws CustomException {
		int inventoryCount = this.fetchObject("sqlCheckShipmentInventoryExistsForAnShipment", paramAWB,
				sqlSessionShipment);
		return (inventoryCount > 0 ? true : false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#isTRMIssued(com.ngen.
	 * cosys.shipment.model.ShipmentInventory)
	 */
	@Override
	public Boolean isTRMIssued(ShipmentInventory shipmentVerificationModel) throws CustomException {
		int isTRMIssued = this.fetchObject("sqlCheckTRMHasBeenIssuedForShipment", shipmentVerificationModel,
				sqlSessionShipment);
		return (isTRMIssued > 0 ? true : false);
	}

	@Override
	public Boolean checkForAWBFlightDetailsForImport(ShipmentInventory value) throws CustomException {
		int isFlightValid = super.fetchObject("checkForAWBFlightDetailsForImport", value, sqlSessionShipment);
		return (isFlightValid > 0 ? true : false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO#
	 * getImportPiecesInformation(com.ngen.cosys.shipment.model.ShipmentInventory)
	 */
	@Override
	public ShipmentInventory getImportPiecesInformation(ShipmentInventory shipmentVerificationModel)
			throws CustomException {
		return this.fetchObject("sqlGetnboundPiecesInformationForShipmentLocation", shipmentVerificationModel,
				sqlSessionShipment);
	}

	@Override
	public Boolean isShipmentLoaded(ShipmentInventory shipmentVerificationModel) throws CustomException {
		return this.fetchObject("sqlCheckisUldTrollyInfoloaded", shipmentVerificationModel, sqlSessionShipment);
	}
	
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_LOCATION)
    public void getUpdateExportRecords(ShipmentInventory paramFlightresult) throws CustomException {
       super.insertData("insertExportRecords", paramFlightresult, sqlSessionShipment);
   }
    
    public void getUpdatedLocSHC(ShipmentInventory paramFlightresult) throws CustomException {
       super.insertData("insertInventorySHC", paramFlightresult, sqlSessionShipment);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_LOCATION_UTL_INVENTORY)
   public void getInsertUTLPieces(ShipmentInventory obj1) throws CustomException {
      super.insertData("insertUTLRecords", obj1, sqlSessionShipment);
      
   }

   @Override
   public void getinsertUTLRecordsForFlight(ShipmentInventory obj1) throws CustomException {
      super.insertData("insertUTLRecordsForFlightID", obj1, sqlSessionShipment);
      
   }

   @Override
   public void getinsertMergedLocation(ShipmentInventory paramAWBresult) throws CustomException {
      super.insertData("insertMergedLocationForFlight", paramAWBresult, sqlSessionShipment);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_LOCATION_SPLIT_LOCATION)
   public void getMergedLocation(ShipmentInventory paramAWBresult) throws CustomException {
      super.insertData("insertMergedLocation", paramAWBresult, sqlSessionShipment);   
   }

	@Override
	public void getinsertStorageInfoAfterSplitting(ShipmentInventory paramAWBresult) throws CustomException {
		// Check record exists for the inventory line if exist update
		ShipmentInventory nins = new ShipmentInventory();
		nins.setShipmentInventoryId(paramAWBresult.getId());
		BigInteger uldTrolleyInfoforInv = this.fetchObject("sqlCheckTrollyInfoExsitance", nins, sqlSessionShipment);
		if (uldTrolleyInfoforInv != null) {
			paramAWBresult.setUldId(uldTrolleyInfoforInv.intValue());
			super.insertData("insertStorageInfoAfterSplitting", paramAWBresult, sqlSessionShipment);
		}
	}

   @Override
   public void getInsertBreakDownStorageSHCInfoSplitting(ShipmentInventory paramAWBresult) throws CustomException {
      super.insertData("sqlInsertBreakDownStorageSHCInfoSplitting", paramAWBresult, sqlSessionShipment); 
      
   }

   @Override
   public void getupdateStorageInfonSplit(ShipmentInventory paramFlightresult) throws CustomException {
      super.updateData("updateStorageInfonSplit", paramFlightresult, sqlSessionShipment);
      
   }

@Override
public ShipmentInventoryWorkingListModel checkIrregularityForShipmentByFlight(InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent payload)
		throws CustomException {
	return this.fetchObject("sqlCheckIrregularityForShipmentByFlight", payload, sqlSessionShipment);
}

public boolean verifySQGroupCarrier(String awbPrefix) throws CustomException {
	int count = super.fetchObject("sqlVerifySQGroupCarrier", awbPrefix, sqlSessionShipment);
	return count > 0 ? true: false;
}
/**
 * Method to Check Single Transaction point
 * 
 * @param paramAWB
 * @return LocalDateTime
 * 
 * @throws CustomException
 */
@Override
public LocalDateTime getLastUpdatedDateTime (ShipmentMaster paramValue)
		throws CustomException{
	return fetchObject("getLastUpdatedTimeforShipmentVerificationSearch", paramValue, sqlSessionShipment);
}

@Override
public boolean isFlighHandledInSystem(String shipmentNumber) throws CustomException {
	//exclude for TTH cases
	boolean tthFlag =fetchObject("sqlToverifyTTHShipmentForTSM", shipmentNumber, sqlSessionShipment); 
	if(tthFlag) 
		return false;
	//check for booking if booking not there then check enroute
	boolean bookingflag =fetchObject("sqlQueryBookingForTSM_shpMng", shipmentNumber, sqlSessionShipment);
	

	if(bookingflag) {
		// get out bound booking if it has, if it has check for handledInSystem flag
		return fetchObject("sqlCheckFlightHandledInSystem_shpMng", MultiTenantUtility.getAirportCityMap(shipmentNumber), sqlSessionShipment); 
	}
	if(!bookingflag) {
		//else check for the routing 
		 return fetchObject("sqlroutingConfigForTsm_shpMng", MultiTenantUtility.getAirportCityMap(shipmentNumber), sqlSessionShipment);
	}
	return bookingflag;
}

@Override
public boolean isDataSyncCREnabled() throws CustomException {
	// TODO Auto-generated method stub
	String flag = "N";
	flag=fetchObject("sqlCheckDataSyncCREnabledShpMng",null,sqlSessionShipment);
	return flag.equalsIgnoreCase("Y");
}

	@Override
	public boolean checkValidWarehouseForUser(ShipmentInventory data) throws CustomException {
		boolean isRestrictedHandlingAreaExists = false;
		boolean isValidLocation = true;

		isRestrictedHandlingAreaExists = fetchObject("sqlCheckRestrictedHandlingAreaExists", data, sqlSessionShipment);

		if (isRestrictedHandlingAreaExists) {
			isValidLocation = fetchObject("sqlCheckAllowedLocation", data, sqlSessionShipment);
		} else {
			boolean isRestrictedTerminalLocation = fetchObject("sqlCheckLocationInRestrictedTerminal", data,
					sqlSessionShipment);
			if (isRestrictedTerminalLocation) {
				isValidLocation = false;
			}
		}

		return isValidLocation;

	}

@Override
public ShipmentInventory getInboundDetailsForPartsuffix(ShipmentInventory shipmentInventory) throws CustomException {
	return fetchObject("sqlgetInboundDetailsForPartsuffix",shipmentInventory,sqlSessionShipment);
}

@Override
public boolean checkIfPresentInAcceptance(ShipmentMaster paramAWB) throws CustomException {
	return super.fetchObject("checkIfPresentInAcceptance", paramAWB, sqlSessionShipment);
}
@Override
public boolean checkIfWarehouseLocationExist(ShipmentInventory data) throws CustomException {
		return fetchObject("checkIfWarehouseLocationExist",data,sqlSessionShipment);

}

@Override
public Boolean getCustomsInspectionCheck(ShipmentMaster paramAWB) throws CustomException {
	
	return  fetchObject("sqlgetCustomsInspectionCheck",paramAWB,sqlSessionShipment);
}

@Override
public String getCustomsInspectionLocationFromSystemParam() throws CustomException {

	return super.fetchObject("sqlgetCustomsInspectionLocationFormSystemParam", null, sqlSessionShipment);
}


}