package com.ngen.cosys.ics.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
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
@JsonPropertyOrder(value = { "status", "cargoTerminal" })
public class FetchICSULDListModel {

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "terminal")
   List<Terminal> terminal;

   /* private Terminal terminal; */

}
