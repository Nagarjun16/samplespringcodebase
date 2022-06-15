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

   FMA_IDENTIFIER("FMA"), MRS_IDENTIFIER("MRS"), CMD_IDENTIFIER("CMD/"),

   ADD_ACTION_CODE_IDENTIFIER("A/"),

   MODIFY_ACTION_CODE_IDENTIFIER("M/"),

   DELETE_ACTION_CODE_IDENTIFIER("D/"),

   MASTER_AIR_WEB_BILL_IDENTIFIER("MWB/"),

   FLIGHT_IDENTIFIER("FLT/"),

   HOUSE_WAY_BILL_IDENTIFIER("HWB/"),

   MASTER_AWB_SHIPPER_NAME_AND_ADDRESS_IDENTIFIER("SHP/"),

   MASTER_AWB_CONSIGNEE_NAME_AND_ADDRESS_IDENTIFIER("CNE/"),

   PERMIT_DETAIL("TPT/"),

   TBD_PERMIT_IDENTIFIER("TBD/"),

   CUSTOMS_PERMIT_IDENTIFIER("CED/"),

   IMPORT_MASTER_AWB_DETAIL_IDENTIFIER("IMW/"),

   EXEMPTION_DETAILS_IDENTIFIER("EXP/"),

   DELIVERED_OR_UNDELIVERED_IDENTIFIER("DUI/"),

   SENDER_INFORMATION_IDENTIFIER("SND/"), SHIPMENT_DESCRIPTION_CODE_IDENTIFIER("T"), PERMT_TO_FOLLOW(
         Types.PERMT_TO_FOLLOW), IA_NUMBER(Types.IA_NUMBER), NOT_APPLICABLE("NA"), EXEMPTION_CODE(
               Types.EXEMPTION_CODE), TRANS_SHIPMENT("TS"), PERMIT_NUMBER(Types.PERMIT_NUMBER), MATCHED(
                     "Matched"), UNMATCHED_EXEMPTED("Unmatched-Exempted"), UNMATCHED_TRANS_SHIPMENT(
                           "Unmatched-Transhipment"), PART_SHIPMENT("PA"), UNDELIVERED_INDICATOR("UN");

   public class Types {

      public static final String IA_NUMBER = "IA";
      public static final String EXEMPTION_CODE = "EC";
      public static final String PERMIT_NUMBER = "PN";
      public static final String PERMT_TO_FOLLOW = "PTF";

      private Types() {
         // Do nothing
      }
   }

   public static final byte[] LINEFEED_SITA = { 0x0D, 0x0A };
   String textIdentifiers;
   public static final char SLANTCHAR = '/';

   TextMessageConstants(String textIdentifiers) {
      this.textIdentifiers = textIdentifiers;
   }

   public String getValue() {
      return this.textIdentifiers;
   }

}
