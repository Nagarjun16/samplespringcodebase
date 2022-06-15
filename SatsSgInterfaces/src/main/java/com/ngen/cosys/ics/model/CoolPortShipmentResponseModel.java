package com.ngen.cosys.ics.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@ToString
@JacksonXmlRootElement(localName = "ShipmentInfoResponse")
public class CoolPortShipmentResponseModel {

   @JacksonXmlProperty(localName = "AWBNumber")
   private String awbNumber;

   @JacksonXmlProperty(localName = "Origin")
   private String origin;

   @JacksonXmlProperty(localName = "Destination")
   private String destination;

   @JacksonXmlProperty(localName = "Pieces")
   private String pieces;

   @JacksonXmlProperty(localName = "Weight")
   private String weight;

   @JacksonXmlProperty(localName = "SHC")
   private String shc;

   @JacksonXmlProperty(localName = "Description")
   private String description;

   @JacksonXmlProperty(localName = "TempUnit")
   private Character tempUnit;
   
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "BookingInfo")
   private BookingInfo bookingInfo;


   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "TemperatureInfo")
   private List<TemperatureInfo> temperatureInfo;

}
