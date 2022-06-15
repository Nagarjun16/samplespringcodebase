package com.ngen.cosys.temp.tracking.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter
@Setter
public class TempTrackingInfo {

   @JacksonXmlProperty(localName = "Time")
   private String time;

   @JacksonXmlProperty(localName = "Temperature")
   private String temperature;
}
