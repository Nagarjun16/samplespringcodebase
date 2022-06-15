package com.ngen.cosys.billing.gstirn.controller;

import java.math.BigInteger;

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
import com.ngen.cosys.billing.communityportalinvoiceposting.service.CommunityPortalInvoicePostingJobService;
import com.ngen.cosys.billing.gstirn.model.IRNPostingModel;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class GstIrnEInvoiceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GstIrnEInvoiceController.class);

	private RestTemplate restTemplate = null;

	@Autowired
	CommunityPortalInvoicePostingJobService primaryUrlService;

	@ApiOperation("API for Posting IRN data")
	@RequestMapping(value = "/api/satssgbatches/irnPosting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void postDataToIRPPortal() throws CustomException {
		LOGGER.warn("INSIDE GST IRP EInvoice Posting BATCH JOB");
		IRNPostingModel postingModel = new IRNPostingModel();
		BigInteger count = primaryUrlService.getirpPostingLoopCountLimit();
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
			// GST EInvoide Batch job failed
			LOGGER.warn("GST IRP EInvoice JOB FAILED " + e.getMessage());

		}

	}

	String getIRNPostingControllerUrl() throws CustomException {
		StringBuilder path = new StringBuilder();
		String primarypath = primaryUrlService.getSendToBIALMessageUrl();
		//for testing
		//String primarypath = "http://localhost:9360";
		path.append(primarypath);
		path.append("/aisatsinterfaces/api/irpportal/einvoiceposting");
		LOGGER.warn("GST IRP EInvoice url::" +path.toString());
		return path.toString();
	}
}
