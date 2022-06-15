/**
 * 
 * DLSULD.java
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

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

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
@Validated
@NoArgsConstructor
public class DLSULD extends DLSULDTrolley {

   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private Boolean select = Boolean.FALSE;

   private String boardPoint;
   private String offPoint;

   private List<DLSUldtrolleySHCinfo> shcs;

   @Valid
   private List<DLSAccessory> accessoryList;

   @Valid
   private List<DLSPiggyBackInfo> piggyBackUldList;

   private List<DlsContentCode> contentCodeList;

}