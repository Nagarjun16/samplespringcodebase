package com.ngen.cosys.ics.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonPropertyOrder(value = { "cargoTerminal", "uldInfo" })
public class Terminal {

   @JacksonXmlProperty(localName = "cargoTerminal")
   private String cargoTerminal;

   @JacksonXmlElementWrapper(localName = "uldInfo", useWrapping = true)
   @JacksonXmlProperty(localName = "uld")
   private List<ULDInfo> uldInfo;

}
