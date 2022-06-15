package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.ndf.service.NdfMessageJobService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class NDFMessageJob extends AbstractCronJob {
	@Autowired
	NdfMessageJobService service;
	private static final Logger LOGGER = LoggerFactory.getLogger(NDFMessageJob.class);

	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		LOGGER.warn("Inside the NdfMessageJob ",jobExecutionContext);
		
			try {
				LOGGER.warn("calling Service for Ndf message ",jobExecutionContext);
				service.getNdfMessageDefinition();
			}
			
		 catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
