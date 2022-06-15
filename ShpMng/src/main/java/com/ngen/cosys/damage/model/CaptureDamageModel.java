package com.ngen.cosys.damage.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "entityKey", entityRefFieldName = "flightId", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE, repository = NgenAuditEventRepository.AWB)

public class CaptureDamageModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   /**
    * once document upload damageInfoId will come
    */
   private BigInteger damageInfoId;
   

   /**
    * once document upload refrenceId will come
    */
   private String referenceId;

   private String origin;

   private BigInteger id;

  // @NgenAuditField(fieldName = "Flight Id")
   private BigInteger flightId;
   
   private String shipmentHouseId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TOTAL_DAMAGED_PIECES)
   private BigInteger damagePieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;
   
   //@NgenAuditField(fieldName = "Flight Segment")
   private BigInteger flightSegmentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NATURE_OF_GOODS)
   @NotBlank(message = "data.required.mandatory")
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "awb.nog.invalid")
   @Length(max=20, message="awb.nog.len")
   private String content;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ENTITY_TYPE)
   @NotBlank(message = "data.required.mandatory")
   private String entityType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ENTITY_KEY)
   @NotBlank(message = "data.required.mandatory")
   private String entityKey;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HWBNUMBER)
   private String subEntityKey;
   
   private Boolean isHandleByHouse;  

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ENTITY_DATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "entityKey")
   private LocalDate entityDate;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHP_REMARK)
   @Size(max = 65, message = "INVDRMK")
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.remarks.pattern")
   private String remark;

   private List<String> emailTo;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CAPTURE_DETAILS)
   @Valid
   List<CaptureDamageDetails> captureDetails;

}