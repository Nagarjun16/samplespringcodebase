package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
@Validated
public class BuildUpULD extends ULDInFormation {

   /**
    * 
    */
   private BigInteger uldSequenceId;
   private static final long serialVersionUID = 1L;
   private List<BuildUpShipment> shipment;
   // FOR common Load Shipment   Shipment List
   private List<UldShipment> shipmentList; //
   

}
