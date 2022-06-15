package com.ngen.cosys.shipment.information.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DimensionDetails {
	private int lengthPerPiece;
	private int heightPerpiece;
	private int widthPerpiece;
	private int numberOfPieces;
	private double weight;
}
