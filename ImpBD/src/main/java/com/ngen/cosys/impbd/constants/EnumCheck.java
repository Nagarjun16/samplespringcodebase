package com.ngen.cosys.impbd.constants;

public enum EnumCheck {
   TRIPID(001);
   private final int value;
   
   EnumCheck(int value){
      this.value = value;
   }
   public int toInteger() {
      return value;
   }
   
}
