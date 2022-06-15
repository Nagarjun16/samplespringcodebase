/**
 * {@link EventNotificationLogDetails}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Event Notification Log details
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class EventNotificationLogDetails extends BaseBO {

   /**
    * Default serialVersionUID 
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger eventNotificationLogDetailsId;
   private BigInteger eventNotificationLogId;
   private BigInteger eventNotificationId;
   private BigInteger flightId;
   private String flightKey;
   private LocalDateTime flightDateTime;
   private boolean hasCompleted;
   
}
