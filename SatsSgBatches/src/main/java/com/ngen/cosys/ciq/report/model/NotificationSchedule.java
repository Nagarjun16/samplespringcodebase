/**
 * {@link NotificationSchedule}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.ciq.report.model;

import java.math.BigInteger;
import java.time.LocalTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CiQ Notification Schedule configuration
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class NotificationSchedule extends CiQReport {

   /**
    * Default serialVersionUID 
    */
   private static final long serialVersionUID = 1L;
   //
   private BigInteger notificationScheduleId;
   private Integer generatedOn;
   private Integer dayInMonth; // Monthly Job
   private LocalTime configuredTime; // Daily
   private String weekOfTheDay; // Weekly Job
   private String carrierCode;
   private String messageType;
   private String reportFrequency;
   //
   private List<NotificationMember> notificationMembers;
   private String fromDate;
   private String toDate;
   private boolean transitFlag;
}
