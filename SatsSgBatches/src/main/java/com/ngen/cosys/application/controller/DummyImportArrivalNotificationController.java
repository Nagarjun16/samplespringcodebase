/**
 * Dummy rest service component which helps in testing arrival notification
 */
package com.ngen.cosys.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.service.ImportArrivalNotificationService;
import com.ngen.cosys.framework.exception.CustomException;

@NgenCosysAppInfraAnnotation
public class DummyImportArrivalNotificationController {

   @Autowired
   private ImportArrivalNotificationService service;

   @PostMapping(value = "/api/batches/sendImportArrivalNotification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void process() throws CustomException {
      this.service.sendNotification();
   }

}