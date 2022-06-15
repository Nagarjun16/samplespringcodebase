package com.ngen.cosys.application.job;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

import com.ngen.cosys.application.service.ICSBatchesService;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.esb.connector.router.ESBConnectorService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ICSOperativeFlightRequestModel;
import com.ngen.cosys.model.OperativeFlightModel;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.ConnectorUtils;
import com.ngen.cosys.scheduler.esb.connector.ICSEndPointURIGenerator;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ICSOperativeFlightDataJob extends AbstractCronJob {

	private final Logger LOGGER = LoggerFactory.getLogger(ICSOperativeFlightDataJob.class);
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
			LOGGER.warn("ICS OPERATIVE FLIGHT JOB STARTED");
			operativeFlightDetails = icsBatchesService.getOperativeFlightDetails();
			if (!CollectionUtils.isEmpty(operativeFlightDetails.getOperativeFlightList())) {
				BigDecimal divideCount = BigDecimal.valueOf(500);
				// Total Operative flight list Size
				BigDecimal totalOperativeFlightListCount = BigDecimal.valueOf(operativeFlightDetails.getOperativeFlightList().size());
				LOGGER.warn("ICS OPERATIVE FLIGHT JOB FLIGHT LIST SIZE -" + totalOperativeFlightListCount);

				BigDecimal countForPayload = totalOperativeFlightListCount.divide(divideCount);
				double rounOffCount = Math.ceil(countForPayload.doubleValue());
				int flightListPayloadCount = (int) rounOffCount;
				int startIndex = 0;
				int lastIndex = 499;
				for (int i = 1; i <= flightListPayloadCount; i++) {
					ICSOperativeFlightRequestModel requestModel = new ICSOperativeFlightRequestModel();
					List<OperativeFlightModel> specificList = new ArrayList<OperativeFlightModel>();
					if (flightListPayloadCount == i) {
						specificList = operativeFlightDetails.getOperativeFlightList().subList(startIndex,
								operativeFlightDetails.getOperativeFlightList().size());
					} else {
						specificList = operativeFlightDetails.getOperativeFlightList().subList(startIndex,
								lastIndex * i);
					}
					requestModel.setOperativeFlightList(specificList);
					startIndex = lastIndex * i;

					Object xmlPayload = JacksonUtility.convertObjectToXMLString(requestModel);
					LOGGER.info("ICS OPERATIVE FLIGHT JOB PAYLOAD" + xmlPayload.toString());
					boolean loggerEnabled = true;
					BigInteger messageId = null;
					if (!ObjectUtils.isEmpty(xmlPayload)) {
						// Default message statuse
						String status = "SENT";
						// Log the first entry
						Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled,
								"ICS", TenantContext.getTenantId());
						payloadHeaders.put(ESBRouterTypeUtils.COSYS_ESB.getName(), "false");
						//Sending Payload to ESB
						response = connerctorService.route(xmlPayload, endPointURL, MediaType.APPLICATION_XML,
								payloadHeaders);
						referenceId = logOutgoingMessage((String) xmlPayload);
						// If there are any response errors then log the status as ERROR
						if (Objects.nonNull(response)
								&& Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())
								&& (Objects.nonNull(response.getBody())
										&& response.getBody() instanceof CustomException)) {
							status = "ERROR";
						}
						// Update the status of message
						logOutgoingMessage(referenceId, status);
					}
				}
				LOGGER.warn("ICS OPERATIVE FLIGHT JOB END");
			}
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
		outgoingMessage.setSubMessageType("ICSFlights");
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
	}

	private BigInteger logOutgoingMessage(String payload) {
		OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
		outgoingMessage.setChannelSent("HTTP");
		outgoingMessage.setInterfaceSystem("ICS");
		outgoingMessage.setSenderOriginAddress("COSYS");
		outgoingMessage.setMessageType("ICS");
		outgoingMessage.setSubMessageType("ICSFlights");
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
