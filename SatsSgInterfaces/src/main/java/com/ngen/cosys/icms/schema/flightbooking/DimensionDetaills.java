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
public class DimensionDetaills extends BaseBO {
	private String dimensionDetaillsId;
	private String bookingCommodityDetailsId;
	private int dimensionSerialNumber;
	private double lengthPerPiece;
	private double displayLengthPerPiece;
	private double heightPerpiece;
	private double displayHeightPerpiece;
	private double widthPerPiece;
	private double displayWidthPerPiece;
	private int numberOfPieces;
	private double volume;
	private double displayVolume;
	private double volumeThreeDecimal;
	private double weight;
	private double displayWeight;
	private String tiltable;
	private String createdUserId = BookingConstant.CREATEDBY;
	private String modifiedUserId = BookingConstant.CREATEDBY;
}
