package com.ngen.cosys.impbd.shipment.verification.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.InjectRequestTime;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
// import com.ngen.cosys.audit.NgenAudit;
// import com.ngen.cosys.audit.NgenAuditEventRepository;
// import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.model.FlightModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Setter
@Getter
@ToString
@NoArgsConstructor
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "flightNumber", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_COMPLETE, repository = NgenAuditEventRepository.FLIGHT)
public class DocumentVerificationFlightModel extends FlightModel {

   private static final long serialVersionUID = -5197186884971097677L;
   
   private BigInteger flightSegmentId;
   
   private String segmentinfo;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDPT)
   private String boardPoint;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ACTYPE)
   private String aircraftType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTRMKS)
   private String flightRemarks;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.STS)
   private String status;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DOCUMENT_VERIFICATION_COMPLETED)
   private Boolean documentCompleteStatus;

   private boolean documentReOpenStatus;
   
   private String inboundFlightDelayReason;
   
   private String printerName;
   
   private Integer docRecievedCount;
   
   private Integer eawbCount;
   
   private String finalizeCheck;
   
   private String codeAdminStatus;
   
   private String printerForAT;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT_COMPLETE)
   private Boolean flightCompleted;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.LASTUPDATEDUSERCODE)
   private String lastupdatedBy;
      
   @InjectRequestTime
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.LASTUPDATEDON)
   private LocalDateTime lastmodifiedOn;
   
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DOC_VERIFICATION_SHIP_DETALS)
   private List<DocumentVerificationShipmentModel> documentVerificationShipmentModelList;
   private String customsFlightNumber;

}
