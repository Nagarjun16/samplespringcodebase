/**
 * Chat Message Controller
 */
package com.ngen.cosys.message.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.ngen.cosys.common.ConfigurationConstant;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.model.ChatInitMessage;
import com.ngen.cosys.message.model.ChatMessage;
import com.ngen.cosys.message.model.Message;
import com.ngen.cosys.message.service.CommonService;

/**
 * Chat Message Controller
 */
@Controller
public class ChatMessageController {

   @Autowired
   private SimpMessagingTemplate simpMessagingTemplate;

   @Autowired
   private CommonService commonService;

   /**
    * Process Message to User
    * 
    * @param tenant
    *           Tenant
    * @param from
    *           From User
    * @param to
    *           To User
    * @param message
    *           Message
    * @return
    * @throws CustomException
    */
   @MessageMapping(ConfigurationConstant.CHAT_USER_MESSAGE_MAPPING)
   @SendTo(ConfigurationConstant.CHAT_SEND_TO_USER)
   public ChatMessage processUserMessage(@DestinationVariable(ConfigurationConstant.TENANT_PATH_VARIABLE) String tenant,
         @DestinationVariable(ConfigurationConstant.FROM_PATH_VARIABLE) String from,
         @DestinationVariable(ConfigurationConstant.TO_PATH_VARIABLE) String to, @Payload ChatMessage message)
         throws CustomException {
      commonService.saveUserMessages(message);
      message.setDetail(commonService.getUserChatSummary(message));
      return message;
   }

   /**
    * Process Message to Group
    * 
    * @param tenant
    *           Tenant
    * @param from
    *           From User
    * @param to
    *           To User
    * @param message
    *           Message
    * @return
    * @throws CustomException
    */
   @MessageMapping(ConfigurationConstant.CHAT_GROUP_MESSAGE_MAPPING)
   @SendTo(ConfigurationConstant.CHAT_SEND_TO_GROUP)
   public ChatMessage processGroupMessage(
         @DestinationVariable(ConfigurationConstant.TENANT_PATH_VARIABLE) String tenant,
         @DestinationVariable(ConfigurationConstant.FROM_PATH_VARIABLE) String from,
         @DestinationVariable(ConfigurationConstant.TO_PATH_VARIABLE) String to, @Payload ChatMessage message)
         throws CustomException {
      commonService.saveGroupMessages(message);
      //message.setDetail(commonService.getFromAndToGroupChatMessages(message));
      return message;
   }

   /**
    * Process Message for Summary
    * 
    * @param tenant
    *           Tenant
    * @param from
    *           From User
    * @param message
    *           Message
    * @return
    * @throws CustomException
    */
   @MessageMapping(ConfigurationConstant.CHAT_SUMMARY_MESSAGE_MAPPING)
   @SendTo(ConfigurationConstant.CHAT_SEND_TO_SUMMARY)
   public ChatMessage processSummaryMessage(
         @DestinationVariable(ConfigurationConstant.TENANT_PATH_VARIABLE) String tenant,
         @DestinationVariable(ConfigurationConstant.FROM_PATH_VARIABLE) String from, @Payload ChatMessage message)
         throws CustomException {
      // Update Summary Detail
      message.setDetail(commonService.getUserChatSummary(message));
      //
      return message;
   }

   /**
    * Process Message for Init
    * 
    * @param tenant
    *           Tenant
    * @param from
    *           From User
    * @param message
    *           Message
    * @return
    * @throws CustomException
    */
   @MessageMapping(ConfigurationConstant.CHAT_INIT_MESSAGE_MAPPING)
   @SendTo(ConfigurationConstant.CHAT_SEND_TO_INIT)
   public ChatInitMessage processInitMessage(
         @DestinationVariable(ConfigurationConstant.TENANT_PATH_VARIABLE) String tenant,
         @DestinationVariable(ConfigurationConstant.FROM_PATH_VARIABLE) String from,
         @DestinationVariable(ConfigurationConstant.TO_PATH_VARIABLE) String to,
         @Payload ChatInitMessage chatInitMessage) throws CustomException {
      if (Objects.equals(chatInitMessage.getMessageType(), ConfigurationConstant.USER)) {
         // for user
         chatInitMessage.setDetail(commonService.getFromAndToChatSummary(chatInitMessage));
      }
      else if(Objects.equals(chatInitMessage.getMessageType(), ConfigurationConstant.FLIGHT))
      {
         //for flight
         chatInitMessage.setDetail(commonService.getFromAndToFlightGroupMessage(chatInitMessage));
      }
      else 
      {
         // for group
         chatInitMessage.setDetail(commonService.getFromAndToGroupChatMessages(chatInitMessage));
      }
      return chatInitMessage;
   }
   
   /**
    * update all unread message
    *  with from and to
    * @param tenant
    *           Tenant
    * @param from
    *           From User
    * @param message
    *           Message
    * @return
    * @throws CustomException
    */
   @MessageMapping(ConfigurationConstant.CHAT_UPDATE_UNREADMESSAGES_MESSAGE_MAPPING)
   public ChatInitMessage updateUnReadMessage(
         @DestinationVariable(ConfigurationConstant.TENANT_PATH_VARIABLE) String tenant,
         @DestinationVariable(ConfigurationConstant.FROM_PATH_VARIABLE) String from,
         @DestinationVariable(ConfigurationConstant.TO_PATH_VARIABLE) String to,
         @Payload ChatInitMessage chatInitMessage) throws CustomException {
         commonService.updateUnReadMessages(chatInitMessage);
      return chatInitMessage;
   }
   
   /**
    * Process Message to Group
    * 
    * @param tenant
    *           Tenant
    * @param from
    *           From User
    * @param to
    *           To User
    * @param message
    *           Message
    * @return
    * @throws CustomException
    */
   @MessageMapping(ConfigurationConstant.CHAT_FLIGHT_GROUP_MESSAGE_MAPPING)
   @SendTo(ConfigurationConstant.CHAT_SEND_TO_FLIGHT_GROUP)
   public ChatMessage processGroupMessagForFlight(
         @DestinationVariable(ConfigurationConstant.TENANT_PATH_VARIABLE) String tenant,
         @DestinationVariable(ConfigurationConstant.FROM_PATH_VARIABLE) String from,
         @DestinationVariable(ConfigurationConstant.TO_PATH_VARIABLE) String to, @Payload ChatMessage message)
         throws CustomException {
      commonService.saveGroupMessagesOfFlight(message);
      //message.setDetail(commonService.getFromAndToGroupChatMessages(message));
      return message;
   }
   
   

   /**
    * Send to User
    * 
    * @param tenant
    *           Tenant
    * @param user
    *           User
    * @param message
    *           Message
    */
   public void sendToUser(String tenant, String user, Message message) {
      simpMessagingTemplate.convertAndSend(
            String.format("%s/%s/%s", ConfigurationConstant.CHAT_SEND_TO_USER_URI, tenant, user), message);
   }

   /**
    * Send to User
    * 
    * @param tenant
    *           Tenant
    * @param group
    *           Group
    * @param message
    *           Message
    */
   public void sendToGroup(String tenant, String group, Message message) {
      simpMessagingTemplate.convertAndSend(
            String.format("%s/%s/%s", ConfigurationConstant.CHAT_SEND_TO_GROUP_URI, tenant, group), message);
   }
}