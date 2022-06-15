package com.ngen.cosys.billing.chargepost.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

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
entityFieldName = "finSysChargeCode", 
entityRefFieldName = "customerId", 
eventName = NgenAuditEventType.GENERATE_SD_BILL,
repository = NgenAuditEventRepository.BILLING
)
@NgenAudit(entityType = NgenAuditEntityType.CUSTOMER, 
entityFieldName = "finSysChargeCode", 
entityRefFieldName = "customerId", 
eventName = NgenAuditEventType.GENERATE_AP_BILL,
repository = NgenAuditEventRepository.BILLING
)
public class BillEntryBO extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Long billEntryId;

   private Long billGenId;

   @NgenAuditField(fieldName = "Customer Id")
   private Long customerId;

   private Long chargeCodeId;

   private Long groupChargeCodeId;

   @NgenAuditField(fieldName = "Apportioned Charge TotalAmount")
   private BigDecimal apportionedChargeTotalAmount;

   @NgenAuditField(fieldName = "Apportioned DiscountAmount")
   private BigDecimal apportionedDiscountAmount;

   @NgenAuditField(fieldName = "Apportioned Amount")
   private BigDecimal apportionedAmount;

   @NgenAuditField(fieldName = "Apportioning Percentage")
   private BigDecimal apportioningPercentage;

   @NgenAuditField(fieldName = "Posting Status")
   private Boolean postingStatus;

   private String createdUserCode;

   private LocalDateTime createdDateTime;

   private String lastUpdatedUserCode;

   private LocalDateTime lastUpdatedDateTime;

   @NgenAuditField(fieldName = "Apportioned Quantity")
   private BigDecimal apportionedQuantity;

   @NgenAuditField(fieldName = "Apportioned Quantity Weight")
   private BigDecimal apportionedQuantityWeight;

   @NgenAuditField(fieldName = "Apportioned Quantity NetWeight")
   private BigDecimal apportionedQuantityNetWeight;

   @NgenAuditField(fieldName = "finSys InvoiceNumber")
   private String finSysInvoiceNumber;

   @NgenAuditField(fieldName = "FinSys ChargeCode")
   private String finSysChargeCode;

   @NgenAuditField(fieldName = "BillingNo")
   private Long billingNo;

   @NgenAuditField(fieldName = "Pricing Type")
   private String pricingType;

   @NgenAuditField(fieldName = "ChargeEntry Id")
   private String chargeEntryId;

   @NgenAuditField(fieldName = "ChargeEntry Ids")
   private List<String> chargeEntryIds;

   @NgenAuditField(fieldName = "BillPaid Amount")
   private List<String> billPaidAmount;
   
   @NgenAuditField(fieldName = "Subgroup Id")
   private BigInteger chargeSubGroupId;
}