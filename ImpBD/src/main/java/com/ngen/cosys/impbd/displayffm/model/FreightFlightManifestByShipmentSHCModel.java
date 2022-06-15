package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigInteger;

import com.ngen.cosys.model.SHCModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FreightFlightManifestByShipmentSHCModel extends SHCModel{

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 988195714809035235L;

   private BigInteger shipmentId;

   private BigInteger id;

}
