/**
 * AlteaFMRepository.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.dao;

import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.events.payload.AlteaFMEvent;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface is used for DCS Update Cargo DAO
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface AlteaFMRepository {

   /**
    * @param alteaFMEvent
    * @return
    * @throws CustomException
    */
   boolean isMessageAddressingSetupConfigured(AlteaFMEvent alteaFMEvent) throws CustomException;
   
   /**
    * @param param
    * @return
    * @throws CustomException
    */
   String getConfiguredPerFlightMessageCount(String param) throws CustomException;
   
   /**
    * @param alteaFMEvent
    * @return
    * @throws CustomsException
    */
   int getSENTMessageCountByFlight(AlteaFMEvent alteaFMEvent) throws CustomException;
   
   /**
    * @param alteaFMEvent
    * @return
    * @throws CustomException
    */
   DCSFMUpdateCargoFigures getAlteaFMEventSourcePayload(AlteaFMEvent alteaFMEvent) throws CustomException;
   
}
