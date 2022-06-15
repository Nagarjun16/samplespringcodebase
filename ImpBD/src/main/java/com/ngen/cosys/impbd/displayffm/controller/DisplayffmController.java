/**
 * 
 * DisplayffmController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 30 April, 2018 NIIT -
 */
package com.ngen.cosys.impbd.displayffm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.displayffm.model.DisplayffmByFlightModel;
import com.ngen.cosys.impbd.displayffm.model.SearchDisplayffmModel;
import com.ngen.cosys.impbd.displayffm.service.DisplayffmService;
import com.ngen.cosys.validators.DisplayffmValidationGroup;
import com.ngen.cosys.validators.IncomingFlightValidation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This controller takes care of the maintaining Display FFM list.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation
public class DisplayffmController {

   @Autowired
   private DisplayffmService displayffmService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;
   
   @Autowired
   IncomingFlightValidation flightValidation;

   /**
    * REST api to fetch Display FFM List
    * 
    * @return complete list of Display FFM segment details,shipment details,uld
    *         details
    * @throws CustomException
    */
   @ApiOperation("API for retreiving FFM information")
   @PostMapping(path = "/api/import/displayffm/getAllDisplayffm")
   public BaseResponse<List<DisplayffmByFlightModel>> getAllDisplayffm(
         @ApiParam(value = "Request Object", required = true) @Validated({
               DisplayffmValidationGroup.class }) @Valid @RequestBody SearchDisplayffmModel searchDisplayffmModel)
         throws CustomException {
      BaseResponse<List<DisplayffmByFlightModel>> displayffmList = utilitiesModelConfiguration
            .getBaseResponseInstance();
      flightValidation.flightValidation(searchDisplayffmModel);
      displayffmList.setData(displayffmService.search(searchDisplayffmModel));
      return displayffmList;
   }
   
   @ApiOperation("API for updating FFM status information")
   @PostMapping(path = "/api/import/displayffm/updateFFMstatus")
   public BaseResponse<List<DisplayffmByFlightModel>> replaceAllFFMinfo(
         @ApiParam(value = "Request Object", required = true) @Validated({
               DisplayffmValidationGroup.class }) @Valid @RequestBody SearchDisplayffmModel searchDisplayffmModel)
         throws CustomException {
      BaseResponse<List<DisplayffmByFlightModel>> displayffmList = utilitiesModelConfiguration
            .getBaseResponseInstance();
      displayffmList.setData(displayffmService.updateFFMstatus(searchDisplayffmModel));
      return displayffmList;
   }
}