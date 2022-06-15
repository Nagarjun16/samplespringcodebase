/**
 * 
 * RuleEngineExecutorService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 OCT, 2018   NIIT      -
 */
package com.ngen.cosys.rule.engine.util;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.processing.engine.rule.executor.RuleExecutor;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;

/**
 * This interface is used for Rule Engine Executor Service
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class RuleEngineExecutorService implements RuleEngineExecutor {

   private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngineExecutorService.class);
   
   @Autowired
   RuleExecutor ruleExecutor;
   
   /**
    * @throws CustomException 
    * @see com.ngen.cosys.rule.engine.util.RuleEngineExecutor#initRuleEngineProcessForACAS(java.lang.String)
    * 
    */
   @Override
   public boolean initRuleEngineProcessForACAS(FactPayload factPayload) throws CustomException {
      //
      LOGGER.debug("Rule Engine Executor Service :: initRuleEngineProcessForACAS");
      // Initial check
      if (Objects.isNull(factPayload.getFactShipment())
            || StringUtils.isEmpty(factPayload.getFactShipment().getShipmentNumber())
            || Objects.isNull(factPayload.getFactShipment().getShipmentDate())
            || Objects.isNull(factPayload.getTriggerPoint())
            || (!factPayload.isRulesConfigured() && CollectionUtils.isEmpty(factPayload.getRulesPayload()))) {
         LOGGER.debug("The requested payload is not having sufficient information to execute");
         return false;
      }
      //
      LOGGER.debug("Shipment Number :: {} ", factPayload.getFactShipment().getShipmentNumber());
      ruleExecutor.executeRule(factPayload);
      if (Objects.nonNull(factPayload) && factPayload.isMessageTrigger()) {
         LOGGER.debug("Rule Engine Message Trigger Condition satisfied..!");
         return true;
      }
      LOGGER.debug("Shipment characteristics is not matched :: Message Trigger Condition is FALSE");
      //
      return false;
   }

}
