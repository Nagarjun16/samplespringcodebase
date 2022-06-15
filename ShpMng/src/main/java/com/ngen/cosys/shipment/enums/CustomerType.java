package com.ngen.cosys.shipment.enums;

public enum CustomerType {

   CONSIGNEE(CType.CONSIGNEE),
   SHIPPER(CType.SHIPPER),
   ALSO_NOTIFY(CType.ALSO_NOTIFY);
   
   String type;
   
   CustomerType(String type) {
      this.type = type;
   }
   
   public String getType() {
      return this.type;
   }
   
   class CType {
      private static final String CONSIGNEE = "CNE";
      private static final String SHIPPER = "SHP";
      private static final String ALSO_NOTIFY = "NFY";
   }
}
