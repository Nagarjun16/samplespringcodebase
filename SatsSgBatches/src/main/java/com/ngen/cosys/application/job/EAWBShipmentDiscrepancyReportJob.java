package com.ngen.cosys.application.job;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Service.EAWBShipmentDiscrepancyReportService;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;

public class EAWBShipmentDiscrepancyReportJob extends AbstractCronJob {

	@Autowired
	EAWBShipmentDiscrepancyReportService service;
	@Autowired
	ReportMailService reportMailService;

	private static final Logger logger = LoggerFactory.getLogger(EAWBShipmentDiscrepancyReportJob.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// method will take care of sending mail with report
		logger.warn("inside eawb discrepancy report job");
		super.executeInternal(jobExecutionContext);
		LocalDateTime yesterdayTime = null;
		LocalDateTime todayTime = null;

		final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";

		List<String> responseDataForEAWB = new ArrayList<>();
		List<String> responseDataForNEAWB = new ArrayList<>();
		try {
			responseDataForEAWB = service.getShipemntInformation();
			logger.warn("All the carriers for EAWB ---->"+ responseDataForEAWB);
			responseDataForNEAWB = service.getShipemntInformationForNAWB();
			logger.warn("All the carriers for NEAWB ---->"+ responseDataForNEAWB);
		} catch (CustomException e) {
			logger.warn(e.getMessage());
		}
		if (!CollectionUtils.isEmpty(responseDataForEAWB)) {
			yesterdayTime = LocalDate.now().atTime(16, 0).minusDays(1);
			todayTime = LocalDate.now().atTime(16, 0);
			String fromDate = yesterdayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
			String toDate = todayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
			logger.warn("From Date for EAWB ----> "+ fromDate + "to date for EAWB ----> "+ toDate);
			for (String value : responseDataForEAWB) {
				List<String> emailAddresses = null;
				try {
					emailAddresses = service.getEmailAddressesForEAWB(value);
					logger.warn("email address for EAWB "+ value + " carrier "+ ": "+ emailAddresses);
				} catch (CustomException e) {
					logger.warn(e.getMessage());
				}
				Map<String, Object> reportParams = new HashMap<>();
				reportParams.put("carrierCode", value);
				reportParams.put("fromDate", fromDate);
				reportParams.put("toDate", toDate);
				logger.warn("report param data for EAWB : "+ reportParams);
				List<ReportMailPayload> reportPayload = new ArrayList<>();
				ReportMailPayload reportMailPayload = new ReportMailPayload();
				reportMailPayload.setReportName("EAWB Discrepancy List");
				reportMailPayload.setReportFormat(ReportFormat.CSV);
				reportMailPayload.setReportParams(reportParams);
				logger.warn("report mail payload data for EAWB : "+ reportMailPayload);
				EMailEvent emailEvent = new EMailEvent();
				if (!CollectionUtils.isEmpty(emailAddresses)) {
					emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
					emailEvent.setNotifyAddress(null);
					emailEvent.setMailSubject("EAWB discrepancy report " + "-" + value + "_"
							+ LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + "-" + "[DO NOT REPLY]");
					emailEvent.setMailBody("EAWB Report");
					reportPayload.add(reportMailPayload);
					logger.warn("email event data: "+ emailEvent);
					reportMailService.sendReport(reportPayload, emailEvent);
					logger.warn("mail successfully sent for EAWB");
				}
			}
		}
		if (!CollectionUtils.isEmpty(responseDataForNEAWB)) {
			yesterdayTime = LocalDate.now().atTime(16, 0).minusDays(1);
			todayTime = LocalDate.now().atTime(16, 0);
			String fromDate = yesterdayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
			String toDate = todayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
			logger.warn("From Date ----> "+ fromDate + "to date ----> "+ toDate);
			for (String value : responseDataForNEAWB) {
				List<String> emailAddresses = null;
				try {
					emailAddresses = service.getEmailAddressesForNEAWB(value);
					logger.warn("email address for NEAWB "+ value + " carrier "+ ": "+ emailAddresses);
				} catch (CustomException e) {
					logger.warn(e.getMessage());
				}
				Map<String, Object> reportParams = new HashMap<>();
				reportParams.put("carrierCode", value);
				reportParams.put("fromDate", fromDate);
				reportParams.put("toDate", toDate);
				logger.warn("report param data NEAWB : "+ reportParams);
				List<ReportMailPayload> reportPayload = new ArrayList<>();
				ReportMailPayload reportMailPayload = new ReportMailPayload();
				reportMailPayload.setReportName("NON_EAWB Discrepancy List");
				reportMailPayload.setReportFormat(ReportFormat.CSV);
				reportMailPayload.setReportParams(reportParams);
				logger.warn("report mail payload data for EAWB : "+ reportMailPayload);
				EMailEvent emailEvent = new EMailEvent();
				if (!CollectionUtils.isEmpty(emailAddresses)) {
					emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
					emailEvent.setNotifyAddress(null);
					emailEvent.setMailSubject("NON_EAWB discrepancy report " + "-" + value + "_"
							+ LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + "-" + "[DO NOT REPLY]");
					emailEvent.setMailBody("NEAWB Report");
					reportPayload.add(reportMailPayload);
					logger.warn("email event data: "+ emailEvent);
					reportMailService.sendReport(reportPayload, emailEvent);
					logger.warn("mail successfully sent for NEAWB");
				}

			}
		}
	}
}
