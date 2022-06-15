/**
 * 
 * ChatSummaryMessage.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22th August, 2018 NIIT -
 */
package com.ngen.cosys.message.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@ToString
@NoArgsConstructor
public class ChatSummaryMessage extends Message {
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   /**
    * LoginCode of the user
    */
   private String userLoginCode;
   /**
    * short name of the user
    */
   private String userShortName;
   /**
    * profile picture of the user
    */
   private String userProfilePicture;
   /**
    * unread message count
    */
   private Long unreadMessageCount;
   
   /**
    * type of Message
    */
   private String messageType;
   /**
    * flightId of Flight
    */
   private long flightId;
   /**
    * segment order of flight
    */
   private long flightSegmentOrder;

}
