package com.ngen.cosys.ics.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.validation.groups.ContainerMovementValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder(value = { "containerId", "staffLoginId", "pchsDestination" })
@JacksonXmlRootElement(localName = "containerMovementRequest")
public class ContainerMovementModel {

   @NotBlank(message = "container.id.cant.blank", groups = ContainerMovementValidationGroup.class)
   @Length(max = 10, message = "container.id.cant.be.more.than", groups = ContainerMovementValidationGroup.class)
   @JacksonXmlProperty(localName = "containerId")
   private String containerId;

   @NotBlank(message = "staffLoginId.cant.be.blank", groups = ContainerMovementValidationGroup.class)
   @Length(max = 10, message = "staff.login.id.cant.be.more.than", groups = ContainerMovementValidationGroup.class)
   @JacksonXmlProperty(localName = "staffLoginId")
   private String staffLoginId;

   @NotBlank(message = "pchsDestination.cant.be.blank", groups = ContainerMovementValidationGroup.class)
   @Length(max = 10, message = "pchsDestination.cant.be.more.than", groups = ContainerMovementValidationGroup.class)
   @JacksonXmlProperty(localName = "pchsDestination")
   private String pchsDestination;
}
