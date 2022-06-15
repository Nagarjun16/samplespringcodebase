/**
 * {@link IncomingESBMessageResendService}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.service;

import java.util.Collection;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.IncomingESBErrorMessageLog;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingMessageSequence;

/**
 * Incoming ESB Message Resend Service
 * 
 * @author NIIT Technologies Ltd
 */
public interface IncomingESBMessageResendService {

   /**
    * GET Incoming ESB 'PROESSING' Messages for Sequence Process
    * 
    * @param messageSequence
    * @return
    * @throws CustomException
    */
   List<IncomingESBMessageLog> getIncomingESBProcessingMessageForSequenceProcess(
         IncomingMessageSequence messageSequence) throws CustomException;
   
   /**
    * GET Incoming ESB 'INITIATED' Messages for Sequence Process
    * 
    * @param messageSequence
    * @return
    * @throws CustomException
    */
   List<IncomingESBMessageLog> getIncomingESBInitiatedMessagesForSequenceProcess(
         IncomingMessageSequence messageSequence) throws CustomException;
   
   /**
    * GET Incoming ESB Error Message logs
    * 
    * @return
    * @throws CustomException
    */
   List<IncomingESBErrorMessageLog> getIncomingESBErrorMessageLogs() throws CustomException;
   
   /**
    * LOG Incoming Message Process Details
    * 
    * @param messageLogs
    * @throws CustomException
    */
   void logIncomingESBMessageSequenceProcessDetails(List<IncomingESBMessageLog> messageLogs) throws CustomException;
   
   /**
    * LOG Incoming ESB Message Process stats
    * 
    * @param messageDetails
    * @throws CustomException
    */
   void logIncomingESBMessageProcessStats(Collection<?> messageDetails) throws CustomException;
   
}
