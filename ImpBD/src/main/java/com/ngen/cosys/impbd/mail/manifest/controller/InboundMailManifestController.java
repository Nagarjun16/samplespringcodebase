package com.ngen.cosys.impbd.mail.manifest.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidator;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.AirmailStatusEventParentModel;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestModel;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestShipmentInventoryInfoModel;
import com.ngen.cosys.impbd.mail.manifest.service.InboundMailManifestService;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailManifestValidationGroup;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
@RequestMapping("/api/mail/manifest")
public class InboundMailManifestController {

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private InboundMailManifestService service;

	@Autowired
	private MoveableLocationTypesValidator moveableLocationTypesValidator;

	@Autowired
	AirmailStatusEventProducer producer;

	private static final Logger logger = LoggerFactory.getLogger(InboundMailManifestController.class);

	@ApiOperation(value = "Api which allows user to get mail manifest information")
	@RequestMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public BaseResponse<InboundMailManifestModel> search(
			@Validated(value = InboundMailManifestValidationGroup.class) @RequestBody InboundMailManifestModel requestModel)
			throws CustomException {
		BaseResponse<InboundMailManifestModel> response = beanFactory.getBean(BaseResponse.class);
		InboundMailManifestModel responseModel = service.search(requestModel);
		response.setData(responseModel);
		return response;
	}

	@ApiOperation(value = "Api which allows user to save the mail manifest information")
	@RequestMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public BaseResponse<InboundMailManifestModel> manifest(
			@Validated(value = InboundMailManifestValidationGroup.class) @RequestBody InboundMailManifestModel requestModel)
			throws CustomException {
		BaseResponse<InboundMailManifestModel> response = beanFactory.getBean(BaseResponse.class);
		InboundMailManifestModel responseModel = service.manifest(requestModel);
		response.setData(responseModel);
		return response;
	}

	@ApiOperation(value = "Api which allows user to transfer mail to to CN 46")
	@RequestMapping(value = "/transferToCN46", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public BaseResponse<InboundMailManifestModel> transferToCN46(
			@Validated(value = InboundMailManifestValidationGroup.class) @RequestBody InboundMailManifestModel requestModel)
			throws CustomException {
		BaseResponse<InboundMailManifestModel> response = beanFactory.getBean(BaseResponse.class);
		InboundMailManifestModel responseModel = service.transferToCN46(requestModel);
		response.setData(responseModel);
		return response;
	}

	@ApiOperation(value = "Api which allows user to transfer mail to service report")
	@RequestMapping(value = "/transferToServiceReport", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public BaseResponse<InboundMailManifestModel> transferToServiceReport(
			@Validated(value = InboundMailManifestValidationGroup.class) @RequestBody InboundMailManifestModel requestModel)
			throws CustomException {
		BaseResponse<InboundMailManifestModel> response = beanFactory.getBean(BaseResponse.class);
		InboundMailManifestModel responseModel = service.transferToServiceReport(requestModel);
		response.setData(responseModel);
		return response;
	}

	@ApiOperation(value = "Api which allows user to mark document complete at flight level")
	@RequestMapping(value = "/documentComplete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public BaseResponse<InboundMailManifestModel> documentComplete(
			@Validated(value = InboundMailManifestValidationGroup.class) @RequestBody InboundMailManifestModel requestModel)
			throws CustomException {
		BaseResponse<InboundMailManifestModel> response = beanFactory.getBean(BaseResponse.class);
		InboundMailManifestModel responseModel = service.documentComplete(requestModel);
		response.setData(responseModel);
		return response;
	}

	@ApiOperation(value = "Api which allows user to mark break down complete at flight level")
	@RequestMapping(value = "/breakDownComplete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public BaseResponse<InboundMailManifestModel> breakDownComplete(
			@Validated(value = InboundMailManifestValidationGroup.class) @RequestBody InboundMailManifestModel requestModel)
			throws CustomException {
		BaseResponse<InboundMailManifestModel> response = beanFactory.getBean(BaseResponse.class);
		InboundMailManifestModel responseModel = service.breakDownComplete(requestModel);
		response.setData(responseModel);
		// Code commented by neel because it is asked by SATS to move import messages
		// triggering point to breakdown save
		/*
		 * if (!requestModel.getCheckData()) { AirmailStatusEventParentModel
		 * eventParentmodel = new AirmailStatusEventParentModel();
		 * List<AirmailStatusEvent> eventList = new ArrayList<>();
		 * requestModel.getMailBagInfo().forEach(req -> { if
		 * (Objects.nonNull(req.getShipmentId())) { AirmailStatusEvent event = new
		 * AirmailStatusEvent(); event.setSourceTriggerType("IMPORTBREAKDOWN"); if
		 * (req.getDestination().equalsIgnoreCase(requestModel.getTenantId())) {
		 * event.setImportExportIndicator("I"); } else {
		 * event.setImportExportIndicator("T"); }
		 * event.setBreakDownLocation(req.getUldKey());
		 * event.setStoreLocation(req.getStorageLocation());
		 * event.setNextDestination(req.getNextDestination());
		 * event.setBup(req.isBup());
		 * event.setManifestedUldKey(req.getStorageLocation());
		 * event.setShipmentId(req.getShipmentId());
		 * event.setFlightId(requestModel.getFlightId());
		 * event.setPieces(req.getPieces()); event.setWeight(req.getWeight());
		 * event.setStatus("CREATED"); event.setCreatedOn(requestModel.getCreatedOn());
		 * event.setCreatedBy(requestModel.getCreatedBy());
		 * event.setTenantId(requestModel.getTenantId());
		 * event.setShipmentNumber(req.getShipmentNumber());
		 * event.setCarrierCode(requestModel.getCarrierCode());
		 * event.setMailBag(req.getMailBagNumber());
		 * event.setTransferCarrierTo(req.getTransferCarrierTo());
		 * event.setEmbargo("Y".equalsIgnoreCase(req.getEmbargoFlag()) ? true : false);
		 * eventList.add(event); } }); eventParentmodel.setAllMessage(eventList);
		 * logger.warn("data before triggering import breakdown: " + eventParentmodel);
		 * producer.publish(eventParentmodel); }
		 */
		return response;
	}

	@ApiOperation(value = "Api which checks for the destination of the container")
	@RequestMapping(value = "/checkContainerDestination", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public BaseResponse<List<InboundMailManifestShipmentInventoryInfoModel>> checkContainerDestination(
			@Validated(value = InboundMailManifestValidationGroup.class) @RequestBody List<InboundMailManifestShipmentInventoryInfoModel> requestModel)
			throws CustomException {
		BaseResponse<List<InboundMailManifestShipmentInventoryInfoModel>> response = beanFactory
				.getBean(BaseResponse.class);
		List<InboundMailManifestShipmentInventoryInfoModel> responseModel = service
				.checkContainerDestination(requestModel);
		response.setData(responseModel);
		return response;
	}

	@ApiOperation(value = "Api which allows user to mark break down complete at flight level")
//	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.AIRMAIL_IMPORT)
	@RequestMapping(value = "/updateLocation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public BaseResponse<List<InboundMailManifestShipmentInventoryInfoModel>> updateLocation(
			@Validated(value = InboundMailManifestValidationGroup.class) @RequestBody List<InboundMailManifestShipmentInventoryInfoModel> requestModel)
			throws CustomException {
		BaseResponse<List<InboundMailManifestShipmentInventoryInfoModel>> response = beanFactory
				.getBean(BaseResponse.class);
		List<InboundMailManifestShipmentInventoryInfoModel> responseModel = service.updateLocation(requestModel);
		response.setData(responseModel);
		AirmailStatusEventParentModel eventParentmodel = new AirmailStatusEventParentModel();
		List<AirmailStatusEvent> eventList = new ArrayList<>();
		requestModel.forEach(obj -> {
			if (!MultiTenantUtility.isTenantCityOrAirport(obj.getDestinationCity())) {
				try {
					AirmailStatusEvent event = new AirmailStatusEvent();
					event.setSourceTriggerType("IMPORTBREAKDOWNUPDATELOCATION");
					event.setCarrierCode(obj.getIncomingCarrier());
					event.setStoreLocation(obj.getStorageLocation());
					MoveableLocationTypeModel movableLocationModel = new MoveableLocationTypeModel();
					if (!StringUtils.isEmpty(obj.getStorageLocation())) {
						movableLocationModel.setKey(obj.getStorageLocation());
						movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
						event.setStoreLocationType(movableLocationModel.getLocationType());
					}
					event.setPreviousStoreLocation(obj.getExistingShipmentLocation());
					if (!StringUtils.isEmpty(obj.getExistingShipmentLocation())) {
						movableLocationModel.setKey(obj.getExistingShipmentLocation());
						movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
						event.setPreviousStoreLocationType(movableLocationModel.getLocationType());
					}
					event.setNextDestination(obj.getNextDestination());
					event.setMailBag(obj.getMailBagNumber());
					event.setTenantId(obj.getTenantAirport());
					event.setStatus("CREATED");
					event.setCreatedBy(obj.getCreatedBy());
					event.setCreatedOn(obj.getCreatedOn());
					eventList.add(event);
				} catch (Exception e) {
				}
			}
		});
		if (!CollectionUtils.isEmpty(eventList)) {
			eventParentmodel.setAllMessage(eventList);
			logger.warn("data before triggering import breakdown: " + eventParentmodel);
			producer.publish(eventParentmodel);
		}
		return response;
	}
}