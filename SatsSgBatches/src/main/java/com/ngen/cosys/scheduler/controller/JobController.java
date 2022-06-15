/**
 * This is a rest service class which exposes API service to manage Quartz Job
 * and it's associated triggers.
 * 
 * @author NIIT Technologies Pvt Ltd
 */
package com.ngen.cosys.scheduler.controller;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.CosysRestController;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.response.ServerResponseCode;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.scheduler.job.AbstractSimpleJob;
import com.ngen.cosys.scheduler.model.BatchJobModel;
import com.ngen.cosys.scheduler.service.JobService;

@CosysRestController(path = "/api/job/")
public class JobController {

	@Autowired
	@Lazy
	JobService jobService;

	@Autowired
	private BeanFactory beanFactory;

	/**
	 * Method to schedule the Job
	 * 
	 * @param requestModel
	 * @return BaseResponse
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws CustomException
	 */
	@RequestMapping(path = "/schedule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> schedule(@RequestBody BatchJobModel requestModel)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, CustomException {

		// Job Name is mandatory
		if (StringUtils.isEmpty(requestModel.getJobName())) {
			throw new CustomException(ServerResponseCode.JOB_NAME_NOT_PRESENT.getCode(), "",
					ErrorType.ERROR.toString());
		}

		// Check if job Name is unique
		if (!jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId()) && StringUtils.isEmpty(requestModel.getCronExpression())) {
			// Single Trigger
			AbstractSimpleJob simpleJob = (AbstractSimpleJob) Class.forName(requestModel.getJobClazz()).newInstance();
			boolean status = jobService.scheduleOneTimeJob(requestModel.getJobName(), requestModel.getJobGroup(),
					simpleJob.getClass(), requestModel.getJobScheduleTime(), null, requestModel.getTenantId());
			if (status) {
				return getServerResponse(jobService.getAllJobs(requestModel.getTenantId()));
			}
		} else if (!jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId()) && !StringUtils.isEmpty(requestModel.getCronExpression())) {
			// Cron Trigger
			AbstractCronJob cronJob = (AbstractCronJob) Class.forName(requestModel.getJobClazz()).newInstance();
			boolean status = jobService.scheduleCronJob(requestModel.getJobName(), "Cron", cronJob.getClass(),
					requestModel.getJobScheduleTime(), requestModel.getCronExpression(), null,
					requestModel.getTenantId());
			if (status) {
				return getServerResponse(jobService.getAllJobs(requestModel.getTenantId()));
			}
		} else {
			throw new CustomException(ServerResponseCode.JOB_WITH_SAME_NAME_EXIST.getCode(), "",
					ErrorType.ERROR.toString());
		}

		// Return default response
		return getServerResponse(null);
	}

	/**
	 * Method to unschedule a job
	 * 
	 * @param requestModel
	 */
	@RequestMapping(path = "/unschedule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void unschedule(@RequestBody BatchJobModel requestModel) {
		jobService.unScheduleJob(requestModel.getJobName(), requestModel.getJobGroup(), requestModel.getTenantId());
	}

	/**
	 * Method to delete job
	 * 
	 * @param requestModel
	 * @return BaseResponse
	 * @throws CustomException
	 */
	@RequestMapping(path = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> delete(@RequestBody BatchJobModel requestModel) throws CustomException {
		if (jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId())) {
			boolean isJobRunning = jobService.isJobRunning(requestModel.getJobName(), requestModel.getJobGroup(),
					requestModel.getTenantId());

			if (!isJobRunning) {
				boolean status = jobService.deleteJob(requestModel.getJobName(), requestModel.getJobGroup(),
						requestModel.getTenantId());
				if (status) {
					return getServerResponse(jobService.getAllJobs(requestModel.getTenantId()));
				}
			} else {
				throw new CustomException(ServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE.getCode(), "",
						ErrorType.ERROR.toString());
			}
		} else {
			// Job doesn't exist
			throw new CustomException(ServerResponseCode.JOB_DOESNT_EXIST.getCode(), "", ErrorType.ERROR.toString());
		}

		// Return default response
		return getServerResponse(null);
	}

	/**
	 * Method to pause job
	 * 
	 * @param requestModel
	 * @return BaseResponse
	 * @throws CustomException
	 */
	@RequestMapping(path = "/pause", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> pause(@RequestBody BatchJobModel requestModel) throws CustomException {

		if (jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId())) {
			boolean isJobRunning = jobService.isJobRunning(requestModel.getJobName(), requestModel.getJobGroup(),
					requestModel.getTenantId());
			if (!isJobRunning) {
				boolean status = jobService.pauseJob(requestModel.getJobName(), requestModel.getJobGroup(),
						requestModel.getTenantId());
				if (status) {
					return getServerResponse(jobService.getAllJobs(requestModel.getTenantId()));
				}
			} else {
				throw new CustomException(ServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE.getCode(), "",
						ErrorType.ERROR.toString());
			}
		} else {
			// Job doesn't exist
			throw new CustomException(ServerResponseCode.JOB_DOESNT_EXIST.getCode(), "", ErrorType.ERROR.toString());
		}

		// Return default response
		return getServerResponse(null);
	}

	/**
	 * Method to resume job
	 * 
	 * @param requestModel
	 * @return BaseResponse
	 * @throws CustomException
	 */
	@RequestMapping(path = "/resume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> resume(@RequestBody BatchJobModel requestModel) throws CustomException {

		if (jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId())) {
			String jobState = jobService.getJobState(requestModel.getJobName(), requestModel.getJobGroup(),
					requestModel.getTenantId());
			if (jobState.equals("PAUSED")) {
				boolean status = jobService.resumeJob(requestModel.getJobName(), requestModel.getJobGroup(),
						requestModel.getTenantId());
				if (status) {
					return getServerResponse(jobService.getAllJobs(requestModel.getJobGroup()));
				}
			} else {
				throw new CustomException(ServerResponseCode.JOB_NOT_IN_PAUSED_STATE.getCode(), "",
						ErrorType.ERROR.toString());
			}
		} else {
			// Job doesn't exist
			throw new CustomException(ServerResponseCode.JOB_DOESNT_EXIST.getCode(), "", ErrorType.ERROR.toString());
		}

		// Return default response
		return getServerResponse(null);
	}

	/**
	 * Method to update job
	 * 
	 * @param requestModel
	 * @return BaseResponse<Object>
	 * @throws CustomException
	 */
	@RequestMapping(path = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> updateJob(@RequestBody BatchJobModel requestModel) throws CustomException {
		// Job Name is mandatory
		if (StringUtils.isEmpty(requestModel.getJobName())) {
			throw new CustomException(ServerResponseCode.JOB_NAME_NOT_PRESENT.getCode(), "",
					ErrorType.ERROR.toString());
		}

		// Edit Job
		if (jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId()) && StringUtils.isEmpty(requestModel.getCronExpression())) {
			// Single Trigger
			boolean status = jobService.updateOneTimeJob(requestModel.getJobName(), requestModel.getJobGroup(),
					requestModel.getJobScheduleTime(), requestModel.getTenantId());
			if (status) {
				return getServerResponse(jobService.getAllJobs(requestModel.getTenantId()));
			}
		} else if (jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId()) && !StringUtils.isEmpty(requestModel.getCronExpression())) {
			// Cron Trigger
			boolean status = jobService.updateCronJob(requestModel.getJobName(), requestModel.getJobGroup(),
					requestModel.getJobScheduleTime(), requestModel.getCronExpression(), requestModel.getTenantId());
			if (status) {
				return getServerResponse(jobService.getAllJobs(requestModel.getTenantId()));
			}
		} else {
			throw new CustomException(ServerResponseCode.JOB_DOESNT_EXIST.getCode(), "", ErrorType.ERROR.toString());
		}

		// Return default response
		return getServerResponse(null);
	}

	/**
	 * Method to get all jobs
	 * 
	 * @return BaseResponse<Object>
	 */
	@RequestMapping(path = "/jobs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> getAllJobs() {
		List<BatchJobModel> list = jobService.getAllJobs(TenantContext.get().getTenantId());
		return getServerResponse(list);
	}

	/**
	 * Method to check job name
	 * 
	 * @param requestModel
	 * @return BaseResponse<Object>
	 * @throws CustomException
	 */
	@RequestMapping(path = "/checkJobName", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> checkJobName(@RequestBody BatchJobModel requestModel) throws CustomException {
		// Job Name is mandatory
		if (StringUtils.isEmpty(requestModel.getJobName())) {
			throw new CustomException(ServerResponseCode.JOB_NAME_NOT_PRESENT.getCode(), "",
					ErrorType.ERROR.toString());
		}
		boolean status = jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId());
		if (status) {
			throw new CustomException(ServerResponseCode.JOB_WITH_SAME_NAME_EXIST.getCode(), "",
					ErrorType.ERROR.toString());
		}
		// Return default response
		return getServerResponse(null);
	}

	/**
	 * Method to check is job running
	 * 
	 * @param requestModel
	 * @return BaseResponse<Object>
	 */
	@RequestMapping(path = "/isJobRunning", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> isJobRunning(@RequestBody BatchJobModel requestModel) {
		boolean status = jobService.isJobRunning(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId());
		return getServerResponse(status);
	}

	/**
	 * Method to check job state
	 * 
	 * @param requestModel
	 * @return BaseResponse<Object>
	 */
	@RequestMapping(path = "/jobState", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> getJobState(@RequestBody BatchJobModel requestModel) {
		String jobState = jobService.getJobState(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId());
		return getServerResponse(jobState);
	}

	/**
	 * Method to stop job
	 * 
	 * @param requestModel
	 * @return BaseResponse<Object>
	 * @throws CustomException
	 */
	@RequestMapping(path = "/stop", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> stopJob(@RequestBody BatchJobModel requestModel) throws CustomException {
		if (jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId())) {

			if (jobService.isJobRunning(requestModel.getJobName(), requestModel.getJobGroup(),
					requestModel.getTenantId())) {
				boolean status = jobService.stopJob(requestModel.getJobName(), requestModel.getJobGroup(),
						requestModel.getTenantId());
				if (status) {
					return getServerResponse(jobService.getAllJobs(requestModel.getTenantId()));
				}
			} else {
				// Job not in running state
				throw new CustomException(ServerResponseCode.JOB_NOT_IN_RUNNING_STATE.getCode(), "",
						ErrorType.ERROR.toString());
			}

		} else {
			// Job doesn't exist
			throw new CustomException(ServerResponseCode.JOB_DOESNT_EXIST.getCode(), "", ErrorType.ERROR.toString());
		}
		// Return default response
		return getServerResponse(null);
	}

	/**
	 * Method to start job now
	 * 
	 * @param requestModel
	 * @return BaseResponse<Object>
	 * @throws CustomException
	 */
	@RequestMapping(path = "/start", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<Object> startJobNow(@RequestBody BatchJobModel requestModel) throws CustomException {

		if (jobService.isJobWithNamePresent(requestModel.getJobName(), requestModel.getJobGroup(),
				requestModel.getTenantId())) {
			if (!jobService.isJobRunning(requestModel.getJobName(), requestModel.getJobGroup(),
					requestModel.getTenantId())) {
				boolean status = jobService.startJobNow(requestModel.getJobName(), requestModel.getJobGroup(),
						requestModel.getTenantId());
				if (status) {
					// Success
					return getServerResponse(jobService.getAllJobs(requestModel.getTenantId()));
				}
			} else {
				// Job already running
				throw new CustomException(ServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE.getCode(), "",
						ErrorType.ERROR.toString());
			}
		} else {
			// Job doesn't exist
			throw new CustomException(ServerResponseCode.JOB_DOESNT_EXIST.getCode(), "", ErrorType.ERROR.toString());
		}
		// Return default response
		return getServerResponse(null);
	}

	/**
	 * Build server response
	 * 
	 * @param responseCode
	 * @param data
	 * @return BaseResponse<Object>
	 */
	@SuppressWarnings("unchecked")
	public BaseResponse<Object> getServerResponse(Object data) {
		BaseResponse<Object> baseResponse = beanFactory.getBean(BaseResponse.class);
		baseResponse.setData(data);
		return baseResponse;
	}
	
	/**
	 * Method to cleanup/restart a job
	 * 
	 * @param requestModel
	 */
	@RequestMapping(path = "/cleanupJob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<BatchJobModel> cleanup(@RequestBody BatchJobModel requestModel) {
		BaseResponse<BatchJobModel> baseResponse = beanFactory.getBean(BaseResponse.class);
		baseResponse.setData(jobService.cleanUpJob(requestModel));
		return baseResponse;
	}
	
	/**
	 * Method to reinitiate the message
	 * 
	 * @param requestModel
	 */
	@RequestMapping(path = "/reinitiateMessages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<BatchJobModel> reinitiateMessages(@RequestBody BatchJobModel requestModel) {
		BaseResponse<BatchJobModel> baseResponse = beanFactory.getBean(BaseResponse.class);
		baseResponse.setData(jobService.reinitiateMessages(requestModel));
		return baseResponse;
	}

}