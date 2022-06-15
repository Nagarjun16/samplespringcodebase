package com.ngen.cosys.ics.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JsonPropertyOrder(value = { "containerId", "state", "location", "exitDate", "exitTime"  })
public class FetchICSLocationContainerInfoModel {

   @JacksonXmlProperty(localName = "containerId")
   public String containerId;

   @JacksonXmlProperty(localName = "state")
   public String state;

   @JacksonXmlProperty(localName = "location")
   public String location;

   @JacksonXmlProperty(localName = "exitDate")
   private String exitDate;

   @JacksonXmlProperty(localName = "exitTime")
   private String exitTime;

}