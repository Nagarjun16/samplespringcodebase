package com.ngen.cosys.satssginterfaces.mss.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.AssignULDDAO;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;
import com.ngen.cosys.satssginterfaces.mss.model.DLS;
import com.ngen.cosys.satssginterfaces.mss.model.DLSPiggyBackInfo;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.validator.AssignULDValidator;

@Service
@Transactional
public class AssignULDServiceImpl implements AssignULDService {

   @Autowired
   private AssignULDDAO assignULDDAO;
   @Autowired
   private AssignULDValidator assignULDValidator;
   @Autowired
   private UpdateDLSService updateDLSService;
   private boolean validationSuccess;


   // Add single ULD/Trolley
   @Transactional(readOnly = false, rollbackFor = Throwable.class)
   public AssignULD assignUld(AssignULD uld) throws CustomException {
      if (ObjectUtils.isEmpty(uld.getUld().getUldTrolleyNo())) {
         assignULDValidator.checkIfAssignULDExists(uld);
         if (!uld.isErrorFlag()) {
            addULD(uld);
         }
         return uld;
      }
      assignULDValidator.checkIfAssignULDExists(uld);
      assignULDValidator.checkIfUldCarrierCompatible(uld);
      assignULDValidator.checkIfUldExistsInInventory(uld);
      assignULDValidator.checkPiggyBackUldIsExist(uld);
      if (!uld.isErrorFlag()) {
         addULD(uld);
      }
      return uld;
   }


   public AssignULD assignUldO(AssignULD uld) throws CustomException {
      addULD(uld);
      assignULDValidator.checkIfUldExistsInInventory(uld);
      if (uld.isWarningFlag()) {
         insertInventory(uld);
      }
      addToDLS(uld);
      return uld;
   }

   public void addToDLS(AssignULD uld) throws CustomException {
      DLS dls = new DLS();
      BigInteger flightId = assignULDDAO.getFlightID(uld);
      dls.setFlightId(flightId);
      DLSULD assignULD = new DLSULD();
      assignULD.setUldTrolleyNumber(uld.getUld().getUldTrolleyNo());
      assignULD.setTrolleyInd(uld.getUld().isTrolleyInd());
      assignULD.setTareWeight(uld.getUld().getTareWeight());
      assignULD.setPriorityOfLoading(uld.getUld().getPriority());
      String contentCode = uld.getUld().getContentCode();
      if (isUldLoaded(uld) == 0) {
         assignULD.setManual(true);
      }

      List<String> contentCodes = new ArrayList<>();
      if (!StringUtils.isEmpty(contentCode)) {
         contentCodes.add(contentCode);
      }
      assignULD.setContentCode(contentCodes);
      assignULD.setHeightCode(uld.getUld().getHeightCode());
      assignULD.setHandlingArea(uld.getUld().getHandlingArea());
      assignULD.setUsedForPerishableContainer(uld.getUld().isPhcFlag());
      assignULD.setUsedForExpressCourierContainer(uld.getUld().getEccFlag());
      assignULD.setUsedAsTrolley(uld.getUld().isTrolleyFlag());
      assignULD.setUsedAsStandBy(uld.getUld().isStandByFlag());
      assignULD.setRemarks(uld.getUld().getRemarks());
      assignULD.setFlightSegmentId(uld.getUld().getSegmentId());
      List<DLSPiggyBackInfo> piggyBackUldList = new ArrayList<>();
      if (!CollectionUtils.isEmpty(uld.getPiggyBackULDList())) {
         List<AssignULD> piggyBackAssUldList = new ArrayList<>(uld.getPiggyBackULDList());

         for (AssignULD piggyBackAssUld : piggyBackAssUldList) {
            DLSPiggyBackInfo piggyBackUld = new DLSPiggyBackInfo();
            piggyBackUld.setUldNumber(piggyBackAssUld.getUld().getUldTrolleyNo());
            piggyBackUldList.add(piggyBackUld);
         }
         assignULD.setPiggyBackUldList(piggyBackUldList);
      } else {
         assignULD.setPiggyBackUldList(new ArrayList<>());
      }

      List<DLSULD> ulds = new ArrayList<>();
      ulds.add(assignULD);
      dls.setUldTrolleyList(ulds);
      updateDLSService.insertUpdateDLS(dls);

   }

   private AssignULD addULD(AssignULD uld) throws CustomException {
      getPiggyBackUldTareWeight(uld.getPiggyBackULDList(), uld);
      assignULDDAO.addULD(uld);

      BigInteger uldId = assignULDDAO.getBulkUldID(uld);

      if (uld.getPiggyBackULDList() != null && !uld.getPiggyBackULDList().isEmpty()) {
         uld.getPiggyBackULDList().forEach(piggyBack -> {
            if (piggyBack.getFlagCRUD().equals(Action.CREATE.toString())) {
               uld.setFlagCRUD(Action.CREATE.toString());
            }
            piggyBack.setAssUldTrolleyId(uldId);
         });
         if (uld.getFlagCRUD().equals(Action.CREATE.toString())) {
            assignULDDAO.insertPiggyBackList(uld);
         }
      }
      if (!ObjectUtils.isEmpty(uld.getUld().getUldNumber())
            && !uld.getUld().getUldTrolleyNo().equalsIgnoreCase("BULK")
            && uld.isDlsInd()) {
         addToDLS(uld);
      }
      return uld;
   }


   private AssignULD updateULDV(AssignULD uld) throws CustomException {
      assignULDValidator.checkPiggyBackUldIsExist(uld);
      if (uld.isErrorFlag()) {
         validationSuccess = false;
      }
      if (!uld.getUld().isTrolleyInd()) {
         validateContentCode(uld);
      }
      return uld;
   }

   public AssignULD validateContentCode(AssignULD uld) throws CustomException {
      return uld;
   }


   // modify piggyback List
   public void modifyPiggyBackList(AssignULD uld) throws CustomException {
     /* AssignULD uldInsert = new AssignULD();
      AssignULD uldDelete = new AssignULD();
      AssignULD uldUpdate = new AssignULD();
      uldInsert.setAssUldTrolleyId(uld.getAssUldTrolleyId());
      uldDelete.setAssUldTrolleyId(uld.getAssUldTrolleyId());
      uldUpdate.setAssUldTrolleyId(uld.getAssUldTrolleyId());
      getPiggyBackUldTareWeight(uld.getPiggyBackULDList(), uld);
      List<AssignULD> insertPiggyBackList = uld.getPiggyBackULDList().stream()
            .filter((piggybackUld) -> "C".equalsIgnoreCase(piggybackUld.getFlagCRUD())).collect(Collectors.toList());

      uldInsert.setPiggyBackULDList(insertPiggyBackList);
      if (!insertPiggyBackList.isEmpty()) {
         assignULDDAO.insertPiggyBackList(uldInsert);
      }
      List<AssignULD> updatePiggyBackList = uld.getPiggyBackULDList().stream()
            .filter((piggybackUld) -> "U".equalsIgnoreCase(piggybackUld.getFlagCRUD())).collect(Collectors.toList());
      uldUpdate.setPiggyBackULDList(updatePiggyBackList);
      if (!updatePiggyBackList.isEmpty()) {
         assignULDDAO.updatePiggyBackList(uldUpdate);
      }
      List<AssignULD> deletePiggyBackList = uld.getPiggyBackULDList().parallelStream()
            .filter((piggybackUld) -> "D".equalsIgnoreCase(piggybackUld.getFlagCRUD())).collect(Collectors.toList());
      uldDelete.setPiggyBackULDList(deletePiggyBackList);
      if (!deletePiggyBackList.isEmpty()) {
         assignULDDAO.deleteSomePiggyBackList(uldDelete);
      }*/
   }



   // insert an ULD/Trolley into inventory
   @Override
   public AssignULD insertInventory(AssignULD uld) throws CustomException {
      assignULDDAO.insertUldInventory(uld);
      return uld;
   }

   private AssignULD getPiggyBackUldTareWeight(List<AssignULD> insertPiggyBackList, AssignULD assignUld)
         throws CustomException {
      if (!CollectionUtils.isEmpty(insertPiggyBackList) && assignUld.isDlsInd()) {

         for (AssignULD piggyUld : insertPiggyBackList) {
            if (Action.CREATE.toString().equalsIgnoreCase(piggyUld.getFlagCRUD())) {
               getTareWeight(piggyUld);

               if (piggyUld.getUld().getTareWeight() != null) {
                  if (assignUld.getUld().getTareWeight() == null) {
                     assignUld.getUld().setTareWeight(BigDecimal.ZERO);
                  }
                  assignUld.getUld()
                        .setTareWeight(assignUld.getUld().getTareWeight().add(piggyUld.getUld().getTareWeight()));
               }
            } else if (Action.DELETE.toString().equalsIgnoreCase(piggyUld.getFlagCRUD())) {
               getTareWeight(piggyUld);
               assignUld.getUld()
                     .setTareWeight(assignUld.getUld().getTareWeight().subtract(piggyUld.getUld().getTareWeight()));
            }

         }
      }
      return assignUld;
   }

   private int countDigitsInString(String string) {
      int counter = 0;
      if (!StringUtils.isEmpty(string)) {
         for (char c : string.toCharArray()) {
            if (c >= '0' && c <= '9') {
               ++counter;
            }
         }
      }
      return counter;
   }



   void checkBeforeDelete(List<AssignULD> assignUldList) throws CustomException {
      for (AssignULD uld : assignUldList) {
         Integer count = isUldLoaded(uld);
         if (count != null && count.intValue() > 0) {
            validationSuccess = false;
            uld.addError("uld.is.already.loaded", "uld.uldTrolleyNo", ErrorType.ERROR);
         }
      }
   }

   @Override
   public Integer isUldLoaded(AssignULD uld) throws CustomException {
      return assignULDDAO.isUldLoaded(uld);
   }
   
   @Override
   public AssignULD getTareWeight(AssignULD uld) throws CustomException {
      uld.setUldNumberLength(countDigitsInString(uld.getUld().getUldTrolleyNo()));

      // getting contourCode
      if (uld.getUld().getHeightCode() == null) {
         String contourCode = assignULDDAO.getContourCode(uld);
         if (contourCode != null) {
            uld.getUld().setHeightCode(contourCode);
         } else {
            contourCode = assignULDDAO.getContourCodeFfromUldCharacterStics(uld);
            uld.getUld().setHeightCode(contourCode);
         }
      }

      // getting the tareWeight
      if (uld.getUld().getTareWeight() == null) {
         BigDecimal uldTareWeight = assignULDDAO.getTareWeight(uld.getUld());
         if (uldTareWeight == null) {
            uldTareWeight = new BigDecimal(0);
         }
         uld.getUld().setTareWeight(uldTareWeight);
      }

      // get actual Weight
      BigDecimal actualGrossWeight = assignULDDAO.getActualGrossWeight(uld.getUld());
      if (actualGrossWeight != null) {
         uld.getUld().setActualGrossWeight(actualGrossWeight);
      }

      return uld;
   }

}
