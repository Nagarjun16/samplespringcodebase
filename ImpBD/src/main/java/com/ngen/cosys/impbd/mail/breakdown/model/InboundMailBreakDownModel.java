package com.ngen.cosys.impbd.mail.breakdown.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailBreakDownValidationGroup;
import com.ngen.cosys.model.FlightModel;
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
@CheckValidFlightConstraint(type = FlightType.Type.IMPORT, number = "flightNumber", originDate = "flightDate", groups = InboundMailBreakDownValidationGroup.class)
@NgenAudit(eventName = NgenAuditEventType.MAIL_BREAKDOWN, repository = NgenAuditEventRepository.MAILBAG, entityFieldName = "flightKey", entityType = NgenAuditEntityType.MAILBAG)
public class InboundMailBreakDownModel extends FlightModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   @NgenAuditField(fieldName = "Flight Key")
   private String flightKey;
   @NgenAuditField(fieldName = "Dispatch Number")
   private String dispatchNumber;
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   //@NotBlank(message = "ULD Number is required", groups = InboundMailBreakDownValidationGroup.class)
   @NgenAuditField(fieldName = "ULD Number")
   private String uldNumber;
   @NgenAuditField(fieldName = "Shipment Location")
   private String shipmentLocation;
   @NgenAuditField(fieldName = "Warehouse Location")
   private String warehouseLocation;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
 
   private LocalDateTime staDate;
   
   @Valid
   @NgenAuditField(fieldName = "Mail Bag Shipment")
   private InboundMailBreakDownShipmentModel mailBagShipments;
   
   @NgenAuditField(fieldName = "Working List Shipment Info")
   private List<InboundMailBreakDownWorkingListShipmentInfo> workingListShipmentInfo;
   
   @NgenAuditField(fieldName = "Shipments")
   private List<InboundMailBreakDownShipmentModel> shipments;
   
   private boolean validateContainerDest;
}