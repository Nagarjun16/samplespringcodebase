package com.ngen.cosys.aed.controller;

import java.io.IOException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.aed.model.GhaFlightSchdRequestModel;
import com.ngen.cosys.aed.model.GhaMawbInfoRequestModel;
import com.ngen.cosys.aed.model.GhaScanInfoRequestModel;
import com.ngen.cosys.aed.service.OutboundShipmentEAcceptanceService;
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(value = "/api/aed")
public class AEDCustomsMessageProducerController {
	@Autowired
	private BeanFactory factory;

	@Autowired
	private OutboundShipmentEAcceptanceService service;

	@ApiOperation("AED Sumofdeclaredwt Interfaces")
	@RequestMapping(value = "/sumofdeclaredwt", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_XML_VALUE })
	public @ResponseBody String scwaigtsumof() throws CustomException, JsonGenerationException, JsonMappingException, IOException {
		return null;
	}

	@ApiOperation("AED GhaMawbInfo Interfaces")
	@RequestMapping(value = "/ghamawbinfo", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_XML_VALUE }, consumes = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody void ghaMawbInfo(@RequestBody GhaMawbInfoRequestModel requestModel) {
		//service.getGhaMawbInfo(requestModel);
	}

	@ApiOperation("AED Ghaflightschd Interfaces")
	@RequestMapping(value = "/ghaflightschd", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_XML_VALUE }, consumes = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody void ghaFlightSchd(@RequestBody GhaFlightSchdRequestModel requestModel) {
		//service.getGhaFlightSchd(requestModel);
	}

	@ApiOperation("AED GhaMawbScan Interfaces")
	@RequestMapping(value = "/ghamawbscaninfo", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_XML_VALUE }, consumes = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody void ghaMawbScanInfo(@RequestBody GhaScanInfoRequestModel requestModel) {
		//service.getGhaMawbScanInfo(requestModel);
	}
}