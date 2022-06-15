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
public class OtherChargeDetails extends BaseBO { 
	private String OtherChargeDetailsId;
	private String bookingDetailsId;
	private String otherChargeHeadCode;
	private String otherChargeHeadName;
	private double otherChargeAmount;
	private boolean dueCarrierFlag;
	private boolean dueAgentFlag;
	private double taxAmount;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
