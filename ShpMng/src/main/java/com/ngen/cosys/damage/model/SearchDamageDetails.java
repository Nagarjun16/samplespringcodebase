package com.ngen.cosys.damage.model;

import java.math.BigInteger;
import java.time.LocalDate;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
public class SearchDamageDetails extends BaseBO  {

   /**
    * Default system generated serial version id
    */
   private static final long serialVersionUID = 1L;
   @NotBlank(message = "data.required.mandatory")
 
   private String entityKey;
   private String entityType;
   private String subEntityKey;
   @NgenAuditField(fieldName = "Entity Date")
   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "entityKey")
   private LocalDate entityDate; 

   private String flight;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;
   
   /**
    * origin of the flight for shipment
    */
   private String origin;

   /**
    * destination of the flight for shipment
    */
   private String destination;
   
   private String shipmentHouseId;
   
   private int houseCount;
   
   @NgenAuditField(fieldName = "handledByHouse")
   private String handledByHouse;
   private Boolean isHandleByHouse; 
   private BigInteger flightSegmentId;

}