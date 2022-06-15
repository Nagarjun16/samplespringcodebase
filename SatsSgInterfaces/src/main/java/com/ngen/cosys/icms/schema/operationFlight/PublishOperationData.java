package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains publish operational data details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class PublishOperationData implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @Valid
	private FlightOperation flightoperation;
}
