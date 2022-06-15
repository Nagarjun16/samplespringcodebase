package com.ngen.cosys.ics.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder(value = { "subRiskId", "SHC" })
@JsonIgnoreProperties(value = {"staffLoginId","id"})
public class PrintULDTagSubList {

   public PrintULDTagSubList(String subRiskId, String shc) {
      this.subRiskId = subRiskId;
      this.shc = shc;
   }

   @JacksonXmlProperty(localName = "subRiskId")
   private String subRiskId;

   @JacksonXmlProperty(localName = "SHC")
   private String shc;

   private String staffLoginId;

   private BigInteger id;
}