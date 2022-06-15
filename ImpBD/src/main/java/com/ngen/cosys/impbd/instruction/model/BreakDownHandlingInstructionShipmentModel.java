package com.ngen.cosys.impbd.instruction.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.instruction.validator.BreakDownHandlingInstructionValidationGroup;
import com.ngen.cosys.model.SHCModel;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.CompareOriginDestinationConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@CompareOriginDestinationConstraint(originField = "origin", destinationField = "destination", groups = {
      BreakDownHandlingInstructionValidationGroup.class })
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "shipmentNumber", eventName = NgenAuditEventType.CARGO_BREAKDOWN_HANDLING_INFORMATION, repository = NgenAuditEventRepository.AWB)
public class BreakDownHandlingInstructionShipmentModel extends BaseBO {

   /**
    * System Generated Serial Version id
    */
   private static final long serialVersionUID = -4796323241263476799L;

   private BigInteger impBreakDownHandlingInformationId;
   private Long flightId;
   private BigInteger segmentId;
   private BigInteger breakdownId;
   private BigInteger shipmentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightKey;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDate flightDate;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHCSB)
   private List<SHCModel> shcs;

   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HOUSE_INFO)
   private List<BreakDownHandlingInformationByHouse> house;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   @CheckAirportCodeConstraint(message = "Origin can not be blank", mandatory = MandatoryType.Type.REQUIRED, groups = {
         BreakDownHandlingInstructionValidationGroup.class })
   private String origin;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   @CheckAirportCodeConstraint(message = "Destination can not be blank", mandatory = MandatoryType.Type.REQUIRED, groups = {
         BreakDownHandlingInstructionValidationGroup.class })
   private String destination;

   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;

   @CheckPieceConstraint(message = "Pieces can not be blank", type = MandatoryType.Type.REQUIRED, groups = {
         BreakDownHandlingInstructionValidationGroup.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   private BigInteger totalPieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.BREAKDOWN_INSTRUCTION)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = {
         BreakDownHandlingInstructionValidationGroup.class })
   private String instruction;

   @NotBlank(message = "Shipment Number field is mandatory", groups = BreakDownHandlingInstructionValidationGroup.class)
   @CheckShipmentNumberConstraint(groups = {
         BreakDownHandlingInstructionValidationGroup.class }, mandatory = MandatoryType.Type.REQUIRED)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;

}