/**
 * Batch Job which runs to re-fire missing FSU messages for export shipments
 */
package com.ngen.cosys.message.resend.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.service.OutgoingShipmentsMissingFSUMessageSendService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

@Component
public class OutgoingShipmentsMissingFSUMessageSendJob extends AbstractCronJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(OutgoingShipmentsMissingFSUMessageSendJob.class);

	@Autowired
	private OutgoingShipmentsMissingFSUMessageSendService service;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			refireMessage();
		} catch (Exception ex) {
			LOGGER.error("OutgoingShipmentsMissingFSUMessageSendJob Process exception occurred - {}", ex);
		}
	}

	/**
	 * Method to fire message for import shipments
	 * 
	 * @throws CustomException
	 */
	public void refireMessage() throws CustomException {

		// RCS
		this.service.refireRCSMissingShipments();

		// MAN
		this.service.refireMANMissingShipments();

		// DEP
		this.service.refireDEPMissingShipments();

	}
}