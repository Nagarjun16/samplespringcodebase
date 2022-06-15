/**
 * EventCommunication.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Event communication mode type
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class EventCommunication {

   private BigInteger eventNotificationId;
   private BigInteger communicationTypeId;
   private String communicationType;
   
}
