/**
 * {@link ReportPOIConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi.constants;

/**
 * Report POI Constants
 * 
 * @author NIIT Technologies Ltd
 */
public final class ReportPOIConstants {
   // 
   private ReportPOIConstants() {
      throw new RuntimeException("Creating instance is not allowed");
   }
   //
   public static final String IMPORT_EXPORT_REFERENCE_WORKINGLIST_REPORT = "importExportReferenceWorkingListReport";
   public static final String IMPORT_EXPORT_WORKINGLIST_REPORT = "importExportWorkingListReport";
   public static final String TRANSFER_TRANSIT_REFERENCE_REPORT = "transferTransitReferenceReport";
   public static final String TRANSFER_TRANSIT_REFERENCE_WORKINGLIST_REPORT = "transferTransitReferenceWorkingListReport";
   public static final String TRANSFER_TRANSIT_WORKINGLIST_REPORT = "transferTransitWorkingListReport";
   public static final String SPECIAL_CARGO_MONITORING_REPORT = "SpecialCargoMonitoringReport";
   // POI 
   public static final int DEFAULT_ZOOM_SCALE = 90;
   public static final int DEFAULT_ROW_FREEZE_PANE = 1;
   public static final int TOP_HEADERS_FREEZE_PANE = 2;
   public static final int DEFAULT_COLUMN_FREEZE_PANE = 6;
   public static final int WORKINGLIST_COLUMN_FREEZE_PANE = 5;
   public static final int TRANSFER_TRANSIT_COLUMN_FREEZE_PANE = 2;
   //
   public static final String GROUP_TOTAL = "{} Total";
   public static final String GRAND_TOTAL = "Grand Total";
   //
   public static final String SUBTOTAL = "SUBTOTAL";
   public static final String SUBTRACT = "SUBTRACT";
   public static final String PERCENT = "PERCENT";
   public static final String PERCENT_DLV = "PERCENT_DLV";
   // 
   public static final String[] EXCEL_COLUMN_NAMES = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", //
         "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", //
         "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS", "AT", "AU", "AV", "AW", //
         "AX", "AY", "AZ" };
   
}
