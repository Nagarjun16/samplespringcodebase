package com.ngen.cosys.satssg.interfaces.singpost.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.validation.groups.FetchULDValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JsonPropertyOrder(value = { "containerId", "binOruld" })
@JacksonXmlRootElement(localName = "infeedWorkstationRequest")
public class InfeedULDModel {

   @NotBlank(message = "container.id.cant.blank", groups = FetchULDValidationGroup.class)
   @Length(max = 20, message = "container.id.cant.be.more.than", groups = { FetchULDValidationGroup.class })
   @JacksonXmlProperty(localName = "containerId")
   private String containerId;

   @JacksonXmlProperty(localName = "binOruld")
   private String binOruld;

}
