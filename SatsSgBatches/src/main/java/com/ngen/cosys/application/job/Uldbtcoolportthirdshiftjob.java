/**
 * {@link Uldbtcoolportthirdshiftjob}
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
import com.ngen.cosys.uncollectedfreightout.service.UncollectedFreightoutService;

/**
 * ULD BT Coolport Job
 * 
 * @author NIIT Technologies Ltd
 */
public class Uldbtcoolportthirdshiftjob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(Uldbtcoolportjob.class);

   private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   private static final String DATE_FORMAT = "ddMMMyyyy";
   private static final String EXPORT_REPORT_NAME = "PHC_Ramp_Handover_Export";
   private static final String IMPORT_REPORT_NAME = "PHC_Ramp_Handover_Import";
   private static final String FIRST_SHIFT_FILE_NAME = "_0030_0900";
   private static final String SECOND_SHIFT_FILE_NAME = "_0900_1700";
   private static final String THIRD_SHIFT_FILE_NAME = "_1700_0030";
   private static final String EXPORT_MAIL_TEMPLATE = "PHC Ramp Handover Export";
   private static final String IMPORT_MAIL_TEMPLATE = "PHC Ramp Handover Import";
   private static final String MAIL_BODY = "Attached File:";
   
   @Autowired
   UncollectedFreightoutService service;
   
   @Autowired
   ReportMailService reportMailService;

   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

      // UTC 0100 - 1st Shift && 0930 - 2nd Shift && 1700 - 3rd Shift
      LocalDateTime currentUTCTime = LocalDateTime.now();
      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(currentUTCTime,TenantContext.get().getTenantId());
      // Shift Timings Start & End Times
      LocalDateTime firstShiftStartTime = LocalDateTime.of(currentUTCTime.toLocalDate(), LocalTime.of(00, 30));
      LocalDateTime firstShiftEndTime = LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.of(8, 59));
      
      LocalDateTime secondShiftStartTime = LocalDateTime.of(currentUTCTime.toLocalDate(), LocalTime.of(9, 00));
      LocalDateTime secondShiftEndTime = LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.of(16, 59));
      
      LocalDateTime thirdShiftStartTime = LocalDateTime.of(currentUTCTime.toLocalDate(), LocalTime.of(17, 00));
      LocalDateTime thirdShiftEndTime = LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.of(00, 29));
      // Shifts
      LocalDateTime firstShiftTime = LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.of(9, 29));
      LocalDateTime secondShiftTime = LocalDateTime.of(currentZoneTime.toLocalDate(), LocalTime.of(17, 29));
      LocalDateTime thirdShiftTime = LocalDateTime.of(currentZoneTime.plusHours(8).toLocalDate(), LocalTime.of(00, 59));
      //
      boolean withInFirstShift = currentZoneTime.isAfter(firstShiftTime) && currentZoneTime.isBefore(secondShiftTime);
      boolean withInSecondShift = currentZoneTime.isAfter(secondShiftTime) && currentZoneTime.isBefore(thirdShiftTime);
      boolean withInThirdShift = currentZoneTime.isAfter(thirdShiftTime) && currentZoneTime.isBefore(firstShiftTime);

     
      //
      LOGGER.info("ULD BT Cool Port Job :: Current UTC Time - {}, and Zone Time - {}", currentUTCTime, currentZoneTime);
      List<String> emailAddresses = Collections.emptyList();
      try {
         emailAddresses = service.getEmailAddress();
      } catch (Exception ex) {
         LOGGER.error("ULD BT Cool Port Job :: Exception occurred - {}", ex);
      }
      boolean emailAddressAvailability = !CollectionUtils.isEmpty(emailAddresses) ? true : false;
      LOGGER.info("ULD BT Cool Port Job :: Email Addresses availability - {}",
            String.valueOf(emailAddressAvailability));
      if (!emailAddressAvailability) {
         return;
      }
      String shiftExtension = null;
      LocalDateTime mailTime = currentZoneTime;
      Map<String, Object> reportParameters = Collections.synchronizedMap(new HashMap<>());
      //
      if (withInFirstShift) {
         LOGGER.info("ULD BT Cool Port Job :: WithINFirstShift Case # Current Zone Time - {}", currentZoneTime);
         reportParameters.put(ReportParams.FROM_DATE,
               firstShiftStartTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParameters.put(ReportParams.TO_DATE, firstShiftEndTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParameters.put(ReportParams.SHIFT, String.valueOf(1));
         shiftExtension = FIRST_SHIFT_FILE_NAME;
      } else if (withInSecondShift) {
         LOGGER.info("ULD BT Cool Port Job :: WithINSecondShift Case # Current Zone Time - {}", currentZoneTime);
         reportParameters.put(ReportParams.FROM_DATE,
               secondShiftStartTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParameters.put(ReportParams.TO_DATE,
               secondShiftEndTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParameters.put(ReportParams.SHIFT, String.valueOf(2));
         shiftExtension = SECOND_SHIFT_FILE_NAME;
      } else if (withInThirdShift) {
         LOGGER.info("ULD BT Cool Port Job :: WithINThirdShift Case # Current Zone Time - {}", currentZoneTime);
         reportParameters.put(ReportParams.FROM_DATE,
               thirdShiftStartTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParameters.put(ReportParams.TO_DATE, thirdShiftEndTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
         reportParameters.put(ReportParams.SHIFT, String.valueOf(3));
         shiftExtension = THIRD_SHIFT_FILE_NAME;
         mailTime = currentUTCTime;
      } else {
         // Case never occur
         LOGGER.info("ULD BT Cool Port Job :: Case Never occur timings # Current Zone Time - {}", currentZoneTime);
      }
      // Report Payload
      List<ReportMailPayload> reportPayload = new ArrayList<>();
      // Export Mail
      EMailEvent emailEvent = getEmailPayload(emailAddresses, true, mailTime, shiftExtension);
      reportPayload.add(getReportMailPayload(reportParameters, true, mailTime, shiftExtension));
      reportMailService.sendReport(reportPayload, emailEvent);
      LOGGER.info("ULD BT Cool Port Job :: Export list sent successfully");
      // Import Mail
      reportPayload = new ArrayList<>();
      emailEvent = getEmailPayload(emailAddresses, false, mailTime, shiftExtension);
      reportPayload.add(getReportMailPayload(reportParameters, false, mailTime, shiftExtension));
      reportMailService.sendReport(reportPayload, emailEvent);
      LOGGER.info("ULD BT Cool Port Job :: Import list sent successfully");
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
         fileName = shiftFileNameFormat(EXPORT_REPORT_NAME, currentZoneTime, shiftExtension);
         emailEvent.setMailSubject(mailSubject(EXPORT_MAIL_TEMPLATE, currentZoneTime, shiftExtension));
         emailEvent.setMailBody(MAIL_BODY + " " + fileName);
      } else {
         fileName = shiftFileNameFormat(IMPORT_REPORT_NAME, currentZoneTime, shiftExtension);
         emailEvent.setMailSubject(mailSubject(IMPORT_MAIL_TEMPLATE, currentZoneTime, shiftExtension));
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
         reportMailPayload.setReportName(EXPORT_REPORT_NAME);
         reportMailPayload.setFileName(shiftFileNameFormat(EXPORT_REPORT_NAME, currentZoneTime, shiftExtension));
      } else {
         reportMailPayload.setReportName(IMPORT_REPORT_NAME);
         reportMailPayload.setFileName(shiftFileNameFormat(IMPORT_REPORT_NAME, currentZoneTime, shiftExtension));
      }
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
