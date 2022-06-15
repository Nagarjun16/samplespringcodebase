package com.ngen.cosys.billing.chargepost.model;

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
entityFieldName = "billGenDate", 
entityRefFieldName = "customerId", 
eventName = NgenAuditEventType.GENERATE_SD_BILL,
repository = NgenAuditEventRepository.BILLING
)

@NgenAudit(entityType = NgenAuditEntityType.CUSTOMER,
entityFieldName = "billGenDate", 
eventName = NgenAuditEventType.GENERATE_AP_BILL,
repository = NgenAuditEventRepository.BILLING)

public class BillGenBO extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Long billGenId;

   @NgenAuditField(fieldName = "BillGen Date")
   private LocalDateTime billGenDate;

   @NgenAuditField(fieldName = "Customer Id")
   private Long customerId;

   @NgenAuditField(fieldName = "Consolidation Status")
   private Boolean consolidationStatus;

   @NgenAuditField(fieldName = "Posting Status")
   private Boolean postingStatus;

   private String createdUserCode;

   private LocalDateTime createdDateTime;

   private String lastUpdatedUserCode;

   private LocalDateTime lastUpdatedDateTime;

   @NgenAuditField(fieldName = "BillGenMonth No")
   private Integer billGenMonthNo;

   @NgenAuditField(fieldName = "BillGenDay No")
   private Integer billGenDayNo;

   @NgenAuditField(fieldName = "Service Type")
   private String serviceType;
}