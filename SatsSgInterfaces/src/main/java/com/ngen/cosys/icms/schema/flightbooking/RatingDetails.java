package com.ngen.cosys.icms.schema.flightbooking;

import java.util.Date;

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
public class RatingDetails { 
	private String ratingDetailsId;
	private String bookingCommodityDetailsId;
	private String rateClass;
	private double iataRate;
	private double iataRateInSystemCurrency;
	private double iataCharge;
	private double iataChargeInSystemCurrency;
	private Date totalTaxes;
	private Date totalTaxesInSystemCurrency;
	private Date marketRate;
	private Date marketRateInSystemCurrency;
	private double marketCharge;
	private double marketChargeInSystemCurrency;
	private double baseCharge;
	private double baseChargeInSystemCurrency;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
