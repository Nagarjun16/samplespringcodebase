/**
 * {@link ReportTransferTransitWorkingListConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi.constants;

/**
 * Transfer Transit Working List Report POI Constants
 * 
 * @author NIIT Technologies Ltd
 */
public class ReportTransferTransitWorkingListConstants {
   //
   private ReportTransferTransitWorkingListConstants() {
      throw new RuntimeException("Creating instance is not allowed");
   }
   //
   public static final String DATE_FORMAT = "YYYY-MM";
   public static final String TRANSFER_TRANSIT_WORKINGLIST_FILE_NAME = "TransferTransitWorkingListReport";
   public static final String TRANSFER_TRANSIT_WORKINGLIST_SHEET_NAME = "GHAT-SAT-%s_Working Report";
   //
   public static final String SQL_SELECT_TRANSFER_TRANSIT_WORKINGLIST_DATA = "sqlSelectTransferTransitWorkingListData";
   //
   public static final String[] HEADER_DETAILS = { "C2K_Identifier", "GHA", "Month", "Station", "Airline", //
         "Export_Transfer_Shipments_Manifested", "RCT_Sent", "Missing_RCT", "RCT%", "Export_Transfer_FFM_Sent", //
         "Export_Transfer_FFM_Sent_In-time", "Export_Transfer_DEP_Sent", "Export_Transfer_DEP_Sent_In-time", //
         "Missing_DEP", "Late_DEP", "DEP%", "Import_Transfer_AWB/MWBs", "Import_Transfer_Shipments_Manifested", //
         "Import_Transfer_FWB_Received", "Import_Transfer_FFM_Received_Before_ATA", "Import_Transfer_RCF_Sent", //
         "Import_Transfer_RCF_In-time", "Missing_RCF", "Late_RCF", "RCF%", "TFD_Event_Completed", "TFD_Message_In-time", //
         "Missing_TFD", "TFD%", "Import_Transit_AWB/MWBs", "Import_Transit_Shipment_Movements", "FWB_Received", //
         "FFM_Received_Before_ATA", "Transit_RCF_Sent", "Transit_RCF_sent_In-time", "Missing_RCF", "Late_RCF", //
         "RCF%", "Transit_FFM_Sent", "Transit_FFM_Sent_In-time", "Transit_DEP_Sent", "Transit_DEP_Sent_In-time", //
         "Missing_DEP", "Late_DEP", "DEP%" };
   
}
