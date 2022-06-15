package com.ngen.cosys.billing.chargepost.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

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
public class ThresholdRange {
   private BigDecimal fromThreshold;
   private BigDecimal tillThreshold;
   private String discountType;
}