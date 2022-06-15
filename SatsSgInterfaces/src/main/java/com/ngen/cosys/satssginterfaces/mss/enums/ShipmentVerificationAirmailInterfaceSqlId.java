package com.ngen.cosys.satssginterfaces.mss.enums;

public enum ShipmentVerificationAirmailInterfaceSqlId {
	SQL_GET_SHIPMENT_VERIFICATION("sqlGetShipmentVerification"), SQL_INSERT_SHIPMENT_VERIFICATION(
			"sqlInsertShipmentVerification"), SQL_UPDATE_SHIPMENT_VERIFICATION("sqlUpdateShipmentVerification"), 
	SQL_UPDATE_DG_CHECKLIST("sqlUpdateDgChecklist"), SQL_INSERT_DG_CHECKLIST("sqlInsertDgChecklist");

	private final String queryId;

	ShipmentVerificationAirmailInterfaceSqlId(String queryId) {
		this.queryId = queryId;
	}

	@Override
	public String toString() {
		return String.valueOf(this.queryId);
	}

}