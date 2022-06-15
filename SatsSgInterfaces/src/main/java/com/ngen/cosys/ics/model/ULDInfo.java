package com.ngen.cosys.ics.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonPropertyOrder(value = { "containerId", "damageStatus" })
public class ULDInfo {

   @JacksonXmlProperty(localName = "containerId")
   private String containerId;

   @JacksonXmlProperty(localName = "damageStatus")
   private String damageStatus;
}
