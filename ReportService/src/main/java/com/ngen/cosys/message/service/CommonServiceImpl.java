/**
 * 
 * CommonServiceImpl.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.dao.CommonDao;
import com.ngen.cosys.message.model.ChatMessage;
import com.ngen.cosys.message.model.ChatSummary;
import com.ngen.cosys.message.model.Message;
import com.ngen.cosys.message.model.UserChatMessage;
import com.ngen.cosys.message.model.UserDetails;
import com.ngen.cosys.message.model.UserGroup;

/**
 * 
 * This service will implement common service like fetching user details and
 * groups
 * 
 * @author NIIT Technologies Ltd
 */
@Service
public class CommonServiceImpl implements CommonService {

   @Autowired
   CommonDao commonDao;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.message.service.CommonService#getAllUsers()
    */
   @Override
   public List<UserDetails> getAllUsers(UserDetails userDetails) throws CustomException {
      return commonDao.getAllUsers(userDetails);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.message.service.CommonService#getAllGroups()
    */
   @Override
   public List<UserGroup> getAllGroups() throws CustomException {
      return commonDao.getAllGroups();
   }

   @Override
   public List<UserChatMessage> getAllUserMessages(String userId) throws CustomException {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public List<UserChatMessage> getAllUserGroupMessages(String userId) throws CustomException {
      // TODO Auto-generated method stub
      return null;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.message.service.CommonService#getUserChatSummary(com.ngen.
    * cosys.message.model.Message)
    */
   @Override
   public List<ChatSummary> getUserChatSummary(Message message) throws CustomException {
      return commonDao.getUserChatSummary(message);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.message.service.CommonService#getFromAndToChatSummary(com.ngen
    * .cosys.message.model.Message)
    */
   @Override
   public List<ChatMessage> getFromAndToChatSummary(Message message) throws CustomException {
      updateUnReadMessages(message);
      return commonDao.getFromAndToChatSummary(message);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.message.service.CommonService#updateUnReadMessages(com.ngen.
    * cosys.message.model.Message)
    */
   @Override
   public Integer updateUnReadMessages(Message message) throws CustomException {
      return commonDao.updateUnReadMessages(message);
   }

   /* (non-Javadoc)
    * @see com.ngen.cosys.message.service.CommonService#saveChatUserMessages(com.ngen.cosys.message.model.Message)
    */
   @Override
   public Integer saveUserMessages(Message message) throws CustomException {
     return commonDao.saveUserMessages(message);
   }

   /* (non-Javadoc)
    * @see com.ngen.cosys.message.service.CommonService#saveGroupMessages(com.ngen.cosys.message.model.Message)
    */
   @Override
   public Integer saveGroupMessages(Message message) throws CustomException {
     return commonDao.saveGroupMessages(message);
   }

   /* (non-Javadoc)
    * @see com.ngen.cosys.message.service.CommonService#getFromAndToGroupChatMessages(com.ngen.cosys.message.model.Message)
    */
   @Override
   public List<ChatMessage> getFromAndToGroupChatMessages(Message message) throws CustomException {
       return commonDao.getFromAndToGroupChatMessages(message);
   }

   @Override
   public Integer saveGroupMessagesOfFlight(Message message) throws CustomException {
       return commonDao.saveGroupMessagesOfFlight(message);
   }

   @Override
   public List<ChatMessage> getFromAndToFlightGroupMessage(Message message) throws CustomException {
      return commonDao.getFromAndToFlightGroupMessage(message);
   }
}
