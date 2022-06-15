package com.ngen.cosys.satssg.interfaces.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.message.enums.TextMessageConstants;

@Component
@Scope(scopeName = "singleton")
public class TextMessageUtils {

   private String startOfMessageIdentifier = "=TEXT";
   private String priorityIdentifier = "=PRIORITY";
   private String destinationIdentifier = "=DESTINATION";
   private String originIdentifier = "=ORIGIN";
   private String headerIdentifier = "=HEADER";
   private String messageIdIdentifier = "=MSGID";
   private String slashIdentifier = "/";
   private String commaIdentifier = ",";
   private String dotIdentifier = ".";
   private String spaceIdentifier = " ";
   private String startOfTextIdentifier = "STX";
   private final String[] priorities = { "QD", "QK", "QP" };

   /**
    * Method to get string message as a List
    * 
    * @param textMessage
    * @return List<String> - message
    */
   public List<String> getMessageList(String textMessage) {
      // Object to store messages as List
      List<String> messageAsList = new LinkedList<String>();
      // Tokenizer for splitting message by CRLF character
      StringTokenizer stringTokenizer = new StringTokenizer(textMessage, TextMessageConstants.CRLF.getValue());
      while (stringTokenizer.hasMoreTokens()) {
         String lineItem = stringTokenizer.nextToken();
         // trim the text
         if (!StringUtils.isEmpty(lineItem)) {
            messageAsList.add(lineItem);
         }
      }
      return messageAsList;
   }

   /**
    * Method to get string message as a List
    * 
    * @param textMessage
    * @return List<String> - message without trimming spaces for outgoing messages
    */
   public List<String> getMessageListwithoutTrim(String textMessage) {
      // Object to store messages as List
      List<String> messageAsList = new LinkedList<String>();
      // Tokenizer for splitting message by CRLF character
      StringTokenizer stringTokenizer = new StringTokenizer(textMessage, TextMessageConstants.CRLF.getValue());
      while (stringTokenizer.hasMoreTokens()) {
         String lineItem = stringTokenizer.nextToken();
         // trim the text
         if (!StringUtils.isEmpty(lineItem)) {
            messageAsList.add(lineItem);
         }
      }
      return messageAsList;
   }

   /**
    * Method to get string message as a List
    * 
    * @param textMessage
    * @param ignoreCheckForTextIdentifier
    *           - This parameter would be used in case of validating outbound
    *           messages before dispatching.
    * @return List<String> - message with no header information
    */
   public List<String> getMessageListWithNoEnvelop(List<String> messageAsList, boolean ignoreCheckForTextIdentifier) {
      // Object to store messages as List
      List<String> messageWithNoEnvelop = new LinkedList<>();
      // Identifier for ignoring lines until it reaches =TEXT
      boolean omitMessageLine = true;
      // TYPE A Format
      for (String lineItem : messageAsList) {

         // Ignore omit message line check
         if (ignoreCheckForTextIdentifier) {
            // turn off the omit line identifier
            omitMessageLine = false;
         }

         if (!omitMessageLine) {
            messageWithNoEnvelop.add(lineItem.trim());
         }

         if (!ignoreCheckForTextIdentifier && startOfMessageIdentifier.equalsIgnoreCase(lineItem)) {
            // turn off the omit line identifier
            omitMessageLine = false;
         }
      }

      // TYPE B Format
      if (CollectionUtils.isEmpty(messageWithNoEnvelop)) {
         for (String lineItem : messageAsList) {
            if (!omitMessageLine) {
               messageWithNoEnvelop.add(lineItem);
            } else {

               if (lineItem.startsWith(dotIdentifier)) {
                  // turn off the omit line identifier
                  omitMessageLine = false;
               }
            }
         }
      }
      return messageWithNoEnvelop;
   }

   /**
    * Method to get string message as a List
    * 
    * @param textMessage
    * @param ignoreCheckForTextIdentifier
    *           - This parameter would be used in case of validating outbound
    *           messages before dispatching.
    * @return List<String> - message with no header information without triming for
    *         outgoing messages
    */
   public List<String> getMessageListWithNoEnvelopWithoutTrim(List<String> messageAsList,
         boolean ignoreCheckForTextIdentifier) {
      // Object to store messages as List
      List<String> messageWithNoEnvelop = new LinkedList<>();
      // Identifier for ignoring lines until it reaches =TEXT
      boolean omitMessageLine = true;
      // TYPE A Format
      for (String lineItem : messageAsList) {

         // Ignore omit message line check
         if (ignoreCheckForTextIdentifier) {
            // turn off the omit line identifier
            omitMessageLine = false;
         }

         if (!omitMessageLine) {
            messageWithNoEnvelop.add(lineItem.trim());
         }

         if (!ignoreCheckForTextIdentifier && startOfMessageIdentifier.equalsIgnoreCase(lineItem)) {
            // turn off the omit line identifier
            omitMessageLine = false;
         }
      }

      // TYPE B Format
      if (CollectionUtils.isEmpty(messageWithNoEnvelop)) {
         for (String lineItem : messageAsList) {
            if (!omitMessageLine) {
               messageWithNoEnvelop.add(lineItem);
            } else {

               if (lineItem.startsWith(dotIdentifier)) {
                  // turn off the omit line identifier
                  omitMessageLine = false;
               }
            }
         }
      }
      return messageWithNoEnvelop;
   }

   /**
    * Method to get an message byte array
    * 
    * @param message
    * @return byte[] - get message as array
    */
   public byte[] getMessageAsByteArray(String message) {
      return message.getBytes();
   }

   /**
    * Method to extract information such as HEADER/PRIORITY/ORIGIN/DESTINATION
    * address from message
    * 
    * @param messageAsList
    * @return MessageEnvelop
    */
   public MessageEnvelop getMessageEnvelop(List<String> messageAsList) {
      MessageEnvelop envelop = new MessageEnvelop();

      // Derive Type A/B
      boolean typeA = this.isTypeA(messageAsList);
      if (typeA) {
         envelop.setHeaderFormat1(true);
         envelop.setHeaderFormat2(false);
      } else {
         envelop.setHeaderFormat1(false);
         envelop.setHeaderFormat2(true);
      }

      // Header
      String messageReceiveDateTime = this.getHeader(messageAsList);
      envelop.setMessageReceiveDateTime(messageReceiveDateTime);

      // Priority
      String priority = this.getPriority(messageAsList);
      envelop.setPriority(priority);

      // Origin
      List<String> originAddress = this.getOriginAddress(messageAsList);
      envelop.setRecipientAddress(originAddress);

      // Destination
      List<String> destinationAddress = this.getDestinationAddress(messageAsList);
      envelop.setDestinationAddress(destinationAddress);

      // pimaaddress
      List<String> pimaAddress = this.getPimaAddress(messageAsList);
      envelop.setPimaAddress(pimaAddress);

      return envelop;
   }

   /**
    * Method to derive Type A
    * 
    * @param messageAsList
    * @return
    */
   public boolean isTypeA(List<String> messageAsList) {
      // TYPE A Format
      boolean isTypeA = false;
      for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
         String lineItem = messageAsList.get(msgIndex);

         if (lineItem.startsWith(priorityIdentifier) || lineItem.startsWith(destinationIdentifier)
               || lineItem.startsWith(headerIdentifier) || lineItem.startsWith(startOfMessageIdentifier)
               || lineItem.startsWith(messageIdIdentifier)) {
            isTypeA = true;
            break;
         }
      }
      return isTypeA;
   }

   /**
    * Method to extract origin address after =ORIGIN from message
    * 
    * @param messageAsList
    * @return List<String> - Origin address
    */
   public List<String> getOriginAddress(List<String> messageAsList) {
      boolean isOriginLine = false;
      List<String> origin = new LinkedList<>();
      // TYPE A Format
      for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
         String lineItem = messageAsList.get(msgIndex);

         // Extract origin address
         if (isOriginLine) {
            origin.add(lineItem.trim());
            String nextLine = messageAsList.get(msgIndex + 1);
            if (nextLine.startsWith(priorityIdentifier) || nextLine.startsWith(destinationIdentifier)
                  || nextLine.startsWith(headerIdentifier) || nextLine.startsWith(startOfMessageIdentifier)
                  || nextLine.startsWith(messageIdIdentifier)) {
               break;
            }
         }

         if (lineItem.startsWith(originIdentifier)) {
            isOriginLine = true;
         }
      }

      // TYPE B Format
      if (!isOriginLine) {
         String innerOriginAddressLine = null;
         for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
            String lineItem = messageAsList.get(msgIndex);
            if (lineItem.startsWith(dotIdentifier)) {
               innerOriginAddressLine = lineItem.substring(1, lineItem.length());
               break;
            }
         }

         // Split the line
         if (!StringUtils.isEmpty(innerOriginAddressLine)) {
            String[] lineItem = innerOriginAddressLine.split(spaceIdentifier);
            Arrays.stream(lineItem).filter(t -> t.length() == 7).forEach(t -> {
               origin.add(t);
            });
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
   public List<String> getDestinationAddress(List<String> messageAsList) {
      boolean isDestinationLine = false;
      List<String> destination = new LinkedList<>();
      // TYPE A Format
      for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
         String lineItem = messageAsList.get(msgIndex);

         // Extract destination address
         if (isDestinationLine) {
            if (lineItem.startsWith(this.startOfTextIdentifier)) {
               String[] destinationAddress = lineItem.split(this.commaIdentifier);
               destination.add(destinationAddress[1].trim());
            } else {
               destination.add(lineItem.trim());
            }

            String nextLine = messageAsList.get(msgIndex + 1);
            if (nextLine.startsWith(priorityIdentifier) || nextLine.startsWith(headerIdentifier)
                  || nextLine.startsWith(originIdentifier) || nextLine.startsWith(startOfMessageIdentifier)
                  || nextLine.startsWith(messageIdIdentifier)) {
               break;
            }
         }

         if (lineItem.startsWith(destinationIdentifier)) {
            isDestinationLine = true;
         }
      }

      // TYPE B Format
      if (!isDestinationLine) {
         // This hold line items until it reaches . character
         List<String> innerMessageList = new ArrayList<>();
         for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
            String lineItem = messageAsList.get(msgIndex);
            if (!lineItem.startsWith(dotIdentifier)) {
               innerMessageList.add(lineItem);
            } else {
               break;
            }
         }

         for (int msgIndex = 0; msgIndex < innerMessageList.size(); msgIndex++) {
            String[] lineItem = innerMessageList.get(msgIndex).split(spaceIdentifier);
            Arrays.stream(lineItem).filter(t -> t.length() == 7).forEach(t -> {
               destination.add(t);
            });
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
   public String getPriority(List<String> messageAsList) {
      boolean isPriorityLine = false;
      String msgPriority = null;
      // TYPE A Format
      for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
         String lineItem = messageAsList.get(msgIndex);

         // Extract priority
         if (isPriorityLine) {
            msgPriority = lineItem;
            String nextLine = messageAsList.get(msgIndex + 1);
            if (nextLine.startsWith(headerIdentifier) || nextLine.startsWith(destinationIdentifier)
                  || nextLine.startsWith(originIdentifier) || nextLine.startsWith(startOfMessageIdentifier)
                  || nextLine.startsWith(messageIdIdentifier)) {
               break;
            }
         }

         if (lineItem.startsWith(priorityIdentifier)) {
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

      return msgPriority;
   }

   /**
    * Method to extract information after =HEADER line from message
    * 
    * @param messageAsList
    * @return String - header
    */
   public String getHeader(List<String> messageAsList) {
      boolean isHeaderLine = false;
      String msgReceiveDateTime = null;
      // TYPE A Format
      for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
         String lineItem = messageAsList.get(msgIndex);

         // Extract receive time of message in origin interface system
         if (isHeaderLine) {
            String[] headerMessage = lineItem.split(this.commaIdentifier);
            msgReceiveDateTime = headerMessage[1];
            String nextLine = messageAsList.get(msgIndex + 1);
            if (nextLine.startsWith(priorityIdentifier) || nextLine.startsWith(destinationIdentifier)
                  || nextLine.startsWith(originIdentifier) || nextLine.startsWith(startOfMessageIdentifier)
                  || nextLine.startsWith(messageIdIdentifier)) {
               break;
            }
         }
         if (lineItem.startsWith(headerIdentifier)) {
            isHeaderLine = true;
         }
      }

      // TYPE B Format
      if (!isHeaderLine) {
         // TODO: Need to understand message header date/time pattern
      }
      return msgReceiveDateTime;
   }

   /**
    * Method to derive message type from incoming message
    * 
    * @param message
    * @return String - message type
    */
   public String getMessageType(String message) {
      String[] firstMessageLine = message.split(this.slashIdentifier);
      return firstMessageLine[0];
   }

   public List<String> getPimaAddress(List<String> messageAsList) {
      boolean isOriginLine = false;
      List<String> origin = new LinkedList<>();
      // TYPE A Format
      for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
         String lineItem = messageAsList.get(msgIndex);

         // Extract origin address
         if (isOriginLine) {
            origin.add(lineItem.trim());
            String nextLine = messageAsList.get(msgIndex + 1);
            if (nextLine.startsWith(priorityIdentifier) || nextLine.startsWith(destinationIdentifier)
                  || nextLine.startsWith(headerIdentifier) || nextLine.startsWith(startOfMessageIdentifier)
                  || nextLine.startsWith(messageIdIdentifier)) {
               break;
            }
         }

         if (lineItem.startsWith(originIdentifier)) {
            isOriginLine = true;
         }
      }

      // TYPE B Format
      if (!isOriginLine) {
         String innerOriginAddressLine = null;
         for (int msgIndex = 0; msgIndex < messageAsList.size(); msgIndex++) {
            String lineItem = messageAsList.get(msgIndex);
            if (lineItem.startsWith(dotIdentifier)) {
               innerOriginAddressLine = lineItem.substring(1, lineItem.length());
               break;
            }
         }

         // Split the line
         if (!StringUtils.isEmpty(innerOriginAddressLine)) {
            String[] lineItem = innerOriginAddressLine.split(spaceIdentifier);
            Arrays.stream(lineItem).filter(t -> t.length() > 7).forEach(t -> {
               origin.add(t);
            });
         }
      }

      return origin;
   }

}