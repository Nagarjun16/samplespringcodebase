package com.ngen.cosys.satssg.interfaces.singpost.factory.enums;

public enum BookingStatusCode {
   UU(Types.UPDATE);

   public class Types {

      public static final String UPDATE = "UU";

      private Types() {
      }
   }

   String type;

   BookingStatusCode(String type) {
      this.type = type;
   }

   public String getType() {

      return type;
   }
}