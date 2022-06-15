/**
*
* ExportMailManifest.java
*
* Copyright <PRE><IMG SRC = XX></IMG></PRE>
*
* Version      Date                Author        Reason
* 1.0          31 May, 2018 NIIT      
*/
package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ExportMailBagManifest extends BaseBO{
   
   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 1L;
   /**
    * This field contains Dispatch Number
    */
   private Short dispatchNumber;
   /**
    * This field contains Mail Bag Number
    */
   private String mailBagNumber;
   /**
    * This field contains Total Pieces 
    */
   private BigInteger pieces;
   /**
    * This field contains Manifested Weight
    */
   private BigDecimal manifestedWeight;
   /**
    * This field contains Nested Id
    */
   private String mailType;
   /**
    * This field contains origin
    */
   private String origin;
   /**
    * This field contains destination
    */
   private String destination;
   /**
    * This field contains Next Destination
    */
   private String nextDestination;
   /**
    * This field contains Agent Code
    */
   private String agentCode;
   /**
    * This field contains rcar Status
    */
   private String rcarStatus;
   /**
    * This field contains Remarks
    */
   private String remarks;
   private BigInteger shipmentId;
   private BigInteger assUldTrolleyId;
   private BigInteger loadedShipmentInfoId;
   private String storeLocation;
   private String warehouseLocation;
   private BigDecimal weight;
}
