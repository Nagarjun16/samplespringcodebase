/**
 * 
 * ChatSummary.java
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
/**
 * 
 * This Model will act as global model for chat
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@Component
@ToString
@NoArgsConstructor
public class ChatSummary extends Message {/**
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
    * read status of a message whether it is read or not
    */
   private boolean readStatus;
   /**
    * 
    */
   private String type;
}
