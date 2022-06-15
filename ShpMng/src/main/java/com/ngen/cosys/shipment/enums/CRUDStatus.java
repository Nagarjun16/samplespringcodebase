package com.ngen.cosys.shipment.enums;

public enum CRUDStatus {

	CREATE(CRUDType.CREATE),

	UPDATE(CRUDType.UPDATE),

	DELETE(CRUDType.DELETE);

	String type;

	public class CRUDType {
		public static final String CREATE = "C";
		public static final String UPDATE = "U";
		public static final String DELETE = "D";

		private CRUDType() {
			// Nothing to add
		}
	}

	CRUDStatus(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}
