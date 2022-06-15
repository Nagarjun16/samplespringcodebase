/**
 * {@link ReportTransferTransitReferenceConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi.constants;

/**
 * Transfer Transit Reference Report POI Constants
 * 
 * @author NIIT Technologies Ltd
 */
public class ReportTransferTransitReferenceConstants {
   // 
   private ReportTransferTransitReferenceConstants() {
      throw new RuntimeException("Creating instance is not allowed");
   }
   //
   public static final String TRANSFER_TRANSIT_REFERENCE_FILE_NAME = "TransferTransitReferenceWorkingReport";
   public static final String TRANSFER_TRANSIT_REFERENCE_SHEET_NAME = "TransferTransitReference";
   //
   public static final String SQL_SELECT_TRANSFER_TRANSIT_REFERENCE_DATA = "sqlSelectTransferTransitReferenceData";
   //
   public static final String BLANK = "";
   //
   public static final String[] HEADER_DETAILS = { "GROUP", "Airline", "Export_Transfer_Shipments_Manifested", //
         "RCT_Sent", BLANK, BLANK, "Export_Transfer_FFM_Sent", "Export_Transfer_FFM_Sent_In-time", //
         "Export_Transfer_DEP_Sent", "Export_Transfer_DEP_Sent_In-time", BLANK, BLANK, BLANK, //
         "Import_Transfer_AWB/MWBs", "Import_Transfer_Shipments_Manifested", "Import_Transfer_FWB_Received", //
         "Import_Transfer_FFM_Received_Before_ATA", "Import_Transfer_RCF_Sent", "Import_Transfer_RCF_In-time", //
         BLANK, BLANK, BLANK, "TFD_Event_Completed", "TFD_Message_In-time", BLANK, BLANK, "Import_Transit_AWB/MWBs", //
         "Import_Transit_Shipments", "FWB_Received", "FFM_Received_Before_ATA", "Transit_RCF_Sent", //
         "Transit_RCF_sent_In-time", BLANK, BLANK, BLANK, "Transit_FFM_Sent", "Transit_FFM_Sent_In-time", //
         "Transit_DEP_Sent", "Transit_DEP_Sent_In-time", BLANK, BLANK, BLANK };
   
}
