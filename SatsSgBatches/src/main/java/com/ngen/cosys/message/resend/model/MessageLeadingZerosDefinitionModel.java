/**
 * This is a model class for holding properties for Leading Zero's configuration
 */
package com.ngen.cosys.message.resend.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageLeadingZerosDefinitionModel extends BaseBO {

   /**
    * default serial version id
    */
   private static final long serialVersionUID = 1L;
   private String carrierCode;
   private String flightType;
   private String messageType;
   private String flowType;
   private String referenceBy;
   private String flightNumber;
   private String offPoint;
   private BigInteger zerosToRemove = BigInteger.ZERO;
   private BigInteger zerosToAppend = BigInteger.ZERO;

}