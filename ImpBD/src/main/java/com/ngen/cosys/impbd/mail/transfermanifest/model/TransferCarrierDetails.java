package com.ngen.cosys.impbd.mail.transfermanifest.model;


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

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.MAIL_TRANSFER_MANIEST, repository = NgenAuditEventRepository.MAILBAG, entityFieldName = "Mail Bag Number", entityType = NgenAuditEntityType.MAILBAG)
public class TransferCarrierDetails extends BaseBO {/**
    * 
    */
   private static final long serialVersionUID = 1L;
   @NgenAuditField(fieldName = "Mail Bag Number")
   private String mailBagNumber;
   @NgenAuditField(fieldName = "Incoming Carrier")
   private String incomingCarrier;
   @NgenAuditField(fieldName = "Transfer Carrier")
   private String transferCarrier;
   @NgenAuditField(fieldName = "Dispatch Number")
   private String dispatchNumber;
   
   private BigInteger transTransferManifestByAwbId;
   @NgenAuditField(fieldName = "Receptacle Number")
   private String receptacleNumber;
   @NgenAuditField(fieldName = "Origin")
   private String origin;
   @NgenAuditField(fieldName = "Destination")
   private String destination;
   @NgenAuditField(fieldName = "Pieces")
   private int pieces;
   @NgenAuditField(fieldName = "Weight")
   private float weight;
   private int registeredIndicator;
   private int damaged;
   private String flightId;
   @NgenAuditField(fieldName = "Flight Key")
   private String flightKey;
   @NgenAuditField(fieldName = "TRM Number")
   private String trmNumber;
   @NgenAuditField(fieldName = "Flight Date")
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightDate;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = "Issue Date")
   private LocalDateTime issuedDate;
   
   private Boolean finalizedFlag;
   @NgenAuditField(fieldName = "Finalized By")
   private String finalizedBy;
   @NgenAuditField(fieldName = "Finalized Date")
   private LocalDate finalizedDate;
   
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;
   
   private boolean select;
   
   private BigInteger shipmentId;
   
   private String nextDestination;

}
