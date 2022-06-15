package com.ngen.cosys.impbd.mail.manifest.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue5;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckShipmentLocationConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.MAIL_IMP_MAIL_MANIFEST_UPDATE_LOCATION, repository = NgenAuditEventRepository.MAILBAG, 
	entityFieldName = "Mail Bag Number", entityType = NgenAuditEntityType.MAILBAG)
public class InboundMailManifestShipmentInventoryInfoModel extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   private BigInteger shipmentInventoryId;
   private BigInteger shipmentId;
   private BigInteger houseId;
   private BigInteger flightId;
   @NgenAuditEntityValue5(parentEntityType = NgenAuditEntityType.MAILBAG, entityType = NgenAuditEntityType.ULD)
   @CheckShipmentLocationConstraint(mandatory = MandatoryType.Type.REQUIRED)
   @NgenAuditEntityRef2(entityType = NgenAuditEntityType.ULD,  parentEntityType = NgenAuditEntityType.MAILBAG)    
   @NgenAuditEntityValue2(entityType = NgenAuditEntityType.ULD, parentEntityType = NgenAuditEntityType.MAILBAG)
   @NgenAuditField(fieldName = "ULD Number")
   private String storageLocation;
   @NgenAuditField(fieldName = "Warehouse Location")
   private String breakDownLocation;
   private String existingShipmentLocation;
   private String existingWarehouseLocation;
   private BigInteger breakDownPieces = BigInteger.ZERO;
   private BigDecimal breakDownWeight = BigDecimal.ZERO;
   @NgenAuditField(fieldName = "Mail Bag Number")
   private String mailBagNumber;
   private String nextDestination;
   private String incomingCarrier = null;
   private String incomingCountry = null;
   private String incomingCity = null;
   private String outgoingCarrier = null;
   private String flightOffPoint = null;
   private boolean checkChild;
   private boolean checkSHC;
   private boolean uldPopup;
   private boolean DestinationCheck;
   private String containerDestination;
   private boolean releaseDest;
   private String outgoingCountry;
   private String outgoingCity;
   private String originOfficeExchange;
   private String destinationOfficeExchange;
   private String agentCode;
   private String originCountry;
   private String originCity;
   private String destinationCountry;
   private String destinationCity;
   private String embargoFlag;
   private String damaged;
   private String transferCarrierFrom;
   private Boolean transferred = Boolean.FALSE;
   private Boolean delivered = Boolean.FALSE;
   private String loadedHouse;
   private List<String> filteredDataForStoreLocation = new ArrayList<>();
   private List<String> filteredDataForBreakDownLocation = new ArrayList<>();
   private List<String> filteredDataForNextDestination = new ArrayList<>();
   private List<String> filteredDataForTransferCarrier = new ArrayList<>();
   private List<String> filteredDataForEmbargo = new ArrayList<>();
   private boolean closedTransit; 
}