/**
 * This is a enum for holding query ids of inward service reporot
 */
package com.ngen.cosys.inward.constants;

public enum ServiceReportQueryIds {

   SQL_GET_SEGMENTS_FOR_INWARD_SERVICE_REPORT("sqlGetSegmentsForInwardServiceReport"),

   SQL_GET_SERVICE_REPORT_BY_AWB("sqlGetInwardServiceReportFlightSegmentsByAWB"),

   SQL_GET_SERVICE_REPORT_BY_MAIL("sqlGetInwardServiceReportFlightSegmentsByMAIL"),
   
   SQL_GET_SERVICE_REPORT_OTHER_DISCREPANCY_FOR_ULD_RAMP_CHECK_IN("sqlGetInwardServiceReportOtherDiscrepancyOnRampCheckIn"),

   SQL_GET_SERVIE_REPORT_FINALIZED_FOR_CARGO("sqlGetFlightServiceReportFinalizedForCargo"),

   SQL_GET_SERVIE_REPORT_FINALIZED_FOR_MAIL("sqlGetFlightServiceReportFinalizedByMail"),

   SQL_FINALIZE_SERVIE_REPORT_FOR_CARGO("sqlFinalizeServiceReportForCargo"),

   SQL_FINALIZE_SERVIE_REPORT_FOR_MAIL("sqlFinalizeServiceReportForMail"),

   SQL_GET_SERVICE_REPORT_BY_FLIGHT("sqlGetInwardServiceReport"), SQL_UPDATE_SERVICE_REPORT_BY_FLIGHT(
         "sqlUpdateInwardServiceReport"), SQL_INSERT_SERVICE_REPORT_BY_FLIGHT("sqlInsertInwardServiceReport"),

   SQL_DELETE_SERVICE_REPORT_SHIPMENT_DISCREPANCY(
         "sqlDeleteServiceReportShipmentDiscrepancy"), SQL_UPDATE_SERVICE_REPORT_SHIPMENT_DISCREPANCY(
               "sqlUpdateServiceReportShipmentDiscrepancy"), SQL_INSERT_SERVICE_REPORT_SHIPMENT_DISCREPANCY(
                     "sqlInsertServiceReportShipmentDiscrepancy"),

   SQL_DELETE_SERVICE_REPORT_OTHER_DISCREPANCY(
         "sqlDeleteServiceReportOtherDiscrepancy"), SQL_UPDATE_SERVICE_REPORT_OTHER_DISCREPANCY(
               "sqlUpdateServiceReportOtherDiscrepancy"), SQL_INSERT_SERVICE_REPORT_OTHER_DISCREPANCY(
                     "sqlInsertServiceReportOtherDiscrepancy");

   private final String queryId;

   ServiceReportQueryIds(String queryId) {
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