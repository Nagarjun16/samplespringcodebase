package com.ngen.cosys.application.job;

import java.time.LocalDate;
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
import org.springframework.util.StringUtils;

import com.ngen.cosys.ForeignUld.Model.ForeignUldArrivedOnIncomingFlightModel;
import com.ngen.cosys.ForeignUld.Service.ForeignUldArrivedOnIncomingFlightSrevice;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;

public class ForeignUldArrivedOnIncomingFlightJob extends AbstractCronJob {

   @Autowired
   ForeignUldArrivedOnIncomingFlightSrevice service;

   @Autowired
   ReportMailService reportMailService;

   private static final Logger logger = LoggerFactory.getLogger(EAWBShipmentDiscrepancyReportJob.class);

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      logger.warn("inside foreign uld arrived on incoming flight report job");
      super.executeInternal(jobExecutionContext);
      String responseData = null;
      try {
         responseData = service.getReportOnTheBasisOfCarriercode();
         logger.warn("All the carriers for Foreign ULDs ---->" + responseData);

         if (!StringUtils.isEmpty(responseData)) {
            ForeignUldArrivedOnIncomingFlightModel parameters = service.getParametersForForeignUldsReport();
            logger.warn("From Date" + parameters.getFromDate());

            List<String> emailAddresses = null;
            emailAddresses = service.getEmailAddresses(responseData);
            logger.warn("email address for Foreign ULD " + responseData + " carrier " + ": " + emailAddresses);

            Map<String, Object> reportParams = new HashMap<>();
            reportParams.put("carrierCode", responseData);
            reportParams.put("fromDate", parameters.getFromDate().toString());
            reportParams.put("toDate", parameters.getToDate().toString());
            List<ReportMailPayload> reportPayload = new ArrayList<>();
            ReportMailPayload reportMailPayload = new ReportMailPayload();
            reportMailPayload.setReportName("ForeignUldArrivedOnIncomingFlightReport");
            reportMailPayload.setReportFormat(ReportFormat.CSV);
            reportMailPayload.setReportParams(reportParams);
            EMailEvent emailEvent = new EMailEvent();
            if (!CollectionUtils.isEmpty(emailAddresses)) {
               emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
               emailEvent.setNotifyAddress(null);
               emailEvent.setMailSubject("Foreign Uld Arrived On Incoming Flight Report " + "-" + responseData + "_"
                     + LocalDate.now().format(MultiTenantUtility.getTenantDateFormat()) + "-" + "[DO NOT REPLY]");
               emailEvent.setMailBody("Foreign Uld Arrived On Incoming Flight Report ");
               reportPayload.add(reportMailPayload);
               reportMailService.sendReport(reportPayload, emailEvent);
            }

         }
         service.updateLatestFromDateForForeignULD();
      } catch (Exception e) {
         logger.warn(e.getMessage());
      }
   }

}
