/**
 * 
 * MailBO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          17 JUN, 2018   NIIT      -
 */
package com.ngen.cosys.impbd.service.mail.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is the Mail Payload which actually received from Interfaces
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class MailBO extends BaseBO {

   /**
    * Default serial version id
    */
   private static final long serialVersionUID = 1L;

   /**
    * Subject of the mail
    */
   private String mailSubject;

   /**
    * Body of the Mail (Generated from Template Engine)
    */
   private String mailBody;

   /**
    * Sender Name
    */
   private String senderName;

   /**
    * Sender Mail Address
    */
   private String mailFrom;

   /**
    * Recipient Mail Address
    */
   private String mailTo;

   /**
    * CC Mail Address
    */
   private String mailCC;

   /**
    * BCC Mail Address
    */
   private String mailBCC;

   /**
    * Mail To Addresses (More than one recipient / Mail Groups)
    */
   private String[] mailToAddress;

   /**
    * Mail CC Addresses (More than one CC / Mail Groups)
    */
   private String[] mailCCAddress;

   /**
    * Mail BCC Addresses (More than one BCC / Mail Groups)
    */
   private String[] mailBCCAddress;

   /**
    * Mail Reply To Address
    */
   private String replyTo;

   /**
    * Set Notify Address
    */
   private String notifyAddress;

   /**
    * Mail Send Status
    */
   private boolean mailStatus = Boolean.TRUE;

   /**
    * Failed Reason
    */
   private String failedReason;

   /**
    * Mail Template Instance
    */
   private TemplateBO template;

   /**
    * Mail Contents that needs to be embedded in Mail Body (Generated from Template
    * Engine)
    */
   private Map<String, Object> mailEmbeddedContents;

   /**
    * Mail Attachments (Source of the ContentType & Attachment Stream Data)
    */
   private Map<String, AttachmentStream> mailAttachments;

   /**
    * Mail Attachment Stream Data that holds document information and associated
    * Key & Value
    *
    */
   @Getter
   @Setter
   private class AttachmentStream implements Serializable {

      private static final long serialVersionUID = 1L;

      /**
       * No Argument Constructor
       */
      private AttachmentStream() {
      }

      private BigInteger fileId; // File referenceId
      private String fileName; // File Name
      private String fileType; // File Type
      private String fileData; // File Data
      private byte[] fileBytes; // File Bytes

   }

}
