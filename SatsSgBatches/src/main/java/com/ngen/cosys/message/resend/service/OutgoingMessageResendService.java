/**
 * {@link OutgoingMessageResendService}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.service;

import java.util.Collection;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.OutgoingErrorMessageLog;

/**
 * Outgoing Message Resend Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface OutgoingMessageResendService {

   /**
    * GET Outgoing Error Message logs
    * 
    * @return
    * @throws CustomException
    */
   List<OutgoingErrorMessageLog> getOutgoingErrorMessageLogs() throws CustomException;
   
   /**
    * GET Outgoing Error Message logs
    * 
    * @return
    * @throws CustomException
    */
   List<OutgoingErrorMessageLog> getOutgoingErrorMessageLogsAisats() throws CustomException;
   
   /**
    * LOG Outgoing Message Process stats
    * 
    * @param messageDetails
    * @throws CustomException
    */
   void logOutgoingMessageProcessStats(Collection<?> messageDetails) throws CustomException;
   
}
