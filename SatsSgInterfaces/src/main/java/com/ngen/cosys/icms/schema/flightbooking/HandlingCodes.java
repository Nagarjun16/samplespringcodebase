package com.ngen.cosys.icms.schema.flightbooking;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class HandlingCodes { 
	private String handlingCodesId;
	private String bookingCommodityDetailsId;
	private HandlingCode handlingCode;
}
