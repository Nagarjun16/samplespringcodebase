package com.ngen.cosys.satssginterfaces.mss.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.UpdateIndicator;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.UnloadShipmentDAO;
import com.ngen.cosys.satssginterfaces.mss.model.DLS;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentHouse;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentInventory;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentRequest;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentSHCs;
import com.ngen.cosys.utils.BigDecimalUtils;
import com.ngen.cosys.utils.IntegerUtils;
import com.ngen.cosys.validator.UnloadValidator;

@Service
public class UnloadShipmentServiceImpl implements UnloadShipmentService {

   @Autowired
   UnloadShipmentDAO unloadShipmentDAOImpl;
   @Autowired
   BigDecimalUtils bigDecimalUtils;
   @Autowired
   IntegerUtils integerUtils;
   @Autowired
   UpdateDLSService updateDLSService;

   @Autowired
   UnloadValidator unloadValidator;


   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.buildup.service.UnloadShipmentService#searchForFlight(
    * com.ngen.cosys.export.buildup.model.UnloadShipmentSearch)
    */
   /*@Override
   public Flight searchForFlight(Flight flight) throws CustomException {
      Flight flightdata = unloadShipmentDAOImpl.searchForFlight(flight);
      if (!ObjectUtils.isEmpty(flightdata)) {
         if (!unloadShipmentDAOImpl.isLoadDone(flightdata)) {
            // throw new
            // CustomException(UnloadShipmentError.NOLOAD.getErrorId(),UnloadShipmentConstants.UNLOADSHIPMENTFORM.getConstantId(),ErrorType.ERROR);
            flightdata.addError(UnloadShipmentError.NOLOAD.getErrorId(),
                  UnloadShipmentConstants.UNLOADSHIPMENTFORM.getConstantId(), ErrorType.ERROR);
         }
      }
      return flightdata;
   }*/

   /*private List<UnloadShipment> shipmentDetails(UnloadShipmentSearch uld) throws CustomException {
      List<UnloadShipment> unloadShipmentList = unloadShipmentDAOImpl.getShipmentInfo(uld);
      unloadShipmentList.forEach(item -> {
         String shc = item.getShcCodes().stream().map(Object::toString).collect(Collectors.joining(" "));
         item.setShcs(shc);
      });
      return unloadShipmentList;
   }*/

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.export.buildup.service.UnloadShipmentService#
    * getShipmentDetails(com.ngen.cosys.export.buildup.model.UnloadShipmentSearch)
    */
   /*@Override
   public List<UnloadShipment> getShipmentDetails(UnloadShipmentSearch uld) throws CustomException {
      return shipmentDetails(uld);
   }*/

   private void addPieceWeight(List<UnloadShipment> unloadShipmentList) {
      unloadShipmentList.forEach(item -> item.getShpmtInventoryList().forEach(item2 -> {
         if ((null == item.getUnloadPieces() || BigInteger.ZERO.equals(item.getUnloadPieces()))
               && (null == item.getUnloadWeight() || BigDecimal.ZERO.equals(item.getUnloadWeight()))) {
            item.setUnloadPieces(item2.getPieces());
            item.setUnloadWeight(item2.getWeight());

         } else {
            item.setUnloadPieces(item2.getPieces().add(item.getUnloadPieces()));
            item.setUnloadWeight(item2.getWeight().add(item.getUnloadWeight()));
         }
      }));

   }

   /*private void updateHouseInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
      List<UnloadShipment> updatehouseInfoList = new ArrayList<>();
      for (UnloadShipment shipment : unloadShipmentList) {
         if (!CollectionUtils.isEmpty(shipment.getHouseNumbers())) {
            updatehouseInfoList.add(shipment);
         }
      }

      for (UnloadShipment shpInv : updatehouseInfoList) {
         for (UnloadShipmentInventory inv : shpInv.getShpmtInventoryList()) {
            List<ShipmentHouse> shipmentHouseInfoList = new ArrayList<>();
            for (String housenum : inv.getHouseNumbers()) {
               ShipmentHouse houseInfo = new ShipmentHouse();
               houseInfo.setShipmentInventoryId(inv.getShipmentInventoryId());
               houseInfo.setNumber(housenum);
               houseInfo.setShipmentId(inv.getShipmentId());
               houseInfo.setShipmentHouseId(unloadShipmentDAOImpl.getShipmentHouseId(houseInfo));
               houseInfo.setPieces(inv.getPieces());
               houseInfo.setWeight(inv.getWeight());
               houseInfo.setTenantId(inv.getTenantId());
               shipmentHouseInfoList.add(houseInfo);
            }
            if (!CollectionUtils.isEmpty(shipmentHouseInfoList)) {
               unloadShipmentDAOImpl.createShipmentInventoryHouseInfo(shipmentHouseInfoList);
            }
         }

      }

      // Update House information
      unloadShipmentDAOImpl.updateHouseInfo(updatehouseInfoList);
   }*/

   /*private void upadteShipmentInventoryInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
      // Update House information
      for (UnloadShipment shipment : unloadShipmentList) {
         // update or save in shipment Inventory table
         for (UnloadShipmentInventory inventory : shipment.getShpmtInventoryList()) {
            // update inventory in case of unload and loaded pieces are equal
            if (unloadShipmentDAOImpl.deleteShipmentInvetory(inventory) == 0) {
               unloadShipmentDAOImpl.updateInventoryforExistedFlight(inventory);
            }
            inventory.setStatus(unloadShipmentDAOImpl.getLocationCode(inventory));
            if (unloadShipmentDAOImpl.updateShipmentInvetory(inventory) == 0) {
               unloadShipmentDAOImpl.addShipmentInventory(inventory);
            } else {
               inventory.setShipmentInventoryId(unloadShipmentDAOImpl.getShipmentInventoryId(inventory));
            }
            if (!CollectionUtils.isEmpty(inventory.getShcCodes())) {
               List<UnloadShipmentSHCs> shcList = new ArrayList<>();
               for (String shc : inventory.getShcCodes()) {
                  inventory.setCode(shc);
                  if (!unloadShipmentDAOImpl.isShcExists(inventory)) {
                     UnloadShipmentSHCs spcHandlnCode = new UnloadShipmentSHCs();
                     spcHandlnCode.setShipmentInventoryId(inventory.getShipmentInventoryId());
                     spcHandlnCode.setCode(shc);
                     shcList.add(spcHandlnCode);
                  }
               }
               if (!CollectionUtils.isEmpty(shcList)) {
                  unloadShipmentDAOImpl.insertShipmentInventorySHCs(shcList);
               }
            }

         }
      }

   }*/

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.export.buildup.service.UnloadShipmentService#unloadShipment(
    * java.util.List)
    */

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public UnloadShipmentRequest unloadShipment(UnloadShipmentRequest unloadShipment) throws CustomException {

      List<UnloadShipment> unloadShipmentList = unloadShipment.getUnloadShipments();
      UnloadShipmentRequest unloadShipmentRequest = new UnloadShipmentRequest();
      unloadShipmentRequest.setFlagUpdate(UpdateIndicator.NO.toString());
      addPieceWeight(unloadShipmentList);
      // adding unload pieces and weight
      if (unloadShipment.isFromUnload()) {
         unloadValidator.unloadValidator(unloadShipment);
         if (unloadShipment.isFlagError()) {
            return unloadShipment;
         }
      }
      // setting the shipment ID to unload shipment list
      unloadShipmentList.forEach(item -> item.getShpmtInventoryList().forEach(item2 -> {
         item2.setShipmentId(item.getShipmentId());
         item2.setFlight(item.getFlight());
         item2.setAssUldTrolleyNumber(item.getAssUldTrolleyNumber());
      }));
      // Update Load shipment
      unloadShipmentDAOImpl.reduceLoadShipment(unloadShipmentList);
      // Update Inventory information
      upadteShipmentInventoryInfo(unloadShipmentList);
      // Update House information
      updateHouseInfo(unloadShipmentList);
      // Update Manifest shipment Info
      unloadShipmentDAOImpl.updateManifestShpmtInfo(unloadShipmentList);
      // Update DLSULDTrolly information
      unloadShipmentDAOImpl.updateDLSULDTrolly(unloadShipmentList);
      UnloadShipment loadedId = unloadShipmentDAOImpl.getLoadedShipmentIdForZeroPieces(unloadShipmentList.get(0));
      // delete from LoadShipmentSHCInfo
      unloadShipmentDAOImpl.deleteLoadShipmentSHCInfo(unloadShipmentList);
      // delete deleteLoadShipmentHouseInfo
      unloadShipmentDAOImpl.deleteLoadShipmentHouseInfo(unloadShipmentList);
      // delete from unload shipment id piece/weight =0
      unloadShipmentDAOImpl.deleteLoadShipment(unloadShipmentList);
      if (unloadShipment.isFromAmendULD()) {
         // delete from AssignedULDTrolleyToFlightPiggyBackInfo
         unloadShipmentDAOImpl.deleteAssignedULDTrolleyToFlightPiggyBackInfo(unloadShipmentList);
         // delete from assigned ULD trolley if it is empty
         unloadShipmentDAOImpl.deleteAssingedULDTrolly(unloadShipmentList);

      }
      // delete from manifest shipment Info
      unloadShipmentDAOImpl.deleteManifestShpmtShcs(unloadShipmentList);
      // delete from manifest House Info
      unloadShipmentDAOImpl.deleteManifestShpmtHouseInfo(unloadShipmentList);
      // delete manifest shipment Info
      unloadShipmentDAOImpl.deleteManifestShpmtInfo(unloadShipmentList);
      // delete from ManifestULDInfo
      unloadShipmentDAOImpl.deleteManifestULDInfo(unloadShipmentList);
      // delete from ManifestInfo
      unloadShipmentDAOImpl.deleteManifestInfo(unloadShipmentList);
      // fetch DLSID's where piece/weight count equal to zero
      List<DLSULD> dlsULDList = unloadShipmentDAOImpl.getDLSId(unloadShipment);
      if (!CollectionUtils.isEmpty(dlsULDList)) {
         DLS dls = new DLS();
         dls.setFlightId(unloadShipmentList.get(0).getFlight().getFlightId());
         dls.setUldTrolleyList(dlsULDList);
         dlsULDList.forEach(uld -> uld.setFlagCRUD(Action.DELETE.toString()));
         dls.setFlagCRUD(Action.DELETE.toString());
         updateDLSService.updateUld(dls);
      }
      unloadShipmentRequest.setFlagUpdate(UpdateIndicator.YES.toString());
      unloadShipmentRequest.setUnloadShipments(unloadShipmentList);

      return unloadShipmentRequest;
   }
   
   private void upadteShipmentInventoryInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
      // Update House information
      for (UnloadShipment shipment : unloadShipmentList) {
         // update or save in shipment Inventory table
         for (UnloadShipmentInventory inventory : shipment.getShpmtInventoryList()) {
            // update inventory in case of unload and loaded pieces are equal
            if (unloadShipmentDAOImpl.deleteShipmentInvetory(inventory) == 0) {
               unloadShipmentDAOImpl.updateInventoryforExistedFlight(inventory);
            }
            inventory.setStatus(unloadShipmentDAOImpl.getLocationCode(inventory));
            if (unloadShipmentDAOImpl.updateShipmentInvetory(inventory) == 0) {
               unloadShipmentDAOImpl.addShipmentInventory(inventory);
            } else {
               inventory.setShipmentInventoryId(unloadShipmentDAOImpl.getShipmentInventoryId(inventory));
            }
            if (!CollectionUtils.isEmpty(inventory.getShcCodes())) {
               List<UnloadShipmentSHCs> shcList = new ArrayList<>();
               for (String shc : inventory.getShcCodes()) {
                  inventory.setCode(shc);
                  if (!unloadShipmentDAOImpl.isShcExists(inventory)) {
                     UnloadShipmentSHCs spcHandlnCode = new UnloadShipmentSHCs();
                     spcHandlnCode.setShipmentInventoryId(inventory.getShipmentInventoryId());
                     spcHandlnCode.setCode(shc);
                     shcList.add(spcHandlnCode);
                  }
               }
               if (!CollectionUtils.isEmpty(shcList)) {
                  unloadShipmentDAOImpl.insertShipmentInventorySHCs(shcList);
               }
            }

         }
      }

   }
   
   private void updateHouseInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
      List<UnloadShipment> updatehouseInfoList = new ArrayList<>();
      for (UnloadShipment shipment : unloadShipmentList) {
         if (!CollectionUtils.isEmpty(shipment.getHouseNumbers())) {
            updatehouseInfoList.add(shipment);
         }
      }

      for (UnloadShipment shpInv : updatehouseInfoList) {
         for (UnloadShipmentInventory inv : shpInv.getShpmtInventoryList()) {
            List<ShipmentHouse> shipmentHouseInfoList = new ArrayList<>();
            for (String housenum : inv.getHouseNumbers()) {
               ShipmentHouse houseInfo = new ShipmentHouse();
               houseInfo.setShipmentInventoryId(inv.getShipmentInventoryId());
               houseInfo.setNumber(housenum);
               houseInfo.setShipmentId(inv.getShipmentId());
               houseInfo.setShipmentHouseId(unloadShipmentDAOImpl.getShipmentHouseId(houseInfo));
               houseInfo.setPieces(inv.getPieces());
               houseInfo.setWeight(inv.getWeight());
               houseInfo.setTenantId(inv.getTenantAirport());
               shipmentHouseInfoList.add(houseInfo);
            }
            if (!CollectionUtils.isEmpty(shipmentHouseInfoList)) {
               unloadShipmentDAOImpl.createShipmentInventoryHouseInfo(shipmentHouseInfoList);
            }
         }

      }

      // Update House information
      unloadShipmentDAOImpl.updateHouseInfo(updatehouseInfoList);
   }

   /*@Override
   public UnloadShipment getUnLoadWeight(UnloadShipment unloadShipment) throws CustomException {
      if (unloadShipment.getLoadedPieces().compareTo(unloadShipment.getUnloadPieces()) < 0) {
         unloadShipment.setFlagError(true);
         return unloadShipment;
      }
      BigDecimal consumedWeight = new BigDecimal("0.0");
      unloadShipment.setUnloadWeight(bigDecimalUtils.calculateProportionalWeight(unloadShipment.getLoadedPieces(),
            unloadShipment.getLoadedWeight(), consumedWeight, unloadShipment.getUnloadPieces()));
      return unloadShipment;
   }*/

   // common service for Amend ULD and offload ULD
   /*@Override
   public UnloadShipmentRequest unloadShipmentInfo(UnloadShipmentRequest shpmentdata) throws CustomException {
      if (shpmentdata.isFromAmendULD()) {
         // preparing Inventory for Amend ULD
         for (UnloadShipment unload : shpmentdata.getUnloadShipments()) {
            List<UnloadShipmentInventory> inventoryList = new ArrayList<>();
            UnloadShipmentInventory inventory = new UnloadShipmentInventory();
            inventory.setPieces(unload.getLoadedPieces());
            inventory.setWeight(unload.getLoadedWeight());
            inventory.setShipmentLocation(unload.getAssUldTrolleyNumber());
            inventory.setReferenceDetails("UNLOADED");
            inventoryList.add(inventory);
            unload.setShpmtInventoryList(inventoryList);
         }
      } else {
         // Offload
         for (UnloadShipment unload : shpmentdata.getUnloadShipments()) {
            for (UnloadShipmentInventory inventory : unload.getShpmtInventoryList()) {
               inventory.setReferenceDetails("UNLOADED");
            }
         }
      }

      shpmentdata.setFromUnload(false);
      UnloadShipmentRequest unloadShipmentResponse = unloadShipment(shpmentdata);
      return unloadShipmentResponse;
   }*/

   // Mobile service to get flight Details by ULD
   /*@Override
   public FlightDetails getFlightDetailsByULD(UnloadShipmentSearch uld) throws CustomException {

      return unloadShipmentDAOImpl.getFlightDetailsByULD(uld);
   }*/

   // Mobile service to get flight Details by ShipmentNumber
   /*@Override
   public List<FlightDetails> getFlightDetailsByShipmentNumber(UnloadShipmentSearch shipmentDetail)
         throws CustomException {
      List<FlightDetails> flightDetail = unloadShipmentDAOImpl.getFlightDetailsByShipmentNumber(shipmentDetail);
      
       * if (flightDetail == null) { throw new
       * CustomException(UnloadShipmentError.SHIPMENT_NOTLOADED.getErrorId(),
       * "shipmentNumber", ErrorType.ERROR); }
       
      return flightDetail;
   }*/

   // Mobile service to get flight and Shipment Details
  /* @Override
   public UnloadShipmentRequest getShipmentInfoAndFlightDetails(FlightDetails loadedShipmentInfo)
         throws CustomException {
      UnloadShipmentSearch loadedShipmentData = new UnloadShipmentSearch();
      loadedShipmentData.setFlight(loadedShipmentInfo.getFlight());
      loadedShipmentData.setReason(loadedShipmentInfo.getReason());
      UnloadShipmentRequest searchResult = unloadShipmentDAOImpl.searchFlightDetails(loadedShipmentInfo);
      if (!ObjectUtils.isEmpty(loadedShipmentInfo.getShipment())) {
         searchResult.setShipmentDetails(unloadShipmentDAOImpl.getBookedPiecesAndWeight(loadedShipmentInfo));
         loadedShipmentData.setShipmentNumber(loadedShipmentInfo.getShipment().getShipmentNumber());
         loadedShipmentData.setShipmentDate(loadedShipmentInfo.getShipment().getShipmentDate());
      } else {
         searchResult.setAssUldTrolleyNo(loadedShipmentInfo.getUld().getAssUldTrolleyNo());
         loadedShipmentData.setUldNumber(loadedShipmentInfo.getUld().getAssUldTrolleyNo());
      }
      searchResult.setUnloadShipments(shipmentDetails(loadedShipmentData));
      return searchResult;
   }*/

}
