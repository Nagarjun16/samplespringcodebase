package com.ngen.cosys.impbd.model;

import java.math.BigInteger;


import com.ngen.cosys.model.OCIModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ArrivalManifestShipmentOciModel extends OCIModel {

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;

   private BigInteger id;

   

}