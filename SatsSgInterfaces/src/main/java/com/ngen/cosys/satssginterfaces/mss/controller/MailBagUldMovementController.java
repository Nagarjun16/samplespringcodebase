/**
 * 
 * MailBagUldMovementController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 4 June, 2018 NIIT -
 */
package com.ngen.cosys.satssginterfaces.mss.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.esb.route.ConnectorPublisher;
import com.ngen.cosys.esb.route.ConnectorUtils;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.IncomingMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageErrorLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutgoingMessageLog;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.mss.validator.UldMovementValidator;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.satssginterfaces.mss.model.BaseErrorResponseMessagesMss;
import com.ngen.cosys.satssginterfaces.mss.model.MSSMailBagMovement;
import com.ngen.cosys.satssginterfaces.mss.model.MailBagUldMovementBaseModel;
import com.ngen.cosys.satssginterfaces.mss.service.MssService;

/**
 * This is the entry point to process the incoming Uld Movement MSS messages.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation(path = "/api/interface/mss")
public class MailBagUldMovementController {

   @Autowired
   UldMovementValidator uldMovementValidator;

   private String queueName;

   @Autowired
   ApplicationLoggerService logger;

   @Autowired
   private ConnectorPublisher router;

   @Autowired
   private MssService service;

   private boolean mssActive = true;

   @RequestMapping(value = "/preannouncementuldmessage", method = RequestMethod.POST, consumes = {
         MediaType.APPLICATION_JSON_VALUE,
         MediaType.APPLICATION_XML_VALUE }, produces = MediaType.APPLICATION_XML_VALUE)
   public ResponseEntity<Object> incomingMessages(@RequestBody String payload, HttpServletRequest request)
         throws CustomException {
      Object response = null;
      int i = 0;
      MailBagUldMovementBaseModel jsonString = null;
      //
      try {
         jsonString = (MailBagUldMovementBaseModel) JacksonUtility.convertJSONStringToObject(payload,
               MailBagUldMovementBaseModel.class);

      } catch (Exception e) {
         i = 1;
         updateErrorLogs(request, payload.toString(), null);
      }
      if (i != 1) {
         response = processMessages(jsonString, payload.toString(), request);
      }

      // Update Status
      // this.updateMessageStatus(request);
      return new ResponseEntity<>(response, HttpStatus.OK);
   }

   private Object processMessages(final MailBagUldMovementBaseModel mailBagUldMovementBaseModel, String payload,
         HttpServletRequest request) throws CustomException {
      List<MSSMailBagMovement> responseList = null;
      BaseErrorResponseMessagesMss validateModel = null;
      boolean checkMssIncomingIsActiveOrNot = service.checkMssIncomingIsActiveOrNot();
      if (!checkMssIncomingIsActiveOrNot) {
         this.mssActive = false;
         updateErrorLogs(request, payload.toString(), mailBagUldMovementBaseModel);
      } else {
         this.mssActive = true;
         try {
            if ("TGFMCULD".equalsIgnoreCase(mailBagUldMovementBaseModel.getMsgType())) {
               validateModel = uldMovementValidator.validateUldMovement(mailBagUldMovementBaseModel);
               if (!Optional.ofNullable(validateModel.getErrorResponseMessagesMss()).isPresent()
                     || (Optional.ofNullable(validateModel.getErrorResponseMessagesMss()).isPresent()
                           && StringUtils.isEmpty(validateModel.getErrorResponseMessagesMss().getErrorDescription()))) {
                  for (MSSMailBagMovement value : mailBagUldMovementBaseModel.getListMailBagUldMovement()) {
                     value.setTenantId(mailBagUldMovementBaseModel.getTenantAirport());
                     service.messageTypeTGFMCULD(value);
                  }
                  List<MSSMailBagMovement> uldMovement = mailBagUldMovementBaseModel.getListMailBagUldMovement();
                  this.queueName = mailBagUldMovementBaseModel.getMsgType();
                  updateMessageStatusForIncoming(request, payload, "PROCESSED",
                        mailBagUldMovementBaseModel.getMsgType());
                  return mailBagUldMovementBaseModel;
               } else {
                  returnErrorResponseMessage(mailBagUldMovementBaseModel, validateModel);
                  return validateModel;
               }
            }
            if ("TGFMMBUL".equalsIgnoreCase(mailBagUldMovementBaseModel.getMsgType())) {
               validateModel = uldMovementValidator.validateMailBagUldBasedMovement(mailBagUldMovementBaseModel);
               if (!Optional.ofNullable(validateModel.getErrorResponseMessagesMss()).isPresent()
                     || (Optional.ofNullable(validateModel.getErrorResponseMessagesMss()).isPresent()
                           && StringUtils.isEmpty(validateModel.getErrorResponseMessagesMss().getErrorDescription()))) {
                  // for (MSSMailBagMovement value :
                  // mailBagUldMovementBaseModel.getListMailBagUldMovement()) {
                  // service.messageTypeTGFMMBUL(value);
                  // }
                  for (MSSMailBagMovement value : mailBagUldMovementBaseModel.getListMailBagUldMovement()) {
                     MSSMailBagMovement responseData = service.messageTypeForTGFMMBUL(value);
                     performMailBagMovementOperationsForTGFMMBUL(responseData);
                  }
                  List<MSSMailBagMovement> uldMovement = mailBagUldMovementBaseModel.getListMailBagUldMovement();
                  this.queueName = mailBagUldMovementBaseModel.getMsgType();
                  updateMessageStatusForIncoming(request, payload, "PROCESSED",
                        mailBagUldMovementBaseModel.getMsgType());
                  return mailBagUldMovementBaseModel;
               } else {
                  returnErrorResponseMessage(mailBagUldMovementBaseModel, validateModel);
                  return validateModel;
               }
            }
            if ("TGFMMBSI".equalsIgnoreCase(mailBagUldMovementBaseModel.getMsgType())) {
               validateModel = uldMovementValidator.validateMailbagMovement(mailBagUldMovementBaseModel);
               if (!Optional.ofNullable(validateModel.getErrorResponseMessagesMss()).isPresent()
                     || (Optional.ofNullable(validateModel.getErrorResponseMessagesMss()).isPresent()
                           && StringUtils.isEmpty(validateModel.getErrorResponseMessagesMss().getErrorDescription()))) {
                  for (MSSMailBagMovement value : mailBagUldMovementBaseModel.getListMailBagUldMovement()) {
                     MSSMailBagMovement responseData = service.messageTypeTGFMMBSI(value);
                     performMailBagMovementOperations(responseData);
                  }
                  List<MSSMailBagMovement> uldMovement = mailBagUldMovementBaseModel.getListMailBagUldMovement();
                  this.queueName = mailBagUldMovementBaseModel.getMsgType();
                  updateMessageStatusForIncoming(request, payload, "PROCESSED",
                        mailBagUldMovementBaseModel.getMsgType());
                  return mailBagUldMovementBaseModel;
               } else {
                  returnErrorResponseMessage(mailBagUldMovementBaseModel, validateModel);
                  return validateModel;
               }
            }

         } catch (Exception e) {
            if ("TGFMMBSI".equalsIgnoreCase(mailBagUldMovementBaseModel.getMsgType())) {
               validateModel = uldMovementValidator.validateMailbagMovement(mailBagUldMovementBaseModel);
            }
            if ("TGFMMBUL".equalsIgnoreCase(mailBagUldMovementBaseModel.getMsgType())) {
               validateModel = uldMovementValidator.validateMailBagUldBasedMovement(mailBagUldMovementBaseModel);
            }
            if ("TGFMCULD".equalsIgnoreCase(mailBagUldMovementBaseModel.getMsgType())) {
               validateModel = uldMovementValidator.validateUldMovement(mailBagUldMovementBaseModel);
            }
            if (Optional.ofNullable(validateModel.getErrorResponseMessagesMss()).isPresent()
                  && !StringUtils.isEmpty(validateModel.getErrorResponseMessagesMss().getErrorDescription())) {
               returnErrorResponseMessage(mailBagUldMovementBaseModel, validateModel);
            }
            updateErrorLogs(request, payload.toString(), mailBagUldMovementBaseModel);
         }
      }

      return responseList;
   }

   private void performMailBagMovementOperationsForTGFMMBUL(MSSMailBagMovement responseData) throws CustomException {

      if (responseData.getValidMailBags() && !CollectionUtils.isEmpty(responseData.getMethodFor())) {
         for (String value : responseData.getMethodFor()) {
            if ("Load".equalsIgnoreCase(value)) {
               service.load(responseData, responseData.getSegmentIdForAssignedTrolley());
               service.produceAirmailStatusEventAfterLoad(responseData);
            } else if ("Unload".equalsIgnoreCase(value)) {
               service.unload(responseData, responseData.getSegmentIdForAssignedTrolley());
            }
         }

      }
   }

   private void performMailBagMovementOperations(MSSMailBagMovement responseData) throws CustomException {

      if (responseData.getValidMailBags() && !CollectionUtils.isEmpty(responseData.getMethodFor())) {
         for (String value : responseData.getMethodFor()) {
            if ("BreakDown".equalsIgnoreCase(value)) {
               service.breakDown(responseData);
            } else if ("UpdateLocation".equalsIgnoreCase(value)) {
               service.updateStoreLocationOfShipment(responseData, responseData.getResponse());
               service.produceAirmailStatusEvents(responseData.getResponse(), responseData);
            } else if ("Acceptance".equalsIgnoreCase(value)) {
               service.acceptmailbag(responseData);
               service.produceAirmailStatusEventAfterAcceptance(responseData);
            } else if ("UpdateLyingList".equalsIgnoreCase(value)) {
               service.updateLyingList(responseData);
            } else if ("BookingMailBags".equalsIgnoreCase(value)) {
               service.bookShipmentInfo(responseData);
            } else if ("Xray".equalsIgnoreCase(value)) {
               service.insertXRAYData(responseData, responseData.getResponse());
            }
         }

      }
   }

   private void returnErrorResponseMessage(final MailBagUldMovementBaseModel mailBagUldMovementBaseModel,
         BaseErrorResponseMessagesMss validateModel) {
      BigInteger messageId = null;
      this.queueName = mailBagUldMovementBaseModel.getMsgType();
      boolean loggerEnabled = false;
      Object jsonPayload = JacksonUtility.convertObjectToJSONString(validateModel);
      // insert outgoing message
      messageId = this.insertOutgoingMessage(jsonPayload, this.queueName, validateModel);

      Map<String, String> payloadHeaders = ConnectorUtils.getPayloadHeaders(messageId, loggerEnabled, null,
            TenantContext.getTenantId());
      // jsonPayload = "<Jms>dummayPayload</Jms>";
      ResponseEntity<Object> response = null;
      boolean errorType = false;
      try {
         response = router.sendJobDataToConnector(jsonPayload, "MSS_TMSG", MediaType.APPLICATION_JSON, payloadHeaders);
         this.updateMessageStatus(messageId, errorType);
      } catch (Exception e) {
         errorType = true;
         // Update Status In FAILURE
         this.updateMessageStatus(messageId, errorType);
         insertErrorOutgoingLog(messageId, e);
      }

   }

   public BigInteger insertOutgoingMessage(Object payload, String messageName,
         BaseErrorResponseMessagesMss validateModel) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setChannelSent("MQ");
      outgoingMessage.setInterfaceSystem("MSS");
      outgoingMessage.setSenderOriginAddress("COSYS");
      outgoingMessage.setMessageType("MSS");
      if (Optional.ofNullable(validateModel).isPresent()
            && Optional.ofNullable(validateModel.getErrorResponseMessagesMss().getErrorDescription()).isPresent()) {
         outgoingMessage.setSubMessageType(validateModel.getMsgType());
      } else {
         outgoingMessage.setSubMessageType(messageName);
      }
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
      // logger.logInterfaceOutgoingMessage(outgoingMessage);
      //
      return outgoingMessage.getOutMessageId();
   }

   public void updateMessageStatus(BigInteger referenceId, boolean errorType) {
      OutgoingMessageLog outgoingMessage = new OutgoingMessageLog();
      //
      outgoingMessage.setOutMessageId(referenceId);
      outgoingMessage.setAcknowledgementReceivedOn(LocalDateTime.now());
      if (errorType) {
         outgoingMessage.setStatus("ERROR");
      } else {
         outgoingMessage.setStatus("SENT");
      }
      // connectorLogger.logOutgoingMessage(outgoingMessage);
      if ("ERROR".equalsIgnoreCase(outgoingMessage.getStatus())) {

      }
   }

   private void insertErrorOutgoingLog(BigInteger messageId, Exception e) {
      Exception ex = (Exception) e;
      OutgoingMessageErrorLog errorLog = new OutgoingMessageErrorLog();
      errorLog.setOutMessageId(messageId);
      errorLog.setCreatedBy("MSS");
      errorLog.setCreatedOn(LocalDateTime.now());
      errorLog.setErrorCode("ERROR");
      errorLog.setLineItem(ex.getMessage());
      errorLog.setMessage("ERROR");
      // logger.logInterfaceOutgoingErrorMessage(errorLog);

   }

   private BigInteger updateMessageStatusForIncoming(HttpServletRequest request, String allPayload, String status,
         String subMessageType) {
      IncomingMessageLog incomingMessage = new IncomingMessageLog();
      incomingMessage.setChannelReceived("HTTP");
      incomingMessage.setInterfaceSystem("MSS");
      incomingMessage.setSenderOriginAddress("MSS");
      incomingMessage.setMessageType("MSS");
      incomingMessage.setSubMessageType(subMessageType);
      incomingMessage.setMessage(allPayload);
      incomingMessage.setCarrierCode(null);
      incomingMessage.setFlightNumber(null);
      incomingMessage.setFlightOriginDate(null);
      incomingMessage.setShipmentNumber(null);
      incomingMessage.setShipmentDate(null);
      incomingMessage.setReceivedOn(LocalDateTime.now());
      incomingMessage.setVersionNo(1);
      incomingMessage.setSequenceNo(1);
      incomingMessage.setMessageContentEndIndicator(null);
      incomingMessage.setStatus(status);
      incomingMessage.setCreatedBy("MSS");
      incomingMessage.setCreatedOn(LocalDateTime.now());
      // Logging the data into incoming message log table
      logger.logInterfaceIncomingMessage(incomingMessage);
      return incomingMessage.getInMessageId();
   }

   private void updateErrorLogs(HttpServletRequest request, String payload,
         MailBagUldMovementBaseModel pullMailBagResponseModel) {
      if (StringUtils.isEmpty(this.queueName)) {
         this.queueName = pullMailBagResponseModel.getMsgType();
      }
      StringBuilder allError = new StringBuilder();
      if (Optional.ofNullable(pullMailBagResponseModel).isPresent()
            && !CollectionUtils.isEmpty(pullMailBagResponseModel.getListMailBagUldMovement())) {
         for (MSSMailBagMovement errorData : pullMailBagResponseModel.getListMailBagUldMovement()) {

            if (!CollectionUtils.isEmpty(errorData.getMessageList())) {
               for (int i = 0; i <= errorData.getMessageList().size() - 1; i++) {
                  if (StringUtils.isEmpty(allError)) {
                     allError.append(errorData.getMessageList().get(i).getCode());
                  } else {
                     allError.append("\r\n");
                     allError.append(errorData.getMessageList().get(i).getCode());
                  }
               }
            }

         }
      }

      IncomingMessageErrorLog errorLog = new IncomingMessageErrorLog();
      BigInteger messageLogId = updateMessageStatusForIncoming(request, payload, "ERROR",
            pullMailBagResponseModel.getMsgType());
      errorLog.setInMessageId(messageLogId);
      errorLog.setCreatedBy("MSS");
      errorLog.setCreatedOn(LocalDateTime.now());
      errorLog.setErrorCode("Error");
      if (!this.mssActive) {
         allError.append("MSS message is not active to process. Kindly enable it from Maintain System Parameter");
         errorLog.setLineItem("MSS.NotActive");
         errorLog.setErrorCode("MSS.NotActive");
      } else {
         errorLog.setLineItem("ERROR");
      }
      errorLog.setMessage(allError.toString());
      logger.logInterfaceIncomingErrorMessage(errorLog);

   }

}
