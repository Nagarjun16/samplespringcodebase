package com.ngen.cosys.etqs.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.etqs.model.ETQSShipmentInfo;
import com.ngen.cosys.etqs.service.ETQSService;
import com.ngen.cosys.framework.exception.CustomException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class ETQSServiceRequestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ETQSServiceRequestController.class);

	@Autowired
	ETQSService etqsService;

	@SuppressWarnings("static-access")
	@ApiOperation("ETQS method to get the serviceNumber for the Given AWB Number")
	@PostRequest(path = "/api/etqs/servicenumber/request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Object> getServiceNumber(@RequestBody ETQSShipmentInfo requestModel,
			HttpServletRequest request) throws CustomException {

		ETQSShipmentInfo response = etqsService.getServcieNumber(requestModel);

		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@SuppressWarnings("static-access")
	@ApiOperation("ETQS method to get the serviceNumber for the Given AWB Number")
	@PostRequest(path = "/api/etqs/queenumber/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Object> consumeQueeStatus(@RequestBody ETQSShipmentInfo requestModel,
			HttpServletRequest request) throws CustomException {

		ETQSShipmentInfo response = etqsService.updateQueeNumber(requestModel);

		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
