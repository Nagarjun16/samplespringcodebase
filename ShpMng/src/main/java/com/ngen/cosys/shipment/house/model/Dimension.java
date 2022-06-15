package com.ngen.cosys.shipment.house.model;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Dimension extends BaseBO {
   /**
    * Default generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private BigDecimal dg;
   private BigDecimal shipmentWeight;
   private BigDecimal calculatedVolume;
   private BigInteger shipmentPcs;
   private BigDecimal volumetricWeight;
   private String volumetricUnitCode;
   private String volumeCode;
   private String unitCode;
   private String weightCode;
   private BigDecimal volume;
   private List<DimensionDetails> dimensionDetails;
   private String shipmentNumber;
   private BigInteger messageid;
   private String oldVolumeCode;

}