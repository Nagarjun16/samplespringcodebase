package com.ngen.cosys.billing.sap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.billing.sap.service.SapOutboundFileProcessor;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class SalesAndDistributionBillingController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   @Qualifier("salesAndDistributionBillingDetailsProcessImpl")
   private SapOutboundFileProcessor sapOutboundFileProcessor;

   private static Logger logger = LoggerFactory.getLogger(SalesAndDistributionBillingController.class);

   @SuppressWarnings("rawtypes")
   @ApiOperation("process infomation from sale and distribution details")
   @PostRequest(value = "api/salesAndDistributionBilling/getProcessSalesAndDistributionBillingDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse getProcessSalesAndDistributionBillingDetails() {
      BaseResponse<?> response = utilitiesModelConfiguration.getBaseResponseInstance();
      logger.info("Start creating IO file {}", ' ');
      try {
         sapOutboundFileProcessor.createFile();
      } catch (CustomException e) {
         logger.error("Exception", e);

      }
      response.setData(null);
      return response;

   }

}
