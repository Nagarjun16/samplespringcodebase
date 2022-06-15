/**
 * {@link DashboardEventTypes}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.enums;

/**
 * Dashboard Event Types
 * 
 * @author NIIT Technologies Ltd
 */
public enum DashboardEventTypes {

   OUTBOUND_BUILDUP_COMPLETE(Key.OUTBOUND_BUILDUP_COMPLETE), //
   OUTBOUND_DLS_COMPLETE(Key.OUTBOUND_DLS_COMPLETE), //
   OUTBOUND_MANIFEST_COMPLETE(Key.OUTBOUND_MANIFEST_COMPLETE), //
   OUTBOUND_NOTOC_FINALIZATION(Key.OUTBOUND_NOTOC_FINALIZATION), //
   OUTBOUND_SERVICE_REPORT_COMPLETE(Key.OUTBOUND_SERVICE_REPORT_COMPLETE), //
   OUTBOUND_FLIGHT_COMPLETE(Key.OUTBOUND_FLIGHT_COMPLETE), //
   OUTBOUND_FLIGHT_POUCH_COMPLETE(Key.OUTBOUND_FLIGHT_POUCH_COMPLETE), //
   OUTBOUND_ACCEPTED_DG_SHIPMENTS(Key.OUTBOUND_ACCEPTED_DG_SHIPMENTS), //
   OUTBOUND_ACCEPTED_XPS_SHIPMENTS(Key.OUTBOUND_ACCEPTED_XPS_SHIPMENTS), //
   OUTBOUND_ACCEPTED_RAC_SHIPMENTS(Key.OUTBOUND_ACCEPTED_RAC_SHIPMENTS), //
   OUTBOUND_ACCEPTED_PIL_SHIPMENTS(Key.OUTBOUND_ACCEPTED_PIL_SHIPMENTS), //
   OUTBOUND_ACCEPTED_VAL_SHIPMENTS(Key.OUTBOUND_ACCEPTED_VAL_SHIPMENTS), //
   OUTBOUND_ACCEPTED_AVI_SHIPMENTS(Key.OUTBOUND_ACCEPTED_AVI_SHIPMENTS), //
   OUTBOUND_ACCEPTED_HUM_SHIPMENTS(Key.OUTBOUND_ACCEPTED_HUM_SHIPMENTS), //
   OUTBOUND_ACCEPTED_OTH_SHIPMENTS(Key.OUTBOUND_ACCEPTED_OTH_SHIPMENTS), //
   OUTBOUND_ASSIGNED_DG_SHIPMENTS(Key.OUTBOUND_ASSIGNED_DG_SHIPMENTS), //
   OUTBOUND_ASSIGNED_XPS_SHIPMENTS(Key.OUTBOUND_ASSIGNED_XPS_SHIPMENTS), //
   OUTBOUND_ASSIGNED_RAC_SHIPMENTS(Key.OUTBOUND_ASSIGNED_RAC_SHIPMENTS), //
   OUTBOUND_ASSIGNED_PIL_SHIPMENTS(Key.OUTBOUND_ASSIGNED_PIL_SHIPMENTS), //
   OUTBOUND_ASSIGNED_VAL_SHIPMENTS(Key.OUTBOUND_ASSIGNED_VAL_SHIPMENTS), //
   OUTBOUND_ASSIGNED_AVI_SHIPMENTS(Key.OUTBOUND_ASSIGNED_AVI_SHIPMENTS), //
   OUTBOUND_ASSIGNED_HUM_SHIPMENTS(Key.OUTBOUND_ASSIGNED_HUM_SHIPMENTS), //
   OUTBOUND_ASSIGNED_OTH_SHIPMENTS(Key.OUTBOUND_ASSIGNED_OTH_SHIPMENTS), //
   //
   INBOUND_FFM_RECEIVED(Key.INBOUND_FFM_RECEIVED), //
   INBOUND_RAMP_CHECKIN(Key.INBOUND_RAMP_CHECKIN), //
   INBOUND_DOCUMENT_COMPLETE(Key.INBOUND_DOCUMENT_COMPLETE), //
   INBOUND_BREAKDOWN_COMPLETE(Key.INBOUND_BREAKDOWN_COMPLETE), //
   INBOUND_SERVICE_REPORT_COMPLETE(Key.INBOUND_SERVICE_REPORT_COMPLETE), //
   INBOUND_THROUGH_TRANSIT_FINALIZATION(Key.INBOUND_THROUGH_TRANSIT_FINALIZATION), //
   INBOUND_FLIGHT_COMPLETE(Key.INBOUND_FLIGHT_COMPLETE), //
   INBOUND_FLIGHT_DISCREPANCY_LIST_SENT(Key.INBOUND_FLIGHT_DISCREPANCY_LIST_SENT), //
   INBOUND_FLIGHT_CLOSE(Key.INBOUND_FLIGHT_CLOSE), //
   INBOUND_MANIFESTED_DG_SHIPMENTS(Key.INBOUND_MANIFESTED_DG_SHIPMENTS), //
   INBOUND_MANIFESTED_XPS_SHIPMENTS(Key.INBOUND_MANIFESTED_XPS_SHIPMENTS), //
   INBOUND_MANIFESTED_RAC_SHIPMENTS(Key.INBOUND_MANIFESTED_RAC_SHIPMENTS), //
   INBOUND_MANIFESTED_PIL_SHIPMENTS(Key.INBOUND_MANIFESTED_PIL_SHIPMENTS), //
   INBOUND_MANIFESTED_VAL_SHIPMENTS(Key.INBOUND_MANIFESTED_VAL_SHIPMENTS), //
   INBOUND_MANIFESTED_AVI_SHIPMENTS(Key.INBOUND_MANIFESTED_AVI_SHIPMENTS), //
   INBOUND_MANIFESTED_HUM_SHIPMENTS(Key.INBOUND_MANIFESTED_HUM_SHIPMENTS), //
   INBOUND_MANIFESTED_OTH_SHIPMENTS(Key.INBOUND_MANIFESTED_OTH_SHIPMENTS);
   
   //
   public class Key {
      //
      private Key() {}
      // EXPORT Events
      public static final String OUTBOUND_BUILDUP_COMPLETE = "OutboundFlightBuildupCompletedEvent";
      public static final String OUTBOUND_DLS_COMPLETE = "OutboundFlightDLS";
      public static final String OUTBOUND_MANIFEST_COMPLETE = "OutboundFlightManifestCompletedEvent";
      public static final String OUTBOUND_NOTOC_FINALIZATION = "NOTOCFinlization";
      public static final String OUTBOUND_SERVICE_REPORT_COMPLETE = "OutwardServiceReportCompleted";
      public static final String OUTBOUND_FLIGHT_COMPLETE = "OutboundFlightComplete";
      public static final String OUTBOUND_FLIGHT_POUCH_COMPLETE = "OutboundFlightPouchCompleted";
      public static final String OUTBOUND_ACCEPTED_DG_SHIPMENTS = "OutboundShpAcptDGD";
      public static final String OUTBOUND_ACCEPTED_XPS_SHIPMENTS = "OutboundShpAcptXPS";
      public static final String OUTBOUND_ACCEPTED_RAC_SHIPMENTS = "OutboundShpAcptRAC";
      public static final String OUTBOUND_ACCEPTED_PIL_SHIPMENTS = "OutboundShpAcptPIL";
      public static final String OUTBOUND_ACCEPTED_VAL_SHIPMENTS = "OutboundShpAcptVAL";
      public static final String OUTBOUND_ACCEPTED_AVI_SHIPMENTS = "OutboundShpAcptAVI";
      public static final String OUTBOUND_ACCEPTED_HUM_SHIPMENTS = "OutboundShpAcptHUM";
      public static final String OUTBOUND_ACCEPTED_OTH_SHIPMENTS = "OutboundShpAcptOTH";
      public static final String OUTBOUND_ASSIGNED_DG_SHIPMENTS = "OutboundShpAsgnDGD";
      public static final String OUTBOUND_ASSIGNED_XPS_SHIPMENTS = "OutboundShpAsgnXPS";
      public static final String OUTBOUND_ASSIGNED_RAC_SHIPMENTS = "OutboundShpAsgnRAC";
      public static final String OUTBOUND_ASSIGNED_PIL_SHIPMENTS = "OutboundShpAsgnPIL";
      public static final String OUTBOUND_ASSIGNED_VAL_SHIPMENTS = "OutboundShpAsgnVAL";
      public static final String OUTBOUND_ASSIGNED_AVI_SHIPMENTS = "OutboundShpAsgnAVI";
      public static final String OUTBOUND_ASSIGNED_HUM_SHIPMENTS = "OutboundShpAsgnHUM";
      public static final String OUTBOUND_ASSIGNED_OTH_SHIPMENTS = "OutboundShpAsgnOTH";
      // IMPORT Events
      public static final String INBOUND_FFM_RECEIVED = "InboundFFMReceived";
      public static final String INBOUND_RAMP_CHECKIN = "InboundFlightRampCheckIn";
      public static final String INBOUND_DOCUMENT_COMPLETE = "InboundShipmentDocumentComplete";
      public static final String INBOUND_BREAKDOWN_COMPLETE = "InboundFlightBreakDownComplete";
      public static final String INBOUND_SERVICE_REPORT_COMPLETE = "InwardsFlightServiceReportEvent";
      public static final String INBOUND_THROUGH_TRANSIT_FINALIZATION = "TranshipmentThroughTransitFinalizedByFlight";
      public static final String INBOUND_FLIGHT_COMPLETE = "InboundFlightComplete";
      public static final String INBOUND_FLIGHT_DISCREPANCY_LIST_SENT = "InboundFlightDiscrepancyList";
      public static final String INBOUND_FLIGHT_CLOSE = "InboundFlightCloseEvent";
      public static final String INBOUND_MANIFESTED_DG_SHIPMENTS = "InboundManiShipDGD";
      public static final String INBOUND_MANIFESTED_XPS_SHIPMENTS = "InboundManiShipXPS";
      public static final String INBOUND_MANIFESTED_RAC_SHIPMENTS = "InboundManiShipRAC";
      public static final String INBOUND_MANIFESTED_PIL_SHIPMENTS = "InboundManiShipPIL";
      public static final String INBOUND_MANIFESTED_VAL_SHIPMENTS = "InboundManiShipVAL";
      public static final String INBOUND_MANIFESTED_AVI_SHIPMENTS = "InboundManiShipAVI";
      public static final String INBOUND_MANIFESTED_HUM_SHIPMENTS = "InboundManiShipHUM";
      public static final String INBOUND_MANIFESTED_OTH_SHIPMENTS = "InboundManiShipOTH";
      public static final String INBOUND_UCM_MESSAGE_SENT = "InboundUldUcmMessageEvent";      
   }
   
   private String value;
   
   /**
    * Initialize
    * 
    * @param event
    */
   DashboardEventTypes(String event) {
      this.value = event;
   }
   
   /**
    * @return
    */
   public String value() {
      return this.value;
   }

   /**
    * Enum of the event
    * 
    * @param value
    * @return
    */
   public static DashboardEventTypes enumOf(String value) {
      //
      for (DashboardEventTypes event : values())
         if (event.value().equalsIgnoreCase(value))
            return event;
      //
      return null;
   }
   
}
