package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.model.ULDModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FreightFlightManifestUldModel extends ULDModel {

   private static final long serialVersionUID = 1L;

   private BigInteger impFreightFlightManifestBySegmentId;

   private BigInteger impFreightFlightManifestUldId;

   private String uldRemarks;
   private BigInteger shipmentCount;

   private BigInteger pieceCount;

   private BigDecimal weight;

   // LooseCargo
   @Valid
   @NgenCosysAppAnnotation
   private List<FreightFlightManifestByShipmentModel> shipments;
}
