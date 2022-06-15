/**
 * This is a model which is used for assigning shipments to Export Working List based on booking
 */
package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentInventoryWorkingListModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger id;
   private BigInteger flightId;
   private BigInteger flightSegmentId;
   private BigInteger shipmentId;
   private BigInteger pieces;
   private BigDecimal weight;
   private BigInteger manifestPieces;
   private BigInteger freightOutPieces;
   private BigInteger irregularityPiecesFound;
   private BigInteger irregularityPiecesMissing;
   private List<String> partSuffix;
   
}