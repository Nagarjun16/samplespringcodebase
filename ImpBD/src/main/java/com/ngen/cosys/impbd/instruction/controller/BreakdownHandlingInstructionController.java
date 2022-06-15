package com.ngen.cosys.impbd.instruction.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInformationQuery;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInstructionFlightSegmentModel;
import com.ngen.cosys.impbd.instruction.model.BreakdownHandlingListResModel;
import com.ngen.cosys.impbd.instruction.service.BreakDownHandlingInformationService;
import com.ngen.cosys.impbd.instruction.validator.BreakDownHandlingInstructionValidationGroup;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation
public class BreakdownHandlingInstructionController {

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private BreakDownHandlingInformationService service;

   
   @ApiOperation(value = "get breakdown instruction list")
   @RequestMapping(value = "/selectbdinfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<BreakdownHandlingListResModel>> getbdinfolist(
         @ApiParam("get breakdown instruction list") @Valid @RequestBody BreakDownHandlingInformationQuery breakDownHandlingInformationQuery)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<BreakdownHandlingListResModel>> baseResponse = utility.getBaseResponseInstance();
      List<BreakdownHandlingListResModel> searchRes = service
            .selectBreakDownHandlingInformations(breakDownHandlingInformationQuery);
      if (searchRes != null) {
         baseResponse.setData(searchRes);
      }
      return baseResponse;
   }

  
   @ApiOperation("create breakdown instruction")
   @RequestMapping(value = "/createbdinfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<BreakDownHandlingInstructionFlightSegmentModel> insertbdinfo(
         @Validated(BreakDownHandlingInstructionValidationGroup.class) @RequestBody BreakDownHandlingInstructionFlightSegmentModel breakDownHandlingInformationQuery)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<BreakDownHandlingInstructionFlightSegmentModel> response = utility.getBaseResponseInstance();
      service.createBreakDownHandlingInformation(breakDownHandlingInformationQuery);
      return response;
   }

 
   @ApiOperation(value = "delete breakdown instruction list")
   @RequestMapping(value = "/deletebdinfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<BreakDownHandlingInstructionFlightSegmentModel> deletebdinfolist(
         @ApiParam("get breakdown instruction list") @RequestBody BreakDownHandlingInstructionFlightSegmentModel breakDownHandlingInformationQuery)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<BreakDownHandlingInstructionFlightSegmentModel> response = utility.getBaseResponseInstance();
      service.deleteBreakDownHandlingInformations(breakDownHandlingInformationQuery);
      return response;
   }
   @ApiOperation(value = "get hawb info")
   @RequestMapping(value = "/getHAWBInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<String>> getHAWBInfo(
         @ApiParam("get hawb info") @Valid @RequestBody String shipmentNumber)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<String>> baseResponse = utility.getBaseResponseInstance();
      
      List<String> searchRes =this.service.getHawbInfo(shipmentNumber);
      if (searchRes != null) {
         baseResponse.setData(searchRes);
      }
     
      return baseResponse;
   }
}