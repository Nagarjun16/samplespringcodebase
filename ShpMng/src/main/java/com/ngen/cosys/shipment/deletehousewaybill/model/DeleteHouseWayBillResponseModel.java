package com.ngen.cosys.shipment.deletehousewaybill.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.shipment.model.ShipmentInventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Validated
@NgenAudit(eventName = NgenAuditEventType.DELETE_HOUSE_WAY_BILL, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB Number", entityType = NgenAuditEntityType.AWB)
public class DeleteHouseWayBillResponseModel extends ShipmentInventory {

	/**
	 * System Generated Serial Verison id
	 */
	private static final long serialVersionUID = 6022541580654061914L;
	@NgenAuditField(fieldName = "Shipment Inventory Chargeable Weight")
	private BigDecimal shipmentInventoryChargeableWeight;
	private BigInteger shipmentInventoryHouseId;
	private int shipmentHouseId;
	private BigInteger shipmentInventoryHousePieces;
	private BigDecimal shipmentInventoryHouseWeight;
	@NgenAuditField(fieldName = "Shipment Inventory House Chargeable Weight")
	private BigDecimal shipmentInventoryHouseChargeableWeight;
	private BigInteger impBreakDownStorageInfoId;
	private BigInteger impShipmentVerificationId;
	private BigInteger impBreakDownULDTrolleyInfoId;
	private String deliveryIssuedOrNot;
	private String deliveryRequestIssuedOrNot;
	private BigInteger shipInvHouseIdSHC;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_NUMBER)
	private String hawbNumber;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_NUMBER)
	private String awbNumber;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HWB_REMARK)
	private String remarks;
	private BigInteger shpHseCstmrId;
	private BigInteger inboundFlightId;
	private BigInteger expHouseInfId;
	private BigInteger documentInfoid;
	private BigInteger houseWeignId;
	private BigInteger weighingId;
	private Boolean finalized = Boolean.FALSE;
	private Boolean arrivalRptSent = Boolean.FALSE;
	private BigInteger locationId;
	private BigInteger housePieces;
	private BigDecimal houseWeight;


}
