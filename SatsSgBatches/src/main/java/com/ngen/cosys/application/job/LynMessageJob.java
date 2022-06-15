package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.events.listener.LyingListStoreEventListner;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.mesage.lyn.service.LynMessageService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

/**
 * @author Niit Technologies 
 * this class is used for triggering LYN message after
 *  particular time interval
 */
public class LynMessageJob extends AbstractCronJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LynMessageJob.class);
	
	@Autowired
	private LynMessageService service ;
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		LOGGER.warn("Inside the LynMessageJob ",jobExecutionContext);
		try {
			LOGGER.warn("calling Service for Lyn message ",jobExecutionContext);
			service.getLynMessageDefinition();
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
