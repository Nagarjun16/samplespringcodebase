package com.ngen.cosys.impbd.shipment.inventory.constant;

public enum ShipmentInventorySqlId {

	SQL_CHECK_INVENTORY("sqlCheckShipmentInventory"),

	SQL_INSERT_INVENTORY("sqlInsertShipmentInventory"),

	SQL_UPDATE_PIECE_WEIGHT("sqlUpdateShipmentInventoryPieceWeight"),
	
	SQL_UPDATE_INVENTORY("sqlUpdateShipmentInventoryBreakDown"),
	
	SQL_DELETE_INVENTORY("deleteShipmentinventoryLineItem"),

	SQL_CHECK_SHC("sqlCheckShipmentInventoryShc"),

	SQL_DELETE_SHC("deleteShipmentInventorySHCLineItem"),
	
	SQL_INSERT_SHC("sqlInsertShipmentInventoryShc"),

	SQL_CHECK_HOUSE("sqlCheckShipmentInventoryHouse"), SQL_INSERT_HOUSE(
			"sqlInsertShipmentInventoryHouse"), SQL_UPDATE_HOUSE_PIECE_WEIGHT(
					"sqlUpdateShipmentInventoryHousePieceWeight"),

	SQL_INSERT_HAWB_FOR_SHIPMENT("sqlInsertShipmentHAWBinfo"),
	
	SQL_GET_SHIPMENTHOUSEINFO("sqlGetShipmentMasterHouseInformation"), SQL_CREATE_SHIPMENTHOUSEINFO(
			"sqlInsertShipmentMasterHouseInformation"), SQL_UPDATE_SHIPMENTHOUSEINFO(
					"sqlUpdateShipmentMasterHouseInformation");
	

	private final String queryId;

	ShipmentInventorySqlId(String queryId) {
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