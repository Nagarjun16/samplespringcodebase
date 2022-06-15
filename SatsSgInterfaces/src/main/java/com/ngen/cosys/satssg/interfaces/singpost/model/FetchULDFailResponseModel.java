package com.ngen.cosys.satssg.interfaces.singpost.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@Setter
@ToString
@Getter
@NoArgsConstructor
@JsonPropertyOrder(value = { "status", "errorNumber", "errorDescription" })
@JacksonXmlRootElement(localName = "updateULDResponse")
public class FetchULDFailResponseModel {

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   @JacksonXmlProperty(localName = "errorNumber")
   private String errorNumber;

   @JsonInclude(JsonInclude.Include.NON_NULL)
   @JacksonXmlProperty(localName = "errorDescription")
   private String errorDescription;

}
