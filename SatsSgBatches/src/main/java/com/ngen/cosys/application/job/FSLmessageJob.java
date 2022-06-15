package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.FSLMessageJobService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class FSLmessageJob extends AbstractCronJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(FSLmessageJob.class);
	@Autowired
	FSLMessageJobService service;
	
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		LOGGER.warn(" FSLMessageJob ",jobExecutionContext);
		
			try {
				LOGGER.warn("calling Service for FSL message ",jobExecutionContext);
				service.getFSLMessageDefinition();
			}
			
		 catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
