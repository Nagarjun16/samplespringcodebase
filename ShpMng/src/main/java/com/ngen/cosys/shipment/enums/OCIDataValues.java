/**
 * This is an Enum for validating OCI line items specific to an country
 * requirement
 */
package com.ngen.cosys.shipment.enums;

public enum OCIDataValues {

   SHP_T("SHP/T"), CNE_T("CNE/T"), CNE_KC("CNE/KC"), CNE_U("CNE/U");

   public class Value {
      public static final String SHP_T = "SHP/T";
      public static final String CNE_T = "CNE/T";
      public static final String CNE_KC = "CNE/KC";
      public static final String CNE_U = "CNE/U";

      private Value() {
         // Nothing to add
      }
   }

   String type;

   OCIDataValues(String oci) {
      this.type = oci;
   }

   public String getType() {
      return this.type;
   }

}