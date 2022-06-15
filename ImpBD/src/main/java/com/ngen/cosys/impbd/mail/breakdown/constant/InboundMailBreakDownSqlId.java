package com.ngen.cosys.impbd.mail.breakdown.constant;

public enum InboundMailBreakDownSqlId {

   GET_FLIGHTDETAIL("flightdetailmailbreakdown"),
   GET_FLIGHTDETAILS("getflightdetailmailbreakdown"),
   SEARCH_MAILBREAKDOWN_WORKINGLIST("getmailbreakdownworkinglist"),
   GET_MAILBAGNUMBERCOUNT("getmailbagnumbercount"),
   GET_BREAKDOWN_COMPLETE_CHECK("checkMailBreakDownCompleted"),
   SEARCH_MAILBREAKDOWN("getmailbreakdown");
   
   private final String value;

   InboundMailBreakDownSqlId(String value) {
      this.value = value;
   }

   @Override
   public String toString() {
      return String.valueOf(this.value);
   }
}
