package com.ngen.cosys.billing.chargepost.model;

import java.math.BigDecimal;
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
entityFieldName = "billGenId", 
entityRefFieldName = "customerId", 
eventName = NgenAuditEventType.GENERATE_SD_BILL,
repository = NgenAuditEventRepository.BILLING
)
@NgenAudit(entityType = NgenAuditEntityType.CUSTOMER, 
entityFieldName = "billGenId", 
entityRefFieldName = "customerId", 
eventName = NgenAuditEventType.GENERATE_AP_BILL,
repository = NgenAuditEventRepository.BILLING
)
public class BillChargeConsolidationBO extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Long billChargeConsolidationId;

   @NgenAuditField(fieldName = "billGenId")
   private Long billGenId;

   @NgenAuditField(fieldName = "CustomerId")
   private Long customerId;

   private Long chargeCodeId;

   private Long chargeGroupId;

   @NgenAuditField(fieldName = "Charge Total Amount")
   private BigDecimal chargeTotalAmount;

   @NgenAuditField(fieldName = "Discount Amount")
   private BigDecimal discountAmount;

   @NgenAuditField(fieldName = "Amount")
   private BigDecimal amount;

   private String createdUserCode;

   private LocalDateTime createdDateTime;

   private String lastUpdatedUserCode;

   private LocalDateTime lastUpdatedDateTime;
   
   @NgenAuditField(fieldName = "Percentage")
   private BigDecimal percentage;
}