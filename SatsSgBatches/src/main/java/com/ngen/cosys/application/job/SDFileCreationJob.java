package com.ngen.cosys.application.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ngen.cosys.billing.sap.controller.SalesAndDistributionBillingController;
import com.ngen.cosys.billing.sap.service.SapOutFileProcessor;
import com.ngen.cosys.billing.sap.service.SapOutboundFileProcessor;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class SDFileCreationJob extends AbstractCronJob {

   @Autowired
   @Qualifier("salesAndDistributionBillingDetailsProcessImpl")
   private SapOutboundFileProcessor sapOutboundFileProcessor;

   private static Logger logger = LoggerFactory.getLogger(SDFileCreationJob.class);

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

      logger.info("Inside into SDFileCreationJob runing....................");

      super.executeInternal(jobExecutionContext);
      try {

         sapOutboundFileProcessor.createFile();

      } catch (CustomException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

}
