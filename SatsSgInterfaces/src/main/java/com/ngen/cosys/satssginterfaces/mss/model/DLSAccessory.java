/**
 * 
 * DLSAccessory.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 6 March, 2018 NIIT -
 */
package com.ngen.cosys.satssginterfaces.mss.model;

/**
 * This is model class for DLSAccessory.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DLSAccessory extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Boolean select = Boolean.FALSE;
   private BigInteger dlsUldTrolleyId;
   private BigInteger transactionSequenceNo;
   private BigInteger dlsULDTrolleyAccessoryInfoId;
   private String type;
   private String accessoryPartId;
   private BigInteger quantity;

   @NotNull(message = "g.required")
   private BigInteger accessoryTypeId;

}