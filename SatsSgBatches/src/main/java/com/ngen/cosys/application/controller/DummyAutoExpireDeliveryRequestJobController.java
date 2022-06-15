package com.ngen.cosys.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.model.AutoExpireDeliveryRequestModel;
import com.ngen.cosys.application.service.AutoExpireDeliveryRequestService;
import com.ngen.cosys.framework.exception.CustomException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class DummyAutoExpireDeliveryRequestJobController {

   @Autowired
   private AutoExpireDeliveryRequestService service;

   /**
    * Method to test auto expiry of PO
    * 
    * @throws CustomException
    */
   @ApiOperation("API for Testing Auto Expiry of PO")
   @RequestMapping(value = "/api/satssgbatches/dummy/autoexpirepo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void test() throws CustomException {

      // Get the list of shipments
      List<AutoExpireDeliveryRequestModel> shipments = this.service.getShipments();

      // Expire the PO
      for (AutoExpireDeliveryRequestModel t : shipments) {
         this.service.expirePO(t);
      }

   }
}
