package com.ngen.cosys.satssginterfaces.mss.enums;

public enum UnloadShipmentQuery {

	SEARCH_FLIGHT("searchFlight"),
	SHIPMENT_INFO("getShpmtInfo"),
	SHIPMENT_ID("shipmentId"),
	REDUCE_LOADSHIPMENT("reduceLoadShpmt"),
	INSERT_SHIPMENT_INVENTORY("saveShpmtInventory"),
	UPDATE_SHIPMENT_INVENTORY("updateShpmtInventory"),
	UPDATE_LDSHPMT_HOUSEINFO("updateHouseInfo"),
	INVENTORY_LOACTION("inventoryLocations"),
	UPADTE_MANIFEST_SHIPMENT_INFO("updateManifestShpmtInfo"),
	DELETE_LOAD_SHIPMENT("deleteLoadShipment"),
	DELETE_ASSINGED_ULDTROLLEY("deleteAssingedULDTrolly"),
	DELETE_LOADSHIPMENT_SHCINFO("deleteLoadShipmentSHCInfo"),
	DELETE_MANIFEST_SHIPMENTINFO("deleteManifestShpmtInfo"),
	DELETE_MANIFEST_SHIPMENT_SHCS("deleteManifestShpmtShcs"),
	DELETE_MANIFEST_SHIPMENT_HOUSE_INFO("deleteManifestShpmtHouseInfo"),
	DELETE_MANIFEST_ULDINFO("deleteManifestULDInfo"),
	DELETE_MANIFEST_INFO("deleteManifestInfo"),
	UPDATE_DLSULD_TROLLEY("updateDLSULDTrolly"),
	DELETE_DLSULD_TROLLEY("deleteDLSULDTrolly"),
	DELETE_DLSULD_TROLLEY_SHCS("deleteDLSULDTrollySHCInfo"),
	DELETE_DLSULD_ACCESORRY_INFO("deleteDLSULDTrollyAccesorryInfo"),
	GET_DLS_ID("getDLSId"),
	DELETE_LOADSHIPMENT_INFO("deleteLoadShipmentHouseInfo"),
	INSERT_SHIPMENT_INVENTORY_SHCS("insertShipmentInventorySHCs"),
	GET_SHIPMENT_HOUSE_IDS("getShipmentHouseId"),
	CREATE_SHIPMENTINVENTORY_HOUSEINFO("createShipmentInventoryHouseInfo"),
	GET_SHIPMENTINVENTORY_ID("getShipmentInventoryId"),
	DELETE_DLS("deleteDLS"),
	DELETE_ULDTOFLIGHT_PIGGYBAG_INFO("assignedULDTrolleyToFlightPiggyBackInfo"),
	GET_ULD_COUNT("getUldCount"),
	VALIDATE_SHIPMENT_LOCATION("valiadateShipmentLocation"),
	HOUSEPIECE_COUNT("getLoadShipmentHousePieceCount"),
	VLD_SHC_LIST("validSHCList"),
	GET_FLIGHT_DETAILBY_ULD ("getFlightDetailsByULD"),
	GET_FLIGHT_DETAILBY_SHPMTNUM("getFlightDetailsByShipment"),
	GET_SEGMENT("getSegment"),
	GET_FLIGHT_DETAILS("searchFlightDetails"),
	IS_SHC_EXISTS("schExists"),
	BOOKED_PIECES_WEIGHT("bookedPiecesAndWeight"),
	DELETE_INVENTORY_FOR_FLIGHT("deleteInventoryForFlight"),
	UPDATE_INVENTORY_FOR_FLIGHT("updateInventoryforExistedFlight");

	String queryId;
	UnloadShipmentQuery(String queryId) {
		this.queryId = queryId;
	}

	public String getQueryId() {
		return queryId;
	}
}
