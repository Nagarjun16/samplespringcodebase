package com.ngen.cosys.billing.sap.controller;

import java.io.FileNotFoundException;

import java.io.IOException;

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
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class SapCreditNotesController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   @Qualifier("sapCreditNoteProcessImpl")
   private SapInboundFileProcessor sapInboundFileProcessor;

   private static Logger logger = LoggerFactory.getLogger(SapCreditNotesController.class);

   /**
    * his method will process information from sap invoice and credit note document
    * 
    * @param
    * @return
 * @throws IOException 
    * @throws @throws
    */

   @SuppressWarnings("rawtypes")
   @ApiOperation("process infomation from sap credit note document")
   @PostRequest(value = "api/sapinvoiceandcreditnotesetup/getProcessInformationFromSapCreditNoteDocument", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse getProcessInformationFromSapCreditNoteDocument() throws IOException  {
      BaseResponse<?> response = utilitiesModelConfiguration.getBaseResponseInstance();
      logger.info("File Looking in Directory {}", ' ');

      try {
         sapInboundFileProcessor.processFile();
         response.setData(null);
      } catch (FileNotFoundException e) {
         logger.error("File Not Found", e);

      } catch (CustomException e) {
         logger.error("Exception", e);
      }
      return response;
   }

}
