package com.ngen.cosys.satssg.interfaces.util;

import java.util.LinkedList;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

public class MessageEnvelop extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private String messageReceiveDateTime;
   private String priority;
   private String messageFormatType;
   private String messageId;

   private String carrier;

   private boolean requiredDoubleSignature = Boolean.FALSE;

   // Header Format 1 will be true if target system wants format to be in
   // =PRIORITY,=DESTINATION TYPE B,=ORIGIN,=MSGID (IF PRESENT),=TEXT
   boolean headerFormat1;
   // Header Format 1 will be true if target system wants format to be in
   // PRIORITY DESTINATION . ORIGIN CURRENTDT
   boolean headerFormat2;

   private List<String> destinationAddress;

   public boolean isRequiredDoubleSignature() {
      return requiredDoubleSignature;
   }

   public void setRequiredDoubleSignature(boolean requiredDoubleSignature) {
      this.requiredDoubleSignature = requiredDoubleSignature;
   }

   private List<String> recipientAddress;
   private List<String> emailAddress;

   public String getCarrier() {
      return carrier;
   }

   public void setCarrier(String carrier) {
      this.carrier = carrier;
   }

   private List<String> pimaAddress;

   /**
    * @return the pimaAddress
    */
   public List<String> getPimaAddress() {
      return pimaAddress;
   }

   /**
    * @param pimaAddress
    *           the pimaAddress to set
    */
   public void setPimaAddress(List<String> pimaAddress) {
      this.pimaAddress = pimaAddress;
   }

   /**
    * Populate this object only for messages which has other than standard
    * framework supprorted message headers.
    */
   private List<String> messageHeader;

   /**
    * @return the messageReceiveDateTime
    */
   public String getMessageReceiveDateTime() {
      return messageReceiveDateTime;
   }

   /**
    * @param messageReceiveDateTime
    *           the messageReceiveDateTime to set
    */
   public void setMessageReceiveDateTime(String messageReceiveDateTime) {
      this.messageReceiveDateTime = messageReceiveDateTime;
   }

   /**
    * @return the priority
    */
   public String getPriority() {
      return priority;
   }

   /**
    * @param priority
    *           the priority to set
    */
   public void setPriority(String priority) {
      this.priority = priority;
   }

   /**
    * @return the messageFormatType
    */
   public String getMessageFormatType() {
      return messageFormatType;
   }

   /**
    * @param messageFormatType
    *           the messageFormatType to set
    */
   public void setMessageFormatType(String messageFormatType) {
      this.messageFormatType = messageFormatType;
   }

   /**
    * @return the messageId
    */
   public String getMessageId() {
      return messageId;
   }

   /**
    * @param messageId
    *           the messageId to set
    */
   public void setMessageId(String messageId) {
      this.messageId = messageId;
   }

   /**
    * @return the headerFormat1
    */
   public boolean isHeaderFormat1() {
      return headerFormat1;
   }

   /**
    * @param headerFormat1
    *           the headerFormat1 to set
    */
   public void setHeaderFormat1(boolean headerFormat1) {
      this.headerFormat1 = headerFormat1;
   }

   /**
    * @return the headerFormat2
    */
   public boolean isHeaderFormat2() {
      return headerFormat2;
   }

   /**
    * @param headerFormat2
    *           the headerFormat2 to set
    */
   public void setHeaderFormat2(boolean headerFormat2) {
      this.headerFormat2 = headerFormat2;
   }

   /**
    * @return the destinationAddress
    */
   public List<String> getDestinationAddress() {
      if (this.destinationAddress == null) {
         this.destinationAddress = new LinkedList<String>();
      }
      return destinationAddress;
   }

   /**
    * @param destinationAddress
    *           the destinationAddress to set
    */
   public void setDestinationAddress(List<String> destinationAddress) {
      this.destinationAddress = destinationAddress;
   }

   /**
    * @return the recipientAddress
    */
   public List<String> getRecipientAddress() {
      if (this.recipientAddress == null) {
         this.recipientAddress = new LinkedList<String>();
      }
      return recipientAddress;
   }

   /**
    * @param recipientAddress
    *           the recipientAddress to set
    */
   public void setRecipientAddress(List<String> recipientAddress) {
      this.recipientAddress = recipientAddress;
   }

   /**
    * @return the messageHeader
    */
   public List<String> getMessageHeader() {
      if (this.messageHeader == null) {
         this.messageHeader = new LinkedList<String>();
      }
      return messageHeader;
   }

   /**
    * @param messageHeader
    *           the messageHeader to set
    */
   public void setMessageHeader(List<String> messageHeader) {
      this.messageHeader = messageHeader;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return "MessageEnvelop [messageReceiveDateTime=" + messageReceiveDateTime + ", priority=" + priority
            + ", messageFormatType=" + messageFormatType + ", messageId=" + messageId + ", headerFormat1="
            + headerFormat1 + ", headerFormat2=" + headerFormat2 + ", destinationAddress=" + destinationAddress
            + ", recipientAddress=" + recipientAddress + ", messageHeader=" + messageHeader + "]";
   }

   public List<String> getEmailAddress() {
      return emailAddress;
   }

   public void setEmailAddress(List<String> emailAddress) {
      this.emailAddress = emailAddress;
   }

}