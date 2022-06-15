package com.ngen.cosys.cscc.constant;

public final class ResponseStatus {
   private String id;

   private ResponseStatus(String anID) {
      this.id = anID;
   }

   public String toString() {
      return this.id;
   }

   public static final String SUCCESS = new ResponseStatus("Success").toString();
   public static final String FAIL = new ResponseStatus("Fail").toString();
}
