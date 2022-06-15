/**
 * 
 * SearchShipmentNumberDetails.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 5 Jan, 2017 NIIT -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is the class for all Shipment for Reuse Details.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "awbResponseNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_REUSE_AWB_NUMBER, repository = NgenAuditEventRepository.AWB)
public class SearchShipmentNumberForReuse extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;
   /**
    * This field contains shipment Number
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String awbResponseNumber;
   /**
    * This field contains Source
    */
   @Length(max = 3, message = "g.three.digit.code")
   @Pattern(regexp = "^([a-zA-Z]) {3}", message = "admin.special.characters.not.allowed")
   private String source;
   /**
    * This field contains Approving Auhtority
    */
   private String approvedBy;
   /**
    * This field contains Created Date And Time of Shipment Number
    */
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime createdDateAndTime;
   /**
    * This field contains Remarks
    */
   private String remarks;

}