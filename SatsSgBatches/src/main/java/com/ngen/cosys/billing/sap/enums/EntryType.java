package com.ngen.cosys.billing.sap.enums;

public enum EntryType {

	QUANTITY("Quantity"), RATE("Rate "), AMOUNT("Amount"),
	TOTAL_QUANTITY("Total Quantity"), TOTAL_RATE("Total Rate"), 
	TOTAL_AMOUNT("Total Amount"),GRAND_TOTAL("Grand Total");

	private final String value;

	private EntryType(String value) {
		this.value = value;
	}

	/**
	 * Value
	 * 
	 * @return Value
	 */
	public String value() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
