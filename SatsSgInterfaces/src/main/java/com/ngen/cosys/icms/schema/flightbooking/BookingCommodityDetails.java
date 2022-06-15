package com.ngen.cosys.icms.schema.flightbooking;



import java.util.List;

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
public class BookingCommodityDetails extends BaseBO{
	private String bookingCommodityDetailsId;
	private String bookingDetailsId;
	private int serialNumber;
	private String commodityCode;
	private int pieces;
	private double weight;
	private double displayWeight;
	private double volume;
	private double grossVolume;
	private double displayGrossVolume;
	private double volumeThreeDecimal;
	private String shipmentDescription;
	private String sccCode;
	private double chargeableWeight;
	private double displayChargableWeight;
	private HandlingCodes handlingCodes;
	private List<DimensionDetaills> dimensionDetaills;
	private RatingDetails ratingDetails;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
