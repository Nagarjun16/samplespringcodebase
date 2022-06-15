package com.ngen.cosys.impbd.constants;

public enum HardcodedParam {

   WEIGHT_UNIT("L"),
   PART_SHIPMENT("P"),
   DIVIDE_SHIPMENT("D"),
   SPLIT_SHIPMENT("S"),
   TOTAL_SHIPMENT("T"),
   POUND_CODE("L"),
   WEIGHT_CODE("K"),
   FLIGHT_DELAY("FLIGHT DELAY"),
   CREATED_USER("SYSADMIN"),
   LOOSECARGO("Bulk"),
   TT("TT"),
   TTT("TTT"),
   TTH("TTH"),
   NOBREAK("NOBREAK"),
   BREAK("BREAK");

   private final String type;

   private HardcodedParam(String value) {
      this.type = value;
   }

   /*
    * (non-Javadoc)
    * @see java.lang.Enum#toString()
    */
   @Override
   public String toString() {
      return String.valueOf(this.type);
   }
}