/**
 * Batch Job which runs to re-fire missing FSU messages for import shipments
 */
package com.ngen.cosys.message.resend.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.service.IncomingShipmentsMissingFSUMessageSendService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

@Component
public class IncomingShipmentsMissingFSUMessageSendJob extends AbstractCronJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(IncomingShipmentsMissingFSUMessageSendJob.class);

	@Autowired
	private IncomingShipmentsMissingFSUMessageSendService service;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
	 * JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			refireMessage();
		} catch (Exception ex) {
			LOGGER.error("IncomingShipmentsMissingFSUMessageSendJob Process exception occurred - {}", ex);
		}
	}

	/**
	 * Method to fire message for import shipments
	 * 
	 * @throws CustomException
	 */
	public void refireMessage() throws CustomException {

		// RCF/NFD
		this.service.refireRCFNFDMissingShipments();

		// AWD
		this.service.refireAWDMissingShipments();

		// DLV
		this.service.refireDLVMissingShipments();

	}

}