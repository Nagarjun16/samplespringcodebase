/**
 * {@link NotificationMember}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CiQ Notification Member configuration
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class NotificationMember extends CiQReport {

   /**
    * Default serialVersionUID 
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger notificationMemberId;
   private BigInteger notificationScheduleId;
   private String emailId;
   private String partyType;
   
}
