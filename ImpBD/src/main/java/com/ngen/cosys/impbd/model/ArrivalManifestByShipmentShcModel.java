package com.ngen.cosys.impbd.model;

import java.math.BigInteger;


import com.ngen.cosys.model.SHCModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArrivalManifestByShipmentShcModel extends SHCModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 988195714809035235L;
   
   private BigInteger referenceId;
   
   private BigInteger shipmentId;

   private BigInteger id;

   
}
