package com.ngen.cosys.ciq.report.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.ciq.report.dummyservice.DummyCiqservice;
import com.ngen.cosys.framework.exception.CustomException;


import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class CiqDailyJobDummyController {

	/**
	 * Method to test auto expiry of PO
	 * 
	 * @throws CustomException 
	 */

	@Autowired
	DummyCiqservice dummyCiqserviceImpl;

	@ApiOperation("API for generating CIQ daily report")
	@RequestMapping(value = "/api/satssgbatches/dummy/ciqDailyJob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void test() throws CustomException {

		dummyCiqserviceImpl.ciqDummyService();

	}
}
