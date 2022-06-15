package com.ngen.cosys.ics.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder(value = { "status", "errorType", "errorNumber", "errorDescription" })
@JacksonXmlRootElement(localName = "InOutPDResponse")
public class BLEEquipmentRampProcessResponseModel {

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JacksonXmlProperty(localName = "errorType")
   private String errorType;

   /* @JsonInclude(JsonInclude.Include.NON_NULL) */
   @JacksonXmlProperty(localName = "errorNumber")
   private String errorNumber;

   /* @JsonInclude(JsonInclude.Include.NON_NULL) */
   @JacksonXmlProperty(localName = "errorDescription")
   private String errorDescription;

}
