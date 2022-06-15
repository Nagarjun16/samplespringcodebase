package com.ngen.cosys.inward.model;

import java.math.BigInteger;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
public class DamageModel extends BaseBO{
	 private BigInteger flightid;
	 private String shipmentNumber;
	 private String remarks;
	 private String content;
	 private BigInteger damagedPieces;
	 private String weatherCondition;
	 private String severity;
	 private String occurence;
	 private String NatureOfDamageBooleancrushed;
	 private String NatureOfDamageBooleanseam;
	 private String NatureOfDamageBooleanpuncture;
	 private String NatureOfDamageBooleantorn;
	 private String NatureOfDamageBooleanwet;
	 private String NatureOfDamageBooleanforeigntaping;
	 
	 private String NatureOfDamageBooleanothers;

}
