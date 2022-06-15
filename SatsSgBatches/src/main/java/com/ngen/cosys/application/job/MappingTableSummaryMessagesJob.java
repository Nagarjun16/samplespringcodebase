package com.ngen.cosys.application.job;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.AirmailStatus.Enums.AirmailStatusEnums;
import com.ngen.cosys.application.service.MssbatchesServiceImpl;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerServiceImpl;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.MappingTableSummaryModel;
import com.ngen.cosys.model.MssMessageParentModel;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;
import com.ngen.cosys.scheduler.esb.connector.ConnectorUtils;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class MappingTableSummaryMessagesJob extends AbstractCronJob {
   @Autowired
   private MssbatchesServiceImpl service;
   @Autowired
   private ConnectorPublisher publisher;
   
   @Autowired
   ApplicationLoggerService logger;
   @Autowired
   private ConnectorLoggerService connectorLogger;

   private static final String messageName = "TGFMCMBS";

   private static final String qname = "MSS_TMSG";

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
    * JobExecutionContext)
    */

   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      super.executeInternal(jobExecutionContext);
      MssMessageParentModel<List<MappingTableSummaryModel>> data = null;
      try {
         data = service.mappingTableSummaryMessages(messageName);
      } catch (CustomException e) {
         throw new JobExecutionException(e.getCause());
      }
      // Send batch job data to Connector framework
      if (!CollectionUtils.isEmpty((List) data.getData())) {
         delegateToConnector(data, messageName);
      }
   }

   private void delegateToConnector(MssMessageParentModel data, String messageName) {
      if (!CollectionUtils.isEmpty((List) data.getData())) {
         Object payload = this.constructJSONObject(data);
         BigInteger messageId = null;
         BigInteger errorMessageId = null;
         boolean loggerEnabled = false;
         if (!loggerEnabled) {
            messageId = this.insertOutgoingMessage(payload, messageName);
         }
         Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, null,TenantContext.getTenantId());
         ResponseEntity<Object> response = publisher.sendJobDataToConnector(payload, this.qname,
               MediaType.APPLICATION_JSON, payloadHeaders);
         boolean errorType = false;
         if (Objects.nonNull(response)) {
            String exceptionMsg = null;
            if (Objects.equals(HttpStatus.BAD_REQUEST, response.getStatusCode())) {
               CustomException ex = null;
               if (Objects.nonNull(response.getBody()) && response.getBody() instanceof CustomException) {
                  ex = (CustomException) response.getBody();
                  exceptionMsg = ex.getErrorCode();
                  if ((Map<String, Object>) ex.getPlaceHolders()[1] instanceof Map) {
                     System.out.println("IF PART");
                     Map<String, Object> resultPayload = (Map<String, Object>) ex.getPlaceHolders()[1];
                     if (!loggerEnabled) {
                        // LogINTO outgoing error message table
                        messageId = new BigInteger(
                              ConnectorUtils.getObjectValue(ESBRouterTypeUtils.MESSAGE_ID.getName(), resultPayload));
                        errorMessageId = new BigInteger(ConnectorUtils
                              .getObjectValue(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName(), resultPayload));
                     } else {
                        messageId = null;
                        errorMessageId = null;
                     }
                  } else {
                     System.out.println("ELSE PART");
                     System.out.println("type of placeholder value " + ex.getPlaceHolders()[1]);
                  }
               } else {
                  // Partial Success Case
                  if (!loggerEnabled) {
                     messageId = new BigInteger(
                           response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                     if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects
                           .nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
                        errorMessageId = new BigInteger(
                              response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
                     }
                  }
                  System.out.println("Partial Success");
               }
            } else {
               // Success Case
               if (loggerEnabled) {
                  messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                  if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName())
                        && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
                     errorMessageId = new BigInteger(
                           response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
                  }
               }
               System.out.println("Success");
               //
            }
         }
         // Update Status
         this.updateMessageStatus(messageId, errorType);
      }
   }

   /*
    * (non-Javadoc)
    * 
    * will convert the data which is of type list into object with fields having
    * its local name
    */
   private Object constructJSONObject(MssMessageParentModel flightData) {
      // Convert object to JSON string
      Object payload = JacksonUtility.convertObjectToJSONString(flightData);
      return payload;
   }

   // will insert the outgoing message having the payload
   public BigInteger insertOutgoingMessage(Object payload, String messageName) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setChannelSent(AirmailStatusEnums.CHANNEL.toString());
      outgoingMessage.setInterfaceSystem(AirmailStatusEnums.MSS.toString());
      outgoingMessage.setSenderOriginAddress(AirmailStatusEnums.COSYS.toString());
      outgoingMessage.setMessageType(AirmailStatusEnums.MSS.toString());
      outgoingMessage.setSubMessageType(messageName);
      outgoingMessage.setCarrierCode(null);
      outgoingMessage.setFlightNumber(null);
      outgoingMessage.setFlightOriginDate(null);
      outgoingMessage.setShipmentNumber(null);
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(null);
      outgoingMessage.setMessage(payload.toString());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus("PROCESSED");
      logger.logInterfaceOutgoingMessage(outgoingMessage);
      //
      return outgoingMessage.getOutMessageId();
   }

   public void updateMessageStatus(BigInteger referenceId, boolean errorType) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setOutMessageId(referenceId);
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      if (errorType) {
         outgoingMessage.setStatus("FAILURE");
      } else {
         outgoingMessage.setStatus("SENT");
      }
      connectorLogger.logOutgoingMessage(outgoingMessage);
   }
}
