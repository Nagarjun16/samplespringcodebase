package com.ngen.cosys.impbd.tracing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingFlightModel;
import com.ngen.cosys.impbd.tracing.service.BreakDownTracingService;
import com.ngen.cosys.validators.UserAssignedCarrierValidation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation()
public class BreakDownTracingController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private BreakDownTracingService tracingService;

   /**
    * REST api to fetch Arrival Shipments List
    * 
    * @return complete list of arrival manifest segment details,shipment
    *         details,uld details
    * @throws CustomException
    */

   @ApiOperation("fetch Breakdown Tracing list")
   @PostRequest(value = "api/config/breakdown/tracingList", method = RequestMethod.POST)
   public BaseResponse<List<BreakDownTracingFlightModel>> fetchTracingDetails(
         @ApiParam(value = "fetchBreakdownTracingList", required = true )@Validated({
        	 UserAssignedCarrierValidation.class }) @RequestBody BreakDownTracingFlightModel flightInfo)
         throws CustomException {
      BaseResponse<List<BreakDownTracingFlightModel>> tracingBreakdownResponse = utilitiesModelConfiguration
            .getBaseResponseInstance();
      tracingBreakdownResponse.setData(tracingService.fetchTracingList(flightInfo));
      return tracingBreakdownResponse;
   }
}