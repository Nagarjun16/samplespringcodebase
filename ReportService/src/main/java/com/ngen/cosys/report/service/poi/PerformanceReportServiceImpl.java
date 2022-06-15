package com.ngen.cosys.report.service.poi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.PerformanceReport.PerformanceReportDao;
import com.ngen.cosys.report.service.PerformanceReportService;
import com.ngen.cosys.report.service.poi.enums.PerformanceReportUtils;
import com.ngen.cosys.report.service.poi.model.PerformanceReportModel;
import com.ngen.cosys.report.service.poi.util.PerformanceReportUtil;

import reactor.util.CollectionUtils;

@Service
public class PerformanceReportServiceImpl implements PerformanceReportService {

   @Autowired
   PerformanceReportDao dao;

   final DateTimeFormatter dateformatter = DateTimeFormatter
         .ofPattern(PerformanceReportUtils.DATEMONTH_FORMAT.getName());
   final DateTimeFormatter dateformatter1 = MultiTenantUtility.getTenantDateFormat();

   LocalDate fromDateRequest = null;

   private static final Logger logger = LoggerFactory.getLogger(PerformanceReportServiceImpl.class);

   @Override
   public OutputStream getPerformancereport(PerformanceReportModel requestModel) throws CustomException, IOException {
      logger.warn("Started executing the Performance Report at :" + LocalDateTime.now());
      List<PerformanceReportModel> response = dao.getPerofrmanceReport(requestModel);
      logger.warn("data from query for performance report at : " + LocalDateTime.now() + " :" + response);
      HSSFWorkbook workbook = new HSSFWorkbook();
      HSSFSheet sheet = null;
      HSSFSheet summarySheet = null;
      HSSFSheet lateLodgeInSheet = null;
      HSSFCellStyle cellStyle = (HSSFCellStyle) workbook.createCellStyle();
      cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));

      OutputStream outputStream = null;
      if (!CollectionUtils.isEmpty(response)) {
         try {
            fromDateRequest = requestModel.getFromDate();
            logger.warn("Started creating sheets");
            sheet = PerformanceReportUtil.createSheet(workbook, dateformatter.format(fromDateRequest));
            summarySheet = PerformanceReportUtil.createSheet(workbook, PerformanceReportUtils.SUMMARY.getName());
            lateLodgeInSheet = PerformanceReportUtil.createSheet(workbook,
                  PerformanceReportUtils.LATE_LODGE_IN.getName());
            workbook.setForceFormulaRecalculation(false);

            logger.warn("Started creating sheet header");
            PerformanceReportUtil.setCommonSheetHeader(sheet, workbook, fromDateRequest);
            PerformanceReportUtil.setSummarySheetHeader(summarySheet, workbook, dateformatter1.format(fromDateRequest));
            PerformanceReportUtil.setCommonSheetHeader(lateLodgeInSheet, workbook, fromDateRequest);
            logger.warn("Sheet Creation finished");

            int satsCount = 0;
            int dnataCount = 0;
            int haCount = 0;
            int taCount = 0;
            int manualHandoverCount = 0;
            int haEqualsPaCount = 0;
            int haLessThanPACount = 0;
            int haLessThanPABy4Hrs = 0;
            int paMinusTABy6Hrs = 0;
            int haLessThanAAByTwentyFourHrsCount = 0;
            int hAGreaterThanAACount = 0;
            int ipsAAForSatsCount = 0;
            int ipsAAForDNATACount = 0;
            int paLessThanAAHavingHAForSATSCount = 0;
            int paLessThanAAHavingHAForDNATACount = 0;
            int palessThanHAHavingNoHACount = 0;
            int rfdtAACount = 0;
            int taDateCount = 0;
            List<PerformanceReportModel> lateLodgeInBags = new ArrayList<>();
            // writing the data into the report
            int rowCountDetails = 0;
            for (PerformanceReportModel data : response) {

               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())) {
                  satsCount++;
               }
               if (PerformanceReportUtils.NO.getName().equalsIgnoreCase(data.getSATS_CAR())) {
                  dnataCount++;
               }

               Row dataRow = sheet.createRow(++rowCountDetails);
               writeBook(data, dataRow);
               if (!StringUtils.isEmpty(data.getHA_DAT_SCAN())) {
                  haCount++;
               }
               if (!StringUtils.isEmpty(data.getTA_DAT_SCAN())) {
                  taCount++;
               }
               if (!StringUtils.isEmpty(data.getManualHandoverBags())) {
                  manualHandoverCount++;
               }
               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.HAEQUALSPA.getName().equalsIgnoreCase(data.getHaAndPACompare())) {
                  haEqualsPaCount++;
               }

               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.HALESSTHANPA.getName().equalsIgnoreCase(data.getHaAndPACompare())) {
                  haLessThanPACount++;
               }

               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.HAHAS4HRSSCANTIME.getName().equalsIgnoreCase(data.getHaAndPACompare())) {
                  haLessThanPABy4Hrs++;
               }
               if (PerformanceReportUtils.NO.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.PAGREATERTHANTA.getName().equalsIgnoreCase(data.getPaAndTACompare())) {
                  paMinusTABy6Hrs++;
               }
               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.HALESSTHANAA24HRS.getName().equalsIgnoreCase(data.getHaAndAACompare())) {
                  haLessThanAAByTwentyFourHrsCount++;
               }
               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.HAGREATERTHANAA24HRS.getName()
                           .equalsIgnoreCase(data.getHaAndAACompare())) {
                  hAGreaterThanAACount++;
               }
               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && (!StringUtils.isEmpty(data.getIpsAAScan()))) {
                  ipsAAForSatsCount++;
               }
               if (PerformanceReportUtils.NO.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && (!StringUtils.isEmpty(data.getIpsAAScan()))) {
                  ipsAAForDNATACount++;
               }

               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.PALESSTHANIPSAAHAVINGAA.getName()
                           .equalsIgnoreCase(data.getPaAndIPSAACompare())) {
                  paLessThanAAHavingHAForSATSCount++;
               }
               if (PerformanceReportUtils.NO.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.PALESSTHANIPSAAHAVINGAA.getName()
                           .equalsIgnoreCase(data.getPaAndIPSAACompare())) {
                  paLessThanAAHavingHAForDNATACount++;
               }
               if (PerformanceReportUtils.YES.getName().equalsIgnoreCase(data.getSATS_CAR())
                     && PerformanceReportUtils.PALESSTHANIPSAAHAVINGNOHA.getName()
                           .equalsIgnoreCase(data.getPaAndIPSAACompare())) {
                  palessThanHAHavingNoHACount++;
               }
               if (!StringUtils.isEmpty(data.getAA_DAT_RFDT_SCAN())) {
                  rfdtAACount++;
               }
               if (!StringUtils.isEmpty(data.getTA_DAT_SCAN())) {
                  taDateCount++;
               }
            }
            logger.warn("Started writing the data into SummarySheet");
            setDataIntoSummarySheet(summarySheet, workbook, cellStyle, response, satsCount, dnataCount, haCount,
                  taCount, manualHandoverCount, haEqualsPaCount, haLessThanPACount, haLessThanPABy4Hrs, paMinusTABy6Hrs,
                  haLessThanAAByTwentyFourHrsCount, hAGreaterThanAACount, ipsAAForSatsCount, ipsAAForDNATACount,
                  paLessThanAAHavingHAForSATSCount, paLessThanAAHavingHAForDNATACount, palessThanHAHavingNoHACount,
                  rfdtAACount, taDateCount);

            lateLodgeInBags = response.stream()
                  .filter(data -> (PerformanceReportUtils.PALESSTHANIPSAAHAVINGNOHA.getName()
                        .equalsIgnoreCase(data.getPaAndIPSAACompare())
                        || PerformanceReportUtils.PALESSTHANIPSAAHAVINGAA.getName()
                              .equalsIgnoreCase(data.getPaAndIPSAACompare()))
                        && !PerformanceReportUtils.HAEQUALSPA.getName().equalsIgnoreCase(data.getHaAndPACompare())
                        && !PerformanceReportUtils.HALESSTHANPA.getName().equalsIgnoreCase(data.getHaAndPACompare())
                        && !PerformanceReportUtils.HAHAS4HRSSCANTIME.getName()
                              .equalsIgnoreCase(data.getHaAndPACompare()))
                  .collect(Collectors.toList());

            int lateLodgeInCount = 0;
            if (!CollectionUtils.isEmpty(lateLodgeInBags)) {
               for (PerformanceReportModel data : lateLodgeInBags) {
                  Row row = lateLodgeInSheet.createRow(++lateLodgeInCount);
                  writeBook(data, row);
               }
               // lateLodgeInSheet.autoSizeColumn(0);
            }
            logger.warn("Header Created and now writing the data into output stream");
            outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            logger.warn("After writing the data into workbook outputstream");
            outputStream.close();
            logger.warn("closing the output stream");
            workbook.close();
            logger.warn("closing the workbook stream");
         } catch (Exception e) {
            logger.warn("Performance report not able to generate " + e);
         }

      }
      logger.warn("everything happened successfully. Returning the outputstream");
      return outputStream;
   }

   private void writeBook(PerformanceReportModel data, Row row) {
      row.createCell(0).setCellValue(data.getBookingMailBags()); // A

      row.createCell(1).setCellValue(data.getBKG_FLT()); // B

      row.createCell(2).setCellValue(data.getBKG_FLT_DAT()); // C

      row.createCell(3).setCellValue(data.getBKG_FLT_DAT_RFDT()); // D (Leg - DateSTD)

      row.createCell(4).setCellValue(data.getAA_DAT_RFDT_SCAN()); // E (Exp_eAcceptanceHouseInformation.CreatedDateTime)

      row.createCell(5).setCellValue(data.getAA_DAT_IPS_SCAN()); // F

      row.createCell(6).setCellValue(data.getTA_DAT_SCAN()); // G

      row.createCell(7).setCellValue(data.getHA_DAT_SCAN_FIRST()); // H (ManifestedDateTime)

      row.createCell(8).setCellValue(data.getHA_FLT_FIRST()); // I

      row.createCell(9).setCellValue(data.getHA_FLT_STD_FIRST()); // J (ManifestedFlightDate)

      row.createCell(10).setCellValue(data.getCONTAINER_FIRST()); // K

      row.createCell(11).setCellValue(data.getHA_DAT_SCAN()); // L (FirstTimeMailManifestCompletedAt)

      row.createCell(12).setCellValue(data.getHA_FLT()); // M

      row.createCell(13).setCellValue(data.getHA_FLT_STD()); // N (Flt_OperativeFlight_Segments.DateSTD)

      row.createCell(14).setCellValue(data.getCONTAINER()); // O

      row.createCell(15).setCellValue(data.getSRC_SYS()); // P

      row.createCell(16).setCellValue(data.getSATS_CAR()); // Q

      row.createCell(17).setCellValue(
            calculateHourRange(data.getHA_MINUS_PA_FLT(), PerformanceReportUtils.HA_MINUS_PA_FLT.getName(), data)); // R

      row.createCell(18).setCellValue(
            calculateHourRange(data.getHA_MINUS_AA_IPS(), PerformanceReportUtils.HA_MINUS_AA_IPS.getName(), data)); // S

      row.createCell(19).setCellValue(calculateHourRange(data.getPA_FLT_MINUS_AA_IPS(),
            PerformanceReportUtils.PA_FLT_MINUS_AA_IPS.getName(), data)); // T

      row.createCell(20).setCellValue(calculateHourRange(data.getPA_FLT_MINUS_AA_RFDT(),
            PerformanceReportUtils.PA_FLT_MINUS_AA_RFDT.getName(), data)); // U

      row.createCell(21).setCellValue(
            calculateHourRange(data.getPA_FLT_MINUS_TA(), PerformanceReportUtils.PA_FLT_MINUS_TA.getName(), data)); // V

      row.createCell(22).setCellValue(data.getDAT_PA_RCV()); // W

      row.createCell(23).setCellValue(data.getINVALD_PA()); // X

      row.createCell(24).setCellValue(data.getWHSE_OFD()); // Y

      row.createCell(25).setCellValue(data.getRAMP_OFD()); // Z

   }

   public String calculateHourRange(String hours, String column, PerformanceReportModel data) {
      if (!StringUtils.isEmpty(hours)) {
         int hourInt = Integer.valueOf(hours);
         if (column.equals(PerformanceReportUtils.HA_MINUS_PA_FLT.getName())
               || column.equals(PerformanceReportUtils.PA_FLT_MINUS_AA_IPS.getName())
               || column.equals(PerformanceReportUtils.PA_FLT_MINUS_AA_RFDT.getName())) {
            if (column.equals(PerformanceReportUtils.HA_MINUS_PA_FLT.getName())) { // Early, SAME FLIGHT and N.A logic
               if (!StringUtils.isEmpty(data.getHA_FLT_STD_FIRST())
                     && !StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT())) {
                  if (data.getHA_FLT_STD_FIRST().equalsIgnoreCase(data.getBKG_FLT_DAT_RFDT())) { // J equals D
                     data.setHaAndPACompare(PerformanceReportUtils.HAEQUALSPA.getName());
                     return PerformanceReportUtils.SAME_FLIGHT.getName();
                  }
               }
               // if J minus D is negative
               if (!ObjectUtils.isEmpty(data.getValidateEarly()) && data.getValidateEarly() < 0) {
                  if (PerformanceReportUtils.HA_MINUS_PA_FLT.getName().equals(column)) {
                     data.setHaAndPACompare(PerformanceReportUtils.HALESSTHANPA.getName());
                  }
                  return PerformanceReportUtils.EARLY.getName();
               }
               // If BKG_FLT_DAT_RFDT is blank, HA_MINUS_PA_FLT should be N.A
               if (StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT())) {
                  data.setHaAndPACompare(PerformanceReportUtils.NA.getName());
                  return PerformanceReportUtils.NA.getName();
               }
            }

            // If either/both BKG_FLT_RFDT & AA_DAT_IPS_SCAN are blank, PA_FLT_MINUS_AA_IPS
            // should be N.A
            if (column.equals(PerformanceReportUtils.PA_FLT_MINUS_AA_IPS.getName())) {
               if (StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT()) || StringUtils.isEmpty(data.getAA_DAT_IPS_SCAN())) {
                  return PerformanceReportUtils.NA.getName();
               }
            }

            if (column.equals(PerformanceReportUtils.PA_FLT_MINUS_AA_RFDT.getName())) {
               if (StringUtils.isEmpty(data.getAA_DAT_RFDT_SCAN()) || StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT())) {
                  return PerformanceReportUtils.NA.getName();
               }
            }

            if (hourInt <= 4) {
               if (PerformanceReportUtils.PA_FLT_MINUS_AA_IPS.getName().equalsIgnoreCase(column)) {
                  if (!StringUtils.isEmpty(data.getHA_FLT_STD())) {
                     data.setPaAndIPSAACompare(PerformanceReportUtils.PALESSTHANIPSAAHAVINGAA.getName());
                  } else {
                     data.setPaAndIPSAACompare(PerformanceReportUtils.PALESSTHANIPSAAHAVINGNOHA.getName());
                  }
               } else if (PerformanceReportUtils.HA_MINUS_PA_FLT.getName().equals(column)) {
                  data.setHaAndPACompare(PerformanceReportUtils.HAHAS4HRSSCANTIME.getName());
               }
               return "<4 Hrs";
            } else if (hourInt > 4 && hourInt <= 12) {
               return "4-12 Hrs";
            } else if (hourInt > 12 && hourInt <= 18) {
               return "12-18 Hrs";
            } else if (hourInt > 18 && hourInt <= 24) {
               return "18-24 Hrs";
            } else if (hourInt > 24) {
               return ">24 Hrs";
            }
         } else if (column.equals(PerformanceReportUtils.HA_MINUS_AA_IPS.getName())) {
            // If AA_DAT_IPS_SCAN is blank, HA_MINUS_AA_IPS should be N.A
            if (StringUtils.isEmpty(data.getAA_DAT_IPS_SCAN())) {
               return PerformanceReportUtils.NA.getName();
            }
            if (hourInt <= 24) {
               return "<24 Hrs";
            } else if (hourInt > 24) {
               return ">24 Hrs";
            }
         } else if (column.equals(PerformanceReportUtils.PA_FLT_MINUS_TA.getName())) {
            // if H or G is null
            if (StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT()) || StringUtils.isEmpty(data.getTA_DAT_SCAN())) {
               return PerformanceReportUtils.NA.getName();
            }
            if (hourInt <= 6) {
               return "<6 Hrs";
            } else if (hourInt > 6 && hourInt <= 12) {
               return "6-12 Hrs";
            } else if (hourInt > 12 && hourInt <= 18) {
               return "12-18 Hrs";
            } else if (hourInt > 18 && hourInt <= 24) {
               return "18-24 Hrs";
            } else if (hourInt > 24) {
               return ">24 Hrs";
            }
         }

      } else {
         if (PerformanceReportUtils.HA_MINUS_PA_FLT.getName().equals(column)
               && StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT())) {
            return PerformanceReportUtils.NA.getName();
         } else if (PerformanceReportUtils.HA_MINUS_AA_IPS.getName().equals(column)
               && StringUtils.isEmpty(data.getAA_DAT_IPS_SCAN())) {
            return PerformanceReportUtils.NA.getName();
         } else if (PerformanceReportUtils.PA_FLT_MINUS_AA_IPS.getName().equals(column)
               && (StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT()) || StringUtils.isEmpty(data.getAA_DAT_IPS_SCAN()))) {
            return PerformanceReportUtils.NA.getName();
         } else if (PerformanceReportUtils.PA_FLT_MINUS_AA_RFDT.getName().equals(column)
               && (StringUtils.isEmpty(data.getAA_DAT_RFDT_SCAN())
                     || StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT()))) {
            return PerformanceReportUtils.NA.getName();
         } else if (PerformanceReportUtils.PA_FLT_MINUS_TA.getName().equals(column)
               && (StringUtils.isEmpty(data.getBKG_FLT_DAT_RFDT()) || StringUtils.isEmpty(data.getTA_DAT_SCAN()))) {
            return PerformanceReportUtils.NA.getName();
         }
      }
      return null;
   }

   private void setDataIntoSummarySheet(HSSFSheet summarySheet, Workbook wb, HSSFCellStyle cellStyle,
         List<PerformanceReportModel> response, int satsCount, int dnataCount, int haCount, int taCount,
         int manualHandoverCount, int haEqualsPaCount, int haLessThanPACount, int haLessThanPABy4Hrs,
         int paMinusTABy6Hrs, int haLessThanAAByTwentyFourHrsCount, int hAGreaterThanAACount, int ipsAAForSatsCount,
         int ipsAAForDNATACount, int paLessThanAAHavingHAForSATSCount, int paLessThanAAHavingHAForDNATACount,
         int palessThanHAHavingNoHACount, int rfdtAACount, int taDateCount) {

      summarySheet.getRow(2).createCell(1).setCellValue(satsCount);

      summarySheet.getRow(2).createCell(2).setCellValue(dnataCount);
      summarySheet.getRow(2).createCell(3).setCellFormula("B3+C3");

      summarySheet.getRow(3).createCell(1).setCellFormula("+B3");
      summarySheet.getRow(3).createCell(2).setCellFormula("+C3");
      summarySheet.getRow(3).createCell(3).setCellFormula("B4+C4");

      summarySheet.getRow(4).createCell(1).setCellValue(haCount);
      summarySheet.getRow(4).createCell(2).setCellValue(taCount);
      summarySheet.getRow(4).createCell(3).setCellFormula("B5+C5");

      summarySheet.getRow(5).createCell(1);
      summarySheet.getRow(5).createCell(2).setCellValue(manualHandoverCount);

      summarySheet.getRow(5).createCell(3).setCellFormula("D5+C6");

      summarySheet.getRow(6).createCell(1).setCellFormula("B5/B4");
      summarySheet.getRow(6).getCell(1).setCellStyle(cellStyle);

      summarySheet.getRow(6).createCell(2).setCellFormula("(C5+C6)/C4");
      summarySheet.getRow(6).getCell(2).setCellStyle(cellStyle);

      summarySheet.getRow(6).createCell(3).setCellFormula("(B5+C5+C6)/D3");
      summarySheet.getRow(6).getCell(3).setCellStyle(cellStyle);

      summarySheet.getRow(7).createCell(1).setCellValue(rfdtAACount);
      summarySheet.getRow(7).createCell(2).setCellValue(taDateCount);
      summarySheet.getRow(7).createCell(3).setCellFormula("B8+C8");

      summarySheet.getRow(11).createCell(1).setCellValue(haEqualsPaCount);
      summarySheet.getRow(11).createCell(3).setCellFormula("B12/B4");
      summarySheet.getRow(11).getCell(3).setCellStyle(cellStyle);
      summarySheet.getRow(11).createCell(4).setCellFormula("B12/(B4-B27-B28)");
      summarySheet.getRow(11).getCell(4).setCellStyle(cellStyle);

      summarySheet.getRow(12).createCell(1).setCellValue(haLessThanPACount);
      summarySheet.getRow(12).createCell(3).setCellFormula("(B12+B13)/B4");
      summarySheet.getRow(12).getCell(3).setCellStyle(cellStyle);
      summarySheet.getRow(12).createCell(4).setCellFormula("(B12+B13)/(B4-B27-B28)");
      summarySheet.getRow(12).getCell(4).setCellStyle(cellStyle);

      summarySheet.getRow(13).createCell(1).setCellValue(haLessThanPABy4Hrs);
      summarySheet.getRow(13).createCell(3).setCellFormula("(B12+B13+B14)/B4");
      summarySheet.getRow(13).getCell(3).setCellStyle(cellStyle);
      summarySheet.getRow(13).createCell(4).setCellFormula("(B12+B13+B14)/(B4-B27-B28)");
      summarySheet.getRow(13).getCell(4).setCellStyle(cellStyle);

      summarySheet.getRow(15).createCell(2).setCellFormula("C6");

      summarySheet.getRow(16).createCell(2).setCellValue(paMinusTABy6Hrs);

      summarySheet.getRow(17).createCell(2).setCellFormula("C16+C17+C31+C32+C33");
      summarySheet.getRow(17).createCell(3).setCellFormula("C18/C4");
      summarySheet.getRow(17).getCell(3).setCellStyle(cellStyle);
      summarySheet.getRow(17).createCell(4).setCellFormula("C18/(C4-C26)");
      summarySheet.getRow(17).getCell(4).setCellStyle(cellStyle);

      summarySheet.getRow(18).createCell(2).setCellFormula("B12+B13+B14+C18+B30+C34");
      summarySheet.getRow(18).createCell(3).setCellFormula("C19/D4");
      summarySheet.getRow(18).getCell(3).setCellStyle(cellStyle);
      summarySheet.getRow(18).createCell(4).setCellFormula("C19/(B4-B26-B27-B28+C4-C26)");
      summarySheet.getRow(18).getCell(4).setCellStyle(cellStyle);

      summarySheet.getRow(21).createCell(1).setCellValue(haLessThanAAByTwentyFourHrsCount);
      summarySheet.getRow(21).createCell(3).setCellFormula("B22/B4");
      summarySheet.getRow(21).getCell(3).setCellStyle(cellStyle);
      summarySheet.getRow(21).createCell(4).setCellFormula("(B22-B28)/(B3-B27-B28)");
      summarySheet.getRow(21).getCell(4).setCellStyle(cellStyle);

      summarySheet.getRow(22).createCell(1).setCellValue(hAGreaterThanAACount);
      summarySheet.getRow(22).createCell(3).setCellFormula("(B22+B23)/B4");
      summarySheet.getRow(22).getCell(3).setCellStyle(cellStyle);
      summarySheet.getRow(22).createCell(4).setCellFormula("(B22+B23-B28)/(B3-B27-B28)");
      summarySheet.getRow(22).getCell(4).setCellStyle(cellStyle);

      summarySheet.getRow(24).createCell(1).setCellValue(ipsAAForSatsCount);
      summarySheet.getRow(24).createCell(2).setCellValue(ipsAAForDNATACount);
      summarySheet.getRow(24).createCell(3).setCellFormula("(B25+C25+B29+C29)/D3");
      summarySheet.getRow(24).getCell(3).setCellStyle(cellStyle);

      summarySheet.getRow(25).createCell(1).setCellValue(paLessThanAAHavingHAForSATSCount);
      summarySheet.getRow(25).createCell(2).setCellValue(paLessThanAAHavingHAForDNATACount);
      summarySheet.getRow(25).createCell(3).setCellFormula("SUM(B26+C26)");

      summarySheet.getRow(26).createCell(1).setCellValue(palessThanHAHavingNoHACount);

   }

}
