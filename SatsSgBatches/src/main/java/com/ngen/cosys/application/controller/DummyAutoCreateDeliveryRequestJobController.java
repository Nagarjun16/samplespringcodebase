package com.ngen.cosys.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.service.AutoCreateDeliveryRequestService;
import com.ngen.cosys.framework.exception.CustomException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class DummyAutoCreateDeliveryRequestJobController {

   @Autowired
   private AutoCreateDeliveryRequestService service;

   /**
    * Method to test auto expiry of PO
    * 
    * @throws CustomException
    */
   @ApiOperation("API for Testing Auto Create Delivery Request")
   @RequestMapping(value = "/api/satssgbatches/dummy/autocreatedeliveryrequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void test() throws CustomException {
      this.service.scheduleDeliveryRequests();
   }

}