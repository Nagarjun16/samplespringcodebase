package com.ngen.cosys.TrendAnalysis.Model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SQTrendAnalysisModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   private String shipmentNumber;

   private LocalDate shipmentDate;

   private String origin;

   private String destination;

   private BigInteger pieces;

   private BigDecimal weight;

   private String shc;

   private String natureOfGoods;

   private LocalDateTime lodgeInDateTime;

   private String agent;

   private LocalDateTime acceptanceDateTime;

   private String acceptanceFlightKey;

   private LocalDateTime acceptanceFlightSTD;

   private LocalDateTime acceptanceFlightATD;

   private String bookingCancellationReason;

   private String offloadReason;

   private BigInteger offloadPieces;

   private BigDecimal offloadWeight;

   private String departedFlightKey;

   private LocalDateTime departedFlightStd;

   private LocalDateTime departedFlightDateAtd;

   private String departedPart;

   private BigInteger departedPieces;

   private BigDecimal departedweight;

   private String departedAsAcceptanceFlight;

   private BigInteger totalBkdAWB_STDMinus8Hrs;

   private BigInteger totalBkdPcs_STDMinus8Hrs;

   private BigDecimal totalBkdWgt_STDMinus8Hrs;

   private BigInteger totalBkdAWB_STDMinus4Hrs;

   private BigInteger totalBkdPcs_STDMinus4Hrs;

   private BigDecimal totalBkdWgt_STDMinus4Hrs;

   private BigInteger totalBkdAWB_STDMinus2Hrs;

   private BigInteger totalBkdPcs_STDMinus2Hrs;

   private BigDecimal totalBkdWgt_STDMinus2Hrs;

   private BigInteger totalDepAWB;

   private BigInteger totalDepPcs;

   private BigDecimal totalDepWgt;

   private BigInteger totalOffLoadAWB;

   private BigInteger TotalOffLoadPcs;

   private BigDecimal totalOffLoadWgt;

   private LocalDateTime offLoadDateTime;
   
   private LocalDateTime fromDate;
   
   private LocalDateTime toDate;
   
   private boolean fromOffload;
   
   private BigInteger bookedPieces; 
   
   private BigDecimal bookedWeight;  
   
   private String bookedPartSuffix;  
   
   private BigInteger cancelledBookingPieces; 
   
   private BigDecimal cancelledBookingWeight;  
   
   private String cancelledBookingPartSuffix;  
   
   private String cancelledBookingFlightKey;  
   
   private LocalDateTime cancelledBookingFlightSTD;  
   
   private LocalDateTime cancelledBookingFlightATD; 
   
   private boolean fromCancelBooking;
   
   private LocalDateTime cancelledDateTime;

}
