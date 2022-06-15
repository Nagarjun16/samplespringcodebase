/**
 * {@link ReportImportExportReferenceWorkingListConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Import Export Reference Working List Report POI Constants
 * 
 * @author NIIT Technologies Ltd
 */
public class ReportImportExportReferenceWorkingListConstants {
   // 
   private ReportImportExportReferenceWorkingListConstants() {
      throw new RuntimeException("Creating instance is not allowed");
   }
   //
   public static final String DATE_FORMAT = "YYYY-MM";
   public static final String IMPORT_EXPORT_REFERENCE_WORKINGLIST_FILE_NAME = "ImportExportReferenceWorkingListReport";
   public static final String IMPORT_EXPORT_REFERENCE_WORKINGLIST_SHEET_NAME = "GHAL-SAT-REF-%s_Working Re"; //Sheetname length cant be more than 31
   //
   public static final String SQL_SELECT_IMPORT_EXPORT_REFERENCE_WORKINGLIST_DATA = "sqlSelectImportExportReferenceWorkingListData";
   //
   public static final List<Integer> CARRIER_GROUP_FORMULA_EXCLUSION = Arrays.asList(12, 18, 27, 32, 37, 40, 41);
   //
   public static final String[] HEADER_DETAILS = { "C2K_Identifier", "GHA", "Month", "Station", "Group", "Airline", //
         "Export_AWB/MWBs", "Export_Shipments_Manifested", "FWB_Received", "FWB_Received_Before_RCS", "RCS_Sent", //
         "Missing_RCS", "RCS%", "FFM_Sent", "DEP_Sent", "DEP_Sent_In-time", "Missing_DEP", "Late_DEP", "DEP%", //
         "Import_AWB/MWBs", "Import_Shipments_Manifested", "FWB_Received_Before_ATA", "FFM_Received_Before_ATA", //
         "RCF_Sent", "RCF_Sent_In-time", "Missing_RCF", "Late_RCF", "RCF%", "AWD_Sent", "AWD_Sent_In-time", //
         "Missing_AWD", "Late_AWD", "AWD%", "NFD_Messages_Sent", "NFD_Sent_In-time", "Missing_NFD", "Late_NFD", //
         "NFD%", "DLV_Event", "DLV_Sent_In-time", "Missing_DLV", "DLV%" };
   
}
