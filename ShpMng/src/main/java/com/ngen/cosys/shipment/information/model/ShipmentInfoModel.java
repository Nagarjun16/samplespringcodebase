package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CANCEL_SHIPMENT, repository = NgenAuditEventRepository.AWB, entityRefFieldName = "shipmentId")
public class ShipmentInfoModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   private String process;
   //@NgenAuditField(fieldName = "Shipment Id")
   private BigInteger shipmentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;
   private String lar;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTDATE)
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN)
   private String origin;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION)
   private String destination;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_PIECES)
   private BigInteger awbPiece;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_WEIGHT)
   private BigDecimal awbWeight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NATURE_OF_GOODS)
   private String natureOfGoods;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHC)
   private String shcs;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.AGENT)
   private String agent;
   private String rcarType;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PART_SHIPMENT)
   private Boolean partShipment;
   private BigInteger slacPieces;
   private String volume;
   private Boolean consol;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.RECEIVED_ON)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime receivedOn;
   private Boolean eawb;
   private Boolean pouchReceived;
   private Boolean fwbe;
   private Boolean fwbm;
   private Boolean fhle;
   private Boolean fhlm;
   private Boolean fsu;
   private String awbRemarks;
   private BigInteger uploadedDocument;
   private String courierTag;
   private String rfidTag;
   private Boolean canReUse;
   private Boolean abondanedCargo;
   private String authorizationLetter;
   private String bankCreationDocument;
   private String emailTo;
   private Boolean awbOnHold;
   private Boolean cancelCharge = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ACTUAL_WEIGHT)
   private BigDecimal actualWeight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ACTUAL_PIECES)
   private BigInteger actualPieces;

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate cancelledOn;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.RELEASED_ON)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime releasedOn;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.RELEASED_BY)
   private String releasedBy;
   
   private String epouchUpload;
   private List<ShipmentRemarksModel> remarks;
   private List<ShipmentInfoMessageModel> fhlOutgoingMessageSummary;
   private List<ShipmentInfoMessageModel> fwbOutgoingMessageSummary;
   private List<ShipmentInfoMessageModel> fsuOutgoingMessageSummary;
   private List<String> uploadedDocuments;
   private List<ShipmentFlightModel> flightDetails;
   private List<ShipmentDeliveryModel> shipmentDeliveryDetails;
   private List<ShipmentBillingModel> billingDetails;
   private List<ShipmentAcceptanceModel> acceptanceDetails;
   private List<ShipmentTransferManifestModel> tmDetails;
   private List<ShipmentHouseInfoModel> houseInfoDetails;
   private List<EpouchFileModel> epouchDocuments;
   // freight out details
   private List<ShipmentFreightOutInfoModel> freightOutDetails;

   // Transhipment
   private List<ShipmentFlightModel> incomingFlightDetails;
   private List<ShipmentFlightModel> outbooundFlightDetails;

   private List<ShipmentHouseInfoSummaryModel> houseSummaryDetails;
   private String stausForCancel;

   private String checkListReceived;
   private String noa;

   private BigInteger declaredPiece;
   private BigDecimal declaredWeight;

   private List<ShipmentNoaListModel> noalist;

   private Boolean svc;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTTYPE)
   private String shipmentType;

   private List<ShipmentFlightModel> partShipmentDetails;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CANCEL_SHIPMENT_REMARK)
   private String cancelShipmentRmrk;

   private String userLoginCode;

   private String password;
   
   private String routingInfo;
   
   private String messageType;

   private List<ShipmentNoaListModel> eWarehouseRemarks;
   //Purge Shipment params//
   private String reasonForPurge;
   private boolean readyForPurge;
   private String archivedBy;
   private String paymentStatus;
   //Purge Shipment params//
   private String ifNON;
   private String agentCode;
   private String agentName;
   private BigInteger customerId;
   private BigInteger uploadedDocId;
   private List<HandoverLocation> handOverLocation;
   
 //Added For Daxing For house information starts//
   @NgenAuditField(fieldName = "handledByHouse")
   private String handledByHouse;
   private BigInteger shipmentHouseId;
   @NgenAuditField(fieldName = "indicatorDomIntl")
   private String indicatorDomIntl;
   private BigInteger housePieces;
   private BigDecimal houseWeight;
   private String houseOrigin;
   private String houseDestination;
   @NgenAuditField(fieldName = "House Chargeable Weight")
   private BigDecimal houseChargeableWeight;
   private String houseNOG;
   private BigDecimal ChargebleWeight;
   private String houseShcs;
   private String houseCustomerCode;
   private String hwbNumber;
   private List<ShipmentRemarksModel> houseRemarks;
 //Added For Daxing For house information ends//
   private BigInteger flightId;
   private Boolean handlingChangeFlag = Boolean.TRUE;
   private boolean checkDocumentAccepted;
   
   private BigDecimal totalVolume;
   private BigDecimal totalVolumetricWeight;
   private Boolean isBondedTruck = Boolean.FALSE;
  
}