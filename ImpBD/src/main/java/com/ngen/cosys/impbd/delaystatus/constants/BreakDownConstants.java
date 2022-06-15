package com.ngen.cosys.impbd.delaystatus.constants;

public enum BreakDownConstants {
	
		GET_FLIGHTDETAIL("sqlGetDelayFlightInfo"),
		CLOSE_FLIGHT("sqlCloseFlight"),
		REOEN_FLIGHT("sqlReopenFlight");
	   
	   private final String value;

	   BreakDownConstants(String value) {
	      this.value = value;
	   }

	   @Override
	   public String toString() {
	      return String.valueOf(this.value);
	   }

}
