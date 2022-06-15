/**
 * {@link OutgoingMessageResendDAO}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.OutgoingErrorMessageLog;

/**
 * Outgoing Message Resend Data Access Layer
 * 
 * @author NIIT Technologies Ltd
 */
public interface OutgoingMessageResendDAO {

   /**
    * GET Outgoing Error Message logs
    * 
    * @return
    * @throws CustomException
    */
   List<OutgoingErrorMessageLog> getOutgoingErrorMessageLogDetails() throws CustomException;
   
   /**
    * @param outgoingErrorMessages
    * @throws CustomException
    */
   void updateOutgoingMessagesProcessingState(List<OutgoingErrorMessageLog> outgoingErrorMessages)
         throws CustomException;
   
   /**
    * UPDATE Outgoing Message Log
    * 
    * @param resentErrorMessageLogs
    * @throws CustomException
    */
   void updateResentOutgoingMessageLog(List<OutgoingErrorMessageLog> resentErrorMessageLogs) throws CustomException;
   
   /**
    * UPDATE Outgoing Error Message Log
    * 
    * @param failedErrorMessageLogs
    * @throws CustomException
    */
   void updateFailedOutgoingErrorMessageLog(List<OutgoingErrorMessageLog> failedErrorMessageLogs)
         throws CustomException;
   
   
   /**
    * GET Outgoing Error Message logs
    * 
    * @return
    * @throws CustomException
    */
   List<OutgoingErrorMessageLog> getOutgoingErrorMessageLogDetailsAisats() throws CustomException;
   
}
