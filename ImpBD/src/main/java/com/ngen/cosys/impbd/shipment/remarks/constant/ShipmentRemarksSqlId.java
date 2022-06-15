package com.ngen.cosys.impbd.shipment.remarks.constant;

public enum ShipmentRemarksSqlId {

	INSERT_SHIPMENT_REMARKS("sqlInsertShipmentRemarks"), GET_SHIPMENT_REMARKS("sqlGetShipmentRemarks");

	private final String queryId;

	public String getQueryId() {
		return this.queryId;
	}

	ShipmentRemarksSqlId(String queryId) {
		this.queryId = queryId;
	}

	@Override
	public String toString() {
		return String.valueOf(this.queryId);
	}

}