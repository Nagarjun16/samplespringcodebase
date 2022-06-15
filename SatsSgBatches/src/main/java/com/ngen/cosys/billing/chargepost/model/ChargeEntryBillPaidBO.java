package com.ngen.cosys.billing.chargepost.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

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
public class ChargeEntryBillPaidBO extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Long billEntryId;

   @NgenAuditField(fieldName = "ChargeEntry Id")
   private String chargeEntryId;

   private String createdUserCode;

   private LocalDateTime createdDateTime;

   private String lastUpdatedUserCode;

   private LocalDateTime lastUpdatedDateTime;

   @NgenAuditField(fieldName = "BillPaid Amount")
   private BigDecimal billPaidAmount;

   @NgenAuditField(fieldName = "Paid Type")
   private String paidType;
   // change in data type from integer
   @NgenAuditField(fieldName = "Chargeable Quantity")
   private BigDecimal chargeableQuantity;
   // change in data type from integer
   
   @NgenAuditField(fieldName = "Chargeable Duration")
   private BigInteger chargeableDuration;
   
   @NgenAuditField(fieldName = "Waived Amount")
   private BigDecimal waivedAmount;

   @NgenAuditField(fieldName = "WaivedUpdated Quantity")
   private Integer waivedUpdatedQuantity;

   @NgenAuditField(fieldName = "WaivedUpdated Duration")
   private Integer waivedUpdatedDuration;

   @NgenAuditField(fieldName = "Waived Percentage")
   private Integer waivedPercentage;

   private int count;

   private long paymentReceiptId;

   @NgenAuditField(fieldName = "ChargeEntryId ForCharge")
   private BigInteger chargeEntryIdForCharge;
}