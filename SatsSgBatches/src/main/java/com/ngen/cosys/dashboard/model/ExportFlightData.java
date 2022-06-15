/**
 * {@link ExportFlightData}
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
 * Export Flight Data
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@NoArgsConstructor
public class ExportFlightData extends BaseBO implements Serializable {
   // Generated Serial VersionUID
   private static final long serialVersionUID = -1092664473039501939L;
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
   private LocalDateTime dateSTD;
   private LocalDateTime dateETD;
   private LocalDateTime dateATD;
   private String flightTime; // ATD/ETD/STD
   private LocalDateTime flightDateTime; // ATD/ETD/STD Time
   private boolean autoCompleteFlag;
   private String flightStatus;
   //
   private String buildupCompleted;
   private String buildupCompletedColor;
   private LocalDateTime buildupCompletedTime;
   private String dlsCompleted;
   private String dlsCompletedColor;
   private LocalDateTime dlsCompletedTime;
   private String manifestCompleted;
   private String manifestCompletedColor;
   private LocalDateTime manifestCompletedTime;
   private String flightCompleted;
   private String flightCompletedColor;
   private LocalDateTime flightCompletedTime;
   private String outwardServiceReportFinalized;
   private String outwardServiceReportColor;
   private LocalDateTime outwardServiceReportFinalizedTime;
   private Integer ntmVersion;
   private String notocFinalizedColor;
   private LocalDateTime notocFinalizedTime;
   private Integer notocThresholdMinutes;
   private LocalDateTime uflMessageTime;
   //
   private String pouchCompleted;
   private String pouchCompletedColor;
   private String pouchCheckout;
   private String pouchDropoff;
   private String ucmSent;
   private String ucmSentColor;
   //
   private Integer bookedShipments;
   private Integer assignedShipments;
   private Integer readyToLoad;
   private String readyToLoadDisplay;
   private Integer actualULD;
   private Integer manifestULD;
   private String uldDisplay;
   //
   private Integer transhipmentBooked;
   private Integer transhipmentLoaded;
   private String transhipmentDisplay;
   //
   private Integer dlsULDBTAssigned;
   private Integer dlsULDBTGrossWeightUpdated;
   private String dlsULDBTDisplay;
   //
   private Integer rampULDBTAssigned;
   private Integer rampULDBTReleased;
   private String rampReleaseDisplay;
   private String rampReleaseDisplayColor;
   //
   private Integer tthUnits;
   //
   private Integer uldAllotment;
   private Integer uldAssigned;
   private String uldUsedDisplay;
   private String uldUsedDisplayColor;
   //
   private Integer palletAllotment;
   private Integer palletAssigned;
   private String palletUsedDisplay;
   private String palletUsedDisplayColor;
   //
   private Integer dgBooked;
   private Integer acceptedDGReady;
   private String acceptedDGDisplay;
   private String acceptedDGDisplayColor;
   private Integer assignedDGReady;
   private String assignedDGDisplay;
   private String assignedDGDisplayColor;
   //
   private Integer xpsBooked;
   private Integer acceptedXPSReady;
   private String acceptedXPSDisplay;
   private String acceptedXPSDisplayColor;
   private Integer assignedXPSReady;
   private String assignedXPSDisplay;
   private String assignedXPSDisplayColor;
   //
   private Integer racBooked;
   private Integer acceptedRACReady;
   private String acceptedRACDisplay;
   private String acceptedRACDisplayColor;
   private Integer assignedRACReady;
   private String assignedRACDisplay;
   private String assignedRACDisplayColor;
   //
   private Integer pilBooked;
   private Integer acceptedPILReady;
   private String acceptedPILDisplay;
   private String acceptedPILDisplayColor;
   private Integer assignedPILReady;
   private String assignedPILDisplay;
   private String assignedPILDisplayColor;
   //
   private Integer valBooked;
   private Integer acceptedVALReady;
   private String acceptedVALDisplay;
   private String acceptedVALDisplayColor;
   private Integer assignedVALReady;
   private String assignedVALDisplay;
   private String assignedVALDisplayColor;
   //
   private Integer aviBooked;
   private Integer acceptedAVIReady;
   private String acceptedAVIDisplay;
   private String acceptedAVIDisplayColor;
   private Integer assignedAVIReady;
   private String assignedAVIDisplay;
   private String assignedAVIDisplayColor;
   //
   private Integer humBooked;
   private Integer acceptedHUMReady;
   private String acceptedHUMDisplay;
   private String acceptedHUMDisplayColor;
   private Integer assignedHUMReady;
   private String assignedHUMDisplay;
   private String assignedHUMDisplayColor;
   //
   private Integer othBooked;
   private Integer acceptedOTHReady;
   private String acceptedOTHDisplay;
   private String acceptedOTHDisplayColor;
   private Integer assignedOTHReady;
   private String assignedOTHDisplay;
   private String assignedOTHDisplayColor;
   //
   private String hoDlsColor;
   private String hoNotocColor;
   
}
