package com.ngen.cosys.shipment.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidator;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.DataSyncStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent;
import com.ngen.cosys.events.payload.MovedToExaminationEvent;
import com.ngen.cosys.events.producer.DataSyncStoreEventProducer;
import com.ngen.cosys.events.producer.InboundShipmentPiecesEqualsToBreakDownPiecesStoreEventProducer;
import com.ngen.cosys.events.producer.MovedToExaminationEventProducer;
import com.ngen.cosys.export.commonbooking.model.PartSuffix;
import com.ngen.cosys.export.commonbooking.service.CommonBookingService;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.shipment.constant.ImportExportIndicator;
import com.ngen.cosys.shipment.dao.MaintainShipmentLocationDAO;
import com.ngen.cosys.shipment.enums.CRUDStatus;
import com.ngen.cosys.shipment.model.FreightOut;
import com.ngen.cosys.shipment.model.LocationDuplicateCheck;
import com.ngen.cosys.shipment.model.SearchShipmentLocation;
import com.ngen.cosys.shipment.model.ShipmentInventory;
import com.ngen.cosys.shipment.model.ShipmentInventoryShcModel;
import com.ngen.cosys.shipment.model.ShipmentInventoryWorkingListModel;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.uldinfo.UldMovementFunctionTypes;
import com.ngen.cosys.uldinfo.model.UldInfoModel;
import com.ngen.cosys.uldinfo.service.UldInfoService;
import com.ngen.cosys.validator.dao.FlightValidationDao;
import com.ngen.cosys.validator.model.FlightValidateModel;

@Service
public class MaintainShipmentLocationServiceImpl implements MaintainShipmentLocationService {

	private static final String MSSG_TRAC_ASSIGNTGP06 = "TRAC_ASSIGNTGP06";
	private static final String FORM_CTRL_WEIGHT_INV = "weightInv";
	private static final String FORM_CTRL_PIECES_INV = "piecesInv";
	private static final String FORM_CTRL_CHARGEABLE_WEIGHT_INV = "chargeableWeightInv";
	private static final String SHP_OR_WAREHOUSE_LOCATION = "SHPORWAREHOUSELOCATION";

	private static final String DATEFORMAT = "yyyy-MM-dd'T'00:00:00";
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATEFORMAT);

	@Autowired
	private HAWBHandlingHelper hawbHandlingHelper;
	
	@Autowired
	InboundShipmentPiecesEqualsToBreakDownPiecesStoreEventProducer producer;

	@Autowired
	private MaintainShipmentLocationDAO maintainShipmentLocationDAO;

	@Autowired
	private MoveableLocationTypesValidator moveableLocationTypesValidator;

	@Autowired
	private UldInfoService uldInfoService;

	@Autowired
	private DataSyncStoreEventProducer dataEventProducer;
	
	@Autowired
	private CommonBookingService commonBookingService;
	
	@Autowired
    private FlightValidationDao flightValidationDao;

	@Autowired
	private DomesticInternationalHelper domesticInternationalHelper;
	
	@Autowired
	MovedToExaminationEventProducer movedToExaminationEventProducer;

	private static final Logger LOGGER = LoggerFactory.getLogger(MaintainShipmentLocationServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.ngen.cosys.shipment.service.MaintainShipmentLocationService#getSearchedLoc(com.ngen.cosys.shipment.model.SearchShipmentLocation)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ShipmentMaster getSearchedLoc(SearchShipmentLocation paramAWB) throws CustomException {
		if (!maintainShipmentLocationDAO.validateShipmentNumber(paramAWB)) {
			paramAWB.addError("no.record", "shipmentNumber", ErrorType.ERROR);
		}
		if(maintainShipmentLocationDAO.validateShipmentForWeighing(paramAWB)) {
			paramAWB.addError("SHPNOTACCPTED", "shipmentNumber", ErrorType.ERROR);
		}
		if (!CollectionUtils.isEmpty(paramAWB.getMessageList())) {
			throw new CustomException();
		}
		if(maintainShipmentLocationDAO.validateShipmentNumberForAcceptance(paramAWB)) {
			paramAWB.addError("NOACCEPTANCE", "shipmentNumber", ErrorType.ERROR);
		}
		if (!CollectionUtils.isEmpty(paramAWB.getMessageList())) {
			throw new CustomException();
		}
		return maintainShipmentLocationDAO.getSearchedLoc(paramAWB);
	}

	/* (non-Javadoc)
	 * @see com.ngen.cosys.shipment.service.MaintainShipmentLocationService#getSplittedLoc(com.ngen.cosys.shipment.model.ShipmentMaster)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void getSplittedLoc(ShipmentMaster paramAWB) throws CustomException {
		boolean chWeightInvFlag= false;
		for (ShipmentInventory paraAWBreuslt : paramAWB.getShipmentInventories()) {
			paraAWBreuslt.setShipmentNumber(paramAWB.getShipmentNumber());
			paraAWBreuslt.setShipmentDate(paramAWB.getShipmentDate());
			paraAWBreuslt.setShipmentType(paramAWB.getShipmentType());
			paraAWBreuslt.setOrigin(paramAWB.getOrigin());
			paraAWBreuslt.setDestination(paramAWB.getDestination());
			//Added as part of JV01-121
			if (Optional.ofNullable(paramAWB.getHouseInformation()).isPresent()
					&& Optional.ofNullable(paramAWB.getHouseInformation().getHouseId()).isPresent()) {
				paraAWBreuslt.setHouseId(paramAWB.getHouseInformation().getHouseId());
				paraAWBreuslt.setHawbNumber(paramAWB.getHwbNumber());

			}
			if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.ChargeableWeight.class) && 
					Optional.ofNullable(paraAWBreuslt.getChargeableWeightInv()).isPresent() )
			{
				chWeightInvFlag= true;	
			}
			// Set the location to an upper case
			if (!StringUtils.isEmpty(paraAWBreuslt.getShipmentLocation())) {
				paraAWBreuslt.setShipmentLocation(paraAWBreuslt.getShipmentLocation().toUpperCase());
			}

			if (!StringUtils.isEmpty(paraAWBreuslt.getWarehouseLocation())) {
				paraAWBreuslt.setWarehouseLocation(paraAWBreuslt.getWarehouseLocation().toUpperCase());
	            
	            // Check if it a valid location for the user
	            if(!maintainShipmentLocationDAO.checkValidWarehouseForUser(paraAWBreuslt)) {
	                
	            	paraAWBreuslt.addError("error.not.authorized.use.location", "warehouseLocation", ErrorType.ERROR);
	            }
	         }	
			if (!StringUtils.isEmpty(paraAWBreuslt.getOldShipmentLocation())) {
				paraAWBreuslt.setOldShipmentLocation(paraAWBreuslt.getOldShipmentLocation().toUpperCase());
			}

			if (!StringUtils.isEmpty(paraAWBreuslt.getOldWarehouseLocation())) {
				paraAWBreuslt.setOldWarehouseLocation(paraAWBreuslt.getOldWarehouseLocation().toUpperCase());
			}

			if (Action.CREATE.toString().equalsIgnoreCase(paraAWBreuslt.getFlagCRUD())
					&& (paraAWBreuslt.getPiecesInv() == null || paraAWBreuslt.getPiecesInv() == 0)) {
				paraAWBreuslt.addError(MSSG_TRAC_ASSIGNTGP06, FORM_CTRL_PIECES_INV, ErrorType.ERROR);
			}
			if (Action.CREATE.toString().equalsIgnoreCase(paraAWBreuslt.getFlagCRUD())
					&& (paraAWBreuslt.getWeightInv() == null || paraAWBreuslt.getWeightInv().equals(BigDecimal.ZERO))) {
				paraAWBreuslt.addError(MSSG_TRAC_ASSIGNTGP06, FORM_CTRL_WEIGHT_INV, ErrorType.ERROR);
			}
			//Added as part of JV01-121
			if (chWeightInvFlag &&		
					Action.CREATE.toString().equalsIgnoreCase(paraAWBreuslt.getFlagCRUD())
					&& (paraAWBreuslt.getChargeableWeightInv() == null || paraAWBreuslt.getWeightInv().equals(BigDecimal.ZERO))) {
				paraAWBreuslt.addError(MSSG_TRAC_ASSIGNTGP06, FORM_CTRL_CHARGEABLE_WEIGHT_INV, ErrorType.ERROR);
			}
			if (Action.CREATE.toString().equalsIgnoreCase(paraAWBreuslt.getFlagCRUD())
					&& (StringUtils.isEmpty(paraAWBreuslt.getShipmentLocation())
							&& StringUtils.isEmpty(paraAWBreuslt.getWarehouseLocation()))) {
				paraAWBreuslt.addError(SHP_OR_WAREHOUSE_LOCATION, "maintainLocationForm", ErrorType.ERROR);
			}
			if (paraAWBreuslt.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())
					&& paraAWBreuslt.getShcListInv() == null && !paramAWB.getShcList().isEmpty()
					&& (paramAWB.getShcList().size() > 1 || (paramAWB.getShcList().size() == 1
							&& !"GEN".equalsIgnoreCase(paramAWB.getShcList().get(0))))) {
				paraAWBreuslt.addError(MSSG_TRAC_ASSIGNTGP06, "shcListInv", ErrorType.ERROR);
			}
			if (paraAWBreuslt.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())
					&& (paraAWBreuslt.getHandlingArea() == null)) {
				paraAWBreuslt.addError(MSSG_TRAC_ASSIGNTGP06, "handlingArea", ErrorType.ERROR);
			}
			if (!paraAWBreuslt.getMessageList().isEmpty()) {
				throw new CustomException();
			}

			// Validate Valid SHC
			if (!CollectionUtils.isEmpty(paraAWBreuslt.getShcListInv())) {
				validateValidSHC(paraAWBreuslt);
			}

			// Validate Chargeable Location SHCs
			validateChargeableSHC(paraAWBreuslt);
		}

		// -----------------*****************START SPLIT
		// LOCATION******************-------------------

		List<ShipmentInventory> firstresult = paramAWB.getShipmentInventories().stream()
				.filter(ele -> "U".equals(ele.getFlagCRUD())).collect(Collectors.toList());
		List<ShipmentInventory> secondresult = paramAWB.getShipmentInventories().stream()
				.filter(ele -> "C".equals(ele.getFlagCRUD())).collect(Collectors.toList());

		for (ShipmentInventory first : firstresult) {
			for (ShipmentInventory second : secondresult) {
				if (second.getPiecesInv() >= first.getPiecesInv()) {
					second.addError("SHPLOC", "piecesInv", ErrorType.ERROR);
				}
				if (second.getWeightInv().compareTo(first.getWeightInv()) >= 0) {
					second.addError("SHPLOC", "weightInv", ErrorType.ERROR);
				}
				//Added as part of JV01-121
				if (chWeightInvFlag &&
						second.getChargeableWeightInv().compareTo(first.getChargeableWeightInv()) >= 0) {
					second.addError("SHPLOC", "chargeableWeightInv", ErrorType.ERROR);
				}
				if (!second.getMessageList().isEmpty()) {
					throw new CustomException();
				}
			}
		}
		for (ShipmentInventory paramAWBresult : paramAWB.getShipmentInventories()) {
			List<ShipmentInventory> referenceDetails = paramAWB.getShipmentInventories().stream()
					.filter(ele -> !StringUtils.isEmpty(ele.getReferenceDetails()))
					.filter(ele -> "U".equals(ele.getFlagCRUD())).collect(Collectors.toList());
					
			switch (paramAWBresult.getFlagCRUD()) {
			case CRUDStatus.CRUDType.CREATE:
				if (paramAWBresult.getFlightId() == 0) {
					maintainShipmentLocationDAO.getinsertMergedLocation(paramAWBresult);
				} else
					//set reference details
					if(!CollectionUtils.isEmpty(referenceDetails)) {						
						paramAWBresult.setReferenceDetails(referenceDetails.get(0).getReferenceDetails());
					}
					maintainShipmentLocationDAO.getMergedLocation(paramAWBresult);
				if (!ImportExportIndicator.EXPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())) {
					if (!paramAWB.getShipmentInventories().isEmpty()) {
						paramAWBresult.setId(paramAWB.getShipmentInventories().get(0).getShipmentInventoryId());
					}
					maintainShipmentLocationDAO.getinsertStorageInfoAfterSplitting(paramAWBresult);
					if (paramAWBresult.getShcListInv() != null && paramAWBresult.getStorageInfoId() != null) {
						for (ShipmentInventoryShcModel a : paramAWBresult.getShcListInv()) {
							paramAWBresult.setSpecialHandlingCodeInv(a.getShcInv());
							maintainShipmentLocationDAO.getInsertBreakDownStorageSHCInfoSplitting(paramAWBresult);
						}
					}
				}
				if (paramAWBresult.getShcListInv() != null) {
					for (ShipmentInventoryShcModel a : paramAWBresult.getShcListInv()) {
						paramAWBresult.setSpecialHandlingCodeInv(a.getShcInv());
							maintainShipmentLocationDAO.getUpdatedLocSHC(paramAWBresult);
					}
				}
				break;
			case CRUDStatus.CRUDType.UPDATE:
				for (ShipmentInventory first : firstresult) {
					for (ShipmentInventory second : secondresult) {
						first.setPiecesInv(first.getPiecesInv() - second.getPiecesInv());
						first.setWeightInv(first.getWeightInv().subtract(second.getWeightInv()));
						//Added as part of JV01-121
						if(chWeightInvFlag) {
						first.setChargeableWeightInv(first.getChargeableWeightInv().subtract(second.getChargeableWeightInv()));
						}
					}
				}
				maintainShipmentLocationDAO.getUpdatedInventoryLoc(paramAWBresult);
				if (!ImportExportIndicator.EXPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())) {
					maintainShipmentLocationDAO.getupdateStorageInfonSplit(paramAWBresult);
				}
				break;
			default:
				break;
			}
		}

		// -----------------*****************END SPLIT
		// LOCATION******************-------------------

		// Update the ULD inventory
		paramAWB.getShipmentInventories().forEach(t -> {
			// Set the base tenant/terminal/logged in user
			t.setTerminal(paramAWB.getTerminal());
			t.setCreatedBy(paramAWB.getLoggedInUser());
			t.setModifiedBy(paramAWB.getLoggedInUser());

		});
		this.updateULDInventory(paramAWB.getShipmentInventories());
	}

	/* (non-Javadoc)
	 * @see com.ngen.cosys.shipment.service.MaintainShipmentLocationService#getMergedLoc(com.ngen.cosys.shipment.model.ShipmentMaster)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void getMergedLoc(ShipmentMaster paramAWB) throws CustomException {
		for (ShipmentInventory paramAWBresult : paramAWB.getShipmentInventories()) {
			paramAWBresult.setShipmentNumber(paramAWB.getShipmentNumber());
			paramAWBresult.setShipmentDate(paramAWB.getShipmentDate());
			if (Optional.ofNullable(paramAWB.getHouseInformation()).isPresent()
					&& Optional.ofNullable(paramAWB.getHouseInformation().getHwbNumber()).isPresent()) {
				paramAWBresult.setHawbNumber(paramAWB.getHwbNumber());
			}

			if (paramAWBresult.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())
					&& (StringUtils.isEmpty(paramAWBresult.getShipmentLocation())
							&& StringUtils.isEmpty(paramAWBresult.getWarehouseLocation()))) {
				paramAWBresult.addError(SHP_OR_WAREHOUSE_LOCATION, "maintainLocationForm", ErrorType.ERROR);
			}
			if (!paramAWBresult.getMessageList().isEmpty()) {
				throw new CustomException();
			}
			// Validate Valid SHC
			if (!CollectionUtils.isEmpty(paramAWBresult.getShcListInv())) {
				validateValidSHC(paramAWBresult);
			}
			// Validate Chargeable Location SHCs
			validateChargeableSHC(paramAWBresult);
		}
		maintainShipmentLocationDAO.getMergedLoc(paramAWB);
		// Update the ULD inventory
		paramAWB.getShipmentInventories().forEach(t -> {
			// Set the base tenant/terminal/logged in user
			t.setTerminal(paramAWB.getTerminal());
			t.setCreatedBy(paramAWB.getLoggedInUser());
			t.setModifiedBy(paramAWB.getLoggedInUser());

		});
		this.updateULDInventory(paramAWB.getShipmentInventories());
	}

	/**
	 * @param param
	 * @throws CustomException
	 */
	public void validateChargeableSHC(ShipmentInventory param) throws CustomException {
		List<String> chargeableSHCGroup = maintainShipmentLocationDAO.getChegeableLocationSHC(param);

		List<String> billingShcGroup = new ArrayList<String>();
		List<String> billingShcGroupHanlingCodes = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(param.getShcListInv())) {
			billingShcGroup = maintainShipmentLocationDAO.getInventoryBillingGroup(param);
			billingShcGroupHanlingCodes = maintainShipmentLocationDAO.getInventoryBillingGroupHandlingCodes(param);
		}

		if (!CollectionUtils.isEmpty(chargeableSHCGroup)) {
			if (CollectionUtils.isEmpty(param.getShcListInv())) {
				param.addError("NO_SHC_DEFINED", "shcListInv", ErrorType.ERROR);
			} else {

				int numShcMatch = billingShcGroup.stream().filter(
						shcObj -> chargeableSHCGroup.stream().anyMatch(shcObj2 -> shcObj.equalsIgnoreCase(shcObj2)))
						.collect(Collectors.toList()).size();

				if (numShcMatch == 0) {
					param.addError("ATLEAST ONE SHC SHOULD MATCH WITH THE CHARGEABLE LOCATION { "
							+ param.getWarehouseLocation() + " } SHC", "warehouseloc", ErrorType.ERROR);

				} else if (numShcMatch == 1 && billingShcGroup.size() > 1) {
					param.addError(
							"Conflicting SHCs for the location " + param.getWarehouseLocation() + ", Currently entered "
									+ billingShcGroupHanlingCodes.stream().collect(Collectors.joining(",")),
							"warehouseloc", ErrorType.ERROR);
				}

			}

		} else {
			if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Awb.ShipmentAllowMULSHCS.class)) {
			if (billingShcGroup.size() > 1) {
				param.addError(
						"Conflicting SHCs for the inventory " + param.getWarehouseLocation() + ", Currently entered "
								+ billingShcGroupHanlingCodes.stream().collect(Collectors.joining(",")),
						"warehouseloc", ErrorType.ERROR);
			}
		  }
		}
		if (!param.getMessageList().isEmpty()) {
			throw new CustomException();
		}
	}

	/**
	 * @param param
	 * @throws CustomException
	 */
	public void validateValidSHC(ShipmentInventory param) throws CustomException {
		for (ShipmentInventoryShcModel shcMatch : param.getShcListInv()) {
			if (!maintainShipmentLocationDAO.fetchAllMasterShcs(shcMatch.getShcInv())) {
				shcMatch.addError("shccode", "shcInv", ErrorType.ERROR);
				throw new CustomException();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ngen.cosys.shipment.service.MaintainShipmentLocationService#getUpdatedLoc(com.ngen.cosys.shipment.model.ShipmentMaster)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void getUpdatedLoc(ShipmentMaster paramAWB) throws CustomException {

		BigInteger inventoryPieces = BigInteger.ZERO;
		BigDecimal inventoryWeight = BigDecimal.ZERO;
		BigDecimal inventoryChargeableWeight = BigDecimal.ZERO;
		// Transaction Validation. In case of done by only one user
		LocalDateTime lastupdatDateTime = maintainShipmentLocationDAO.getLastUpdatedDateTime(paramAWB);

		if (!ObjectUtils.isEmpty(lastupdatDateTime) && ObjectUtils.isEmpty(paramAWB.getLastUpdatedTime())) {
			paramAWB.addError("shipment.location.transaction", "", ErrorType.ERROR);
			throw new CustomException();
		}

		if (!ObjectUtils.isEmpty(paramAWB.getLastUpdatedTime())) {
			LocalDateTime capturedTime = paramAWB.getLastUpdatedTime();
			if (!ObjectUtils.isEmpty(lastupdatDateTime) && lastupdatDateTime.isAfter(capturedTime)) {
				paramAWB.addError("shipment.location.transaction", "", ErrorType.ERROR);
				throw new CustomException();
			}
		}

		List<ShipmentInventory> listDataforUTL = new ArrayList<>();
		for (ShipmentInventory paramValue : paramAWB.getShipmentInventories()) {
			if (StringUtils.isEmpty(paramValue.getAssignedUldTrolley())
					&& (paramValue.getLoaded() == null || paramValue.getLoaded().intValue() == 0)
					&& !ImportExportIndicator.EXPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())) {
				ShipmentInventory flight = null;
				if (!StringUtils.isEmpty(paramValue.getFlightKey())
						&& !ObjectUtils.isEmpty(paramValue.getFlightKeyDate())) {
					flight = maintainShipmentLocationDAO.checkForFlightId(paramValue);
					
					//Check for future data import flight
					FlightValidateModel validationFlight = new FlightValidateModel();
					validationFlight.setFlightKey((String) paramValue.getFlightKey());
					validationFlight.setFlightDate(paramValue.getFlightKeyDate());
					boolean isFlightArrived = flightValidationDao.validateArivalFlightForImport(validationFlight,
							paramAWB.getTenantAirport());
					if (!isFlightArrived) {
						paramValue.addError("incoming.flight.arrivalCheck", "flightKeyDate", ErrorType.ERROR);
						throw new CustomException();
					}
					
				} else {
					paramValue.addError("INVALIDFLT", "flightKey", ErrorType.ERROR);
					paramValue.addError("INVALIDFLT", "flightKeyDate", ErrorType.ERROR);
				}
				if (flight == null) {
					paramValue.addError("INVALIDFLT", "flightKeyDate", ErrorType.ERROR);
					throw new CustomException();
				} else {
					paramValue.setFlightId(flight.getFlightId());
				}
			}
		}
		for (ShipmentInventory value : paramAWB.getShipmentInventories()) {
			int count = 0;
			boolean chWeightInvFlag = false;
			value.setShipmentNumber(paramAWB.getShipmentNumber());
			value.setShipmentDate(paramAWB.getShipmentDate());
			value.setShipmentType(paramAWB.getShipmentType());
			value.setOrigin(paramAWB.getOrigin());
			value.setDestination(paramAWB.getDestination());
			//Added as part of JV01-121
			if (Optional.ofNullable(paramAWB.getHouseInformation()).isPresent()
					&& Optional.ofNullable(paramAWB.getHouseInformation().getHouseId()).isPresent()) {
				value.setHouseId(paramAWB.getHouseInformation().getHouseId());
				value.setHawbNumber(paramAWB.getHwbNumber());

			}
			if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.ChargeableWeight.class) && 
					Optional.ofNullable(value.getChargeableWeightInv()).isPresent() )
			{
				chWeightInvFlag= true;	
			}
			// Set the location to an upper case
			if (!StringUtils.isEmpty(value.getShipmentLocation())) {
				value.setShipmentLocation(value.getShipmentLocation().toUpperCase());
			}

			
			if (!StringUtils.isEmpty(value.getWarehouseLocation())) {
	            value.setWarehouseLocation(value.getWarehouseLocation().toUpperCase());
	            
	            value.setAccessLocation(maintainShipmentLocationDAO.checkValidWarehouseForUser(value));
	            
	            // Check if it a valid location for the user
	            if(!value.getAccessLocation()&&(Action.CREATE.toString().equalsIgnoreCase(value.getFlagCRUD())||Action.UPDATE.toString().equals(value.getFlagCRUD()))) {
	                
	            	
	            	value.addError("You are not Authorized to use the Location", "warehouseLocation", ErrorType.ERROR);
	            }
	         }	

			if (!StringUtils.isEmpty(value.getOldShipmentLocation())) {
				value.setOldShipmentLocation(value.getOldShipmentLocation().toUpperCase());
			}

			if (!StringUtils.isEmpty(value.getOldWarehouseLocation())) {
				value.setOldWarehouseLocation(value.getOldWarehouseLocation().toUpperCase());
			}

			if ((Action.CREATE.toString().equalsIgnoreCase(value.getFlagCRUD())
					|| Action.UPDATE.toString().equals(value.getFlagCRUD()))
					&& (value.getPiecesInv() == null || value.getPiecesInv() == 0)) {
				value.addError(MSSG_TRAC_ASSIGNTGP06, FORM_CTRL_PIECES_INV, ErrorType.ERROR);
			}
			if ((Action.CREATE.toString().equalsIgnoreCase(value.getFlagCRUD())
					|| Action.UPDATE.toString().equals(value.getFlagCRUD()))
					&& (value.getWeightInv() == null || value.getWeightInv().equals(BigDecimal.ZERO)
							|| value.getWeightInv().equals(new BigDecimal("0.0")))) {
				value.addError(MSSG_TRAC_ASSIGNTGP06, FORM_CTRL_WEIGHT_INV, ErrorType.ERROR);
			}
			//Added as part of JV01-121
			if (chWeightInvFlag &&	 
					(Action.CREATE.toString().equalsIgnoreCase(value.getFlagCRUD())
					|| Action.UPDATE.toString().equals(value.getFlagCRUD()))
					&& (value.getChargeableWeightInv() == null || value.getChargeableWeightInv().equals(BigDecimal.ZERO)
							|| value.getChargeableWeightInv().equals(new BigDecimal("0.0")))) {
				value.addError(MSSG_TRAC_ASSIGNTGP06, FORM_CTRL_CHARGEABLE_WEIGHT_INV, ErrorType.ERROR);
			}
			if ((Action.CREATE.toString().equalsIgnoreCase(value.getFlagCRUD())
					|| Action.UPDATE.toString().equalsIgnoreCase(value.getFlagCRUD()))
					&& (StringUtils.isEmpty(value.getShipmentLocation())
							&& StringUtils.isEmpty(value.getWarehouseLocation()) && !value.isHold()
							&& value.getUnableToLocate() == 0)) {
				value.addError(SHP_OR_WAREHOUSE_LOCATION, "maintainLocationForm", ErrorType.ERROR);
			}
			if (Action.CREATE.toString().equalsIgnoreCase(value.getFlagCRUD()) && value.getShcListInv() == null
					&& !paramAWB.getShcList().isEmpty()
					&& (paramAWB.getShcList().size() > 1 || (paramAWB.getShcList().size() == 1
							&& !"GEN".equalsIgnoreCase(paramAWB.getShcList().get(0))))) {
				value.addError(MSSG_TRAC_ASSIGNTGP06, "shcListInv", ErrorType.ERROR);
			}
			if (Action.CREATE.toString().equalsIgnoreCase(value.getFlagCRUD()) && (value.getHandlingArea() == null)) {
				value.addError(MSSG_TRAC_ASSIGNTGP06, "handlingArea", ErrorType.ERROR);
			}
			if (paramAWB.getShipmentTypeflag().equalsIgnoreCase("IMPORT")) {
				if (!maintainShipmentLocationDAO.checkForAWBFlightDetailsForImport(value)) {
					value.addError("DATESTACHECK", "flightKeyDate", ErrorType.ERROR);
				}
			}

			int warehousecount = 0;
			for (ShipmentInventory shpData : paramAWB.getShipmentInventories()) {

				LocationDuplicateCheck duplicateCheck1 = new LocationDuplicateCheck(value.getShipmentLocation(),
						value.getWarehouseLocation(), value.getFlightId(), value.getPartSuffix());
				LocationDuplicateCheck duplicateCheck2 = new LocationDuplicateCheck(shpData.getShipmentLocation(),
						shpData.getWarehouseLocation(), shpData.getFlightId(), shpData.getPartSuffix());
				duplicateCheck2.toString();
				duplicateCheck1.toString();
				if (duplicateCheck1.equals(duplicateCheck2) && 
						(!shpData.isHold() && StringUtils.isEmpty(shpData.getDeliveryRequestOrderNo()) && StringUtils.isEmpty(shpData.getDeliveryOrderNo()))) {
					if (!Objects.isNull(value.getShipmentLocation())) {
						count++;
					} else if (!Objects.isNull(value.getWarehouseLocation())
							|| !StringUtils.isEmpty(value.getWarehouseLocation())) {
						warehousecount++;
					}
				}
				Object[] messageshpLoc = { shpData.getShipmentLocation() };
				Object[] messagewarLoc = { shpData.getWarehouseLocation() };
				if (count > 1) {
					shpData.addError("DUPLICATE", "maintainLocationForm", ErrorType.ERROR, messageshpLoc);
					count = 1;
				}
				if (warehousecount > 1) {
					shpData.addError("DUPLICATE", "maintainLocationForm", ErrorType.ERROR, messagewarLoc);
					warehousecount = 1;
				}
				if (!shpData.getMessageList().isEmpty()) {
					throw new CustomException();
				}
			}

			// Validate Valid SHC
			if (!CollectionUtils.isEmpty(value.getShcListInv())) {
				validateValidSHC(value);
			}
			// Validate Chargeable Location SHCs only for Import
			if (ImportExportIndicator.IMPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())) {
				validateChargeableSHC(value);
			}
			
			//set scale
			value.setWeightInv(value.getWeightInv().setScale(1, RoundingMode.HALF_EVEN));
			
			inventoryPieces = inventoryPieces.add(BigInteger.valueOf(value.getPiecesInv().intValue()));
			inventoryWeight = inventoryWeight.add(value.getWeightInv());
			//Added as part of JV01-121
			if (chWeightInvFlag) {
				inventoryChargeableWeight = inventoryChargeableWeight.add(value.getChargeableWeightInv());
			}

			if (Action.UPDATE.toString().equals(value.getFlagCRUD())
					&& value.getUnableToLocate() > value.getPiecesInv()) {
				value.addError("UTLPIECESNUM", "unableToLocate", ErrorType.ERROR);
			}
			if (!value.getMessageList().isEmpty()) {
				throw new CustomException();
			}
			if (Action.UPDATE.toString().equals(value.getFlagCRUD())) {
				listDataforUTL.add(value);
			}

		}

		for (ShipmentInventory value : paramAWB.getShipmentInventories()) {
			//Added as part of JV01-121
			boolean chWeightInvFlag= false;
			if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.ChargeableWeight.class) && 
					Optional.ofNullable(value.getChargeableWeightInv()).isPresent() )
			{
				chWeightInvFlag= true;	
			}
			if ((BigInteger.valueOf(value.getPiecesInv().intValue()).compareTo(inventoryPieces) > 0
					&& ((Action.CREATE.toString().equals(value.getFlagCRUD()))
							|| (Action.UPDATE.toString().equals(value.getFlagCRUD()))))) {
				value.addError("SHPLOC", FORM_CTRL_PIECES_INV, ErrorType.ERROR);
			}
			if ((value.getWeightInv().compareTo(inventoryWeight) > 0)
					&& ((Action.CREATE.toString().equals(value.getFlagCRUD()))
							|| (Action.UPDATE.toString().equals(value.getFlagCRUD())))) {
				value.addError("SHPLOC", FORM_CTRL_WEIGHT_INV, ErrorType.ERROR);
			}
			//Added as part of JV01-121
			if (chWeightInvFlag) {
				if ((value.getChargeableWeightInv().compareTo(inventoryChargeableWeight) > 0)
						&& ((Action.CREATE.toString().equals(value.getFlagCRUD()))
								|| (Action.UPDATE.toString().equals(value.getFlagCRUD())))) {
					value.addError("chgweight.comparison", FORM_CTRL_CHARGEABLE_WEIGHT_INV, ErrorType.ERROR);
				}
			}
			if (!value.getMessageList().isEmpty()) {
				throw new CustomException();
			}
		}

		BigInteger freightOutPieces = BigInteger.ZERO;
		BigDecimal freightOutWeight = BigDecimal.ZERO;
		//Added as part of JV01-121
		BigDecimal freightOutChargeableWeight = BigDecimal.ZERO;
		boolean chWeightFreightFlag= false;
		
		if (!CollectionUtils.isEmpty(paramAWB.getFreightOutArray())) {
			for (FreightOut t : paramAWB.getFreightOutArray()) {
				//Added as part of JV01-121
				if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.ChargeableWeight.class) && 
						Optional.ofNullable(t.getChargeableWeightFreightOut()).isPresent() )
				{
					chWeightFreightFlag= true;	
				}
				freightOutPieces = freightOutPieces.add(BigInteger.valueOf(t.getPiecesFreightOut()));
				freightOutWeight = freightOutWeight.add(t.getWeightFreightOut());
				//Added as part of JV01-121
				if(chWeightFreightFlag) {
				freightOutChargeableWeight = freightOutChargeableWeight.add(t.getChargeableWeightFreightOut());}
			}
		}
		BigInteger totalPieces = freightOutPieces.add(inventoryPieces);
		BigDecimal totalWeight = freightOutWeight.add(inventoryWeight);
		//Added as part of JV01-121
		BigDecimal totalChargeableWeight = freightOutChargeableWeight.add(inventoryChargeableWeight);
		
		HAWBHandlingHelperRequest hAWBHandlingHelperRequest = new HAWBHandlingHelperRequest();
		hAWBHandlingHelperRequest.setShipmentNumber(paramAWB.getShipmentNumber());
		hAWBHandlingHelperRequest.setShipmentDate(paramAWB.getShipmentDate());
		boolean handledByHouse = hawbHandlingHelper.isHandledByHouse(hAWBHandlingHelperRequest);
	
		// Check the weight is equal to shipment weight if inventory pieces matches with shipment pieces
		if (!ObjectUtils.isEmpty(paramAWB.getPieces()) && !ObjectUtils.isEmpty(paramAWB.getWeight())
				&& !ObjectUtils.isEmpty(inventoryPieces) && !ObjectUtils.isEmpty(inventoryWeight)
				&& ((totalPieces.compareTo(BigInteger.valueOf(paramAWB.getPieces())) >= 0
						&& totalWeight.compareTo(paramAWB.getWeight()) != 0)
						|| (((!maintainShipmentLocationDAO.checkIfPresentInAcceptance(paramAWB)
								&& totalPieces.compareTo(BigInteger.valueOf(paramAWB.getPieces())) < 0)
								|| (maintainShipmentLocationDAO.checkIfPresentInAcceptance(paramAWB)
										&& totalPieces.compareTo(BigInteger.valueOf(paramAWB.getPieces())) != 0))
								&& totalWeight.compareTo(paramAWB.getWeight()) >= 0))) {

			//Added as part of JV01-121
			if (handledByHouse) {
				paramAWB.addError("data.house.inventory.weight.not.matching.with.house.weight", FORM_CTRL_WEIGHT_INV,
						ErrorType.ERROR);
			} else {
				paramAWB.addError("data.shipment.inventory.weight.not.matching.with.shipment.weight",
						FORM_CTRL_WEIGHT_INV, ErrorType.ERROR);
			}
			
			if (!CollectionUtils.isEmpty(paramAWB.getMessageList())) {
				throw new CustomException();
			}
		}
		// Check the total inv chargeable weight is equal to hawb/shipment chargeable weight
		if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.ChargeableWeight.class)) {
			if (!ObjectUtils.isEmpty(paramAWB.getPieces()) && !ObjectUtils.isEmpty(paramAWB.getChargeableWeight())
					&& !ObjectUtils.isEmpty(inventoryPieces) && !ObjectUtils.isEmpty(inventoryChargeableWeight)
					&& ((totalPieces.compareTo(BigInteger.valueOf(paramAWB.getPieces())) >= 0
							&& totalChargeableWeight.compareTo(paramAWB.getChargeableWeight()) != 0)
							|| (((!maintainShipmentLocationDAO.checkIfPresentInAcceptance(paramAWB)
									&& totalPieces.compareTo(BigInteger.valueOf(paramAWB.getPieces())) < 0)
									|| (maintainShipmentLocationDAO.checkIfPresentInAcceptance(paramAWB)
											&& totalPieces
													.compareTo(BigInteger.valueOf(paramAWB.getPieces())) != 0))
									&& totalChargeableWeight.compareTo(paramAWB.getChargeableWeight()) >= 0))) {
				if (handledByHouse) {
					paramAWB.addError("house.chgweight.validation", FORM_CTRL_CHARGEABLE_WEIGHT_INV,
							ErrorType.ERROR);
				} else {
					paramAWB.addError("shp.chgweight.validation", FORM_CTRL_CHARGEABLE_WEIGHT_INV, ErrorType.ERROR);
				}

			}
			if (!CollectionUtils.isEmpty(paramAWB.getMessageList())) {
				throw new CustomException();
			}
		}
		// ------------------------------------*****************START**************--------------------------------

		if (!paramAWB.getShipmentTypeflag().equalsIgnoreCase("EXPORT")) {
			for (ShipmentInventory paramFlightresult : paramAWB.getShipmentInventories()) {
				paramFlightresult.setShipmentNumber(paramAWB.getShipmentNumber());
				paramFlightresult.setShipmentDate(paramAWB.getShipmentDate());
				if (Optional.ofNullable(paramAWB.getHouseInformation()).isPresent()
						&& Optional.ofNullable(paramAWB.getHouseInformation().getHwbNumber()).isPresent()) {
					paramFlightresult.setHawbNumber(paramAWB.getHwbNumber());
				}
				if ("C".equals(paramFlightresult.getFlagCRUD())) {
					if (paramFlightresult.getFlightKey() == null) {
						paramFlightresult.addError("TRAC_ASSIGNTGP06", "flightKey", ErrorType.ERROR);
					}
					if (paramFlightresult.getFlightKeyDate() == null) {
						paramFlightresult.addError("TRAC_ASSIGNTGP06", "flightKeyDate", ErrorType.ERROR);
					}
					if (!paramFlightresult.getMessageList().isEmpty()) {
						throw new CustomException();
					}
					maintainShipmentLocationDAO.getUpdatedLoc(paramFlightresult);
					//Added as part of JV01-121
					if (handledByHouse) {
						LOGGER.warn("Creating the Location for ShipmentNumber ::" + paramAWB.getShipmentNumber() + ", "
								+ "House Id :: " + paramFlightresult.getHouseId() + ", "
								+ "WareHouseLocation :: " + paramFlightresult.getWarehouseLocation());
					}
					else {
						LOGGER.warn("Creating the Location for ShipmentNumber :: " + paramAWB.getShipmentNumber() + ", "
								+ "WareHouseLocation :: " + paramFlightresult.getWarehouseLocation());}
					
					String invShcs = " ";
					if (paramFlightresult.getShcListInv() != null) {
						for (ShipmentInventoryShcModel a : paramFlightresult.getShcListInv()) {
								if(!ObjectUtils.isEmpty(paramFlightresult.getShipmentInventoryId())) {
								paramFlightresult.setSpecialHandlingCodeInv(a.getShcInv());
								maintainShipmentLocationDAO.getUpdatedLocSHC(paramFlightresult);
								invShcs = " " + invShcs + a.getShcInv();
							}
						}
					}
					//Added as part of JV01-121
					if (handledByHouse) {
						LOGGER.warn("Creating the Location for ShipmentNumber ::" + paramAWB.getShipmentNumber() + ", "
								+ "House Id :: " + paramFlightresult.getHouseId() + ", "
								+ "WareHouseLocation :: " + paramFlightresult.getWarehouseLocation() + " InventoryShcs :: "
								+ invShcs);
					}
					else {
						LOGGER.warn("Creating the Location for ShipmentNumber ::" + paramAWB.getShipmentNumber() + ", "
								+ "WareHouseLocation :: " + paramFlightresult.getWarehouseLocation()
								+ " InventoryShcs :: " + invShcs);}
				}
			}
		} else {
			for (ShipmentInventory paramFlightresult : paramAWB.getShipmentInventories()) {
				if ("C".equals(paramFlightresult.getFlagCRUD())) {
					maintainShipmentLocationDAO.getUpdateExportRecords(paramFlightresult);
					if (paramFlightresult.getShcListInv() != null) {
						for (ShipmentInventoryShcModel a : paramFlightresult.getShcListInv()) {
							if(!ObjectUtils.isEmpty(paramFlightresult.getShipmentInventoryId())) {
								paramFlightresult.setSpecialHandlingCodeInv(a.getShcInv());
									maintainShipmentLocationDAO.getUpdatedLocSHC(paramFlightresult);
							}
						}
					}
				}
			}
		}

		// ---------------------------********************END*****************----------------------------------
		
		maintainShipmentLocationDAO.getupdatedDeliveredOn(paramAWB);
		if (!CollectionUtils.isEmpty(listDataforUTL)) {

			//////// ***********************START UNABLE TO LOCATE*************************************--------------------------------

			for (ShipmentInventory paramAWBresult : listDataforUTL) {
				//Added as part of JV01-121
				boolean chWeightInvFlag= false;
				if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.ChargeableWeight.class) && 
						Optional.ofNullable(paramAWBresult.getChargeableWeightInv()).isPresent() )
				{
					chWeightInvFlag= true;	
				}
				ShipmentInventory obj1 = new ShipmentInventory();
				obj1.setShipmentId(paramAWBresult.getShipmentId());
				if (Optional.ofNullable(paramAWB.getHouseInformation()).isPresent()
						&& Optional.ofNullable(paramAWB.getHouseInformation().getHouseId()).isPresent()) {
					obj1.setHouseId(paramAWB.getHouseInformation().getHouseId());
					obj1.setHawbNumber(paramAWB.getHwbNumber());
				}
				obj1.setPiecesInv(paramAWBresult.getUnableToLocate());
				obj1.setShipmentLocation(paramAWBresult.getShipmentLocation());
				BigDecimal pie = new BigDecimal(paramAWBresult.getPiecesInv());
				BigDecimal utlPie = new BigDecimal(paramAWBresult.getUnableToLocate());
				obj1.setWeightInv(
						((paramAWBresult.getWeightInv().divide(pie, 2, RoundingMode.HALF_UP)).multiply(utlPie)));
				//Added as part of JV01-121
				if (chWeightInvFlag) {
					obj1.setChargeableWeightInv(
							((paramAWBresult.getChargeableWeightInv().divide(pie, 2, RoundingMode.HALF_UP))
									.multiply(utlPie)));}
				obj1.setWarehouseLocation(paramAWBresult.getWarehouseLocation());
				if (!StringUtils.isEmpty(paramAWBresult.getShipmentLocation())
						&& !StringUtils.isEmpty(paramAWBresult.getWarehouseLocation())) {
					obj1.setRemarks(
							paramAWBresult.getShipmentLocation() + " and " + paramAWBresult.getWarehouseLocation());
				}
				if (!StringUtils.isEmpty(paramAWBresult.getShipmentLocation())
						&& StringUtils.isEmpty(paramAWBresult.getWarehouseLocation())) {
					obj1.setRemarks(paramAWBresult.getShipmentLocation());
				}
				if (StringUtils.isEmpty(paramAWBresult.getShipmentLocation())
						&& !StringUtils.isEmpty(paramAWBresult.getWarehouseLocation())) {
					obj1.setRemarks(paramAWBresult.getWarehouseLocation());
				}
				obj1.setFlightId(paramAWBresult.getFlightId());
				obj1.setFlightKey(paramAWBresult.getFlightKey());
				obj1.setFlightKeyDate(paramAWBresult.getFlightKeyDate());
				obj1.setHandlingArea(paramAWBresult.getHandlingArea());
				obj1.setShcListInv(paramAWBresult.getShcListInv());

				if (paramAWBresult.getPiecesInv() == paramAWBresult.getUnableToLocate()) {
					maintainShipmentLocationDAO.getDeletedInventorySHCs(paramAWBresult);
					maintainShipmentLocationDAO.getDeletedInventory(paramAWBresult);
					// For import case delete breakdown storage info
					if (!ImportExportIndicator.EXPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())) {
						//delete storage info piecesInv equal unable to locate pices
						maintainShipmentLocationDAO.deleteStorageInfo(paramAWBresult);
						paramAWB.getShipmentInventories().remove(paramAWBresult);
					}
				} else {
					paramAWBresult.setPiecesInv(paramAWBresult.getPiecesInv() - paramAWBresult.getUnableToLocate());
					paramAWBresult.setWeightInv(paramAWBresult.getWeightInv().subtract(obj1.getWeightInv()));
					//Added as part of JV01-121
					if (chWeightInvFlag) {
						paramAWBresult.setChargeableWeightInv(
								paramAWBresult.getChargeableWeightInv().subtract(obj1.getChargeableWeightInv()));
					}
					maintainShipmentLocationDAO.getUpdatedInventoryLoc(paramAWBresult);
					// For import case storing and updating breakdown tables
					if ((paramAWBresult.getUnableToLocate() != 0
							&& paramAWBresult.getPiecesInv() != paramAWBresult.getUnableToLocate())) {
						for (ShipmentInventory shipmentInventory : paramAWB.getShipmentInventories()) {
							if (shipmentInventory.getShipmentInventoryId() == paramAWBresult.getShipmentInventoryId()) {
								shipmentInventory.setPiecesInv(paramAWBresult.getPiecesInv());
								shipmentInventory.setWeightInv(paramAWBresult.getWeightInv());
								//Added as part of JV01-121
								if (chWeightInvFlag) {
									shipmentInventory.setChargeableWeightInv(paramAWBresult.getChargeableWeightInv());
								}
							}
						}
					}
					String invShcs = " ";
					if (paramAWBresult.getShcListInv() != null) {
							maintainShipmentLocationDAO.getDeletedInventorySHCs(paramAWBresult);
						for (ShipmentInventoryShcModel a : paramAWBresult.getShcListInv()) {
							paramAWBresult.setSpecialHandlingCodeInv(a.getShcInv());
							maintainShipmentLocationDAO.getUpdatedLocSHC(paramAWBresult);
							invShcs = " " + invShcs + a.getShcInv();
						}
					}
					//Added as part of JV01-121
					if (handledByHouse) {
						LOGGER.warn("Creating the Location for ShipmentNumber ::" + paramAWB.getShipmentNumber() + ", "
								+ "House Id :: " + paramAWBresult.getHouseId() + ", " + "WareHouseLocation :: "
								+ paramAWBresult.getWarehouseLocation() + " InventoryShcs :: " + invShcs);
					}
					else {
						LOGGER.warn("Creating the Location for ShipmentNumber ::" + paramAWB.getShipmentNumber() + ", "
								+ "WareHouseLocation :: " + paramAWBresult.getWarehouseLocation() + " InventoryShcs :: "
								+ invShcs);}
				}
				if (paramAWBresult.getUnableToLocate() != 0) {
					if (paramAWBresult.getFlightId() == 0) {
						maintainShipmentLocationDAO.getInsertUTLPieces(obj1);
					} else
						maintainShipmentLocationDAO.getinsertUTLRecordsForFlight(obj1);
					if (!paramAWB.getShipmentInventories().isEmpty()) {
						paramAWBresult.setId(paramAWB.getShipmentInventories().get(0).getShipmentInventoryId());
					}
					// For import case storing and updating breakdown tables
					if (!ImportExportIndicator.EXPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())) {
						obj1.setOldWarehouseLocation(null);
						obj1.setOldShipmentLocation(null);
						paramAWB.getShipmentInventories().add(obj1);
					}

					if (paramAWBresult.getShcListInv() != null) {
						for (ShipmentInventoryShcModel a : obj1.getShcListInv()) {
							obj1.setSpecialHandlingCodeInv(a.getShcInv());
							maintainShipmentLocationDAO.getUpdatedLocSHC(obj1);
						}
					}
				}
				
			}
			
			//////// ***********************END*************************************--------------------------------
		}

		// Update the ULD inventory
		paramAWB.getShipmentInventories().forEach(t -> {
			// Set the base tenant/terminal/logged in user
			t.setTerminal(paramAWB.getTerminal());
			t.setCreatedBy(paramAWB.getLoggedInUser());
			t.setModifiedBy(paramAWB.getLoggedInUser());

		});
		this.updateULDInventory(paramAWB.getShipmentInventories());

		// Capture Break Down
		this.captureBreakDownInformation(paramAWB);
	}

	/*
	 * Method to capture break down information for import/transshipment shipments
	 */
	private void captureBreakDownInformation(ShipmentMaster paramAWB) throws CustomException {
		if (!ImportExportIndicator.EXPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())
				&& !CollectionUtils.isEmpty(paramAWB.getShipmentInventories())) {
			// Filter out not loaded shipments and flights and it's inventory
			List<ShipmentInventory> notLoadedInventory = paramAWB.getShipmentInventories().stream()
					.filter(item -> StringUtils.isEmpty(item.getAssignedUldTrolley()))
					.filter(item -> (item.getLoaded() == null || item.getLoaded().intValue() == 0))
					.collect(Collectors.toList());

			Map<Integer, List<ShipmentInventory>> groupByFlightInventory = notLoadedInventory.stream()
					.collect(Collectors.groupingBy(ShipmentInventory::getFlightId));
		

			if (!CollectionUtils.isEmpty(groupByFlightInventory)) {
				for (Map.Entry<Integer, List<ShipmentInventory>> entry : groupByFlightInventory.entrySet()) {

					//List of inventory items by flight
					List<ShipmentInventory> inventoryLineItems = entry.getValue();
					
					//Shipment Id
					BigInteger shipmentId = BigInteger.valueOf(entry.getValue().get(0).getShipmentId());
					
					//Iterate each line item and check against the part suffix whether it is loaded OR not
					for(ShipmentInventory t : inventoryLineItems) {
						// once inventory loaded with uld not updating/inserting location info storageInfo
						BigInteger verificationId = this.maintainShipmentLocationDAO
								.getShipmentVerification(t);
						t.setVerificationId(verificationId);
						Boolean isLoaded = this.maintainShipmentLocationDAO.isShipmentLoaded(t);
						
						//Set the loaded flag
						t.setAutoloadFlag(isLoaded);
					}
					
					// Create/Update Shipment Verification if not exists
					ShipmentInventory shipmentVerificationModel = new ShipmentInventory();
					shipmentVerificationModel.setFlightId(entry.getKey().intValue());
					shipmentVerificationModel.setShipmentId(shipmentId.intValue());
					//Added as part of JV01-121
					if (Optional.ofNullable(paramAWB.getHouseInformation()).isPresent()
							&& Optional.ofNullable(paramAWB.getHouseInformation().getHouseId()).isPresent()) {
						shipmentVerificationModel.setHouseId(paramAWB.getHouseInformation().getHouseId());
					}
					shipmentVerificationModel.setPiecesInv(0);
					shipmentVerificationModel.setWeightInv(BigDecimal.ZERO);
					//Added as part of JV01-121
					shipmentVerificationModel.setChargeableWeightInv(BigDecimal.ZERO);
					shipmentVerificationModel.setTerminal(paramAWB.getTerminal());
					shipmentVerificationModel.setShipmentNumber(paramAWB.getShipmentNumber());
					shipmentVerificationModel.setShipmentDate(paramAWB.getShipmentDate());

					// Create Shipment Verification Info
					this.maintainShipmentLocationDAO.createShipmentVerification(shipmentVerificationModel);

					// Set shipment verification id Create/Update Break Down ULD Trolley info
					inventoryLineItems
							.forEach(t -> t.setParentReferenceId(shipmentVerificationModel.getParentReferenceId()));
					this.maintainShipmentLocationDAO.createShipmentVerificationULDTrolleyInfo(inventoryLineItems);

					// Create Break Down Storage Info
					this.maintainShipmentLocationDAO.createShipmentVerificationStorageInfo(inventoryLineItems);
						
					// Assign shipment if booked to export flight
					if (MultiTenantUtility.isTranshipment(paramAWB.getOrigin(), paramAWB.getDestination())) {
					    //Add the list of Part Suffix's
					    List<String> partSuffixs = new ArrayList<>();
					    for(ShipmentInventory t : inventoryLineItems) {
					       if(!StringUtils.isEmpty(t.getPartSuffix())) {
					          partSuffixs.add(t.getPartSuffix());
					       }
					    }

						ShipmentInventoryWorkingListModel requestModel = new ShipmentInventoryWorkingListModel();
						requestModel.setShipmentId(shipmentId);
						requestModel.setFlightId(BigInteger.valueOf(entry.getKey().intValue()));
						requestModel.setPartSuffix(partSuffixs);
						this.maintainShipmentLocationDAO.createExportWorkingListShipment(requestModel);

					}						
				}
			}
		}
	}

	/*
	 * Method to delete break down information for import/transshipment shipments
	 */
	private void deleteBreakDownInformation(ShipmentMaster paramAWB) throws CustomException {
		// If not empty then capture break down information
		if (!ImportExportIndicator.EXPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())
				&& !CollectionUtils.isEmpty(paramAWB.getShipmentInventories())) {

			// Check for records which has been marked for delete
			List<ShipmentInventory> deletedInventory = paramAWB.getShipmentInventories().stream()
					.filter(t -> "Y".equalsIgnoreCase(t.getFlagDelete())).collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(deletedInventory)) {
				Map<Integer, List<ShipmentInventory>> groupByFlightInventory = deletedInventory.stream()
						.collect(Collectors.groupingBy(ShipmentInventory::getFlightId));

				if (!CollectionUtils.isEmpty(groupByFlightInventory)) {
					for (Map.Entry<Integer, List<ShipmentInventory>> entry : groupByFlightInventory.entrySet()) {
						BigInteger shipmentId = BigInteger.valueOf(entry.getValue().get(0).getShipmentId());

						// Create/Update Shipment Verification if not exists
						ShipmentInventory shipmentVerificationModel = new ShipmentInventory();
						shipmentVerificationModel.setFlightId(entry.getKey().intValue());
						shipmentVerificationModel.setShipmentId(shipmentId.intValue());
						shipmentVerificationModel.setTerminal(paramAWB.getTerminal());
						shipmentVerificationModel.setShipmentNumber(paramAWB.getShipmentNumber());
						shipmentVerificationModel.setShipmentDate(paramAWB.getShipmentDate());

						boolean continueAmending = true;

						// Check if TRM has been issued for shipment OR not
						boolean isTRMIssued = this.maintainShipmentLocationDAO.isTRMIssued(shipmentVerificationModel);
						if (isTRMIssued) {
							continueAmending = false;
						}

						if (continueAmending && MultiTenantUtility.isTranshipment(paramAWB.getOrigin(), paramAWB.getDestination())) {
							// Check if flight is marked as completed for transshipment shipment
							FlightValidateModel incomingFlight = new FlightValidateModel();
							incomingFlight.setFlightKey(entry.getValue().get(0).getFlightKey());
							incomingFlight.setFlightDate(entry.getValue().get(0).getFlightKeyDate());
						}

						if (continueAmending) {
							List<ShipmentInventory> inventoryLineItems = entry.getValue();

							// Get the shipment verification id
							BigInteger shipmentVerificationId = this.maintainShipmentLocationDAO
									.getShipmentVerification(shipmentVerificationModel);
							shipmentVerificationModel.setParentReferenceId(shipmentVerificationId);

							// Set shipment verification id
							inventoryLineItems.forEach(t -> t.setParentReferenceId(shipmentVerificationId));

							// Delete Break Down Storage Info based on inventory id
							this.maintainShipmentLocationDAO.deleteShipmentVerificationStorageInfo(inventoryLineItems);

							// Delete Break Down ULD Trolley info when no storage info available
							this.maintainShipmentLocationDAO
									.deleteShipmentVerificationULDTrolleyInfo(shipmentVerificationModel);

							// update/Delete Shipment Verification Info
							this.maintainShipmentLocationDAO.deleteShipmentVerification(shipmentVerificationModel);
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.service.MaintainShipmentLocationService#
	 * raiseEventForInventoryOnImportShipments(com.ngen.cosys.shipment.model.
	 * ShipmentMaster)
	 */
	@Override
	public void raiseEventForInventoryOnImportShipments(ShipmentMaster paramAWB) throws CustomException {
		// Raise an event to send outbound messages for import shipments e.g. RCF/NFD
		if (!ImportExportIndicator.EXPORT.toString().equalsIgnoreCase(paramAWB.getShipmentTypeflag())
				&& !CollectionUtils.isEmpty(paramAWB.getShipmentInventories())) {

			// Filter flight and it's piece weight
			Map<Integer, List<ShipmentInventory>> groupByFlightInventory = paramAWB.getShipmentInventories().stream()
					.collect(Collectors.groupingBy(ShipmentInventory::getFlightId));

			if (!CollectionUtils.isEmpty(groupByFlightInventory)) {
				for (Map.Entry<Integer, List<ShipmentInventory>> entry : groupByFlightInventory.entrySet()) {
					BigInteger totalPieces = BigInteger.ZERO;
					BigDecimal totalWeight = BigDecimal.ZERO;
					BigInteger shipmentId = BigInteger.valueOf(entry.getValue().get(0).getShipmentId());
					String awbNo = (entry.getValue().get(0).getShipmentNumber());
					// Calculate the Pieces/Weight
					Set<String> partSuffixs = new HashSet<>();
					for (ShipmentInventory t : entry.getValue()) {
						totalPieces = totalPieces.add(BigInteger.valueOf(t.getPiecesInv()));
						totalWeight = totalWeight.add(t.getWeightInv());
						partSuffixs.add(t.getPartSuffix());
					}

					// Raise the event for RCF/NFD
					InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent payload = new InboundShipmentPiecesEqualsToBreakDownPiecesStoreEvent();
					payload.setShipmentId(shipmentId);
					payload.setFlightId(BigInteger.valueOf(entry.getKey().intValue()));
					payload.setPieces(totalPieces);
					payload.setWeight(totalWeight);
					payload.setStatus(EventStatus.NEW.getStatus());
					payload.setConfirmedAt(LocalDateTime.now());
					payload.setConfirmedBy(paramAWB.getLoggedInUser());
					payload.setCreatedBy(paramAWB.getLoggedInUser());
					payload.setCreatedOn(LocalDateTime.now());
					payload.setFunction("Maintain Shipment Location");
					payload.setEventName(EventTypes.Names.INBOUND_SHIPMENT_PIECES_EQUALS_TO_BREAK_DOWN_PIECES_EVENT);
					payload.setHawbNumber(paramAWB.getHwbNumber());
					// RCF will be triggered for 5 pieces from Inbound Breakdown or Maintain Location if
					// FDCA of 1 pieces is available ahead of location creation.

					ShipmentInventoryWorkingListModel isIrregulartiPieces = this.maintainShipmentLocationDAO
							.checkIrregularityForShipmentByFlight(payload);

					// Check the location pieces is greater than OR equal to manifest pieces
					Boolean isGreaterOREqual = this.maintainShipmentLocationDAO
							.checkLocationPiecesIsGreaterOREqualToManifest(payload);
					// if manifest exist then inventory pieces match with breakDown Pieces
					if (!ObjectUtils.isEmpty(isIrregulartiPieces)) {
						if (isGreaterOREqual
								|| (!ObjectUtils.isEmpty(isIrregulartiPieces.getIrregularityPiecesFound())
										&& !ObjectUtils.isEmpty(isIrregulartiPieces.getManifestPieces())
										&& (isIrregulartiPieces.getManifestPieces()
												.add(isIrregulartiPieces.getIrregularityPiecesFound())
												.compareTo(totalPieces) == 0))
								|| (!ObjectUtils.isEmpty(isIrregulartiPieces.getIrregularityPiecesMissing()))
										&& !ObjectUtils.isEmpty(isIrregulartiPieces.getManifestPieces())
										&& (isIrregulartiPieces.getManifestPieces()
												.subtract(isIrregulartiPieces.getIrregularityPiecesMissing())
												.compareTo(totalPieces) == 0)
								|| (!ObjectUtils.isEmpty(isIrregulartiPieces.getFreightOutPieces()))
										&& (isIrregulartiPieces.getFreightOutPieces().add(totalPieces)
												.compareTo(BigInteger.valueOf(paramAWB.getPieces())) == 0)) {
							producer.publish(payload);

							// Raise an event for data synch..
							//Changed to accomodate changes done by Nishant
							PartSuffix sipmentCheck = new PartSuffix();
							sipmentCheck.setShipmentNumber(paramAWB.getShipmentNumber());
							sipmentCheck.setShipmentDate(paramAWB.getShipmentDate());
							if (this.maintainShipmentLocationDAO.isDataSyncCREnabled() 
									&& !MultiTenantUtility.isTenantCityOrAirport(paramAWB.getDestination())
									&& this.commonBookingService.methodToCheckSQOrOalShipment(sipmentCheck)
									&& this.maintainShipmentLocationDAO.isFlighHandledInSystem(awbNo)) {
								for (String partSuffix : partSuffixs) {
									DataSyncStoreEvent eventPayload = new DataSyncStoreEvent();
									eventPayload.setShipmentId(shipmentId);
									eventPayload.setFlightId(BigInteger.valueOf(entry.getKey().intValue()));
									eventPayload.setStatus(EventStatus.NEW.getStatus());
									eventPayload.setConfirmedAt(LocalDateTime.now());
									eventPayload.setConfirmedBy("BATCH");
									eventPayload.setCreatedBy("BATCH");
									eventPayload.setCreatedOn(LocalDateTime.now());
									eventPayload.setFunction("Data Sync Job");
									eventPayload.setEventName(EventTypes.Names.DATA_SYNC_EVENT);
									eventPayload.setPartSuffix(partSuffix);
									dataEventProducer.publish(eventPayload);
								}
							}

						}
					}
				}
			}
			
			// derive Domestic && International
			DomesticInternationalHelperRequest domesticInternationalHelperRequest = new DomesticInternationalHelperRequest();
			domesticInternationalHelperRequest.setOrigin(paramAWB.getOrigin());
			domesticInternationalHelperRequest.setDestination(paramAWB.getDestination());
			String domesticOrInternational = domesticInternationalHelper
								.getDOMINTHandling(domesticInternationalHelperRequest);
			
			if (!CollectionUtils.isEmpty(groupByFlightInventory)) {
				List<String> locationsList=new ArrayList<String>();
				BigInteger shipmentId=null;
				for (Map.Entry<Integer, List<ShipmentInventory>> entry : groupByFlightInventory.entrySet()) {
					paramAWB.setFlightId(entry.getKey().intValue());
					Boolean inspectionCheck = this.maintainShipmentLocationDAO.getCustomsInspectionCheck(paramAWB);
					String inspectionLocationCheck = this.maintainShipmentLocationDAO.getCustomsInspectionLocationFromSystemParam();
					for (ShipmentInventory t : entry.getValue()) {
						shipmentId=BigInteger.valueOf(t.getShipmentId());
						locationsList.add(t.getWarehouseLocation());
					}
					
					if (domesticOrInternational.equalsIgnoreCase("INT") && inspectionCheck && locationsList.contains(inspectionLocationCheck)) {
						// call Moved to examination/inspection event
						MovedToExaminationEvent movedToExaminationEvent = new MovedToExaminationEvent();
						movedToExaminationEvent.setShipmentId(shipmentId);
						movedToExaminationEvent.setShipmentNumber(paramAWB.getShipmentNumber());
						movedToExaminationEvent.setFunction("Maintain Shipment Location");
						movedToExaminationEvent.setHouseNumber(paramAWB.getHwbNumber());
						movedToExaminationEvent.setFlightId(BigInteger.valueOf(entry.getKey().intValue()));
						movedToExaminationEvent.setEventName(EventTypes.Names.MOVED_TO_INSPECTION_MESSAGE_EVENT);

						movedToExaminationEventProducer.publish(movedToExaminationEvent);
					}
				}
			}
			
			
		}
	}

	/* (non-Javadoc)
	 * @see com.ngen.cosys.shipment.service.MaintainShipmentLocationService#getDeletedInventory(com.ngen.cosys.shipment.model.ShipmentMaster)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void getDeletedInventory(ShipmentMaster paramAWB) throws CustomException {
		for (ShipmentInventory value : paramAWB.getShipmentInventories()) {
			value.setShipmentNumber(paramAWB.getShipmentNumber());
			//Added as part of JV01-121
			if (Optional.ofNullable(paramAWB.getHouseInformation()).isPresent()
					&& Optional.ofNullable(paramAWB.getHouseInformation().getHouseId()).isPresent()) {
				value.setHouseId(paramAWB.getHouseInformation().getHouseId());
				value.setHawbNumber(paramAWB.getHwbNumber());

			}
			value.setShipmentDate(paramAWB.getShipmentDate());
			value.setShipmentType(paramAWB.getShipmentType());
			value.setOrigin(paramAWB.getOrigin());
			value.setDestination(paramAWB.getDestination());
			if (!StringUtils.isEmpty(value.getWarehouseLocation())) {
	            value.setWarehouseLocation(value.getWarehouseLocation().toUpperCase());
	            
	            // Check if it a valid location for the user
	          /*  if(!maintainShipmentLocationDAO.checkValidWarehouseForUser(value)) {
	                
	            	value.addError("Access denied to delete this location", "warehouseLocation", ErrorType.ERROR);
	            }
	            if (!value.getMessageList().isEmpty()) {
					throw new CustomException();
				}*/
	         }	
			
			if ("Y".equals(value.getFlagDelete())) {
				if(maintainShipmentLocationDAO.checkIfInventoryIsUpdated(value)) {
					value.addError("checkIfInvetoryUpdated", "maintainLocationForm", ErrorType.ERROR);
				}
				if(!value.getMessageList().isEmpty()) {
					throw new CustomException();
				}
				maintainShipmentLocationDAO.getDeletedInventorySHCs(value);
				maintainShipmentLocationDAO.getDeletedInventory(value);
			}
		}

		// Delete the break down information
		this.deleteBreakDownInformation(paramAWB);
	}

	/**
	 * Method which updates the ULD invetory for an given shipment location
	 * 
	 * @param locationInfo
	 * @throws CustomException
	 */
	private void updateULDInventory(List<ShipmentInventory> locationInfo) throws CustomException {

		// iterate each location and check if the shipment location can be updated to inventory or not
		if (!CollectionUtils.isEmpty(locationInfo)) {
			for (ShipmentInventory t : locationInfo) {
				// Split the shipment location
				if (!StringUtils.isEmpty(t.getShipmentLocation())) {
					MoveableLocationTypeModel requestModel = new MoveableLocationTypeModel();
					requestModel.setKey(t.getShipmentLocation());
					requestModel = moveableLocationTypesValidator.split(requestModel);

					// If the location is an dummy then do not capture this info in ULD Inventory
					if ("ULD".equalsIgnoreCase(requestModel.getLocationType())) {
						UldInfoModel uldInfoModel = new UldInfoModel();
						uldInfoModel.setCreatedBy(t.getCreatedBy());

						uldInfoModel.setHandlingCarrierCode(requestModel.getPart3());

						if (!StringUtils.isEmpty(t.getWarehouseLocation())) {
							uldInfoModel.setUldLocationCode(t.getWarehouseLocation());
						} else {
							uldInfoModel.setUldLocationCode(t.getTerminal());
						}

						uldInfoModel.setMovableLocationType(requestModel.getLocationType());

						uldInfoModel.setUldCreatedDate(LocalDateTime.now());
						uldInfoModel.setUldCarrierCode(requestModel.getPart3());
						uldInfoModel.setUldKey(requestModel.getKey());
						uldInfoModel.setUldNumber(requestModel.getPart2());
						uldInfoModel.setUldType(requestModel.getPart1());
						uldInfoModel.setFunctionName(UldMovementFunctionTypes.Names.SHIPMENTLOCATION);
						this.uldInfoService.updateUldInfo(uldInfoModel);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.ngen.cosys.shipment.service.MaintainShipmentLocationService#isLocationExists(com.ngen.cosys.shipment.model.ShipmentMaster)
	 */
	@Override
	public boolean isLocationExists(ShipmentMaster paramAWB) throws CustomException {
		return this.maintainShipmentLocationDAO.isLocationExists(paramAWB);
	}

	@Override
	public ShipmentInventory getInboundDetailsForPartsuffix(ShipmentInventory shipmentInventory )
			throws CustomException {
		return this.maintainShipmentLocationDAO.getInboundDetailsForPartsuffix(shipmentInventory);
		
	}

}