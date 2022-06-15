package com.ngen.cosys.application.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.service.ICSBatchesService;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.esb.connector.router.ESBConnectorService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ICSOperativeFlightRequestModel;
import com.ngen.cosys.model.ICSOperativeFlightResponseModel;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.ConnectorUtils;
import com.ngen.cosys.scheduler.esb.connector.ICSEndPointURIGenerator;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

@NgenCosysAppInfraAnnotation(path = "api/ics")
public class ICSAdochDataPushDummyController {

	private final Logger LOGGER = LoggerFactory.getLogger(ICSAdochDataPushDummyController.class);
	@Autowired
	private ICSBatchesService icsBatchesService;

	@Autowired
	private ESBConnectorService connerctorService;

	@Autowired
	ConnectorLoggerService connectorLoggerService;

	@Autowired
	ApplicationLoggerService loggerService;

	@Autowired
	private ICSEndPointURIGenerator urlGenerator;

	private ICSOperativeFlightRequestModel operativeFlightDetails;

	@PostMapping(value = "/adochdatapush", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ICSOperativeFlightResponseModel> performBatchOperation() {

		final String endPointURL = urlGenerator.getICSConnectorURL("operative-flight-details");
		ICSOperativeFlightResponseModel responseModel = null;
		BigInteger referenceId = null;
		ResponseEntity<Object> response = null;
		try {
			LOGGER.warn("ICS OPERATIVE FLIGHT ADOCH JOB STARTED");
			operativeFlightDetails = icsBatchesService.getAdhocPushFlightDetails();
			Object xmlPayload = JacksonUtility.convertObjectToXMLString(operativeFlightDetails);
			LOGGER.info("ICS OPERATIVE FLIGHT ADOCH JOB :: " + xmlPayload.toString());
			boolean loggerEnabled = true;
			BigInteger messageId = null;

			if (!ObjectUtils.isEmpty(xmlPayload)) {
				// Default message status
				String status = "SENT";
				// Log the first entry
				Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, "ICS",
						TenantContext.getTenantId());
				payloadHeaders.put(ESBRouterTypeUtils.COSYS_ESB.getName(), "false");
				response = connerctorService.route(xmlPayload, endPointURL, MediaType.APPLICATION_XML, payloadHeaders);
				referenceId = logOutgoingMessage((String) xmlPayload);
				// If there are any response errors then log the status as ERROR
				if (Objects.nonNull(response) && Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())
						&& (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException)) {
					status = "ERROR";
				}
				// Update the status of message
				logOutgoingMessage(referenceId, status);
				LOGGER.warn("ICS OPERATIVE FLIGHT ADOCH JOB END");
			}
			icsBatchesService.updateSystenParamCreatedDateTime(LocalDateTime.now());

		} catch (CustomException e) {
			// Log into server
			LOGGER.info("ICS OPERATIVE FLIGHT JOB FAILED " + e);
		}
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
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
		connectorLoggerService.logOutgoingMessage(outgoingMessage);
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
		return outgoingMessage.getOutMessageId();
	}

}
