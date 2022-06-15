/**
 * This is a job class which is used for creating delivery request for shipments
 * which has been created from Agent Portal - EDelivery Request
 */
package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.AutoCreateDeliveryRequestService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class AutoCreateDeliveryRequestJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(AutoCreateDeliveryRequestJob.class);

   @Autowired
   private AutoCreateDeliveryRequestService service;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
    * JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         this.service.scheduleDeliveryRequests();
      } catch (CustomException ex) {
         LOGGER.error("Unable to Create Delivery Request for EDelivery", ex);
      }
   }

}