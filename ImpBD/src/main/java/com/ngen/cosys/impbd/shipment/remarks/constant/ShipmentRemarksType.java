package com.ngen.cosys.impbd.shipment.remarks.constant;

public enum ShipmentRemarksType {

	FLIGHT_DELAY(Type.DLY),

	SHIPMENT_OFFLOADED(Type.OFL),

	GENERAL(Type.GEN);

	public class Type {
		public static final String DLY = "DLY";
		public static final String OFL = "OFLD";
		public static final String GEN = "GEN";

		private Type() {
			// Nothing to add
		}
	}

	String type;

	ShipmentRemarksType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}