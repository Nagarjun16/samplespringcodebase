package com.ngen.cosys.impbd.enums;

public enum DisplayCpmQueryIds {

   SQL_GET_CPM_INFO("sqlGetDisplayCPMInfo");

   String queryId;

   DisplayCpmQueryIds(String queryId) {
      this.queryId = queryId;
   }

   public String getQueryId() {
      return this.queryId;
   }

}