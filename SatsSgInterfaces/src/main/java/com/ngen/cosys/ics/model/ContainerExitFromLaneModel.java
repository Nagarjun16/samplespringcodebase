package com.ngen.cosys.ics.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.validation.groups.ContainerExitLaneValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JsonPropertyOrder(value = { "containerId", "destinationLane", "staffLoginId", "workstationId" })
@JacksonXmlRootElement(localName = "containerExitRequest")
public class ContainerExitFromLaneModel {

   @NotBlank(message = "container.id.cant.blank", groups = ContainerExitLaneValidationGroup.class)
   @Length(max = 20, message = "container.id.cant.be.more.than", groups = { ContainerExitLaneValidationGroup.class })
   @JacksonXmlProperty(localName = "containerId")
   private String containerId;

   @NotBlank(message = "destination.lane.cant.blank", groups = ContainerExitLaneValidationGroup.class)
   @Length(max = 10, message = "destination.lane.cant.be.more.than", groups = ContainerExitLaneValidationGroup.class)
   @JacksonXmlProperty(localName = "destinationLane")
   private String destinationLane;

   @Length(max = 10, message = "staff.login.id.cant.be.more.than", groups = ContainerExitLaneValidationGroup.class)
   @JacksonXmlProperty(localName = "staffLoginId")
   private String staffLoginId;

   @Length(max = 10, message = "work.station.id.cant.be.more.than", groups = ContainerExitLaneValidationGroup.class)
   @JacksonXmlProperty(localName = "workstationId")
   private String workstationId;

}
