package com.ngen.cosys.application.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.controller.ExpTonnageDailyVerificationFltController.ReportParams;
import com.ngen.cosys.application.service.ApplicationSystemMonitoringService;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.timezone.util.TenantZoneTime;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "/api/systemmonitoring/exptonnageFltShp")
public class ExpTonnageDailyVerificationFltShpController {

   private static final Logger LOGGER = LoggerFactory.getLogger(ExpTonnageDailyVerificationFltShpController.class);
   private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   private static final String DATE_FORMAT = "ddMMMyyyy";
   private static final String REPORT_NAME = "Export_Daily_Tonnage_Verification_Flt_Shp";
   private static final String MAIL_TEMPLATE = "Export Daily Tonnage Verification(Shipment)";
   private static final String MAIL_BODY = "Attached File:";
   private static final String ONLYTIME_FORMAT = "HH:mm";
   
   @Autowired
   ReportMailService reportMailService;
   @Autowired
   ApplicationSystemMonitoringService service;
   
	
   @ApiOperation("Get Flight from cosys")
	@PostMapping(value = "/api/batches/expdailyverificationfltshp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void ffmfwbMonitoring() throws CustomException {
	   LocalDateTime currentUTCTime = LocalDateTime.now();
	      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(currentUTCTime,TenantContext.get().getTenantId());
	      
	      String email = service.fetchEmailsforSystemMonitoring();
	      String[] emailAddress =  email.split(",");
	      List<String>  emailAddresses = new ArrayList<String>(Arrays.asList(emailAddress));
	      Map<String, Object> reportParams = new HashMap<>();
	      reportParams.put(ReportParams.FROM_DATE,LocalDateTime.of(currentZoneTime.toLocalDate().withDayOfMonth(1), LocalTime.of(00,00)).format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
          reportParams.put(ReportParams.TO_DATE,LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.now()).format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
	      
	      if (!CollectionUtils.isEmpty(emailAddresses)) { {
	      // Report Payload
	         List<ReportMailPayload> reportPayload = new ArrayList<>();
	         LocalDateTime mailTime = currentZoneTime;
	         // Export Mail
	         EMailEvent emailEvent = getEmailPayload(emailAddresses, true, mailTime);
	         reportPayload.add(getReportMailPayload(reportParams, true, mailTime));
	         reportMailService.sendReport(reportPayload, emailEvent);
	         LOGGER.info("Export Tonnage Daily Verification Flt Job :: Export Tonnage list sent successfully");
	      }
	      
	      
	  
	  }
	}
	
	 /**
	    * GET Report Mail Payload
	    * 
	    * @param emailAddresses
	    * @param exportList
	    * @param currentZoneTime
	    * @param shiftExtension
	    * @return
	    */
	   private EMailEvent getEmailPayload(List<String> emailAddresses, boolean exportList, LocalDateTime currentZoneTime) {
	      EMailEvent emailEvent = new EMailEvent();
	      emailEvent.setMailToAddress(Arrays.copyOf(emailAddresses.toArray(), emailAddresses.size(), String[].class));
	      emailEvent.setNotifyAddress(null);
	      String fileName = null;
	         fileName = shiftFileNameFormat(REPORT_NAME, currentZoneTime);
	         emailEvent.setMailSubject(mailSubject(MAIL_TEMPLATE, currentZoneTime));
	         emailEvent.setMailBody(MAIL_BODY + " " + fileName);
	      return emailEvent;
	   }
	   
	   /**
	    * Email Subject
	    * 
	    * @param subject
	    * @param currentZoneTime
	    * @param shiftExtension
	    * @return
	    */
	   private String mailSubject(String subject, LocalDateTime currentZoneTime) {
	      return subject + " " + dateFormat(currentZoneTime)+" "+currentZoneTime.format(DateTimeFormatter.ofPattern(ONLYTIME_FORMAT));
	   }
	   
	   /**
	    * @param reportName
	    * @param currentZoneTime
	    * @param shiftExtension
	    * @return
	    */
	   private String shiftFileNameFormat(String reportName, LocalDateTime currentZoneTime) {
	      return reportName +"_"+LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.now()).format(MultiTenantUtility.getTenantDateFormat());
	   }
	   
	   /**
	    * @param currentZoneTime
	    * @return
	    */
	   private String dateFormat(LocalDateTime currentZoneTime) {
	      return currentZoneTime.format(MultiTenantUtility.getTenantDateFormat()).toUpperCase();
	   }
	   
	   /**
	    * Report Mail Payload
	    * 
	    * @param reportParameters
	    * @param exportList
	    * @param currentZoneTime
	    * @param shiftExtension
	    * @return
	    */
	   private ReportMailPayload getReportMailPayload(Map<String, Object> reportParameters, boolean exportList,
	         LocalDateTime currentZoneTime) {
	      ReportMailPayload reportMailPayload = new ReportMailPayload();
	         reportMailPayload.setReportName(REPORT_NAME);
	         reportMailPayload.setFileName(shiftFileNameFormat(REPORT_NAME, currentZoneTime));
	      reportMailPayload.setReportFormat(ReportFormat.XLS);
	      reportMailPayload.setReportParams(reportParameters);
	      return reportMailPayload;
	   }
	   
	   /**
	    * Report Parameters sub class
	    */
	   class ReportParams {
	      // Params
	      public static final String FROM_DATE = "FromDate";
	      public static final String TO_DATE = "ToDate";
	      public static final String SHIFT = "Shift";
	   }
	   

}
