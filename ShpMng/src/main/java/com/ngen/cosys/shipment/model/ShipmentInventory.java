/**
 * 
 * ShipmentInventory.java
 *
 *
 * Version Date Author Reason 1.0 3 Jan, 2018 NIIT -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.MappedTypes;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityRef3;
import com.ngen.cosys.audit.NgenAuditEntityRef6;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEntityValue6;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAudits;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.multitenancy.entity.validator.annotation.EntityAttribute;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class ShipmentInventory entity to able user to remove shipment on
 * hold or get to know other details regarding shipment on hold e.g. warehouse
 * location, freight In, freight in date, SHC etc.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedTypes(LocalDateTime.class)
// @CheckValidFlightConstraint(type = FlightType.Type.IMPORT, number =
// "flightKey", originDate = "flightKeyDate")
@NgenAudits({
@NgenAudit(eventName = NgenAuditEventType.SHIPMENT_ON_HOLD, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB),
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_LOCATION, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB),
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_LOCATION_UTL_INVENTORY, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB),
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_LOCATION_SPLIT_LOCATION, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB),
})
public class ShipmentInventory extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   /**
    * unique shipmentInventoryId
    */
   private int id;
   
   private int shipmentInventoryId;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;
   
   /**
    * Shipment Type (AWB/CBV/UCB)
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTTYPE)
   private String shipmentType;

   private LocalDate shipmentDate;
   private ArrayList<Integer> sInvIdList;
   
   /**
    * origin of the flight for shipment
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   private String origin;

   /**
    * destination of the flight for shipment
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   private String destination;

   private boolean selected;

   private BigInteger comTracingShipmentInfoId;

   private BigInteger ComTracingShipmentLocationInfoId;

   /**
    * unique shipment ID
    */
   private int shipmentId;

   /**
    * location from where shipment will be done
    */
   // @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_LOCATION)
   @NgenAuditEntityValue2(entityType = NgenAuditEntityType.ULD, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityRef2(entityType = NgenAuditEntityType.ULD,  parentEntityType = NgenAuditEntityType.AWB)
   private String shipmentLocation;
   /**
    * number of pieces for the shipment
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   private Integer piecesInv;

   private BigInteger invPiecesToCalculateWeight;

   /**
    * Weight for the shipment
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   private BigDecimal weightInv;
   
   private Integer storagePieces;

   /**
    * Weight for the shipment
    */
   private BigDecimal actualWeightWeighed;

   /**
    * Unable to locate piece info
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.UNABLE_TO_LOCATE_PIECES)
   private int unableToLocate;

   /**
    * ware house location
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WAREHOUSE_LOCAITON)
   @NgenAuditEntityValue3(entityType = NgenAuditEntityType.LOCATION, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityRef3(entityType = NgenAuditEntityType.LOCATION,  parentEntityType = NgenAuditEntityType.AWB)
   private String warehouseLocation;

   /**
    * flight number
    */
   private int flightId;

   /**
    * flight Key
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightKey;

   /**
    * date on which flight has been scheduled
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightKeyDate;

   /**
    * shipment handling code
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SPECIALHANDLINGCODE)
   private String specialHandlingCodeInv;
   
   private String shcDummy;

   /**
    * shipment handling code
    */
   @NgenCosysAppAnnotation
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHC_LIST)
   private List<ShipmentInventoryShcModel> shcListInv;

   /**
    * user can lock or unlock using hold option
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.INVENTORY_ON_HOLD)
   private boolean hold;

   /**
    * reason for shipment on hold
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REASON_FOR_INVENTORIES_ON_HOLD)
   private String reasonForHold;

   /**
    * remarks
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
   @Length(max = 65, message = "INVDRMK")
   @CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   private String remarks;

   /**
    * defines if the Row is been selected for Hold
    */
   private boolean holdSelected;

   private String handlingArea;

   private String deliveryRequestOrderNo;
   
   private String deliveryOrderNo;

   private int uldId;

   private String assignedUldTrolley;
   
   private BigInteger loaded;

   private BigInteger storageInfoId;
   
   private BigInteger referenceId;

   private BigInteger parentReferenceId;

   private String breakDownUld = "Bulk";
   
   private String specialHandlingCode;
   
   private String oldShipmentLocation;
   
   private String oldWarehouseLocation;
   
   private Integer oldPiecesInv;
   
   private BigDecimal oldWeightInv;
   
   private BigDecimal oldChargeableWeightInv;
   
   private BigInteger transactionSequenceNo;

   private String referenceDetails;
   
   private BigInteger manifestPieces =BigInteger.ZERO;
   
   private BigInteger foundPieces=BigInteger.ZERO;
   
   private BigInteger breakDownPieces=BigInteger.ZERO;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PARTSUFFIX)
   private String partSuffix;
   
   private Boolean autoloadFlag = Boolean.FALSE;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.USER_GROUP_TO_NOTIFY)
   private String holdNotifyGroup;
   private Boolean accessLocation = Boolean.FALSE;
   private String dipSvcSTATS;
   
   /*
    * Hold remarks/reason/locked by
    */
   private String holdRemarks;
   private boolean holdOldValue;
   private String remarksOldValue;
   
   private BigInteger verificationId;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.INV_CHARGEABLE_WEIGHT)
   private BigDecimal chargeableWeightInv;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HOUSE_ID)
   private String houseId;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HWBNUMBER)
   @NgenAuditEntityValue6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityRef6(entityType = NgenAuditEntityType.HAWB,  parentEntityType = NgenAuditEntityType.AWB)
   private String hawbNumber;
   
}