/**
 * Notification Message Controller
 */
package com.ngen.cosys.message.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ngen.cosys.common.ConfigurationConstant;
import com.ngen.cosys.message.model.NotificationMessage;

/**
 * Notification Message Controller (For Testing)
 */
@Controller
public class NotificationMessageController {

   /**
    * Process Message (For Testing)
    * 
    * @param message
    *           Notification Message
    * @return Notification Messages
    */
   @MessageMapping(ConfigurationConstant.NOTIFICATION_MESSAGE_MAPPING)
   @SendTo(ConfigurationConstant.NOTIFICATION_SEND_TO)
   public NotificationMessage processMessage(
         @DestinationVariable(ConfigurationConstant.FROM_TO_PATH_VARIABLE) String fromTo, NotificationMessage message) {
      return message;
   }
}
