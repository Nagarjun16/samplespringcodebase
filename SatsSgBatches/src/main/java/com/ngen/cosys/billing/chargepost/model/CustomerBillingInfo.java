/**
 * 
 * CustomerBillingInfo.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 6 April, 2018 NIIT -
 */
package com.ngen.cosys.billing.chargepost.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
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


/**
 * This Model contains information related to CustomerBillingInfo
 * 
 * @author NIIT Technologies Ltd
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@Component
@ToString
@NgenAudit(eventName = NgenAuditEventType.CUSTOMER_BILLING_SETUP, 
repository = NgenAuditEventRepository.BILLING, 
entityFieldName = "customerCode",
entityRefFieldName = "customerId",
entityType = NgenAuditEntityType.CUSTOMER)
public class CustomerBillingInfo extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * customerId of the customer
    */
   @NgenAuditField(fieldName = "Customer Id")
   private Long customerId;
   /**
    * customerInfoId of the customer which is customerSetupInfoId
    */
   
   private Long customerInfoId;
   /**
    * paymentType of customer
    */
   @NgenAuditField(fieldName = "Payment Type")
   private String paymentType;
   /**
    * Frequency of customer
    */
   @NgenAuditField(fieldName = "Billing Frequency")
   private String billingFrequency;
   /**
    * financeSystemIdentificationNumber of customer
    */
   @NgenAuditField(fieldName = "sap customer code")
   private String financeSystemIdentificationNumber;
   /**
    * nextBillingDate of customer
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = "Next Billing Date")
   private LocalDateTime nextBillingDate;
   /**
    * billingBuffer of customer
    */
   @NgenAuditField(fieldName = "Billing Buffer")
   private Short billingBuffer;
   /**
    * tells whether it is build by billedByGHA
    */
   @NgenAuditField(fieldName = "Billed By GHA")
   private Boolean billedByGHA;
   /**
    * tells whether it is build by billedByAirline
    */
   @NgenAuditField(fieldName = "Billed By Airline")
   private Boolean billedByAirline;
   /**
    * tells whether it is build sendESupportDocument is applicable or not
    */
   private Boolean sendESupportDocument;
   /**
    * tells whether customer is blacklisted
    */
   @NgenAuditField(fieldName = "Black Listed")
   private Boolean blackListed;
   /**
    * tells whether customer can acceptCargo
    */
   @NgenAuditField(fieldName = "Accept Cargo")
   private Boolean acceptCargo;
   /**
    * tells whether customer can deliverCargo
    */
   @NgenAuditField(fieldName = "Deliver Cargo")
   private Boolean deliverCargo;
   /**
    * amount which pending by customer
    */
   @NgenAuditField(fieldName = "Pending Amount")
   private BigDecimal pendingAmount;
   /**
    * advice to the staff
    */
   @NgenAuditField(fieldName = "Advice To Staff")
   private String adviceToStaff;
   /**
    * From Customer master table customerName
    */
   @NgenAuditField(fieldName = "Customer Name")
   private String customerName;
   /**
    * From Customer master table customerMasterId which is customerId
    * 
    */
   @NgenAuditField(fieldName = "Customer MasterId")
   private Long customerMasterId;
   /**
    * From Customer master table customerCode of customer
    */
   @NgenAuditField(fieldName = "Customer code")
   private String customerCode;
   /**
    * list of payment options
    */
   @NgenAuditField(fieldName = "Payment Options")
   private List<String> paymentOptions;
   /**
    * Billing run day one of the month
    */
   @NgenAuditField(fieldName = "BillRunDay One")
   private Long billRunDayOne;
   /**
    * Billing run day two of the month
    */
   @NgenAuditField(fieldName = "BillRunDay Two")
   private Long billRunDayTwo;
   /**
    * AP Vendor Code
    */
   @NgenAuditField(fieldName = "APVendor Code")
   private String apVendorCode;
   /**
    * AP Frequency of customer
    */
   @NgenAuditField(fieldName = "AP Frequency")
   private String apFrequency;
   /**
    * Ap Next Billing Date
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = "APNext BillingDate")
   private LocalDateTime apNextBillingDate;
   /**
    * AP Billing run day one of the month
    */
   @NgenAuditField(fieldName = "APBillRun One")
   private Long apBillRunOne;
   /***
    * AP Billing run day two of the month
    */
   @NgenAuditField(fieldName = "APBillRun Two")
   private Long apBillRunTwo;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = "SD Next Posting Date")
   private LocalDateTime nextSDPostingDate;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = "AP Next Posting Date")
   private LocalDateTime nextAPPostingDate;
   
   @NgenAuditField(fieldName = "AP Cycle Details")
   private List<CustomerBillingCycle> apCycleList;
   
   @NgenAuditField(fieldName = "SD Cycle Details")
   private List<CustomerBillingCycle> sdCycleList;
}