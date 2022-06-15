/**
 * {@link IncomingESBMessageResendDAO}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.IncomingESBErrorMessageLog;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingMessageSequence;

/**
 * Incoming Message Resend Data Access Layer
 * 
 * @author NIIT Technologies Ltd
 */
public interface IncomingESBMessageResendDAO {

   /**
    * GET Incoming ESB 'PROCESSING' Messages for Sequence Re-Process
    * 
    * @param messageSequence
    * @return
    * @throws CustomException
    */
   List<IncomingESBMessageLog> getIncomingESBProcessingMessageLogDetails(IncomingMessageSequence messageSequence)
         throws CustomException;
   
   /**
    * GET Incoming ESB 'INITIATED' Messages for Sequence Process
    * 
    * @param messageSequence
    * @return
    * @throws CustomException
    */
   List<IncomingESBMessageLog> getIncomingESBInitiatedMessageLogDetails(IncomingMessageSequence messageSequence)
         throws CustomException;
   
   /**
    * GET Incoming ESB Error Message logs
    * 
    * @return
    * @throws CustomException
    */
   List<IncomingESBErrorMessageLog> getIncomingESBErrorMessageLogDetails() throws CustomException;
   
   /**
    * @param incomingESBMessage
    * @return
    * @throws CustomException
    */
   List<BigInteger> verifyShipmentDuplicateReference(IncomingESBMessageLog incomingESBMessage) throws CustomException;
   
   /**
    * @param processingESBMessages
    * @throws CustomException
    */
   void updateESBMessagesReProcessingState(List<IncomingESBMessageLog> processingESBMessages) throws CustomException;
   
   /**
    * Initiated ESB Messages
    * 
    * @param initiatedESBMessages
    * @throws CustomException
    */
   void updateESBMessagesProcessingState(List<IncomingESBMessageLog> initiatedESBMessages) throws CustomException;
   
   /**
    * Initiated ESB Error Messages
    * 
    * @param incomingESBErrorMessages
    * @throws CustomException
    */
   void updateESBErrorMessagesProcessingState(List<IncomingESBErrorMessageLog> incomingESBErrorMessages)
         throws CustomException;
   
   /**
    * UPDATE Incoming ESB Message Sequence Log
    * 
    * @param messageLogs
    * @throws CustomException
    */
   void updateResentIncomingESBMessageSequenceLog(List<IncomingESBMessageLog> messageLogs) throws CustomException;
   
   /**
    * UPDATE Incoming ESB Message Log
    * 
    * @param resentErrorMessageLogs
    * @throws CustomException
    */
   void updateResentIncomingESBMessageLog(List<IncomingESBErrorMessageLog> resentErrorMessageLogs)
         throws CustomException;
   
   /**
    * UPDATE Incoming ESB Error Message Log
    * 
    * @param failedErrorMessageLogs
    * @throws CustomException
    */
   void updateFailedIncomingESBErrorMessageLog(List<IncomingESBErrorMessageLog> failedErrorMessageLogs)
         throws CustomException;
   
}
