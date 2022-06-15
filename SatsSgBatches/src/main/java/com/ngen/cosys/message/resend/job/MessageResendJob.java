/**
 * {@link MessageResendJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.job;

import java.util.Collection;

import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface defines Resends Job life cycle events
 * 
 * @author NIIT Technologies Ltd
 */
public interface MessageResendJob {

   /**
    * Initialize configuration and process
    * 
    * @throws CustomException
    */
   void messageResendProcess() throws CustomException;
   
   /**
    * Message Builder
    * 
    * @param errorMessageLogs
    * @return
    * @throws CustomException
    */
   Collection<?> buildMessage(Collection<?> errorMessageLogs) throws CustomException;
   
   /**
    * Send Notification regards INFO/WARNING/CRITICAL Alerts
    * 
    * @param messageDetails
    * @throws CustomException
    */
   void sendNotificationMessageResendProcess(Collection<?> messageDetails) throws CustomException;
   
   /**
    * Log Sent/Not details into reference table
    * 
    * @param errorMessageLogs
    * @throws CustomException
    */
   void logMessageResendProcess(Collection<?> errorMessageLogs) throws CustomException;
   
}
