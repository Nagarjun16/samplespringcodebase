/**
 * 
 * ChatAndNotificationQuery.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22th August, 2018 NIIT -
 */
package com.ngen.cosys.message.enums.query;
/**
 * 
 * Enums for Chat mapper file
 * 
 * @author NIIT Technologies Ltd
 */
public enum ChatAndNotificationQuery {
   
   SQL_GET_ALL_USERS("getAllUsers"),
   SQL_GET_ALL_ROLES("getAllGroups"),
   SQL_GET_USER_CHAT_SUMMARY("getUserChatSummary"),
   SQL_GET_CHAT_SUMMARY_BY_FROM_AND_TO("getChatSummaryByFromAndTo"),
   SQL_UPDATE_UNREAD_MESSAGES("updateUnReadMessages"),
   SQL_SAVE_CHAT_USER_MESSAGES("saveChatUserMessages"),
   SQL_SAVE_CHAT_GROUP_MESSAGES("saveGroupMessages"),
   SQL_GET_GROUP_CHAT_BY_FROM_AND_TO("getGroupChatByFromAndTo"),
   SQL_SAVE_CHAT_GROUP_FLIGHT_MESSAGES("saveGroupFlightMessages"),
   SQL_GET_FLIGHT_GROUP_CHAT_BY_FROM_AND_TO("getFlightGroupChatByFromAndTo");
   
   String queryId;
   
   ChatAndNotificationQuery(String queryId) {
      this.queryId = queryId;
   }

   public String getQueryId() {
      return queryId;
   }



}
