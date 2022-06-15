package com.ngen.cosys.ics.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.ngen.cosys.JsonSerializer.InterfaceLocalDateFormatSerializer;
import com.ngen.cosys.JsonSerializer.InterfaceLocalTimeWithoutSecondSerializer;

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

public class ICSOperativeFlightModel {
	

	   @JacksonXmlProperty(localName = "flightCarrier")
	   private String flightCarrier;

	   @JacksonXmlProperty(localName = "flightNumber")
	   private String flightNumber;

	   @JsonSerialize(using = InterfaceLocalDateFormatSerializer.class)
	   @JacksonXmlProperty(localName = "flightDate")
	   private LocalDate flightDate;

	   @JacksonXmlProperty(localName = "flightType")
	   private String flightType;

	   @JsonSerialize(using = InterfaceLocalTimeWithoutSecondSerializer.class)
	   @JacksonXmlProperty(localName = "flightTime")
	   private LocalTime flightTime;

	   @JacksonXmlProperty(localName = "boardPoint")
	   private String boardPoint;

	   @JacksonXmlProperty(localName = "offPoint2")
	   private String offPoint2;

	   @JacksonXmlProperty(localName = "offPoint3")
	   private String offPoint3;

	   @JacksonXmlProperty(localName = "offPoint4")
	   private String offPoint4;

	   @JacksonXmlProperty(localName = "offPoint5")
	   private String offPoint5;

	   @JacksonXmlProperty(localName = "offPoint6")
	   private String offPoint6;

	   @JsonIgnore
	   private String offPointsCSV;
	   
	   @JacksonXmlProperty(localName = "operationalDirection")
	   private String operationalDirection;

	}

