package com.ngen.cosys.ics.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "preAnnouncementRequest")
public class PreAnnouncementRequestModel {

   @NotBlank(message = "container.id.cant.blank")
   @JacksonXmlProperty(localName = "containerId")
   private String containerId;

}
