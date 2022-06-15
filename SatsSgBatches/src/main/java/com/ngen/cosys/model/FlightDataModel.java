package com.ngen.cosys.model;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
public class FlightDataModel extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private List<FlightModel> flightModel;
   private List<ShipmentDataModel> shipmentDataModel;
}
