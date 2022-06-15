package com.ngen.cosys.ics.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder(value = { "status", "errorNumber", "errorDescription" })
@JacksonXmlRootElement(localName = "updateULDResponse")
public class ULDAutoWeightResponseModel {

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   @JacksonXmlProperty(localName = "errorNumber")
   private String errorNumber;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   @JacksonXmlProperty(localName = "errorDescription")
   private String errorDescription;

}
