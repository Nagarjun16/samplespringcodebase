package com.ngen.cosys.impbd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.model.DisplayIncomigFlightConfigurationTime;
import com.ngen.cosys.impbd.model.IncomingFlightModel;
import com.ngen.cosys.impbd.model.IncomingFlightQuery;
import com.ngen.cosys.impbd.service.IncomingFlightService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This APIs gives an overview of all incoming flights for a specific time
 * period, based on their handling terminal
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation
public class IncomingFlightsController {
   @Autowired
   private IncomingFlightService incomingFlightsService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   /**
    * API to get all incoming flights based on terminal, date, time and carrier
    * group
    * 
    * @return
    */
   @ApiOperation("Get all incoming flights based on terminal, date, time and carrier group")
   @RequestMapping(value = "/api/impbd/incoming/fetch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<IncomingFlightModel>> search(
         @ApiParam(value = "incomingFlight", required = true) @Valid @RequestBody IncomingFlightQuery searchIncomingFlight)
         throws CustomException {
      BaseResponse<List<IncomingFlightModel>> incomingFlights = utilitiesModelConfiguration.getBaseResponseInstance();
      incomingFlights.setData(incomingFlightsService.fetch(searchIncomingFlight));
      return incomingFlights;
   }

   @RequestMapping(value = "/api/impbd/incoming/fetchTelexMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<String>> fetchTelexMessage(
         @ApiParam(value = "incomingFlight", required = true) @Valid @RequestBody IncomingFlightQuery searchIncomingFlight)
         throws CustomException {
      BaseResponse<List<String>> telexMessage = utilitiesModelConfiguration.getBaseResponseInstance();
      telexMessage.setData(incomingFlightsService.fetchTelexMessage(searchIncomingFlight));
      return telexMessage;
   }

   @ApiOperation("Get Cofiguration Time For Dispalyincoming Flight")
   @RequestMapping(value = "/api/impbd/incoming/configuarationtime", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<DisplayIncomigFlightConfigurationTime> configuarableTime(
         @ApiParam(value = "incomingFlight", required = true) @Valid @RequestBody DisplayIncomigFlightConfigurationTime searchIncomingFlight)
         throws CustomException {
      BaseResponse<DisplayIncomigFlightConfigurationTime> incomingFlights = utilitiesModelConfiguration
            .getBaseResponseInstance();
      incomingFlights.setData(incomingFlightsService.fetchTime(searchIncomingFlight));
      return incomingFlights;
   }

   @ApiOperation("Get all incoming flights based on terminal, date, time and carrier group")
   @RequestMapping(value = "/api/impbd/myincoming/fetch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<IncomingFlightModel>> searchMyflights(
         @ApiParam(value = "incomingFlight", required = true) @Valid @RequestBody DisplayIncomigFlightConfigurationTime searchIncomingFlight)
         throws CustomException {
      BaseResponse<List<IncomingFlightModel>> incomingFlights = utilitiesModelConfiguration.getBaseResponseInstance();
      incomingFlights.setData(incomingFlightsService.fetchMyFlights(searchIncomingFlight));
      return incomingFlights;
   }
}