/**
 * {@link OutgoingMessageNotificationProcessor}
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
import com.ngen.cosys.message.resend.model.OutgoingErrorMessageLog;
import com.ngen.cosys.timezone.util.TenantZoneTime;

/**
 * Outgoing Message Notification Processor
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class OutgoingMessageNotificationProcessor extends NotificationProcessor {

   private static final String OUTGOING_MESSAGE_RESEND_TEMPLATE = "";
   private static final String OUTGOING_MESSAGE_RESEND_MAIL_SUBJECT = "Outgoing Message Resend Scheduled Job Status";
   private static final String OUTGOING_MESSAGE_RESEND_MAIL_BODY = "Outgoing Message Resend Process Information";
   
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
      sendEmail(messageNotifications, OUTGOING_MESSAGE_RESEND_TEMPLATE, getMailSubject(notificationTime), getMailBody(),
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
      OutgoingErrorMessageLog errorMessageLog = null;
      //
      for (Object message : messageDetails) {
         if (message instanceof MessageDetail) {
            messageDetail = (MessageDetail) message;
            errorMessageLog = (OutgoingErrorMessageLog) messageDetail.getErrorMessageLog();
         }
         if (Objects.nonNull(errorMessageLog.getOutgoingResendMessageLog())
               && Objects.nonNull(errorMessageLog.getOutgoingResendMessageLog().getFailedTime())) {
            messageNotification = new MessageNotification();
            messageNotification.setMessage(errorMessageLog.getMessage());
            messageNotification.setFailedReason(errorMessageLog.getErrorMessage());
            messageNotification.setFailedTime(errorMessageLog.getOutgoingResendMessageLog().getFailedTime());
            messageNotification.setRetryLimit(errorMessageLog.getOutgoingResendMessageLog().getRetryLimit());
            // Notification Time
            errorMessageLog.getOutgoingResendMessageLog().setNotificationTime(notificationTime);
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
      return OUTGOING_MESSAGE_RESEND_MAIL_SUBJECT + " - "
            + String.valueOf(TenantZoneTime.getZoneDateTime(notificationTime, null));
   }
   
   /**
    * @return
    */
   private String getMailBody() {
      return OUTGOING_MESSAGE_RESEND_MAIL_BODY;
   }
   
   /**
    * @return
    */
   private List<String> getMailingList() {
      return Collections.emptyList();
   }
   
}
