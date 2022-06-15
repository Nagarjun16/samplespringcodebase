/**
 * Enum for defining constants for shipment process type like Import/Export/Transhipment
 */
package com.ngen.cosys.shipment.enums;

public enum ShipmentProcessType {

	IMPORT(Type.IMPORT),
	
	EXPORT(Type.EXPORT),
	
	TRANSHIPMENT(Type.TRANSHIPMENT);
	
	public class Type{
		
		public static final String IMPORT = "IMPORT";
		public static final String EXPORT = "EXPORT";
		public static final String TRANSHIPMENT = "TRANSHIPMENT";
		
		private Type() {
			//Do nothing
		}		
	}
	
	String processType;
	
	public String getProcessType() {
		return this.processType;
	}
	
	private ShipmentProcessType(String processType) {
		this.processType = processType;
	}
	
}