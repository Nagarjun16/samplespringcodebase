/**
 * {@link MessageTypeUtils}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.message.resend.util;

import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.CRLF;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.DOT_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.DUO_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.IATA_RESEND_CODE;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.SLASH;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.START_OF_MESSAGE_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.UFL_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.UIM_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.SPACEIDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.PRIORITY_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.DESTINATION_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.ORIGIN_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.HEADER_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.LINEFEED_CRLF;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.START_OF_HEADER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.START_OF_TEXT;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.END_OF_TEXT;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.SPACE;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.MESSAGE_ID;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.COMMA_IDENTIFIER;
import static com.ngen.cosys.message.resend.common.MessageASCIIConstants.priorities;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.message.resend.common.MessageASCIIConstants;
import com.ngen.cosys.message.resend.model.IncomingESBMessageLog;

/**
 * This class used for Identity Message Type and utility to parse the message
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class MessageTypeUtils {
	

   /**
    * Get Message Lines data
    * 
    * @param payload
    * @return
    */
   public static List<String> getMessageLineData(String payload) {
      // Object to store Message Payload Line Items
      List<String> messageLines = new LinkedList<>();
      // Tokenizer for splitting message by CRLF character
      try {
         StringTokenizer tokenizer = new StringTokenizer(payload, CRLF);
         while (tokenizer.hasMoreTokens()) {
            String lineItem = tokenizer.nextToken();
            // Trim the text
            if (!StringUtils.isEmpty(lineItem) && !lineItem.trim().startsWith(IATA_RESEND_CODE)) {
               messageLines.add(lineItem);
            }
         }
      } catch (Exception ex) {
         // Do not parse if message is empty
      }
      return messageLines;
   }

   /**
    * Parse Message Envelope data Extract Message Header information to identify
    * the message type
    * 
    * @param messageLines
    * @param ignoreTextIdentifier
 * @param messageLog 
    * @return
    */
   public static List<String> parseMessageEnvelope(List<String> messageLines, boolean ignoreTextIdentifier, IncomingESBMessageLog messageLog,boolean stxetx) {
      // Message TYPE-A TYPE-B Parser
      List<String> messageEnvelopes = messageTYPEParser(messageLines, ignoreTextIdentifier,messageLog,stxetx);
      messageEnvelopes = messageEnvelopeFusion(messageLines, messageEnvelopes);
      //
      return messageEnvelopes;
   }
   
   /**
    * TYPE-A & TYPE-B Message Parser
    * 
    * @param messageLines
    * @param ignoreTextIdentifier
 * @param messageLog 
    * @return
    */
   public static List<String> messageTYPEParser(List<String> messageLines, boolean ignoreTextIdentifier, IncomingESBMessageLog messageLog,boolean stxetx) {
      // Message Envelopes
      List<String> messageEnvelopes = new LinkedList<>();
      // Identifier for ignoring lines until it reaches =TEXT
      boolean ignoreMessageLine = true;
      // TYPE A message format
      for (String lineData : messageLines) {    	  
    	  
         // Ignore message line check
         if (ignoreTextIdentifier) {
            ignoreMessageLine = false;
         }
         
       
         if (!ignoreMessageLine) {
            messageEnvelopes.add(lineData.trim());
         }
         if (!ignoreTextIdentifier && START_OF_MESSAGE_IDENTIFIER.equalsIgnoreCase(lineData))  {
            // Turn-Off Line Identifier
            ignoreMessageLine = false;
         }
        
        
      }
      // TYPE B message format
      if (CollectionUtils.isEmpty(messageEnvelopes)) {
    	  boolean dotidentifier= false;
         for (String lineData : messageLines) {
        	 if(dotidentifier)
        	 {
        		 ignoreMessageLine = false;
        	 }
        	 if(stxetx)
        	 {
        		 
	        	 if(lineData.length()>1 && Arrays.equals(START_OF_TEXT, lineData.substring(0,1).getBytes()))
	        	 {
	        		 ignoreMessageLine = true;
	        		 messageEnvelopes.add(lineData.substring(1, lineData.length()));
	        	 }
	        	else if(Arrays.equals(END_OF_TEXT, lineData.getBytes()) )
	             {
	          	   ignoreMessageLine = true;
	             }
        	 }
        	 
            if (!ignoreMessageLine) {
               messageEnvelopes.add(lineData);
            } else {
               if (lineData.startsWith(DOT_IDENTIFIER)) {
                  // Turn-Off Line Identifier
            	   dotidentifier= true;
                  ignoreMessageLine = false;
                  if(lineData.length()>1 && Objects.nonNull(messageLog))
                  {
                	  String address = lineData.substring(1, lineData.length()); 
                	  // Split the line
                      if (!StringUtils.isEmpty(address)) {
                         String[] add = address.split(SPACEIDENTIFIER);
                         messageLog.setSenderAddress(add[0]);
                         
                      }
                  }   
               }                
               else if (lineData.startsWith(UFL_IDENTIFIER) || lineData.startsWith(UIM_IDENTIFIER)) {
                  ignoreMessageLine = false;
                  messageEnvelopes.add(lineData);
               }
              
            }
         }
      }
      //
      return messageEnvelopes;
   }

   /**
    * Message Fusion Envelope
    * 
    * @param messageLines
    * @param messageEnvelopes
 * @return
    */
   public static List<String> messageEnvelopeFusion(List<String> messageLines, List<String> messageEnvelopes) {
      if (CollectionUtils.isEmpty(messageEnvelopes)) {
         boolean fusionMessage = false;
         for (String lineData : messageLines) {
            if (DUO_IDENTIFIER.equalsIgnoreCase(lineData)) {
               fusionMessage = true;
               break;
            }
         }
         // Add the Entire Message List
         if (fusionMessage) {
            messageEnvelopes.addAll(messageLines);
         }
      }
      return messageEnvelopes;
   }

   /**
    * @param ch
    * @return
    */
   public static boolean isAlphaNumeric(char ch) {
      return Character.isAlphabetic(ch) || Character.isDigit(ch);
   }

   /**
    * @param array
    * @return
    */
   public static boolean isAlaphabet(char[] array) {
      if (ObjectUtils.isEmpty(array)) {
         return false;
      }
      for (char ch : array)
         if (!Character.isAlphabetic(ch))
            return false;
      //
      return true;
   }

   /**
    * @param array
    * @return
    */
   public static boolean isNumber(char[] array) {
      if (ObjectUtils.isEmpty(array)) {
         return false;
      }
      for (char ch : array)
         if (!Character.isDigit(ch))
            return false;
      //
      return true;
   }

   /**
    * GET Message Type in the line item
    * 
    * @param message
    * @return
    */
   public static String getMessageType(String message) {
      String[] lineData = message.split(SLASH);
      return removeMessageCodes(lineData[0]);
   }

   /**
    * GET Message Version
    * 
    * @param message
    * @return
    */
   public static String getMessageVersion(String message) {
      String[] lineData = message.split(SLASH);
      String versionNo = null;
      if (lineData.length > 1)
         versionNo = lineData[1];
      return versionNo;
   }

   /**
    * Remove Message Codes of SOH-STX-ETX
    * 
    * @param messageType
    * @return
    */
   private static String removeMessageCodes(String messageType) {
      if (!StringUtils.isEmpty(messageType)) {
         messageType = messageType.replaceAll(new String(MessageASCIIConstants.START_OF_HEADER), "");
         messageType = messageType.replaceAll(new String(MessageASCIIConstants.START_OF_TEXT), "");
         messageType = messageType.replaceAll(new String(MessageASCIIConstants.END_OF_TEXT), "");
         return messageType;
      }
      return null;
   }

   /**
    * Message format is valid/not In the case of Version/Sequence No comes with
    * String format
    * 
    * @param values
    * @return
    */
   public static boolean isMessageFormatOnlyNumber(String format) {
      for (char ch : format.toCharArray()) {
         if (!Character.isDigit(ch)) {
            return false;
         }
      }
      return true;
   }   
   
   
  
   
// Header Format 2 will be true if target system wants format to be in
   // PRIORITY DESTINATION . ORIGIN CURRENTDT
   public static byte[] generateHeaderFormat2(String textMessage, boolean controlCharReq,List<String> messageAsList)
         throws IOException {   	

	      // Priority
	      String priority = getPriority(messageAsList);     

	      // Origin
	      List<String> originAddress = getOriginAddress(messageAsList);
	      

	      // Destination
	      List<String> destinationAddress = getDestinationAddress(messageAsList);
	     

	      // pimaaddress
	      List<String> pimaAddress = getPimaAddress(messageAsList);
	      
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      DataOutputStream dout = new DataOutputStream(bout);
      dout.write(LINEFEED_CRLF);
    
      if (controlCharReq)
         // Header Line
         dout.write(START_OF_HEADER);

      // ADDRESS LINE...
      // Priority code
      if (StringUtils.isEmpty(priority)) {
         // if no priority code then set default as QK
         dout.writeBytes("QK");
      } else {
         dout.writeBytes(priority);
      }
      // SPACE
      dout.writeByte(0x20);

      // DESTINATION code
      int i = 1;
      int noOf7AddLines = 0;
      int index = 0;
      for (String destination : destinationAddress) {
         if (noOf7AddLines > 0 && i == 1) {
            dout.write(LINEFEED_CRLF);
         }
         dout.writeBytes(destination);     

         // If the destination address reaches 7 recipients then start from second line
         if (i == 7) {
            i = 0;
            noOf7AddLines++;
         }
         else
         {
        	 // Check if are there any more destination address if yes then append SPACE
             // character at end
        	 try {
                 if (!ObjectUtils.isEmpty(destinationAddress.get(index + 1))) {
                    dout.write(SPACE);
                 }
              } catch (Exception ex) {
                 // Do nothing
              }
         }
         i++;
         index++;
      }

      // Origin line...
      dout.write(LINEFEED_CRLF);
      dout.writeBytes(DOT_IDENTIFIER);
      // SENDER SIGNATURE...
      for (String origin : originAddress) {
         dout.writeBytes(origin);
      }
      // SPACE
      dout.writeByte(0x20);
     

      // current dt
      dout.writeBytes(getStringFromDate(LocalDateTime.now(), "ddHHmm"));

     
      // pima address line
      if (pimaAddress != null) {
         // SPACE
         dout.writeByte(0x20);

         for (String pimaAddress1 : pimaAddress) {
            dout.writeBytes(pimaAddress1);
            dout.writeByte(0x20);
         }

      }
      dout.write(LINEFEED_CRLF);

      if (controlCharReq)
         // MSG BODY Text area
         dout.write(START_OF_TEXT);

      CharSequence crlfCharSequence = new String(new char[] { 0x0d, 0x0a });
      CharSequence lfCharSequence = new String(new char[] { 0x0a });
      CharSequence crCharSequence = new String(new char[] { 0x0d });

      String messageBody;
      List<String> messageAsList1 = getMessageLineData(textMessage);

      for (String t : messageAsList1) {
         if ("true".equalsIgnoreCase(t) || "false".equalsIgnoreCase(t)) {
            // Do nothing..this is a first element in EDI message which is
            // used to decide whether message could be stored in db or not.
            // Hence ignore
         } else {
            messageBody = t;
            // Add \r\n character if not found
            if (!StringUtils.isEmpty(t) && (t.lastIndexOf(0x0a) == -1) && (t.lastIndexOf(0x0d) == -1)) {
               messageBody = messageBody + crlfCharSequence;
            } else {
               // Add \r character if not found
               if (!StringUtils.isEmpty(t) && (t.lastIndexOf(0x0d) == -1) && (t.lastIndexOf(0x0a) > -1)) {
                  messageBody = messageBody.replace(lfCharSequence, crlfCharSequence);
               }
               // Add \n character if not found
               if (!StringUtils.isEmpty(t) && (t.lastIndexOf(0x0a) == -1) && (t.lastIndexOf(0x0d) > -1)) {
                  messageBody = messageBody.replace(crCharSequence, crlfCharSequence);
               }
            }
            dout.writeBytes(messageBody);
         }
      }
      if (controlCharReq)
         // Terminator msg
         dout.write(END_OF_TEXT);
      return bout.toByteArray();
   }
   
   
   /**
    * Method to extract origin address after =ORIGIN from message
    * 
    * @param messageAsList
    * @return List<String> - Origin address
    */
   public static List<String> getOriginAddress(List<String> messageAsList) {
      boolean isOriginLine = false;
      List<String> origin = new LinkedList<>();
      // TYPE A Format
      if(!CollectionUtils.isEmpty(messageAsList)) {
    	  for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
    	         String lineItem = messageAsList.get(msgIndex);

    	         // Extract origin address
    	         if (isOriginLine) {
    	            origin.add(lineItem.trim());
    	            String nextLine = messageAsList.get(msgIndex + 1);
    	            if (nextLine.startsWith(PRIORITY_IDENTIFIER) || nextLine.startsWith(DESTINATION_IDENTIFIER)
    	                  || nextLine.startsWith(HEADER_IDENTIFIER) || nextLine.startsWith(START_OF_MESSAGE_IDENTIFIER)
    	                  || nextLine.startsWith(MESSAGE_ID)) {
    	               break;
    	            }
    	         }

    	         if (lineItem.startsWith(ORIGIN_IDENTIFIER)) {
    	            isOriginLine = true;
    	         }
    	      }

    	      // TYPE B Format
    	      if (!isOriginLine) {
    	         String innerOriginAddressLine = null;
    	         for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
    	            String lineItem = messageAsList.get(msgIndex);
    	            if (lineItem.startsWith(DOT_IDENTIFIER)) {
    	               innerOriginAddressLine = lineItem.substring(1, lineItem.length());
    	               break;
    	            }
    	         }

    	         // Split the line
    	         if (!StringUtils.isEmpty(innerOriginAddressLine)) {
    	            String[] lineItem = innerOriginAddressLine.split(SPACEIDENTIFIER);
    	            Arrays.stream(lineItem).filter(t -> t.length() == 7).forEach(t -> {
    	               origin.add(t);
    	            });
    	         }
    	      }
      }   

      return origin;
   }

   /**
    * Method to extract destination address after =DESTINATION from message
    * 
    * @param messageAsList
    * @return List<String> - Destination address
    */
   public static List<String> getDestinationAddress(List<String> messageAsList) {
      boolean isDestinationLine = false;
      List<String> destination = new LinkedList<>();
      // TYPE A Format
      if(!CollectionUtils.isEmpty(messageAsList)) {
      for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
         String lineItem = messageAsList.get(msgIndex);

         // Extract destination address
         if (isDestinationLine) {
            if (lineItem.startsWith(START_OF_MESSAGE_IDENTIFIER)) {
               String[] destinationAddress = lineItem.split(COMMA_IDENTIFIER);
               destination.add(destinationAddress[1].trim());
            } else {
               destination.add(lineItem.trim());
            }

            String nextLine = messageAsList.get(msgIndex + 1);
            if (nextLine.startsWith(PRIORITY_IDENTIFIER) || nextLine.startsWith(HEADER_IDENTIFIER)
                  || nextLine.startsWith(ORIGIN_IDENTIFIER) || nextLine.startsWith(START_OF_MESSAGE_IDENTIFIER)
                  || nextLine.startsWith(MESSAGE_ID)) {
               break;
            }
         }

         if (lineItem.startsWith(DESTINATION_IDENTIFIER)) {
            isDestinationLine = true;
         }
      }

      // TYPE B Format
      if (!isDestinationLine) {
         // This hold line items until it reaches . character
         List<String> innerMessageList = new ArrayList<>();
         for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
            String lineItem = messageAsList.get(msgIndex);
            if (!lineItem.startsWith(DOT_IDENTIFIER)) {
               innerMessageList.add(lineItem);
            } else {
               break;
            }
         }

         for (int msgIndex = 0; msgIndex < innerMessageList.size(); msgIndex++) {
            String[] lineItem = innerMessageList.get(msgIndex).split(SPACEIDENTIFIER);
            Arrays.stream(lineItem).filter(t -> t.length() == 7).forEach(t -> {
               destination.add(t);
            });
         }
      }
      }
      return destination;
   }

   /**
    * Method to extract information after = PRIORITY from message
    * 
    * @param messageAsList
    * @return String - priority
    */
   public static String getPriority(List<String> messageAsList) {
      boolean isPriorityLine = false;
      String msgPriority = null;
      // TYPE A Format
      if(!CollectionUtils.isEmpty(messageAsList))
      {
    	  for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
    	         String lineItem = messageAsList.get(msgIndex);

    	         // Extract priority
    	         if (isPriorityLine) {
    	            msgPriority = lineItem;
    	            String nextLine = messageAsList.get(msgIndex + 1);
    	            if (nextLine.startsWith(HEADER_IDENTIFIER) || nextLine.startsWith(DESTINATION_IDENTIFIER)
    	                  || nextLine.startsWith(ORIGIN_IDENTIFIER) || nextLine.startsWith(START_OF_MESSAGE_IDENTIFIER)
    	                  || nextLine.startsWith(MESSAGE_ID)) {
    	               break;
    	            }
    	         }

    	         if (lineItem.startsWith(PRIORITY_IDENTIFIER)) {
    	            isPriorityLine = true;
    	         }
    	      }

    	      // TYPE B Format
    	      if (!isPriorityLine) {
    	         for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
    	            String lineItem = messageAsList.get(msgIndex);
    	            if (!isPriorityLine) {
    	               for (String preDefinedPriority : priorities) {
    	                  if (lineItem.startsWith(preDefinedPriority)) {
    	                     // Get the first 2 characters as priority
    	                     msgPriority = lineItem.substring(0, preDefinedPriority.length());
    	                     isPriorityLine = true;
    	                     break;
    	                  }
    	               }
    	            }
    	         }
    	      }

      }
      
      return msgPriority;
   }

   /**
    * Method to extract information after =HEADER line from message
    * 
    * @param messageAsList
    * @return String - header
    */
   public static String getHeader(List<String> messageAsList) {
      boolean isHeaderLine = false;
      String msgReceiveDateTime = null;
      // TYPE A Format
      if(!CollectionUtils.isEmpty(messageAsList))
      {
    	  for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
    	         String lineItem = messageAsList.get(msgIndex);

    	         // Extract receive time of message in origin interface system
    	         if (isHeaderLine) {
    	            String[] headerMessage = lineItem.split(COMMA_IDENTIFIER);
    	            msgReceiveDateTime = headerMessage[1];
    	            String nextLine = messageAsList.get(msgIndex + 1);
    	            if (nextLine.startsWith(PRIORITY_IDENTIFIER) || nextLine.startsWith(DESTINATION_IDENTIFIER)
    	                  || nextLine.startsWith(ORIGIN_IDENTIFIER) || nextLine.startsWith(START_OF_MESSAGE_IDENTIFIER)
    	                  || nextLine.startsWith(MESSAGE_ID)) {
    	               break;
    	            }
    	         }
    	         if (lineItem.startsWith(HEADER_IDENTIFIER)) {
    	            isHeaderLine = true;
    	         }
    	      }

    	      // TYPE B Format
    	      if (!isHeaderLine) {
    	         // TODO: Need to understand message header date/time pattern
    	      }
      }
      
      return msgReceiveDateTime;
   }
   
   public static List<String> getPimaAddress(List<String> messageAsList) {
	      boolean isOriginLine = false;
	      List<String> origin = new LinkedList<>();
	      // TYPE A Format
	      if(!CollectionUtils.isEmpty(messageAsList))
	      {
	    	  for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
	 	         String lineItem = messageAsList.get(msgIndex);

	 	         // Extract origin address
	 	         if (isOriginLine) {
	 	            origin.add(lineItem.trim());
	 	            String nextLine = messageAsList.get(msgIndex + 1);
	 	            if (nextLine.startsWith(PRIORITY_IDENTIFIER) || nextLine.startsWith(DESTINATION_IDENTIFIER)
	 	                  || nextLine.startsWith(HEADER_IDENTIFIER) || nextLine.startsWith(START_OF_MESSAGE_IDENTIFIER)
	 	                  || nextLine.startsWith(MESSAGE_ID)) {
	 	               break;
	 	            }
	 	         }

	 	         if (lineItem.startsWith(ORIGIN_IDENTIFIER)) {
	 	            isOriginLine = true;
	 	         }
	 	      }

	 	      // TYPE B Format
	 	      if (!isOriginLine) {
	 	         String innerOriginAddressLine = null;
	 	         for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
	 	            String lineItem = messageAsList.get(msgIndex);
	 	            if (lineItem.startsWith(DOT_IDENTIFIER)) {
	 	               innerOriginAddressLine = lineItem.substring(1, lineItem.length());
	 	               break;
	 	            }
	 	         }

	 	         // Split the line
	 	         if (!StringUtils.isEmpty(innerOriginAddressLine)) {
	 	            String[] lineItem = innerOriginAddressLine.split(SPACEIDENTIFIER);
	 	            Arrays.stream(lineItem).filter(t -> t.length() > 7).forEach(t -> {
	 	               origin.add(t);
	 	            });
	 	         }
	 	      }
	      }
	     

	      return origin;
	   }
   
   public static String getStringFromDate(LocalDateTime date, String dateFormat) {
       String sDate = null;
       final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
       try {
          sDate = dateFormatter.format(date);
       } catch (Exception e) {
         
       }
       return sDate;
    }
   
   
   public static String getMessageType(List<String> messageLines) {
		// TODO Auto-generated method stub
		 int j = 0;
		 for (String lineData : messageLines) {
			 if (!StringUtils.isEmpty(lineData)) {
				 if (j == 0) { // Message Type and Version - FFM/8
	                  String messageType = getMessageType(lineData); 
	                 return messageType;
	               }
			 }
		 }
		 return null;
	}



  

	      
	     
   
}
