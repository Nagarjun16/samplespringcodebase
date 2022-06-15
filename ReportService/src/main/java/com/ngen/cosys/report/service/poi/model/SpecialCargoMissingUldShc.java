package com.ngen.cosys.report.service.poi.model;

import java.math.BigInteger;
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
import lombok.ToString;

/**
 * List of shipments and detail for SpecialCargoMonitering Function
 *
 */
@ApiModel
@XmlRootElement
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class SpecialCargoMissingUldShc extends BaseBO {

	private static final long serialVersionUID = 1L;

	private String uldNumber;
	private String shc;
	private BigInteger bookingId;
	private BigInteger flightBookingId;
	private BigInteger partBookingId;
	private BigInteger flightPartBookingId;
	private String shipmentNumber;
	private LocalDate shipmentDate;
	private BigInteger shipmentId;
	private BigInteger segmentId;
	private BigInteger shipmentInventoryId;

	private String flightKey;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;
	private BigInteger flightId;
	
	//all missmatch
	private String dlsMismatchUld;
	private String dlsMismatchUldShc;
	private String uldTagMismatchUld;
	private String uldTagMismatchUldShc;
	private String notocMismatchUld;
	private String loadedMismatchUld;
	private String loadedMismatchUldShc;
	private String partsuffix;
	

}
