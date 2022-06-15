package com.ngen.cosys.billing.gstirn.job;

import java.math.BigInteger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.billing.communityportalinvoiceposting.service.CommunityPortalInvoicePostingJobService;
import com.ngen.cosys.billing.gstirn.model.IRNPostingModel;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class GstIrnEInvoiceJob extends AbstractCronJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstIrnEInvoiceJob.class);

	@Autowired
	CommunityPortalInvoicePostingJobService primaryUrlService;

	private RestTemplate restTemplate = null;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		LOGGER.warn("INSIDE GST IRP EInvoice Posting BATCH JOB");
		IRNPostingModel postingModel = new IRNPostingModel();
		BigInteger count = primaryUrlService.getirpPostingLoopCountLimit();
		
		LOGGER.warn("INSIDE GST IRP EInvoice Posting Running for Count :" + count);
		try {
			for (int i = 0; i < count.intValue(); i++) {				
				String url = getIRNPostingControllerUrl();
				restTemplate = CosysApplicationContext.getRestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<IRNPostingModel> entity = new HttpEntity<>(postingModel, headers);
				restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			}

		} catch (Exception e) {
			// GST EInvoice Batch job failed
			LOGGER.warn("GST IRP EInvoice JOB FAILED " + e.getMessage());

		}

	}

	String getIRNPostingControllerUrl() throws CustomException {
		StringBuilder path = new StringBuilder();
		String primarypath = primaryUrlService.getSendToBIALMessageUrl();
		path.append(primarypath);
		path.append("/aisatsinterfaces/api/irpportal/einvoiceposting");
		LOGGER.warn("GST IRP EInvoice url::" +path.toString());
		return path.toString();
	}

}
