package com.ngen.cosys.application.job;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.ngen.cosys.ScnLyingList.Service.ScnLyingListService;
import com.ngen.cosys.ScnLyingListModel.ScnLyingListParentModel;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;
import com.ngen.cosys.timezone.util.TenantZoneTime;

public class ScnLyingListJob extends AbstractCronJob {

  // private static final String DATE_FORMAT = null;
@Autowired
   ScnLyingListService service;
   @Autowired
   ReportMailService  reportMailService;
   final String DATE_FORMAT = "yyyy-MM-dd";
   private static final String MAIL_BODY = "Attached File: SCNX_REPORT";
   private static final Logger logger = LoggerFactory.getLogger(EAWBShipmentDiscrepancyReportJob.class);
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      //method will take care of sending mail with report  
      super.executeInternal(jobExecutionContext);
      LocalDateTime yesterdayTime = null;
      LocalDateTime todayTime = null;
      
      final String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
      
      final String MailSub = "ddMMMyyyy";
      LocalDateTime currentUTCTime = LocalDateTime.now();
      LocalDateTime currentZoneTime = TenantZoneTime.getZoneDateTime(currentUTCTime,TenantContext.get().getTenantId());
      List <ScnLyingListParentModel> responseData = new ArrayList<>();
  
      try {
         responseData = service.getShipemntInformation();
         
      } catch (CustomException e) {
         e.printStackTrace();
      }
      if(!CollectionUtils.isEmpty(responseData)) {
            yesterdayTime = LocalDateTime.of(currentZoneTime.toLocalDate().minusDays(1),LocalTime.of(00,00));//.ofTimeatTime(8, 20).minusDays(1);
            todayTime = LocalDateTime.of(currentZoneTime.toLocalDate().minusDays(1),LocalTime.of(23, 59));
            logger.info("yesterdaytime", yesterdayTime);
            logger.info("todayTime", todayTime);
         String fromDate = yesterdayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
         String toDate = todayTime.format(DateTimeFormatter.ofPattern(TIMEFORMAT));
         for(ScnLyingListParentModel value: responseData) {
            List<String> emailAddresses = null;
            try {
               emailAddresses  = service.getEmailAddresses(value.getCarrierCode());
               logger.info("emailAddresses", emailAddresses);
            } catch (CustomException e) {
               e.printStackTrace();
            }
            Map<String, Object> reportParams = new HashMap<>();
            reportParams.put("carrierCode", value.getCarrierCode());
            reportParams.put("fromDate", fromDate);
            reportParams.put("toDate", toDate);
          
            List<ReportMailPayload> reportPayload = new ArrayList<>();
            ReportMailPayload reportMailPayload = new ReportMailPayload();
            reportMailPayload.setReportName("SCN_LYING_LIST_report");
            reportMailPayload.setFileName(shiftFileNameFormat("SCNX",currentZoneTime));
            reportMailPayload.setReportFormat(ReportFormat.CSV);
            reportMailPayload.setReportParams(reportParams);
            logger.info("reportParams for scn", reportParams);
            EMailEvent emailEvent = new EMailEvent();
            if(!CollectionUtils.isEmpty(emailAddresses)) {
               emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
               emailEvent.setNotifyAddress(null);
               emailEvent.setMailSubject("SCN List" +" "+ value.getCarrierCode()+ " " + currentZoneTime.toLocalDate().format(DateTimeFormatter.ofPattern(MailSub))+ "-" + "[DO NOT REPLY]");
               emailEvent.setMailBody(MAIL_BODY);
               reportPayload.add(reportMailPayload);
               reportMailService.sendReport(reportPayload, emailEvent);
            }
            
         } 
      }
      
      

   }
   
   private String dateFormat(LocalDateTime currentZoneTime) {
	      return currentZoneTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).toUpperCase();
	   }
	   
   private String shiftFileNameFormat(String reportName, LocalDateTime currentZoneTime) {
	      return reportName + "_" + dateFormat(currentZoneTime);
	   }
}
