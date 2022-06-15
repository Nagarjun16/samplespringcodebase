/**
 * {@link IncomingESBMessageNotificationProcessor}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.core.MessageDetail;
import com.ngen.cosys.message.resend.model.IncomingESBErrorMessageLog;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * Incoming ESB Message Notification Processor
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class IncomingESBMessageNotificationProcessor extends NotificationProcessor {

   private static final String INCOMING_MESSAGE_RESEND_TEMPLATE = "";
   private static final String INCOMING_MESSAGE_RESEND_MAIL_SUBJECT = "Incoming Message Resend Scheduled Job Status";
   private static final String INCOMING_MESSAGE_RESEND_MAIL_BODY = "Incoming Message Resend Process Information";
   
   /**
    * SEND Notification
    * 
    * @param messageDetails
    * @throws CustomException
    */
   public void sendNotification(Collection<?> messageDetails) throws CustomException {
      LOGGER.info("Notification Processor Send Notification - {}");
      LocalDateTime notificationTime = LocalDateTime.now();
      List<MessageNotification> messageNotifications = getMessageNotificationDetails(messageDetails, notificationTime);
      sendEmail(messageNotifications, INCOMING_MESSAGE_RESEND_TEMPLATE, getMailSubject(notificationTime), getMailBody(),
            getMailingList());
   }
   
   /**
    * @param messageDetails
    * @param notificationTime
    * @return
    */
   private List<MessageNotification> getMessageNotificationDetails(Collection<?> messageDetails,
         LocalDateTime notificationTime) {
      List<MessageNotification> messageNotifications = new ArrayList<>();
      MessageNotification messageNotification = null;;
      //
      MessageDetail messageDetail = null;
      IncomingESBErrorMessageLog errorMessageLog = null;
      //
      for (Object message : messageDetails) {
         if (message instanceof MessageDetail) {
            messageDetail = (MessageDetail) message;
            errorMessageLog = (IncomingESBErrorMessageLog) messageDetail.getErrorMessageLog();
         }
         if (Objects.nonNull(errorMessageLog.getIncomingESBResendMessageLog())
               && Objects.nonNull(errorMessageLog.getIncomingESBResendMessageLog().getFailedTime())) {
            messageNotification = new MessageNotification();
            messageNotification.setMessage(errorMessageLog.getMessage());
            messageNotification.setFailedReason(errorMessageLog.getErrorMessage());
            messageNotification.setFailedTime(errorMessageLog.getIncomingESBResendMessageLog().getFailedTime());
            messageNotification.setRetryLimit(errorMessageLog.getIncomingESBResendMessageLog().getRetryLimit());
            // Notification Time
            errorMessageLog.getIncomingESBResendMessageLog().setNotificationTime(notificationTime);
            messageNotifications.add(messageNotification);
         }
      }
      return messageNotifications;
   }
   
   /**
    * @param notificationTime
    * @return
    */
   private String getMailSubject(LocalDateTime notificationTime) {
      return INCOMING_MESSAGE_RESEND_MAIL_SUBJECT + " - "
            + String.valueOf(TenantZoneTime.getZoneDateTime(notificationTime, null));
   }
   
   /**
    * @return
    */
   private String getMailBody() {
      return INCOMING_MESSAGE_RESEND_MAIL_BODY;
   }
   
   /**
    * @return
    */
   private List<String> getMailingList() {
      return Collections.emptyList();
   }
   
}
