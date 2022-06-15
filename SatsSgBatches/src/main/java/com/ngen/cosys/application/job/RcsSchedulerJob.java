package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.RcsSchedulerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;




public class RcsSchedulerJob extends AbstractCronJob{
	
	   private static final Logger LOGGER = LoggerFactory.getLogger(RcsSchedulerJob.class);

	   @Autowired
	   private RcsSchedulerService service;
	   
	   @Override
	   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	      LOGGER.warn("Inside the RcsSchedulerJob ", jobExecutionContext);
	      try {
	         LOGGER.warn("calling Service for RcsSchedulerJob", jobExecutionContext);
	         service.getRcsTriggerFlightList();
	      } catch (CustomException e) {
	         LOGGER.error("Exception while performing RcsSchedulerJob", e);
	      }

	   }

}
