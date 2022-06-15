/**
 * Printer Type Enums
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.printer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Printer Type Enums
 */
public enum PrinterType {

   AWB_BARCODE("AWB-BARCODE"), // Honeywell - NGCUATPRT03 #
   FLIGHT_POUCH_QR_CODE("FLIGHT-POUCH-QR-CODE"), // Honeywell -
   ULD_TAG("ULD-TAG"), // SATO CL4NX - NGCUATPRT02
   //
   XPS_AWB_BARCODE("XPS-AWB-BARCODE"), // RFID Tag
   XPS_ULD_TAG("XPS-ULD-TAG"), // RFID ULD Tag
   //
   HANDOVER_FORM("HANDOVER-FORM"), // Thermal(Epson) - NGCUATPRT01
   HANDOVER_REPRINT_FORM("HANDOVER-REPRINT-FORM"), // Thermal(Epson) - NGCUATPRT01
   OFFLOAD_FORM("OFFLOAD-FORM"), // Thermal(Epson) - NGCUATPRT01
   //
   PICK_ORDER("PICK-ORDER"), // DotMatrix - NGCUATPRT08
   DELIVERY_ORDER("DELIVERY-ORDER"), // DotMatrix - NGCUATPRT08
   TRANSFER_MANIFEST("TRANSFER-MANIFEST"), // DotMatrix - NGCUATPRT08
   CARGO_MANIFEST("CARGO-MANIFEST"), // CARGO MANIFEST
   //
   NAWB("NAWB"), // DotMatrix - NGCUATPRT04
   LASER("LASER"), // Invoice, NOTOC - Konica
  // EPICK_ORDER("EPICK-ORDER"),

   PRINT_BUP_DELIVERY_ORDER("BUPDeliveryOrder");

   private String type;

   /**
    * Initialize
    * 
    * @param printerType
    *           printerType
    */
   PrinterType(String printerType) {
      this.type = printerType;
   }

   /**
    * @param printerType
    * @return type
    */
   @JsonValue
   public String getType() {
      return this.type;
   }

   /**
    * Gets Enum of value
    * 
    * @param value
    * @return
    */
   public static PrinterType enumOf(String value) {
      //
      for (PrinterType printerType : values())
         if (printerType.getType().equalsIgnoreCase(value))
            return printerType;
      //
      return null;
   }

}
