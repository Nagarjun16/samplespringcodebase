package com.ngen.cosys.shipment.information.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShipmentAndBookingDetails {
	private String shipmentNumber;
	private LocalDate shipmentDate;
	private String origin;
	private String destination;
	private int pieces;
	private double grossWeight;
	private String natureOfGoodsDescription;
	private int partPieces;
	private double partWeight;
	private String flightKey;
	private LocalDate flightOriginDate;
	private String flightBoardPoint;
	private String flightOffPoint;
	private double volume;
	private String partBookingStatusCode;
	private String volumeUnitCode;
	private String weightUnitCode;
	private String partSuffix;

}
