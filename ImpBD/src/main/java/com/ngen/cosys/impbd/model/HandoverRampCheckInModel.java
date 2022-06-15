package com.ngen.cosys.impbd.model;

import java.math.BigInteger;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated
public class HandoverRampCheckInModel extends BaseBO {

   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private BigInteger impRampCheckInId;
   private BigInteger flightId;
   private String uldNumber;
   private String contentCode;
   private Boolean usedAsTrolley;
}
