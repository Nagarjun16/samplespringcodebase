package com.ngen.cosys.ics.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JacksonXmlRootElement(localName = "fetchICSULDListResponse")
@JsonPropertyOrder(value = { "status", "errorNumber", "errorDescription" })
public class FetchICSULDListResponse {

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JacksonXmlProperty(localName = "errorNumber")
   private String errorNumber;

   @JacksonXmlProperty(localName = "errorDescription")
   private String errorDescription;

}
