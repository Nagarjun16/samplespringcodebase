/**
 * AlteaFMValidation.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.validation;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.payload.AlteaFMEvent;

/**
 * This class is used for Event validation
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class AlteaFMValidation {

   private static final Logger LOGGER = LoggerFactory.getLogger(AlteaFMValidation.class);
   
   public static boolean isPayloadValid(AlteaFMEvent payload) {
      //
      if (StringUtils.isEmpty(payload.getEventSource())) {
         LOGGER.debug("Altea FM Event Payload - Event Source is NULL");
         return false;
      }
      if (StringUtils.isEmpty(payload.getTenant())) {
         LOGGER.debug("Altea FM Event Payload - Tenant is NULL");
         return false;
      }
      if (StringUtils.isEmpty(payload.getFlightKey())) {
         LOGGER.debug("Altea FM Event Payload - Flight Key is NULL");
         return false;
      }
      if (Objects.isNull(payload.getTenant())) {
         LOGGER.debug("Altea FM Event Payload - Flight Date is NULL");
         return false;
      }
      //
      return true;
   }
         
}
