package com.ngen.cosys.impbd.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the Base class for Change of Customer Code.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
@NgenAudit(eventName = NgenAuditEventType.ECC_SHIPMNT, repository = NgenAuditEventRepository.FLIGHT, entityFieldName = "Flight Key", entityType = NgenAuditEntityType.FLIGHT)
public class ShipmentList extends BaseBO {
   /**
    * Flight Key Information
    */
	@NgenAuditField(fieldName = "Flight Key")
   private String flightKey;

   private String agent;

   private String flightID;
   /**
    * Estimated Time of Arrival.
    */
   @NgenAuditField(fieldName = "Flight STA")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime sta;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime eta;
  
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime ata;
   
   private String eo;
   private List<String> checker;
   private String userId;
   /**
    *
    */
   private String bay;

   private int worksheetID;

   private int worksheetShipmentID;

   private int customerID;
   private boolean errorFlag;
   private String flagNT;
   private String flagT;
   @Valid
   @NgenCosysAppAnnotation 
	@NgenAuditField(fieldName = "Shipment List")
   private List<ShipmentListDetails> shipmentListDetails;

}
