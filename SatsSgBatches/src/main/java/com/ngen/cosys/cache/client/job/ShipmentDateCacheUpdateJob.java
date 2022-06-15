/**
 * {@link ShipmentDateCacheUpdateJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.cache.client.job;

import java.time.LocalDateTime;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.cache.client.common.CacheClientConstants;
import com.ngen.cosys.cache.client.service.CacheUpdateService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

/**
 * Shipment Date cache update Job to update/remove cached memory data
 * 
 * It removes the data once shipment is completed any one of the following cycle 
 * Reject, Return, Depart or Delivery
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = CacheClientConstants.SHIPMENT_DATE_CACHE_UPDATE)
public class ShipmentDateCacheUpdateJob extends AbstractCronJob {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentDateCacheUpdateJob.class);
   
   @Autowired
   CacheUpdateService cacheUpdateService;
   
   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      LOGGER.info("Shipment Date Cache Update Process Initiated - {}");
      LocalDateTime startTime = LocalDateTime.now();
      try {
         cacheUpdateService.shipmentDateCacheRefresh();
      } catch (Exception ex) {
         LOGGER.error("Shipment Date Cache update job exception occurred - {}", ex);
      }
      LOGGER.info("Shipment Date Cache Update Process Completed :: START TIME - {}, END TIME - {}", startTime,
            LocalDateTime.now());
   }
   
}
