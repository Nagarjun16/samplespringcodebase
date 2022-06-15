package com.ngen.cosys.impbd.shipment.breakdown.constant;

public enum InboundBreakDownSqlId {
   GET_BREAK_DOWN_DATA("sqlGetInboundBreakdownData"),

   CHECK_BREAK_DOWN_STORAGE_INFO("sqlCheckBreakDownStorageInfo"), INSERT_BREAK_DOWN_STORAGE_INFO(
         "sqlInsertBreakDownStorageInfo"), UPDATE_BREAK_DOWN_STORAGE_INFO(
               "sqlUpdateBreakDownStorageInfo"), SELECT_BREAK_DOWN_STORAGE_INFO("sqlSelectBreakDownStorageInfo"),

   CHECK_BREAK_DOWN_HOUSE_INFO("sqlCheckBreakDownHouseInfo"), INSERT_BREAK_DOWN_HOUSE_INFO(
         "sqlInsertBreakDownShipmentHouseInfo"), UPDATE_BREAK_DOWN_HOUSE_INFO("sqlUpdateBreakDownShipmentHouseInfo"),

   CHECK_BREAK_DOWN_STORAGE_SHC_INFO("sqlCheckBreakDownStorageSHCInfo"), INSERT_BREAK_DOWN_STORAGE_SHC_INFO(
         "sqlInsertBreakDownStorageSHCInfo"), UPDATE_BREAK_DOWN_SHC_INFO("sqlUpdateBreakDownShipmentSHCInfo"),

   INSERT_BREAK_DOWN_ULD_TROLLEY_INFO("sqlInsertBreakDownULDTrolleyInfo"), UPDATE_BREAK_DOWN_ULD_TROLLEY_INFO(
         "sqlUpdateBreakDownULDTrolleyInfo"), SELECT_BREAK_DOWN_ULD_TROLLEY_INFO("sqlSelectBreakDownULDTrolleyInfo"),

   CHECK_TRANSFER_TYPE("sqlGetTransferType"),

   CHECK_HANDLING_MODE("sqlGetULDBreak"),

   CHECKDOCUMENTCOMPLETED("sqlCheckDocumentVerificationCompleted"),

   GETIRREGULARITYINFO("getIrregularityInformation"),
	
   GETIRREGULARITYINFO_HAWB_HANDLING("getIrregularityInformationForHAWBHandling");
	
   private final String queryId;

   InboundBreakDownSqlId(String queryId) {
      this.queryId = queryId;
   }

   public String getQueryId() {
      return this.queryId;
   }

   @Override
   public String toString() {
      return String.valueOf(this.queryId);
   }
}
