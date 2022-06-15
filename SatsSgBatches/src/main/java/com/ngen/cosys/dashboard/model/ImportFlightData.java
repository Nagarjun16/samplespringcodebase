/**
 * {@link ImportFlightData}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Import Flight Data
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class ImportFlightData extends BaseBO implements Serializable {
   // Generated Serial VersionUID
   private static final long serialVersionUID = 7323615322009601823L;
   //
   private BigInteger dashboardBatchLogId;
   private String carrierGroupCode;
   private String carrierCode;
   //
   private BigInteger flightId;
   private String flightKey;
   private String flightDate; // Default STD format ddMMMyy
   private String flightBoardPoint;
   private String flightOffPoint;
   private String flightSector;
   private String flightType;
   private String aircraftType;
   private String aircraftRegCode;
   private String previousAircraftRegCode;
   private LocalDateTime aircraftRegCodeUpdatedTime;
   private String aircraftRegDisplayColor;
   private String parkingBay;
   private String previousParkingBay;
   private LocalDateTime parkingBayUpdatedTime;
   private String parkingBayDisplayColor;
   private String terminalCode;
   private LocalDateTime dateSTA;
   private LocalDateTime dateETA;
   private LocalDateTime dateATA;
   private String flightTime; // ATD/ETD/STD
   private LocalDateTime flightDateTime; // ATD/ETD/STD Time
   private String flightStatus;
   //
   private String rampCheckInCompleted;
   private String rampCheckInCompletedColor;
   private LocalDateTime rampCheckInCompletedTime;
   private String documentVerificationCompleted;
   private String documentVerificationCompletedColor;
   private LocalDateTime documentVerificationCompletedTime;
   private String breakdownCompleted;
   private String breakdownCompletedColor;
   private LocalDateTime breakdownCompletedTime;
   private String flightCompleted;
   private String flightCompletedColor;
   private LocalDateTime flightCompletedTime;
   private String flightClosed;
   private String flightClosedColor;
   private LocalDateTime flightClosedTime;
   private String flightDiscrepancyListSent;
   private String flightDiscrepancyListSentColor;
   private LocalDateTime flightDiscrepancyListSentTime;
   private String throughTransitWorkingListFinalized;
   private String throughTransitWorkingListFinalizedColor;
   private LocalDateTime throughTransitWorkingListFinalizedTime;
   private String inwardServiceReportFinalized;
   private String inwardServiceReportFinalizedColor;
   private LocalDateTime inwardServiceReportFinalizedTime;
   private String ffmStatus;
   private String ffmColor;
   private LocalDateTime ffmProcessedTime;
   //
   private String ucmSent;
   private String ucmSentColor;
   //
   private String towInRampTotalULD;
   private String towInRampCheckedInULD;
   private String towInRampDisplay;
   //
   private String documentsAttached;
   private String documentsReceived;
   private String documentsDisplay;
   //
   private String localDeliveryShipments;
   private String localDeliveryShipmentsWeight;
   private String localDeliveryDisplay;
   //
   private String transhipmentsTotal;
   private String transhipmentsWeight;
   private String transhipmentsDisplay;
   //
   private Integer manifestedDGTotal;
   private Integer manifestedDGReady;
   private String manifestedDGDisplay;
   private String manifestedDGDisplayColor;
   //
   private Integer manifestedXPSTotal;
   private Integer manifestedXPSReady;
   private String manifestedXPSDisplay;
   private String manifestedXPSDisplayColor;
   //
   private Integer manifestedRACTotal;
   private Integer manifestedRACReady;
   private String manifestedRACDisplay;
   private String manifestedRACDisplayColor;
   //
   private Integer manifestedPILTotal;
   private Integer manifestedPILReady;
   private String manifestedPILDisplay;
   private String manifestedPILDisplayColor;
   //
   private Integer manifestedVALTotal;
   private Integer manifestedVALReady;
   private String manifestedVALDisplay;
   private String manifestedVALDisplayColor;
   //
   private Integer manifestedAVITotal;
   private Integer manifestedAVIReady;
   private String manifestedAVIDisplay;
   private String manifestedAVIDisplayColor;
   //
   private Integer manifestedHUMTotal;
   private Integer manifestedHUMReady;
   private String manifestedHUMDisplay;
   private String manifestedHUMDisplayColor;
   //
   private Integer manifestedOTHTotal;
   private Integer manifestedOTHReady;
   private String manifestedOTHDisplay;
   private String manifestedOTHDisplayColor;
   
}
