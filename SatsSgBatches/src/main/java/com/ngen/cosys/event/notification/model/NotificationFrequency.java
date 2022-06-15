/**
 * NotificationFrequency.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for Notification Frequency
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class NotificationFrequency {

   private LocalTime fixedTime;
   private Integer repeatTime;
   
}
