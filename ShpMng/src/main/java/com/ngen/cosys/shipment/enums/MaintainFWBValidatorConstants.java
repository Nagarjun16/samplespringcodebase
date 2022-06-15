package com.ngen.cosys.shipment.enums;

public enum MaintainFWBValidatorConstants {

	SSR(Type.SSR),

	OSI(Type.OSI),

	CREATE(Type.CREATE),

	UPDATE(Type.UPDATE),

	DELETE(Type.DELETE);

	public final class Type {

		public static final String SSR = "SSR";
		public static final String OSI = "OSI";
		public static final String CREATE = "C";
		public static final String UPDATE = "U";
		public static final String DELETE = "D";

		private Type() {
			// Do nothing
		}

	}

	private final String type;

	MaintainFWBValidatorConstants(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}