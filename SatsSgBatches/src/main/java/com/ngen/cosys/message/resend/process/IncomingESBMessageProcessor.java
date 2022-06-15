/**
 * {@link IncomingESBMessageProcessor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.process;

import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.BATCH;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.FAILED;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.FFM;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.INITIATED;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.PROCESSED;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.RESENT;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.config.MessageResendConfig;
import com.ngen.cosys.message.resend.core.MessageDetail;
import com.ngen.cosys.message.resend.core.MessageResponse;
import com.ngen.cosys.message.resend.model.IncomingESBErrorMessageLog;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingESBResendMessageLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLogDetail;
import com.ngen.cosys.message.resend.util.MessageResendUtils;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

/**
 * Incoming ESB Message Resend Processor
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class IncomingESBMessageProcessor extends MessageProcessor {

	@Autowired
	private MessageResendConfig messageConfig;

	/**
	 * ESB INITIATED Message Process for Sequence
	 * 
	 * @param incomingMessageLogs
	 * @throws CustomException
	 */
	public void process(List<IncomingESBMessageLog> incomingMessageLogs) throws CustomException {
		LOGGER.info("Incoming ESB INITIATED Sequence Message Processor :: message process - {}");

		// Load the interface incoming config details
		this.messageConfig.loadIncomingOutgoingInterfaceConfig();

		MessageResponse response = null;
		for (IncomingESBMessageLog messageLog : incomingMessageLogs) {
			if (messageLog.isDuplicate()) {
				continue;
			}
			Set<String> endPointKeys = new HashSet<>();
			// Payload Headers
			Map<String, String> payloadHeaders = MessageResendUtils.getPayloadHeaders(messageLog.getInterfacingSystem(),
					null, MultiTenantUtility.getTenantIdFromContext(), false, messageLog.getIncomingESBMessageLogId());
			// RouteToInterface
			response = routeToInterface(messageLog.getMessage(), messageLog.getInterfacingSystem(), endPointKeys,
					messageConfig.incomingInterfaceConfigDetails(), messageLog.getMessageType(), payloadHeaders, true,
					false);
			if (Objects.nonNull(response)) {
				if (Objects.nonNull(response.getHttpStatus()) && response.getHttpStatus().is2xxSuccessful()) {
					messageLog.setProcessed(true);
					messageLog.setStatus(MessageResendConstants.PROCESSED);
				} else {
					messageLog.setProcessed(false);
					messageLog.setStatus(MessageResendConstants.FAILED);
					messageLog.setExceptionMessage(response.getErrorMessage());
				}
			}
		}
	}

	/**
	 * @param incomingFFMLogs
	 * @throws CustomException
	 */
	public boolean processFFM(List<IncomingFFMLog> incomingFFMLogs) throws CustomException {
		LOGGER.info("Incoming FFM HOLD Message Processor :: message process - {}");

		// Load the interface incoming config details
		this.messageConfig.loadIncomingOutgoingInterfaceConfig();

		MessageResponse response = null;
		for (IncomingFFMLog incomingFFMLog : incomingFFMLogs) {
			
			// Get initiated List
			if (!CollectionUtils.isEmpty(incomingFFMLog.getIncomingFFMLogDetails())) {
				List<IncomingFFMLogDetail> initiateIncomingDetails = incomingFFMLog.getIncomingFFMLogDetails().stream()
						.filter(obj -> obj.getMessageStatus().equalsIgnoreCase(INITIATED)).collect(Collectors.toList());
				// Fetch distinct partGroup from initiateList
				List<Integer> partGroup = initiateIncomingDetails.stream().map(obj -> obj.getPartGroup()).distinct()
						.collect(Collectors.toList());
				for (Integer part : partGroup) {
					Set<String> endPointKeys = new HashSet<>();
					String message = incomingFFMLog.getPartGroupMessage().get(part);
					// Payload Headers
					String systemName = initiateIncomingDetails.stream().findFirst().isPresent()?initiateIncomingDetails.stream().findFirst().get().getChannel():"";
					Map<String, String> payloadHeaders = MessageResendUtils.getPayloadHeaders(systemName, null,
							MultiTenantUtility.getTenantIdFromContext(), false, null);
					
					LOGGER.warn("Incoming processFFM Starting - Before Route - YYYXYZ ::  TIME - {}",
							 LocalDateTime.now());
					
					// RouteToInterface
					response = routeToInterface(message, BATCH, endPointKeys,
							messageConfig.incomingInterfaceConfigDetails(), FFM, payloadHeaders, true, true);
					
					LOGGER.warn("Incoming processFFM Starting - After Route - YYYXYZ ::  TIME - {}",
							 LocalDateTime.now());
					
					if (Objects.nonNull(response)) {
						if (Objects.nonNull(response.getHttpStatus()) && response.getHttpStatus().is2xxSuccessful()) {
							incomingFFMLog.setMessageProcessedTime(LocalDateTime.now());
							updateMessageStatus(incomingFFMLog, PROCESSED);
						} else {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param messageDetails
	 * @throws CustomException
	 */
	public void process(Collection<?> messageDetails) throws CustomException {
		LOGGER.info("Incoming ESB Message Processor :: message process - {}");

		// Load the interface incoming config details
		this.messageConfig.loadIncomingOutgoingInterfaceConfig();

		MessageDetail messageDetail = null;
		IncomingESBErrorMessageLog errorMessageLog = null;
		MessageResponse response = null;
		String messageType = null;
		for (Object message : messageDetails) {
			if (message instanceof MessageDetail) {
				messageDetail = (MessageDetail) message;
				errorMessageLog = (IncomingESBErrorMessageLog) messageDetail.getErrorMessageLog();
			}
			if (Objects.isNull(errorMessageLog.getIncomingESBResendMessageLog())) {
				errorMessageLog.setIncomingESBResendMessageLog(copyMessageLog(errorMessageLog));
			} else {
				errorMessageLog.getIncomingESBResendMessageLog()
						.setIncomingESBMessageLogId(errorMessageLog.getIncomingESBMessageLogId());
				errorMessageLog.getIncomingESBResendMessageLog()
						.setIncomingESBErrorMessageLogId(errorMessageLog.getIncomingESBErrorMessageLogId());
			}
			messageType = errorMessageLog.getMessageType();
			Set<String> endPointKeys = new HashSet<>();

			// Payload Headers
			Map<String, String> payloadHeaders = MessageResendUtils.getPayloadHeaders(
					errorMessageLog.getInterfacingSystem(), null, MultiTenantUtility.getTenantIdFromContext(), false,
					errorMessageLog.getIncomingESBMessageLogId());
			LocalDateTime messageTime = LocalDateTime.now();

			// RouteToInterface
			response = routeToInterface(messageDetail.getPayload().toString(), errorMessageLog.getInterfacingSystem(),
					endPointKeys, messageConfig.incomingInterfaceConfigDetails(), messageType, payloadHeaders, true,
					false);
			if (Objects.nonNull(response)) {
				if (Objects.nonNull(response.getHttpStatus()) && response.getHttpStatus().is2xxSuccessful()) {
					errorMessageLog.setProcessed(true);
					errorMessageLog.setStatus(PROCESSED);
					errorMessageLog.setErrorCode(RESENT);
					errorMessageLog.setErrorMessage(null);
					errorMessageLog.getIncomingESBResendMessageLog().setResentTime(messageTime);
				} else {
					errorMessageLog.setProcessed(false);
					errorMessageLog.setStatus(FAILED);
					errorMessageLog.setErrorCode(FAILED);
					errorMessageLog.setErrorMessage(response.getErrorMessage());
					errorMessageLog.getIncomingESBResendMessageLog().setFailedTime(messageTime);
					Integer retryLimit = errorMessageLog.getIncomingESBResendMessageLog().getRetryLimit();
					if (Objects.isNull(retryLimit) || retryLimit == 0) {
						retryLimit = 1;
					} else {
						retryLimit += 1;
					}
					errorMessageLog.getIncomingESBResendMessageLog().setRetryLimit(retryLimit + 1);
				}
			}
		}
	}

	/**
	 * @param errorMessageLog
	 * @return
	 */
	private IncomingESBResendMessageLog copyMessageLog(IncomingESBErrorMessageLog errorMessageLog) {
		IncomingESBResendMessageLog resendMessageLog = new IncomingESBResendMessageLog();
		resendMessageLog.setIncomingESBErrorMessageLogId(errorMessageLog.getIncomingESBErrorMessageLogId());
		resendMessageLog.setIncomingESBMessageLogId(errorMessageLog.getIncomingESBMessageLogId());
		return resendMessageLog;
	}

	/**
	 * @param incomingFFMLog
	 * @param process
	 */
	private void updateMessageStatus(IncomingFFMLog incomingFFMLog, String status) {
		incomingFFMLog.setMessageStatus(status);
		for (IncomingFFMLogDetail incomingFFMLogDetail : incomingFFMLog.getIncomingFFMLogDetails()) {
			if (Objects.equals(incomingFFMLogDetail.getMessageStatus(), INITIATED)) {
				incomingFFMLogDetail.setMessageStatus(status);
			}
		}
	}

}