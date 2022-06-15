/**
 * This is a business validator component for validating shipment information
 * for break down
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.breakdown.validator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.common.validator.LoadingMoveableLocationValidator;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.HardcodedParam;
import com.ngen.cosys.impbd.shipment.breakdown.dao.InboundBreakdownDAO;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentHouseModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.utils.BigDecimalUtils;
import com.ngen.cosys.validator.dao.FlightValidationDao;
import com.ngen.cosys.validator.dao.MasterValidationDao;
import com.ngen.cosys.validator.enums.FlightType;
import com.ngen.cosys.validator.model.FlightValidateModel;
import com.ngen.cosys.validators.ArrivalManifestValidation;

@Service
public class InboundBreakDownValidator {

	private static final String BD_INVENTORYPIECEVALIDATION = "bd.inventorypiecevalidation";

	@Autowired
	private InboundBreakdownDAO inboundBreakdownDAO;

	@Autowired
	private BigDecimalUtils bigDecimalUtils;

	@Autowired
	MasterValidationDao mastervalidatorDao;

	@Autowired
	private ArrivalManifestValidation manifestValidation;

	@Autowired
	private FlightValidationDao flightValidationDao;
	
	@Autowired
	LoadingMoveableLocationValidator loadingmoveablelocationvalidator;

	/**
	 * Validate break down shipment information
	 * 
	 * @param inboundBreakdownModel
	 * @throws CustomException
	 */
	public void validate(InboundBreakdownModel inboundBreakdownModel) throws CustomException {

		// Check if break down completed
		FlightValidateModel flightValidationModel = new FlightValidateModel();
		flightValidationModel.setFlightKey(inboundBreakdownModel.getFlightNumber());
		flightValidationModel.setFlightDate(inboundBreakdownModel.getFlightDate());
		flightValidationModel.setFlightType(FlightType.IMPORT.getType());

		boolean isBreakdownCompleted = flightValidationDao.isBreakDownComplete(flightValidationModel);
		if (isBreakdownCompleted) {
			inboundBreakdownModel.addError(
					"error.breakdown.already.completed", "",
					ErrorType.ERROR);
			throw new CustomException();
		}

		// Check if flight completed
		boolean isFlightCompleted = flightValidationDao.isIncomingFlightCompleted(flightValidationModel);
		if (isFlightCompleted) {
			inboundBreakdownModel.addError("incoming.flight.completed", "", ErrorType.ERROR);
			throw new CustomException();
		}

		// Validate transfer type of an shipment
		InboundBreakdownShipmentModel shipmentData = inboundBreakdownDAO
				.checkTransferType(inboundBreakdownModel.getShipment());
		if (!ObjectUtils.isEmpty(shipmentData) && !StringUtils.isEmpty(shipmentData.getTransferType())
				&& (shipmentData.getTransferType().equalsIgnoreCase(HardcodedParam.TT.toString())
						|| shipmentData.getTransferType().equalsIgnoreCase(HardcodedParam.TTT.toString())
						|| shipmentData.getTransferType().equalsIgnoreCase(HardcodedParam.TTH.toString()))) {
			inboundBreakdownModel.addError("uld.tt", null, ErrorType.ERROR);
		}

		// Validate handling mode
		shipmentData = inboundBreakdownDAO.checkHandlingMode(inboundBreakdownModel.getShipment());

		if (!ObjectUtils.isEmpty(shipmentData)
				&& shipmentData.getHandlingMode().equalsIgnoreCase(HardcodedParam.NOBREAK.toString())) {
			inboundBreakdownModel.addError("uld.noBreak", null, ErrorType.ERROR);
		}

		// Check Shipment Location/Warehouse Location and Weight
		checkMandatoryInventory(inboundBreakdownModel.getShipment());
		
		//validate for ULD rampcheckin verified or not
		if(FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.ValidateForCheckedInULDs.class)){			
			validateCheckInUld(inboundBreakdownModel.getShipment().getInventory());
		}

		// Validate duplicate location
		validateDuplicationLocation(inboundBreakdownModel.getShipment().getInventory());

		// Validate inventory pieces
		checkInventoryPiece(inboundBreakdownModel);

		// Calculate Proportional Weight
		if (inboundBreakdownModel.getShipment().getBreakDownPieces()
				.compareTo(inboundBreakdownModel.getShipment().getManifestPieces()) == 0
				&& inboundBreakdownModel.getShipment().getBreakDownWeight()
						.compareTo(inboundBreakdownModel.getShipment().getManifestWeight()) == 0) {
			// calculateConsumedPieceWeight(inboundBreakdownModel.getShipment().getInventory(),
			// inboundBreakdownModel);
		}
	}

	private void validateCheckInUld(List<InboundBreakdownShipmentInventoryModel> inventoryList) throws CustomException {

		String Ulds = null;
		
		for (InboundBreakdownShipmentInventoryModel inventory : inventoryList) {

			if (!StringUtils.isEmpty(inventory.getUldNumber()) && !inventory.getUldNumber().equalsIgnoreCase("Bulk")) {
				// check ULD verified or not
				Boolean isVerified = inboundBreakdownDAO.isRampUldVerified(inventory);
				if(!isVerified) {
					if(StringUtils.isEmpty(Ulds)) {
						Ulds = inventory.getUldNumber();
					}else {						
						Ulds = Ulds +" , "+ inventory.getUldNumber();
					}
				}
			}
			
		}
		
		if(!StringUtils.isEmpty(Ulds)) {
			inventoryList.get(0).addError("imp.bd.ULD.Ramp.validation", "uldNumber", ErrorType.ERROR, new String[] {Ulds});
		}
		
		if (!CollectionUtils.isEmpty(inventoryList.get(0).getMessageList())) {
			throw new CustomException(inventoryList.get(0).getMessageList());
		}

	}

	private void checkMandatoryInventory(InboundBreakdownShipmentModel inboundBreakDownShipmentModel)
			throws CustomException {
		if (!CollectionUtils.isEmpty(inboundBreakDownShipmentModel.getInventory())) {

			// Iterate and check the required info
			for (InboundBreakdownShipmentInventoryModel inventory : inboundBreakDownShipmentModel.getInventory()) {
				
				// Validate Pieces
				if (inventory.getPieces() == null || inventory.getPieces().equals(BigDecimal.ZERO)) {
					inventory.addError("data.weight.required", "pieces", ErrorType.ERROR);
				}
				
				// Validate weight
				if (inventory.getWeight() == null || inventory.getWeight().equals(BigDecimal.ZERO)) {
					inventory.addError("data.weight.required", "weight", ErrorType.ERROR);
				}
				
				if(FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)){
					if(inventory.getChargeableWeight() == null || inventory.getChargeableWeight().equals(BigDecimal.ZERO)) {
						inventory.addError("data.chargeable.weight.required", "chargeableWeight", ErrorType.ERROR);
					}
				}
				

				// validate Shipment/Warehouse Location
				if (ObjectUtils.isEmpty(inventory.getShipmentLocation())
						&& ObjectUtils.isEmpty(inventory.getWarehouseLocation())) {
					inventory.addError("Shipment.locationcheck", "warehouseLocation", ErrorType.ERROR);
				}
				
				//Validate if the entered warehouse location is a restricted location
				 // Check if it a valid location for the user
				if(!ObjectUtils.isEmpty(inventory.getWarehouseLocation())) {
					inventory.setWarehouseLocation(inventory.getWarehouseLocation().toUpperCase());
		            
					inventory.setAccessLocation(inboundBreakdownDAO.checkValidWarehouseForUser(inventory));
					if(!inventory.getAccessLocation()&&(Action.CREATE.toString().equalsIgnoreCase(inventory.getFlagCRUD())||Action.UPDATE.toString().equals(inventory.getFlagCRUD()))) {
		                inventory.addError("error.not.authorized.use.location", "warehouseLocation", ErrorType.ERROR);
		            }
	            
				}
				



				if (!CollectionUtils.isEmpty(inventory.getMessageList())) {
					throw new CustomException(inventory.getMessageList());
				}
				List<String> billingShcGroup = new ArrayList<String>();
				List<String> billingShcGroupHanlingCodes = new ArrayList<String>();
				List<String> chargeableSHCGroup = inboundBreakdownDAO.getChegeableLocationSHC(inventory);
				if (!CollectionUtils.isEmpty(inventory.getShc())) {
					billingShcGroup = inboundBreakdownDAO.getInventoryBillingGroup(inventory);
					billingShcGroupHanlingCodes = inboundBreakdownDAO.getInventoryBillingGroupHandlingCodes(inventory);
				}


				if (MultiTenantUtility.isTenantCityOrAirport(inboundBreakDownShipmentModel.getDestination()) && 
						FeatureUtility.isFeatureEnabled(ApplicationFeatures.Awb.ShipmentAllowMULSHCS.class)) {
					if (!CollectionUtils.isEmpty(chargeableSHCGroup)) {
						if (CollectionUtils.isEmpty(inventory.getShc())) {
							inventory.addError("NO_SHC_DEFINED", "shcListInv", ErrorType.ERROR);
						} else {

							int numShcMatch = billingShcGroup.stream()
									.filter(shcObj -> chargeableSHCGroup.stream()
											.anyMatch(shcObj2 -> shcObj.equalsIgnoreCase(shcObj2)))
									.collect(Collectors.toList()).size();

							if (numShcMatch == 0) {
								inventory.addError("import.one.uld.match", "",ErrorType.ERROR,new String[]{inventory.getWarehouseLocation()});

							} else if (numShcMatch == 1 && billingShcGroup.size() > 1) {
								if (inventory.getIsGroupLocation()) {
									String[] placeholders = new String[2];
									placeholders[0] = inventory.getWarehouseLocation();
									placeholders[1] = inboundBreakDownShipmentModel.getShipmentNumber();
									placeholders[2] = billingShcGroupHanlingCodes.stream().collect(Collectors.joining(","));
									inventory.addError("import.conflicting.shc.locations","warehouseloc", ErrorType.ERROR,placeholders);
								} else {
									String[] placeholders = new String[2];
									placeholders[0] = inventory.getWarehouseLocation();
									placeholders[1] = billingShcGroupHanlingCodes.stream().collect(Collectors.joining(","));
									
									inventory.addError("import.conflicting.shc.currently.entered","warehouseloc", ErrorType.ERROR,placeholders);
								}
							}

						}

					} else {
						if (billingShcGroup.size() > 1) {
							if (inventory.getIsGroupLocation()) {
								inventory.addError("import.conflicting.shc.shipmentnumber","warehouseloc", ErrorType.ERROR,new String[] {inboundBreakDownShipmentModel.getShipmentNumber()});
							} else {
								String[] placeholders = new String[2];
								placeholders[0] = inventory.getWarehouseLocation();
								placeholders[1] = billingShcGroupHanlingCodes.stream().collect(Collectors.joining(","));
								inventory.addError("conflicting.shc.for.inventory","warehouseloc", ErrorType.ERROR,placeholders);
							}
						}
					}
				}
				if (!inventory.getMessageList().isEmpty()) {
					throw new CustomException();
				}

				// Validate SHC of each inventory line item
				if (!CollectionUtils.isEmpty(inventory.getShc())) {
					for (InboundBreakdownShipmentShcModel validShc : inventory.getShc()) {
						if (!StringUtils.isEmpty(validShc.getSpecialHandlingCode())) {
							Map<String, Object> parameterMap = new HashMap<>();
							parameterMap.put("code", validShc.getSpecialHandlingCode());
							parameterMap.put("shipmentNumber", inboundBreakDownShipmentModel.getShipmentNumber());
							boolean isValidShc = mastervalidatorDao.isValidSHC(parameterMap);
							if (!isValidShc) {
								validShc.addError("data.shc.not.valid", "specialHandlingCode", ErrorType.ERROR);
								throw new CustomException();
							}
						}
					}
				}
			}
		}
	}

	private void checkInventoryPiece(InboundBreakdownModel inboundBreakdownModel) throws CustomException {

		BigInteger shipmentPieces = inboundBreakdownModel.getShipment().getPiece();
		BigDecimal shipmentWeight = inboundBreakdownModel.getShipment().getWeight();

		// BigInteger manifestPieces = inboundBreakdownModel.getShipment().getManifestPieces();
		// BigDecimal manifestWeight = inboundBreakdownModel.getShipment().getManifestWeight();

		if (!ObjectUtils.isEmpty(shipmentPieces) && !ObjectUtils.isEmpty(shipmentWeight)) {
			
			//derive total pieces for the given flight
			Map<String, BigInteger> totalPieces = inboundBreakdownModel.getShipment().getInventory().stream()
					.filter(locationInfo -> !Action.DELETE.toString().equalsIgnoreCase(locationInfo.getFlagCRUD()))
					.collect(Collectors.groupingBy(InboundBreakdownShipmentInventoryModel::getShipmentNumber,
							Collectors.mapping(InboundBreakdownShipmentInventoryModel::getPieces,
									Collectors.reducing(BigInteger.ZERO, BigInteger::add))));

			//derive total weight for the given flight
			Map<String, BigDecimal> totalWeight = inboundBreakdownModel.getShipment().getInventory().stream()
					.filter(locationInfo -> !Action.DELETE.toString().equalsIgnoreCase(locationInfo.getFlagCRUD()))
					.collect(Collectors.groupingBy(InboundBreakdownShipmentInventoryModel::getShipmentNumber,
							Collectors.mapping(InboundBreakdownShipmentInventoryModel::getWeight,
									Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
            //get inventory  pieces and breakDown pieces for the other flight which is part case
			InboundBreakdownShipmentModel utilisedPieces = inboundBreakdownDAO
					.getFreightOutUtilisedPieces(inboundBreakdownModel.getShipment());

			/*
			 * if Sum of BD Piece is Less than to Document Piece but Weight is equal to the
			 * Awb Document Weight then throw Error
			 */
			if (!CollectionUtils.isEmpty(totalPieces) && shipmentPieces.compareTo(BigInteger.ZERO) != 0
					&& totalPieces.get(inboundBreakdownModel.getShipment().getShipmentNumber())
							.add(utilisedPieces.getTotalUtilisedPieces()).compareTo(shipmentPieces) == -1
					&& (totalWeight.get(inboundBreakdownModel.getShipment().getShipmentNumber())
							.add(utilisedPieces.getTotalUtilisedWeight())).compareTo(shipmentWeight) == 0) {
				inboundBreakdownModel.addError("import.breakdown.weight.pcs.not.match", null,ErrorType.NOTIFICATION);
				throw new CustomException();
			}

			/*
			 * if Sum of BD Piece is Greater or Equal to the AWB Document Pieces but Weight
			 * is Greater/less than Awb Document Weight then throw an error
			 */
			if (!CollectionUtils.isEmpty(totalPieces) && shipmentPieces.compareTo(BigInteger.ZERO) != 0
					&& totalPieces.get(inboundBreakdownModel.getShipment().getShipmentNumber())
							.add(utilisedPieces.getTotalUtilisedPieces()).compareTo(shipmentPieces) != -1
					&& (totalWeight.get(inboundBreakdownModel.getShipment().getShipmentNumber())
							.add(utilisedPieces.getTotalUtilisedWeight())).compareTo(shipmentWeight) != 0) {
				inboundBreakdownModel.addError("import.breakdown.weight.pcs.not.match", null,ErrorType.NOTIFICATION);
				throw new CustomException();
			}

			// if Sum of BD Piece is Greater than Awb Piece then throw an error
			if (!CollectionUtils.isEmpty(totalWeight) && shipmentWeight.compareTo(BigDecimal.ZERO) > 0 && totalWeight
					.get(inboundBreakdownModel.getShipment().getShipmentNumber()).compareTo(shipmentWeight) > 0) {
				inboundBreakdownModel.addError(BD_INVENTORYPIECEVALIDATION, null, ErrorType.NOTIFICATION);
				throw new CustomException();
			}

			// validate inventory and inventory house pieces
			for (InboundBreakdownShipmentInventoryModel inventoryData : inboundBreakdownModel.getShipment()
					.getInventory()) {
				BigInteger inventoryHousePiece = BigInteger.ZERO;
				if (!CollectionUtils.isEmpty(inventoryData.getHouse())) {
					for (InboundBreakdownShipmentHouseModel inventoryHouseData : inventoryData.getHouse()) {
						inventoryHousePiece = inventoryHousePiece.add(inventoryHouseData.getPieces());
					}
				}
				if (inventoryHousePiece.compareTo(inventoryData.getPieces()) > 0) {
					throw new CustomException("shipment.inventoryHousePiece", null, ErrorType.WARNING);
				}
			}

			/*
			 * Check inventory pieces/weight should not be greater than shipment master
			 * pieces/weight
			 */
			if (!CollectionUtils.isEmpty(inboundBreakdownModel.getShipment().getInventory())) {
				InboundBreakdownShipmentModel requestModel = new InboundBreakdownShipmentModel();
				requestModel.setShipmentNumber(inboundBreakdownModel.getShipment().getShipmentNumber());
				requestModel.setShipmentdate(inboundBreakdownModel.getShipment().getShipmentdate());
				requestModel.setFlightId(inboundBreakdownModel.getFlightId());

				// Set the break down piece/weight
				requestModel
						.setBreakDownPieces(totalPieces.get(inboundBreakdownModel.getShipment().getShipmentNumber()));
				requestModel
						.setBreakDownWeight(totalWeight.get(inboundBreakdownModel.getShipment().getShipmentNumber()));

				// Validate the piece/weight
				Boolean isGreater = this.inboundBreakdownDAO
						.isInventoryPieceWeightGreaterThanShipmentMasterPieceWeight(requestModel);
				if (!ObjectUtils.isEmpty(isGreater) && isGreater) {
					inboundBreakdownModel.addError(BD_INVENTORYPIECEVALIDATION, null, ErrorType.NOTIFICATION);
					throw new CustomException();
				}
			}
		}
	}

	private void validateDuplicationLocation(List<InboundBreakdownShipmentInventoryModel> inventory)
			throws CustomException {
		if (!CollectionUtils.isEmpty(inventory)) {

			// Filter out the entries based on ULD/Trolley Number and BULK
			Map<String, List<InboundBreakdownShipmentInventoryModel>> locationGroupByShipmentLocation = inventory
					.stream()
					.filter(locationInfo -> !Action.DELETE.toString().equalsIgnoreCase(locationInfo.getFlagCRUD()))
					.filter(locationInfo -> !StringUtils.isEmpty(locationInfo.getShipmentLocation()))
					.collect(Collectors.groupingBy(InboundBreakdownShipmentInventoryModel::getShipmentLocation));

			// Iterate through each locations of an ULD/Trolley and BULK
			for (Map.Entry<String, List<InboundBreakdownShipmentInventoryModel>> entry : locationGroupByShipmentLocation
					.entrySet()) {
				// Previous Warehouse Location of Shipment Location
				String preivousLocation = null;
				for (InboundBreakdownShipmentInventoryModel locationInfo : entry.getValue()) {
					// Derive Warehouse Location
					String warehouseLocation = StringUtils.isEmpty(locationInfo.getWarehouseLocation()) ? "**"
							: locationInfo.getWarehouseLocation();
					if (StringUtils.isEmpty(preivousLocation)) {
						preivousLocation = warehouseLocation;
					} else if (!preivousLocation.equalsIgnoreCase(warehouseLocation)) {
						locationInfo.addError("imp.warehouse.location.not.matching.for.shipment.location",
								"warehouseLocation", ErrorType.ERROR);
						throw new CustomException();
					}
				}
			}

			// Filter out the entries based on ULD/Trolley Number and BULK
			Map<String, List<InboundBreakdownShipmentInventoryModel>> locationGroupByBreakDownULD = inventory.stream()
					.filter(locationInfo -> !Action.DELETE.toString().equalsIgnoreCase(locationInfo.getFlagCRUD()))
					.collect(Collectors.groupingBy(InboundBreakdownShipmentInventoryModel::getUldNumber));

			// Iterate through each locations of an ULD/Trolley and BULK
			for (Map.Entry<String, List<InboundBreakdownShipmentInventoryModel>> entry : locationGroupByBreakDownULD
					.entrySet()) {
				// Iterate each location
				Set<String> uniqueLocationInfo = new HashSet<>();
				String houseId="**";
				for (InboundBreakdownShipmentInventoryModel locationInfo : entry.getValue()) {
					// Derive Shipment Location
					String shipmentLocation = StringUtils.isEmpty(locationInfo.getShipmentLocation()) ? "**"
							: locationInfo.getShipmentLocation();
					// Derive Warehouse Location
					String warehouseLocation = StringUtils.isEmpty(locationInfo.getWarehouseLocation()) ? "**"
							: locationInfo.getWarehouseLocation();
					//derive partSuffix
					String partSuffix = StringUtils.isEmpty(locationInfo.getPartSuffix()) ? "**"
							: locationInfo.getPartSuffix();
					
					
					//Specific to HAWB handling
					if(FeatureUtility.hasAnyFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)){
						if(!ObjectUtils.isEmpty(locationInfo.getShipmentHouseAWBId()) && locationInfo.getShipmentHouseAWBId().compareTo(BigInteger.ZERO) != 0) {
							houseId = locationInfo.getShipmentHouseAWBId().toString();
						}
					}

					// Add it to set
					if (uniqueLocationInfo.contains(shipmentLocation + "_" + warehouseLocation +"_" + partSuffix + "_" + houseId)) {
						// throw Duplicate Shipment Location Exception
						if (!StringUtils.isEmpty(shipmentLocation)) {
							locationInfo.addError("bd.shipmentLocationDuplicate", "shipmentLocation", ErrorType.ERROR);
						}

						if (!StringUtils.isEmpty(warehouseLocation)) {
							locationInfo.addError("bd.shipmentLocationDuplicate", "warehouseLocation", ErrorType.ERROR);
						}
						throw new CustomException();
					} else {
						uniqueLocationInfo.add(shipmentLocation + "_" + warehouseLocation + "_" + partSuffix + "_" + houseId);
					}
				}
			}
		}
	}

	private void calculateConsumedPieceWeight(List<InboundBreakdownShipmentInventoryModel> inventory,
			InboundBreakdownModel inboundBreakdownModel) {
		// Filter out all the Duplicate ULD Numbers
		Set<InboundBreakdownShipmentInventoryModel> duplicateULDNumbers = inventory.stream()
				.filter(locationInfo -> !Action.DELETE.toString().equalsIgnoreCase(locationInfo.getFlagCRUD()))
				.filter(manifestValidation.distinctByKey(InboundBreakdownShipmentInventoryModel::getUldNumber))
				.collect(Collectors.toSet());
		// calculate consumed pieces /weight only when there is duplicate of ULDs
		if (inboundBreakdownModel.getShipment().getManifestPieces().compareTo(BigInteger.ZERO) == 1) {
			if (duplicateULDNumbers.size() != inventory.size()) {
				BigInteger totalInventoryPieces = BigInteger.ZERO;
				BigDecimal totalInventoryWeight = BigDecimal.ZERO;
				for (InboundBreakdownShipmentInventoryModel uldData : duplicateULDNumbers) {

					for (InboundBreakdownShipmentInventoryModel inventoryData : inventory) {

						if ((uldData.getUldNumber().equalsIgnoreCase(inventoryData.getUldNumber()))) {

							// when the BD Pieces under the container or b exceeds the manifested piece
							// under that container or bulk
							if (inventoryData.getPieces().compareTo(inventoryData.getManifestPieces()) == 0
									|| inventoryData.getPieces().compareTo(inventoryData.getManifestPieces()) == -1) {
								inventoryData.setWeight(
										bigDecimalUtils.calculateProportionalWeight(inventoryData.getManifestPieces(),
												inventoryData.getManifestWeight(), null, inventoryData.getPieces()));
								if (inboundBreakdownModel.getShipment().getManifestPieces()
										.compareTo(totalInventoryPieces.add(inventoryData.getPieces())) == 0) {
									inventoryData.setWeight(inboundBreakdownModel.getShipment().getManifestWeight()
											.subtract(totalInventoryWeight));
								}
							}

							if (inventoryData.getPieces().compareTo(inventoryData.getManifestPieces()) == 1) {
								if (inboundBreakdownModel.getShipment().getManifestPieces()
										.compareTo(totalInventoryPieces.add(inventoryData.getPieces())) == 0) {
									inventoryData.setWeight(inboundBreakdownModel.getShipment().getManifestWeight()
											.subtract(totalInventoryWeight));
								} else {
									inventoryData.setWeight(bigDecimalUtils.calculateProportionalWeight(
											inboundBreakdownModel.getShipment().getManifestPieces()
													.subtract(totalInventoryPieces),
											inboundBreakdownModel.getShipment().getManifestWeight()
													.subtract(totalInventoryWeight),
											null, inventoryData.getPieces()));
								}
							}

							if (totalInventoryPieces.compareTo(BigInteger.ZERO) == 0
									&& inventoryData.getPieces().compareTo(inventoryData.getManifestPieces()) == 1) {
								inventoryData.setWeight(bigDecimalUtils.calculateProportionalWeight(
										inboundBreakdownModel.getShipment().getManifestPieces()
												.subtract(totalInventoryPieces),
										inboundBreakdownModel.getShipment().getManifestWeight()
												.subtract(totalInventoryWeight),
										null, inventoryData.getPieces()));
							}

							totalInventoryWeight = totalInventoryWeight.add(inventoryData.getWeight());
							totalInventoryPieces = totalInventoryPieces.add(inventoryData.getPieces());
						}
					}
				}
			}

			/// Only one line items are available for weight calculation
			if (inventory.size() == 1 && inventory.stream().findFirst().get().getPieces()
					.compareTo(inboundBreakdownModel.getShipment().getManifestPieces()) == 0) {
				inventory.stream().findFirst().get().setWeight(inboundBreakdownModel.getShipment().getManifestWeight());
			} else if (inventory.size() == 1 && inventory.stream().findFirst().get().getPieces()
					.compareTo(inventory.stream().findFirst().get().getManifestPieces()) == 1) {
				inventory.stream().findFirst().get()
						.setWeight(bigDecimalUtils.calculateProportionalWeight(
								inboundBreakdownModel.getShipment().getManifestPieces(),
								inboundBreakdownModel.getShipment().getManifestWeight(), null,
								inventory.stream().findFirst().get().getPieces()));
			} else if (inventory.size() == 1 && inventory.stream().findFirst().get().getPieces()
					.compareTo(inventory.stream().findFirst().get().getManifestPieces()) == -1) {
				inventory.stream().findFirst().get()
						.setWeight(bigDecimalUtils.calculateProportionalWeight(
								inventory.stream().findFirst().get().getPieces(),
								inventory.stream().findFirst().get().getWeight(), null,
								inventory.stream().findFirst().get().getPieces()));
			}

			/// All are Different containers case Weight Calculation
			if (duplicateULDNumbers.size() == inventory.size()) {
				BigInteger totalInventoryPieces = BigInteger.ZERO;
				BigDecimal totalInventoryWeight = BigDecimal.ZERO;
				for (InboundBreakdownShipmentInventoryModel inventoryData : inventory) {

					// when the BD Pieces under the container or b exceeds the manifested piece
					// under that container or bulk
					if (inventoryData.getPieces().compareTo(inventoryData.getManifestPieces()) == 0
							|| inventoryData.getPieces().compareTo(inventoryData.getManifestPieces()) == -1) {
						inventoryData.setWeight(
								bigDecimalUtils.calculateProportionalWeight(inventoryData.getManifestPieces(),
										inventoryData.getManifestWeight(), null, inventoryData.getPieces()));
						if (inboundBreakdownModel.getShipment().getManifestPieces()
								.compareTo(totalInventoryPieces.add(inventoryData.getPieces())) == 0) {
							inventoryData.setWeight(inboundBreakdownModel.getShipment().getManifestWeight()
									.subtract(totalInventoryWeight));
						}
					}

					if (inventoryData.getPieces().compareTo(inventoryData.getManifestPieces()) == 1) {
						if (inboundBreakdownModel.getShipment().getManifestPieces()
								.compareTo(totalInventoryPieces.add(inventoryData.getPieces())) == 0) {
							inventoryData.setWeight(inboundBreakdownModel.getShipment().getManifestWeight()
									.subtract(totalInventoryWeight));
						} else {
							inventoryData.setWeight(bigDecimalUtils.calculateProportionalWeight(
									inboundBreakdownModel.getShipment().getManifestPieces()
											.subtract(totalInventoryPieces),
									inboundBreakdownModel.getShipment().getManifestWeight()
											.subtract(totalInventoryWeight),
									null, inventoryData.getPieces()));
						}
					}

					if (totalInventoryPieces.compareTo(BigInteger.ZERO) == 0
							&& inventoryData.getPieces().compareTo(inventoryData.getManifestPieces()) == 1) {
						inventoryData.setWeight(bigDecimalUtils.calculateProportionalWeight(
								inboundBreakdownModel.getShipment().getManifestPieces().subtract(totalInventoryPieces),
								inboundBreakdownModel.getShipment().getManifestWeight().subtract(totalInventoryWeight),
								null, inventoryData.getPieces()));
					}

					totalInventoryWeight = totalInventoryWeight.add(inventoryData.getWeight());
					totalInventoryPieces = totalInventoryPieces.add(inventoryData.getPieces());
				}
			}
		}
	}

}