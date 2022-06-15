package com.ngen.cosys.satssginterfaces.mss.enums;

public enum InboundBreakDownAirmailInterfaceSqlId {
	GET_BREAK_DOWN_DATA("sqlGetInboundBreakdownData"),
	CHECK_BREAK_DOWN_STORAGE_INFO("sqlCheckBreakDownStorageInfo"),
	INSERT_BREAK_DOWN_STORAGE_INFO("sqlInsertBreakDownStorageInfo"),
	UPDATE_BREAK_DOWN_STORAGE_INFO("sqlUpdateBreakDownStorageInfo"),
	SELECT_BREAK_DOWN_STORAGE_INFO("sqlSelectBreakDownStorageInfo"),
	CHECK_BREAK_DOWN_HOUSE_INFO("sqlCheckBreakDownHouseInfo"),
	INSERT_BREAK_DOWN_HOUSE_INFO("sqlInsertBreakDownShipmentHouseInfo"),
	UPDATE_BREAK_DOWN_HOUSE_INFO("sqlUpdateBreakDownShipmentHouseInfo"),
	CHECK_BREAK_DOWN_STORAGE_SHC_INFO("sqlCheckBreakDownStorageSHCInfo"),
	INSERT_BREAK_DOWN_STORAGE_SHC_INFO("sqlInsertBreakDownStorageSHCInfo"),
	CHECK_BREAK_DOWN_SHIPMENT_IRREGULARITY("sqlCheckBreakDownShipmentIrregularity"),
	INSERT_BREAK_DOWN_SHIPMENT_IRREGULARITY("sqlInsertBreakDownShipmentIrregularity"),
	UPDATE_BREAK_DOWN_SHIPMENT_IRREGULARITY("sqlUpdateBreakDownShipmentIrregularity"),
	CHECK_BREAK_DOWN_ULD_TROLLEY_INFO("sqlCheckBreakDownULDTrolleyInfo"),
	INSERT_BREAK_DOWN_ULD_TROLLEY_INFO("sqlInsertBreakDownULDTrolleyInfo"),
	UPDATE_BREAK_DOWN_ULD_TROLLEY_INFO("sqlUpdateBreakDownULDTrolleyInfo"),
	SELECT_BREAK_DOWN_ULD_TROLLEY_INFO("sqlSelectBreakDownULDTrolleyInfo"),
	SELECT_HANDLING_INFO("sqlFetchHandlingInstructions"),
	UPDATE_BREAK_DOWN_SHC_INFO("sqlUpdateBreakDownShipmentSHCInfo"),
	FLIGHT_FINALIZED("sqlGetFlightFinalized"),
	ULD_CHECKED_IN("sqlGetULDCheckedIn"),
	CHECK_ULD_HANDOVER("sqlGetHandOverCheckIn"),
	CHECK_TRANSFER_TYPE("sqlGetTransferType"),
	CHECK_HANDLING_MODE("sqlGetULDBreak"),
	CHECKDOCUMENTCOMPLETED("sqlCheckDocumentVerificationCompleted"),
	GETIRREGULARITYINFO("getIrregularityInformation");
	

	private final String queryId;
	   
	InboundBreakDownAirmailInterfaceSqlId(String queryId) {
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
