package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains flight schedule control details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class FlightScheduleControl implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String flightscheduleControlStation;
	private String officeNumericCode;

}
