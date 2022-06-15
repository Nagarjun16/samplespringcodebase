/**
 * EntityEventTypes.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.enums;

/**
 * This enum is used for Event Entity
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum EntityEventTypes {

   // AWB - OUTBOUND
   AWB_OUTBOUND_LOAD_SHIPMENT(Key.AWB_OUTBOUND_UNLOAD), //
   AWB_OUTBOUND_UNLOAD(Key.AWB_OUTBOUND_UNLOAD), //
   AWB_OUTBOUND_OFFLOAD(Key.AWB_OUTBOUND_OFFLOAD), //
   AWB_OUTBOUND_MANIFEST(Key.AWB_OUTBOUND_MANIFEST), //
   AWB_OUTBOUND_FLIGHT_COMPLETE(Key.AWB_OUTBOUND_FLIGHT_COMPLETE), //
   // AWB - INBOUND
   AWB_INBOUND_DOCUMENT_COMPLETE(Key.AWB_INBOUND_DOCUMENT_COMPLETE), //
   AWB_INBOUND_DOCUMENT_RELEASE(Key.AWB_INBOUND_DOCUMENT_RELEASE), //
   AWB_INBOUND_BREAKDOWN_COMPLETE(Key.AWB_INBOUND_BREAKDOWN_COMPLETE), //
   AWB_INBOUND_DELIVERED(Key.AWB_INBOUND_DELIVERED), //
   AWB_INBOUND_PICK_ORDER_REQUEST(Key.AWB_INBOUND_PICK_ORDER_REQUEST), //
   // FLIGHT - OUTBOUND
   FLIGHT_OUTBOUND_DLS_NFM(Key.FLIGHT_OUTBOUND_DLS_NFM), //
   FLIGHT_OUTBOUND_COMPLETE(Key.FLIGHT_OUTBOUND_COMPLETE), //
   FLIGHT_OUTBOUND_DLS_COMPLETE(Key.FLIGHT_OUTBOUND_DLS_COMPLETE), //
   FLIGHT_OUTBOUND_NTM(Key.FLIGHT_OUTBOUND_NTM), //
   FLIGHT_OUTBOUND_MANIFEST_COMPLETE(Key.FLIGHT_OUTBOUND_MANIFEST_COMPLETE), //
   FLIGHT_OUTBOUND_OFFLOAD_FINALIZATION(Key.FLIGHT_OUTBOUND_OFFLOAD_FINALIZATION), //
   // FLIGHT - INBOUND
   FLIGHT_INBOUND_FFM(Key.FLIGHT_INBOUND_FFM), //
   FLIGHT_INBOUND_ARRIVAL(Key.FLIGHT_INBOUND_ARRIVAL), //
   FLIGHT_INBOUND_RAMP_CHECK_IN(Key.FLIGHT_INBOUND_RAMP_CHECK_IN), //
   FLIGHT_INBOUND_BREAKDOWN_COMPLETE(Key.FLIGHT_INBOUND_BREAKDOWN_COMPLETE), //
   FLIGHT_INBOUND_COMPLETE(Key.FLIGHT_INBOUND_COMPLETE), //
   FLIGHT_INBOUND_ULD_FINALIZED(Key.FLIGHT_INBOUND_ULD_FINALIZED), //
   // ULD - OUTBOUND
   ULD_OUTBOUND_OFFLOAD(Key.ULD_OUTBOUND_OFFLOAD), //
   // ULD - INBOUND
   ULD_INBOUND_CHECK_IN(Key.ULD_INBOUND_CHECK_IN);
   
   public class Key {
      //
      private Key() {}
      //
      public static final String AWB_OUTBOUND_UNLOAD = "OutboundUnloadShipment";
      public static final String AWB_OUTBOUND_OFFLOAD = "OutboundShipmentOffloaded";
      public static final String AWB_OUTBOUND_MANIFEST = "OutboundShipmentManifested";
      public static final String AWB_OUTBOUND_FLIGHT_COMPLETE = "OutboundShipmentFlightCompleted";
      // AWB - INBOUND
      public static final String AWB_INBOUND_DOCUMENT_COMPLETE = "InboundShipmentDocumentComplete";
      public static final String AWB_INBOUND_DOCUMENT_RELEASE = "InboundShipmentDocumentRelease";
      public static final String AWB_INBOUND_BREAKDOWN_COMPLETE = "InboundShipmentBreakDownComplete";
      public static final String AWB_INBOUND_DELIVERED = "InboundShipmentDelivered";
      public static final String AWB_INBOUND_PICK_ORDER_REQUEST = "InboundShipmentPickOrderRequest";
      // FLIGHT - OUTBOUND
      public static final String FLIGHT_OUTBOUND_DLS_NFM = "DLSNFMEvent";
      public static final String FLIGHT_OUTBOUND_COMPLETE = "OutboundFlightComplete";
      public static final String FLIGHT_OUTBOUND_DLS_COMPLETE = "OutboundFlightDLS";
      public static final String FLIGHT_OUTBOUND_NTM = "OutboundNTMEvent";
      public static final String FLIGHT_OUTBOUND_MANIFEST_COMPLETE = "OutboundFlightManifestCompletedEvent";
      public static final String FLIGHT_OUTBOUND_OFFLOAD_FINALIZATION = "OutboundOffloadFinalizationEvent";
      // FLIGHT - INBOUND
      public static final String FLIGHT_INBOUND_FFM = "InboundFFMReceived";
      public static final String FLIGHT_INBOUND_ARRIVAL = "InboundFlightArrival";
      public static final String FLIGHT_INBOUND_RAMP_CHECK_IN = "InboundFlightRampCheckIn";
      public static final String FLIGHT_INBOUND_BREAKDOWN_COMPLETE = "InboundFlightBreakDownComplete";
      public static final String FLIGHT_INBOUND_COMPLETE = "InboundFlightComplete";
      public static final String FLIGHT_INBOUND_ULD_FINALIZED = "InboundULDFinalized";
      // ULD - OUTBOUND
      public static final String ULD_OUTBOUND_OFFLOAD = "OutboundOffloadULD";
      // ULD - INBOUND
      public static final String ULD_INBOUND_CHECK_IN = "InboundULDCheckIn";
      
   }
   
   private String value;
   
   /**
    * Initialize
    * 
    * @param event
    */
   EntityEventTypes(String event) {
      this.value = event;
   }
   
   /**
    * @return
    */
   public String value() {
      return this.value;
   }
   
   /**
    * Enum Of the value
    * 
    * @param value
    * @return
    */
   public static EntityEventTypes enumOf(String value) {
      //
      for (EntityEventTypes event : values())
         if (event.value().equalsIgnoreCase(value))
            return event;
      //
      return null;
   }
   
}
