/**
 * EventNotificationConfiguration.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.type;

import com.ngen.cosys.event.notification.core.NotificationStats;
import com.ngen.cosys.event.notification.model.EventNotificationModel;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface is used for event notification configuration
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface EventNotificationConfiguration {

   /**
    * Log event notification
    * 
    * @param notificationStats
    * @throws CustomException
    */
   void logEventNotification(NotificationStats notificationStats) throws CustomException;
   
   /**
    * Send Event Notification
    * 
    * @param eventNotificationModel
    * @param notificationStats
    * @throws CustomException
    */
   void sendEventNotification(EventNotificationModel eventNotificationModel, NotificationStats notificationStats)
         throws CustomException;
   
}
