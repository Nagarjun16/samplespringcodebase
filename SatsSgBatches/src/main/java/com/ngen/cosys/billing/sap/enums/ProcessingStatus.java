package com.ngen.cosys.billing.sap.enums;

public enum ProcessingStatus {

	IN_PROGRESS("PROGRESS"), SUCCESSFUL("SUCCESS"), FAILED("FAILED");

	private final String value;

	private ProcessingStatus(String value) {
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
