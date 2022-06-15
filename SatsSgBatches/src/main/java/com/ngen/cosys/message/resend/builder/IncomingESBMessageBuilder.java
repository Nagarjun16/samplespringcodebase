/**
 * {@link IncomingESBMessageBuilder}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.core.MessageDetail;
import com.ngen.cosys.message.resend.model.IncomingESBErrorMessageLog;

/**
 * Incoming ESB Message Builder
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class IncomingESBMessageBuilder {

   private static final Logger LOGGER = LoggerFactory.getLogger(IncomingESBMessageBuilder.class);
   
   /**
    * Incoming ESB Message Builder
    * 
    * @param errorMessageLogs
    * @return
    * @throws CustomException
    */
   public Collection<?> build(Collection<?> errorMessageLogs) throws CustomException {
      LOGGER.info("Incoming ESB Message Builder - Resend Job - {}");
      List<MessageDetail> messageDetails = new ArrayList<>();
      MessageDetail messageDetail = null;
      IncomingESBErrorMessageLog incomingESBErrorMessageLog = null;
      //
      for (Object errorMessageLog : errorMessageLogs) {
         if (errorMessageLog instanceof IncomingESBErrorMessageLog) {
            incomingESBErrorMessageLog = (IncomingESBErrorMessageLog) errorMessageLog;
            messageDetail = new MessageDetail();
            messageDetail.setErrorMessageLog(incomingESBErrorMessageLog);
         }
         if (Objects.nonNull(incomingESBErrorMessageLog)) {
            messageDetail.setPayload(copyPayload(incomingESBErrorMessageLog));
            messageDetails.add(messageDetail);
         }
      }
      return messageDetails;
   }
   
   /**
    * @param errorMessageLog
    * @return
    */
   private Object copyPayload(IncomingESBErrorMessageLog errorMessageLog) {
      return errorMessageLog.getMessage();
   }
   
}
