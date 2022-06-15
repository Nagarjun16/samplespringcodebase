package com.ngen.cosys.ics.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel
@NoArgsConstructor
@JsonPropertyOrder(value = { "dgClassCode", "SHC" })
@JsonIgnoreProperties(value = {"staffLoginId","id"})
public class PrintULDTagList {

   public PrintULDTagList(String dgClassCode, String shc) {
      this.dgClassCode = dgClassCode;
      this.shc = shc;
   }

   @JacksonXmlProperty(localName = "dgClassCode")
   private String dgClassCode;

   @JacksonXmlProperty(localName = "SHC")
   private String shc;

   private String staffLoginId;

   private BigInteger id;

}