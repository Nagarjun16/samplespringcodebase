package com.ngen.cosys.billing.sap.enums;

public enum FileNamePrefix {

   INVOICE("O096"), 
   CREDIT_NOTE("O097"), 
   MATERIAL_MASTER("O094"), 
   CUSTOMER_MASTER("O095"), 
   SALES_AND_DISTRIBUTION("I062");

   private final String value;

   private FileNamePrefix(String value) {
      this.value = value;
   }

   /**
    * Value
    * 
    * @return Value
    */
   public String value() {
      return this.value;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Enum#toString()
    */
   @Override
   public String toString() {
      return String.valueOf(this.value);
   }
}
