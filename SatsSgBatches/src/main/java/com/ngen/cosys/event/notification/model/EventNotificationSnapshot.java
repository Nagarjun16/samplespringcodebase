/**
 * EventNotificationSnapshot
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ngen.cosys.event.notification.enums.EntityEventTypes;
import com.ngen.cosys.event.notification.enums.EventEntity;
import com.ngen.cosys.event.notification.enums.SLAType;
import com.ngen.cosys.framework.model.BaseBO;

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
public class EventNotificationSnapshot extends BaseBO {

   /**
    * Default serialVersionUID
    */
   private static final long serialVersionUID = 1L;
   //
   private EventEntity entity;
   private EntityEventTypes eventType;
   private SLAType slaType;
   private String flightKey;
   private LocalDate flightDate;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   private boolean eventCompleted;
   private LocalDateTime eventCompletedOn;
   private Integer notificationSentCount;
   private LocalDateTime notificationSentOn;
   
}
