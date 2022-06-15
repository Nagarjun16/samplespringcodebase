/**
 * {@link TransferTransitReferenceReport}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.ngen.cosys.report.model.ExcelReportRequest;
import com.ngen.cosys.report.model.ExcelReportResponse;
import com.ngen.cosys.report.service.ExcelReportService;
import com.ngen.cosys.report.service.poi.constants.ReportPOIConstants;
import com.ngen.cosys.report.service.poi.constants.ReportTransferTransitReferenceConstants;
import com.ngen.cosys.report.service.poi.model.CIQTransferTransitReport;
import com.ngen.cosys.report.service.poi.util.POIBase;
import com.ngen.cosys.report.service.poi.util.POIElements;
import com.ngen.cosys.report.service.poi.util.POIElements.POIHeader;
import com.ngen.cosys.report.service.poi.util.POIUtils;
import com.ngen.cosys.service.util.enums.ReportFormat;

/**
 * TransferTransitReferenceReport is an implementation of Excel Report Service
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = ReportPOIConstants.TRANSFER_TRANSIT_REFERENCE_REPORT)
public class TransferTransitReferenceReport extends POIBase implements ExcelReportService {

   private static final Logger LOGGER = LoggerFactory.getLogger(TransferTransitReferenceReport.class);
   
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
      List<CIQTransferTransitReport> reportData = (List<CIQTransferTransitReport>) dataset;
      Map<String, Long> groupByIndex = groupByCounts(reportData);
      int headerSize = ReportTransferTransitReferenceConstants.HEADER_DETAILS.length;
      LOGGER.debug("Transfer Transit Reference Working Report Header Size - {}", headerSize);
      //
      int groupIndex = 0;
      boolean applyFormulaInNextRow = false;
      String carrierGroup = null;
      String formula = null;
      // Report supported customization
      font = POIUtils.createFont(workbook, false, null, null, true, false, (short) 11);
      cellStyle = POIUtils.createCellStyle(workbook, font, null, true);
      //
      for (CIQTransferTransitReport report : reportData) {
         // Row Creation and Column default Index
         this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
         columnIndex = 0;
         // Group Index apply formula
         if (applyFormulaInNextRow) {
            // Apply the formula in Current Row wherever it's applicable (BLANK)
            for (int j = 0; j < headerSize; j++) {
               if (j == 0) {
                  String groupValue = ReportPOIConstants.GROUP_TOTAL.replace("{}", carrierGroup);
                  cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, groupValue);
               } else if (j == 1 || j == 2) { // Empty cell
                  cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, null);
               } else {
                  formula = elements.getFormulas().get(ReportPOIConstants.SUBTOTAL);
                  String columnName = ReportPOIConstants.EXCEL_COLUMN_NAMES[j];
                  int rangeFrom = rowIndex - groupIndex;
                  int rangeTo = rowIndex - 1;
                  formula = manipulateFormula(formula, columnName, String.valueOf(rangeFrom), String.valueOf(rangeTo),
                        true);
                  cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
               }
            }
            // Formula applied for the existing group, So set to false
            applyFormulaInNextRow = false;
            groupIndex = 1;
            // New row for Next Carrier Group
            this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
            columnIndex = 0;
         } else {
            if (groupByIndex.containsKey(report.getGroup())) {
               groupIndex += 1;
            }
         }
         // Check Group Index
         carrierGroup = report.getGroup();
         if (groupByIndex.containsKey(carrierGroup) && groupIndex == groupByIndex.get(carrierGroup)) {
            applyFormulaInNextRow = true;
         }
         // 1st segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getGroup());
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getAirline());
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferShipmentsManifested(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getExportRCTSent(),
               null);
         // 1st segment formula
         if (Objects.nonNull(report.getExportTransferShipmentsManifested())
               && Objects.nonNull(report.getExportRCTSent())) {
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "C", "D", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "C", "D", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
         }
         // 2nd segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferFFMSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferFFMSentInTime(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferDEPSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, 
               report.getExportTransferFFMSentInTime(), null);
         // 2nd segment formula
         if (Objects.nonNull(report.getExportTransferShipmentsManifested())
               && Objects.nonNull(report.getExportTransferDEPSent())) {
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "C", "I", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "I", "J", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "J", "C", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
         }
         // 3rd segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportTransferAWBMWBs(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportTransferShipmentsManifested(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportTransferFWBReceived(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, 
               report.getImportTransferFFMReceivedBeforeATA(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, 
               report.getImportTransferRCFSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportTransferRCFInTime(), null);
         // 3rd segment formula
         if (Objects.nonNull(report.getImportTransferShipmentsManifested())
               && Objects.nonNull(report.getImportTransferRCFSent())
               && Objects.nonNull(report.getImportTransferRCFInTime())) {
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "O", "R", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "R", "S", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "S", "O", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
         }
         // 4th segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getTfdEventCompleted(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getTfdMessageInTime(),
               null);
         // 4th segment formula
         if (Objects.nonNull(report.getTfdEventCompleted()) && Objects.nonNull(report.getTfdMessageInTime())) {
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "W", "X", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "W", "X", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
         }
         // 5th segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportTransitAWBMWBs(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportTransitShipments(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getFwbReceived(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getFfmReceivedBeforeATA(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getTransitRCFSent(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getTransitRCFSentInTime(), null);
         // 5th segment formula
         if (Objects.nonNull(report.getImportTransitShipments()) && Objects.nonNull(report.getTransitRCFSent())
               && Objects.nonNull(report.getTransitRCFSentInTime())) {
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AB", "AE", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AE", "AF", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "AF", "AB", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
         }
         // 6th segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getTransitFFMSent(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getTransitFFMSentInTime(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getTransitDEPSent(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getTransitDEPSentInTime(), null);
         // 6th segment formula
         if (Objects.nonNull(report.getImportTransitShipments()) && Objects.nonNull(report.getTransitDEPSent())
               && Objects.nonNull(report.getTransitDEPSentInTime())) {
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AB", "AL", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AL", "AM", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
            formula = manipulateFormula(formula, "AM", "AB", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
         }
      }
      // Last before Carrier Group Total
      if (applyFormulaInNextRow) {
         this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
         columnIndex = 0;
         // Apply the formula in Current Row wherever it's applicable (BLANK)
         for (int j = 0; j < headerSize; j++) {
            if (j == 0) {
               String groupValue = ReportPOIConstants.GROUP_TOTAL.replace("{}", carrierGroup);
               cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, groupValue);
            } else if (j == 1 || j == 2) { // Empty cell
               cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, null);
            } else {
               formula = elements.getFormulas().get(ReportPOIConstants.SUBTOTAL);
               String columnName = ReportPOIConstants.EXCEL_COLUMN_NAMES[j];
               int rangeFrom = rowIndex - groupIndex;
               int rangeTo = rowIndex - 1;
               formula = manipulateFormula(formula, columnName, String.valueOf(rangeFrom), String.valueOf(rangeTo),
                     true);
               cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            }
         }
      }
      // Row Creation and Column default Index
      this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
      columnIndex = 0;
      // Apply the formula in Current Row wherever it's applicable (BLANK)
      for (int j = 0; j < headerSize; j++) {
         if (j == 0) {
            String groupValue = ReportPOIConstants.GRAND_TOTAL;
            cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, groupValue);
         } else if (j == 1 || j == 2) { // Empty cell
            cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, null);
         } else {
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTOTAL);
            String columnName = ReportPOIConstants.EXCEL_COLUMN_NAMES[j];
            int rangeFrom = 2; // Data begins from row 2
            int rangeTo = rowIndex - 1;
            formula = manipulateFormula(formula, columnName, String.valueOf(rangeFrom), String.valueOf(rangeTo),
                  true);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
         }
      }
   }
   
   /**
    * @param reportData
    * @return
    */
   private Map<String, Long> groupByCounts(List<CIQTransferTransitReport> reportData) {
      return reportData.stream().filter(e -> !StringUtils.isEmpty(e.getGroup()))
            .collect(Collectors.groupingBy(CIQTransferTransitReport::getGroup, Collectors.counting()));
   }
   
   /**
    * @param reportRequest
    * @return
    */
   private POIElements getReportData(ExcelReportRequest reportRequest) {
      POIElements elements = new POIElements();
      //
      reportProperties(elements);
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
   private void reportProperties(POIElements elements) {
      // Report format
      elements.setReportFormat(ReportFormat.XLSX);
      elements.setFileName(ReportTransferTransitReferenceConstants.TRANSFER_TRANSIT_REFERENCE_FILE_NAME);
      elements.setSheetName(ReportTransferTransitReferenceConstants.TRANSFER_TRANSIT_REFERENCE_SHEET_NAME);
      elements.setZoomScale(ReportPOIConstants.DEFAULT_ZOOM_SCALE);
      // Freeze Panes
      elements.setFreezeRowSplit(ReportPOIConstants.DEFAULT_ROW_FREEZE_PANE);
      elements.setFreezeColumnSplit(ReportPOIConstants.TRANSFER_TRANSIT_COLUMN_FREEZE_PANE);
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
      header.setHeaders(Arrays.asList(ReportTransferTransitReferenceConstants.HEADER_DETAILS));
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
      formula.put(ReportPOIConstants.SUBTOTAL, "SUBTOTAL(9,C$:C#)");
      formula.put(ReportPOIConstants.SUBTRACT, "A$-B$");
      formula.put(ReportPOIConstants.PERCENT, "(A$-B$)*100");
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
      List<CIQTransferTransitReport> reportData = sqlSession.selectList(
            ReportTransferTransitReferenceConstants.SQL_SELECT_TRANSFER_TRANSIT_REFERENCE_DATA, reportRequest);
      elements.setDatasets(reportData);
   }
   
}
