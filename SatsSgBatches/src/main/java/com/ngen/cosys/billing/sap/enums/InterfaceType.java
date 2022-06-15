package com.ngen.cosys.billing.sap.enums;

public enum InterfaceType {

	INVOICE_AND_CREDIT_NOTE("INVOICE_AND_CREDIT_NOTE"), 
	MATERIAL_MASTER("MATERIAL_MASTER"), 
	CUSTOMER_MASTER("CUSTOMER_MASTER");

	private final String value;

	private InterfaceType(String value) {
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
