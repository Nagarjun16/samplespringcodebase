package com.ngen.cosys.shipment.awb.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

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
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentOtherChargeInfo extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   
   private BigInteger shipmentOtherChargesId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMS_ORIGIN)
   private String customsOrigin;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGE_CODE)
   private String chargeCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CURRENCY)
   private String currency;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.COLLECT_BANK_ENDORSEMENT_CLEARANCE_LETTER)
   private boolean collectBankEndorsementClearanceLetter;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DUE_FROM_AIRLINE)
   private BigDecimal dueFromAirline;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DUE_FROM_AGENT)
   private BigDecimal dueFromAgent;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FREIGHT_CHARGES)
   private BigDecimal freightCharges;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.EXCHANGE_RATE)
   private BigDecimal exchangeRate;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TOTAL_COLLECT_CHARGES_CHARGE_AMOUNT)
   private String totalCollectChargesChargeAmount;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION_CURRENCY_CHARGE_AMOUNT)
   private String destinationCurrencyChargeAmount;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CC_FEE)
   private BigDecimal ccFee;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TOTAL)
   private BigDecimal total;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.VALUATION_CHARGES)
   private BigDecimal valuationCharges;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TAX)
   private BigDecimal tax;
}
