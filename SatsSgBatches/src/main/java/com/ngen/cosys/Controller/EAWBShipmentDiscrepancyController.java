package com.ngen.cosys.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.EAWBShipmentDiscrepancyReportParentModel;
import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.NonAWBLowStockLimitModel;
import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.NonAWBLowStockLimitParentModel;
import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Service.EAWBShipmentDiscrepancyReportService;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.application.job.EAWBShipmentDiscrepancyReportJob;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;

import io.swagger.annotations.ApiOperation;

@RestController
public class EAWBShipmentDiscrepancyController {

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	@Autowired
	EAWBShipmentDiscrepancyReportService service;
	@Autowired
	ReportMailService reportMailService;

	private static final Logger logger = LoggerFactory.getLogger(EAWBShipmentDiscrepancyReportJob.class);

	@ApiOperation("fetch data according to carrier code")
	@PostRequest(value = "api/jobdata/carriercode", method = RequestMethod.POST)
	public BaseResponse<List<EAWBShipmentDiscrepancyReportParentModel>> fetchShipmentDiscrepancyReport()
			throws CustomException {
		BaseResponse<List<EAWBShipmentDiscrepancyReportParentModel>> response = utilitiesModelConfiguration
				.getBaseResponseInstance();

		LocalDateTime yesterdayTime = null;
		LocalDateTime todayTime = null;

		final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

		List<String> responseDataForEAWB = new ArrayList<>();
		List<String> responseDataForNEAWB = new ArrayList<>();
		try {
			responseDataForEAWB = service.getShipemntInformation();
			responseDataForNEAWB = service.getShipemntInformationForNAWB();
		} catch (CustomException e) {
			logger.warn(e.getMessage());
		}
		if (!CollectionUtils.isEmpty(responseDataForEAWB)) {
			yesterdayTime = LocalDate.now().atTime(16, 0).minusDays(1);
			todayTime = LocalDate.now().atTime(16, 0).plusDays(1);
			String fromDate = yesterdayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
			String toDate = todayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
			for (String value : responseDataForEAWB) {
				List<String> emailAddresses = null;
				try {
					emailAddresses = service.getEmailAddressesForEAWB(value);
				} catch (CustomException e) {
					logger.warn(e.getMessage());
				}
				Map<String, Object> reportParams = new HashMap<>();
				reportParams.put("carrierCode", value);
				reportParams.put("fromDate", fromDate);
				reportParams.put("toDate", toDate);
				List<ReportMailPayload> reportPayload = new ArrayList<>();
				ReportMailPayload reportMailPayload = new ReportMailPayload();
				reportMailPayload.setReportName("EAWB Discrepancy List");
				reportMailPayload.setReportFormat(ReportFormat.CSV);
				reportMailPayload.setReportParams(reportParams);
				EMailEvent emailEvent = new EMailEvent();
				if (!CollectionUtils.isEmpty(emailAddresses)) {
					emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
					emailEvent.setNotifyAddress(null);
					emailEvent.setMailSubject("EAWB discrepancy report " + "-" + value + "_"
							+ LocalDate.now().format(MultiTenantUtility.getTenantDateFormat()) + "-" + "[DO NOT REPLY]");
					emailEvent.setMailBody("EAWB Report");
					reportPayload.add(reportMailPayload);
					reportMailService.sendReport(reportPayload, emailEvent);
				}
			}
		}
		if (!CollectionUtils.isEmpty(responseDataForNEAWB)) {
			yesterdayTime = LocalDate.now().atTime(16, 0).minusDays(1);
			todayTime = LocalDate.now().atTime(16, 0).plusDays(1);
			String fromDate = yesterdayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
			String toDate = todayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
			for (String value : responseDataForNEAWB) {
				List<String> emailAddresses = null;
				try {
					emailAddresses = service.getEmailAddressesForNEAWB(value);
				} catch (CustomException e) {
					logger.warn(e.getMessage());
				}
				Map<String, Object> reportParams = new HashMap<>();
				reportParams.put("carrierCode", value);
				reportParams.put("fromDate", fromDate);
				reportParams.put("toDate", toDate);
				List<ReportMailPayload> reportPayload = new ArrayList<>();
				ReportMailPayload reportMailPayload = new ReportMailPayload();
				reportMailPayload.setReportName("NON_EAWB Discrepancy List");
				reportMailPayload.setReportFormat(ReportFormat.CSV);
				reportMailPayload.setReportParams(reportParams);
				EMailEvent emailEvent = new EMailEvent();
				if (!CollectionUtils.isEmpty(emailAddresses)) {
					emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
					emailEvent.setNotifyAddress(null);
					emailEvent.setMailSubject("NON_EAWB discrepancy report " + "-" + value + "_"
							+ LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + "-" + "[DO NOT REPLY]");
					emailEvent.setMailBody("NEAWB Report");
					reportPayload.add(reportMailPayload);
					reportMailService.sendReport(reportPayload, emailEvent);
				}

			}
		}
		return response;
	}

	@ApiOperation("fetch data according to carrier code")
	@PostRequest(value = "api/jobdata/carriercodefornawb", method = RequestMethod.POST)
	public BaseResponse<List<String>> fetchShipmentDiscrepancyReportForNAWB()
			throws CustomException {
		BaseResponse<List<String>> response = utilitiesModelConfiguration
				.getBaseResponseInstance();
		response.setData(service.getShipemntInformationForNAWB());
		return response;
	}

	@ApiOperation("fetch data for nawb stock limit check")
	@PostRequest(value = "api/jobdata/fetchforawbstock", method = RequestMethod.POST)
	public BaseResponse<List<NonAWBLowStockLimitParentModel>> getShipemntInformationAwbStockLimit()
			throws CustomException {
	   List<String> emailAddresses = new ArrayList<>(); 
       List<NonAWBLowStockLimitParentModel> nonAWBStockLimitData = service.getShipemntInformationAwbStockLimit();
         if(!CollectionUtils.isEmpty(nonAWBStockLimitData)) {
            for(NonAWBLowStockLimitParentModel value: nonAWBStockLimitData) {
               for(NonAWBLowStockLimitModel value2: value.getAwbLists()) {
                  if(value2.getReserved() > 0) {
                     try {
                        try {
                           service.revertUnUsedButReservedAWBstoStockAWBs(value2.getAwbStockId());
                           emailAddresses = service.getEmailAddressesForNEAWBLowStock(value2.getCarriercode());                        
                        } catch (CustomException e) {
                           e.printStackTrace();
                        }
                     } catch (IllegalArgumentException ex) {
                        ex.getMessage();
                     }
                  }
               }
            }
         }
         
         try {
            /*List<String> emailAddresses = new ArrayList<>();
           
            emailAddresses.add("ASHWIN.5.KAUR@NIIT-TECH.COM");*/
            Map<String, Object> reportParams = new HashMap<>();
            List<ReportMailPayload> reportPayload = new ArrayList<>();
            ReportMailPayload reportMailPayload = new ReportMailPayload();
            reportMailPayload.setReportName("stocklimit_report");
            reportMailPayload.setReportFormat(ReportFormat.CSV);
            reportMailPayload.setReportParams(reportParams);
            EMailEvent emailEvent = new EMailEvent();
           /* emailAddresses.add("NEEL.5.KAMAL@NIIT-TECH.COM");
            emailAddresses.add("UPPARA.LAKSHMI@NIIT-TECH.COM");*/
            if(!CollectionUtils.isEmpty(emailAddresses)) {
               emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
            }
            
            emailEvent.setNotifyAddress(null);
            emailEvent.setMailSubject("EAWB discrepancy report " + "-" + LocalDateTime.now() + "-" + "[DO NOT REPLY]");
            emailEvent.setMailBody(null);
            reportPayload.add(reportMailPayload);
            reportMailService.sendReport(reportPayload, emailEvent);
            System.out.println(emailEvent.getMailToAddress());
         } catch(Exception e) {
            e.getMessage();
         }
		BaseResponse<List<NonAWBLowStockLimitParentModel>> response = utilitiesModelConfiguration
				.getBaseResponseInstance();
		
	     response.setData(service.getShipemntInformationAwbStockLimit());
		return response;
	}

}
