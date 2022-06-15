package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.PrelodgeServiceListService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

import io.swagger.annotations.ApiOperation;

public class PrelodgeServiceListJob extends AbstractCronJob {
	
	   private static final Logger LOG = LoggerFactory.getLogger(PrelodgeServiceListJob.class);

	   @Autowired
	   private PrelodgeServiceListService service;

	   /**
	    * Method to delete Service Numbers from Service List
	    * 
	    * @throws CustomException
	    */
	   @ApiOperation("API to delete Service Numbers from Service List")
	   //@RequestMapping(value = "/api/satssgbatches/dummy/deleteFromServicelist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		   try {
			   this.service.deleteFromServiceList();
		   	} catch (CustomException ex) {
		          LOG.error("Unable to delete Service List", ex);
		    }
	   }
}
