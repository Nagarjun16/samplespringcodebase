/**
 * {@link MessageSequenceJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.job;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;

/**
 * This interface defines Sequence Job life cycle events
 * 
 * @author NIIT Technologies Ltd
 */
public interface MessageSequenceJob {

   /**
    * Re-processing state messages
    * 
    * @throws CustomException
    */
   void messageSequenceReProcess() throws CustomException;
   
   /**
    * Initialize configuration and process
    * 
    * @throws CustomException
    */
   void messageSequenceProcess() throws CustomException;
   
   /**
    * Message sequence process details
    * 
    * @param messageLogs
    * @throws CustomException
    */
   void logMessageSequenceProcessDetails(List<IncomingESBMessageLog> messageLogs) throws CustomException;
   
}
