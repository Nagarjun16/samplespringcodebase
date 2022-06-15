/**
 * {@link MessageASCIIConstants}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.common;

/**
 * Message ASCII Constants
 * 
 * @author NIIT Technologies Ltd
 */
public final class MessageASCIIConstants {

   private MessageASCIIConstants() {
      throw new RuntimeException("Creating instances are not allowed");
   }
   public static final String NEW_LINE = System.getProperty("line.separator");
   //
   public static final String SENDER_ADDRESS_PREFIX = ".SIN";
   // CR LF
   public static final byte[] LINEFEED_CRLF = { 0x0D, 0x0A };
   // SOH
   public static final byte[] START_OF_HEADER = { 0x01 };
   // STX
   public static final byte[] START_OF_TEXT = { 0x02 };
   // ETX
   public static final byte[] END_OF_TEXT = { 0x03 };
   // SPACE
   public static final byte[] SPACE = { 0x20 };
   //
   public static final String CRLF = "\r\n";
   public static final String SLASH = "/";
   public static final String HYPHEN = "-";
   public static final String REGEX_CRLF = "(?m)^[ \t]*\r?\n";
   public static final String DOT_IDENTIFIER = ".";
   public static final String DUO_IDENTIFIER = "DUO";
   public static final String UFL_IDENTIFIER = "FLT";
   public static final String UIM_IDENTIFIER = "UIM";
   public static final String MWB_IDENTIFIER = "MWB";
   public static final String MBI_IDENTIFIER = "MBI";
   public static final String UFL_MSG_TYPE = "UFL";
   public static final String IATA_RESEND_CODE = "PDM";
   public static final String START_OF_MESSAGE_IDENTIFIER = "=TEXT";
   public static final String SPACEIDENTIFIER = " ";
   public static final String NIL_CONTENT = "NIL";
   public static final String CONT_PART = "CONT";
   public static final String LAST_PART = "LAST";
   // IN Channel CCN Interface System 
   public static final String SYSTEM_CCN = "CCN";
   public static final String SYSTEM_UFIS = "UFIS";
   public static final String SYSTEM_AED = "AED";
   public static final String PRIORITY_IDENTIFIER = "=PRIORITY";
   public static final String DESTINATION_IDENTIFIER = "=DESTINATION";
   public static final String ORIGIN_IDENTIFIER = "=ORIGIN";
   public static final String HEADER_IDENTIFIER = "=HEADER";
   public static final String MESSAGE_ID = "=MSGID";
   public static final String COMMA_IDENTIFIER = ",";
   public static final String[] priorities = { "QD", "QK", "QP" };
   
}
