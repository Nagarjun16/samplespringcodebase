package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.application.service.UpdateInProcessForAwbNumberStockService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class UpdateInProcessForAwbNumberStock extends AbstractCronJob {
	
	@Autowired
	private UpdateInProcessForAwbNumberStockService updateInProcessForAwbNumberStockService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
	 * JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException{
		 
		try {
			updateInProcessForAwbNumberStockService.UpdateInProcessForAwbNumberStock();
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}


}
