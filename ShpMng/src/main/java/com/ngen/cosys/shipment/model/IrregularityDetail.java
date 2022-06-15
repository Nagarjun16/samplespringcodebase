/**
 * 
 * IrregularityDetail.java
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityRef6;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue6;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the class for all Irregularity Details.
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
public class IrregularityDetail extends BaseBO {

   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   /**
    * This field contains Transaction Sequence Number
    */
   private long sequenceNumber;

   /**
    * This field contains the shipment number
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   @NotBlank(message = "export.enter.awbnumber")
   private String shipmentNumber;

   /**
    * This field contains the type of irregularity
    **/
   @NgenAuditField(fieldName = NgenAuditFieldNameType.IRREGULARITY_TYPE)
   @NotBlank(message = "awb.irr.typ.empty")
   private String irregularityType;

   private String oldirregularityType;

   /**
    * Irregularity in the number of pieces
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
   private BigInteger pieces;

   /**
    * Irregularity in the weight of total number of pieces.
    */
   private BigInteger oldpieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   @Min(value = 0, message = "awb.irr.wgt.zero")
   private BigDecimal weight;

   private BigDecimal oldweight;

   /**
    * This field contains flightKey of operative flight.
    */
   @Pattern(regexp = "^[A-Z0-9]{0,7}", message = "flight.invalid.flight.no")
   private String flightKey;
   
   private String oldflightKey;
   
   private BigInteger flightId;   
   private BigInteger oldflightId;
   private BigInteger flightSegmentId;
   private BigInteger oldflightSegmentId;
   /**
    * Flight Date
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDate;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime oldflightDate;

   /**
    * This field contains the remark
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.IRREGULARITY_REMARKS)
   @Length(max = 65, message = "INVDRMK")
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters")
   private String remark;

   private String oldremark;
   /**
    * This field tells whether the flight discrepancy list is sent or not
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FDL_SENT)
   private boolean fdlSentFlag;

   private String source;

   private String destination;

   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
   private LocalDate shipmentDate;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flight_No;

   // For Audit trail
   @NgenAuditField(fieldName = "Segment")
   private String Segment;
   
   private BigInteger shipmentId;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HWBNUMBER)
   @NgenAuditEntityValue6(entityType = NgenAuditEntityType.HAWB, parentEntityType = NgenAuditEntityType.AWB)
   @NgenAuditEntityRef6(entityType = NgenAuditEntityType.HAWB,  parentEntityType = NgenAuditEntityType.AWB)
   private String hawbNumber;

}