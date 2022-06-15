/**
 * {@link MailResendProcessor}
 * 
 * @copyright SATS Singapore
 * @author NIIT
 */
package com.ngen.cosys.email.processor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.email.config.MailProperties;
import com.ngen.cosys.email.model.MailResendDetail;
import com.ngen.cosys.mail.config.MailConfig;
import com.ngen.cosys.mail.config.MailProps;

/**
 * Mail Resend Processor
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class MailResendProcessor {

   private static final Logger LOGGER = LoggerFactory.getLogger(MailResendProcessor.class);
   //
   private static final String COMMA = ", ";
   private static final String HYPHEN = " - ";
   private static final String LINE_SEPARATOR = System.getProperty("line.separator");
   private static final String DEFAULT_CHANNEL = "STANDARD_MAIL";
   private static final String INVALID_MAIL_ADDRESSES_TO_BE_REMOVED = "Invalid Mail Addresses To Be Removed";
   
   @Autowired
   MailConfig mailConfig;
   
   @Autowired
   MailResendClient mailResendClient;
   
   /**
    * @param failedMailDetails
    * @throws Exception
    */
   public void resendFailedMailDetails(List<MailResendDetail> failedMailDetails) throws Exception {
      LOGGER.info("Mail Resend Processor :: resend mail details - {}");
      MailProps mailProps = mailConfig.getConfiguredMailProperties(DEFAULT_CHANNEL);
      //
      if (Objects.nonNull(mailProps)) {
         MailProperties.setMailConfig(mailProps);
         for (MailResendDetail mailResendDetail : failedMailDetails) {
            mailResendClient.resend(MailProperties.properties, mailResendDetail, false);
         }
      }
   }
   
   /**
    * @param invalidMailAddressDetails
    * @param notificationAddress
    * @throws Exception
    */
   public void notifyInvalidMailAddressesDetails(List<MailResendDetail> invalidMailAddressDetails,
         String notificationAddress) throws Exception {
      LOGGER.info("Mail Resend Processor :: Notify invalid mail addresses details - {}");
      if (!CollectionUtils.isEmpty(invalidMailAddressDetails)) {
         Map<String, Set<String>> filteredInvalidMailAddresses = getInvalidAddressesByMailSubject(
               invalidMailAddressDetails);
         String bodyString = getMailBodyDetail(filteredInvalidMailAddresses);
         MailResendDetail mailResendDetail = getInvalidMailAddressesNotificationPayload(bodyString, notificationAddress);
         MailProps mailProps = mailConfig.getConfiguredMailProperties(DEFAULT_CHANNEL);
         if (Objects.nonNull(mailProps)) {
            MailProperties.setMailConfig(mailProps);
            mailResendClient.resend(MailProperties.properties, mailResendDetail, true);
            String messageStatus = mailResendDetail.isResendSuccess() ? "NOTIFIED" : "FAILED";
            // Status Update
            for (MailResendDetail notifyResendDetail : invalidMailAddressDetails) {
               notifyResendDetail.setResendStatus(messageStatus);
            }
         }
      }
   }
   
   /**
    * @param mailBody
    * @param notificationAddress
    * @return
    */
   private MailResendDetail getInvalidMailAddressesNotificationPayload(String mailBody, String notificationAddress) {
      MailResendDetail mailResendDetail = new MailResendDetail();
      mailResendDetail.setMessageSubject(INVALID_MAIL_ADDRESSES_TO_BE_REMOVED);
      mailResendDetail.setMessagePayload(mailBody);
      mailResendDetail.setMessageRecipients(notificationAddress);
      return mailResendDetail;
   }
   
   /**
    * @param invalidMailAddressDetails
    * @return
    */
   private Map<String, Set<String>> getInvalidAddressesByMailSubject(List<MailResendDetail> invalidMailAddressDetails) {
      Map<String, Set<String>> invalidAddresses = new HashMap<>();
      for (MailResendDetail invalidAddress : invalidMailAddressDetails) {
         Set<String> failedReasons = Collections.emptySet();
         if (invalidAddresses.containsKey(invalidAddress.getMessageSubject())) {
            invalidAddresses.get(invalidAddress.getMessageSubject()).add(invalidAddress.getFailedReason());
         } else {
            failedReasons = new HashSet<>();
            failedReasons.add(invalidAddress.getFailedReason());
            // Update in Invalid Addresses
            invalidAddresses.put(invalidAddress.getMessageSubject(), failedReasons);
         }
      }
      return invalidAddresses;
   }
   
   /**
    * @param invalidMailAddresses
    * @return
    */
   private String getMailBodyDetail(Map<String, Set<String>> invalidMailAddresses) {
      StringBuilder mailBody = new StringBuilder();
      for (Iterator<Entry<String, Set<String>>> iterator = invalidMailAddresses.entrySet().iterator(); iterator
            .hasNext();) {
         Map.Entry<String, Set<String>> entry = iterator.next();
         mailBody.append(entry.getKey()).append(HYPHEN);
         String failedReason = "";
         for (String mailAddress : entry.getValue()) {
            failedReason = getRawValue(failedReason, mailAddress);
         }
         mailBody.append(failedReason).append(LINE_SEPARATOR);
      }
      return mailBody.toString();
   }
   
   /**
    * @param result
    * @param value
    * @return
    */
   private String getRawValue(String result, String value) {
      return StringUtils.isEmpty(result) ? value : result + COMMA + value;
   }
   
}
