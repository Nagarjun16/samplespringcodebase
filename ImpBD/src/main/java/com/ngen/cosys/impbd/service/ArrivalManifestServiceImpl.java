package com.ngen.cosys.impbd.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.dimension.model.Dimension;
import com.ngen.cosys.dimension.utility.DimensionUtilties;
import com.ngen.cosys.events.payload.ManageBookingEvent;
import com.ngen.cosys.events.producer.ManageBookingStoreEventProducer;
import com.ngen.cosys.events.template.model.FlightModel;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.impbd.constants.ArrivalManifestErrorCodes;
import com.ngen.cosys.impbd.constants.HardcodedParam;
import com.ngen.cosys.impbd.dao.ArrivalManifestDao;
import com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO;
import com.ngen.cosys.impbd.dao.RampCheckInDAO;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestBySegmentModel;
import com.ngen.cosys.impbd.model.ArrivalManifestByShipmentShcModel;
import com.ngen.cosys.impbd.model.ArrivalManifestOtherServiceInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentDimensionInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentOciModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentOnwardMovementModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;
import com.ngen.cosys.impbd.model.CargPreAnnouncementShcModel;
import com.ngen.cosys.impbd.model.CargoPreAnnouncement;
import com.ngen.cosys.impbd.model.RampCheckInUld;
import com.ngen.cosys.impbd.model.SearchArrivalManifestModel;
import com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMasterShc;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.routing.DeriveRoutingHelper;
import com.ngen.cosys.routing.RoutingRequestModel;
import com.ngen.cosys.routing.RoutingResponseModel;
import com.ngen.cosys.service.util.model.FlightInfo;
import com.ngen.cosys.throughtransit.model.ThroughTransitShipmentAutoCreationModel;
import com.ngen.cosys.throughtransit.service.ThroughTransitAutoCreationService;
import com.ngen.cosys.transfertype.model.InboundFlightModel;
import com.ngen.cosys.transfertype.service.SetupTransferTypeService;
import com.ngen.cosys.transfertype.util.FindShipmentPairEqualToGivenNumberForTransfer;
import com.ngen.cosys.uldstatus.model.UldStatusRequestModel;
import com.ngen.cosys.uldstatus.model.UldStatusResponseModel;
import com.ngen.cosys.uldstatus.service.DeriveUldStatusService;
import com.ngen.cosys.utils.BigDecimalUtils;
import com.ngen.cosys.validator.utils.FlightHandlingSystemValidator;
import com.ngen.cosys.validators.ArrivalManifestValidation;

/**
 * This class takes care of the responsibilities related to the Arrival Manifest
 * service operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ArrivalManifestServiceImpl implements ArrivalManifestService {

   private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("ddMMMyyyy HH:mm");

   @Autowired
   private ArrivalManifestDao arrivalDao;

   @Autowired
   private ArrivalManifestValidation manifestValidation;

   @Autowired
   private BigDecimalUtils weightConversion;

   @Autowired
   private CargoPreAnnouncementDAO cargoPreAnnouncementDAO;

   @Autowired
   private ShipmentMasterDAO shipmentMasterDao;

   @Autowired
   private RampCheckInDAO rampDao;

   @Autowired
   private DimensionUtilties dimensionDataUtility;

   @Autowired
   private ManageBookingStoreEventProducer createBooking;

   @Autowired
   private ThroughTransitAutoCreationService autoCreateThroughTransitPair;

   @Autowired
   private DeriveRoutingHelper routeInfo;

   @Autowired
   private FindShipmentPairEqualToGivenNumberForTransfer findShipmentPairEqualToGivenNumberForTransfer;

   @Autowired
   private FlightHandlingSystemValidator handlingSystemValidator;

   @Autowired
   private SetupTransferTypeService deriveTransferTypes;

   // ULD Content Code
   private static final String COURIER = "Q";
   private static final String CARGO = "C";

   // ULD Handling Mode
   private static final String BREAK = "BREAK";

   // ULD Status
   private static final String IN = "IN";
   
   private static final String DOM = "DOM";
   private static final String H = "H";
   
   private static final String COU = "COU";
   private static final String AWB = "AWB";
   private static final String CBV = "CBV";
   
   
   @Autowired
   private DomesticInternationalHelper domesticInternationalHelper;
   
   @Autowired
   private HAWBHandlingHelper hawbHandlingHelper;
   
   @Autowired
   private DeriveUldStatusService deriveUldStatusService;

   /**
    * Find Shipment Details using Flight Number,Date.
    * 
    * @param flightKey,flightDate
    * @return List<ArrivalManifestModel>
    * @throws CustomException
    */

   @Override
   public ArrivalManifestByFlightModel fetchShipmentDetails(SearchArrivalManifestModel searchArrivalManifestModel)
         throws CustomException {
      ArrivalManifestByFlightModel arrivalManifestData = arrivalDao.fetchShipmentDetails(searchArrivalManifestModel);
      Optional<ArrivalManifestByFlightModel> oWeight = Optional.ofNullable(arrivalManifestData);
      if (!oWeight.isPresent()) {
         throw new CustomException("NORECORD", null, ErrorType.ERROR);
      }
      BigInteger tempFlightULD = BigInteger.ZERO;
      BigInteger tempFlightPiece = BigInteger.ZERO;
      BigDecimal tempFlightWeight = BigDecimal.ZERO;
      BigInteger tempFlightCargoInULD = BigInteger.ZERO;
      BigInteger tempFlightLooseCargo = BigInteger.ZERO;
      Set<ArrivalManifestShipmentInfoModel> shipmentRecords = new HashSet<>();
      for (ArrivalManifestBySegmentModel segmentData : arrivalManifestData.getSegments()) {
    	 String allotmentUldCount=null;
         BigInteger tempSegmentULDCount = BigInteger.ZERO;
         segmentData.setLooseCargoCount(BigInteger.valueOf(segmentData.getBulkShipments().size()));
         BigInteger tempSegmentPiece = BigInteger.ZERO;
         BigDecimal tempSegmentWeight = BigDecimal.ZERO;
         BigInteger tempCargoInULD = BigInteger.ZERO;
         if ("Loose".equalsIgnoreCase(searchArrivalManifestModel.getShipmentType())
               && !CollectionUtils.isEmpty(segmentData.getManifestedUlds())) {
            segmentData.getManifestedUlds().clear();
         } else if ("ULD".equalsIgnoreCase(searchArrivalManifestModel.getShipmentType())
               && !CollectionUtils.isEmpty(segmentData.getBulkShipments())) {
            segmentData.getBulkShipments().clear();
         }
         
         if (!CollectionUtils.isEmpty(segmentData.getSegmentUldGruopDetailsCountList())) {
			for (String arrivalManifestShipmentInfoModel : segmentData.getSegmentUldGruopDetailsCountList()) {
				if(StringUtils.isEmpty(allotmentUldCount)) {
					allotmentUldCount = arrivalManifestShipmentInfoModel;
				}else {
					allotmentUldCount=allotmentUldCount +" ,"+ arrivalManifestShipmentInfoModel;
				}
			}
         }	
         
         for (ArrivalManifestUldModel uldModelData : segmentData.getManifestedUlds()) {
            BigInteger tempPieceCount = BigInteger.ZERO;
            BigDecimal tempWeightValue = BigDecimal.ZERO;
            BigInteger uldAWBCount = BigInteger.ZERO;
            if (!uldModelData.getShipments().isEmpty()) {
               tempSegmentULDCount = tempSegmentULDCount.add(BigInteger.ONE);
            }

            for (ArrivalManifestShipmentInfoModel shpModel : uldModelData.getShipments()) {
               Set<ArrivalManifestShipmentInfoModel> duplicateShipments = shipmentRecords.stream()
                     .filter(shipment -> shipment.getShipmentNumber().equalsIgnoreCase(shpModel.getShipmentNumber()))
                     .collect(Collectors.toSet());
               tempPieceCount = tempPieceCount.add(shpModel.getPiece());
               tempWeightValue = tempWeightValue.add(shpModel.getWeight());
               if (CollectionUtils.isEmpty(duplicateShipments)) {
                  uldAWBCount = uldAWBCount.add(BigInteger.ONE);
                  tempCargoInULD = tempCargoInULD.add(BigInteger.ONE);
               }

               // Concatenate Onward Movement Information
               StringBuilder movementInfo = new StringBuilder();
               if (!StringUtils.isEmpty(shpModel.getDepartureAirportCityCode())) {
                  movementInfo.append(shpModel.getDepartureAirportCityCode());
                  movementInfo.append("/");
               }

               if (!StringUtils.isEmpty(shpModel.getDepartureCarrierCode())) {
                  movementInfo.append(shpModel.getDepartureCarrierCode());
               }

               if (!StringUtils.isEmpty(shpModel.getDepartureFlightNumber())) {
                  movementInfo.append(shpModel.getDepartureFlightNumber());
               }

               if (!ObjectUtils.isEmpty(shpModel.getDepartureFlightDate())) {
                  movementInfo.append("/");

                  movementInfo.append(FORMATTER.format(shpModel.getDepartureFlightDate()).toUpperCase());
                  movementInfo.append(" ");

                  movementInfo.append(
                        this.getDifferenceTime(arrivalManifestData.getSta(), shpModel.getDepartureFlightDate()));
               }

               if (!StringUtils.isEmpty(shpModel.getTransferType())) {
                  movementInfo.append("/");
                  movementInfo.append(shpModel.getTransferType());
               }
               shpModel.setOnwardMovement(movementInfo.toString());
            }

            shipmentRecords.addAll(uldModelData.getShipments());
            uldModelData.setPieceCount(tempPieceCount);
            uldModelData.setWeight(tempWeightValue);
            uldModelData.setShipmentCount(uldAWBCount);
            tempSegmentPiece = tempSegmentPiece.add(tempPieceCount);
            tempSegmentWeight = tempSegmentWeight.add(tempWeightValue);
         }

         for (ArrivalManifestShipmentInfoModel looseCargo : segmentData.getBulkShipments()) {
            tempSegmentPiece = tempSegmentPiece.add(looseCargo.getPiece());
            tempSegmentWeight = tempSegmentWeight.add(looseCargo.getWeight());

            // Concatenate Onward Movement Information
            StringBuilder movementInfo = new StringBuilder();
            if (!StringUtils.isEmpty(looseCargo.getDepartureAirportCityCode())) {
               movementInfo.append(looseCargo.getDepartureAirportCityCode());
               movementInfo.append("/");
            }

            if (!StringUtils.isEmpty(looseCargo.getDepartureCarrierCode())) {
               movementInfo.append(looseCargo.getDepartureCarrierCode());
            }

            if (!StringUtils.isEmpty(looseCargo.getDepartureFlightNumber())) {
               movementInfo.append(looseCargo.getDepartureFlightNumber());
            }

            if (!ObjectUtils.isEmpty(looseCargo.getDepartureFlightDate())) {
               movementInfo.append("/");

               movementInfo.append(FORMATTER.format(looseCargo.getDepartureFlightDate()).toUpperCase());
               movementInfo.append(" ");

               movementInfo
                     .append(this.getDifferenceTime(arrivalManifestData.getSta(), looseCargo.getDepartureFlightDate()));
            }

            if (!StringUtils.isEmpty(looseCargo.getTransferType())) {
               movementInfo.append("/");
               movementInfo.append(looseCargo.getTransferType());
            }
            looseCargo.setOnwardMovement(movementInfo.toString());

         }
         tempSegmentULDCount = arrivalDao.getSegmentLevelULDCount(segmentData);
         segmentData.setUldCount(tempSegmentULDCount);
         segmentData.setSegmentUldGruopDetailsCount(allotmentUldCount);
         segmentData.setSegmentPieceCount(tempSegmentPiece);
         segmentData.setSegmentWeight(tempSegmentWeight);
         segmentData.setCargoInULD(tempCargoInULD);
         tempFlightPiece = tempFlightPiece.add(segmentData.getSegmentPieceCount());
         tempFlightWeight = tempFlightWeight.add(segmentData.getSegmentWeight());
         tempFlightCargoInULD = tempFlightCargoInULD.add(segmentData.getCargoInULD());
         tempFlightLooseCargo = tempFlightLooseCargo.add(segmentData.getLooseCargoCount());
         tempFlightULD = tempFlightULD.add(segmentData.getUldCount());
      }

      arrivalManifestData.setPieceCount(tempFlightPiece);
      arrivalManifestData.setWeight(tempFlightWeight);
      arrivalManifestData.setCargoInULD(tempFlightCargoInULD);
      arrivalManifestData.setLooseCargo(tempFlightLooseCargo);
      arrivalManifestData.setUldCount(tempFlightULD);

      // DataSync validation
      FlightInfo flightInfo = new FlightInfo();
      flightInfo.setFlightKey(arrivalManifestData.getFlightNo());
      flightInfo.setFlightDate(arrivalManifestData.getFlightDate());
      flightInfo.setType("I");
      Boolean handlingSystem = handlingSystemValidator.isFlightHandlinginSystem(flightInfo);
      arrivalManifestData.setHandlinginSystem(handlingSystem);

      return arrivalManifestData;
   }

   @Override
   @Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
   public void createULDShipment(ArrivalManifestByFlightModel arrivalFlightDetails) throws CustomException {
	  //validate Shipment
      manifestValidation.validateShipmentsInfo(arrivalFlightDetails);
      
      if (!CollectionUtils.isEmpty(arrivalFlightDetails.getMessageList())) {
         throw new CustomException();
      }

      if (arrivalDao.isDocumentReceived(arrivalFlightDetails)) {
         arrivalFlightDetails.addError("Imp_ArrivalManifest", null, ErrorType.NOTIFICATION);
         throw new CustomException();
      }
      List<ArrivalManifestByFlightModel> flightExists = arrivalDao.checkArrivalManifestFlight(arrivalFlightDetails);
      if (!flightExists.isEmpty()) {
         for (ArrivalManifestBySegmentModel segmentData : arrivalFlightDetails.getSegments()) {
            segmentData.setImpArrivalManifestByFlightId(flightExists.get(0).getImpArrivalManifestByFlightId());
            createSegment(segmentData, arrivalFlightDetails.getFlightId());
         }
      } else {
         arrivalDao.createArrivalManifestFlight(arrivalFlightDetails);
         for (ArrivalManifestBySegmentModel segmentData : arrivalFlightDetails.getSegments()) {
            segmentData.setImpArrivalManifestByFlightId(arrivalFlightDetails.getImpArrivalManifestByFlightId());
            createSegment(segmentData, arrivalFlightDetails.getFlightId());
         }
      }
   }

   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   private void createSegment(ArrivalManifestBySegmentModel segmentData, BigInteger flightID) throws CustomException {
      if (segmentData.getNilCargo() && (!CollectionUtils.isEmpty(segmentData.getBulkShipments())
            || !CollectionUtils.isEmpty(segmentData.getManifestedUlds()) || segmentData.getBulkShipmentsCount() > 0
            || segmentData.getManifestUldCount() > 0)) {
         segmentData.addError("NilCargoValidation", null, ErrorType.NOTIFICATION);
         throw new CustomException();
      }

      List<ArrivalManifestBySegmentModel> segmentExists = arrivalDao.checkArrivalManifestSegment(segmentData);
      arrivalDao.updateNilCargo(segmentData);
      ArrivalManifestUldModel uldLooseData = new ArrivalManifestUldModel();
      if (!segmentExists.isEmpty()) {
         uldLooseData.setImpArrivalManifestBySegmentId(segmentExists.get(0).getImpArrivalManifestBySegmentId());
         for (ArrivalManifestUldModel uldModelData : segmentData.getManifestedUlds()) {
            uldModelData.setFlightId(flightID);
            uldModelData.setFlightSegmentId(segmentData.getSegmentId());
            for (ArrivalManifestShipmentInfoModel shpModel : uldModelData.getShipments()) {
               shpModel.setFlightId(flightID);
            }
            uldModelData.setImpArrivalManifestBySegmentId(segmentExists.get(0).getImpArrivalManifestBySegmentId());
            createUld(uldModelData);

         }
      } else {
         arrivalDao.createArrivalManifestSegment(segmentData);
         uldLooseData.setImpArrivalManifestBySegmentId(segmentData.getImpArrivalManifestBySegmentId());
         for (ArrivalManifestUldModel uldModelData : segmentData.getManifestedUlds()) {
            uldModelData.setImpArrivalManifestBySegmentId(segmentData.getImpArrivalManifestBySegmentId());
            uldModelData.setFlightId(flightID);
            for (ArrivalManifestShipmentInfoModel shpModel : uldModelData.getShipments()) {
               shpModel.setFlightId(flightID);
            }
            createUld(uldModelData);
         }
      }

      if (!CollectionUtils.isEmpty(segmentData.getBulkShipments())) {
         for (ArrivalManifestShipmentInfoModel shpModel : segmentData.getBulkShipments()) {
            shpModel.setFlightId(flightID);
         }
         uldLooseData.setShipments(segmentData.getBulkShipments().stream().collect(Collectors.toList()));
         manifestValidation.duplicateShipementUnderLooseCargo(uldLooseData);
         if (!CollectionUtils.isEmpty(uldLooseData.getMessageList())) {
            segmentData.addError(ArrivalManifestErrorCodes.DUPLICATELOOSECARGO.toString(), null,
                  ErrorType.NOTIFICATION);
            throw new CustomException();
         }
         createUld(uldLooseData);
         ArrivalManifestByFlightModel arrivalFlightData = new ArrivalManifestByFlightModel();
         arrivalFlightData.setFlightId(flightID);
         arrivalDao.updateBulkShipmentIndicator(arrivalFlightData);
      }

   }

   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   private void createUld(ArrivalManifestUldModel uldModelData) throws CustomException {
      List<ArrivalManifestUldModel> uldInfo = arrivalDao.checkArrivalManifestUld(uldModelData);

      if (uldModelData.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())
            && CollectionUtils.isEmpty(uldModelData.getShipments()) && Objects.nonNull(uldModelData.getUldNumber())) {
         uldModelData.addError("empty.uld", "uldNumber", ErrorType.ERROR);
         throw new CustomException();
      }

      if (!arrivalDao.checkAssignedULD(uldModelData)) {
         if (!uldInfo.isEmpty()) {
            for (ArrivalManifestUldModel uldData : uldInfo) {
               uldModelData.setImpArrivalManifestUldId(uldData.getImpArrivalManifestUldId());
               arrivalDao.updateULD(uldModelData);
            }
            createShipment(uldModelData);
         } else {
            arrivalDao.createULD(uldModelData);
            createShipment(uldModelData);
         }
      } else {
         uldModelData.addError("uld.import.assignment", "uldNumber", ErrorType.NOTIFICATION);
         throw new CustomException();
      }
   }

   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   private void createShipment(ArrivalManifestUldModel uldModelData) throws CustomException {
	   //validate Request data
      manifestValidation.validateRequestData(uldModelData);
      
      int deleteShipmentsCount = 0;
      for (ArrivalManifestShipmentInfoModel shpModel : uldModelData.getShipments()) {

         if (!shpModel.getFlagCRUD().equalsIgnoreCase("D") && !shpModel.getFlagCRUD().equalsIgnoreCase("R")) {
            manifestValidation.validateAwbNumber(shpModel);
         }
         if (!CollectionUtils.isEmpty(shpModel.getMessageList())) {
            throw new CustomException();
         }

         shpModel.setImpArrivalManifestUldId(uldModelData.getImpArrivalManifestUldId());
         if (shpModel.getWeightUnitCode().equalsIgnoreCase(HardcodedParam.POUND_CODE.toString())) {
            shpModel.setWeight(weightConversion.convertPoundsToKilos(shpModel.getWeight()));
            shpModel.setWeightUnitCode(HardcodedParam.WEIGHT_CODE.toString());
         }
         
         // For Audit Trail
         shpModel.setShcCode(shpModel.getShc().stream().map( n -> n.getSpecialHandlingCode() ).collect( Collectors.joining( " " ) ));

         if (Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
            arrivalDao.createShipment(shpModel);
         } else if (Action.UPDATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
            arrivalDao.updateShipment(shpModel);
         } else if (Action.DELETE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
            shpModel.setShipmentId(shpModel.getImpArrivalManifestShipmentInfoId());
            arrivalDao.deleteShipmentDimension(shpModel);
            arrivalDao.deleteShipmentOCI(shpModel);
            arrivalDao.deleteOSI(shpModel);
            arrivalDao.deleteShipmentOnward(shpModel);
            arrivalDao.deleteSHC(shpModel);
            arrivalDao.deleteShipment(shpModel);
            deleteShipmentsCount++;
         }
         if(!Action.DELETE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())  
        		 && !Action.READ.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
           createSHC(shpModel);
         }
         createAdditionalInfo(shpModel);
      }

      if (deleteShipmentsCount == uldModelData.getShipments().size()) {
         arrivalDao.deleteULD(uldModelData);
      }

   }

   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   private void createSHC(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      manifestValidation.checkForAtleastOneShc(shpModel);
      shpModel.setShipmentId(shpModel.getImpArrivalManifestShipmentInfoId());
      if (Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())
            || Action.UPDATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
         arrivalDao.deleteSHC(shpModel);
      }
      for (ArrivalManifestByShipmentShcModel shcModel : shpModel.getShc()) {
         if (!CollectionUtils.isEmpty(shcModel.getMessageList())) {
            throw new CustomException();
         }
         shcModel.setShipmentId(shpModel.getImpArrivalManifestShipmentInfoId());
         if (Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())
               || Action.UPDATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
            arrivalDao.createSHC(shcModel);
         }
      }
   }

   /**
    * Delete ULD/Shipment Details using Shipment Details and ULD Details.
    * 
    * @param ArrivalManifestByFlightModel
    * 
    * @return List<ArrivalManifestModel>
    * @throws CustomException
    */

   @Override
   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public void deleteULDShipment(ArrivalManifestUldModel arrivalFlightDetails) throws CustomException {
      if (checkManualShipment(arrivalFlightDetails)) {
         for (ArrivalManifestShipmentInfoModel shpModel : arrivalFlightDetails.getShipments()) {
            if (Action.DELETE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
               arrivalDao.deleteShipmentDimension(shpModel);
               arrivalDao.deleteShipmentOCI(shpModel);
               arrivalDao.deleteOSI(shpModel);
               arrivalDao.deleteShipmentOnward(shpModel);
               arrivalDao.deleteShipment(shpModel);
            }
         }
      }
   }

   private boolean checkManualShipment(ArrivalManifestUldModel arrivalFlightDetails) throws CustomException {
      CargoPreAnnouncement preannounceMentData = new CargoPreAnnouncement();
      preannounceMentData.setUldNumber(arrivalFlightDetails.getUldNumber());
      for (ArrivalManifestShipmentInfoModel uldData : arrivalFlightDetails.getShipments()) {
         preannounceMentData.setFlightId(uldData.getPiece());
         preannounceMentData.setUldOffPoint(uldData.getDestination());
      }
      return arrivalDao.checkManualShipment(preannounceMentData);
   }

   @Override
   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public void createAdditionalInfo(ArrivalManifestShipmentInfoModel arrivalFlightDetails) throws CustomException {
      if (!CollectionUtils.isEmpty(arrivalFlightDetails.getOsi())) {
         for (ArrivalManifestOtherServiceInfoModel osiData : arrivalFlightDetails.getOsi()) {
            if (StringUtils.isEmpty(osiData.getRemarks())) {
               arrivalFlightDetails.getOsi().remove(osiData);
               if (CollectionUtils.isEmpty(arrivalFlightDetails.getOsi())) {
                  break;
               }
            }
         }
      }

      if (!CollectionUtils.isEmpty(arrivalFlightDetails.getDimensions())
            || !CollectionUtils.isEmpty(arrivalFlightDetails.getOci())
            || !CollectionUtils.isEmpty(arrivalFlightDetails.getOsi())) {

         if (Objects.nonNull(arrivalFlightDetails.getDensityGroupCode())
               && Objects.nonNull(arrivalFlightDetails.getVolumeAmount())) {
            if (arrivalFlightDetails.getDensityGroupCode().compareTo(BigInteger.ZERO) == 0
                  && arrivalFlightDetails.getVolumeAmount().compareTo(BigDecimal.ZERO) == 0) {
               arrivalFlightDetails.addError(ArrivalManifestErrorCodes.DENSITYFIELDVALUEERROR.toString(), null,
                     ErrorType.NOTIFICATION);
               throw new CustomException();
            }
         }

      }

      if (ObjectUtils.isEmpty(arrivalFlightDetails.getVolumeAmount())
            && !ObjectUtils.isEmpty(arrivalFlightDetails.getDensityGroupCode())) {
         Dimension dimensionData = new Dimension();
         dimensionData.setDg(new BigDecimal(arrivalFlightDetails.getDensityGroupCode()));
         dimensionData.setShipmentWeight(arrivalFlightDetails.getWeight());
         Dimension voulume = dimensionDataUtility.getVolumeByDensity(dimensionData);
         arrivalFlightDetails.setVolumeAmount(voulume.getVolume());
      }

      arrivalDao.updateDensityInformation(arrivalFlightDetails);
      DimensionInformation(arrivalFlightDetails);
      ociInformation(arrivalFlightDetails);
      onwardInformation(arrivalFlightDetails);
      osiInformation(arrivalFlightDetails);

   }

   private void osiInformation(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      for (ArrivalManifestOtherServiceInfoModel osiModel : shpModel.getOsi()) {
         if (Action.CREATE.toString().equalsIgnoreCase(osiModel.getFlagCRUD())) {
            osiModel.setShipmentId(shpModel.getImpArrivalManifestShipmentInfoId());
            arrivalDao.createShipmentOSI(osiModel);
         } else if (Action.UPDATE.toString().equalsIgnoreCase(osiModel.getFlagCRUD())) {
            arrivalDao.updateShipmentOSI(osiModel);
         }
      }

   }

   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   private void onwardInformation(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
	   
	   
		if (!ObjectUtils.isEmpty(shpModel) && StringUtils.isEmpty(shpModel.getCarrierDestination())
					&& !MultiTenantUtility.isTenantCityOrAirport(shpModel.getDestination())) {
			
			RoutingRequestModel routingData = new RoutingRequestModel();
			routingData.setShipmentNumber(shpModel.getShipmentNumber());
			routingData.setShipmentDate(shpModel.getShipmentdate());
			routingData.setShipmentOrigin(shpModel.getOrigin());
			routingData.setShipmentDestination(shpModel.getDestination());
			routingData.setIncomingFlightId(shpModel.getFlightId());
			routingData.setFlightNumber(shpModel.getFlightKey());
			routingData.setFlightDate(shpModel.getFlightDate().atStartOfDay());
			routingData.setCarrier(shpModel.getCarrierCode());
			ArrivalManifestShipmentInfoModel onwordInfo = fetchRoutingInfoForReplaceFFM(routingData);
			if (!ObjectUtils.isEmpty(onwordInfo)) {
				shpModel.setCarrierDestination(onwordInfo.getCarrierDestination());
				if (!StringUtils.isEmpty(shpModel.getFlightKey())) {
					shpModel.setCarrierCode(shpModel.getFlightKey().substring(0, 2));
				}
			}
		}

      if (checkCarrierValidation(shpModel)) {
         List<ArrivalManifestShipmentOnwardMovementModel> onwardDetailsList = new ArrayList<>();
         if ((Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())
               || Action.UPDATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD()))) {
            // 1. Get the first route which is tenant
            ArrivalManifestShipmentOnwardMovementModel onwardModelData = new ArrivalManifestShipmentOnwardMovementModel();
            onwardModelData.setShipmentId(shpModel.getImpArrivalManifestShipmentInfoId());
            onwardModelData.setAirportCityCode(shpModel.getTenantAirport());
            onwardModelData.setCarrierCode(shpModel.getFlightKey().substring(0, 2));
            onwardDetailsList.add(onwardModelData);

         }

         // 2. Add the on word route to manifest
         ArrivalManifestShipmentOnwardMovementModel onwardModelData = new ArrivalManifestShipmentOnwardMovementModel();
         onwardModelData.setShipmentId(shpModel.getImpArrivalManifestShipmentInfoId());
         onwardModelData.setAirportCityCode(shpModel.getCarrierDestination());
         onwardModelData.setCarrierCode(shpModel.getCarrierCode());
         onwardDetailsList.add(onwardModelData);

         Boolean flag = arrivalDao.getShipmentOnwardMovement(onwardDetailsList.get(0));
         if (flag && Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())
               || Action.UPDATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
            arrivalDao.onwardDelete(onwardDetailsList.get(0));

         }
         for (ArrivalManifestShipmentOnwardMovementModel movementDetails : onwardDetailsList) {
            if (Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())
                  || Action.UPDATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
               arrivalDao.createShipmentOnwardMovement(movementDetails);
            }
         }
      }
   }

   private void ociInformation(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      for (ArrivalManifestShipmentOciModel ociModelData : shpModel.getOci()) {
         ociModelData.setShipmentId(shpModel.getImpArrivalManifestShipmentInfoId());
         if (Action.CREATE.toString().equalsIgnoreCase(ociModelData.getFlagCRUD())) {
            arrivalDao.createShipmentOCI(ociModelData);
         } else if (Action.UPDATE.toString().equalsIgnoreCase(ociModelData.getFlagCRUD())) {
            arrivalDao.updateShipmentOCI(ociModelData);
         } else if (Action.DELETE.toString().equalsIgnoreCase(ociModelData.getFlagCRUD())) {
            arrivalDao.ociDelete(ociModelData);
         }
      }
   }

   private void DimensionInformation(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      for (ArrivalManifestShipmentDimensionInfoModel dimensionData : shpModel.getDimensions()) {
         dimensionData.setShipmentId(shpModel.getImpArrivalManifestShipmentInfoId());
         if (Action.CREATE.toString().equalsIgnoreCase(dimensionData.getFlagCRUD())) {
            arrivalDao.createShipmentDimension(dimensionData);
         } else if (Action.UPDATE.toString().equalsIgnoreCase(dimensionData.getFlagCRUD())) {
            arrivalDao.updateShipmentDimension(dimensionData);
         } else if (Action.DELETE.toString().equalsIgnoreCase(dimensionData.getFlagCRUD())) {
            arrivalDao.dimensionDelete(dimensionData);
         }
      }

   }

   public boolean checkCarrierValidation(ArrivalManifestShipmentInfoModel shpModel) throws CustomException {
      if (StringUtils.isNotEmpty(shpModel.getCarrierDestination())
            || StringUtils.isNotEmpty(shpModel.getCarrierCode())) {
         return true;
      }
      return false;
   }

   @Override
   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public void createPreannounceMentData(ArrivalManifestByFlightModel arrivalFlightDetails) throws CustomException {
      List<CargoPreAnnouncement> cargoPreAnnouncementList = new ArrayList<>();
      arrivalDao.updateBulkIndicator(arrivalFlightDetails);
      for (ArrivalManifestBySegmentModel segmentData : arrivalFlightDetails.getSegments()) {
         for (ArrivalManifestUldModel uldData : segmentData.getManifestedUlds()) {
        	uldData.setFlightSegmentId(segmentData.getSegmentId());
            CargoPreAnnouncement preannounceMentData = new CargoPreAnnouncement();
            preannounceMentData.setIncomingFlightId(String.valueOf(arrivalFlightDetails.getFlightId()));
            preannounceMentData.setFlightId(arrivalFlightDetails.getFlightId());
            preannounceMentData.setUldBoardPoint(segmentData.getBoardingPoint());
            // setting uld info
            setUldDetails(uldData, preannounceMentData);
            CargoPreAnnouncement preannounceMentDataExists = arrivalDao.checkPreannounceMent(preannounceMentData);
            if (ObjectUtils.isEmpty(preannounceMentDataExists)) {
               preannounceMentData.setAnnouncementSourceType("AM");
               arrivalDao.createPreannounceMent(preannounceMentData);
               insertPreAnnounceMentSHC(preannounceMentData, uldData);
            } else {
               preannounceMentData.setCargoPreAnnouncementId(preannounceMentDataExists.getCargoPreAnnouncementId());
               arrivalDao.updatePreannounceMent(preannounceMentData);
               cargoPreAnnouncementDAO.deleteCargoPreAnnouncementShc(preannounceMentData);
               insertPreAnnounceMentSHC(preannounceMentData, uldData);
            }
            if (!arrivalDao.isRampcheckinExist(preannounceMentData)) {
               arrivalDao.insertRamCheckIn(preannounceMentData);
               this.insertRampCheckInSHC(preannounceMentData, uldData);
            }else {
            	RampCheckInUld checkInData = arrivalDao.fetchRampCheckInDetails(preannounceMentData);
            	preannounceMentData.setRampCheckinId(checkInData.getImpRampCheckInId());
            	rampDao.deleteSHCs(checkInData);
            	this.insertRampCheckInSHC(preannounceMentData, uldData);
            }
            List<ArrivalManifestUldModel> uldInfo = arrivalDao.checkArrivalManifestUld(uldData);
            if (uldInfo.isEmpty() && !ObjectUtils.isEmpty(preannounceMentDataExists)) {
               preannounceMentData.setCargoPreAnnouncementId(preannounceMentDataExists.getCargoPreAnnouncementId());
               cargoPreAnnouncementList.add(preannounceMentData);
               cargoPreAnnouncementDAO.deleteCargoPreAnnouncementShc(preannounceMentData);
               cargoPreAnnouncementDAO.deleteCargoPreAnnouncement(cargoPreAnnouncementList);
               RampCheckInUld checkInData = arrivalDao.fetchRampCheckInDetails(preannounceMentData);
               Optional<RampCheckInUld> rampDataExists = Optional.ofNullable(checkInData);
               if (rampDataExists.isPresent()) {
                  rampDao.deleteSHCs(checkInData);
                  List<RampCheckInUld> deleteULDList = new ArrayList<>();
                  deleteULDList.add(checkInData);
                  rampDao.delete(deleteULDList);
               }
            }
         }
      }

   }

   public void setUldDetails(ArrivalManifestUldModel uldData, CargoPreAnnouncement preannounceMentData)
         throws CustomException {

      preannounceMentData.setUldNumber(uldData.getUldNumber());
      preannounceMentData.setWarehouseHandlingInstructions(uldData.getUldRemarks());
      preannounceMentData.setHandlingAreaCode(uldData.getTerminal());
      preannounceMentData.setManualFlag(true);
      
      // Derive the ULD Status info

      UldStatusRequestModel uldStatusRequestModel = new UldStatusRequestModel();
      uldStatusRequestModel.setFlightId(preannounceMentData.getFlightId());
      uldStatusRequestModel.setFlightSegmentId(uldData.getFlightSegmentId());
      uldStatusRequestModel.setUldNumber(uldData.getUldNumber());
      UldStatusResponseModel uldStatusResponseModel = this.deriveUldStatusService.derive(uldStatusRequestModel);

      // set the response to model
      preannounceMentData.setBookingFlightId(uldStatusResponseModel.getBookingFlightId());
      preannounceMentData.setUldOffPoint(uldStatusResponseModel.getDestination());
      preannounceMentData.setHandlingMode(uldStatusResponseModel.getHandlingMode());
      preannounceMentData.setUldStatus(uldStatusResponseModel.getStatus());
      preannounceMentData.setTransferType(uldStatusResponseModel.getTransferType());


      // Get SHC Handling Group of an ULD
      String shcHandlingGroup = arrivalDao.fetchSHCGroupHandlingCode(uldData);
      preannounceMentData.setUldLoadedWith(shcHandlingGroup);

      // Derive content code
      if ("COU".equalsIgnoreCase(shcHandlingGroup)) {
         preannounceMentData.setContentCode(COURIER);
      } else {
         preannounceMentData.setContentCode(CARGO);
      }
      

      // Set default handling mode
      if (StringUtils.isEmpty(preannounceMentData.getHandlingMode())
            && CARGO.equalsIgnoreCase(preannounceMentData.getContentCode())) {
    	  preannounceMentData.setHandlingMode(BREAK);
      }

      // Set default uld status
      if (StringUtils.isEmpty(preannounceMentData.getUldStatus())) {
    	  preannounceMentData.setUldStatus(IN);
      }

      // Set default uld status
      if (StringUtils.isEmpty(preannounceMentData.getUldOffPoint())) {
    	  preannounceMentData.setUldOffPoint(uldData.getTenantAirport());
      }
      
   }

   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public void insertPreAnnounceMentSHC(CargoPreAnnouncement cargoPreAnnouncement, ArrivalManifestUldModel uldData)
			throws CustomException {

		CargPreAnnouncementShcModel shcModel = new CargPreAnnouncementShcModel();
		shcModel.setId(cargoPreAnnouncement.getCargoPreAnnouncementId());
		HashSet<String> newset = new HashSet<>();
		// ULD Shipment exiting SHC List;
		List<String> uldShipmentShcList = arrivalDao.getUldShipmentSHCList(uldData);
		int i = 0;
		if (!CollectionUtils.isEmpty(uldShipmentShcList)) {
			for (String shcData : uldShipmentShcList) {
				if (newset.add(shcData) && i <= 9) {
					shcModel.setPreSpecialHandlingCode(shcData);
					cargoPreAnnouncementDAO.insertCargoPreAnnouncementSHC(shcModel);
					i++;
				}
			}

		}

	}
   
   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public void insertRampCheckInSHC(CargoPreAnnouncement cargoPreAnnouncement, ArrivalManifestUldModel uldData)
			throws CustomException {

		HashSet<String> newset = new HashSet<>();
		// ULD Shipment exiting SHC List;
		List<String> uldShipmentShcList = arrivalDao.getUldShipmentSHCList(uldData);
		int i = 0;
		if (!CollectionUtils.isEmpty(uldShipmentShcList)) {
			for (String shcData : uldShipmentShcList) {
				CargPreAnnouncementShcModel shcModel = new CargPreAnnouncementShcModel();
				shcModel.setId(cargoPreAnnouncement.getRampCheckinId());
				if (newset.add(shcData) && i <= 9) {
					shcModel.setPreSpecialHandlingCode(shcData);
					cargoPreAnnouncementDAO.insertCargoPreAnnouncementRamCheckinSHC(shcModel);
					i++;
				}
			}

		}

	}

   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public void createShipmentMaster(ArrivalManifestShipmentInfoModel shpModel, ArrivalManifestByFlightModel flightData)
         throws CustomException {
      ShipmentMaster shipmentData = new ShipmentMaster();
      shipmentData.setShipmentType(AWB);
      
      
      shpModel.setFlightId(flightData.getFlightId());
      shipmentData.setShipmentNumber(shpModel.getShipmentNumber());
      shipmentData.setShipmentdate(shpModel.getShipmentdate());

      shipmentData.setWeightUnitCode(HardcodedParam.WEIGHT_CODE.toString());
      shipmentData.setOrigin(shpModel.getOrigin());
      shipmentData.setDestination(shpModel.getDestination());
      shipmentData.setSvc(shpModel.getSvc());
      shipmentData.setCarrierCode(flightData.getCarrierCode());
      
      if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Awb.CourierToCommercial.class)) {
    	  String shcs=" ";
    	  for (ArrivalManifestByShipmentShcModel shcData : shpModel.getShc()) {
				shcs = shcs +" "+ shcData.getSpecialHandlingCode();
		  }
    	  
    	  if(shcs.contains(COU)){
    		  shipmentData.setShipmentType(CBV);
    	  }
      }
      
      if (Action.CREATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())
				|| Action.UPDATE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {

    	  ShipmentMaster existingShipmentInfo = shipmentMasterDao.getShipmentInfo(shipmentData);
    	  Optional<ShipmentMaster> oShipmentMaster = Optional.ofNullable(existingShipmentInfo);
    	  
			// derive Domestic && International
			DomesticInternationalHelperRequest domesticInternationalHelperRequest = new DomesticInternationalHelperRequest();
			domesticInternationalHelperRequest.setOrigin(shipmentData.getOrigin());
			domesticInternationalHelperRequest.setDestination(shipmentData.getDestination());
			String domesticOrInternational = domesticInternationalHelper
					.getDOMINTHandling(domesticInternationalHelperRequest);

			shipmentData.setHandledByDOMINT(domesticOrInternational);

			// check FHL for AWB if exist set H else M
			HAWBHandlingHelperRequest hAWBHandlingHelperRequest = new HAWBHandlingHelperRequest();
			hAWBHandlingHelperRequest.setShipmentNumber(shipmentData.getShipmentNumber());
			hAWBHandlingHelperRequest.setShipmentDate(shipmentData.getShipmentdate());
			hAWBHandlingHelperRequest.setOrigin(shipmentData.getOrigin());
			hAWBHandlingHelperRequest.setDestination(shipmentData.getDestination());
			String handledByMasterHouse = hawbHandlingHelper.getHandledByMasterHouse(hAWBHandlingHelperRequest);
			// validate AWB document and shipmentInvenory and freightOut
			Boolean dataExist = arrivalDao.validatateAWBDocInvFreightOutInfo(shipmentData);
			
			// dataExist not update houseMaster info
			if (StringUtils.isEmpty(shpModel.getHandledByMasterHouse()) && !StringUtils.isEmpty(handledByMasterHouse)) {
				shipmentData.setHandledByMasterHouse(handledByMasterHouse);
			} else {
				
				if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.HAWBHandling.class)
						&& oShipmentMaster.isPresent() && (dataExist 
						&& !oShipmentMaster.get().getHandledByMasterHouse().equalsIgnoreCase(shpModel.getHandledByMasterHouse()))) {
					shpModel.addError("arrival.manifest.handlingtype.change", null,ErrorType.ERROR);
		            throw new CustomException();
				}
				
				if(!StringUtils.isEmpty(shpModel.getHandledByMasterHouse())) {					
					shipmentData.setHandledByMasterHouse(shpModel.getHandledByMasterHouse());
				}
				
			}

			shipmentData.setManuallyCreated(true);
			shipmentData.setNatureOfGoodsDescription(shpModel.getNatureOfGoodsDescription());
			shpModel.setFlightId(flightData.getFlightId());
			ArrivalManifestShipmentInfoModel ShipmentPieces = arrivalDao.fetchShipmentPieces(shpModel);
			if (Objects.nonNull(ShipmentPieces)) {
				shipmentData.setPiece(ShipmentPieces.getTotalPieces());
				shipmentData.setWeight(ShipmentPieces.getWeight());
			}

			// Bug 16105
			if (shpModel.getShipmentDescriptionCode().equalsIgnoreCase(HardcodedParam.TOTAL_SHIPMENT.toString())) {
				shipmentData.setPiece(shpModel.getTotalPieces());
				shipmentData.setWeight(shpModel.getWeight());
			}

			if (shpModel.getShipmentDescriptionCode().equalsIgnoreCase(HardcodedParam.SPLIT_SHIPMENT.toString())) {
				shipmentData.setPiece(shpModel.getTotalPieces());
				shipmentData.setWeight(ShipmentPieces.getWeight());
			}

			if (shpModel.getShipmentDescriptionCode().equalsIgnoreCase(HardcodedParam.DIVIDE_SHIPMENT.toString())
					|| shpModel.getShipmentDescriptionCode()
							.equalsIgnoreCase(HardcodedParam.PART_SHIPMENT.toString())) {
				if (Objects.nonNull(ShipmentPieces)) {
					shipmentData.setPiece(ShipmentPieces.getTotalPieces());
					shipmentData.setPartShipment(true);
					if (ShipmentPieces.getPiece().compareTo(ShipmentPieces.getTotalPieces()) == 0) {
						shipmentData.setWeight(ShipmentPieces.getWeight());
					} else {
						shipmentData.setWeight(BigDecimal.ZERO);
					}
				}
			}

			// Check for shipment existence
			if (oShipmentMaster.isPresent() && (oShipmentMaster.get().getShipmentId() != null)) {
				shipmentData.setShipmentId(oShipmentMaster.get().getShipmentId());
			}

			if (arrivalDao.isDocumentReceivedStatus(shipmentData) && oShipmentMaster.isPresent()
					&& (oShipmentMaster.get().getShipmentId() != null)) {
				shipmentMasterDao.createShipment(shipmentData);
			}

			if (ObjectUtils.isEmpty(shipmentData.getShipmentId())) {
				shipmentMasterDao.createShipment(shipmentData);
			}

			List<ShipmentMasterShc> shcs = new ArrayList<>();

			for (ArrivalManifestByShipmentShcModel shcData : shpModel.getShc()) {
				ShipmentMasterShc shipmentMasterShcs = new ShipmentMasterShc();
				shipmentMasterShcs.setShipmentId(shipmentData.getShipmentId());
				shipmentMasterShcs.setSpecialHandlingCode(shcData.getSpecialHandlingCode());
				shcs.add(shipmentMasterShcs);
			}

			shipmentMasterDao.createShipmentMasterShc(shcs);

			if (checkCarrierValidation(shpModel)) {
				List<ShipmentMasterRoutingInfo> onwardDetails = new ArrayList<>();
				// 1. Get the first route which is tenant
				if (!CollectionUtils.isEmpty(shpModel.getMovementInfo()) && MultiTenantUtility
						.isTenantCityOrAirport(shpModel.getMovementInfo().get(0).getAirportCityCode())) {
					ShipmentMasterRoutingInfo stationRoute = new ShipmentMasterRoutingInfo();
					stationRoute.setShipmentId(shipmentData.getShipmentId());
					stationRoute.setFromPoint(shpModel.getMovementInfo().get(0).getAirportCityCode());
					stationRoute.setCarrier(shpModel.getMovementInfo().get(0).getCarrierCode());
					onwardDetails.add(stationRoute);
				}

				// 2. Add the route
				ShipmentMasterRoutingInfo firstRoute = new ShipmentMasterRoutingInfo();
				firstRoute.setShipmentId(shipmentData.getShipmentId());
				firstRoute.setFromPoint(shpModel.getCarrierDestination());
				firstRoute.setCarrier(shpModel.getCarrierCode());
				onwardDetails.add(firstRoute);

				// 3. Add the destination
				if (!CollectionUtils.isEmpty(onwardDetails)) {
					boolean isShipmentDestinationFoundInRouting = false;
					for (ShipmentMasterRoutingInfo t : onwardDetails) {
						if (!StringUtils.isEmpty(t.getFromPoint())
								&& !StringUtils.isEmpty(shipmentData.getDestination())
								&& t.getFromPoint().equalsIgnoreCase(shipmentData.getDestination())) {
							isShipmentDestinationFoundInRouting = true;
						}
					}

					if (!isShipmentDestinationFoundInRouting) {
						ShipmentMasterRoutingInfo onwardRoute = new ShipmentMasterRoutingInfo();
						onwardRoute.setShipmentId(shipmentData.getShipmentId());
						onwardRoute.setFromPoint(shipmentData.getDestination());
						onwardRoute.setCarrier(shpModel.getCarrierCode());
						onwardDetails.add(onwardRoute);
					}
				}
				shipmentMasterDao.createShipmentMasterRoutingInfo(onwardDetails);
			}

		} else if (Action.DELETE.toString().equalsIgnoreCase(shpModel.getFlagCRUD())) {
         ShipmentMaster existingShipmentInfo = shipmentMasterDao.getShipment(shipmentData);
         Optional<ShipmentMaster> oShipmentMaster = Optional.ofNullable(existingShipmentInfo);
         // Check for shipment existence
         if (oShipmentMaster.isPresent() && (oShipmentMaster.get().getShipmentId() != null)) {
            shipmentData.setShipmentId(oShipmentMaster.get().getShipmentId());
            if (!arrivalDao.documentReceived(shipmentData)) {
               arrivalDao.deleteShipmentMasters(shipmentData);
            }
         }
      }
   }

   @Override
   public boolean checkValidFlight(ArrivalManifestByFlightModel flightData) throws CustomException {
      boolean isValid = arrivalDao.checkValidFlight(flightData);
      if (!isValid) {
         throw new CustomException("invalid.Arrival.Flight", null, ErrorType.ERROR);
      }
      return isValid;
   }

   private void createShipmentBooking(ArrivalManifestByFlightModel airlineFlightManifest,
         ArrivalManifestBySegmentModel segmentManifestData, ArrivalManifestShipmentInfoModel shipmentManifestData)
         throws CustomException {

      List<FlightModel> flightList = new ArrayList<FlightModel>();
      FlightModel flight = new FlightModel();
      flight.setFlightKey(airlineFlightManifest.getFlightNumber());
      flight.setFlightOriginDate(airlineFlightManifest.getFlightDate());
      flight.setFlightId(airlineFlightManifest.getFlightId());
      flight.setFlightBoardPoint(segmentManifestData.getBoardingPoint());
      flight.setFlightOffPoint(segmentManifestData.getOffPoint());
      flight.setFlightSegmentId(segmentManifestData.getSegmentId());
      flight.setBookingStatusCode("SS");
      flight.setVolumeUnitCode("MC");
      flight.setDensityGroupCode(shipmentManifestData.getDensityGroupCode());

      ManageBookingEvent bookingEvent = new ManageBookingEvent();
      bookingEvent.setShipmentNumber(shipmentManifestData.getShipmentNumber());
      bookingEvent.setShipmentDate(shipmentManifestData.getShipmentdate());
      bookingEvent.setOrigin(shipmentManifestData.getOrigin());
      bookingEvent.setDestination(shipmentManifestData.getDestination());
      bookingEvent.setNatureOfGoodsDescription(shipmentManifestData.getNatureOfGoodsDescription());
      bookingEvent.setDensityGroupCode(shipmentManifestData.getDensityGroupCode());
      bookingEvent.setBlockSpace(BigInteger.ZERO);
      bookingEvent.setPieces(shipmentManifestData.getPiece());
      bookingEvent.setGrossWeight(shipmentManifestData.getWeight());
      bookingEvent.setWeightUnitCode(shipmentManifestData.getWeightUnitCode());
      bookingEvent.setFlagCreate("Y");
      bookingEvent.setFlagUpdate("N");
      bookingEvent.setCreatedBy(airlineFlightManifest.getCreatedBy());
      flight.setBookingPieces(bookingEvent.getPieces());
      flight.setBookingWeight(bookingEvent.getGrossWeight());
      flightList.add(flight);
      bookingEvent.setFlightList(flightList);
      createBooking.publish(bookingEvent);
   }
   
	private void updateShipmentBooking(ArrivalManifestByFlightModel airlineFlightManifest,
			ArrivalManifestBySegmentModel segmentManifestData,ArrivalManifestShipmentInfoModel shipmentManifestData) {
		List<FlightModel> flightList = new ArrayList<FlightModel>();
		FlightModel flight = new FlightModel();
		flight.setFlightKey(airlineFlightManifest.getFlightNumber());
		flight.setFlightOriginDate(airlineFlightManifest.getFlightDate());
		flight.setFlightId(airlineFlightManifest.getFlightId());
		flight.setFlightBoardPoint(segmentManifestData.getBoardingPoint());
		flight.setFlightOffPoint(segmentManifestData.getOffPoint());
		flight.setFlightSegmentId(segmentManifestData.getSegmentId());
		flight.setBookingStatusCode("SS");
		flight.setVolumeUnitCode("MC");
		flight.setDensityGroupCode(shipmentManifestData.getDensityGroupCode());

		ManageBookingEvent bookingEvent = new ManageBookingEvent();
		bookingEvent.setShipmentNumber(shipmentManifestData.getShipmentNumber());
		bookingEvent.setShipmentDate(shipmentManifestData.getShipmentdate());
		bookingEvent.setOrigin(shipmentManifestData.getOrigin());
		bookingEvent.setDestination(shipmentManifestData.getDestination());
		bookingEvent.setNatureOfGoodsDescription(shipmentManifestData.getNatureOfGoodsDescription());
		bookingEvent.setDensityGroupCode(shipmentManifestData.getDensityGroupCode());
		bookingEvent.setBlockSpace(BigInteger.ZERO);
        if (shipmentManifestData.getPiece().compareTo(shipmentManifestData.getTotalPieces()) < 0) {
           bookingEvent.setPieces(shipmentManifestData.getTotalPieces());
           bookingEvent.setGrossWeight(shipmentManifestData.getTotalWeight());
        } else {
           bookingEvent.setPieces(shipmentManifestData.getPiece());
           bookingEvent.setGrossWeight(shipmentManifestData.getWeight());
        }

		bookingEvent.setWeightUnitCode(shipmentManifestData.getWeightUnitCode());
		bookingEvent.setFlagCreate("N");
		bookingEvent.setFlagUpdate("Y");
		bookingEvent.setCreatedBy(airlineFlightManifest.getCreatedBy());
		flight.setBookingPieces(bookingEvent.getPieces());
		flight.setBookingWeight(bookingEvent.getGrossWeight());
		flightList.add(flight);
		bookingEvent.setFlightList(flightList);
		createBooking.publish(bookingEvent);
	}

   @Override
   public ArrivalManifestShipmentInfoModel fetchRoutingDetail(ArrivalManifestByFlightModel flightData)
         throws CustomException {

      for (ArrivalManifestBySegmentModel segmentData : flightData.getSegments()) {
         if (!CollectionUtils.isEmpty(segmentData.getBulkShipments())) {
            for (ArrivalManifestShipmentInfoModel shipmentData : segmentData.getBulkShipments()) {
               if (Objects.nonNull(shipmentData)) {

                	 if (Objects.nonNull(shipmentData.getDestination())
                              && !MultiTenantUtility.isTenantCityOrAirport(shipmentData.getDestination())) {
                     RoutingRequestModel routingData = new RoutingRequestModel();
                     routingData.setShipmentNumber(shipmentData.getShipmentNumber());
                     routingData.setShipmentDate(shipmentData.getShipmentdate());
                     routingData.setShipmentOrigin(shipmentData.getOrigin());
                     routingData.setShipmentDestination(shipmentData.getDestination());
                     routingData.setFlightBoardPoint(segmentData.getBoardingPoint());
                     routingData.setFlightOffPoint(segmentData.getOffPoint());
                     routingData.setIncomingFlightId(flightData.getFlightId());
                     routingData.setFlightNumber(flightData.getFlightNo());
                     routingData.setFlightDate(flightData.getFlightDate().atStartOfDay());
                     routingData.setFlightType(flightData.getFlightType());
                     routingData.setCarrier(flightData.getCarrierCode());
                     return getRouteDetails(routingData);
                  } else {
                     return null;
                  }
               }

            }

         } else if (!CollectionUtils.isEmpty(segmentData.getManifestedUlds())) {
            for (ArrivalManifestUldModel uldData : segmentData.getManifestedUlds()) {
               for (ArrivalManifestShipmentInfoModel shipmentData : uldData.getShipments()) {
                  if (Objects.nonNull(shipmentData) && Objects.nonNull(shipmentData.getDestination())) {
                	  if (!MultiTenantUtility.isTenantAirport(shipmentData.getDestination())) {
                        RoutingRequestModel routingData = new RoutingRequestModel();
                        routingData.setShipmentNumber(shipmentData.getShipmentNumber());
                        routingData.setShipmentDate(shipmentData.getShipmentdate());
                        routingData.setShipmentOrigin(shipmentData.getOrigin());
                        routingData.setShipmentDestination(shipmentData.getDestination());
                        routingData.setFlightBoardPoint(segmentData.getBoardingPoint());
                        routingData.setFlightOffPoint(segmentData.getOffPoint());
                        routingData.setIncomingFlightId(flightData.getFlightId());
                        routingData.setFlightNumber(flightData.getFlightNo());
                        routingData.setFlightDate(flightData.getFlightDate().atStartOfDay());
                        routingData.setFlightType(flightData.getFlightType());
                        routingData.setCarrier(flightData.getCarrierCode());
                        return getRouteDetails(routingData);
                     } else {
                        return null;
                     }
                  }
               }
            }
         }
      }
      return null;
   }
   
   public ArrivalManifestShipmentInfoModel fetchRoutingInfoForReplaceFFM(RoutingRequestModel routingData) throws CustomException {
	   ArrivalManifestShipmentInfoModel onwordInfo = getRouteDetails(routingData);
	return onwordInfo;   
   }
   

   public ArrivalManifestShipmentInfoModel getRouteDetails(RoutingRequestModel routingData) throws CustomException {
	   if (!MultiTenantUtility.isTenantAirport(routingData.getShipmentDestination())) {
         List<RoutingResponseModel> routingDetails = routeInfo.getRoutes(routingData);
         ArrivalManifestShipmentInfoModel shipmentData = new ArrivalManifestShipmentInfoModel();
         shipmentData.setShipmentNumber(routingData.getShipmentNumber());
         shipmentData.setShipmentdate(routingData.getShipmentDate());
         shipmentData.setOrigin(routingData.getShipmentOrigin());
         shipmentData.setDestination(routingData.getShipmentDestination());
         if (Objects.nonNull(routingDetails)) {
            for (RoutingResponseModel routingInformation : routingDetails) {
            	   if (MultiTenantUtility.isTenantAirport(routingInformation.getNextDestination())) {
                  continue;
               }
               if (routingInformation.getNextDestination().equalsIgnoreCase(routingData.getShipmentDestination())) {
                  shipmentData.setCarrierDestination(routingInformation.getNextDestination());
                  shipmentData.setCarrierCode(routingInformation.getNextCarrier());
                  break;
               }
            }
            for (RoutingResponseModel routingInformation : routingDetails) {
               if (Objects.nonNull(shipmentData.getCarrierDestination())) {
                  if (!shipmentData.getCarrierDestination().equalsIgnoreCase(routingInformation.getNextDestination())) {
                     ArrivalManifestShipmentOnwardMovementModel onwardDetails = new ArrivalManifestShipmentOnwardMovementModel();
                     onwardDetails.setAirportCityCode(routingInformation.getNextDestination());
                     onwardDetails.setCarrierCode(routingInformation.getNextCarrier());
                     shipmentData.getMovementInfo().add(onwardDetails);
                  }
               }
            }
         }
         return shipmentData;

      }
      return null;

   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public void postProcessManifestInformation(ArrivalManifestByFlightModel airlineFlightManifest)
         throws CustomException {
      for (ArrivalManifestBySegmentModel segmentManifestData : airlineFlightManifest.getSegments()) {        

         // insert Bulk Cargo Information for Through Transit
         processBulkShipments(airlineFlightManifest, segmentManifestData.getBulkShipments());

         // insert ULD Cargo Information for Through Transit
         for (ArrivalManifestUldModel uldManifestData : segmentManifestData.getManifestedUlds()) {
            for (ArrivalManifestShipmentInfoModel shipmentManifestData : uldManifestData.getShipments()) {
               arrivalDao.updateServiceIndicator(shipmentManifestData);
               createShipmentMaster(shipmentManifestData, airlineFlightManifest);
            }
         }
         // booking creation
         bookingCreation(airlineFlightManifest, segmentManifestData);
      }
   }

   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   private void processBulkShipments(ArrivalManifestByFlightModel airlineFlightManifest,
         List<ArrivalManifestShipmentInfoModel> bulkConsignments) throws CustomException {
      if (!CollectionUtils.isEmpty(bulkConsignments)) {
         for (ArrivalManifestShipmentInfoModel shipmentManifestData : bulkConsignments) {
            createShipmentMaster(shipmentManifestData, airlineFlightManifest);
            arrivalDao.updateServiceIndicator(shipmentManifestData);
         }
      }
   }

   public void bookingCreation(ArrivalManifestByFlightModel airlineFlightManifest,
			ArrivalManifestBySegmentModel segmentManifestData) throws CustomException {

		List<ArrivalManifestShipmentInfoModel> notBookedShipments = arrivalDao
				.fetchNotBookedTranshipments(airlineFlightManifest);

		Set<ArrivalManifestShipmentInfoModel> notBookedShipmentsSet = notBookedShipments.stream()
				.filter(ArrivalManifestValidation.distinctByKey(ArrivalManifestShipmentInfoModel::getShipmentNumber))
				.collect(Collectors.toSet());

		for (ArrivalManifestShipmentInfoModel shpModel : notBookedShipmentsSet) {
		   if(shpModel.isBookingRecordFound()) {
		      updateShipmentBooking(airlineFlightManifest, segmentManifestData, shpModel);
		   } else {
		      createShipmentBooking(airlineFlightManifest, segmentManifestData, shpModel);
		   }
			
		}



	}

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.service.ArrivalManifestService#
    * createThroughTransitAdvice(com.ngen.cosys.impbd.model.
    * ArrivalManifestByFlightModel)
    */
   @Override
   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public void createThroughTransitAdvice(ArrivalManifestByFlightModel airlineFlightManifest) throws CustomException {

      // Create a distinct list of arrival manifest shipments
      List<ArrivalManifestShipmentInfoModel> distinctManifestedTransshipments = new ArrayList<>();
      Set<String> distinctShipmentNumbers = new HashSet<>();

      // Deleted a distinct list of arrival manifest shipments
      List<ArrivalManifestUldModel> deletedArrivalManifestULDs = new ArrayList<>();
      List<ArrivalManifestShipmentInfoModel> deletedArrivalManifestShipments = new ArrayList<>();

      if (!CollectionUtils.isEmpty(airlineFlightManifest.getSegments())) {
         for (ArrivalManifestBySegmentModel segment : airlineFlightManifest.getSegments()) {
            if (!CollectionUtils.isEmpty(segment.getManifestedUlds())) {
               for (ArrivalManifestUldModel uld : segment.getManifestedUlds()) {
                  if (!CollectionUtils.isEmpty(uld.getShipments())) {
                     for (ArrivalManifestShipmentInfoModel t : uld.getShipments()) {
                        if ((t.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())
                              || t.getFlagCRUD().equalsIgnoreCase(Action.UPDATE.toString()))
                        		&& !MultiTenantUtility.isTenantAirport(t.getDestination())) {
                           if (distinctShipmentNumbers.contains(t.getShipmentNumber())) {
                              // Do nothing
                           } else {
                              // Add it to list
                              distinctShipmentNumbers.add(t.getShipmentNumber());

                              // Add the shipment to list
                              distinctManifestedTransshipments.add(t);
                           }
                        } else if (t.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString())
                        	    && !MultiTenantUtility.isTenantAirport(t.getDestination())) {
                           // Add the shipment to list
                           t.setUldNumber(uld.getUldNumber());
                           deletedArrivalManifestShipments.add(t);

                        }
                     }
                  }
                  if (!CollectionUtils.isEmpty(deletedArrivalManifestShipments)) {
                     // Add the shipment to list
                     deletedArrivalManifestULDs.add(uld);

                  }
               }
            }

            if (!CollectionUtils.isEmpty(segment.getBulkShipments())) {
               for (ArrivalManifestShipmentInfoModel t : segment.getBulkShipments()) {
                  if ((t.getFlagCRUD().equalsIgnoreCase(Action.CREATE.toString())
                        || t.getFlagCRUD().equalsIgnoreCase(Action.UPDATE.toString()))
                		  && !MultiTenantUtility.isTenantAirport(t.getDestination())) {
                     if (distinctShipmentNumbers.contains(t.getShipmentNumber())) {
                        // Do nothing
                     } else {
                        // Add it to list
                        distinctShipmentNumbers.add(t.getShipmentNumber());

                        // Add the shipment to list
                        distinctManifestedTransshipments.add(t);
                     }
                  } else if (t.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString())
                	  && !MultiTenantUtility.isTenantAirport(t.getDestination())) {
                     // Add the shipment to list
                     deletedArrivalManifestShipments.add(t);

                  }
               }
            }
         }
      }

      if (!CollectionUtils.isEmpty(distinctShipmentNumbers)) {

         // Method to update booking information for each arrival manifest information
         this.updateBookingInformation(airlineFlightManifest, distinctManifestedTransshipments);

         // Get the list of through service shipments
         List<ArrivalManifestShipmentInfoModel> throughServiceShipments = this.arrivalDao
               .getThroughServiceShipmentInfo(airlineFlightManifest, distinctShipmentNumbers);

         // Call through service auto creation component
         if (!CollectionUtils.isEmpty(throughServiceShipments)) {
            for (ArrivalManifestShipmentInfoModel t : throughServiceShipments) {
               ThroughTransitShipmentAutoCreationModel requestModel = new ThroughTransitShipmentAutoCreationModel();
               requestModel.setShipmentDate(t.getShipmentdate());
               requestModel.setShipmentNumber(t.getShipmentNumber());
               requestModel.setOrigin(t.getOrigin());
               requestModel.setDestination(t.getDestination());
               requestModel.setOutboundFlightId(t.getBookingFlightId());
               requestModel.setOutboundFlightSegmentId(t.getBookingFlightSegmentId());
               requestModel.setInboundFlightId(t.getFlightId());
               requestModel.setInboundFlightSegmentId(t.getFlightSegmentId());
               requestModel.setInboundFlightBoardPoint(t.getIncomingFlightBoardPoint());
               requestModel.setCreatedBy(airlineFlightManifest.getCreatedBy());
               requestModel.setLoggedInUser(airlineFlightManifest.getCreatedBy());
               requestModel.setCreatedOn(airlineFlightManifest.getCreatedOn());
               requestModel.setModifiedBy(airlineFlightManifest.getCreatedBy());
               requestModel.setModifiedOn(airlineFlightManifest.getCreatedOn());
               requestModel.setTransferType(t.getTransferType());
               requestModel.setPieces(t.getPiece());
               requestModel.setWeight(t.getWeight());
               requestModel.setNatureOfGoodsDescription(t.getNatureOfGoodsDescription());
               requestModel.setBookingStatusCode("SS");
               this.autoCreateThroughTransitPair.autoCreateAdvice(requestModel);
            }
         }
      }
      if ((!CollectionUtils.isEmpty(deletedArrivalManifestULDs)
            || !CollectionUtils.isEmpty(deletedArrivalManifestShipments))) {
         // Delete the through service information for the ULD/ShipmentNumber
         this.arrivalDao.deleteThrougServiceInfo(airlineFlightManifest.getFlightId(), deletedArrivalManifestULDs,
               deletedArrivalManifestShipments);
      }
   }

   /**
    * Method to update the booking for manifested shipments
    * 
    * @param airlineFlightManifest
    *           - Incoming Flight Info
    * @throws CustomException
    */
   private void updateBookingInformation(ArrivalManifestByFlightModel airlineFlightManifest,
         List<ArrivalManifestShipmentInfoModel> distinctManifestedTransshipments) throws CustomException {

      // Iterate each shipment by it's group and process booking
      if (!CollectionUtils.isEmpty(distinctManifestedTransshipments)) {

         // Iterate the
         for (ArrivalManifestShipmentInfoModel consignment : distinctManifestedTransshipments) {

            InboundFlightModel inboundShipmentInformation = new InboundFlightModel();
            inboundShipmentInformation.setShipmentDate(consignment.getShipmentdate());
            inboundShipmentInformation.setShipmentNumber(consignment.getShipmentNumber());
            inboundShipmentInformation.setInboundFlight(airlineFlightManifest.getFlightNumber());
            inboundShipmentInformation.setInboundflightDate(airlineFlightManifest.getFlightDate());
            inboundShipmentInformation.setInboundflightId(airlineFlightManifest.getFlightId());
            
            // Get the transfer type/booking information
            List<InboundFlightModel> bookingInformation = deriveTransferTypes
                  .getAllTransferType(inboundShipmentInformation);

            // Get the aggregated non duplicate flights and Clear the arrival manifest
            // shipment info booking
            Map<BigInteger, List<InboundFlightModel>> groupByOutboundFlight = bookingInformation.stream()
                  .collect(Collectors.groupingBy(InboundFlightModel::getOutboundFlightId));
            if (!CollectionUtils.isEmpty(groupByOutboundFlight)) {
               for (Map.Entry<BigInteger, List<InboundFlightModel>> entry : groupByOutboundFlight.entrySet()) {
                  for (InboundFlightModel t : entry.getValue()) {
                     this.arrivalDao.clearBookingInfoFromArrivalManifestByFlight(t);
                  }
               }
            }

            if (!CollectionUtils.isEmpty(bookingInformation)) {
               for (InboundFlightModel t : bookingInformation) {

                  // Check existing booking information based on pieces and weight if it is less
                  // than/equal to booking pieces then update
                  List<InboundFlightModel> shipments = this.arrivalDao.getArrivalManifestShipments(t);

                  // Considerable shipment list
                  List<InboundFlightModel> updatableShipmentLineItem = new ArrayList<>();

                  // Derive the arrival manifest shipments info for updating booking flight
                  if (!CollectionUtils.isEmpty(shipments)) {
                     // If the shipment is an total then consider FFM pieces itself to match
                     Boolean isTotalShipment = shipments.get(0).getTotalShipment();

                     if (!ObjectUtils.isEmpty(isTotalShipment) && isTotalShipment) {
                        // Sum up the manifest pieces in case of SPLIT for same incoming flight
                        BigInteger manifestPieces = BigInteger.ZERO;
                        for (InboundFlightModel s : shipments) {
                           manifestPieces = manifestPieces.add(s.getManifestPieces());
                        }
                        // Get the manifest line items based on total manifest pieces
                        updatableShipmentLineItem = this.findShipmentPairEqualToGivenNumberForTransfer
                              .findByPiece(shipments, manifestPieces);
                     } else {

                        // Get the line items for piece weight
                        updatableShipmentLineItem = this.findShipmentPairEqualToGivenNumberForTransfer
                              .findByPieceWeight(shipments, t.getBookingPieces(), t.getBookingWeight());

                        if (CollectionUtils.isEmpty(updatableShipmentLineItem)) {
                           updatableShipmentLineItem = this.findShipmentPairEqualToGivenNumberForTransfer
                                 .findByPiece(shipments, t.getBookingPieces());
                        }
                     }
                  }

                  if (!CollectionUtils.isEmpty(updatableShipmentLineItem)) {
                     BigInteger manifestPieces = BigInteger.ZERO;
                     BigDecimal manifestWeight = BigDecimal.ZERO;

                     for (InboundFlightModel th : updatableShipmentLineItem) {
                        manifestPieces = manifestPieces.add(th.getManifestPieces());
                        manifestWeight = manifestWeight.add(th.getManifestWeight());

                        if (!ObjectUtils.isEmpty(t)) {
                           th.setTransferType(t.getTransferType());
                           th.setOutboundFlightId(t.getOutboundFlightId());
                           th.setOutboundSegmentId(t.getOutboundSegmentId());
                           this.arrivalDao.updateTransferTypeInArrivalManifestByShipmentInfoId(th);
                        }
                     }

                     // If it is an Through Transit shipment then update the booking
                     if (t.getForce()) {
                        t.setManifestPieces(manifestPieces);
                        t.setManifestWeight(manifestWeight);
                        this.arrivalDao.updatePartBookingPiecesWeight(t);
                     }

                  } else {
                     // If the line items are not updated then simply update based on
                     // inbound/outbound pair
                     if (!CollectionUtils.isEmpty(shipments)) {
                        for (InboundFlightModel th : shipments) {
                           th.setTransferType(t.getTransferType());
                           th.setOutboundFlightId(t.getOutboundFlightId());
                           th.setOutboundSegmentId(t.getOutboundSegmentId());

                           // Update transfer type for arrival manifest shipment info if exists
                           this.arrivalDao.updateTransferTypeInArrivalManifestByShipmentInfoId(th);
                        }
                     }
                  }
               }
            } else if (!ObjectUtils.isEmpty(inboundShipmentInformation)) {
               // Fetch the single transfer type
               inboundShipmentInformation = deriveTransferTypes.getTransferType(inboundShipmentInformation);

               if (!ObjectUtils.isEmpty(inboundShipmentInformation)) {
                  this.arrivalDao.updateTransferTypeInArrivalManifestByShipmentNumber(inboundShipmentInformation);
               }

            }

         }
      }
   }

   @Override
   public boolean isImportTransShipment(ArrivalManifestShipmentInfoModel reuseShipmentValidatorModel)
         throws CustomException {
      return arrivalDao.isImportTransShipment(reuseShipmentValidatorModel);
   }

   /**
    * Method to derive difference in time
    * 
    * @param incoming
    * @param departure
    * @return Difference between incoming and outgoing
    */
   private String getDifferenceTime(LocalDateTime incoming, LocalDateTime departure) {
      Duration duration = Duration.between(departure, incoming);
      long seconds = Math.abs(duration.getSeconds());
      long hours = seconds / 3600;
      seconds -= (hours * 3600);
      long minutes = seconds / 60;
      return hours + "Hr " + minutes + "min";
   }

}