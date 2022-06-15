package com.ngen.cosys.impbd.damage.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

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
public class DamageReportAWBDetails extends BaseBO{
   @Getter
/**
    * entityKey is AWB /Mail Tag /ULD Number
    */
   private BigInteger flightId;
   @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED,shipmentNumberField="entityKey",shipmentTypeField="entityType") 
   private String entityKey;
   private String entityType;
   private BigInteger damagePieces;
   private String content;
   private String severity;
   private String occurence;
   private Boolean NatureOfDamageBooleancrushed;
   private Boolean NatureOfDamageBooleanseam;
   private Boolean NatureOfDamageBooleanpuncture;
   private Boolean NatureOfDamageBooleantorn;
   private Boolean NatureOfDamageBooleanwet;
   private Boolean NatureOfDamageBooleanforeigntaping;
   private Boolean NatureOfDamageBooleanothers;
   private String remark;
   private String HAWBNumber;
   
   


}
