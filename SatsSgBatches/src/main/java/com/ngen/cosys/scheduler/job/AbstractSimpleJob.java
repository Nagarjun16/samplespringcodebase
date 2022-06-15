package com.ngen.cosys.scheduler.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngen.cosys.multitenancy.job.CosysJob;

@DisallowConcurrentExecution
public abstract class AbstractSimpleJob extends CosysJob {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractSimpleJob.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobKey key = jobExecutionContext.getJobDetail().getKey();

		if (LOG.isDebugEnabled()) {
			LOG.debug("Simple Job started with key :" + key.getName() + ", Group :" + key.getGroup()
					+ " , Thread Name :" + Thread.currentThread().getName() + " ,Time now :" + new Date());
		}

		// *********** For retrieving stored key-value pairs ***********/
		JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Simple Job started with data");
			String[] dataKey = dataMap.getKeys();
			for (String t : dataKey) {
				LOG.debug("Data Key :" + t + " Value : " + dataMap.getString(t));
			}
		}
	}

}