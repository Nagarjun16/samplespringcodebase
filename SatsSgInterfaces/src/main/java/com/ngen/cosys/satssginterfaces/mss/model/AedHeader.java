package com.ngen.cosys.satssginterfaces.mss.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class AedHeader {

   @JacksonXmlProperty(localName = "MESSAGE_TYPE")
   private String aedMessageType;

   @JacksonXmlProperty(localName = "MESSAGE_ID")
   private String aedMessageId;

   @JacksonXmlProperty(localName = "SEND_DATETIME")
   private String sendDateTime;

}
