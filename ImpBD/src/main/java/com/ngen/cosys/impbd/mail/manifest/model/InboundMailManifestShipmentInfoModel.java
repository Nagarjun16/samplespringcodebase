package com.ngen.cosys.impbd.mail.manifest.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.model.ShipmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.AIRMAIL_IMPORT, repository = NgenAuditEventRepository.MAIL_BREAKDOWN, entityFieldName = "Flight Id", entityType = NgenAuditEntityType.AIRMAIL_BREAKDOWN)
public class InboundMailManifestShipmentInfoModel extends ShipmentModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   @NgenAuditField(fieldName = "Airmail Manifest Id")
   private BigInteger airmailManifestId;

   private String nextDestination;
   private String originOfExchange;
   private String destinationOfExchange;
   private String transferType;
   private String mailType;
   private String remarks;

   private String dispatchNumber;
   private BigInteger pieces;
   private BigInteger breakDownPieces;
   private BigDecimal breakDownWeight;
   private String embargo;
   private Boolean delivered;
   private Boolean bup;
   private Boolean damaged;
   @NgenAuditField(fieldName = "ULD Trolley No.")
   private String uldTrollyNo;
   @NgenAuditField(fieldName = "Flight Id")
   private BigInteger flightId;
   @NgenAuditField(fieldName = "Store Location")
   private List<String> storeLocations;
   @NgenAuditField(fieldName = "Breakdown Location")
   private List<String> brkdwnLocation;
   private BigInteger impBreakDownHouseInfoId;
   
   private String shipmentCarrierGroup;
   
   private String transferCarrierGroup;

   
   @Valid
   @NgenAuditField(fieldName = "Inventory")
   private List<InboundMailManifestShipmentInventoryInfoModel> inventory;
}