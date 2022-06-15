/**
 * 
 * MaintainFWBController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 23 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.ChargeableEntity;
import com.ngen.cosys.cargoprocessingengine.model.CargoProcessingEngineModel;
import com.ngen.cosys.cargoprocessingengine.service.CloseShipmentFailureService;
import com.ngen.cosys.cargoprocessingengine.service.ValidateChinaCustomService;
import com.ngen.cosys.events.payload.SendManuallyCreatedFreightWayBillStoreEvent;
import com.ngen.cosys.events.producer.SendManuallyCreatedFreightWayBillStoreEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;
import com.ngen.cosys.processing.engine.rule.fact.FactShipment;
import com.ngen.cosys.processing.engine.rule.triggerpoint.OutboundMessage;
import com.ngen.cosys.rule.engine.util.RuleEngineExecutor;
import com.ngen.cosys.shipment.model.FWB;
import com.ngen.cosys.shipment.model.FWBDetails;
import com.ngen.cosys.shipment.model.FetchFWBRequest;
import com.ngen.cosys.shipment.model.FetchRouting;
import com.ngen.cosys.shipment.model.Routing;
import com.ngen.cosys.shipment.service.MaintainFWBService;
import com.ngen.cosys.shipment.validators.FetchRoutingValidatorGroup;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;
import com.ngen.cosys.shipment.validators.SearchFWBGroup;
import com.ngen.cosys.shipment.validators.SearchNonIataFWBGroup;
import com.ngen.cosys.validator.dao.UserValidForCarrierDao;

import io.swagger.annotations.ApiOperation;

/**
 * This class is controller for MaintainFWB.
 * 
 * @author NIIT Technologies Ltd
 */

// @NgenCosysAppInfraAnnotation(values= {SearchFWBGroup.class})
@NgenCosysAppInfraAnnotation
public class MaintainFWBController {

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(MaintainFWBController.class);

   @Autowired
   private MaintainFWBService maintainFWBService;
  
   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;
   
   @Autowired
   SendManuallyCreatedFreightWayBillStoreEventProducer producer;
   
   @Autowired
   ValidateChinaCustomService validatechinacustom;
	   
   @Autowired
   CloseShipmentFailureService closeshipmentfailure;
   
   @Autowired
   RuleEngineExecutor ruleExecutor;
   
   @Autowired
   private UserValidForCarrierDao userValidForCarrierDao;


   private BaseResponse<FWB> performGet(FetchFWBRequest fetchFWBRequest, BaseResponse<FWB> fwbDetailsRS)
         throws CustomException {
	   FWB responseModel=new FWB();
	   Map<String, Object> parameterMap = new HashMap<>();
	      parameterMap.put("loggedInUser", fetchFWBRequest.getLoggedInUser());
	      parameterMap.put("shipmentNumber", fetchFWBRequest.getAwbNumber());
	      parameterMap.put("type", "AWB");
	      System.out.println(userValidForCarrierDao.isUserValidForCarrier(parameterMap));
		if (!userValidForCarrierDao.isUserValidForCarrier(parameterMap)) {
			System.out.println(userValidForCarrierDao.isUserValidForCarrier(parameterMap));
			responseModel.addError("user.not.authorized", "Restricted Airline",
					ErrorType.ERROR);
			responseModel.setRestrictedAirline(true);
			fwbDetailsRS.setData(responseModel);
			return fwbDetailsRS;
		}
      responseModel = maintainFWBService.get(fetchFWBRequest);
      fwbDetailsRS.setData(responseModel);
      return fwbDetailsRS;
   }

   @ApiOperation("Maintain FWB")
   @PostRequest(value = "/api/shpmng/fwb/get", method = RequestMethod.POST)
   public BaseResponse<FWB> get(@Validated(SearchFWBGroup.class) @RequestBody FetchFWBRequest fetchFWBRequest)
         throws CustomException {
      BaseResponse<FWB> fwbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
      return performGet(fetchFWBRequest, fwbDetailsRS);
   }

   @ApiOperation("Maintain FWB")
   @PostRequest(value = "/api/shpmng/fwb/get/noniata", method = RequestMethod.POST)
   public BaseResponse<FWB> getNonIata(
         @Validated(SearchNonIataFWBGroup.class) @RequestBody FetchFWBRequest fetchFWBRequest) throws CustomException {
      BaseResponse<FWB> fwbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
      return performGet(fetchFWBRequest, fwbDetailsRS);
   }

   @ApiOperation("Maintain FWB")
   @PostRequest(value = "/api/shpmng/fwb/save", method = RequestMethod.POST)
   public BaseResponse<FWB> save(@Validated(MaintainFreightWayBillValidator.class) @RequestBody FWB requestModel)
         throws CustomException {
      BaseResponse<FWB> fwbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         FWB responseModel = maintainFWBService.save(requestModel);
         fwbDetailsRS.setData(responseModel);
         sendFWBMessage(requestModel);
     	try {
			// Validating the OCI and Shipper/Consignee Details for CHINA Custom shipments
			// and closing the failure if valid.
			if (requestModel != null && requestModel.getAwbNumber() != null
					&& requestModel.getAwbDate() != null) {
				CargoProcessingEngineModel shipment = new CargoProcessingEngineModel();
				shipment.setShipmentNumber(requestModel.getAwbNumber());
				shipment.setShipmentDate(requestModel.getAwbDate());
				shipment.setProcessAreaCode("CHINA_CUSTOM");
				boolean isvalid = validatechinacustom.validateChinaCustom(shipment);
				if (!isvalid) {
					List<Integer> failureids = closeshipmentfailure.getFailureId(shipment);

					closeshipmentfailure.closeShipmentFailure(failureids, requestModel.getCreatedOn(),
							requestModel.getCreatedBy());

				}
			}
		} catch (Exception e) {
		}
         
      } catch (CustomException e) {
         fwbDetailsRS.setData(requestModel);
         lOgger.error(EXCEPTION, e);
      }
      return fwbDetailsRS;
   }

   @ApiOperation("Maintain FWB")
   @PostRequest(value = "/api/shpmng/fwb/delete", method = RequestMethod.POST)
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB)
   public BaseResponse<FWB> delete(@RequestBody FWB requestModel) throws CustomException {
      BaseResponse<FWB> fwbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
		try {
			maintainFWBService.delete(requestModel);
			fwbDetailsRS.setData(requestModel);

			ChargeableEntity chargeableEntity = new ChargeableEntity();
			chargeableEntity.setEventType(ChargeEvents.EXP_FWB_UPDATE);
			chargeableEntity.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
			chargeableEntity.setReferenceId(BigInteger.valueOf(requestModel.getShipmentFreightWayBillId()));
			chargeableEntity.setReferenceType(ReferenceType.FWB_ID.getReferenceType());
			chargeableEntity.setAdditionalReferenceId(BigInteger.valueOf(requestModel.getShipmentFreightWayBillId()));
			chargeableEntity.setAdditionalReferenceType(ReferenceType.FWB_ID.getReferenceType());
			chargeableEntity.setHandlingTerminal(requestModel.getTerminal());
			chargeableEntity.setUserCode(requestModel.getLoggedInUser());
			Charge.cancelCharge(chargeableEntity);
		} catch (CustomException e) {
			fwbDetailsRS.setData(requestModel);
			fwbDetailsRS.setSuccess(false);
          lOgger.error(EXCEPTION, e);
      }
      return fwbDetailsRS;
   }

   @ApiOperation("Maintain FWB")
   @PostRequest(value = "/api/shpmng/fwb/fetchRouting", method = RequestMethod.POST)
   public BaseResponse<Routing> fetchRouting(
         @Validated(FetchRoutingValidatorGroup.class) @RequestBody FetchRouting requestModel) throws CustomException {
   BaseResponse<Routing> fwbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
      Routing responseModel = maintainFWBService.fetchRoutingDetails(requestModel);
    // List<RoutingResponseModel> responseModel = docAWBService.routeDetails(requestModel);
      fwbDetailsRS.setData(responseModel);
      fwbDetailsRS.setMessageList(requestModel.getMessageList());
      return fwbDetailsRS;
      
      /*   
      BaseResponse<List<RoutingResponseModel>> awbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
      // Derive the routing information
      List<RoutingResponseModel> responseModel = docAWBService.routeDetails(requestModel);
      awbDetailsRS.setData(responseModel);
      return awbDetailsRS;*/
   }
   @ApiOperation("Maintain FWB")
   @PostRequest(value = "/api/shpmng/fwb/getFWBDetails", method = RequestMethod.POST)
   public BaseResponse<FWBDetails> getFWBDetailsForMobile(@Valid @RequestBody FWBDetails fetchFWBRequest)
         throws CustomException {
      BaseResponse<FWBDetails> fwbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
      FWBDetails responseModel = maintainFWBService.fetchFWBDetailsForMobile(fetchFWBRequest);
      fwbDetailsRS.setData(responseModel);
      return fwbDetailsRS;
   }

   @ApiOperation("Maintain FWB")
   @PostRequest(value = "/api/shpmng/fwb/saveFWBDetails", method = RequestMethod.POST)
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB)
   public BaseResponse<FWBDetails> saveFWBDetailsForMobile(@RequestBody FWBDetails fetchFWBRequest)
         throws CustomException {
      BaseResponse<FWBDetails> fwbDetailsRS = utilitiesModelConfiguration.getBaseResponseInstance();
      FWBDetails responseModel = maintainFWBService.saveFWBDetailsForMobile(fetchFWBRequest);
      fwbDetailsRS.setData(responseModel);
      return fwbDetailsRS;
   }
   
   /**
    * @param requestModel
    * @throws CustomException 
    */
   private void sendFWBMessage(FWB requestModel) throws CustomException {
	   SendManuallyCreatedFreightWayBillStoreEvent event = new SendManuallyCreatedFreightWayBillStoreEvent();
      event.setCarrier(requestModel.getRouting().get(0).getCarrierCode());
      event.setShipmentNumber(requestModel.getAwbNumber());
      event.setShipmentDate(requestModel.getAwbDate());
      event.setCreatedBy(requestModel.getCreatedBy());
      event.setCreatedOn(LocalDateTime.now());
      event.setLastModifiedBy(requestModel.getModifiedBy());
      event.setLastModifiedOn(LocalDateTime.now());
      // Rule Engine Execution call for ACAS Shipment to trigger FWB Message to CCN
      // Interface
      event.setAcasShipment(identifyACASShipment(requestModel));
      //
      producer.publish(event);
   }
   
   /**
    * @param requestModel
    * @return
    * @throws CustomException
    */
   private boolean identifyACASShipment(FWB requestModel) throws CustomException {
      // Payload Initialization
      FactPayload factPayload = new FactPayload();
      FactShipment factShipment = new FactShipment();
      factShipment.setShipmentNumber(requestModel.getAwbNumber());
      factShipment.setShipmentDate(requestModel.getAwbDate());
      factPayload.setFactShipment(factShipment);
      // Trigger Point & Operation
      factPayload.setTriggerPoint(OutboundMessage.class);
      factPayload.setTriggerPointOperation(com.ngen.cosys.processing.engine.rule.triggerpoint.operation.FWB.class);
      // Rules configured false
      /*
       * factPayload.setRulesConfigured(true); factPayload.setRulesPayload(new
       * ArrayList<>()); factPayload.getRulesPayload().add(ACASGroup.class);
       */
      // Set Audit Details
      factPayload.setCreatedBy(requestModel.getCreatedBy());
      factPayload.setCreatedOn(requestModel.getCreatedOn());
      factPayload.setModifiedBy(requestModel.getModifiedBy());
      factPayload.setModifiedOn(requestModel.getModifiedOn());
      //
      return ruleExecutor.initRuleEngineProcessForACAS(factPayload);
   }
}