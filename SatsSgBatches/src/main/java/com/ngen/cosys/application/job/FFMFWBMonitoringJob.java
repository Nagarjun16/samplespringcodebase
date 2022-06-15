/**
 * {@link FFMFWBMonitoringJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.application.job;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * ULD BT Coolport Job
 * 
 * @author NIIT Technologies Ltd
 */
public class FFMFWBMonitoringJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(FFMFWBMonitoringJob.class);

   private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   private static final String DATE_FORMAT = "ddMMMyyyy";
   private static final String FWB_REPORT_NAME = "FWBMonitoringReport";
   private static final String FFM_REPORT_NAME = "FFMMonitoringReport";
   private static final String FWB_MAIL_TEMPLATE = "FWB Monitoring Report";
   private static final String FFM_MAIL_TEMPLATE = "FFM MonitoringReport";
   private static final String MAIL_BODY = "Attached File:";
   
   @Autowired
   ReportMailService reportMailService;

   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

      LocalDateTime currentUTCTime = LocalDateTime.now();
      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(currentUTCTime,TenantContext.get().getTenantId());
      List<String> emailAddresses = null;
         emailAddresses.add("UPPARA.LAKSHMI@NIIT-TECH.COM");
      Map<String, Object> reportParams = new HashMap<>();
      reportParams.put(ReportParams.FROM_DATE,LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.now().minusHours(3)).format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
      reportParams.put(ReportParams.TO_DATE,LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.now()).format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
      
      if (!CollectionUtils.isEmpty(emailAddresses)) { {
      // Report Payload
         List<ReportMailPayload> reportPayload = new ArrayList<>();
         String shiftExtension = null;
         LocalDateTime mailTime = currentZoneTime;
         // Export Mail
         EMailEvent emailEvent = getEmailPayload(emailAddresses, true, mailTime, shiftExtension);
         reportPayload.add(getReportMailPayload(reportParams, true, mailTime, shiftExtension));
         reportMailService.sendReport(reportPayload, emailEvent);
         LOGGER.info("FWB Monitoring Job :: FWB Monitoring list sent successfully");
         // Import Mail
         reportPayload = new ArrayList<>();
         emailEvent = getEmailPayload(emailAddresses, false, mailTime, shiftExtension);
         reportPayload.add(getReportMailPayload(reportParams, false, mailTime, shiftExtension));
         reportMailService.sendReport(reportPayload, emailEvent);
         LOGGER.info("FFM Monitoring Job :: ImportFFM Monitoring list sent successfully");
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
   private EMailEvent getEmailPayload(List<String> emailAddresses, boolean exportList, LocalDateTime currentZoneTime,
         String shiftExtension) {
      EMailEvent emailEvent = new EMailEvent();
      emailEvent.setMailToAddress(Arrays.copyOf(emailAddresses.toArray(), emailAddresses.size(), String[].class));
      emailEvent.setNotifyAddress(null);
      String fileName = null;
      if (exportList) {
         fileName = shiftFileNameFormat(FWB_REPORT_NAME, currentZoneTime, shiftExtension);
         emailEvent.setMailSubject(mailSubject(FWB_MAIL_TEMPLATE, currentZoneTime, shiftExtension));
         emailEvent.setMailBody(MAIL_BODY + " " + fileName);
      } else {
         fileName = shiftFileNameFormat(FFM_REPORT_NAME, currentZoneTime, shiftExtension);
         emailEvent.setMailSubject(mailSubject(FFM_MAIL_TEMPLATE, currentZoneTime, shiftExtension));
         emailEvent.setMailBody(MAIL_BODY + " " + fileName);
      }
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
   private String mailSubject(String subject, LocalDateTime currentZoneTime, String shiftExtension) {
      return subject + " " + dateFormat(currentZoneTime) + shiftExtension;
   }
   
   /**
    * @param reportName
    * @param currentZoneTime
    * @param shiftExtension
    * @return
    */
   private String shiftFileNameFormat(String reportName, LocalDateTime currentZoneTime, String shiftExtension) {
      return reportName + "_" + dateFormat(currentZoneTime) + shiftExtension;
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
         LocalDateTime currentZoneTime, String shiftExtension) {
      ReportMailPayload reportMailPayload = new ReportMailPayload();
      if (exportList) {
         reportMailPayload.setReportName(FWB_REPORT_NAME);
         reportMailPayload.setFileName(shiftFileNameFormat(FWB_REPORT_NAME, currentZoneTime, shiftExtension));
      } else {
         reportMailPayload.setReportName(FFM_REPORT_NAME);
         reportMailPayload.setFileName(shiftFileNameFormat(FFM_REPORT_NAME, currentZoneTime, shiftExtension));
      }
      reportMailPayload.setReportFormat(ReportFormat.CSV);
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
