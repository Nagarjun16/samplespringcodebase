package com.ngen.cosys.billing.sap.enums;

public enum UserCode {

	SAP_INTERFACE("ICDSAPINTERFACE");

	private final String value;

	private UserCode(String value) {
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
