package com.ngen.cosys.icms.schema.flightbooking;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.icms.util.BookingConstant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class ProductDetails { 
	private String productDetailsId;
	private String shipmentDetailsId;
	private String productName;
	private int productCode;
	private String productPriority;
	private String productTransportMode;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
