package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigInteger;

import com.ngen.cosys.model.OnwardMovementModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FreightFlightManifestShipmentOnwardMovementModel extends OnwardMovementModel{

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;

   private BigInteger id;
 
   private String transferType;
}
