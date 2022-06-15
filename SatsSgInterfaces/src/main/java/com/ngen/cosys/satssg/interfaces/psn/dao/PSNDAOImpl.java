package com.ngen.cosys.satssg.interfaces.psn.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.processing.engine.rule.executor.RuleExecutor;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;
import com.ngen.cosys.processing.engine.rule.fact.FactShipment;
import com.ngen.cosys.processing.engine.rule.group.ACASGroup;
import com.ngen.cosys.processing.engine.rule.triggerpoint.InboundMessage;
import com.ngen.cosys.satssg.interfaces.psn.model.AirwayBillIdentification;
import com.ngen.cosys.satssg.interfaces.psn.model.PsnMessageModel;

@Repository("psnDAO")
public class PSNDAOImpl extends BaseDAO implements PSNDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Autowired
   private ShipmentProcessorService shipmentProcessorService;

   @Autowired
   RuleExecutor ruleExecutor;
   
   @Override
   public void insertPSNInfo(PsnMessageModel psnMessageModel) throws CustomException {
      for (AirwayBillIdentification awb : psnMessageModel.getAirwayBillIdentifications()) {
         LocalDate shipmentDate = shipmentProcessorService.getShipmentDate(awb.getShipmentNumber());
         awb.setShipmentDate(shipmentDate);
         awb.setAckCode(psnMessageModel.getAckCode());
         awb.setRemark(psnMessageModel.getRemark());
         awb.setTransactionDateTime(psnMessageModel.getTransactionDateTime());
      }
      // Insert the PSN message info
      super.insertData("insertPSNInfo", psnMessageModel.getAirwayBillIdentifications(), sqlSession);
      //
      closeRuleFailure(psnMessageModel.getAirwayBillIdentifications());
   }
   @Override
   public boolean validatePSNCode(PsnMessageModel psnMessageModel) throws CustomException {
      int psnCount = this.fetchObject("sqlCheckValidPSNCode", psnMessageModel, sqlSession);
      return psnCount > 0;
   }

   /**
    * @param awb
    * @return
    * @throws CustomException
    */
   private void closeRuleFailure(List<AirwayBillIdentification> awbList) throws CustomException {
      for (AirwayBillIdentification awb : awbList) {
         // Payload Initialization
         FactPayload factPayload = new FactPayload();
         FactShipment factShipment = new FactShipment();
         factShipment.setShipmentNumber(awb.getShipmentNumber());
         factShipment.setShipmentDate(awb.getShipmentDate());
         factShipment.setPsnCode(awb.getAckCode());
         factPayload.setFactShipment(factShipment);
         // Trigger Point & Operation
         factPayload.setTriggerPoint(InboundMessage.class);
         factPayload.setTriggerPointOperation(com.ngen.cosys.processing.engine.rule.triggerpoint.operation.PSN.class);
         // Rules configured false
         factPayload.setRulesPayload(new ArrayList<>()); 
         factPayload.getRulesPayload().add(ACASGroup.class);
         // Set Audit Details
         factPayload.setCreatedBy(awb.getCreatedBy());
         factPayload.setCreatedOn(awb.getCreatedOn());
         factPayload.setModifiedBy(awb.getModifiedBy());
         factPayload.setModifiedOn(awb.getModifiedOn());
         // Execute Rule
         ruleExecutor.closeRuleFailure(factPayload);
      }
   }
   
}