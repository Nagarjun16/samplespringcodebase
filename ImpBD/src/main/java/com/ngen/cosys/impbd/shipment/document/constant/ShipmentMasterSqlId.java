/**
 * Query id's for Shipment Master
 */
package com.ngen.cosys.impbd.shipment.document.constant;

public enum ShipmentMasterSqlId {

   SQL_CHECK_FOR_SVC_SHIPMENT("sqlCheckForSVCShipment"),

   SQL_CHECK_FOR_PART_SHIPMENT("sqlCheckForPartShipment"),

   SQL_GET_FWB_INFO("sqlGetShipmentFreightWayBill"),

   SQL_GET_SHIPMENTMASTER("sqlGetShipmentMaster"), SQL_CREATE_SHIPMENTMASTER(
         "sqlInsertShipmentMaster"), SQL_UPDATE_SHIPMENTMASTER("sqlUpdateShipmentMaster"),

   SQL_CHECK_SHIPMENTMASTERROUTINGINFO("sqlGetShipmentMasterRoutingInfo"), SQL_CREATE_SHIPMENTMASTERROUTINGINFO(
         "sqlInsertShipmentMasterRoutingInfo"), SQL_UPDATE_SHIPMENTMASTERROUTINGINFO(
               "sqlUpdateShipmentMasterRoutingInfo"),SQL_DELETE_SHIPMENTMASTERROUTINGINFO("deleteRoutingInfo"),

   SQL_CHECK_SHIPMENTMASTERSHC("sqlGetShipmentMasterShc"), SQL_CREATE_SHIPMENTMASTERSHC("sqlInsertShipmentMasterShc"),

   SQL_CHECK_SHIPMENTMASTERSHCHANDLINGGROUP(
         "sqlGetShipmentMasterShcHandlingGroup"), SQL_CREATE_SHIPMENTMASTERSHCHANDLINGGROUP(
               "sqlInsertShipmentMasterShcHandlingGroup"),

   SQL_UPDATE_DOCUMENTRECEIVEDON("sqlUpdateDocumentreceivedon"),
   SQL_GET_SHIPMENTMASTER_INFO("sqlGetShipmentMasterInfoWithOutType"),
   SQL_UPDATE_EXP_SHIPMENTBOOKING("sqlUpdateExportShipmentBooking");
	

   private final String queryId;

   ShipmentMasterSqlId(String queryId) {
      this.queryId = queryId;
   }

   @Override
   public String toString() {
      return String.valueOf(this.queryId);
   }

}