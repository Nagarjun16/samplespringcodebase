/**
 *   ChangeOfAwbHawbDAO.java
 *   
 *   Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 May, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.enums;

/**
 * @author Yuganshu.K
 *
 */
public enum ChangeOfAWbHawbQueryIds {
	
	SQL_CHECK_SHIPMENT_ORIGIN("checkShipmentOrigin"),
	SQL_UPDATE_SHIPMENT_MASTER("updateShipmentMasterRecord"),
	SQL_UPDATE_ARRIVAL_MANIFEST("updateArrivalManifest"),
	SQL_UPDATE_BREAKDOWN_HANDLING_DEFINATION("updateBreakdownHandlingInformation"),
	SQL_UPDATE_INWARD_SERVICE_REPORT_SHIPMENT_DISCREPANCY("updateInwardServiceReportShipmentDiscrepancy"),
	SQL_UPDATE_AGENT_DELIVERY_PLANNING_WORKSHEET_DISCREPANCY("updateAgentDeliveryPlanningWorksheetShipments"),
	SQL_UPDATE_VEHICLE_PERMIT_AUTHORIZATION("updateVehiclePermitAuthorization"),
	SQL_UPDATE_EQUIPMENT_REQUEST_SHIPMENT("updateEquipmentRequestShipments"),
	SQL_UPDATE_SHIPMENT_IRREGULARITY("updateShipmentIrregularity"),
	SQL_UPDATE_SHIPMENT_REMARKS("updateShipmentRemarks"),
	SQL_UPDATE_TRANSSHIPMENT_TRANSFER_MANIFEST_BYAWBINFO("updateTranshipmentTransferManifestByAWBInfo"),
	SQL_UPDATE_TRANSSHIPMENT_TTWA_CONNECTING_FLIGHT_SHIPMENT("updateTranshipmentTTWAConnectingFlightShipment"),
	SQL_UPDATE_AGT_SID_HEADER("updateAgtSIDHeader"),
	SQL_UPDATE_AGT_INBOUND_PREBOOKING_SHIPMENT("updateAgtInboundPreBookingShipments"),
	SQL_UPDATE_TRANSSHIPMENT_THROUGH_TRANSIT_WORKINGLIST_SHIPMENT("updateTranshipmentThroughTransitWorkingListShipment"),
	SQL_CHECK_HAWB_EXISTS("checkHAWBExists"),
	SQL_CHECK_HWB_EXISTS_HANDLED_BY_HOUSE("checkHWBExistsHandledByHouse"),
	SQL_UPDATE_HAWB_NUMBER("updateHawbNumber"),
	SQL_UPDATE_HWB_HANDLED_BY_HOUSE("updateHwbNumberHandledByHouse"),
	SQL_INSERT_CHANGE_AWB_HISTORY("insertChangedAwbNumberHistory"),
	SQL_INSERT_CHANGE_HAWB_HISTORY("insertChangedHawbNumberHistory"),
	SQL_CHECK_NEW_AWB_NUMBER("checkNewShipmentNumberAlreadyExists"),
	SQL_UPDATE_DG_DECLARATION("updateDgDeclaration"),
	SQL_UPDATE_FWB("updateShipmentFreightWayBill"),
	SQL_UPDATE_AgentAcceptancePlanningShipments("updateAgentAcceptancePlanningShipments"),
	SQL_UPDATE_EACCEPTANCEDOCUMENTINFORMATION("updateExpeAcceptanceDocumentInformation"),
	SQL_UPDATE_EXPBOOKINGDELTA("updateExpBookingDelta"),
	SQL_UPDATE_EXPBOOKING("updateExpShipmentBooking"),
	SQL_UPDATEPRELODGEINFO("updateAgtPrelodgeExportDocuments"),
	SQL_UPDATE_EXP_WORKINGLISTSNAPSHOT("updateExpWorkingListSnapshotShipmentDetails"),
	SQL_UPDATE_EXP_AUTOWEIGHSHIPMENT("updateExpeAcceptanceAutoWeighShipment"),
	SQL_UPDATE_SHIPMENTCHECKLISTSTATUS("updateShipmentCheckListStatus"),
	SQL_UPDATE_BLACKLISTSHIPMENT("updateMstBlackListedShipments"),
	SQL_UPDATE_OUTWARDSERVICEREPORTDISC("updateExpOutwardServiceReportShipmentDiscrepancy"),
	SQL_INSERT_CHANGEREMARK("insertChangeAWBRemark"),
	SQL_INSERT_CHANGEREHAWBMARK("insertChangeHAWBRemark"),
	SQL_GET_REASON("getReason"),
	SQL_GET_REASONHAWB("getReasonHAWB"),
	SQL_UPDATE_HWB_SHIPMENT_IRREGULARITY("sqlUpdateHwbShipmentIrregularity"),
	SQL_UPDATE_HWB_SHIPMENT_REMARKS("sqlUpdateHWBShipmentRemarks"),
	SQL_UPDATE_HWB_COM_DAMAGE_INFO("sqlUpdateHWBComDamageInfo"),
	SQL_CHECK_ANY_PAID_CHARGE_FOR_AWB("sqlCheckAnyPaidChargeForAWB");
	 private final String queryId;

	 ChangeOfAWbHawbQueryIds(String queryId) {
	      this.queryId = queryId;
	   }

	   @Override
	   public String toString() {
	      return String.valueOf(this.queryId);
	   }
}
