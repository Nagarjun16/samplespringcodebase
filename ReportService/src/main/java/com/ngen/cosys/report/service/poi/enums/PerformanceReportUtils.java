package com.ngen.cosys.report.service.poi.enums;

public enum PerformanceReportUtils {

   DATEMONTH_FORMAT(Type.DATEMONTH_FORMAT),
   SUMMARY(Type.SUMMARY),
   LATE_LODGE_IN(Type.LATE_LODGE_IN),
   YES(Type.YES),
   NO(Type.NO),
   HAEQUALSPA(Type.HAEQUALSPA),
   HALESSTHANPA(Type.HALESSTHANPA),
   HAHAS4HRSSCANTIME(Type.HAHAS4HRSSCANTIME),
   PAGREATERTHANTA(Type.PAGREATERTHANTA),
   HALESSTHANAA24HRS(Type.HALESSTHANAA24HRS),
   HAGREATERTHANAA24HRS(Type.HAGREATERTHANAA24HRS),
   PALESSTHANIPSAAHAVINGAA(Type.PALESSTHANIPSAAHAVINGAA),
   PALESSTHANIPSAAHAVINGNOHA(Type.PALESSTHANIPSAAHAVINGNOHA),
   HA_MINUS_PA_FLT(Type.HA_MINUS_PA_FLT),
   HA_MINUS_AA_IPS(Type.HA_MINUS_AA_IPS),
   PA_FLT_MINUS_AA_IPS(Type.PA_FLT_MINUS_AA_IPS),
   PA_FLT_MINUS_AA_RFDT(Type.PA_FLT_MINUS_AA_RFDT),
   PA_FLT_MINUS_TA(Type.PA_FLT_MINUS_TA),
   NA(Type.NA),
   SAME_FLIGHT(Type.SAME_FLIGHT),
   EARLY(Type.EARLY)
   ;
   
   public class Type {

      private Type() {}

      public static final String DATEMONTH_FORMAT = "ddMMM";
      public static final String SUMMARY = "Summary";
      public static final String LATE_LODGE_IN = "Late Lodge In";
      public static final String YES = "Y";
      public static final String NO = "N";
      public static final String HAEQUALSPA = "HaEqualsPa";
      public static final String HALESSTHANPA = "HaLessThenPa";
      public static final String HAHAS4HRSSCANTIME = "HaHas4HrsScanTime";
      public static final String PAGREATERTHANTA = "PaGreaterthanTA";
      public static final String HALESSTHANAA24HRS = "HaLessThanAAByTwentyFourHrs";
      public static final String HAGREATERTHANAA24HRS = "HasGeaterThanAAByTwentyFourHrs";
      public static final String PALESSTHANIPSAAHAVINGAA = "PALessThanIPSAAHavingHA";
      public static final String PALESSTHANIPSAAHAVINGNOHA = "PALessThanIPSAAHavingNoHA";
      public static final String HA_MINUS_PA_FLT = "HA_MINUS_PA_FLT";
      public static final String HA_MINUS_AA_IPS = "HA_MINUS_AA_IPS";
      public static final String PA_FLT_MINUS_AA_IPS = "PA_FLT_MINUS_AA_IPS";
      public static final String PA_FLT_MINUS_AA_RFDT = "PA_FLT_MINUS_AA_RFDT";
      public static final String PA_FLT_MINUS_TA = "PA_FLT_MINUS_TA";
      public static final String NA = "N.A";
      public static final String LESSTHAN4HRS = "<4 Hrs";
      public static final String FOURTO12HRS = "4-12 Hrs";
      public static final String TWELVETO18HRS = "12-18 Hrs";
      public static final String EIGHTEENTO24HRS = "18-24 Hrs";
      public static final String GREATERTHAN24HRS = ">24 Hrs";
      public static final String SAME_FLIGHT = "SAME FLIGHT";
      public static final String EARLY = "Early";
   }

   private String name;

   /**
    * @param value
    */
   PerformanceReportUtils(String value) {
      this.name = value;
   }

   /**
    * @return
    */
   public String getName() {
      return this.name;
   }
  
}
