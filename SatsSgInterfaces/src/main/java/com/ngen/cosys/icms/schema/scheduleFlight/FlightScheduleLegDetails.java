package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains flight schedule leg details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class FlightScheduleLegDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NotNull(message="Leg Origin cannot be null",groups = xmlValidator.class)
	@Size(min=3,max = 3, message = "Leg origin must contain 3 characters",groups = xmlValidator.class)
	private String legOrigin;
	@NotNull(message="Leg Destination cannot be null",groups = xmlValidator.class)
	@Size(min=3,max = 3, message = "Leg Destination must contain 3 characters",groups = xmlValidator.class)
	private String legDestination;
	@NotBlank(message="STA cannot be blank",groups = xmlValidator.class)
	private String STA;
	@NotBlank(message="STD cannot be blank",groups = xmlValidator.class)
	private String STD;
	@NotBlank(message="STAUTC cannot be blank",groups = xmlValidator.class)
	private String STAUTC;
	@NotBlank(message="STDUTC cannot be blank",groups = xmlValidator.class)
	private String STDUTC;
	@Valid
	@NotNull(message="Leg capacity detail cannot be null",groups = xmlValidator.class)
	private LegCapacityDetailsType legCapacityDetailsType;
	@NotBlank(message="dayChangeAtOrigin cannot be blank",groups = xmlValidator.class)
	private String dayChangeAtOrigin;
	@NotBlank(message="dayChangeAtDestination cannot be blank",groups = xmlValidator.class)
	private String dayChangeAtDestination;
	

}
