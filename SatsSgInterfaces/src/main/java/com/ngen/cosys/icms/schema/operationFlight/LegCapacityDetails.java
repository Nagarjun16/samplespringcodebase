package com.ngen.cosys.icms.schema.operationFlight;

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
 * Model class contains leg capacity details
 */

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class LegCapacityDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	private TotalLegCapacity totalLegCapacity;
	@Valid
	@NotNull(message="Aircraft details cannot be null",groups = xmlValidator.class)
	private AirCraftdetails aircraftDetails;

}
