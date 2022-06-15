/**
 * Cron Job to trigger events for through transit shipments which are finalized
 */
package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.ThroughTransitShipmentFinalizeService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ThroughTransitShipmentFinalizeJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(ThroughTransitShipmentFinalizeJob.class);

   @Autowired
   private ThroughTransitShipmentFinalizeService service;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
    * JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         this.service.process();
      } catch (CustomException e) {
         LOGGER.error("Exception while sending events for through tranis finalized shipments", e);
      }
   }

}