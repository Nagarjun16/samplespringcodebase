package com.ngen.cosys.impbd.mail.manifest.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferCN46FromManifestModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   private BigInteger flightId;
   private String flightKey;
   private LocalDate flightDate;
   private String dispatchNumber;
   private BigInteger shipmentId;
   private LocalDateTime dispatchDate;
   private String uldTrollyNumber;
   private String originOfficeExchange;
   private String destinationOfficeExchange;
   private String origin;
   private String destination;
   private String nextDestination;
   private BigInteger lcPieces;
   private BigDecimal lcWeight;
   private BigInteger cpPieces;
   private BigDecimal cpWeight;
   private BigInteger emsPieces;
   private BigDecimal emsWeight;
   private BigInteger othPieces;
   private BigDecimal othWeight;
   private BigInteger totPieces;
   private BigDecimal totWeight;
   private BigInteger segmentId;
   private BigInteger airmailManifestId;
   private String remarks;
}
