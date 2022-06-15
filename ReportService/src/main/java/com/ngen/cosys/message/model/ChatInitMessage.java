package com.ngen.cosys.message.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel("ChatInitMessage")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatInitMessage extends ChatMessage {

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
   
   

}
