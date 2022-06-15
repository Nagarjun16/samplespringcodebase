package com.ngen.cosys.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.service.PrelodgeServiceListService;
import com.ngen.cosys.framework.exception.CustomException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class DummyControllerForPrelodgeServiceList {
	
	   @Autowired
	   private PrelodgeServiceListService service;

	   /**
	    * Method to test Auto Expiry of SID
	    * 
	    * @throws CustomException
	    */
	   @ApiOperation("API to Test delete Service Numbers from Service List")
	   @RequestMapping(value = "/api/satssgbatches/dummy/deleteFromServicelist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public void test() throws CustomException {
	      this.service.deleteFromServiceList();
	   }

}
