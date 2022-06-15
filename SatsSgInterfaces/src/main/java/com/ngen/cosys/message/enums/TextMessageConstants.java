package com.ngen.cosys.message.enums;

public enum TextMessageConstants {

   SLANT_IDENTIFIER("/"),

   HYPHEN_IDENTIFIER("-"),

   LINE_FEEDER_IDENTIFIER("\n"),   

   SLANT("/"),

   CRLF("\r\n"),

   HIPHEN("-"),

   EMPTYSTRING(""),

   CONT("CONT"),

   ERROR("ERROR"),

   LAST("LAST"),

   FNA_IDENTIFIER("FNA"),

   FMA_IDENTIFIER("FMA"),

   CMD_IDENTIFIER("CMD/"),
  MRS_IDENTIFIER("MRS"),
   ADD_ACTION_CODE_IDENTIFIER("A/"),

   MODIFY_ACTION_CODE_IDENTIFIER("M/"),

   DELETE_ACTION_CODE_IDENTIFIER("D/"),

   MASTER_AIR_WEB_BILL_IDENTIFIER("MWB"),

   FLIGHT_IDENTIFIER("FLT"),

   HOUSE_WAY_BILL_IDENTIFIER("HWB"),

   MASTER_AWB_SHIPPER_NAME_AND_ADDRESS_IDENTIFIER("SHP"),

   MASTER_AWB_CONSIGNEE_NAME_AND_ADDRESS_IDENTIFIER("CNE"),

   PERMIT_DETAIL("TPT"),

   TBD_PERMIT_IDENTIFIER("TDB"),

   CUSTOMS_PERMIT_IDENTIFIER("CED"),

   IMPORT_MASTER_AWB_DETAIL_IDENTIFIER("IMW"),

   EXEMPTION_DETAILS_IDENTIFIER("EXP"),

   DELIVERED_OR_UNDELIVERED_IDENTIFIER("DUI"),

   SENDER_INFORMATION_IDENTIFIER("SND/"),
   SHIPMENT_DESCRIPTION_CODE_IDENTIFIER("T"),
   SHIPMENT_WWEIGHT_UNIT_CODE_IDENTIFIER("K"),
   ACK_IDENTIFIER("ACK"),
   INVALID_LATE_INDICATOR ("INVALID LATE INDICATOR "),
   FLIGHT_CLOSED_CMD_ROUTED_TO_TDB("FLIGHT CLOSED CMD ROUTED TO TDB"), 
   PSN_IDENTIFIER("PSN");
   String textIdentifiers;
   public static final byte[] LINEFEED_SITA = { 0x0D, 0x0A };
   public static final char SLANTCHAR = '/';
   
   public static final char HIPHENCHAR = '-';
   
   public static final String CUST_STS_COD = "SF";

   TextMessageConstants(String textIdentifiers) {
      this.textIdentifiers = textIdentifiers;
   }

   public String getValue() {
      return this.textIdentifiers;
   }

}
