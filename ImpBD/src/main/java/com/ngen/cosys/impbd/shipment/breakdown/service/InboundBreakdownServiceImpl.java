/**
 * This is a class which performs break down for a scheckhipment. Shipment can
 * be categorized into Courier/Mail/AWB. This class establishes to all relevant
 * entities. It also derives Shipment Master, Inventory and Remarks information.
 * 
 * Usage: 1. Auto-wire this class service interface 2. invoke the breakDown()
 * method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.breakdown.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidator;
import com.ngen.cosys.events.producer.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEventProducer;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.shipment.breakdown.controller.InboundBreakdownController;
import com.ngen.cosys.impbd.shipment.breakdown.dao.InboundBreakdownDAO;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentHouseModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundUldFlightModel;
import com.ngen.cosys.impbd.shipment.breakdown.validator.InboundBreakDownValidator;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService;
import com.ngen.cosys.impbd.shipment.inventory.dao.ShipmentInventoryDAOImpl;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;
import com.ngen.cosys.impbd.shipment.verification.service.ShipmentVerificationService;
import com.ngen.cosys.impbd.workinglist.dao.BreakDownWorkingListDAO;
import com.ngen.cosys.impbd.workinglist.model.ExportWorkingListModel;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.service.util.model.FlightInfo;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.uldinfo.UldMovementFunctionTypes;
import com.ngen.cosys.uldinfo.model.UldInfoModel;
import com.ngen.cosys.uldinfo.service.UldInfoService;
import com.ngen.cosys.validator.enums.ShipmentType;
import com.ngen.cosys.validator.utils.FlightHandlingSystemValidator;

@Service
public class InboundBreakdownServiceImpl implements InboundBreakdownService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InboundBreakdownController.class);

	
	@Autowired
	private ShipmentVerificationService shipmentVerificationService;

	@Autowired
	private ShipmentMasterService shipmentMasterService;

	@Autowired
	private InboundBreakdownDAO inboundBreakdownDAO;

	@Autowired
	InboundShipmentPiecesEqualsToBreakDownPiecesStoreEventProducer producer;

	@Autowired
	private InboundBreakDownValidator breakDownValidation;

	@Autowired
	private ShipmentInventoryDAOImpl inventoryDao;

	@Autowired
	private UldInfoService uldMasterInfo;

	@Autowired
	private BreakDownWorkingListDAO breakDownWorkingListDAO;

	@Autowired
	private MoveableLocationTypesValidator moveableLocationTypesValidator;

	@Autowired
	private FlightHandlingSystemValidator handlingSystemValidator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.inboundbreakdown.service.InboundBreakdownService#
	 * getShipmentDetails(com.ngen.cosys.impbd.inboundbreakdown.model.
	 * InboundBreakdownModel)
	 */
	@Override
	public InboundBreakdownModel get(InboundBreakdownModel inboundBreakdownModel) throws CustomException {

		// Check for Shipment Type
		if (StringUtils.isEmpty(inboundBreakdownModel.getShipment().getShipmentType())) {
			throw new CustomException("imp.entershipmenttype.m", null, ErrorType.ERROR);
		}

		InboundBreakdownShipmentModel shipmentTypeExists = inboundBreakdownDAO
				.fetchShipmentType(inboundBreakdownModel.getShipment());
		if (Objects.nonNull(shipmentTypeExists) && inboundBreakdownModel.getShipment().getShipmentType() != null
				&& !inboundBreakdownModel.getShipment().getShipmentType()
						.equalsIgnoreCase(shipmentTypeExists.getShipmentType())) {
			throw new CustomException("imp.Shipment.type", null, ErrorType.ERROR);

		}

		InboundBreakdownModel bdData = inboundBreakdownDAO.get(inboundBreakdownModel);
		if (ObjectUtils.isEmpty(bdData)) {
			throw new CustomException("NORECORD", null, ErrorType.ERROR);
		}

		// check if it is Manifested
		if (inboundBreakdownModel.getShipment().getFlagCRUD().equalsIgnoreCase("N")
				&& !ObjectUtils.isEmpty(bdData.getShipment())
				&& ObjectUtils.isEmpty(bdData.getShipment().getManifestPieces())
				&& CollectionUtils.isEmpty(bdData.getShipment().getInventory())) {
			throw new CustomException("bd.checkShipmentManifested", null, ErrorType.WARNING);
		}

		inboundBreakdownModel.setFlightId(bdData.getFlightId());
		

		if (!CollectionUtils.isEmpty(bdData.getShipment().getInventory())) {
			for (InboundBreakdownShipmentInventoryModel inventoryData : bdData.getShipment().getInventory()) {
				BigInteger deliveryHasItBeenInitiated = inboundBreakdownDAO.checkDeliveryInitiated(inventoryData);
				if (!ObjectUtils.isEmpty(deliveryHasItBeenInitiated) && deliveryHasItBeenInitiated.intValue() > 0) {
					inventoryData.setIsDeliveryInitiated(true);
				}
				BigInteger trmHasItBeenInitiated = inboundBreakdownDAO.checkTrmInitiated(inventoryData);
				if (!ObjectUtils.isEmpty(trmHasItBeenInitiated) && trmHasItBeenInitiated.intValue() > 0) {
					inventoryData.setIsTrmintiated(true);
				}
			}
		}
		
		//get house Total Pieces/weight/chargeableWwight
		InboundBreakdownShipmentModel houseTotalPiecesWeight = inboundBreakdownDAO.getTotalHousePiecesWeight(bdData.getShipment());
		if(!ObjectUtils.isEmpty(houseTotalPiecesWeight)) {
			bdData.getShipment().setTotalHousePieces(houseTotalPiecesWeight.getTotalHousePieces());
			bdData.getShipment().setTotalHouseWeight(houseTotalPiecesWeight.getTotalHouseWeight());
			bdData.getShipment().setTotalHouseChargeableWeight(houseTotalPiecesWeight.getTotalHouseChargeableWeight());	
		}

		// To Display Remaining Pieces/Weight
		InboundBreakdownShipmentModel utilisedPieces = inboundBreakdownDAO.getUtilisedPieces(bdData.getShipment());

		if (Objects.nonNull(utilisedPieces)) {
			if (bdData.getShipment().getPiece() != null
					&& !bdData.getShipment().getWeight().equals(new BigDecimal("0.0"))) {
				
				bdData.getShipment().setTotalBreakDownPieces(utilisedPieces.getTotalUtilisedPieces());
				bdData.getShipment().setTotalBreakDownWeight(utilisedPieces.getTotalUtilisedWeight());
				
				if (bdData.getShipment().getPiece().compareTo(utilisedPieces.getTotalUtilisedPieces()) != -1) {
					bdData.getShipment().setTotalUtilisedPieces(
							bdData.getShipment().getPiece().subtract(utilisedPieces.getTotalUtilisedPieces()));
					bdData.getShipment().setTotalUtilisedWeight(
							bdData.getShipment().getWeight().subtract(utilisedPieces.getTotalUtilisedWeight()));
				} else {
					// Avoid showing negative values in remaining pieces/Weight in UI
					bdData.getShipment().setTotalUtilisedPieces(new BigInteger("0"));
					bdData.getShipment().setTotalUtilisedWeight(BigDecimal.ZERO);
				}
			} else {
				if (bdData.getShipment().getManifestPieces() != null
						&& bdData.getShipment().getManifestPieces().intValue() != 0) {
					bdData.getShipment().setTotalUtilisedPieces(
							bdData.getShipment().getManifestPieces().subtract(utilisedPieces.getTotalUtilisedPieces()));
					bdData.getShipment().setTotalUtilisedWeight(
							bdData.getShipment().getManifestWeight().subtract(utilisedPieces.getTotalUtilisedWeight()));
				} else {
					// Avoid showing negative values in remaining pieces/Weight in UI
					bdData.getShipment().setTotalUtilisedPieces(new BigInteger("0"));
					bdData.getShipment().setTotalUtilisedWeight(BigDecimal.ZERO);
				}
			}
		}

		FlightInfo flightInfo = new FlightInfo();
		flightInfo.setFlightKey(inboundBreakdownModel.getFlightNumber());
		flightInfo.setFlightDate(inboundBreakdownModel.getFlightDate());
		flightInfo.setType("I");
		Boolean handlingSystem = handlingSystemValidator.isFlightHandlinginSystem(flightInfo);
		bdData.setHandlinginSystem(handlingSystem);
		return bdData;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public InboundBreakdownModel breakDown(InboundBreakdownModel inboundBreakdownModel) throws CustomException {

		// Set the shipment number
		if (!CollectionUtils.isEmpty(inboundBreakdownModel.getShipment().getInventory())) {
			inboundBreakdownModel.getShipment().getInventory()
					.forEach(t -> t.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber()));
		}

		LocalDateTime startTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),inboundBreakdownModel.getTenantId());

		LocalDateTime lastupdatDateTime = inboundBreakdownDAO.getLastUpdatedDateTime(inboundBreakdownModel.getShipment());
		
		if (!ObjectUtils.isEmpty(lastupdatDateTime) && ObjectUtils.isEmpty(inboundBreakdownModel.getShipment().getLastUpdatedTime()) 
				&& !ObjectUtils.isEmpty(inboundBreakdownModel.getGroupCreateLocation()) && !inboundBreakdownModel.getGroupCreateLocation()) {
			inboundBreakdownModel.addError("inbound.breakdown.transaction", "", ErrorType.ERROR);
			throw new CustomException();
		}
		
		if (!ObjectUtils.isEmpty(inboundBreakdownModel.getShipment().getLastUpdatedTime())) {
			LocalDateTime capturedTime = TenantZoneTime.getZoneDateTime(
					inboundBreakdownModel.getShipment().getLastUpdatedTime(), inboundBreakdownModel.getTenantId());
			if (!ObjectUtils.isEmpty(lastupdatDateTime) && lastupdatDateTime.isAfter(capturedTime)) {
				inboundBreakdownModel.addError("inbound.breakdown.transaction", "", ErrorType.ERROR);
				throw new CustomException();
			}
		}

		// Validate break down information
		breakDownValidation.validate(inboundBreakdownModel);
		LocalDateTime endTime = TenantZoneTime.getZoneDateTime(LocalDateTime.now(),
				inboundBreakdownModel.getTenantId());
		
		LOGGER.warn("calling creating inbound breakdown validation:: Start Time : {}, End Time : {}", startTime,
				endTime, inboundBreakdownModel.getShipment().getShipmentNumber());

		// 1. Create/Update data to Shipment_Master ** Tables
		ShipmentMaster shipmentMaster = new ShipmentMaster();
		if (!ObjectUtils.isEmpty(inboundBreakdownModel.getShipment())) {
			shipmentMaster.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber());
			shipmentMaster.setShipmentdate(inboundBreakdownModel.getShipment().getShipmentdate());
			shipmentMaster.setShipmentType(inboundBreakdownModel.getShipment().getShipmentType());
			shipmentMaster
					.setNatureOfGoodsDescription(inboundBreakdownModel.getShipment().getNatureOfGoodsDescription());
			shipmentMaster.setOrigin(inboundBreakdownModel.getShipment().getOrigin());
			shipmentMaster.setDestination(inboundBreakdownModel.getShipment().getDestination());
			shipmentMaster.setPiece(inboundBreakdownModel.getShipment().getPiece());
			shipmentMaster.setWeight(inboundBreakdownModel.getShipment().getWeight());
			shipmentMasterService.createShipment(shipmentMaster);
		}


		if (inboundBreakdownModel.getShipment().getPreBookedPieces()) {
			shipmentMaster.setDispatchYear(inboundBreakdownModel.getFlightId());
			inboundBreakdownDAO.updateAgentPlanningWorksheetShipmentStatus(shipmentMaster);
		}

		// 2. Create/Update data to Break Down ** and Shipment Verification Tables
		ShipmentVerificationModel sv = new ShipmentVerificationModel();
		sv.setFlightId(inboundBreakdownModel.getFlightId());
		sv.setShipmentId(shipmentMaster.getShipmentId());
		BigInteger breakdownPiece = BigInteger.ZERO;
		BigDecimal breakdownWeight = BigDecimal.ZERO;
		BigDecimal chargeableWeight = BigDecimal.ZERO;

		for (InboundBreakdownShipmentInventoryModel inventoryData : inboundBreakdownModel.getShipment()
				.getInventory()) {
			if (!inventoryData.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString())) {
				breakdownPiece = breakdownPiece.add(inventoryData.getPieces());
				breakdownWeight = breakdownWeight.add(inventoryData.getWeight());
				
				if(inventoryData.getChargeableWeight() != null && inventoryData.getChargeableWeight().compareTo(BigDecimal.ZERO) != 0) {					
					chargeableWeight=chargeableWeight.add(inventoryData.getChargeableWeight());
				}
			}
		}
		
		// Utility Method to Verify Whether HAWBHandling Feature is Enabled
		if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) {
			if(!ObjectUtils.isEmpty(inboundBreakdownModel.getHawbInfo())) {
				if(!StringUtils.isEmpty(inboundBreakdownModel.getHawbInfo().getHawbNumber())) {
					inboundBreakdownModel.getHawbInfo().setShipmentNumber(shipmentMaster.getShipmentNumber());
					inboundBreakdownModel.getHawbInfo().setShipmentdate(shipmentMaster.getShipmentdate());
					inboundBreakdownModel.getShipment().setHawbNumber(inboundBreakdownModel.getHawbInfo().getHawbNumber());
					inboundBreakdownModel.getShipment().setHouseNumber(inboundBreakdownModel.getHawbInfo().getHawbNumber());
					//Create ShipmentHouse Information for found case
					if(inboundBreakdownModel.getHawbInfo().getHawbPieces() == null) {
						inboundBreakdownModel.getHawbInfo().setHawbPieces(breakdownPiece);;
					}
					
					if(inboundBreakdownModel.getHawbInfo().getHawbWeight() == null) {
						inboundBreakdownModel.getHawbInfo().setHawbWeight(breakdownWeight);
					}
					
					if(inboundBreakdownModel.getHawbInfo().getHawbChargebleWeight() == null) {
						inboundBreakdownModel.getHawbInfo().setHawbChargebleWeight(chargeableWeight);
					}
					
					inboundBreakdownModel.getHawbInfo().setShipmentId(shipmentMaster.getShipmentId());
					inboundBreakdownModel.getHawbInfo().setShipmentType("HAWB");
					if(inboundBreakdownModel.getHawbInfo().getShipmentHouseId() == null)	{
						inventoryDao.createHouseAWBInfo(inboundBreakdownModel.getHawbInfo());
					}
				}
			}
		}
		
		if (FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)) {
			// update Shipment Verification for House level by handling feature
			if (!ObjectUtils.isEmpty(inboundBreakdownModel.getHawbInfo())
					&& !StringUtils.isEmpty(inboundBreakdownModel.getHawbInfo().getHawbNumber())) {
				InboundBreakdownShipmentModel requestInfo = new InboundBreakdownShipmentModel();
				requestInfo.setShipmentHouseId(inboundBreakdownModel.getHawbInfo().getShipmentHouseId());
				requestInfo.setShipmentId(shipmentMaster.getShipmentId());
				requestInfo.setFlightId(inboundBreakdownModel.getFlightId());
				InboundBreakdownShipmentModel shipmentHousesBreakdowninfo = inventoryDao
						.getShipmentHousesBreakDownPices(requestInfo);
				if (!ObjectUtils.isEmpty(shipmentHousesBreakdowninfo)) {
					breakdownPiece = breakdownPiece.add(shipmentHousesBreakdowninfo.getTotalShipmentHousesBreakDownPieces());
					breakdownWeight = breakdownWeight.add(shipmentHousesBreakdowninfo.getTotalShipmentHousesBreakDownWeight());
					sv.setBreakDownPieces(breakdownPiece);
					sv.setBreakDownWeight(breakdownWeight);
				} else {
					sv.setBreakDownPieces(breakdownPiece);
					sv.setBreakDownWeight(breakdownWeight);
				}
			} else {
				sv.setBreakDownPieces(breakdownPiece);
				sv.setBreakDownWeight(breakdownWeight);
			}
			sv.setInvokedFromBreakDown(Boolean.TRUE);
			sv = shipmentVerificationService.createShipmentVerification(sv);
		}else {		
			//update Shipment Verification
			sv.setBreakDownPieces(breakdownPiece);
			sv.setBreakDownWeight(breakdownWeight);
			sv.setInvokedFromBreakDown(Boolean.TRUE);
			sv = shipmentVerificationService.createShipmentVerification(sv);
		}

		// Set the ShipmentData to parent Model
		InboundBreakdownShipmentModel shipment = inboundBreakdownModel.getShipment();
		shipment.setShipmentVerificationId(sv.getImpShipmentVerificationId());
		shipment.setFlightId(inboundBreakdownModel.getFlightId());
		shipment.setShipmentId(shipmentMaster.getShipmentId());
		// set the irregularity pieces for triggering RCF Event
		shipment.setBreakDownPieces(breakdownPiece);
		shipment.setBreakDownWeight(breakdownWeight);

		List<InboundBreakdownShipmentModel> deleteInventorys = null;
		if(FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class) 
				&& !StringUtils.isEmpty(inboundBreakdownModel.getShipment().getHawbNumber())){
			 deleteInventorys = this.inboundBreakdownDAO.fetchHouseInventoryId(inboundBreakdownModel.getShipment());
		}else {			
			 deleteInventorys = this.inboundBreakdownDAO.fetchInventoryId(inboundBreakdownModel.getShipment());
		}
		
		
		// delete inventory SHCS
		if (Objects.nonNull(shipment.getInventory())) {
			for (InboundBreakdownShipmentInventoryModel inventroy : shipment.getInventory()) {
				inboundBreakdownDAO.deleteStorageSHCInfo(inventroy);
			}
		}

		// delete InboundBreakDownStrorageInfo
		if (Objects.nonNull(deleteInventorys)) {
			for (InboundBreakdownShipmentModel deleteInventory : deleteInventorys) {
				InboundBreakdownShipmentInventoryModel inventory = new InboundBreakdownShipmentInventoryModel();
				inventory.setInventoryId(deleteInventory.getId());
				this.inboundBreakdownDAO.deleteStorageInfo(inventory);
			}
		}

		List<InboundBreakdownShipmentInventoryModel> removeInventory = new ArrayList<>();
		List<InboundBreakdownShipmentInventoryModel> addInventory = new ArrayList<>();
		List<InboundBreakdownShipmentInventoryModel> requestedInventoryData = new ArrayList<>();
		for (InboundBreakdownShipmentInventoryModel inventoryData : inboundBreakdownModel.getShipment()
				.getInventory()) {
			if (Objects.isNull(inventoryData.getShipmentLocation())) {
				inventoryData.setShipmentLocation("");
			}
			if (Objects.isNull(inventoryData.getWarehouseLocation())) {
				inventoryData.setWarehouseLocation("");
			}
			if (Action.DELETE.toString().equalsIgnoreCase(inventoryData.getFlagCRUD())) {
				this.inventoryDao.deleteInventory(inventoryData);
				removeInventory.add(inventoryData);
			}
		}

		// Removing deleted inventories
		inboundBreakdownModel.getShipment().getInventory().removeAll(removeInventory);
		requestedInventoryData.addAll(inboundBreakdownModel.getShipment().getInventory());
		// adding request inventories
		addInventory.addAll(requestedInventoryData);
		if(CollectionUtils.isEmpty(addInventory) && !CollectionUtils.isEmpty(deleteInventorys)) {
			this.inboundBreakdownDAO.deleteTrolleyInfo(inboundBreakdownModel.getShipment());
		}

		// 5. Create/Update data to Shipment_Inventory ** Tables
		for (InboundBreakdownShipmentInventoryModel inventory : addInventory) {
			// Validate PO/DO/LOADED/TT/ASSIGNULDTROLLY SHOULD NOT INTIATED FOR INV LINE ITEM
			if (valdateCreatInventory(inventory)) {
				if (StringUtils.isEmpty(inventory.getShipmentLocation())) {
					inventory.setShipmentLocation(null);
				}
				if (StringUtils.isEmpty(inventory.getWarehouseLocation())) {
					inventory.setWarehouseLocation(null);
				}

				inventory.setShipmentId(shipmentMaster.getShipmentId());
				
				if(inboundBreakdownModel.getHawbInfo() != null) {					
					inventory.setShipmentHouseAWBId(inboundBreakdownModel.getHawbInfo().getShipmentHouseId());
				}
				
				inventory.setFlightId(inboundBreakdownModel.getFlightId());
				
				//added for Auditing
				inventory.setOrigin(shipmentMaster.getOrigin());
				inventory.setDestination(shipmentMaster.getDestination());
				inventory.setHandCarry(inboundBreakdownModel.getShipment().getHandCarry());
				inventory.setFlightKey(inboundBreakdownModel.getFlightNumber());
				inventory.setFlightOriginDate(inboundBreakdownModel.getFlightDate());

				inventory.setTerminal(inventory.getHandlingArea());
				
				if(FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class) 
						&& !StringUtils.isEmpty(inboundBreakdownModel.getShipment().getHawbNumber())){
					inventory.setHawbNumber(inboundBreakdownModel.getShipment().getHawbNumber());
					if(!ObjectUtils.isEmpty(inboundBreakdownModel.getHawbInfo())) {
						inventory.setHawbOrigin(inboundBreakdownModel.getHawbInfo().getHawbOrigin());
						inventory.setHawbDestination(inboundBreakdownModel.getHawbInfo().getHawbDestination());
						inventory.setHawbPieces(inboundBreakdownModel.getHawbInfo().getHawbPieces());
						inventory.setHawbWeight(inboundBreakdownModel.getHawbInfo().getHawbWeight());
					}
					
				}
				// Set Shipment Id
				// Create new Inventory
				inventoryDao.createInventory(inventory);
				// SHC
				createShc(inventory, inventory.getInventoryId());
				// House
				if (!CollectionUtils.isEmpty(inventory.getHouse())) {
					inventory.getHouse().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
				}
				createHouse(inventory, inventory.getInventoryId());
				
			}
		}

		// Create inventory at breakDown ULD Trolley and Storage info tables
		for (InboundBreakdownShipmentInventoryModel inventory : shipment.getInventory()) {
			// Validate PO/DO/LOADED/TT/ASSIGNULDTROLLY SHOULD NOT INTIATED FOR INV LINE
			// ITEM
			if (valdateCreatInventory(inventory)) {
				if (inventory.getPieces().compareTo(BigInteger.ZERO) == 0) {
					inventory.addError("bd.inventorypiecezero", "pieces", ErrorType.ERROR);
					throw new CustomException();
				}
				shipment.setUldNumber(inventory.getUldNumber());
				shipment.setUldDamage(inventory.getUldDamage());
				shipment.setWarehouseHandlingInstruction(inventory.getWarehouseHandlingInstruction());
				if (StringUtils.isEmpty(inventory.getHandlingMode())) {
					shipment.setHandlingMode("BREAK");
				} else {
					shipment.setHandlingMode(inventory.getHandlingMode());
				}
				shipment.setTransferType(inventory.getTransferType());
				// inserting into breakdown trolley info
				createBreakDownTrolleyInfo(shipment, inventory, inboundBreakdownModel);
			}
		}
		for (InboundBreakdownShipmentInventoryModel inventory : addInventory) {
		   if (!StringUtils.isEmpty(inventory.getShipmentLocation())) {
	           MoveableLocationTypeModel requestModel = new MoveableLocationTypeModel();
	           requestModel.setKey(inventory.getShipmentLocation());
	           requestModel = moveableLocationTypesValidator.split(requestModel);
	           if (!requestModel.getDummyLocation()) {
	               UldInfoModel uldMaster = new UldInfoModel();
	               uldMaster.setUldKey(inventory.getShipmentLocation());
	               //uldMaster.setInboundFlightId(inboundBreakdownModel.getFlightId());
	               uldMaster.setTerminal(inboundBreakdownModel.getTerminal());
	               uldMaster.setUldLocationCode(inboundBreakdownModel.getTerminal());
	               uldMaster.setHandlingCarrierCode(requestModel.getPart3());
	               uldMaster.setUldCarrierCode(requestModel.getPart3());
	               uldMaster.setUldNumber(requestModel.getPart2());
	               //uldMaster.setFlightBoardPoint(inboundBreakdownModel.getBoardingPoint());
	               //uldMaster.setFlightOffPoint(inboundBreakdownModel.getTenantAirport());
	               uldMaster.setMovableLocationType(requestModel.getLocationType());
	               uldMaster.setUldType(requestModel.getPart1());
	               uldMaster.setContentCode("C");
	               if (inventory.getUldDamage()) {
	                   uldMaster.setUldConditionType("DAM");
	               }
	               uldMaster.setFunctionName(UldMovementFunctionTypes.Names.INBOUNDBREAKDOWNSHIPMENTLOCATION);
	               uldMasterInfo.updateUldInfo(uldMaster);
	           }
	       }
		}
		
		// get the Sum of Pieces sent for RCF in StatusUpdateEvent
		BigInteger rcfStatusUpdateEventPieces = inboundBreakdownDAO.getTotalRcfPicesFormStatusUpdateEvent(shipment);

		// 6. If manifest pieces == break down pieces then fire the event using Event
		// Utility for AWB
		// RCF will be triggered for 5(assume document pieces iS 5) pieces from Inbound
		// Breakdown or Maintain Location
		// if FDCA of 1(assume) pieces is available ahead of location creation.
		if (ShipmentType.Type.AWB.equalsIgnoreCase(shipment.getShipmentType())
				&& shipment.getManifestPieces().compareTo(shipment.getBreakDownPieces()) == 0
				&& rcfStatusUpdateEventPieces.compareTo(shipment.getManifestPieces()) != 0
				|| (!ObjectUtils.isEmpty(shipment.getIrregularityPiecesFound())
						&& !ObjectUtils.isEmpty(shipment.getManifestPieces())
						&& (shipment.getManifestPieces().add(shipment.getIrregularityPiecesFound())
								.compareTo(shipment.getBreakDownPieces()) == 0))
				|| (!ObjectUtils.isEmpty(shipment.getIrregularityPiecesMissing()))
						&& !ObjectUtils.isEmpty(shipment.getManifestPieces())
						&& (shipment.getManifestPieces().subtract(shipment.getIrregularityPiecesMissing())
								.compareTo(shipment.getBreakDownPieces()) == 0)) {
			// calling RCF/NFD event at Controller by using flag

			inboundBreakdownModel.setShipmentId(shipment.getShipmentId());
			inboundBreakdownModel.setBreakDownPieces(shipment.getBreakDownPieces());
			inboundBreakdownModel.setBreakDownWeight(shipment.getBreakDownWeight());
			inboundBreakdownModel.setCheckForRCFNFDtrigger(true);

		}

		// 7. Create the booking and working list info for TransShipment shipments
		ArrivalManifestShipmentInfoModel shipmentData = new ArrivalManifestShipmentInfoModel();
		shipmentData.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber());
		shipmentData.setShipmentdate(inboundBreakdownModel.getShipment().getShipmentdate());
		shipmentData.setTerminal(inboundBreakdownModel.getTerminal());
		shipmentData.setUserType(inboundBreakdownModel.getUserType());
		shipmentData.setCreatedBy(inboundBreakdownModel.getCreatedBy());
		shipmentData.setCreatedOn(inboundBreakdownModel.getCreatedOn());
		shipmentData.setModifiedBy(inboundBreakdownModel.getModifiedBy());
		shipmentData.setModifiedOn(inboundBreakdownModel.getModifiedOn());

		List<ExportWorkingListModel> transhipmentsList = breakDownWorkingListDAO.fetchBookingDetails(shipmentData);
		if (!CollectionUtils.isEmpty(transhipmentsList)) {
			for (ExportWorkingListModel tempData : transhipmentsList) {

				ArrivalManifestByFlightModel flightData = new ArrivalManifestByFlightModel();
				flightData.setFlightId(tempData.getBookingFlightid());
				flightData.setSegmentId(tempData.getBookingSegmentid());
				shipmentData.setShipmentId(tempData.getShipmentid());
				ArrivalManifestByFlightModel flightDetails = breakDownWorkingListDAO
						.checkOutgoingFlightExists(flightData);

				if (Objects.isNull(flightDetails) || flightDetails.getImpArrivalManifestByFlightId().intValue() == 0) {
					breakDownWorkingListDAO.insertWorkingListFlight(flightData);
					shipmentData.setExportWorkiglistId(flightData.getImpArrivalManifestByFlightId());
				} else {
					shipmentData.setExportWorkiglistId(flightDetails.getImpArrivalManifestByFlightId());
				}
				BigInteger id = breakDownWorkingListDAO.checkShipmentExists(shipmentData);
				if (id.intValue() == 0) {
					shipmentData.setFlightId(tempData.getBookingFlightid());
					shipmentData.setPiece(tempData.getBookingPieces());
					shipmentData.setWeight(tempData.getBookingWeight());
					shipmentData.setOffloadedFlag(tempData.getPartShipment());
					if (Objects.nonNull(flightDetails)) {
						shipmentData.setImpArrivalManifestUldId(flightDetails.getImpArrivalManifestByFlightId());
					} else if (Objects.isNull(flightDetails)) {
						shipmentData.setImpArrivalManifestUldId(flightData.getImpArrivalManifestByFlightId());
					}
					breakDownWorkingListDAO.insertWorkingListShipment(shipmentData);
				}
			}
		}
		inboundBreakdownModel.setFlightHandledInSystem(
				breakDownWorkingListDAO.isFlighHandledInSystem(inboundBreakdownModel.getShipment().getShipmentNumber()));
		return inboundBreakdownModel;
	}

	/**
	 * validate inventoryLine
	 * 
	 * @return true conditions satisfy
	 * @throws CustomException
	 */
	private Boolean valdateCreatInventory(InboundBreakdownShipmentInventoryModel inventory) throws CustomException {
		// Validate PO/DO/LOADED/TT/ASSIGNULDTROLLY SHOULD NOT INTIATED FOR INV LINE
		// ITEM
		InboundBreakdownShipmentInventoryModel validateInvetory = inboundBreakdownDAO.validateInventory(inventory);
		if (ObjectUtils.isEmpty(validateInvetory)) {
			validateInvetory = new InboundBreakdownShipmentInventoryModel();
		}
		if (!inventory.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString())
				&& (inventory.getDeliveryOrderNo() == null
						|| inventory.getDeliveryOrderNo().compareTo(BigInteger.ZERO) == 0)
				&& (inventory.getThroughTransit() == null || !inventory.getThroughTransit())
				&& validateInvetory.getAssignedUldTrolley() == null
				&& (validateInvetory.getDeliveryRequestOrderNo() == null
						|| validateInvetory.getDeliveryRequestOrderNo().compareTo(BigInteger.ZERO) == 0)
				&& (validateInvetory.getLoaded() == null
						|| validateInvetory.getLoaded().compareTo(BigInteger.ZERO) == 0)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * Method to create breakdown ULD/Trolley info
	 * 
	 * @param shipment
	 * @throws CustomException
	 */
	private void createBreakDownTrolleyInfo(InboundBreakdownShipmentModel shipment,
			InboundBreakdownShipmentInventoryModel inventory, InboundBreakdownModel inboundData)
			throws CustomException {
		if (!inventory.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString())) {
			InboundBreakdownShipmentModel uldTrolley = this.inboundBreakdownDAO.selectBreakDownULDTrolleyInfo(shipment);
			Optional<InboundBreakdownShipmentModel> optUld = Optional.ofNullable(uldTrolley);
			BigInteger uldTrolleyId;
			if (!optUld.isPresent() || optUld.get().getId().intValue() == 0) {
				shipment.setFlightKey(inboundData.getFlightNumber());
				shipment.setFlightOriginDate(inboundData.getFlightDate());
				this.inboundBreakdownDAO.insertBreakDownULDTrolleyInfo(shipment);
				uldTrolleyId = shipment.getId();
			} else {
				inboundBreakdownDAO.updateBreakDownULDTrolleyInfo(shipment);
				uldTrolleyId = optUld.get().getId();
			}
			// If ULD, Does not exists insert into ULD Masters
			if (!shipment.getUldNumber().equalsIgnoreCase("Bulk")) {
				MoveableLocationTypeModel requestModel = new MoveableLocationTypeModel();
				requestModel.setKey(shipment.getUldNumber());
				requestModel = moveableLocationTypesValidator.split(requestModel);
				if (!requestModel.getDummyLocation()) {
					UldInfoModel uldMaster = new UldInfoModel();
					uldMaster.setUldKey(shipment.getUldNumber());
					uldMaster.setInboundFlightId(shipment.getFlightId());
					uldMaster.setTerminal(shipment.getTerminal());
					uldMaster.setUldLocationCode(shipment.getTerminal());
					uldMaster.setHandlingCarrierCode(inboundData.getCarrierCode());
					uldMaster.setUldCarrierCode(requestModel.getPart3());
					uldMaster.setUldNumber(requestModel.getPart2());
					uldMaster.setFlightBoardPoint(inboundData.getBoardingPoint());
					uldMaster.setFlightOffPoint(inboundData.getTenantAirport());
					uldMaster.setMovableLocationType(requestModel.getLocationType());
					uldMaster.setUldType(requestModel.getPart1());
					uldMaster.setApronCargoLocation("CARGO");
					uldMaster.setContentCode("C");
					uldMaster.setFunctionName(UldMovementFunctionTypes.Names.INBOUNDBREAKDOWN);
					uldMasterInfo.updateUldInfo(uldMaster);
				}
			}

			// Add the inventory for a given ULD/Trolley
			// inserting data to the break down storage info
			createBreakDownInventory(shipment, uldTrolleyId, inventory);

		}

	}

	/**
	 * Method to create breakdown storage info
	 * 
	 * @param shipment
	 * @param uldTrolleyId
	 * @throws CustomException
	 */
	private void createBreakDownInventory(InboundBreakdownShipmentModel shipment, BigInteger uldTrolleyId,
			InboundBreakdownShipmentInventoryModel inventory) throws CustomException {

		inventory.setImpArrivalManifestULDId(uldTrolleyId);
		InboundBreakdownShipmentInventoryModel invtr = this.inboundBreakdownDAO
				.selectInboundBreakdownShipmentInventoryModel(inventory);
		// Calculate proportional weight if inventory weight is empty
		if (shipment.getManifestPieces().compareTo(BigInteger.ZERO) != 0) {
			// Manifest Pieces and Manifest Weight Calculation Based on ULD or Bulk Selected
			ArrivalManifestShipmentInfoModel shipmentData = new ArrivalManifestShipmentInfoModel();
			shipmentData.setShipmentNumber(shipment.getShipmentNumber());
			shipmentData.setShipmentdate(shipment.getShipmentdate());
			shipmentData.setUldNumber(inventory.getUldNumber());
			if ("Bulk".equalsIgnoreCase(inventory.getUldNumber())) {
				shipmentData.setUldNumber(null);
			}

		}

		Optional<InboundBreakdownShipmentInventoryModel> optinv = Optional.ofNullable(invtr);
		BigInteger storageInfoId;
		if (!optinv.isPresent() || optinv.get().getInboundBreakdownStorageInfoId().intValue() == 0) {
			this.inboundBreakdownDAO.insertBreakDownStorageInfo(inventory);
			storageInfoId = inventory.getInboundBreakdownStorageInfoId();
		} else {
			storageInfoId = optinv.get().getInboundBreakdownStorageInfoId();
			this.inboundBreakdownDAO.updateBreakDownStorageInfo(inventory);
		}
		// Add Inventory specific SHC info
		createShcInfo(inventory, storageInfoId);
		// Add Inventory specific house info
		createHouseInfo(inventory, storageInfoId, shipment);

	}

	/**
	 * Method to create house info for a inventory
	 * 
	 * @param inventory
	 * @param inventoryId
	 * @throws CustomException
	 */
	private void createHouseInfo(InboundBreakdownShipmentInventoryModel inventory, BigInteger storageInfoId,
			InboundBreakdownShipmentModel shipment) throws CustomException {
		for (InboundBreakdownShipmentHouseModel house : inventory.getHouse()) {
			house.setShipmentInventoryId(storageInfoId);
			if (!this.inboundBreakdownDAO.checkBreakDownShipmentHouseModel(house)) {
				this.inboundBreakdownDAO.insertBreakDownShipmentHouseModel(house);
			} else {
				this.inboundBreakdownDAO.updateBreakDownShipmentHouseModel(house);
			}

			// Shipment_HouseInformation insertion goes here
			house.setShipmentId(shipment.getShipmentId());

			if (Objects.nonNull(inventoryDao.getShipmentMasterHouse(house))) {
				inventoryDao.updateShipmentMasterHouse(house);
			} else {
				inventoryDao.createShipmentMasterHouse(house);
			}
		}
	}

	/**
	 * Method to create SHC info for a inventory
	 * 
	 * @param inventory
	 * @param inventoryId
	 * @throws CustomException
	 */
	private void createShcInfo(InboundBreakdownShipmentInventoryModel inventory, BigInteger storageInfoId)
			throws CustomException {
		inboundBreakdownDAO.deleteStorageSHCInfo(inventory);
		for (InboundBreakdownShipmentShcModel shc : inventory.getShc()) {
			shc.setShipmentInventoryId(storageInfoId);
			if (!StringUtils.isEmpty(shc.getSpecialHandlingCode())
					&& !this.inboundBreakdownDAO.checkBreakDownStorageSHCInfo(shc)) {
				this.inboundBreakdownDAO.insertBreakDownStorageSHCInfo(shc);
			}
		}
	}

	/**
	 * Method to create SHC for a inventory
	 * 
	 * @param inventory
	 * @param inventoryId
	 * @throws CustomException
	 */
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	private void createShc(InboundBreakdownShipmentInventoryModel inventory, BigInteger inventoryId)
			throws CustomException {
		InboundBreakdownShipmentModel model=new InboundBreakdownShipmentModel();
		model.setId(inventoryId);
		BigInteger isexist=inboundBreakdownDAO.isInventoryExistInLocation(inventory);
		if(!ObjectUtils.isEmpty(isexist)) {
			this.inboundBreakdownDAO.deleteInventorySHCInfo(model);
			for (InboundBreakdownShipmentShcModel shc : inventory.getShc()) {
				shc.setShipmentInventoryId(inventoryId);
				if (!StringUtils.isEmpty(shc.getSpecialHandlingCode())) {
					inventoryDao.createInventoryShc(shc);
				}
			}
		}
		
	}

	/**
	 * Method to create House for a inventory
	 * 
	 * @param inventory
	 * @param inventoryId
	 * @throws CustomException
	 */
	private void createHouse(InboundBreakdownShipmentInventoryModel inventory, BigInteger storageInfoId)
			throws CustomException {
		// create storage house
		for (InboundBreakdownShipmentHouseModel house : inventory.getHouse()) {
			house.setShipmentInventoryId(inventory.getInventoryId());

			// Check for shipment master house and if not exists insert other wise increase
			// piece/weight
			BigInteger shipmentHouseId = inventoryDao.getShipmentMasterHouse(house);
			house.setShipmentHouseId(shipmentHouseId);
			if (!Optional.ofNullable(shipmentHouseId).isPresent()) {
				inventoryDao.createShipmentMasterHouse(house);
			} else {
				inventoryDao.updateShipmentMasterHouse(house);
			}

			// Insert the inventory house association data
			Boolean houseExists = inventoryDao.getInventoryHouse(house);
			Optional<Boolean> oHouse = Optional.ofNullable(houseExists);
			if (!oHouse.isPresent() || !oHouse.get()) {
				inventoryDao.createInventoryHouse(house);
			} else {
				inventoryDao.updateInventoryHouse(house);
			}
		}
	}

	@Override
	public List<InboundUldFlightModel> getFlightInfoForUld(InboundUldFlightModel inboundBreakdownModel)
			throws CustomException {

		List<InboundUldFlightModel> flightlist = inventoryDao.getFlightInforForULd(inboundBreakdownModel);
		return flightlist;
	}

	@Override
	public boolean isDataSyncCREnabled() throws CustomException {
		return breakDownWorkingListDAO.isDataSyncCREnabled();
	}

}