/**
 * 
 * MaintainRemark.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          3 Jan, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.model;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef6;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue6;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the Base class for all the AWB Shipment List.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_REMARK, repository = NgenAuditEventRepository.AWB)
public class MaintainRemark extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * This field contains Shipment Number
    */
   
   private int shipmentRemarkId;
   /**
    * This field contains AWB Number
    */
   @NotBlank(message = "export.enter.awbnumber")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;

   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	private LocalDate shipmentDate; 
   /**
    * The ShipmentId
    */
   private long shipmentId;
   
   /**
    * The FlightId
    */
   private Long flightId;
   /**
    * This field contains flight number
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightKey;
   /**
    * This field contains flight date
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDateTime flightDate;
   
   /**
    * The Remark Type
    */
   private String remarkType;
   /**
    * The remarks
    */
   @NotBlank(message = "INVR001")
   @Length(max=65, message="INVDRMK")
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_REMARKS)
   @CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
   private String shipmentRemarks;
   /**
    * The Flight Source
    */
   private String flightSource;
   /**
    * The Flight Destination
    */
   private String flightDestination;
   /**
    * The shipment type
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTTYPE)
   private String shipmentType;
   private Integer segmentOrder;
   private String segments;
   private String concatSegment;
   private List<ShipmentRemarksResponse> remarks;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HWBNUMBER)
   @NgenAuditEntityValue6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityRef6(entityType = NgenAuditEntityType.HAWB,  parentEntityType = NgenAuditEntityType.AWB)
   private String hawbNumber;
   
   private boolean handledbyHouse;
   private List<SHC> specialHandlingCodeHAWB;

 	
  
}