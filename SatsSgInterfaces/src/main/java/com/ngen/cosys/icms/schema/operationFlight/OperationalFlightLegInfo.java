package com.ngen.cosys.icms.schema.operationFlight;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class OperationalFlightLegInfo extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String legOrigin;
	private String legDestination;
	private String dateSTA;
	private String STAUTC;
	private String dateSTD;
	private String STDUTC;
	private String dateETD;
	private String aircraftType;
	private String serviceType;
	private BigInteger flightId;
	private int flightSegmentOrder;
	private String domesticStatus;
	private String aircraftRegistrationNo;
	private Boolean isDeleteLeg;
	

}
