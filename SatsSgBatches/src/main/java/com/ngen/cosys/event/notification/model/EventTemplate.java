/**
 * EventTemplate
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
 * Holds of event type associated template for communication modes 
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class EventTemplate {

   private BigInteger eventNotificationId;
   //
   private BigInteger templateId;
   private String templateName;
   private String emailTemplate;
   private String smsTemplate;
   private String faxTemplate;
   
}
