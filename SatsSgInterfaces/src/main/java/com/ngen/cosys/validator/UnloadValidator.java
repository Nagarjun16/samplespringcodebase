package com.ngen.cosys.validator;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.UnloadShipmentDAO;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentInventory;
import com.ngen.cosys.satssginterfaces.mss.model.UnloadShipmentRequest;

@Component
public class UnloadValidator {

   @Autowired
   private UnloadShipmentDAO unloadShipmentDAOImpl;

   public void unloadValidator(UnloadShipmentRequest shipment) throws CustomException {

      List<UnloadShipment> unloadShipmentList = shipment.getUnloadShipments();
      if (!validatePieceWeight(unloadShipmentList)) {
         shipment.setUnloadShipments(unloadShipmentList);
         shipment.setFlagError(true);
      }

      // shipment location validation
      if (!validateShipmentLocation(unloadShipmentList)) {
         shipment.setUnloadShipments(unloadShipmentList);
         shipment.setFlagError(true);
      }

      // house info validation
      if (!validateHouseInfo(unloadShipmentList)) {
         shipment.setUnloadShipments(unloadShipmentList);
         shipment.setFlagError(true);
      }

      // Special handling code check
      if (!validateSpecialHandlingCode(unloadShipmentList)) {
         shipment.setUnloadShipments(unloadShipmentList);
         shipment.setFlagError(true);
      }
      return;
   }

   private boolean validatePieceWeight(List<UnloadShipment> unloadShipmentList) {
      boolean vallid = true;
      for (UnloadShipment shpment : unloadShipmentList) {
         if (shpment.getLoadedWeight().compareTo(shpment.getUnloadWeight()) < 0) {
            //
            shpment.getShpmtInventoryList().get(0).addError("invalid.weight", "", ErrorType.ERROR);
            vallid = false;
         }
         if (shpment.getLoadedPieces().compareTo(shpment.getUnloadPieces()) < 0) {

            shpment.getShpmtInventoryList().get(0).addError("invalid.pieces", "", ErrorType.ERROR);
            vallid = false;
         }
      }
      return vallid;
   }

   private boolean validateShipmentLocation(List<UnloadShipment> unloadShipmentList) throws CustomException {
      Boolean validator = true;
      for (UnloadShipment shpment : unloadShipmentList) {
         for (UnloadShipmentInventory inv : shpment.getShpmtInventoryList()) {
            int count = 0;
            if (!StringUtils.isEmpty(inv.getShipmentLocation())) {
               inv.setShipmentLocation(inv.getShipmentLocation().trim());
               if (inv.getShipmentLocation().equalsIgnoreCase(shpment.getAssUldTrolleyNumber())) {
                  List<UnloadShipment> unldList = unloadShipmentList.stream()
                        .filter(
                              item -> item.getAssUldTrolleyNumber().equalsIgnoreCase(shpment.getAssUldTrolleyNumber()))
                        .collect(Collectors.toList());
                  // checking whether ULD is loaded with some other shipment
                  count = unloadShipmentDAOImpl.getUnloadUldsCount(shpment);
                  if (unldList.size() != count) {
                     validator = false;
                     inv.addError("","", ErrorType.ERROR);
                  } else {
                     if (!checkUnloadWeight(unldList)) {
                        inv.addError("","", ErrorType.ERROR);
                        validator = false;
                     }

                  }
               } else {

                  inv.setFlight(shpment.getFlight());
                  if (unloadShipmentDAOImpl.valiadateShipmentLocation(inv)) {
                     validator = false;
                     inv.addError("","", ErrorType.ERROR);

                  }
               }
            }
         }

      }
      return validator;
   }

   private boolean checkUnloadWeight(List<UnloadShipment> unldList) throws CustomException {
      boolean vallid = true;
      BigInteger loadedPieces = new BigInteger("0");
      BigInteger unloadloadedPieces = new BigInteger("0");
      for (UnloadShipment uld : unldList) {
         loadedPieces = uld.getLoadedPieces().add(loadedPieces);
         unloadloadedPieces = uld.getUnloadPieces().add(unloadloadedPieces);
      }
      if (unloadloadedPieces.compareTo(loadedPieces) < 0) {
         vallid = false;
      }
      return vallid;
   }

   private boolean validateHouseInfo(List<UnloadShipment> unloadShipmentList) throws CustomException {
      for (UnloadShipment shpment : unloadShipmentList) {
         if (!CollectionUtils.isEmpty(shpment.getHouseNumbers())) {
            for (UnloadShipmentInventory inv : shpment.getShpmtInventoryList()) {

               if (CollectionUtils.isEmpty(inv.getHouseNumbers())) {

                  inv.addError("", "hoseinfo", ErrorType.ERROR);
                  return false;
               } else {
                  inv.setLoadedShipmentInfoId(shpment.getLoadedShipmentInfoId());
                  if (!unloadShipmentDAOImpl.getLoadShipmentHousePieceCount(inv)) {
                     inv.addError("", "hoseinfo", ErrorType.ERROR);
                     return false;
                  }

               }
            }
         }
      }
      return true;
   }

   private boolean validateSpecialHandlingCode(List<UnloadShipment> unloadShipmentList) throws CustomException {
      boolean validator = true;
      for (UnloadShipment shipmemt : unloadShipmentList) {
         for (UnloadShipmentInventory inv : shipmemt.getShpmtInventoryList()) {
            if (!CollectionUtils.isEmpty(inv.getShcCodes())
                  && (inv.getShcCodes().size() != unloadShipmentDAOImpl.validSpecialhandlingCodeList(inv))) {
               validator = false;
               inv.addError("", "shcCodes", ErrorType.ERROR);
               return validator;

            }

         }
      }

      return validator;
   }

}