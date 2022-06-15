package com.ngen.cosys.shipment.house.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.ChargeableEntity;
import com.ngen.cosys.damage.enums.FlagCRUD;
import com.ngen.cosys.dimension.model.Dimension;
import com.ngen.cosys.dimension.model.DimensionDetails;
import com.ngen.cosys.dimension.utility.DimensionUtilties;
import com.ngen.cosys.events.payload.ShipmentStatusUpdateEvent;
import com.ngen.cosys.events.producer.ShipmentStatusUpdateEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.processing.engine.rule.executor.RuleExecutor;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;
import com.ngen.cosys.processing.engine.rule.fact.FactShipment;
import com.ngen.cosys.processing.engine.rule.group.FHLGroup;
import com.ngen.cosys.shipment.enums.CRUDStatus;
import com.ngen.cosys.shipment.house.dao.MaintainHouseDAO;
import com.ngen.cosys.shipment.house.model.HouseDimensionDetailsModel;
import com.ngen.cosys.shipment.house.model.HouseDimensionModel;
import com.ngen.cosys.shipment.house.model.HouseModel;
import com.ngen.cosys.shipment.house.model.HouseOtherChargeDeclarationModel;
import com.ngen.cosys.shipment.house.model.HouseSearch;
import com.ngen.cosys.shipment.house.model.HouseSpecialHandlingCodeModel;
import com.ngen.cosys.shipment.house.model.MasterAirWayBillModel;
import com.ngen.cosys.shipment.validator.ShpMngBusinessValidator;
import com.ngen.cosys.validator.dao.UserValidForCarrierDao;

/**
 * This class takes care of the responsibilities related to the Flights
 * responsible for shipment on hold service operation that comes from the
 * controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class MaintainHouseServiceImpl implements MaintainHouseService {

	private static final String EXCEPTION = "Exception Happened ... ";

	private static final Logger lOgger = LoggerFactory.getLogger(MaintainHouseService.class);

	@Autowired
	private MaintainHouseDAO dao;
	
	@Autowired
	private ShipmentStatusUpdateEventProducer shipmentStatusUpdateEventProducer;

	DimensionUtilties utils = new DimensionUtilties();

	@Autowired
	private RuleExecutor ruleExecutor;

	@Autowired
	@Qualifier("maintainHouseValidator")
	private ShpMngBusinessValidator validator;
	@Autowired
	private UserValidForCarrierDao userValidForCarrierDao;

	@Autowired
	private DomesticInternationalHelper domIntHelper;

	// 1.get the Master Air Way Bill
	@Override
	public MasterAirWayBillModel getMasterAWBInformation(MasterAirWayBillModel masterAWBRequestModel)
			throws CustomException {
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("loggedInUser", masterAWBRequestModel.getLoggedInUser());
		parameterMap.put("shipmentNumber", masterAWBRequestModel.getAwbNumber());
		parameterMap.put("type", "AWB");
		System.out.println(userValidForCarrierDao.isUserValidForCarrier(parameterMap));
		if (!userValidForCarrierDao.isUserValidForCarrier(parameterMap)) {
			System.out.println(userValidForCarrierDao.isUserValidForCarrier(parameterMap));
			masterAWBRequestModel.addError("user.not.authorized", "Restricted Airline", ErrorType.ERROR);
			masterAWBRequestModel.setRestrictedAirline(true);
			return masterAWBRequestModel;
		}
		if (!CollectionUtils.isEmpty(masterAWBRequestModel.getMessageList())) {
			throw new CustomException();
		}
		return dao.getMasterAWBInformation(masterAWBRequestModel);
	}

	// get the AWB related House Air Way Bill
	@Override
	public HouseModel getHouseAWBInformation(HouseModel houseRequestModel) throws CustomException {
		return dao.getHouseAWBInformation(houseRequestModel);
	}

	// 3.insert the value for HAWB Corresponding to given AWB and for required awb
	// field
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public HouseModel save(HouseModel houseRequestModel) throws CustomException {

		// Validate the request model
		this.validator.validate(houseRequestModel);

		// Populate the Master AWB for the 1st house and subsequent house
		// addition/modification should only update piece/weight
		MasterAirWayBillModel masterAWBRequestModel = new MasterAirWayBillModel();
		masterAWBRequestModel.setId(houseRequestModel.getMasterAwbId());
		masterAWBRequestModel.setAwbNumber(houseRequestModel.getAwbNumber());
		masterAWBRequestModel.setOrigin(houseRequestModel.getOrigin());
		masterAWBRequestModel.setDestination(houseRequestModel.getDestination());
		masterAWBRequestModel.setPieces(houseRequestModel.getPieces());
		masterAWBRequestModel.setWeight(houseRequestModel.getWeight());
		masterAWBRequestModel.setWeightUnitCode(houseRequestModel.getWeightUnitCode());
		masterAWBRequestModel.setAwbDate(houseRequestModel.getAwbDate());
		masterAWBRequestModel.setAwbPrefix(masterAWBRequestModel.getAwbNumber().substring(0, 3));
		masterAWBRequestModel.setAwbSuffix(masterAWBRequestModel.getAwbNumber().substring(3));
		masterAWBRequestModel.setHawbNumber(houseRequestModel.getHawbNumber());

		// 1. AWB
		masterAWBRequestModel = dao.saveMasterAWB(masterAWBRequestModel);

		// 2. House
		houseRequestModel.setMasterAwbId(masterAWBRequestModel.getId());
		dao.saveHouse(houseRequestModel);

		// 3. Consignee
		if (Optional.ofNullable(houseRequestModel.getConsignee().getName()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getConsignee().getAddress().getCountry()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getConsignee().getAddress().getStreetAddress()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getConsignee().getAddress().getPlace()).isPresent()) {
			houseRequestModel.getConsignee().setHouseId(houseRequestModel.getId());
			houseRequestModel.getConsignee().setType("CNE");
			dao.saveHouseCustomer(houseRequestModel.getConsignee());
		}
		// 4. Shipper
		if (Optional.ofNullable(houseRequestModel.getShipper().getName()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getShipper().getAddress().getCountry()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getShipper().getAddress().getStreetAddress()).isPresent()
				|| Optional.ofNullable(houseRequestModel.getShipper().getAddress().getPlace()).isPresent()) {
			houseRequestModel.getShipper().setHouseId(houseRequestModel.getId());
			houseRequestModel.getShipper().setType("SHP");
			dao.saveHouseCustomer(houseRequestModel.getShipper());
		}

		// 5. OCI
		if (!CollectionUtils.isEmpty(houseRequestModel.getOci())) {
			houseRequestModel.getOci().forEach(t -> t.setHouseId(houseRequestModel.getId()));
			dao.saveHouseOtherCustomInfo(houseRequestModel.getOci());
		}

		// 6. Charges
		// At-least one of the value in this model to be entered for performing CRUD
		// operation
		Optional<HouseOtherChargeDeclarationModel> oOtherCharges = Optional
				.ofNullable(houseRequestModel.getOtherChargeDeclarations());
		if (oOtherCharges.isPresent() && !StringUtils.isEmpty(oOtherCharges.get().getPcIndicator())
				&& !StringUtils.isEmpty(oOtherCharges.get().getCurrencyCode())
				&& !StringUtils.isEmpty(oOtherCharges.get().getOtherCharge())) {
			houseRequestModel.getOtherChargeDeclarations().setHouseId(houseRequestModel.getId());
			dao.saveHouseChargeDeclaration(houseRequestModel.getOtherChargeDeclarations());
		}

		// 7. SHC
		if (!CollectionUtils.isEmpty(houseRequestModel.getShc())) {
			houseRequestModel.setShc(houseRequestModel.getShc().stream()
					.filter(a -> a.getCode() != "" && a.getCode() != null).collect(Collectors.toList()));
			houseRequestModel.getShc().forEach(t -> t.setHouseId(houseRequestModel.getId()));
			dao.saveHouseSHC(houseRequestModel, houseRequestModel.getShc());
		}

		// 8. Description
		if (!CollectionUtils.isEmpty(houseRequestModel.getDescriptionOfGoods())) {
			houseRequestModel.getDescriptionOfGoods().forEach(t -> t.setHouseId(houseRequestModel.getId()));
			dao.saveHouseDescriptionOfGoods(houseRequestModel.getDescriptionOfGoods());
		}

		// 9. Tariffs
		if (!CollectionUtils.isEmpty(houseRequestModel.getTariffs())) {
			houseRequestModel.getTariffs().forEach(t -> t.setHouseId(houseRequestModel.getId()));
			dao.saveHarmonisedTariffScheduleInfo(houseRequestModel.getTariffs());
		}

		// 10. Update Master AWB Info
		this.dao.saveUpdateAWB(masterAWBRequestModel);

		// CPE Rule Engine Process
		executeRuleEngineProcess(houseRequestModel);
		// Return modified model with entity id's

		// Create the charge entry for manual creation of house in case of export
		// shipment
		if (!ObjectUtils.isEmpty(houseRequestModel.getApplyCharge()) && houseRequestModel.getApplyCharge()
				&& MultiTenantUtility.isTenantCityOrAirport(houseRequestModel.getOrigin())) {

			// Get the shipment/customer id
			HouseModel chargeModel = this.dao.getShipmentInfoForCharges(houseRequestModel);
			if (!ObjectUtils.isEmpty(chargeModel)) {
				ChargeableEntity chargeableEntity = new ChargeableEntity();
				chargeableEntity.setReferenceType(ReferenceType.SHIPMENT.getReferenceType());
				chargeableEntity.setReferenceId(chargeModel.getShipmentId());
				chargeableEntity.setAdditionalReferenceId(houseRequestModel.getId());
				chargeableEntity.setAdditionalReferenceType(ReferenceType.FHL_ID.getReferenceType());
				chargeableEntity.setEventType(ChargeEvents.EXP_FHL_UPDATE);
				chargeableEntity.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
				chargeableEntity.setHandlingTerminal(houseRequestModel.getTerminal());
				chargeableEntity.setUserCode(houseRequestModel.getLoggedInUser());
				chargeableEntity.setCustomerId(chargeModel.getCustomerId());
				chargeableEntity.setQuantity(BigDecimal.valueOf(1));
				Charge.calculateCharge(chargeableEntity);
			}
		}

		return houseRequestModel;
	}

	private void executeRuleEngineProcess(HouseModel houseRequestModel) {
		//
		FactPayload factPayload = new FactPayload();
		FactShipment factShipment = new FactShipment();
		factShipment.setShipmentNumber(houseRequestModel.getAwbNumber());
		factShipment.setShipmentDate(houseRequestModel.getAwbDate());
		factPayload.setFactShipment(factShipment);
		factPayload.setRulesPayload(new ArrayList<>());
		factPayload.getRulesPayload().add(FHLGroup.class);
		// Set Audit info
		factPayload.setCreatedBy(houseRequestModel.getCreatedBy());
		factPayload.setCreatedOn(houseRequestModel.getCreatedOn());
		factPayload.setModifiedBy(houseRequestModel.getModifiedBy());
		factPayload.setModifiedOn(houseRequestModel.getModifiedOn());
		// Execute Rule
		try {
			this.ruleExecutor.closeRuleFailure(factPayload);
		} catch (CustomException e) {
			lOgger.error(EXCEPTION, e);
		}
	}

	// 4.Delete Master AWB if it do not have HAWB
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public MasterAirWayBillModel delete(MasterAirWayBillModel masterAWBRequestModel) throws CustomException {
		// Delete the data
		dao.delete(masterAWBRequestModel);
		// Re-fetch the information
		return this.getMasterAWBInformation(masterAWBRequestModel);
	}

	// get the AWB related first House shipper and consignee info
	@Override
	public HouseModel getShipperAndconsigneeInfoForFirstHouse(HouseModel houseRequestModel) throws CustomException {
		return dao.getShipperAndconsigneeInfoForFirstHouse(houseRequestModel);
	}

// HAWB MASTERS STARTS HERE

	// getHouseWayBillMaster will fetch the data for the HAWB and will show it on
	// HAWB Record
	@Override
	public MasterAirWayBillModel getHouseWayBillMaster(HouseSearch houseRequestModel) throws CustomException {
		MasterAirWayBillModel housewaybill = dao.getHouseWayBillMaster(houseRequestModel);
		DomesticInternationalHelperRequest request = new DomesticInternationalHelperRequest();
		request.setOrigin(housewaybill.getOrigin());
		request.setDestination(housewaybill.getDestination());
		String domInt = domIntHelper.getDOMINTHandling(request);
		housewaybill.setIntDom(domInt);

		return housewaybill;
	}

	// setHouseWayBillMaster will update the HAWB Record on click save after making
	// changes

	@Override
	public void setHouseWayBillMaster(MasterAirWayBillModel request) throws CustomException {
		dao.houseValidation(request);
		/*
		 * Used for hawb record update Audit.
		 */
		if (request.getHouseModel() != null) {
			request.getHouseModel().setAwbNumber(request.getAwbNumber());
		}
		if (ObjectUtils.isEmpty(request.getHouseModel().getConsignee().getAppointedAgent())) {
			BigInteger agentId =dao.fetchAppointedAgent();
			request.getHouseModel().getConsignee().setAppointedAgent(agentId);
//			throw new CustomException("shpmgmt.appointed.agent", "", ErrorType.ERROR);
			
		} else if (!ObjectUtils.isEmpty(request.getHouseModel().getConsignee().getAppointedAgent())) {
			Integer appointedAgentCode = dao
					.getAppointmentAgent(request.getHouseModel().getConsignee().getAppointedAgent());
			if (appointedAgentCode == 0) {
				throw new CustomException("shpmgmt.appointed.agent", "", ErrorType.ERROR);
			}
		}

		dao.setHouseWayBillMaster(request);
		if (!ObjectUtils.isEmpty(request.getHouseModel().getShc())) {
			for (HouseSpecialHandlingCodeModel shcs : request.getHouseModel().getShc()) {
				switch (shcs.getFlagCRUD()) {
				case CRUDStatus.CRUDType.CREATE:
					dao.validShc(shcs);
					dao.onSaveHouseShc(shcs);
					break;
				case CRUDStatus.CRUDType.UPDATE:
					dao.validShc(shcs);
					dao.onSaveHouseShcUpdate(shcs);
					break;
				case CRUDStatus.CRUDType.DELETE:
					dao.onSaveHouseShcDelete(shcs);
					break;
				default:
					break;
				}
			}
		}
		if (!ObjectUtils.isEmpty(request.getHouseModel().getConsignee())) {
			switch (request.getHouseModel().getConsignee().getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				dao.onSaveConsigneeInformationInsert(request.getHouseModel().getConsignee());
				break;
			case CRUDStatus.CRUDType.UPDATE:
				dao.onSaveConsigneeInformationUpdateMaster(request.getHouseModel().getConsignee());
				break;
			case CRUDStatus.CRUDType.DELETE:
				dao.onSaveConsigneeInformationDelete(request.getHouseModel().getConsignee());
				break;
			default:
				break;
			}
		}
		if (!ObjectUtils.isEmpty(request.getHouseModel().getShipper())) {
			switch (request.getHouseModel().getShipper().getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				dao.onSaveShipperInformationInsert(request.getHouseModel().getShipper());
				break;
			case CRUDStatus.CRUDType.UPDATE:
				dao.onSaveShipperInformationUpdateMaster(request.getHouseModel().getShipper());
				break;
			case CRUDStatus.CRUDType.DELETE:
				dao.onSaveShipperInformationDelete(request.getHouseModel().getShipper());
				break;
			default:
				break;
			}
		}
		if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class)) {
			BigInteger flightId = null;
			int isFlightCompleted = 0;
			// Check the flight completed status
			if (Objects.nonNull(request.getId())) {
				flightId = dao.getFlightId(request.getId());

				if (Objects.nonNull(flightId)) {
					isFlightCompleted = dao.isFlightCompleted(flightId);
				}
				if (isFlightCompleted > 0) {
					ShipmentStatusUpdateEvent event = new ShipmentStatusUpdateEvent();
					event.setFlightId(flightId);
					event.setHouseNumber(request.getHawbNumber());
					event.setShipmentNumber(request.getAwbNumber());
					event.setCreatedBy(request.getCreatedBy());
					event.setCreatedOn(LocalDateTime.now());
					event.setFunction("HAWB Record");
					event.setMessageType("U");
					event.setConsigneeName(request.getHouseModel().getConsignee().getName());
					event.setConsigneeAddress(request.getHouseModel().getConsignee().getAddress().getPlace());
					shipmentStatusUpdateEventProducer.publish(event);
				}
			}
		}
		

	}

	// hawb list starts here

	// get the HAWB and AWB Master Data as per AWB Number
	@Override
	public MasterAirWayBillModel getHouseWayBillList(HouseSearch houseSearchRequest) throws CustomException {
		MasterAirWayBillModel housewaybill = dao.getHouseWayBillList(houseSearchRequest);
		if (Optional.ofNullable(housewaybill).isPresent()) {
			if (MultiTenantUtility.isTranshipment(housewaybill.getOrigin(), housewaybill.getDestination())) {
				housewaybill.addError("mawb.transshipment", null, ErrorType.ERROR);
			}
			if (MultiTenantUtility.isTenantCityOrAirport(housewaybill.getOrigin())) {
				housewaybill.addError("mawb.export", null, ErrorType.ERROR);
			}

			DomesticInternationalHelperRequest request = new DomesticInternationalHelperRequest();
			request.setOrigin(housewaybill.getOrigin());
			request.setDestination(housewaybill.getDestination());
			housewaybill.setIntDom(domIntHelper.getDOMINTHandling(request));
		}

		if (Optional.ofNullable(housewaybill).isPresent()
				&& !CollectionUtils.isEmpty(housewaybill.getMaintainHouseDetailsList())) {
			List<HouseModel> houseList = housewaybill.getMaintainHouseDetailsList();
			houseList = houseList.stream()
					.filter(house -> house.getShipmentType() != null && house.getShipmentType().equals("HAWB"))
					.collect(Collectors.toList());
			housewaybill.setTotalHWB(BigInteger.valueOf(houseList.size()));
			BigInteger totalPiecesHAWB = BigInteger.ZERO;
			BigDecimal totalWeightHAWB = BigDecimal.ZERO;
			BigDecimal totalChgWeightHAWB = BigDecimal.ZERO;
			for (HouseModel house : houseList) {
				if (house.getPieces() != null) {
					totalPiecesHAWB = totalPiecesHAWB.add(house.getPieces());
				}
				if (house.getWeight() != null) {
					totalWeightHAWB = totalWeightHAWB.add(house.getWeight());
				}
				if (house.getChargeableWeight() != null) {
					totalChgWeightHAWB = totalChgWeightHAWB.add(house.getChargeableWeight());
				}
			}
			housewaybill.setTotalPieces(totalPiecesHAWB);
			housewaybill.setTotalWeight(totalWeightHAWB);
			housewaybill.setTotalChargeableWeight(totalChgWeightHAWB);
			housewaybill.setMaintainHouseDetailsList(houseList);
		}
		return housewaybill;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void onSaveHouseNumber(MasterAirWayBillModel houseRequestModel) throws CustomException {

		Set<String> shcSet = new HashSet<String>();

		if (!ObjectUtils.isEmpty(houseRequestModel.getShcList()) && houseRequestModel.getShcList().size() > 0)
			shcSet.addAll(houseRequestModel.getShcList().stream().collect(Collectors.toSet()));
		for (HouseModel houseRecord : houseRequestModel.getMaintainHouseDetailsList()) {
			// Validate the request model
			// this.validatorHouse.validate(houseRequestModel);
			if (!ObjectUtils.isEmpty(houseRecord.getShc()) && houseRecord.getShc().size() > 0)
				shcSet.addAll(houseRecord.getShc().stream()
						.filter(data -> !data.getFlagCRUD().equals(FlagCRUD.DELETE.getValue())).map(x -> x.getCode())
						.collect(Collectors.toSet()));

			houseRecord.setId(houseRecord.getHouseId());
			houseRecord.setAwbPieces(
					houseRequestModel.getPieces() != null ? houseRequestModel.getPieces() : BigInteger.valueOf(0));
			houseRecord.setAwbWeight(
					houseRequestModel.getWeight() != null ? houseRequestModel.getWeight() : BigDecimal.valueOf(0));
			houseRecord.setAwbChargeableWeight(
					houseRequestModel.getChargeableWeight() != null ? houseRequestModel.getChargeableWeight()
							: BigDecimal.valueOf(0));
			switch (houseRecord.getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				dao.houseListValidation(houseRecord);
				dao.onSaveHouseNumber(houseRecord);
				if (Optional.ofNullable(houseRecord.getHouseDimension()).isPresent()
						&& Optional.ofNullable(houseRecord.getHouseDimension().getVolumetricWeight()).isPresent()
						&& houseRecord.getHouseDimension().getVolumetricWeight()
								.compareTo(BigDecimal.valueOf(0)) >= 0) {
					HouseDimensionModel houseDimension = new HouseDimensionModel();
					houseDimension.setVolumetricWeight(houseRecord.getHouseDimension().getVolumetricWeight());
					houseDimension.setHouseId(houseRecord.getId());
					houseDimension.setTotalDimpieces(BigInteger.valueOf(0));
					houseDimension.setUnitCode("CMT");
					houseDimension.setVolumeUnitCode("MC");
					dao.insertHouseDimension(houseDimension);
				}

				if (!ObjectUtils.isEmpty(houseRecord.getShc())) {
					for (HouseSpecialHandlingCodeModel houseRecordShc : houseRecord.getShc()) {
						houseRecordShc.setHouseId(houseRecord.getId());
						switch (houseRecordShc.getFlagCRUD()) {
						case CRUDStatus.CRUDType.CREATE:
							dao.validShc(houseRecordShc);
							dao.onSaveHouseShc(houseRecordShc);
							break;
						case CRUDStatus.CRUDType.UPDATE:
							dao.validShc(houseRecordShc);
							dao.onSaveHouseShcUpdate(houseRecordShc);
							break;
						case CRUDStatus.CRUDType.DELETE:
							dao.onSaveHouseShcDelete(houseRecordShc);
							break;
						default:
							break;
						}
					}
				}
				break;
			case CRUDStatus.CRUDType.UPDATE:
				if (!ObjectUtils.isEmpty(houseRequestModel.getHawbNumber())) {
					dao.houseListValidation(houseRecord);
				} /*
					 * changes for bug-81
					 */
				// dao.onSaveHouseNumberUpdate(houseRecord);
				/*
				 * changes end here for bug-81
				 */
				if (!ObjectUtils.isEmpty(houseRecord.getShc())) {
					for (HouseSpecialHandlingCodeModel houseRecordShc : houseRecord.getShc()) {
						switch (houseRecordShc.getFlagCRUD()) {
						case CRUDStatus.CRUDType.CREATE:
							dao.validShc(houseRecordShc);
							dao.onSaveHouseShc(houseRecordShc);
							break;
						case CRUDStatus.CRUDType.UPDATE:
							dao.validShc(houseRecordShc);
							dao.onSaveHouseShcUpdate(houseRecordShc);
							break;
						case CRUDStatus.CRUDType.DELETE:
							dao.onSaveHouseShcDelete(houseRecordShc);
							break;
						default:
							break;
						}
					} /*
						 * changes for bug-81
						 */
					List<HouseSpecialHandlingCodeModel> shcNotDeleted = houseRecord.getShc().stream()
							.filter(shc -> !shc.getFlagCRUD().equalsIgnoreCase("D")).collect(Collectors.toList());
					houseRecord.setShc(shcNotDeleted);
				}
				dao.onSaveHouseNumberUpdate(houseRecord);
				HouseDimensionModel houseDimension = new HouseDimensionModel();
				houseDimension.setVolumetricWeight(houseRecord.getHouseDimension().getVolumetricWeight());
				houseDimension.setHouseId(houseRecord.getId());
				switch (houseRecord.getHouseDimension().getFlagCRUD()) {
				case CRUDStatus.CRUDType.CREATE:
					if (Optional.ofNullable(houseRecord.getHouseDimension()).isPresent()
							&& Optional.ofNullable(houseRecord.getHouseDimension().getVolumetricWeight()).isPresent()
							&& houseRecord.getHouseDimension().getVolumetricWeight()
									.compareTo(BigDecimal.valueOf(0)) >= 0) {
						houseDimension.setTotalDimpieces(BigInteger.valueOf(0));
						houseDimension.setUnitCode("CMT");
						houseDimension.setVolumeUnitCode("MC");
						dao.insertHouseDimension(houseDimension);
					}
					break;
				case CRUDStatus.CRUDType.UPDATE:
					houseDimension.setHouseDimensionId(houseRecord.getHouseDimension().getHouseDimensionId());
					dao.updateHouseVolumetricWeight(houseDimension);
					break;
				}
				/*
				 * changes end here for bug-81
				 */
				break;
			default:
				break;
			}
			if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class)) {
			 this.publishShipmentEvent(houseRecord);
			}
		}

		if (houseRequestModel.getTotalChargeableWeight().compareTo(houseRequestModel.getChargeableWeight()) > 0) {
			HouseModel model = new HouseModel();
			model.setMasterAwbId(houseRequestModel.getId());
			model.setAwbChargeableWeight(houseRequestModel.getTotalChargeableWeight());
			dao.updateAWBChargeableWeight(model);
		}

		houseRequestModel.setShcList(shcSet.stream().collect(Collectors.toList()));
		if (!houseRequestModel.getShcList().isEmpty())
			updateSHCinMaster(houseRequestModel);

		// System.out.println(shcSet);
		// ITERATING FOR CHECKING VALIDATION SO THAT GET THE LATEST DATA AFTER INSERT OR
		// UPDATE AND ROLLBACK IF NOT VALID
//		for (HouseModel houseRecord : houseRequestModel.getMaintainHouseDetailsList()) {
//			dao.houseListValidation(houseRecord);
//		}
      
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void onSaveCustomerinformation(HouseModel houseRequestModel) throws CustomException {
		// new fix for fetching shipment number for audit trail
		String shipmentNumber = houseRequestModel.getAwbNumber();
		String houseNumber = houseRequestModel.getHawbNumber();
		// SAVE Consignee Details

		if (ObjectUtils.isEmpty(houseRequestModel.getConsignee().getAppointedAgent())) {
			BigInteger agentId =dao.fetchAppointedAgent();
			houseRequestModel.getConsignee().setAppointedAgent(agentId);
			
			
			//throw new CustomException("shpmgmt.appointed.agent", "", ErrorType.ERROR);
		} else if (!ObjectUtils.isEmpty(houseRequestModel.getConsignee().getAppointedAgent())) {
			Integer appointedAgentCode = dao.getAppointmentAgent(houseRequestModel.getConsignee().getAppointedAgent());
			if (appointedAgentCode == 0) {
				throw new CustomException("shpmgmt.appointed.agent", "", ErrorType.ERROR);
			}
		}

		if (!ObjectUtils.isEmpty(houseRequestModel.getConsignee())) {
			// Bug-81 new fix
			houseRequestModel.getConsignee().setShipmentNumber(shipmentNumber);
			houseRequestModel.getConsignee().setHawbNumber(houseNumber);
			if (houseRequestModel.getConsignee().getAddress().getCountry() == null) {
				throw new CustomException("ERR_HAWB_01", "", ErrorType.ERROR);
			}
			switch (houseRequestModel.getConsignee().getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				dao.onSaveConsigneeInformationInsert(houseRequestModel.getConsignee());				
				break;
			case CRUDStatus.CRUDType.UPDATE:
				dao.onSaveConsigneeInformationUpdate(houseRequestModel.getConsignee());
				break;
			case CRUDStatus.CRUDType.DELETE:
				dao.onSaveConsigneeInformationDelete(houseRequestModel.getConsignee());
				break;
			default:
				break;
			}
		}
		if (!ObjectUtils.isEmpty(houseRequestModel.getShipper())) {
			// bug-81 new fix
			houseRequestModel.getShipper().setShipmentNumber(shipmentNumber);
			houseRequestModel.getShipper().setHawbNumber(houseNumber);
			if (houseRequestModel.getShipper().getAddress().getCountry() == null) {
				throw new CustomException("ERR_HAWB_01", "", ErrorType.ERROR);
			}
			switch (houseRequestModel.getShipper().getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				dao.onSaveShipperInformationInsert(houseRequestModel.getShipper());
				break;
			case CRUDStatus.CRUDType.UPDATE:
				dao.onSaveShipperInformationUpdate(houseRequestModel.getShipper());
				break;
			case CRUDStatus.CRUDType.DELETE:
				dao.onSaveShipperInformationDelete(houseRequestModel.getShipper());
				break;
			default:
				break;
			}
			
		}
		if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class)) {
			BigInteger flightId = null;
			int isFlightCompleted = 0;
			// Check the flight completed status
			if (Objects.nonNull(houseRequestModel.getMasterAwbId())) {
				flightId = dao.getFlightId(houseRequestModel.getMasterAwbId());
				if (Objects.nonNull(flightId)) {
					isFlightCompleted = dao.isFlightCompleted(flightId);
				}
				if (isFlightCompleted > 0) {
					ShipmentStatusUpdateEvent event = new ShipmentStatusUpdateEvent();
					event.setFlightId(flightId);
					event.setHouseNumber(houseRequestModel.getHawbNumber());
					event.setShipmentNumber(houseRequestModel.getAwbNumber());
					event.setCreatedBy(houseRequestModel.getCreatedBy());
					event.setCreatedOn(LocalDateTime.now());
					event.setFunction("HAWB List");
					event.setMessageType("U");
					if (Objects.nonNull(houseRequestModel.getConsignee()))
						event.setConsigneeName(houseRequestModel.getConsignee().getName());
					if (Objects.nonNull(houseRequestModel.getConsignee()))
						event.setConsigneeAddress(houseRequestModel.getConsignee().getAddress().getPlace());
					shipmentStatusUpdateEventProducer.publish(event);
				}
			}
		}
		
	}

	// get the Customer Details
	@Override
	public HouseModel getConsigneeShipperDetails(HouseSearch houseModel) throws CustomException {
		return dao.getConsigneeShipperDetails(houseModel);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void onSaveCustomerinformationList(MasterAirWayBillModel houseRequestModel) throws CustomException {
		String shipmentNumber = houseRequestModel.getAwbNumber();
		String houseNumber = null;
		
		for (HouseModel t : houseRequestModel.getMaintainHouseDetailsList()) {

			if (!ObjectUtils.isEmpty(t.getConsignee())) {
				// Bug-81 new fix
				t.getConsignee().setType("CNE");
				if (t.getConsignee().getAddress().getCountry() == null) {
					throw new CustomException("ERR_HAWB_01", "", ErrorType.ERROR);
				}
				if(ObjectUtils.isEmpty(t.getConsignee().getAppointedAgent())) {
					
					BigInteger agentId =dao.fetchAppointedAgent();
					t.getConsignee().setAppointedAgent(agentId);
				//	throw new CustomException("shpmgmt.appointed.agent", "", ErrorType.ERROR);
				}
				else if(!ObjectUtils.isEmpty(t.getConsignee().getAppointedAgent())) {
					Integer appointedAgentCode =  dao.getAppointmentAgent(t.getConsignee().getAppointedAgent());
					if(appointedAgentCode == 0) {
						throw new CustomException("shpmgmt.appointed.agent", "", ErrorType.ERROR);
					}
				}
				
				switch (t.getConsignee().getFlagCRUD()) {
				case CRUDStatus.CRUDType.CREATE:
					dao.onSaveConsigneeInformationInsert(t.getConsignee());
					break;
				case CRUDStatus.CRUDType.UPDATE:
					dao.onSaveConsigneeInformationUpdate(t.getConsignee());
					break;
				case CRUDStatus.CRUDType.DELETE:
					t.getConsignee().setHouseId(t.getHouseId());
					dao.onSaveConsigneeInformationDeleteData(t.getConsignee());
					break;
				default:
					break;
				}				
			}			
			if (!ObjectUtils.isEmpty(t.getShipper())
					&& !ObjectUtils.isEmpty(t.getShipper().getAddress().getCountry())) {
				// bug-81 new fix
				t.getShipper().setType("SHP");
				if (t.getShipper().getAddress().getCountry() == null) {
					throw new CustomException("ERR_HAWB_01", "", ErrorType.ERROR);
				}
				switch (t.getShipper().getFlagCRUD()) {
				case CRUDStatus.CRUDType.CREATE:
					dao.onSaveShipperInformationInsert(t.getShipper());
					break;
				case CRUDStatus.CRUDType.UPDATE:
					dao.onSaveShipperInformationUpdate(t.getShipper());
					break;
				case CRUDStatus.CRUDType.DELETE:
					t.getShipper().setHouseId(t.getHouseId());
					dao.onSaveShipperInformationDeleteData(t.getShipper());
					break;
				default:
					break;
				}
			}
			
			houseNumber = t.getHawbNumber();
		}
		if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class)) {
			BigInteger flightId = null;
			int isFlightCompleted = 0;
			// Check the flight completed status
			if (Objects.nonNull(houseRequestModel.getId())) {
				flightId = dao.getFlightId(houseRequestModel.getId());

				if (Objects.nonNull(flightId)) {
					isFlightCompleted = dao.isFlightCompleted(flightId);
				}
				if (isFlightCompleted > 0) {
					ShipmentStatusUpdateEvent event = new ShipmentStatusUpdateEvent();
					event.setFlightId(flightId);
					event.setHouseNumber(houseNumber);
					event.setShipmentNumber(houseRequestModel.getAwbNumber());
					event.setCreatedBy(houseRequestModel.getCreatedBy());
					event.setCreatedOn(LocalDateTime.now());
					event.setFunction("HAWB List");
					event.setMessageType("U");
					event.setConsigneeName(
							houseRequestModel.getMaintainHouseDetailsList().get(0).getConsignee().getName());
					event.setConsigneeAddress(houseRequestModel.getMaintainHouseDetailsList().get(0).getConsignee()
							.getAddress().getPlace());
					shipmentStatusUpdateEventProducer.publish(event);
				}
			}
		}
		
	}

	private void updateSHCinMaster(MasterAirWayBillModel data) throws CustomException {
		dao.updateMainMastersSHCData(data);
	}

	@Override
	public Dimension getVolumeWithVolumetricWeight(Dimension d) throws CustomException {
		return utils.getVolumewithVolumetricWeight(d);
	}

	@Override
	public HouseModel editHouseDimension(HouseModel houseModel) throws CustomException {

		HouseDimensionModel houseDimension = houseModel.getHouseDimension();

		if (houseDimension.getVolumetricWeight() != null
				&& houseDimension.getVolumetricWeight().compareTo(new BigDecimal("9999999.99")) == 1) {
			throw new CustomException("volumetric.weight.exceeded", "", ErrorType.ERROR);
		}

		List<HouseDimensionDetailsModel> listOfDimensionDetails = houseDimension.getDimensionList();
		if (houseDimension.getFlagUpdate() != null && houseDimension.getFlagUpdate().equals("Y")) {
			// check if dimension pieces is more than the HAWB pieces
			if (houseDimension.getPieces() != null && houseDimension.getTotalDimpieces() != null
					&& houseDimension.getPieces().compareTo(houseDimension.getTotalDimpieces()) == -1) {
				throw new CustomException("ERR_HAWB_02", "", ErrorType.ERROR);
			}

			BigInteger houseDimensionId = houseDimension.getHouseDimensionId();
			if (houseDimensionId == null) { // inserting new record into Dimension
				BigInteger shipmentHouseDimId = null;
				dao.insertHouseDimension(houseDimension);
				if (houseDimension.getId() != null) {
					shipmentHouseDimId = houseDimension.getId();
				}
				for (HouseDimensionDetailsModel dimensionDtlsModel : listOfDimensionDetails) {
					if (dimensionDtlsModel.getPieces() > 0) {
						// inserting new record into dimensionDetails
						dimensionDtlsModel.setHouseDimensionId(shipmentHouseDimId);
						dao.insertHouseDimensionDetails(dimensionDtlsModel);
					}
				}
			} else {
				// if dimension is there then updating
				dao.updateHouseDimension(houseDimension);
				for (HouseDimensionDetailsModel dimensionDtlsModel : listOfDimensionDetails) {
					// if dimension details is there then updating otherwise inserting new dimension
					// details
					if (dimensionDtlsModel.getHouseDimensionDtlsId() != null) {
						dao.updateDimensionalDetails(dimensionDtlsModel);
					} else {
						dimensionDtlsModel.setHouseDimensionId(houseDimensionId);
						dao.insertHouseDimensionDetails(dimensionDtlsModel);
					}
				}
			}

			if (Optional.ofNullable(houseDimension).isPresent()
					&& Optional.ofNullable(houseDimension.getVolumetricWeight()).isPresent()
					&& houseDimension.getVolumetricWeight().compareTo(houseModel.getWeight()) == 1) {
				houseModel.setChargeableWeight(houseDimension.getVolumetricWeight());
				houseModel.setHouseId(houseDimension.getHouseId());
				dao.updateHouseChargeableWeight(houseModel);
			}
			if (Optional.ofNullable(houseModel.getAwbChargeableWeight()).isPresent()) {
				dao.updateAWBChargeableWeight(houseModel);
			}

		}

		if (houseDimension.getFlagDelete() != null && houseDimension.getFlagDelete().equals("Y")) {
			for (HouseDimensionDetailsModel dimensionDtlsModel : listOfDimensionDetails) {
				Dimension d = new Dimension();
				DimensionDetails dimDtls = new DimensionDetails();
				List<DimensionDetails> dimDtlsList = new ArrayList<DimensionDetails>();
				dimDtls.setPcs(BigInteger.valueOf(dimensionDtlsModel.getPieces()));
				dimDtls.setHeight(BigInteger.valueOf(dimensionDtlsModel.getHeight()));
				dimDtls.setWidth(BigInteger.valueOf(dimensionDtlsModel.getWidth()));
				dimDtls.setLength(BigInteger.valueOf(dimensionDtlsModel.getLength()));
				d.setUnitCode(houseDimension.getUnitCode());
				d.setWeightCode(houseModel.getWeightUnitCode());
				d.setVolumeCode(houseDimension.getVolumeUnitCode());
				dimDtlsList.add(dimDtls);
				d.setDimensionDetails(dimDtlsList);
				d.setShipmentPcs(houseModel.getPieces());
				d = utils.getVolumewithVolumetricWeight(d);

				dao.deleteDimension(dimensionDtlsModel.getHouseDimensionDtlsId());

				dimensionDtlsModel.setDimensionVolume(d.getCalculatedVolume());
				dimensionDtlsModel.setDimnVolumetricWeight(d.getVolumetricWeight());
				dimensionDtlsModel.setHouseId(houseModel.getHouseId());

				dao.updateDimension(dimensionDtlsModel);
			}
		}

		return houseModel;
	}

	// hawb list ends here
	
	private void publishShipmentEvent(HouseModel houseRequestModel) throws CustomException {
		// Check the flight completed status
		BigInteger flightId = null;
		int isFlightCompleted = 0;
		if (!Objects.isNull(houseRequestModel.getMasterAwbId())) {
			flightId = dao.getFlightId(houseRequestModel.getMasterAwbId());
			if (!Objects.isNull(flightId)) {
				isFlightCompleted = dao.isFlightCompleted(flightId);
			}

			if (isFlightCompleted > 0) {
				ShipmentStatusUpdateEvent event = new ShipmentStatusUpdateEvent();
				event.setFlightId(flightId);
				event.setHouseNumber(houseRequestModel.getHawbNumber());
				event.setShipmentNumber(houseRequestModel.getAwbNumber());
				event.setCreatedBy(houseRequestModel.getCreatedBy());
				event.setCreatedOn(LocalDateTime.now());
				event.setFunction("HAWB List");
				if (Objects.nonNull(houseRequestModel.getConsignee()))
					event.setConsigneeName(houseRequestModel.getConsignee().getName());
				if (Objects.nonNull(houseRequestModel.getConsignee()))
					event.setConsigneeAddress(houseRequestModel.getConsignee().getAddress().getPlace());
				if (Objects.isNull(houseRequestModel.getConsignee())) {
					HouseSearch houseSearch = new HouseSearch();
					houseSearch.setHawbNumber(houseRequestModel.getHawbNumber());
					houseSearch.setAwbNumber(houseRequestModel.getAwbNumber());
					houseSearch.setId(houseRequestModel.getHouseId());
					houseSearch.setAwbDate(houseRequestModel.getAwbDate());
					HouseModel hModel = dao.getConsigneeShipperDetails(houseSearch);
					if (Objects.nonNull(hModel.getConsignee())) {
						event.setConsigneeName(hModel.getConsignee().getName());
					}
					if (Objects.nonNull(hModel.getConsignee())) {
						event.setConsigneeAddress(hModel.getConsignee().getAddress().getPlace());
					}
				}
				if (Objects.nonNull(houseRequestModel.getFlagCRUD())
						&& "U".equalsIgnoreCase(houseRequestModel.getFlagCRUD())) {
					event.setMessageType("U");
				}
				shipmentStatusUpdateEventProducer.publish(event);
			}
		}
	}

	
}