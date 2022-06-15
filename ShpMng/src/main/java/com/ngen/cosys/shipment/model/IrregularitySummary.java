/**
 * 
 * IrregularitySummary.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 3 Jan, 2018 NIIT -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckNatureOfGoodsConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the class for keeping all Shipment Irregularity Data.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_SHIPMENT_IRREGULARITY, repository = NgenAuditEventRepository.AWB)
public class IrregularitySummary extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * This field contains Shipment Id
    */
   private BigInteger shipmentId;
   /**
    * This field contains AWB Number OR Mail Bag Id OR Courier Number
    */
   @NotBlank(message = "export.enter.awbnumber")
   private String shipmentType;
   /**
    * This field contains the shipment number
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   @NotBlank(message = "export.enter.awbnumber")
   private String shipmentNumber;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;

   /**
    * The Origin airport of shipment
    */
   @NotBlank(message = "awb.origin.empty")
   private String origin;
   /**
    * The Destination airport of shipment
    */
   @NotBlank(message = "awb.destination.empty")
   private String destination;
   /**
    * The Number of pieces
    */
   private int pieces;
   /**
    * The Weight of total number of pieces
    */
   private double weight;
   /**
    * The Nature of Goods
    */
   @CheckNatureOfGoodsConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   private String natureOfGoods;
   /**
    * The Special Handling Code
    */
   private List<String> specialHandlingCodes;
   /**
    * List of Irregularity Details
    */
   @Valid
   @NgenCosysAppAnnotation
   private List<IrregularityDetail> irregularityDetails;
  
   @Valid
   @NgenCosysAppAnnotation
   private List<IrregularityDetail> irregularityDetailsHAWB;

   private int newSequence;

   private List<DamageDetails> damageDetails;
   
   
   private ShipmentHouseInformation shipmentHouseInfo;
   private String hawbNumber;
   private String specialHandlingCodeHAWB;
   
}