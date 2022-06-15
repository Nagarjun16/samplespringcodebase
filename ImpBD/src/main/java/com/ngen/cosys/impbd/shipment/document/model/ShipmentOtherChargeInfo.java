package com.ngen.cosys.impbd.shipment.document.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentOtherChargeInfo extends BaseBO {
   /**
    * System Generated Serial Version id
    */
   private static final long serialVersionUID = -3417559437173256962L;

   private BigInteger shipmentId;
   private BigInteger shipmentOtherChargesId;

   private String customsOrigin;
   private String chargeCode;
   private String currency;

   private Boolean collectBankEndorsementClearanceLetter;

   private BigDecimal dueFromAirline;
   private BigDecimal dueFromAgent;
   private BigDecimal freightCharges;
   private BigDecimal valuationCharges;
   private BigDecimal tax;

}