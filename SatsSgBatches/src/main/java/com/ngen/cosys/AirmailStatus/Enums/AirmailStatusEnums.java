package com.ngen.cosys.AirmailStatus.Enums;

public enum AirmailStatusEnums {
   COSYS("COSYS"),
   
   SINGPOST("SINGPOST"),
   HA("HA"),
   LD("LD"),
   AA("AA"),
   TA("TA"),
   OF("OF"),
   
   MSS("MSS"),
   
   PROCESSED("PROCESSED"),
   SENT("SENT"),
   
   CHANNEL("MQ");
   
   private String queryId;

   AirmailStatusEnums(String queryId) {
      this.queryId = queryId;
   }

   public String getQueryid() {
      return this.queryId;
   }

}
