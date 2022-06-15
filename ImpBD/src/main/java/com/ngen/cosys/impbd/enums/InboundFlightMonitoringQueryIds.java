package com.ngen.cosys.impbd.enums;

import com.ngen.cosys.framework.constant.Action;

public enum InboundFlightMonitoringQueryIds {

   SQL_GET_Inbound_Flight_Monitoring("InboundFlightMonitoringListInfo");

   String queryId;

   public class CRUDType {
      public final String CREATE = Action.CREATE.toString();
      public final String UPDATE = Action.UPDATE.toString();
      public final String DELETE = Action.DELETE.toString();

      private CRUDType() {
         // Nothing to add
      }
   }

   InboundFlightMonitoringQueryIds(String queryId) {
      this.queryId = queryId;
   }

   public String getQueryId() {
      return this.queryId;
   }
}