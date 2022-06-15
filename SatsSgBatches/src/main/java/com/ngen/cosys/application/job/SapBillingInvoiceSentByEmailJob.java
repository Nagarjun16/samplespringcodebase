package com.ngen.cosys.application.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.billing.sap.controller.SapInvoiceController;
import com.ngen.cosys.billing.sap.service.SapInvoiceSentByEmailService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class SapBillingInvoiceSentByEmailJob extends AbstractCronJob {

	@Autowired
	private SapInvoiceSentByEmailService sapInvoiceSentByEmailService;

	private static Logger logger = LoggerFactory.getLogger(SapInvoiceController.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("Inside into sapInvoiceAndCreditNoteProcessImpl runing....................");
		super.executeInternal(jobExecutionContext);

		try {

			sapInvoiceSentByEmailService.invoiceSentByEmail();

		} catch (CustomException e) {
			logger.error("Exception", e);
		}

	}
}
