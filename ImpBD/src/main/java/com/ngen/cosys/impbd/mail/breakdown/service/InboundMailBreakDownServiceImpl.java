package com.ngen.cosys.impbd.mail.breakdown.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

import com.ngen.cosys.airmail.model.MailContainerInfo;
import com.ngen.cosys.airmail.processor.MailbagProcessor;
import com.ngen.cosys.airmail.service.MailContainerInfoService;
import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidator;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.breakdown.dao.InboundMailBreakDownDAO;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownModel;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownShipmentModel;
import com.ngen.cosys.impbd.mail.breakdown.model.UldDestinationEntry;
import com.ngen.cosys.impbd.mail.breakdown.model.UldDestinationRequest;
import com.ngen.cosys.impbd.mail.validator.MailValidator;
import com.ngen.cosys.impbd.shipment.breakdown.dao.InboundBreakdownDAO;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentHouseModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentInventoryModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentModel;
import com.ngen.cosys.impbd.shipment.breakdown.model.InboundBreakdownShipmentShcModel;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService;
import com.ngen.cosys.impbd.shipment.inventory.service.ShipmentInventoryService;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;
import com.ngen.cosys.impbd.shipment.verification.service.ShipmentVerificationService;
import com.ngen.cosys.model.MailbagModel;
import com.ngen.cosys.uldinfo.UldMovementFunctionTypes;
import com.ngen.cosys.uldinfo.model.UldInfoModel;
import com.ngen.cosys.uldinfo.service.UldInfoService;
import com.ngen.cosys.util.MailbagUtil;
import com.ngen.cosys.validator.enums.ShipmentType;

@Service
public class InboundMailBreakDownServiceImpl implements InboundMailBreakDownService {

   private final Logger myLogger = LoggerFactory.getLogger(InboundMailBreakDownService.class);
   private static final String LOCATION = "Either shipment or warehouse Location is mandatory";
   private static final String INVALIDFLIGHT = "invalid.flight";
   private static final String NOG = "MAIL";

   @Autowired
   @Qualifier("inboundMailBreakDownValidator")
   private MailValidator validator;

   @Autowired
   private MailbagProcessor mailbagProcessor;

   @Autowired
   private InboundMailBreakDownDAO dao;

   @Autowired
   private InboundBreakdownDAO inboundBreakDownDao;

   @Autowired
   private ShipmentVerificationService shipmentVerificationService;

   @Autowired
   private ShipmentMasterService shipmentMasterService;

   @Autowired
   private ShipmentInventoryService shipmentInventoryService;

   @Autowired
   private ShipmentProcessorService shipmentProcessorService;

   @Autowired
   private UldInfoService uldService;
   @Autowired
   private MoveableLocationTypesValidator moveableLocationTypesValidator;

   @Autowired
   MailContainerInfoService mailContainerService;

   private String form = "mailBreakdownWorklistForm";

   @Override
   public InboundMailBreakDownModel search(InboundMailBreakDownModel requestModel) throws CustomException {
      if (null == requestModel.getFlightKey() || null == requestModel.getFlightDate()) {
         throw new CustomException("CUST001", form, ErrorType.ERROR);
      }
      InboundMailBreakDownModel flightData = dao.flightDetail(requestModel);
      if (StringUtils.isEmpty(flightData)) {
         throw new CustomException(INVALIDFLIGHT, form, ErrorType.ERROR);
      }
      requestModel.setFlightId(flightData.getFlightId());
      InboundMailBreakDownModel data = dao.search(requestModel);
      if (StringUtils.isEmpty(data)) {
         throw new CustomException("MATMASTR02", form, ErrorType.ERROR);
      } else {
         data.setFlightKey(flightData.getFlightKey());
         data.setFlightDate(flightData.getFlightDate());
      }
      data.setCarrierCode(flightData.getCarrierCode());
      /*
       * if (!CollectionUtils.isEmpty(data.getShipments())) { for
       * (InboundMailBreakDownShipmentModel value : data.getShipments()) {
       * value.setIncomingCarrier(flightData.getCarrierCode());
       * value.setTenantId(requestModel.getTenantId()); } }
       */
      data.setShipments(
            (List<InboundMailBreakDownShipmentModel>) mailbagProcessor.setMailbagParts(data.getShipments()));
      return data;
   }

   @Override
   public InboundMailBreakDownModel searchMailBreakDown(InboundMailBreakDownModel requestModel) throws CustomException {
      if (null == requestModel.getFlightKey() || null == requestModel.getFlightDate()) {
         throw new CustomException("CUST001", form, ErrorType.ERROR);
      }
      InboundMailBreakDownModel flightData = dao.flightDetail(requestModel);
      if (StringUtils.isEmpty(flightData)) {
         throw new CustomException(INVALIDFLIGHT, form, ErrorType.ERROR);
      }
      requestModel.setFlightId(flightData.getFlightId());
      InboundMailBreakDownModel data = dao.searchMailBreakDown(requestModel);
      if (null == data) {

         requestModel.setCarrierCode(flightData.getCarrierCode());
         requestModel.setFlightKey(flightData.getFlightKey());
         requestModel.setFlightDate(flightData.getFlightDate());
         requestModel.setStaDate(flightData.getSta());
         requestModel.setBoardingPoint(flightData.getBoardingPoint());
         requestModel.setOffPoint(flightData.getOffPoint());
         return requestModel;
      } else {
         data.setBoardingPoint(flightData.getBoardingPoint());
         data.setOffPoint(flightData.getOffPoint());
         data.setCarrierCode(flightData.getCarrierCode());
         data.setFlightKey(flightData.getFlightKey());
         data.setFlightDate(flightData.getFlightDate());
         data.setStaDate(flightData.getSta());
         Map<Object, InboundMailBreakDownShipmentModel> map = new HashMap<>();
         for (InboundMailBreakDownShipmentModel i : data.getShipments()) {
            map.put(i.getMailBagNumber(), i);
         }
         List<InboundMailBreakDownShipmentModel> listOfKeys = new ArrayList<>(map.values());
         data.setShipments((List<InboundMailBreakDownShipmentModel>) mailbagProcessor.setMailbagParts(listOfKeys));
         return data;
      }
   }

   @Override
   @Transactional(rollbackFor = Throwable.class)
   public InboundMailBreakDownModel breakDown(InboundMailBreakDownModel requestModel) throws CustomException {

      // Validate the model
      this.validator.validate(requestModel);

      List<InboundMailBreakDownShipmentModel> createData = requestModel.getShipments().stream()
            .filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      List<InboundMailBreakDownShipmentModel> updateData = requestModel.getShipments().stream()
            .filter(obj -> (Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());

      List<InboundMailBreakDownShipmentModel> deleteData = requestModel.getShipments().stream()
            .filter(obj -> (Action.DELETE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());

      // Validate the EmbargoFlag && CarrierCode,TransferCarrier
      Iterator<InboundMailBreakDownShipmentModel> iter = updateData.iterator();
      String exceptionMsg = new String("Embargo failure for mailbags :");
      boolean flag = false;
      while (iter.hasNext()) {
         InboundMailBreakDownShipmentModel breakDown = iter.next();
         if (breakDown.getOutgoingCarrier() == null)
            continue;
         if (!(requestModel.getCarrierCode().equals(breakDown.getOutgoingCarrier()))
               && breakDown.getEmbargoFlag().equals("YES")) {
            flag = true;
            exceptionMsg = exceptionMsg + " " + breakDown.getMailBagNumber() + ",";
         }
         if (!iter.hasNext() && flag) {
            throw new CustomException(exceptionMsg.substring(0, exceptionMsg.length() - 1), "", ErrorType.ERROR);
         }
      }

      if (!CollectionUtils.isEmpty(deleteData)) {
         int count = dao.checkBreakdownComplete(requestModel.getFlightId());
         if (count == 1) {
            throw new CustomException("impbd.breakdown.completed.for.flight", "", ErrorType.ERROR);
         } else {
            deleteOperationForAMailBag(deleteData);
         }

      }

      if (!CollectionUtils.isEmpty(updateData)) {
         dao.updateOutgoingCarrier(updateData);
      }
      if (!CollectionUtils.isEmpty(createData)) {
         for (InboundMailBreakDownShipmentModel mailBag : createData) {
            int otherShipments = dao.getLoadedSHC(mailBag.getShipmentLocation());
            if (otherShipments > 0) {
               mailBag.setUldPopup(true);
            }

            // mailBag.setUldNumber(requestModel.getUldNumber());
            MailbagModel mailbagModel = MailbagUtil.getMailgag(mailBag.getMailBagNumber());
            mailBag.setDestinationOfficeExchange(mailbagModel.getDestinationCountry()
                  + mailbagModel.getDestinationAirport() + mailbagModel.getDestinationQualifier());
            mailBag.setDispatchNumber(mailbagModel.getDispatchNumber());
            mailBag.setDispatchYear(new BigInteger(mailbagModel.getYear()));
            mailBag.setOriginOfficeExchange(mailbagModel.getOriginCountry() + mailbagModel.getOriginAirport()
                  + mailbagModel.getOriginQualifier());
            mailBag.setMailCategory(mailbagModel.getCategory());
            mailBag.setMailSubType(mailbagModel.getSubCategory());
            mailBag.setFormattedWeight(mailBag.getWeight());
            int savedMailBagCount = dao.checkMailBagAlreadyBrokenDown(mailBag);
            if (savedMailBagCount >= 1) {
               String[] placeHolder = new String[] { mailBag.getMailBagNumber() };
               throw new CustomException("Airmail.BrokenDown.MailBag", form, ErrorType.ERROR, placeHolder);
            }

            // 1. Add shipment information to Shipment Master
            ShipmentMaster shipmentMaster = new ShipmentMaster();
            shipmentMaster.setShipmentType(ShipmentType.Type.MAIL);
            shipmentMaster.setShipmentNumber(mailBag.getMailBagNumber().substring(0, 20));
            shipmentMaster.setCarrierCode(requestModel.getCarrierCode());
            shipmentMaster.setOriginOfficeExchange(mailBag.getOriginOfficeExchange());
            shipmentMaster.setDestinationOfficeExchange(mailBag.getDestinationOfficeExchange());
            shipmentMaster.setOrigin(mailbagModel.getOriginAirport());
            shipmentMaster.setDestination(mailbagModel.getDestinationAirport());
            shipmentMaster.setMailCategory(mailbagModel.getCategory());
            shipmentMaster.setMailSubCategory(mailbagModel.getSubCategory());
            if(!StringUtils.isEmpty(mailBag.getOutgoingCarrier())) {
               mailBag.setOutgoingCarrier(mailBag.getOutgoingCarrier().trim());
            }
            shipmentMaster.setCarrierCode(!StringUtils.isEmpty(mailBag.getOutgoingCarrier()) ? mailBag.getOutgoingCarrier(): requestModel.getCarrierCode());
            shipmentMaster.setNatureOfGoodsDescription(NOG);
            shipmentMaster.setRegistered(mailbagModel.getRegisteredInsuredFlag().equalsIgnoreCase("1") ? true : false);
            /*
             * if (mailBag.isBup()) { shipmentMaster.setPiece(mailBag.getPieces());
             * shipmentMaster.setWeight(mailBag.getWeight()); } else { if
             * (mailbagModel.getLastBagIndicator().equalsIgnoreCase("1")) {
             * shipmentMaster.setPiece(BigInteger.valueOf(Long.parseLong(mailBag.
             * getReceptacleNumber()))); } }
             */

            // Get the shipment date
            LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(shipmentMaster.getShipmentNumber());
            shipmentMaster.setShipmentdate(shipmentDate);
            shipmentMasterService.createShipment(shipmentMaster);

            // 2. Add shipment information to Shipment Verification
            ShipmentVerificationModel shipmentVerificationModel = new ShipmentVerificationModel();
            shipmentVerificationModel.setFlightId(requestModel.getFlightId());
            shipmentVerificationModel.setShipmentId(shipmentMaster.getShipmentId());
            ShipmentVerificationModel getVerificationPiecesWeight = dao
                  .getShipmentVerificationPiecesAndWeight(shipmentVerificationModel);
            if (Optional.ofNullable(getVerificationPiecesWeight).isPresent()) {
               shipmentVerificationModel
                     .setBreakDownPieces(mailBag.getPieces().add(getVerificationPiecesWeight.getBreakDownPieces()));
               shipmentVerificationModel
                     .setBreakDownWeight(mailBag.getWeight().add(getVerificationPiecesWeight.getBreakDownWeight()));
            } else {
               shipmentVerificationModel.setBreakDownPieces(mailBag.getPieces());
               shipmentVerificationModel.setBreakDownWeight(mailBag.getWeight());
            }
            shipmentVerificationModel.setInvokedFromBreakDown(Boolean.TRUE);
            shipmentVerificationService.createShipmentVerification(shipmentVerificationModel);

            // 3. Add ULD/MT info to Break Down ULD/MT
            InboundBreakdownShipmentModel inboundBreakDownShipmentModel = new InboundBreakdownShipmentModel();
            inboundBreakDownShipmentModel.setShipmentdate(shipmentMaster.getShipmentdate());
            inboundBreakDownShipmentModel.setShipmentId(shipmentMaster.getShipmentId());
            inboundBreakDownShipmentModel.setShipmentNumber(shipmentMaster.getShipmentNumber());
            inboundBreakDownShipmentModel.setShipmentType(ShipmentType.Type.MAIL);
            inboundBreakDownShipmentModel.setFlightId(requestModel.getFlightId());
            inboundBreakDownShipmentModel
                  .setShipmentVerificationId(shipmentVerificationModel.getImpShipmentVerificationId());
            inboundBreakDownShipmentModel.setUldNumber(mailBag.getUldNumber());
            // TODO: Need to check cargo pre-announcement function for break condition
            if (mailBag.isBup()) {
               inboundBreakDownShipmentModel.setHandlingMode("NO BREAK");
               inboundBreakDownShipmentModel.setIntactContainer(true);
            } else {
               inboundBreakDownShipmentModel.setHandlingMode("BREAK");
               inboundBreakDownShipmentModel.setIntactContainer(false);
            }

            InboundBreakdownShipmentInventoryModel inboundBreakDownShipmentInventoryModel = new InboundBreakdownShipmentInventoryModel();
            inboundBreakDownShipmentInventoryModel.setShipmentId(shipmentMaster.getShipmentId());
            inboundBreakDownShipmentInventoryModel.setFlightId(requestModel.getFlightId());
            inboundBreakDownShipmentInventoryModel.setShipmentLocation(mailBag.getShipmentLocation());
            inboundBreakDownShipmentInventoryModel.setWarehouseLocation(mailBag.getWarehouseLocation());
            inboundBreakDownShipmentInventoryModel.setInventoryId(null);

            InboundBreakdownShipmentInventoryModel getInventoryPiecesWeight = dao
                  .getShipmentInventoryPiecesAndWeight(inboundBreakDownShipmentInventoryModel);
            if (Optional.ofNullable(getInventoryPiecesWeight).isPresent()) {
               inboundBreakDownShipmentInventoryModel
                     .setPieces(mailBag.getPieces().add(getInventoryPiecesWeight.getPieces()));
               inboundBreakDownShipmentInventoryModel
                     .setWeight(mailBag.getWeight().add(getInventoryPiecesWeight.getWeight()));
            } else {
               inboundBreakDownShipmentInventoryModel.setPieces(mailBag.getPieces());
               inboundBreakDownShipmentInventoryModel.setWeight(mailBag.getWeight());
            }

            List<InboundBreakdownShipmentInventoryModel> inventory = new ArrayList<>();
            inventory.add(inboundBreakDownShipmentInventoryModel);
            inboundBreakDownShipmentModel.setInventory(inventory);

            // House
            InboundBreakdownShipmentHouseModel inboundBreakdownShipmentHouseModel = new InboundBreakdownShipmentHouseModel();
            inboundBreakdownShipmentHouseModel.setShipmentId(shipmentMaster.getShipmentId());
            inboundBreakdownShipmentHouseModel.setType(ShipmentType.Type.MAIL);
            inboundBreakdownShipmentHouseModel.setNumber(mailBag.getMailBagNumber());
            inboundBreakdownShipmentHouseModel.setPieces(mailBag.getPieces());
            inboundBreakdownShipmentHouseModel.setWeight(mailBag.getWeight());
            inboundBreakdownShipmentHouseModel.setOriginOfficeExchange(mailBag.getOriginOfficeExchange());
            inboundBreakdownShipmentHouseModel.setDestinationOfficeExchange(mailBag.getDestinationOfficeExchange());
            inboundBreakdownShipmentHouseModel.setMailCategory(mailBag.getMailCategory());
            inboundBreakdownShipmentHouseModel.setMailType(mailBag.getMailType());
            inboundBreakdownShipmentHouseModel.setMailSubType(mailBag.getMailSubType());
            inboundBreakdownShipmentHouseModel.setDispatchYear(mailBag.getDispatchYear());
            inboundBreakdownShipmentHouseModel.setDispatchNumber(mailBag.getDispatchNumber());
            inboundBreakdownShipmentHouseModel.setLastBag(mailBag.getLastBag());
            inboundBreakdownShipmentHouseModel.setRegistered(mailBag.getRegistered());
            inboundBreakdownShipmentHouseModel.setReceptacleNumber(mailBag.getReceptacleNumber());
            inboundBreakdownShipmentHouseModel.setNextDestination(mailBag.getNextDestination());
            inboundBreakdownShipmentHouseModel.setTransferCarrier(mailBag.getTransferCarrier());
            String shipmentMailType = this.dao.getMailShipmentType(mailBag.getMailBagNumber().substring(13, 15));
            inboundBreakdownShipmentHouseModel.setShipmentMailType(shipmentMailType);
            inboundBreakdownShipmentHouseModel.setWarehouseLocation(requestModel.getWarehouseLocation());
            inboundBreakdownShipmentHouseModel.setUldNumber(requestModel.getUldNumber());
            inboundBreakdownShipmentHouseModel.setShipmentLocation(requestModel.getShipmentLocation());
            List<InboundBreakdownShipmentHouseModel> house = new ArrayList<>();
            house.add(inboundBreakdownShipmentHouseModel);
            inboundBreakDownShipmentInventoryModel.setHouse(house);

            // SHC
            InboundBreakdownShipmentShcModel inboundBreakdownShipmentShcModel = new InboundBreakdownShipmentShcModel();
            // TODO Need to remove below hardcoding later.
            inboundBreakdownShipmentShcModel.setSpecialHandlingCode("MAL");

            List<InboundBreakdownShipmentShcModel> shcs = new ArrayList<>();
            shcs.add(inboundBreakdownShipmentShcModel);
            inboundBreakDownShipmentInventoryModel.setShc(shcs);
            // set shc at shipment level
            inboundBreakDownShipmentModel.setShcs(shcs);

            this.createBreakDownTrolleyInfo(inboundBreakDownShipmentModel);

            // Shipment inventory
            InboundBreakdownModel inboundBreakdownModel = new InboundBreakdownModel();
            inboundBreakdownModel.setFlightId(requestModel.getFlightId());
            inboundBreakdownModel.setShipment(inboundBreakDownShipmentModel);
            shipmentInventoryService.createInventory(inboundBreakdownModel);
            mailBag.setIncomingCarrier(requestModel.getCarrierCode());
            mailBag.setShipmentId(shipmentMaster.getShipmentId());
            mailBag.setShipmentHouseId(dao.getShipmentHouseInformationId(mailBag));

            dao.updateShipmentMasterComplete(shipmentMaster.getShipmentId());

            if ("Y".equalsIgnoreCase(mailBag.getEmbargoFlag())) {
               dao.insertDataintoEmbargoFailure(mailBag);
            }
            mailBag.setCreatedBy(requestModel.getCreatedBy());
            mailBag.setCreatedOn(requestModel.getCreatedOn());
            if (!StringUtils.isEmpty(requestModel.getUldNumber())) {
               uldMasterOperations(mailBag, requestModel.getFlightDate(), requestModel.getFlightId(),
                     requestModel.getBoardingPoint(), requestModel.getOffPoint(), requestModel.getUldNumber(),
                     requestModel, UldMovementFunctionTypes.Names.INBOUNDBREAKDOWN);
            }
            if (!StringUtils.isEmpty(mailBag.getShipmentLocation())
                  && !mailBag.getShipmentLocation().equalsIgnoreCase(requestModel.getUldNumber())) {
               uldMasterOperations(mailBag, requestModel.getFlightDate(), requestModel.getFlightId(),
                     requestModel.getBoardingPoint(), requestModel.getOffPoint(), null, requestModel,
                     UldMovementFunctionTypes.Names.INBOUNDBREAKDOWNSHIPMENTLOCATION);
            }
         }

         dao.insertOutgoingCarrier(createData);

      }
      return requestModel;
   }

   private void uldMasterOperations(InboundMailBreakDownShipmentModel mailBag, LocalDate flightDate,
         BigInteger flightId, String boardPoint, String offPoint, String uldNumber,
         InboundMailBreakDownModel requestModel, String inboundBreakdownLocation) throws CustomException {
      UldInfoModel uldInfo = new UldInfoModel();
      MoveableLocationTypeModel movableLocationModel = new MoveableLocationTypeModel();
      if (!StringUtils.isEmpty(uldNumber)) {
         movableLocationModel.setKey(uldNumber);
         uldInfo.setUldKey(uldNumber);
      } else {
         movableLocationModel.setKey(mailBag.getShipmentLocation());
         uldInfo.setUldKey(mailBag.getShipmentLocation());
      }

      movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
      uldInfo.setCreatedBy(mailBag.getCreatedBy());
      uldInfo.setModifiedBy(mailBag.getCreatedBy());
      if (!StringUtils.isEmpty(mailBag.getWarehouseLocation())) {
         uldInfo.setUldLocationCode(mailBag.getWarehouseLocation());
      } else {
         uldInfo.setUldLocationCode(mailBag.getTerminal());
      }
      uldInfo.setMovableLocationType(movableLocationModel.getLocationType());
      uldInfo.setUldCarrierCode(movableLocationModel.getPart3());
      uldInfo.setUldNumber(movableLocationModel.getPart2());
      uldInfo.setUldType(movableLocationModel.getPart1());
      uldInfo.setContentCode("M");
      if (UldMovementFunctionTypes.Names.INBOUNDBREAKDOWN.equalsIgnoreCase(inboundBreakdownLocation)) {
         uldInfo.setFlightArrivalDate(flightDate.toString());
         uldInfo.setFlightBoardPoint(boardPoint);
         uldInfo.setFlightOffPoint(offPoint);
         uldInfo.setInboundFlightId(flightId);
         uldInfo.setHandlingCarrierCode(requestModel.getCarrierCode());
         uldInfo.setFunctionName(UldMovementFunctionTypes.Names.INBOUNDBREAKDOWN);
      } else {
         uldInfo.setHandlingCarrierCode(movableLocationModel.getPart3());
         uldInfo.setFunctionName(UldMovementFunctionTypes.Names.INBOUNDBREAKDOWNSHIPMENTLOCATION);
      }

      uldService.updateUldInfo(uldInfo);
      mailBag.setShipmentLocationType(movableLocationModel.getLocationType());
   }

   /**
    * Method to create breakdown ULD/Trolley info
    * 
    * @param shipment
    * @throws CustomException
    */
   private void createBreakDownTrolleyInfo(InboundBreakdownShipmentModel shipment) throws CustomException {
      InboundBreakdownShipmentModel uldTrolley = this.inboundBreakDownDao.selectBreakDownULDTrolleyInfo(shipment);
      Optional<InboundBreakdownShipmentModel> optUld = Optional.ofNullable(uldTrolley);
      BigInteger uldTrolleyId;
      if (!optUld.isPresent() || 0 == optUld.get().getId().intValue()) {
         this.inboundBreakDownDao.insertBreakDownULDTrolleyInfo(shipment);
         uldTrolleyId = shipment.getId();
      } else {
         inboundBreakDownDao.updateBreakDownULDTrolleyInfo(shipment);
         uldTrolleyId = optUld.get().getId();
      }
      // Add the inventory for a given ULD/Trolley
      createBreakDownInventory(shipment, uldTrolleyId);
   }

   /**
    * Method to create breakdown storage info
    * 
    * @param shipment
    * @param uldTrolleyId
    * @throws CustomException
    */
   private void createBreakDownInventory(InboundBreakdownShipmentModel shipment, BigInteger uldTrolleyId)
         throws CustomException {
      for (InboundBreakdownShipmentInventoryModel inventory : shipment.getInventory()) {
         // inventory.setInboundBreakdownShipmentId(uldTrolleyId);
         inventory.setImpArrivalManifestULDId(uldTrolleyId);
         InboundBreakdownShipmentInventoryModel invtr = this.inboundBreakDownDao
               .selectInboundBreakdownShipmentInventoryModel(inventory);
         Optional<InboundBreakdownShipmentInventoryModel> optinv = Optional.ofNullable(invtr);
         if (!optinv.isPresent() || 0 == optinv.get().getInboundBreakdownStorageInfoId().intValue()) {
            this.inboundBreakDownDao.insertBreakDownStorageInfo(inventory);
         } else if (optinv.isPresent()) {
            inventory.setInventoryId(optinv.get().getInboundBreakdownStorageInfoId());

            // InboundBreakdownShipmentInventoryModel piecesAndWeight = dao
            // .getPiecesAndWeightForStorageInfo(inventory);
            //
            // if (Optional.ofNullable(piecesAndWeight).isPresent()) {
            // inventory.setPieces(inventory.getPieces().add(piecesAndWeight.getPieces()));
            // inventory.setWeight(inventory.getWeight().add(piecesAndWeight.getWeight()));
            // }

            this.inboundBreakDownDao.updateBreakDownStorageInfo(inventory);
         }
         if (ObjectUtils.isEmpty(inventory.getInventoryId())) {
            inventory.setInventoryId(inventory.getInboundBreakdownStorageInfoId());
         }
         // Add Inventory specific SHC info
         createShcInfo(inventory);
         // Add Inventory specific house info
         createHouseInfo(inventory);
         inventory.setInventoryId(null);
      }
   }

   /**
    * Method to create house info for a inventory
    * 
    * @param inventory
    * @param inventoryId
    * @throws CustomException
    */
   private void createHouseInfo(InboundBreakdownShipmentInventoryModel inventory) throws CustomException {
      for (InboundBreakdownShipmentHouseModel house : inventory.getHouse()) {
         house.setShipmentInventoryId(inventory.getInventoryId());
         if (!this.inboundBreakDownDao.checkBreakDownShipmentHouseModel(house)) {
            this.inboundBreakDownDao.insertBreakDownShipmentHouseModel(house);

         } else {
            this.inboundBreakDownDao.updateBreakDownShipmentHouseModel(house);
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
   private void createShcInfo(InboundBreakdownShipmentInventoryModel inventory) throws CustomException {
      for (InboundBreakdownShipmentShcModel shc : inventory.getShc()) {
         shc.setShipmentInventoryId(inventory.getInventoryId());
         if (!this.inboundBreakDownDao.checkBreakDownStorageSHCInfo(shc)) {
            this.inboundBreakDownDao.insertBreakDownStorageSHCInfo(shc);
         } else {
            this.inboundBreakDownDao.updateBreakDownStorageSHCInfo(shc);
         }
      }
   }

   @Override
   public InboundMailBreakDownShipmentModel splitMailBagNumber(InboundMailBreakDownShipmentModel requestModel) {
      MailbagModel mailbagModel = MailbagUtil.getMailgag(requestModel.getMailBagNumber());
      requestModel.setOrigin(mailbagModel.getOriginAirport());
      requestModel.setMailCategory(mailbagModel.getCategory());
      requestModel.setMailSubType(mailbagModel.getSubCategory());
      requestModel.setDispatchYear(BigInteger.valueOf(Long.valueOf(mailbagModel.getYear())));
      requestModel.setDispatchNumber(mailbagModel.getDispatchNumber());
      requestModel.setReceptacleNumber(mailbagModel.getReceptableNumber());
      requestModel.setLastBag(1 == Integer.valueOf(mailbagModel.getLastBagIndicator()) ? Boolean.TRUE : Boolean.FALSE);
      requestModel.setRegistered(
            1 == Integer.valueOf(mailbagModel.getRegisteredInsuredFlag()) ? Boolean.TRUE : Boolean.FALSE);
      requestModel.setWeight(BigDecimal.valueOf(Double.valueOf(mailbagModel.getWeight())));
      requestModel.setPieces(BigInteger.valueOf(Long.valueOf(mailbagModel.getWeight())));
      requestModel.setOriginOfficeExchange(
            mailbagModel.getOriginCountry() + mailbagModel.getOriginAirport() + mailbagModel.getOriginQualifier());
      requestModel.setDestinationOfficeExchange(mailbagModel.getDestinationCountry()
            + mailbagModel.getDestinationAirport() + mailbagModel.getDestinationQualifier());
      requestModel.setDestination(mailbagModel.getDestinationAirport());
      return requestModel;
   }

   @Override
   public InboundMailBreakDownModel checkMailBag(InboundMailBreakDownModel requestModel) throws CustomException {
      // Breakdown Completed or not Check
      int count = dao.checkBreakdownComplete(requestModel.getFlightId());
      if (count == 1) {
         throw new CustomException("impbd.breakdown.completed.for.flight", "", ErrorType.ERROR);
      }

      if (StringUtils.isEmpty(requestModel.getUldNumber())) {
         throw new CustomException("impbd.enter.uld", "", ErrorType.ERROR);
      }

      if (StringUtils.isEmpty(requestModel.getShipmentLocation())
            && StringUtils.isEmpty(requestModel.getWarehouseLocation())) {
         throw new CustomException(LOCATION, "", ErrorType.ERROR);
      }
      if (!StringUtils.isEmpty(requestModel.getShipmentLocation())) {
         MoveableLocationTypeModel movableLocationModel = new MoveableLocationTypeModel();
         movableLocationModel.setKey(requestModel.getShipmentLocation());
         movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
         if (!movableLocationModel.getMessageList().isEmpty()) {
            throw new CustomException(movableLocationModel.getMessageList().get(0).getCode(), "", ErrorType.ERROR);
         }

         MailContainerInfo containerInfo = new MailContainerInfo();
         containerInfo.setStoreLocation(requestModel.getShipmentLocation());
         containerInfo.setMode("IMPORT");
         containerInfo = mailContainerService.getMailContainerInfo(containerInfo);
         if (!containerInfo.getMessageList().isEmpty()) {
            throw new CustomException(containerInfo.getMessageList().get(0).getCode(), "", ErrorType.ERROR);
         }
      }

      MailbagModel mailbagModel = null;
      for (InboundMailBreakDownShipmentModel value : requestModel.getShipments()) {
         mailbagModel = MailbagUtil.getMailgag(value.getMailBagNumber());
         value.setOrigin(mailbagModel.getOriginAirport());
         value.setMailCategory(mailbagModel.getCategory());
         value.setMailSubType(mailbagModel.getSubCategory());
         value.setDispatchYear(BigInteger.valueOf(Long.valueOf(mailbagModel.getYear())));
         value.setDispatchNumber(mailbagModel.getDispatchNumber());
         value.setReceptacleNumber(mailbagModel.getReceptableNumber());
         value.setLastBag(1 == Integer.valueOf(mailbagModel.getLastBagIndicator()) ? Boolean.TRUE : Boolean.FALSE);
         value.setRegistered(
               1 == Integer.valueOf(mailbagModel.getRegisteredInsuredFlag()) ? Boolean.TRUE : Boolean.FALSE);
         value.setWeight(BigDecimal.valueOf(Double.valueOf(mailbagModel.getWeight())));
         value.setPieces(BigInteger.valueOf(1));

         value.setOriginCountry(mailbagModel.getOriginCountry());
         value.setOriginCity(mailbagModel.getOriginAirport());
         value.setDestinationCountry(mailbagModel.getDestinationCountry());
         value.setDestinationCity(mailbagModel.getDestinationAirport());

         value.setOriginOfficeExchange(
               mailbagModel.getOriginCountry() + mailbagModel.getOriginAirport() + mailbagModel.getOriginQualifier());
         value.setDestinationOfficeExchange(mailbagModel.getDestinationCountry() + mailbagModel.getDestinationAirport()
               + mailbagModel.getDestinationQualifier());
         value.setFormattedWeight(value.getWeight().divide(BigDecimal.TEN));
         int savedMailBagCount = dao.checkMailBagAlreadyBrokenDown(value);
         if (savedMailBagCount >= 1) {
            String[] placeHolder = new String[] { value.getMailBagNumber() };
            throw new CustomException("Airmail.BrokenDown.MailBag", form, ErrorType.ERROR, placeHolder);
         }
      }

      List<InboundMailBreakDownShipmentModel> createData = requestModel.getShipments().stream()
            .filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());

      requestModel.setShipments((List<InboundMailBreakDownShipmentModel>) mailbagProcessor.setMailbagParts(createData));
      return requestModel;
   }

   @Override
   public InboundMailBreakDownModel searchMailBags(InboundMailBreakDownModel requestModel) throws CustomException {
      List<InboundMailBreakDownShipmentModel> listOfKeys = new ArrayList<>();
      if (null != requestModel.getFlightKey() && null != requestModel.getFlightDate()
            && null != requestModel.getUldNumber()) {
         InboundMailBreakDownModel flightData = dao.flightDetail(requestModel);

         if (StringUtils.isEmpty(flightData)) {
            throw new CustomException(INVALIDFLIGHT, form, ErrorType.ERROR);
         }
         requestModel.setFlightId(flightData.getFlightId());

         InboundMailBreakDownModel data = dao.searchMailBreakDown(requestModel);
         if (null != data) {
            if (!CollectionUtils.isEmpty(data.getShipments())) {
               Map<Object, InboundMailBreakDownShipmentModel> map = new HashMap<>();
               for (InboundMailBreakDownShipmentModel i : data.getShipments()) {
                  map.put(i.getMailBagNumber(), i);
               }
               listOfKeys = new ArrayList<>(map.values());
            }
         }

         if (null == data) {
            requestModel.setCarrierCode(flightData.getCarrierCode());
            requestModel.setFlightKey(flightData.getFlightKey());
            requestModel.setFlightDate(flightData.getFlightDate());
            requestModel.setBoardingPoint(flightData.getBoardingPoint());
            requestModel.setOffPoint(flightData.getOffPoint());
            return requestModel;
         } else {
            data.setCarrierCode(flightData.getCarrierCode());
            data.setFlightKey(flightData.getFlightKey());
            data.setFlightDate(flightData.getFlightDate());
            data.setBoardingPoint(flightData.getBoardingPoint());
            data.setOffPoint(flightData.getOffPoint());
            data.setShipments(listOfKeys);
            return data;
         }
      } else if (!StringUtils.isEmpty(requestModel.getUldNumber())) {
         InboundMailBreakDownModel flightData = dao.flightData(requestModel);
         if (StringUtils.isEmpty(flightData)) {
            throw new CustomException("ULD_INVALID", form, ErrorType.ERROR);
         }
         requestModel.setFlightId(flightData.getFlightId());
         InboundMailBreakDownModel data = dao.searchMailBreakDown(requestModel);
         if (null != data) {
            if (!CollectionUtils.isEmpty(data.getShipments())) {
               Map<Object, InboundMailBreakDownShipmentModel> map = new HashMap<>();
               for (InboundMailBreakDownShipmentModel i : data.getShipments()) {
                  map.put(i.getMailBagNumber(), i);
               }
               listOfKeys = new ArrayList<>(map.values());
            }
         }
         if (null == data) {
            requestModel.setCarrierCode(flightData.getCarrierCode());
            requestModel.setFlightKey(flightData.getFlightKey());
            requestModel.setFlightDate(flightData.getFlightDate());
            return requestModel;
         } else {
            data.setCarrierCode(flightData.getCarrierCode());
            data.setFlightKey(flightData.getFlightKey());
            data.setFlightDate(flightData.getFlightDate());
            data.setShipments(listOfKeys);
            return data;
         }
      } else {
         throw new CustomException("Mail_ULD", form, ErrorType.ERROR);
      }

   }

   @Override
   public InboundMailBreakDownModel splitMailBag(InboundMailBreakDownModel request) throws CustomException {
      int count;
      count = dao.getMailBagNumberCount(request.getMailBagShipments().getMailBagNumber());
      MailbagModel mailbagModel = MailbagUtil.getMailgag(request.getMailBagShipments().getMailBagNumber());
      if (count > 0) {
         throw new CustomException("MAILEXPORTACCPT01", form, ErrorType.ERROR);
      } else {
         for (InboundMailBreakDownShipmentModel value : request.getShipments()) {
            value.setMailBagNumber(request.getMailBagShipments().getMailBagNumber());
            value.setOrigin(mailbagModel.getOriginAirport());
            value.setMailCategory(mailbagModel.getCategory());
            value.setMailSubType(mailbagModel.getSubCategory());
            value.setDispatchYear(BigInteger.valueOf(Long.valueOf(mailbagModel.getYear())));
            value.setDispatchNumber(mailbagModel.getDispatchNumber());
            value.setReceptacleNumber(mailbagModel.getReceptableNumber());
            value.setLastBag(1 == Integer.valueOf(mailbagModel.getLastBagIndicator()) ? Boolean.TRUE : Boolean.FALSE);
            value.setRegistered(
                  1 == Integer.valueOf(mailbagModel.getRegisteredInsuredFlag()) ? Boolean.TRUE : Boolean.FALSE);
            value.setWeight(BigDecimal.valueOf(Double.valueOf(mailbagModel.getWeight())));
            value.setPieces(BigInteger.valueOf(1));
            value.setOriginOfficeExchange(mailbagModel.getOriginCountry() + mailbagModel.getOriginAirport()
                  + mailbagModel.getOriginQualifier());
            value.setDestinationOfficeExchange(mailbagModel.getDestinationCountry()
                  + mailbagModel.getDestinationAirport() + mailbagModel.getDestinationQualifier());
            value.setOriginCountry(mailbagModel.getOriginCountry());
            value.setOriginCity(mailbagModel.getOriginAirport());
            value.setDestinationCountry(mailbagModel.getDestinationCountry());
            value.setDestinationCity(mailbagModel.getDestinationAirport());
         }
      }
      List<InboundMailBreakDownShipmentModel> createData = request.getShipments().stream()
            .filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      request.setShipments((List<InboundMailBreakDownShipmentModel>) mailbagProcessor.setMailbagParts(createData));
      return request;
   }

   @Override
   @Transactional(rollbackFor = Throwable.class)
   public InboundMailBreakDownModel mailbreakDown(InboundMailBreakDownModel requestModel) throws CustomException {
      // Validate the model
      this.validator.validate(requestModel);
      List<InboundMailBreakDownShipmentModel> createData = requestModel.getShipments().stream()
            .filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      myLogger.error("size==========================", createData.size());
      if (CollectionUtils.isEmpty(createData)) {
         throw new CustomException("airmail.nomailbagtosave.m", form, ErrorType.ERROR);
      } else {
         for (InboundMailBreakDownShipmentModel mailBag : createData) {

            MailbagModel mailbagModel = MailbagUtil.getMailgag(mailBag.getMailBagNumber());
            mailBag.setDestinationOfficeExchange(mailbagModel.getDestinationCountry()
                  + mailbagModel.getDestinationAirport() + mailbagModel.getDestinationQualifier());
            mailBag.setDispatchNumber(mailbagModel.getDispatchNumber());
            mailBag.setDispatchYear(new BigInteger(mailbagModel.getYear()));
            mailBag.setOriginOfficeExchange(mailbagModel.getOriginCountry() + mailbagModel.getOriginAirport()
                  + mailbagModel.getOriginQualifier());
            mailBag.setMailCategory(mailbagModel.getCategory());
            mailBag.setMailSubType(mailbagModel.getSubCategory());
            mailBag.setFormattedWeight(mailBag.getWeight());
            int savedMailBagCount = dao.checkMailBagAlreadyBrokenDown(mailBag);
            if (savedMailBagCount >= 1) {
               String[] placeHolder = new String[] { mailBag.getMailBagNumber() };
               throw new CustomException("Airmail.BrokenDown.MailBag", form, ErrorType.ERROR, placeHolder);
            }

            // 1. Add shipment information to Shipment Master
            ShipmentMaster shipmentMaster = new ShipmentMaster();
            shipmentMaster.setShipmentType(ShipmentType.Type.MAIL);
            shipmentMaster.setShipmentNumber(mailBag.getMailBagNumber().substring(0, 20));
            shipmentMaster.setCarrierCode(requestModel.getCarrierCode());
            shipmentMaster.setOriginOfficeExchange(mailBag.getOriginOfficeExchange());
            shipmentMaster.setDestinationOfficeExchange(mailBag.getDestinationOfficeExchange());
            shipmentMaster.setOrigin(mailbagModel.getOriginAirport());
            shipmentMaster.setDestination(mailbagModel.getDestinationAirport());
            shipmentMaster.setMailCategory(mailbagModel.getCategory());
            shipmentMaster.setMailSubCategory(mailbagModel.getSubCategory());
            shipmentMaster.setReceptacleNumber(mailbagModel.getReceptableNumber());
            shipmentMaster.setDispatchYear(mailBag.getDispatchYear());
            /*
             * shipmentMaster.setPiece(BigInteger.ONE);
             * shipmentMaster.setWeight(BigDecimal.valueOf(Double.valueOf(mailbagModel.
             * getWeight())));
             */

            // Get the shipment date
            LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(shipmentMaster.getShipmentNumber());
            shipmentMaster.setShipmentdate(shipmentDate);
            shipmentMasterService.createShipment(shipmentMaster);

            // 2. Add shipment information to Shipment Verification
            ShipmentVerificationModel shipmentVerificationModel = new ShipmentVerificationModel();
            shipmentVerificationModel.setFlightId(requestModel.getFlightId());
            shipmentVerificationModel.setShipmentId(shipmentMaster.getShipmentId());
            /*
             * shipmentVerificationModel.setBreakDownPieces(BigInteger.ONE);
             * shipmentVerificationModel.setBreakDownWeight(mailBag.getWeight());
             */
            shipmentVerificationModel.setInvokedFromBreakDown(Boolean.TRUE);
            if (!Optional.ofNullable(mailBag.getPieces()).isPresent()) {
               mailBag.setPieces(BigInteger.ONE);
            }
            ShipmentVerificationModel getVerificationPiecesWeight = dao
                  .getShipmentVerificationPiecesAndWeight(shipmentVerificationModel);
            if (Optional.ofNullable(getVerificationPiecesWeight).isPresent()) {
               shipmentVerificationModel
                     .setBreakDownPieces(mailBag.getPieces().add(getVerificationPiecesWeight.getBreakDownPieces()));
               shipmentVerificationModel
                     .setBreakDownWeight(mailBag.getWeight().add(getVerificationPiecesWeight.getBreakDownWeight()));
            } else {
               shipmentVerificationModel.setBreakDownPieces(mailBag.getPieces());
               shipmentVerificationModel.setBreakDownWeight(mailBag.getWeight());
            }
            shipmentVerificationService.createShipmentVerification(shipmentVerificationModel);

            // 3. Add ULD/MT info to Break Down ULD/MT
            InboundBreakdownShipmentModel inboundBreakDownShipmentModel = new InboundBreakdownShipmentModel();
            inboundBreakDownShipmentModel.setShipmentdate(shipmentMaster.getShipmentdate());
            inboundBreakDownShipmentModel.setShipmentId(shipmentMaster.getShipmentId());
            inboundBreakDownShipmentModel.setShipmentNumber(shipmentMaster.getShipmentNumber());
            inboundBreakDownShipmentModel.setShipmentType(ShipmentType.Type.MAIL);
            inboundBreakDownShipmentModel.setFlightId(requestModel.getFlightId());
            inboundBreakDownShipmentModel
                  .setShipmentVerificationId(shipmentVerificationModel.getImpShipmentVerificationId());
            inboundBreakDownShipmentModel.setUldNumber(requestModel.getUldNumber());
            // TODO: Need to check cargo pre-announcement function for break condition
            inboundBreakDownShipmentModel.setHandlingMode("BREAK");

            InboundBreakdownShipmentInventoryModel inboundBreakDownShipmentInventoryModel = new InboundBreakdownShipmentInventoryModel();
            inboundBreakDownShipmentInventoryModel.setShipmentId(shipmentMaster.getShipmentId());
            inboundBreakDownShipmentInventoryModel.setFlightId(requestModel.getFlightId());
            inboundBreakDownShipmentInventoryModel.setShipmentLocation(requestModel.getShipmentLocation());
            inboundBreakDownShipmentInventoryModel.setWarehouseLocation(requestModel.getWarehouseLocation());
            inboundBreakDownShipmentInventoryModel.setInventoryId(null);
            InboundBreakdownShipmentInventoryModel getInventoryPiecesWeight = dao
                  .getShipmentInventoryPiecesAndWeight(inboundBreakDownShipmentInventoryModel);
            if (Optional.ofNullable(getInventoryPiecesWeight).isPresent()) {
               inboundBreakDownShipmentInventoryModel
                     .setPieces(mailBag.getPieces().add(getInventoryPiecesWeight.getPieces()));
               inboundBreakDownShipmentInventoryModel
                     .setWeight(mailBag.getWeight().add(getInventoryPiecesWeight.getWeight()));
            } else {
               inboundBreakDownShipmentInventoryModel.setPieces(mailBag.getPieces());
               inboundBreakDownShipmentInventoryModel.setWeight(mailBag.getWeight());
            }

            List<InboundBreakdownShipmentInventoryModel> inventory = new ArrayList<>();
            inventory.add(inboundBreakDownShipmentInventoryModel);
            inboundBreakDownShipmentModel.setInventory(inventory);

            // House
            InboundBreakdownShipmentHouseModel inboundBreakdownShipmentHouseModel = new InboundBreakdownShipmentHouseModel();
            inboundBreakdownShipmentHouseModel.setShipmentId(shipmentMaster.getShipmentId());
            inboundBreakdownShipmentHouseModel.setType(ShipmentType.Type.MAIL);
            inboundBreakdownShipmentHouseModel.setNumber(mailBag.getMailBagNumber());
            inboundBreakdownShipmentHouseModel.setPieces(BigInteger.ONE);
            inboundBreakdownShipmentHouseModel.setWeight(mailBag.getWeight());
            inboundBreakdownShipmentHouseModel.setOriginOfficeExchange(mailBag.getOriginOfficeExchange());
            inboundBreakdownShipmentHouseModel.setDestinationOfficeExchange(mailBag.getDestinationOfficeExchange());
            inboundBreakdownShipmentHouseModel.setMailCategory(mailBag.getMailCategory());
            inboundBreakdownShipmentHouseModel.setMailType(mailBag.getMailType());
            inboundBreakdownShipmentHouseModel.setMailSubType(mailBag.getMailSubType());
            inboundBreakdownShipmentHouseModel.setDispatchYear(mailBag.getDispatchYear());
            inboundBreakdownShipmentHouseModel.setDispatchNumber(mailBag.getDispatchNumber());
            inboundBreakdownShipmentHouseModel.setLastBag(mailBag.getLastBag());
            inboundBreakdownShipmentHouseModel.setRegistered(mailBag.getRegistered());
            inboundBreakdownShipmentHouseModel.setReceptacleNumber(mailBag.getReceptacleNumber());
            inboundBreakdownShipmentHouseModel.setNextDestination(mailBag.getNextDestination());
            inboundBreakdownShipmentHouseModel.setTransferCarrier(mailBag.getTransferCarrier());
            inboundBreakdownShipmentHouseModel.setWarehouseLocation(requestModel.getWarehouseLocation());
            inboundBreakdownShipmentHouseModel.setUldNumber(requestModel.getUldNumber());
            inboundBreakdownShipmentHouseModel.setShipmentLocation(requestModel.getShipmentLocation());

            List<InboundBreakdownShipmentHouseModel> house = new ArrayList<>();
            house.add(inboundBreakdownShipmentHouseModel);
            inboundBreakDownShipmentInventoryModel.setHouse(house);

            // SHC
            InboundBreakdownShipmentShcModel inboundBreakdownShipmentShcModel = new InboundBreakdownShipmentShcModel();
            // TODO Need to remove below hardcoding later.
            inboundBreakdownShipmentShcModel.setSpecialHandlingCode("MAL");

            List<InboundBreakdownShipmentShcModel> shcs = new ArrayList<>();
            shcs.add(inboundBreakdownShipmentShcModel);
            inboundBreakDownShipmentInventoryModel.setShc(shcs);
            // set shc at shipment level
            inboundBreakDownShipmentModel.setShcs(shcs);

            this.createBreakDownTrolleyInfo(inboundBreakDownShipmentModel);

            // Shipment inventory
            InboundBreakdownModel inboundBreakdownModel = new InboundBreakdownModel();
            inboundBreakdownModel.setFlightId(requestModel.getFlightId());
            inboundBreakdownModel.setShipment(inboundBreakDownShipmentModel);
            shipmentInventoryService.createInventory(inboundBreakdownModel);
            mailBag.setShipmentId(shipmentMaster.getShipmentId());
         }
         dao.insertOutgoingCarrier(createData);

      }

      return requestModel;
   }

   /* Delete Function Service Method starts here */
   private void deleteOperationForAMailBag(List<InboundMailBreakDownShipmentModel> deleteData) throws CustomException {
      for (InboundMailBreakDownShipmentModel x : deleteData) {
         /*
          * Delete Function which search if there is any mailbag or house available or
          * not
          */
         int countShpHse = dao.checkShipmentHouseInfo(x);
         if (countShpHse != 0) {
            /*
             * Delete Function which fetch inv houses if there is any then else part or this
             * one
             */
            int countInvHse = dao.fetchInvHseInfo(x);
            if (countInvHse == 0) {
               /* Delete Function which deletes inv shc info */
               dao.deleteInvntryShc(x);
               /* Delete Function which deletes inv info */
               dao.deleteInvntry(x);
               /* Delete Function which deletes breakdown house info */
               dao.deletebreakdownHouseInfo(x);
               /* Delete Function which updates breakdown storage info */
               dao.updateBreakdownStorageInfo(x);
               /*
                * Delete Function which fetches storage pc & wgt if there is any then update
                * verifctn info else delete follwing info
                */
               int countBrkDwnPcWgt = dao.fetchBreakdownStorageInfoPieceWeight(x);
               if (countBrkDwnPcWgt == 0) {
                  dao.deleteBreakdownStorageShcInfo(x);
                  dao.deleteBreakdownStorageInfo(x);
                  dao.deleteBreakdownTrolleyInfo(x);
                  dao.deleteShipmentVerification(x);
               } else {
                  dao.updateShipmentVerificationinfo(x);
               }
               /* Delete Function which deletes Shipmnt house info */
               dao.deleteShpHseInf(x);
               /*
                * Delete Function which chck shipment house info if left nothing then deletes
                * shipment master info
                */
               int countHseInf = dao.checkShipmentHouseInfo(x);
               if (countHseInf == 0) {
                  dao.deleteShipmentMasterRoutingInfo(x);
                  dao.deleteShipmentMaster(x);
               }
            } else {
               /* Delete Function which deletes inv house info */
               dao.deleteInventoryHouse(x);
               /* Delete Function which updates inv piece & weight info */
               dao.updateInventoryPieceWeight(x);
               /*
                * Delete Function which fetches inv pc & wgt if there is nothng then delete
                * follwing info
                */
               int invPieceWeight = dao.getPieceWeightInventryCheck(x);
               if (invPieceWeight == 0) {
                  dao.deleteInvntryShc(x);
                  dao.deleteInvntry(x);
               }
               dao.deletebreakdownHouseInfo(x);
               dao.updateBreakdownStorageInfo(x);
               BigInteger breakdownStoragePieces = dao.getBreakDownStoragePiecesInfo(x);
               if (null != breakdownStoragePieces && breakdownStoragePieces.intValue() < 1) {
                  dao.deleteBreakdownStorageShcInfo(x);
                  dao.deleteBreakdownStorageInfo(x);
               }
               dao.updateShipmentVerificationinfo(x);
               BigInteger shpVerPieces = dao.getShpVerPiecesInfo(x);
               if (null != shpVerPieces && shpVerPieces.intValue() < 1) {
                  dao.deleteBreakdownTrolleyInfo(x);
                  dao.deleteShipmentVerification(x);
               }
               /*
                * Delete Function which fetches storage pc & wgt if there is any then update
                * verifctn info else delete follwing info
                */
               /*
                * Delete Function which chck shipment house info if left nothing then deletes
                * shipment master info
                */
               dao.deleteShpHseInf(x);
               int countHseInf = dao.checkShipmentHouseInfo(x);
               if (countHseInf == 0) {
                  dao.deleteShipmentMasterRoutingInfo(x);
                  dao.deleteShipmentMaster(x);
               }
            }
         }
      }
   }

   @Override
   public InboundMailBreakDownModel updateContainerDestination(InboundMailBreakDownModel request)
         throws CustomException {
      Map<String, String> mailBags = new HashMap<>();
      Set<String> shipmentLocations = new HashSet<>();
      List<String> uldKeys = new ArrayList<String>();
      UldDestinationRequest destinationRequest = new UldDestinationRequest();

      List<InboundMailBreakDownShipmentModel> breakdownListUpdate = request.getShipments().stream()
            .filter(obj -> Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD())
                  || Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))
            .collect(Collectors.toList());

      for (InboundMailBreakDownShipmentModel mailBreakDown : breakdownListUpdate) {
         if (mailBreakDown.getShipmentLocation() != null) {
            mailBags.put(mailBreakDown.getMailBagNumber().substring(0, 20) + mailBreakDown.getShipmentLocation(),
                  mailBreakDown.getShipmentLocation());
            shipmentLocations.add(mailBreakDown.getShipmentLocation());
            // check Content Code is C, stop prompt "Container is Used by Cargo"
            MailContainerInfo containerInfo = new MailContainerInfo();
            containerInfo.setStoreLocation(request.getUldNumber());
            containerInfo.setMode("IMPORT");
            containerInfo = mailContainerService.getMailContainerInfo(containerInfo);
            if (!containerInfo.getMessageList().isEmpty()) {
               throw new CustomException(containerInfo.getMessageList().get(0).getCode(), "", ErrorType.ERROR);
            }
            // check Content Code is M and has Shipment, give info prompt
            int otherShipments = dao.getLoadedSHC(mailBreakDown.getShipmentLocation());
            if (otherShipments > 0) {
               mailBreakDown.setUldPopup(true);
            }
         }
      }

      uldKeys.addAll(shipmentLocations);
      destinationRequest.setUldKeys(uldKeys);
      if (destinationRequest.getUldKeys().size() > 0) {
         Map<String, String> destReqMap = new HashMap<>();
         List<UldDestinationEntry> destReq = dao.getUldDestinations(destinationRequest);
         if (destReq != null) {
            for (UldDestinationEntry uldEntry : destReq) {
               destReqMap.put(uldEntry.getUldKey(), uldEntry.getContainerDestination());
            }
         }

         for (int i = 0; i < breakdownListUpdate.size(); i++) {
            InboundMailBreakDownShipmentModel currMailBreakDown = breakdownListUpdate.get(i);
            if (mailBags.containsKey(
                  currMailBreakDown.getMailBagNumber().substring(0, 20) + currMailBreakDown.getShipmentLocation())) {
               String shipLocation = mailBags.get(
                     currMailBreakDown.getMailBagNumber().substring(0, 20) + currMailBreakDown.getShipmentLocation());
               if (destReqMap.get(shipLocation) != null) {
                  if (destReqMap.get(shipLocation).equalsIgnoreCase(currMailBreakDown.getNextDestination())) {
                     currMailBreakDown.setReleaseDest(true);
                  } else {
                     currMailBreakDown.setReleaseDest(false);
                  }
               } else {
                  currMailBreakDown.setReleaseDest(true);
               }
               currMailBreakDown.setContainerDestination(destReqMap.get(shipLocation));
            }
         }
      }
      return request;
   }

   @Override
   public InboundMailBreakDownModel checkContainerDestinationForBreakDown(InboundMailBreakDownModel requestModel)
         throws CustomException {
      List<InboundMailBreakDownShipmentModel> createData = requestModel.getShipments().stream()
            .filter(obj -> (Action.CREATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      List<String> allContDest = new ArrayList<>();
      for (InboundMailBreakDownShipmentModel mailBagData : createData) {
         String contDest = dao.checkContainerDestinationForBreakDown(mailBagData.getShipmentLocation());
         if (!StringUtils.isEmpty(contDest) && !contDest.equalsIgnoreCase(mailBagData.getNextDestination())) {
            String uldWithDest = mailBagData.getShipmentLocation() + ": " + contDest;
            allContDest.add(uldWithDest);
            mailBagData.setContainerDestination(contDest);
         }

      }
      if (!CollectionUtils.isEmpty(allContDest)) {
         requestModel.getShipments().get(0).setAllContainerDest(allContDest);
      }
      return requestModel;
   }
}