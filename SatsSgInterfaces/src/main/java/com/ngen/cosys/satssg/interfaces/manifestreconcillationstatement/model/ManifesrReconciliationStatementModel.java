package com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
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
import lombok.ToString;

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
@ToString
@NgenAudit(entityFieldName ="flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS, repository = NgenAuditEventRepository.FLIGHT)
public class ManifesrReconciliationStatementModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTDATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightOriginDate;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate scheduledFlightDate;

   private String pointOfLading;
   private String pointOfUnlading;
   private String acknowledgeCodeSequenceNo;
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTKEY)
   private String flightNumber;
   @NgenAuditField(fieldName = "Acknowledge Code")
   private String acknowledgeCode;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.EXPORTIMPORTINDICATOR)
   private String importExportIndicator;
   private String carrierCode;
   @NgenAuditField(fieldName = "FMA Received")
   private LocalDateTime fma;
   @NgenAuditField(fieldName = "FNA Received")
   private LocalDateTime fna;
   

}