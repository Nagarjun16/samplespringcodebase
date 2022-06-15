package com.ngen.cosys.ics.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "preAnnouncementResponse")
public class PreAnnouncementResponse {

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JacksonXmlProperty(localName = "errorNumber")
   private String errorNumber;

   @JacksonXmlProperty(localName = "errorDescription")
   private String errorDescription;

}
