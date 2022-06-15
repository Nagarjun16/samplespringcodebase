/**
 * Listener class for intercepting calls scheduler instantiation/invocation
 */
package com.ngen.cosys.scheduler.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.scheduler.service.JobService;

@Component
public class ApplicationSchedulerListener implements SchedulerListener {

	@Autowired
	JobService jobService;

	@Override
	public void jobAdded(JobDetail arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void jobDeleted(JobKey arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void jobPaused(JobKey arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void jobResumed(JobKey arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void jobScheduled(Trigger arg0) {
		// No implementation if required need to add functionality in this method

	}

	@Override
	public void jobUnscheduled(TriggerKey arg0) {
		// No implementation if required need to add functionality in this method

	}

	@Override
	public void jobsPaused(String arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void jobsResumed(String arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void schedulerError(String arg0, SchedulerException arg1) {
		// No implementation if required need to add functionality in this method

	}

	@Override
	public void schedulerInStandbyMode() {
		// No implementation if required need to add functionality in this method

	}

	@Override
	public void schedulerShutdown() {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void schedulerShuttingdown() {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void schedulerStarted() {
		// Load existing jobs from application specific batch job configuration
		// jobService.setupJobs();
	}

	@Override
	public void schedulerStarting() {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void schedulingDataCleared() {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void triggerFinalized(Trigger arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void triggerPaused(TriggerKey arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void triggerResumed(TriggerKey arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void triggersPaused(String arg0) {
		// No implementation if required need to add functionality in this method
	}

	@Override
	public void triggersResumed(String arg0) {
		// No implementation if required need to add functionality in this method
	}

}
