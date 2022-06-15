package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class FlightLegDetails extends BaseBO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull(message="Leg Origin cannot be null",groups = xmlValidator.class)
	@Size(min=3,max = 3, message = "Leg origin must contain 3 characters",groups = xmlValidator.class)
	private String legOrigin;
	
	@NotNull(message="Leg Destination cannot be null",groups = xmlValidator.class)
	@Size(min=3,max = 3, message = "Leg Destination must contain 3 characters",groups = xmlValidator.class)
	private String legDestination;
	
	@NotBlank(message="STA cannot be blank",groups = xmlValidator.class)
	private String STA;
	
	//@NotBlank(message="STAUTC cannot be blank",groups = xmlValidator.class)
	private String STAUTC;
	
	@NotBlank(message="STD cannot be blank",groups = xmlValidator.class)
	private String STD;
	
	//@NotBlank(message="STDUTC cannot be blank",groups = xmlValidator.class)
	private String STDUTC;
	@Valid
	private LegCapacityDetails legCapacityDetails;
	
	private String aircraftType;
	
	private String serviceType;
	
	private BigInteger flightId;
	

}
