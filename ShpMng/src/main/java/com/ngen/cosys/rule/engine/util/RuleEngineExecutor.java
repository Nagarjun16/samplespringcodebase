/**
 * 
 * RuleEngineExecutor.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 OCT, 2018   NIIT      -
 */
package com.ngen.cosys.rule.engine.util;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;

/**
 * This interface is used for Rule Engine
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface RuleEngineExecutor {

   /**
    * @param shipmentNumber
    */
   boolean initRuleEngineProcessForACAS(FactPayload factPayload) throws CustomException ;
   
}
