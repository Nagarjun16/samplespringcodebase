/**
 * 
 * StockDetail.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version   Date   Author   Reason
 * 1.0   29 Jan, 2018   NIIT   -
 */
package com.ngen.cosys.shipment.stockmanagement.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validatorgroup.StockManagementValidatorGroup;
import com.ngen.cosys.validator.annotations.CheckBlackListedShipmentConstraint;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class for Stock Detail.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@Component
@Scope(scopeName = "prototype")
@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "carrierCode", entityType = NgenAuditEntityType.MASTER, eventName = NgenAuditEventType.NAWB_STOCK_SUMMARY, repository = NgenAuditEventRepository.MASTER)
@NgenAudit(entityFieldName = "First AWB number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NAWB_STOCK_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class StockDetail extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   /**
    * This field contains the AWB Details StockID
    */
   private long awbStockId;

   /**
    * This field contains AWB StockID
    */
   @NotNull(message="awb.stock.id.blank", groups = StockManagementValidatorGroup.class)
   @NgenAuditField(fieldName = "Stock Id")
   private String stockId;

   /**
    * This field contains Carrier Code
    */

   @NotBlank(message="flight.carrier.not.blank", groups = StockManagementValidatorGroup.class)
   
   @NgenAuditField(fieldName = "Carrier Code")
   private String carrierCode;

   /**
    * This field contains Category Code
    */
   @NotBlank(message="flight.carrier.not.blank", groups = StockManagementValidatorGroup.class)
   @NgenAuditField(fieldName = "Stock Category")
   private String stockCategoryCode;

   /**
    * This field contains AWB Number
    */
   @CheckBlackListedShipmentConstraint(groups=  StockManagementValidatorGroup.class, mandatory =MandatoryType.Type.REQUIRED)
   @CheckShipmentNumberConstraint(groups=  StockManagementValidatorGroup.class, mandatory = MandatoryType.Type.REQUIRED)
   @NgenAuditField(fieldName = "First AWB number")
   private String firstAwbNumber;

   /**
    * This field contains Number Of AWB
    */
   @NotNull(message="awb.no.of.awb.blank", groups = StockManagementValidatorGroup.class)
   @NgenAuditField(fieldName = "Number of AWB")
   private BigInteger numberOfAwb;
   
   /**
    * This field contains Low Stock Limit
    */
   @NotNull(message="awb.no.of.awb.blank", groups = StockManagementValidatorGroup.class)
   @NgenAuditField(fieldName = "Low Stock Limit")
   private BigInteger lowStockLimit;
   @NgenAuditField(fieldName = "Number of AWB Created")
   private BigInteger noOfAWBCreated;
   
   private String createdBy;
   private BigInteger totalShipments;
 
   @NgenAuditField(fieldName = "New Low Stock Limit")
   private BigInteger newLowStockLimit;
   
   private List<StockSummary> stockStatusList;
  
}