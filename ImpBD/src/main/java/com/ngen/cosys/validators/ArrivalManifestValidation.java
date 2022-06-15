package com.ngen.cosys.validators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.impbd.constants.ArrivalManifestErrorCodes;
import com.ngen.cosys.impbd.constants.HardcodedParam;
import com.ngen.cosys.impbd.dao.ArrivalManifestValidationDao;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestBySegmentModel;
import com.ngen.cosys.impbd.model.ArrivalManifestByShipmentShcModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;
import com.ngen.cosys.impbd.service.ArrivalManifestValidationService;
import com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.validator.dao.MasterValidationDao;
import com.ngen.cosys.validator.dao.ShipmentValidationDao;

@Component
@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class ArrivalManifestValidation {

   @Autowired
   private ArrivalManifestValidationService manifestService;

   @Autowired
   private ShipmentValidationDao shipmentValidationDao;

   @Autowired
   MasterValidationDao mastervalidatorDao;
   
   @Autowired
   private ShipmentMasterDAO shipmentMasterDao;
   
   @Autowired
   ArrivalManifestValidationDao arrivalManifestValidationDao;
   
   @Autowired
   private DomesticInternationalHelper domesticInternationalHelper;
   
   private static final String DOM = "DOM";
   private static final String H = "H";

   Set<ArrivalManifestShipmentInfoModel> duplicateShipmentList = new HashSet<ArrivalManifestShipmentInfoModel>();
   
   public void validateRequestData(ArrivalManifestUldModel uldData) throws CustomException {

	      Set<ArrivalManifestShipmentInfoModel> duplicateShipmentnumbers = uldData.getShipments().stream()
	            .filter(distinctByKey(ArrivalManifestShipmentInfoModel::getShipmentNumber)).collect(Collectors.toSet());

	      List<ArrivalManifestShipmentInfoModel> shipmentData = uldData.getShipments().stream()
	            .filter(shipment -> !shipment.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString()))
	            .collect(Collectors.toList());

	      if (duplicateShipmentnumbers.size() < shipmentData.size()) {
	         uldData.addError(ArrivalManifestErrorCodes.DUPLICATESHIPMENTFORSAMEULD.toString(), "uldNumber",
	               ErrorType.ERROR);
	         throw new CustomException();
	      }

	      // this Iteration is for adding the Duplicate to the Set Which is already
	      // inserted in DB.
	      List<ArrivalManifestUldModel> uldNumberInfo = manifestService.fetchUldDetails(uldData);
	      for (ArrivalManifestShipmentInfoModel shpData : uldData.getShipments()) {

	         // this method Checks Whether Origin and Destination are Valid
	         checkShipmentoriginDestination(shpData);

	         if (Action.CREATE.toString().equalsIgnoreCase(shpData.getFlagCRUD())) {

	            // Check whether there is any duplicate Shipment being tried to add under same
	            // ULD
	        	shpData.setFlightSegmentId(uldData.getFlightSegmentId()); 
	            duplicateShipementUnderSameULD(uldNumberInfo, shpData);

	            // Consolidate Duplicate Shipments into Separate Set For Piece and Description
	            // Field Validation
	            List<ArrivalManifestShipmentInfoModel> shipmentExistsListData = manifestService.fetchShipments(shpData);
	            for (ArrivalManifestShipmentInfoModel shptempModel : shipmentExistsListData) {
	               if (shpData.getShipmentNumber().equalsIgnoreCase(shptempModel.getShipmentNumber())) {
	                  duplicateShipmentList.add(shptempModel);
	               }
	            }
	         }
	         this.validateShipmentTotalPieces(shpData);
	      }
	   }

   public void validateShipmentTotalPieces(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      if (Objects.isNull(shpModel.getTotalPieces())) {
         shpModel.addError("error.validate.total.pieces", "totalPieces", ErrorType.ERROR);
      }
      
      if (shpModel.getPiece().compareTo(BigInteger.ZERO) == 0) {
    	  shpModel.addError("error.pieces.mandatory", "piece", ErrorType.ERROR);
			throw new CustomException();
	   }

	  if (shpModel.getWeight().compareTo(BigDecimal.ZERO) == 0) {
			shpModel.addError("error.weight.mandatory", "weight", ErrorType.ERROR);
			throw new CustomException();
	  }
  
      if (shpModel.getPiece().compareTo(shpModel.getTotalPieces()) == 1 
    		  && !shpModel.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString())
    		  && !shpModel.getFlagCRUD().equalsIgnoreCase(Action.READ.toString())) {
         shpModel.addError(ArrivalManifestErrorCodes.PIECEFIELDVALUEVALIDATION.toString(), "piece", ErrorType.ERROR);
      }

      if ((HardcodedParam.PART_SHIPMENT.toString().equalsIgnoreCase(shpModel.getShipmentDescriptionCode()))
            || (HardcodedParam.DIVIDE_SHIPMENT.toString().equalsIgnoreCase(shpModel.getShipmentDescriptionCode()))
            || (HardcodedParam.SPLIT_SHIPMENT.toString().equalsIgnoreCase(shpModel.getShipmentDescriptionCode()))) {
         if (shpModel.getTotalPieces().equals(BigInteger.ZERO)) {
            shpModel.addError(ArrivalManifestErrorCodes.TOTALPIECEVALIDATION.toString(), "totalPieces",
                  ErrorType.ERROR);
         }
      }
      
      this.validateSegmentLevelDuplicateShipments(shpModel);
      
      this.validateCOUShipment(shpModel);
      
     
      if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class) 
			   && H.equalsIgnoreCase(shpModel.getHandledByMasterHouse()) && !shpModel.getTenantAirport().equalsIgnoreCase(shpModel.getDestination())) { 
			shpModel.addError("transhipment.handling.type.h.not.allowed", null,ErrorType.ERROR);
			throw new CustomException();
		}
   }

   private void validateCOUShipment(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
		// derive Domestic && International
		DomesticInternationalHelperRequest domesticInternationalHelperRequest = new DomesticInternationalHelperRequest();
		domesticInternationalHelperRequest.setOrigin(shpModel.getOrigin());
		domesticInternationalHelperRequest.setDestination(shpModel.getDestination());
		String domesticOrInternational = domesticInternationalHelper
				.getDOMINTHandling(domesticInternationalHelperRequest);
		// validate AWB document and shipmentInvenory and freightOut
	   if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class) 
			   &&!Action.DELETE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())
			   && DOM.equalsIgnoreCase(domesticOrInternational) 
			   && H.equalsIgnoreCase(shpModel.getHandledByMasterHouse())) {
			shpModel.addError("arrival.manifest.domistic.handler.validation", null,ErrorType.ERROR);
			throw new CustomException();
		}
	   
	   if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Awb.CourierToCommercial.class)) {
	    	  String shcs=" ";
	    	  for (ArrivalManifestByShipmentShcModel shcData : shpModel.getShc()) {
					shcs = shcs +" "+ shcData.getSpecialHandlingCode();
			  }
	    	  //COU Shipment Check
	    	  ShipmentMaster existingCOUShipmentInfo = shipmentMasterDao.checkCOUShipment(shpModel);
	    	  Optional<ShipmentMaster> couShipmentMaster = Optional.ofNullable(existingCOUShipmentInfo);
	    	  if (couShipmentMaster.isPresent() && (couShipmentMaster.get().getShipmentId() != null) 
	    			  && !shcs.contains("COU") && Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
	    		  shpModel.addError("arrival.manifest.cou.shipment.check", null,ErrorType.ERROR,new String[] {shpModel.getShipmentNumber()});
					throw new CustomException();
	    	  }
	    	  
	    	  //General Shipment Check
	    	  ShipmentMaster shipmentData = new ShipmentMaster();
	    	  shipmentData.setShipmentType("AWB");
	    	  
	    	  shipmentData.setShipmentNumber(shpModel.getShipmentNumber());
	    	  shipmentData.setShipmentdate(shpModel.getShipmentdate());
	    	  ShipmentMaster existingShipmentInfo = shipmentMasterDao.getShipment(shipmentData);
	    	  Optional<ShipmentMaster> shipmentMaster = Optional.ofNullable(existingShipmentInfo);
	    	  if(shipmentMaster.isPresent() && shipmentMaster.get().getShipmentId() != null 
	    			  && shcs.contains("COU") && Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
	    		  shpModel.addError("arrival.manifest.cou.shipment.check", null,ErrorType.ERROR,new String[] {shpModel.getShipmentNumber()});
					throw new CustomException();
	    	  }
	      }
	
   }

public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
      Set<Object> seen = ConcurrentHashMap.newKeySet();
      return t -> seen.add(keyExtractor.apply(t));
   }

   public void checkShipmentDescriptionCode(ArrivalManifestShipmentInfoModel shipmentData,
         List<ArrivalManifestShipmentInfoModel> duplicateList) throws CustomException {

      String tempShipmentNumber = shipmentData.getShipmentNumber();
      BigInteger tempTotalPieces = shipmentData.getTotalPieces();
      BigInteger totalPieceCount = BigInteger.ZERO;

      for (ArrivalManifestShipmentInfoModel shipmentDuplicate : duplicateList) {
         if (tempShipmentNumber.equalsIgnoreCase(shipmentDuplicate.getShipmentNumber())) {

            // Two Duplicate Shipment cannot Have Different Total Pieces
            if (!shipmentDuplicate.getTotalPieces().equals(tempTotalPieces)) {
               shipmentData.addError(ArrivalManifestErrorCodes.INVALIDTOTALVALUE.toString(), "totalPieces",
                     ErrorType.ERROR);
               throw new CustomException();
            }

            // If the Shipment Description is Empty for any of the Duplicate records assign
            // the Previous Description code to the data
            if (StringUtils.isEmpty(shipmentData.getShipmentDescriptionCode())) {
               shipmentData.addError(ArrivalManifestErrorCodes.SHIPMENTDESCRIPTIONVALIDATION.toString(),
                     "shipmentDescriptionCode", ErrorType.ERROR);
               throw new CustomException();
            }
           
            totalPieceCount = totalPieceCount.add(shipmentDuplicate.getPiece());
         }
      }

      if (!duplicateList.isEmpty()) {
         assignShipmentDescriptionCode(shipmentData, totalPieceCount, tempTotalPieces);
      }

      if (containsShipmentNumber(duplicateList, shipmentData.getShipmentNumber())
            && StringUtils.isEmpty(shipmentData.getShipmentDescriptionCode())) {
         assignShipmentDescriptionCode(shipmentData, totalPieceCount, tempTotalPieces);
      }

      if (duplicateList.isEmpty()) {
         BigInteger totalPieceCounts = BigInteger.ZERO;
         if (shipmentData.getPiece().equals(shipmentData.getTotalPieces())) {
            totalPieceCounts = shipmentData.getPiece();
         }
         assignShipmentDescriptionCode(shipmentData, totalPieceCounts, shipmentData.getTotalPieces());
      }
   }

   

   public static boolean containsShipmentNumber(List<ArrivalManifestShipmentInfoModel> list, String shipmentNumber) {
      for (ArrivalManifestShipmentInfoModel shipmentNo : list) {
         if (shipmentNo.getShipmentNumber().equalsIgnoreCase(shipmentNumber)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Method to check one SHC
    * 
    * @param shpModel
    */
   public void checkForAtleastOneShc(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      for (ArrivalManifestByShipmentShcModel t : shpModel.getShc()) {
         if (!StringUtils.isEmpty(t.getSpecialHandlingCode())) {
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("code", t.getSpecialHandlingCode());
            parameterMap.put("shipmentNumber", shpModel.getShipmentNumber());
            boolean isValidShc = mastervalidatorDao.isValidSHC(parameterMap);
            if (!isValidShc) {
               t.addError("data.shc.not.valid", "specialHandlingCode", ErrorType.ERROR);
            }
         }
      }
   }

   public void assignShipmentDescriptionCode(ArrivalManifestShipmentInfoModel shipmentData, BigInteger totalPieceCount,
         BigInteger tempTotalPieces) throws CustomException {

      // Sum of all duplicate Shipments should not be greater than Total Piece Value
      if (totalPieceCount.compareTo(tempTotalPieces) == 1) {
         shipmentData.addError(ArrivalManifestErrorCodes.SUMPIECEVALIDATION.toString(), "piece", ErrorType.ERROR);
         throw new CustomException();
      }

      // Change the Description Code based upon Piece Count
      if (totalPieceCount.compareTo(tempTotalPieces) == 0) {
         if (StringUtils.isEmpty(shipmentData.getShipmentDescriptionCode())) {
            shipmentData.setShipmentDescriptionCode(HardcodedParam.TOTAL_SHIPMENT.toString());
         }
      }

   }

   public void checkShipmentoriginDestination(ArrivalManifestShipmentInfoModel shpData) throws CustomException {
      if (MultiTenantUtility.isTenantCityOrAirport(shpData.getOrigin())) {
         shpData.addError(ArrivalManifestErrorCodes.ORIGINVALIDATION.toString(), "origin", ErrorType.ERROR);
      }

      if (shpData.getOrigin().equalsIgnoreCase(shpData.getDestination())) {
         shpData.addError(ArrivalManifestErrorCodes.ORIGINDESSAME.toString(), "destination", ErrorType.ERROR);
      }
   }

   // Check whether there is any duplicate Shipment being tried to add under same
   // ULD
   public void duplicateShipementUnderSameULD(List<ArrivalManifestUldModel> uldNumberInfo,
         ArrivalManifestShipmentInfoModel shpData) throws CustomException {
      for (ArrivalManifestUldModel checkDuplicateShipment : uldNumberInfo) {
         shpData.setImpArrivalManifestUldId(checkDuplicateShipment.getImpArrivalManifestUldId());
         List<ArrivalManifestShipmentInfoModel> shipmentExistsList = manifestService.fetchDuplicateShipments(shpData);
         for (ArrivalManifestShipmentInfoModel shpModel : shipmentExistsList) {
            if (shpData.getShipmentNumber().equalsIgnoreCase(shpModel.getShipmentNumber())) {
               shpData.addError(ArrivalManifestErrorCodes.DUPLICATESHIPMENTFORSAMEULD.toString(), "shipmentNumber",
                     ErrorType.ERROR);
               throw new CustomException();
            }
         }
      }
   }

   // Check whether there is any duplicate Shipment being tried to add under same
   // ULD
   public void duplicateShipementUnderLooseCargo(ArrivalManifestUldModel uldModelData) throws CustomException {
      Set<ArrivalManifestShipmentInfoModel> duplicateShipmentnumbers = uldModelData.getShipments().stream()
            .filter(distinctByKey(ArrivalManifestShipmentInfoModel::getShipmentNumber)).collect(Collectors.toSet());

      List<ArrivalManifestShipmentInfoModel> shipmentData = uldModelData.getShipments().stream()
            .filter(shipment -> !shipment.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString()))
            .collect(Collectors.toList());

      if (duplicateShipmentnumbers.size() < shipmentData.size()) {
    	  Set<ArrivalManifestShipmentInfoModel> losseShipmentnumbersCreate = duplicateShipmentnumbers.stream()
					.filter(s -> s.getFlagCRUD().equalsIgnoreCase("C")).collect(Collectors.toSet());
    	  String placeholders[] = new String[] {losseShipmentnumbersCreate.stream().findFirst().get().getShipmentNumber()};
         uldModelData.addError("error.no.duplicate.shipments.same.loose.shipment", null, ErrorType.NOTIFICATION,placeholders);
      }
   }

   // this method Checks Whether Split Shipments is Fully Received or Not and Part
   // Shipment is not duplicated
   public void validateShipmentsInfo(ArrivalManifestByFlightModel arrivalFlightDetails) throws CustomException {
      // Validate Flight Level Split Shipments For Total Shipment Pieces Received
      List<ArrivalManifestShipmentInfoModel> shipmentNumbersWithSplit = arrivalFlightDetails.getSegments().stream()
            .flatMap(segment -> segment.getManifestedUlds().stream())
            .flatMap(manifestedUlds -> manifestedUlds.getShipments().stream())
            .filter(shipment -> !Objects.isNull(shipment.getShipmentDescriptionCode())
                  && !Action.DELETE.toString().equalsIgnoreCase(shipment.getFlagCRUD()))
            .filter(shipment -> shipment.getShipmentDescriptionCode()
                  .equalsIgnoreCase(HardcodedParam.SPLIT_SHIPMENT.toString()))
            .collect(Collectors.toList());

      // Adding Loose Shipments also into that already Consolidated
      shipmentNumbersWithSplit
            .addAll(arrivalFlightDetails.getSegments().stream().flatMap(segment -> segment.getBulkShipments().stream())
                  .filter(shipment -> !Objects.isNull(shipment.getShipmentDescriptionCode())
                        && !Action.DELETE.toString().equalsIgnoreCase(shipment.getFlagCRUD()))
                  .filter(shipment -> shipment.getShipmentDescriptionCode()
                        .equalsIgnoreCase(HardcodedParam.SPLIT_SHIPMENT.toString()))
                  .collect(Collectors.toList()));

      // Validate Flight Level Part Shipments For Duplicates
      List<ArrivalManifestShipmentInfoModel> shipmentNumbersWithPart = arrivalFlightDetails.getSegments().stream()
            .flatMap(segment -> segment.getManifestedUlds().stream())
            .flatMap(manifestedUlds -> manifestedUlds.getShipments().stream())
            .filter(shipment -> !Objects.isNull(shipment.getShipmentDescriptionCode())
                  && !Action.DELETE.toString().equalsIgnoreCase(shipment.getFlagCRUD()))
            .filter(shipment -> shipment.getShipmentDescriptionCode()
                  .equalsIgnoreCase(HardcodedParam.PART_SHIPMENT.toString()))
            .collect(Collectors.toList());

      // Adding Loose Shipments also into that already Consolidated
      shipmentNumbersWithPart
            .addAll(arrivalFlightDetails.getSegments().stream().flatMap(segment -> segment.getBulkShipments().stream())
                  .filter(shipment -> !Objects.isNull(shipment.getShipmentDescriptionCode())
                        && !Action.DELETE.toString().equalsIgnoreCase(shipment.getFlagCRUD()))
                  .filter(shipment -> shipment.getShipmentDescriptionCode()
                        .equalsIgnoreCase(HardcodedParam.PART_SHIPMENT.toString()))
                  .collect(Collectors.toList()));
      
      // Validate Flight Level Part Shipments For Duplicates
      List<ArrivalManifestShipmentInfoModel> shipmentNumbersWithTotal = arrivalFlightDetails.getSegments().stream()
            .flatMap(segment -> segment.getManifestedUlds().stream())
            .flatMap(manifestedUlds -> manifestedUlds.getShipments().stream())
            .filter(shipment -> !Objects.isNull(shipment.getShipmentDescriptionCode())
                  && !Action.DELETE.toString().equalsIgnoreCase(shipment.getFlagCRUD()))
            .filter(shipment -> shipment.getShipmentDescriptionCode()
                  .equalsIgnoreCase(HardcodedParam.TOTAL_SHIPMENT.toString()))
            .collect(Collectors.toList());

      // Adding Loose Shipments also into that already Consolidated
      shipmentNumbersWithTotal
            .addAll(arrivalFlightDetails.getSegments().stream().flatMap(segment -> segment.getBulkShipments().stream())
                  .filter(shipment -> !Objects.isNull(shipment.getShipmentDescriptionCode())
                        && !Action.DELETE.toString().equalsIgnoreCase(shipment.getFlagCRUD()))
                  .filter(shipment -> shipment.getShipmentDescriptionCode()
                        .equalsIgnoreCase(HardcodedParam.TOTAL_SHIPMENT.toString()))
                  .collect(Collectors.toList()));

      Set<ArrivalManifestShipmentInfoModel> duplicateShipmentnumbersSplit = shipmentNumbersWithSplit.stream()
            .filter(distinctByKey(ArrivalManifestShipmentInfoModel::getShipmentNumber)).collect(Collectors.toSet());

      Set<ArrivalManifestShipmentInfoModel> duplicateShipmentnumbersPart = shipmentNumbersWithPart.stream()
            .filter(distinctByKey(ArrivalManifestShipmentInfoModel::getShipmentNumber)).collect(Collectors.toSet());
      
      Set<ArrivalManifestShipmentInfoModel> duplicateShipmentnumbersTotal = shipmentNumbersWithTotal.stream()
              .filter(distinctByKey(ArrivalManifestShipmentInfoModel::getShipmentNumber)).collect(Collectors.toSet());

      for (ArrivalManifestShipmentInfoModel tempData : shipmentNumbersWithSplit) {
         if (Objects.isNull(tempData.getTotalPieces())) {
            tempData.addError("error.validate.total.pieces", "totalPieces", ErrorType.ERROR);
            throw new CustomException();
         }

         for (ArrivalManifestShipmentInfoModel shpModel : duplicateShipmentnumbersSplit) {
            if (shpModel.getShipmentNumber().equalsIgnoreCase(tempData.getShipmentNumber())) {
               if (shpModel.getTotalPieces().compareTo(tempData.getTotalPieces()) == 1
                     || shpModel.getTotalPieces().compareTo(tempData.getTotalPieces()) == -1) {
                  tempData.addError(ArrivalManifestErrorCodes.INVALIDTOTALVALUE.toString(), "totalPieces",
                        ErrorType.ERROR);
                  throw new CustomException();
               }

               // Check For Same Shipments having Different Destination
               if (!shpModel.getDestination().equalsIgnoreCase(tempData.getDestination())) {
                  tempData.addError("arrival.destinationCheck", "destination", ErrorType.ERROR);
                  throw new CustomException();
               }

            }

         }
      }

		for (ArrivalManifestShipmentInfoModel tempData : shipmentNumbersWithPart) {
			if (Objects.isNull(tempData.getTotalPieces())) {
				tempData.addError("error.validate.total.pieces", "totalPieces", ErrorType.ERROR);
				throw new CustomException();
			}
		}
        //Bug 11714
		if (duplicateShipmentnumbersPart.size() < shipmentNumbersWithPart.size()) {
			List<ArrivalManifestShipmentInfoModel> partShipmentnumbersCreate = new ArrayList<ArrivalManifestShipmentInfoModel>();
			for (ArrivalManifestShipmentInfoModel duplicateShipmentnumbers : duplicateShipmentnumbersPart) {
				for (ArrivalManifestShipmentInfoModel arrivalManifestShipmentInfoModel : shipmentNumbersWithPart) {
					if (duplicateShipmentnumbers.getShipmentNumber().equalsIgnoreCase(arrivalManifestShipmentInfoModel.getShipmentNumber()) && 
							arrivalManifestShipmentInfoModel.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())) {
						partShipmentnumbersCreate.add(arrivalManifestShipmentInfoModel);
					}
				}
			}
			String shipmentNumbersCreate="";
			if (!CollectionUtils.isEmpty(partShipmentnumbersCreate)) {
				shipmentNumbersCreate = partShipmentnumbersCreate.stream().map(t -> t.getShipmentNumber()).collect(Collectors.joining("**"));
				arrivalFlightDetails.addError(
						"error.part.shipment.duplicate.update.ds",
						null, ErrorType.NOTIFICATION,new String[] {shipmentNumbersCreate});
			}
		}
		if (duplicateShipmentnumbersTotal.size() < shipmentNumbersWithTotal.size()) {
			List<ArrivalManifestShipmentInfoModel> totalShipmentnumbersCreate = new ArrayList<ArrivalManifestShipmentInfoModel>();
			for (ArrivalManifestShipmentInfoModel duplicateShipmentnumbers : duplicateShipmentnumbersTotal) {
				for (ArrivalManifestShipmentInfoModel arrivalManifestShipmentInfoModel : shipmentNumbersWithTotal) {
					if (duplicateShipmentnumbers.getShipmentNumber().equalsIgnoreCase(arrivalManifestShipmentInfoModel.getShipmentNumber()) && 
							arrivalManifestShipmentInfoModel.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())) {
						totalShipmentnumbersCreate.add(arrivalManifestShipmentInfoModel);
					}
				}
			}
			String shipmentNumbersDuplicate="";
			if (!CollectionUtils.isEmpty(totalShipmentnumbersCreate)) {
			  shipmentNumbersDuplicate = totalShipmentnumbersCreate.stream().map(t -> t.getShipmentNumber()).collect(Collectors.joining("**"));
			arrivalFlightDetails.addError(
					"error.total.shipment.already.existed",
					null, ErrorType.NOTIFICATION,new String[] {shipmentNumbersDuplicate});
			}
		}

      for (ArrivalManifestShipmentInfoModel shipmentWithSplit : duplicateShipmentnumbersSplit) {
         BigInteger tempTotalPieces = BigInteger.ZERO;
         for (ArrivalManifestShipmentInfoModel tempShipments : shipmentNumbersWithSplit) {
            if (tempShipments.getShipmentNumber().equalsIgnoreCase(shipmentWithSplit.getShipmentNumber())) {
               tempTotalPieces = tempTotalPieces.add(tempShipments.getPiece());
            }
         }

         
         if (tempTotalPieces.compareTo(shipmentWithSplit.getTotalPieces()) == 1 ||
        		 shipmentWithSplit.getPiece().compareTo(shipmentWithSplit.getTotalPieces()) == 0) {
        	 shipmentWithSplit.addError("error.check.split.shipment", "totalPieces",
                  ErrorType.NOTIFICATION);
         }

      }

      arrivalFlightDetails.getSegments().stream().flatMap(segment -> segment.getManifestedUlds().stream())
            .flatMap(manifestedUlds -> manifestedUlds.getShipments().stream())
            .filter(shipment -> Objects.isNull(shipment.getShipmentDescriptionCode())).forEach(shipment -> {
               if (shipment.getPiece().compareTo(shipment.getTotalPieces()) == 0) {
                  shipment.setShipmentDescriptionCode(HardcodedParam.TOTAL_SHIPMENT.toString());
               }
            });

      arrivalFlightDetails.getSegments().stream().flatMap(segment -> segment.getBulkShipments().stream())
            .filter(shipment -> Objects.isNull(shipment.getShipmentDescriptionCode())).forEach(shipment -> {
               if (shipment.getPiece().compareTo(shipment.getTotalPieces()) == 0) {
                  shipment.setShipmentDescriptionCode(HardcodedParam.TOTAL_SHIPMENT.toString());
               }
            });

      // Validate Flight Level Part Shipments For Duplicates
      List<ArrivalManifestShipmentInfoModel> shipmentNumbersWithDivide = arrivalFlightDetails.getSegments().stream()
            .flatMap(segment -> segment.getManifestedUlds().stream())
            .flatMap(manifestedUlds -> manifestedUlds.getShipments().stream())
            .filter(shipment -> !Objects.isNull(shipment.getShipmentDescriptionCode())
                  && !Action.DELETE.toString().equalsIgnoreCase(shipment.getFlagCRUD()))
            .filter(shipment -> shipment.getShipmentDescriptionCode()
                  .equalsIgnoreCase(HardcodedParam.DIVIDE_SHIPMENT.toString()))
            .collect(Collectors.toList());

      // Adding Loose Shipments also into that already Consolidated
      shipmentNumbersWithDivide
            .addAll(arrivalFlightDetails.getSegments().stream().flatMap(segment -> segment.getBulkShipments().stream())
                  .filter(shipment -> !Objects.isNull(shipment.getShipmentDescriptionCode())
                        && !Action.DELETE.toString().equalsIgnoreCase(shipment.getFlagCRUD()))
                  .filter(shipment -> shipment.getShipmentDescriptionCode()
                        .equalsIgnoreCase(HardcodedParam.DIVIDE_SHIPMENT.toString()))
                  .collect(Collectors.toList()));

      for (ArrivalManifestShipmentInfoModel shipmentDetails : shipmentNumbersWithPart) {
         if (shipmentDetails.getPiece().compareTo(shipmentDetails.getTotalPieces()) == 1
               || shipmentDetails.getPiece().compareTo(shipmentDetails.getTotalPieces()) == 0) {
        	 shipmentDetails.addError("error.part.pieces.lesser.than.total", "totalPieces", ErrorType.NOTIFICATION);
         }

         // Validate Divide Shipments with the Shipment Number already stored
         for (ArrivalManifestShipmentInfoModel tempShipment : duplicateShipmentnumbersPart) {
            if (shipmentDetails.getShipmentNumber().equalsIgnoreCase(tempShipment.getShipmentNumber())) {
               List<ArrivalManifestShipmentInfoModel> duplicatePartShipmentsStored = manifestService
                     .fetchShipments(tempShipment);
               if (!CollectionUtils.isEmpty(duplicatePartShipmentsStored)) {
                  checkShipmentDescriptionCode(shipmentDetails, duplicatePartShipmentsStored);
               }

            }
         }
      }

		// Segregating the Divide Shipments into set
		Set<ArrivalManifestShipmentInfoModel> duplicateShipmentnumbersDivide = shipmentNumbersWithDivide.stream()
				.filter(distinctByKey(ArrivalManifestShipmentInfoModel::getShipmentNumber)).collect(Collectors.toSet());

		for (ArrivalManifestShipmentInfoModel shipmentDetails : shipmentNumbersWithDivide) {
			if (Objects.isNull(shipmentDetails.getTotalPieces())) {
				shipmentDetails.addError("error.validate.total.pieces.data", "totalPieces", ErrorType.ERROR,
						new String[] { shipmentDetails.getShipmentNumber() });
				throw new CustomException();
			}
			if (shipmentDetails.getPiece().compareTo(shipmentDetails.getTotalPieces()) == 1
					|| shipmentDetails.getPiece().compareTo(shipmentDetails.getTotalPieces()) == 0) {
				arrivalFlightDetails.addError("error.divide.shipment.pieces.check.total", null, ErrorType.NOTIFICATION,
						new String[] { shipmentDetails.getShipmentNumber() });
				throw new CustomException();
			}

		}

		// Check against the database whether shipment exists for an ULD/Bulk
		List<ArrivalManifestBySegmentModel> segments = arrivalFlightDetails.getSegments();
		if (!CollectionUtils.isEmpty(segments)) {
			for (ArrivalManifestBySegmentModel s : segments) {
				// Loose shipments
				List<ArrivalManifestShipmentInfoModel> looseShipments = s.getBulkShipments();
				if (!CollectionUtils.isEmpty(looseShipments)) {
					for (ArrivalManifestShipmentInfoModel bulk : looseShipments) {
						if (Action.CREATE.toString().equalsIgnoreCase(bulk.getFlagCRUD())) {
							bulk.setFlightId(arrivalFlightDetails.getFlightId());
							bulk.setFlightSegmentId(s.getSegmentId());
							Boolean isShipmentExists = this.arrivalManifestValidationDao
									.isShipmentExistsInArrivalManifest(bulk);
							if (isShipmentExists) {
								arrivalFlightDetails.addError("duplicate.shipment", null,
										ErrorType.ERROR, new String[] { bulk.getShipmentNumber() });
							}
						}
					}
				}

				// ULD shipments
				List<ArrivalManifestUldModel> uld = s.getManifestedUlds();
				if (!CollectionUtils.isEmpty(uld)) {
					for (ArrivalManifestUldModel u : uld) {
						List<ArrivalManifestShipmentInfoModel> uldShipments = u.getShipments();
						if (!CollectionUtils.isEmpty(uldShipments)) {
							for (ArrivalManifestShipmentInfoModel uldshp : uldShipments) {
								if (Action.CREATE.toString().equalsIgnoreCase(uldshp.getFlagCRUD())) {
									uldshp.setFlightId(arrivalFlightDetails.getFlightId());
									uldshp.setFlightSegmentId(s.getSegmentId());
									Boolean isShipmentExists = this.arrivalManifestValidationDao
											.isShipmentExistsInArrivalManifest(uldshp);
									if (isShipmentExists) {
										arrivalFlightDetails.addError("duplicate.shipment", null,
												ErrorType.ERROR, new String[] { uldshp.getShipmentNumber() });
									}
								}
							}
						}
					}
				}
			}
		}

   }
    
   /**
    * Check Segment wise Duplicate Shipments
    * @param shpModel
    * @throws CustomException
    */
   public void validateSegmentLevelDuplicateShipments(ArrivalManifestShipmentInfoModel shpModel) throws CustomException{
	   
	   Boolean shipmentExistedInOtherSegment = arrivalManifestValidationDao.checkSegmentLevelDuplicateShipments(shpModel);
	   
	   if(Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD()) && shipmentExistedInOtherSegment) {		   
		   shpModel.addError(
				   "arrival.manifest.segment.level.duplicate.check",
				   null, ErrorType.NOTIFICATION,new String[] {shpModel.getShipmentNumber()});
		   throw new CustomException();
	   }
   }

   /**
    * Method to check valid AWB Prefix and Number
    * 
    * @param shipmentNumber
    * @param context
    * @return boolean
    * @throws CustomException
    */
   public void validateAwbNumber(ArrivalManifestShipmentInfoModel shipment) throws CustomException {
      // Variable to store 3 numbers
      String awbPrefix;
      // Variable to store 8 numbers
      String awbNumber;

      // Check if the Shipment Number is entered with "-" if yes then split the number
      Pattern shipmentNumberPattern = Pattern.compile("^[0-9]{11}");
      Matcher shipmentNumberMatcher = shipmentNumberPattern.matcher(shipment.getShipmentNumber());
      if (!shipmentNumberMatcher.matches()) {
         shipment.addError("data.awb.number.pattern.match", "shipmentNumber", ErrorType.ERROR);
      }

      // Extract the prefix
      awbPrefix = shipment.getShipmentNumber().substring(0, 3);

      // Check for valid awb prefix
      boolean isValidAwbPrefix = shipmentValidationDao.isValidAwbPrefix(awbPrefix);
      if (!isValidAwbPrefix) {
         shipment.addError("data.awb.number.invalid.prefix", "shipmentNumber", ErrorType.ERROR);
      }

      // Get AWB Mod check flag if PrefixExist
      if (isValidAwbPrefix) {
         boolean awbModCheckFlag = shipmentValidationDao.getAwbPreifxModCheckFlag(awbPrefix);
         if (awbModCheckFlag) {
            // Extract the number
            awbNumber = shipment.getShipmentNumber().substring(3);
            // Extract first 7 digit of AWB number
            String awbNumberWithNoCheckDigit = awbNumber.substring(0, awbNumber.length() - 1);
            // Concatenate first 7 digit and mod 7 check digit and compare with awbNumber
            boolean isAwbModCheckNumber = awbNumber
                  .equals(awbNumberWithNoCheckDigit + String.valueOf(Integer.valueOf(awbNumberWithNoCheckDigit) % 7));
            if (!isAwbModCheckNumber) {
               shipment.addError("data.awb.number.invalid", "shipmentNumber", ErrorType.ERROR);
            }
         }
      }
      //Check for BlackList AWB
      boolean isBlacklisted=shipmentValidationDao.getAwbBlacklistedCheckFlag(shipment.getShipmentNumber());
      if(isBlacklisted) {
    	  shipment.addError("EXPIMPEXT05", "shipmentNumber", ErrorType.ERROR);
      }
   }

}