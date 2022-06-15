/**
 * This model has properties which helps in handling weight difference between
 * shipment master and inventory
 */
package com.ngen.cosys.shipment.awb.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentWeightDifferenceModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   private BigDecimal shipmentWeight;
   private BigDecimal inventoryWeight;
   private BigDecimal differenceWeight = BigDecimal.ZERO;
   private Boolean shipmentMasterIsHigher = Boolean.FALSE;
   private Boolean shipmentInventoryIsHigher = Boolean.FALSE;

}