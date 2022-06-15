package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains flight schedule publish details
 */
@XmlRootElement(name = "flightSchedulePublish", namespace = "http://www.ibsplc.com/icargo/services/types/FlightScheduleService/standard/2012/12/12_01")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Component
public class FlightSchedulePublish implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @Valid
	@NotNull(message="Publish headaer cannot be null",groups = xmlValidator.class)
	@XmlElement(name = "publishHeader")
	private PublishHeader objHeader;
	@Valid
	@NotNull(message="Publish entity cannot be null",groups = xmlValidator.class)
	@XmlElement(name = "publishEntity")
	private PublishEntity objEntity;

}
