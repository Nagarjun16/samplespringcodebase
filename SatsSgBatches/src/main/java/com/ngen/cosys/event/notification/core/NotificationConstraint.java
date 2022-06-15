/**
 * NotificationConstraint.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.core;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for Event Notification Constraint
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class NotificationConstraint {

   // Any attempt Flag
   private boolean anyAttempt = false;
   // Any Match Flag
   private boolean anyMatch = false;
   private boolean allMatch = false;
   private boolean hasReference = false;
   private boolean hasCompleted = false;
   // SLA Type
   private String slaType;
   private BigInteger eventNotificationId;
   private Set<String> hasValues = Collections.emptySet();
   // List of parameter
   private List<ParameterConstraint> parameterConstraints = Collections.emptyList();
   
}
