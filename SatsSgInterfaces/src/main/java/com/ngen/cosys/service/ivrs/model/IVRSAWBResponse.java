/**
 * {@link IVRSAWBResponse}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IVRS AWB Response
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = { "shipmentId", "shipmentDate", "messageId", "loggedInUser", "userType", "createdBy", //
      "createdOn", "modifiedBy", "modifiedOn", "versionDateTime", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", //
      "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "sectorId" })
@JsonPropertyOrder(value = { "messageSequenceNo", "requestDateTime", "status", "errorCode", "errorDescription",
      "awbPrefix", "awbSuffix", "totalPieces", "totalWeight", "origin", "destination", "consigneeName", "awbDetails" })
@NgenAudit(entityType = NgenAuditEntityType.AWB, repository = NgenAuditEventRepository.NOTIFICATION, // 
   eventName = NgenAuditEventType.IVRS)
@NgenAudit(entityType = NgenAuditEntityType.AWB, repository = NgenAuditEventRepository.NOTIFICATION, // 
   eventName = NgenAuditEventType.FAX)
public class IVRSAWBResponse extends IVRSBaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   //
   @NgenAuditField(fieldName = "Status")
   @JsonProperty(value = "status")
   private String status;
   @NgenAuditField(fieldName = "Error Code")
   @JsonProperty(value = "errorCode")
   private String errorCode;
   @NgenAuditField(fieldName = "Error Description")
   @JsonProperty(value = "errorDesc")
   private String errorDescription;
   @NgenAuditField(fieldName = "AWB Prefix")
   @JsonProperty(value = "awbPfx")
   private String awbPrefix;
   @NgenAuditField(fieldName = "AWB Suffix")
   @JsonProperty(value = "awbNum")
   private String awbSuffix;
   @NgenAuditField(fieldName = "Total Pieces")
   @JsonProperty(value = "totPieces")
   private Integer totalPieces;
   @NgenAuditField(fieldName = "Total Weight")
   @JsonProperty(value = "totWeight")
   private Double totalWeight;
   @NgenAuditField(fieldName = "Origin")
   @JsonProperty(value = "origin")
   private String origin;
   @NgenAuditField(fieldName = "Destination")
   @JsonProperty(value = "destination")
   private String destination;
   @NgenAuditField(fieldName = "Consignee Name")
   @JsonProperty(value = "consigneeName")
   private String consigneeName;
   @NgenAuditField(fieldName = "AWB Details")
   @JsonInclude(Include.NON_EMPTY)
   @JsonProperty(value = "awbDetails")
   private List<IVRSAWBResponse.AWBDetail> awbDetails;
   //
   private BigInteger shipmentId;
   private LocalDate shipmentDate;
   
   /**
    * AWB Details
    */
   @NoArgsConstructor
   @Getter
   @Setter
   @JsonIgnoreProperties(value = { "shipmentId" })
   @JsonPropertyOrder(value = { "flightCarrier", "flightNumber", "flightDate", "pieces", "weight", "fsuStatus" })
   @NgenAudit(entityType = NgenAuditEntityType.AWB, repository = NgenAuditEventRepository.NOTIFICATION, // 
      eventName = NgenAuditEventType.IVRS)
   @NgenAudit(entityType = NgenAuditEntityType.AWB, repository = NgenAuditEventRepository.NOTIFICATION, // 
      eventName = NgenAuditEventType.FAX)
   public static class AWBDetail {
      //
      @NgenAuditField(fieldName = "Flight Carrier")
      @JsonProperty(value = "flightCar")
      private String flightCarrier;
      @NgenAuditField(fieldName = "Flight Number")
      @JsonProperty(value = "flightNum")
      private String flightNumber;
      @NgenAuditField(fieldName = "Flight Date")
      @JsonProperty(value = "flightDate")
      @JsonSerialize(using = LocalDateSerializer.class)
      private LocalDate flightDate;
      @NgenAuditField(fieldName = "Pieces")
      @JsonProperty(value = "pieces")
      private Integer pieces;
      @NgenAuditField(fieldName = "Weight")
      @JsonProperty(value = "weight")
      private Double weight;
      @NgenAuditField(fieldName = "FSU Status")
      @JsonProperty(value = "fsuStatus")
      private String fsuStatus;
      //
      private BigInteger shipmentId;
      
   }
   
}
