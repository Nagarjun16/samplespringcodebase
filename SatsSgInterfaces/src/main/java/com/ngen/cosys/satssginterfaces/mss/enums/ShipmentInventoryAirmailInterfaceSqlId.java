package com.ngen.cosys.satssginterfaces.mss.enums;

public enum ShipmentInventoryAirmailInterfaceSqlId {

	SQL_CHECK_INVENTORY("sqlCheckShipmentInventory"),

	SQL_INSERT_INVENTORY("sqlInsertShipmentInventory"),

	SQL_UPDATE_PIECE_WEIGHT("sqlUpdateShipmentInventoryPieceWeight"),

	SQL_CHECK_SHC("sqlCheckShipmentInventoryShc"),

	SQL_INSERT_SHC("sqlInsertShipmentInventoryShc"),

	SQL_CHECK_HOUSE("sqlCheckShipmentInventoryHouse"), SQL_INSERT_HOUSE(
			"sqlInsertShipmentInventoryHouse"), SQL_UPDATE_HOUSE_PIECE_WEIGHT(
					"sqlUpdateShipmentInventoryHousePieceWeight"),

	SQL_GET_SHIPMENTHOUSEINFO("sqlGetShipmentMasterHouseInformation"), SQL_CREATE_SHIPMENTHOUSEINFO(
			"sqlInsertShipmentMasterHouseInformation"), SQL_UPDATE_SHIPMENTHOUSEINFO(
					"sqlUpdateShipmentMasterHouseInformation");

	private final String queryId;

	ShipmentInventoryAirmailInterfaceSqlId(String queryId) {
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