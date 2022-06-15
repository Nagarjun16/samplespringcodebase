package com.ngen.cosys.impbd.delaystatus.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.delaystatus.model.DelayStatusSearch;
import com.ngen.cosys.impbd.delaystatus.service.BreakDownStatusService;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation
public class BreakDownDelayStatusController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private BreakDownStatusService delayService;

   /**
    * REST API to fetch BreakDown Summary List
    * 
    * @return complete Delay List
    * 
    * @throws CustomException
    */

   @ApiOperation("fetch Breakdown Delay Status list")
   @PostRequest(value = "api/config/breakdown/delayList", method = RequestMethod.POST)
   public BaseResponse<List<BreakDownSummaryModel>> fetchSummaryDetails(
         @ApiParam(value = "fetchDelayList", required = true) @RequestBody @Valid DelayStatusSearch flightInfo)
         throws CustomException {
      BaseResponse<List<BreakDownSummaryModel>> delayList = utilitiesModelConfiguration.getBaseResponseInstance();
      delayList.setData(delayService.fetchDelayList(flightInfo));
      return delayList;
   }

   /**
    * REST API to close Flight
    * 
    * @return null
    * 
    * @throws CustomException
    */
 
   @ApiOperation("close Flight")
   @PostRequest(value = "api/config/breakdown/closeFlight", method = RequestMethod.POST)
   public BaseResponse<DelayStatusSearch> closeFlight(
         @ApiParam(value = "fetchDelayList", required = true) @RequestBody DelayStatusSearch flightInfo)
         throws CustomException {
      BaseResponse<DelayStatusSearch> delayList = utilitiesModelConfiguration.getBaseResponseInstance();
      delayService.closeFlight(flightInfo);
      delayList.setData(null);
      return delayList;
   }
}