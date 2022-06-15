package com.ngen.cosys.impbd.mail.manifest.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestModel;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestShipmentInfoModel;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestShipmentInventoryInfoModel;
import com.ngen.cosys.impbd.mail.manifest.model.TransferCN46FromManifestModel;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;

@Repository
public class InboundMailManifestDAOImpl extends BaseDAO implements InboundMailManifestDAO {
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Override
	public InboundMailManifestModel search(InboundMailManifestModel reqParam) throws CustomException {
		return super.fetchObject("fetchmailmanifest", reqParam, sqlSession);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public TransferCN46FromManifestModel transferToCN46(TransferCN46FromManifestModel reqParam) throws CustomException {
		super.insertData("transfertocndeatils", reqParam, sqlSession);
		return reqParam;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public TransferCN46FromManifestModel transfer(TransferCN46FromManifestModel reqParam) throws CustomException {
		/*
		 * Integer id = fetchObject("fetchAirmailManifestId", reqParam, sqlSession);
		 * List<InboundMailManifestShipmentInfoModel> value = new ArrayList<>(); for
		 * (InboundMailManifestShipmentInfoModel param : reqParam.getShipments()) {
		 * param.setAirmailManifestId(BigInteger.valueOf(id)); value.add(param); } if
		 * (!CollectionUtils.isEmpty(value)) { super.insertList("transfer", value,
		 * sqlSession); } return reqParam;
		 */
		Integer count = fetchObject("getExistingRecordOfAirmanifets", reqParam, sqlSession);
		if (count > 0) {
			updateData("updateAirmailManifestCn46Shipments", reqParam, sqlSession);
		} else {
			super.insertData("transfer", reqParam, sqlSession);
		}
		return reqParam;
	}

	@Override
	public Integer checkTransferToCN46(InboundMailManifestShipmentInfoModel reqParam) throws CustomException {
		Integer count = super.fetchObject("checktransfertocn46", reqParam, sqlSession);
		return count;
	}

	@Override
	public Integer checkId(InboundMailManifestModel reqParam) throws CustomException {
		Integer id = super.fetchObject("takeId", reqParam, sqlSession);
		return id;
	}

	@Override
	public Boolean checkDocumentCompleted(InboundMailManifestModel reqParam) throws CustomException {
		Boolean value;
		// checkFlightId

		InboundMailManifestModel flightdata = super.fetchObject("checkFlightId", reqParam, sqlSession);
		if (null == flightdata) {
			super.insertData("insertflightevents", reqParam, sqlSession);
		}
		Integer data = super.fetchObject("checkdocumentcompleted", reqParam, sqlSession);
		if (data == 0)
			value = false;
		else
			value = true;
		return value;
	}

	@Override
	public Boolean checkBreakDownComplete(InboundMailManifestModel reqParam) throws CustomException {
		Boolean value;
		InboundMailManifestModel flightdata = super.fetchObject("checkFlightId", reqParam, sqlSession);
		if (null == flightdata) {
			super.insertData("insertflightevents", reqParam, sqlSession);
		}
		Integer data = super.fetchObject("checkbreakdowncompleted", reqParam, sqlSession);
		if (data == 0)
			value = false;
		else
			value = true;
		return value;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_IMP_MAIL_MANIFEST_DOC_COMPLETE)
	@Override
	public InboundMailManifestModel documentComplete(InboundMailManifestModel reqParam) throws CustomException {
		super.updateData("documentcomplete", reqParam, sqlSession);
		return reqParam;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_IMP_MAIL_MANIFEST_BD_COMPLETE)
	@Override
	public InboundMailManifestModel breakDownComplete(InboundMailManifestModel reqParam) throws CustomException {
		super.updateData("breakdowncomplete", reqParam, sqlSession);
		return reqParam;
	}

	// Reopen Breakdown Complete Dao Implementation//
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_IMP_MAIL_MANIFEST_BD_COMPLETE)
	@Override
	public InboundMailManifestModel afterBreakDownComplete(InboundMailManifestModel reqParam) throws CustomException {
		super.updateData("afterBreakdowncomplete", reqParam, sqlSession);
		return reqParam;
	}

	@Override
	public InboundMailManifestModel reopenBreakdownIfAlreadyDone(InboundMailManifestModel reqParam)
			throws CustomException {

		return super.fetchObject("reopenBreakdownIfComplete", reqParam, sqlSession);
	}

	@Override
	public Boolean checkBreakdownIfAlreadyDone(InboundMailManifestModel reqParam) throws CustomException {
		Boolean value;

		Integer data = super.fetchObject("checkbreakdowncompleted", reqParam, sqlSession);
		if (data == 0)
			value = false;
		else
			value = true;
		return value;
	}

	@Override
	public InboundMailManifestModel reOpenBreakDown(InboundMailManifestModel reqParam) throws CustomException {
		super.updateData("reOpenBreakDown", reqParam, sqlSession);
		return reqParam;
	}

	// Reopen Document Complete Dao Implementation//
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_IMP_MAIL_MANIFEST_DOC_COMPLETE)
	public InboundMailManifestModel afterDocumentComplete(InboundMailManifestModel reqParam) throws CustomException {
		super.updateData("afterDocumentComplete", reqParam, sqlSession);
		return reqParam;
	}

	@Override
	public InboundMailManifestModel reopenDocumentIfAlreadyDone(InboundMailManifestModel reqParam)
			throws CustomException {

		return super.fetchObject("reopenDocumentIfComplete", reqParam, sqlSession);
	}

	@Override
	public Boolean checkDocumentIfAlreadyDone(InboundMailManifestModel reqParam) throws CustomException {
		Boolean value;

		Integer data = super.fetchObject("checkdocumentcompleted", reqParam, sqlSession);
		if (data == 0)
			value = false;
		else
			value = true;
		return value;
	}

	@Override
	public InboundMailManifestModel reOpenDocument(InboundMailManifestModel reqParam) throws CustomException {
		super.updateData("reOpenDocument", reqParam, sqlSession);
		return reqParam;
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public int updateLocation(InboundMailManifestShipmentInventoryInfoModel reqParam) throws CustomException {
		int count = super.updateData("updateLocation", reqParam, sqlSession);
		return count;
	}

	@Override
	public List<ShipmentIrregularityModel> irregularityInfo(InboundMailManifestModel reqParam) throws CustomException {
		return fetchList("irregularityInfo", reqParam, sqlSession);
	}

	@Override
	public InboundMailManifestModel flightId(InboundMailManifestModel reqparam) throws CustomException {
		return super.fetchObject("selectflightid", reqparam, sqlSession);
	}

	@Override
	public Integer checkDateAta(InboundMailManifestModel reqparam) throws CustomException {
		Integer count = fetchObject("checkDateAta", reqparam, sqlSession);
		return count;
	}

	@Override
	public List<InwardServiceReportShipmentDiscrepancyModel> nonIrregularityData(
			List<InwardServiceReportShipmentDiscrepancyModel> requestModel) throws CustomException {
		for (InwardServiceReportShipmentDiscrepancyModel value : requestModel) {
			Integer count = fetchObject("checkduplicateDoc", value, sqlSession);
			if (count >= 1) {
				value.setManual(true);
			} else {
				value.setManual(false);
			}
		}
		return requestModel;
	}

	@Override
	public void unFinalizeServiceReport(InboundMailManifestModel reqParam) throws CustomException {
		updateData("unFinalizeServiceReport", reqParam, sqlSession);

	}

	@Override
	public BigInteger getInventory(InboundMailManifestShipmentInventoryInfoModel updateLocationModel)
			throws CustomException {
		return fetchObject("checkInventoryForUpdate", updateLocationModel, sqlSession);

	}

	@Override
	public BigInteger getHouseInventory(InboundMailManifestShipmentInventoryInfoModel inventory)
			throws CustomException {
		return fetchObject("getHouseInventory", inventory, sqlSession);

	}

	@Override
	public void deleteHouse(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException {
		deleteData("deleteHouseInventory", inventory, sqlSession);
	}

	@Override
	public void deductExistingInventoryPieceWeight(InboundMailManifestShipmentInventoryInfoModel inventory)
			throws CustomException {
		updateData("deductExistingInventoryPieceWeight", inventory, sqlSession);
		
		BigInteger pieces=fetchObject("getInventotyPieceAndWeightInfo", inventory, sqlSession);
		
		if (pieces.intValue() < 1) {
			insertData("deleteInventorySHC", inventory, sqlSession);
			insertData("deleteInventory", inventory, sqlSession);
		}
	}

//	@NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public void addNewInventoryPieceWeight(InboundMailManifestShipmentInventoryInfoModel inventory)
			throws CustomException {
		updateData("addNewInventoryPieceWeight", inventory, sqlSession);
	}

	// @NgenAuditAction(entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN,
	// eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@Override
	public void createInventory(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException {
		insertData("insertInventory", inventory, sqlSession);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAIL_IMP_MAIL_MANIFEST_UPDATE_LOCATION)
	@Override
	public void createHouseInventory(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException {
		insertData("insertHouseInventory", inventory, sqlSession);
	}

	@Override
	public BigInteger getExistingInventory(InboundMailManifestShipmentInventoryInfoModel inventory)
			throws CustomException {
		return fetchObject("getExistingInventory", inventory, sqlSession);
	}

	@Override
	public String getStoreLocationType(String storageLocation) throws CustomException {
		return fetchObject("getStoreLocationType", storageLocation, sqlSession);
	}

	@Override
	public List<TransferCN46FromManifestModel> getTransferToCn46Data(InboundMailManifestShipmentInfoModel requestModel)
			throws CustomException {
		return fetchList("getTransferToCn46Data", requestModel, sqlSession);
	}

	@Override
	public Integer getExistingRecordOfAirmanifets(TransferCN46FromManifestModel reqModel) throws CustomException {
		return fetchObject("getExistingRecordOfAirmanifets", reqModel, sqlSession);
	}

	@Override
	public Integer checkMailBagLoaded(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException {
		return fetchObject("checkMailBagLoaded", inventory, sqlSession);
	}

	@Override
	public String loadedHouse(InboundMailManifestShipmentInventoryInfoModel mailBags) throws CustomException {
		Integer count = fetchObject("loadedHouseCheck", mailBags, sqlSession);
		if (count == 0) {
			return null;
		} else {
			return mailBags.getMailBagNumber();
		}

	}

	@Override
	public String checkContentCode(String updateLocationModel) throws CustomException {
		return super.fetchObject("checkContentCode", updateLocationModel, sqlSession);
	}

	@Override
	public int getLoadedSHC(String storeLocation) throws CustomException {
		return super.fetchObject("getLoadedSHC1", storeLocation, sqlSession);
	}

	@Override
	public String getContainerDestination(String storageLocation) throws CustomException {
		return super.fetchObject("getContainerDestinationForOverView", storageLocation, sqlSession);
	}

	@Override
	public void updateNextDestinationOfMailBags(List<InboundMailManifestShipmentInventoryInfoModel> updateLocationModel)
			throws CustomException {
		updateData("updateNextDestOfMB", updateLocationModel, sqlSession);
	}
}
