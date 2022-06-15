package com.ngen.cosys.shipment.house.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef6;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue6;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.house.validator.HouseWayBillValidationGroup;
import com.ngen.cosys.validator.annotations.CheckNatureOfGoodsConstraint;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentDestinationConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.annotations.CompareOriginDestinationConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class ShipmentOnHoldDetail entity to able user to remove shipment
 * on hold or get to know other details regarding shipment on hold e.g.
 * warehouse location, freight In, freight in date, SHC etc.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
@CompareOriginDestinationConstraint(originField = "origin", destinationField = "destination", groups = {
      HouseWayBillValidationGroup.class })
//hawb list audit
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_HWB_LIST, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB Number", entityType = NgenAuditEntityType.AWB)
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_HOUSE, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB Number", entityType = NgenAuditEntityType.AWB)
public class HouseModel extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private BigInteger masterAwbId;
   private BigInteger id;

   private Boolean selectHAWB = Boolean.FALSE;

   /**
    * Property added for supporting the Add/Edit
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_NUMBER)
   private String awbNumber;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_DATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "awbNumber")
   private LocalDate awbDate;

   /**
    * HAWBNumber
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_NUMBER)
   @NgenAuditEntityValue6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityRef6(entityType = NgenAuditEntityType.HAWB,  parentEntityType = NgenAuditEntityType.AWB)
   private String hawbNumber;

   /**
    * Origin related to AWBNumber
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   @CheckShipmentDestinationConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = {
         HouseWayBillValidationGroup.class })
   private String origin;

   /**
    * destination related to AWBNumber
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   @CheckShipmentDestinationConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = {
         HouseWayBillValidationGroup.class })
   private String destination;

   /**
    * number of pieces
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   @CheckPieceConstraint(type = MandatoryType.Type.REQUIRED, groups = { HouseWayBillValidationGroup.class })
   private BigInteger pieces;

   /**
    * weight
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   @CheckWeightConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = { HouseWayBillValidationGroup.class })
   private BigDecimal weight;
   /**
    * weightunitcode
    */
   private String weightUnitCode;
   /**
    * slac
    */
   @Pattern(regexp = "[0-9]*", groups = { HouseWayBillValidationGroup.class }, message = "data.invalid.house.slac")
   @Size(min = 0, max = 5, groups = { HouseWayBillValidationGroup.class }, message = "data.invalid.house.slac")
   private String slac;

   /**
    * nature of goods
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.NATURE_OF_GOODS)
   @CheckNatureOfGoodsConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
         HouseWayBillValidationGroup.class })
   @Size(max = 20, message = "Nature Of Goods Description max 20 charater")
   private String natureOfGoods;

   /**
    * shipment handling code
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SPECIALHANDLINGCODE)
   private String specialHandlingCode;

   /**
    * shipper for entire AWB
    */
   // bug-81 fix
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPPER_NAME)
   private String shipperName;

   private int sNo;

   /**
    * Consignee for entire AWB.
    */
   //bug-81 fix
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONSIGNEE_NAME)
   private String consigneeName;

   /**
    * Temp variable for search customer info
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_TYPE)
   private String customerType;

   /**
    * shipper details for hawb.
    */
   /**
	 * 
	 * bug-81 fix 
	 * */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPPER)
   @Valid
   private HouseCustomerModel shipper;

   /**
    * consignee details for hawb.
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONSIGNEE)
   @Valid
   private HouseCustomerModel consignee;
   /**
	 * 
	 * bug-81 fix end
	 * */
   /**
    * otherChargeDeclarations details for hawb.
    */
   /*@NgenAuditField(fieldName = NgenAuditFieldNameType.OTHER_CHARGE_DECLARATIONS)*/
   private HouseOtherChargeDeclarationModel otherChargeDeclarations;

   /**
    * shc details for hawb.
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.LIST_OF_SHC)
   private List<HouseSpecialHandlingCodeModel> shc;

   /**
    * descriptionOfGoods details for hawb.
    */
   @Valid
   /*@NgenAuditField(fieldName = NgenAuditFieldNameType.LIST_OF_DESCRIPTION_OF_GOODS)*/
   private List<HouseDescriptionOfGoodsModel> descriptionOfGoods;

   /**
    * tariffs details for hawb.
    */
   @Valid
   /*@NgenAuditField(fieldName = NgenAuditFieldNameType.LIST_OF_TARIFFS)*/
   private List<HouseHarmonisedTariffScheduleModel> tariffs;

   /**
    * Other Customs Info details for hawb.
    */
   @Valid
   /*@NgenAuditField(fieldName = NgenAuditFieldNameType.LIST_OF_OCI)*/
   private List<HouseOtherCustomsInformationModel> oci;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_CHARGEABLE_WEIGHT)
   private BigDecimal chargeableWeight;

   // Billing
   private BigInteger shipmentId;
   private BigInteger customerId;
   private Boolean applyCharge = Boolean.FALSE;
   
   private boolean doFlag = Boolean.FALSE;

	private boolean poFlag = Boolean.FALSE;

   // for hawb List
	/**
	    *  house id for hawb.
	    */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HOUSE_ID)
   private BigInteger houseId;
	/**
	    *  awb weight for hawb.
	    */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_WEIGHT)
   private BigDecimal awbWeight;
	/**
	    *  awb chargeable weight for hawb.
	    */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_CHARGEABLE_WEIGHT)
   private BigDecimal awbChargeableWeight;
	/**
	    *  awb pieces for hawb.
	    */
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_PIECES)
   private BigInteger awbPieces;
   //To control the display of the Screen
   boolean display;
   private Boolean check = Boolean.FALSE;
   private String appointedAgentCode;
   private String appointedAgentHouse;
   private HouseDimensionModel houseDimension;
   private String shipmentType;
   private BigInteger locationPieces;
}