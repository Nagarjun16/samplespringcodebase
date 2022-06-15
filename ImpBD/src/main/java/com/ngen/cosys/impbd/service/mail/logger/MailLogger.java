/**
 * 
 * MailLogger.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 09 JUL, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service.mail.logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.consumer.logger.service.ApplicationLoggerService;
import com.ngen.cosys.events.esb.connector.logger.payload.OutboundServiceMessageDocumentLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutboundServiceMessageLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutboundServiceMessageReferenceLog;
import com.ngen.cosys.events.esb.connector.logger.payload.OutboundServiceMessageTemplateLog;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.AttachmentStream;
import com.ngen.cosys.events.payload.EMailEvent.MessageReferenceDetail;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * Email Logger service
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class MailLogger implements MailLoggerService {

   @Autowired
   ApplicationLoggerService logger;

   /**
    * @see com.ngen.cosys.cloud.service.mail.logger.MailLoggerService#logEMailMessage(com.ngen.cosys.events.payload.EMailEvent)
    * 
    */
   @Override
   public BigInteger logEMailEventMessage(EMailEvent emailEvent) throws CustomException {
      //
      OutboundServiceMessageLog outboundServiceMessage = getEMailEventMessageLog(emailEvent);
      // Set EMail Recipient Count
      emailEvent.setRecipientCount(outboundServiceMessage.getRecipientCount());
      // Message Reference
      emailEvent.setMessageReference(outboundServiceMessage.getMessageReference());
      //
      logger.logOutboundServiceMessage(outboundServiceMessage);
      //
      if (!CollectionUtils.isEmpty(outboundServiceMessage.getMessageReferences())) {
         for (OutboundServiceMessageReferenceLog referenceLog : outboundServiceMessage.getMessageReferences()) {
            referenceLog.setOutServiceMessageLogId(outboundServiceMessage.getOutServiceMessageLogId());
         }
         logger.logOutboundServiceMessageReference(outboundServiceMessage);
      }
      //
      if (Objects.nonNull(outboundServiceMessage.getMessageTemplate())) {
         outboundServiceMessage.getMessageTemplate()
               .setOutServiceMessageLogId(outboundServiceMessage.getOutServiceMessageLogId());
         logger.logOutboundServiceMessageTemplate(outboundServiceMessage.getMessageTemplate());
      }
      //
      if (!CollectionUtils.isEmpty(outboundServiceMessage.getMessageDocuments())) {
         //
         for (OutboundServiceMessageDocumentLog documentLog : outboundServiceMessage.getMessageDocuments()) {
            documentLog.setOutServiceMessageLogId(outboundServiceMessage.getOutServiceMessageLogId());
         }
         logger.logOutboundServiceMessageDocument(outboundServiceMessage);
      }
      return outboundServiceMessage.getOutServiceMessageLogId();
   }

   /**
    * @see com.ngen.cosys.cloud.service.mail.logger.MailLoggerService#updateEMailMessageLog(com.ngen.cosys.events.payload.EMailEvent)
    * 
    */
   @Override
   public void updateEMailMessageLog(String msgStatus, String errorMessage, BigInteger messageId)
         throws CustomException {
      OutboundServiceMessageLog outboundServiceMessage = new OutboundServiceMessageLog();
      outboundServiceMessage.setOutServiceMessageLogId(messageId);
      outboundServiceMessage.setMessageStatus(msgStatus);
      //
      if (!StringUtils.isEmpty(errorMessage)) {
         if (errorMessage.length() > 1000) {
            errorMessage = errorMessage.substring(0, 1000);
         }
         outboundServiceMessage.setFailedReason(errorMessage);
      }
      //
      logger.updateOutboundServiceMessageLog(outboundServiceMessage);
   }

   /**
    * @param emailEvent
    * @return
    */
   private OutboundServiceMessageLog getEMailEventMessageLog(EMailEvent emailEvent) {
      OutboundServiceMessageLog outboundServiceMessage = getOutboundServiceMessageLog(emailEvent);
      //
      OutboundServiceMessageTemplateLog outboundServiceMessageTemplate = getOutboundServiceMessageTemplateLog(
            emailEvent.getTemplate());
      outboundServiceMessage.setMessageTemplate(outboundServiceMessageTemplate);
      //
      List<OutboundServiceMessageDocumentLog> outboundServiceMessageDocuments = getOutboundServiceMessageDocumentsLog(
            emailEvent);
      outboundServiceMessage.setMessageDocuments(outboundServiceMessageDocuments);
      //
      List<OutboundServiceMessageReferenceLog> outboundServiceMessageReferences = getOutboundServiceMessageReferenceLog(
            emailEvent);
      outboundServiceMessage.setMessageReferences(outboundServiceMessageReferences);
      //
      return outboundServiceMessage;
   }

   /**
    * @param emailEvent
    * @return
    */
   private OutboundServiceMessageLog getOutboundServiceMessageLog(EMailEvent emailEvent) {
      //
      OutboundServiceMessageLog outboundServiceMessage = new OutboundServiceMessageLog();
      outboundServiceMessage.setMessageType("EMAIL");
      outboundServiceMessage.setMessageSubject(emailEvent.getMailSubject());
      outboundServiceMessage.setMessageSender(emailEvent.getMailFrom());
      outboundServiceMessage.setMessageStatus(emailEvent.getMailStatus());
      String errorMessage = emailEvent.getFailedReason();
      if (!StringUtils.isEmpty(errorMessage)) {
         if (errorMessage.length() > 1000) {
            errorMessage = errorMessage.substring(0, 1000);
         }
         outboundServiceMessage.setFailedReason(errorMessage);
      }
      String messagePayload = null;
      if (Objects.nonNull(emailEvent.getTemplate())
            && !StringUtils.isEmpty(emailEvent.getTemplate().getTemplateMessage())) {
         messagePayload = emailEvent.getTemplate().getTemplateMessage();
      } else {
         messagePayload = emailEvent.getMailBody();
      }
      outboundServiceMessage.setMessagePayload(messagePayload);
      outboundServiceMessage.setMessageRecipients(getMessageRecipients(emailEvent));
      outboundServiceMessage.setRecipientCount(emailEvent.getRecipientCount());
      //
      return outboundServiceMessage;
   }

   /**
    * @param emailEvent
    * @return
    */
   private String getMessageRecipients(EMailEvent emailEvent) {
      String rowValue = null;
      String value = null;
      //
      if (!StringUtils.isEmpty(emailEvent.getMailTo()) || !ObjectUtils.isEmpty(emailEvent.getMailToAddress())) {
         value = getRecipients(emailEvent.getMailTo(), emailEvent.getMailToAddress());
         rowValue = getRawValue(rowValue, getRecipientInfo(value, "To"));
      }
      if (!StringUtils.isEmpty(emailEvent.getMailCC()) || !ObjectUtils.isEmpty(emailEvent.getMailCCAddress())) {
         if (!StringUtils.isEmpty(rowValue)) {
            rowValue += ",";
         }
         value = getRecipients(emailEvent.getMailCC(), emailEvent.getMailCCAddress());
         rowValue = getRawValue(rowValue, getRecipientInfo(value, "CC"));
      }
      if (!StringUtils.isEmpty(emailEvent.getMailBCC()) || !ObjectUtils.isEmpty(emailEvent.getMailBCCAddress())) {
         if (!StringUtils.isEmpty(rowValue)) {
            rowValue += ",";
         }
         value = getRecipients(emailEvent.getMailBCC(), emailEvent.getMailBCCAddress());
         rowValue = getRawValue(rowValue, getRecipientInfo(value, "BCC"));
      }
      //
      return rowValue;
   }

   /**
    * @param single
    * @param multiple
    * @return
    */
   private String getRecipients(String single, String[] multiple) {
      //
      String rowValue = null;
      if (!StringUtils.isEmpty(single)) {
         rowValue = getRawValue(rowValue, single);
      }
      if (!ObjectUtils.isEmpty(multiple)) {
         for (String value : multiple) {
            rowValue = getRawValue(rowValue, value);
         }
      }
      //
      return rowValue;
   }

   /**
    * @param recipient
    * @param type
    * @return
    */
   private String getRecipientInfo(String recipient, String type) {
      //
      StringBuilder recipients = new StringBuilder();
      recipients.append(type).append(" : [").append((String) recipient).append("]");
      return recipients.toString();
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
    * @param emailEvent
    * @return
    */
   private List<OutboundServiceMessageDocumentLog> getOutboundServiceMessageDocumentsLog(EMailEvent emailEvent) {
      //
      List<OutboundServiceMessageDocumentLog> outboundServiceMessageDocuments = null;
      if (!CollectionUtils.isEmpty(emailEvent.getMailAttachments())) {
         //
         outboundServiceMessageDocuments = new ArrayList<>();
         OutboundServiceMessageDocumentLog documentMessage = null;
         //
         for (Iterator<Entry<String, AttachmentStream>> iterator = emailEvent.getMailAttachments().entrySet()
               .iterator(); iterator.hasNext();) {
            //
            Map.Entry<String, AttachmentStream> entry = iterator.next();
            documentMessage = new OutboundServiceMessageDocumentLog();
            AttachmentStream attachment = entry.getValue();
            documentMessage.setMessageDocumentId(attachment.getFileId());
            documentMessage.setMessageDocumentName(attachment.getFileName());
            documentMessage.setMessageDocumentFormat(attachment.getFileType());
            documentMessage.setMessageDocumentData(attachment.getFileData());
            //
            outboundServiceMessageDocuments.add(documentMessage);
         }
      }
      return outboundServiceMessageDocuments;
   }

   /**
    * @param emailEvent
    * @return
    */
   private List<OutboundServiceMessageReferenceLog> getOutboundServiceMessageReferenceLog(EMailEvent emailEvent) {
      //
      List<OutboundServiceMessageReferenceLog> outboundServiceMessageReferences = Collections.emptyList();
      if (!CollectionUtils.isEmpty(emailEvent.getMessageReferenceDetails())) {
         //
         outboundServiceMessageReferences = new ArrayList<>();
         OutboundServiceMessageReferenceLog referenceLog = null;
         //
         for (MessageReferenceDetail referenceDetail : emailEvent.getMessageReferenceDetails()) {
            referenceLog = new OutboundServiceMessageReferenceLog();
            referenceLog.setMessageReferenceNumber(referenceDetail.getMessageReferenceNumber());
            referenceLog.setMessageReferenceDate(referenceDetail.getMessageReferenceDate());
            outboundServiceMessageReferences.add(referenceLog);
         }
      }
      //
      return outboundServiceMessageReferences;
   }

   /**
    * @param rowValue
    * @param value
    * @return
    */
   private String getRawValue(String rowValue, String value) {
      return StringUtils.isEmpty(rowValue) ? value : rowValue + "," + value;
   }

}
