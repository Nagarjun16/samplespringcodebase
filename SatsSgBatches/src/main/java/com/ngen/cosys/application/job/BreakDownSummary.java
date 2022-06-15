package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.breakdown.dao.BreakDownSummaryDao;
import com.ngen.cosys.satssg.breakdown.service.BreakDownSummaryService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class BreakDownSummary extends AbstractCronJob {
	
	@Autowired
	private BreakDownSummaryService breakDownSummaryService;
	
	@Autowired
	private BreakDownSummaryDao breakDownSummaryDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
	 * JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		
		try {
			breakDownSummaryService.getFlightDetails();
		} catch (CustomException e) {
			
		}
		
		
	}

}
