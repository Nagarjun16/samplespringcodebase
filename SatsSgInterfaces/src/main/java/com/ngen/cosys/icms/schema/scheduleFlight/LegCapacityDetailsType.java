package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.icms.schema.operationFlight.AirCraftdetails;
import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains leg capacity details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class LegCapacityDetailsType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private TotalCapacity totalCapacity;
	@Valid
	@NotNull(message="Aircraft Details cannot be null",groups = xmlValidator.class)
	private AirCraftdetails airCraftdetails;

}
