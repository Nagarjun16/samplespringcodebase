package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.hibernate.validator.constraints.NotEmpty;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DLSPiggyBackInfo extends BaseBO {

   /**
    * Default generated version id
    */
   private static final long serialVersionUID = 1L;

   private Boolean select = Boolean.FALSE;

   private BigInteger flightSegmentId;

   private BigInteger dlsUldTrolleyId; // DLSId

   @NotEmpty(message = "g.required")
   private String uldNumber;// ULDNumber

   private BigInteger dlsUldTrolleyPiggyBackId;// DLSUldTrolleyPiggyBackId

   private String piggyBackFlag;

   private BigDecimal calculatedTareWeight;

}