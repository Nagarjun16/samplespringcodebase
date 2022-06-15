package com.ngen.cosys.shipment.awb.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@XmlRootElement
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated
public class FetchAWBRequest extends BaseBO {
   
   private static final long serialVersionUID = 1L;
    @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
   private String shipmentNumber;
   
   private boolean nonIATA=false;
   private String ShipmentType;
   
   private Boolean svc;
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentdate;

}
