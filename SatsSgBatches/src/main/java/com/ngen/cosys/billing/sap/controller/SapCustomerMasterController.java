package com.ngen.cosys.billing.sap.controller;

/**
 * This controller takes care of all requests related to procssing of sap
 * customer file
 * 
 * @author NIIT Technologies Ltd
 *
 */

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.billing.sap.model.ParsedFileData;
import com.ngen.cosys.billing.sap.service.SapInboundFileProcessor;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class SapCustomerMasterController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   @Qualifier("sapCustomerMasterProcessImpl")
   private SapInboundFileProcessor sapInboundFileProcessor;

   private static Logger logger = LoggerFactory.getLogger(SapCustomerMasterController.class);

   /**
    * this method take care of process infomation from sap customer master document
    * 
    * @param
    * @return
    * @throws @throws
    */

   @ApiOperation("process infomation from sap customer master document")
   @PostRequest(value = "api/sapcustomermaster/getProcessInformationFromSapCustomerMasterDocument", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<ParsedFileData> getProcessInformationFromSapCustomerMasterDocument() {
      @SuppressWarnings("unchecked")
      BaseResponse<ParsedFileData> response = utilitiesModelConfiguration.getBaseResponseInstance();
      logger.info("File Looking in Directory {}", ' ');

      try {

         sapInboundFileProcessor.processFile();
         response.setData(null);
      } catch (FileNotFoundException e) {
         logger.error("File Not Found ", e);

      } catch (Exception e) {
         logger.error("Exception ", e);
      }

      return response;
   }

}
