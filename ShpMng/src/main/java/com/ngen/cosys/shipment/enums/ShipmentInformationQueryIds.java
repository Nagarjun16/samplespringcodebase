/**
 * Enum for defining constants for shipment information queries
 */
package com.ngen.cosys.shipment.enums;

public enum ShipmentInformationQueryIds {

	SQL_DERIVE_PROCESS_TYPE("sqlDeriveShipmentProcessType");

	String queryId;

	public String getQueryId() {
		return this.queryId;
	}

	private ShipmentInformationQueryIds(String queryId) {
		this.queryId = queryId;
	}

}