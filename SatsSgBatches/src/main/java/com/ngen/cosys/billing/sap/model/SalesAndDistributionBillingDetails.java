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
public class SalesAndDistributionBillingDetails extends SAPFileRecord {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private long billEntryId;

   private long billGenId;

   private Date billGenDate;

   private String customerId;

   private String chargeCodeId;

   private String groupChargeCodeId;

   private String documentSequenceNumber;

   private String salesOrderType;

   private String documentCurrency;

   private String sapCustomerCode;

   private BigDecimal totalQuantity;

   private BigDecimal totalAmount;

   private String materialNumber;

   private String materialDescription;

   private String uom;

   private BigDecimal cumulativeOrderQuantity;

   private BigDecimal grossWeight;

   private BigDecimal netWeight;

   private String pricingType;

   private BigDecimal amount;

   private String discountType;

   private BigDecimal discountAmount;

   private int billingNumber;

   private String salesOrganization;

   private String distributionChannel;

   private String division;

}
