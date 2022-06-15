/**
 * EventNotificationModel
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Holds of event logging attributes 
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class EventNotificationLog extends EventTypeConfig {

   /**
    * Default serialVersionUID
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger eventNotificationLogId;
   private LocalDateTime startTime;
   private LocalDateTime endTime;
   private LocalDateTime notificationSentTime;
   private LocalDateTime notificationScheduledTime;
   private Integer notificationSentCount;
   private Integer totalRecords;
   private Integer executionCount;
   private Integer newlyAdded;
   private Integer updatedCount;
   private Integer completedCount;
   
}
