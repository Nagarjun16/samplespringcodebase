package com.ngen.cosys.ics.controller;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.BLEEquipmentRampProcessRequestModel;
import com.ngen.cosys.ics.model.BLEEquipmentRampProcessResponseModel;
import com.ngen.cosys.ics.model.BLEPDLocationRequestModel;
import com.ngen.cosys.ics.model.BLEPDLocationResponseModel;
import com.ngen.cosys.ics.service.BLEEquipmentRampProcessService;
import com.ngen.cosys.validation.groups.RampProcessValidationGroup;

@NgenCosysAppInfraAnnotation(path = "/api/ics")
public class BLEEquipmentRampProcessController {

   @Autowired
   private Validator validator;

   @Autowired
   private BLEEquipmentRampProcessService service;

   @PostMapping(value = "/update-equipment-ramp-process", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<BLEEquipmentRampProcessResponseModel> updateEquipmentRampProcess(@RequestBody @Valid BLEEquipmentRampProcessRequestModel request) {

      BLEEquipmentRampProcessResponseModel responseModel = null;
      StringBuilder errorMessage = new StringBuilder();

      Set<ConstraintViolation<BLEEquipmentRampProcessRequestModel>> violations = this.validator.validate(request, RampProcessValidationGroup.class);

      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<BLEEquipmentRampProcessRequestModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(", ");
         }
         responseModel = new BLEEquipmentRampProcessResponseModel();
         responseModel.setStatus("F");
         responseModel.setErrorType("CMR");
         responseModel.setErrorNumber("10010");
         responseModel.setErrorDescription(errorMessage.toString());
      } else {
         try {
            // Fetch PDMasterId based on pdId
            String palletDollyMasterId = service.isPalletDollyExist(request);
            if (!StringUtils.isEmpty(palletDollyMasterId)) {
               Integer status = service.performEquipmentRampProcess(request);
               if (status > 0) {
                  responseModel = new BLEEquipmentRampProcessResponseModel();
                  responseModel.setStatus("S");
               }

            } else {
               responseModel = new BLEEquipmentRampProcessResponseModel();
               responseModel.setStatus("F");
               responseModel.setErrorNumber("10010");
               responseModel.setErrorType("CMR");
               responseModel.setErrorDescription("PD doesn't exist in inventory");
            }
         } catch (CustomException e) {
            e.printStackTrace();
            responseModel = new BLEEquipmentRampProcessResponseModel();
            responseModel.setStatus("F");
            responseModel.setErrorNumber("10010");
            responseModel.setErrorType("CMR");
            responseModel.setErrorDescription(e.getMessage());
         }

      }
      return new ResponseEntity<>(responseModel, HttpStatus.OK);
   }

   @PostMapping(value = "/fetch-pd-location", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<BLEPDLocationResponseModel> fetchPDLocation(@RequestBody @Valid BLEPDLocationRequestModel request) {

      BLEEquipmentRampProcessResponseModel responseModel = null;
      StringBuilder errorMessage = new StringBuilder();
      BLEPDLocationResponseModel convertXMLStringToObject = null;

      Set<ConstraintViolation<BLEPDLocationRequestModel>> violations = this.validator.validate(request, RampProcessValidationGroup.class);

      if (!violations.isEmpty()) {
         Iterator<ConstraintViolation<BLEPDLocationRequestModel>> iterator = violations.iterator();
         while (iterator.hasNext()) {
            errorMessage.append(iterator.next().getMessage()).append(", ");
         }
         responseModel = new BLEEquipmentRampProcessResponseModel();
         responseModel.setErrorType(ResponseStatus.FAIL);
         responseModel.setErrorNumber(HttpStatus.BAD_REQUEST.toString());
         responseModel.setErrorDescription(errorMessage.toString());
      } else {
         // SERVICE CALL TO BLE
         String responseObject = "<PDLocationResponse><PDLocation><LocationInfo><palletDolley>PD0010</palletDolley><Location>XYZ</Location></LocationInfo><LocationInfo><palletDolley>PD0020</palletDolley><Location>PQR</Location></LocationInfo></PDLocation></PDLocationResponse>";
         if (Objects.nonNull(responseObject)) {
            convertXMLStringToObject = (BLEPDLocationResponseModel) JacksonUtility.convertXMLStringToObject(responseObject, BLEPDLocationResponseModel.class);
         }
      }

      return new ResponseEntity<>(convertXMLStringToObject, HttpStatus.OK);
   }

}