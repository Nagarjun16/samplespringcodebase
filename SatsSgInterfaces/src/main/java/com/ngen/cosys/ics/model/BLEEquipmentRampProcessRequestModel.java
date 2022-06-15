package com.ngen.cosys.ics.model;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.enums.EquipmentRampEnum;
import com.ngen.cosys.enums.InOutEnum;
import com.ngen.cosys.validation.groups.RampProcessValidationGroup;
import com.ngen.cosys.validator.EnumValidator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder(value = { "palletDolley", "eventTime", "stage", "moveType" })
@JacksonXmlRootElement(localName = "InOutPDRequest")
public class BLEEquipmentRampProcessRequestModel {

   @NotBlank(message = "pallet.dolley.cant.blank", groups = RampProcessValidationGroup.class)
   @Length(max = 6, min = 6, message = "pallet.dolley.should.be.max.character", groups = { RampProcessValidationGroup.class })
   @JacksonXmlProperty(localName = "palletDolley")
   private String palletDolley;

   //@Pattern(regexp = "", message = "Invalid Date Format", groups = { RampProcessValidationGroup.class })
   @JsonSerialize(using = LocalDateSerializer.class)
   @JacksonXmlProperty(localName = "eventTime")
   private LocalDateTime eventTime;

   @EnumValidator(enumClass = EquipmentRampEnum.class, ignoreCase = true, message = "pls.provide.a.valid.stage.type", groups = { RampProcessValidationGroup.class })
   @JacksonXmlProperty(localName = "stage")
   private String stage;

   @EnumValidator(enumClass = InOutEnum.class, ignoreCase = true, message = "pls.provide.a.valid.move.type", groups = { RampProcessValidationGroup.class })
   @JacksonXmlProperty(localName = "moveType")
   private String moveType;
   
   @JacksonXmlProperty(localName = "gate")
   @NotBlank(message = "gate.number.cant.be.blank", groups = RampProcessValidationGroup.class)
   /*@Length(max = 5, min = 5, message = "Gate number should be {max} character length", groups = { RampProcessValidationGroup.class })*/
   private String gate;

   @JsonIgnore
   private String tripInfoId;

   @JsonIgnore
   private String pdMasterId;

}
