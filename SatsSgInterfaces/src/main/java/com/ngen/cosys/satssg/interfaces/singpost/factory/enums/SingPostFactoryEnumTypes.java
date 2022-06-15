package com.ngen.cosys.satssg.interfaces.singpost.factory.enums;

public enum SingPostFactoryEnumTypes {
   AA(Types.AA), HA(Types.HA), OF(Types.OF), LD(Types.LD), PA(Types.PA), DL(Types.DL);

   public class Types {

      public static final String HA = "bagHandOver";
      public static final String AA = "bagReceivingScan";
      public static final String OF = "offload";
      public static final String LD = "flightTouchDown";
      public static final String TA = "";

      public static final String PA = "preAlert";
      public static final String DL = "deliveryAcknowledgement";

      private Types() {
      }
   }

   String type;

   SingPostFactoryEnumTypes(String type) {
      this.type = type;
   }

   public String getType() {
      return type;
   }
}