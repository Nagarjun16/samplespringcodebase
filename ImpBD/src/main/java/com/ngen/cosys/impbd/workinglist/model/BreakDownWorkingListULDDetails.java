package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BreakDownWorkingListULDDetails extends BaseBO {

   /**
    * System generated default serial version id
    */
   private static final long serialVersionUID = -4762601778044925498L;

   private String uldNumber;
   private String wareHouseHandlingInstructions;
   private String shipmentNumber;
   private String outboundFlight;
   private String transferType;
   private String natureOfGoodsDescription;
   private String partSuffix;
   
   private boolean uldDamage = Boolean.FALSE;
   
   private BigInteger impArrivalManifestULDId;
   private BigInteger flightId;   
   private BigInteger readyForDelivery;
   private BigInteger breakDownPieces;
   private BigInteger manifestedPiece;
   private BigInteger outboundFlightId;
   
   private BigDecimal breakDownWeight;
   private BigDecimal manifestedWeight;
   
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   
   

}