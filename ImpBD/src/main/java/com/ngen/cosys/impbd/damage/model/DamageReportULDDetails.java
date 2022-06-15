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
public class DamageReportULDDetails extends BaseBO{
   private BigInteger flightId;
   private String entityKey;
   private String entityType;
   private String occurence;
   private String remark;
   private Boolean NatureOfDamageBooleanserusable;
   private Boolean NatureOfDamageBooleansernonusable;
   private Boolean NatureOfDamageBooleannonservicable;
   

}
