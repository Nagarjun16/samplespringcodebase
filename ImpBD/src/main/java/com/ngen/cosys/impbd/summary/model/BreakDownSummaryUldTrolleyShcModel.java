package com.ngen.cosys.impbd.summary.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BreakDownSummaryUldTrolleyShcModel extends BaseBO {

   /**
    * System generated default serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger id;
   private BigInteger referenceId;

   private String shcGroup;
   private BigDecimal tonnage;

}