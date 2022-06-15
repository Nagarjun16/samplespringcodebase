package com.ngen.cosys.satssginterfaces.mss.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AEDWeightDetail {
   
   @JacksonXmlProperty(localName = "GHAMAWBINFO_ID")
   private String ghamawbinfoID;

   @JacksonXmlProperty(localName = "RECORD_IND")
   private String recordInd;

   @JacksonXmlProperty(localName = "TOTAL_ACTUAL_WT")
   private String totalActualWt;

   @JacksonXmlProperty(localName = "TOTAL_ACTUAL_WT_UOM")
   private String totalActualWtUom;

   @JacksonXmlProperty(localName = "PERCENT_TOTAL_GROSS_WT")
   private String PercentTotalGrossWt;
      
   @JacksonXmlProperty(localName = "MAWB_NO")
   private String mawbNo;

   @JacksonXmlProperty(localName = "EXEMPTION_CODE")
   private String exemptionCode;
   
   
}
