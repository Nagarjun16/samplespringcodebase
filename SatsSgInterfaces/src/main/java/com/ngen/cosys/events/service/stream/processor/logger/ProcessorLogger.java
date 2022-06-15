/**
 * 
 * ProcessorLogger.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          09 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.events.service.stream.processor.logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.esb.jackson.util.JacksonUtility;
import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutboundServiceMessageDocumentLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutboundServiceMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutboundServiceMessageTemplateLog;
import com.ngen.cosys.events.payload.FAXEvent;
import com.ngen.cosys.events.payload.SMSEvent;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.service.ivrs.model.IVRSRequest;
import com.ngen.cosys.service.wscale.model.WeighingScalePayload;

/**
 * This Processor Logger service used for FAX/SMS logging
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class ProcessorLogger implements ProcessorLoggerService {

   @Autowired
   ApplicationLoggerService logger;
   
   /**
    * @see com.ngen.cosys.events.service.stream.processor.logger.ProcessorLoggerService#logProcessorEventMessage(java.lang.Object)
    * 
    */
   @Override
   public BigInteger logProcessorEventMessage(Object eventPayload) throws CustomException {
      OutboundServiceMessageLog outboundServiceMessage = getEventPayload(eventPayload);
      logger.logOutboundServiceMessage(outboundServiceMessage);
      //
      if (Objects.nonNull(outboundServiceMessage.getMessageTemplate())) {
         outboundServiceMessage.getMessageTemplate()
               .setOutServiceMessageLogId(outboundServiceMessage.getOutServiceMessageLogId());
         logger.logOutboundServiceMessageTemplate(outboundServiceMessage.getMessageTemplate());
      }
      if (Objects.nonNull(outboundServiceMessage.getMessageDocuments())) {
         for (OutboundServiceMessageDocumentLog messageDocument : outboundServiceMessage.getMessageDocuments()) {
            messageDocument.setOutServiceMessageLogId(outboundServiceMessage.getOutServiceMessageLogId());
         }
         logger.logOutboundServiceMessageDocument(outboundServiceMessage);
      }
      //
      return outboundServiceMessage.getOutServiceMessageLogId();
   }

   /**
    * @see com.ngen.cosys.events.service.stream.processor.logger.ProcessorLoggerService#updateEMailMessageLog(java.lang.String,
    *      java.lang.String, java.math.BigInteger)
    * 
    */
   @Override
   public void updateProcessorMessageLog(String msgStatus, String errorMessage, BigInteger messageId)
         throws CustomException {
      //
      OutboundServiceMessageLog outboundServiceMessage = new OutboundServiceMessageLog();
      outboundServiceMessage.setOutServiceMessageLogId(messageId);
      outboundServiceMessage.setMessageStatus(msgStatus);
      if (Objects.nonNull(errorMessage)) {
         outboundServiceMessage.setFailedReason(errorMessage);
      }
      //
      logger.updateOutboundServiceMessageLog(outboundServiceMessage);
   }
   
   /**
    * @param eventPayload
    * @return
    */
   private OutboundServiceMessageLog getEventPayload(Object eventPayload) {
      OutboundServiceMessageLog outboundServiceMessage = null;
      //
      if (Objects.nonNull(eventPayload)) {
         if (eventPayload instanceof FAXEvent) {
            FAXEvent faxEvent = (FAXEvent) eventPayload;
            outboundServiceMessage = getOutboundServiceMessageLog(faxEvent, "FAX");
         } else if (eventPayload instanceof SMSEvent) {
            SMSEvent smsEvent = (SMSEvent) eventPayload;
            outboundServiceMessage = getOutboundServiceMessageLog(smsEvent, "SMS");
            // Template Log Message
            outboundServiceMessage.setMessageTemplate(getOutboundServiceMessageTemplateLog(smsEvent.getTemplate()));
         } else if (eventPayload instanceof WeighingScalePayload) {
            WeighingScalePayload wscalePayload = (WeighingScalePayload) eventPayload;
            outboundServiceMessage = getOutboundServiceMessageLog(wscalePayload, "WSCALE");
         } else if (eventPayload instanceof IVRSRequest) {
            IVRSRequest ivrsRequest = (IVRSRequest) eventPayload;
            outboundServiceMessage = getOutboundServiceMessageLog(ivrsRequest, "IVRS");
            outboundServiceMessage.setMessageDocuments(getOutboundServiceMessageDocumentLogs(ivrsRequest));
         }
      }
      //
      return outboundServiceMessage;
   }

   /**
    * @param emailEvent
    * @return
    */
   private OutboundServiceMessageLog getOutboundServiceMessageLog(Object payload, String messageType) {
      //
      OutboundServiceMessageLog outboundServiceMessage = new OutboundServiceMessageLog();
      outboundServiceMessage.setMessageType(messageType);
      outboundServiceMessage.setMessageSender("COSYS");
      outboundServiceMessage.setMessageStatus("INITIATED");
      String messagePayload = null;
      //
      if (Objects.equals("SMS", messageType)) {
         SMSEvent smsEvent = (SMSEvent) payload;
         outboundServiceMessage.setMessageRecipients(String.valueOf(smsEvent.getNumber()));
         messagePayload = smsEvent.getMessage();
      } else if (Objects.equals("WSCALE", messageType)) {
         WeighingScalePayload wscalePayload = (WeighingScalePayload) payload;
         messagePayload = getWeighingScalePayload(wscalePayload);
         outboundServiceMessage.setMessageRecipients("NA");
      } else if (Objects.equals("IVRS", messageType) || Objects.equals("FAX", messageType)) {
         IVRSRequest ivrsRequest = (IVRSRequest) payload;
         String shipmentNumber = ivrsRequest.getAwbPrefix() + ivrsRequest.getAwbSuffix();
         String recipientDetail = ivrsRequest.getConsigneeName() + " [" + ivrsRequest.getContactNo() + "] ";
         outboundServiceMessage.setMessageReference(shipmentNumber);
         outboundServiceMessage.setMessageRecipients(recipientDetail);
         messagePayload = getIVRSRequestPayload(ivrsRequest);
      } else {
         // Applicable for NONE
         outboundServiceMessage.setMessageRecipients("NA");
         messagePayload = payload.toString();
      }
      outboundServiceMessage.setMessagePayload(messagePayload);
      //
      return outboundServiceMessage;
   }
   
   /**
    * @param template
    * @return
    */
   private OutboundServiceMessageTemplateLog getOutboundServiceMessageTemplateLog(TemplateBO template) {
      //
      OutboundServiceMessageTemplateLog outboundServiceMessageTemplate = null;
      if (Objects.nonNull(template)) {
         outboundServiceMessageTemplate = new OutboundServiceMessageTemplateLog();
         outboundServiceMessageTemplate.setMessageTemplateId(template.getTemplateId());
         outboundServiceMessageTemplate.setMessageTemplateName(template.getTemplateName());
         outboundServiceMessageTemplate.setMessageTemplatePayload(template.getTemplateMessage());
         if (!CollectionUtils.isEmpty(template.getTemplateParams())) {
            outboundServiceMessageTemplate.setMessageTemplateParams(template.getTemplateParams().toString());
         }
      }
      //
      return outboundServiceMessageTemplate;
   }
   
   /**
    * @param ivrsRequest
    * @return
    */
   private List<OutboundServiceMessageDocumentLog> getOutboundServiceMessageDocumentLogs(IVRSRequest ivrsRequest) {
      boolean found = false;
      List<OutboundServiceMessageDocumentLog> outboundServiceMessageDocumentDetails = new ArrayList<>();
      OutboundServiceMessageDocumentLog outboundServiceMessageDocumentLog = null;
      if (!CollectionUtils.isEmpty(ivrsRequest.getAwbDetails())) {
         String documentName = ivrsRequest.getAwbPrefix() + ivrsRequest.getAwbSuffix();
         for (IVRSRequest.AWBDetail awbDetail : ivrsRequest.getAwbDetails()) {
            if (!StringUtils.isEmpty(awbDetail.getBase64Content())) {
               found = true;
               outboundServiceMessageDocumentLog = new OutboundServiceMessageDocumentLog();
               String flightKey = awbDetail.getFlightCarrier() + awbDetail.getFlightNumber() + "#"
                     + String.valueOf(awbDetail.getFlightDate());
               outboundServiceMessageDocumentLog.setMessageDocumentName(documentName + "#" + flightKey);
               outboundServiceMessageDocumentLog.setMessageDocumentFormat("pdf");
               outboundServiceMessageDocumentLog.setMessageDocumentData(awbDetail.getBase64Content());
               outboundServiceMessageDocumentDetails.add(outboundServiceMessageDocumentLog);
            }
         }
      }
      return found ? outboundServiceMessageDocumentDetails : Collections.emptyList();
   }
   
   /**
    * @param payload
    * @return
    */
   public String getWeighingScalePayload(WeighingScalePayload payload) {
      //
      Map<String, String> keyValues = new HashMap<>();
      keyValues.put("wscaleip", payload.getWscaleIP());
      keyValues.put("wscaleport", payload.getWscalePort());
      keyValues.put("tenantID", payload.getTenantID());
      keyValues.put("loggerEnabled", String.valueOf(payload.isLoggerEnabled()));
      //
      return (String) JacksonUtility.convertObjectToJSONString(keyValues);
   }
   
   /**
    * @param ivrsRequest
    * @param outboundServiceMessage
    * @return
    */
   public String getIVRSRequestPayload(IVRSRequest ivrsRequest) {
      return (String) JacksonUtility.convertObjectToJSONString(copyRequest(ivrsRequest));
   }
   
   /**
    * @param ivrsRequest
    * @return
    */
   private IVRSRequest copyRequest(IVRSRequest ivrsRequest) {
      IVRSRequest copy = new IVRSRequest();
      copy.setAwbPrefix(ivrsRequest.getAwbPrefix());
      copy.setAwbSuffix(ivrsRequest.getAwbSuffix());
      copy.setTotalPieces(ivrsRequest.getTotalPieces());
      copy.setTotalWeight(ivrsRequest.getTotalWeight());
      copy.setOrigin(ivrsRequest.getOrigin());
      copy.setDestination(ivrsRequest.getDestination());
      copy.setConsigneeName(ivrsRequest.getConsigneeName());
      copy.setContactNo(ivrsRequest.getContactNo());
      if (!CollectionUtils.isEmpty(ivrsRequest.getAwbDetails())) {
         copy.setAwbDetails(getAWBDetails(ivrsRequest.getAwbDetails()));
      }
      return copy;
   }
   
   /**
    * @param awbDetails
    * @return
    */
   private List<IVRSRequest.AWBDetail> getAWBDetails(List<IVRSRequest.AWBDetail> awbDetails) {
      List<IVRSRequest.AWBDetail> copyAWBDetails = new ArrayList<>();
      IVRSRequest.AWBDetail copyAWBDetail = null;
      for (IVRSRequest.AWBDetail awbDetail : awbDetails) {
         copyAWBDetail = new IVRSRequest.AWBDetail();
         copyAWBDetail.setFlightCarrier(awbDetail.getFlightCarrier());
         copyAWBDetail.setFlightNumber(awbDetail.getFlightNumber());
         copyAWBDetail.setFlightDate(awbDetail.getFlightDate());
         copyAWBDetail.setPieces(awbDetail.getPieces());
         copyAWBDetail.setWeight(awbDetail.getWeight());
         copyAWBDetail.setFsuStatus(awbDetail.getFsuStatus());
         copyAWBDetails.add(copyAWBDetail);
      }
      return copyAWBDetails;
   }
   
}
