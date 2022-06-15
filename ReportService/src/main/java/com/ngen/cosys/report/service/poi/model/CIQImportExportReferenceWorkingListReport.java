/**
 * {@link CIQImportExportReport}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CIQ Import Export Reference Working List Report
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class CIQImportExportReferenceWorkingListReport {

   private String c2kIdentifier;
   private String gha;
   private String month;
   private String station;
   private String group;
   private String airline;
   private Integer exportAWBMWBs;
   private Integer exportShipmentsManifested;
   private Integer fwbReceived;
   private Integer fwbReceivedBeforeRCS;
   private Integer rcsSent;
   private Integer ffmSent;
   private Integer depSent;
   private Integer depSentInTime;
   private Integer importAWBMWBs;
   private Integer importShipmentsManifested;
   private Integer fwbReceivedBeforeATA;
   private Integer ffmReceivedBeforeATA;
   private Integer rcfSent;
   private Integer rcfSentInTime;
   private Integer awdSent;
   private Integer awdSentInTime;
   private Integer nfdMessagesSent;
   private Integer nfdSentInTime;
   private Integer dlvEvent;
   private Integer dlvSentInTime;
   
}
