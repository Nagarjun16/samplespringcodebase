/**
 * Report Request Type Enums
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.report.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Report Request Type Enums
 */
public enum ReportRequestType {
	DOWNLOAD("download"), //
	INLINE("inline"), //
	PRINT("print"), //
	EMAIL("email");

	private String requestType;

	/**
	 * Initialize
	 * 
	 * @param requestType
	 *            requestType
	 */
	ReportRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Request Type
	 * 
	 * @return Request Type
	 */
	@JsonValue
	public String requestType() {
		return this.requestType;
	}

	/**
	 * Gets Enum Of Value
	 * 
	 * @param value
	 *            Value
	 * @return Enum
	 */
	public static ReportRequestType enumOf(String value) {
		ReportRequestType[] requestTypes = values();
		//
		for (ReportRequestType requestType : requestTypes) {
			if (requestType.requestType().equalsIgnoreCase(value)) {
				return requestType;
			}
		}
		//
		return null;
	}
}
