/**
 * Report Status Enums
 * 
 * @copyright SATS Singapore 2018-19
 */
package com.ngen.cosys.printer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Report Status Enums
 */
public enum PrintStatus {

   INITIATED("INITIATED"),
   PRINTED("PRINTED"),
   FAILED("FAILED");
   
   private String status;
   
   /**
    * Initialize
    * 
    * @param value
    *           status
    */
   PrintStatus(String value) {
      this.status = value;
   }
   
   /**
    * Get Printer status
    * 
    * @return
    */
   @JsonValue
   public String getStatus() {
      return this.status;
   }
   
   /**
    * Get Enum of Report status value
    * 
    * @param value
    * @return
    *       ReportStatus reportStatus
    */
   public static PrintStatus enumOf(String value) {
      //
      for (PrintStatus reportStatus : values()) {
         if (reportStatus.getStatus().equalsIgnoreCase(value)) {
            return reportStatus;
         }
      }
      //
      return null;
   }
   
}
