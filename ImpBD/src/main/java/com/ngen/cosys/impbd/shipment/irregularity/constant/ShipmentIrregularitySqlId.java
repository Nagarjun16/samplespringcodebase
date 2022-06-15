package com.ngen.cosys.impbd.shipment.irregularity.constant;

public enum ShipmentIrregularitySqlId {

	CREATE_SHIPMENT_IRREGULARITY("sqlInsertShipmentIrregularity"), GET_SHIPMENT_IRREGULARITY(
			"sqlGetShipmentIrregularity"), DELETE_SHIPMENT_IRREGULARITY(
					"sqlDeleteShipmentIrregularity"), CHECK_MANIFESTED_PIECE_MATCHES_FOR_SHIPMENT_HOUSE_BREAK_DOWN_IRREGULARITY(
							"sqlCheckShipmentManifestedPiecesMatchesWithHouseBreakDownIrregularityPieces");

	private final String queryId;

	ShipmentIrregularitySqlId(String queryId) {
		this.queryId = queryId;
	}

	public String getQueryId() {
		return this.queryId;
	}

	@Override
	public String toString() {
		return String.valueOf(this.queryId);
	}

}