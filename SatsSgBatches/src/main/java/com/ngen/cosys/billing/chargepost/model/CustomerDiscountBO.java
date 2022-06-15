package com.ngen.cosys.billing.chargepost.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

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
public class CustomerDiscountBO extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Long customerDiscountModelId;

   private Long billingChargeCodeId;

   private Long customerId;

   private Long chargeGroupId;

   private BigDecimal chargeTotalAmount;

   private BigDecimal flatAmount;

   private BigDecimal percentage;

   private BigDecimal threshhold;

   private String createdUserCode;

   private LocalDateTime createdDateTime;

   private String lastUpdatedUserCode;

   private LocalDateTime lastUpdatedDateTime;

   private BigDecimal minAmount;
}