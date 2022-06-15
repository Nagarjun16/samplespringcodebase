package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigInteger;

import com.ngen.cosys.model.OSIModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FreightFlightManifestOtherServiceInfoModel extends OSIModel {
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 133808667821969078L;

   private BigInteger shipmentId;

   private BigInteger id;

}
