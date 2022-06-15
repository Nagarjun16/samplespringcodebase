package com.ngen.cosys.application.job;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.util.StringUtils;

import com.ngen.cosys.application.dao.ManifestReconcillationStatementDAO;
import com.ngen.cosys.application.service.ProduceManifestReconcillationStatementMessageService;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ManifesrReconciliationStatementModel;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;
import com.ngen.cosys.scheduler.esb.connector.ConnectorUtils;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ManifestReconcillationStatementJob extends AbstractCronJob {

   private static final Logger LOGGER = LoggerFactory.getLogger(ManifestReconcillationStatementJob.class);

   @Autowired
   private ProduceManifestReconcillationStatementMessageService mrsService;

   @Autowired
   private ManifestReconcillationStatementDAO manifestReconcillationStatementDAO;

   @Autowired
   ConnectorPublisher publisher;

   @Autowired
   ApplicationLoggerService loggerService;

   @Autowired
   ProduceManifestReconcillationStatementMessageService produceManifestReconcillationStatementMessageService;

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      super.executeInternal(jobExecutionContext);
      try {
         List<ManifesrReconciliationStatementModel> flightData = manifestReconcillationStatementDAO
               .getFlightsToSendMrs();
         for (ManifesrReconciliationStatementModel flight : flightData) {

            ManifesrReconciliationStatementModel shipmentData = manifestReconcillationStatementDAO
                  .getConsolidatedMrsInfo(flight);
            if (null != shipmentData) {

               boolean loggerEnabled = false;
               BigInteger messageId = null;

               try {
                  String mrsMessage = mrsService.buildManifestReconcillationStatementMesaage(shipmentData);

                  if (!loggerEnabled) {
                     messageId = logOutgoingMessage(mrsMessage, shipmentData.getFlightNumber(),
                           shipmentData.getScheduledFlightDate());
                  }
                  Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, "CMD",
                        TenantContext.getTenantId());
                  ResponseEntity<Object> response = publisher.sendJobDataToConnector(mrsMessage, "CCN",
                        MediaType.APPLICATION_JSON, payloadHeaders);

                  if (Objects.nonNull(response)) {
                     if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
                        CustomException ex = null;
                        if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
                           ex = (CustomException) response.getBody();
                           messageId = (BigInteger) ex.getPlaceHolders()[1];
                        } else {
                           // Partial Success Case
                           if (loggerEnabled) {
                              messageId = new BigInteger(
                                    response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                           }

                        }
                     } else {
                        // Success Case
                        if (loggerEnabled) {
                           messageId = new BigInteger(
                                 response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                        }
                     }
                  }
                  // Update the MRS Sent status
                  produceManifestReconcillationStatementMessageService.updateMrsSentDateToCustomsFlight(flight);

                  logOutgoingMessage(messageId, "SENT", shipmentData.getFlightNumber(),
                        shipmentData.getScheduledFlightDate());
               } catch (Exception e) {
                  LOGGER.error("Exception while constructing/sending MRS message", e);
                  // Log Outgoing message
                  if (messageId != null) {
                     logOutgoingMessage(messageId, "EXCEPTION", shipmentData.getFlightNumber(),
                           shipmentData.getScheduledFlightDate());
                  }
               }
            }
         }
      } catch (Exception e) {
         LOGGER.error("Exception while executing the MRS Job", e);
      }
   }

   private void logOutgoingMessage(BigInteger referenceId, String status, String flightKey,
         LocalDate scheduledFlightDate) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setOutMessageId(referenceId);
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("CCN");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("MRS");

      // Set flight info
      if (!StringUtils.isEmpty(flightKey)) {
         outgoingMessage.setCarrierCode(flightKey.substring(0, 2));
         outgoingMessage.setFlightNumber(flightKey.substring(2));
      }

      outgoingMessage.setFlightOriginDate(LocalDateTime.of(scheduledFlightDate.getYear(),
            scheduledFlightDate.getMonthValue(), scheduledFlightDate.getDayOfMonth(), 0, 0));
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus(status);
      loggerService.logOutgoingMessage(outgoingMessage);
   }

   private BigInteger logOutgoingMessage(String payload, String flightKey, LocalDate scheduledFlightDate) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      outgoingMessage.setChannelSent("HTTP");
      outgoingMessage.setInterfaceSystem("CCN");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("MRS");

      // Set flight info
      if (!StringUtils.isEmpty(flightKey)) {
         outgoingMessage.setCarrierCode(flightKey.substring(0, 2));
         outgoingMessage.setFlightNumber(flightKey.substring(2));
      }

      outgoingMessage.setFlightOriginDate(LocalDateTime.of(scheduledFlightDate.getYear(),
            scheduledFlightDate.getMonthValue(), scheduledFlightDate.getDayOfMonth(), 0, 0));
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