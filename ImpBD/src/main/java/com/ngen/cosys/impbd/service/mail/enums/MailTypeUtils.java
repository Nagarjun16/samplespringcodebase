/**
 * 
 * MailTypeUtils.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          17 JUN, 2018   NIIT      -
 */
package com.ngen.cosys.impbd.service.mail.enums;

import org.springframework.http.MediaType;

/**
 * This enum is used for commonly used Mail Type Constants
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum MailTypeUtils {
   
   APPLICATION_CSV(Type.APPLICATION_CSV), //
   APPLICATION_MSWORD(Type.APPLICATION_MSWORD), //
   APPLICATION_PDF(Type.APPLICATION_PDF), //
   APPLICATION_XLS(Type.APPLICATION_XLS), //
   IMAGE_GIF(Type.IMAGE_GIF), //
   IMAGE_JPEG(Type.IMAGE_JPEG), //
   IMAGE_PNG(Type.IMAGE_PNG), //
   TEXT_HTML(Type.TEXT_HTML), //
   TEXT_PLAIN(Type.TEXT_PLAIN), //
   TEXT_XML(Type.TEXT_XML), //
   MULTIPART_MIXED(Type.MULTIPART_MIXED), //
   SMTP(Type.SMTP), //
   SMTP_MAIL_PROFILE(Type.SMTP_MAIL_PROFILE), //
   SMTP_HOST(Type.SMTP_MAIL_HOST), //
   SMTP_PORT(Type.SMTP_MAIL_PORT), //
   SMTP_AUTH(Type.SMTP_MAIL_AUTH), //
   SMTP_USERNAME(Type.SMTP_MAIL_USERNAME), //
   SMTP_PASSWORD(Type.SMTP_MAIL_PASSWORD), //
   SMTP_SSL(Type.SMTP_MAIL_SSL_ENCTYPE), //
   SMTP_TSL(Type.SMTP_MAIL_TLS_ENCTYPE), //
   SMTP_TRANSPORT_PROTOCOL(Type.MAIL_TRANSPORT_PROTOCOL), //
   SMTP_CONNECTION_TIMEOUT(Type.SMTP_MAIL_CONNECTION_TIMEOUT), //
   SMTP_TIMEOUT(Type.SMTP_MAIL_TIMEOUT),
   SMTP_MAIL_SSL_TRUST(Type.SMTP_MAIL_SSL_TRUST),
   SMTP_TLS_ENABLE(Type.SMTP_MAIL_TLS_ENABLE),
   SMTP_MAIL_DEBUG_AUTH(Type.SMTP_MAIL_DEBUG_AUTH),
   SMTP_SSL_TRANSPORT_PROTOCOL(Type.SMTP_SSL_TRANSPORT_PROTOCOL);
	
   private class Type {
      /**
       * No argument Constructor
       */
      private Type() {}

      public static final String APPLICATION_PDF = MediaType.APPLICATION_PDF_VALUE; 	// Application PDF
      public static final String APPLICATION_CSV = "application/csv";					// Application CSV
      public static final String APPLICATION_MSWORD = "application/msword";             // Application MS Word
      public static final String APPLICATION_XLS = "application/vnd.ms-excel";          // Application MS Excel
      public static final String IMAGE_GIF = MediaType.IMAGE_GIF_VALUE;                 // Image of GIF Type
      public static final String IMAGE_JPEG = MediaType.IMAGE_JPEG_VALUE;				// Image of JPEG Type
      public static final String IMAGE_PNG = MediaType.IMAGE_PNG_VALUE;                 // Image of PNG Type
      public static final String TEXT_HTML = MediaType.TEXT_HTML_VALUE;                 // Text HTML
      public static final String TEXT_PLAIN = MediaType.TEXT_PLAIN_VALUE;				// Text Plain
      public static final String TEXT_XML = MediaType.TEXT_XML_VALUE;					// Text XML
      public static final String MULTIPART_MIXED = "multipart/mixed";                   // Multipart-Mixed

      // SMTP MAIL SERVER
      public static final String SMTP = "smtp";
      public static final String SMTP_MAIL_PROFILE = "mail.smtp.profile";               // SMTP Profile
      public static final String SMTP_MAIL_HOST = "mail.smtp.host";					    // SMTP Server Address
      public static final String SMTP_MAIL_PORT = "mail.smtp.port";                     // SMTP Port Number
      public static final String SMTP_MAIL_AUTH = "mail.smtp.auth";					    // SMTP Server Authorization Header
      public static final String SMTP_MAIL_USERNAME = "mail.username";                  // SMTP Mail Username
      public static final String SMTP_MAIL_PASSWORD = "mail.password";                  // SMTP Mail Password
      public static final String SMTP_MAIL_SSL_ENCTYPE = "mail.smtp.startssl.enable";   // SSL Encryption
      public static final String SMTP_MAIL_TLS_ENCTYPE = "mail.smtp.starttls.required"; // TSL Encryption
      public static final String SMTP_MAIL_TLS_ENABLE = "mail.smtp.starttls.enable";    // TLS Enable
      public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";   // Mail Transport Protocol
      public static final String SMTP_MAIL_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout"; //SMTP Connection Timeout
      public static final String SMTP_MAIL_TIMEOUT = "mail.smtp.timeout"; //SMTP Request Timeout
      public static final String SMTP_MAIL_SSL_TRUST = "mail.smtp.ssl.trust"; // SMTP All hosts are trusted
      public static final String SMTP_MAIL_DEBUG_AUTH = "mail.debug.auth"; //SMTP Debug Auth
      public static final String SMTP_SSL_TRANSPORT_PROTOCOL = "mail.smtp.ssl.protocols"; 
   }
	
   /**
    * Mail Type value
    */
   String value;

   /**
    * @param name
    */
   private MailTypeUtils(String name) {
      this.value = name;
   }

   /**
    * @return
    */
   public String getValue() {
      return this.value;
   }

}
