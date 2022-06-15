package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ngen.cosys.billing.sap.service.SapInboundFileProcessor;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class SapCustomerMasterJob extends AbstractCronJob {

   @Autowired
   @Qualifier("sapCustomerMasterProcessImpl")
   private SapInboundFileProcessor sapInboundFileProcessor;

   private static Logger logger = LoggerFactory.getLogger(ChargePostSDFileJob.class);

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      logger.info("SAP Customer Master sync batch started....................");
      super.executeInternal(jobExecutionContext);
      try {
         sapInboundFileProcessor.processFile();
      } catch (Exception e) {
         logger.error("Exception ", e);
      }
   }
}
