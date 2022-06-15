/**
 * Equation.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 * 
 */
package com.ngen.cosys.event.notification.enums;

/**
 * Equation Types
 * 
 * @author NIIT Technologies Ltd
 * 
 */
public enum Equation {

   EQUALS(Type.EQUALS), //
   NOT_EQUALS(Type.NOT_EQUALS), //
   LESS_THAN(Type.LESS_THAN), //
   LESS_THAN_EQUALS(Type.LESS_THAN_EQUALS), //
   GREATER_THAN(Type.GREATER_THAN), //
   GREATER_THAN_EQUALS(Type.GREATER_THAN_EQUALS);
   
   public class Type {
      //
      private Type() {}
      //
      public static final String EQUALS = "=";
      public static final String NOT_EQUALS = "<>";
      public static final String LESS_THAN = "<";
      public static final String LESS_THAN_EQUALS = "<=";
      public static final String GREATER_THAN = ">";
      public static final String GREATER_THAN_EQUALS = ">=";
   }
   
   private String type;
   
   /**
    * Initialize
    * 
    * @param equationType
    */
   Equation(String equationType) {
      this.type = equationType;
   }
   
   /**
    * GET Type
    * 
    * @return
    */
   public String type() {
      return this.type;
   }
   
   /**
    * EnumOf value
    * 
    * @param value
    * @return
    */
   public static Equation enumOf(String value) {
      //
      for (Equation equation : values())
         if (equation.type().equalsIgnoreCase(value))
            return equation;
      //
      return null;
   }
   
}
