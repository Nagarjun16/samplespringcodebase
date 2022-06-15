package com.ngen.cosys.validator;

public enum AssignULDQuery {
	SQL_FOR_INSERT_ULD("insertULD"),
	SQL_FOR_DELETE_ULD("deleteULD"),
	SQL_FOR_UPDATE_ULD("updateULD"),
	SQL_FOR_INSERT_PIGGYBACKULD_LIST("insertPiggyBackULDList"),
	SQL_FOR_UPDATE_PIGGYBACKULD_LIST("updatePiggyBackULDList"),	
	SQL_FOR_DELETE_SOMEPIGGYBACKULD_LIST("deleteSomePiggyBackULDs"),
	SQL_FOR_DELETE_PIGGYBACKULD_LIST("deletePiggyBackULDList"),
	SQL_FOR_FETCH_FLIGHT("getFlightDetail"),
	SQL_CHECK_IF_ASSIGNULD_EXITS("sqlCheckIfAssignUldExists"),
	SQL_FOR_FETCH_ULD("fetchAssignULD"), 
	SQL_FOR_FETCH_FLIGHTID("getFlightID"),
	SQL_FOR_FETCH_TAREWEIGHT("getTareWeightForUld"),
	SQL_GET_CONTOUR_CODE("getContourCode"),
	SQL_GET_HEIGHT_CODE_FROM_ULDCHAR("getContourCodeULDChar"),
	SQL_CHECK_INVENTORY("fetchUldInventory"),
	SQL_FOR_INSERT_ULD_INVENTORY("insertULDInventory"),
	SQL_FOR_FETCH_PIGGYBACK_ULD("fetchPiggybackUldInventory"),
	SQL_FOR_FETCH_DLSID("fetchDLSId"),
	SQL_FOR_FETCH_AIRCRAFTBODYTYPE("getAircraftBodyType"),
	SQL_FOR_SEARCH_IN_ULDINVENTORY("searchInULDInventory"), 
	SQL_CHECK_IF_ASSIGNULD_BULK_EXISTS("sqlCheckIfAssignUldBulkExists"),
	SQL_GET_BULK_ULD_ID("getBulkUldID"),
	SQL_IS_PERISHABLE_CARGO("isPerishableCargo"),
	SQL_GET_CONTENT_CODE("getContentCode"),
	SQL_GET_DESTINATION_CODE("getDestinationCode"),
	SQL_IS_CONTENT_CODE_VALID("isContentCodeValid"),
	SQL_IS_DAMAGED("isDamaged"),
	SQL_IS_CARRIER_COMPATIBLE("isCarrierCompatible"),
	SQL_IS_EXCEPTION_ULD("isExceptionULD"),
	SQL_GET_ALI_INFORMATION("getAliInformation"),
	SQL_IS_ULD_LOADED("isUldLoaded"),
	SQ_GET_ACTUAL_GROSS_WEIGHT("getActualWeightForUld"),
	IS_ULD_TYPE_EXIST("isULDInUldMaster")
	;
	
	String queryId;
	AssignULDQuery(String queryId) {
		this.queryId = queryId;
	}

	public String getQueryId() {
		return queryId;
	}
}
