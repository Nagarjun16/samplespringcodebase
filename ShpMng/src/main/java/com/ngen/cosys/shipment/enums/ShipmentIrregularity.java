package com.ngen.cosys.shipment.enums;

public enum ShipmentIrregularity {
   SHPIRR_FLT_KEY("SHPIRR_FLT_KEY"),
   IRR_DETAIL_ERR("irregularityDetails["),
   IRR_FLT_KEY("].flightKey"),
   IRR_FLT_DATE("].flightDate"),
   SHPIRR_FLT_DATE("SHPIRR_FLT_DATE"),
   VAL_FID_IN("VAL_FID_IN"),
   FLT_KEY("flightKey"),
   FLT_DATE("flightDate")
   ;
   String enumId; 
   
   ShipmentIrregularity(String enumId){
      this.enumId = enumId;
   }
   
   public String getEnum() {
      return this.enumId;
   }
}
