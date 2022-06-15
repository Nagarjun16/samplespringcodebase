/**
 * {@link FFMMonitoringJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.application.job;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.application.service.ApplicationSystemMonitoringService;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.timezone.util.TenantZoneTime;

public class FFMMonitoringJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(FFMMonitoringJob.class);

   private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   private static final String DATE_FORMAT = "ddMMMyyyy";
   private static final String FFM_REPORT_NAME = "FFMMonitoringReport";
   private static final String FFM_MAIL_TEMPLATE = "FFM MonitoringReport";
   private static final String MAIL_BODY = "Attached File:";
   private static final String ONLYTIME_FORMAT = "HH:mm";
   
   @Autowired
   ReportMailService reportMailService;
   @Autowired
   ApplicationSystemMonitoringService service;

   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

      LocalDateTime currentUTCTime = LocalDateTime.now();
      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(currentUTCTime,TenantContext.get().getTenantId());
      String email = null;
      try {
         email = service.fetchEmailsforSystemMonitoring();
      } catch (CustomException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      String[] emailAddress =  email.split(",");
      List<String>  emailAddresses = new ArrayList<String>(Arrays.asList(emailAddress));
      Map<String, Object> reportParams = new HashMap<>();
     // reportParams.put(ReportParams.FROM_DATE,LocalDateTime.of(currentZoneTime.toLocalDate().minusDays(1), LocalTime.of(00, 00)).format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
      //reportParams.put(ReportParams.TO_DATE,LocalDateTime.of(currentZoneTime.toLocalDate().minusDays(1), LocalTime.of(23, 59)).format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
      reportParams.put(ReportParams.FROM_DATE,LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.now().minusHours(3)).format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
      reportParams.put(ReportParams.TO_DATE,LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.now()).format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
      
      if (!CollectionUtils.isEmpty(emailAddresses)) { {
      // Report Payload
         List<ReportMailPayload> reportPayload = new ArrayList<>();
         LocalDateTime mailTime = currentZoneTime;
         // FFM Mail
         EMailEvent emailEvent = getEmailPayload(emailAddresses, true, mailTime);
         reportPayload = new ArrayList<>();
         emailEvent = getEmailPayload(emailAddresses, false, mailTime);
         reportPayload.add(getReportMailPayload(reportParams, false, mailTime));
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
   private EMailEvent getEmailPayload(List<String> emailAddresses, boolean exportList, LocalDateTime currentZoneTime) {
      EMailEvent emailEvent = new EMailEvent();
      emailEvent.setMailToAddress(Arrays.copyOf(emailAddresses.toArray(), emailAddresses.size(), String[].class));
      emailEvent.setNotifyAddress(null);
      String fileName = null;
         fileName = shiftFileNameFormat(FFM_REPORT_NAME, currentZoneTime);
         emailEvent.setMailSubject(mailSubject(FFM_MAIL_TEMPLATE, currentZoneTime));
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
      return subject + " " + dateFormat(currentZoneTime)+"_"+currentZoneTime.format(DateTimeFormatter.ofPattern(ONLYTIME_FORMAT));
   }
   
   /**
    * @param reportName
    * @param currentZoneTime
    * @param shiftExtension
    * @return
    */
   private String shiftFileNameFormat(String reportName, LocalDateTime currentZoneTime) {
      return reportName + "_" + dateFormat(currentZoneTime)+"_"+currentZoneTime.format(DateTimeFormatter.ofPattern(ONLYTIME_FORMAT));
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
         reportMailPayload.setReportName(FFM_REPORT_NAME);
         reportMailPayload.setFileName(shiftFileNameFormat(FFM_REPORT_NAME, currentZoneTime));
     
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

