package com.ngen.cosys.platform.rfid.tracker.interfaces.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.platform.rfid.tracker.interfaces.delegate.ImportFFMJobDelegate;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

@DisallowConcurrentExecution
public class ImportFFMJob extends AbstractCronJob {

	private static final Logger LOG = LoggerFactory.getLogger(ImportFFMJob.class);

	@Autowired
	private ImportFFMJobDelegate importFFMJobDelegate;

	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		super.executeInternal(jobExecutionContext);
		LOG.info(new Date() + "=== " + "ImportFFMJob===========================getImportFFM()");
		importFFMJobDelegate.getImportFFM();
	}

}
