package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.schema.scheduleFlight.PublishHeader;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class contains operational flight publish details
 */

@XmlRootElement(name = "operationalFlightPublish", namespace = "http://www.ibsplc.com/icargo/services/types/FlightOperationServiceTypes/standard/2012/12/12_01")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Component
public class OperationalFlightPublish implements Serializable{
	private static final long serialVersionUID = 1L;
	@Valid
	@XmlElement(name = "publishHeader")
	private PublishHeader objHeader;
	@Valid
	@XmlElement(name = "publishEntity")
	private PublishOperationEntity objEntity;
}
