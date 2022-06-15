package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.service.CargoManifestDeclerationMessageService;
import com.ngen.cosys.satssg.interfaces.psn.service.PSNService;
import com.ngen.cosys.satssg.interfaces.util.MessageTypeIdentifier;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "/api/cmd")
public class ProcessIncomingCargoManifestDeclerationMessasgeController {

   private static final Logger LOGGER = LoggerFactory
         .getLogger(ProcessIncomingCargoManifestDeclerationMessasgeController.class);

   @Autowired
   private CargoManifestDeclerationMessageService cargoManifestDeclerationMessageService;

   @Autowired
   PSNService psnService;

   @Autowired
   ConnectorLoggerService logger;

   @ApiOperation("Get  Cmd Message")
   @PostRequest(value = "/pullcmdmessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<String> pullMailBagStatus(@RequestBody String payload, HttpServletRequest request) {
      LOGGER.warn("payload ", payload);
      try {
         String messageType = MessageTypeIdentifier.getMessageType(payload);
         boolean contains = StringUtils.isEmpty(messageType) ? true : false;
         if (!contains && messageType.equalsIgnoreCase("PSN") || contains && payload.contains("PSN")) {
            return psnService.processMessages(payload);
         } else if ((!contains && messageType.equalsIgnoreCase("CMD") || contains && payload.contains("CMD"))
               || (!contains && messageType.equalsIgnoreCase("MRS") || contains && payload.contains("MRS"))
               || (!contains && messageType.equalsIgnoreCase("FMA") || contains && payload.contains("FMA"))
               || (!contains && messageType.equalsIgnoreCase("FNA") || contains && payload.contains("FNA"))) {
            return cargoManifestDeclerationMessageService.processMessages(payload, request);
         } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
      } catch (Exception e) {
         LOGGER.error("Unable to process message either PSN/CMD", e);
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
   }
   
}