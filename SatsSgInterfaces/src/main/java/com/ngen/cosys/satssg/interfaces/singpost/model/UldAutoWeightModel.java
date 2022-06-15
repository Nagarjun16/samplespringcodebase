package com.ngen.cosys.satssg.interfaces.singpost.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.validation.groups.UldAutoWeightValidationGroup;

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
@Component
@JsonPropertyOrder(value = { "containerId", "ULDWeight", "staffLoginId" })
@JacksonXmlRootElement(localName = "updateULDRequest")
public class UldAutoWeightModel {

   @NotBlank(message = "container.id.cant.blank", groups = UldAutoWeightValidationGroup.class)
   @JacksonXmlProperty(localName = "containerId")
   private String containerId;

   @JacksonXmlProperty(localName = "ULDWeight")
   private float ULDWeight;

   @NotBlank(message = "staffLoginId.cant.be.blank", groups = UldAutoWeightValidationGroup.class)
   @Length(max = 10, message = "staff.login.id.cant.be.more.than", groups = { UldAutoWeightValidationGroup.class })
   @JacksonXmlProperty(localName = "staffLoginId")
   private String staffLoginId;
   
}