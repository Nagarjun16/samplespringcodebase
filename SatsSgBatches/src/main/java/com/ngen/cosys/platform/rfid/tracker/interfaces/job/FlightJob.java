package com.ngen.cosys.platform.rfid.tracker.interfaces.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.platform.rfid.tracker.interfaces.delegate.FlightDelegate;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

@DisallowConcurrentExecution
public class FlightJob extends AbstractCronJob {

	private static final Logger LOG = LoggerFactory.getLogger(FlightJob.class);

	@Autowired
	private FlightDelegate flightDelegate;

	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		super.executeInternal(jobExecutionContext);
		LOG.info(new Date() + "=== " + "FlightJob===========================getFlights()");
		flightDelegate.getFlights();
	}

}
