package com.ngen.cosys.shipment.awb.model;

import java.math.BigInteger;

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
public class ShipmentMasterHandlingArea extends BaseBO {
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   private BigInteger shipmentMasterHandlAreaId;

   private String handledBy;
}
