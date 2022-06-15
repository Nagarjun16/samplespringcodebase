/**
 * {@link OutgoingMessageProcessor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.process;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.config.MessageResendConfig;
import com.ngen.cosys.message.resend.core.MessageDetail;
import com.ngen.cosys.message.resend.core.MessageResponse;
import com.ngen.cosys.message.resend.model.OutgoingErrorMessageLog;
import com.ngen.cosys.message.resend.model.OutgoingResendMessageLog;
import com.ngen.cosys.message.resend.util.MessageResendUtils;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

/**
 * Outgoing Message Resend Processor
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class OutgoingMessageProcessor extends MessageProcessor {

	@Autowired
	private MessageResendConfig messageConfig;

	/**
	 * @param messageDetails
	 * @throws CustomException
	 */
	public void process(Collection<?> messageDetails) throws CustomException {
		LOGGER.warn("Outgoing Message Processor :: message process - {}");

		// Load the interface incoming config details
		this.messageConfig.loadIncomingOutgoingInterfaceConfig();

		MessageDetail messageDetail = null;
		OutgoingErrorMessageLog errorMessageLog = null;
		String connectorURL = null;
		MessageResponse response = null;
		for (Object message : messageDetails) {
			if (message instanceof MessageDetail) {
				messageDetail = (MessageDetail) message;
				errorMessageLog = (OutgoingErrorMessageLog) messageDetail.getErrorMessageLog();
			}
			if (Objects.isNull(errorMessageLog.getOutgoingResendMessageLog())) {
				errorMessageLog.setOutgoingResendMessageLog(copyMessageLog(errorMessageLog));
			} else {
				errorMessageLog.getOutgoingResendMessageLog()
						.setOutgoingMessageLogId(errorMessageLog.getOutgoingMessageLogId());
				errorMessageLog.getOutgoingResendMessageLog()
						.setOutgoingErrorMessageLogId(errorMessageLog.getOutgoingErrorMessageLogId());
			}
			connectorURL = messageConfig.connectorConfigURL(errorMessageLog.getInterfacingSystem());
			// Construct payload headers
			Map<String, String> payloadHeaders = MessageResendUtils.getPayloadHeaders(
					errorMessageLog.getInterfacingSystem(), null, MultiTenantUtility.getTenantIdFromContext(), false,
					errorMessageLog.getOutgoingMessageLogId());
			LocalDateTime messageTime = LocalDateTime.now();
			try {
				response = route(messageDetail.getPayload(), connectorURL, errorMessageLog.getInterfacingSystem(),
						payloadHeaders, false);
				if (Objects.nonNull(response)) {
					if (Objects.nonNull(response.getHttpStatus()) && response.getHttpStatus().is2xxSuccessful()) {
						errorMessageLog.setProcessed(true);
						errorMessageLog.setStatus(MessageResendConstants.SENT);
						errorMessageLog.setErrorCode(MessageResendConstants.RESENT);
						errorMessageLog.setErrorMessage(null);
						errorMessageLog.getOutgoingResendMessageLog().setResentTime(messageTime);
					} else {
						errorMessageLog.setProcessed(false);
						errorMessageLog.setErrorCode(MessageResendConstants.FAILED);
						errorMessageLog.setErrorMessage(response.getErrorMessage());
						errorMessageLog.getOutgoingResendMessageLog().setFailedTime(messageTime);
						Integer retryLimit = errorMessageLog.getOutgoingResendMessageLog().getRetryLimit();
						if (Objects.isNull(retryLimit) || retryLimit == 0) {
							retryLimit = 1;
						} else {
							retryLimit += 1;
						}
						errorMessageLog.getOutgoingResendMessageLog().setRetryLimit(retryLimit + 1);
					}
				}
			} catch (Exception e) {
				errorMessageLog.setProcessed(false);
				errorMessageLog.setErrorCode(MessageResendConstants.FAILED);
				errorMessageLog.setErrorMessage(response.getErrorMessage());
				errorMessageLog.getOutgoingResendMessageLog().setFailedTime(messageTime);
				Integer retryLimit = errorMessageLog.getOutgoingResendMessageLog().getRetryLimit();
				if (Objects.isNull(retryLimit) || retryLimit == 0) {
					retryLimit = 1;
				} else {
					retryLimit += 1;
				}
				errorMessageLog.getOutgoingResendMessageLog().setRetryLimit(retryLimit + 1);
			}
		}
	}

	/**
	 * @param errorMessageLog
	 * @return
	 */
	private OutgoingResendMessageLog copyMessageLog(OutgoingErrorMessageLog errorMessageLog) {
		OutgoingResendMessageLog resendMessageLog = new OutgoingResendMessageLog();
		resendMessageLog.setOutgoingErrorMessageLogId(errorMessageLog.getOutgoingErrorMessageLogId());
		resendMessageLog.setOutgoingMessageLogId(errorMessageLog.getOutgoingMessageLogId());
		return resendMessageLog;
	}

	
	

	//


	/**
	 * @param messageDetails
	 * @throws CustomException
	 */
	public void processAisatsOutgoing(Collection<?> messageDetails) throws CustomException {
		LOGGER.warn("AISATS OUTGOING Outgoing Message Processor :: message process - {}");

		// Load the interface incoming config details
		this.messageConfig.loadIncomingOutgoingInterfaceConfigAisatsOutgoing();

		MessageDetail messageDetail = null;
		OutgoingErrorMessageLog errorMessageLog = null;
		String connectorURL = null;
		MessageResponse response = null;
		for (Object message : messageDetails) {
			if (message instanceof MessageDetail) {
				messageDetail = (MessageDetail) message;
				errorMessageLog = (OutgoingErrorMessageLog) messageDetail.getErrorMessageLog();
			}
			if (Objects.isNull(errorMessageLog.getOutgoingResendMessageLog())) {
				errorMessageLog.setOutgoingResendMessageLog(copyMessageLog(errorMessageLog));
			} else {
				errorMessageLog.getOutgoingResendMessageLog()
				.setOutgoingMessageLogId(errorMessageLog.getOutgoingMessageLogId());
				errorMessageLog.getOutgoingResendMessageLog()
				.setOutgoingErrorMessageLogId(errorMessageLog.getOutgoingErrorMessageLogId());
			}
			connectorURL = messageConfig.connectorConfigURLAisatsOutgoing(errorMessageLog.getInterfacingSystem());
			// Construct payload headers
			Map<String, String> payloadHeaders = MessageResendUtils.getPayloadHeaders(
					errorMessageLog.getInterfacingSystem(), null, MultiTenantUtility.getTenantIdFromContext(), false,
					errorMessageLog.getOutgoingMessageLogId());
			LocalDateTime messageTime = LocalDateTime.now();
			try {
				response = route(messageDetail.getPayload(), connectorURL, errorMessageLog.getInterfacingSystem(),
						payloadHeaders, false);
				if (Objects.nonNull(response)) {
					if (Objects.nonNull(response.getHttpStatus()) && response.getHttpStatus().is2xxSuccessful()) {
						errorMessageLog.setProcessed(true);
						errorMessageLog.setStatus(MessageResendConstants.SENT);
						errorMessageLog.setErrorCode(MessageResendConstants.RESENT);
						errorMessageLog.setErrorMessage(null);
						errorMessageLog.getOutgoingResendMessageLog().setResentTime(messageTime);
					} else {
						errorMessageLog.setProcessed(false);
						errorMessageLog.setErrorCode(MessageResendConstants.FAILED);
						errorMessageLog.setErrorMessage(response.getErrorMessage());
						errorMessageLog.getOutgoingResendMessageLog().setFailedTime(messageTime);
						Integer retryLimit = errorMessageLog.getOutgoingResendMessageLog().getRetryLimit();
						if (Objects.isNull(retryLimit) || retryLimit == 0) {
							retryLimit = 1;
						} else {
							retryLimit += 1;
						}
						errorMessageLog.getOutgoingResendMessageLog().setRetryLimit(retryLimit + 1);
					}
				}
			} catch (Exception e) {
				errorMessageLog.setProcessed(false);
				errorMessageLog.setErrorCode(MessageResendConstants.FAILED);
				errorMessageLog.setErrorMessage(response.getErrorMessage());
				errorMessageLog.getOutgoingResendMessageLog().setFailedTime(messageTime);
				Integer retryLimit = errorMessageLog.getOutgoingResendMessageLog().getRetryLimit();
				if (Objects.isNull(retryLimit) || retryLimit == 0) {
					retryLimit = 1;
				} else {
					retryLimit += 1;
				}
				errorMessageLog.getOutgoingResendMessageLog().setRetryLimit(retryLimit + 1);
			}
		}
	}





}
