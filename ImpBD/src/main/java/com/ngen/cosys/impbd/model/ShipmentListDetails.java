package com.ngen.cosys.impbd.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
@NgenAudit(eventName = NgenAuditEventType.ECC_SHIPMNT, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldName.NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB)

public class ShipmentListDetails extends BaseBO {

   /**
    * Number of pieces.
    */
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.PIECES)
   @CheckPieceConstraint( type = MandatoryType.Type.REQUIRED)
   private BigInteger pieces;

   /**
    * Weight Of the pieces.
    */
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.WEIGHT)
   @CheckWeightConstraint( mandatory = MandatoryType.Type.REQUIRED)
   private BigDecimal weight;

   /**
    * Name of the Customer
    */
   private String agent;
	
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.AGENTCODE)
    private String agentCode;
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.SHIPMENTTYPE)
   private String shipmentType;
  
   private int shipmentId;

   /**
    * ULD Number for shipment
    */
   //@CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.ULDNUMBER)
   private String uldnumber;

   /**
    * Shipment Number for a shipment
    */
	
@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;

   /**
    * Loading Advice
    */
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.LOADINGADVICE)
   private String loadingAdivce;
	

   /**
    * SHCS for the shipment
    */
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.SHC)
   private List<SpecialHandlingCode> shcList;

   /**
    * Wraehouse Location
    */
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.WAREHOUSELOCATION)
   private String wareHouseLocation;

   /**
    * Delivery Location of a shipment
    */
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.DELIVERYLOCATION)
   private String deliveryLocation;

   /**
    * Status for shipment
    */
   private String status;
   
   /**
    *  Select boolean varaible for delete operation
    */
   private boolean select;
   /**
   * Eo for the shipment
   */
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.EO)
   private List<EquipmentOperator> eqpOperator;
   /**
    *  Remarks for the shipment
    */
	@NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.REMARKS)
	@Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters")
   private String remarks;
   /**
    * ShipmentID
    */
   private int worksheetShipmentID;

   /**
    * CustomerID
    */
   @NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.CUSTOMERCODE)
   private String customerCode;
   /**
    * WorksheetID for team
    */
   private int worksheetID;
   /**
    * Flight id
    */
   private String flightID;

   /**
    *  ID of assigned user
    */
   private int worksheetAssignedList;
   /**
    * ID of user
    */
   private String userid;
  
   /**
    * Flag variable for crud operation
    */
   private String flagMaintain;
  
   /**
    * team id
    */
   private int comTeamId;
  
   /**
    * Shipment date.
    */
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   private LocalDate shipmentDate;
   
   private boolean errorFlag;
   
   private String lateBooking;
   
   private String del;

   private boolean noShow;
   private boolean colour;
   
   private String sourceId;
  // @NgenAuditEntityRef2(entityType = NgenAuditEntityType.FLIGHT,  parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTKEY)
   private String flightKey;
   @NgenAuditEntityValue3(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditField(fieldName = NgenAuditFieldName.NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDate flightDate;
   
   private String agentCustomerId;
   
   private String customerID;
   
   private int isArrivalDone;
}
