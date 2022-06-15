/**
 * {@link TransferTransitWorkingListReport}
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
import com.ngen.cosys.report.model.ExcelReportRequest;
import com.ngen.cosys.report.model.ExcelReportResponse;
import com.ngen.cosys.report.service.ExcelReportService;
import com.ngen.cosys.report.service.poi.constants.ReportImportExportWorkingListConstants;
import com.ngen.cosys.report.service.poi.constants.ReportPOIConstants;
import com.ngen.cosys.report.service.poi.constants.ReportTransferTransitReferenceConstants;
import com.ngen.cosys.report.service.poi.constants.ReportTransferTransitWorkingListConstants;
import com.ngen.cosys.report.service.poi.model.CIQTransferTransitReport;
import com.ngen.cosys.report.service.poi.model.CIQTransferTransitWorkingListReport;
import com.ngen.cosys.report.service.poi.util.POIBase;
import com.ngen.cosys.report.service.poi.util.POIElements;
import com.ngen.cosys.report.service.poi.util.POIElements.POIHeader;
import com.ngen.cosys.report.service.poi.util.POIUtils;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * TransferTransitWorkingListReport is an implementation of Excel Report Service
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = ReportPOIConstants.TRANSFER_TRANSIT_WORKINGLIST_REPORT)
public class TransferTransitWorkingListReport extends POIBase implements ExcelReportService {

   private static final Logger LOGGER = LoggerFactory.getLogger(TransferTransitWorkingListReport.class);
   
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
      List<CIQTransferTransitWorkingListReport> reportData = (List<CIQTransferTransitWorkingListReport>) dataset;
      int headerSize = ReportTransferTransitReferenceConstants.HEADER_DETAILS.length;
      LOGGER.debug("Transfer Transit Working List Report Header Size - {}", headerSize);
      //
      String formula = null;
      // Report supported customization
      font = POIUtils.createFont(workbook, false, null, null, true, false, (short) 11);
      cellStyle = POIUtils.createCellStyle(workbook, font, null, true);
      //
      for (CIQTransferTransitWorkingListReport report : reportData) {
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
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferShipmentsManifested(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getRctSent(), null);
         // 1st segment formula
//         if (Objects.nonNull(report.getExportTransferShipmentsManifested()) ) { // && Objects.nonNull(report.getRctSent())
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "F", "G", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//            if(report.getExportTransferShipmentsManifested() == 0) {
//            	cell = POIUtils.createCell(row, cellStyle, false, null, false, columnIndex++, true, 0, null);
//            } else {
            	formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
                formula = manipulateFormula(formula, "G", "F", String.valueOf(rowIndex), false);
                cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//            }
//         }
         // 2nd segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferFFMSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferFFMSentInTime(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferDEPSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getExportTransferDEPSentInTime(), null);
         // 2nd segment formula
//         if (Objects.nonNull(report.getExportTransferShipmentsManifested())) { //&& Objects.nonNull(report.getExportTransferDEPSent()) && Objects.nonNull(report.getExportTransferDEPSentInTime())
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "F", "L", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "L", "M", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
//            if(report.getExportTransferShipmentsManifested() == 0) {
//            	cell = POIUtils.createCell(row, cellStyle, false, null, false, columnIndex++, true, 0, null);
//            } else {
            	formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
                formula = manipulateFormula(formula, "M", "F", String.valueOf(rowIndex), false);
                cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//            }
//         }
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
               report.getImportTransferRCFSentInTime(), null);
         // 3rd segment formula
//         if (Objects.nonNull(report.getImportTransferShipmentsManifested()) ) { //&& Objects.nonNull(report.getImportTransferRCFSent()) && Objects.nonNull(report.getImportTransferRCFSentInTime())
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "R", "U", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "U", "V", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
//            if(report.getImportTransferShipmentsManifested() == 0) {
//            	cell = POIUtils.createCell(row, cellStyle, false, null, false, columnIndex++, true, 0, null);
//            } else {
            	formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
                formula = manipulateFormula(formula, "V", "R", String.valueOf(rowIndex), false);
                cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//            }
//         }
         // 4th segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, report.getTfdEventCompleted(),
               null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getTfdMessageSentInTime(), null);
         // 4th segment formula
//         if (Objects.nonNull(report.getTfdEventCompleted()) ) { //&& Objects.nonNull(report.getTfdMessageSentInTime())
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "Z", "AA", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            // TFD%
//            if(report.getTfdEventCompleted() == 0) {
//            	cell = POIUtils.createCell(row, cellStyle, false, null, false, columnIndex++, true, 0, null);
//            } else {
            	formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
                formula = manipulateFormula(formula, "AA", "Z", String.valueOf(rowIndex), false);
                cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//            }
//         }
         // 5th segment
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportTransitAWBMWBs(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getImportTransitShipmentMovements(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getFwbReceived(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getFfmReceivedBeforeATA(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getTransitRCFSent(), null);
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true,
               report.getTransitRCFSentInTime(), null);
         // 5th segment formula
//         if (Objects.nonNull(report.getImportTransitShipmentMovements()) ) { //&& Objects.nonNull(report.getTransitRCFSent()) && Objects.nonNull(report.getTransitRCFSentInTime())
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AE", "AH", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AH", "AI", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
//            if(report.getImportTransitShipmentMovements() == 0) {
//            	cell = POIUtils.createCell(row, cellStyle, false, null, false, columnIndex++, true, 0, null);
//            } else {
            	formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
                formula = manipulateFormula(formula, "AI", "AE", String.valueOf(rowIndex), false);
                cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//            }
//         }
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
//         if (Objects.nonNull(report.getImportTransitShipmentMovements()) && Objects.nonNull(report.getTransitDEPSent())
//               && Objects.nonNull(report.getTransitDEPSentInTime())) {
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AE", "AO", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
            formula = elements.getFormulas().get(ReportPOIConstants.SUBTRACT);
            formula = manipulateFormula(formula, "AO", "AP", String.valueOf(rowIndex), false);
            cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
            //
//            if(report.getImportTransitShipmentMovements() == 0) {
//            	cell = POIUtils.createCell(row, cellStyle, false, null, false, columnIndex++, true, 0, null);
//            } else {
            	formula = elements.getFormulas().get(ReportPOIConstants.PERCENT);
                formula = manipulateFormula(formula, "AP", "AE", String.valueOf(rowIndex), false);
                cell = POIUtils.createCell(row, cellStyle, true, formula, false, columnIndex++, false, null, null);
//            }
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
      elements.setFileName(ReportTransferTransitWorkingListConstants.TRANSFER_TRANSIT_WORKINGLIST_FILE_NAME);
      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(LocalDateTime.parse(reportRequest.getParameters().get("fromDate").toString()),
              reportRequest.getTenantId());
      String sheetName = String.format(ReportTransferTransitWorkingListConstants.TRANSFER_TRANSIT_WORKINGLIST_SHEET_NAME,
              currentZoneTime.format(DateTimeFormatter.ofPattern(ReportTransferTransitWorkingListConstants.DATE_FORMAT)));
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
      header.setHeaders(Arrays.asList(ReportTransferTransitWorkingListConstants.HEADER_DETAILS));
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
      formula.put(ReportPOIConstants.PERCENT, "ROUND(IF(OR(A$<>0,B$<>0),(A$/B$)*100,0),2)");
//      formula.put(ReportPOIConstants.PERCENT, "(A$/B$)*100");
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
            ReportTransferTransitWorkingListConstants.SQL_SELECT_TRANSFER_TRANSIT_WORKINGLIST_DATA, reportRequest);
      elements.setDatasets(reportData);
   }
   
}
