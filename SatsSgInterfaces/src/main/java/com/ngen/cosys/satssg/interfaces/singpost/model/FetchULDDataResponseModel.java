package com.ngen.cosys.satssg.interfaces.singpost.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FetchULDDataResponseModel {
	
	private static final long serialVersionUID = 1L;
	
	@JacksonXmlProperty(localName ="status")
	private String status;

	@JsonProperty("containerId")
	private String containerId;

	@JacksonXmlProperty(localName ="outgoingFlightCarrier")
	private String outgoingFlightCarrier;
	
	@JacksonXmlProperty(localName ="outgoingFlightNumber")
    private String outgoingFlightNumber;
	
	@JacksonXmlProperty(localName ="outgoingFlightDate")
	private Date outgoingFlightDate;
	
	@JacksonXmlProperty(localName ="STDTime")
	private long STDTime;
	
	@JacksonXmlProperty(localName ="XPSFlag")
	private String	XPSFlag;
	
	@JacksonXmlProperty(localName ="offPoint")
	private String offPoint;
	
	@JacksonXmlProperty(localName ="uldNetWeight")
	private float uldNetWeight;
	
	@JacksonXmlProperty(localName ="uldTareWeight")
	private float uldTareWeight;
	
	@JacksonXmlProperty(localName ="uldGrossWeight")
	private float uldGrossWeight;
	
	@JacksonXmlProperty(localName ="contentCode")
	private String contentCode;
	
	@JacksonXmlProperty(localName ="dgClassList")
	private String dgClassList;
	
	@JacksonXmlProperty(localName ="dgSubClassList")
	private String dgSubClassList;
	
	@JacksonXmlProperty(localName ="errorNumber")
	private String errorNumber;
	
	@JacksonXmlProperty(localName ="errorDiscription")
	private String errorDiscription;
}
