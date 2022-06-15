package com.ngen.cosys.impbd.mail.document.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentModel;
import com.ngen.cosys.impbd.mail.document.model.InboundMailDocumentShipmentModel;
import com.ngen.cosys.impbd.mail.document.service.InboundMailDocumentService;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailDocumentValidationGroup;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailManifestValidationGroup;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
@RequestMapping("/api/mail/document")
public class InboundMailDocumentController {

   @Autowired
   private BeanFactory beanFactory;

   @Autowired
   private InboundMailDocumentService service;

   @ApiOperation(value = "Api which allows user to get mail document information")
   @RequestMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailDocumentModel> search(
         @Validated(value = InboundMailDocumentValidationGroup.class) @RequestBody InboundMailDocumentModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailDocumentModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailDocumentModel responseModel = service.search(requestModel);
      response.setData(responseModel);
      return response;
   }

   @ApiOperation(value = "Api which allows user to save the mail document information")
   @RequestMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailDocumentModel> documentIn(
         @Validated(value = InboundMailDocumentValidationGroup.class) @RequestBody InboundMailDocumentModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailDocumentModel> response = beanFactory.getBean(BaseResponse.class);
      InboundMailDocumentModel responseModel = service.documentIn(requestModel);
      response.setData(responseModel);
      return response;
   }

   @PostMapping(value = "/dispatchyear", produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<BigInteger>> getDispatchYears() throws CustomException {
      BaseResponse<List<BigInteger>> response = beanFactory.getBean(BaseResponse.class);
      List<BigInteger> years = service.getDispatchYears();
      response.setData(years);
      return response;
   }

   @ApiOperation(value = "Api which allows user to mark break down complete at flight level")
   @RequestMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailDocumentModel> update(
         @Validated(value = InboundMailManifestValidationGroup.class) @RequestBody InboundMailDocumentModel requestModel)
         throws CustomException {
      BaseResponse<InboundMailDocumentModel> response = beanFactory.getBean(BaseResponse.class);
      service.update(requestModel);
      response.setData(requestModel);
      return response;
   }

   @ApiOperation(value = "Api which allows user to check all the validation")
   @RequestMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public BaseResponse<InboundMailDocumentShipmentModel> validate(
         @RequestBody InboundMailDocumentShipmentModel requestModel) throws CustomException {
      BaseResponse<InboundMailDocumentShipmentModel> response = beanFactory.getBean(BaseResponse.class);
      service.validate(requestModel);
      response.setData(requestModel);
      return response;
   }
}