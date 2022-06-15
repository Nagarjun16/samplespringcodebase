/**
 * 
 * CommonDao.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 23th August, 2018 NIIT -
 */
package com.ngen.cosys.message.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.model.ChatMessage;
import com.ngen.cosys.message.model.ChatSummary;
import com.ngen.cosys.message.model.Message;
import com.ngen.cosys.message.model.UserDetails;
import com.ngen.cosys.message.model.UserGroup;

/**
 * 
 * This CommonDao takes care of all requests related to from common service
 * 
 * @author NIIT Technologies Ltd
 */
public interface CommonDao {
   /**
    * This method will return all user details
    * 
    * @return
    * @throws CustomException
    */
   public List<UserDetails> getAllUsers(UserDetails userDetails) throws CustomException;

   /**
    * This method will return all groups
    * 
    * @return
    * @throws CustomException
    */
   public List<UserGroup> getAllGroups() throws CustomException;

   /**
    * This method returns entire chat summary of a user
    * 
    * @param message
    * @return
    * @throws CustomException
    */
   public List<ChatSummary> getUserChatSummary(Message message) throws CustomException;

   /**
    * This method returns entire chat summary based on a user
    * 
    * @param message
    * @return
    * @throws CustomException
    */
   public List<ChatMessage> getFromAndToChatSummary(Message message) throws CustomException;

   /**
    * 
    * @param message
    * @throws CustomException
    */
   public Integer updateUnReadMessages(Message message) throws CustomException;
   /**
    * 
    * @param message
    * @return
    * @throws CustomException
    */
   public Integer saveUserMessages(Message message) throws CustomException;
   /**
    * 
    * @param message
    * @return
    * @throws CustomException
    */
   public Integer saveGroupMessages(Message message) throws CustomException;
   /**
    * This method returns entire chat summary of a group
    * 
    * @param message
    * @return
    * @throws CustomException
    */
   public List<ChatMessage> getFromAndToGroupChatMessages(Message message) throws CustomException;
   /**
    * This method returns entire chat summary of a  flight group
    * @param message
    * @return
    * @throws CustomException
    */
   public Integer saveGroupMessagesOfFlight(Message message) throws CustomException;
   /**
    * 
    * @param message
    * @return
    * @throws CustomException
    */
   public List<ChatMessage> getFromAndToFlightGroupMessage(Message message) throws CustomException;

}
