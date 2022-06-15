package com.ngen.cosys.icms.schema.scheduleFlight;

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
 * Model class contains Flight schedule details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class FlightSchedule implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NotNull(message="Carrier code cannot be null",groups = xmlValidator.class)
	@Size(min = 2, max = 3, message = "carrier code must contain 2 or 3 characters",groups = xmlValidator.class)
	private String carrierCode;
	
	@NotBlank(message="Flight schedule number cannot be blank",groups = xmlValidator.class)
	@Size(max = 5, message = "Flight number should not exceed 5 characters",groups = xmlValidator.class)
	private String flightscheduleNumber;
	
	private String flightscheduleOrigin;
	
	private String flightscheduleDestination;
	
	@NotBlank(message="Flight schedule from cannot be blank",groups = xmlValidator.class)
	private String flightscheduleFromDate;
	
	private String flightscheduleRoute;
	
	@NotBlank(message="Flight schedule to cannot be blank",groups = xmlValidator.class)
	private String flightscheduleToDate;
	
	@NotBlank(message="Flight type cannot be blank or null",groups = xmlValidator.class)
	@Size(max = 5, message = "Flight type size should not exceed 5 characters",groups = xmlValidator.class)
	private String flightType;
	
	@NotBlank(message="Schedule type cannot be null or blank",groups = xmlValidator.class)
	@Size(max = 5, message = "Flight type size should not exceed 5 characters",groups = xmlValidator.class)
	private String scheduleType;
	
	@NotBlank(message="Flight schedule status cannot be blank",groups = xmlValidator.class)
	@Size(max = 10, message = "Flight schedule status should not exceed 10 characters",groups = xmlValidator.class)
	private String flightscheduleStatus;
	
	private FlightScheduleControl flightScheduleControl;
	
	private String flightOwner;
	
	private String flightscheduleRemarks;
	@Valid
	@NotNull(message="Flight leg details cannot be null",groups = xmlValidator.class)
	private List<FlightScheduleLegDetails> flightscheduleLegDetails;
	
	@Valid
	@NotNull(message="Flight segment details cannot be null",groups = xmlValidator.class)
	private List<FlightSegmentDetails> flightSegmentDetails;
	
	@NotBlank(message="Flight schedule frequency cannot be blank",groups = xmlValidator.class)
	@Size(max = 7, message = "Flight schedule frequency should not exceed 7 characters",groups = xmlValidator.class)
	private String flightScheduleFrequency;
	private String offerDisplayType;
	private String offerDisplayIndicator;	

}
