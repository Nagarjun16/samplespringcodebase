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
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.AirmailStatus.Enums.AirmailStatusEnums;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.PushMailBagRequestModel;
import com.ngen.cosys.satssg.singpost.service.ProduceBagHandoverToAirlineService;
import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ProduceBagHandoverToAirlineJob extends AbstractCronJob {

   @Autowired
   private ProduceBagHandoverToAirlineService produceBagHandoverToAirlineService;
   
   @Autowired
   private ConnectorPublisher publisher;
   
   @Autowired
   private ConnectorLoggerService connectorLogger;
   
   @Autowired
   ApplicationLoggerService logger;
   
   private static final Logger LOG = LoggerFactory.getLogger(ProduceBagHandoverToAirlineJob.class);
   
   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
    * JobExecutionContext)
    */            
   @Override
   protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      LOG.warn("Inside Singpost HA message");
      super.executeInternal(jobExecutionContext);
      PushMailBagRequestModel result = null;
      Object value = jobExecutionContext.getJobDetail().getJobDataMap().get("STARTHOURS");
      try {
          result = produceBagHandoverToAirlineService.pushBagHandoverToAirlineStatus(value);
          LOG.warn("Inside Singpost HA message Data:", result.getMailBag());
      } catch (CustomException e) {
         e.printStackTrace();
      }
      // Send batch job data to Connector framework
      String qname = "SINGPOST";
      Object payload = this.constructXMLObject(result);
      if (!CollectionUtils.isEmpty(result.getMailBag())) {
         //
         BigInteger referenceId = this.insertOutgoingMessage(payload);

         Object resultPayload = publisher.sendJobDataToConnector(payload, qname, MediaType.APPLICATION_JSON, null);
         //
         boolean errorType = false;
         if (Objects.nonNull(resultPayload) && resultPayload instanceof Map) {
            // TODO: Actual Response Payload Status update
            Map<String, Object> response = (Map<String, Object>) resultPayload;
            String systemName = (String) response.get("systemName");
         } else {
            errorType = true;
         }
         // Update Status
         //this.updateMessageStatus(referenceId, errorType);
      }
   }

   public BigInteger insertOutgoingMessage(Object payload) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setChannelSent(AirmailStatusEnums.CHANNEL.toString());
      outgoingMessage.setInterfaceSystem(AirmailStatusEnums.SINGPOST.toString());
      outgoingMessage.setSenderOriginAddress(AirmailStatusEnums.COSYS.toString());
      outgoingMessage.setMessageType(AirmailStatusEnums.SINGPOST.toString());
      outgoingMessage.setSubMessageType(AirmailStatusEnums.HA.toString());
      outgoingMessage.setCarrierCode(null);
      outgoingMessage.setFlightNumber(null);
      outgoingMessage.setFlightOriginDate(null);
      outgoingMessage.setShipmentNumber(null);
      outgoingMessage.setShipmentDate(null);
      outgoingMessage.setRequestedOn(LocalDateTime.now());
      outgoingMessage.setSentOn(LocalDateTime.now());
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      outgoingMessage.setMessage(payload.toString());
      outgoingMessage.setVersionNo(1);
      outgoingMessage.setSequenceNo(1);
      outgoingMessage.setMessageContentEndIndicator(null);
      outgoingMessage.setStatus(AirmailStatusEnums.SENT.toString());
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

   private Object constructXMLObject(PushMailBagRequestModel result) {
      //
      Object payload = JacksonUtility.convertObjectToXMLString(result);
      return payload;
   }

}
