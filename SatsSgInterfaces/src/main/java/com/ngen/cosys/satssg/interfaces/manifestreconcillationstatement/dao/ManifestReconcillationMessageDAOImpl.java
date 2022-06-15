package com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.model.ManifesrReconciliationStatementModel;

@Repository("ManifestReconcillationMessageDAO")
public class ManifestReconcillationMessageDAOImpl extends BaseDAO implements ManifestReconcillationMessageDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
   public void addAckInfo(ManifesrReconciliationStatementModel addCustomMRSShipmentInfo) throws CustomException {
      if ("O".equalsIgnoreCase(addCustomMRSShipmentInfo.getImportExportIndicator())) {
         addCustomMRSShipmentInfo.setImportExportIndicator("E");
      }
      Integer update = updateData("updateMrsAckInfo", addCustomMRSShipmentInfo, sqlSession);
      if("FMA".equalsIgnoreCase(addCustomMRSShipmentInfo.getAcknowledgeCode())) {
    	
    	  updateData("update_Customs_FlightsForFMA", addCustomMRSShipmentInfo, sqlSession); 
    	  
      }  if("FNA".equalsIgnoreCase(addCustomMRSShipmentInfo.getAcknowledgeCode())) {
    	  
    	  updateData("update_Customs_FlightsForFNA", addCustomMRSShipmentInfo, sqlSession);
      }
     
      if (update == 0) {
         insertData("insertMrsAckInfo", addCustomMRSShipmentInfo, sqlSession);
      }
   }

   @Override
   public Boolean validateCarrier(String carrierCode) throws CustomException {
      int count = fetchObject("validateCarrierCodeForMrs", carrierCode, sqlSession);
      Boolean response;
      if (count > 0)
         response = true;
      else
         response = false;
      return response;
   }

}