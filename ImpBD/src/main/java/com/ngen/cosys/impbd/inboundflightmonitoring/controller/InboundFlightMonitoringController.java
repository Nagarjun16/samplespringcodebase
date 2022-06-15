package com.ngen.cosys.impbd.inboundflightmonitoring.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringSerach;
import com.ngen.cosys.impbd.inboundflightmonitoring.service.InboundFlightMonitoringService;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class InboundFlightMonitoringController {

   @Autowired
   private InboundFlightMonitoringService service;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @ApiOperation("fetch Inbound Flight Monitoring Information")
   @PostRequest(value = "api/inboundFlightMonitoring/search", method = RequestMethod.POST)
   public BaseResponse<Object> search(@RequestBody @Valid InboundFlightMonitoringSerach inboundFlightMonitoringSerach)
         throws CustomException {
      BaseResponse<Object> inboundFlightMonitoringModel = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         inboundFlightMonitoringModel
               .setData(service.getInboundFlightMonitoringInformation(inboundFlightMonitoringSerach));
      } catch (CustomException e) {
         inboundFlightMonitoringModel.setData(inboundFlightMonitoringSerach);
      }
      return inboundFlightMonitoringModel;
   }
}