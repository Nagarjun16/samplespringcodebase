package com.ngen.cosys.ics.model;

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
@JsonPropertyOrder(value = { "status", "ULD" })
@JacksonXmlRootElement(localName = "fetchImportULDResponse")
public class ICSPreAnnouncementResponseModel {

   private String status;

   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "ULD")
   private ULD uld;
}