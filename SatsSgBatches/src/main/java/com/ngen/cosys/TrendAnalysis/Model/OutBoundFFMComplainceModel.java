package com.ngen.cosys.TrendAnalysis.Model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutBoundFFMComplainceModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   private String carrierCode;
   
   private String flightKey;
   
   private String flightBoardPoint;
   
   private String flightOffPoint;
   
   private String flagImpExp;
   
   private String flagFrtPax;
   
   private String aircraftType;
   
   private String autoFlightComplete;
   
   private LocalDateTime dateSTA;
   
   private LocalDateTime dateATA;
   
   private LocalDateTime dateETA;
   
   private LocalDateTime dateSTD;
   
   private LocalDateTime dateATD;
   
   private LocalDateTime dateETD;
   
   private String flightStatus;
   
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
   
   private BigInteger totalOffLoadPcs;
   
   private BigDecimal totalOffLoadWgt;
   
   private LocalDateTime buildupCompletedTime;
   
   private String buildupCompleteLeadTime;
   
   private LocalDateTime dlsCompletedTime;
   
   private String dlsCompleteLeadTime;
   
   private LocalDateTime notocFinalizedTime;
   
   private String notocSentLeadTime;
   
   private LocalDateTime manifestCompletedTime;
   
   private String manifestCompleteLeadTime;
   
   private LocalDateTime offLoadLastFinalizedTime;
   
   private Integer aliLastVersion;
   
   private LocalDateTime aliLastReceivedTime;
   
   private LocalDateTime outwardServiceReportFinalizedTime;
   
   private LocalDateTime rampCheckInCompletedAt;
   
   private String rampCheckInCompleteLeadTime;
   
   private LocalDateTime impFFMProcessedTime;
   
   private String impFFMProcessedLeadTime;
   
   private LocalDateTime documentVerificationCompletedAt;
   
   private String documentVerificationCompleteLeadTime;
   
   private LocalDateTime breakDownCompletedTime;
   
   private String breakdownCompleteLeadTime;
   
   private LocalDateTime fdlSentTime;
   
   private String fdlSentLeadTime;
   
   private LocalDateTime inwardServiceReportFinalizedTime;
   
   private LocalDateTime ucmSentTime;
   
   private String ucmSentLeadTime;
   
   private LocalDateTime ttWorkingListFinalizedTime;
   
   private LocalDateTime flightCompletedTime;
   
   private String flightCompleteLeadTime;
   
   private LocalDateTime flightClosedTime;
   
   private LocalDateTime createdTime;
   
   private LocalDateTime updatedTime;
   
   private LocalDateTime uldLastReleaseTime;
   
   private boolean fromImport;
   
   private BigInteger totalBkdAWB;
   
   private BigInteger totalBkdPcs;
   
   private BigDecimal totalBkdWgt;
   
   private BigInteger stdminus_2_4_8hrsCount;

}
