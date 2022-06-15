package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is model class for DLSULD.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class DLSuldtrolleyosi extends BaseBO {
   /**
    * Default serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger dlsId;// dlsUldTrolleyId

   private BigInteger transactionSequenceNo;

   private BigInteger dlsULDTrolleyOsiId;// DLSULDTrolleyOSI

   private String detail;

   private Boolean manual = Boolean.FALSE;

}