package com.ngen.cosys.billing.chargerecalculate.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.billing.chargerecalculate.service.ChargeRecalculateService;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class ChargeRecalculateController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private ChargeRecalculateService chargeRecalculateService;

   private static Logger logger = LoggerFactory.getLogger(ChargeRecalculateController.class);

   @SuppressWarnings({ "unchecked", "rawtypes" })
   @ApiOperation("Charge Recalculator")
   @PostRequest(value = "api/billing/recalculator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse recalculateCharges() {
      BaseResponse response = utilitiesModelConfiguration.getBaseResponseInstance();
      logger.info("---Charge Recalculation Triggered---" + LocalDateTime.now());

      try {
         chargeRecalculateService.recalculateCharges();
         logger.info("---Charge Recalculation Completed---" + LocalDateTime.now());
      } catch (CustomException err) {
         logger.error("Charge Recalculation Error : " + err);
      }
      response.setData(null);

      return response;
   }

}
