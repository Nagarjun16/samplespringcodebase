/**
 * {@link ReportImportExportWorkingListConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Import Export Working List Report POI Constants
 * 
 * @author NIIT Technologies Ltd
 */
public class ReportImportExportWorkingListConstants {
   // 
   private ReportImportExportWorkingListConstants() {
      throw new RuntimeException("Creating instance is not allowed");
   }
   //
   public static final String DATE_FORMAT = "YYYY-MM";
   public static final String IMPORT_EXPORT_WORKINGLIST_FILE_NAME = "ImportExportWorkingListReport";
   public static final String IMPORT_EXPORT_WORKINGLIST_SHEET_NAME = "GHAL-SAT-%s_Working Report";
   //
   public static final String SQL_SELECT_IMPORT_EXPORT_WORKINGLIST_DATA = "sqlSelectImportExportWorkingListData";
   //
   public static final List<Integer> CARRIER_GROUP_FORMULA_EXCLUSION = Arrays.asList(10, 11, 16, 17, 18, 25, 26, 27, 30,
         31, 32, 35, 36, 37, 40, 41);
   //
   public static final String[] HEADER_DETAILS = { "C2K_Identifier", "GHA", "Month", "Station", "Airline", //
         "Export_AWB/MWBs", "Export_Shipments_Manifested", "FWB_Received", "FWB_Received_Before_RCS", "RCS_Sent", //
         "Missing_RCS", "RCS%", "FFM_Sent", "FFM_Sent_In-time", "DEP_Sent", "DEP_Sent_In-time", "Missing_DEP", //
         "Late_DEP", "DEP%", "Import_AWB/MWBs", "Import_Shipments_Manifested", "FWB_Received_Before_ATA", //
         "FFM_Received_Before_ATA", "RCF_Sent", "RCF_Sent_In-time", "Missing_RCF", "Late_RCF", "RCF%", "AWD_Sent", //
         "AWD_Sent_In-time", "Missing_AWD", "Late_AWD", "AWD%", "NFD_Messages_Sent", "NFD_Sent_In-time", //
         "Missing_NFD", "Late_NFD", "NFD%", "DLV_Event", "DLV_Sent_In-time", "Missing_DLV", "DLV%" };
   
}
