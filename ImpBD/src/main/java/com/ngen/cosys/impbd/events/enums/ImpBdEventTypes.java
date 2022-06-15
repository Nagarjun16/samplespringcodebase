package com.ngen.cosys.impbd.events.enums;

public enum ImpBdEventTypes {

	SINGAPORE_CUSTOMS_EVENT(Names.SINGAPORE_CUSTOMS_EVENT),

	EAW_SHIPMENTS_EVENT(Names.EAW_SHIPMENTS_EVENT),

	EAP_SHIPMENTS_EVENT(Names.EAP_SHIPMENTS_EVENT),

	GENEREAL_SHIPMENTS_EVENT(Names.GENEREAL_SHIPMENTS_EVENT),
	
	INWARD_SERVICE_EVENT(Names.INWARD_SERVICE_EVENT),
	
	DAMAGE_SERVICE_EVENT(Names.DAMAGE_SERVICE_EVENT);

	public class Names {

		public static final String SINGAPORE_CUSTOMS_EVENT = "SingaporeCustomsEvent";
		public static final String EAW_SHIPMENTS_EVENT = "EAWShipmentsEvent";
		public static final String EAP_SHIPMENTS_EVENT = "EAPShipmentsEvent";
		public static final String GENEREAL_SHIPMENTS_EVENT = "GenerealShipmentsEvent";
		public static final String INWARD_SERVICE_EVENT = "InwardReportEvent";
		
		public static final String DAMAGE_SERVICE_EVENT = "DamageReportEvent";
	}

	String name;

	ImpBdEventTypes(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
