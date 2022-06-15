/**
 * This is a batch job component for auto expiring SID
 * 
 * @author NIIT Technologies Pvt. Ltd
 */
package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.AutoExpireSIDService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class AutoExpireSIDJob extends AbstractCronJob {

   private static final Logger LOG = LoggerFactory.getLogger(AutoExpireSIDJob.class);

   @Autowired
   AutoExpireSIDService service;

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         this.service.expireSID();
      } catch (CustomException ex) {
         LOG.error("Unable to expire SID for Shipment", ex);
      }
   }

}