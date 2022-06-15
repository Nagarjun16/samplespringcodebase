package com.ngen.cosys.application.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutoFreightoutInventoryModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   private BigInteger shipmentInventoryId;
   private BigInteger deliveryId;
   private BigInteger freightOutId;
   private String shipmentNumber;

}