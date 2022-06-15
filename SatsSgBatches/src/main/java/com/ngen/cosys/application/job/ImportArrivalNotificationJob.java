/**
 * Cron Job for sending import arrival notification
 */
package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.ImportArrivalNotificationService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ImportArrivalNotificationJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(ImportArrivalNotificationJob.class);

   @Autowired
   private ImportArrivalNotificationService service;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
    * JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         this.service.sendNotification();
      } catch (CustomException e) {
         LOGGER.error("Exception while sending import arrival notification for shipments", e);
      }
   }

}