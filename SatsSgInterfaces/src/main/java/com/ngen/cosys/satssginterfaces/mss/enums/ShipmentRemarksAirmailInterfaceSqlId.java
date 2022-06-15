package com.ngen.cosys.satssginterfaces.mss.enums;

public enum ShipmentRemarksAirmailInterfaceSqlId {

	INSERT_SHIPMENT_REMARKS("sqlInsertShipmentRemarks"), GET_SHIPMENT_REMARKS("sqlGetShipmentRemarks");

	private final String queryId;

	public String getQueryId() {
		return this.queryId;
	}

	ShipmentRemarksAirmailInterfaceSqlId(String queryId) {
		this.queryId = queryId;
	}

	@Override
	public String toString() {
		return String.valueOf(this.queryId);
	}

}