package com.ngen.cosys.billing.sap.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Component
@ToString
public class AccountsPayableDetails extends SAPFileRecord {

   private static final long serialVersionUID = 1L;

   private long billEntryId;

   private long billGenId;

   private String customerId;

   private String chargeCodeId;

   private String documentSequenceNumber;

   private String postingKey;

   private String apVendorCode;

   private String foreignAmount;

   private String glAccountCode;

   private BigDecimal sgdAmount;

   private BigDecimal totalSGDAmount;

   private String taxCode;

   private String foreignGST;

   private String sgdGST;

   private String apVendorKey;

   private Date billGenDate;
   
   private String billingFrequency;
   
   private String payeeName;
   
   private String accountNo;

}
