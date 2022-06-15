package com.ngen.cosys.impbd.constants;

public enum CRUDTypes {

	CREATE(Type.CREATE),

	UPDATE(Type.UPDATE),

	DELETE(Type.DELETE);

	public class Type {

		public static final String CREATE = "C";
		public static final String UPDATE = "U";
		public static final String DELETE = "D";

		private Type() {
			// Do nothing
		}
	}

	private final String status;

	private CRUDTypes(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

}