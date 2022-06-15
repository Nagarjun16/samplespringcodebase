package com.ngen.cosys.billing.chargerecalculate.job;

import java.time.LocalDateTime;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.billing.chargerecalculate.service.ChargeRecalculateService;
import com.ngen.cosys.billing.sap.controller.SapInvoiceController;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ChargeRecalculateJob extends AbstractCronJob {

   @Autowired
   private ChargeRecalculateService chargeRecalculateService;

   private static Logger logger = LoggerFactory.getLogger(SapInvoiceController.class);

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      super.executeInternal(jobExecutionContext);

      logger.info("---Charge Recalculation Triggered---" + LocalDateTime.now());

      try {
         chargeRecalculateService.recalculateCharges();
         logger.info("---Charge Recalculation Completed---" + LocalDateTime.now());
      } catch (CustomException err) {
         logger.error("Charge Recalculation Error : " + err);
      }
   }
}
