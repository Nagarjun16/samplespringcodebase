/**
 * {@link IncomingFFMLogDAO}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.IncomingFFMLog;

/**
 * Incoming FFM Log Service
 * 
 * @author Coforge PTE Ltd
 */
public interface IncomingFFMLogDAO {

   /**
    * GET FFM HOLD Messages from FFM Log & INITIATED, REJECTED status 
    * 
    * @return
    * @throws CustomException
    */
   List<IncomingFFMLog> getFFMHOLDMessages() throws CustomException;
   
   /**
    * Update FFM & Details with Status
    * 
    * @param incomingFFMLogs
    * @throws CustomException
    */
   void updateFFMLogDetails(List<IncomingFFMLog> incomingFFMLogs) throws CustomException;
   
}
