package com.ngen.cosys.impbd.displaycpm.model;

import java.math.BigDecimal;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DisplayCpmDetailsModel extends BaseBO {

   /**
    * The default serialVersionUID.
    */

   private static final long serialVersionUID = 1L;

   /**
    * uldNumber
    */
   private String uldNumber;

   /**
    * destination
    */

   private String destination;

   /**
    * weight
    */
   private BigDecimal weight;

   /**
    * contentCode
    */
   private String contentCode;

   /**
    * Remarks
    */
   private String remarks;

   /**
    * Load Position
    */
   private String loadPosition;

   /**
    * SHC
    */
   private String shc;

   /**
    * Contour code
    */
   private String contourCode;

   /**
    * Volume Code
    */
   private String volumeCode;

   /**
    * Load Instruction
    */
   private String loadInstruction;
}