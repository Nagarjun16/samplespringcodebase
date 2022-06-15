package com.ngen.cosys.impbd.mail.manifest.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.airmail.model.MailContainerInfo;
import com.ngen.cosys.airmail.processor.MailbagProcessor;
import com.ngen.cosys.airmail.service.MailContainerInfoService;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidator;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.manifest.dao.InboundMailManifestDAO;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestModel;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestShipmentInfoModel;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestShipmentInventoryInfoModel;
import com.ngen.cosys.impbd.mail.manifest.model.TransferCN46FromManifestModel;
import com.ngen.cosys.impbd.mail.validator.MailValidator;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.impbd.shipment.irregularity.service.ShipmentIrregularityService;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;
import com.ngen.cosys.inward.service.InwardServiceReportService;
import com.ngen.cosys.uldinfo.UldMovementFunctionTypes;
import com.ngen.cosys.uldinfo.model.UldInfoModel;
import com.ngen.cosys.uldinfo.service.UldInfoService;
import com.ngen.cosys.validator.enums.ShipmentType;

@Service
public class InboundMailManifestServiceImpl implements InboundMailManifestService {

   @Autowired
   InboundMailManifestDAO inboundMailManifestdao;

   @Autowired
   private MailbagProcessor mailbagProcessor;

   @Autowired
   InwardServiceReportService inwardReportService;

   @Autowired
   ShipmentIrregularityService shipmentIrregularityService;

   @Autowired
   private UldInfoService uldService;

   @Autowired
   MailContainerInfoService mailContainerService;

   @Autowired
   private MoveableLocationTypesValidator moveableLocationTypesValidator;

   @Autowired
   @Qualifier("inboundMailManifestValidator")
   MailValidator validator;

   private String form = "importmanifestForm";

   @Override
   public InboundMailManifestModel search(InboundMailManifestModel requestModel) throws CustomException {
      if (StringUtils.isEmpty(requestModel.getFlightKey()) || requestModel.getFlightDate() == null) {
         throw new CustomException("CUST001", form, ErrorType.ERROR);
      }
      InboundMailManifestModel flightId = inboundMailManifestdao.flightId(requestModel);
      if (flightId == null) {
         throw new CustomException("BOOKING1", form, ErrorType.ERROR);
      }
      requestModel.setFlightId(flightId.getFlightId());
      requestModel.setCarrierCode(flightId.getCarrierCode());
      InboundMailManifestModel data = inboundMailManifestdao.search(requestModel);
      if (StringUtils.isEmpty(data)) {
         flightId.setCheckData(true);
         return flightId;
      } else {
         data.setMailFirstTimeBreakDownCompletedBy(flightId.getMailFirstTimeBreakDownCompletedBy());
         data.setMailFirstTimeDocumentVerificationCompletedBy(
               flightId.getMailFirstTimeDocumentVerificationCompletedBy());
         data.setFlightId(flightId.getFlightId());
         data.setSegmentId(flightId.getSegmentId());
         data.setCarrierCode(flightId.getCarrierCode());
         data.setSegments(flightId.getSegments());
      }
      if (!CollectionUtils.isEmpty(data.getShipments())) {
         data.getMailBagInfo().forEach(obj -> {
            obj.setCarrierCode(flightId.getCarrierCode());
            obj.setIncomingCarrier(flightId.getCarrierCode());
         });
         mailbagProcessor.setMailbagParts(data.getMailBagInfo());
         for (InboundMailManifestShipmentInfoModel value : data.getShipments()) {
            BigInteger pieceSum = BigInteger.ZERO;
            BigDecimal weightSum = BigDecimal.ZERO;
            for (InboundMailManifestShipmentInventoryInfoModel obj : value.getInventory()) {
               obj.setIncomingCarrier(flightId.getCarrierCode());
               pieceSum = pieceSum.add(obj.getBreakDownPieces());
               weightSum = weightSum.add(obj.getBreakDownWeight());
               obj.setLoadedHouse(inboundMailManifestdao.loadedHouse(obj));
            }
            value.setBreakDownPieces(pieceSum);
            value.setBreakDownWeight(weightSum);
            value.setInventory((List<InboundMailManifestShipmentInventoryInfoModel>) mailbagProcessor
                  .setMailbagParts(value.getInventory()));
         }
      }
      /*
       * for(MailBagInformation mailBags: data.getMailBagInfo()) {
       * mailBags.setLoadedHouse(inboundMailManifestdao.loadedHouse(mailBags)); }
       */
      // data.setShipments((List<InboundMailManifestShipmentInfoModel>)mailbagProcessor.setMailbagParts(data.getShipments()));
      return data;
   }

   @Override
   @Transactional(rollbackFor = Throwable.class)
   public InboundMailManifestModel manifest(InboundMailManifestModel requestModel) throws CustomException {
      // Validate the model
      this.validator.validate(requestModel);

      return null;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.mail.manifest.service.InboundMailManifestService#
    * transferToCN46(com.ngen.cosys.impbd.mail.manifest.model.
    * InboundMailManifestModel)
    */
   @Override
   @Transactional(rollbackFor = Throwable.class)
   public InboundMailManifestModel transferToCN46(InboundMailManifestModel requestModel) throws CustomException {
      for (InboundMailManifestShipmentInfoModel toTransferData : requestModel.getShipments()) {
         toTransferData.setFlightId(requestModel.getFlightId());
         List<TransferCN46FromManifestModel> transferData = inboundMailManifestdao
               .getTransferToCn46Data(toTransferData);
         for (TransferCN46FromManifestModel value : transferData) {
            Integer id = inboundMailManifestdao.checkId(requestModel);
            if (value.getUldTrollyNumber() == null) {
               value.setUldTrollyNumber("bulk");
            }
            if (null != id) {
               value.setAirmailManifestId(BigInteger.valueOf(id));
               // inboundMailManifestdao.transferToCN46(value);
               inboundMailManifestdao.transfer(value);
            } else {
               inboundMailManifestdao.transferToCN46(value);
               inboundMailManifestdao.transfer(value);
            }
         }
      }

      return requestModel;
   }

   @Override
   @Transactional(rollbackFor = Throwable.class)
   public InboundMailManifestModel transferToServiceReport(InboundMailManifestModel requestModel)
         throws CustomException {

      return null;
   }

   @Override
   @Transactional(rollbackFor = Throwable.class)
   public InboundMailManifestModel documentComplete(InboundMailManifestModel requestModel) throws CustomException {

      if (!requestModel.getCheckData()) {
         Integer count = inboundMailManifestdao.checkDateAta(requestModel);
         if (count < 1) {
            throw new CustomException("DATEATACHECK", form, ErrorType.ERROR);
         }
         boolean value = inboundMailManifestdao.checkDocumentIfAlreadyDone(requestModel);
         requestModel.setDocumentCompletedBy(requestModel.getModifiedBy());
         requestModel.setDocumentCompletedAt(requestModel.getModifiedOn());
         if (value) {
            inboundMailManifestdao.afterDocumentComplete(requestModel);
         } else {
            // Update the status of document complete
            inboundMailManifestdao.documentComplete(requestModel);
         }
         irreguLarityCreationForDocument(requestModel);
      } else {

         InboundMailManifestModel reOpen = inboundMailManifestdao.reopenDocumentIfAlreadyDone(requestModel);
         // Reopen Document
         requestModel.setDocumentCompletedAt(reOpen.getDocumentCompletedAt());
         requestModel.setDocumentCompletedBy(reOpen.getDocumentCompletedBy());
         inboundMailManifestdao.reOpenDocument(requestModel);
         inboundMailManifestdao.unFinalizeServiceReport(requestModel);
      }

      return requestModel;
   }

   private void irreguLarityCreationForDocument(InboundMailManifestModel requestModel) throws CustomException {
      // Get the status of Document Complete and breakdown complete
      boolean documentComplete = inboundMailManifestdao.checkDocumentCompleted(requestModel);
      boolean breakDownCompleted = inboundMailManifestdao.checkBreakDownComplete(requestModel);
      if (!CollectionUtils.isEmpty(requestModel.getInboundShipments())) {
         for (InboundMailManifestShipmentInfoModel f : requestModel.getInboundShipments()) {
            if (Optional.ofNullable(f.getPieces()).isPresent() && Optional.ofNullable(f.getWeight()).isPresent()
                  && documentComplete && breakDownCompleted) {
               this.processFlightComplete(requestModel);
            }
         }
      }
   }

   @Override
   @Transactional(rollbackFor = Throwable.class)
   public InboundMailManifestModel breakDownComplete(InboundMailManifestModel requestModel) throws CustomException {
      if (!requestModel.getCheckData()) {
         Integer count = inboundMailManifestdao.checkDateAta(requestModel);
         if (count < 1) {
            throw new CustomException("DATEATACHECK", form, ErrorType.ERROR);
         }
         boolean value = inboundMailManifestdao.checkBreakdownIfAlreadyDone(requestModel);
         requestModel.setBreakDownCompletedBy(requestModel.getModifiedBy());
         requestModel.setBreakDownCompletedAt(requestModel.getModifiedOn());
         if (value) {
            inboundMailManifestdao.afterBreakDownComplete(requestModel);
         } else {
            // Update the status of BreakDown complete
            inboundMailManifestdao.breakDownComplete(requestModel);
         }
         irregularityCreation(requestModel);
      } else {
         InboundMailManifestModel reOpen = inboundMailManifestdao.reopenBreakdownIfAlreadyDone(requestModel);
         // Reopen Breakdown
         requestModel.setBreakDownCompletedAt(reOpen.getBreakDownCompletedAt());
         requestModel.setBreakDownCompletedBy(reOpen.getBreakDownCompletedBy());
         inboundMailManifestdao.reOpenBreakDown(requestModel);
         inboundMailManifestdao.unFinalizeServiceReport(requestModel);
      }

      return requestModel;
   }

   private void irregularityCreation(InboundMailManifestModel requestModel) throws CustomException {
      // Get the status of Break Complete and document complete
      boolean documentComplete = inboundMailManifestdao.checkDocumentCompleted(requestModel);
      boolean breakDownCompleted = inboundMailManifestdao.checkBreakDownComplete(requestModel);
      if (!CollectionUtils.isEmpty(requestModel.getInboundShipments())) {
         for (InboundMailManifestShipmentInfoModel f : requestModel.getInboundShipments()) {
            if (Optional.ofNullable(f.getPieces()).isPresent() && Optional.ofNullable(f.getWeight()).isPresent()
                  && breakDownCompleted && documentComplete) {
               this.processFlightComplete(requestModel);
            }
         }
      }
   }

   /**
    * Method which checks both document and break is marked as complete at flight
    * level if yes it derives shipment irregularity for each DN and also publishes
    * data to flight inward service report
    * 
    * @param requestModel
    * @throws CustomException
    */
   private void processFlightComplete(InboundMailManifestModel requestModel) throws CustomException {
      // Get all mail bags which has a irregularity
      List<ShipmentIrregularityModel> irregularityInfo = inboundMailManifestdao.irregularityInfo(requestModel);
      // Populate all irregularity information for the inward service report shipment
      // discrepancy
      List<InwardServiceReportShipmentDiscrepancyModel> physicalDiscrepancy = new ArrayList<>();
      // Add shipment irregularity
      for (ShipmentIrregularityModel t : irregularityInfo) {
         this.shipmentIrregularityService.createIrregularity(t);
         // Populate irregulrity info
         InwardServiceReportShipmentDiscrepancyModel serviceReportShipmentDiscrepancyModel = new InwardServiceReportShipmentDiscrepancyModel();
         serviceReportShipmentDiscrepancyModel.setShipmentType(ShipmentType.Type.MAIL);
         serviceReportShipmentDiscrepancyModel.setShipmentdate(t.getShipmentdate());
         serviceReportShipmentDiscrepancyModel.setShipmentNumber(t.getShipmentNumber());
         serviceReportShipmentDiscrepancyModel.setDiscrepancyType(t.getDiscrepancyType());
         serviceReportShipmentDiscrepancyModel.setIrregularityDescription(t.getIrregularityRemarks());
         serviceReportShipmentDiscrepancyModel.setIrregularityPieces(t.getPiece());
         serviceReportShipmentDiscrepancyModel.setIrregularityType(t.getCargoIrregularityCode());
         serviceReportShipmentDiscrepancyModel.setOrigin(t.getOrigin());
         serviceReportShipmentDiscrepancyModel.setDestination(t.getDestination());
         serviceReportShipmentDiscrepancyModel.setWeight(t.getWeight());
         serviceReportShipmentDiscrepancyModel.setWeightUnitCode(t.getWeightUnitCode());
         serviceReportShipmentDiscrepancyModel.setPiece(t.getDocumentedPieces());
         serviceReportShipmentDiscrepancyModel.setReceptacleNumber(t.getReceptacleNumber());
         physicalDiscrepancy.add(serviceReportShipmentDiscrepancyModel);

      }

      InwardServiceReportModel inwardServiceReportModel = new InwardServiceReportModel();
      inwardServiceReportModel.setServiceReportFor(ShipmentType.Type.MAIL);
      inwardServiceReportModel.setFlightId(requestModel.getFlightId());
      inwardServiceReportModel.setPhysicalDiscrepancy(physicalDiscrepancy);

      List<InwardServiceReportShipmentDiscrepancyModel> nonIrregularityData = inboundMailManifestdao
            .nonIrregularityData(physicalDiscrepancy);
      List<InwardServiceReportShipmentDiscrepancyModel> manualFlagDisabledData = nonIrregularityData.stream()
            .filter(obj -> !obj.getManual()).collect(Collectors.toList());
      inwardServiceReportModel.setPhysicalDiscrepancy(manualFlagDisabledData);
      inwardServiceReportModel.getPhysicalDiscrepancy().forEach(ele -> ele.setFlagCRUD(Action.CREATE.toString()));
      inwardReportService.createServiceReportOnFlightComplete(inwardServiceReportModel);
   }

   @Override
   public List<InboundMailManifestShipmentInventoryInfoModel> checkContainerDestination(
         List<InboundMailManifestShipmentInventoryInfoModel> requestModel) throws CustomException {
      List<InboundMailManifestShipmentInventoryInfoModel> updateLocationModel = requestModel.stream()
            .filter(obj -> (Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      if (StringUtils.isEmpty(requestModel.get(0).getStorageLocation())
            && StringUtils.isEmpty(requestModel.get(0).getBreakDownLocation())) {
         throw new CustomException("exp.buildup.unload.location.required", "", ErrorType.ERROR);

      }
      /*
       * for Bug - 17273( Stopped for below validation in UI only.)
       * 
       * if (!StringUtils.isEmpty(requestModel.get(0).getStorageLocation())) {
       * MoveableLocationTypeModel movableLocationModel = new
       * MoveableLocationTypeModel();
       * movableLocationModel.setKey(requestModel.get(0).getStorageLocation());
       * movableLocationModel =
       * moveableLocationTypesValidator.split(movableLocationModel); if
       * (!movableLocationModel.getMessageList().isEmpty()) { throw new
       * CustomException(movableLocationModel.getMessageList().get(0).getCode(), "",
       * ErrorType.ERROR); } if (movableLocationModel.getDummyLocation()) { throw new
       * CustomException("data.invalid.storage.location", "", ErrorType.ERROR);
       * 
       * }}
       */
      int i = 0;
      for (InboundMailManifestShipmentInventoryInfoModel inventory : updateLocationModel) {
         Integer countCheckForLoad = inboundMailManifestdao.checkMailBagLoaded(inventory);
         if (countCheckForLoad > 0) {
            throw new CustomException("ALREADYLOADEDMB", "", ErrorType.ERROR);
         }
         // Check Content code is C ,stop prompt "Container is Used by Cargo"
         if (!StringUtils.isEmpty(updateLocationModel.get(0).getStorageLocation()) && i == 0) {
            MailContainerInfo containerInfo = new MailContainerInfo();
            containerInfo.setStoreLocation(updateLocationModel.get(0).getStorageLocation());
            containerInfo.setMode("IMPORT");
            containerInfo = mailContainerService.getMailContainerInfo(containerInfo);
            if (!containerInfo.getMessageList().isEmpty()) {
               throw new CustomException(containerInfo.getMessageList().get(0).getCode(), "", ErrorType.ERROR);
            }
         }
         if (i == 0) {
            String containerDestination = inboundMailManifestdao
                  .getContainerDestination(updateLocationModel.get(0).getStorageLocation());
            if (!StringUtils.isEmpty(containerDestination)
                  && !containerDestination.equalsIgnoreCase(inventory.getNextDestination())) {
               inventory.setReleaseDest(true);
               inventory.setContainerDestination(containerDestination);
            }
         }

         i++;
      }
      return updateLocationModel;
   }

   @Override
   @Transactional(rollbackFor = Throwable.class)
   public List<InboundMailManifestShipmentInventoryInfoModel> updateLocation(
         List<InboundMailManifestShipmentInventoryInfoModel> requestModel) throws CustomException {
      List<InboundMailManifestShipmentInventoryInfoModel> updateLocationModel = requestModel.stream()
            .filter(obj -> (Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      for (InboundMailManifestShipmentInventoryInfoModel inventory : updateLocationModel) {

         BigInteger newInventoryId = inboundMailManifestdao.getInventory(inventory);

         inventory.setHouseId(inboundMailManifestdao.getHouseInventory(inventory));
         if (inventory.getShipmentInventoryId() != newInventoryId) {
            // deduct pieces from previous inv
            inboundMailManifestdao.deductExistingInventoryPieceWeight(inventory);
            inboundMailManifestdao.deleteHouse(inventory);
         }
         if (!Optional.ofNullable(newInventoryId).isPresent()) {
            inboundMailManifestdao.createInventory(inventory);
            inboundMailManifestdao.createHouseInventory(inventory);
         } else {
            // add pieces into new inv
            inventory.setShipmentInventoryId(newInventoryId);
            inboundMailManifestdao.addNewInventoryPieceWeight(inventory);
            inboundMailManifestdao.createHouseInventory(inventory);
         }
         if (!StringUtils.isEmpty(inventory.getStorageLocation())) {
            uldMasterOperations(inventory);
         }
      }
      if (updateLocationModel.get(0).isReleaseDest()
            && !StringUtils.isEmpty(updateLocationModel.get(0).getContainerDestination())) {
         inboundMailManifestdao.updateNextDestinationOfMailBags(updateLocationModel);
      }

      return requestModel;
   }

   private void uldMasterOperations(InboundMailManifestShipmentInventoryInfoModel inventory) throws CustomException {
      UldInfoModel uldInfo = new UldInfoModel();
      MoveableLocationTypeModel movableLocationModel = new MoveableLocationTypeModel();
      movableLocationModel.setKey(inventory.getStorageLocation());
      movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
      uldInfo.setUldKey(inventory.getStorageLocation());
      uldInfo.setCreatedBy(inventory.getCreatedBy());
      uldInfo.setModifiedBy(inventory.getModifiedBy());
      // have to fetch data from table and set here
      /*
       * uldInfo.setFlightArrivalDate(flightDate.toString());
       * uldInfo.setFlightBoardPoint(boardPoint); uldInfo.setFlightOffPoint(offPoint);
       */
      uldInfo.setInboundFlightId(inventory.getFlightId());
      uldInfo.setHandlingCarrierCode(inventory.getIncomingCarrier());
      if (!StringUtils.isEmpty(inventory.getBreakDownLocation())) {
         uldInfo.setUldLocationCode(inventory.getBreakDownLocation());
      } else {
         uldInfo.setUldLocationCode(inventory.getTerminal());
      }
      uldInfo.setMovableLocationType(movableLocationModel.getLocationType());
      uldInfo.setUldCarrierCode(movableLocationModel.getPart3());
      uldInfo.setUldNumber(movableLocationModel.getPart2());
      uldInfo.setUldType(movableLocationModel.getPart1());
      uldInfo.setContentCode("M");
      uldInfo.setFunctionName(UldMovementFunctionTypes.Names.INBOUNDMAILMANIFEST);
      uldService.updateUldInfo(uldInfo);

   }

   @Override
   public void checkTransferToCN46(InboundMailManifestModel requestModel) throws CustomException {
      for (InboundMailManifestShipmentInfoModel value : requestModel.getShipments()) {
         value.setFlightId(requestModel.getFlightId());
         Integer count = inboundMailManifestdao.checkTransferToCN46(value);
         if (count >= 1) {
            throw new CustomException("mail.mainfest001", form, ErrorType.ERROR);
         }
      }

   }

   @Override
   public String getStoragelocType(String requestModel) throws CustomException {
      return inboundMailManifestdao.getStoreLocationType(requestModel);
   }

}