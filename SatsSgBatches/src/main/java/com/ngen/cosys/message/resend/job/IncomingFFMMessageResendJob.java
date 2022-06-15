/**
 * {@link IncomingFFMMessageResendJob}
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
import com.ngen.cosys.message.resend.builder.FFMBuilder;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLog;
import com.ngen.cosys.message.resend.model.IncomingMessageSequence;
import com.ngen.cosys.message.resend.process.IncomingESBMessageProcessor;
import com.ngen.cosys.message.resend.service.IncomingESBMessageResendService;
import com.ngen.cosys.message.resend.service.IncomingFFMLogService;
import com.ngen.cosys.message.resend.util.FFMUtils;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

/**
 * This implementation class is used for Incoming FFM Message Resends to APP for
 * sequence message processing
 * 
 * @author NIIT Technologies Ltd
 */
@Component(value = MessageResendConstants.INCOMING_FFM_MESSAGE_RESEND)
public class IncomingFFMMessageResendJob extends AbstractCronJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(IncomingFFMMessageResendJob.class);

	@Autowired
	IncomingFFMLogService incomingFFMLogService;

	@Autowired
	IncomingESBMessageResendService messageResendService;

	@Autowired
	FFMUtils messageUtility;

	@Autowired
	FFMBuilder messageBuilder;

	@Autowired
	IncomingESBMessageProcessor messageProcessor;

	/**
	 * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			// Re-Processing
			messageSequenceProcess();
		} catch (Exception ex) {
			LOGGER.error("Incoming FFM Message Sequence Process exception occurred - {}", ex);
		}
	}

	public void messageSequenceProcess() throws CustomException, Exception {
		LOGGER.warn("Incoming FFM Message Sequence Re-Process Initiated - YYYXYZ12 {}");
		LocalDateTime startTime = LocalDateTime.now();
		// HOLD Messages from FFM Log
		List<IncomingFFMLog> holdFFMLogs = incomingFFMLogService.getFFMHOLDMessages();
		boolean holdMessageLogsAvailability = !CollectionUtils.isEmpty(holdFFMLogs) ? true : false;
		LOGGER.warn("Incoming FFM HOLD Message Logs Availability - YYYXYZ12 - {}, Size - {}",
				String.valueOf(holdMessageLogsAvailability), holdFFMLogs.size());
		// ESB Message INITIATED Process for Parallelism
		IncomingMessageSequence messageSequence = new IncomingMessageSequence();
		messageSequence.setTypeFFM(true);
		List<IncomingESBMessageLog> messageLogs = messageResendService
				.getIncomingESBInitiatedMessagesForSequenceProcess(messageSequence);
		boolean initiatedMessageLogsAvailability = !CollectionUtils.isEmpty(messageLogs) ? true : false;
		LOGGER.warn("Incoming FFM INITIATED Message Logs Availability - YYYXYZ12 - {}, Size - {}",
				String.valueOf(initiatedMessageLogsAvailability), messageLogs.size());
		List<IncomingFFMLog> incomingFFMLogs = messageUtility.mergeFFMLogs(holdFFMLogs, messageLogs);
		// Combine all part(sequence) messages to one single message
		
		LOGGER.warn("Incoming FFM Message Before Building started - YYYXYZ12 ::  TIME - {}",
				 LocalDateTime.now());
		
		messageBuilder.build(incomingFFMLogs);
		
		LOGGER.warn("Incoming FFM Message After Building started - YYYXYZ12 ::  TIME - {}",
				 LocalDateTime.now());
		
		// FFM Processor
		boolean interfaceStatus = messageProcessor.processFFM(incomingFFMLogs);
		if (interfaceStatus) {
			// Logger
			
			LOGGER.warn("Incoming FFM Message Before updateLogDetails started - YYYXYZ12 ::  TIME - {}",
					 LocalDateTime.now());
			
			incomingFFMLogService.updateFFMLogDetails(incomingFFMLogs);
			
			LOGGER.warn("Incoming FFM Message After updateLogDetails started - YYYXYZ12 ::  TIME - {}",
					 LocalDateTime.now());
			
			// ESB Message Log Process update
			logMessageProcessDetails(messageLogs);
			LOGGER.warn("Incoming FFM Message INITIATED Sequence Process Completed - YYYXYZ12 :: START TIME - {}, END TIME - {}",
					startTime, LocalDateTime.now());
		} else {
			LOGGER.error("Interface CargoMessaging Down - YYYXYZ12 :: Time -{}", LocalDateTime.now());
		}
	}

	/**
	 * Log Message Sequence Process Details
	 * 
	 */
	public void logMessageProcessDetails(List<IncomingESBMessageLog> messageLogs) throws CustomException {
		LOGGER.warn("Incoming FFM Message Sequence Process - YYYXYZ12 :: LOG Details - {}");
		for (IncomingESBMessageLog messageLog : messageLogs) {
			messageLog.setProcessed(true);
			messageLog.setStatus(MessageResendConstants.PROCESSED);
		}
		messageResendService.logIncomingESBMessageSequenceProcessDetails(messageLogs);
	}

}
