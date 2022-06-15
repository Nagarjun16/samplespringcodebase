package com.ngen.cosys.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.model.TracingShipmentModel;
import com.ngen.cosys.application.service.TracingBatchJobService;
import com.ngen.cosys.framework.exception.CustomException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class DummyTracingController {

   @Autowired
   private TracingBatchJobService service;

   @ApiOperation("API for Testing Auto Create Delivery Request")
   @RequestMapping(value = "/api/satssgbatches/dummy/createTracingForUndeliveredShipments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void createTracingForUndeliveredShipments() throws CustomException {
      List<TracingShipmentModel> undeliveredShipments = this.service.getUndeliverdShipments();
      if (!CollectionUtils.isEmpty(undeliveredShipments)) {
         for (TracingShipmentModel t : undeliveredShipments) {
            this.service.createTracing(t);
         }
      }
   }

}