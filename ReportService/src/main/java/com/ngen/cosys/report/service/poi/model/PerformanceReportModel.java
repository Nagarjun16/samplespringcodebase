package com.ngen.cosys.report.service.poi.model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PerformanceReportModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   private LocalDate fromDate;

   private LocalDate toDate;

   private String BookingMailBags;

   private String BKG_FLT;

   private String BKG_FLT_DAT;

   private String BKG_FLT_DAT_RFDT;

   private String AA_DAT_RFDT_SCAN;

   private String AA_DAT_IPS_SCAN;

   private String TA_DAT_SCAN;

   private String CONTAINER;

   private String HA_DAT_SCAN;

   private String HA_FLT;

   private String HA_FLT_STD;

   private String HA_DAT_SCAN_FIRST;

   private String HA_FLT_FIRST;

   private String HA_FLT_STD_FIRST;

   private String CONTAINER_FIRST;

   private String SRC_SYS;

   private String SATS_CAR;

   private String HA_MINUS_PA_FLT;

   private String HA_MINUS_AA_IPS;

   private String PA_FLT_MINUS_AA_IPS;

   private String PA_FLT_MINUS_AA_RFDT;

   private String PA_FLT_MINUS_TA;

   private String DAT_PA_RCV;

   private String INVALD_PA;

   private String WHSE_OFD;

   private String RAMP_OFD;

   private String assisted;

   private BigInteger totalBagsForSATS;

   private BigInteger totalBagsForDNATA;

   private String manifestingMbs;

   private BigInteger totalBagsManifestedForSATS;

   private BigInteger totalBagsManifestedForDNATA;

   private BigInteger preAlertAndManifestedSatsBags;

   private BigInteger preAlertAndManifestedDNATABags;

   private BigInteger bookingFlightId;

   private BigInteger spaceIssueFlightId;

   private String spaceIssueMbs;

   private long lateLodgeInBagsHours;

   private String bagType;

   private boolean handedOver;

   private String dnataHandOverBags;

   private String manualHandoverBags;

   private String haAndPACompare;

   private String paAndTACompare;

   private String haAndAACompare;

   private String ipsAAScan;

   private String paAndIPSAACompare;

   private long bookingMinusAA;

   private String mailType;
   
   private Integer validateEarly;
}
