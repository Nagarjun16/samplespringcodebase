package com.ngen.cosys.inward.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.payload.InwardsFlightServiceReportEvent;
import com.ngen.cosys.events.producer.InwardsFlightServiceReportProducer;
import com.ngen.cosys.events.producer.InwardsShipmentServiceReportProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;
import com.ngen.cosys.inward.service.InwardServiceReportService;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.validators.InwardSearchValidationGroup;
import com.ngen.cosys.validators.InwardServiceValidationGroup;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class InwardServiceReportController {

   @Autowired
   private InwardServiceReportService inwardServiceReportService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModel;

   @Autowired
   private InwardsFlightServiceReportProducer flightproducer;

   @Autowired
   private InwardsShipmentServiceReportProducer shipmentproducer;

   @ApiOperation("get board point  on flightkey date")
   @PostRequest(value = "/api/impbd/inward/fetchboardpoint", method = RequestMethod.POST)
   public BaseResponse<List<InwardServiceReportModel>> fetchbp(
         @Valid @RequestBody InwardServiceReportModel fetchboardpoint) throws CustomException {
      BaseResponse<List<InwardServiceReportModel>> inwardServiceRequest = utilitiesModel.getBaseResponseInstance();
      inwardServiceRequest.setData(inwardServiceReportService.fetchBoardPoint(fetchboardpoint));
      return inwardServiceRequest;
   }

   @ApiOperation("get discrepancies based on flightkey date and boardpoint")
   @PostRequest(value = "/api/impbd/inward/fetch", method = RequestMethod.POST)
   public BaseResponse<List<InwardServiceReportModel>> search(
         @Validated(value = InwardSearchValidationGroup.class) @RequestBody InwardServiceReportModel fetchinwardService)
         throws CustomException {
      BaseResponse<List<InwardServiceReportModel>> inwardServiceRequest = utilitiesModel.getBaseResponseInstance();
      inwardServiceRequest.setData(inwardServiceReportService.fetch(fetchinwardService));
      return inwardServiceRequest;
   }

   @ApiOperation("add discrepancy")
   @PostRequest(value = "/api/impbd/inward/add", method = RequestMethod.POST)
   public BaseResponse<InwardServiceReportModel> add(
         @Validated(value = InwardServiceValidationGroup.class) @RequestBody InwardServiceReportModel requestModel)
         throws CustomException {
      BaseResponse<InwardServiceReportModel> inwardServiceRequest = utilitiesModel.getBaseResponseInstance();
      requestModel.setKey(requestModel.getFlightNumber());
      requestModel.setDate1(requestModel.getFlightDate());
         inwardServiceReportService.createServiceReportForSave(requestModel);
         inwardServiceRequest.setData(requestModel);
      
      return inwardServiceRequest;
   }

   @ApiOperation("finalize discrepancy")
   @PostRequest(value = "/api/impbd/inward/finalize", method = RequestMethod.POST)
   public BaseResponse<InwardServiceReportModel> finalize(@RequestBody InwardServiceReportModel requestModel)
         throws CustomException {
      BaseResponse<InwardServiceReportModel> inwardServiceRequest = utilitiesModel.getBaseResponseInstance();
      InwardsFlightServiceReportEvent flightEvent = new InwardsFlightServiceReportEvent();
      flightEvent.setFlightId(requestModel.getFlightId());
      flightEvent.setShipmentType("AWB");
      flightEvent.setCreatedBy("SYSADMIN");
      flightEvent.setCreatedOn(LocalDateTime.now());
      flightproducer.publish(flightEvent);
      inwardServiceReportService.finalizeServiceReportForCargo(requestModel);
      inwardServiceRequest.setData(requestModel);
      return inwardServiceRequest;
   }

   @ApiOperation("finalize discrepancy")
   @PostRequest(value = "/api/impbd/inward/finalizeservicereport", method = RequestMethod.POST)
   public BaseResponse<InwardServiceReportModel> finalizeservice(@RequestBody InwardServiceReportModel requestModel)
         throws CustomException {
      BaseResponse<InwardServiceReportModel> inwardServiceRequest = utilitiesModel.getBaseResponseInstance();
      inwardServiceReportService.finalizeServiceReportForMail(requestModel);
      inwardServiceRequest.setData(requestModel);
      return inwardServiceRequest;
   }
   
   @ApiOperation("Send Email")
   @PostRequest(value="/api/impbd/inward/sendattachment",method = RequestMethod.POST)
  public BaseResponse<InwardServiceReportModel> sendemailReport(@RequestBody InwardServiceReportModel requestModel) throws CustomException{
	   BaseResponse<InwardServiceReportModel> inwardServiceRequest = utilitiesModel.getBaseResponseInstance();
	      inwardServiceReportService.sendemailReport(requestModel);
	      inwardServiceRequest.setData(requestModel);
	return inwardServiceRequest;
	   }
   
   @ApiOperation("get AwbDetails")
   @PostRequest(value="/api/impbd/inward/getshipdetails",method = RequestMethod.POST)
   public BaseResponse<InwardServiceReportShipmentDiscrepancyModel> getautoawbdetails(@RequestBody InwardServiceReportShipmentDiscrepancyModel requestModel) throws CustomException{
	   BaseResponse<InwardServiceReportShipmentDiscrepancyModel> resp  = utilitiesModel.getBaseResponseInstance();
	   inwardServiceReportService.getAwbDetails(requestModel);
	   resp.setData(requestModel);
	return resp;
	
	   
   }
}