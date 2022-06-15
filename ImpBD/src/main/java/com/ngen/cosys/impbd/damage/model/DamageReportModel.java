package com.ngen.cosys.impbd.damage.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

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
public class DamageReportModel extends BaseBO{
   private BigInteger flightId;
   private String flight;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate  flightDate;
   @NotNull(message = "data.required.mandatory")
   private String weatherCondition;
   /**
    * preparedBy is user name
    */
   private String preparedBy;
   private String damageCargoFinalizeBy;
   private String finalizeflag;
   private String status;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate damageCargoFinalizeAt;
   private List<DamageReportAWBDetails> listDamageReportAWBDetails;
   private List<DamageReportULDDetails> listDamageReportULDDetails;
   private List<DamageReportMailDetails> listDamageReportMailDetails;
   

}
