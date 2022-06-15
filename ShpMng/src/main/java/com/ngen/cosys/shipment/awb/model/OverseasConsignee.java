package com.ngen.cosys.shipment.awb.model;



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
public class OverseasConsignee extends BaseBO {
   
   
   private static final long serialVersionUID = 1L;
   private long overseasConsignee_ID;
   private String consigneeName;
   private String destinationCode;
   private String consigneeState;
   private String consigneePostalCode;
   private String consigneeAddress;
   private String CountryCode;
   private String consigneePlace;
   private String consigneePhone;

}
