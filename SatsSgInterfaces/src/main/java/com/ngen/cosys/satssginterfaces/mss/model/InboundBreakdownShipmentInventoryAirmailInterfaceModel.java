package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InboundBreakdownShipmentInventoryAirmailInterfaceModel extends BaseBO {
   /**
    * System Generated Serial Version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger inventoryId;
   private BigInteger inboundBreakdownShipmentId;
   private BigInteger shipmentId;
   private BigInteger flightId;

   private String shipmentLocation;
   private String warehouseLocation;

   @CheckPieceConstraint(type = MandatoryType.Type.REQUIRED)
   private BigInteger pieces;

   private BigDecimal damagePieces;

   @CheckWeightConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   private BigDecimal weight;

   private String handlingMode;
   private String transferType;
   private String uldNumber;
   private String warehouseHandlingInstruction;
   private BigInteger impArrivalManifestULDId;

   private List<InboundBreakdownShipmentHouseAirmailInterfaceModel> house;

   @Valid
   private List<InboundBreakdownShipmentShcAirmailInterfaceModel> shc;

}