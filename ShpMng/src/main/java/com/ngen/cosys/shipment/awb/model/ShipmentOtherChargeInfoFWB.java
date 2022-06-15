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
public class ShipmentOtherChargeInfoFWB extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   
   private BigInteger shipmentOtherChargesId;

   @NgenAuditField(fieldName = "Customs Origin")
   private String customsOrigin;

   @NgenAuditField(fieldName = "Charge Code")
   private String chargeCode;

   @NgenAuditField(fieldName = "Currency")
   private String currency;

   @NgenAuditField(fieldName = "Collect Bank Endorsement Clearance Letter")
   private boolean collectBankEndorsementClearanceLetter;

   @NgenAuditField(fieldName = "Due From Airline")
   private String dueFromAirline;

   @NgenAuditField(fieldName = "Due From Agent")
   private String dueFromAgent;

   @NgenAuditField(fieldName = "Freight Charges")
   private String freightCharges;

   @NgenAuditField(fieldName = "Exchange Rate")
   private BigDecimal exchangeRate;

   @NgenAuditField(fieldName = "Total Collect Charges Charge Amount")
   private String totalCollectChargesChargeAmount;

   @NgenAuditField(fieldName = "Destination Currency Charge Amount")
   private String destinationCurrencyChargeAmount;

   @NgenAuditField(fieldName = "CC Fee")
   private BigDecimal ccFee;

   @NgenAuditField(fieldName = "Total")
   private BigDecimal total;

   @NgenAuditField(fieldName = "Valuation Charges")
   private String valuationCharges;

   @NgenAuditField(fieldName = "TAX")
   private String tax;
}
