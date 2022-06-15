package com.ngen.cosys.billing.sap.airline.job;

import java.time.LocalDateTime;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ngen.cosys.billing.sap.airline.service.SapIncommingPaymentReceiptService;
import com.ngen.cosys.billing.sap.airline.service.SapInvoiceCreditDebitNoteService;
import com.ngen.cosys.billing.sap.airline.service.SapInvoiceSalesEntryService;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class SapAirlineJob extends AbstractCronJob  {
	    @Autowired
	   private SapInvoiceSalesEntryService sapInvoiceSalesEntryService;
	   @Autowired
	   private SapIncommingPaymentReceiptService sapIncommingPaymentReceiptService;
	   @Autowired
	   private SapInvoiceCreditDebitNoteService sapInvoiceCreditDebitNoteService;


	   private static Logger logger = LoggerFactory.getLogger(SapAirlineJob.class);
	 @Override
	   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	      super.executeInternal(jobExecutionContext);
	      logger.info("---SAP Airline Triggered---" + LocalDateTime.now());

	      try {
	    	  sapInvoiceSalesEntryService.createAndSendMessageToSap();
	    	  sapInvoiceCreditDebitNoteService.createAndSendCreditNoteMessageToSap();
	    	  sapInvoiceCreditDebitNoteService.createAndSendDebitNoteMessageToSap();
	    	  sapIncommingPaymentReceiptService.createAndSendMessageToSap();

	         logger.info("---SAP Airline Completed---" + LocalDateTime.now());
	      } catch (CustomException err) {
	         logger.error("SAP Airline Error : " + err);
	      }
	 }

}
