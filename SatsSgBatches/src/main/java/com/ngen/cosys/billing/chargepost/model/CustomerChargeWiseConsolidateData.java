package com.ngen.cosys.billing.chargepost.model;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class CustomerChargeWiseConsolidateData extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Long chargeCodeId;

   private Long billGenId;

   private Long customerId;

   private Long chargeGroupId;

   private LocalDate billGenDate;

   private BigDecimal amount;

   private BigDecimal totalAmount;

   private int behalfOfAirline;

   private String chargeEntryId;

   private String billPaidAmount;
}