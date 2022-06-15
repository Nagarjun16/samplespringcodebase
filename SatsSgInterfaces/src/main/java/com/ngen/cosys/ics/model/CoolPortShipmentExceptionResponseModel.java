package com.ngen.cosys.ics.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JacksonXmlRootElement(localName = "ShipmentInfoResponse")
public class CoolPortShipmentExceptionResponseModel {

   @JacksonXmlProperty(localName = "AWBNumber")
   private String awbNumber;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   @JacksonXmlProperty(localName = "errorType")
   private String errorType;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   @JacksonXmlProperty(localName = "errorNumber")
   private String errorNumber;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   @JacksonXmlProperty(localName = "errorDescription")
   private String errorDescription;

}
