/**
 * {@link IncomingESBMessageResendServiceImpl}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.core.MessageDetail;
import com.ngen.cosys.message.resend.dao.IncomingESBMessageResendDAO;
import com.ngen.cosys.message.resend.model.IncomingESBErrorMessageLog;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingMessageSequence;

/**
 * Incoming ESB Message Resend Service Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class IncomingESBMessageResendServiceImpl implements IncomingESBMessageResendService {

   private static final Logger LOGGER = LoggerFactory.getLogger(IncomingESBMessageResendService.class);
   
   @Autowired
   IncomingESBMessageResendDAO incomingESBMessageResendDAO;
   
   /**
    * @see com.ngen.cosys.message.resend.service.IncomingESBMessageResendService#getIncomingESBProcessingMessageForSequenceProcess(com.ngen.cosys.message.resend.model.IncomingMessageSequence)
    */
   @Override
   public List<IncomingESBMessageLog> getIncomingESBProcessingMessageForSequenceProcess(
         IncomingMessageSequence messageSequence) throws CustomException {
      LOGGER.debug("Incoming ESB Processing Status Messages - {}");
      List<IncomingESBMessageLog> processingESBMessages = incomingESBMessageResendDAO
            .getIncomingESBProcessingMessageLogDetails(messageSequence);
      if (!CollectionUtils.isEmpty(processingESBMessages)) {
         incomingESBMessageResendDAO.updateESBMessagesReProcessingState(processingESBMessages);
      }
      return processingESBMessages;
   }
   
   /**
    * @see com.ngen.cosys.message.resend.service.IncomingESBMessageResendService#getIncomingESBInitiatedMessagesForSequenceProcess(com.ngen.cosys.message.resend.model.IncomingMessageSequence)
    */
   @Override
   public List<IncomingESBMessageLog> getIncomingESBInitiatedMessagesForSequenceProcess(
         IncomingMessageSequence messageSequence) throws CustomException {
      LOGGER.debug("Incoming ESB Initiated Status Messages - {}");
      List<IncomingESBMessageLog> initiatedESBMessages = incomingESBMessageResendDAO
            .getIncomingESBInitiatedMessageLogDetails(messageSequence);
      if (!CollectionUtils.isEmpty(initiatedESBMessages)) {
         if (!messageSequence.isTypeFFM()) {
            incomingESBMessageResendDAO.updateESBMessagesProcessingState(initiatedESBMessages);
         }
      }
      return initiatedESBMessages;
   }
   
   /**
    * @see com.ngen.cosys.message.resend.service.IncomingESBMessageResendService#getIncomingESBErrorMessageLogs()
    */
   @Override
   public List<IncomingESBErrorMessageLog> getIncomingESBErrorMessageLogs() throws CustomException {
      LOGGER.debug("Incoming ESB Error Message logs - {}");
      List<IncomingESBErrorMessageLog> incomingESBErrorMessages = incomingESBMessageResendDAO
            .getIncomingESBErrorMessageLogDetails();
      if (!CollectionUtils.isEmpty(incomingESBErrorMessages)) {
         incomingESBMessageResendDAO.updateESBErrorMessagesProcessingState(incomingESBErrorMessages);
      }
      return incomingESBErrorMessages;
   }

   /**
    * @param messageLogs
    * @throws CustomException
    */
   @Override
   public void logIncomingESBMessageSequenceProcessDetails(List<IncomingESBMessageLog> messageLogs)
         throws CustomException {
      LOGGER.debug("Incoming ESB Message Sequence Log Details - {}");
      incomingESBMessageResendDAO.updateResentIncomingESBMessageSequenceLog(messageLogs);
   }
   
   /**
    * @see com.ngen.cosys.message.resend.service.IncomingESBMessageResendService#logIncomingESBMessageProcessStats(java.util.Collection)
    */
   @Override
   public void logIncomingESBMessageProcessStats(Collection<?> messageDetails) throws CustomException {
      LOGGER.debug("Incoming ESB Error Message UPDATE Log Stats - {}");
      List<IncomingESBErrorMessageLog> resentErrorMessageLogs = new ArrayList<>();
      List<IncomingESBErrorMessageLog> failedErrorMessageLogs = new ArrayList<>();
      MessageDetail messageDetail = null;
      IncomingESBErrorMessageLog errorMessageLog = null;
      //
      for (Object message : messageDetails) {
         if (message instanceof MessageDetail) {
            messageDetail = (MessageDetail) message;
            errorMessageLog = (IncomingESBErrorMessageLog) messageDetail.getErrorMessageLog();
         }
         if (Objects.nonNull(errorMessageLog)) {
            if (errorMessageLog.isProcessed()) {
               resentErrorMessageLogs.add(errorMessageLog);
            } else {
               failedErrorMessageLogs.add(errorMessageLog);
            }
         }
      }
      // Resent error Message logs
      if (!CollectionUtils.isEmpty(resentErrorMessageLogs)) {
         incomingESBMessageResendDAO.updateResentIncomingESBMessageLog(resentErrorMessageLogs);
      }
      if (!CollectionUtils.isEmpty(failedErrorMessageLogs)) {
         incomingESBMessageResendDAO.updateFailedIncomingESBErrorMessageLog(failedErrorMessageLogs);
      }
   }

}
