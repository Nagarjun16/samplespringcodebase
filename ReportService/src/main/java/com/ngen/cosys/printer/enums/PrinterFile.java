/**
 * Print File Names
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.printer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Print File Names
 */
public enum PrinterFile {
   
   PRINT_AWB_BARCODE("AwbBarcode"), //
   PRINT_FLIGHT_POUCH_QR_CODE("FlightPouchQRCode"), //
   PRINT_ULDTAG("UldTag"), //
   PRINT_XPS_AWB_BARCODE("XPSAwbBarCode"), //
   PRINT_XPS_ULD_TAG("XPSUldTag"), //
   PRINT_HANDOVER_FORM("HandoverForm"), //
   PRINT_HANDOVER_REPRINT_FORM("HandoverReprintForm"), //
   PRINT_OFFLOAD_FORM("OffloadForm"), //
   PRINT_PICK_ORDER("PickOrder"), //
   PRINT_DELIVERY_ORDER("DeliveryOrder"), //
   PRINT_TRANSFER_MANIFEST("TransferManifest"), //
   PRINT_CARGO_MANIFEST("CargoManifest"), //
   PRINT_NAWB("NAWB"), 
  // PRINT_EPICK_ORDER("EPickOrder"),
   PRINT_BUP_DELIVERY_ORDER("BUPDeliveryOrder"),
   PRINT_LASER("LASER");
	
   
   private String name;
   
   /**
    * Initialize
    * 
    * @param name
    *           name
    */
   PrinterFile(String name) {
      this.name = name;
   }
   
   /**
    * @param name
    * @return name
    */
   @JsonValue
   public String getName() {
      return this.name;
   }
   
   /**
    * Gets Enum of value
    * 
    * @param value
    * @return
    */
   public static PrinterFile enumOf(String value) {
      //
      for (PrinterFile printerFile : values())
         if (printerFile.getName().equalsIgnoreCase(value)) 
            return printerFile;
      //
      return null;
   }
   
}
