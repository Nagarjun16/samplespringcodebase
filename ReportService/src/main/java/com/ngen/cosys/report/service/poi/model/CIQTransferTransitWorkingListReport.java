/**
 * {@link CIQTransferTransitWorkingListReport}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.report.service.poi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * CIQ Transfer Transit Working List Report
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class CIQTransferTransitWorkingListReport {

   private String c2kIdentifier;
   private String gha;
   private String month;
   private String station;
   private String airline;
   private Integer exportTransferShipmentsManifested;
   private Integer rctSent;
   private Integer exportTransferFFMSent;
   private Integer exportTransferFFMSentInTime;
   private Integer exportTransferDEPSent;
   private Integer exportTransferDEPSentInTime;
   private Integer importTransferAWBMWBs;
   private Integer importTransferShipmentsManifested;
   private Integer importTransferFWBReceived;
   private Integer importTransferFFMReceivedBeforeATA;
   private Integer importTransferRCFSent;
   private Integer importTransferRCFSentInTime;
   private Integer tfdEventCompleted;
   private Integer tfdMessageSentInTime;
   private Integer importTransitAWBMWBs;
   private Integer importTransitShipmentMovements;
   private Integer fwbReceived;
   private Integer ffmReceivedBeforeATA;
   private Integer transitRCFSent;
   private Integer transitRCFSentInTime;
   private Integer transitFFMSent;
   private Integer transitFFMSentInTime;
   private Integer transitDEPSent;
   private Integer transitDEPSentInTime;
   
}
