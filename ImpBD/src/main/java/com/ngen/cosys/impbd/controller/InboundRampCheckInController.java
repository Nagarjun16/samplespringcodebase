package com.ngen.cosys.impbd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.flightdiscrepancylist.validators.FlightValidatorGroup;
import com.ngen.cosys.impbd.mail.validator.group.ContainerValidatorGroup;
import com.ngen.cosys.impbd.mail.validator.group.RampCheckinValidationGroup;
import com.ngen.cosys.impbd.model.RampCheckInDetails;
import com.ngen.cosys.impbd.model.RampCheckInList;
import com.ngen.cosys.impbd.model.RampCheckInModel;
import com.ngen.cosys.impbd.model.RampCheckInParentModel;
import com.ngen.cosys.impbd.model.RampCheckInPiggyback;
import com.ngen.cosys.impbd.model.RampCheckInPiggybackModel;
import com.ngen.cosys.impbd.model.RampCheckInSearchFlight;
import com.ngen.cosys.impbd.model.RampCheckInUld;
import com.ngen.cosys.impbd.model.RampCheckInUldModel;
import com.ngen.cosys.impbd.service.RampCheckInService;
import com.ngen.cosys.validators.InboundRampValidationGroup;

import io.swagger.annotations.ApiOperation;

/**
 * A staff who is responsible to capture ULD information which is towed to their
 * respective terminals can use this APIs to see and update information of
 * respective ULDs in a specific flight
 * 
 * @author NIIT Technologies
 *
 */
@NgenCosysAppInfraAnnotation
@Validated
public class InboundRampCheckInController {

   @Autowired
   private RampCheckInService rampCheckInService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @ApiOperation("Search for Flight to see ULD information for CheckIn")
   @RequestMapping(value = "api/rampcheckin/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInDetails> search(@Validated(value = FlightValidatorGroup.class) @RequestBody RampCheckInSearchFlight query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInDetails> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.fetch(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Check-In ULD by Ramp Staff")
   @RequestMapping(value = "api/ramp-check-in/updatedata", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> updateData(@Validated(ContainerValidatorGroup.class) @RequestBody RampCheckInModel query) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.updateAll(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Un_Check-In Complete")
   @RequestMapping(value = "api/ramp-check-in/uncheckin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> unCheckin(@Valid @RequestBody RampCheckInModel query) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.unCheckIn(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Check-In New ULD")
   @RequestMapping(value = "api/ramp-check-in/create-uld", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInParentModel> createUld(
         @Validated(value = RampCheckinValidationGroup.class) @RequestBody RampCheckInParentModel query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInParentModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.createUld(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;

   }

   @ApiOperation("Remove manually added ULD")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/delete-uld", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> deleteUld(@Valid @RequestBody RampCheckInModel query) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.delete(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Check-In Complete")
   @RequestMapping(value = "api/ramp-check-in/check-in", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> checkin(@Valid @RequestBody RampCheckInModel query) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.checkIn(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Re-Open Check-In Complete")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/reopen", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> reopen(@Valid @RequestBody RampCheckInModel query) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.reopen(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Capture Driver Information")
   @RequestMapping(value = "api/ramp-check-in/update-driver", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> updatedriver(@Validated(value = RampCheckinValidationGroup.class)  @RequestBody RampCheckInModel query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.assignDriver(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("List Piggyback ULD")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/get-piggyback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<RampCheckInPiggyback>> getPiggyback(@Valid @RequestBody RampCheckInPiggyback query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<RampCheckInPiggyback>> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.getPiggyback(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Capture Piggyback ULD")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/update-piggyback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<RampCheckInPiggyback>> maintainPiggyback(
         @Valid @RequestBody List<RampCheckInPiggyback> query) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<RampCheckInPiggyback>> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.managePiggyback(query));
      return rampCheckInData;
   }

   @ApiOperation("Update Bulk")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/update-bulk", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInDetails> updateBulk(@Valid @RequestBody RampCheckInDetails query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInDetails> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.updateBulkStatus(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Capture Driver Information")
   @RequestMapping(value = "api/ramp-check-in/update-driverId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInUldModel> updatedriverId(@Valid @RequestBody RampCheckInUldModel query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInUldModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.assignDriverId(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Search for Flight to see ULD information for CheckIn")
   @RequestMapping(value = "api/rampcheckin/searchList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInList> searchList(@Valid @RequestBody RampCheckInSearchFlight query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInList> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.fetchList(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Capture Piggyback ULD")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/update-piggybackList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInPiggybackModel> maintainPiggybackList(
         @Validated({ InboundRampValidationGroup.class }) @RequestBody RampCheckInPiggybackModel query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInPiggybackModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.maintainPiggybackList(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("List Piggyback ULD")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/get-piggybackList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInPiggybackModel> getPiggybackList(@Valid @RequestBody RampCheckInPiggybackModel query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInPiggybackModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.getPiggybackList(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Delete Piggyback ULD")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/delete-piggybackList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInPiggybackModel> deletePiggybackList(
         @Valid @RequestBody RampCheckInPiggybackModel query) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInPiggybackModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.deletePiggybackList(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Check-In Complete")
   @NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.INBOUND_RAMP_CEHCKIN)
   @RequestMapping(value = "api/ramp-check-in/check-in-data", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> checkinData(@Valid @RequestBody RampCheckInModel query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.checkInData(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Check-In ULD by Ramp StafflIST")
   @RequestMapping(value = "api/ramp-check-in/updatedataList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> updateDataList(@Valid @RequestBody RampCheckInModel query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.updateAllList(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }

   @ApiOperation("Check-In ULD by Ramp StafflIST")
   @RequestMapping(value = "api/ramp-check-in/carriercheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<Integer> checkCarrierGroup(@Valid @RequestBody RampCheckInUld query) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<Integer> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.checkCarrierCode(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }
   
   @ApiOperation("Capture Temperature Log Entry")
   @RequestMapping(value = "api/ramp-check-in/save-temperature-log", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<RampCheckInModel> saveTemperature(@Validated(value = RampCheckinValidationGroup.class)  @RequestBody RampCheckInModel query)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<RampCheckInModel> rampCheckInData = utilitiesModelConfiguration.getBaseResponseInstance();
      rampCheckInData.setData(rampCheckInService.saveAllULDTemperature(query));
      rampCheckInData.setMessageList(query.getMessageList());
      rampCheckInData.setSuccess(true);
      return rampCheckInData;
   }
   
}