/**
 * 
 * CommonDaoImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22th August, 2018 NIIT -
 */
package com.ngen.cosys.message.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.enums.query.ChatAndNotificationQuery;
import com.ngen.cosys.message.model.ChatMessage;
import com.ngen.cosys.message.model.ChatSummary;
import com.ngen.cosys.message.model.Message;
import com.ngen.cosys.message.model.UserDetails;
import com.ngen.cosys.message.model.UserGroup;

/**
 * 
 * This CommonDaoImpl will implement commonDao interface
 * 
 * @author NIIT Technologies Ltd
 */
@Repository
public class CommonDaoImpl extends BaseDAO implements CommonDao {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   SqlSession sqlSession;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.message.dao.CommonDao#getAllUsers()
    */
   @Override
   public List<UserDetails> getAllUsers(UserDetails userDetails) throws CustomException {
      return super.fetchList(ChatAndNotificationQuery.SQL_GET_ALL_USERS.getQueryId(),userDetails, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.message.dao.CommonDao#getAllGroups()
    */
   @Override
   public List<UserGroup> getAllGroups() throws CustomException {
      return super.fetchList(ChatAndNotificationQuery.SQL_GET_ALL_ROLES.getQueryId(), "1", sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.message.dao.CommonDao#getUserChatSummary(com.ngen.cosys.
    * message.model.Message)
    */
   @Override
   public List<ChatSummary> getUserChatSummary(Message message) throws CustomException {
      return super.fetchList(ChatAndNotificationQuery.SQL_GET_USER_CHAT_SUMMARY.getQueryId(), message, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.message.dao.CommonDao#getFromAndToChatSummary(com.ngen.cosys.
    * message.model.Message)
    */
   @Override
   public List<ChatMessage> getFromAndToChatSummary(Message message) throws CustomException {
      return super.fetchList(ChatAndNotificationQuery.SQL_GET_CHAT_SUMMARY_BY_FROM_AND_TO.getQueryId(), message,
            sqlSession);
   }

   /* (non-Javadoc)
    * @see com.ngen.cosys.message.dao.CommonDao#updateUnReadMessages(com.ngen.cosys.message.model.Message)
    */
   @Override
   public Integer updateUnReadMessages(Message message) throws CustomException {
      return super.updateData(ChatAndNotificationQuery.SQL_UPDATE_UNREAD_MESSAGES.getQueryId(), message, sqlSession);
   }

   /* (non-Javadoc)
    * @see com.ngen.cosys.message.dao.CommonDao#saveChatUserMessage(com.ngen.cosys.message.model.Message)
    */
   @Override
   public Integer saveUserMessages(Message message) throws CustomException {
      
      return super.insertData(ChatAndNotificationQuery.SQL_SAVE_CHAT_USER_MESSAGES.getQueryId(), message, sqlSession);
   }
   
   @Override
   public Integer saveGroupMessages(Message message) throws CustomException {
      
      return super.insertData(ChatAndNotificationQuery.SQL_SAVE_CHAT_GROUP_MESSAGES.getQueryId(), message, sqlSession);
   }

   @Override
   public List<ChatMessage> getFromAndToGroupChatMessages(Message message) throws CustomException {
      return super.fetchList(ChatAndNotificationQuery.SQL_GET_GROUP_CHAT_BY_FROM_AND_TO.getQueryId(), message,sqlSession);
   }

   @Override
   public Integer saveGroupMessagesOfFlight(Message message) throws CustomException {
      return super.insertData(ChatAndNotificationQuery.SQL_SAVE_CHAT_GROUP_FLIGHT_MESSAGES.getQueryId(), message, sqlSession);
   }

   @Override
   public List<ChatMessage> getFromAndToFlightGroupMessage(Message message) throws CustomException {
      return super.fetchList(ChatAndNotificationQuery.SQL_GET_FLIGHT_GROUP_CHAT_BY_FROM_AND_TO.getQueryId(), message,sqlSession);
   }

}
