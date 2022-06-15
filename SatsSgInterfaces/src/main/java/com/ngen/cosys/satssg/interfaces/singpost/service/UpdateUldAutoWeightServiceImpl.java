package com.ngen.cosys.satssg.interfaces.singpost.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.dao.UpdateUldAutoWeightDao;
import com.ngen.cosys.satssg.interfaces.singpost.model.UldAutoWeightModel;

@Service
public class UpdateUldAutoWeightServiceImpl implements UpdateUldAutoWeightService {

   @Autowired
   private UpdateUldAutoWeightDao updateUldAutoWeightDao;

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
   public Integer updateUldAutoWeight(UldAutoWeightModel uldAutoWeightModel) throws CustomException {

      // 1. Store the ICS data
      this.updateUldAutoWeightDao.insertUpdateUldAutoWeightRequest(uldAutoWeightModel);

      // 2. Store the audit trail against ULD
      HashMap<String, Object> auditMap = new HashMap<>();
      auditMap.put("eventActor", uldAutoWeightModel.getStaffLoginId()+"(ICS)");
      auditMap.put("eventAction", "ADD");
      auditMap.put("entityType", NgenAuditEntityType.ULD);
      auditMap.put("eventName", "UpdateUldAutoWeight");
      auditMap.put("entityValue", uldAutoWeightModel.getContainerId());

      // Construct the EventValue
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonStr = null;
      try {
         HashMap<String, Object> auditEventMap = new HashMap<>();
         auditEventMap.put("eventValue", uldAutoWeightModel);
         jsonStr = objectMapper.writeValueAsString(auditEventMap);
      } catch (JsonProcessingException e) {
         // Do nothing
      }
      auditMap.put("eventValue", jsonStr);
      this.updateUldAutoWeightDao.logMasterAuditData(auditMap);

      // 3. Update the Weight if it is assigned to an flight
      return updateUldAutoWeightDao.updateUldAutoWeight(uldAutoWeightModel);
   }

}