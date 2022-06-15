package com.ngen.cosys.mailbag.overview.information.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class MailbagCorrectionResponse extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String incomingFlight;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate incomingFlightDate;
	private String breakdownUld;
	private String storeLocation;
	private String paFlight;
	private String incomingFlightDate1;
	
	private String outgoingFlight;
	private String outgoingFlightDate;
	private String manifestedUldTrolley;
	private String xrayResult;
	private String emabrgo;
	private String damage;
	private String remark;
	
	
	// capture Damage
	private String mailbagNo;
	private String piecesDamaged;
	private String captureDamageRemark;
	
	// List
	
	private String natureOfDamage;
	private String piecesDamageds;
	private String severity;
	private String occurence;
	private String emailTo;
	
	
}
