package com.ngen.cosys.report.service.poi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

public class PerformanceReportUtil {

   private static final Logger logger = LoggerFactory.getLogger(PerformanceReportUtil.class);
   final static DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("ddMMM");
   final static DateTimeFormatter dateformatter1 = MultiTenantUtility.getTenantDateFormat();

   public static HSSFSheet createSheet(HSSFWorkbook workbook, String name) {
      return workbook.createSheet(name);

   }

   public static void setCommonSheetHeader(HSSFSheet sheet, HSSFWorkbook workbook, LocalDate requestModel) {
      CellStyle style = workbook.createCellStyle();
      Font font = workbook.createFont();
      font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
      style.setFont(font);
      String[] allHeader = { "MAIL_TAG_INF", "BKG_FLT", "BKG_FLT_DAT", "BKG_FLT_DAT_RFDT", "AA_DAT_RFDT_SCAN",
            "AA_DAT_IPS_SCAN", "TA_DAT_SCAN", "HA_DAT_SCAN", "HA_FLT", "HA_FLT_STD", "CONTAINER", "HA_DAT_SCAN_LAST",
            "HA_FLT_LAST", "HA_FLT_STD_LAST", "CONTAINER_LAST", "SRC_SYS", "SATS_CAR", "HA_MINUS_PA_FLT",
            "HA_MINUS_AA_IPS", "PA_FLT_MINUS_AA_IPS", "PA_FLT_MINUS_AA_RFDT", "PA_FLT_MINUS_TA", "DAT_PA_RCV",
            "INVALD_PA", "WHSE_OFD", "RAMP_OFD" };
      logger.warn("All Header For sheet: " + sheet.getSheetName() + "  " + allHeader);
      Row header = null;
      header = sheet.createRow(0);
      for (int i = 0; i < allHeader.length; i++) {
         header.createCell(i).setCellValue(allHeader[i]);
         if (i > 0) {
            sheet.setColumnWidth(i, 5000);
         }
      }
      sheet.setColumnWidth(0, 10000);
      String dateFormat = dateformatter.format(requestModel);
      if (sheet.getSheetName().equalsIgnoreCase(dateFormat)) {
         setExtraHeader(header, style);
      }

   }

   private static void setExtraHeader(Row header, CellStyle style) {
      header.createCell(50).setCellValue("HA=PA");
      header.getCell(50).setCellStyle(style);
      header.createCell(51).setCellValue("HA < PA");
      header.getCell(51).setCellStyle(style);
      header.createCell(52).setCellValue("HA - PA 4 Hrs");
      header.getCell(52).setCellStyle(style);
      header.createCell(53).setCellValue("STD(PA) - TA ≥ 6 Hrs (DNATA)");
      header.getCell(53).setCellStyle(style);
      header.createCell(54).setCellValue("HA- AA < 24 hrs");
      header.getCell(54).setCellStyle(style);
      header.createCell(55).setCellValue("HA-AA > 24 (SATS) but with HA=PA and HA<PA");
      header.getCell(55).setCellStyle(style);
      header.createCell(56).setCellValue("IPS AA Scan For Sats");
      header.getCell(56).setCellStyle(style);
      header.createCell(57).setCellValue("IPS AA Scan For DNATA");
      header.getCell(57).setCellStyle(style);
      header.createCell(58).setCellValue("PA -  IPS (AA) < 4hrs and having HA For Sata");
      header.getCell(58).setCellStyle(style);
      header.createCell(59).setCellValue("PA -  IPS (AA) < 4hrs and having HA For Dnata");
      header.getCell(59).setCellStyle(style);
      header.createCell(60).setCellValue("PA -  IPS (AA) < 4hrs and no HA");
      header.getCell(60).setCellStyle(style);

   }

   public static void setSummarySheetHeader(HSSFSheet summarySheet, Workbook workbook, String downloadedDate) {
      summarySheet.setColumnWidth(0, 6000);
      summarySheet.setColumnWidth(4, 5000);
      CellStyle style = workbook.createCellStyle(); // Create new style
      style.setWrapText(true);
      Row row0 = summarySheet.createRow(0);
      row0.createCell(0).setCellValue("eHUB Summary - " + downloadedDate);
      Row row1 = summarySheet.createRow(1);
      row1.createCell(1).setCellValue("SATS");
      row1.createCell(2).setCellValue("DNATA");
      row1.createCell(3).setCellValue("Total");
      row1.createCell(4).setCellValue("After Adjusting for Space & Late Lodgement ");
      row1.getCell(4).setCellStyle(style);

      String[] summaryVerticalHeader = { "", "", "Total Bags", "Total Bags with PA", "Total HA/TA", "Manual Handover",
            "HA% or TA%", "RFDT AA/TA", "", "Uplift Reliability", "SATS Performance", "HA = PA", "HA < PA",
            "HA - PA < 4 Hrs", "Dnata Performance", "Manual Handover ≥ 6hrs", "STD(PA) - TA ≥ 6 Hrs (DNATA)",
            "Handover to Dnata ≥  6hrs", "Overall SLA Performance", "",
            "Acceptance Scan  – Handover Scan < 24 hrs (All service types)", "HA- AA < 24 hrs",
            "HA-AA > 24 (SATS) but with HA=PA and HA<PA", "", "IPS AA Scan", "PA -  IPS (AA) < 4hrs ",
            "PA -  IPS (AA) < 4hrs and no HA ", "Space Issue", "IPS/PA Issue", "N.A SATS CAR", "N.A Non SATS CAR",
            "BKG_FLT_DAT - IPS <6HRS", "New TA Implementation", "Bags With Missing HA Details" };

      for (int i = 2; i < summaryVerticalHeader.length; i++) {
         Row row = summarySheet.createRow(i);
         row.createCell(0).setCellValue(summaryVerticalHeader[i]);
         row.getCell(0).setCellStyle(style);
      }

   }
}
