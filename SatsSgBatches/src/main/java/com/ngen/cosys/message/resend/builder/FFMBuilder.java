/**
 * {@link FFMBuilder}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.builder;

import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.CONT_PART;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.DOT_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.START_OF_MESSAGE_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageResendConstants.INITIATED;



import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.message.resend.common.MessageASCIIConstants;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.model.IncomingFFMLog;
import com.ngen.cosys.message.resend.model.IncomingFFMLogDetail;
import com.ngen.cosys.message.resend.util.MessageTypeUtils;

/**
 * FFM Message Builder to prepare the final message
 *  - Combine all the parts, process one single message
 *  - More than 1 part will not have subsequence header in the CONT part
 * 
 * @author Coforge PTE Ltd
 */
@Component
public class FFMBuilder {

   private static final Logger LOGGER = LoggerFactory.getLogger(FFMBuilder.class);
   
   public static final String NEW_LINE = System.getProperty("line.separator");
   
   /**
    * Build message
    * 
    * @param incomingFFMLogs
    * @throws Exception
    */
   public void build(List<IncomingFFMLog> incomingFFMLogs) throws Exception {
      LOGGER.info("FFM Message Builder - Final Message preparation to process {}");
      for (IncomingFFMLog incomingFFMLog : incomingFFMLogs) {        
            // Combine all parts into one message
            combineAllPartsToOneMessage(incomingFFMLog);
         
      }
   }
   
   /**
    * Combine All Parts to One Message
    * 
    * @param incomingFFMLog
    * @return
    */
   private void combineAllPartsToOneMessage(IncomingFFMLog incomingFFMLog) {
      
      int totalPartGroup = incomingFFMLog.getTotalPartGroup();
      String message = null;
      String formattedMessage = "";
      // Initialization of parts message
      incomingFFMLog.setPartGroupMessage(new HashMap<>());
      for (int partGroup = 1; partGroup <= totalPartGroup; partGroup++) {
    	  final int partGrp = partGroup;
    	  List<IncomingFFMLogDetail> partGroupList = incomingFFMLog.getIncomingFFMLogDetails().stream().filter(incomingFFMDetails->incomingFFMDetails.getPartGroup().equals(partGrp)).collect(Collectors.toList());
    	  List<Integer> sequenceNos = partGroupList.stream().map(incomingFFMLogDetail->incomingFFMLogDetail.getSequenceNo()).sorted().collect(Collectors.toList());
    	  String messageStatus = partGroupList.stream().map(incomingFFMLogDetail->incomingFFMLogDetail.getMessageStatus()).findFirst().get();
    	 int i =0;
         for (Integer sequence : sequenceNos) {
            message = partGroupList.stream().filter(incomingFFMDetails->incomingFFMDetails.getSequenceNo().equals(sequence)).findFirst().get().getMessage();
            if(messageStatus.equalsIgnoreCase(INITIATED))
	      	  {
            	
        		if (i == 0) {
                    // If first sequence then no need to remove the envelop & CONT
                    // sequence != totalSequence # Let Header remains and remove CONT identifier
                    formattedMessage = StringUtils.isEmpty(formattedMessage) //
                          ? formatMessage(message, false, true) //
                          : formattedMessage + NEW_LINE + formatMessage(message, false, true);
                 } else {
                    // sequence == totalSequence - CONT Identifier won't be there
                    formattedMessage = StringUtils.isEmpty(formattedMessage) //
                          ? formatMessage(message, true, true) //
                          : formattedMessage + NEW_LINE + formatMessage(message, true, true);
                 }           	
	      		  
	      	  }
            else
            {
            	 // If first sequence then no need to remove the envelop & CONT
                // sequence != totalSequence # Let Header remains and remove CONT identifier
                formattedMessage = message;
	        }
            i++;           
         }
         incomingFFMLog.setMessageStatus(messageStatus);
         incomingFFMLog.getPartGroupMessage().put(partGroup, formattedMessage);
         // Set Single message if total parts is same
         if (partGroup == totalPartGroup) {
            incomingFFMLog.setMessage(formattedMessage);
         }
         // Reset Message & Formatted message to null
         message = null;
         formattedMessage = "";
      }
   }
   
   /**
    * GET Sequence Message
    * 
    * @param incomingFFMLogDetails
    * @param sequence
    * @param partGroup
    * @return
    */
   private String getSequenceMessage(List<IncomingFFMLogDetail> incomingFFMLogDetails, int sequence, int partGroup) {
      String sequenceMessage = null;
      for (IncomingFFMLogDetail incomingFFMLogDetail : incomingFFMLogDetails) {
         if (incomingFFMLogDetail.getSequenceNo() == sequence //
               && incomingFFMLogDetail.getPartGroup() == partGroup //
               && Objects.equals(incomingFFMLogDetail.getMessageStatus(), MessageResendConstants.INITIATED)) {
            sequenceMessage = incomingFFMLogDetail.getMessage();
            break;
         }
      }
      return sequenceMessage;
   }
   
   /**
    * Message Formatter
    *  - Remove message envelope of queue address & unique identifiers
    *  - Remove CONT identifiers
    * 
    * @param message
    * @param ignoreEnvelope
    * @param ignoreCONTIdentifier
    * @return
    */
   private String formatMessage(String message, boolean ignoreEnvelope, boolean ignoreCONTIdentifier) {
      List<String> messageLines = MessageTypeUtils.getMessageLineData(message);
      String messageData = "";
      boolean readCONTLine = false;
      int j = 0;
      boolean ignoreBaseData = ignoreEnvelope; // MessageType/Version CRLF Sequence/Flight/Date/RegCode CRLF SIN/Date
      for (String lineData : messageLines) {
         if (!ignoreEnvelope) {        	 
            if (ignoreCONTIdentifier) {
               if (Objects.equals(lineData, CONT_PART)) {
                  readCONTLine = true;
               } else {
                  if (ignoreBaseData && j == 3) {
                     ignoreBaseData = false;
                  }
                  if (!ignoreBaseData //
                        && (!readCONTLine //
                              || !Objects.equals(new String(MessageASCIIConstants.END_OF_TEXT), lineData))) {
                     messageData = StringUtils.isEmpty(messageData) ? lineData : messageData + NEW_LINE + lineData;
                  }
               }
            }
            j += 1;
         } else {
            if (lineData.startsWith(DOT_IDENTIFIER)||START_OF_MESSAGE_IDENTIFIER.equalsIgnoreCase(lineData)) {
               ignoreEnvelope = false;
            }
         }
        
      }
      return messageData;
   }
   
}
