package com.ngen.cosys.application.job;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.application.service.ICSBatchesService;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.esb.connector.router.ESBConnectorService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ICSOperativeFlightRequestModel;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.ConnectorUtils;
import com.ngen.cosys.scheduler.esb.connector.ICSEndPointURIGenerator;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ICSOperativeFlightAdhocPushJob extends AbstractCronJob {

	private final Logger LOGGER = LoggerFactory.getLogger(ICSOperativeFlightAdhocPushJob.class);
	@Autowired
	private ICSBatchesService icsBatchesService;

	@Autowired
	private ESBConnectorService connerctorService;

	@Autowired
	ConnectorLoggerService logger;

	@Autowired
	ApplicationLoggerService loggerService;

	@Autowired
	private ICSEndPointURIGenerator urlGenerator;

	private ICSOperativeFlightRequestModel operativeFlightDetails;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		final String endPointURL = urlGenerator.getICSConnectorURL("operative-flight-details");
		BigInteger referenceId = null;
		ResponseEntity<Object> response = null;

		try {
			LOGGER.warn("ICS OPERATIVE FLIGHT ADOCH JOB STARTED");
			operativeFlightDetails = icsBatchesService.getAdhocPushFlightDetails();
			if (!CollectionUtils.isEmpty(operativeFlightDetails.getOperativeFlightList())) {
				Object xmlPayload = JacksonUtility.convertObjectToXMLString(operativeFlightDetails);
				LOGGER.info("ICS OPERATIVE FLIGHT ADOCH JOB PAYLOAD:: " + xmlPayload.toString());
				boolean loggerEnabled = true;
				BigInteger messageId = null;
				if (!StringUtils.isEmpty(xmlPayload)) {
					// Default message status
					String status = "SENT";
					// Log the first entry

					Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled,
							"ICS",TenantContext.getTenantId());
					payloadHeaders.put(ESBRouterTypeUtils.COSYS_ESB.getName(), "false");
					response = connerctorService.route(xmlPayload, endPointURL, MediaType.APPLICATION_XML,
							payloadHeaders);
					referenceId = logOutgoingMessage((String) xmlPayload);
					// If there are any response errors then log the status as ERROR
					if (Objects.nonNull(response) && Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())
							&& (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException)) {
						status = "ERROR";
					}
					// Update the status of message
					LOGGER.warn("ICS OPERATIVE FLIGHT ADOCH JOB END");
					logOutgoingMessage(referenceId, status);

				}
			}
			icsBatchesService.updateSystenParamCreatedDateTime(LocalDateTime.now());
		} catch (CustomException e) {
			// Log into server
			LOGGER.info("ICS OPERATIVE FLIGHT JOB FAILED " + e);
		}
	}

	private void logOutgoingMessage(BigInteger referenceId, String status) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setOutMessageId(referenceId);
		outgoingMessage.setChannelSent("HTTP");
		outgoingMessage.setInterfaceSystem("ICS");
		outgoingMessage.setSenderOriginAddress("COSYS");
		outgoingMessage.setMessageType("ICS");
		outgoingMessage.setSubMessageType("ICSAdochFlights");
		outgoingMessage.setCarrierCode(null);
		outgoingMessage.setFlightNumber(null);
		outgoingMessage.setFlightOriginDate(null);
		outgoingMessage.setShipmentNumber(null);
		outgoingMessage.setShipmentDate(null);
		outgoingMessage.setRequestedOn(LocalDateTime.now());
		outgoingMessage.setSentOn(LocalDateTime.now());
		outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
		outgoingMessage.setVersionNo(1);
		outgoingMessage.setSequenceNo(1);
		outgoingMessage.setMessageContentEndIndicator(null);
		outgoingMessage.setStatus(status);
		logger.logOutgoingMessage(outgoingMessage);
		LOGGER.warn("ICS OPERATIVE FLIGHT ADOCH JOB UPDATED IN OUTGOING MESSAGE LOG");
	}

	private BigInteger logOutgoingMessage(String payload) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setChannelSent("HTTP");
		outgoingMessage.setInterfaceSystem("ICS");
		outgoingMessage.setSenderOriginAddress("COSYS");
		outgoingMessage.setMessageType("ICS");
		outgoingMessage.setSubMessageType("ICSAdochFlights");
		outgoingMessage.setCarrierCode(null);
		outgoingMessage.setFlightNumber(null);
		outgoingMessage.setFlightOriginDate(null);
		outgoingMessage.setShipmentNumber(null);
		outgoingMessage.setShipmentDate(null);
		outgoingMessage.setRequestedOn(LocalDateTime.now());
		outgoingMessage.setSentOn(LocalDateTime.now());
		outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
		outgoingMessage.setVersionNo(1);
		outgoingMessage.setSequenceNo(1);
		outgoingMessage.setMessageContentEndIndicator(null);
		outgoingMessage.setStatus("SENT");
		outgoingMessage.setMessage(payload);
		loggerService.logInterfaceOutgoingMessage(outgoingMessage);
		LOGGER.warn("ICS OPERATIVE FLIGHT ADOCH JOB UPDATED IN OUTGOING MESSAGE LOG");
		return outgoingMessage.getOutMessageId();
	}

}
