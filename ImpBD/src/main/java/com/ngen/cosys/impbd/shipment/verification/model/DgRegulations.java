/**
 * 
 * NotocSpecialInstruction.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 6 March, 2018 NIIT -
 */
package com.ngen.cosys.impbd.shipment.verification.model;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents DG Regulation Header
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class DgRegulations extends BaseBO {
   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 1L;

   private int regId;

   @NotBlank(message = "g.mandatory")
   private String unid;

   @NotBlank(message = "g.mandatory")
   private String psn;

   private boolean tech;

   @NotBlank(message = "g.mandatory")
   private String classCode;

   @NotBlank(message = "g.mandatory")
   private String shc;

   @NotBlank(message = "g.mandatory")
   private String erg;

   private String sbr1;

   private String imp1;

   private String sbr2;

   private String imp2;

   private String packingGroupCode;

   private List<String> packingGroupCodeList;

   private List<String> packingGroupInstructionList;

   @Valid
   private List<DgRegulationDetails> dgDetails;
}
