package com.ngen.cosys.platform.rfid.tracker.feeder.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.feeder.service.FeederService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

@DisallowConcurrentExecution
public class FeederJob extends AbstractCronJob {

	private static final Logger LOG = LoggerFactory.getLogger(FeederJob.class);

	@Autowired
	FeederService feederService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		super.executeInternal(jobExecutionContext);
		LOG.info(new Date() + "=== " + "pushTrackingFeeds===========================pushTrackingFeeds()");
		try {
			feederService.pushTrackingFeeds();
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
