package com.ngen.cosys.satssginterfaces.mss.enums;

public enum ManifestQuery {
	
	FETCH_LOAD_DETAILS ("fetchLoadDetails"),
	INSERT_MANIFEST ("insertManifest"),
	GET_MANIFEST_ID ("getManifestId"),
	INSERT_MANIFEST_CONNECTING_FLIGHT ("insertManifestConnectingFlight"),
	GET_MANIFEST_ULD_ID ("getManifestULDId"),
	INSERT_MANIFEST_ULD ("insertManifestULD"),
	GET_MANIFEST_SHIPMENT_ID("getManifestShipmentId"),
	UPDATE_MANIFEST_SHIPMENT("updateManifestShipment"),
	INSERT_MANIFEST_SHIPMENT ("insertManifestShipment"),
	DELETE_ALL_MANIFEST_SHC ("deleteAllManifestShipmentSHC"),
	DELETE_ALL_MANIFEST_HOUSE ("deleteAllManifestShipmentHouse"),
	INSERT_MANIFEST_SHC ("insertManifestShipmentSHC"),
	INSERT_MANIFEST_HOUSE ("insertManifestShipmentHouse"),
	GET_EVENT_STORE_DATA("getEventStoreData"),
	DELETE_MANIFEST ("deleteManifest"),
	DELETE_MANIFEST_CONNECTING_FLIGHT ("deleteManifestConnectingFlight"),
	DELETE_MANIFEST_ULD ("deleteManifestULD"),
	DELETE_MANIFEST_SHIPMENT ("deleteManifestShipment"),
	DELETE_MANIFEST_SHC ("deleteManifestShipmentSHC"),
	DELETE_MANIFEST_HOUSE ("deleteManifestShipmentHouse"),
	GET_MANIFEST_ULDS("getManifestULDS"),
	GET_MANIFEST_SHIPMENTS("getManifestShipments"),
    GET_SEPARATE_MANIFEST_VERSION("getSeparateManifestVersion"),
    GET_SUPPLEMENTARY_MANIFEST_VERSION("getSupplementaryManifestVersion"),
	DISPLAY_MANIFEST_DETAILS ("displayManifestDetails"),
	FETCH_SUPPLEMENTARY_LOAD_DETAILS ("fetchSupplementaryLoadDetails"), 
	UPDATE_FLIGHT_REGISTRATION("updateFlightRegistration"),
	UPDATE_CONNECTING_FLIGHT("updateManifestConnectingFlight"),
	NON_MANIFESTED_CODESHARE("nonManifestedCodeShare"),
	GET_CODESHARE_EXISTS("getCodeShareExists"), 
	SET_MANIFEST_CONTROL_FIRST_TIME("setManifestControlFirstTime"),
	SET_MANIFEST_CONTROL("setManifestControl"),
	GET_SHIPMENT_LOAD_NOT_EQUAL("getShipmentLoadNotEqual"),
	GET_NUMBER_OF_DGN_OR_EXP("getNumberOfDGNorExp"),
	GET_NUMBER_OF_HOUSE_UNEQUAL("getNumberOfHouseUnequal"),
	UPDATE_SEPARATE_MANIFEST_VERSION("updateSeparateManifestVersion"), 
	UPDATE_SUPPLEMENTARY_MANIFEST_VERSION("updateSupplementaryManifestVersion"), 
	CHECK_DAMAGED_ULD("checkDamagedULD"),
    GET_FLIGHT("getFlightId");
	
	String queryId;
	ManifestQuery(String queryId) {
		this.queryId = queryId;
	}

	public String getQueryId() {
		return queryId;
	}
}
