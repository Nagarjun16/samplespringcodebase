/**
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi.constants;

/**
 * Special Cargo Monitoring Report POI Constants
 * 
 * @author NIIT Technologies Ltd
 */
public class SpecialCargoMonitoringConstants {

   private SpecialCargoMonitoringConstants() {
      throw new RuntimeException("Creating instance is not allowed");
   }

   public static final String DATE_FORMAT = "YYYY-MM";
   public static final String DATE = "ddMMMyyyy";
   public static final String DATETIME = "ddMMMyyyy HH:mm";
   public static final String T_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";
   public static final String TIME = "HH:mm";
   
   public static final String SPECIAL_CARGO_MONITORNING_FILE_NAME = "SpecialCargoMonitoringReport";
   public static final String SPECIAL_CARGO_MONITORNING_SHEET_NAME = "Special Cargo Monitoring Report";
//   public static final String SQL_SELECT_COOLPORT_MONITORNING_DATA = "sqlSelectCoolportMonitoringReportData";
   public static final String[] TOP_HEADER = { "SNo", "Flight No", "Date", "ATD/ETD/STD", "DG.Seg", "AWB No", "DG.ORG/DST", 
	   "Bkg Pc", "DG.Bkg Wgt", "AWB SHC", "HO vs DLS", null, "HO vs ULD Tag", null, 
	   "HO vs Load", null, "HO vs Notoc", "Request", null, null, null, null, null,
	   "Handover & Confirm", null, null, null, null, null, null, null, null, null, null,
	   "Images", null, null, null
   };
   public static final String[] SUB_HEADER = { null, null, null, null, null, null, null, 
	   null, null, null, "Missing ULD", "Missing ULD/SHC", "Missing ULD", "Missing ULD/SHC", 
	   "Missing ULD", "Missing ULD/SHC", "Missing ULD", "Shipment Loc", "Loc Pc", "Req Pc", "Req By", "HO Loc", "Date/Time", 
	   "From Loc", "Req Pc", "Req By", "Req Time", "Ho Loc", "Ho Pc", "Ho SHC", "Ho By", "Ho To loginId", "Ho To StaffId", "Date/Time",
	   "Location", "TimeStamp", "User", "File Name"
   };
   
}
