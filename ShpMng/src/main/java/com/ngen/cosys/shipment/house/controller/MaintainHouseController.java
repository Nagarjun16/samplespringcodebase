/**
 * This class is controller for maintain house way bill .
 * 
 * @author NIIT Technologies Ltd.
 *
 */
package com.ngen.cosys.shipment.house.controller;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.BillingShipment;
import com.ngen.cosys.billing.api.model.ChargeableEntity;
import com.ngen.cosys.cargoprocessingengine.model.CargoProcessingEngineModel;
import com.ngen.cosys.cargoprocessingengine.service.CloseShipmentFailureService;
import com.ngen.cosys.cargoprocessingengine.service.ValidateChinaCustomService;
import com.ngen.cosys.dimension.model.Dimension;
import com.ngen.cosys.events.payload.SendManuallyCreatedFreightHouseWayBillStoreEvent;
import com.ngen.cosys.events.producer.SendManuallyCreatedFreightHouseWayBillStoreEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;
import com.ngen.cosys.processing.engine.rule.fact.FactShipment;
import com.ngen.cosys.processing.engine.rule.triggerpoint.OutboundMessage;
import com.ngen.cosys.processing.engine.rule.triggerpoint.operation.FHL;
import com.ngen.cosys.rule.engine.util.RuleEngineExecutor;
import com.ngen.cosys.shipment.enums.CRUDStatus;
import com.ngen.cosys.shipment.house.dao.MaintainHouseDAO;
import com.ngen.cosys.shipment.house.model.HouseModel;
import com.ngen.cosys.shipment.house.model.HouseSearch;
import com.ngen.cosys.shipment.house.model.MasterAirWayBillModel;
import com.ngen.cosys.shipment.house.service.MaintainHouseService;
import com.ngen.cosys.shipment.house.validator.HouseWayBillValidationGroup;
import com.ngen.cosys.shipment.house.validator.MAWBValidationGroup;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation(path = "/api/shipment/house")
public class MaintainHouseController {

	@Autowired
	private MaintainHouseService service;


	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	@Autowired
	SendManuallyCreatedFreightHouseWayBillStoreEventProducer producer;

	@Autowired
	RuleEngineExecutor ruleEngineExecutor;

	@Autowired
	ValidateChinaCustomService validatechinacustom;

	@Autowired
	CloseShipmentFailureService closeshipmentfailure;

	/**
	 * 1. Searching All details belongs to AWB Number
	 * 
	 * @param get
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Api method to retrieve MAWB and it's associated house information")
	@PostRequest(value = "/getMawbInfo", method = RequestMethod.POST)
	public BaseResponse<MasterAirWayBillModel> getMAWBInformation(
			@Validated(value = MAWBValidationGroup.class) @RequestBody MasterAirWayBillModel masterAWBRequestModel)
			throws CustomException {
		BaseResponse<MasterAirWayBillModel> maintainHouseListResponse = utilitiesModelConfiguration
				.getBaseResponseInstance();
		MasterAirWayBillModel awbDetail = service.getMasterAWBInformation(masterAWBRequestModel);
		if (masterAWBRequestModel.isRestrictedAirline()) {
			maintainHouseListResponse.setData(masterAWBRequestModel);

		} else {
			maintainHouseListResponse.setData(awbDetail);

		}

		return maintainHouseListResponse;
	}

	/**
	 * 2. Searching All details belongs to AWB Number
	 * 
	 * @param get
	 * @throws CustomException
	 */
	@ApiOperation(value = "Api method to retrieve house information")
	@PostRequest(value = "/getHouse", method = RequestMethod.POST)
	public BaseResponse<HouseModel> getHouse(@RequestBody HouseModel houseRequestModel) throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<HouseModel> maintainHouseListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
		HouseModel houseModel = service.getHouseAWBInformation(houseRequestModel);
		maintainHouseListResponse.setData(houseModel);
		return maintainHouseListResponse;
	}

	/**
	 * 3. inserting All details belongs to AWB Number
	 * 
	 * @param save
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Api method to save an house")
	@PostRequest(value = "/saveHouse", method = RequestMethod.POST)
//	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public BaseResponse<HouseModel> saveHouse(
			@ApiParam(value = "awbNumber", required = true) @Validated(value = HouseWayBillValidationGroup.class) @RequestBody HouseModel houseRequestModel)
			throws CustomException {
		BaseResponse<HouseModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
		HouseModel houseModel = service.save(houseRequestModel);
		if (CollectionUtils.isEmpty(houseModel.getMessageList())) {
			// SEND FHL Message to CCN Interface
			sendFHLMessage(houseRequestModel);
		}
		try {
			// Validating the OCI and Shipper/Consignee Details for CHINA Custom shipments
			// and closing the failure if valid.
			if (houseRequestModel != null && houseRequestModel.getAwbNumber() != null
					&& houseRequestModel.getAwbDate() != null) {
				CargoProcessingEngineModel shipment = new CargoProcessingEngineModel();
				shipment.setShipmentNumber(houseRequestModel.getAwbNumber());
				shipment.setShipmentDate(houseRequestModel.getAwbDate());
				shipment.setProcessAreaCode("CHINA_CUSTOM");
				boolean isvalid = validatechinacustom.validateChinaCustom(shipment);
				if (!isvalid) {
					List<Integer> failureids = closeshipmentfailure.getFailureId(shipment);

					closeshipmentfailure.closeShipmentFailure(failureids, houseRequestModel.getCreatedOn(),
							houseRequestModel.getCreatedBy());

				}
			}
		} catch (Exception e) {
		}
		response.setMessageList(houseModel.getMessageList());
		response.setData(houseModel);
		response.setSuccess(true);
		return response;
	}

	/**
	 * 4. deleting All details belongs to AWB Number
	 * 
	 * @param get
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Api method to delete an house")
	@PostRequest(value = "/deleteHouse", method = RequestMethod.POST)
//	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public BaseResponse<MasterAirWayBillModel> deleteHouse(
			@ApiParam(value = "awbNumber", required = true) @RequestBody MasterAirWayBillModel masterAWBRequestModel)
			throws CustomException {
		BaseResponse<MasterAirWayBillModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
		MasterAirWayBillModel masterAirWayBillModel = service.delete(masterAWBRequestModel);
		response.setData(masterAirWayBillModel);
		response.setSuccess(true);

		for (HouseModel houseDetails : masterAWBRequestModel.getMaintainHouseDetailsList()) {
			if (houseDetails.getSelectHAWB()) {
				ChargeableEntity chargeableEntity = new ChargeableEntity();
				chargeableEntity.setEventType(ChargeEvents.EXP_FHL_UPDATE);
				chargeableEntity.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
				chargeableEntity.setReferenceId(houseDetails.getId());
				chargeableEntity.setReferenceType(ReferenceType.FHL_ID.getReferenceType());
				chargeableEntity.setAdditionalReferenceId(houseDetails.getId());
				chargeableEntity.setAdditionalReferenceType(ReferenceType.FHL_ID.getReferenceType());
				chargeableEntity.setHandlingTerminal(masterAWBRequestModel.getTerminal());
				chargeableEntity.setUserCode(masterAWBRequestModel.getLoggedInUser());
				Charge.cancelCharge(chargeableEntity);
			}
		}

		return response;
	}

	/**
	 * @param houseRequestModel
	 * @throws CustomException
	 */
//	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	private void sendFHLMessage(HouseModel houseRequestModel) throws CustomException {
		//
		SendManuallyCreatedFreightHouseWayBillStoreEvent event = new SendManuallyCreatedFreightHouseWayBillStoreEvent();
		event.setCarrier(null);
		event.setShipmentNumber(houseRequestModel.getAwbNumber());
		event.setShipmentDate(houseRequestModel.getAwbDate());
		event.setHawbNumber(houseRequestModel.getHawbNumber());
		event.setCreatedBy(houseRequestModel.getCreatedBy());
		event.setCreatedOn(houseRequestModel.getCreatedOn());
		event.setLastModifiedBy(houseRequestModel.getModifiedBy());
		event.setLastModifiedOn(houseRequestModel.getModifiedOn());
		event.setAcasShipment(identifyACASShipment(houseRequestModel));
		//
		producer.publish(event);
	}

	/**
	 * @param requestModel
	 * @return
	 * @throws CustomException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	private boolean identifyACASShipment(HouseModel requestModel) throws CustomException {
		// Payload Initialization
		FactPayload factPayload = new FactPayload();
		FactShipment factShipment = new FactShipment();
		factShipment.setShipmentNumber(requestModel.getAwbNumber());
		factShipment.setShipmentDate(requestModel.getAwbDate());
		factPayload.setFactShipment(factShipment);
		// Trigger Point & Operation
		factPayload.setTriggerPoint(OutboundMessage.class);
		factPayload.setTriggerPointOperation(FHL.class);
		// Set Audit Details
		factPayload.setCreatedBy(requestModel.getCreatedBy());
		factPayload.setCreatedOn(requestModel.getCreatedOn());
		factPayload.setModifiedBy(requestModel.getModifiedBy());
		factPayload.setModifiedOn(requestModel.getModifiedOn());
		//
		return ruleEngineExecutor.initRuleEngineProcessForACAS(factPayload);
	}

	/**
	 * 5. get first shipper and consignee info AWB Number
	 * 
	 * @param get
	 * @throws CustomException
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Api method to retrieve first house shipper and consignee info")
	@PostRequest(value = "/getShipperAndconsigneeInfoForFirstHouse", method = RequestMethod.POST)
	public BaseResponse<HouseModel> getShipperAndconsigneeInfoForFirstHouse(@RequestBody HouseModel houseRequestModel)
			throws CustomException {
		BaseResponse<HouseModel> maintainHouseListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
		HouseModel houseModel = service.getShipperAndconsigneeInfoForFirstHouse(houseRequestModel);
		maintainHouseListResponse.setData(houseModel);
		return maintainHouseListResponse;
	}

	// HOUSE WAY BILL MASTER STARTS HERE

	/**
	 * Fetch House Way Bill Master Record
	 * 
	 * @param get
	 * @throws CustomException
	 */
	@ApiOperation(value = "Api method to retrieve house way bill list with hawb number list")
	@PostRequest(value = "/getHouseWayBillMaster", method = RequestMethod.POST)
	public BaseResponse<MasterAirWayBillModel> getHouseWayBillMaster(
			@Validated @RequestBody HouseSearch houseSearchRequest) throws CustomException {
		BaseResponse<MasterAirWayBillModel> maintainHouseListResponse = utilitiesModelConfiguration
				.getBaseResponseInstance();
		MasterAirWayBillModel houseModel = service.getHouseWayBillMaster(houseSearchRequest);
		// check if it is an Export Shipment and if we have access to display this
		// Screen

		maintainHouseListResponse.setData(houseModel);
		return maintainHouseListResponse;
	}

	@ApiOperation(value = "Api method to save an house")
	@PostRequest(value = "/setHouseWayBillMaster", method = RequestMethod.POST)
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public void setHouseWayBillMaster(
			@ApiParam(value = "awbNumber", required = true) @RequestBody @Valid MasterAirWayBillModel houseRequestModel)
			throws CustomException {
		BaseResponse<MasterAirWayBillModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
		service.setHouseWayBillMaster(houseRequestModel);

		// billing call
		BillingShipment billingShipment = new BillingShipment();
		billingShipment.setShipmentNumber(houseRequestModel.getAwbNumber());
		billingShipment.setShipmentDate(houseRequestModel.getAwbDate());
		billingShipment.setHandlingTerminal(houseRequestModel.getTerminal());
		billingShipment.setUserCode(houseRequestModel.getLoggedInUser());
		billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
		billingShipment.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
		billingShipment.setHandelByHouse(true);
		billingShipment.setHouseNumber(houseRequestModel.getHawbNumber());

		try {
			Charge.calculateCharge(billingShipment);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// HOUSE WAY BILL MASTER ENDS HERE

	// HOUSE WAY BILL LIST STARTS HERE

	/**
	 * Fetch House Way Bill List With AWB Number
	 * 
	 * @param get
	 * @throws CustomException
	 */
	@ApiOperation(value = "Api method to retrieve house way bill list with hawb number list")
	@PostRequest(value = "/getHouseWayBillList", method = RequestMethod.POST)
	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.MAINTAIN_HOUSE)
	public BaseResponse<MasterAirWayBillModel> getHouseWayBillList(
			@Validated @RequestBody HouseSearch houseSearchRequest) throws CustomException {
		BaseResponse<MasterAirWayBillModel> maintainHouseListResponse = utilitiesModelConfiguration
				.getBaseResponseInstance();
		MasterAirWayBillModel houseModel = null;

		try {
			houseModel = service.getHouseWayBillList(houseSearchRequest);
			maintainHouseListResponse.setData(houseModel);
		} catch (CustomException e) {
			maintainHouseListResponse.setData(houseModel);
			maintainHouseListResponse.setSuccess(false);

		}

		return maintainHouseListResponse;
	}

	@ApiOperation(value = "Api method to save an house")
	@PostRequest(value = "/onSaveHouseNumber", method = RequestMethod.POST)
//	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_HOUSE)
	public void onSaveHouseNumber(
			@ApiParam(value = "awbNumber", required = true) @RequestBody MasterAirWayBillModel houseRequestModel)
			throws CustomException {
		BaseResponse<MasterAirWayBillModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
		service.onSaveHouseNumber(houseRequestModel);

		for (HouseModel houseRecord : houseRequestModel.getMaintainHouseDetailsList()) {
			switch (houseRecord.getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				BillingShipment billingShipment = new BillingShipment();
				billingShipment.setShipmentId(houseRequestModel.getId());
				billingShipment.setShipmentNumber(houseRequestModel.getAwbNumber());
				billingShipment.setShipmentDate(houseRequestModel.getAwbDate());
				billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
				billingShipment.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
				billingShipment.setHandlingTerminal(houseRequestModel.getTerminal());
				billingShipment.setUserCode(houseRequestModel.getLoggedInUser());
				billingShipment.setHandelByHouse(true);
				billingShipment.setShipmentHouseId(houseRecord.getId());
				billingShipment.setHouseNumber(houseRecord.getHawbNumber());
				// calculate charge
				try {
					Charge.calculateCharge(billingShipment);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case CRUDStatus.CRUDType.UPDATE:
				BillingShipment billingShipment2 = new BillingShipment();
				billingShipment2.setShipmentId(houseRequestModel.getId());
				billingShipment2.setShipmentNumber(houseRequestModel.getAwbNumber());
				billingShipment2.setShipmentDate(houseRequestModel.getAwbDate());
				billingShipment2.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
				billingShipment2.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
				billingShipment2.setHandlingTerminal(houseRequestModel.getTerminal());
				billingShipment2.setUserCode(houseRequestModel.getLoggedInUser());
				billingShipment2.setHandelByHouse(true);
				billingShipment2.setShipmentHouseId(houseRecord.getId());
				billingShipment2.setHouseNumber(houseRecord.getHawbNumber());
				// calculate charge
				try {
					Charge.calculateCharge(billingShipment2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}

		/*
		 * if(houseRequestModel.getMaintainHouseDetailsList().size()>0) {
		 * List<HouseModel> list = houseRequestModel.getMaintainHouseDetailsList(); for
		 * (int i = 0; i < list.size(); i++) { HouseModel house = list.get(i);
		 * 
		 * /* 1. CNBilling API Call For Check Payment Status (N/F). | 2. Calculate
		 * Charge's For AWB/HWB.
		 * 
		 * if (house.isAppFeature(ApplicationFeatures.CNBilling.class)) { if
		 * (house.getHawbNumber() != null) { CNEntityInformation entityInformation = new
		 * CNEntityInformation();
		 * entityInformation.setShipmentNumber(house.getAwbNumber());
		 * entityInformation.setHouseNumber(house.getHawbNumber());
		 * ShipmentUtility.createOrSearchCNBillingCharge(entityInformation); } } } }
		 */
		/*
		 * if (CollectionUtils.isEmpty(houseModel.getMessageList())) { // SEND FHL
		 * Message to CCN Interface sendFHLMessage(houseRequestModel); }
		 */
		// response.setMessageList(houseModel.getMessageList());
		// response.setData(houseModel);
		// response.setSuccess(true);
		// return response;
	}

	@ApiOperation(value = "Api method to save an house")
	@PostRequest(value = "/onSaveCustomerinformation", method = RequestMethod.POST)
	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.MAINTAIN_HOUSE)
	public void onSaveCustomerinformation(
			@ApiParam(value = "awbNumber", required = true) @RequestBody HouseModel houseRequestModel)
			throws CustomException {
		BaseResponse<HouseModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
		service.onSaveCustomerinformation(houseRequestModel);


		if (!ObjectUtils.isEmpty(houseRequestModel.getConsignee())) {
			if (!ObjectUtils.isEmpty(houseRequestModel.getConsignee())) {
				if ((ObjectUtils.isEmpty(houseRequestModel.getConsignee().getOldCode())
						&& !ObjectUtils.isEmpty(houseRequestModel.getConsignee().getAppointedAgentCode()))
						|| ((!ObjectUtils.isEmpty(houseRequestModel.getConsignee().getOldCode())
								&& !ObjectUtils.isEmpty(houseRequestModel.getConsignee().getAppointedAgentCode()))
								&& !houseRequestModel.getConsignee().getAppointedAgentCode()
										.equals(houseRequestModel.getConsignee().getOldCode()))) {
					BillingShipment billingShipment = new BillingShipment();
					billingShipment.setShipmentId(houseRequestModel.getMasterAwbId());
					billingShipment.setShipmentNumber(houseRequestModel.getAwbNumber());
					billingShipment.setShipmentDate(houseRequestModel.getAwbDate());
					billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
					billingShipment.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
					billingShipment.setHandlingTerminal(houseRequestModel.getTerminal());
					billingShipment.setUserCode(houseRequestModel.getLoggedInUser());
					billingShipment.setHandelByHouse(true);
					billingShipment.setShipmentHouseId(houseRequestModel.getId());
					billingShipment.setHouseNumber(houseRequestModel.getHawbNumber());
					// calculate charge
					try {
						Charge.calculateCharge(billingShipment);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	@ApiOperation(value = "Api method to retrieve house way bill list with hawb number list")
	@PostRequest(value = "/getConsigneeShipperDetails", method = RequestMethod.POST)
	public BaseResponse<HouseModel> getConsigneeShipperDetails(@RequestBody HouseSearch houseModel)
			throws CustomException {
		BaseResponse<HouseModel> maintainHouseListResponse = utilitiesModelConfiguration.getBaseResponseInstance();
		HouseModel houseModelData = service.getConsigneeShipperDetails(houseModel);
		maintainHouseListResponse.setData(houseModelData);
		return maintainHouseListResponse;
	}

	@ApiOperation(value = "Api method to save an house")
	@PostRequest(value = "/onSaveCustomerinformationList", method = RequestMethod.POST)
	// @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName =
	// NgenAuditEventType.MAINTAIN_HOUSE)
	public void onSaveCustomerinformationList(
			@ApiParam(value = "awbNumber", required = true) @RequestBody MasterAirWayBillModel houseRequestModel)
			throws CustomException {
		BaseResponse<MasterAirWayBillModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
		service.onSaveCustomerinformationList(houseRequestModel);
		for (HouseModel t : houseRequestModel.getMaintainHouseDetailsList()) {
			if (!ObjectUtils.isEmpty(t.getConsignee())) {
				if (t.getConsignee().getName() != null) {

					BillingShipment billingShipment = new BillingShipment();
					billingShipment.setShipmentId(houseRequestModel.getId());
					billingShipment.setShipmentNumber(houseRequestModel.getAwbNumber());
					billingShipment.setShipmentDate(houseRequestModel.getAwbDate());
					billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
					billingShipment.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
					billingShipment.setHandlingTerminal(houseRequestModel.getTerminal());
					billingShipment.setUserCode(houseRequestModel.getLoggedInUser());
					billingShipment.setHandelByHouse(true);
					billingShipment.setShipmentHouseId(t.getHouseId());
					billingShipment.setHouseNumber(t.getHawbNumber());
					// calculate charge
					try {
						Charge.calculateCharge(billingShipment);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@PostRequest(value = "/getVolumeWithVolumetricWeight", method = RequestMethod.POST)
	public BaseResponse<Dimension> getVolumeWithVolumetricWeight(@RequestBody @Valid Dimension d)
			throws CustomException {
		BaseResponse<Dimension> response = utilitiesModelConfiguration.getBaseResponseInstance();
		response.setData(service.getVolumeWithVolumetricWeight(d));
		return response;
	}

	@RequestMapping(value = "/editHouseDimension", method = RequestMethod.POST)
	public BaseResponse<HouseModel> editHouseDimension(@RequestBody @Valid HouseModel houseModel)
			throws CustomException {
		BaseResponse<HouseModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
		response.setData(service.editHouseDimension(houseModel));
		return response;
	}

	// // HAWB LIST ends Here
}