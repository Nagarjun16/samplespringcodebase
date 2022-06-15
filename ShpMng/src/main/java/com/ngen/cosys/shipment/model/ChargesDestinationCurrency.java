package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;

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

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.MAINTAIN_FWB, repository = NgenAuditEventRepository.AWB)
public class ChargesDestinationCurrency extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillCCChargesId;

   @NgenAuditField(fieldName = "destinationCountryCode")
   private String destinationCountryCode;

   @NgenAuditField(fieldName = "currencyConversionExchangeRate")
   private BigDecimal currencyConversionExchangeRate;

   @NgenAuditField(fieldName = "destinationCurrencyChargeAmount")
   private BigDecimal destinationCurrencyChargeAmount;

   @NgenAuditField(fieldName = "chargesAtDestinationChargeAmount")
   private BigDecimal chargesAtDestinationChargeAmount;

   @NgenAuditField(fieldName = "totalCollectChargesChargeAmount")
   private BigDecimal totalCollectChargesChargeAmount;

   @NgenAuditField(fieldName = "destinationCurrencyCode")
   private String destinationCurrencyCode;

}