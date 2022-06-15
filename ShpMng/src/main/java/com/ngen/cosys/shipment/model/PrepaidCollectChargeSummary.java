/**
 * 
 * PrepaidCollectChargeSummary.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          22 January , 2018    NIIT      -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.Size;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- PrepaidCollectChargeSummary
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class PrepaidCollectChargeSummary extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	private Long shipmentFreightWayBillId;
	private Long shipmentFreightWayBillPPDCOLId;
	private BigInteger neutralAWBId;
	private BigInteger neutralAWBPPDCOLId;
	private String chargeTypeLineIdentifier;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_VALUATION_CHARGES)
	private BigDecimal valuationChargeAmount;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_WEIGHT_CHARGE)
	private BigDecimal totalWeightChargeAmount;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_TAXES)
	private BigDecimal taxesChargeAmount;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_TOTAL_CH_AGENT)
	private BigDecimal totalOtherChargesDueAgentChargeAmount;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OTH_TOTAL_CH_CARRIER)
	private BigDecimal totalOtherChargesDueCarrierChargeAmount;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGE_SUUMERT_TOTAL)
	private BigDecimal chargeSummaryTotalChargeAmount;
	@Size(max=2,message = "g.max.2.char")
	@NgenAuditField(fieldName = "totalWeightChargeIdentifier")
	private String totalWeightChargeIdentifier;
	@NgenAuditField(fieldName = "valuationChargeIdentifier")
	@Size(max=2,message = "g.max.2.char")
	private String valuationChargeIdentifier;
	@NgenAuditField(fieldName = "taxesChargeIdentifier")
	@Size(max=2,message = "g.max.2.char")
	private String taxesChargeIdentifier;
	@NgenAuditField(fieldName = "totalOtherChargesDueAgentChargeIdentifier")
	@Size(max=2,message = "g.max.2.char")
	private String totalOtherChargesDueAgentChargeIdentifier;
	@NgenAuditField(fieldName = "totalOtherChargesDueCarrierChargeIdentifier")
	@Size(max=2,message = "g.max.2.char")
	private String totalOtherChargesDueCarrierChargeIdentifier;
	@NgenAuditField(fieldName = "chargeSummaryTotalChargeIdentifier")
	@Size(max=2,message = "Max 2 charater")
	private String chargeSummaryTotalChargeIdentifier;
	@NgenAuditField(fieldName = "otherChargeIndicator")
	private String otherChargeIndicator;
}
