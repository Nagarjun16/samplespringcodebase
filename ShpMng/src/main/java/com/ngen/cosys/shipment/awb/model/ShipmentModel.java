package com.ngen.cosys.shipment.awb.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
import com.ngen.cosys.shipment.awb.validator.AWBDocumentConstraintsValidator;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.shipment.validators.SearchAWBDocument;
import com.ngen.cosys.validator.annotations.CheckBlackListedShipmentConstraint;
import com.ngen.cosys.validator.annotations.CheckCarrierCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckNatureOfGoodsConstraint;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentDestinationConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = { SearchAWBDocument.class,
      SaveAWBDocument.class }, shipmentNumberField = "shipmentNumber", shipmentTypeField = "shipmentType", nonIataField = "nonIATA")
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentId")
public class ShipmentModel extends BaseBO {

   private static final long serialVersionUID = 1L;

//   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTID)
   private BigInteger shipmentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTTYPE)
   @NotBlank(groups = { SearchAWBDocument.class, SaveAWBDocument.class }, message = "Shipment Type is Mandatory")
   private String shipmentType;

   @CheckBlackListedShipmentConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = { SearchAWBDocument.class })
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentdate;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   @CheckShipmentDestinationConstraint(groups = { SaveAWBDocument.class }, mandatory = MandatoryType.Type.REQUIRED)
   private String origin;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   @CheckShipmentDestinationConstraint(groups = { SaveAWBDocument.class }, mandatory = MandatoryType.Type.REQUIRED)
   private String destination;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_DESCRIPTION_CODE)
   private String shipmentDescriptionCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   @CheckPieceConstraint(groups = { SaveAWBDocument.class }, type = MandatoryType.Type.REQUIRED)
   private BigInteger pieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHTCODE)
   @NotNull(message = "export.weight.unitcode.required")
   @Size(max = 1, message = "awb.wgt.unit.code")
   private String weightUnitCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   @CheckWeightConstraint(groups = { SaveAWBDocument.class }, mandatory = MandatoryType.Type.REQUIRED)
   private BigDecimal weight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.VOLUME_UNIT_CODE)
   private String volumeunitCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.VOLUME_AMOUNT)
   private BigDecimal volumeAmount;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DENSITY_INDICATOR)
   private String densityIndicator;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DENSITY_GROUP_CODE)
   private BigInteger densityGroupCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TOTALPIECES)
   private BigInteger totalPieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NATUREOFGOODS)
   @CheckNatureOfGoodsConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
         AWBDocumentConstraintsValidator.class, SaveAWBDocument.class })
   private String natureOfGoodsDescription;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.MOVEMEN_PRIORITY_CODE)
   private String movementPriorityCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMS_ORIGIN_CODE)
   private String customsOriginCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMS_REFERENCE)
   private String customsReference;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER)
   @CheckCarrierCodeConstraint(groups = SaveAWBDocument.class, mandatory = MandatoryType.Type.REQUIRED)
   private String carrierCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHC_CODE)
   private String shcCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER_DESTINATION)
   private String carrierDestination;

}