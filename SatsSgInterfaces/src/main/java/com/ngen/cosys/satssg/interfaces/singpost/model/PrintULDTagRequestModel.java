package com.ngen.cosys.satssg.interfaces.singpost.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.JsonSerializer.InterfaceLocalDateFormatSerializer;
import com.ngen.cosys.ics.model.PrintULDTagList;
import com.ngen.cosys.ics.model.PrintULDTagSubList;
import com.ngen.cosys.validation.groups.ContainerExitLaneValidationGroup;

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
@JsonPropertyOrder(value = { "containerId", "workstationId", "outgoingFlightCarrier", "outgoingFlightNumber", "outgoingFlightDate", "outgoingFlightDate", "STDTime", "offPoint", "uldNetWeight", "uldTareWeight", "uldGrossWeight", "contentCode",
      "dgClassList", "dgSubClassList", "uldNog", "staffLoginId" })
@JacksonXmlRootElement(localName = "printULDTagRequest")
@JsonIgnoreProperties(value = {"printertype","content","id"})
public class PrintULDTagRequestModel {

   @NotBlank(message = "container.id.cant.blank", groups = ContainerExitLaneValidationGroup.class)
   @Length(max = 11, message = "container.id.cant.be.more.than", groups = { ContainerExitLaneValidationGroup.class })
   @JacksonXmlProperty(localName = "containerId")
   private String containerId;

   @NotBlank(message = "staffLoginId.cant.be.blank", groups = ContainerExitLaneValidationGroup.class)
   @Length(max = 10, message = "staff.login.id.cant.be.more.than", groups = { ContainerExitLaneValidationGroup.class })
   @JacksonXmlProperty(localName = "staffLoginId")
   private String staffLoginId;

   @NotBlank(message = "workstationId.cant.be.blank", groups = ContainerExitLaneValidationGroup.class)
   @Length(max = 10, message = "work.station.id.cant.be.more.than", groups = { ContainerExitLaneValidationGroup.class })
   @JacksonXmlProperty(localName = "workstationId")
   private String workstationId;

   @NotBlank(message = "outgoingflightcarrier.cant.be.blank", groups = ContainerExitLaneValidationGroup.class)
   @Length(max = 3, message = "outgoingflightcarrier.cant.be.more.than", groups = { ContainerExitLaneValidationGroup.class })
   @JacksonXmlProperty(localName = "outgoingFlightCarrier")
   private String outgoingFlightCarrier;

   @JacksonXmlProperty(localName = "outgoingFlightNumber")
   private String outgoingFlightNumber;

   @JsonSerialize(using = InterfaceLocalDateFormatSerializer.class)
   @JacksonXmlProperty(localName = "outgoingFlightDate")
   private LocalDate outgoingFlightDate;

   @JacksonXmlProperty(localName = "STDTime")
   private String stdTime;

   @JacksonXmlProperty(localName = "XPSFlag")
   private String xpsFlag;

   @NotBlank(message = "offpoint.cant.be.blank", groups = ContainerExitLaneValidationGroup.class)
   @Length(max = 8, message = "offpoint.cant.be.more.than", groups = { ContainerExitLaneValidationGroup.class })
   @JacksonXmlProperty(localName = "offPoint")
   private String offPoint;

   @JacksonXmlProperty(localName = "uldNetWeight")
   private String uldNetWeight;

   @JacksonXmlProperty(localName = "uldTareWeight")
   private String uldTareWeight;

   @JacksonXmlProperty(localName = "uldGrossWeight")
   private String uldGrossWeight;

   @NotBlank(message = "contentcode.cant.be.blank", groups = ContainerExitLaneValidationGroup.class)
   @Length(max = 1, message = "contentcode.cant.be.more.than", groups = { ContainerExitLaneValidationGroup.class })
   @JacksonXmlProperty(localName = "contentCode")
   private String contentCode;
   
   private String content;

   @JacksonXmlProperty(localName = "dgClassList")
   @JacksonXmlElementWrapper(useWrapping = true)
   private List<PrintULDTagList> dgClassList;

   @JacksonXmlProperty(localName = "dgSubClassList")
   @JacksonXmlElementWrapper(useWrapping = true)
   private List<PrintULDTagSubList> dgSubClassList;

   @JacksonXmlProperty(localName = "uldNog")
   private String uldNog;
   
   private String printertype;
      
   private BigInteger id;
}