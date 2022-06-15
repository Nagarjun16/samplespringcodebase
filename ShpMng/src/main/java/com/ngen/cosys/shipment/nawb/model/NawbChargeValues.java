package com.ngen.cosys.shipment.nawb.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NawbChargeValues extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private BigDecimal dueagentcarrier;
   private BigDecimal dueagentcharge;
   private BigDecimal valuationCharges;
   private BigDecimal tax;
   private BigDecimal freightcharges;
   private String shipmentNumber;
   private BigInteger shipmentId;
   private BigInteger customerId;
   private String carrierCode;
}
