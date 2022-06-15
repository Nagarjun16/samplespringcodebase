package com.ngen.cosys.impbd.constants;

public enum ArrivalManifestErrorCodes {
   
   DENSITYFIELDVALUEERROR("IMPARR01"),
   TOTALPIECEVALIDATION("IMPARR02"),
   CHECKSHIPMENTDESCRIPTION("IMPARR03"),
   INVALIDSHIPMENTDESC("IMPARR04"),
   INVALIDTOTALVALUE("IMPARR05"),
   PIECEFIELDVALUEVALIDATION("IMPARR06"),
   SUMPIECEVALIDATION("IMPARR07"),
   DUPLICATESHIPMENT("IMPARR08"),
   DUPLICATESHIPMENTFORSAMEULD("IMPARR09"),
   ORIGINVALIDATION("IMPARR10"),
   ORIGINDESSAME("IMPARR11"),
   DEPARTUREDATEEMPTY("IMPARR12"),
   OUTBOUNDFLIGHTERROR("IMPARR13"),
   DESTINATIONVALIDATION("IMPARR14"),
   SHIPMENTDESCRIPTIONVALIDATION("IMPARR15"),
   CARRIERDESTINATION("IMPARR16"),
   DUPLICATELOOSECARGO("IMPARR17"),
   DUPLICATEPARTSHIPMENT("impbd.part.duplicate.pls.update.ds"),
   DUPLICATETOTALSHIPMENT("impbd.shipment.already.existed"),
   CHECKSPLITSHIPMENT("error.check.split.shipment");
	
   private final String value;
   
   ArrivalManifestErrorCodes(String value){
      this.value = value;      
   }
   
   @Override
   public String toString() {
      return String.valueOf(this.value);
   }

}
