package com.ngen.cosys.impbd.shipment.verification.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.model.ShipmentModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "shipmentNumber", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.DOCUMENT_VERIFICATION, repository = NgenAuditEventRepository.AWB)
public class DocumentVerificationShipmentModel extends ShipmentModel {

   private static final long serialVersionUID = 3860829832470467939L;
   
//   @NgenAuditField(fieldName = "selectCheck")
   private Boolean selectCheck = Boolean.FALSE;
   
//   @NgenAuditField(fieldName = "flightId")
   private BigInteger flightId;
   
   private BigInteger flightSegmentId;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.BOARDPT)
   private String boardPoint;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OFFPT)
   private String offPoint;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SEGORIGIN)
   private String segOrign;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGINS)
   private String origin;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTS)
   private String destination;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SEGDEST)
   private String segDestination;

//   @NgenAuditField(fieldName = "arrivalManifestbyId")
   private BigInteger arrivalManifestbyId;

//   @NgenAuditField(fieldName = "shipmentId")
   private BigInteger shipmentId;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.PIECE)
   private BigInteger awbPieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   private BigDecimal awbWeight;
   
   private String awbPieceWeight;
   
   private String manifestPiecesWeight;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.MANPIECES)
   private BigInteger manifestPieces =  BigInteger.ZERO;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.MANWEIGHT)
   private BigDecimal manifestWeight = BigDecimal.ZERO;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OLDDOCREVD)
   private Boolean oldDocRecieved = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DOCREVD)
   private Boolean docRecieved = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OLDCOPYAWB)
   private Boolean oldCopyAwb = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.COPYAWB)
   private Boolean copyAwb = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OLDDOCPOUCH)
   private Boolean oldDocPouch = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DOCPOUCH)
   private Boolean docPouch = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OLDCHKLISTREQ)
   private Boolean oldCheckListRequired = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHKLISTREQ)
   private Boolean checkListRequired = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHKLSTFLAG)
   private Boolean checkListRequiredFlg = Boolean.FALSE;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.LOCKED)
   private String locked;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.PARTSHIPMENTS)
   private String partShipment;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.TRANSFERTYPES)
   private String transferType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OUTBOUNDFLT)
   private String outboundFlight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.EAWBS)
   private String eawb;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.EAWS)
   private String eaw;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.EAPS)
   private String eap;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FWBS)
   private String fwb;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.FHLS)
   private String fhl;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DGN)
   private String dgn = "N";

   private String eliInfo = "N";
   
   private String elmInfo = "N";
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DGINFO)
   private String dgInfo = "N";

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NATUREOFGOODSS)
   private String natureOfGoods;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHCSS)
   private String shc;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.IRREGULARITY)
   private String irregularity;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.BARCODES)
   private String barcode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CONSIGNEES)
   private String consignee;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.APPINTEDAGT)
   private String appointedAgent;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.APOINTEDAGTNAME)
   private String appointedAgentName;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.NOASENT)
   private String noaSent;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.CHARGESMODE)
   private String chargesMode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.BREAKDOWNPIECECOMP)
   private BigInteger breakdownPieceCompleted;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.BDPIECES)
   private BigInteger breakdownPieces;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
   private BigDecimal breakdownWeight;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.READYFORDEL)
   private BigInteger readyfordelivery;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.OFFLOADRMKSCODE)
   private String offloadRemarksCode;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.RMKS)
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters")
   private String remarks;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.RMKTYPE)
   private String remarkType;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.REASON)
   private String reason;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DOC_COMP_AT)
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime documentCompletedAt;

   @NgenAuditField(fieldName = NgenAuditFieldNameType.DOC_COMP_BY)
   private String documentCompletedBy;  
   
   private String foreignAwbWithOnwordInfo;
   
   //CPE Action for FWB
   private String cpeFwbAction;

   //Through service shipment
   private String throughService = "N";
   private int firstFourDigitsAfterPrefix;
   
   private int serialNo;
   
	//AISATS 
	private String handledByDOMINT;
	
	private String handledByMasterHouse;
}