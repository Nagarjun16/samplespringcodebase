package com.ngen.cosys.satssg.interfaces.singpost.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder(value = { "subRiskId", "SHC" })
public class ULDAutoweightSubList {

   public ULDAutoweightSubList(String subRiskId, String shc) {
      super();
      this.subRiskId = subRiskId;
      this.shc = shc;
   }

   @JacksonXmlProperty(localName = "subRiskId")
   private String subRiskId;

   @JacksonXmlProperty(localName = "SHC")
   private String shc;
}
