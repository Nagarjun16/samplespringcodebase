package com.ngen.cosys.impbd.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArrivalManifestShipmentRejectDetails extends BaseBO {

   /**
    * System generated default serial version id
    */
   private static final long serialVersionUID = 1L;

   private String shipmentNumber;
   private String rejectReason;
   private String shipmentDescriptionCode;
   private String origin;
   private String destination;
   private BigInteger piece;
   private String weightUnitCode;
   private BigDecimal weight;
   private String natureOfGoodsDescription;

}