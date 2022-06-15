/**
 * {@link ImportExportWorkingListReport}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.report.model.ExcelReportRequest;
import com.ngen.cosys.report.model.ExcelReportResponse;
import com.ngen.cosys.report.service.ExcelReportService;
import com.ngen.cosys.report.service.poi.constants.ReportImportExportWorkingListConstants;
import com.ngen.cosys.report.service.poi.constants.ReportPOIConstants;
import com.ngen.cosys.report.service.poi.model.CIQImportExportWorkingListReport;
import com.ngen.cosys.report.service.poi.util.POIBase;
import com.ngen.cosys.report.service.poi.util.POIElements;
import com.ngen.cosys.report.service.poi.util.POIElements.POIHeader;
import com.ngen.cosys.report.service.poi.util.POIUtils;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * ImportExportWorkingListReport is an implementation of Excel Report Service
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = ReportPOIConstants.IMPORT_EXPORT_WORKINGLIST_REPORT)
public class ImportExportWorkingListReport extends POIBase implements ExcelReportService {

   private static final Logger LOGGER = LoggerFactory.getLogger(ImportExportWorkingListReport.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   /**
    * @see com.ngen.cosys.report.service.ExcelReportService#generateReport(com.ngen.cosys.report.model.ExcelReportRequest,
    *      javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   @Override
   public ExcelReportResponse generateReport(ExcelReportRequest reportRequest, HttpServletRequest request,
         HttpServletResponse response) throws CustomException {
      ExcelReportResponse reportResponse = new ExcelReportResponse();
      //
      reportResponse.setReportName(reportRequest.getReportName());
      String reportData = (String) build(getReportData(reportRequest));
      reportResponse.setReportData(reportData);
      //
      return reportResponse;
   }
   
   /**
    * @see com.ngen.cosys.report.service.poi.util.POIBase#initialize(com.ngen.cosys.service.util.enums.ReportFormat)
    */
   @Override
   protected void initialize(ReportFormat reportFormat) throws CustomException {
      // Reinitialize
      super.initializeProperties();
      this.workbook = POIUtils.createWorkbook(reportFormat);
   }

   /**
    * @see com.ngen.cosys.report.service.poi.util.POIBase#createHeader(com.ngen.cosys.report.service.poi.util.POIElements)
    */
   @Override
   protected void createHeader(POIElements elements) throws CustomException {
      this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
      if (Objects.nonNull(elements.getHeader()) && !CollectionUtils.isEmpty(elements.getHeader().getHeaders())) {
         for (String column : elements.getHeader().getHeaders()) {
            POIUtils.createCell(row, null, false, null, true, columnIndex++, false, null, column);
         }
      }
   }
   
   /**
    * @see com.ngen.cosys.report.service.poi.util.POIBase#build(com.ngen.cosys.report.service.poi.util.POIElements)
    */
   @Override
   public Object build(POIElements elements) throws CustomException {
      // Create workbook
      initialize(elements.getReportFormat());
      // Sheet name
      this.spreadSheet = POIUtils.createSpreadSheet(workbook, elements, sheetIndex);
      // Header
      createHeader(elements);
      // Content Implementation
      if (Objects.nonNull(elements.getDatasets())) {
         populateReportData(elements, elements.getDatasets());
      }
      // Close
      return close(elements);
   }

   /**
    * @see com.ngen.cosys.report.service.poi.util.POIBase#close(com.ngen.cosys.report.service.poi.util.POIElements)
    */
   @Override
   protected Object close(POIElements elements) throws CustomException {
      String reportData = null;
      try {
         OutputStream outputStream = POIUtils.createFile(elements, workbook);
         reportData = POIUtils.convertOutputStreamToBase64Text(outputStream);
      } catch (IllegalStateException | IOException ex) {
         LOGGER.debug("Exception occurred while creating file");
      }
      return reportData;
   }
   
   /**
    * Populate display data
    * 
    * @param elements
    * @param dataset
    */
   @SuppressWarnings("unchecked")
   private void populateReportData(POIElements elements, Object dataset) {
      List<CIQImportExportWorkingListReport> reportData = (List<CIQImportExportWorkingListReport>) dataset;
      int headerSize = ReportImportExportWorkingListConstants.HEADER_DETAILS.length;
      LOGGER.debug("Import Export Reference Working List Report Header Size - {}", headerSize);
      //
      String formula = null;
      // Report supported customization
      font = POIUtils.createFont(workbook, false, null, null, true, false, (short) 11);
      cellStyle = POIUtils.createCellStyle(workbook, font, null, true);
      //
      for (CIQImportExportWorkingListReport report : reportData) {
         // Row Creation and Column default Index
         this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
         columnIndex = 0;
         // 1st segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null,
               report.getC2kIdentifier());
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getGha());
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getMonth());
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getStation());
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getAirline());
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getExportAWBMWBs(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportShipmentsManifested(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getFwbReceived(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getFwbReceivedBeforeRCS(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getRcsSent(), null);
         // 1st segment formula
//         if (Objects.nonNull(report.getExportAWBMWBs()) ) { //&& Objects.nonNull(report.getRcsSent())
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "F", "J", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "J", "F", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//         }
         // 2nd segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getFfmSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getFfmSentInTime(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getDepSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getDepSentInTime(),
               null);
         // 2nd segment formula
//         if (Objects.nonNull(report.getExportShipmentsManifested()) ) { // && Objects.nonNull(report.getDepSent()) && Objects.nonNull(report.getDepSentInTime())
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "G", "O", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "O", "P", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "P", "G", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//         }
         // 3rd segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getImportAWBMWBs(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportShipmentsManifested(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getFwbReceivedBeforeATA(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getFfmReceivedBeforeATA(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getRcfSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getRcfSentInTime(),
               null);
         // 3rd segment formula
//         if (Objects.nonNull(report.getImportShipmentsManifested()) ) { //&& Objects.nonNull(report.getRcfSent())&& Objects.nonNull(report.getRcfSentInTime())
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "U", "X", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "X", "Y", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "Y", "U", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//         }
         // 4th segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getAwdSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getAwdSentInTime(),
               null);
         // 4th segment formula
//         if (Objects.nonNull(report.getImportAWBMWBs()) ) { //&& Objects.nonNull(report.getAwdSent())  && Objects.nonNull(report.getAwdSentInTime())
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "T", "AC", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AC", "AD", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "AD", "T", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//         }
         // 5th segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getNfdMessagesSent(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getNfdSentInTime(),
               null);
         // 5th segment formula
//         if (Objects.nonNull(report.getImportShipmentsManifested()) ) { // && Objects.nonNull(report.getNfdMessagesSent()) && Objects.nonNull(report.getNfdSentInTime())
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "U", "AH", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AH", "AI", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "AI", "U", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//         }
         // 6th segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getDlvEvent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getDlvSentInTime(),
               null);
         // 6th segment formula
//         if (Objects.nonNull(report.getDlvEvent()) && Objects.nonNull(report.getDlvSentInTime())) {
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AM", "AN", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "AN", "AM", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//         }
      }
   }
   
   /**
    * @param reportRequest
    * @return
    */
   private POIElements getReportData(ExcelReportRequest reportRequest) {
      POIElements elements = new POIElements();
      //
      reportProperties(elements, reportRequest);
      reportHeaderDetails(elements);
      reportFormulaDetails(elements);
      reportDatasetDetails(reportRequest, elements);
      //
      return elements;
   }
   
   /**
    * Report Properties
    * 
    * @param elements
    */
   private void reportProperties(POIElements elements, ExcelReportRequest reportRequest) {
      // Report format
      elements.setReportFormat(ReportFormat.XLSX);
      elements.setFileName(ReportImportExportWorkingListConstants.IMPORT_EXPORT_WORKINGLIST_FILE_NAME);
      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(LocalDateTime.parse(reportRequest.getParameters().get("fromDate").toString()),
            reportRequest.getTenantId());
      String sheetName = String.format(ReportImportExportWorkingListConstants.IMPORT_EXPORT_WORKINGLIST_SHEET_NAME,
            currentZoneTime.format(DateTimeFormatter.ofPattern(ReportImportExportWorkingListConstants.DATE_FORMAT)));
      elements.setSheetName(sheetName);
      elements.setZoomScale(ReportPOIConstants.DEFAULT_ZOOM_SCALE);
      // Freeze Panes
      elements.setFreezeRowSplit(ReportPOIConstants.DEFAULT_ROW_FREEZE_PANE);
      elements.setFreezeColumnSplit(ReportPOIConstants.WORKINGLIST_COLUMN_FREEZE_PANE);
   }
   
   /**
    * Report Header Information 
    * 
    * @param elements
    */
   private void reportHeaderDetails(POIElements elements) {
      POIHeader header = elements.getHeader();
      if (Objects.isNull(header)) {
         header = elements.new POIHeader();
      }
      header.setHeaders(Arrays.asList(ReportImportExportWorkingListConstants.HEADER_DETAILS));
      header.setFontHeight((short) 11);
      header.setBold(true);
      header.setWrapText(true);
      elements.setHeader(header);
   }
   
   /**
    * Report Formula details to apply
    * 
    * @param elements
    */
   private void reportFormulaDetails(POIElements elements) {
      Map<String, String> formula = new HashMap<>();
      formula.put(ReportPOIConstants.SUBTRACT, "A$-B$");
      formula.put(ReportPOIConstants.PERCENT, "ROUND(IF(OR(A$<>0,B$<>0),(A$/B$)*100,0),2)");
      elements.setFormulas(formula);
   }
   
   /**
    * @param formula
    * @param s1
    * @param s2
    * @param s3
    * @param subTotal
    * @return
    */
   public String manipulateFormula(String formula, String s1, String s2, String s3, boolean subTotal) {
      if (StringUtils.isEmpty(formula)) {
         return null;
      }
      if (subTotal) {
         formula = formula.replace("C", s1);
         formula = formula.replace("$", s2);
         formula = formula.replace("#", s3);
      } else {
         formula = formula.replace("A", s1);
         formula = formula.replace("B", s2);
         formula = formula.replace("$", s3);
      }
      return formula;
   }
   
   /**
    * Report Data extraction
    * 
    * @param reportRequest
    */
   private void reportDatasetDetails(ExcelReportRequest reportRequest, POIElements elements) {
      List<CIQImportExportWorkingListReport> reportData = sqlSession.selectList(
            ReportImportExportWorkingListConstants.SQL_SELECT_IMPORT_EXPORT_WORKINGLIST_DATA, reportRequest);
      elements.setDatasets(reportData);
   }
   
}
