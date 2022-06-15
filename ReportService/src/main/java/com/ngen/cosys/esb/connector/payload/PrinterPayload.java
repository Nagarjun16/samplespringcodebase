/**
 * 
 * PrinterPayload.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          10 JUL, 2018   NIIT      -
 */
package com.ngen.cosys.esb.connector.payload;

import java.math.BigInteger;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is the Printer Payload which actually gets payload in stream of data from
 * interface and sent to Mule ESB of Multipart request
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "messageId", "tenantID", "loggerEnabled", "contentParams" })
@JacksonXmlRootElement(localName = "printerPayload")
public class PrinterPayload {

   @JacksonXmlProperty(localName = "contentName")
   private String contentName;  // Content Name
   
   @JacksonXmlProperty(localName = "contentFormat")
   private String contentFormat;  // Content format is File format type included with (.)

   @JacksonXmlProperty(localName = "contentData")
   private String contentData;  // Content Data (PDF Data or Text data in bytes)
   
   @JacksonXmlProperty(localName = "messageId")
   private BigInteger messageId;
   
   @JacksonXmlProperty(localName = "tenantID")
   private String tenantID;
   
   @JacksonXmlProperty(localName = "loggerEnabled")
   private boolean loggerEnabled = Boolean.FALSE;
   
   @JacksonXmlProperty(localName = "queueName")
   private String queueName;
   
   @JacksonXmlProperty(localName = "printerType")
   private String printerType; 
   
   @JacksonXmlProperty(localName = "contentParams")
   private Map<String, Object> contentParams;
   
}
