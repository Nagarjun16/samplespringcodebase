/**
 * 
 * AdHocFlightCode.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          24 October, 2017   NIIT      -
 */
package com.ngen.cosys.flight.constant;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This is a constant file for AdHoc flight codes. 
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum AdHocFlightCode {
	ADHOC("A"),
	MANUAL("M"),
	TRUE("true"),
	FALSE("false"),
	NULL(null);
	
	private final String indicator;
	
	private AdHocFlightCode(String value) {
		this.indicator = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(this.indicator);
	}
	
	/**
	 * Returns the ENUM for the specified String.
	 * 
	 * @param value
	 * @return
	 */
	@JsonCreator 
	public static AdHocFlightCode fromValue(String value) {
		for (AdHocFlightCode ahFc : values()) {
			if (ahFc.indicator.equalsIgnoreCase(value)) {
				return ahFc;
			}
		}
		throw new IllegalArgumentException(
				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	}
}