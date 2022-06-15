package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.billing.chargepost.service.ChargePostService;
import com.ngen.cosys.billing.sap.controller.SapInvoiceController;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ChargePostSDFileJob extends AbstractCronJob {

   @Autowired
   private ChargePostService chargePostService;

   private static Logger logger = LoggerFactory.getLogger(ChargePostSDFileJob.class);

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      logger.info("Inside into ChargePostSDFileJob runing....................");
      super.executeInternal(jobExecutionContext);
      try {
         chargePostService.getGenerateCustomerSDBill();
      } catch (CustomException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }
}
