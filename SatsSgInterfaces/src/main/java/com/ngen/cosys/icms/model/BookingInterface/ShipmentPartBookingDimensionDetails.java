/**
 * 
 * ShipmentPartBookingDimensionDetails.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          22 December, 2017    NIIT      -
 */
package com.ngen.cosys.icms.model.BookingInterface;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents ShipmentPartBookingDimensionDetails Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentPartBookingDimensionDetails extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private long partBookingId;
	private long flightBookingId;
	private int transactionSequenceNo;
	private int pieces;
	private double weight;
	private String weightUnit;
	private String weightUnitCode;
	private String unitCode;
	private String densityGroupCode;
	private String volumeUnitCode;
	private double volume;
	private int length;
    private int width;
	private int height;
	private String shipmentUnitCode;
	private boolean checkBoxFlag = false;
	private String flagUpdate = "Y";
	private long partBookingDimensionId;
	private long totalDimentionPieces;
	private double totalDimentionVolume;
	private String measurementUnitCode;
	private double totalDimentionVolumetricWeight;
	private Double tempVolume;
}
