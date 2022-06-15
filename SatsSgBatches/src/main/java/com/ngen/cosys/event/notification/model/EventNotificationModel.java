/**
 * EventNotificationModel
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Holds of configured attributes 
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class EventNotificationModel extends EventTypeConfig {

   /**
    * Default serialVersionUID
    */
   private static final long serialVersionUID = 1L;
   //
   private List<EventNotification> eventNotification;
   
}
