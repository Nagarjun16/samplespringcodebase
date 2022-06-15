package com.ngen.cosys.export.flightautocomplete.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlightAutoCompleteShipmentHouseDetails extends BaseBO {

   /**
    * Default system generated serail version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   private String number;

}