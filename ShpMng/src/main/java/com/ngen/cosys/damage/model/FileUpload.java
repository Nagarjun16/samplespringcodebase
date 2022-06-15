package com.ngen.cosys.damage.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "entityKey", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_PHOTO, repository = NgenAuditEventRepository.AWB)
@NgenAudit(entityFieldName = "entityKey", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE, repository = NgenAuditEventRepository.AWB)
public class FileUpload extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private BigInteger uploadDocId;
   private String referenceId;

   @NgenAuditField(fieldName = "Associated To")
   private String associatedTo;

   @NgenAuditField(fieldName = "Stage")
   private String stage;

   @NgenAuditField(fieldName = "Entity Key")
   private String document;

   @NgenAuditField(fieldName = "Remarks")
   private String remarks;

   @NgenAuditField(fieldName = "Document Name")
   private String documentName;

   private String documentSize;

   // Document Type is CLAIM DOCUMENT/CUSTOMS CLEARANCE
   @NgenAuditField(fieldName = "Document Type")
   private String documentType;

   // Document Type Description
   @NgenAuditField(fieldName = "Document Type Description")
   private String documentTypeDescription;

   // Document Format is PDF/XLS/PNG
   @NgenAuditField(fieldName = "Document Format")
   private String documentFormat;
   //
   @NgenAuditField(fieldName = "Document Description")
   private String documentDescription;

   private boolean isDeleted;

   @NgenAuditField(fieldName = "Entity Type")
   private String entityType;

   @NgenAuditField(fieldName = "Entity Key")
   private String entityKey; 

   @NgenAuditField(fieldName = "Entity Date")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate entityDate;

   private BigInteger flightId; // Nullable

   private String userCode;

   private String emailTo;

   @NgenAuditField(fieldName = "Document Date/Time")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentTime;

}
