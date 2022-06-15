package com.ngen.cosys.satssginterfaces.mss.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.UpdateDLSDAO;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;
import com.ngen.cosys.satssginterfaces.mss.model.DLS;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;
import com.ngen.cosys.satssginterfaces.mss.model.DlsContentCode;
import com.ngen.cosys.satssginterfaces.mss.model.Segment;
import com.ngen.cosys.satssginterfaces.mss.model.ULDIInformationDetails;

@Service
public class UpdateDLSServiceImpl implements UpdateDLSService {

   @Autowired
   UpdateDLSDAO dao;
   @Autowired
   private AssignULDService assignULDService;

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public DLS insertUpdateDLS(DLS dls) throws CustomException {
      if (!CollectionUtils.isEmpty(dls.getUldTrolleyList())) {
         for (DLSULD dlsUld : dls.getUldTrolleyList()) {
            dlsUld.setContentCodeList(prepareContentCode(dlsUld.getContentCode()));
         }
      }
      dao.insertUpdateDLS(dls);
      // insertSystemGenratedOsi(dls);

      return dls;
   }

   private List<DlsContentCode> prepareContentCode(List<String> contentCode) {
      final ArrayList<DlsContentCode> ccode = new ArrayList<>();
      if (!CollectionUtils.isEmpty(contentCode)) {
         contentCode.forEach(cc -> {
            if (!StringUtils.isEmpty(cc)) {
               DlsContentCode c = new DlsContentCode();
               c.setCode(cc);
               c.setCreatedBy("SYSADMIN");
               ccode.add(c);
            }
         });
      }
      return ccode;
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public DLS updateUld(DLS dls) throws CustomException {
      if (Action.CREATE.toString().equalsIgnoreCase(dls.getFlagCRUD())) {
         dao.insertDLS(dls);
         for (DLSULD uld : dls.getUldTrolleyList()) {
            uld.setDlsId(dls.getDlsId());
            uld.setFlightId(dls.getFlightId());
            uld.setHandlingArea(dls.getTerminal());
            uld.setContentCodeList(prepareContentCode(uld.getContentCode()));
            if (Action.CREATE.toString().equalsIgnoreCase(uld.getFlagCRUD())) {
               dao.insertUld(uld);
               // insertSystemGenratedOsi(dls);
            }

            // Assign ULD/Trolley
            this.assignULDTrolleyToFlight(uld);
         }
      } else if (Action.UPDATE.toString().equalsIgnoreCase(dls.getFlagCRUD())) {

         for (DLSULD uld : dls.getUldTrolleyList()) {
            uld.setDlsId(dls.getDlsId());
            uld.setFlightId(dls.getFlightId());
            uld.setHandlingArea(dls.getTerminal());
            uld.setContentCodeList(prepareContentCode(uld.getContentCode()));
            // addPiggyBackUldTareWeight(uld);
            if (Action.CREATE.toString().equalsIgnoreCase(uld.getFlagCRUD())) {
               dao.insertUld(uld);
            } else {
               dao.updateUld(uld);
            }
            // insertSystemGenratedOsi(dls);
            // Assign ULD/Trolley
            this.assignULDTrolleyToFlight(uld);
         }

      } else if (Action.DELETE.toString().equalsIgnoreCase(dls.getFlagCRUD())) {
         dao.deleteUldTrollyList(dls.getUldTrolleyList());
         // insertSystemGenratedOsi(dls);
      }
      return dls;
   }

   private void assignULDTrolleyToFlight(DLSULD dlsUld) throws CustomException {
      // call AssignULD
      // Check for ULD/Trolley
      AssignULD assUld = new AssignULD();
      assUld.setFlightKey(dlsUld.getFlightKey());
      assUld.setFlightOriginDate(dlsUld.getFlightOriginDate());

      Segment seg = new Segment();
      seg.setSegmentId(dlsUld.getFlightSegmentId());
      assUld.setSegment(seg);

      ULDIInformationDetails uld = new ULDIInformationDetails();
      uld.setSegmentId(dlsUld.getFlightSegmentId());
      uld.setUldTrolleyNo(dlsUld.getUldTrolleyNumber());
      if (!CollectionUtils.isEmpty(dlsUld.getContentCode())) {
         uld.setContentCode(dlsUld.getContentCode().get(0));
      }
      if (!StringUtils.isEmpty(dlsUld.getHeightCode())) {
         uld.setHeightCode(dlsUld.getHeightCode());
      }
      if (!StringUtils.isEmpty(dlsUld.getRemarks())) {
         uld.setRemarks(dlsUld.getRemarks());
      }
      if (dlsUld.getTareWeight() != null) {
         uld.setTareWeight(dlsUld.getTareWeight());
      }
      uld.setActualGrossWeight(dlsUld.getActualWeight());
      if (!dlsUld.getTrolleyInd()) {
         // ForPiggyBack Parameter Set
         List<AssignULD> assUldForPiggyBackList = new ArrayList<>();
         dlsUld.getPiggyBackUldList().forEach(piggyBack -> {
            AssignULD assUldForPiggyBack = new AssignULD();
            ULDIInformationDetails uldforPiggyBack = new ULDIInformationDetails();
            uldforPiggyBack.setUldTrolleyNo(piggyBack.getUldNumber());
            assUldForPiggyBack.setUld(uldforPiggyBack);
            assUldForPiggyBackList.add(assUldForPiggyBack);
            if (Action.CREATE.toString().equalsIgnoreCase(piggyBack.getFlagCRUD())) {
               assUldForPiggyBack.setFlagCRUD(Action.CREATE.toString());
            }
         });
         assUld.setPiggyBackULDList(assUldForPiggyBackList);
         // ForPiggyBack Parameter Set End
      }
      assUld.setUld(uld);
      assUld.setDlsInd(false);
      assignULDService.assignUld(assUld);
   }

}
