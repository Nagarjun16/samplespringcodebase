/**
 * {@link FFMParser}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.parser;

import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.SLASH;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.message.resend.common.MessageASCIIConstants;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLogDetail;
import com.ngen.cosys.message.resend.util.MessageTypeUtils;

/**
 * FFM Parser
 * 
 * @author Coforge PTE Ltd
 */
@Component
public class FFMParser implements MessageParser {

   private static final Logger LOGGER = LoggerFactory.getLogger(FFMParser.class);
   // Month 
   private static final String[] MONTH = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV",
         "DEC" };
   
   /**
    * @see com.ngen.cosys.message.resend.parser.MessageParser#parse(com.ngen.cosys.message.resend.model.IncomingESBMessageLog)
    */
   @Override
   public Object parse(IncomingESBMessageLog messageLog) {
      LOGGER.debug("FFM Parser - GET FFM payload from the Type-B message {}", messageLog.getMessage());
      // Get Message Lines data
      List<String> messageLines = MessageTypeUtils.getMessageLineData(messageLog.getMessage());
      messageLines = MessageTypeUtils.parseMessageEnvelope(messageLines, false,messageLog,false);
      //
      if (!CollectionUtils.isEmpty(messageLines)) {
         return getFlightDetail(messageLog, messageLines);
      }
      return null;
   }

   /**
    * Carrier, Flight Key and Date
    * 
    * @param messageLog
    * @param messageLines
    * @return
    */
   private IncomingFFMLog getFlightDetail(IncomingESBMessageLog messageLog, List<String> messageLines) {
      IncomingFFMLog incomingFFMLog = null;
      // Content Not Empty
      if (!CollectionUtils.isEmpty(messageLines)) {
         int j = 0; // Line Counter
         String messageType = null;
         String versionNo = null;
         String sequenceNo = null;
         String flightKey = null;
         String airportCode = null;
         String segment = null;
         LocalDateTime dateSTA = null;
         boolean nilCargo = false;
         boolean lastPartReceived = false;
         for (String lineData : messageLines) {
            if (!StringUtils.isEmpty(lineData)) {
               if (j == 0) { // Message Type and Version - FFM/8
                  messageType = MessageTypeUtils.getMessageType(lineData);
                  versionNo = MessageTypeUtils.getMessageVersion(lineData);
               } else if (j == 1) { // Flight Line - 1/SQXXXX/DD-MON/
                  String[] flightLine = lineData.split(SLASH);
                  sequenceNo = flightLine[0];
                  if(flightLine.length>1)
                  {
                  flightKey = flightLine[1];
                  }
                  if(flightLine.length>2)
                  {
                	  dateSTA = getFlightDateTime(flightLine[2]);
                  }
                  if(flightLine.length>3)
                  {
                  airportCode = flightLine[3];
                  }
               } else if (j == 2) { // Segment Detail - SIN OR SHJ/NIL
                  String[] segmentLine = lineData.split(SLASH);
                  segment = segmentLine[0].trim();
                  // NIL content
                  if (segmentLine.length > 1 && !StringUtils.isEmpty(segmentLine[1])
                        && Objects.equals(MessageASCIIConstants.NIL_CONTENT, segmentLine[1].trim())) {
                     nilCargo = true;
                  }
               } else {
                  if (Objects.equals(MessageASCIIConstants.LAST_PART, lineData.trim())) {
                     lastPartReceived = true;
                  }
               }
               j += 1;
            }
         }
         incomingFFMLog = copyData(messageLog, messageType, versionNo, sequenceNo, flightKey, airportCode, dateSTA,
               segment, nilCargo, lastPartReceived);
      }
      return incomingFFMLog;
   }
   
   /**
    * @param flightDate
    * @return
    */
   private LocalDateTime getFlightDateTime(String flightDate) {
      // Expected flight date format is NNMMM
      if (StringUtils.isEmpty(flightDate) || flightDate.length() < 5) {
         return null;
      }
      LocalDate today = LocalDate.now();
      int todaysYear = today.getYear();
      int todaysMonth = today.getMonthValue();
      String strMsgDate = flightDate.substring(0, 2);
      boolean msgDateFormat = MessageTypeUtils.isNumber(strMsgDate.toCharArray());
      LOGGER.debug("Message Date value - {}, and Formatter Verification : {}", strMsgDate, String.valueOf(msgDateFormat));
      if (!msgDateFormat)
         return null;
      // Message Date
      int msgDate = Integer.parseInt(strMsgDate);
      if (msgDate < 1 || msgDate > 31) {
         return null;
      }
      String strMsgMonth = flightDate.substring(2).length() > 3 ? flightDate.substring(2, 5) : flightDate.substring(2);
      boolean msgMonthFormat = Arrays.stream(MONTH).anyMatch(month -> month.compareTo(strMsgMonth) == 0);
      LOGGER.debug("Message Month value - {}, and Formatter Verification : {}", strMsgMonth,
            String.valueOf(msgMonthFormat));
      if (!msgMonthFormat)
         return null;
      // In DEC Month - JAN FFM Message processed then consider the Year+1
      if (todaysMonth == 12 && MONTH[0].compareTo(strMsgMonth) == 0) {
        todaysYear += 1; 
      }
      int msgMonth = indexOfStringInArray(MONTH, strMsgMonth);
      if (msgMonth == -1)
         return null;
      // 0 based Index - Message month
      msgMonth += 1;
      LOGGER.debug("Parsed Message DateTime - Year : {}, Month : {}, Day : {}", todaysYear, msgMonth, msgDate);
      // Build Message Date
      LocalDateTime msgDateTime = LocalDateTime.of(todaysYear, msgMonth, msgDate, 00, 00);
      //
      return msgDateTime;
   }
   
   /**
    * String matched index value or returns -1 if no match found
    * 
    * @param array
    * @param value
    * @return
    */
   private int indexOfStringInArray(String[] array, String value) {
      if (!ObjectUtils.isEmpty(array) && !StringUtils.isEmpty(value)) {
         for (int j = 0; j < array.length; j++)
            if (array[j].compareTo(value) == 0)
               return j;
      }
      return -1;
   }

   /**
    * Copy FFM Data
    * 
    * @param messageLog
    * @param messageType
    * @param versionNo
    * @param sequenceNo
    * @param flightKey
    * @param airportCode
    * @param dateSTA
    * @param segment
    * @param nilCargo
    * @param lastPartReceived
    * @return
    */
   private IncomingFFMLog copyData(IncomingESBMessageLog messageLog, String messageType, String versionNo,
         String sequenceNo, String flightKey, String airportCode, LocalDateTime dateSTA, String segment,
         boolean nilCargo, boolean lastPartReceived) {
      IncomingFFMLog incomingFFMLog = new IncomingFFMLog();
      //
      incomingFFMLog.setFlightKey(flightKey);
      incomingFFMLog.setDateSTA(dateSTA);
      incomingFFMLog.setAirportCode(airportCode);
      incomingFFMLog.setNilCargo(nilCargo);
      incomingFFMLog.setLastPartReceived(lastPartReceived);
      // Message Log Details
      IncomingFFMLogDetail incomingFFMLogDetail = new IncomingFFMLogDetail();
      incomingFFMLogDetail.setMessageESBSequenceNo(messageLog.getMessageESBSequenceNo());
      incomingFFMLogDetail.setChannel(messageLog.getInterfacingSystem());
      incomingFFMLogDetail.setFlightKey(flightKey);
      incomingFFMLogDetail.setDateSTA(dateSTA);
      incomingFFMLogDetail.setSegment(segment);
      incomingFFMLogDetail.setMessage(messageLog.getMessage());
      incomingFFMLogDetail.setVersionNo(Integer.valueOf(versionNo.trim()));
      incomingFFMLogDetail.setSequenceNo(Integer.valueOf(sequenceNo.trim()));
      incomingFFMLogDetail.setLastMessage(lastPartReceived);
      incomingFFMLogDetail.setCreatedDateTime(messageLog.getCreatedOn());
      incomingFFMLogDetail.setIncomingESBMessageLogId(messageLog.getIncomingESBMessageLogId());
      incomingFFMLogDetail.setSenderAddress(messageLog.getSenderAddress());
      incomingFFMLog.setIncomingFFMLogDetails(Arrays.asList(incomingFFMLogDetail));
      //
      return incomingFFMLog;
   }

	public IncomingFFMLogDetail parseForLastIndicator(IncomingFFMLogDetail incomingMessageDetail) {
		 List<String> messageLines = MessageTypeUtils.getMessageLineData(incomingMessageDetail.getMessage());
	     messageLines = MessageTypeUtils.parseMessageEnvelope(messageLines, false,null,false);
		if (!CollectionUtils.isEmpty(messageLines)) {       
	        boolean lastPartReceived = false;
	        for (String lineData : messageLines) {
	           if (!StringUtils.isEmpty(lineData)) {            
	                 if (Objects.equals(MessageASCIIConstants.LAST_PART, lineData.trim())) {
	                    lastPartReceived = true;
	                 }              
	           }
	        }
	        incomingMessageDetail.setLastMessage(lastPartReceived);
	     }
		return incomingMessageDetail;
	}
   
}
