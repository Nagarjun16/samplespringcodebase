package com.ngen.cosys.application.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.service.RcsSchedulerService;
// import com.ngen.cosys.esb.connector.ConnectorService;
import com.ngen.cosys.export.flightautocomplete.service.FlightAutoCompleteService;
import com.ngen.cosys.framework.exception.CustomException;

@NgenCosysAppInfraAnnotation
public class DummyControllerForRcsScheduler {

   private static final Logger LOGGER = LoggerFactory.getLogger(DummyControllerForAutoFlightComplete.class);
   @Autowired
   RcsSchedulerService service;

   @PostMapping(value = "/api/batches/rcsscheduler", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void performBatchOperation() throws CustomException {
      LOGGER.debug("INSIDE RCS SCHEDULER");
      service.getRcsTriggerFlightList();
   }

}