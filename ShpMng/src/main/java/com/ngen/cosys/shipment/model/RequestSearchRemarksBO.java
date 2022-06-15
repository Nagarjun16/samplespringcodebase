package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;
import com.ngen.cosys.validator.enums.MandatoryType;

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
@UserCarrierValidation(shipmentNumber = "shipmentNumber", flightKey = "", loggedInUser = "loggedInUser", type = "AWB")
public class RequestSearchRemarksBO extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * This field contains AWB Number
    */
   @NotBlank(message = "export.enter.awbnumber")
   @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED, checkBlackListField = "false")
   private String shipmentNumber;
   /**
    * This field contains the Flight Number
    */
   private String flightKey;

   /**
    * This field contains the Flight Number
    */
   private LocalDateTime flightDate;

   /**
    * The flightId
    */
   private String flightId;
   /**
    * The shipment type
    */
   private String shipmentType;
   /**
    * The shipment date
    */
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   private LocalDate shipmentDate;
   
   private String hawbNumber;
   
   private BigInteger shipmentId;
   private boolean handledbyHouse;

}
