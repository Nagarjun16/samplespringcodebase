package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains total leg capacity details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class TotalLegCapacity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String weight;
	private String volume;
	private String lowerdeckone;
	private String lowerdeckTwo;
	private String upperDeckOne;
	private String upperDeckTwo;
	private String overBookingWeight;
	private String overBookingVolume;

}
