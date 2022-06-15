package com.ngen.cosys.ics.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemperatureInfo {

   @JacksonXmlProperty(localName = "ULDNumber")
   private String uldNumber;

   @JacksonXmlElementWrapper(useWrapping = false)

   @JacksonXmlProperty(localName = "TrackingID")
   private String trackingId;

   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "Info")
   private List<Info> info;
}