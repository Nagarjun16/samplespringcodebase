/**
 * 
 * SearchShipmentIrregularity.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          6 Jan, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.time.LocalDate;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the class for keeping all Shipment Irregularity Data.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchShipmentIrregularity extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   /**
    * This field contains AWB Number OR Mail Bag Id OR Courier Number
    */
   @NotBlank(message = "awb.shipment.type.empty")
   private String shipmentType;
   /**
    * This field contains the shipment number
    */
   @NotBlank(message = "awb.no.of.awb.blank")
   private String shipmentNumber;
   
   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   private LocalDate shipmentDate;
   
   private String hawbNumber;
   
   private BigInteger shipmentId;
}
