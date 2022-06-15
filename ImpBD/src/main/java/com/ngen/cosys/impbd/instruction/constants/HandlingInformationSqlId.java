package com.ngen.cosys.impbd.instruction.constants;

public enum HandlingInformationSqlId {
	SELECT_BREAKDOWNHANDLINGINFORMATION("selectImpBreakDownHandlingInformation"),	
	INSERT_BREAKDOWNHANDLINGINFORMATION("insertBreakDownHandlingInformation"),
	UPDATE_BREAKDOWNHANDLINGINFORMATION("updateBreakDownHandlingInformation"),
	DELETE_BREAKDOWNHANDLINGINFORMATION("deleteBreakDownHandlingInformation"),
	SELECT_BREAKDOWNHANDLINGINFORMATIONBYHOUSE("selectBreakDownHandlingInformationByHouse"),
	INSERT_BREAKDOWNHANDLINGINFORMATIONBYHOUSE("insertBreakDownHandlingInformationByHouse"),
	UPDATE_BREAKDOWNHANDLINGINFORMATIONBYHOUSE("updateBreakDownHandlingInformationByHouse"),
	DELETE_BREAKDOWNHANDLINGINFORMATIONBYHOUSE("deleteBreakDownHandlingInformationByHouse");
   private final String value;

   HandlingInformationSqlId(String value) {
      this.value = value;
   }

   @Override
   public String toString() {
      return String.valueOf(this.value);
   }
}