/**
 * 
 * ImportExportIndicator.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 February, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.constant;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ImportExportIndicator {
	EXPORT("EXPORT"), IMPORT("IMPORT"), TRANS("TRANS");

	private String type;

	ImportExportIndicator(String value) {
		this.type = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(this.type);
	}

	/**
	 * Returns the ENUM for the specified String.
	 * 
	 * @param value
	 * @return
	 */
	@JsonCreator
	public static ImportExportIndicator fromValue(String value) {
		for (ImportExportIndicator sType : values()) {
			if (sType.type.equalsIgnoreCase(value)) {
				return sType;
			}
		}
		throw new IllegalArgumentException(
				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	}
}