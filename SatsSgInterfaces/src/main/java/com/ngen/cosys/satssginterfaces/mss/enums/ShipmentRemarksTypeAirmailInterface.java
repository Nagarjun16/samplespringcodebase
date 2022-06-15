package com.ngen.cosys.satssginterfaces.mss.enums;

public enum ShipmentRemarksTypeAirmailInterface {

	FLIGHT_DELAY(Type.DLY),

	SHIPMENT_OFFLOADED(Type.OFL),

	GENERAL(Type.GEN);

	public class Type {
		public static final String DLY = "DLY";
		public static final String OFL = "OFL";
		public static final String GEN = "GEN";

		private Type() {
			// Nothing to add
		}
	}

	String type;

	ShipmentRemarksTypeAirmailInterface(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}