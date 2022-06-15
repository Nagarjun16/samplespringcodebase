package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentMasterAirmailInterface extends ShipmentModelAirmailInterface {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = -9207013787317412804L;

	private String shipmentType;
	private String originOfficeExchange;
	private String mailCategory;
	private String mailSubCategory;
	private String destinationOfficeExchange;

	private BigInteger dispatchYear;

	private Boolean svc = Boolean.FALSE;
	private Boolean partShipment = Boolean.FALSE;
	private Boolean registered = Boolean.FALSE;
	private Boolean manuallyCreated = Boolean.FALSE;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime documentReceivedOn;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime documentPouchReceivedOn;

	private ShipmentMasterAirmailInterfaceCustomerInfo consignee;
	private ShipmentMasterAirmailInterfaceCustomerInfo shipper;
	private ShipmentOtherChargeInfoAirmailInterface otherChargeInfo;
	private ShipmentMasterAirmailInterfaceHandlingArea handlingArea;

	private List<ShipmentMasterAirmailInterfaceRoutingInfo> routing;
	private List<ShipmentMasterAirmailInterfaceShc> shcs;
	private List<ShipmentMasterAirmailInterfaceShcHandlingGroup> shcHandlingGroup;	
	private List<ShipmentRemarksAirmailInterfaceModel> remarks;

}