/**
 * Message Type Identifier
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.satssg.interfaces.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.message.enums.TextMessageConstants;

/**
 * To identify the Message payload type to process the incoming message
 *
 */
@Component("messageTypeIdentifier")
public class MessageTypeIdentifier {

   private static final Logger LOGGER = LoggerFactory.getLogger(MessageTypeIdentifier.class);

   private static final String COSYS_TELEX_ADDRESS = "COSYS_TELEX_ADDRESS";
   private static final String CMA_RECIPIENT_ADDRESS = "CMA_RECIPIENT_ADDRESS";
   private static final String FNA_RECIPIENT_ADDRESS = "FNA_RECIPIENT_ADDRESS";

   private static final String SQL_SELECT_APP_SYSTEM_PARAMETER = "sqlSelectAppSystemParameter";

   @Qualifier("sqlSessionTemplate")
   @Autowired
   private SqlSession sqlSession;

   /**
    * @param payload
    * @return
    */
   public static String getMessageType(String payload) {
      if (StringUtils.isEmpty(payload)) {
         return null;
      }
      String regex = "(?m)^[ \t]*\r?\n";
      String parsedMessage = payload.replaceAll(regex, "");
      List<String> payloadLineData = new ArrayList<>();
      StringTokenizer stringTokenizer = new StringTokenizer(parsedMessage, TextMessageConstants.CRLF.getValue());
      while (stringTokenizer.hasMoreTokens()) {
         String lineItem = stringTokenizer.nextToken();
         if (!StringUtils.isEmpty(lineItem)) {
            payloadLineData.add(lineItem);
         }
      }
      int messageSize = payloadLineData.size();
      String messageType = messageSize > 2 ? payloadLineData.get(2) : null;
      if (!StringUtils.isEmpty(messageType) && messageType.length() > 3) {
         messageType = messageType.substring(0, 3);
      }
      LOGGER.warn("CCN Payload Message Type :: {}", messageType);
      //
      return messageType;
   }

   /**
    * Get Cosys System Telex configured from App System Parameter
    * 
    * @return
    */
   public String getCosysTelexAddress() {
      String telexAddress = sqlSession.selectOne(SQL_SELECT_APP_SYSTEM_PARAMETER, COSYS_TELEX_ADDRESS);
      LOGGER.warn("Message Telex Address :: {}", telexAddress);
      return telexAddress;
   }

   /**
    * Get CMA Recipient Address
    * 
    * @return
    */
   public String getCMARecipientAddress() {
      String cmaAddress = sqlSession.selectOne(SQL_SELECT_APP_SYSTEM_PARAMETER, CMA_RECIPIENT_ADDRESS);
      LOGGER.warn("Message Type CMA Address :: {}", cmaAddress);
      return cmaAddress;
   }

   /**
    * Get FNA Recipient Address
    * 
    * @return
    */
   public String getFNARecipientAddress() {
      String fnaAddress = sqlSession.selectOne(SQL_SELECT_APP_SYSTEM_PARAMETER, FNA_RECIPIENT_ADDRESS);
      LOGGER.warn("Message Type CNA Address :: {}", fnaAddress);
      return fnaAddress;
   }

}
