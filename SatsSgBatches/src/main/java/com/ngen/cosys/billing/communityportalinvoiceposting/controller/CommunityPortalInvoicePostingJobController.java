/**
 * 
 */
package com.ngen.cosys.billing.communityportalinvoiceposting.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.billing.communityportalinvoiceposting.model.InvoiceRequest;
import com.ngen.cosys.billing.communityportalinvoiceposting.service.CommunityPortalInvoicePostingJobService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;

import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Shubhi.2.S
 *
 */
@NgenCosysAppInfraAnnotation
public class CommunityPortalInvoicePostingJobController {

	static RestTemplate restTemplate = null;

	@Autowired
	private CommunityPortalInvoicePostingJobService communityPortalInvoicePostingJobService;

	private static Logger logger = LoggerFactory.getLogger(CommunityPortalInvoicePostingJobController.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })

	/**
	 * Get ShipmentInfo from cosys
	 * 
	 * @param addMRSModel
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation("Get Flight from cosys")
	@RequestMapping(value = "/api/satssgcustoms/customsmrs/getCommunityPortalDataToPushToMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void getCommunityPortalInvoiceDetailsForPosting() throws CustomException {

		List<InvoiceRequest> result = communityPortalInvoicePostingJobService
				.getDebitCreditInvoiceDetailsForPosting();
		String url = getSendToBIALMessageUrl();
		restTemplate = CosysApplicationContext.getRestTemplate();
	      HttpHeaders headers = new HttpHeaders();
	      headers.setContentType(MediaType.APPLICATION_JSON);
	      for(InvoiceRequest request: result) {
	    	  HttpEntity<List<InvoiceRequest>> entity = new HttpEntity<List<InvoiceRequest>>(new ArrayList<InvoiceRequest>(Arrays.asList(request)), headers);
				restTemplate.exchange(url+"?receiptNumber="+request.getInvoiceNumber(), HttpMethod.POST, entity, String.class); 
	      }
	      
	      List<InvoiceRequest> result1 = communityPortalInvoicePostingJobService
					.getGSTInvoiceDetailsForPosting();
	      
	      for(InvoiceRequest request: result1) {
	    	  HttpEntity<List<InvoiceRequest>> entity = new HttpEntity<List<InvoiceRequest>>(new ArrayList<InvoiceRequest>(Arrays.asList(request)), headers);
				restTemplate.exchange(url+"?receiptNumber="+request.getInvoiceNumber(), HttpMethod.POST, entity, String.class); 
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
