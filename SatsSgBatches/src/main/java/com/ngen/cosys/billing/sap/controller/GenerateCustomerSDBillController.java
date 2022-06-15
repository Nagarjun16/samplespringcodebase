package com.ngen.cosys.billing.sap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.billing.chargepost.service.ChargePostService;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class GenerateCustomerSDBillController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private ChargePostService chargePostService;

   /*
    * @Autowired
    * 
    * @Qualifier("accountsPayableDetailsProcessImpl") private
    * SapOutboundFileProcessor sapOutboundFileProcessor;
    */

   private static Logger logger = LoggerFactory.getLogger(GenerateCustomerSDBillController.class);

   @SuppressWarnings("rawtypes")
   @ApiOperation("process infomation from sap customer master document")
   @PostRequest(value = "api/generateCustomerSDBillController/getGenerateCustomerSDBill", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse getGenerateCustomerSDBill() {
      BaseResponse<?> response = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         chargePostService.getGenerateCustomerSDBill();
      } catch (CustomException e) {
         // TODO Auto-generated catch block
         logger.error("Exception Occured - " + e);
      }
      return response;

   }

}
