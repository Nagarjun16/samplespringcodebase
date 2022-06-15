package com.ngen.cosys.satssginterfaces.mss.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "GHAMAWBNO")
public class AEDResponse {

   @JacksonXmlElementWrapper(useWrapping = true)
   @JacksonXmlProperty(localName = "HEADER")
   private AedHeader headers;

   @JacksonXmlElementWrapper(useWrapping = true)
   @JacksonXmlProperty(localName = "DETAIL")
   private AedDetail details;

}