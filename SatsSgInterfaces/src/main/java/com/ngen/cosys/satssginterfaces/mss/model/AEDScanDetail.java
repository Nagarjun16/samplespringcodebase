package com.ngen.cosys.satssginterfaces.mss.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AEDScanDetail {

  
   @JacksonXmlProperty(localName = "GHASCANINFO_ID")
   private String ghamawbscanID;

   @JacksonXmlProperty(localName = "RECORD_IND")
   private String recordInd;
   
   @JacksonXmlProperty(localName = "MAWB_NO")
   private String mawbNo;

   @JacksonXmlProperty(localName = "INSPECTION_OUTCOME")
   private String inspectionOutcome;

   @JacksonXmlProperty(localName = "INSPECTION_DATETIME")
   private String inspectionDatetime;

   @JacksonXmlProperty(localName = "INSPECTION_REMARKS")
   private String inspectionRemarks;
   
   
}
