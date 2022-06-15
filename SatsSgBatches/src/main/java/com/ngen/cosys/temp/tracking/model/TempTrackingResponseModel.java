package com.ngen.cosys.temp.tracking.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
@JacksonXmlRootElement(localName = "TempResponse")
public class TempTrackingResponseModel{

   @JacksonXmlProperty(localName = "AWBNumber")
   private String awbNumber;

   @JacksonXmlProperty(localName = "ULDNumber")
   private String uldNumber;

   @JacksonXmlProperty(localName = "Location")
   private String location;
   
   @JacksonXmlProperty(localName = "TempUnti")
   private String tempUnti;
   
   @JacksonXmlProperty(localName = "TrackingDetail")
   private List<TempTrackingInfo> tempTrackingInfo = new ArrayList<>();
   

}
