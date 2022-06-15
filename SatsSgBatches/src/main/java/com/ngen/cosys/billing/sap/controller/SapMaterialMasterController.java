package com.ngen.cosys.billing.sap.controller;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.billing.sap.service.SapInboundFileProcessor;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class SapMaterialMasterController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   @Qualifier("sapMaterialMasterProcessImpl")
   private SapInboundFileProcessor sapInboundFileProcessor;

   private static Logger logger = LoggerFactory.getLogger(SapMaterialMasterController.class);

   /**
    * process infomation from sap material master document
    * 
    * @param @return @throws
    * 
    */

   @SuppressWarnings({ "rawtypes", "unchecked" })
   @ApiOperation("process infomation from sap material master document")
   @PostRequest(value = "api/sapmaterialmaster/getProcessInformationFromSapMaterialMasterDocument", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse getProcessInformationFromSapMaterialMasterDocument() {
      BaseResponse response = utilitiesModelConfiguration.getBaseResponseInstance();
      logger.info("File Looking in Directory {}", ' ');
      try {

         sapInboundFileProcessor.processFile();
      } catch (FileNotFoundException e) {
         logger.error("File Not Found ", e);

      } catch (Exception e) {
         logger.error("Exception ", e);
      }

      response.setData(null);

      return response;
   }

}
