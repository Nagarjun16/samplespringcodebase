package com.ngen.cosys.impbd.workinglist.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.customs.api.enums.CustomsEventTypes;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent;
import com.ngen.cosys.impbd.events.producer.SingaporeCustomsDataSyncProducer;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListShipmentResult;
import com.ngen.cosys.impbd.workinglist.model.SendTSMMessage;
import com.ngen.cosys.impbd.workinglist.service.BreakDownWorkingListService;
import com.ngen.cosys.validators.BreakDownWorkListValidationGroup;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * BreakDownWorkingListController.java , this controller is responsible for the
 * breakdown working list of flight
 *
 * @author NIIT Technologies Ltd
 */
@NgenCosysAppInfraAnnotation(path = "/api/impbd/bdworklist", values = { BreakDownWorkListValidationGroup.class })
public class BreakDownWorkingListController {

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private BreakDownWorkingListService arrivalManifestFlightService;
   
   @Autowired
   private SingaporeCustomsDataSyncProducer singaporeCustomsDataSyncProducer;
   

   /**
    * this controller method returns the Breakdown working list
    * 
    * @param arrivalManifestFlight
    * @return
    * @throws CustomException
    */
   @ApiOperation("get breakdown working list of flight")
   @RequestMapping(value = "/getbreakdownworkinglist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<BreakDownWorkingListModel> getBreakDownWorkingList(
         @ApiParam("ArrivalManifestByFlight") @Validated(value = BreakDownWorkListValidationGroup.class) @RequestBody BreakDownWorkingListModel breakDownWorkingListModel)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<BreakDownWorkingListModel> response = utility.getBaseResponseInstance();
      breakDownWorkingListModel = arrivalManifestFlightService.getBreakDownWorkingList(breakDownWorkingListModel);
      response.setData(breakDownWorkingListModel);
      return response;
   }

   public BaseResponse<String> performBreakDownComplete() {
      return null;
   }

   @ApiOperation("update flight delay for shipment")
   @RequestMapping(value = "/updateflightdelay", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<String> updateDelayForAllShipment(
         @RequestBody List<BreakDownWorkingListShipmentResult> breakDownWorkingListShipmentResult)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<String> response = utility.getBaseResponseInstance();
      arrivalManifestFlightService.updateFlightDelayForShipments(breakDownWorkingListShipmentResult);
      return response;
   }

   @ApiOperation("Breakdown completd after checking the all Shipments recevied")
   @RequestMapping(value = "/breakdownComplete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<String> breakdownComplete(@RequestBody BreakDownWorkingListModel breakDownWorkingListFlightData)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<String> response = utility.getBaseResponseInstance();
      
      BreakDownWorkingListModel breakDownWorkingListModel = arrivalManifestFlightService.breakDownComplete(breakDownWorkingListFlightData);
      
      // Raise event for sync customs information
      SingaporeCustomsDataSyncEvent singaporeCustomsDataSyncEvent = new SingaporeCustomsDataSyncEvent();
      singaporeCustomsDataSyncEvent.setFlightId(breakDownWorkingListFlightData.getFlightId());
      singaporeCustomsDataSyncEvent.setFlightKey(breakDownWorkingListFlightData.getFlightNumber());
      singaporeCustomsDataSyncEvent.setFlightDate(breakDownWorkingListFlightData.getFlightDate());
      singaporeCustomsDataSyncEvent.setTenantId(breakDownWorkingListFlightData.getTenantAirport());
      singaporeCustomsDataSyncEvent.setEventType(CustomsEventTypes.Type.INBOUND_BREAK_DOWN);
      singaporeCustomsDataSyncEvent.setCreatedDate(LocalDateTime.now());
      singaporeCustomsDataSyncEvent.setCreatedBy(breakDownWorkingListFlightData.getLoggedInUser());

      // Invoke the event
      this.singaporeCustomsDataSyncProducer.publish(singaporeCustomsDataSyncEvent);
      
      //Initialize Flight Complete Event
      if(breakDownWorkingListModel.getDocumentCompleted()) {
    	  arrivalManifestFlightService.breakdownWorkingListFlightCompleteEventInitialize(breakDownWorkingListModel);
      }
      

      return response;
   }

   @ApiOperation("ReopenBreakdown completd after checking the all Shipments recevied")
   @RequestMapping(value = "/ReopenbreakdownComplete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<String> reopenbreakdownComplete(
         @RequestBody BreakDownWorkingListModel breakDownWorkingListFlightData) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<String> response = utility.getBaseResponseInstance();
      arrivalManifestFlightService.reOpenBreakDownComplete(breakDownWorkingListFlightData);
      return response;
   }
   
   @ApiOperation("ReopenBreakdown completd after checking the all Shipments recevied")
   @RequestMapping(value = "/sendLhRcfNfd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<String> sendLhRcfNfdReport(
         @RequestBody BreakDownWorkingListModel breakDownWorkingListFlightData) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<String> response = utility.getBaseResponseInstance();
      arrivalManifestFlightService.sendLhRcfNfdReport(breakDownWorkingListFlightData);
      return response;
   }
   
   @RequestMapping(path = "/ManualsendTsm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<String> sendTSMManually(@RequestBody SendTSMMessage requestModel)
         throws CustomException {

      @SuppressWarnings("unchecked")
      BaseResponse<String> response = utility.getBaseResponseInstance();
      this.arrivalManifestFlightService.validateDataAndTriggerEvents(requestModel);
      response.setConfirmMessage(true);
      response.setSuccess(true);
      return response;
   }
   
   @RequestMapping(path = "/reSendSegregationReport", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<String> reSendSegregationReport(@RequestBody BreakDownWorkingListModel breakDownWorkingListFlightData)
         throws CustomException {

      @SuppressWarnings("unchecked")
      BaseResponse<String> response = utility.getBaseResponseInstance();
      
      arrivalManifestFlightService.reSendSegregationReport(breakDownWorkingListFlightData);
      
      response.setConfirmMessage(true);
      response.setSuccess(true);
      return response;
   }

}