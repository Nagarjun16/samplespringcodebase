package com.ngen.cosys.ics.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "infeedWorkstationResponse")
public class InfeedWorkstationResponse {

   @JacksonXmlProperty(localName = "staus")
   private String status;
}