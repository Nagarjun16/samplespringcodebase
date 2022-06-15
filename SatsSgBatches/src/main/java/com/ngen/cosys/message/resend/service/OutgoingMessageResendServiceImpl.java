/**
 * {@link OutgoingMessageResendServiceImpl}
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
import com.ngen.cosys.message.resend.dao.OutgoingMessageResendDAO;
import com.ngen.cosys.message.resend.model.OutgoingErrorMessageLog;

/**
 * Outgoing Message Resend Service Implementation
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class OutgoingMessageResendServiceImpl implements OutgoingMessageResendService {

   private static final Logger LOGGER = LoggerFactory.getLogger(OutgoingMessageResendService.class);
   
   @Autowired
   OutgoingMessageResendDAO outgoingMessageResendDAO;
   
   /**
    * @see com.ngen.cosys.message.resend.service.OutgoingMessageResendService#getOutgoingErrorMessageLogs()
    */
   @Override
   public List<OutgoingErrorMessageLog> getOutgoingErrorMessageLogs() throws CustomException {
      LOGGER.debug("Outgoing Error Message logs - {}");
      List<OutgoingErrorMessageLog> outgoingErrorMessages = outgoingMessageResendDAO
            .getOutgoingErrorMessageLogDetails();
      if (!CollectionUtils.isEmpty(outgoingErrorMessages)) {
         outgoingMessageResendDAO.updateOutgoingMessagesProcessingState(outgoingErrorMessages);
      }
      return outgoingErrorMessages;
   }

   /**
    * @see com.ngen.cosys.message.resend.service.OutgoingMessageResendService#logOutgoingMessageProcessStats(java.util.Collection)
    */
   @Override
   public void logOutgoingMessageProcessStats(Collection<?> messageDetails) throws CustomException {
      LOGGER.debug("Outgoing Error Message UPDATE Log Stats - {}");
      List<OutgoingErrorMessageLog> resentErrorMessageLogs = new ArrayList<>();
      List<OutgoingErrorMessageLog> failedErrorMessageLogs = new ArrayList<>();
      MessageDetail messageDetail = null;
      OutgoingErrorMessageLog errorMessageLog = null;
      //
      for (Object message : messageDetails) {
         if (message instanceof MessageDetail) {
            messageDetail = (MessageDetail) message;
            errorMessageLog = (OutgoingErrorMessageLog) messageDetail.getErrorMessageLog();
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
         outgoingMessageResendDAO.updateResentOutgoingMessageLog(resentErrorMessageLogs);
      }
      if (!CollectionUtils.isEmpty(failedErrorMessageLogs)) {
         outgoingMessageResendDAO.updateFailedOutgoingErrorMessageLog(failedErrorMessageLogs);
      }
   }


@Override
public List<OutgoingErrorMessageLog> getOutgoingErrorMessageLogsAisats() throws CustomException {
	LOGGER.debug("Outgoing Error Message logs - {}");
    List<OutgoingErrorMessageLog> outgoingErrorMessages = outgoingMessageResendDAO.getOutgoingErrorMessageLogDetailsAisats();
    if (!CollectionUtils.isEmpty(outgoingErrorMessages)) {
       outgoingMessageResendDAO.updateOutgoingMessagesProcessingState(outgoingErrorMessages);
    }
    return outgoingErrorMessages;
}

}
