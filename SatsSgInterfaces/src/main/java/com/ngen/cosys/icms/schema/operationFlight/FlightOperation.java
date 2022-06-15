package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;
import java.util.List;

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
 * Model class contains flight operation details
 */

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class FlightOperation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NotNull(message="Carrier Code cannot be null",groups = xmlValidator.class)
	@Size(min = 2, max = 3, message = "carrier code must contain 2 or 3 characters",groups = xmlValidator.class)
	private String carrierCode;
	
	@NotBlank(message="Flight Number cannot be blank",groups = xmlValidator.class)
	@Size(max = 5, message = "Flight number should not exceed 5 characters",groups = xmlValidator.class)
	private String flightNumber;
	
	private String flightOrigin;
	
	private String flightDestination;
	
	private String flightCreationDate;
	
	private String flightRoute;
	
	@NotBlank(message="Flight Date cannot be blank",groups = xmlValidator.class)
	private String flightDate;
	
	@NotBlank(message="Flight Type cannot be blank",groups = xmlValidator.class)
	@Size(max = 5, message = "Flight type should not exceed 5 characters",groups = xmlValidator.class)
	private String flightType;
	
	private String scheduleType;
	
	@NotBlank(message="Flight Status cannot be blank",groups = xmlValidator.class)
	@Size(max = 10, message = "Flight schedule status should not exceed 10 characters",groups = xmlValidator.class)
	private String flightStatus;
	
	private String flightOwner;
	
	private FlightControl flightControl;
	@Valid
	@NotNull(message="Flight leg details cannot be null",groups = xmlValidator.class)
	private List<FlightLegDetails> flightLegDetails;
	@Valid
	@NotNull(message="Flight segment details cannot be null",groups = xmlValidator.class)
	private List<FlightSegmentDetails> flightSegmentDetails;
	private String offerDisplayIndicator;
	
	private String flightRemarks;
	private String aircraftRegNo;
	private String dateSTA;
	private String dateSTD;
}
