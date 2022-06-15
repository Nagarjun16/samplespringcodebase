/**
 * NotificationStats.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.core;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for Event Notification Analysis Stats
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class NotificationStats {

   private String module;
   private String entity;
   private String eventName;
   private BigInteger eventNotificationLogId; // Reference Id for Snapshot
   //
   private int totalRecords = 0; // executionCount + completedCount
   private int executionCount = 0; // newlyAdded + updatedCount
   private int newlyAdded = 0;
   private int updatedCount = 0;
   private int completedCount = 0;
   // Notification Constraints of EntityHolder & List of notification constraints
   private Map<EntityConstraint, List<NotificationConstraint>> notificationConstraints = Collections.emptyMap();
   //
   private LocalDateTime startTime;
   private LocalDateTime endTime;
   
}
