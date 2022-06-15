/**
 * {@link CiQReportConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.common;

/**
 * CiQ Report Constants
 * 
 * @author NIIT Technologies Ltd
 */
public final class CiQReportConstants {

   private CiQReportConstants() {
      throw new RuntimeException("Creating instances are not allowed");
   }

   // Duration Limit
   public static final Long DURATION_LIMIT = 30L;
   public static final String INITIATED = "INITIATED";
   public static final String INPROGRESS = "INPROGRESS";
   public static final String COMPLETED = "COMPLETED";
   // Beans
   public static final String CIQ_REPORT_DAILY_JOB = "ciqReportDailyJob";
   public static final String CIQ_REPORT_WEEKLY_JOB = "ciqReportWeeklyJob";
   public static final String CIQ_REPORT_MONTHLY_JOB = "ciqReportMonthlyJob";
   // Party Types
   public static final String PARTY_IATA = "IATA";
   public static final String PARTY_SATS = "SATS";
   public static final String FROM = "fromdate";
   public static final String TO = "todate";
   public static final String FROM_POI_REPORT = "fromDate";
   public static final String TO_POI_REPORT = "toDate";
   // CASE statements
   public static final String IMPORT_EXPORT = "I/E";
   public static final String IMPORT_EXPORT_WORKING = "Imp Exp Working Report";
   public static final String IMPORT_EXPORT_REFERENCE_WORKING_LIST = "ImpExpRefWorkingListReport";
   public static final String IMPORT_EXPORT_REFERENCE = "Import Export Reference Report";
   public static final String CIQ_MESSAGE_TEMPLATE = "Message Template Report";
   public static final String TRANSFER_TRANSIT_WORKING = "Transfer Transit WorkingReport";
   public static final String TRANSFER_TRANSIT = "Transfer/Transit";
   public static final String TRANSFER_TRANSIT_REFERENCE = "TransferTransitReferenceReport";
   public static final String TRANSFER_TRANSIT_REFERENCE_WORKING_LIST = "TransferTransitRefWorkgListRep";

   // Mail Subject
   public static final String IMPORT_EXPORT_SUBJECT = "Import Export Report";
   public static final String IMPORT_EXPORT_WORKING_SUBJECT = "Import Export Working Report";
   public static final String IMPORT_EXPORT_REFERENCE_WORKING_LIST_SUBJECT = "Import Export Reference Working List Report";
   public static final String IMPORT_EXPORT_REFERENCE_SUBJECT = "Import Export Reference Report";
   public static final String CIQ_MESSAGE_TEMPLATE_SUBJECT = "Message Template Report";
   public static final String TRANSFER_TRANSIT_WORKING_SUBJECT = "Transfer Transit Working Report";
   public static final String TRANSFER_TRANSIT_SUBJECT = "Transfer Transit Report";
   public static final String TRANSFER_TRANSIT_REFERENCE_SUBJECT = "Transfer Transit Reference Report";
   public static final String TRANSFER_TRANSIT_REFERENCE_WORKING_LIST_SUBJECT = "Transfer Transit Reference Working List Report";

   public static final String IMPORT_EXPORT_REPORT_NAME = "GHAL-SAT-%s";
   public static final String IMPORT_EXPORT_REFERENCE_REPORT_NAME = "GHAL-SAT-REF-%s";
   public static final String IMPORT_EXPORT_WORKING_REPORT_NAME = "GHAL-SAT-%s_Working Report";
   public static final String IMPORT_EXPORT_REFERENCE_WORKING_LIST_REPORT_NAME = "GHAL-SAT-REF-%s_Working Report";
   public static final String TRANSFER_TRANSIT_REPORT_NAME = "GHAT-SAT-%s";
   public static final String TRANSFER_TRANSIT_REFERENCE_REPORT_NAME = "GHAT-SAT-REF-%s";
   public static final String TRANSFER_TRANSIT_WORKING_REPORT_NAME = "GHAT-SAT-%s_Working Report";
   public static final String TRANSFER_TRANSIT_REFERENCE_WORKING_LIST_REPORT_NAME = "GHAT-SAT-REF-%s_Working Report";
   public static final String CIQ_MESSAGE_TEMPLATE_REPORT_NAME = "Message-Template";

   public static final String CIQ_FSU_EXCEPTION_REPORT = "FSU Messaging Exception Report";
   public static final String CIQ_FSU_MESSAGING_REPORT = "FSU Messaging Report";
   public static final String CARRIER_EXISTS = "carrierflag";
   public static final String CARRIER_CODE = "carrier";
   public static final String GHA_IM_EX = "GHA I/E";
   public static final String GHA_TRANSFER_TRANSIT = "GHA Transfer/Transit";
   // Message Type Reports Name (ReportService)
   public static final String IMPORT_EXPORT_REPORT = "Import_Export";
   public static final String IMPORT_EXPORT_WORKING_REPORT = "importExportWorkingListReport";
   public static final String IMPORT_EXPORT_REFERENCE_WORKING_LIST_REPORT = "importExportReferenceWorkingListReport";
   public static final String IMPORT_EXPORT_REFERENCE_REPORT = "Import-Export_Reference_report";
   public static final String CIQ_MESSAGE_TEMPLATE_REPORT = "Message_Template_Report";
   public static final String TRANSFER_TRANSIT_WORKING_REPORT = "transferTransitWorkingListReport";
   public static final String TRANSFER_TRANSIT_REPORT = "TransferTransitReport";
   public static final String TRANSFER_TRANSIT_REFERENCE_REPORT = "TransferTransitReferenceReport";
   public static final String TRANSFER_TRANSIT_REFERENCE_WORKING_LIST_REPORT = "transferTransitReferenceWorkingListReport";

   public static final String GHA_IMP_EXP_REFERENCE_REPORT = "gha_imp_exp_reference_report";
   public static final String GHA_TRF_TRN_REPORT_ = "GHA_trf_trn_report_";
   public static final String TRANSFERTRANSITWORKINGREPORT = "TransferTransitWorkingReport";
   public static final String TRANSFERTRANSITREFERENCEREPORT = "transferTransitReferenceReport";
   public static final String MESSAGE_TEMPLATE_REPORT_NAME = "Message_Template_Report";
   public static final String EXPORT_REFERENCE_REPORT = "Import-Export_Reference_report";
   public static final String TRANSFER_TRANSIT_WORKING_REPORT_FILE_NAME = "Transfer-Transit-Working-Report";
   public static final String TRF_TRN_REFERENCE = "C2K Trf Trn Reference ";
   // FSU Exception message typess
   public static final String RCS = "RCS";
   public static final String NFD = "NFD";
   public static final String RCF = "RCF";
   public static final String DLV = "DLV";
   public static final String AWD = "AWD";
   public static final String DEP = "DEP";
   public static final String TFD = "TFD";
   public static final String RCT = "RCT";
   // FSU EXCEPTION REPORT NAMES
   public static final String FSU_EXCEPTION_REPORT_RCS = "FSU_EXCEPTION_REPORT_RCS_EXP";
   public static final String FSU_EXCEPTION_REPORT_RCF_OR_NFD = "FSU_EXCEPTION_REPORT_RCF_OR_NFD_EXP";
   public static final String FSU_EXCEPTION_REPORT_AWD = "FSU_EXCEPTION_REPORT_AWD_EXP";
   public static final String FSU_EXCEPTION_REPORT_DEP = "FSU_EXCEPTION_REPORT_DEP_EXP";
   public static final String FSU_EXCEPTION_REPORT_DLV = "FSU_EXCEPTION_REPORT_DLV_EXP";
   public static final String FSU_EXCEPTION_REPORT_TFD = "FSU_EXCEPTION_REPORT_TFD_EXP";
   public static final String FSU_EXCEPTION_REPORT_RCT = "FSU_EXCEPTION_REPORT_RCT_EXP";
   // FSU MESSAGING REPORT NAMES
   public static final String FSU_MESSAGING_REPORT_RCS = "FSU_EXCEPTION_REPORT_RCS";
   public static final String FSU_MESSAGING_REPORT_RCF_OR_NFD = "FSU_EXCEPTION_REPORT_RCF_OR_NFD";
   public static final String FSU_MESSAGING_REPORT_AWD = "FSU_EXCEPTION_REPORT_AWD";
   public static final String FSU_MESSAGING_REPORT_DEP = "FSU_EXCEPTION_REPORT_DEP";
   public static final String FSU_MESSAGING_REPORT_DLV = "FSU_Exception_DLV";
   public static final String FSU_MESSAGING_REPORT_TFD = "FSU_EXCEPTION_REPORT_TFD";
   public static final String FSU_MESSAGING_REPORT_RCT = "FSU_EXCEPTION_REPORT_RCT";


   public static final String CIQ_TRANSFER_TRANSIT_DEP = "CiQ_Transfer_Transit_DEP";
   // constants for file names
   public static final String WORKSHEET_NAME = "workSheetName";
   public static final String MESSAGE = "-Message-";
   public static final String CSV = "CSV";
   public static final String XLS = "XLS";
   public static final String XLSX = "xlsx";
   public static final String TRUE = "1";
   public static final String FALSE = "0";

}
