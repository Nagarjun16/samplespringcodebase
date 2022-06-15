package com.ngen.cosys.billing.sap.enums;

public enum Indicator {
	INSERT("I"), UPDATE("U"), DELETE("D");

	private final String value;

	private Indicator(String value) {
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
