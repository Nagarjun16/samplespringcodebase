/**
 * 
 * MarkShipmentForReuse.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 5 Jan, 2017 NIIT -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is the class for keeping all shipment which can be marked for reuse.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
public class MarkShipmentForReuse extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   /**
    * This field contains Shipment Id
    */
   private BigInteger shipmentId;
   /**
    * This field contains Awb Number Prefix
    */
   private String awbPrefix;
   /**
    * This field contains Awb Number Suffix
    */
   private String awbSuffix;

   @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
   private String awbshipmentNumber;

   /**
    * This field contains Origin
    */
   private String origin;
   /**
    * This field contains Remarks
    */
   private String remarks;
   /**
    * List of Shipment Number for Reuse Details
    */
   private List<SearchShipmentNumberForReuse> searchShipmentNumberForReuse;

}