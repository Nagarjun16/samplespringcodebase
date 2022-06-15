/**
 * This is a business interface to manage Quartz Jobs
 * 
 * @author NIIT Technologies Pvt Ltd
 */
package com.ngen.cosys.scheduler.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.ngen.cosys.multitenancy.job.CosysJob;
import com.ngen.cosys.scheduler.model.BatchJobDataMapModel;
import com.ngen.cosys.scheduler.model.BatchJobModel;

public interface JobService {

	/**
	 * Get All Jobs
	 */
	List<BatchJobModel> getTenantJobs();

	/**
	 * Method to load jobs.
	 */
	void setupJobs(String tenantId, SchedulerFactoryBean schedulerFactory, List<BatchJobModel> batchJobs);

	/**
	 * Schedule a job by jobName at given date.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @param jobClass.
	 * @param date.
	 * @return boolean.
	 */
	boolean scheduleOneTimeJob(String jobName, String jobGroup, Class<? extends CosysJob> jobClass, LocalDateTime date,
			List<BatchJobDataMapModel> jobDataMap, String tenantId);

	/**
	 * Schedule a job by jobName at given date.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @param jobClass.
	 * @param date.
	 * @param cronExpression.
	 * @return boolean.
	 */
	boolean scheduleCronJob(String jobName, String jobGroup, Class<? extends CosysJob> jobClass, LocalDateTime date,
			String cronExpression, List<BatchJobDataMapModel> jobDataMap, String tenantId);

	/**
	 * Update one time scheduled job.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @param scheduleTime.
	 * @return boolean.
	 */
	boolean updateOneTimeJob(String jobName, String jobGroup, LocalDateTime scheduleTime, String tenantId);

	/**
	 * Update scheduled cron job.
	 * 
	 * @param jobName.
	 * @param date.
	 * @param cronExpression.
	 * @return boolean.
	 */
	boolean updateCronJob(String jobName, String jobGroup, LocalDateTime date, String cronExpression, String tenantId);

	/**
	 * Remove the indicated Trigger from the scheduler. If the related job does not
	 * have any other triggers, and the job is not durable, then the job will also
	 * be deleted.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return boolean.
	 */
	boolean unScheduleJob(String jobName, String jobGroup, String tenantId);

	/**
	 * Delete the identified Job from the Scheduler - and any associated Triggers.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return boolean.
	 */
	boolean deleteJob(String jobName, String jobGroup, String tenantId);

	/**
	 * Pause a job.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return boolean.
	 */
	boolean pauseJob(String jobName, String jobGroup, String tenantId);

	/**
	 * Resume paused job.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return boolean.
	 */
	boolean resumeJob(String jobName, String jobGroup, String tenantId);

	/**
	 * Start a job now.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return boolean.
	 */
	boolean startJobNow(String jobName, String jobGroup, String tenantId);

	/**
	 * Check if job is already running.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return boolean.
	 */
	boolean isJobRunning(String jobName, String jobGroup, String tenantId);

	/**
	 * Get all jobs.
	 * 
	 * @return List<Map<String, Object>>.
	 */
	List<BatchJobModel> getAllJobs(String tenantId);

	/**
	 * Check job exist with given name.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return boolean.
	 */
	boolean isJobWithNamePresent(String jobName, String jobGroup, String tenantId);

	/**
	 * Get the current state of job.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return String.
	 */
	String getJobState(String jobName, String jobGroup, String tenantId);

	/**
	 * Stop a job.
	 * 
	 * @param jobName.
	 * @param jobGroup.
	 * @return boolean.
	 */
	boolean stopJob(String jobName, String jobGroup, String tenantId);

	/**
	 * GET Job Next Fire Time for Display
	 * 
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	LocalDateTime getJobNextFireTime(String jobName, String jobGroup, String tenantId);

	BatchJobModel cleanUpJob(BatchJobModel requestModel);

	BatchJobModel reinitiateMessages(BatchJobModel requestModel);

}