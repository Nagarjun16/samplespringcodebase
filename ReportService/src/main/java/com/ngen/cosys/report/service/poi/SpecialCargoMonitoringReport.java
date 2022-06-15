/**
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.model.ExcelReportRequest;
import com.ngen.cosys.report.model.ExcelReportResponse;
import com.ngen.cosys.report.service.ExcelReportService;
import com.ngen.cosys.report.service.poi.constants.ReportPOIConstants;
import com.ngen.cosys.report.service.poi.constants.SpecialCargoMonitoringConstants;
import com.ngen.cosys.report.service.poi.model.SpecialCargoHandover;
import com.ngen.cosys.report.service.poi.model.SpecialCargoInventory;
import com.ngen.cosys.report.service.poi.model.SpecialCargoSearch;
import com.ngen.cosys.report.service.poi.model.SpecialCargoShipment;
import com.ngen.cosys.report.service.poi.util.POIBase;
import com.ngen.cosys.report.service.poi.util.POIElements;
import com.ngen.cosys.report.service.poi.util.POIElements.POIHeader;
import com.ngen.cosys.report.service.poi.util.POIUtils;
import com.ngen.cosys.service.util.enums.ReportFormat;

/**
 * SpecialCargoMonitoringReport is an implementation of Excel Report Service
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = ReportPOIConstants.SPECIAL_CARGO_MONITORING_REPORT)
public class SpecialCargoMonitoringReport extends POIBase implements ExcelReportService {

   private static final Logger LOGGER = LoggerFactory.getLogger(SpecialCargoMonitoringReport.class);
   
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
      reportResponse.setReportName(reportRequest.getReportName());
      String reportData = (String) build(getReportData(reportRequest));
      reportResponse.setReportData(reportData);
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
	  LOGGER.warn("Create Headers fetchMonitoringListForReport ");
	  CellStyle style = workbook.createCellStyle();
	  style.setAlignment(HorizontalAlignment.CENTER);
//	    style.setVerticalAlignment(VerticalAlignment.CENTER);
	   
	  this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
	  if (Objects.nonNull(elements.getTopHeader()) && !CollectionUtils.isEmpty(elements.getTopHeader().getHeaders())) {
         for (String column : elements.getTopHeader().getHeaders()) {
            POIUtils.createCell(row, style, false, null, true, columnIndex++, false, null, column);
         }
      }
	  columnIndex = 0;
	  this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
      if (Objects.nonNull(elements.getHeader()) && !CollectionUtils.isEmpty(elements.getHeader().getHeaders())) {
         for (String column : elements.getHeader().getHeaders()) {
            POIUtils.createCell(row, style, false, null, true, columnIndex++, false, null, column);
         }
      }
      
      // Make All Headers Center & Merge
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,2,2));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,3,3));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,4,4));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,5,5));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,6,6));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,7,7));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,8,8));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,1,9,9));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,0,10,11));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,0,12,13));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,0,14,15));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,0,17,22));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,0,23,33));
      spreadSheet.addMergedRegion(new CellRangeAddress(0,0,34,37));
      
      LOGGER.warn("Headers Created for fetchMonitoringListForReport");
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
         LOGGER.warn("Exception occurred while creating file "+ex);
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

	  int slNo = 0;
      int staticColCount = 0;
      int shipColCount = 0;
      int invColCount = 0;
      int handColCount = 0;
      int imgColCount = 0;
      
      String location = "";
      String shipmentNumber = "";
      
      DateTimeFormatter dateTime = MultiTenantUtility.getTenantDateTimeFormat();
      DateTimeFormatter date = MultiTenantUtility.getTenantDateFormat();
      DateTimeFormatter time = DateTimeFormatter.ofPattern(SpecialCargoMonitoringConstants.TIME);
      
      List<SpecialCargoSearch> reportData = (List<SpecialCargoSearch>) dataset;

      // Report supported customization
      font = POIUtils.createFont(workbook, false, null, null, true, false, (short) 11);
      cellStyle = POIUtils.createCellStyle(workbook, font, null, true);
      
      CellStyle cs = workbook.createCellStyle();
      cs.setWrapText(true);
      
      LOGGER.warn("Create rows and columns for fetchMonitoringListForReport");
      
      for (SpecialCargoSearch report : reportData) {
    	 // Mismatch Section
		 this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
         columnIndex = 0; 
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, true, ++slNo, null); // For Integer
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getFlightKey()); // For String
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getFlightDate().format(date));
		 cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getAtdEtdStdMonitering().format(time));
         cell = POIUtils.createCell(row, null, false, null, false, columnIndex++, false, null, report.getFlightOffPoint());
         
         for (SpecialCargoShipment shipment : report.getSpecialCargoMoniteringShipmentList()) {
        	 shipColCount = 0;
        	 this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
        	 if(shipment.isPartSuffixToShow()) {
        		 shipmentNumber = shipment.getShipmentNumber() +  "(" + shipment.getPartSuffix() + ")";
        	 }
        	 else if(!shipment.isPartSuffixToShow()) {
        		 shipmentNumber = shipment.getShipmentNumber();
        	 }
        	 cell = POIUtils.createCell(row, null, false, null, false, (columnIndex + shipColCount++), false, null, shipmentNumber);
        	 cell = POIUtils.createCell(row, null, false, null, false, (columnIndex + shipColCount++), false, null, shipment.getSegment());
        	 cell = POIUtils.createCell(row, null, false, null, false, (columnIndex + shipColCount++), true, shipment.getFlightBookedPieces().intValue(), null);
        	 cell = POIUtils.createCell(row, null, false, null, false, (columnIndex + shipColCount++), false, null, shipment.getFlightBookedWeight().toString());
        	 cell = POIUtils.createCell(row, null, false, null, false, (columnIndex + shipColCount++), false, null, shipment.getAwbSHC());
        	 cell = POIUtils.createCell(row, cs, false, null, false, (columnIndex + shipColCount++), false, null, replaceBrTag(shipment.getMissingDLSUldListForDisplay()));
        	 cell = POIUtils.createCell(row, cs, false, null, false, (columnIndex + shipColCount++), false, null, replaceBrTag(shipment.getMissingDLSUldShcListForDisplay()));
        	 cell = POIUtils.createCell(row, cs, false, null, false, (columnIndex + shipColCount++), false, null, replaceBrTag(shipment.getMissingPrintTagUldListForDisplay()));
        	 cell = POIUtils.createCell(row, cs, false, null, false, (columnIndex + shipColCount++), false, null, replaceBrTag(shipment.getMissingPrintTagUldShcListForDisplay()));
        	 cell = POIUtils.createCell(row, cs, false, null, false, (columnIndex + shipColCount++), false, null, replaceBrTag(shipment.getMissingLoadedUldListForDisplay()));
        	 cell = POIUtils.createCell(row, cs, false, null, false, (columnIndex + shipColCount++), false, null, replaceBrTag(shipment.getMissingLoadedUldShcListForDisplay()));
        	 cell = POIUtils.createCell(row, cs, false, null, false, (columnIndex + shipColCount++), false, null, replaceBrTag(shipment.getMissingNotocUldListForDisplay()));
        	 
        	 // Request Section
        	 if(!ObjectUtils.isEmpty(shipment.getInventoryList())) {
        		 for (SpecialCargoInventory inventory : shipment.getInventoryList()) {
            		 invColCount = 0;
            		 staticColCount = columnIndex + shipColCount;
            		 this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
            		 if(StringUtils.isEmpty(inventory.getShipmentInventoryWarehoueLocation())) {
            			 location = inventory.isHandoverFlag() ? "*" + inventory.getShipmentInventoryShipmentLocation(): inventory.getShipmentInventoryShipmentLocation();
            		 } else {
            			 location = inventory.isHandoverFlag() ? "*" + inventory.getShipmentInventoryWarehoueLocation(): inventory.getShipmentInventoryWarehoueLocation();
            		 }
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + invColCount++), 
            			 false, null, location);
                	 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + invColCount++), 
                		 true, inventory.getShipmentInventoryPieces().intValue(), null); 
                	 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + invColCount++), 
                    	 true, inventory.getRequestPieces().intValue(), null); 
                	 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + invColCount++), 
                		 false, null, inventory.getRequestBy());
                	 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + invColCount++), 
                		 false, null, inventory.getHandOverLocForReq());
                	 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + invColCount++), 
                		 false, null, inventory.getRequestDateTime() != null ? inventory.getRequestDateTime().format(dateTime) : null);
    			 }
        	 } else {
        		 invColCount += 6;
        	 }
        	 
        	 // Header & Confirm Section
        	 if(!ObjectUtils.isEmpty(shipment.getSpecialCargoMoniteringHandoverSection())) {
        		 for (SpecialCargoHandover handover : shipment.getSpecialCargoMoniteringHandoverSection()) {
            		 handColCount = 0;
            		 staticColCount = columnIndex + shipColCount + invColCount;
            		 this.row = POIUtils.createRow(spreadSheet, elements, rowIndex++);
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getFromLoc());
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                    		 true, handover.getReqPc() != null ? handover.getReqPc().intValue() : null, null);
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getReqBy());
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getReqTime() != null ? handover.getReqTime().format(date) : null);
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getHandoverLocation());
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                    		 true, handover.getHandoverPieces().intValue(), null);
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getHandOverShcForDisplay());
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getHandoverBy());
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getHandoverToLoginId());
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getHandoverToStaffId());
            		 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + handColCount++), 
                			 false, null, handover.getHandoverDateTime() != null ? handover.getHandoverDateTime().format(dateTime) : null);
            		 
            		 // Image Section
            		 if(!ObjectUtils.isEmpty(handover.getImageSection())) {
            			 for (SpecialCargoHandover image : handover.getImageSection()) {
            				 imgColCount = 0;
            				 staticColCount = columnIndex + shipColCount + invColCount + handColCount;
            				 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + imgColCount++), 
                        			 false, null, handover.getHandoverLocation());
            				 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + imgColCount++), 
                        			 false, null, image.getDateForFileUpload() != null ? image.getDateForFileUpload().format(dateTime) : null);
            				 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + imgColCount++), 
                        			 false, null, image.getUserNameForFileUpload());
            				 cell = POIUtils.createCell(row, null, false, null, false, (staticColCount + imgColCount++), 
                        			 false, null, image.getDocumentName());
            			 }
					}
    			 }
        	 }
		 }
      }
      
      LOGGER.warn("Rows and columns created for fetchMonitoringListForReport");
      
      // Set all columns to auto size
      for (int i = 0; i < elements.getHeader().getHeaders().size(); i++) {
    	  spreadSheet.autoSizeColumn(i, true);
      }
      
      LOGGER.warn("Autosize headers for fetchMonitoringListForReport");
   }
   
   /**
    * @param reportRequest
    * @return
    */
   private POIElements getReportData(ExcelReportRequest reportRequest) {
      POIElements elements = new POIElements();
      reportProperties(elements, reportRequest);
      reportHeaderDetails(elements);
      reportDatasetDetails(reportRequest, elements);
      return elements;
   }
   
   /**
    * Report Properties
    * 
    * @param elements
    */
   private void reportProperties(POIElements elements, ExcelReportRequest reportRequest) {
      elements.setReportFormat(ReportFormat.XLSX);
//      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(LocalDateTime.parse(reportRequest.getParameters().get("fromDate").toString()),
//            TenantZone.SIN.getAirportCode());
      String sheetName = SpecialCargoMonitoringConstants.SPECIAL_CARGO_MONITORNING_SHEET_NAME;
//      String sheetName = String.format(SpecialCargoMonitoringConstants.SPECIAL_CARGO_MONITORNING_SHEET_NAME,
//              currentZoneTime.format(DateTimeFormatter.ofPattern(SpecialCargoMonitoringConstants.DATE_FORMAT)));
      elements.setSheetName(sheetName);
      elements.setZoomScale(ReportPOIConstants.DEFAULT_ZOOM_SCALE);
      // Freeze Panes
      elements.setFreezeRowSplit(ReportPOIConstants.TOP_HEADERS_FREEZE_PANE);
//      elements.setFreezeColumnSplit(ReportPOIConstants.WORKINGLIST_COLUMN_FREEZE_PANE);
   }
   
   /**
    * Report Header Information 
    * 
    * @param elements
    */
   private void reportHeaderDetails(POIElements elements) {
	  POIHeader topHeader = elements.getTopHeader();
      if (Objects.isNull(topHeader)) {
    	  topHeader = elements.new POIHeader();
      }
      topHeader.setHeaders(Arrays.asList(SpecialCargoMonitoringConstants.TOP_HEADER));
      topHeader.setFontHeight((short) 11);
      topHeader.setBold(true);
      topHeader.setWrapText(true);
      elements.setTopHeader(topHeader); 
	   
      POIHeader subHeader = elements.getHeader();
      if (Objects.isNull(subHeader)) {
         subHeader = elements.new POIHeader();
      }
      subHeader.setHeaders(Arrays.asList(SpecialCargoMonitoringConstants.SUB_HEADER));
      subHeader.setFontHeight((short) 11);
      subHeader.setBold(true);
      subHeader.setWrapText(true);
      elements.setHeader(subHeader);
   }
   
   /**
    * Report Data extraction
    * 
    * @param reportRequest
    */
	private void reportDatasetDetails(ExcelReportRequest reportRequest, POIElements elements) {
		LOGGER.warn("Set parameters for fetchMonitoringListForReport " + reportRequest.getParameters());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SpecialCargoMonitoringConstants.T_DATETIME);
		
		SpecialCargoSearch request = new SpecialCargoSearch();
		request.setShcGroup(reportRequest.getParameters().get("shcGroup") == null ? null : reportRequest.getParameters().get("shcGroup").toString());
		request.setByRequestHandOver(reportRequest.getParameters().get("byRequestHandOver") == null ? null : reportRequest.getParameters().get("byRequestHandOver").toString());
		request.setFromDate(reportRequest.getParameters().get("fromDate") == null ? null : LocalDateTime.parse(reportRequest.getParameters().get("fromDate").toString(), formatter));
		request.setToDate(reportRequest.getParameters().get("toDate") == null ? null : LocalDateTime.parse(reportRequest.getParameters().get("toDate").toString(), formatter));
		request.setCarrierGroup(reportRequest.getParameters().get("carrierGroup") == null ? null : reportRequest.getParameters().get("carrierGroup").toString());
		request.setTerminal(reportRequest.getParameters().get("requestTerminal") == null ? null : reportRequest.getParameters().get("requestTerminal").toString());
		request.setFlightKey(reportRequest.getParameters().get("flightKey") == null ? null : reportRequest.getParameters().get("flightKey").toString());
		request.setFlightDate(reportRequest.getParameters().get("flightDate") == null ? null : LocalDate.parse(reportRequest.getParameters().get("flightDate").toString(), formatter));
		request.setShipmentNumber(reportRequest.getParameters().get("shipmentNumber") == null ? null : reportRequest.getParameters().get("shipmentNumber").toString());
		request.setFromRequest((boolean) reportRequest.getParameters().get("fromRequest"));
		request.setDlsMismatchYesNo(reportRequest.getParameters().get("dlsMismatchYesNo") == null ? null : reportRequest.getParameters().get("dlsMismatchYesNo").toString());
		request.setNotocMismatchYesNo(reportRequest.getParameters().get("notocMismatchYesNo") == null ? null : reportRequest.getParameters().get("notocMismatchYesNo").toString());
		
		LOGGER.warn("Parameters assigned for fetchMonitoringListForReport ");
		
		List<SpecialCargoSearch> reportData = fetchMonitoringListForReport(request);
		elements.setDatasets(reportData);
		
		LOGGER.warn("RestTemplate - Response size " + reportData.size());
	}
   
   public List<SpecialCargoSearch> fetchMonitoringListForReport(SpecialCargoSearch request) {
	    String connectingUrl = sqlSession.selectOne("getConnectingUrl");
//	    String connectingUrl = "http://localhost:9160";
		connectingUrl += "/expbu/api/export/buildup/special-cargo/fetchMonitoringListForReport";

		LOGGER.warn("Inside RestTemplate function for fetchMonitoringListForReport " + connectingUrl);
		
		// Set your headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Set your entity to send
		HttpEntity entity = new HttpEntity(request, headers);
		
		RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
		ResponseEntity<List<SpecialCargoSearch>> response = restTemplate.exchange(connectingUrl, HttpMethod.POST, 
				entity, new ParameterizedTypeReference<List<SpecialCargoSearch>>() {});
		
		LOGGER.warn("RestTemplate - Response from fetchMonitoringListForReport " + response.getBody());
		return response.getBody();
	}	
   
   public String replaceBrTag(String value) {
	   if(!StringUtils.isEmpty(value)) {
		   return value.replace("<br>", "\n");
	   }
	   return null;
   }
   
}
