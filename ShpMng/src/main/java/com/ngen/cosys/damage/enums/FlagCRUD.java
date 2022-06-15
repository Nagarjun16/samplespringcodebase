/**
 * 
 * FlagCRUD.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          05 MAY, 2018   NIIT      -
 */
package com.ngen.cosys.damage.enums;

/**
 * This enum is used for Flag CRUD 
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum FlagCRUD {

   CREATE(Type.CREATE),
   UPDATE(Type.UPDATE),
   DELETE(Type.DELETE);
   
   public class Type {
      //
      private Type() {}
      //
      public static final String CREATE = "C";
      public static final String UPDATE = "U";
      public static final String DELETE = "D";
   }
   
   String value;
   
   FlagCRUD(String value) {
      this.value = value;
   }
   
   public String getValue() {
      return this.value;
   }
   
}
