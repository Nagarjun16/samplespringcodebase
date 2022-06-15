package com.ngen.cosys.satssg.singpost.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ngen.cosys.AirmailStatus.Enums.AirmailStatusEnums;
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.FlightTouchDownBase;
import com.ngen.cosys.satssg.singpost.model.PushMailBagRequestModel;
import com.ngen.cosys.satssg.singpost.service.ProduceBagHandoverToAirlineService;
import com.ngen.cosys.satssg.singpost.service.ProduceBagReceivingScanStatusService;
import com.ngen.cosys.satssg.singpost.service.ProduceFlightTouchDownService;
import com.ngen.cosys.satssg.singpost.service.ProduceHandoverToDNATAService;
import com.ngen.cosys.satssg.singpost.service.ProduceOffloadService;
import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation
public class SingpostOutgoingMessageController {

   private static final String qname = "SINGPOST";

   private static final Logger lOGGER = LoggerFactory.getLogger(SingpostOutgoingMessageController.class);

   public enum SingPostMessage {
      AA("AA"), TA("TA"), LD("LD"), HA("HA"), OF("OF"), DLV("DLV");

      private final String type;

      private SingPostMessage(String value) {
         this.type = value;
      }

      /*
       * (non-Javadoc)
       * 
       * @see java.lang.Enum#toString()
       */
      @Override
      public String toString() {
         return String.valueOf(this.type);
      }

      /**
       * Returns the ENUM for the specified String.
       * 
       * @param value
       * @return
       */
      @JsonCreator
      public static SingPostMessage fromValue(String value) {
         for (SingPostMessage eType : values()) {
            if (eType.type.equalsIgnoreCase(value)) {
               return eType;
            }
         }
         throw new IllegalArgumentException(
               "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
      }
   }

   private void validateMessage(String messageName) throws CustomException {
      try {
         SingPostMessage message = SingPostMessage.fromValue(messageName);
         lOGGER.debug(message.toString());
      } catch (IllegalArgumentException ex) {
         lOGGER.debug(ex.getMessage());
         throw new CustomException("datamessageinvalidSingpost", null, ErrorType.ERROR);
      }
   }

   @Autowired
   ProduceBagReceivingScanStatusService aaService;
   @Autowired
   ProduceFlightTouchDownService ldService;
   @Autowired
   ProduceHandoverToDNATAService taService;
   @Autowired
   ProduceBagHandoverToAirlineService haService;
   @Autowired
   ProduceOffloadService ofService;
   @Autowired
   private ConnectorPublisher publisher;
   @Autowired
   ApplicationLoggerService logger;

   public BigInteger insertOutgoingMessage(Object payload, String subMessageType) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setChannelSent(AirmailStatusEnums.CHANNEL.toString());
      outgoingMessage.setInterfaceSystem(AirmailStatusEnums.SINGPOST.toString());
      outgoingMessage.setSenderOriginAddress(AirmailStatusEnums.COSYS.toString());
      outgoingMessage.setMessageType(AirmailStatusEnums.SINGPOST.toString());
      outgoingMessage.setSubMessageType(subMessageType);
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

   private void delegateToMessageConnector(Object payload, String subMessageType) {
      //
      BigInteger referenceId = this.insertOutgoingMessage(payload, subMessageType);

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
      // this.updateMessageStatus(referenceId, errorType);
   }

   private Object constructXMLObject(PushMailBagRequestModel result) {
      Object payload = JacksonUtility.convertObjectToXMLString(result);
      return payload;
   }

   private Object constructXMLObject(FlightTouchDownBase result) {
      Object payload = JacksonUtility.convertObjectToXMLString(result);
      return payload;
   }

   private void sendSingPostOFMessage() throws CustomException {
      PushMailBagRequestModel result = ofService.pushOffloadStatus(null);
      Object payload = this.constructXMLObject(result);
      if (!CollectionUtils.isEmpty(result.getMailBag())) {
         delegateToMessageConnector(payload, SingPostMessage.OF.toString());
      }
   }

   private void sendSingPostHAMessage() throws CustomException {
      PushMailBagRequestModel result = haService.pushBagHandoverToAirlineStatus(null);
      Object payload = this.constructXMLObject(result);
      if (!CollectionUtils.isEmpty(result.getMailBag())) {
         delegateToMessageConnector(payload, SingPostMessage.HA.toString());
      }
   }

   private void sendSingPostLDMessage() throws CustomException {
      FlightTouchDownBase result = ldService.pushFlightTouchDownStatus(null);
      Object payload = this.constructXMLObject(result);
      if (!CollectionUtils.isEmpty(result.getMailBag())) {
         delegateToMessageConnector(payload, SingPostMessage.TA.toString());
      }
   }

   private void sendSingPostTAMessage() throws CustomException {
      PushMailBagRequestModel result = taService.pushHandoverToDNATAStatus(null);
      Object payload = this.constructXMLObject(result);
      if (!CollectionUtils.isEmpty(result.getMailBag())) {
         delegateToMessageConnector(payload, SingPostMessage.TA.toString());
      }

   }

   private void sendSingPostAAMessage() throws CustomException {
      PushMailBagRequestModel result = aaService.pushBagReceivingScanStatus(null);
      Object payload = this.constructXMLObject(result);
      if (!CollectionUtils.isEmpty(result.getMailBag())) {
         delegateToMessageConnector(payload, SingPostMessage.AA.toString());
      }
   }

   @Transactional
   @RequestMapping(value = "/api/interface/singpost/{messageName}", method = RequestMethod.POST)
   public String performMessageOperation(
         @ApiParam(value = "Message Name", required = true) @PathVariable("messageName") String messageName)
         throws CustomException {
      validateMessage(messageName);
      if (SingPostMessage.AA.equals(SingPostMessage.fromValue(messageName))) {
         sendSingPostAAMessage();
      } else if (SingPostMessage.TA.equals(SingPostMessage.fromValue(messageName))) {
         sendSingPostTAMessage();
      } else if (SingPostMessage.LD.equals(SingPostMessage.fromValue(messageName))) {
         sendSingPostLDMessage();
      } else if (SingPostMessage.HA.equals(SingPostMessage.fromValue(messageName))) {
         sendSingPostHAMessage();
      } else if (SingPostMessage.OF.equals(SingPostMessage.fromValue(messageName))) {
         sendSingPostOFMessage();
      }
      return messageName + " Processed Successfully";

   }

}
