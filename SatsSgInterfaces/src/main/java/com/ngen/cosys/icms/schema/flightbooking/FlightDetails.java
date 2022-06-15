package com.ngen.cosys.icms.schema.flightbooking;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.icms.util.BookingConstant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class FlightDetails extends BaseBO{
	private String bookingDetailsId;
	private String awbNumber;
	private String flightCarrierCode;
	private String flightNumber;
	private String flightDate;
	private String segmentOrigin;
	private String segmentDestination;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
