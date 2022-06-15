/**
 * Listener class for intercepting calls during job execution
 */
package com.ngen.cosys.scheduler.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

@Component
public class JobsListener implements JobListener {

	@Override
	public String getName() {
		return "DEFAULTJOB";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// No implementation if required need to add functionality in this method
	}

}