/**
 * 
 * PrepaidCollectCharge.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          24 November, 2017   NIIT      -
 */
package com.ngen.cosys.shipment.constant;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PrepaidCollectCharge {
	TOTALWEIGHTCHARGE("WT"), VALUATIONCHARGE("VC"), TAXES("TX"), TOTALOTHERCHARGESDUEAGENT(
			"OA"), TOTALOTHERCHARGESDUECARRIER("OC"), CHARGESUMMARYTOTAL("CT");

	private String typeOfPrepaidCollectCharge;

	PrepaidCollectCharge(String value) {
		this.typeOfPrepaidCollectCharge = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(this.typeOfPrepaidCollectCharge);
	}

	/**
	 * Returns the ENUM for the specified String.
	 * 
	 * @param value
	 * @return
	 */
	@JsonCreator
	public static PrepaidCollectCharge fromValue(String value) {
		for (PrepaidCollectCharge sType : values()) {
			if (sType.typeOfPrepaidCollectCharge.equalsIgnoreCase(value)) {
				return sType;
			}
		}
		throw new IllegalArgumentException(
				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	}

}
