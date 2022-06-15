package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "StringFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser"
      ,"shipmentId", "origin", "destination", "originOfficeExchange", "destinationOfficeExchange", "pieces",
       "weight", "shipmentHouseId", "nextDestination", "receptacleNumber", "shipmentInventoryId", "shipmentLocation", "wareHouseLocation", 
       "incomingFlightId", "incomingFlightKey", "incomingFlightDate", "manifestedFltId", "flightBoardPoint", "flightOffPoint", "manifestedFlightKey",
       "manifestedFlightDate", "manifestULD", "loadedShipmentInfoId", "assUldTrolleyId", "loadedShipmentPieces", "loadedShipmentWeight",
       "damage","xRayResultFlag", "flightId", "carrier", "loadedHousePieces", "loadedHouseWeight", "validMailBags","originCountry","destinationCountry",
       "shipmentNumber", "methodFor", "segmentIdForAssignedTrolley", "response"}) 
//@CheckHousewayBillConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, shipmentTypeField = "type", hwbNumberField = "mailBagId")
public class MSSMailBagMovement extends AirmailHouseCommonModel  {
   
   /**
    * system generated serial version id
    */
   private static final long serialVersionUID = 1L;

   /**
   *
   * This field contains Transaction Sequence Number
   */
   @JsonProperty(value = "TRA_REF_NUM") 
   private String transactionSequenceNumber;
   /**
   *
   * This field contains ACTION(Add/Update/Delete)
   */
   @JsonProperty(value = "ACTION") 
   private String action;
   /**
   *
   * This field contains MAILBAGID
   */
   @JsonProperty(value = "MAIL_BAG_ID")
   private String mailBagId;
   
   /**
   *
   * This field is concatenated value of ULDtype, Number, Carrier 
   */
   private String uldKey;
   /**
   *
   * This field contains uldType
   */
   @JsonProperty(value = "ULD_TYP")
   private String uldType;
   /**
   *
   * This field contains uldNumber
   */
   @JsonProperty(value = "ULD_NUM")
   private String uldNumber;
   /**
   *
   * This field contains uldCarrier
   */
   @JsonProperty(value = "ULD_CAR")
   private String uldCarrier;
   
   private String contentCode;
   /**
   *
   * This field contains Location
   */
   @JsonProperty(value = "LOCN")
   private String containerlocation; 
   /**
   *
   * This field Location Acceptance
   */
   @JsonProperty(value = "ACC_DTTM")
    
   private String  acceptanceDateTime; 
   /**
   *
   * This field contains XRAY Status
   */
   @JsonProperty(value = "XRAYRSLT")
   private String xRayStatus;
   
   /**
   *
   * This field contains Damage Status
   */
   @JsonProperty(value = "DAMSTAT")
   private String damageStatus;
   /**
   *
   * This field contains OverSizeBagStatus
   */
   @JsonProperty(value = "OOSSTAT")
   private String overSizeBagStatus; 
   /**
   *
   * This field contains Torn  Status
   */
   @JsonProperty(value = "TORNSTAT")
   private String tornStatus;
   /**
   *
   * This field contains WETSTAT Status
   */
   @JsonProperty(value = "WETSTAT")
   private String wetStatus;
   /**
   *
   * This field contains Seal status
   */
   @JsonProperty(value = "SEALSTAT")
   private String sealStatus; 
   /**
   *
   * This field contains PLUN Status
   */
   @JsonProperty(value = "PLUNSTAT")
   private String plunStatuts;
   /**
   *
   * This field contains Mail Bag Weight Status
   */
   @JsonProperty(value = "MBAG_WGHT")
   private String mailBagWeight;
   /**
   *
   * This field contains Mail Bag Remarks
   */
   @JsonProperty(value = "MBAG_RMKS")
   private String mailBagRemarks; 
   /**
   *
   * This field contains Agent Status
   */
   @JsonProperty(value = "AGNT")
   private String agent;   
   /**
   *
   * This field contains  Outgoing Flight Carrier For Container
   */
   @JsonProperty(value = "OUT_FLT_CAR")
   private String outgoingFlightCarrier;
   /**
   *
   * This field contains  Outgoing flight number for container
   */
   @JsonProperty(value = "OUT_FLT_NUM")
   private String outgoingFlightNumber;
   /**
   *
   * This field contains  Outgoing flight date container
   */
   @JsonProperty(value = "OUT_FLT_DATE")
   
   private String outgoingFlightDate;
   /**
   *
   * This field contains  Outgoing flight Time container
   */
   @JsonProperty(value = "OUT_FLT_TIME")
    
   private String outgoingFlightTime;
   /**
   *
   * This field contains MailBag New Destination
   */
   @JsonProperty(value = "MBAG_NEW_DES")
   private String mailBagNewDestination;
   /**
   *
   * This field contains  Weighing DateTime
   */
   @JsonProperty(value = "WGHT_DTTM")
   
   private String weighingDateTime;
   /**
   *
   * This field contains  Sorting DateTime
   */
   @JsonProperty(value = "SORT_DTTM")
   
   private String sortDateTime;
   /**
   *
   * This field contains Store Date Time
   */
   @JsonProperty(value = "STOR_DTTM")
   
   private String storeDateTime;
   /**
   *
   * This field contains Dispatch No
   */
   @JsonProperty(value = "NO_DISPATCH")
   private String dispatchNo; 
   /**
   *
   * This field contains Pieces No
   */
   @JsonProperty(value = "NO_PIECES")
   private String piecesNo;
   /**
   *
   * This field contains  Occurrence date Time
   */
   @JsonProperty(value = "OCCU_DTTM")
    
   private String occurrenceDateTime;
   /**
   *
   * This field contains User
   */
   @JsonProperty(value = "USER")
   private String user;
   /**
   *
   * This field contains Reject Indicator
   */
   @JsonProperty(value = "REJECTIND")
   private String rejectIndicator; 
   
   /**
   *
   * This field contains Reject RSN
   */
   @JsonProperty(value = "REJECTRSN")
   private String rejectRsn; 
   /**
   *
   * This field contains Offload Indicator
   */
   @JsonProperty(value = "OFFLIND")
   private String offloadIndicator;
   /**
   *
   * This field contains  Offload Count
   */
   @JsonProperty(value = "OFFLCNT")
   private String offloadCount;
   /**
   *
   * This field contains Offload RSN
   */
   @JsonProperty(value = "OFFLRSN")
   private String offloadRsn;
   /**
   *
   * This field contains Incoming Flight Carrier
   */
   @JsonProperty(value = "IN_FLT_CAR")
   private String incomimngFlightCarrier;
   /**
   *
   * This field contains Incomimng Flight Number 
   */
   @JsonProperty(value = "IN_FLT_NUM")
   private String incomimngFlightNumber;
   /**
   *
   * This field contains Incomimng Flight Date
   */
   @JsonProperty(value = "IN_FLT_DATE")
   
   private String incomimngFlightDate;
   /**
   *
   * This field contains Incomimng Flight Airport
   */
   @JsonProperty(value = "IN_FLT_ARPO")
   private String incomimngFlightAirport;
   /**
   *
   * This field contains BreakDown Uld Type 
   */
   @JsonProperty(value = "BDULDTYPE")
   private String breakDownUldType; 
   /**
   *
   * This field contains BreakDown Serial No
   */
   @JsonProperty(value = "BDULDSN")
   private String breakDownUldSerialNo;
   /**
   *
   * This field contains BreakDown Uld Airline
   */
   @JsonProperty(value = "BDULDARLI")
   private String breakDownUldAirline;
   /**
   *
   * This field contains Container Destination
   */
   @JsonProperty(value = "ULD_DES")
   private String uldDestination;
   /**
   *
   * This field contains New Container Destination
   */
   @JsonProperty(value = "ULD_NEW_DES")
   private String uldNewDestination;
   /**
   *
   * This field contains Bup indicator for delivery
   */
   @JsonProperty(value = "DLV_BUP")
   private String bupIndicatorForDelivery;
   /**
   *
   * This field contains transit  indicator for BUP
   */
   @JsonProperty(value = "TRA_BUP")
   private String transitIndicatorBup;
   /**
   *
   * This field contains 
   */
   @JsonProperty(value = "CLTR_STAT")
   private String cltrStat;
   /**
   *
   * This field contains Uld Remarks
   */
   @JsonProperty(value = "ULD_RMKS")
   private String uldRemarks;
   /**
   *
   * This field contains Gross weight
   */
   @JsonProperty(value = "GROSS_WGHT")
   private String grossWeight;
   
   private BigInteger segmentId;
   
   private BigInteger bookingId;
   
   private BigInteger flightBookingId;
   
   private List<String> methodFor;
   
   private BigInteger segmentIdForAssignedTrolley;
   
   private ResponseMssMailBagMovement response;
   
   private BigInteger customerId;
}
