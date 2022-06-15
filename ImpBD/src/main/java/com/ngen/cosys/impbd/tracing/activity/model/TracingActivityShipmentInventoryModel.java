package com.ngen.cosys.impbd.tracing.activity.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TracingActivityShipmentInventoryModel extends BaseBO {

   /**
    * Defaut generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private BigInteger tracingShipmentInfoId;
   private String shipmentLocation;
   private BigInteger inventoryPieces;
   private BigDecimal inventoryWeight;
   private String weightUnitCode;
   private String natureOfGoodsDescription;
   private String warehouseLocation;

}