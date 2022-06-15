package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.model.DimensionModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class FreightFlightManifestShipmentDimensionModel extends DimensionModel{
   /*private static final long serialVersionUID = 1L;

   private BigInteger impFreightFlightManifestByAWBId;

   private String weightUnitCode;
   
   private BigDecimal weight;
   
   private BigInteger dimensionLength;
   private BigInteger dimensionWIdth;
   private BigInteger dimensionHeight;
   private BigInteger numberOfPieces;
   private String measurementUnitCode;*/
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;

   private BigInteger id;
}
