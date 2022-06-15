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
public class ShipmentDetails { 
	private String shipmentDetailsId;
	private String bookingDetailsId;
	private String shipmentOrigin;
	private String shipmentDestination;
	private String shippingDate;
	private String agentCode;
	private String agentName;
	private String agentIataCode;
	private String bookingStation;
	private String customerCode;
	private String customerName;
	private String bookingSource;
	private String currency;
	private int totalNumberOfPieces;
	private double totalWeight;
	private double totalDisplayWeight;
	private double totalVolume;
	private double totalDisplayVolume;
	private double totalVolumeThreeDecimal;
	//private boolean isAWBDataCaptureDone;
	private String isAWBDataCaptureDone;
	private String lastUpdateUser;
	private String lastUpdateTime;
	private BookingShipperDetails bookingShipperDetails;
	private BookingConsigneeDetails bookingConsigneeDetails;
	private ProductDetails productDetails;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
