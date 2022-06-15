/**
 * Dummy controller to test through transit shipments finalized
 */
package com.ngen.cosys.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.service.ThroughTransitShipmentFinalizeService;
import com.ngen.cosys.framework.exception.CustomException;

@NgenCosysAppInfraAnnotation
public class DummyThroughTransitShipmentFinalizeController {

   @Autowired
   private ThroughTransitShipmentFinalizeService service;

   @PostMapping(value = "/api/batches/throughtransitshipmentfinalize", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void process() throws CustomException {
      this.service.process();
   }

}