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
public class BookingConsigneeDetails {
	private String bookingConsigneeDetailsId;
	private String shipmentDetailsId;
	private int customerCode;
	private String customerName;
	private String customerFirstAddress;
	private String customerCity;
	private String customerState;
	private String customerCountry;
	private int customerPostalCode;
	private int customerPhonenumber;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
