package com.ngen.cosys.billing.chargerecalculate.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

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
public class ChargeRecalculate {
   private LocalDateTime startOn;
   private LocalDateTime endOn;
   List<BigInteger> shipmentId;
}