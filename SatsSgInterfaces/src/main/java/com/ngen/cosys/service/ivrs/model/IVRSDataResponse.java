/**
 * {@link IVRSDataResponse}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IVRS Data Response
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "shipmentDate", "messageId", "loggedInUser", "userType", "createdBy", "createdOn", //
      "modifiedBy", "modifiedOn", "versionDateTime", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", //
      "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "sectorId" })
@JsonPropertyOrder(value = { "messageSequenceNo", "requestDateTime", "awbPrefix", "awbSuffix", "status", "errorCode",
      "errorDescription" })
@NgenAudit(entityType = NgenAuditEntityType.AWB, repository = NgenAuditEventRepository.NOTIFICATION, // 
   eventName = NgenAuditEventType.IVRS)
public class IVRSDataResponse extends IVRSBaseBO {

   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   //
   @NgenAuditField(fieldName = "AWB Prefix")
   @JsonProperty(value = "awbPfx")
   private String awbPrefix;
   @NgenAuditField(fieldName = "AWB Suffix")
   @JsonProperty(value = "awbNum")
   private String awbSuffix;
   @NgenAuditField(fieldName = "Status")
   @JsonProperty(value = "status")
   private String status;
   @NgenAuditField(fieldName = "Error Code")
   @JsonProperty(value = "errorCode")
   private String errorCode;
   @NgenAuditField(fieldName = "Error Desc")
   @JsonProperty(value = "errorDesc")
   private String errorDescription;
   //
   private LocalDate shipmentDate;
   
}
