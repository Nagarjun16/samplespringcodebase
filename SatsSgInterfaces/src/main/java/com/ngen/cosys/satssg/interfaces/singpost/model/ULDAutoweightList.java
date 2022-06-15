package com.ngen.cosys.satssg.interfaces.singpost.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder(value = { "dgClassCode", "SHC" })
public class ULDAutoweightList {

   public ULDAutoweightList(String dgClassCode, String shc) {
      super();
      this.dgClassCode = dgClassCode;
      this.shc = shc;
   }

   @JacksonXmlProperty(localName = "dgClassCode")
   private String dgClassCode;

   @JacksonXmlProperty(localName = "SHC")
   private String shc;
}
