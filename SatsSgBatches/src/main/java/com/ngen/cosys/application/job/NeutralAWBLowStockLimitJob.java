package com.ngen.cosys.application.job;

import java.time.LocalDateTime;
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

import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.NonAWBLowStockLimitModel;
import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.NonAWBLowStockLimitParentModel;
import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Service.EAWBShipmentDiscrepancyReportService;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.mail.service.ReportMailService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportMailPayload;

public class NeutralAWBLowStockLimitJob extends AbstractCronJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(NeutralAWBLowStockLimitJob.class);
//   600000
   private static final long SLEEP_TIME = 10000;
   @Autowired
   private EAWBShipmentDiscrepancyReportService service;
   @Autowired
   ReportMailService  reportMailService;

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      // method will take care of sending mail with report
	   LOGGER.info("Neutral AWB Low Stock Limit Job :: Started");
      super.executeInternal(jobExecutionContext);
      List<String> emailAddresses = new ArrayList<>(); 
      List<NonAWBLowStockLimitParentModel> nonAWBStockLimitData = new ArrayList<>();
      try {
         nonAWBStockLimitData = service.getShipemntInformationAwbStockLimit();
      } catch (CustomException e) {
         e.printStackTrace();
      }      
      
      if(!CollectionUtils.isEmpty(nonAWBStockLimitData)) {
         for(NonAWBLowStockLimitParentModel value: nonAWBStockLimitData) {
            for(NonAWBLowStockLimitModel value2: value.getAwbLists()) {
                  try {
                     try {
                        emailAddresses = service.getEmailAddressesForNEAWBLowStock(value2.getCarriercode());
                        if(!CollectionUtils.isEmpty(emailAddresses)) {
                        Map<String, Object> reportParams = new HashMap<>();
                        reportParams.put("carrierCode", value2.getCarriercode());
                        List<ReportMailPayload> reportPayload = new ArrayList<>();
                        ReportMailPayload reportMailPayload = new ReportMailPayload();
                        reportMailPayload.setReportName("stocklimit_report");
                        reportMailPayload.setReportFormat(ReportFormat.XLS);
                        reportMailPayload.setReportParams(reportParams);
                        EMailEvent emailEvent = new EMailEvent();
                        emailEvent.setMailToAddress(emailAddresses.toArray(new String[emailAddresses.size()]));
                        emailEvent.setNotifyAddress(null);
                        emailEvent.setMailSubject("Neutral AWB Low Stock Limit " + "-" + LocalDateTime.now() + "-" + "[DO NOT REPLY]");
                        emailEvent.setMailBody("PFA for Neutral AWB Low Stock Limit  report");
                        reportPayload.add(reportMailPayload);
                        reportMailService.sendReport(reportPayload, emailEvent);
                        LOGGER.info("Neutral AWB Low Stock Limit Job :: Report sent successfully");
                        }
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
}
