package com.ngen.cosys.application.job;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ngen.cosys.billing.sap.controller.SapInvoiceController;
import com.ngen.cosys.billing.sap.service.SapInboundFileProcessor;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class SapInterfaceInvoiceAndCreditNoteJob extends AbstractCronJob {

	@Autowired
	@Qualifier("sapInvoiceAndCreditNoteProcessImpl")
	private SapInboundFileProcessor sapInboundFileProcessor;

	private static Logger logger = LoggerFactory.getLogger(SapInvoiceController.class);

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		 logger.info("Inside into sapInvoiceAndCreditNoteProcessImpl runing....................");
		super.executeInternal(jobExecutionContext);

		try {

			sapInboundFileProcessor.processFile();

		} catch (FileNotFoundException e) {
			logger.error("File Not Found", e);

		} catch (CustomException e) {
			logger.error("Exception", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
