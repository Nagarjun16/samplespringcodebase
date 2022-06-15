package com.ngen.cosys.satssginterfaces.mss.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.FlightType;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Validated
@CheckValidFlightConstraint(type = FlightType.Type.IMPORT, number = "flightNumber", originDate = "flightDate")
public class InboundMailBreakDownAirmailInterfaceModel extends FlightAirlineInterfaceModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private String flightKey;
   private String dispatchNumber;
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   @NotBlank(message = "Mail_ULD")
   private String uldNumber;
   private String shipmentLocation;
   private String warehouseLocation;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
 
   private LocalDateTime staDate;
   
   @Valid
   private InboundMailBreakDownShipmentAirmailInterfaceModel mailBagShipments;

   private List<InboundMailBreakDownShipmentAirmailInterfaceModel> shipments;
}