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
public class ShipmentIdentifierDetails { 
	private String bookingDetailsId;
	private String shipmentIdentifierDetailsId;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private int sequenceNumber;
	private int duplicateNumber;
	private String ownerCode;
	private boolean isNonStandard;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
