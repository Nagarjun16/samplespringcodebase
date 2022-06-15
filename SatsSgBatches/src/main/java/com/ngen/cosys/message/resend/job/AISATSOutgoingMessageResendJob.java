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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.builder.OutgoingMessageBuilder;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.model.OutgoingErrorMessageLog;
import com.ngen.cosys.message.resend.notification.OutgoingMessageNotificationProcessor;
import com.ngen.cosys.message.resend.process.OutgoingMessageProcessor;
import com.ngen.cosys.message.resend.service.OutgoingMessageResendService;
import com.ngen.cosys.scheduler.job.AbstractCronJob;



/**
 * This implementation class is used for Outgoing Message Resends for Aisats to External
 * Systems if any intermittent network failure/unavailability/exception cases.
 * It ensures there is no data loss between systems and provides HA
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = MessageResendConstants.OUTGOING_MESSAGE_RESEND_AISATS)
public class AISATSOutgoingMessageResendJob extends AbstractCronJob implements MessageResendJob {

	   private static final Logger LOGGER = LoggerFactory.getLogger(OutgoingMessageResendJob.class);

	   @Autowired
	   OutgoingMessageResendService messageResendService;
	   
	   @Autowired
	   OutgoingMessageBuilder messageBuilder;
	   
	   @Autowired
	   OutgoingMessageProcessor messageProcessor;
	   
	   @Autowired
	   OutgoingMessageNotificationProcessor messageNotificationProcessor;
	   
	   /**
	    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
	    */
	   @Override
	   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	      try {
	         messageResendProcess();
	      } catch (Exception ex) {
	         LOGGER.error("Outgoing Message Resend Process exception occurred - {}", ex);
	      }
	   }
	   
	   /**
	    * @see com.ngen.cosys.message.resend.job.MessageResendJob#messageResendProcess()
	    */
	  
	   public void messageResendProcess() throws CustomException {
	      LOGGER.info("Outgoing Message Resend Process Initiated {}");
	      LocalDateTime startTime = LocalDateTime.now();
	      List<OutgoingErrorMessageLog> errorMessageLogs = messageResendService.getOutgoingErrorMessageLogsAisats();
	      boolean errorMessageLogsAvailability = !CollectionUtils.isEmpty(errorMessageLogs) ? true : false;
	      LOGGER.info("Outgoing Message Resend Process Message Logs Availability - {}, Size - {}",
	            String.valueOf(errorMessageLogsAvailability), errorMessageLogs.size());
	      if (errorMessageLogsAvailability) {
	         Collection<?> messageDetails = buildMessage(errorMessageLogs);
	         messageProcessor.processAisatsOutgoing(messageDetails);
	         // Notification
	         sendNotificationMessageResendProcess(messageDetails);
	         // Log information
	         logMessageResendProcess(messageDetails);
	      }
	      LOGGER.info("Outgoing Message Resend Process Completed :: START TIME - {}, END TIME - {}", startTime,
	            LocalDateTime.now());
	   }

	   /**
	    * @see com.ngen.cosys.message.resend.job.MessageResendJob#buildMessage(java.util.Collection)
	    */
	   @Override
	   public Collection<?> buildMessage(Collection<?> errorMessageLogs) throws CustomException {
	      LOGGER.info("Outgoing Message Resend Process :: Build Message - {}");
	      return messageBuilder.build(errorMessageLogs);
	   }

	   /**
	    * @see com.ngen.cosys.message.resend.job.MessageResendJob#sendNotificationMessageResendProcess(java.util.Collection)
	    */
	   @Override
	   public void sendNotificationMessageResendProcess(Collection<?> messageDetails) throws CustomException {
	      LOGGER.info("Outgoing Message Resend Process :: Notification Process - {}");
	      messageNotificationProcessor.sendNotification(messageDetails);
	   }

	   /**
	    * @see com.ngen.cosys.message.resend.job.MessageResendJob#logMessageResendProcess(java.util.Collection)
	    */
	   @Override
	   public void logMessageResendProcess(Collection<?> errorMessageLogs) throws CustomException {
	      LOGGER.info("Outgoing Message Resend Process :: LOG Details - {}");
	      messageResendService.logOutgoingMessageProcessStats(errorMessageLogs);
	   }

	}
