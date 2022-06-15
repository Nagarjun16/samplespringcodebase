package com.ngen.cosys.impbd.shipment.irregularity.constant;

public enum ShipmentIrregularityCodes {

	FDCA(Type.FDCA),

	MSCA(Type.MSCA),

	FDAW(Type.FDAW),

	MSAW(Type.MSAW),

	MSMB(Type.MSMB),

	FDMB(Type.FDMB);

	public class Type {
		public static final String FDCA = "FDCA";
		public static final String MSCA = "MSCA";
		public static final String FDAW = "FDAW";
		public static final String MSAW = "MSAW";
		public static final String FDMB = "FDMB";
		public static final String MSMB = "MSMB";

		private Type() {
			// Nothing to add
		}
	}

	String code;

	ShipmentIrregularityCodes(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}