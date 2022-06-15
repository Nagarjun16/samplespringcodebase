/**
 * This is a enumb for holding SQL query ids for Tracing
 */
package com.ngen.cosys.impbd.tracing.activity.constants;

public enum TracingActivityQueryIds {

	SQL_GET_SHIPMENT_SHC_FOR_TRACING_ACTIVITY("sqlGetShipmentShcInfoTracingActivity"),

	SQL_GET_FLIGHT_INFO_FOR_TRACING_ACTIVITY("sqlGetFlightInfoForTracingActivity"),

	SQL_GET_MAX_TRACING_CASE_NUMBER("sqlGetMaxTracingCaseNumber"),
	
	SQL_GET_INVENTORYINFO_FOR_TRACING_CASE_NUMBER("sqlGetInventoryInfoForTracing"),

	SQL_CHECK_TRACING_ACTIVITY_FOR_SHIPMENT_EXISTS("sqlCheckTracingActivityForShipmentExists"),

	SQL_INSERT_TRACING_ACTIVITY_FOR_SHIPMENT("sqlInsertTracingActivityForShipment"),

	SQL_INSERT_TRACING_ACTIVITY_FOR_SHIPMENT_SHC("sqlInsertTracingActivityForShipmentShc"),
	
	SQL_INSERT_INVENTORYINFO_FOR_TRACING_ACTIVITY("sqlInsertInventoryInfoForTracingActivity"),
	
	SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT("sqlDeleteTracingActivityForShipment"),
	
	SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT_SHC("sqlDeleteTracingActivityForShipmentSHC"),
	
	SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT_LOCATION("sqlDeleteTracingActivityForShipmentLocation"),
	
	SQL_GET_TRACING_ACTIVITY_FOR_SHIPMENT_ID("sqlGetTracingActivityCaseIdForShipment"),
	
	SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT_DIMENSION_INFO("sqlDeleteTracingActivityForShipmentDimensionInfo"),
	
	SQL_DELETE_TRACING_ACTIVITY_FOR_SHIPMENT_FOLLOWUP_ACTION("sqlDeleteTracingActivityForShipmentFollowupAction"),;

	private final String queryId;

	TracingActivityQueryIds(String queryId) {
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