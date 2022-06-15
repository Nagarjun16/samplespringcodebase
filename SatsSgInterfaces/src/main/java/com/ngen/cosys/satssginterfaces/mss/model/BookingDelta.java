package com.ngen.cosys.satssginterfaces.mss.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents BookingDelta Request from UI.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@ApiModel
@Component
@Scope("prototype")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
// @NgenCosysAppAnnotation
public class BookingDelta extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private long bookingDeltaId;
	private int bookingVersion;
	private long flightId;
	private String flightBoardPoint;
	private String flightOffPoint;
	private String shipmentNumber;
	@JsonSerialize(using = LocalDateSerializer.class)
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	private LocalDate shipmentDate;
	private int bookingPieces;
	private double bookingWeight;
	private int throughTransitFlag;
	private String statusCode;
	private String bookingChanges;
	private String shc1;
	private String shc2;
	private String shc3;
	private String shc4;
	private String shc5;
	private String shc6;
	private String shc7;
	private String shc8;
	private String shc9;
	private String workingListRemarks;
	private String manifestRemarks;
	private String additionalRemarks;
	private String createdUserCode;
	private LocalDateTime createdDateTime;
	private String lastUpdatedUserCode;
	private LocalDateTime lastUpdatedDateTime;

}
