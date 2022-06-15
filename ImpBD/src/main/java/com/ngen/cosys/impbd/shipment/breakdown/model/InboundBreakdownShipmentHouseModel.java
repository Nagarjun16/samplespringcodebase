package com.ngen.cosys.impbd.shipment.breakdown.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.shipment.breakdown.validator.InboundBreakDownValidationGroup;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.MAIL_BREAKDOWN, repository = NgenAuditEventRepository.MAILBAG, entityFieldName = "number", entityType = NgenAuditEntityType.MAILBAG)
public class InboundBreakdownShipmentHouseModel extends BaseBO {

	/**
	 * System generated default serial version id
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger shipmentId;
	private BigInteger shipmentInventoryId;
	private BigInteger shipmentHouseId;

	private String type;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MAILBAGNUMBER)
	private String number;

	@CheckPieceConstraint(type = MandatoryType.Type.NOTREQUIRED, groups = { InboundBreakDownValidationGroup.class })
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	private BigInteger pieces;

	@CheckPieceConstraint(type = MandatoryType.Type.NOTREQUIRED, groups = { InboundBreakDownValidationGroup.class })
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	private BigDecimal weight;

	// Mail Properties
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN_OFFICE_EXCHANGE)
	private String originOfficeExchange;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MAIL_CATEGORY)
	private String mailCategory;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MAIL_TYPE)
	private String mailType;
	private String mailSubType;	
	private BigInteger dispatchYear;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DISPATCH_NUMBER)
	private String dispatchNumber;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.RECEPTACLE_NUMBER)
	private String receptacleNumber;
	private Boolean lastBag = Boolean.FALSE;
	private Boolean registered = Boolean.FALSE;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION_OFFICE_EXCHANGE)
	private String destinationOfficeExchange;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.NEXT_DESTINATION)
	private String nextDestination;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.TRANSFERRE_CARRIER)
	private String transferCarrier;
	private String shipmentMailType;
	private boolean closedTransit;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTLOCATION)
	private String shipmentLocation;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WAREHOUSE_LOCAITON)
	private String warehouseLocation;
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUMBER)
	private String uldNumber;
	private BigDecimal formattedWeight;

}