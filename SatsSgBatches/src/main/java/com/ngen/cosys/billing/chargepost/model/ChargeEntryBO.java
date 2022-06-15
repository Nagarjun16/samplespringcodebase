package com.ngen.cosys.billing.chargepost.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityType = NgenAuditEntityType.CUSTOMER, 
entityRefFieldName = "chargeEntryId", 
eventName = NgenAuditEventType.GENERATE_SD_BILL,
repository = NgenAuditEventRepository.BILLING
)
@NgenAudit(entityType = NgenAuditEntityType.CUSTOMER, 
entityRefFieldName = "chargeEntryId", 
eventName = NgenAuditEventType.GENERATE_AP_BILL,
repository = NgenAuditEventRepository.BILLING
)
public class ChargeEntryBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Integer billEntryId;

   @NgenAuditField(fieldName = "ChargeEntry Id")
   private Integer chargeEntryId;

   
   private String createdUserCode;

   private String lastUpdatedUserCode;

   @NgenAuditField(fieldName = "BillPaid Amount")
   private BigDecimal billPaidAmount;

   @NgenAuditField(fieldName = "Chargeable Quantity")
   private Integer chargeableQuantity;

   @NgenAuditField(fieldName = "Chargeable Duration")
   private Integer chargeableDuration;

   @NgenAuditField(fieldName = "Waived Amount")
   private BigDecimal waivedAmount;

   @NgenAuditField(fieldName = "WaivedUpdated Quantity")
   private Integer waivedUpdatedQuantity;

   @NgenAuditField(fieldName = "WaivedUpdated Duration")
   private Integer waivedUpdatedDuration;

   @NgenAuditField(fieldName = "Waived Percentage")
   private Integer waivedPercentage;
}