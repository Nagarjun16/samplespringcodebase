package com.ngen.cosys.ics.model;

import org.springframework.stereotype.Component;

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
@JacksonXmlRootElement(localName = "fetchPCHSLocationResponse")
public class FetchICSLocationFailResponseModel {

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JacksonXmlProperty(localName = "errorNumber")
   private String errorNumber;
   
   @JacksonXmlProperty(localName = "errorDescription")
   private String errorDescription;

}
