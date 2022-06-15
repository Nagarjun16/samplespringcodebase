package com.ngen.cosys.impbd.shipment.verification.constant;

public enum ShipmentVerificationSqlId {
   SQL_GET_SHIPMENT_VERIFICATION("sqlGetShipmentVerification"), SQL_INSERT_SHIPMENT_VERIFICATION(
         "sqlInsertShipmentVerification"), SQL_UPDATE_SHIPMENT_VERIFICATION_DOCUMENT(
               "sqlUpdateShipmentVerificationDocument"), SQL_UPDATE_SHIPMENT_VERIFICATION_BREAK_DOWN(
                     "sqlUpdateShipmentVerificationBreakDown"), SQL_UPDATE_DG_CHECKLIST(
                           "sqlUpdateDgChecklist"), SQL_INSERT_DG_CHECKLIST("sqlInsertDgChecklist");

   private final String queryId;

   ShipmentVerificationSqlId(String queryId) {
      this.queryId = queryId;
   }

   @Override
   public String toString() {
      return String.valueOf(this.queryId);
   }

}