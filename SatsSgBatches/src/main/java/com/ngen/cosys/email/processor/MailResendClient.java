/**
 * {@link MailResendClient}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.email.processor;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

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
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.email.enums.MailTypeUtils;
import com.ngen.cosys.email.model.MailResendDetail;
import com.ngen.cosys.email.model.MailResendDocument;
import com.sun.mail.smtp.SMTPMessage;
import com.sun.mail.smtp.SMTPTransport;
import com.sun.mail.util.PropUtil;

/**
 * Mail Resend Client
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class MailResendClient {

   private static final Logger LOGGER = LoggerFactory.getLogger(MailResendClient.class);
   //
   private static final String SENDER_NAME = "MAILER COSYS";
   
   /**
    * Re-Send E-Mail
    * 
    * @param properties
    * @param mailResendDetail
    * @param invalidNotification
    * @throws Exception
    */
   public void resend(Properties properties, MailResendDetail mailResendDetail, boolean invalidNotification)
         throws Exception {
      LOGGER.info("Mail Resend Client :: resend mail - {}");
      try {
         Session mailSession = getSessionInstance(properties);
         if (Objects.nonNull(mailSession)) {
            SMTPMessage mailMessage = new SMTPMessage(mailSession);
            // Set 'FROM' Address
            mailMessage.setFrom(new InternetAddress(getSMTPUsername(properties), SENDER_NAME));
            // Set 'TO' Address
            mailMessage.setRecipients(Message.RecipientType.TO,
                  setRecipientToAddress(mailResendDetail, invalidNotification));
            // Set 'CC' Address
            mailMessage.setRecipients(Message.RecipientType.CC, setRecipientCCAddress(mailResendDetail));
            // Set 'BCC' Address
            mailMessage.setRecipients(Message.RecipientType.BCC, setRecipientBCCAddress(mailResendDetail));
            // Set 'Subject' of the mail
            mailMessage.setSubject(mailResendDetail.getMessageSubject());
            // Set Send Date as Server System Date
            mailMessage.setSentDate(new Date());
            String mailText = mailResendDetail.getMessagePayload();
            if (Objects.nonNull(mailResendDetail.getTemplate())
                  && !StringUtils.isEmpty(mailResendDetail.getTemplate().getTemplatePayload())) {
               mailText = mailResendDetail.getTemplate().getTemplatePayload();
            }
            // Body part holds E-Mail message
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mailText, MailTypeUtils.TEXT_HTML.getValue());
            // Multipart for E-Mail body
            Multipart mailMultipart = new MimeMultipart();
            mailMultipart.addBodyPart(mimeBodyPart);
            // Mail Attachments
            setMailAttachments(mailMultipart, mailResendDetail);
            // Set all the assembled content to the message
            mailMessage.setContent(mailMultipart);
            // If some address invalid, send email to valid one's
            mailMessage.setSendPartial(true);
            // Get the SMTP Transport for SMTP Protocol
            SMTPTransport mailTransport = (SMTPTransport) mailSession.getTransport(MailTypeUtils.SMTP.getValue());
            // Set the Listener
            mailTransport.addTransportListener(new MailTransportListener());
            // Send message
            mailTransport.connect();
            mailMessage.saveChanges();
            // Send
            mailTransport.sendMessage(mailMessage, mailMessage.getAllRecipients());
            // Close the connection to the server
            mailTransport.close();
            // Status update
            mailResendDetail.setResendSuccess(true);
            if (invalidNotification) {
               mailResendDetail.setResendStatus("NOTIFIED");
            } else {
               mailResendDetail.setFailedReason(null);
               mailResendDetail.setResendStatus("RESENT");
            }
         }
      } catch (Exception ex) {
         mailResendDetail.setFailedReason(String.valueOf(ex.getMessage()));
         mailResendDetail.setResendStatus("FAILED");
      } finally {
         // Clean-up
      }
   }
   
   /**
    * Mail Session connection
    * 
    * @param properties
    * @return
    * @throws Exception
    */
   private Session getSessionInstance(Properties properties) throws Exception {
      properties.put(MailTypeUtils.SMTP_HOST, getSMTPServer(properties));
      properties.put(MailTypeUtils.SMTP_PORT, getSMTPPort(properties));
      boolean smtpAuthEnabled = isSMTPServerAuthEnabled(properties);
      if (smtpAuthEnabled) {
         properties.put(MailTypeUtils.SMTP_AUTH, String.valueOf(true));
         properties.put(MailTypeUtils.SMTP_TSL, getSecuredConnection(properties, true));
         String username = getSMTPUsername(properties);
         Assert.notNull(username, "SMTP Server username is not configured.");
         String password = getSMTPPassword(properties);
         Assert.notNull(password, "SMTP Server password is not configured.");
         return Session.getInstance(properties, new MailAuthenticator(username, password));
      }
      return null;
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
       * Implement this method to signal sent mail has been delivered successfully
       * - Status update of any email event
       * 
       * @see javax.mail.event.TransportListener#messageDelivered(javax.mail.event.TransportEvent)
       */
      @Override
      public void messageDelivered(TransportEvent event) {
         LOGGER.debug("Mail Resend Client :: Transport Listener - Message delivered successfully.");
      }

      /**
       * Implement this method to signal sent mail has NOT been delivered to any recipient
       * 
       * @see javax.mail.event.TransportListener#messageNotDelivered(javax.mail.event.TransportEvent)
       */
      @Override
      public void messageNotDelivered(TransportEvent event) {
         LOGGER.debug("Mail Resend Client :: Transport Listener - Message NOT delivered.");
      }

      /**
       * Implement this method to signal sent mail has been partially delivered to some recipient
       * 
       * @see javax.mail.event.TransportListener#messagePartiallyDelivered(javax.mail.event.TransportEvent)
       */
      @Override
      public void messagePartiallyDelivered(TransportEvent event) {
         LOGGER.debug("Mail Resend Client :: Transport Listener - Message partially delivered.");
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
    * Set Recipient TO Addresses
    * 
    * @param mailResendDetail
    * @param toAddresses
    * @param invalidNotification
    * @return
    * @throws AddressException
    */
   private Address[] setRecipientToAddress(MailResendDetail mailResendDetail, boolean invalidNotification)
         throws AddressException {
      if (invalidNotification) {
         Address[] toAddress = new InternetAddress[1];
         toAddress[0] = new InternetAddress(mailResendDetail.getMessageRecipients());
         return toAddress;
      }
      if (!StringUtils.isEmpty(mailResendDetail.getMessageRecipients())) {
         String[] recipientGroup = mailResendDetail.getMessageRecipients().split(",,");
         for (String recipient : recipientGroup) {
            if (recipient.contains("To :")) {
               String toRecipient = recipient.substring("To : [".length(), recipient.lastIndexOf("]"));
               String[] recipients = toRecipient.split(",");
               Address[] toAddresses = new InternetAddress[recipients.length];
               for (int j = 0; j < recipients.length; j++) {
                  toAddresses[j] = new InternetAddress(recipients[j].trim());
               }
               return toAddresses;
            }
         }
      }
      return null;
   }
   
   /**
    * Set Recipient CC Address
    * 
    * @param mailResendDetail
    * @return
    * @throws AddressException
    */
   private Address[] setRecipientCCAddress(MailResendDetail mailResendDetail) throws AddressException {
      if (!StringUtils.isEmpty(mailResendDetail.getMessageRecipients())) {
         String[] recipientGroup = mailResendDetail.getMessageRecipients().split(",,");
         for (String recipient : recipientGroup) {
            boolean bccGroup = false;
            if (recipient.contains("BCC :")) {
               bccGroup = true;
            } else if (!bccGroup && recipient.contains("CC :")) {
               String ccRecipient = recipient.substring("CC : [".length(), recipient.lastIndexOf("]"));
               String[] recipients = ccRecipient.split(",");
               Address[] ccAddresses = new InternetAddress[recipients.length];
               for (int j = 0; j < recipients.length; j++) {
                  ccAddresses[j] = new InternetAddress(recipients[j].trim());
               }
               return ccAddresses;
            }
         }
      }
      return null;
   }
   
   /**
    * Set Recipient BCC Address
    * 
    * @param mailResendDetail
    * @return
    * @throws AddressException
    */
   private Address[] setRecipientBCCAddress(MailResendDetail mailResendDetail) throws AddressException {
      if (!StringUtils.isEmpty(mailResendDetail.getMessageRecipients())) {
         String[] recipientGroup = mailResendDetail.getMessageRecipients().split(",,");
         for (String recipient : recipientGroup) {
            if (recipient.contains("BCC :")) {
               String bccRecipient = recipient.substring("BCC : [".length(), recipient.lastIndexOf("]"));
               String[] recipients = bccRecipient.split(",");
               Address[] bccAddresses = new InternetAddress[recipients.length];
               for (int j = 0; j < recipients.length; j++) {
                  bccAddresses[j] = new InternetAddress(recipients[j].trim());
               }
               return bccAddresses;
            }
         }
      }
      return null;
   }
   
   /**
    * @param mailMultipart
    * @param mailResendDetail
    * @throws MessagingException 
    */
   private void setMailAttachments(Multipart mailMultipart, MailResendDetail mailResendDetail)
         throws MessagingException {
      if (!CollectionUtils.isEmpty(mailResendDetail.getDocuments())) {
         for (MailResendDocument mailResendDocument : mailResendDetail.getDocuments()) {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            // Set the File Path + File Name
            byte[] bytes = getStreamBytes(mailResendDocument);
            mimeBodyPart.setContent(bytes, mailResendDocument.getDocumentFormat());
            mimeBodyPart.setDisposition(BodyPart.ATTACHMENT);
            mimeBodyPart.setFileName(mailResendDocument.getDocumentName());
            mailMultipart.addBodyPart(mimeBodyPart);
         }
      }
   }
   
   /**
    * @param mailResendDocument
    * @return
    */
   private byte[] getStreamBytes(MailResendDocument mailResendDocument) {
      Base64.Decoder decoder = Base64.getDecoder();
      try {
         return decoder.decode(mailResendDocument.getDocumentData());
      } catch (Exception ex) {
         LOGGER.debug("Mail Resend Client :: Decoding Exception occurred - {}", String.valueOf(ex));
      }
      return null;
   }
   
}
