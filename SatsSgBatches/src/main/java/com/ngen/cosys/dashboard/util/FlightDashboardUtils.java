/**
 * {@link FlightDashboardUtils}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.dashboard.common.DashboardConstants;
import com.ngen.cosys.dashboard.enums.DashboardColorConstants;
import com.ngen.cosys.dashboard.enums.DashboardEquation;
import com.ngen.cosys.dashboard.enums.DashboardEventTypes;
import com.ngen.cosys.dashboard.model.EventFlightInformation;
import com.ngen.cosys.dashboard.model.EventNotification;
import com.ngen.cosys.dashboard.model.ExportFlightData;
import com.ngen.cosys.dashboard.model.ImportFlightData;
import com.ngen.cosys.multi.tenancy.enums.TenantZone;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.timezone.util.TenantZoneTime;

import lombok.NoArgsConstructor;

/**
 * Dashboard Utility
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
public class FlightDashboardUtils {

   private static final Logger LOGGER = LoggerFactory.getLogger(FlightDashboardUtils.class);
   //
   private static final int ZERO = 0;
   private static final String SLASH = "/";
   private static final String FLIGHT_CANCELLED = "CNX";
   
   /**
    * @param exportFlights
    * @param eventConfigurations
    */
   public static void exportFlightEventsUpdate(List<ExportFlightData> exportFlights,
         List<EventNotification> eventConfigurations) {
      if (!CollectionUtils.isEmpty(eventConfigurations)) {
         LocalDateTime currentZoneTime = getCurrentZoneTime();
         for (ExportFlightData exportFlight : exportFlights) {
            // Aircraft Registration Code verification
            getOutboundAircraftRegistrationDisplay(exportFlight);
            // Parking Bay verification
            getOutboundParkingBayDisplay(exportFlight);
            // Buildup Complete SLA timing verification
            getBuildUpCompleteSLAUpdate(exportFlight, eventConfigurations, currentZoneTime);
            // DLS Finalize SLA timing verification
            getDLSFinalizeSLAUpdate(exportFlight, eventConfigurations, currentZoneTime);
            // Manifest Complete SLA timing verification
            getManifestCompleteSLAUpdate(exportFlight, eventConfigurations, currentZoneTime);
            // NOTOC Finalize SLA timing verification
            getNOTOCFinalizeSLAUpdate(exportFlight, eventConfigurations, currentZoneTime);
            // Pouch Complete SLA timing verification
            getPouchCompleteSLAUpdate(exportFlight, eventConfigurations, currentZoneTime);
            // Flight Complete SLA timing verification
            getFlightCompleteSLAUpdate(exportFlight, eventConfigurations, currentZoneTime);
            // Outward Service Report Complete SLA timing verification
            getOutwardServiceReportFinalizeSLAUpdate(exportFlight, eventConfigurations, currentZoneTime);
            // Ready to Load Display (Ready/Assigned)
            getReadyToLoadShipmentsDisplay(exportFlight);
            // ULD Display
            getULDDetailsDisplay(exportFlight);
            // Transhipment Display
            getTranshipmentDisplay(exportFlight);
            // DLS-ULDBT Display
            getDLSULDBTDisplay(exportFlight);
            // Ramp Release Display
            getRampReleaseDisplay(exportFlight);
            // Airline Instructions ULD used display
            getULDUsedDisplay(exportFlight);
            // Airline Instructions Pallet used display
            getPalletUsedDisplay(exportFlight);
            // Accepted DG, XPS, RAC, PIL, VAL, AVI, HUM, OTH
            getAcceptedShipmentsBySHCGroupDisplay(exportFlight, eventConfigurations, currentZoneTime);
            // Assigned DG, XPS, RAC, PIL, VAL, AVI, HUM, OTH
            getAssignedShipmentsBySHCGroupDisplay(exportFlight, eventConfigurations, currentZoneTime);
         }
      }
   }
   
   /**
    * @param importFlights
    * @param eventConfigurations
    */
   public static void importFlightEventsUpdate(List<ImportFlightData> importFlights,
         List<EventNotification> eventConfigurations) {
      if (!CollectionUtils.isEmpty(eventConfigurations)) {
         LocalDateTime currentZoneTime = getCurrentZoneTime();
         for (ImportFlightData importFlight : importFlights) {
            // Parking Bay verification
            getInboundParkingBayDisplay(importFlight);
            // Ramp CheckIn Complete SLA timing verification
            getRampCheckInCompleteSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // Document Verification Complete SLA timing verification
            getDocumentVerificationCompleteSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // Breakdown Complete SLA timing verification
            getBreakdownCompleteSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // Flight Complete SLA timing verification
            getFlightCompleteSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // Flight Close SLA timing verification
            getFlightCloseSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // Flight Discrepancy List Sent SLA timing verification
            getFlightDiscrepancyListSentSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // Through Transit Working List Finalize SLA timing verification
            getThroughTransitWorkingListFinalizeSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // Inward Service Report Finalize SLA timing verification
            getInwardServiceReportFinalizeSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // FFM Received SLA timing verification
            getFFMReceivedSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
            // Local Delivery Display
            getLocalDeliveryShipmentsDisplay(importFlight);
            // Transhipments Display
            getTranshipmentsDisplay(importFlight);
            // Manifested SHC Group Display - DG, XPS, RAC, PIL, VAL, AVI, HUM, OTH
            getManifestedShipmentsBySHCGroupDisplay(importFlight, eventConfigurations, currentZoneTime);
            // UCM Message Sent SLA timing verification
            getUCMSentSLAUpdate(importFlight, eventConfigurations, currentZoneTime);
         }
      }
   }
   
   /**
    * GET Outbound Aircraft Registraction display changes
    * 
    * @param exportFlight
    */
   private static void getOutboundAircraftRegistrationDisplay(ExportFlightData exportFlight) {
      if (!Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)
            && !StringUtils.isEmpty(exportFlight.getAircraftRegCode())) {
         // Color
         String colorCode = null;
         LocalDateTime ulfMessageTime = exportFlight.getUflMessageTime();
         LocalDateTime dlsCompletedTime = exportFlight.getDlsCompletedTime();
         LocalDateTime manifestedTime = exportFlight.getManifestCompletedTime();
         LocalDateTime notocFinalizedTime = exportFlight.getNotocFinalizedTime();
         if (Objects.nonNull(ulfMessageTime) //
               && !StringUtils.isEmpty(exportFlight.getPreviousAircraftRegCode()) 
               && !Objects.equals(exportFlight.getAircraftRegCode(), exportFlight.getPreviousAircraftRegCode())
               && isOutboundDataUpdatedRecently(ulfMessageTime, dlsCompletedTime, manifestedTime, notocFinalizedTime,
                     null)) {
            colorCode = DashboardColorConstants.RED.value();
         } else {
            LocalDateTime aircraftRegCodeUpdatedTime = exportFlight.getAircraftRegCodeUpdatedTime();
            if (!Objects.equals(exportFlight.getAircraftRegCode(), exportFlight.getPreviousAircraftRegCode()) //
                  && Objects.nonNull(aircraftRegCodeUpdatedTime) //
                  && Objects.isNull(exportFlight.getDateATD()) //
                  && isOutboundDataUpdatedRecently( //
                        aircraftRegCodeUpdatedTime, dlsCompletedTime, manifestedTime, notocFinalizedTime, null)) {
               colorCode = DashboardColorConstants.BLUE.value();
            }
         }
         exportFlight.setAircraftRegDisplayColor(colorCode);
      }
   }
   
   /**
    * GET Outbound Parking Bay display changes
    * 
    * @param exportFlight
    */
   private static void getOutboundParkingBayDisplay(ExportFlightData exportFlight) {
      if (!Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)
            && !StringUtils.isEmpty(exportFlight.getParkingBay())) {
         // Color
         String colorCode = null;
         LocalDateTime ulfMessageTime = exportFlight.getUflMessageTime();
         LocalDateTime dlsCompletedTime = exportFlight.getDlsCompletedTime();
         LocalDateTime manifestedTime = exportFlight.getManifestCompletedTime();
         LocalDateTime notocFinalizedTime = exportFlight.getNotocFinalizedTime();
         LocalDateTime rampReleaseTime = null;
         if (Objects.nonNull(ulfMessageTime) //
               && !StringUtils.isEmpty(exportFlight.getPreviousParkingBay())
               && !Objects.equals(exportFlight.getParkingBay(), exportFlight.getPreviousParkingBay())
               && isOutboundDataUpdatedRecently(ulfMessageTime, dlsCompletedTime, manifestedTime, notocFinalizedTime,
                     rampReleaseTime)) {
            colorCode = DashboardColorConstants.RED.value();
         } else {
            LocalDateTime parkingBayUpdatedTime = exportFlight.getParkingBayUpdatedTime();
            if (!Objects.equals(exportFlight.getParkingBay(), exportFlight.getPreviousParkingBay()) //
                  && Objects.nonNull(parkingBayUpdatedTime) //
                  && Objects.isNull(exportFlight.getDateATD()) //
                  && isOutboundDataUpdatedRecently(parkingBayUpdatedTime, dlsCompletedTime, manifestedTime,
                        notocFinalizedTime, rampReleaseTime)) {
               colorCode = DashboardColorConstants.BLUE.value();
            }
         }
         exportFlight.setParkingBayDisplayColor(colorCode);
      }
   }
   
   /**
    * @param eventTime
    * @param dlsCompletedTime
    * @param manifestedTime
    * @param notocFinalizedTime
    * @param rampReleaseTime
    * @return
    */
   private static boolean isOutboundDataUpdatedRecently(LocalDateTime eventTime, LocalDateTime dlsCompletedTime,
         LocalDateTime manifestedTime, LocalDateTime notocFinalizedTime, LocalDateTime rampReleaseTime) {
      return ((Objects.nonNull(dlsCompletedTime) && eventTime.isAfter(dlsCompletedTime)) //
            || (Objects.nonNull(manifestedTime) && eventTime.isAfter(manifestedTime)) //
            || (Objects.nonNull(notocFinalizedTime) && eventTime.isAfter(notocFinalizedTime))
            || (Objects.nonNull(rampReleaseTime) && eventTime.isAfter(rampReleaseTime)));
   }
   
   /**
    * GET Buildup Complete SLA Update
    * 
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    * @return
    */
   private static void getBuildUpCompleteSLAUpdate(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flight no action needed
      if (Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(exportFlight.getBuildupCompletedTime())) {
         if (currentZoneTime.isEqual(exportFlight.getFlightDateTime())
               || currentZoneTime.isAfter(exportFlight.getFlightDateTime())) {
            colorCode = DashboardColorConstants.RED.value();
         } else {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.OUTBOUND_BUILDUP_COMPLETE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                           exportFlight.getAircraftType(), exportFlight.getCarrierCode(), exportFlight.getFlightKey(),
                           exportFlight.getDateSTD().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? exportFlight.getFlightDateTime()
                              : exportFlight.getFlightDateTime().minusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.OUTBOUND_BUILDUP_COMPLETE,
                              eventNotification, flightDateTime, currentZoneTime);
                        if (!StringUtils.isEmpty(colorCode)) {
                           break;
                        }
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      exportFlight.setBuildupCompletedColor(colorCode);
   }
   
   /**
    * GET DLS Finalize SLA Update
    * 
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    * @return
    */
   private static void getDLSFinalizeSLAUpdate(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flight no action needed
      if (Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(exportFlight.getDlsCompletedTime())) {
         if (currentZoneTime.isEqual(exportFlight.getFlightDateTime())
               || currentZoneTime.isAfter(exportFlight.getFlightDateTime())) {
            colorCode = DashboardColorConstants.RED.value();
         } else {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.OUTBOUND_DLS_COMPLETE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                           exportFlight.getAircraftType(), exportFlight.getCarrierCode(), exportFlight.getFlightKey(),
                           exportFlight.getDateSTD().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? exportFlight.getFlightDateTime()
                              : exportFlight.getFlightDateTime().minusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.OUTBOUND_DLS_COMPLETE, eventNotification,
                              flightDateTime, currentZoneTime);
                        if (!StringUtils.isEmpty(colorCode)) {
                           break;
                        }
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      exportFlight.setDlsCompletedColor(colorCode);
   }
   
   /**
    * GET Manifest Complete SLA Update
    * 
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    * @return
    */
   private static void getManifestCompleteSLAUpdate(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flight no action needed
      if (Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(exportFlight.getManifestCompletedTime())) {
         if (currentZoneTime.isEqual(exportFlight.getFlightDateTime())
               || currentZoneTime.isAfter(exportFlight.getFlightDateTime())) {
            colorCode = DashboardColorConstants.RED.value();
         } else {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.OUTBOUND_MANIFEST_COMPLETE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                           exportFlight.getAircraftType(), exportFlight.getCarrierCode(), exportFlight.getFlightKey(),
                           exportFlight.getDateSTD().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? exportFlight.getFlightDateTime()
                              : exportFlight.getFlightDateTime().minusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.OUTBOUND_MANIFEST_COMPLETE,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
            // Traffic Light - Default Color Code - GRAY
            if (StringUtils.isEmpty(colorCode)) {
               colorCode = DashboardColorConstants.GRAY.value();
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      exportFlight.setManifestCompletedColor(colorCode);
   }
   
   /**
    * GET NOTOC Finalize SLA Update
    * 
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    * @return
    */
   private static void getNOTOCFinalizeSLAUpdate(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flight no action needed
      if (Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      Duration threshold = null;
      String minutes = null;
      String colorCode = null;
      //
      if (Objects.isNull(exportFlight.getNotocFinalizedTime())) {
         if (currentZoneTime.isEqual(exportFlight.getFlightDateTime())
               || currentZoneTime.isAfter(exportFlight.getFlightDateTime())) {
            colorCode = DashboardColorConstants.RED.value();
         } else {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.OUTBOUND_NOTOC_FINALIZATION);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                           exportFlight.getAircraftType(), exportFlight.getCarrierCode(), exportFlight.getFlightKey(),
                           exportFlight.getDateSTD().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? exportFlight.getFlightDateTime()
                              : exportFlight.getFlightDateTime().minusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.OUTBOUND_NOTOC_FINALIZATION,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
         // Threshold
         threshold = Duration.between(currentZoneTime, exportFlight.getFlightDateTime());
         minutes = String.valueOf(Math.abs(threshold.toMinutes()));
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
         // Threshold
         threshold = Duration.between(exportFlight.getNotocFinalizedTime(), exportFlight.getFlightDateTime());
         minutes = String.valueOf(Math.abs(threshold.toMinutes()));
      }
      exportFlight.setNotocThresholdMinutes(Integer.valueOf(minutes));
      exportFlight.setNotocFinalizedColor(colorCode);
   }
   
   /**
    * GET Pouch Complete SLA Update
    * 
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getPouchCompleteSLAUpdate(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flight no action needed
      if (Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      //
      String colorCode = null;
      if (Objects.equals(DashboardConstants.EVENT_NOT_COMPLETED, exportFlight.getPouchCompleted())) {
         if (currentZoneTime.isEqual(exportFlight.getFlightDateTime())
               || currentZoneTime.isAfter(exportFlight.getFlightDateTime())) {
            colorCode = DashboardColorConstants.RED.value();
         } else {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.OUTBOUND_FLIGHT_POUCH_COMPLETE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                           exportFlight.getAircraftType(), exportFlight.getCarrierCode(), exportFlight.getFlightKey(),
                           exportFlight.getDateSTD().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? exportFlight.getFlightDateTime()
                              : exportFlight.getFlightDateTime().minusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.OUTBOUND_FLIGHT_POUCH_COMPLETE,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
            // Traffic Light - Default Color Code - GRAY
            if (StringUtils.isEmpty(colorCode)) {
               colorCode = DashboardColorConstants.GRAY.value();
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      exportFlight.setPouchCompletedColor(colorCode);
   }
   
   /**
    * GET Flight Complete SLA Update
    * 
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    * @return
    */
   private static void getFlightCompleteSLAUpdate(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flight no action needed
      if (Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(exportFlight.getFlightCompletedTime())) {
         if (Objects.nonNull(exportFlight.getDateATD())) {
            if (currentZoneTime.isEqual(exportFlight.getDateATD())
                  || currentZoneTime.isAfter(exportFlight.getDateATD())) {
               List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                     DashboardEventTypes.Key.OUTBOUND_FLIGHT_COMPLETE);
               if (!CollectionUtils.isEmpty(eventNotifications)) {
                  for (EventNotification eventNotification : eventNotifications) {
                     if (Objects.nonNull(eventNotification)) {
                        boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                              exportFlight.getAircraftType(), exportFlight.getCarrierCode(),
                              exportFlight.getFlightKey(), exportFlight.getDateSTD().toLocalDate(), eventNotification);
                        if (flightMatch) {
                           LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                                 ? exportFlight.getDateATD()
                                 : exportFlight.getDateATD().plusMinutes(eventNotification.getMinutes());
                           colorCode = getSLATimingColor(DashboardEventTypes.Key.OUTBOUND_FLIGHT_COMPLETE,
                                 eventNotification, flightDateTime, currentZoneTime);
                        }
                        if (!StringUtils.isEmpty(colorCode)) {
                           break;
                        }
                     }
                  }
               }
               // Traffic Light - Default Color Code - GRAY
               if (StringUtils.isEmpty(colorCode)) {
                  colorCode = DashboardColorConstants.GRAY.value();
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      exportFlight.setFlightCompletedColor(colorCode);
   }
   
   /**
    * GET Outward Service Report Finalize SLA Update
    * 
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    * @return
    */
   private static void getOutwardServiceReportFinalizeSLAUpdate(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flight no action needed
      if (Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(exportFlight.getOutwardServiceReportFinalizedTime())) {
         if (Objects.nonNull(exportFlight.getDateATD())) {
            if (currentZoneTime.isEqual(exportFlight.getDateATD())
                  || currentZoneTime.isAfter(exportFlight.getDateATD())) {
               List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                     DashboardEventTypes.Key.OUTBOUND_SERVICE_REPORT_COMPLETE);
               if (!CollectionUtils.isEmpty(eventNotifications)) {
                  for (EventNotification eventNotification : eventNotifications) {
                     if (Objects.nonNull(eventNotification)) {
                        boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                              exportFlight.getAircraftType(), exportFlight.getCarrierCode(),
                              exportFlight.getFlightKey(), exportFlight.getDateSTD().toLocalDate(), eventNotification);
                        if (flightMatch) {
                           LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                                 ? exportFlight.getDateATD()
                                 : exportFlight.getDateATD().plusMinutes(eventNotification.getMinutes());
                           colorCode = getSLATimingColor(DashboardEventTypes.Key.OUTBOUND_SERVICE_REPORT_COMPLETE,
                                 eventNotification, flightDateTime, currentZoneTime);
                        }
                        if (!StringUtils.isEmpty(colorCode)) {
                           break;
                        }
                     }
                  }
               }
               // Traffic Light - Default Color Code - GRAY
               if (StringUtils.isEmpty(colorCode)) {
                  colorCode = DashboardColorConstants.GRAY.value();
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      exportFlight.setOutwardServiceReportColor(colorCode);
   }

   /**
    * GET Ready to Load Shipment for display
    *   Assigned / Booked - (x/y)
    *   
    * @param exportFlight
    */
   private static void getReadyToLoadShipmentsDisplay(ExportFlightData exportFlight) {
      StringBuilder readyToLoadDisplay = new StringBuilder();
      readyToLoadDisplay.append(String.valueOf(exportFlight.getAssignedShipments()));
      readyToLoadDisplay.append(SLASH);
      readyToLoadDisplay.append(String.valueOf(exportFlight.getBookedShipments()));
      exportFlight.setReadyToLoadDisplay(readyToLoadDisplay.toString());
   }
   
   /**
    * GET ULD Details for display
    *   Manifested ULD / Actual ULD - (x/y)
    * 
    * @param exportFlight
    */
   private static void getULDDetailsDisplay(ExportFlightData exportFlight) {
      StringBuilder uldDisplay = new StringBuilder();
      uldDisplay.append(String.valueOf(exportFlight.getManifestULD()));
      uldDisplay.append(SLASH);
      uldDisplay.append(String.valueOf(exportFlight.getActualULD()));
      exportFlight.setUldDisplay(uldDisplay.toString());
   }
   
   /**
    * GET Transhipment for display
    *   Transhipment Loaded / Transhipment Booked - (x/y)
    *   
    * @param exportFlight
    */
   private static void getTranshipmentDisplay(ExportFlightData exportFlight) {
      StringBuilder transhipmentDisplay = new StringBuilder();
      transhipmentDisplay.append(String.valueOf(exportFlight.getTranshipmentLoaded()));
      transhipmentDisplay.append(SLASH);
      transhipmentDisplay.append(String.valueOf(exportFlight.getTranshipmentBooked()));
      exportFlight.setTranshipmentDisplay(transhipmentDisplay.toString());
   }
   
   /**
    * GET DLS-ULDBT details for display
    *   DLSULDBT GrossWeight Updated / DLSULDBT Assigned - (x/y)
    *   
    * @param exportFlight
    */
   private static void getDLSULDBTDisplay(ExportFlightData exportFlight) {
      StringBuilder uldBTDisplay = new StringBuilder();
      uldBTDisplay.append(String.valueOf(exportFlight.getDlsULDBTGrossWeightUpdated()));
      uldBTDisplay.append(SLASH);
      uldBTDisplay.append(String.valueOf(exportFlight.getDlsULDBTAssigned()));
      exportFlight.setDlsULDBTDisplay(uldBTDisplay.toString());
   }

   /**
    * GET Ramp Release ULDBT Assigned and Released details for display
    *   Ramp ULDBT released / ULDBT Assigned - (x/y)
    *   
    * @param exportFlight
    */
   private static void getRampReleaseDisplay(ExportFlightData exportFlight) {
      Integer rampULDBTAssigned = exportFlight.getRampULDBTAssigned();
      Integer rampULDBTReleased = exportFlight.getRampULDBTReleased();
      //
      StringBuilder rampReleaseDisplay = new StringBuilder();
      rampReleaseDisplay.append(String.valueOf(rampULDBTReleased));
      rampReleaseDisplay.append(SLASH);
      rampReleaseDisplay.append(String.valueOf(rampULDBTAssigned));
      exportFlight.setRampReleaseDisplay(rampReleaseDisplay.toString());
      // Cancelled Flight no action needed
      if (Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      // Color
      String colorCode = null;
      if (Objects.nonNull(rampULDBTAssigned) && rampULDBTAssigned.compareTo(ZERO) == 1
            && Objects.nonNull(rampULDBTReleased) && rampULDBTReleased.compareTo(ZERO) == 1) {
         colorCode = rampULDBTAssigned.compareTo(rampULDBTReleased) == 0 //
               ? DashboardColorConstants.RED.value()
               : DashboardColorConstants.AMBER.value();
      }
      exportFlight.setRampReleaseDisplayColor(colorCode);
   }
   
   /**
    * GET Airline Instructions ULD Allotment and used details for display
    *   ULD Assigned / ULD Allotment - (x/y)
    *   
    * @param exportFlight
    */
   private static void getULDUsedDisplay(ExportFlightData exportFlight) {
      Integer uldAllotment = exportFlight.getUldAllotment();
      Integer uldAssigned = exportFlight.getUldAssigned();
      //
      StringBuilder uldUsedDisplay = new StringBuilder();
      String colorCode = null;
      // If allotment is EMPTY then display only ULD assigned
      if (Objects.isNull(uldAllotment) || uldAllotment.compareTo(ZERO) == 0) {
         uldUsedDisplay.append(String.valueOf(uldAssigned));
      } else {
         if (uldAllotment.compareTo(ZERO) == 1) {
            // Display changes
            uldUsedDisplay.append(String.valueOf(uldAssigned));
            uldUsedDisplay.append(SLASH);
            uldUsedDisplay.append(String.valueOf(uldAllotment));
            // Cancelled Flight no action needed
            if (!Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
               // Color code
               if (Objects.isNull(uldAssigned) || uldAssigned.compareTo(ZERO) == 0
                     || uldAssigned.compareTo(uldAllotment) == -1) {
                  colorCode = DashboardColorConstants.AMBER.value();
               } else if (uldAssigned.compareTo(uldAllotment) == 1) {
                  colorCode = DashboardColorConstants.RED.value();
               } else if (uldAssigned.compareTo(uldAllotment) == 0) {
                  colorCode = DashboardColorConstants.GREEN.value();
               }
            }
         } else {
            uldUsedDisplay.append(String.valueOf(uldAssigned));
         }
      }
      exportFlight.setUldUsedDisplay(uldUsedDisplay.toString());
      exportFlight.setUldUsedDisplayColor(colorCode);
   }
   
   /**
    * GET Airline Instructions Pallet Allotment and used details for display
    *   Pallet Assigned / Pallet Allotment - (x/y)
    *   
    * @param exportFlight
    */
   private static void getPalletUsedDisplay(ExportFlightData exportFlight) {
      Integer palletAllotment = exportFlight.getPalletAllotment();
      Integer palletAssigned = exportFlight.getPalletAssigned();
      //
      StringBuilder palletUsedDisplay = new StringBuilder();
      String colorCode = null;
      // If allotment is EMPTY then display only Pallet assigned
      if (Objects.isNull(palletAllotment) || palletAllotment.compareTo(ZERO) == 0) {
         palletUsedDisplay.append(String.valueOf(palletAssigned));
      } else {
         if (palletAllotment.compareTo(ZERO) == 1) {
            // Display changes
            palletUsedDisplay.append(String.valueOf(palletAssigned));
            palletUsedDisplay.append(SLASH);
            palletUsedDisplay.append(String.valueOf(palletAllotment));
            // Cancelled Flight no action needed
            if (!Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
               // Color code
               if (Objects.isNull(palletAssigned) || palletAssigned.compareTo(ZERO) == 0
                     || palletAssigned.compareTo(palletAllotment) == -1) {
                  colorCode = DashboardColorConstants.AMBER.value();
               } else if (palletAssigned.compareTo(palletAllotment) == 1) {
                  colorCode = DashboardColorConstants.RED.value();
               } else if (palletAssigned.compareTo(palletAllotment) == 0) {
                  colorCode = DashboardColorConstants.GREEN.value();
               }
            }
         } else {
            palletUsedDisplay.append(String.valueOf(palletAssigned));
         }
      }
      exportFlight.setPalletUsedDisplay(palletUsedDisplay.toString());
      exportFlight.setPalletUsedDisplayColor(colorCode);
   }
   
   /**
    * GET Accepted Shipments by SHC Groups
    *   DG, XPS, RAC, PIL, VAL, AVI, HUM, OTH
    * 
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getAcceptedShipmentsBySHCGroupDisplay(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Accepted DG Shipment
      String[] acceptedDetails = getAcceptedDisplayAndColor(exportFlight.getDgBooked(),
            exportFlight.getAcceptedDGReady(), exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ACCEPTED_DG_SHIPMENTS);
      exportFlight.setAcceptedDGDisplay(acceptedDetails[0]);
      exportFlight.setAcceptedDGDisplayColor(acceptedDetails[1]);
      // Accepted XPS Shipment
      acceptedDetails = getAcceptedDisplayAndColor(exportFlight.getXpsBooked(), exportFlight.getAcceptedXPSReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ACCEPTED_XPS_SHIPMENTS);
      exportFlight.setAcceptedXPSDisplay(acceptedDetails[0]);
      exportFlight.setAcceptedXPSDisplayColor(acceptedDetails[1]);
      // Accepted RAC Shipment
      acceptedDetails = getAcceptedDisplayAndColor(exportFlight.getRacBooked(), exportFlight.getAcceptedRACReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ACCEPTED_RAC_SHIPMENTS);
      exportFlight.setAcceptedRACDisplay(acceptedDetails[0]);
      exportFlight.setAcceptedRACDisplayColor(acceptedDetails[1]);
      // Accepted PIL Shipment
      acceptedDetails = getAcceptedDisplayAndColor(exportFlight.getPilBooked(), exportFlight.getAcceptedPILReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ACCEPTED_PIL_SHIPMENTS);
      exportFlight.setAcceptedPILDisplay(acceptedDetails[0]);
      exportFlight.setAcceptedPILDisplayColor(acceptedDetails[1]);
      // Accepted VAL Shipment
      acceptedDetails = getAcceptedDisplayAndColor(exportFlight.getValBooked(), exportFlight.getAcceptedVALReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ACCEPTED_VAL_SHIPMENTS);
      exportFlight.setAcceptedVALDisplay(acceptedDetails[0]);
      exportFlight.setAcceptedVALDisplayColor(acceptedDetails[1]);
      // Accepted AVI Shipment
      acceptedDetails = getAcceptedDisplayAndColor(exportFlight.getAviBooked(), exportFlight.getAcceptedAVIReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ACCEPTED_AVI_SHIPMENTS);
      exportFlight.setAcceptedAVIDisplay(acceptedDetails[0]);
      exportFlight.setAcceptedAVIDisplayColor(acceptedDetails[1]);
      // Accepted HUM Shipment
      acceptedDetails = getAcceptedDisplayAndColor(exportFlight.getHumBooked(), exportFlight.getAcceptedHUMReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ACCEPTED_HUM_SHIPMENTS);
      exportFlight.setAcceptedHUMDisplay(acceptedDetails[0]);
      exportFlight.setAcceptedHUMDisplayColor(acceptedDetails[1]);
      // Accepted OTH Shipment
      acceptedDetails = getAcceptedDisplayAndColor(exportFlight.getOthBooked(), exportFlight.getAcceptedOTHReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ACCEPTED_OTH_SHIPMENTS);
      exportFlight.setAcceptedOTHDisplay(acceptedDetails[0]);
      exportFlight.setAcceptedOTHDisplayColor(acceptedDetails[1]);
   }
   
   /**
    * GET Assigned Shipments by SHC Groups
    *   DG, XPS, RAC, PIL, VAL, AVI, HUM, OTH
    *   
    * @param exportFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getAssignedShipmentsBySHCGroupDisplay(ExportFlightData exportFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Assigned DG Shipment
      String[] assignedDetails = getAssignedDisplayAndColor(exportFlight.getDgBooked(),
            exportFlight.getAssignedDGReady(), exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ASSIGNED_DG_SHIPMENTS);
      exportFlight.setAssignedDGDisplay(assignedDetails[0]);
      exportFlight.setAssignedDGDisplayColor(assignedDetails[1]);
      // Assigned XPS Shipment
      assignedDetails = getAssignedDisplayAndColor(exportFlight.getXpsBooked(), exportFlight.getAssignedXPSReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ASSIGNED_XPS_SHIPMENTS);
      exportFlight.setAssignedXPSDisplay(assignedDetails[0]);
      exportFlight.setAssignedXPSDisplayColor(assignedDetails[1]);
      // Assigned RAC Shipment
      assignedDetails = getAssignedDisplayAndColor(exportFlight.getRacBooked(), exportFlight.getAssignedRACReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ASSIGNED_RAC_SHIPMENTS);
      exportFlight.setAssignedRACDisplay(assignedDetails[0]);
      exportFlight.setAssignedRACDisplayColor(assignedDetails[1]);
      // Assigned PIL Shipment
      assignedDetails = getAssignedDisplayAndColor(exportFlight.getPilBooked(), exportFlight.getAssignedPILReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ASSIGNED_PIL_SHIPMENTS);
      exportFlight.setAssignedPILDisplay(assignedDetails[0]);
      exportFlight.setAssignedPILDisplayColor(assignedDetails[1]);
      // Assigned VAL Shipment
      assignedDetails = getAssignedDisplayAndColor(exportFlight.getValBooked(), exportFlight.getAssignedVALReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ASSIGNED_VAL_SHIPMENTS);
      exportFlight.setAssignedVALDisplay(assignedDetails[0]);
      exportFlight.setAssignedVALDisplayColor(assignedDetails[1]);
      // Assigned AVI Shipment
      assignedDetails = getAssignedDisplayAndColor(exportFlight.getAviBooked(), exportFlight.getAssignedAVIReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ASSIGNED_AVI_SHIPMENTS);
      exportFlight.setAssignedAVIDisplay(assignedDetails[0]);
      exportFlight.setAssignedAVIDisplayColor(assignedDetails[1]);
      // Assigned HUM Shipment
      assignedDetails = getAssignedDisplayAndColor(exportFlight.getHumBooked(), exportFlight.getAssignedHUMReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ASSIGNED_HUM_SHIPMENTS);
      exportFlight.setAssignedHUMDisplay(assignedDetails[0]);
      exportFlight.setAssignedHUMDisplayColor(assignedDetails[1]);
      // Assigned OTH Shipment
      assignedDetails = getAssignedDisplayAndColor(exportFlight.getOthBooked(), exportFlight.getAssignedOTHReady(),
            exportFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.OUTBOUND_ASSIGNED_OTH_SHIPMENTS);
      exportFlight.setAssignedOTHDisplay(assignedDetails[0]);
      exportFlight.setAssignedOTHDisplayColor(assignedDetails[1]);
   }
   
   /**
    * GET Accepted Display value and Color
    *   [0] - Display
    *   [1] - Color
    *   
    * @param booked (Y)
    * @param ready (X)
    * @param exportFlight
    * @param currentZoneTime
    * @param eventConfigurations
    * @param eventType
    * @return
    */
   private static String[] getAcceptedDisplayAndColor(Integer booked, Integer ready, ExportFlightData exportFlight,
         LocalDateTime currentZoneTime, List<EventNotification> eventConfigurations, String eventType) {
      String[] details = new String[2];
      String colorCode = null;
      List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations, eventType);
      // Display
      StringBuilder display = new StringBuilder();
      display.append(String.valueOf(ready)).append(SLASH).append(String.valueOf(booked));
      // Color code
      if (!Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED) && Objects.nonNull(booked)
            && booked.compareTo(ZERO) == 1) {
         if (Objects.nonNull(ready)) {
            if ((ready.compareTo(ZERO) == 0 || ready.compareTo(ZERO) == 1) && ready.compareTo(booked) == -1) {
               if (currentZoneTime.isEqual(exportFlight.getFlightDateTime())
                     || currentZoneTime.isAfter(exportFlight.getFlightDateTime())) {
                  colorCode = DashboardColorConstants.RED.value();
               } else {
                  if (!CollectionUtils.isEmpty(eventNotifications)) {
                     for (EventNotification eventNotification : eventNotifications) {
                        if (Objects.nonNull(eventNotification)) {
                           boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                                 exportFlight.getAircraftType(), exportFlight.getCarrierCode(),
                                 exportFlight.getFlightKey(), exportFlight.getDateSTD().toLocalDate(),
                                 eventNotification);
                           if (flightMatch) {
                              LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                                    ? exportFlight.getFlightDateTime()
                                    : exportFlight.getFlightDateTime().minusMinutes(eventNotification.getMinutes());
                              colorCode = getSLATimingColor(eventType, eventNotification, flightDateTime,
                                    currentZoneTime);
                           }
                           if (!StringUtils.isEmpty(colorCode)) {
                              break;
                           }
                        }
                     }
                  }
               }
            } else if (ready.compareTo(booked) == 0 || ready.compareTo(booked) == 1) {
               colorCode = DashboardColorConstants.GREEN.value();
            }
         }
      }
      details[0] = display.toString();
      details[1] = colorCode;
      return details;
   }
   
   /**
    * GET Assigned Display value and Color
    *   [0] - Display
    *   [1] - Color
    *   
    * @param booked (Y)
    * @param ready (X)
    * @param exportFlight
    * @param currentZoneTime
    * @param eventConfigurations
    * @param eventType
    * @return
    */
   private static String[] getAssignedDisplayAndColor(Integer booked, Integer ready, ExportFlightData exportFlight,
         LocalDateTime currentZoneTime, List<EventNotification> eventConfigurations, String eventType) {
      String[] details = new String[2];
      String colorCode = null;
      List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations, eventType);
      // Display
      StringBuilder display = new StringBuilder();
      display.append(String.valueOf(ready)).append(SLASH).append(String.valueOf(booked));
      // Color code
      if (!Objects.equals(exportFlight.getFlightStatus(), FLIGHT_CANCELLED) && Objects.nonNull(booked)
            && booked.compareTo(ZERO) == 1) {
         if (Objects.nonNull(ready)) {
            if ((ready.compareTo(ZERO) == 0 || ready.compareTo(ZERO) == 1) && ready.compareTo(booked) == -1) {
               if (currentZoneTime.isEqual(exportFlight.getFlightDateTime())
                     || currentZoneTime.isAfter(exportFlight.getFlightDateTime())) {
                  colorCode = DashboardColorConstants.RED.value();
               } else {
                  if (!CollectionUtils.isEmpty(eventNotifications)) {
                     for (EventNotification eventNotification : eventNotifications) {
                        if (Objects.nonNull(eventNotification)) {
                           boolean flightMatch = getSLAFlightDetailMatching(exportFlight.getFlightType(),
                                 exportFlight.getAircraftType(), exportFlight.getCarrierCode(),
                                 exportFlight.getFlightKey(), exportFlight.getDateSTD().toLocalDate(),
                                 eventNotification);
                           if (flightMatch) {
                              LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                                    ? exportFlight.getFlightDateTime()
                                    : exportFlight.getFlightDateTime().minusMinutes(eventNotification.getMinutes());
                              colorCode = getSLATimingColor(eventType, eventNotification, flightDateTime,
                                    currentZoneTime);
                           }
                           if (!StringUtils.isEmpty(colorCode)) {
                              break;
                           }
                        }
                     }
                  }
               }
            } else if (ready.compareTo(booked) == 0 || ready.compareTo(booked) == 1) {
               colorCode = DashboardColorConstants.GREEN.value();
            }
         }
      }
      details[0] = display.toString();
      details[1] = colorCode;
      return details;
   }
   
   /**
    * GET Inbound Parking Bay Display
    * 
    * @param importFlight
    */
   private static void getInboundParkingBayDisplay(ImportFlightData importFlight) {
      if (!Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)
            && !StringUtils.isEmpty(importFlight.getParkingBay())) {
         // Color
         String colorCode = null;
         LocalDateTime parkingBayUpdatedTime = importFlight.getParkingBayUpdatedTime();
         LocalDateTime dateATA = importFlight.getDateATA();
         LocalDateTime dateETA = importFlight.getDateETA();
         LocalDateTime dateSTA = importFlight.getDateSTA();
         if (!StringUtils.isEmpty(importFlight.getPreviousParkingBay())
               && !Objects.equals(importFlight.getParkingBay(), importFlight.getPreviousParkingBay())
               && Objects.nonNull(parkingBayUpdatedTime)
               && isInboundDataUpdatedBeforeFlightTimings(parkingBayUpdatedTime, dateATA, dateETA, dateSTA)) {
            colorCode = DashboardColorConstants.RED.value();
         }
         importFlight.setParkingBayDisplayColor(colorCode);
      }
   }
   
   /**
    * Is Inbound Data updated before flight timings
    * 
    * @param eventTime
    * @param dateATA
    * @param dateETA
    * @param dateSTA
    * @return
    */
   private static boolean isInboundDataUpdatedBeforeFlightTimings(LocalDateTime eventTime, LocalDateTime dateATA,
         LocalDateTime dateETA, LocalDateTime dateSTA) {
      return ((Objects.nonNull(dateATA) && eventTime.isBefore(dateATA)) //
            || (Objects.nonNull(dateETA) && eventTime.isBefore(dateETA)) //
            || (Objects.nonNull(dateSTA) && eventTime.isBefore(dateSTA)));
   }
   
   /**
    * GET Ramp CheckIn Complete SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getRampCheckInCompleteSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Tow RampCheckIn Display
      StringBuilder rampCheckInDisplay = new StringBuilder();
      rampCheckInDisplay.append(String.valueOf(importFlight.getTowInRampCheckedInULD()));
      rampCheckInDisplay.append(SLASH);
      rampCheckInDisplay.append(String.valueOf(importFlight.getTowInRampTotalULD()));
      // Display
      importFlight.setTowInRampDisplay(rampCheckInDisplay.toString());
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getRampCheckInCompletedTime())) {
         if (Objects.nonNull(importFlight.getDateATA()) //
               && (currentZoneTime.isEqual(importFlight.getDateATA())
                     || currentZoneTime.isAfter(importFlight.getDateATA()))) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_RAMP_CHECKIN);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getDateATA()
                              : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_RAMP_CHECKIN, eventNotification,
                              flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setRampCheckInCompletedColor(colorCode);
   }
   
   /**
    * GET Document Verification Complete SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getDocumentVerificationCompleteSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Documents Display - Received (X) / Attached (Y)
      StringBuilder documentsDisplay = new StringBuilder();
      documentsDisplay.append(String.valueOf(importFlight.getDocumentsReceived()));
      documentsDisplay.append(SLASH);
      documentsDisplay.append(String.valueOf(importFlight.getDocumentsAttached()));
      // Display
      importFlight.setDocumentsDisplay(documentsDisplay.toString());
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getDocumentVerificationCompletedTime())) {
         if (Objects.nonNull(importFlight.getDateATA()) //
               && (currentZoneTime.isEqual(importFlight.getDateATA())
                     || currentZoneTime.isAfter(importFlight.getDateATA()))) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_DOCUMENT_COMPLETE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getDateATA()
                              : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_DOCUMENT_COMPLETE,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setDocumentVerificationCompletedColor(colorCode);
   }
   
   /**
    * GET Breakdown Complete SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getBreakdownCompleteSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getBreakdownCompletedTime())) {
         if (Objects.nonNull(importFlight.getDateATA()) //
               && (currentZoneTime.isEqual(importFlight.getDateATA())
                     || currentZoneTime.isAfter(importFlight.getDateATA()))) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_BREAKDOWN_COMPLETE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getDateATA()
                              : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_BREAKDOWN_COMPLETE,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setBreakdownCompletedColor(colorCode);
   }
   
   /**
    * GET Flight Complete SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getFlightCompleteSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getFlightCompletedTime())) {
         if (Objects.nonNull(importFlight.getDateATA()) //
               && (currentZoneTime.isEqual(importFlight.getDateATA())
                     || currentZoneTime.isAfter(importFlight.getDateATA()))) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_FLIGHT_COMPLETE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getDateATA()
                              : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_FLIGHT_COMPLETE,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setFlightCompletedColor(colorCode);
   }
   
   /**
    * GET Flight Close SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getFlightCloseSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getFlightClosedTime())) {
         if (Objects.nonNull(importFlight.getDateATA()) //
               && (currentZoneTime.isEqual(importFlight.getDateATA())
                     || currentZoneTime.isAfter(importFlight.getDateATA()))) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_FLIGHT_CLOSE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getDateATA()
                              : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_FLIGHT_CLOSE, eventNotification,
                              flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setFlightClosedColor(colorCode);
   }
   
   /**
    * GET Flight Discrepancy List Sent SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getFlightDiscrepancyListSentSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getFlightDiscrepancyListSentTime())) {
         if (Objects.nonNull(importFlight.getDateATA()) //
               && (currentZoneTime.isEqual(importFlight.getDateATA())
                     || currentZoneTime.isAfter(importFlight.getDateATA()))) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_FLIGHT_DISCREPANCY_LIST_SENT);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getDateATA()
                              : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_FLIGHT_DISCREPANCY_LIST_SENT,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setFlightDiscrepancyListSentColor(colorCode);
   }
   
   /**
    * GET Through Transit Working List Finalize SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getThroughTransitWorkingListFinalizeSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getThroughTransitWorkingListFinalizedTime())) {
         if (Objects.nonNull(importFlight.getDateATA()) //
               && (currentZoneTime.isEqual(importFlight.getDateATA())
                     || currentZoneTime.isAfter(importFlight.getDateATA()))) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_THROUGH_TRANSIT_FINALIZATION);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getDateATA()
                              : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_THROUGH_TRANSIT_FINALIZATION,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setThroughTransitWorkingListFinalizedColor(colorCode);
   }
   
   /**
    * GET Inward Service Report Finalize Finalize SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getInwardServiceReportFinalizeSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getInwardServiceReportFinalizedTime())) {
         if (Objects.nonNull(importFlight.getDateATA()) //
               && (currentZoneTime.isEqual(importFlight.getDateATA())
                     || currentZoneTime.isAfter(importFlight.getDateATA()))) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_SERVICE_REPORT_COMPLETE);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getDateATA()
                              : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_SERVICE_REPORT_COMPLETE,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setInwardServiceReportFinalizedColor(colorCode);
   }
   
   /**
    * GET FFM Received SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getFFMReceivedSLAUpdate(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getFfmProcessedTime())) {
         if (currentZoneTime.isEqual(importFlight.getFlightDateTime())
               || currentZoneTime.isAfter(importFlight.getFlightDateTime())) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_FFM_RECEIVED);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getFlightDateTime()
                              : importFlight.getFlightDateTime().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_FFM_RECEIVED, eventNotification,
                              flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setFfmColor(colorCode);
   }
   
   /**
    * GET Local Delivery Shipments Display
    *   Total Shipments (X) / Shipments Weight (Y)
    *    
    * @param importFlight
    */
   private static void getLocalDeliveryShipmentsDisplay(ImportFlightData importFlight) {
      StringBuilder localDeliveryShipmentsDisplay = new StringBuilder();
      localDeliveryShipmentsDisplay.append(String.valueOf(importFlight.getLocalDeliveryShipments()));
      localDeliveryShipmentsDisplay.append(SLASH);
      localDeliveryShipmentsDisplay.append(String.valueOf(importFlight.getLocalDeliveryShipmentsWeight()));
      importFlight.setLocalDeliveryDisplay(localDeliveryShipmentsDisplay.toString());
   }
   
   /**
    * GET Transhipments Display
    *   Total Shipments (X) / Shipments Weight (Y)
    *    
    * @param importFlight
    */
   private static void getTranshipmentsDisplay(ImportFlightData importFlight) {
      StringBuilder transhipmentsDisplay = new StringBuilder();
      transhipmentsDisplay.append(String.valueOf(importFlight.getTranshipmentsTotal()));
      transhipmentsDisplay.append(SLASH);
      transhipmentsDisplay.append(String.valueOf(importFlight.getTranshipmentsWeight()));
      importFlight.setTranshipmentsDisplay(transhipmentsDisplay.toString());
   }
   
   /**
    * GET Manifested Shipments by SHC Groups
    *   DG, XPS, RAC, PIL, VAL, AVI, HUM, OTH
    *   
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    * @param curre
    */
   private static void getManifestedShipmentsBySHCGroupDisplay(ImportFlightData importFlight,
         List<EventNotification> eventConfigurations, LocalDateTime currentZoneTime) {
      // Manifested DG Shipment
      String[] manifestedDetails = getManifestedDisplayAndColor(importFlight.getManifestedDGTotal(),
            importFlight.getManifestedDGReady(), importFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.INBOUND_MANIFESTED_DG_SHIPMENTS);
      importFlight.setManifestedDGDisplay(manifestedDetails[0]);
      importFlight.setManifestedDGDisplayColor(manifestedDetails[1]);
      // Manifested XPS Shipment
      manifestedDetails = getManifestedDisplayAndColor(importFlight.getManifestedXPSTotal(),
            importFlight.getManifestedXPSReady(), importFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.INBOUND_MANIFESTED_XPS_SHIPMENTS);
      importFlight.setManifestedXPSDisplay(manifestedDetails[0]);
      importFlight.setManifestedXPSDisplayColor(manifestedDetails[1]);
      // Manifested RAC Shipment
      manifestedDetails = getManifestedDisplayAndColor(importFlight.getManifestedRACTotal(),
            importFlight.getManifestedRACReady(), importFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.INBOUND_MANIFESTED_RAC_SHIPMENTS);
      importFlight.setManifestedRACDisplay(manifestedDetails[0]);
      importFlight.setManifestedRACDisplayColor(manifestedDetails[1]);
      // Manifested PIL Shipment
      manifestedDetails = getManifestedDisplayAndColor(importFlight.getManifestedPILTotal(),
            importFlight.getManifestedPILReady(), importFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.INBOUND_MANIFESTED_PIL_SHIPMENTS);
      importFlight.setManifestedPILDisplay(manifestedDetails[0]);
      importFlight.setManifestedPILDisplayColor(manifestedDetails[1]);
      // Manifested VAL Shipment
      manifestedDetails = getManifestedDisplayAndColor(importFlight.getManifestedVALTotal(),
            importFlight.getManifestedVALReady(), importFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.INBOUND_MANIFESTED_VAL_SHIPMENTS);
      importFlight.setManifestedVALDisplay(manifestedDetails[0]);
      importFlight.setManifestedVALDisplayColor(manifestedDetails[1]);
      // Manifested AVI Shipment
      manifestedDetails = getManifestedDisplayAndColor(importFlight.getManifestedAVITotal(),
            importFlight.getManifestedAVIReady(), importFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.INBOUND_MANIFESTED_AVI_SHIPMENTS);
      importFlight.setManifestedAVIDisplay(manifestedDetails[0]);
      importFlight.setManifestedAVIDisplayColor(manifestedDetails[1]);
      // Manifested HUM Shipment
      manifestedDetails = getManifestedDisplayAndColor(importFlight.getManifestedHUMTotal(),
            importFlight.getManifestedHUMReady(), importFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.INBOUND_MANIFESTED_HUM_SHIPMENTS);
      importFlight.setManifestedHUMDisplay(manifestedDetails[0]);
      importFlight.setManifestedHUMDisplayColor(manifestedDetails[1]);
      // Manifested OTH Shipment
      manifestedDetails = getManifestedDisplayAndColor(importFlight.getManifestedOTHTotal(),
            importFlight.getManifestedOTHReady(), importFlight, currentZoneTime, eventConfigurations,
            DashboardEventTypes.Key.INBOUND_MANIFESTED_OTH_SHIPMENTS);
      importFlight.setManifestedOTHDisplay(manifestedDetails[0]);
      importFlight.setManifestedOTHDisplayColor(manifestedDetails[1]);
   }
   
   /**
    * GET Manifested Display value and Color
    *   [0] - Display
    *   [1] - Color
    *   
    * @param freightIn (Y)
    * @param ready (X)
    * @param importFlight
    * @param currentZoneTime
    * @param eventConfigurations
    * @param eventType
    * @return
    */
   private static String[] getManifestedDisplayAndColor(Integer freightIn, Integer ready, ImportFlightData importFlight,
         LocalDateTime currentZoneTime, List<EventNotification> eventConfigurations, String eventType) {
      String[] details = new String[2];
      String colorCode = null;
      List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations, eventType);
      // Display
      StringBuilder display = new StringBuilder();
      display.append(String.valueOf(ready)).append(SLASH).append(String.valueOf(freightIn));
      // Color code
      if (!Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)
            && (Objects.nonNull(freightIn) || freightIn.compareTo(ZERO) == 1) && Objects.nonNull(ready)) {
         if ((ready.compareTo(ZERO) == 0 || ready.compareTo(ZERO) == 1) && ready.compareTo(freightIn) == -1) {
            if (Objects.nonNull(importFlight.getDateATA())) {
               // Event based SLA Color coding
               if (!CollectionUtils.isEmpty(eventNotifications)) {
                  for (EventNotification eventNotification : eventNotifications) {
                     if (Objects.nonNull(eventNotification)) {
                        boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                              importFlight.getAircraftType(), importFlight.getCarrierCode(),
                              importFlight.getFlightKey(), importFlight.getDateSTA().toLocalDate(), eventNotification);
                        if (flightMatch) {
                           LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                                 ? importFlight.getDateATA()
                                 : importFlight.getDateATA().plusMinutes(eventNotification.getMinutes());
                           colorCode = getSLATimingColor(eventType, eventNotification, flightDateTime, currentZoneTime);
                        }
                        if (!StringUtils.isEmpty(colorCode)) {
                           break;
                        }
                     }
                  }
               } else { // Count based SLA Color coding
                  colorCode = DashboardColorConstants.AMBER.value();
               }
            }
         } else if (freightIn.compareTo(ZERO) == 1 && ready.compareTo(ZERO) == 1
               && (ready.compareTo(freightIn) == 0 || ready.compareTo(freightIn) == 1)) {
            colorCode = DashboardColorConstants.GREEN.value();
         }
      }
      details[0] = display.toString();
      details[1] = colorCode;
      return details;
   }
   
   /**
    * @return
    */
   public static LocalDateTime getCurrentZoneTime() {
      return TenantZoneTime.getZoneDateTime(LocalDateTime.now(),TenantContext.get().getTenantId());
   }
   
   /**
    * GET Event Notification detail
    * 
    * @param eventConfigurations
    * @param eventType
    * @return
    */
   private static List<EventNotification> getEventNotificationByType(List<EventNotification> eventConfigurations,
         String eventType) {
      List<EventNotification> eventNotificationConfig = null;
      for (EventNotification eventNotification : eventConfigurations) {
         if (Objects.isNull(eventNotificationConfig)) {
            eventNotificationConfig = new ArrayList<>();
         }
         if (Objects.equals(eventType, eventNotification.getEventName())) {
            eventNotificationConfig.add(eventNotification);
         }
      }
      return eventNotificationConfig;
   }
   
   /**
    * GET flight detail matching
    * 
    * @param flightType
    * @param aircraftType
    * @param carrierCode
    * @param flightKey
    * @param flightDate
    * @param eventNotification
    * @return
    */
   private static boolean getSLAFlightDetailMatching(String flightType, String aircraftType, String carrierCode,
         String flightKey, LocalDate flightDate, EventNotification eventNotification) {
      boolean match = Objects.equals(flightType, eventNotification.getFlightType());
      if (match && !StringUtils.isEmpty(eventNotification.getAircraftType())) {
         match = Objects.equals(aircraftType, eventNotification.getAircraftType());
      }
      if (match && !CollectionUtils.isEmpty(eventNotification.getFlightInformations())) {
         boolean anyMatch = false;
         for (EventFlightInformation flightInformation : eventNotification.getFlightInformations()) {
            boolean flightMatch = false;
            if (!StringUtils.isEmpty(flightInformation.getCarrierCode())) {
               flightMatch = Objects.equals(carrierCode, flightInformation.getCarrierCode());
            }
            if (flightMatch && !StringUtils.isEmpty(flightInformation.getFlightKey())) {
               flightMatch = Objects.equals(flightKey, flightInformation.getFlightKey());
            }
            if (flightMatch && Objects.nonNull(flightInformation.getFlightDate())) {
               flightMatch = flightDate.equals(flightInformation.getFlightDate());
            }
            if (flightMatch) {
               anyMatch = true;
               break;
            }
         }
         match = anyMatch;
      }
      return match;
   }
   
   /**
    * GET SLA update Timing Color
    * 
    * @param eventName
    * @param eventNotification
    * @param flightDateTime
    * @param currentZoneTime
    * @return
    */
   private static String getSLATimingColor(String eventName, EventNotification eventNotification,
         LocalDateTime flightDateTime, LocalDateTime currentZoneTime) {
      String colorCode = null;
      boolean importEvent = importEvent(eventName);
      switch (eventNotification.getSlaType()) {
      case DashboardColorConstants.Code.RED:
         if (equationMatching(eventName, eventNotification.getEquation(), flightDateTime, currentZoneTime,
               eventNotification.getMinutes(), importEvent)) {
            colorCode = DashboardColorConstants.RED.value();
         }
         break;
      case DashboardColorConstants.Code.AMBER:
         if (equationMatching(eventName, eventNotification.getEquation(), flightDateTime, currentZoneTime,
               eventNotification.getMinutes(), importEvent)) {
            colorCode = DashboardColorConstants.AMBER.value();
         }
         break;
      case DashboardColorConstants.Code.GREEN:
         // Event is completed before the minimum time, then show as GREEN
         if (!equationMatching(eventName, eventNotification.getEquation(), flightDateTime, currentZoneTime,
               eventNotification.getMinutes(), importEvent)
               && currentZoneTime.isBefore(flightDateTime)) {
            colorCode = DashboardColorConstants.GREEN.value();
         }
         break;
      default: break;
      }
      LOGGER.debug(
            "Flight Dashboard Utils - GET SLA Timing :: EventName - {}, FlightDateTime - {}, Current ZoneTime - {}, ColorCode - {}",
            eventName, flightDateTime, currentZoneTime, colorCode);
      return colorCode;
   }
   
   /**
    * Equation Matching
    * 
    * @param eventName
    * @param equation
    * @param flightDateTime
    * @param currentZoneTime
    * @param minutes
    * @param importEvent
    * @return
    */
   private static boolean equationMatching(String eventName, String equation, LocalDateTime flightDateTime,
         LocalDateTime currentZoneTime, Integer minutes, boolean importEvent) {
      boolean match = false;
      Duration duration = Duration.between(flightDateTime, currentZoneTime);
      switch (equation) {
      case DashboardEquation.Type.EQUALS:
         match = currentZoneTime.isEqual(flightDateTime);
         break;
      case DashboardEquation.Type.NOT_EQUALS:
         match = !currentZoneTime.isEqual(flightDateTime);
         break;
      case DashboardEquation.Type.LESS_THAN:
         match = importEvent //
               ? currentZoneTime.isBefore(flightDateTime) && Math.abs(duration.toMinutes()) <= minutes
               : currentZoneTime.isAfter(flightDateTime) && Math.abs(duration.toMinutes()) <= minutes;
         break;
      case DashboardEquation.Type.LESS_THAN_EQUALS:
         match = importEvent //
               ? (currentZoneTime.isEqual(flightDateTime) //
                     || (currentZoneTime.isBefore(flightDateTime) && Math.abs(duration.toMinutes()) <= minutes))
               : (currentZoneTime.isEqual(flightDateTime) //
                     || (currentZoneTime.isAfter(flightDateTime) && Math.abs(duration.toMinutes()) <= minutes));
         break;
      case DashboardEquation.Type.GREATER_THAN:
         match = importEvent ? currentZoneTime.isAfter(flightDateTime) : flightDateTime.isAfter(currentZoneTime);
         break;
      case DashboardEquation.Type.GREATER_THAN_EQUALS:
         match = importEvent //
               ? (currentZoneTime.isEqual(flightDateTime) || currentZoneTime.isAfter(flightDateTime))
               : (currentZoneTime.isEqual(flightDateTime) || currentZoneTime.isAfter(flightDateTime));
         break;
      }
      return match;
   }
   
   /**
    * @param eventName
    * @return
    */
   private static boolean importEvent(String eventName) {
      return Objects.equals(DashboardEventTypes.Key.INBOUND_RAMP_CHECKIN, eventName)
            || Objects.equals(DashboardEventTypes.Key.INBOUND_DOCUMENT_COMPLETE, eventName)
            || Objects.equals(DashboardEventTypes.Key.INBOUND_BREAKDOWN_COMPLETE, eventName)
            || Objects.equals(DashboardEventTypes.Key.INBOUND_SERVICE_REPORT_COMPLETE, eventName)
            || Objects.equals(DashboardEventTypes.Key.INBOUND_THROUGH_TRANSIT_FINALIZATION, eventName)
            || Objects.equals(DashboardEventTypes.Key.INBOUND_FLIGHT_COMPLETE, eventName)
            || Objects.equals(DashboardEventTypes.Key.INBOUND_FLIGHT_DISCREPANCY_LIST_SENT, eventName)
            || Objects.equals(DashboardEventTypes.Key.INBOUND_FLIGHT_CLOSE, eventName);
   }
   
   /**
    * GET UCM Message SLA Update
    * 
    * @param importFlight
    * @param eventConfigurations
    * @param currentZoneTime
    */
   private static void getUCMSentSLAUpdate(ImportFlightData importFlight, List<EventNotification> eventConfigurations,
         LocalDateTime currentZoneTime) {
      // Cancelled Flights no need action
      if (Objects.equals(importFlight.getFlightStatus(), FLIGHT_CANCELLED)) {
         return;
      }
      String colorCode = null;
      //
      if (Objects.isNull(importFlight.getUcmSent())) {
         if (currentZoneTime.isEqual(importFlight.getFlightDateTime())
               || currentZoneTime.isAfter(importFlight.getFlightDateTime())) {
            List<EventNotification> eventNotifications = getEventNotificationByType(eventConfigurations,
                  DashboardEventTypes.Key.INBOUND_UCM_MESSAGE_SENT);
            if (!CollectionUtils.isEmpty(eventNotifications)) {
               for (EventNotification eventNotification : eventNotifications) {
                  if (Objects.nonNull(eventNotification)) {
                     boolean flightMatch = getSLAFlightDetailMatching(importFlight.getFlightType(),
                           importFlight.getAircraftType(), importFlight.getCarrierCode(), importFlight.getFlightKey(),
                           importFlight.getDateSTA().toLocalDate(), eventNotification);
                     if (flightMatch) {
                        LocalDateTime flightDateTime = Objects.isNull(eventNotification.getMinutes())
                              ? importFlight.getFlightDateTime()
                              : importFlight.getFlightDateTime().plusMinutes(eventNotification.getMinutes());
                        colorCode = getSLATimingColor(DashboardEventTypes.Key.INBOUND_UCM_MESSAGE_SENT,
                              eventNotification, flightDateTime, currentZoneTime);
                     }
                     if (!StringUtils.isEmpty(colorCode)) {
                        break;
                     }
                  }
               }
            }
         }
      } else if (!StringUtils.isEmpty(importFlight.getUcmSent()) && "YES".equalsIgnoreCase(importFlight.getUcmSent())) {
         colorCode = DashboardColorConstants.GREEN.value();
      }
      importFlight.setUcmSentColor(colorCode);
   }
}