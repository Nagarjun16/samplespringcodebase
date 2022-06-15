package com.ngen.cosys.impbd.events.payload;

import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@Validated
public class DamageReportEvent {
	 private BigInteger flightId;
	 private String flightKey;
	 private String registration;
	 private String carrierCode;
	 private String createdBy;
	 private String emailAddress[];
	 private LocalDate flightDate;
	 private String shipmentNumber;
	 private String remarks;
	 private String content;
	 private BigInteger pieces;
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
