package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class contains flight control details
 */

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class FlightControl implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String flightControlStation;
	private String officeNumericCode;
}
