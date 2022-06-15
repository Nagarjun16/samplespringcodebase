package com.ngen.cosys.impbd.damage.model;

import java.math.BigInteger;

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
public class DamageReportMailDetails extends BaseBO {
   private BigInteger flightId;
   private String entityKey;
   private String entityType;
   private BigInteger damagePieces;
   private String severity;
   private String occurence;
   private Boolean NatureOfDamageBooleanalteredseals;
   private Boolean NatureOfDamageBooleanpuncture;
   private Boolean NatureOfDamageBooleantorn;
   private Boolean NatureOfDamageBooleanwet;
   private Boolean NatureOfDamageBooleanothers;
   private String remark;
   
}
