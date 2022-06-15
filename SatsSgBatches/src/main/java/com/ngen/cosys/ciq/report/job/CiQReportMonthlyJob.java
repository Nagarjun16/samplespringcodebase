/**
 * {@link CiQReportMonthlyJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.job;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.ciq.report.builder.CiQReportBuilder;
import com.ngen.cosys.ciq.report.common.CiQReportConstants;
import com.ngen.cosys.ciq.report.common.ReportFrequency;
import com.ngen.cosys.ciq.report.model.CiQReportMailPayload;
import com.ngen.cosys.ciq.report.model.NotificationMember;
import com.ngen.cosys.ciq.report.model.NotificationSchedule;
import com.ngen.cosys.ciq.report.notification.CiQReportNotification;
import com.ngen.cosys.ciq.report.service.CiQReportService;
import com.ngen.cosys.ciq.report.util.CiQReportUtils;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * CiQ Monthly Report Batch Job - Runs every month once to check the configured
 * report meets the time line configured and Generate the report and sends it
 * 
 * @author NIIT Technologies Ltd
 * 
 */

public class CiQReportMonthlyJob extends AbstractCronJob implements CiQReportJob {
   private static final Logger LOGGER = LoggerFactory.getLogger(CiQReportMonthlyJob.class);

   @Autowired
   CiQReportService ciqReportService;

   @Autowired
   CiQReportBuilder ciqReportBuilder;

   @Autowired
   CiQReportNotification ciqReportNotification;

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         // Batch Running Time
         LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
        		 TenantContext.get().getTenantId());
         // Config
         List<NotificationSchedule> notificationSchedules = notificationScheduleConfig(ReportFrequency.MONTHLY);
         boolean notificationSchedulesAvailbility = !CollectionUtils.isEmpty(notificationSchedules) ? true : false;
         LOGGER.info("CiQ Report Monthly Job Schedules availability - {}",
               String.valueOf(notificationSchedulesAvailbility));
         if (notificationSchedulesAvailbility) {
            for (NotificationSchedule reportSchedule : notificationSchedules) {
               boolean dayOfTheMonth = false;
               boolean referenceExists = false;
               BigInteger reportLogId = null;
               boolean dataExists = false;
               if (Objects.nonNull(reportSchedule.getDayInMonth())) {
                  // Day
                  dayOfTheMonth = CiQReportUtils.dayOfTheMonth(currentZoneTime, reportSchedule.getDayInMonth());

                  if (dayOfTheMonth) {
                     // Check the table reference
                     referenceExists = ciqReportService.verifyCiQReport(reportSchedule);
                  }
               }

               if (!StringUtils.isEmpty(reportSchedule.getMessageType()) 
              		 && !StringUtils.isEmpty(reportSchedule.getCarrierCode()) 
              		 && dayOfTheMonth
                     && reportSchedule.getReportName().equalsIgnoreCase(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT)) {
            	  try {
            		  //return true if data exist
            		  dataExists = ciqReportService.dataExists(reportSchedule);
            		  
            	  }catch (Exception e) {
            		  LOGGER.error(CiQReportConstants.CIQ_FSU_EXCEPTION_REPORT +"-"+ reportSchedule.getMessageType(), e);
            	  }
               }

               if (dayOfTheMonth && !referenceExists && dataExists) {
                  // Generate if condition matched
                  CiQReportMailPayload reportMailPayload = generateReport(reportSchedule);
                  if (!StringUtils.isEmpty(reportMailPayload.getReportName())) {
                     reportLogId = ciqReportService.logCiQReport(reportSchedule);
                     // Send Notification
                     sendNotification(reportSchedule, reportMailPayload);
                     // Update COMPLETED Status in reference table
                     ciqReportService.updateCiQReport(reportLogId);
                  }
               }
            }
         }
      } catch (Exception ex) {
         LOGGER.error("CiQ Report Monthly Job exception occurred - {}", ex);
      }
   }

   @Override
   public List<NotificationSchedule> notificationScheduleConfig(String frequency) throws CustomException {
      LOGGER.info("CiQ Report Weekly Job :: notification Scheduled Config - {}");
      return ciqReportService.getScheduledReports(frequency);
   }

   @Override
   public CiQReportMailPayload generateReport(NotificationSchedule reportSchedule) throws CustomException {
      LOGGER.info("CiQ Report Monthly Job :: Generate report - {}");
      return ciqReportBuilder.generate(reportSchedule);
   }

   @Override
   public void sendNotification(NotificationSchedule reportSchedule, CiQReportMailPayload reportMailPayload)
         throws CustomException {
      LOGGER.info("CiQ Report Monthly Job :: Send Notification - {}");
      if (!CollectionUtils.isEmpty(reportSchedule.getNotificationMembers())) {
         Map<String, Set<String>> notificationEmails = reportSchedule.getNotificationMembers().stream() //
               .collect(Collectors.groupingBy(NotificationMember::getPartyType, // Group By Party Type
                     Collectors.mapping(NotificationMember::getEmailId, Collectors.toSet()))); // Email list
         reportMailPayload.setNotificationEmails(notificationEmails);
         ciqReportNotification.sendEmail(reportMailPayload, reportSchedule);
      }
   }
}
