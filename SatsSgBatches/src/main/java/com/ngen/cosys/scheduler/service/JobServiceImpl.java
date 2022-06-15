/**
 * This is a service class which has methods to control Quartz Jobs/Triggers
 * 
 * @author NIIT Technologies Pvt Ltd
 */
package com.ngen.cosys.scheduler.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.job.CosysJob;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.scheduler.job.AbstractSimpleJob;
import com.ngen.cosys.scheduler.model.BatchJobDataMapModel;
import com.ngen.cosys.scheduler.model.BatchJobModel;
import com.ngen.cosys.scheduler.repository.BatchJobRepository;

@Service
public class JobServiceImpl implements JobService {

	private static final Logger lOGGER = LoggerFactory.getLogger(JobService.class);

	@Autowired
	private ApplicationContext context;

	@Autowired
	private BatchJobRepository batchJobRepository;

	private Map<String, SchedulerFactoryBean> schedulerMap = new HashMap<>();

	/**
	 * Gets Scheduler
	 * 
	 * @param tenantId
	 *            Tenant Id
	 * @return Scheduler
	 */
	private Scheduler getScheduler(String tenantId) {
		SchedulerFactoryBean factory = schedulerMap.get(tenantId);
		//
		return Objects.nonNull(factory) ? factory.getScheduler() : null;
	}

	/**
	 * Get All Jobs (Execute it in New Transaction)
	 */
	@Override
	public List<BatchJobModel> getTenantJobs() {
		try {
			return this.batchJobRepository.getJobs();
		} catch (CustomException e) {
			lOGGER.error("Exception Loading All Jobs for Tenant", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.service.JobService#setupJobs()
	 */
	@Override
	public void setupJobs(String tenantId, SchedulerFactoryBean schedulerFactory, List<BatchJobModel> batchJobs) {
		try {
			schedulerMap.put(tenantId, schedulerFactory);
			// Load batch jobs configured in App_ScheduledBatches table
			for (BatchJobModel job : batchJobs) {
				String jobName = String.format("%s.%s", tenantId, job.getJobName());
				String jobGroup = String.format("%s.%s", tenantId, job.getJobGroup());
				//
				if (job.getActive()) {
					//
					if (StringUtils.isEmpty(job.getCronExpression())) {
						AbstractSimpleJob simpleJob = (AbstractSimpleJob) Class.forName(job.getJobClazz())
								.newInstance();
						this.scheduleOneTimeJob(jobName, jobGroup, simpleJob.getClass(), LocalDateTime.now(),
								job.getJobDataMap(), tenantId);
					} else {
						AbstractCronJob cronJob = (AbstractCronJob) Class.forName(job.getJobClazz()).newInstance();
						this.scheduleCronJob(jobName, jobGroup, cronJob.getClass(), LocalDateTime.now(),
								job.getCronExpression(), job.getJobDataMap(), tenantId);
					}
				} else {
					// If it is not active then unschedule the batch job
					this.unScheduleJob(jobName, jobGroup, tenantId);
				}
			}
		} catch (Exception e) {
			lOGGER.error("Exception while loading existing batch jobs", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.scheduler.service.JobService#scheduleOneTimeJob(java.lang.
	 * String, java.lang.String, java.lang.Class, java.time.LocalDateTime,
	 * java.util.List)
	 */
	@Override
	public boolean scheduleOneTimeJob(String jobName, String groupKey, Class<? extends CosysJob> jobClass,
			LocalDateTime scheduleTime, List<BatchJobDataMapModel> jobDataMap, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received to scheduleJob");
		}

		if (!this.isJobWithNamePresent(jobName, groupKey, tenantId)) {
			// Create JobDataMap
			JobDataMap quartzJobDataMap = new JobDataMap();
			if (!CollectionUtils.isEmpty(jobDataMap)) {
				for (BatchJobDataMapModel t : jobDataMap) {
					quartzJobDataMap.put(t.getKey(), t.getValue());
				}
			}

			JobDetail jobDetail = JobUtil.createJob(jobClass, false, context, jobName, groupKey, quartzJobDataMap);

			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug("creating trigger for key :" + jobName + " at date :" + scheduleTime);
			}

			Trigger cronTriggerBean = JobUtil.createSingleTrigger(jobName, groupKey, scheduleTime,
					SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);

			try {
				Scheduler scheduler = getScheduler(tenantId);
				Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);

				if (lOGGER.isDebugEnabled()) {
					lOGGER.debug("Job with key jobKey :" + jobName + " and group :" + groupKey
							+ " scheduled successfully for date :" + dt);
				}

				return true;
			} catch (SchedulerException e) {
				if (lOGGER.isErrorEnabled()) {
					lOGGER.error("SchedulerException while scheduling job with key :" + jobName + " message :", e);
				}
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.scheduler.service.JobService#scheduleCronJob(java.lang.String,
	 * java.lang.String, java.lang.Class, java.time.LocalDateTime, java.lang.String,
	 * java.util.List)
	 */
	@Override
	public boolean scheduleCronJob(String jobName, String groupKey, Class<? extends CosysJob> jobClass,
			LocalDateTime date, String cronExpression, List<BatchJobDataMapModel> jobDataMap, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received to scheduleJob");
		}

		if (!this.isJobWithNamePresent(jobName, groupKey, tenantId)) {

			// Create JobDataMap
			JobDataMap quartzJobDataMap = new JobDataMap();
			if (!CollectionUtils.isEmpty(jobDataMap)) {
				for (BatchJobDataMapModel t : jobDataMap) {
					quartzJobDataMap.put(t.getKey(), t.getValue());
				}
			}

			JobDetail jobDetail = JobUtil.createJob(jobClass, false, context, jobName, groupKey, quartzJobDataMap);

			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug("creating trigger for key :" + jobName + " at date :" + date);
			}

			Trigger cronTriggerBean = JobUtil.createCronTrigger(jobName, groupKey, date, cronExpression,
					CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);

			try {
				Scheduler scheduler = getScheduler(tenantId);
				Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);

				if (lOGGER.isDebugEnabled()) {
					lOGGER.debug("Job with key jobKey :" + jobName + " and group :" + groupKey
							+ " scheduled successfully for date :" + dt);
				}

				return true;
			} catch (SchedulerException e) {
				if (lOGGER.isErrorEnabled()) {
					lOGGER.error("SchedulerException while scheduling job with key :" + jobName + " message :", e);
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.service.JobService#updateOneTimeJob(java.lang.
	 * String, java.time.LocalDateTime)
	 */
	@Override
	public boolean updateOneTimeJob(String jobName, String groupKey, LocalDateTime scheduleTime, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received for updating one time job.");
		}

		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug(
					"Parameters received for updating one time job : jobKey :" + jobName + ", date: " + scheduleTime);
		}

		try {
			Trigger newTrigger = JobUtil.createSingleTrigger(jobName, groupKey, scheduleTime,
					SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

			Date dt = getScheduler(tenantId).rescheduleJob(TriggerKey.triggerKey(jobName), newTrigger);

			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug(
						"Trigger associated with jobKey :" + jobName + " rescheduled successfully for date :" + dt);
			}

			return true;
		} catch (Exception e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while updating one time job with key :" + jobName + " message :", e);
			}

			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.scheduler.service.JobService#updateCronJob(java.lang.String,
	 * java.lang.String, java.time.LocalDateTime, java.lang.String)
	 */
	@Override
	public boolean updateCronJob(String jobName, String groupKey, LocalDateTime scheduleTime, String cronExpression,
			String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received for updating cron job.");
		}

		String jobKey = jobName;

		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Parameters received for updating cron job : jobKey :" + jobKey + ", date: " + scheduleTime);
		}

		try {
			Trigger newTrigger = JobUtil.createCronTrigger(jobKey, groupKey, scheduleTime, cronExpression,
					CronTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);

			Date dt = getScheduler(tenantId).rescheduleJob(TriggerKey.triggerKey(jobKey), newTrigger);

			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug("Trigger associated with jobKey :" + jobKey + " rescheduled successfully for date :" + dt);
			}
			return true;
		} catch (Exception e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while updating cron job with key :" + jobKey + " message :", e);
			}
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.scheduler.service.JobService#unScheduleJob(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean unScheduleJob(String jobName, String jobGroup, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received for Unscheduleding job.");
		}
		TriggerKey tkey = new TriggerKey(jobName, jobGroup);
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Parameters received for unscheduling job : tkey :" + jobName);
		}
		try {
			boolean status = getScheduler(tenantId).unscheduleJob(tkey);
			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug("Trigger associated with jobKey :" + jobName + " unscheduled with status :" + status);
			}
			return status;
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while unscheduling job with key :" + jobName + " message :", e);
			}
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.service.JobService#deleteJob(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean deleteJob(String jobName, String jobGroup, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received for deleting job.");
		}
		JobKey jkey = new JobKey(jobName, jobGroup);
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Parameters received for deleting job : jobKey :" + jobName);
		}
		try {
			boolean status = getScheduler(tenantId).deleteJob(jkey);

			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug("Job with jobKey :" + jobName + " deleted with status :" + status);
			}
			return status;
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while deleting job with key :" + jobName + " message :", e);
			}
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.service.JobService#pauseJob(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean pauseJob(String jobName, String jobGroup, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received for pausing job.");
		}
		JobKey jkey = new JobKey(jobName, jobGroup);

		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Parameters received for pausing job : jobKey :" + jobName);
		}
		try {
			getScheduler(tenantId).pauseJob(jkey);

			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug("Job with jobKey :" + jobName + " paused succesfully.");
			}
			return true;
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while pausing job with key :" + jobName + " message :", e);
			}
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.service.JobService#resumeJob(java.lang.String)
	 */
	@Override
	public boolean resumeJob(String jobName, String jobGroup, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received for resuming job.");
		}
		JobKey jKey = new JobKey(jobName, jobGroup);
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Parameters received for resuming job : jobKey :" + jobName);
		}
		try {
			getScheduler(tenantId).resumeJob(jKey);
			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug("Job with jobKey :" + jobName + " resumed succesfully.");
			}

			return true;
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while resuming job with key :" + jobName + " message :", e);
			}
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.scheduler.service.JobService#startJobNow(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean startJobNow(String jobName, String jobGroup, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received for starting job now.");
		}
		JobKey jKey = new JobKey(jobName, jobGroup);
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Parameters received for starting job now : jobKey :" + jobName);
		}
		try {
			getScheduler(tenantId).triggerJob(jKey);

			if (lOGGER.isDebugEnabled()) {
				lOGGER.debug("Job with jobKey :" + jobName + " started now succesfully.");
			}
			return true;
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while starting job now with key :" + jobName + " message :", e);
			}
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.scheduler.service.JobService#isJobRunning(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean isJobRunning(String jobName, String jobGroup, String tenantId) {
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Request received to check if job is running");
		}
		if (lOGGER.isDebugEnabled()) {
			lOGGER.debug("Parameters received for checking job is running now : jobKey :" + jobName);
		}
		try {
			List<JobExecutionContext> currentJobs = getScheduler(tenantId).getCurrentlyExecutingJobs();
			if (currentJobs != null) {
				for (JobExecutionContext jobCtx : currentJobs) {
					String jobNameDB = jobCtx.getJobDetail().getKey().getName();
					if (jobName.equalsIgnoreCase(jobNameDB)) {
						return true;
					}
				}
			}
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error(
						"SchedulerException while checking job with key :" + jobName + " is running. error message :",
						e);
			}
			return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.service.JobService#getAllJobs()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BatchJobModel> getAllJobs(String tenantId) {
		List<BatchJobModel> list = new ArrayList<>();
		try {
			Scheduler scheduler = getScheduler(tenantId);
			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

					String jobName = jobKey.getName();
					String jobGroup = jobKey.getGroup();

					// get job's trigger
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
					Date scheduleTime = triggers.get(0).getStartTime();
					Date nextFireTime = triggers.get(0).getNextFireTime();
					Date lastFiredTime = triggers.get(0).getPreviousFireTime();
					//
					LocalDateTime dateTime = null;
					//
					BatchJobModel batchJobModel = new BatchJobModel();
					batchJobModel.setJobName(jobName);
					batchJobModel.setJobGroup(jobGroup);
					batchJobModel.setTenantId(tenantId);
					dateTime = TenantTimeZoneUtility
							.fromDatabaseToTenantDateTime(TenantTimeZoneUtility.toDbLocalDateTime(scheduleTime));
					batchJobModel.setJobScheduleTime(dateTime);
					dateTime = TenantTimeZoneUtility
							.fromDatabaseToTenantDateTime(TenantTimeZoneUtility.toDbLocalDateTime(lastFiredTime));
					batchJobModel.setJobLastFireTime(dateTime);
					dateTime = TenantTimeZoneUtility
							.fromDatabaseToTenantDateTime(TenantTimeZoneUtility.toDbLocalDateTime(nextFireTime));
					batchJobModel.setJobNextFireTime(dateTime);
					
					if(TenantTimeZoneUtility.now().isAfter(batchJobModel.getJobNextFireTime().plusMinutes(2))) {
						batchJobModel.setJobNotRunningFlag(1);
					}
					//
					if (isJobRunning(jobName, jobGroup, tenantId)) {
						batchJobModel.setJobStatus("RUNNING");
					} else {
						String jobState = getJobState(jobName, jobGroup, tenantId);
						batchJobModel.setJobStatus(jobState);
					}
					list.add(batchJobModel);
					if (lOGGER.isDebugEnabled()) {
						lOGGER.debug("Job details:");
						lOGGER.debug("Job Name:" + jobName + ", Group Name:" + groupName + ", Schedule Time:"
								+ scheduleTime);
					}
				}
			}
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while fetching all jobs. error message :", e);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.scheduler.service.JobService#isJobWithNamePresent(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public boolean isJobWithNamePresent(String jobName, String jobGroup, String tenantId) {
		try {
			Scheduler scheduler = getScheduler(tenantId);
			if (!ObjectUtils.isEmpty(scheduler)) {
				for (String groupName : scheduler.getJobGroupNames()) {
					for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
						if (jobKey.getName().equalsIgnoreCase(jobName)) {
							return true;
						}
					}
				}
			}
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while checking job with name and group exist:", e);
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.scheduler.service.JobService#getJobState(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String getJobState(String jobName, String jobGroup, String tenantId) {
		try {
			JobKey jobKey = new JobKey(jobName, jobGroup);

			Scheduler scheduler = getScheduler(tenantId);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);

			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
			if (!CollectionUtils.isEmpty(triggers)) {
				for (Trigger trigger : triggers) {
					TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					if (TriggerState.PAUSED.equals(triggerState)) {
						return "PAUSED";
					} else if (TriggerState.BLOCKED.equals(triggerState)) {
						return "BLOCKED";
					} else if (TriggerState.COMPLETE.equals(triggerState)) {
						return "COMPLETE";
					} else if (TriggerState.ERROR.equals(triggerState)) {
						return "ERROR";
					} else if (TriggerState.NONE.equals(triggerState)) {
						return "NONE";
					} else if (TriggerState.NORMAL.equals(triggerState)) {
						return "SCHEDULED";
					}
				}
			}
		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while checking job with name and group exist:", e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.service.JobService#stopJob(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean stopJob(String jobName, String jobGroup, String tenantId) {
		lOGGER.debug("JobServiceImpl.stopJob()");
		try {
			Scheduler scheduler = getScheduler(tenantId);
			JobKey jkey = new JobKey(jobName, jobGroup);

			return scheduler.interrupt(jkey);

		} catch (SchedulerException e) {
			if (lOGGER.isErrorEnabled()) {
				lOGGER.error("SchedulerException while stopping job. error message :", e);
			}
		}
		return false;
	}

	/**
	 * GET Job Next Fire Time
	 * 
	 * @see com.ngen.cosys.scheduler.service.JobService#getJobNextFireTime(java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LocalDateTime getJobNextFireTime(String jobName, String jobGroup, String tenantId) {
		lOGGER.warn("Job Service :: GET Job Next Fire Time - JobName - {}, Group - {}", jobName, jobGroup);
		if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroup)) {
			return null;
		}
		JobKey jobKey = new JobKey(jobName, jobGroup);
		Scheduler scheduler = getScheduler(tenantId);
		LocalDateTime zoneDateTime = null;
		//
		try {
			List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
			if (!CollectionUtils.isEmpty(triggers)) {
				for (Trigger trigger : triggers) {
					Date nextFireTime = trigger.getNextFireTime();
					//
					zoneDateTime = TenantTimeZoneUtility
							.fromDatabaseToTenantDateTime(TenantTimeZoneUtility.toDbLocalDateTime(nextFireTime));
				}
			}
		} catch (Exception ex) {
			lOGGER.error(
					"Job Service :: GET Job Next Fire Time Exception - JobName - {}, Group - {}, Exception StackTrace - {}",
					jobName, jobGroup, ex);
		}
		return zoneDateTime;
	}

	@Override
	public BatchJobModel cleanUpJob(BatchJobModel requestModel) {
		lOGGER.warn("Job Service :: Cleanup or Restart", requestModel.getJobName());
		try {
			this.batchJobRepository.cleanUpJob(requestModel);
		} catch (CustomException e) {
			lOGGER.error("Exception while Job Cleanup or Restart", e);
		}
		return requestModel;
	}

	@Override
	public BatchJobModel reinitiateMessages(BatchJobModel requestModel) {
		lOGGER.warn("Service :: reinitiateMessages ", requestModel.getMessages());
		try {
			requestModel.setDatetoUpdate(requestModel.getDatetoUpdate().minusMinutes(requestModel.getDiffInMins()));
			this.batchJobRepository.reinitiateMessages(requestModel);
		} catch (CustomException e) {
			lOGGER.error("Exception while Reinitiating messages", e);
		}
		return requestModel;
	}
}