/**
 * 
 * AlteaFMController.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.altea.fm.service.AlteaFMService;
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.payload.AlteaFMEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

/**
 * This class is used for Altea FM Message Interface controller used for
 * Internal Testing
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation(path = "/interface/api/altea-fm")
public class AlteaFMController {

   private static final Logger LOGGER = LoggerFactory.getLogger(AlteaFMController.class);
   
   @Autowired
   BeanFactory beanFactory;
   
   @Autowired
   AlteaFMService alteaFMService;
   
   /**
    * @return
    * @throws CustomException
    */
   @SuppressWarnings("unchecked")
   @ApiOperation(value = "Altea FM Message Service for DLS and NOTOC")
   @PostRequest(value = "/message-service", method = RequestMethod.POST)
   public BaseResponse<DCSFMUpdateCargoFigures> alteaFMService(@RequestBody AlteaFMEvent event) throws CustomException {
      LOGGER.debug("Altea FM Controller for Message Processing is invoked");
      BaseResponse<DCSFMUpdateCargoFigures> response = beanFactory.getBean(BaseResponse.class);
      DCSFMUpdateCargoFigures messagePayload = alteaFMService.prepareUpdateCargoPayload(event);
      response.setData(messagePayload);
      return response;
   }
   
}
