/**
 * 
 * HandoverInboundContainerTrolly.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 23 January, 2018 NIIT -
 */
package com.ngen.cosys.impbd.model;

import java.math.BigInteger;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated
public class HandoverInboundContainerTrolly extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private BigInteger impHandOverContainerTrolleyInformationId;
   private BigInteger impHandOverId; 
   private BigInteger flightId;
   private String containerTrolleyNumber;
   private Boolean usedAsTrolley;
   private Boolean capturedManual;
   private String sourceOfInformation;
   private String contentCode;
   private String apronCargoLocation;
   private boolean checkInCompleted;
   private boolean handOverCompleted;
}