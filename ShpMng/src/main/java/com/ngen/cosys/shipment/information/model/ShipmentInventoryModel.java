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
public class ShipmentInventoryModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private Boolean select=Boolean.FALSE;
   private BigInteger shipmentInventory_Id;
   private String shipmentLocation;
   private BigInteger flightId;
   private BigInteger inventoryPieces;
   private BigDecimal inventoryWeight;
   private String warehouseLocation;
   private String hold;
   private String locked;
   private String lockedBy;
   private String lockReason;
   private String screeningCompleted;
   private String shcs;   
   private String partSuffix;

   // Only for import use
   private String deliveryRequestInfo;
   
   private BigDecimal inventoryChgWeight;

}