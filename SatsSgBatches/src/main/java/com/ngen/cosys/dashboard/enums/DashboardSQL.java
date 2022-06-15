/**
 * {@link DashboardSQL}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.enums;

/**
 * Enum used for Dashboard related SQL
 * 
 * @author NIIT Technologies Ltd
 */
public enum DashboardSQL {

   SELECT_EVENT_NOTIFICATION_CONFIGURATION("sqlSelectDashboardEventNotificationConfiguration"), //
   INSERT_EXPORT_DASHBOARD_BATCH_LOG("sqlInsertExportDashboardBatchLog"), //
   UPDATE_EXPORT_DASHBOARD_BATCH_LOG("sqlUpdateExportDashboardBatchLog"), //
   INSERT_IMPORT_DASHBOARD_BATCH_LOG("sqlInsertImportDashboardBatchLog"), //
   UPDATE_IMPORT_DASHBOARD_BATCH_LOG("sqlUpdateImportDashboardBatchLog"), //
   SELECT_EXPORT_FLIGHTS_DATA("sqlSelectDashboardExportFlightsData"), //
   DELETE_EXPORT_FLIGHTS_DATA("sqlDeleteDashboardExportFlightsData"), //
   INSERT_EXPORT_FLIGHTS_DATA("sqlInsertDashboardExportFlightsData"), //
   SELECT_IMPORT_FLIGHTS_DATA("sqlSelectDashboardImportFlightsData"), //
   DELETE_IMPORT_FLIGHTS_DATA("sqlDeleteDashboardImportFlightsData"), //
   INSERT_IMPORT_FLIGHTS_DATA("sqlInsertDashboardImportFlightsData");
   
   private String queryId;
   
   /**
    * @param value
    */
   private DashboardSQL(String value) {
      this.queryId = value;
   }
   
   /**
    * @return
    */
   public String getQueryId() {
      return this.queryId;
   }
   
}
