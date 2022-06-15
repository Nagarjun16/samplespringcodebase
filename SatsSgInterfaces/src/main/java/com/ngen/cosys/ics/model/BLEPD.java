package com.ngen.cosys.ics.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class BLEPD {
   @JacksonXmlProperty(localName = "palletDolley")
   private String palletDolley;

   @JacksonXmlProperty(localName = "Location")
   private String location;
}
