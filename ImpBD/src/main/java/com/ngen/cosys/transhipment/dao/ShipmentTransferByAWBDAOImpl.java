package com.ngen.cosys.transhipment.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.transhipment.helper.FindShipmentInventoryLinesForTransfer;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWB;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBInfo;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBSHC;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBSearch;

@Repository("ShipmentTransferByAWBDAO")
public class ShipmentTransferByAWBDAOImpl extends BaseDAO implements ShipmentTransferByAWBDAO {

	private static final String SHIPMENT_NUMBER = "shipmentNumber";

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	@Autowired
	private FindShipmentInventoryLinesForTransfer findTransferrableInventory;

	@Override
	public TranshipmentTransferManifestByAWBSearch searchList(TranshipmentTransferManifestByAWBSearch search)
			throws CustomException {
		List<TranshipmentTransferManifestByAWB> trmList = fetchList("selectAWB", search, sqlSession);
		if (!ObjectUtils.isEmpty(trmList)) {
			search.setAwbList(trmList);
		}
		return search;
	}

	@Override
	public TranshipmentTransferManifestByAWB search(TranshipmentTransferManifestByAWBSearch search)
			throws CustomException {
		return fetchObject("selectAWB", search, sqlSession);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public TranshipmentTransferManifestByAWB maintain(TranshipmentTransferManifestByAWB maintain)
			throws CustomException {
		checkForHandler(maintain);
		if (Action.DELETE.toString().equalsIgnoreCase(maintain.getFlagCRUD())) {
			for (TranshipmentTransferManifestByAWBInfo awbInfo : maintain.getAwbInfoList()) {
				maintainTRMAWBSHC(awbInfo);
			}
			maintainTRMAWBInfo(maintain);
		} else {
			if (maintain.getAllowFreightOutAwb()) {
				checkDataExistsFreightOut(maintain);
			} else {
				maintain.setFreightOutAwb(new ArrayList<String>());
			}
			if (CollectionUtils.isEmpty(maintain.getFreightOutAwb())) {
				maintainTRMAWB(maintain);
				maintainTRMAWBInfo(maintain);
				for (TranshipmentTransferManifestByAWBInfo awbInfo : maintain.getAwbInfoList()) {
					if (awbInfo.isSelect()) {
						maintainTRMAWBSHC(awbInfo);
					}
				}
			}
		}
		return maintain;
	}

	private void maintainTRMAWB(TranshipmentTransferManifestByAWB awb) throws CustomException {
		if (Action.CREATE.toString().equalsIgnoreCase(awb.getFlagCRUD())) {
			testIfAlreadyRecordExists(awb.getAwbInfoList());
			insertData("insertTranshipmentAWB", awb, sqlSession);
		} else if (Action.UPDATE.toString().equalsIgnoreCase(awb.getFlagCRUD())) {
			updateData("updateTranshipmentAWB", awb, sqlSession);
		} else if (Action.DELETE.toString().equalsIgnoreCase(awb.getFlagCRUD())) {
			deleteData("deleteTranshipmentAWB", awb, sqlSession);
		}
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_TRM_AWB)
	private void maintainTRMAWBInfo(TranshipmentTransferManifestByAWB awb) throws CustomException {
		for (TranshipmentTransferManifestByAWBInfo awbInfo : awb.getAwbInfoList()) {
			awbInfo.setTrmNumber(awb.getTrmNumber());
			awbInfo.setAirlineNumber(awb.getAirlineNumber());
			awbInfo.setTerminal(awb.getTerminal());
			if(!ObjectUtils.isEmpty(awbInfo.getWeight())) {
				awbInfo.setWeight(awbInfo.getWeight().setScale(1));
			}

			// Check for transfer piece/weight
			if (!Action.DELETE.toString().equalsIgnoreCase(awbInfo.getFlagCRUD()) && awbInfo.isSelect()
					&& awbInfo.getPieces() > awbInfo.getInventoryPieces()
					|| (!ObjectUtils.isEmpty(awbInfo.getWeight()) && !ObjectUtils.isEmpty(awbInfo.getInventoryWeight())
							&& awbInfo.getWeight().doubleValue() > awbInfo.getInventoryWeight().doubleValue())) {
				throw new CustomException("data.transfer.pieces.match", SHIPMENT_NUMBER, ErrorType.ERROR,
						new String[] { awbInfo.getShipmentNumber() });
			}

			if (Action.CREATE.toString().equalsIgnoreCase(awbInfo.getFlagCRUD()) && awbInfo.isSelect()) {
				checkTrmUpdateInventory(awbInfo);
				awbInfo.setTransTransferManifestByAwbId(awb.getTransTransferManifestByAwbId());
				DateTimeFormatter formatter = MultiTenantUtility.getTenantDateFormat();
				awbInfo.setRemarks(
						(awbInfo.getInboundFlightNumber() != null ? awbInfo.getInboundFlightNumber() : "") + " "
								+ (awbInfo.getInboundFlightDate() != null
										? formatter.format(awbInfo.getInboundFlightDate())
										: "")
								+ (awbInfo.getRemarks() != null ? awbInfo.getRemarks() : ""));
				this.insertTranshipmentAWBInfo(awbInfo);
			} else if (Action.UPDATE.toString().equalsIgnoreCase(awbInfo.getFlagCRUD()) && awbInfo.isSelect()) {
				this.updateTranshipmentAWBInfo(awbInfo);
			} else if (Action.DELETE.toString().equalsIgnoreCase(awbInfo.getFlagCRUD()) && awbInfo.isSelect()) {
				this.deleteAWBInfoOnMaintainTRM(awbInfo);
				this.deleteTranshipmentAWBInfo(awbInfo);
			}
		}
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_TRM_AWB)
	public void insertTranshipmentAWBInfo(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		insertData("insertTranshipmentAWBInfo", awbInfo, sqlSession);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_TRM_AWB)
	public void updateTranshipmentAWBInfo(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		updateData("updateTranshipmentAWBInfo", awbInfo, sqlSession);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_TRM_AWB)
	public void deleteTranshipmentAWBInfo(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		deleteData("deleteTranshipmentAWBInfo", awbInfo, sqlSession);
	}

	/**
	 * Delete TRM AWB SHC Info and Nullify the TRM Number in inventory/freight out
	 * 
	 * @param awbInfo
	 * @throws CustomException
	 */
	private void deleteAWBInfoOnMaintainTRM(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		deleteData("deleteTranshipmentAWBSHC", awbInfo, sqlSession);
		updateData("sqlUpdateInventorywithTrmNumberAsNull", awbInfo, sqlSession);
		updateData("sqlUpdateShipmentFreightOutwithTrmNumberAsNull", awbInfo, sqlSession);
	}

	private void checkTrmUpdateInventory(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		boolean checkInvPiecesWithTrmPieces = false;
		if (awbInfo.getPieces() > awbInfo.getInventoryPieces()) {
			throw new CustomException("data.transfer.pieces.match", SHIPMENT_NUMBER, ErrorType.ERROR,
					new String[] { awbInfo.getShipmentNumber() });
		}

		// If local shipment then fetch inventory not based on flight else fetch based
		// on flight
		List<TranshipmentTransferManifestByAWBInfo> totInvList = null;
		List<TranshipmentTransferManifestByAWBInfo> updatedInvLis = null;
		if (MultiTenantUtility.isTenantCityOrAirport(awbInfo.getOrigin())) {
			totInvList = this.fetchList("sqlGetTranshipmentTransferManifestInventoryDetailForExport", awbInfo,
					sqlSession);
		} else {
			totInvList = this.fetchList("sqlGetTranshipmentTransferManifestInventoryDetailForImport", awbInfo,
					sqlSession);
		}

		// Get the matched inventory line items
		updatedInvLis = findTransferrableInventory.findByPiece(totInvList, BigInteger.valueOf((awbInfo.getPieces())));

		// If not empty then update trm number
		if (!CollectionUtils.isEmpty(updatedInvLis)) {
			for (TranshipmentTransferManifestByAWBInfo inv : updatedInvLis) {
				awbInfo.setShipmentId(inv.getShipmentId());
				awbInfo.setShipmentInventoryId(inv.getShipmentInventoryId());
				awbInfo.setShipmentFreightOutId(inv.getShipmentFreightOutId());
				int inventoryRecordFound = 0;

				if (!ObjectUtils.isEmpty(awbInfo.getShipmentInventoryId())
						&& awbInfo.getShipmentInventoryId().intValue() > 0) {
					inventoryRecordFound = updateData(
							"sqlUpdateTranshipmentTransferManifestShipmentInventoryWithTrmNumber", awbInfo, sqlSession);
				} else {
					inventoryRecordFound = updateData(
							"sqlUpdateTranshipmentTransferManifestShipmentFreightOutWithTrmNumber", awbInfo,
							sqlSession);
				}

				if (inventoryRecordFound > 0) {
					checkInvPiecesWithTrmPieces = true;
				}
			}
		}

		if (!checkInvPiecesWithTrmPieces) {
			throw new CustomException("data.transfer.pieces.not.match", SHIPMENT_NUMBER, ErrorType.ERROR,
					new String[] { awbInfo.getShipmentNumber() });
		}

	}

	private void maintainTRMAWBSHC(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		deleteData("deleteTranshipmentAWBSHC", awbInfo, sqlSession);
		if (!CollectionUtils.isEmpty(awbInfo.getShcList())
				&& !Action.DELETE.toString().equalsIgnoreCase(awbInfo.getFlagCRUD())) {
			for (TranshipmentTransferManifestByAWBSHC shcInfo : awbInfo.getShcList()) {
				shcInfo.setTransTransferManifestByAWBInfoId(awbInfo.getTransTransferManifestByAWBInfoId());
				insertData("insertTranshipmentAWBSHC", shcInfo, sqlSession);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public TranshipmentTransferManifestByAWBSearch cancelAWB(TranshipmentTransferManifestByAWBSearch cancelAWB)
			throws CustomException {
		for (TranshipmentTransferManifestByAWB awb : cancelAWB.getAwbList()) {
			updateData("updateTranshipmentAWB", awb, sqlSession);

			if (!CollectionUtils.isEmpty(awb.getAwbInfoList())) {
				for (TranshipmentTransferManifestByAWBInfo shipment : awb.getAwbInfoList()) {
					shipment.setTrmNumber(awb.getTrmNumber());

					// Fetch the cancellation reason description
					if (!StringUtils.isEmpty(awb.getCancellationReason())) {
						shipment.setCancellationReason(super.fetchObject("sqlGetTrmCancellationReasonValue",
								awb.getCancellationReason(), sqlSession));
					}

					updateData("sqlUpdateInventorywithTrmNumberAsNull", shipment, sqlSession);
					updateData("sqlUpdateShipmentFreightOutwithTrmNumberAsNull", shipment, sqlSession);
					deleteData("deleteTranshipmentAWBSHC", shipment, sqlSession);
					this.cancelTRMDeleteAWBInfo(shipment);
				}
			}
		}
		return cancelAWB;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CANCEL_TRM_AWB)
	public void cancelTRMDeleteAWBInfo(TranshipmentTransferManifestByAWBInfo shipment) throws CustomException {
		deleteData("deleteTranshipmentAWBInfo", shipment, sqlSession);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public TranshipmentTransferManifestByAWBSearch finalizeAWB(TranshipmentTransferManifestByAWBSearch finalizeAWB)
			throws CustomException {
		for (TranshipmentTransferManifestByAWB awb : finalizeAWB.getAwbList()) {
			awb.setFinalizedDate(TenantZoneTime.getZoneDateTime(LocalDateTime.now(), finalizeAWB.getTenantId()));
			updateData("updateTranshipmentAWB", awb, sqlSession);

			// Refetch the shipment information
			TranshipmentTransferManifestByAWBSearch search = new TranshipmentTransferManifestByAWBSearch();
			search.setTrmNumber(awb.getTrmNumber());
			search.setCarrierCodeFrom(awb.getCarrierCodeFrom());
			search.setCarrierCodeTo(awb.getCarrierCodeTo());
			
			TranshipmentTransferManifestByAWB responseModel = this.search(search);
			awb.setAwbInfoList(responseModel.getAwbInfoList());

			for (TranshipmentTransferManifestByAWBInfo shipmentData : awb.getAwbInfoList()) {
				shipmentData.setCreatedBy(finalizeAWB.getCreatedBy());
				shipmentData.setModifiedBy(finalizeAWB.getModifiedBy());

				if (awb.getFinalizedFlag()) {
					shipmentData.setFinalizedFlag("Y");
				} else {
					shipmentData.setFinalizedFlag("N");
				}
				if (awb.getFinalizedFlag().equals(true)) {
					shipmentData.setRecievingCarrier(awb.getCarrierCodeTo());
					this.updateAWBOnFinalize(shipmentData);
				} else {
					shipmentData.setTransferCarrier(awb.getCarrierCodeFrom());
					this.updateAWBOnUnFinalize(shipmentData);
				}
			}
		}
		return finalizeAWB;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FINALIZE_TRM_AWB)
	public void updateAWBOnFinalize(TranshipmentTransferManifestByAWBInfo shipmentData) throws CustomException {
		updateData("changeCarrierShipmentMaster", shipmentData, sqlSession);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.FINALIZE_TRM_AWB)
	public void updateAWBOnUnFinalize(TranshipmentTransferManifestByAWBInfo shipmentData) throws CustomException {
		updateData("changeCarrierShipmentMasterUnfinalize", shipmentData, sqlSession);
	}

	@Override
	public String getTrmCount(TranshipmentTransferManifestByAWB maintain) throws CustomException {
		return fetchObject("getAWBCountBaseOnCarrierCodeFrom", maintain, sqlSession);
		
		
	}

	@Override
	public TranshipmentTransferManifestByAWBInfo getShipmentDetail(TranshipmentTransferManifestByAWBInfo maintain)
			throws CustomException {
		return fetchObject("getShipmentDetail", maintain, sqlSession);
	}

	@Override
	public TranshipmentTransferManifestByAWB mobileMaintain(TranshipmentTransferManifestByAWB search)
			throws CustomException {
		updateData("updateTranshipmentAWBForMobile", search, sqlSession);
		return search;
	}

	public void testIfAlreadyRecordExists(List<TranshipmentTransferManifestByAWBInfo> trmDataList)
			throws CustomException {
		List<String> existingULD = new ArrayList<>();
		for (TranshipmentTransferManifestByAWBInfo data : trmDataList) {
			if ((int) fetchObject("checkEachTrmNumber", data, sqlSession) > 0) {
				existingULD.add(data.getShipmentNumber());
			}
		}
		if (!existingULD.isEmpty()) {
			String[] placeholders = new String[] {existingULD.stream().collect(Collectors.joining(","))};
			throw new CustomException("TRMDUPLICATESHPMNT", SHIPMENT_NUMBER, ErrorType.ERROR, placeholders);
		}
	}

	@Override
	public String getCarrierNameBasedOnCarrierCode(String carrierCode) throws CustomException {
		return fetchObject("fetchCarrierName", carrierCode, sqlSession);
	}

	@Override
	public boolean checkTRMAlreadyExists(String trmNumber) throws CustomException {
		return fetchObject("checkTRMAlreadyExists", trmNumber, sqlSession);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void transferDataToFreightOut(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		BigInteger shipmentId = fetchObject("getShipmentIdFromShipmentMaster", awbInfo, sqlSession);
		if (shipmentId != null) {
			awbInfo.setShipmentId(shipmentId);
		}
		int count = fetchObject("CheckDataExistsInventory", awbInfo, sqlSession);
		if (count > 0) {
			super.insertData("insertDataToFreightOutFromInventory", awbInfo, sqlSession);
			List<BigInteger> shipmentInventoryIds = fetchList("getShipmentInventoryIds", awbInfo, sqlSession);
			List<BigInteger> shipmentFreightOutIds = fetchList("getShipmentFreightOutIds", awbInfo, sqlSession);
			if (!CollectionUtils.isEmpty(shipmentInventoryIds) && !CollectionUtils.isEmpty(shipmentFreightOutIds)) {
				for (BigInteger id : shipmentInventoryIds) {
					awbInfo.setShipmentInventoryId(id);
					awbInfo.setShipmentFreightOutId(shipmentFreightOutIds.get(shipmentInventoryIds.indexOf(id)));
					insertData("insertIntoFreightOutSHC", awbInfo, sqlSession);
					insertData("insertIntoFreightOutHouse", awbInfo, sqlSession);
					deleteData("deleteShipmentInventoryHouseData", awbInfo, sqlSession);
					deleteData("deleteShipmentInventorySHCData", awbInfo, sqlSession);
				}
			}
			deleteData("deleteDataShipmentInventory", awbInfo, sqlSession);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void transferDataBackToInventory(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		BigInteger shipmentId = fetchObject("getShipmentIdFromShipmentMaster", awbInfo, sqlSession);
		if (shipmentId != null) {
			awbInfo.setShipmentId(shipmentId);
		}
		int count = fetchObject("CheckDataExistsFreightOut", awbInfo, sqlSession);
		if (count > 0) {
			throw new CustomException("data.transfer.freight.out", SHIPMENT_NUMBER, ErrorType.ERROR,
					new String[] { awbInfo.getShipmentNumber() });
		}
	}

	@Override
	public int checkRouting(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException {
		int checkRoutingExists = 0;
		checkRoutingExists = fetchObject("checkRoutingExists", awbInfo, sqlSession);
		return checkRoutingExists;
	}

	private TranshipmentTransferManifestByAWB checkDataExistsFreightOut(TranshipmentTransferManifestByAWB maintain)
			throws CustomException {
		List<String> awbList = new ArrayList<>();
		for (TranshipmentTransferManifestByAWBInfo awbInfo : maintain.getAwbInfoList()) {
			int count = fetchObject("CheckDataExistsFreightOut", awbInfo, sqlSession);
			if (count > 0) {
				awbList.add(awbInfo.getShipmentNumber());
			}
		}
		maintain.setFreightOutAwb(awbList);
		return maintain;
	}

	private TranshipmentTransferManifestByAWB checkForHandler(TranshipmentTransferManifestByAWB maintain)
			throws CustomException {
		String handlerForRespectiveCarrier = fetchObject("sqlGetHandlerForRespectiveCarrier",
				maintain.getCarrierCodeTo(), sqlSession);
		for (TranshipmentTransferManifestByAWBInfo awbInfo : maintain.getAwbInfoList()) {
			if (!StringUtils.isEmpty(handlerForRespectiveCarrier)
					&& !handlerForRespectiveCarrier.equalsIgnoreCase(awbInfo.getInboundFlightHandler())) {
				throw new CustomException("data.transfer.handler.check", null, ErrorType.APP,
						new Object[] { maintain.getCarrierCodeTo(), handlerForRespectiveCarrier });
			}
		}
		return maintain;
	}

}