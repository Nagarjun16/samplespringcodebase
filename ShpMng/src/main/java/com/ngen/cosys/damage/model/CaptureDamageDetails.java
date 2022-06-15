package com.ngen.cosys.damage.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

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

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "Entity Key", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE, repository = NgenAuditEventRepository.AWB)
public class CaptureDamageDetails extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger damageLineItemsId;
   private BigInteger id;
   private BigInteger damageInfoId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NATURE_OF_DAMAGE)
   @NotNull(message = "data.required.mandatory")
   
   private List<String> listNatureOfDamage;

   @NotNull(message = "data.required.mandatory")
   private String natureOfDamage = "+";

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DAMAGED_PIECES)
   @NotNull(message = "data.required.mandatory")
   private BigInteger damagePieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SEVERITY)
   @NotNull(message = "data.required.mandatory")
   private String severity;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OCCURRENCE)
   private String occurrence;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ENTITY_TYPE)
   private String entityType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ENTITY_KEY)
   private String entityKey;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HWBNUMBER)
   private String subEntityKey; 
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ENTITY_DATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   @InjectShipmentDate(shipmentNumberField = "entityKey")
   private LocalDate entityDate;

   private BigInteger flightId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.LINE_REMARKS)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.remarks.pattern")
   @Length(max=65, message="INVDRMK")
   private String lineRemarks;
   
   private String documentName;

}