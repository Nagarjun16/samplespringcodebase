package com.ngen.cosys.icms.util;

public enum BookingRemark {
   WORK_LIST_RMK("WORK_LIST_RMK"), MAN_RMK("MAN_RMK"), ADD_RMK("ADD_RMK");

   private String description;

   private BookingRemark(String desc) {
      this.description = desc;
   }

   public String getDescription() {
      return description;
   }
}