package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentLoadingInfoModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger flightId;
   private BigInteger shipmentId;
   private String assignedUldOrTrolley;
   private String warehouseLocation;
   private BigInteger loadedPieces;
   private BigDecimal loadedWeight;
   private BigInteger offLoadedPieces;
   private BigDecimal offLoadedWeight;
   private Boolean buildupComplete;
   private String flightScreeningCompleted;
   private String flightFsuStatus;
   private String shc;

}