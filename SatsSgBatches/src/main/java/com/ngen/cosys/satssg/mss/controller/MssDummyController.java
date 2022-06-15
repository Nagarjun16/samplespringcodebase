package com.ngen.cosys.satssg.mss.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.service.MssbatchesServiceImpl;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.enums.ESBRouterTypeUtils;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.AgentListMessageModel;
import com.ngen.cosys.model.CloseTransitMessagesModel;
import com.ngen.cosys.model.EmbargoRulesMessageModel;
import com.ngen.cosys.model.EnroutementRulesMessagesModel;
import com.ngen.cosys.model.MappingTableDetailModel;
import com.ngen.cosys.model.MappingTableSummaryModel;
import com.ngen.cosys.model.MssMessageParentModel;
import com.ngen.cosys.model.MssModelOperativeFlightData;
import com.ngen.cosys.model.ULDBuildupRulesModel;
import com.ngen.cosys.model.XRayRulesMessagesModel;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;
import com.ngen.cosys.scheduler.esb.connector.ConnectorUtils;
import com.ngen.cosys.scheduler.esb.connector.jackson.util.JacksonUtility;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation
public class MssDummyController {

   private static final String qname = "MSS_TMSG";
   
   private static final Logger lOGGER = LoggerFactory.getLogger(MssDummyController.class);

   public enum MssMessage {
      TGFMOUFL("TGFMOUFL"), TGFMCPRE("TGFMCPRE"), TGFMCMBS("TGFMCMBS"), TGFMCMBD("TGFMCMBD"), TGFMEMBL(
            "TGFMEMBL"), TGFMENRL("TGFMENRL"), TGFMTRML(
                  "TGFMTRML"), TGFMUCRL("TGFMUCRL"), TGFMAGTL("TGFMAGTL"), TGFMCLTL("TGFMCLTL"), TGFMRESP("TGFMRESP");

      private final String type;

      private MssMessage(String value) {
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
      public static MssMessage fromValue(String value) {
         for (MssMessage eType : values()) {
            if (eType.type.equalsIgnoreCase(value)) {
               return eType;
            }
         }
         throw new IllegalArgumentException(
               "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
      }
   }
   
   @Autowired
   private MssbatchesServiceImpl service;

   @Autowired
   private ConnectorPublisher publisher;
   
   @Autowired
   ApplicationLoggerService logger;
   
   @Autowired
   private ConnectorLoggerService connectorLogger;
   
   private Object constructJSONObject(Object object) {
      // Convert object to JSON string
      return JacksonUtility.convertObjectToJSONString(object);
   }

   public BigInteger insertOutgoingMessage(Object payload , String messageName) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setChannelSent("MQ");
      outgoingMessage.setInterfaceSystem("MSS");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType(messageName);
      //outgoingMessage.setSubMessageType("OF");
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
   
   public BigInteger insertOutgoingErrorMessage(BigInteger messageId, HttpStatus httpStatus,
         String errorMessage) {
      OutgoingMessageErrorLog outgoingErrorMessage = new OutgoingMessageErrorLog();
      //
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
      logger.logInterfaceOutgoingErrorMessage(outgoingErrorMessage);
      //
      return outgoingErrorMessage.getOutMessageId();
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

   private void validateMessage(String messageName) throws CustomException {
      try {
         MssMessage message = MssMessage.fromValue(messageName);
         lOGGER.debug(message.toString());
      } catch(IllegalArgumentException ex) {
         lOGGER.debug(ex.getMessage());
         throw new CustomException("datamessageinvalidMss", null, ErrorType.ERROR);
      }
      /*String[] mssMssgArray = { "TGFMOUFL", "TGFMCPRE", "TGFMCMBS", "TGFMCMBD", "TGFMEMBL", "TGFMENRL", "TGFMTRML",
            "TGFMUCRL", "TGFMAGTL", "TGFMCLTL", "TGFMRESP" };
      List<String> mssMessages = Arrays.asList(mssMssgArray);
      if (!mssMessages.contains(messageName)) {
         
      }*/
   }

   private void delegateToConnector(MssMessageParentModel data, String messageName) {
      if (!CollectionUtils.isEmpty((List)data.getData())) {
         Object payload = this.constructJSONObject(data);
         BigInteger messageId = null;
         BigInteger errorMessageId = null;
         boolean loggerEnabled = false;
         if (!loggerEnabled) {
            messageId = this.insertOutgoingMessage(payload,messageName);
         }
         Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled,
               null, TenantContext.getTenantId());
         ResponseEntity<Object> response = publisher.sendJobDataToConnector(payload, this.qname, MediaType.APPLICATION_JSON, payloadHeaders);
         boolean errorType = false;
         /*if (Objects.nonNull(resultPayload) && resultPayload instanceof Map) {
            // TODO: Actual Response Payload Status update
            Map<String, Object> response = (Map<String, Object>) resultPayload;
            String systemName = (String) response.get("systemName");
         } else {
            errorType = true;
         }*/
         
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
                        messageId = new BigInteger(ConnectorUtils.getObjectValue(ESBRouterTypeUtils.MESSAGE_ID.getName(), resultPayload));
                        errorMessageId = new BigInteger(ConnectorUtils.getObjectValue(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName(), resultPayload));
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
                     messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                     if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
                        errorMessageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
                     }
                  }
                  System.out.println("Partial Success");
               }
            } else {
               // Success Case
               if (loggerEnabled) {
                  messageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.MESSAGE_ID.getName()).get(0));
                  if (response.getHeaders().containsKey(ESBRouterTypeUtils.MESSAGE_ID.getName()) && Objects.nonNull(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()))) {
                     errorMessageId = new BigInteger(response.getHeaders().get(ESBRouterTypeUtils.ERROR_MESSAGE_ID.getName()).get(0));
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

   private void performOperativeFlight(String messageName) throws CustomException {
      MssMessageParentModel<List<MssModelOperativeFlightData>> data = service.operativeFlightData(messageName);
      // Send batch job data to Connector framework
      delegateToConnector(data, messageName);
   }

   private void performEnroutenment(String messageName) throws CustomException {
      MssMessageParentModel<List<EnroutementRulesMessagesModel>> data = service.enroutementRuleMessages(messageName);
      // Send batch job data to Connector framework
      delegateToConnector(data, messageName);
   }

   private void performTableDetailData(String messageName) throws CustomException {
      MssMessageParentModel<List<MappingTableDetailModel>> data = service.mappingTableDetailModel(messageName);
      // Send batch job data to Connector framework
      delegateToConnector(data, messageName);
   }

   private void performTableSummary(String messageName) throws CustomException {
      MssMessageParentModel<List<MappingTableSummaryModel>> data = service.mappingTableSummaryMessages(messageName);
      // Send batch job data to Connector framework
      delegateToConnector(data, messageName);
   }

   private void performEmbargoRule(String messageName) throws CustomException {
      MssMessageParentModel<List<EmbargoRulesMessageModel>> data = service.embargoRulesMessages(messageName);
      // send batch job data to connector framework
      delegateToConnector(data, messageName);
   }
   
   private void performOuthouseRule(String messageName) throws CustomException {
      MssMessageParentModel<List<Object>> data = service.outhouseTransferRulesMessages(messageName);
      // send batch job data to connector framework
      delegateToConnector(data, messageName);
   }
   
   private void performXrayRule(String messageName) throws CustomException {
      MssMessageParentModel<List<XRayRulesMessagesModel>> data = service.xrayRuleMessages(messageName);
      // send batch job data to connector framework
      delegateToConnector(data, messageName);
   }
   
   private void performULDBuildUpRule(String messageName) throws CustomException {
      MssMessageParentModel<List<ULDBuildupRulesModel>> data = service.uldBuildUPRulesMessages(messageName);
      // send batch job data to connector framework
      delegateToConnector(data, messageName);
   }
   
   private void performAgentList(String messageName) throws CustomException {
      MssMessageParentModel<List<AgentListMessageModel>> data = service.agentListMessages(messageName);
      if (!CollectionUtils.isEmpty((List) data.getData())) {
    	// send batch job data to connector framework
          delegateToConnector(data, messageName);
      }
      
   }
   
   private void performCloseTransit(String messageName) throws CustomException {
      MssMessageParentModel<List<CloseTransitMessagesModel>> data = service.closeTransitRuleMessages(messageName);
      // send batch job data to connector framework
      delegateToConnector(data, messageName);
   }

   @ApiOperation("Outgoing Message Manual Trigger for MSS.\n Valid values are -n\"TGFMOUFL - Operative Flight\", \n\"TGFMCPRE - Pre Announcement\", \n\"TGFMCMBS - Mailbag Summary\", \n\"TGFMCMBD - Mailbag Detail\", \n\"TGFMEMBL - Embargo Rule\", \n\"TGFMENRL - Enroutement Rule\", \n\"TGFMTRML - X Ray Rule\",\r\n" + 
         "            \n\"TGFMUCRL - ULD Buildup Rule\", \n\"TGFMAGTL - Postal Authhority Agent List\", \n\"TGFMCLTL - Close Transit Rule\", \n\"TGFMRESP - Generic Response Message\"")
   @RequestMapping(value = "/api/interface/mss/{messageName}", method = RequestMethod.POST)
   public String performMessageOperation(
         @ApiParam(value = "Message Name", required = true) @PathVariable("messageName") String messageName)
         throws CustomException {
      validateMessage(messageName);
      if (MssMessage.TGFMOUFL.equals(MssMessage.fromValue(messageName))) {
         performOperativeFlight(messageName);
      } else if (MssMessage.TGFMENRL.equals(MssMessage.fromValue(messageName))) {
         performEnroutenment(messageName);
      } else if (MssMessage.TGFMCMBD.equals(MssMessage.fromValue(messageName))) {
         performTableDetailData(messageName);
      } else if (MssMessage.TGFMCMBS.equals(MssMessage.fromValue(messageName))) {
         performTableSummary(messageName);
      } else if (MssMessage.TGFMEMBL.equals(MssMessage.fromValue(messageName))) {
         performEmbargoRule(messageName);
      } else if (MssMessage.TGFMTRML.equals(MssMessage.fromValue(messageName))) {
         performOuthouseRule(messageName);
      } else if (MssMessage.TGFMUCRL.equals(MssMessage.fromValue(messageName))) {
         performULDBuildUpRule(messageName);
      } else if (MssMessage.TGFMAGTL.equals(MssMessage.fromValue(messageName))) {
         performAgentList(messageName);
      } else if (MssMessage.TGFMCLTL.equals(MssMessage.fromValue(messageName))) {
         performCloseTransit(messageName);
      }
      return messageName + " Processed Successfully";
   }
}