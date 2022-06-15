/**
 * This is a repository implementation class for persisting shipment inventory/ house and shc
 */
package com.ngen.cosys.shipment.deletehousewaybill.dao;

import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillResponseModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillSearchModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.UpdateShipmentDetails;

@Repository
public class DeleteHouseWayBillDaoImpl extends BaseDAO implements DeleteHouseWayBillDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	// Checking pieces and weight
	@Override
	public BigInteger checkInvPcWgt(DeleteHouseWayBillResponseModel fetchInvDataForDel) throws CustomException {
		return fetchObject("sqlQueryCheckInvPcWgt", fetchInvDataForDel, sqlSessionTemplate);
	}

	// Inserting Remarks for deletion
	@Override
	public void insertRemarksForDeletion(DeleteHouseWayBillResponseModel fetchInvDataForDel) throws CustomException {

		fetchInvDataForDel.setRemarks(
				"HWB " + fetchInvDataForDel.getHawbNumber() + " DeletedBy " + fetchInvDataForDel.getCreatedBy() + " On "
						+ TenantTimeZoneUtility.now().format(DateTimeFormatter.ofPattern("ddMMMHHmm")) + " "
						+ fetchInvDataForDel.getRemarks());
		insertData("sqlQueryInsertRemakrsForHouseDeletion", fetchInvDataForDel, sqlSessionTemplate);

	}

	@Override
	public DeleteHouseWayBillResponseModel getShipmentIdInCaseInvIsNotAvlbl(
			DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel) throws CustomException {
		return fetchObject("sqlQueryFetchShipmentIdInCaseInvNotAvlbl", deleteHouseWayBillSearchModel,
				sqlSessionTemplate);
	}

	// to delete the HAWB
	@Override
	/*
	 * Audit Trail
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DELETE_HOUSE_WAY_BILL)
	public void deleteShipmentHouseInfo(DeleteHouseWayBillResponseModel shipmentHseInf) throws CustomException {
		deleteData("sqlQueryDeleteShipmentHouseInfo", shipmentHseInf, sqlSessionTemplate);

	}

	// To delete HAWB SHC
	@Override
	public void deleteShipmentHouseSHCInfo(DeleteHouseWayBillResponseModel shipmentHseInf) throws CustomException {
		deleteData("sqlQueryDeleteShipmentHouseSHCInfo", shipmentHseInf, sqlSessionTemplate);

	}

	// To Delete Shipper and Consignee info
	@Override
	public void deleteShipmentHouseCustomerInfo(DeleteHouseWayBillResponseModel shipmentHseInf) throws CustomException {
		deleteData("sqlQueryDeleteShipmentHouseCustomerInfo", shipmentHseInf, sqlSessionTemplate);

	}

	// To delete Shipper and Consignee Address
	@Override
	public void deleteShipmentHouseCustomerAddressInfo(BigInteger shpHseCstmrId) throws CustomException {
		deleteData("sqlQueryDeleteShipmentHouseAddressInfo", shpHseCstmrId, sqlSessionTemplate);

	}

	@Override
	public List<BigInteger> getCustomerAddressInfoIds(DeleteHouseWayBillResponseModel shipmentHseInf)
			throws CustomException {
		return fetchList("sqlQueryGetShipmentHouseAddressInfoIds", shipmentHseInf, sqlSessionTemplate);
	}

	@Override
	public BigInteger fetchShipmentHouseCustomerContactInfo(BigInteger x) throws CustomException {

		return fetchObject("sqlQueryFetchHouseContactAdressInfo", x, sqlSessionTemplate);
	}

	// To delete customer contact info
	@Override
	public void deleteCustomerContactInfo(BigInteger fetchShipmentHouseCustomerContactInfo) throws CustomException {

		deleteData("sqlQueryDeleteHouseCustomerAddressContactInfo", fetchShipmentHouseCustomerContactInfo,
				sqlSessionTemplate);

	}

	@Override
	public String checkForShipmentIsImportExport(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)
			throws CustomException {
		return fetchObject("sqlCheckForShipmentIsImportExport", deleteHouseWayBillSearchModel, sqlSessionTemplate);
	}

	@Override
	public void deleteShpInvShcExprt(DeleteHouseWayBillResponseModel invDetls) throws CustomException {
		deleteData("sqlQueryDeleteShipmentInventorySHC", invDetls, sqlSessionTemplate);

	}

	@Override
	public void updateHseWgnPieceWgt(DeleteHouseWayBillResponseModel invDetls) throws CustomException {
		updateData("sqlQueryupdateHseWgnPieceWgt", invDetls, sqlSessionTemplate);

	}

	@Override
	public BigInteger fetchHseWgnPieceWgt(DeleteHouseWayBillResponseModel invDetls) throws CustomException {
		return fetchObject("sqlQueryfetchHseWgnPieceWgt", invDetls, sqlSessionTemplate);
	}

	@Override
	public BigInteger fetchEaccHseInfPieceWgt(DeleteHouseWayBillResponseModel invDetls) throws CustomException {
		return fetchObject("sqlQueryfetchEaccHseInfPieceWgt", invDetls, sqlSessionTemplate);
	}

	@Override
	public void updateAcceptByHouseFlagdocInfo(DeleteHouseWayBillResponseModel invDetls) throws CustomException {
		updateData("sqlQueryupdateAcceptByHouseFlagdocInfo", invDetls, sqlSessionTemplate);

	}

	@Override
	public void updateAcceptByHouseFlagInMaster(DeleteHouseWayBillResponseModel invDetls) throws CustomException {
		updateData("sqlQueryUpdateAcceptByHouseFlagInMaster", invDetls, sqlSessionTemplate);

	}

	// To Delete HAWB dimension and dimension details
	@Override
	public void deleteShipmentHouseDimensionDetails(DeleteHouseWayBillResponseModel shipmentHseInf)
			throws CustomException {
		BigInteger shipmentHouseDimId = fetchObject("sqlQueryFetchHouseDimID", shipmentHseInf.getShipmentHouseId(),
				sqlSessionTemplate);
		if (shipmentHouseDimId != null) {
			deleteData("sqlQueryDeleteDataFromDimesionDetails", shipmentHouseDimId, sqlSessionTemplate);
			deleteData("sqlQueryDeleteDataFromDimesion", shipmentHseInf.getShipmentHouseId(), sqlSessionTemplate);

		}

	}

	// To delete house information from Shipment Inventory
	@Override
	public void deleteShipmentInventoryDetails(DeleteHouseWayBillResponseModel shipmentHseInf) throws CustomException {

		List<BigInteger> shipmentInventoryId = fetchList("sqlGetShipmentInventoryId", shipmentHseInf,
				sqlSessionTemplate);

		if (!CollectionUtils.isEmpty(shipmentInventoryId)) {
			for (BigInteger id : shipmentInventoryId) {
				// delete shc
				deleteData("sqlDeleteShcInventory", id, sqlSessionTemplate);
			}
		}

		// delete inventory
		deleteData("sqlQueryDeleteShipmentInventoryInfo", shipmentHseInf, sqlSessionTemplate);

	}

	@Override
	public void deleteShipmentImpBreakDownStorageDetails(DeleteHouseWayBillResponseModel shipmentHseInf)
			throws CustomException {

		List<BigInteger> impBreakdownInfoId = fetchList("sqlGetImpBreakdownStrorageInfoId", shipmentHseInf,
				sqlSessionTemplate);

		if (!CollectionUtils.isEmpty(impBreakdownInfoId)) {
			for (BigInteger id : impBreakdownInfoId) {
				// delete shc
				deleteData("sqlDeleteImpBreakDownShc", id, sqlSessionTemplate);
			}
		}
		// delete ImpBreakDownStorageInfo
		deleteData("sqlQueryDeleteHAWBImpBreakdownStorageInfo", shipmentHseInf, sqlSessionTemplate);
	}

	@Override
	public void deleteShipmentFreightOutDetails(DeleteHouseWayBillResponseModel shipmentHseInf) throws CustomException {

		List<BigInteger> shipmentFreightOutId = fetchList("sqlGetShipmentFreightOutId", shipmentHseInf,
				sqlSessionTemplate);
		if (!CollectionUtils.isEmpty(shipmentFreightOutId)) {
			for (BigInteger id : shipmentFreightOutId) {
				// delete shc
				deleteData("sqlDeleteShipmentFreightOutSHC", id, sqlSessionTemplate);
			}
		}
		// delete ImpBreakDownStorageInfo
		deleteData("sqlQueryDeleteShipmentFreightOut", shipmentHseInf, sqlSessionTemplate);
	}
	
	@Override
	public Integer isFlightCompleted(BigInteger flightId) throws CustomException {
		return fetchObject("sqlCheckFlightCompleted", flightId, sqlSessionTemplate);
	}
	
	@Override
	public BigInteger getFlightId(Map<String, Object> requestMapForFlight) throws CustomException {
		return fetchObject("sqlGetFlightIdByShipmentNumberAndDate", requestMapForFlight, sqlSessionTemplate);
	}
	
	@Override
	public UpdateShipmentDetails fetchShipmentDetails(Map<String, Object> requestMap) throws CustomException {
		return fetchObject("sqlShipmentHouseDetails", requestMap, sqlSessionTemplate);
	}


}
