package com.ngen.cosys.shipment.terminaltoterminalhandover.model;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class HandoverTerminalShp extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8041714785806625623L;
	private String origin;
	private String shpNumber;
	private String destination;
	private Integer pieces;
	private Double weight;
	private Flight flight;
	private String loadingDetails;
	private Long shipmentId;
	private Long uldId;
	private String piecesweight;
	private String originDestination;

	public String getPeicesAndWeight() {
		String str = "";
		if (this.pieces != null) {
			str += "" + this.pieces;
		}
		if (this.weight != null) {
			str += " / " + this.weight;
		}
		return str;
	}

	public String getOrgDest() {
		String str = "";
		if (this.origin != null) {
			str += "" + this.origin;
		}
		if (this.destination != null) {
			str += " / " + this.destination;
		}
		return str;
	}
	
	

}
