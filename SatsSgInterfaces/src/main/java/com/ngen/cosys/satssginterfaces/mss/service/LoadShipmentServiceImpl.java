package com.ngen.cosys.satssginterfaces.mss.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.LoadShipmentDao;
import com.ngen.cosys.satssginterfaces.mss.dao.MailLoadShipmentDao;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;
import com.ngen.cosys.satssginterfaces.mss.model.BuildUpULD;
import com.ngen.cosys.satssginterfaces.mss.model.BuildupLoadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.CommonLoadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.DLS;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.satssginterfaces.mss.model.DLSUldtrolleySHCinfo;
import com.ngen.cosys.satssginterfaces.mss.model.Flight;
import com.ngen.cosys.satssginterfaces.mss.model.LoadedShipment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestFlight;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestHouse;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSHC;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSegment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestShipment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestULD;
import com.ngen.cosys.satssginterfaces.mss.model.SHCS;
import com.ngen.cosys.satssginterfaces.mss.model.Shipment;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentHouse;
import com.ngen.cosys.satssginterfaces.mss.model.ULDIInformationDetails;
import com.ngen.cosys.satssginterfaces.mss.model.UldShipment;
import com.ngen.cosys.utils.BigDecimalUtils;
import com.ngen.cosys.validator.LoadShipmentValidator;

/**
 * Service to serve Load Shipment related request
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class LoadShipmentServiceImpl implements LoadShipmentService {
   @Autowired
   MailLoadShipmentDao mailDao;

   @Autowired
   LoadShipmentDao dao;

   @Autowired
   private ManifestService manifestService;

   @Autowired
   private LoadShipmentValidator validator;

   @Autowired
   BigDecimalUtils bigDecimalUtils;

   @Autowired
   private UpdateDLSService uldService;

   @Override
   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public BuildupLoadShipment insertLoadShipment(BuildupLoadShipment loadModel) throws CustomException {

      // based on the flag insert/update data into Load Shipment table
      for (UldShipment uld : loadModel.getLoadedShipmentList()) {
         if (ObjectUtils.isEmpty(uld.getAssUldTrolleyId())) {
            uld.setSegmentId(loadModel.getSegmentId());
            uld.setFlightId(loadModel.getFlightId());
            uld.setAssUldTrolleyNo(loadModel.getAssUldTrolleyNo());
            BigInteger assUldId = getAssULDIdForULD(uld, loadModel.getFlightKey(), loadModel.getFlightOriginDate(),
                  loadModel.getLoadType(), loadModel.getTerminal());
            uld.setAssUldTrolleyId(assUldId);
         }

         updateInventory(uld);

         for (LoadedShipment load : uld.getLoadShipmentList()) {
            load.setShipmentNumber(uld.getShipmentNumber());
            load.setShipmentId(uld.getShipmentId());
            load.setAssUldTrolleyId(uld.getAssUldTrolleyId());
            performLoad(uld, load);
         }
      }
      createmanifest(loadModel);
      updateDLS(loadModel);
      return loadModel;
   }

   // Rule Execution Engine Code End Here
   /**
    * @param uldShip
    * @param flightKey
    * @param date
    * @param uld
    * @return
    * @throws CustomException
    */
   private BigInteger getAssULDIdForULD(UldShipment uldShip, String flightKey, LocalDate date, String type,
         String terminal) throws CustomException {

      ULDIInformationDetails uld = new ULDIInformationDetails();
      uld.setHandlingArea(terminal);
      BigInteger assUldId = BigInteger.valueOf(0);
      boolean isUldExist = dao.checkIsUldExist(uldShip);

      if (!StringUtils.isEmpty(uldShip.getAssUldTrolleyNo())) {
         if (uldShip.getAssUldTrolleyNo().length() > 8 && !isUldExist) {
            uld.setTrolleyInd(false);
            if (StringUtils.isEmpty(uldShip.getContentCode())) {
               throw new CustomException("exp.loadshipment.content.code.error", "", ErrorType.ERROR);
            }
            if (StringUtils.isEmpty(uldShip.getHeightCode())) {
               throw new CustomException("exp.loadshipment.height.code.error", "", ErrorType.ERROR);
            }
            uld.setContentCode(uldShip.getContentCode());
            uld.setHeightCode(uldShip.getHeightCode());
            uld.setPhcFlag((uldShip.getPhcIndicator() == 0) ? false : true);
         } else if (!isUldExist) {
            uld.setTrolleyInd(true);
         }
      }

      // code to check if ULD is exist
      uld.setUldNumber(uldShip.getAssUldTrolleyNo());
      uld.setUldTrolleyNo(uldShip.getAssUldTrolleyNo());

      uld.setFlightId(uldShip.getFlightId());
      uld.setSegmentId(uldShip.getSegmentId());
      BigInteger id = dao.getAssignULDTrolleyId(uld);

      if (id != null) {
         return id;
      } else {
         AssignULD assUld = new AssignULD();
         uld.setContentCode(uldShip.getContentCode());
         uld.setHeightCode(uldShip.getHeightCode());
         uld.setPhcFlag((uldShip.getPhcIndicator() == 0) ? false : true);
         assUld.setUld(uld);
         //dao.createAssignUldToFlight(assUld);
         return assUld.getAssUldTrolleyId();
      }

      /*
       * uld.setSegmentId(uldShip.getSegmentId());
       * uld.setPhcFlag((uldShip.getPhcIndicator() == 0) ? false : true); assUldId =
       * getAssULDTrolleyId(uld, flightKey, date, uldShip.getSegmentId()); return
       * assUldId;
       */

   }

   /**
    * @param uld
    * @throws CustomException
    */
   private void updateInventory(UldShipment uld) throws CustomException {
      int inventorySize = uld.getLoadShipmentList().size();
      double diffWeight = 0.0;
      int count = 0;

      for (LoadedShipment load : uld.getLoadShipmentList()) {
         count++;

         if (diffWeight != 0.0) {
            load.setLocationWeight(BigDecimal.valueOf(load.getLocationWeight().setScale(2).doubleValue() + diffWeight));
            dao.updateInventory(load);
         }

         long pieces = load.getLocationPiecs().longValue() - load.getMovePiecs().longValue();
         double weight = load.getLocationWeight().setScale(2).doubleValue()
               - load.getMoveWeight().setScale(2).doubleValue();

         if (pieces == 0 && weight != 0) {
            if (count == inventorySize) {
               throw new CustomException("ERROR", "", ErrorType.ERROR);
            }
            diffWeight = weight;
            load.setLocationWeight(
                  BigDecimal.valueOf(load.getLocationWeight().setScale(2).doubleValue() - Math.abs(weight)));
            dao.updateInventory(load);
         }
      }
   }

   /**
    * @param shipment
    * @param isLoadByULD
    * @param assUldId
    * @param uld
    * @param load
    * @return
    * @throws CustomException
    *            Method to Perform Load Operation
    */
   private void performLoad(UldShipment uld, LoadedShipment load) throws CustomException {

      load.setLoadedPieces(load.getMovePiecs());
      load.setLoadedWeight(load.getMoveWeight());
      boolean isShipemntExist = dao.checkLoadShipmentPK(load);
      if (!isShipemntExist) {
         dao.insertLoadShipment(load);
         insertSHCTag(load);
         checkInventory(uld, load);
      } else {
         updateLoadedRecord(load);
         insertSHCTag(load);
         checkInventory(uld, load);
      }
   }

   /**
    * @param load
    * @throws CustomException
    */
   private void createmanifest(BuildupLoadShipment load) throws CustomException {

      ManifestFlight manifest = new ManifestFlight();
      Flight flight = new Flight();
      ManifestSegment segment = new ManifestSegment();
      List<ManifestULD> uldList = new ArrayList<>();
      List<ManifestSegment> segmentList = new ArrayList<>();

      flight.setFlightKey(load.getFlightKey());
      flight.setFlightId(load.getFlightId());
      flight.setFlightOriginDate(load.getFlightOriginDate());
      manifest.setFlight(flight);

      segment.setFlightId(load.getFlightId().intValue());
      segment.setSegmentId(load.getSegmentId());

      for (UldShipment uldShip : load.getLoadedShipmentList()) {
         int count = 0;
         ManifestULD uld = new ManifestULD();
         List<ManifestShipment> shipmentList = new ArrayList<>();

         uld.setUldTrolleyNo(uldShip.getAssUldTrolleyNo());
         uld.setUldSequenceId(uldShip.getAssUldTrolleyId());

         for (LoadedShipment loadShip : uldShip.getLoadShipmentList()) {
            if (count == 0) {
               Integer loadShipId = dao.getLoadedShipmentInfoId(loadShip);
               List<ManifestSHC> shcsList = dao.getShipmentSHC(loadShipId);
               List<ManifestHouse> houseList = dao.getShipmentHouse(loadShipId);

               LoadedShipment loadedData = dao.fetchLoadedPieceWeight(loadShip);
               Shipment ship = dao.getShipmentDetails(loadShip.getShipmentId().intValue());

               ManifestShipment shipment = new ManifestShipment();
               shipment.setUldNumber(loadShip.getAssUldTrolleyNo());
               shipment.setUldSequenceId(loadShip.getAssUldTrolleyId());
               shipment.setOrigin(ship.getOrigin());
               shipment.setShipmentId(loadShip.getShipmentId());
               shipment.setDestination(ship.getDestination());
               shipment.setShipmentPieces(ship.getShipmentPieces());
               shipment.setNatureOfGoods(ship.getNatureOfGoods());
               shipment.setWeightCode(ship.getWeightCode());
               shipment.setShipmentNumber(ship.getShipmentNumber());
               shipment.setLoadedPieces(loadedData.getLoadedPieces());
               shipment.setLoadedWeight(loadedData.getLoadedWeight());
               shipment.setShcList(shcsList);
               shipment.setHouseList(houseList);
               shipmentList.add(shipment);
            }
            count++;
         }

         uld.setShipment(shipmentList);
         uldList.add(uld);
      }
      segment.setUlds(uldList);
      segmentList.add(segment);
      manifest.setSegment(segmentList);

      manifestService.createManifest(manifest);
   }

   /**
    * @param buildUp
    * @throws CustomException
    */
   private void updateDLS(BuildupLoadShipment buildUp) throws CustomException {
      int count = 0;
      DLS dls = new DLS();
      List<DLSULD> dlsULDList = new ArrayList<>();
      dls.setFlightId(buildUp.getFlightId());

      for (UldShipment uld : buildUp.getLoadedShipmentList()) {
         if ((!uld.isOcsCargoFlag() || !StringUtils.isEmpty(uld.getAssUldTrolleyNo()))
               && !"BULK".equalsIgnoreCase(uld.getAssUldTrolleyNo())) {
            int awbCount = 0;
            DLSULD dlsULD = new DLSULD();

            for (LoadedShipment load : uld.getLoadShipmentList()) {
               List<DLSUldtrolleySHCinfo> shcsList = new ArrayList<>();
               if (buildUp.getLoadType().equalsIgnoreCase("ULD") && count == 0) {
                  perfromUpdateDLS(buildUp, shcsList, dlsULD, load, uld);

               } else if (awbCount == 0) {
                  perfromUpdateDLS(buildUp, shcsList, dlsULD, load, uld);
               }
               awbCount++;
               count++;
            }
            dlsULD.setUsedAsTrolley(uld.isOcsCargoFlag());
            dlsULD.setTrolleyInd(uld.isOcsCargoFlag());
            dlsULD.setHeightCode(uld.getHeightCode());
            dlsULD.setFlightSegmentId(uld.getSegmentId());
            dlsULDList.add(dlsULD);

         }
      }
      dls.setUldTrolleyList(dlsULDList);

      uldService.insertUpdateDLS(dls);

   }

   /**
    * @param buildUp
    * @param dlsSHC
    * @param shcsList
    * @param dlsULD
    * @param load
    * @throws CustomException
    */
   private void perfromUpdateDLS(BuildupLoadShipment buildUp, List<DLSUldtrolleySHCinfo> shcsList, DLSULD dlsULD,
         LoadedShipment load, UldShipment uld) throws CustomException {
      List<String> contentCodeList = new ArrayList<>();

      UldShipment shipObj = new UldShipment();
      shipObj.setAssUldTrolleyId(uld.getAssUldTrolleyId());
      shipObj.setFlightSegmentId(buildUp.getSegmentId());

      Double actualWeight = dao.getLoadedWeight(load.getAssUldTrolleyId().intValue());
      LoadedShipment newLoad = dao.fetchLoadedPieceWeight(load);
      UldShipment uldShipemnt = dao.getUldData(shipObj);

      dlsULD.setUldTrolleyNumber(uld.getAssUldTrolleyNo());
      Optional<UldShipment> t = Optional.ofNullable(uldShipemnt);
      Double totalWeight;
      if (t.isPresent()) {
         totalWeight = actualWeight + uldShipemnt.getTareWeight().setScale(3).doubleValue();
         dlsULD.setTareWeight(uldShipemnt.getTareWeight());
         dlsULD.setRemarks(uldShipemnt.getRemarks());
         dlsULD.setUsedAsStandBy(uldShipemnt.isUsedAsStandBy());
         dlsULD.setUsedAsTrolley(uldShipemnt.isUsedAsTrolley());

         dlsULD.setUsedForPerishableContainer((uldShipemnt.getPhcIndicator() == 0) ? false : true);
         if (!StringUtils.isEmpty(uldShipemnt.getContentCode())) {
            contentCodeList.add(uldShipemnt.getContentCode());
         }
         dlsULD.setTrolleyInd(uldShipemnt.isTrolleyInd());
      } else {
         totalWeight = actualWeight;
         dlsULD.setUsedAsTrolley(true);
         dlsULD.setUsedForPerishableContainer(false);
         dlsULD.setTrolleyInd(true);
      }
      dlsULD.setManifestWeight(BigDecimal.valueOf(actualWeight));
      dlsULD.setActualWeight(BigDecimal.valueOf(0.0));
      dlsULD.setFlightSegmentId(buildUp.getSegmentId());
      dlsULD.setFlightKey(buildUp.getFlightKey());
      if (newLoad != null) {
         dlsULD.setDryIceWeight(newLoad.getDryIceWeight());
      } else {
         dlsULD.setDryIceWeight(BigDecimal.valueOf(0.0));
      }
      dlsULD.setFlightOriginDate(buildUp.getFlightOriginDate());
      dlsULD.setContentCode(contentCodeList);
      dlsULD.setHeightCode(load.getHeightCode());

      Integer loadShipId = dao.getLoadedShipmentInfoId(load);
      SHCS sh = new SHCS();
      sh.setLoadedShipmentInfoId(BigInteger.valueOf(loadShipId));
      List<SHCS> shcTemp = dao.getSHC(sh);
      shcTemp.forEach(i -> {
         DLSUldtrolleySHCinfo dlsSHC = new DLSUldtrolleySHCinfo();
         dlsSHC.setSpecialHandlingCode(i.getCode());
         shcsList.add(dlsSHC);
      });

      dlsULD.setShcs(shcsList);
   }

   private void insertSHCTag(LoadedShipment load) throws CustomException {
      List<SHCS> shcsList = new ArrayList<>();
      List<String> tempList = new ArrayList<>();
      List<ShipmentHouse> shipList = new ArrayList<>();

      Integer loadShipId = dao.getLoadedShipmentInfoId(load);

      if (!CollectionUtils.isEmpty(load.getShcList())) {
         if (load.getShcList().contains("CAO")) {
            validator.validateCaoShcCheck(load);
         }

         load.getShcList().forEach(shc -> {
            SHCS shcs = new SHCS();
            shcs.setLoadedShipmentInfoId(BigInteger.valueOf(loadShipId));
            shcs.setCode(shc);
            shcsList.add(shcs);
         });
         load.setLoadedShcList(shcsList);

         SHCS existSHC = new SHCS();
         existSHC.setLoadedShipmentInfoId(BigInteger.valueOf(loadShipId));
         List<SHCS> existSHCList = dao.getSHC(existSHC);
         shcsList.addAll(existSHCList);
         dao.deleteLoadShc(loadShipId);

         for (Iterator<SHCS> iterator = shcsList.iterator(); iterator.hasNext();) {
            SHCS shc = iterator.next();
            if (tempList.contains(shc.getCode())) {
               iterator.remove();
            }
            tempList.add(shc.getCode());
         }
         dao.insertSHC(shcsList);
      }

      if (!CollectionUtils.isEmpty(load.getShipHouseList()) && !load.isMailBagFlag()) {
         load.getShipHouseList().forEach(tag -> {
            ShipmentHouse house = new ShipmentHouse();
            house.setLoadedShipmentInfoId(BigInteger.valueOf(loadShipId));
            house.setNumber(tag.getNumber());
            String[] arr = tag.getType().split("_");
            house.setType(arr[0]);
            BigInteger newPieces = BigInteger.valueOf(Integer.parseInt(arr[1]));
            house.setPieces(newPieces);
            BigDecimal newWeight = BigDecimal.valueOf(Double.parseDouble(arr[2]));
            house.setWeight(newWeight);
            shipList.add(house);
         });

         ShipmentHouse ship = new ShipmentHouse();
         ship.setLoadedShipmentInfoId(BigInteger.valueOf(loadShipId));
         List<ShipmentHouse> shiphHouseList = dao.getTagNo(ship);
         shipList.addAll(shiphHouseList);
         dao.deleteLoadInventory(loadShipId);
         tempList.clear();

         for (Iterator<ShipmentHouse> iterator = shipList.iterator(); iterator.hasNext();) {
            ShipmentHouse sh = iterator.next();
            if (tempList.contains(sh.getNumber()) && tempList.contains(sh.getType())) {
               iterator.remove();
            }
            tempList.add(sh.getNumber());
            tempList.add(sh.getType());
         }
         dao.insertTagNumber(shipList);
      }
      if (load.isMailBagFlag() && !CollectionUtils.isEmpty(load.getShipHouseList())) {
         load.getShipHouseList().forEach(i -> {
            i.setLoadedShipmentInfoId(BigInteger.valueOf(loadShipId));
         });
         dao.insertTagNumber(load.getShipHouseList());
      }
   }

   /**
    * @param load
    * @throws CustomException
    */
   private void checkInventory(UldShipment uld, LoadedShipment load) throws CustomException {
      load.setIntialLocationPieces(load.getLocationPiecs());
      load.setIntialLocationWeight(load.getLocationWeight());

      BigInteger remaingPieces = BigInteger
            .valueOf(load.getLocationPiecs().longValue() - load.getMovePiecs().longValue());
      BigDecimal remainingWeights = BigDecimal.valueOf(
            load.getLocationWeight().setScale(2).doubleValue() - load.getMoveWeight().setScale(2).doubleValue());
      BigDecimal remainingDryIce = BigDecimal
            .valueOf(load.getDryIceWeight().setScale(3).doubleValue() - load.getMoveDryIce().setScale(2).doubleValue());
      load.setLocationPiecs(remaingPieces);
      load.setLocationWeight(remainingWeights);
      load.setDryIceWeight(remainingDryIce);

      BigDecimal actualWeightWeighed = dao.getWeightWeighed(load.getShipmentInventoryId());
      if (!ObjectUtils.isEmpty(actualWeightWeighed) && actualWeightWeighed.doubleValue() != 0.0) {
         load.setActuvalWeightWeighed(actualWeightWeighed);
      }

      if (remaingPieces.longValue() == 0 && remainingWeights.doubleValue() == 0.0) {
         dao.deleteInventorySHC(load.getShipmentInventoryId().intValue());
         dao.deleteInventoryHouse(load.getShipmentInventoryId().intValue());
         dao.deleteInventory(load.getShipmentInventoryId());
         manageNewInventory(uld, load);

      } else if (!ObjectUtils.isEmpty(load.getMovePiecs()) && !ObjectUtils.isEmpty(load.getMoveWeight())
            && load.getMovePiecs().longValue() != 0 && load.getMoveWeight().doubleValue() != 0.0) {
         dao.updateInventory(load);
         manageNewInventory(uld, load);
      }
   }

   /**
    * @param uld
    * @param load
    * @throws CustomException
    */
   private void manageNewInventory(UldShipment uld, LoadedShipment load) throws CustomException {

      LoadedShipment newLoad = new LoadedShipment();

      LoadedShipment existingInventory = dao.isNewInventoryExist(load);
      newLoad.setShipmentId(load.getShipmentId());

      newLoad.setShipmentLocation(uld.getAssUldTrolleyNo());
      newLoad.setWeighingId(load.getWeighingId());
      newLoad.setLocationPiecs(load.getMovePiecs());
      newLoad.setLocationWeight(load.getMoveWeight());
      newLoad.setDryIceWeight(load.getMoveDryIce());
      newLoad.setFlightId(uld.getFlightId());
      newLoad.setWarehouseLocation(load.getWarehouseLocation());
      if (StringUtils.isEmpty(load.getAssUldTrolleyNo())) {
         // Set it to the Shipment Number as bulk
         newLoad.setAssUldTrolleyNo(uld.getShipmentNumber());
      } else {
         newLoad.setAssUldTrolleyNo(load.getAssUldTrolleyNo());
      }

      if (!ObjectUtils.isEmpty(existingInventory)) {
         newLoad.setLocationPiecs(BigInteger
               .valueOf(existingInventory.getLocationPiecs().longValue() + newLoad.getLocationPiecs().longValue()));
         newLoad.setLocationWeight(BigDecimal.valueOf(
               existingInventory.getLocationWeight().doubleValue() + newLoad.getLocationWeight().doubleValue()));

         if (!ObjectUtils.isEmpty(load.getMoveDryIce()) && load.getMoveDryIce().doubleValue() != 0.0) {
            if (ObjectUtils.isEmpty(existingInventory.getDryIceWeight())) {
               newLoad.setDryIceWeight(load.getMoveDryIce());
            }
         }

         if (!ObjectUtils.isEmpty(load.getActuvalWeightWeighed())) {
            BigDecimal consumedWeight = new BigDecimal("0.0");
            newLoad.setActuvalWeightWeighed(bigDecimalUtils.calculateProportionalWeight(load.getIntialLocationPieces(),
                  load.getActuvalWeightWeighed(), consumedWeight, load.getMovePiecs()));
            newLoad.setActuvalWeightWeighed(BigDecimal.valueOf(existingInventory.getActuvalWeightWeighed().doubleValue()
                  + load.getActuvalWeightWeighed().doubleValue()));
         }

         dao.updateNewInventory(newLoad);
      } else {
         newLoad.setActuvalWeightWeighed(load.getActuvalWeightWeighed());
         dao.createInventory(newLoad);
      }

   }

   /**
    * @param load
    * @throws CustomException
    */
   private void updateLoadedRecord(LoadedShipment load) throws CustomException {
      LoadedShipment loadShip = dao.fetchLoadedPieceWeight(load);
      BigInteger pieces = loadShip.getLoadedPieces();
      BigDecimal weight = loadShip.getLoadedWeight().setScale(5);

      BigInteger addedPieces = BigInteger.valueOf(load.getMovePiecs().longValue() + pieces.longValue());
      BigDecimal addedWeight = BigDecimal
            .valueOf(load.getMoveWeight().setScale(5).doubleValue() + weight.setScale(5).doubleValue());
      load.setLoadedPieces(addedPieces);
      load.setLoadedWeight(addedWeight);
      dao.updateLoadShipment(load);
   }

   /**
    * @param comman
    * @throws CustomException
    *            Method to Load Shipment for external components
    */
   public void insertLoadingData(CommonLoadShipment comman) throws CustomException {
      BuildupLoadShipment buildup = new BuildupLoadShipment();
      buildup.setFlightKey(comman.getFlightKey());
      buildup.setFlightOriginDate(comman.getFlightOriginDate());
      buildup.setFlightId(comman.getFlightId());
      buildup.setSegmentId(comman.getFlightSegmentId());
      //buildup.setAssUldTrolleyNo(comman.get);
      List<UldShipment> uldShipList = new ArrayList<>();
      buildup.setLoadType("ULD");
      /*
       * converting common Load Shipment Object into BuildupLoadShipment Object to
       * call existing service for performing loading operation
       */
      for (BuildUpULD uld : comman.getUldList()) {
         for (UldShipment ship : uld.getShipmentList()) {
            buildup.setAssUldTrolleyNo(ship.getAssUldTrolleyNo());

            if (!comman.isEccShipmentFlag() && !comman.isMailBagFlag()) {
               List<LoadedShipment> invsList = dao.getCommonServiceInventory(ship.getShipmentId().intValue());
               invsList.forEach(i -> {
                  i.setMovePiecs(i.getLocationPiecs());
                  i.setMoveWeight(i.getLocationWeight());
                  i.setMoveDryIce(i.getDryIceWeight());
                  // done for amend uld trolley
                  if (comman.isFromAmendtrolley()) {
                     i.setAssUldTrolleyNo(ship.getAssUldTrolleyNo());
                     i.setFlightId(comman.getFlightIDNew());
                  }
                  // finish changes for amend uld trolley
               });
               ship.setLoadShipmentList(invsList);
               uldShipList.add(ship);
            } else if (comman.isMailBagFlag()) {
               buildup.setMailBagLoading(true);
               List<LoadedShipment> invsList = dao.getInventoryBymailBag(ship);
               invsList.forEach(i -> {

                  List<ShipmentHouse> mailbagList = null;
                  i.setAssUldTrolleyNo(ship.getAssUldTrolleyNo());
                  i.setMovePiecs(i.getLocationPiecs());
                  i.setMoveWeight(i.getLocationWeight());
                  i.setMoveDryIce(BigDecimal.valueOf(0.0));

                  ShipmentHouse house = new ShipmentHouse();
                  house.setShipmentInventoryId(i.getShipmentInventoryId());
                  house.setNumber(i.getNumber());

                  try {
                     mailbagList = dao.getMailBags(house);
                  } catch (CustomException e) {
                  }
                  if (!CollectionUtils.isEmpty(mailbagList)) {
                     i.setShipHouseList(mailbagList);
                     i.setMailBagFlag(true);
                  }
               });
               ship.setLoadShipmentList(invsList);
               uldShipList.add(ship);
            }
         }
      }
      // Invoke load shipment only if shipments have been selected
      if (!CollectionUtils.isEmpty(uldShipList)) {
         buildup.setLoadedShipmentList(uldShipList);

         // Load
         insertLoadShipment(buildup);
      }
   }

}
