package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@Validated
@NoArgsConstructor
public class DLS extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private BigInteger dlsId;
   private BigInteger flightId;
   private String carrier;
   
   private boolean flagError;
   @Valid
   private List<DLSULD> uldTrolleyList;
   private List<DLSuldtrolleyosi> osiList;
   private List<DLSuldtrolleyosi> systemOsiList;
   //private List<AirlineLoadingInstructions> airLineInstruction;
   private boolean assignUldInd =true;
   
}