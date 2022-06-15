/**
 * {@link OutgoingMessageBuilder}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.builder;

import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.AED1;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.AED4;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.AED6;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.SYSTEM_AED;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.SYSTEM_AED1;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.SYSTEM_AED4;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.SYSTEM_AED6;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.SYSTEM_CCN;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.SYSTEM_ARINC;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.SYSTEM_EDIFLY;
import static com.ngen.cosys.message.resend.common.InterfaceSystemConstants.SYSTEM_RX;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.END_OF_TEXT;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.LINEFEED_CRLF;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.NEW_LINE;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.SENDER_ADDRESS_PREFIX;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.START_OF_HEADER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.START_OF_TEXT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.core.MessageDetail;
import com.ngen.cosys.message.resend.core.MessagePayload;
import com.ngen.cosys.message.resend.model.OutgoingErrorMessageLog;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;

/**
 * Outgoing Message Builder
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class OutgoingMessageBuilder {

   private static final Logger LOGGER = LoggerFactory.getLogger(OutgoingMessageBuilder.class);

   
   /**
    * Outgoing Message Builder
    * 
    * @param errorMessageLogs
    * @return
    * @throws CustomException
    */
   public Collection<?> build(Collection<?> errorMessageLogs) throws CustomException {
      LOGGER.info("Outgoing Message Builder - Resend Job - {}");
      List<MessageDetail> messageDetails = new ArrayList<>();
      MessageDetail messageDetail = null;
      OutgoingErrorMessageLog outgoingErrorMessageLog = null;
      //
      for (Object errorMessageLog : errorMessageLogs) {
         if (errorMessageLog instanceof OutgoingErrorMessageLog) {
            outgoingErrorMessageLog = (OutgoingErrorMessageLog) errorMessageLog;
            messageDetail = new MessageDetail();
            messageDetail.setErrorMessageLog(outgoingErrorMessageLog);
         }
         if (Objects.nonNull(outgoingErrorMessageLog)) {
            messageDetail.setPayload(copyPayload(outgoingErrorMessageLog));
            messageDetails.add(messageDetail);
         }
      }
      return messageDetails;
   }
   
   /**
    * @param errorMessageLog
    * @return
    */
   private MessagePayload copyPayload(OutgoingErrorMessageLog errorMessageLog) {
      MessagePayload payload = new MessagePayload();
      String queueName = null;
      String messageData = null;
      if (errorMessageLog.getInterfacingSystem().startsWith(SYSTEM_AED)) {
         if (!StringUtils.isEmpty(errorMessageLog.getSubMessageType())) {
            queueName = identifyAEDQueueName(errorMessageLog.getSubMessageType());
         }
         messageData = errorMessageLog.getMessage();
      } else {
         queueName = errorMessageLog.getInterfacingSystem();
         messageData = buildMessage(errorMessageLog);
      }
      payload.setQname(queueName);
      payload.setPayload(messageData);
      payload.setTenantID(TenantZone.SIN.getAirportCode());
      return payload;
   }
   
   /**
    * @param errorMessageLog
    * @return
    */
   private String buildMessage(OutgoingErrorMessageLog errorMessageLog) {
      // Config
      String systemName = errorMessageLog.getInterfacingSystem();
      boolean systemCCN = Objects.equals(SYSTEM_CCN, systemName);
      boolean systemARINC=Objects.equals(SYSTEM_ARINC, systemName);
      boolean systemEDIFLY=Objects.equals(SYSTEM_EDIFLY, systemName);
      boolean systemRX=Objects.equals(SYSTEM_RX, systemName);
      boolean systemAED = systemName.contains(SYSTEM_AED);
      boolean specialCharacterToBeAdded = false;
      if(systemCCN||systemARINC||systemEDIFLY||systemRX)
      {
    	  specialCharacterToBeAdded = true;
      }
      if(!specialCharacterToBeAdded)
      {
    	  return errorMessageLog.getMessage();
      }
      boolean feedStartOfText = !systemAED;
      boolean feedASCIIChars = !systemAED;
      boolean foundSenderAddress = false;
      boolean headersAdded = false;
      boolean headersConfigAdded = false;
      boolean startOfTextAdded = false;
      boolean typeB = false;      
 	 if(systemARINC ||systemEDIFLY)
 	 {
 		 typeB = true;
 	 }
      String endOfMessage = String.valueOf(charSequence(LINEFEED_CRLF)) + String.valueOf(charSequence(END_OF_TEXT));
      String message = errorMessageLog.getMessage();
      String[] messageLines = systemAED ? new String[] { message } : message.split("\\r?\\n");
      String messageData = null;
      for (String lineData : messageLines) {
    	  headersConfigAdded = headersAdded;
         if (!systemAED && !headersAdded) {        	 
            messageData = buildMessage(messageData, lineData, feedStartOfText,typeB,foundSenderAddress);
            headersAdded = true;
         }
         if (!systemAED && !StringUtils.isEmpty(messageData) && !StringUtils.isEmpty(lineData)) {
            String addressPrefix = lineData.length() > SENDER_ADDRESS_PREFIX.length()
                  ? addressPrefix = lineData.substring(0, 4)
                  : null;
            if (!StringUtils.isEmpty(addressPrefix)
                  && Objects.equals(addressPrefix.toUpperCase(), SENDER_ADDRESS_PREFIX)) {            	
               foundSenderAddress = true;
            } else {
               // Use only once
               if (!systemRX &&!systemCCN && !startOfTextAdded && (foundSenderAddress)) {
                  lineData = String.valueOf(charSequence(START_OF_TEXT)).concat(lineData);                 
                  startOfTextAdded = true;
               }
            }
            if (headersConfigAdded) {
               messageData = buildMessage(messageData, lineData, feedStartOfText,typeB,foundSenderAddress);
            }
         }
      }
      // ETX
      if (feedASCIIChars) {
         messageData += endOfMessage;
      }
      return messageData;
   }
   
   /**
    * @param messageData
    * @param lineData
    * @param feedStartOfText
 * @param typeB 
 * @param foundSenderAddress 
    * @return
    */
   public static String buildMessage(String messageData, String lineData, boolean feedStartOfText, boolean typeB, boolean foundSenderAddress) {
      if (feedStartOfText) {
    	 
    		if(foundSenderAddress)
    		{
    			return messageData + NEW_LINE + lineData  ;
    		}
    		else if(typeB)
    		{
    			return StringUtils.isEmpty(messageData) ? NEW_LINE +String.valueOf(charSequence(START_OF_HEADER)).concat(lineData) // SOH
      	               : messageData + lineData ;
    		}
    		else
    		{
    			return StringUtils.isEmpty(messageData) ? String.valueOf(charSequence(START_OF_HEADER)).concat(lineData) // SOH
     	               : messageData + lineData ;
    		}    		  
    	  
         
      } else {
         return StringUtils.isEmpty(messageData) ? lineData : messageData + NEW_LINE + lineData;
      }
   }
   
   /**
    * Character Feed
    * 
    * @param feed
    * @return
    */
   private static CharSequence charSequence(byte[] feed) {
      return new String(feed);
   }
   
   /**
    * @param subMessageType
    * @return
    */
   private String identifyAEDQueueName(String subMessageType) {
      String queueName = null;
      switch (subMessageType) {
      case AED1:
         queueName = SYSTEM_AED1;
         break;
      case AED4:
         queueName = SYSTEM_AED4;
         break;
      case AED6:
         queueName = SYSTEM_AED6;
         break;
      default:
         break;
      }
      return queueName;
   }
   
}
