package com.ngen.cosys.shipment.model;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the Base class for all the AWB Shipment List.
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
public class SearchRemarks {
   /**
    * This field contains AWB Number
    */
   @NotBlank(message = "awb.no.of.awb.blank")
   private String shipmentNumber;
   /**
    * This field contains the Flight Number
    */
   private String flightKey;
   /**
    * The Date
    */
   private LocalDateTime shipmentDate;
   /**
    * The flightId
    */
   private String flightId;
}