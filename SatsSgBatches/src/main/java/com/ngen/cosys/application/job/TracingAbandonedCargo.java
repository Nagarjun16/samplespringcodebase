package com.ngen.cosys.application.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.application.model.TracingShipmentModel;
import com.ngen.cosys.application.service.TracingBatchJobService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class TracingAbandonedCargo extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(TracingAbandonedCargo.class);

   @Autowired
   private TracingBatchJobService service;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
    * JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         LOGGER.info("TracingAbandonedCargo =========================== IN TRY BLOCK");
         List<TracingShipmentModel> undeliveredShipments = this.service.getUndeliverdShipments();
         if (!CollectionUtils.isEmpty(undeliveredShipments)) {
            for (TracingShipmentModel t : undeliveredShipments) {
               this.service.createTracing(t);
            }
         }
         LOGGER.info("TracingAbandonedCargo =========================== After Service");
      } catch (CustomException ex) {
         LOGGER.error("Unable to abandoned shipment", ex);
      }
   }

}