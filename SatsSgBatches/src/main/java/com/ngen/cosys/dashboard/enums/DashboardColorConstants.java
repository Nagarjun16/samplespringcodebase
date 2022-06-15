/**
 * {@link DashboardColorConstants}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.dashboard.enums;

/**
 * Dashboard Color Constants
 * 
 * @author NIIT Technologies Ltd
 */
public enum DashboardColorConstants {

   BLUE(Code.BLUE), //
   GRAY(Code.GRAY), //
   AMBER(Code.AMBER), //
   GREEN(Code.GREEN), //
   RED(Code.RED);
   
   public class Code {
      //
      private Code() {}
      //
      public static final String BLUE = "BLUE";
      public static final String GRAY = "GRAY";
      public static final String AMBER = "AMBER";
      public static final String GREEN = "GREEN";
      public static final String RED = "RED";
      
   }
   
   private String value;
   
   /**
    * Initialize
    * 
    * @param color
    */
   private DashboardColorConstants(String color) {
      this.value = color;
   }
   
   /**
    * @return
    */
   public String value() {
      return this.value;
   }
   
   /**
    * Enum of the color code
    * 
    * @param color
    * @return
    */
   public static DashboardColorConstants enumOf(String color) {
      //
      for (DashboardColorConstants colorConstant : values())
         if (colorConstant.value().equalsIgnoreCase(color))
            return colorConstant;
      //
      return null;
   }
   
}
