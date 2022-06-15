/**
 * 
 * MailClient.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 17 JUN, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service.mail.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.annotation.PreDestroy;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.payload.EMailEvent.AttachmentStream;
import com.ngen.cosys.events.stream.util.StreamDecoder;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.util.LoggerUtil;
import com.ngen.cosys.impbd.service.mail.enums.MailTypeUtils;
import com.ngen.cosys.impbd.service.mail.logger.MailLoggerService;
import com.sun.mail.smtp.SMTPMessage;
import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.PropUtil;

/**
 * SMTP Email Sender sends an email to Mail Recipient Address
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
public class MailClient {

   private static final Logger LOGGER = LoggerFactory.getLogger(MailClient.class);

   private static final int MAX_RECIPIENT_COUNT = 99;
   private static final String EMPTY = "";
   private static final String SENDER_NAME = "MAILER COSYS";

   @Autowired
   MailLoggerService mailLogger;

   private Session mailSession = null;
   private Properties configProperties = new Properties();

   /**
    * Destroy method to clean up Mail Session and Properties
    */
   @PreDestroy
   public void disconnect() {
      //
      if (Objects.nonNull(mailSession))
         mailSession = null;
      //
      if (Objects.nonNull(configProperties))
         configProperties.clear();
   }

   /**
    * MailClient Session connect event
    * 
    * @param properties
    * @return
    * @throws CustomException
    */
   public boolean connect(Properties properties) throws CustomException {
      //
      LOGGER.warn("Mail Services connect {} ");
      configProperties.putAll(properties);
      //
      configProperties.put(MailTypeUtils.SMTP_HOST, getSMTPServer(configProperties));
      configProperties.put(MailTypeUtils.SMTP_PORT, getSMTPPort(configProperties));
      boolean smtpAuthEnabled = isSMTPServerAuthEnabled(configProperties);
      //
      LOGGER.warn("SMTP Auth Enabled {} ", smtpAuthEnabled);
      if (smtpAuthEnabled) {
         configProperties.put(MailTypeUtils.SMTP_AUTH, String.valueOf(true));
         configProperties.put(MailTypeUtils.SMTP_TSL, getSecuredConnection(configProperties, true));
         //
         String username = getSMTPUsername(configProperties);
         if (Objects.isNull(username)) {
            Assert.notNull("SMTP Server username is not configured..! {} ", username);
         }
         //
         String password = this.getSMTPPassword(configProperties);
         if (Objects.isNull(password)) {
            Assert.notNull("SMTP Server password is not configured..! {} ", password);
         }
         //
         mailSession = Session.getInstance(configProperties, new MailAuthenticator(username, password));
      } else {
         mailSession = Session.getInstance(configProperties);
      }
      //
      if (Objects.isNull(mailSession)) {
         LOGGER.warn("SMTP Server Mail Session is null, Credentials are wrong..");
         return false;
      }
      LOGGER.warn("SMTP Server Mail Session connected successfully.. ");
      return true;
   }

   /**
    * Send Mail
    * 
    * @param mailBO
    * @throws CustomException
    */
   public boolean send(EMailEvent emailEvent) throws CustomException {

      // successful or not
      boolean mailSent = true;

      // Mail Transport
      SMTPTransport mailTransport = null;

      // Body Part holds E-Mail Message
      MimeBodyPart mimeBodyPart = null;

      // SMTP Object reference
      SMTPMessage mailMessage = null;

      // Multipart Object for build E-Mail Body
      Multipart mailMultipart = null;

      // Collection holds all To Addresses
      Address[] toAddresses = null;

      // Collection holds all CC Addresses
      Address[] ccAddresses = null;

      // Collection holds all BCC Addresses
      Address[] bccAddresses = null;

      // Reply TO Address
      Address[] replyToAddress = null;

      // Mail Text
      String mailText = EMPTY;

      try {
         if (StringUtils.isEmpty(emailEvent.getMailFrom())) {
            String username = getSMTPUsername(configProperties);
            emailEvent.setMailFrom(username);
            emailEvent.setSenderName(SENDER_NAME);
         }
         // EMail Recipient Count
         emailEvent.setRecipientCount(getMessageRecipientCount(emailEvent));
         //
         if (emailEvent.getRecipientCount() > MAX_RECIPIENT_COUNT) {
            LOGGER.warn("Cloud Interface - Mail Services Recipient Maximum Count reached :: {}",
                  emailEvent.getRecipientCount());
            return mailSent;
         }
         mailMessage = new SMTPMessage(mailSession);
         //
         mailMessage.setFrom(new InternetAddress(emailEvent.getMailFrom(), emailEvent.getSenderName()));
         // Set 'TO' Addresses
         toAddresses = setRecipientToAddress(emailEvent);
         mailMessage.setRecipients(Message.RecipientType.TO, toAddresses);
         // Set 'CC' Addresses
         ccAddresses = setRecipientCCAddress(emailEvent);
         mailMessage.setRecipients(Message.RecipientType.CC, ccAddresses);
         // Set 'BCC' Addresses
         bccAddresses = setRecipientBCCAddress(emailEvent);
         mailMessage.setRecipients(Message.RecipientType.BCC, bccAddresses);
         // Set 'REPLYTO' Address
         if (!StringUtils.isEmpty(emailEvent.getReplyTo())) {
            replyToAddress = new InternetAddress[1];
            replyToAddress[0] = new InternetAddress(emailEvent.getReplyTo());
            mailMessage.setReplyTo(replyToAddress);
         }
         // Set Subject of the Mail
         mailMessage.setSubject(emailEvent.getMailSubject());
         // Set Sent Date as Server System Date
         mailMessage.setSentDate(new Date());
         //
         if (Objects.isNull(emailEvent.getTemplate())
               || StringUtils.isEmpty(emailEvent.getTemplate().getTemplateMessage())) {
            mailText = emailEvent.getMailBody();
         } else {
            mailText = emailEvent.getTemplate().getTemplateMessage();
         }
         // Configuration files to map MIME Types
         multipartMIMEConfig();
         // Construct Messagebody
         mimeBodyPart = new MimeBodyPart();
         mimeBodyPart.setContent(mailText, MailTypeUtils.TEXT_HTML.getValue());
         //
         mailMultipart = new MimeMultipart();
         mailMultipart.addBodyPart(mimeBodyPart);
         LOGGER.warn("Mail Multipart emailBody details initiated.. ");
         // Add attachments
         if (!CollectionUtils.isEmpty(emailEvent.getMailAttachments())) {
            setMailAttachments(mailMultipart, emailEvent);
            // Set all the assembled content to the Message
            mailMessage.setContent(mailMultipart, MailTypeUtils.MULTIPART_MIXED.getValue());
         } else {
            mailMessage.setContent(mailMultipart, MailTypeUtils.TEXT_HTML.getValue());
         }
         LOGGER.warn("Mail Multipart emailBody content Prepared.. ");
         // Set the Notify Address
         if (!StringUtils.isEmpty(emailEvent.getNotifyAddress())) {
            mailMessage.setEnvelopeFrom(emailEvent.getNotifyAddress());
         }
         // If some address invalid, send email to valid one's
         mailMessage.setSendPartial(true);
         // Get the SMTPTransport for SMTP Protocol
         mailTransport = (SMTPTransport) mailSession.getTransport(MailTypeUtils.SMTP.getValue());
         // Set the Listener
         mailTransport.addTransportListener(new MailTransportListener());
         // Send message
         mailTransport.connect();
         //
         mailMessage.saveChanges();
         // send
         mailTransport.sendMessage(mailMessage, mailMessage.getAllRecipients());
         // Close the connection to the server
         mailTransport.close();
         //
         emailEvent.setMailStatus("SENT");
      } catch (Exception ex) {
         //
         emailEvent.setMailStatus("EXCEPTION");
         LOGGER.warn("Exception occurred while sending mail {} ", getStackTrace(ex));
         emailEvent.setFailedReason(String.valueOf(ex));
         // mail sent unsuccessful
         mailSent = false;
      } finally {
         // Log for Mail Delivery Status
         mailLogger.logEMailEventMessage(emailEvent);
      }
      return mailSent;
   }

   /**
    * Set Recipient TO Addresses
    * 
    * @param mailBO
    * @param toAddresses
    * @return
    * @throws AddressException
    */
   private Address[] setRecipientToAddress(EMailEvent emailEvent) throws AddressException {
      int recipientToCnt = 0;
      boolean recipientTo = false;
      //
      if (Objects.nonNull(emailEvent.getMailTo())) {
         recipientToCnt = 1;
         recipientTo = true;
      }
      if (Objects.nonNull(emailEvent.getMailToAddress())
            && !CollectionUtils.isEmpty(Arrays.asList(emailEvent.getMailToAddress()))) {
         recipientToCnt += emailEvent.getMailToAddress().length;
      }
      Address[] toAddresses = new InternetAddress[recipientToCnt];
      int j = 0;
      if (recipientTo) {
         toAddresses[j] = new InternetAddress(emailEvent.getMailTo());
         j += 1;
      }
      while (j < recipientToCnt) {
         int k = j;
         if (recipientTo) {
            k = j - 1;
         }
         toAddresses[j] = new InternetAddress(emailEvent.getMailToAddress()[k]);
         j++;
      }
      //
      return toAddresses;
   }

   /**
    * Set Recipient CC Addresses
    * 
    * @param mailBO
    * @param ccAddresses
    * @return
    * @throws AddressException
    */
   private Address[] setRecipientCCAddress(EMailEvent emailEvent) throws AddressException {
      int recipientCCCnt = 0;
      boolean recipientCC = false;
      //
      if (!StringUtils.isEmpty(emailEvent.getMailCC())) {
         recipientCCCnt = 1;
         recipientCC = true;
      }
      if (!ObjectUtils.isEmpty(emailEvent.getMailCCAddress())) {
         recipientCCCnt += emailEvent.getMailCCAddress().length;
      }
      Address[] ccAddresses = new InternetAddress[recipientCCCnt];
      int j = 0;
      if (recipientCC) {
         ccAddresses[j] = new InternetAddress(emailEvent.getMailCC());
         j += 1;
      }
      for (; j < recipientCCCnt; j++) {
         int k = j;
         if (recipientCC) {
            k = j - 1;
         }
         ccAddresses[j] = new InternetAddress(emailEvent.getMailCCAddress()[k]);
      }
      //
      return ccAddresses;
   }

   /**
    * Set Recipient BCC Addresses
    * 
    * @param mailBO
    * @param bccAddresses
    * @return
    * @throws AddressException
    */
   private Address[] setRecipientBCCAddress(EMailEvent emailEvent) throws AddressException {
      int recipientBCCCnt = 0;
      boolean recipientBCC = false;
      //
      if (!StringUtils.isEmpty(emailEvent.getMailBCC())) {
         recipientBCCCnt = 1;
         recipientBCC = true;
      }
      if (!ObjectUtils.isEmpty(emailEvent.getMailBCCAddress())) {
         recipientBCCCnt += emailEvent.getMailBCCAddress().length;
      }
      Address[] bccAddresses = new InternetAddress[recipientBCCCnt];
      int j = 0;
      if (recipientBCC) {
         bccAddresses[j] = new InternetAddress(emailEvent.getMailBCC());
         j += 1;
      }
      for (; j < recipientBCCCnt; j++) {
         int k = j;
         if (recipientBCC) {
            k = j - 1;
         }
         bccAddresses[j] = new InternetAddress(emailEvent.getMailBCCAddress()[k]);
      }
      //
      return bccAddresses;
   }

   /**
    * Get EMail Recipient count
    * 
    * @param emailEvent
    * @return
    */
   private int getMessageRecipientCount(EMailEvent emailEvent) {
      int recipientToCnt = 0;
      int recipientCCCnt = 0;
      int recipientBCCCnt = 0;
      // EmailTo-Count
      if (!StringUtils.isEmpty(emailEvent.getMailTo())) {
         recipientToCnt += 1;
      }
      if (!ObjectUtils.isEmpty(emailEvent.getMailToAddress())) {
         recipientToCnt += emailEvent.getMailToAddress().length;
      }
      // EmailCC-Count
      if (!StringUtils.isEmpty(emailEvent.getMailCC())) {
         recipientCCCnt += 1;
      }
      if (!ObjectUtils.isEmpty(emailEvent.getMailCCAddress())) {
         recipientCCCnt += emailEvent.getMailCCAddress().length;
      }
      // EmailBCC-Count
      if (!StringUtils.isEmpty(emailEvent.getMailBCC())) {
         recipientBCCCnt += 1;
      }
      if (!ObjectUtils.isEmpty(emailEvent.getMailBCCAddress())) {
         recipientBCCCnt += emailEvent.getMailBCCAddress().length;
      }
      // Recipient Count
      return recipientToCnt + recipientCCCnt + recipientBCCCnt;
   }

   /**
    * @param mailMultipart
    * @throws MessagingException
    */
   private void setMailAttachments(Multipart mailMultipart, EMailEvent emailEvent) throws MessagingException {
      for (Map.Entry<String, AttachmentStream> entry : emailEvent.getMailAttachments().entrySet()) {
         MimeBodyPart mimeBodyPart = new MimeBodyPart();
         // Set the file Path + File Name
         byte[] bytes = StreamDecoder.getStreamBytes(entry.getValue());
         mimeBodyPart.setContent(bytes, entry.getValue().getFileType());
         mimeBodyPart.setDisposition(BodyPart.ATTACHMENT);
         mimeBodyPart.setFileName(entry.getValue().getFileName());
         mailMultipart.addBodyPart(mimeBodyPart);
      }
   }

   /**
    * Multipart MIME Configuration
    */
   private void multipartMIMEConfig() {
      MailcapCommandMap mailcap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
      mailcap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
      mailcap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
      mailcap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
      mailcap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
      mailcap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
      CommandMap.setDefaultCommandMap(mailcap);
   }

   /**
    * Mail Authenticator implements Authenticator class requires for User
    * verification
    *
    */
   private class MailAuthenticator extends Authenticator {

      private String username;
      private String password;

      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
         return new PasswordAuthentication(username, password);
      }

      public MailAuthenticator(String username, String password) {
         this.username = username;
         this.password = password;
      }

   } // END of MailAuthenticator

   /**
    * Mail Transport Listener implements methods to signal the delivery status of
    * an email
    *
    */
   private class MailTransportListener implements TransportListener {

      /**
       * @see javax.mail.event.TransportListener#messageDelivered(javax.mail.event.TransportEvent)
       * 
       */
      @Override
      public void messageDelivered(TransportEvent event) {
         // Implement this method to signal sent mail has been delivered successfully
         // Status update of any email event
         LOGGER.warn(LoggerUtil.getLoggerMessage(this.getClass().getName(), "messageDelivered", Level.DEBUG,
               "Message delivered successfully..!", null));
      }

      /**
       * @see javax.mail.event.TransportListener#messageNotDelivered(javax.mail.event.TransportEvent)
       * 
       */
      @Override
      public void messageNotDelivered(TransportEvent event) {
         // Implement this method to signal sent mail has NOT been delivered to any
         // recipient
         LOGGER.warn(LoggerUtil.getLoggerMessage(this.getClass().getName(), "messageNotDelivered", Level.DEBUG,
               "Message NOT delivered.. {} Failed ", null));
      }

      /**
       * @see javax.mail.event.TransportListener#messagePartiallyDelivered(javax.mail.event.TransportEvent)
       * 
       */
      @Override
      public void messagePartiallyDelivered(TransportEvent event) {
         // Implement this method to signal sent mail has been partially delivered to
         // some recipient
         LOGGER.warn(LoggerUtil.getLoggerMessage(this.getClass().getName(), "messagePartiallyDelivered", Level.DEBUG,
               "Message partially delivered..! ", null));
      }

   } // END of MailTransportListener

   /**
    * @param properties
    * @return
    */
   private String getSMTPServer(Properties properties) {
      return properties.getProperty(MailTypeUtils.SMTP_HOST.getValue());
   }

   /**
    * @param properties
    * @return
    */
   private String getSMTPPort(Properties properties) {
      return properties.getProperty(MailTypeUtils.SMTP_PORT.getValue());
   }

   /**
    * @param properties
    * @param tlsCertificate
    *           (Recommended)
    * @return
    */
   private String getSecuredConnection(Properties properties, boolean tlsCertificate) {
      if (tlsCertificate) {
         return properties.getProperty(MailTypeUtils.SMTP_TSL.getValue());
      }
      return properties.getProperty(MailTypeUtils.SMTP_SSL.getValue());
   }

   /**
    * @param properties
    * @return
    */
   private boolean isSMTPServerAuthEnabled(Properties properties) {
      return Objects.equals(true, PropUtil.getBooleanProperty(properties, MailTypeUtils.SMTP_AUTH.getValue(), false));
   }

   /**
    * @param properties
    * @return
    */
   private String getSMTPUsername(Properties properties) {
      return properties.getProperty(MailTypeUtils.SMTP_USERNAME.getValue());
   }

   /**
    * @param properties
    * @return
    */
   private String getSMTPPassword(Properties properties) {
      return properties.getProperty(MailTypeUtils.SMTP_PASSWORD.getValue());
   }

   /**
    * Update EMail Delivery status
    * 
    * @param msgStatus
    * @param errorMessage
    * @param messageId
    */
   private void updateEMailDeliveryStatus(String msgStatus, String errorMessage, BigInteger messageId) {
      try {
         mailLogger.updateEMailMessageLog(msgStatus, errorMessage, messageId);
      } catch (CustomException ex) {
         LOGGER.warn("Cloud EMail Services - updateEMailDeliveryStatus Exception occurred :: {}", String.valueOf(ex));
      }
   }

   /**
    * @param throwable
    * @return
    */
   private static String getStackTrace(Throwable throwable) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw, true);
      throwable.printStackTrace(pw);
      if (Objects.nonNull(sw.getBuffer())) {
         return sw.getBuffer().toString();
      }
      return null;
   }

}