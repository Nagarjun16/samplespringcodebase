/**
 * {@link IncomingFWBMessageResendJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.job;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.builder.IncomingESBMessageBuilder;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingMessageSequence;
import com.ngen.cosys.message.resend.process.IncomingESBMessageProcessor;
import com.ngen.cosys.message.resend.service.IncomingESBMessageResendService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

/**
 * This implementation class is used for Incoming FWB Message Resends to APP 
 * for sequence message processing
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = MessageResendConstants.INCOMING_FWB_MESSAGE_RESEND)
public class IncomingFWBMessageResendJob extends AbstractCronJob implements MessageSequenceJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(IncomingFWBMessageResendJob.class);
   
   @Autowired
   IncomingESBMessageResendService messageResendService;
   
   @Autowired
   IncomingESBMessageBuilder messageBuilder;
   
   @Autowired
   IncomingESBMessageProcessor messageProcessor;
   
   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         // Re-Processing
         messageSequenceReProcess();
         // Sequence Process
         messageSequenceProcess();
      } catch (Exception ex) {
         LOGGER.error("Incoming FWB Message Sequence Process exception occurred - {}", ex);
      }
   }
   
   /**
    * @see com.ngen.cosys.message.resend.job.MessageSequenceJob#messageSequenceReProcess()
    */
   @Override
   public void messageSequenceReProcess() throws CustomException {
      LOGGER.info("Incoming FWB Message Sequence Re-Process Initiated {}");
      LocalDateTime startTime = LocalDateTime.now();
      // ESB Message PROCESSING state Re-Processing
      IncomingMessageSequence messageSequence = new IncomingMessageSequence();
      messageSequence.setTypeFWB(true);
      List<IncomingESBMessageLog> messageLogs = messageResendService
            .getIncomingESBProcessingMessageForSequenceProcess(messageSequence);
      boolean messageLogsAvailability = !CollectionUtils.isEmpty(messageLogs) ? true : false;
      LOGGER.info("Incoming FWB PROCESSING Message Logs Availability - {}, Size - {}",
            String.valueOf(messageLogsAvailability), messageLogs.size());
      if (messageLogsAvailability) {
         messageProcessor.process(messageLogs);
         logMessageSequenceProcessDetails(messageLogs);
      }
      LOGGER.info("Incoming FWB Message PROCESSING Sequence Process Completed :: START TIME - {}, END TIME - {}",
            startTime, LocalDateTime.now());
   }

   /**
    * @see com.ngen.cosys.message.resend.job.MessageSequenceJob#messageSequenceProcess()
    */
   @Override
   public void messageSequenceProcess() throws CustomException {
      LOGGER.info("Incoming FWB Message Sequence Process Initiated {}");
      LocalDateTime startTime = LocalDateTime.now();
      // ESB Message INITIATED Process for Parallelism
      IncomingMessageSequence messageSequence = new IncomingMessageSequence();
      messageSequence.setTypeFWB(true);
      List<IncomingESBMessageLog> messageLogs = messageResendService
            .getIncomingESBInitiatedMessagesForSequenceProcess(messageSequence);
      boolean messageLogsAvailability = !CollectionUtils.isEmpty(messageLogs) ? true : false;
      LOGGER.info("Incoming FWB INITIATED Message Logs Availability - {}, Size - {}",
            String.valueOf(messageLogsAvailability), messageLogs.size());
      if (messageLogsAvailability) {
         messageProcessor.process(messageLogs);
         logMessageSequenceProcessDetails(messageLogs);
      }
      LOGGER.info("Incoming FWB Message INITIATED Sequence Process Completed :: START TIME - {}, END TIME - {}",
            startTime, LocalDateTime.now());
   }

   /**
    * Log Message Sequence Process Details
    * 
    * @see com.ngen.cosys.message.resend.job.MessageSequenceJob#logMessageSequenceProcessDetails(java.util.List)
    */
   @Override
   public void logMessageSequenceProcessDetails(List<IncomingESBMessageLog> messageLogs) throws CustomException {
      LOGGER.info("Incoming FWB Message Sequence Process :: LOG Details - {}");
      messageResendService.logIncomingESBMessageSequenceProcessDetails(messageLogs);
   }

}
