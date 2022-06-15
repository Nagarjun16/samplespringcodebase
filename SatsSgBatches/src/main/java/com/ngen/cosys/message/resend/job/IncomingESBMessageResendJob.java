/**
 * {@link IncomingESBMessageResendJob}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.job;

import java.time.LocalDateTime;
import java.util.Collection;
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
import com.ngen.cosys.message.resend.model.IncomingESBErrorMessageLog;
import com.ngen.cosys.message.resend.notification.IncomingESBMessageNotificationProcessor;
import com.ngen.cosys.message.resend.process.IncomingESBMessageProcessor;
import com.ngen.cosys.message.resend.service.IncomingESBMessageResendService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

/**
 * This implementation class is used for Incoming Message Resends to APP if any
 * intermittent network failure/unavailability(Deployment or Restart)/exception
 * cases. It ensures there is no data loss between systems and provides HA
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = MessageResendConstants.INCOMING_MESSAGE_RESEND)
public class IncomingESBMessageResendJob extends AbstractCronJob implements MessageResendJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(IncomingESBMessageResendJob.class);
   
   @Autowired
   IncomingESBMessageResendService messageResendService;
   
   @Autowired
   IncomingESBMessageBuilder messageBuilder;
   
   @Autowired
   IncomingESBMessageProcessor messageProcessor;
   
   @Autowired
   IncomingESBMessageNotificationProcessor messageNotificationProcessor;
   
   /**
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
    */
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      try {
         // Failure message process
         messageResendProcess();
      } catch (Exception ex) {
         LOGGER.error("Incoming ESB ASM/SSM Message Resend Process exception occurred - {}", ex);
      }
   }
   
   /**
    * @see com.ngen.cosys.message.resend.job.MessageResendJob#messageResendProcess()
    */
   @Override
   public void messageResendProcess() throws CustomException {
      LOGGER.info("Incoming ESB Message Resend Process Initiated {}");
      LocalDateTime startTime = LocalDateTime.now();
      List<IncomingESBErrorMessageLog> errorMessageLogs = messageResendService.getIncomingESBErrorMessageLogs();
      boolean errorMessageLogsAvailability = !CollectionUtils.isEmpty(errorMessageLogs) ? true : false;
      LOGGER.info("Incoming ESB Message Resend Process Message Logs Availability - {}, Size - {}",
            String.valueOf(errorMessageLogsAvailability), errorMessageLogs.size());
      if (errorMessageLogsAvailability) {
         Collection<?> messageDetails = buildMessage(errorMessageLogs);
         messageProcessor.process(messageDetails);
         // Notification
         sendNotificationMessageResendProcess(messageDetails);
         // Log information
         logMessageResendProcess(messageDetails);
      }
      LOGGER.info("Incoming Message Resend Process Completed :: START TIME - {}, END TIME - {}", startTime,
            LocalDateTime.now());
   }

   /**
    * @see com.ngen.cosys.message.resend.job.MessageResendJob#buildMessage(java.util.Collection)
    */
   @Override
   public Collection<?> buildMessage(Collection<?> errorMessageLogs) throws CustomException {
      LOGGER.info("Incoming ESB Message Resend Process :: Build Message - {}");
      return messageBuilder.build(errorMessageLogs);
   }

   /**
    * @see com.ngen.cosys.message.resend.job.MessageResendJob#sendNotificationMessageResendProcess(java.util.Collection)
    */
   @Override
   public void sendNotificationMessageResendProcess(Collection<?> messageDetails) throws CustomException {
      LOGGER.info("Incoming ESB Message Resend Process :: Notification Process - {}");
      messageNotificationProcessor.sendNotification(messageDetails);
   }
   
   /**
    * @see com.ngen.cosys.message.resend.job.MessageResendJob#logMessageResendProcess(java.util.Collection)
    */
   @Override
   public void logMessageResendProcess(Collection<?> errorMessageLogs) throws CustomException {
      LOGGER.info("Incoming ESB Message Resend Process :: LOG Details - {}");
      messageResendService.logIncomingESBMessageProcessStats(errorMessageLogs);
   }

}
