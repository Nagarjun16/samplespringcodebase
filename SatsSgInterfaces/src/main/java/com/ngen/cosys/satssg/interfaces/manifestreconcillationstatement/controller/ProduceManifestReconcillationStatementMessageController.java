package com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.service.ManifestReconcillationStatementMessageService;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "/api/mrs")
public class ProduceManifestReconcillationStatementMessageController {
   @Autowired
   private ManifestReconcillationStatementMessageService manifestReconcillationStatementMessageService;

   @ApiOperation("Get  FMA OR FNA Message")
   @PostRequest(value = "/pullfmaorfnamessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<String> FMAOrFNAStatus(@RequestBody String payload, HttpServletRequest request)
         throws MessageParsingException, CustomException {     
      try {
         return manifestReconcillationStatementMessageService.processAcKMessages(payload, null);

      } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }

   }
}
