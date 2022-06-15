package com.ngen.cosys.ics.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@JsonPropertyOrder(value = { "status", "errorNumber", "errorDescription" })
@JacksonXmlRootElement(localName = "preAnnouncementResponse")
public class PreAnnouncementDetailsException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public PreAnnouncementDetailsException(String status, HttpStatus errorCode, Object description) {
      super();
      this.status = status;
      this.errorCode = errorCode.toString();
      this.description = description.toString();
   }

   @JacksonXmlProperty(localName = "status")
   private String status;

   @JacksonXmlProperty(localName = "errorNumber")
   private String errorCode;

   @JacksonXmlProperty(localName = "errorDescription")
   private String description;

}