/**
 * AlteaFMService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.service;

import com.ngen.cosys.altea.fm.model.DCSFMUpdateCargoFigures;
import com.ngen.cosys.events.payload.AlteaFMEvent;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface is used for DCS Update Cargo 
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface AlteaFMService {

   /**
    * Valid Message Addressing Setup
    * 
    * @param event
    * @return
    * @throws CustomException
    */
   public boolean isMessageAddressingSetupValid(AlteaFMEvent event) throws CustomException;
   
   /**
    * GET Configured Per Flight Message Limit
    *       Configuration from App System Parameters
    * @return
    * @throws CustomException
    */
   public int getConfiguredPerFlightMessageCount(AlteaFMEvent event) throws CustomException;
   
   /**
    * GET Altea FM SENT message count by Flight
    * 
    * @param event
    * @return
    * @throws CustomException
    */
   public int getAlteaFMSENTMessageCountByFlight(AlteaFMEvent event) throws CustomException;
   
   /**
    * Prepare Update Cargo Payload
    * 
    * @param event
    * @return
    * @throws CustomException
    */
   public DCSFMUpdateCargoFigures prepareUpdateCargoPayload(AlteaFMEvent event) throws CustomException;
   
}
