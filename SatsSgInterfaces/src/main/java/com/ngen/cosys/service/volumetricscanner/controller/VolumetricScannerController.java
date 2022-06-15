package com.ngen.cosys.service.volumetricscanner.controller;

import java.math.BigInteger;
import java.util.Objects;

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
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.CancelScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.ScanVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtRequest;
import com.ngen.cosys.service.volumetricscanner.model.UpdateVolWgtResponse;
import com.ngen.cosys.service.volumetricscanner.model.VolumetricRequest;
import com.ngen.cosys.service.volumetricscanner.model.VolumetricResponse;
import com.ngen.cosys.service.volumetricscanner.service.VolumetricScannerService;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation(path = "/api/smartgate")
public class VolumetricScannerController {
   
   @Autowired
   VolumetricScannerService volumetricScannerService;

   @Autowired
   ConnectorLoggerService logger;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(VolumetricScannerController.class);
   
   /**
    * SCREEN SCAN Volumetric Scanner Request API
    * 
    * @param volumetricRequest
    * @param request
    * @return
    */
   @ApiOperation("send req to volumetric scanner")
   @PostRequest(value = "/scanVolWgtReq", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<VolumetricResponse> scanVolWgtReq(@RequestBody VolumetricRequest volumetricRequest,
         HttpServletRequest request) {
      LOGGER.warn("SCAN Volumetric Scanner Request :: Payload - {}", volumetricRequest);
      try {
         return volumetricScannerService.saveVolumetricScanRequest(volumetricRequest);
      } catch (Exception e) {
         LOGGER.warn("Volumetric Scanner Request Exception Occurred - ShipmentNumber : {}, Exception : {}",
         volumetricRequest.getShipmentNumber(), String.valueOf(e));
         e.printStackTrace();
         
         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
   }
   
   /**
    * SMARTGATE Error Response API
    * 
    * @param payload
    * @param request
    * @return
    */
   @ApiOperation("Get res from volumetric scanner error response")
   @PostRequest(value = "/updateScanVolWgtError", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<ScanVolWgtResponse> scanVolWgtRes(@RequestBody String payload, HttpServletRequest request) {
      LOGGER.warn("ERROR Volumetric Scanner Response :: Payload - {}", payload);
      try {
         return volumetricScannerService.scanVolWgtRes(payload);
      } catch(Exception e) {
         LOGGER.warn("Volumetric Scanner Request Exception Occurred - Payload : {}, Exception : {}", payload,
               String.valueOf(e));
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
      }
   }
   
   /**
    * SCREEN CANCEL Volumetric Scanner Request API 
    * 
    * @param volumetricRequest
    * @param request
    * @return
    */
   @ApiOperation("send Cancel req to volumetric scanner")
   @PostRequest(value = "/cancelScanVolWgtReq", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<CancelScanVolWgtRequest> cancelScanVolWgtReq(@RequestBody VolumetricRequest volumetricRequest,
         HttpServletRequest request) {
      String shipmentNumber = volumetricRequest.getShipmentNumber();
      LOGGER.warn("CANCEL Volumetric Scanner Request :: Shipment Number - {}", shipmentNumber);
      try {
         CancelScanVolWgtRequest payload = new CancelScanVolWgtRequest();
         payload.setAwb(shipmentNumber);
         return volumetricScannerService.cancelScanVolWgtReq(payload);
      } catch (Exception e) {
         LOGGER.warn("Volumetric Scanner Cancel Request Exception Occurred - ShipmentNumber : {}, Exception : {}",
               shipmentNumber, String.valueOf(e));
         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
   }
   
   /**
    * SMARTGATE CANCEL Volumetric Scanner Response API
    * 
    * @param payload
    * @param request
    * @return
    */
   @ApiOperation("get Cancel  res  from volumetric scanner")
   @PostRequest(value = "/cancelScanVolWgtInfo", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<CancelScanVolWgtResponse> cancelScanVolWgtRes(@RequestBody String payload,
         HttpServletRequest request) {
      LOGGER.warn("CANCEL Volumetric Scanner Response :: Payload - {}", payload);
      try {
         return volumetricScannerService.cancelScanVolWgtRes(payload);
      } catch(Exception e) {
         LOGGER.warn("Volumetric Scanner Cancel Response Exception Occurred - Payload : {}, Exception : {}", payload,
               String.valueOf(e));
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
      }
   }
   
   /**
    * SMARTGATE UPDATE Volumetric Scanner Response API
    * 
    * @param payload
    * @param request
    * @return
    */
   @ApiOperation("get update req   from volumetric scanner")
   @PostRequest(value = "/updateScanVolWgtInfo", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<String> updateVolWgtReqLog(@RequestBody String payload,
         HttpServletRequest request) {
      LOGGER.warn("UPDATE Volumetric Scanner Response :: Payload - {}", payload);
      try {
         return volumetricScannerService.updateVolWgtReqLog(payload);
      } catch(Exception e) {
         LOGGER.warn("Volumetric Scanner update Response Exception Occurred - Payload : {}, Exception : {}", payload,
               String.valueOf(e));
         String response = null;
         UpdateVolWgtRequest volRequest = (UpdateVolWgtRequest) JacksonUtility.convertXMLStringToObject(payload,
               UpdateVolWgtRequest.class);
         //
         BigInteger messageId = null;
         if (Objects.nonNull(volRequest) && !StringUtils.isEmpty(volRequest.getAwb())) {
            try {
               messageId = volumetricScannerService.getVolumetricScannerReferenceLogId(volRequest.getAwb());
               if (Objects.nonNull(messageId)) {
                  response = volumetricScannerService.updateVolumetricResponse(messageId.longValue(), false);
               }
            } catch (CustomException ex) {
               LOGGER.warn("Volumetric Scanner update - Nested Exception :: {}", ex);
            }
            
         }
         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); 
      }
   }
   
   /**
    * DUMMY Service
    * 
    * @param payload
    * @param request
    * @return
    */
   @ApiOperation("send update res  to volumetric scanner")
   @PostRequest(value = "/updateScanVolWgtRes", method = RequestMethod.POST, 
      consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<UpdateVolWgtResponse> updateVolWgtResLog(@RequestBody UpdateVolWgtResponse payload,
         HttpServletRequest request) {
      LOGGER.warn("UPDATE Volumetric Scanner Response NOT USED :: Payload - {}", payload);
      try {
         return volumetricScannerService.updateVolWgtResLog(payload);
      } catch(Exception e) {
         LOGGER.warn("Volumetric Scanner update Response NOT USED Exception Occurred - Payload : {}, Exception : {}",
               payload, String.valueOf(e));
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
      }
   }
   
}