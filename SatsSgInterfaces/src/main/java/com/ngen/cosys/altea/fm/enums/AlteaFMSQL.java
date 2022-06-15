/**
 * AlteaFMSQL.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.enums;

/**
 * This Enum used for Altea FM SQL mapper values
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum AlteaFMSQL {

   SQL_VERIFY_ALTEA_FM_CONFIGURED("sqlVerifyAlteaFMConfigured"), //
   SQL_SELECT_ALTEA_FM_PER_FLIGHT_MESSAGE_COUNT("sqlSelectAlteaFMPerFlightMessageCount"), //
   SQL_SELECT_ALTEA_FM_SENT_MESSAGE_COUNT_BY_FLIGHT("sqlSelectAlteaFMSentMessageCountByFlight"), //
   SQL_SELECT_ALTEA_FM_CARGO_DATA("sqlSelectAlteaFMCargoData");
   
   private String queryId;
   
   /**
    * @param queryId
    */
   private AlteaFMSQL(String queryId) {
      this.queryId = queryId;
   }
   
   /**
    * @return
    */
   public String getQueryId() {
      return this.queryId;
   }
   
}
