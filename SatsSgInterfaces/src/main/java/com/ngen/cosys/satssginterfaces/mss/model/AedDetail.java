package com.ngen.cosys.satssginterfaces.mss.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AedDetail {

   @JacksonXmlProperty(localName = "GHAMAWBNO_ID")
   private String ghamawbNoID;

   @JacksonXmlProperty(localName = "MAWB_NO")
   private String mawbNo;

}
