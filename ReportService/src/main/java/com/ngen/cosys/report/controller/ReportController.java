/**
 * Report Controller
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.report.controller;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.esb.connector.payload.PrinterPayload;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.printer.enums.PrinterType;
import com.ngen.cosys.printer.processor.PrinterProcessor;
import com.ngen.cosys.printer.service.PrinterService;
import com.ngen.cosys.report.common.ReportFormat;
import com.ngen.cosys.report.common.ReportRequestType;
import com.ngen.cosys.report.model.ExcelReportRequest;
import com.ngen.cosys.report.model.ExcelReportResponse;
import com.ngen.cosys.report.model.ReportRequest;
import com.ngen.cosys.report.model.ReportResponse;
import com.ngen.cosys.report.service.ExcelReportService;
import com.ngen.cosys.report.service.PerformanceReportService;
import com.ngen.cosys.report.service.ReportService;
import com.ngen.cosys.report.service.poi.model.PerformanceReportModel;

/**
 * Report Controller
 */
@NgenCosysAppInfraAnnotation
public class ReportController {
	private static Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
	//
	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;
	@Autowired
	private ReportService reportService;

	@Autowired
	private PrinterService printerService;

	@Autowired
	PrinterProcessor printerProcessor;

	@Autowired
	BeanFactory beanFactory;

	ExcelReportService excelReportService;

	@Autowired
	private PerformanceReportService perofrmanceReportService;

	/**
	 * Generate Report
	 * 
	 * @param reportrRequest Report Request
	 * @param request        HTTP Request
	 * @param response       HTTP Response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, value = "/report", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<ReportResponse> generateReport(@RequestBody @Valid ReportRequest reportRequest,
			HttpServletRequest request, HttpServletResponse response) {
		BaseResponse<ReportResponse> callResponse = utilitiesModelConfiguration.getBaseResponseInstance();
		//
		boolean poiExcelReport = false;
		try {
			LOGGER.info(String.format("Request to Generate Report %s is Received\n", reportRequest.getReportName()));
			// Generate Report
			updateReportParametersWithDefault(reportRequest);
			// Update the Report Name Based on Feature Config
			reportRequest = updateReportNameBasedOnTenant(reportRequest);
			// Create Report
			OutputStream outputStream = null;
			ExcelReportResponse excelReportResponse = null;
			if (Objects.nonNull(reportRequest.getFormat())
					&& Objects.equals(ReportFormat.XLSX.format(), reportRequest.getFormat().format())) {
				poiExcelReport = true;
				ExcelReportRequest excelReportRequest = getExcelReportRequest(reportRequest);
				excelReportService = (ExcelReportService) beanFactory.getBean(excelReportRequest.getReportName());
				excelReportResponse = excelReportService.generateReport(excelReportRequest, request, response);
			} else {
				if ("EHUB_PERFORMANCE_RPT".equalsIgnoreCase(reportRequest.getReportName())) {
					PerformanceReportModel requestModel = new PerformanceReportModel();
					Map<String, Object> parameters = (Map<String, Object>) reportRequest.getParameters();
					String date1 = (String) parameters.get("fromDate");
					LocalDate fromDate = LocalDate.parse(date1, MultiTenantUtility.getTenantDateFormat());
					requestModel.setFromDate(fromDate);

					outputStream = perofrmanceReportService.getPerformancereport(requestModel);
				} else {
					// TenantContext.get().setLocale(reportRequest.getLocale());
					outputStream = reportService.createReport(reportRequest, request, response);
				}

			}
			// Call Report Service to Print report based on Report request type and Printer
			// type
			if (ReportRequestType.PRINT == reportRequest.getRequestType()) {
				// Print
				printReport(outputStream, reportRequest);
				// Update Status
				callResponse.setSuccess(true);
				LOGGER.info(String.format("Print of Report %s is Completed\n", reportRequest.getReportName()));
			} else {
				// Create Response
				String base64Content = null;
				if (poiExcelReport && Objects.nonNull(excelReportResponse)) {
					base64Content = excelReportResponse.getReportData();
				} else {
					base64Content = reportService.convertOutputStreamToBase64Text(outputStream);
				}
				ReportResponse reportResponse = new ReportResponse();
				// Set Response Data
				reportResponse.setReportName(reportRequest.getReportName());
				reportResponse.setReportData(base64Content);
				// Update Response Binary Data
				callResponse.setData(reportResponse);
				LOGGER.info(String.format("Generation of Report %s is Completed\n", reportRequest.getReportName()));
			}
		} catch (Exception e) {
			callResponse.setSuccess(false);
			LOGGER.error(String.format("Generation of Report %s Failed\n", reportRequest.getReportName()));
			LOGGER.error("Exception in report {}", e);
			if (poiExcelReport) {
				LOGGER.error("Excel Report Generation exception - {}", String.valueOf(e));
				e.printStackTrace();
			}
		}
		//
		return callResponse;
	}

	/**
	 * Update Report Parameters With Default Values
	 * 
	 * @param reportRequest Report Request
	 */
	private void updateReportParametersWithDefault(ReportRequest reportRequest) {
		Map<String, Object> reportParameters = reportRequest.getParameters();
		//
		reportParameters.put("tenantAirport", reportRequest.getTenantAirport());
		reportParameters.put("terminal", reportRequest.getTerminal());
	}

	private ReportRequest updateReportNameBasedOnTenant(ReportRequest reportRequest) {

		TenantContext.get().getFeatureMap().entrySet().forEach(entry -> {
			if (Objects.nonNull(entry.getValue()) && entry.getValue().isEnabled()
					&& !CollectionUtils.isEmpty(entry.getValue().getConfig())) {
				entry.getValue().getConfig().forEach(config -> {
					if (Objects.nonNull(config)
							&& reportRequest.getReportName().equalsIgnoreCase(config.getConfigId())) {
						if (Objects.nonNull(config.getConfigValue())) {
							reportRequest.setReportName(config.getConfigValue());
						}
					}
				});
			}
		});
		return reportRequest;
	}

	/**
	 * @param reportRequest
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/report/printer-service", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> printerService(@RequestBody @Valid ReportRequest reportRequest,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrinterPayload payload = null;
		if (reportRequest.getPrinterType().equals(PrinterType.XPS_AWB_BARCODE)) {
			ReportRequest reportRequestRFIDAWB = null;
			Map<String, Object> data = reportRequest.getParameters();
			if (data.containsKey("rfidAWBList")) {
				List<Map<String, Object>> rfidAWBList = (List<Map<String, Object>>) data.get("rfidAWBList");
				if (null != rfidAWBList && rfidAWBList.size() > 0) {
					for (Map<String, Object> rfidAWB : rfidAWBList) {
						reportRequestRFIDAWB = new ReportRequest();
						Map<String, Object> awbMapData = new HashMap<String, Object>();
						String str = String.valueOf(rfidAWB.get("awbNo"));
						str = str.substring(0, 3) + "-" + str.substring(3);
						awbMapData.put("tagNo", rfidAWB.get("tagNo"));
						awbMapData.put("awbNo", rfidAWB.get("awbNo"));
						awbMapData.put("qty", rfidAWB.get("qty"));
						awbMapData.put("displayAwbNo", str);
						awbMapData.put("epcCode", rfidAWB.get("epcCode"));

						reportRequestRFIDAWB.setCreatedBy(reportRequest.getCreatedBy());
						reportRequestRFIDAWB.setCreatedOn(reportRequest.getCreatedOn());
						reportRequestRFIDAWB.setFlagCRUD(reportRequest.getFlagCRUD());
						reportRequestRFIDAWB.setFlagDelete(reportRequest.getFlagDelete());
						reportRequestRFIDAWB.setFlagInsert(reportRequest.getFlagInsert());
						reportRequestRFIDAWB.setFlagSaved(reportRequest.getFlagSaved());
						reportRequestRFIDAWB.setFlagUpdate(reportRequest.getFlagUpdate());
						reportRequestRFIDAWB.setFormat(reportRequest.getFormat());
						reportRequestRFIDAWB.setLocalDateFormat(reportRequest.getLocalDateFormat());
						reportRequestRFIDAWB.setLocale(reportRequest.getLocale());
						reportRequestRFIDAWB.setLoggedInUser(reportRequest.getLoggedInUser());
						reportRequestRFIDAWB.setModifiedBy(reportRequest.getModifiedBy());
						reportRequestRFIDAWB.setModifiedOn(reportRequest.getModifiedOn());
						reportRequestRFIDAWB.setPrinterType(reportRequest.getPrinterType());
						reportRequestRFIDAWB.setQueueName(reportRequest.getQueueName());
						reportRequestRFIDAWB.setReportName(reportRequest.getReportName());
						reportRequestRFIDAWB.setRequestType(reportRequest.getRequestType());
						reportRequestRFIDAWB.setSectorId(reportRequest.getSectorId());
						reportRequestRFIDAWB.setTenantId(reportRequest.getTenantAirport());
						reportRequestRFIDAWB.setTerminal(reportRequest.getTerminal());
						reportRequestRFIDAWB.setUserType(reportRequest.getUserType());
						reportRequestRFIDAWB.setSectorId(reportRequest.getSectorId());
						reportRequestRFIDAWB.setParameters(awbMapData);
					}
				}
			}
			payload = printerService.generatePrinterReport(reportRequestRFIDAWB);
			printerProcessor.processReport(payload);
		}

		// changed by vikas for RFID uldTag
		else if (reportRequest.getPrinterType().equals(PrinterType.XPS_ULD_TAG)) {
			ReportRequest reportRequestRFIDAWB = null;
			Map<String, Object> data = reportRequest.getParameters();
			reportRequestRFIDAWB = new ReportRequest();
			Map<String, Object> mapfromRequest = (Map<String, Object>) data.get("map");
			Map<String, Object> uldMapData = new HashMap<String, Object>();
			uldMapData.put("tagNo", mapfromRequest.get("tagNo"));
			uldMapData.put("uldtagId", mapfromRequest.get("uldtagId"));
			uldMapData.put("qty", mapfromRequest.get("qty"));
			uldMapData.put("epcCode", mapfromRequest.get("epcCode"));
			uldMapData.put("flightNo", mapfromRequest.get("flightNo"));
			uldMapData.put("flightDate", mapfromRequest.get("flightDate"));
			uldMapData.put("hexaCollectiveUserMemory", mapfromRequest.get("hexaCollectiveUserMemory"));
			uldMapData.put("netweight", mapfromRequest.get("netweight"));
			uldMapData.put("tareweight", mapfromRequest.get("tareweight"));
			uldMapData.put("totalweight", mapfromRequest.get("totalweight"));
			uldMapData.put("destination", mapfromRequest.get("destination"));
			uldMapData.put("remark", mapfromRequest.get("remark"));
			uldMapData.put("loaded", mapfromRequest.get("loaded"));

			uldMapData.put("content", mapfromRequest.get("content"));
			uldMapData.put("user", mapfromRequest.get("user"));
			uldMapData.put("IsXPS", mapfromRequest.get("IsXPS"));
			uldMapData.put("destination", mapfromRequest.get("destination"));
			uldMapData.put("dgDetailsList", mapfromRequest.get("dgDetailsList"));

			reportRequestRFIDAWB.setCreatedBy(reportRequest.getCreatedBy());
			reportRequestRFIDAWB.setCreatedOn(reportRequest.getCreatedOn());
			reportRequestRFIDAWB.setFlagCRUD(reportRequest.getFlagCRUD());
			reportRequestRFIDAWB.setFlagDelete(reportRequest.getFlagDelete());
			reportRequestRFIDAWB.setFlagInsert(reportRequest.getFlagInsert());
			reportRequestRFIDAWB.setFlagSaved(reportRequest.getFlagSaved());
			reportRequestRFIDAWB.setFlagUpdate(reportRequest.getFlagUpdate());
			reportRequestRFIDAWB.setFormat(reportRequest.getFormat());
			reportRequestRFIDAWB.setLocalDateFormat(reportRequest.getLocalDateFormat());
			reportRequestRFIDAWB.setLocale(reportRequest.getLocale());
			reportRequestRFIDAWB.setLoggedInUser(reportRequest.getLoggedInUser());
			reportRequestRFIDAWB.setModifiedBy(reportRequest.getModifiedBy());
			reportRequestRFIDAWB.setModifiedOn(reportRequest.getModifiedOn());
			reportRequestRFIDAWB.setPrinterType(reportRequest.getPrinterType());
			reportRequestRFIDAWB.setQueueName(reportRequest.getQueueName());
			reportRequestRFIDAWB.setReportName(reportRequest.getReportName());
			reportRequestRFIDAWB.setRequestType(reportRequest.getRequestType());
			reportRequestRFIDAWB.setSectorId(reportRequest.getSectorId());
			reportRequestRFIDAWB.setTenantId(reportRequest.getTenantAirport());
			reportRequestRFIDAWB.setTerminal(reportRequest.getTerminal());
			reportRequestRFIDAWB.setUserType(reportRequest.getUserType());
			reportRequestRFIDAWB.setSectorId(reportRequest.getSectorId());
			reportRequestRFIDAWB.setParameters(uldMapData);
			payload = printerService.generatePrinterReport(reportRequestRFIDAWB);
			printerProcessor.processReport(payload);
		} else {
			payload = printerService.generatePrinterReport(reportRequest);
			printerProcessor.processReport(payload);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * @see com.ngen.cosys.report.service.ReportService#printReport(java.io.OutputStream,
	 *      com.ngen.cosys.report.model.ReportRequest)
	 */
	private void printReport(OutputStream outputStream, ReportRequest reportRequest) {
		PrinterPayload payload = getPrinterPayload(outputStream, reportRequest);
		printerProcessor.processReport(payload);
	}

	/**
	 * Generate Printer Payload
	 * 
	 * @param byteArrayOutputStream
	 * @param reportRequest
	 * @return
	 */
	private PrinterPayload getPrinterPayload(OutputStream outputStream, ReportRequest reportRequest) {
		PrinterPayload payload = new PrinterPayload();
		//
		payload.setContentName(reportRequest.getReportName());
		payload.setContentFormat(reportRequest.getFormat().format());
		payload.setQueueName(reportRequest.getPrinterType().getType()); // Temporary
		payload.setPrinterType(reportRequest.getPrinterType().getType());
		payload.setContentParams(reportRequest.getParameters());
		payload.setTenantID(TenantContext.get().getTenantConfig().getTenantId());
		payload.setContentData(reportService.convertOutputStreamToBase64Text(outputStream));
		//
		return payload;
	}

	/**
	 * Excel Report Request
	 * 
	 * @param reportRequest
	 * @return
	 */
	private ExcelReportRequest getExcelReportRequest(ReportRequest reportRequest) {
		ExcelReportRequest excelReportRequest = new ExcelReportRequest();
		//
		excelReportRequest.setReportName(reportRequest.getReportName());
		excelReportRequest.setCarrierCode(null);
		excelReportRequest.setFromDate(null);
		excelReportRequest.setToDate(null);
		excelReportRequest.setParameters(reportRequest.getParameters());
		//
		return excelReportRequest;
	}

}