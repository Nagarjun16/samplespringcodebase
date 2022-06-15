package com.ngen.cosys.satssg.interfaces.psn.controller;

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
import com.ngen.cosys.satssg.interfaces.psn.service.PSNService;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "/api/psn")
public class PSNMessageController {

   @Autowired
   private PSNService psnService;

   @ApiOperation("Insert PSN Message")
   @PostRequest(value = "/insertPSNMessage", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
         MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<String> insertPSNMessage(@RequestBody String payload, HttpServletRequest request)
         throws MessageParsingException, CustomException {
      try {
         if (payload.contains("PSN")) {
            return psnService.processMessages(payload);
         } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
      } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }
   
}