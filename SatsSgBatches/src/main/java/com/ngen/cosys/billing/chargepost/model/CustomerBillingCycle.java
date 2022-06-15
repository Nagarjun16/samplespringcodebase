/**
 * 
 * CustomerBillingCycle.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 19th December, 2019 NIIT -
 */
package com.ngen.cosys.billing.chargepost.model;

import java.math.BigInteger;

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

@Getter
@Setter
@ApiModel
@ToString
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.CUSTOMER_BILLING_SETUP, repository = NgenAuditEventRepository.BILLING, entityFieldName = "customerCode", entityRefFieldName = "customerId", entityType = NgenAuditEntityType.CUSTOMER)
public class CustomerBillingCycle extends BaseBO {
   /**
    * Default serialVersionUID
    */
   private static final long serialVersionUID = 1L;

   /**
    * 
    */
   @NgenAuditField(fieldName = "Cycle Id")
   private BigInteger billingCycleId;

   /**
    * 
    */
   @NgenAuditField(fieldName = "Cycle Serial No")
   private BigInteger cycleCount;

   /**
    * 
    */
   @NgenAuditField(fieldName = "Bill Run Day")
   private BigInteger billGenerationDay;

   /**
    * 
    */
   @NgenAuditField(fieldName = "Bill Post Day")
   private BigInteger postingDay;

   /**
    * 
    */
   @NgenAuditField(fieldName = "Type of Posting")
   private String postingType;

   /**
    * 
    */
   @NgenAuditField(fieldName = "Parent Customer Info Id")
   private BigInteger customerInfoId;
}
