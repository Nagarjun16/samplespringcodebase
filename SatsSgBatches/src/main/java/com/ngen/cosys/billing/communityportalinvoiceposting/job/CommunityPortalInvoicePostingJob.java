package com.ngen.cosys.billing.communityportalinvoiceposting.job;

/**
 * 
 */

import java.util.List;

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

import com.ngen.cosys.billing.communityportalinvoiceposting.model.InvoiceRequest;
import com.ngen.cosys.billing.communityportalinvoiceposting.service.CommunityPortalInvoicePostingJobService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Shubhi.2.S
 *
 */
public class CommunityPortalInvoicePostingJob extends AbstractCronJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommunityPortalInvoicePostingJob.class);

	@Autowired
	CommunityPortalInvoicePostingJobService communityPortalInvoicePostingJobService;

	private RestTemplate restTemplate = null;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		super.executeInternal(jobExecutionContext);

		List<InvoiceRequest> creditDebitInvoiceList;
		List<InvoiceRequest> gstInvoiceList;
		try {
			communityPortalInvoicePostingJobService.updateCommunityPortalStatusAndErrorDesc();
			creditDebitInvoiceList = communityPortalInvoicePostingJobService.getDebitCreditInvoiceDetailsForPosting();
			String url = getSendToBIALMessageUrl();
			restTemplate = CosysApplicationContext.getRestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			for(InvoiceRequest request: creditDebitInvoiceList) {
				HttpEntity<List<InvoiceRequest>> entity = new HttpEntity<List<InvoiceRequest>>(new ArrayList<InvoiceRequest>(Arrays.asList(request)), headers);
				restTemplate.exchange(url+"?receiptNumber="+request.getInvoiceNumber(), HttpMethod.POST, entity, String.class);
			}
			
			gstInvoiceList = communityPortalInvoicePostingJobService.getGSTInvoiceDetailsForPosting();
			
			for(InvoiceRequest request: gstInvoiceList) {
				HttpEntity<List<InvoiceRequest>> entity = new HttpEntity<List<InvoiceRequest>>(new ArrayList<InvoiceRequest>(Arrays.asList(request)), headers);
				restTemplate.exchange(url+"?receiptNumber="+request.getInvoiceNumber(), HttpMethod.POST, entity, String.class);
			}
			
		} catch (CustomException e) {
			e.printStackTrace();
		}

	}

	private String getSendToBIALMessageUrl() throws CustomException {
		StringBuilder path = new StringBuilder();
		String primarypath = communityPortalInvoicePostingJobService.getSendToBIALMessageUrl();
		path.append(primarypath);
		path.append("/aisatsinterfaces/api/communityportal/invoiceposting");
		return path.toString();
	}
}
