/**
 * Query id's for Shipment Master 
 */
package com.ngen.cosys.satssginterfaces.mss.enums;

public enum ShipmentMasterAirmailInterfaceSqlId {

	SQL_CHECK_FOR_SVC_SHIPMENT("sqlCheckForSVCShipment"),

	SQL_CHECK_FOR_PART_SHIPMENT("sqlCheckForPartShipment"),

	SQL_GET_FWB_INFO("sqlGetShipmentFreightWayBill"),

	SQL_GET_SHIPMENTMASTER("sqlGetShipmentMaster"), SQL_CREATE_SHIPMENTMASTER(
			"sqlInsertShipmentMaster"), SQL_UPDATE_SHIPMENTMASTER("sqlUpdateShipmentMaster"),

	SQL_CHECK_SHIPMENTMASTERCUSTOMERADDRESSINFO(
			"sqlGetShipmentMasterCustomerAddressInfo"), SQL_CREATE_SHIPMENTMASTERCUSTOMERADDRESSINFO(
					"sqlInsertShipmentMasterCustomerAddressInfo"), SQL_UPDATE_SHIPMENTMASTERCUSTOMERADDRESSINFO(
							"sqlUpdateShipmentMasterCustomerAddressInfo"),

	SQL_CHECK_SHIPMENTMASTERCUSTOMERCONTACTINFO(
			"sqlGetShipmentMasterCustomerContactInfo"), SQL_CREATE_SHIPMENTMASTERCUSTOMERCONTACTINFO(
					"sqlInsertShipmentMasterCustomerContactInfo"),

	SQL_CHECK_SHIPMENTMASTERCUSTOMERINFO("sqlGetShipmentMasterCustomerInfo"), SQL_CREATE_SHIPMENTMASTERCUSTOMERINFO(
			"sqlInsertShipmentMasterCustomerInfo"), SQL_UPDATE_SHIPMENTMASTERCUSTOMERINFO(
					"sqlUpdateShipmentMasterCustomerInfo"),

	SQL_CHECK_SHIPMENTMASTERHANDLINGAREA("sqlGetShipmentMasterHandlingArea"), SQL_CREATE_SHIPMENTMASTERHANDLINGAREA(
			"sqlInsertShipmentMasterHandlingArea"),

	SQL_CHECK_SHIPMENTMASTERROUTINGINFO("sqlGetShipmentMasterRoutingInfo"), SQL_CREATE_SHIPMENTMASTERROUTINGINFO(
			"sqlInsertShipmentMasterRoutingInfo"),

	SQL_CHECK_SHIPMENTMASTERSHC("sqlGetShipmentMasterShc"), SQL_CREATE_SHIPMENTMASTERSHC("sqlInsertShipmentMasterShc"),

	SQL_CHECK_SHIPMENTMASTERSHCHANDLINGGROUP(
			"sqlGetShipmentMasterShcHandlingGroup"), SQL_CREATE_SHIPMENTMASTERSHCHANDLINGGROUP(
					"sqlInsertShipmentMasterShcHandlingGroup"),

	SQL_CHECK_SHIPMENTOTHERCHARGEINFO("sqlGetShipmentOtherChargeInfo"), SQL_CREATE_SHIPMENTOTHERCHARGEINFO(
			"sqlInsertShipmentOtherChargeInfo"), SQL_UPDATE_SHIPMENTOTHERCHARGEINFO("sqlUpdateShipmentOtherChargeInfo"),
	SQL_UPDATE_DOCUMENTRECEIVEDON("sqlUpdateDocumentreceivedon");

	private final String queryId;

	ShipmentMasterAirmailInterfaceSqlId(String queryId) {
		this.queryId = queryId;
	}

	@Override
	public String toString() {
		return String.valueOf(this.queryId);
	}

}