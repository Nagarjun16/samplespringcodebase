/**
 * {@link NotificationEvent}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Holds domain model for notification event
 *  
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class NotificationEvent extends BaseBO {

   /**
    * Default serial version UID
    */
   private static final long serialVersionUID = 1L;
   
   private String eventName;
   
}
