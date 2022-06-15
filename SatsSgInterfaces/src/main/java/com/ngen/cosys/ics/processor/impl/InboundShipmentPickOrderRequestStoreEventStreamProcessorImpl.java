package com.ngen.cosys.ics.processor.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.esb.route.ConnectorService;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.esb.route.ICSEndPointURIGenerator;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.payload.InboundShipmentPickOrderRequestStoreEvent;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.SatsSgInterfacePayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.context.TenantContext;

@Component("InboundShipmentPickOrderRequest")
public class InboundShipmentPickOrderRequestStoreEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor {

	private static final String HTTP = "HTTP";

	private static final String COSYS = "COSYS";

	private static final String ICS = "ICS";

	private static final String SENT = "SENT";

	private static final String SUB_MESSAGE_TYPE = "DeliveryRequest";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(InboundShipmentPickOrderRequestStoreEventStreamProcessorImpl.class);

	@Autowired
	ConvertJSONToObject convertJSONToObject;

	@Autowired
	ConnectorLoggerService logger;

	@Autowired
	ConnectorService router;

	@Autowired
	ApplicationLoggerService loggerService;

	@Autowired
	private ICSEndPointURIGenerator urlGenerator;

	@Override
	public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException {

		SatsSgInterfacePayload payloadData = (SatsSgInterfacePayload) payload.getPayload();

		final String endPointURL = urlGenerator.getICSConnectorURL("retrieve-uld-at-requested-lane");
		LOGGER.debug(
				"Retrieve ULD At Request Lane In Stream Processor :: EndPoint URL ==========================  ::  ",
				endPointURL);

		@SuppressWarnings("unchecked")
		Map<String, Object> data = (Map<String, Object>) payloadData.getPayload();

		boolean isValidICSDevice = true;

		InboundShipmentPickOrderRequestStoreEvent eventData = new InboundShipmentPickOrderRequestStoreEvent();

		for (Entry<String, Object> entry : data.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("uldNumber")) {
				eventData.setUldNumber(String.valueOf(entry.getValue()));
			}
			if (entry.getKey().equalsIgnoreCase("outputExitLocation")) {
				eventData.setOutputExitLocation(String.valueOf(entry.getValue()));
			}
			if (String.valueOf(entry.getValue()).length() == 4) {
				eventData.setBinOrUld("BIN");
			} else {
				eventData.setBinOrUld("ULD");
			}
		}

		// Check if device starts with BT/MT then don't post the message
		String deviceNumber = eventData.getUldNumber();
		if (!StringUtils.isEmpty(deviceNumber) && (deviceNumber.startsWith("BT") || deviceNumber.startsWith("MT"))) {
			isValidICSDevice = false;
		}

		// If it is an valid device then send the message
		if (isValidICSDevice) {
			Object xmlStringPayload = JacksonUtility.convertObjectToXMLString(eventData);
			LOGGER.debug("Retrieve ULD At Request Lane In Stream Processor Xml Payload ::  ", xmlStringPayload);
			ResponseEntity<Object> response = null;
			try {
				BigInteger referenceId = null;
				LOGGER.debug("Request Payload {} ", xmlStringPayload);
				boolean loggerEnabled = true;
				BigInteger messageId = null;
				BigInteger errorMessageId = null;
				Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, ICS,
						TenantContext.getTenantId());
				response = router.sendPayloadDataToConnector(xmlStringPayload, endPointURL, MediaType.APPLICATION_XML,
						payloadHeaders);
				LOGGER.debug("Retrieve ULD At Request Lane Xml Payload ::  ", xmlStringPayload);
				LOGGER.debug("Retrieve ULD At Request Lane EndPointURL ::  ", endPointURL);
				LOGGER.debug("Retrieve ULD At Request Lane Payload Headers ::  ", payloadHeaders);
				if (Objects.nonNull(response)) {
					if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
						CustomException ex = null;
						if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
							ex = (CustomException) response.getBody();
							messageId = (BigInteger) ex.getPlaceHolders()[1];
							if (Objects.nonNull(ex.getPlaceHolders()[2])) {
								errorMessageId = (BigInteger) ex.getPlaceHolders()[2];
							}
							if (!loggerEnabled) {
								// LogINTO outgoing error message table
								insertOutgoingErrorMessage(messageId, response.getStatusCode(), "EXCEPTION");
							} else {
								// Error Message log update
								updateOutgoingErrorMessage(errorMessageId);
							}
						} else {
							// Partial Success Case
							if (loggerEnabled) {
								if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName())
										&& Objects.nonNull(response.getHeaders()
												.get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
									errorMessageId = new BigInteger(response.getHeaders()
											.get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
									// Error Message log update
									updateOutgoingErrorMessage(errorMessageId);
								}
							}
							// Success Case
							referenceId = logOutgoingMessage(xmlStringPayload, SUB_MESSAGE_TYPE);
							logOutgoingMessage(referenceId, SENT, SUB_MESSAGE_TYPE);
						}
					} else {
						if (loggerEnabled) {
							// Success Case
							referenceId = logOutgoingMessage(xmlStringPayload, SUB_MESSAGE_TYPE);
							logOutgoingMessage(referenceId, SENT, SUB_MESSAGE_TYPE);
							if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName())
									&& Objects.nonNull(
											response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
								errorMessageId = new BigInteger(response.getHeaders()
										.get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
								// Error Message log update
								updateOutgoingErrorMessage(errorMessageId);
							}
						}
					}
				}

			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error("No payload message for the given event", e);
				}
			}

			LOGGER.debug("Retrieve ULD At Request Lane Response ::  ", response);
		}
	}

	private void logOutgoingMessage(BigInteger referenceId, String status, String submessageType) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setOutMessageId(referenceId);
		outgoingMessage.setChannelSent(HTTP);
		outgoingMessage.setInterfaceSystem(ICS);
		outgoingMessage.setSenderOriginAddress(COSYS);
		outgoingMessage.setMessageType(ICS);
		outgoingMessage.setSubMessageType(submessageType);
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
		LOGGER.debug("logOutgoingMessage LOGGED FOR ICS FETCH ULD AT REQUEST LANE");
		logger.logOutgoingMessage(outgoingMessage);
	}

	private BigInteger logOutgoingMessage(Object payload, String submessageType) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setChannelSent(HTTP);
		outgoingMessage.setInterfaceSystem(ICS);
		outgoingMessage.setSenderOriginAddress(COSYS);
		outgoingMessage.setMessageType(ICS);
		outgoingMessage.setSubMessageType(submessageType);
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
		outgoingMessage.setStatus(SENT);
		outgoingMessage.setMessage((String) payload);
		loggerService.logInterfaceOutgoingMessage(outgoingMessage);
		LOGGER.debug("logOutgoingMessage LOGGED FOR ICS FETCH ULD AT REQUEST LANE");
		return outgoingMessage.getOutMessageId();
	}

	public BigInteger insertOutgoingErrorMessage(BigInteger messageId, HttpStatus httpStatus, String errorMessage) {
		OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();

		outgoingErrorMessage.setOutMessageId(messageId);
		outgoingErrorMessage.setErrorCode("EXCEPTION");
		if (Objects.nonNull(httpStatus)) {
			outgoingErrorMessage.setMessage(ConnectorUtils.getHttpStatusMessage(httpStatus));
		} else {
			if (Objects.nonNull(errorMessage)) {
				outgoingErrorMessage.setMessage(ConnectorUtils.getErrorMessage(errorMessage));
			}
		}
		outgoingErrorMessage.setLineItem(null);
		loggerService.logInterfaceOutgoingErrorMessage(outgoingErrorMessage);
		LOGGER.debug("insertOutgoingErrorMessage :: LOGGED FOR ICS FETCH ULD AT REQUEST LANE");
		return outgoingErrorMessage.getOutMessageId();
	}

	public void updateOutgoingErrorMessage(BigInteger errorMessageId) {
		OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();
		outgoingErrorMessage.setOutMessageErrorId(errorMessageId);
		loggerService.updateInterfaceOutgoingErrorMessage(outgoingErrorMessage);
	}

}
