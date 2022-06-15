package com.ngen.cosys.impbd.constants;

public enum StatusEnum {
   USERSTATUS("SYSADMIN");
   private final String value;

   StatusEnum(String value) {
      this.value = value;
   }

   public String toString() {
      return String.valueOf(this.value);
   }

}
