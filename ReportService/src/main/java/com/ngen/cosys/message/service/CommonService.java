/**
 * 
 * CommonService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22th August, 2018 NIIT -
 */
package com.ngen.cosys.message.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.model.ChatMessage;
import com.ngen.cosys.message.model.ChatSummary;
import com.ngen.cosys.message.model.Message;
import com.ngen.cosys.message.model.UserChatMessage;
import com.ngen.cosys.message.model.UserDetails;
import com.ngen.cosys.message.model.UserGroup;

/**
 * 
 * This CommonService takes care of all requests related to common controller
 * 
 * @author NIIT Technologies Ltd
 */
public interface CommonService {
   /**
    * This method will return all user details
    * 
    * @return
    * @throws CustomException
    */
   public List<UserDetails> getAllUsers(UserDetails userDetails) throws CustomException;

   /**
    * This method will return all groups related to user
    * 
    * @return
    * @throws CustomException
    */
   public List<UserGroup> getAllGroups() throws CustomException;

   /**
    * This method will return all groups related to user
    * @return
    * @throws CustomException
    */
   public List<UserChatMessage> getAllUserMessages(String userId) throws CustomException;

   /**
    * This method will return all groups related to user
    * @return
    * @throws CustomException
    */
   public List<UserChatMessage> getAllUserGroupMessages(String userId) throws CustomException;

   /**
    * This Method will return you chat summary of a user based on user id
    * @param message
    * @return
    * @throws CustomException
    */
   public List<ChatSummary> getUserChatSummary(Message message) throws CustomException;

   /**
    * This Method will return you chat summary based on and from and to value(USER CHAT)
    * @param message
    * @return
    * @throws CustomException
    */
   public List<ChatMessage> getFromAndToChatSummary(Message message) throws CustomException;

   /**
    * This method will update  all unread messages
    * @param message
    * @throws CustomException
    */
   public Integer updateUnReadMessages(Message message) throws CustomException;
   /**
    * This method will save all user messages
    * @param message
    * @return
    * @throws CustomException
    */
   public Integer saveUserMessages(Message message) throws CustomException;
   /**
    * This method will save all group messages
    * @param message
    * @return
    * @throws CustomException
    */
   public Integer saveGroupMessages(Message message) throws CustomException;
   
   /**
    * This method will return all from and to group messages
    * @param message
    * @return
    * @throws CustomException
    */
   public List<ChatMessage> getFromAndToGroupChatMessages(Message message) throws CustomException;
   
   /**
    * This method will save all group messages
    * @param message
    * @return
    * @throws CustomException
    */
   public Integer saveGroupMessagesOfFlight(Message message) throws CustomException;
   
   /**
    * This method will return all from and to flight messages
    * @param message
    * @return
    * @throws CustomException
    */
   public List<ChatMessage> getFromAndToFlightGroupMessage(Message message) throws CustomException;
   
}
