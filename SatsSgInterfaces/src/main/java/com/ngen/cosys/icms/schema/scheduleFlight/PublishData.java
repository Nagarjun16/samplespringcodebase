package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains publish data details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class PublishData implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @Valid
    @NotNull(message="Flight Schedule cannot be null",groups = xmlValidator.class)
	private FlightSchedule flightschedule;
}
