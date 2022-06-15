/**
 * 
 *ShipmentMaster.java
 *
 *
 * Version      Date         Author      Reason
 * 1.0          12 January 2018   NIIT      -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.export.booking.model.SingleShipmentBooking;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class ShipmentMaster entity to show user shipment location details
 * and hold details
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.SHIPMENT_ON_HOLD, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB)
@NgenAudit(eventName = NgenAuditEventType.MAINTAIN_LOCATION, repository = NgenAuditEventRepository.AWB, entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB)
@NgenAudit( eventName = NgenAuditEventType.AWB_DOCUMENT,repository = NgenAuditEventRepository.AWB,entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB)
public class ShipmentMaster extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   /**
    * shipment number of the flight for tracking shipment on hold
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
   private String shipmentNumber;
   
    @InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;
    
    /**
     * Shipment Type (AWB/CBV/UCB)
     */
    private String shipmentType;
	
	private BigInteger comTracingShipmentInfoId;
   
   /**
    * Case Number
    */
   private String caseNumber;

   /**
    * Shipment ID
    */
   private int shipmentId;
   
   /**
    * Flight ID
    */
   private int flightId;

   /**
    * origin of the flight for shipment
    */
   private String origin;

   /**
    * destination of the flight for shipment
    */
   private String destination;

   /**
    * number of pieces
    */
   private int pieces;
   
   private BigInteger piecesMaster;
   
   private int irrPieces;

   /**
    * weight
    */
   private BigDecimal weight;
   
   private BigDecimal irrWeight;

   /**
    * nature of goods
    */
   private String natureOfGoods;

   /**
    * shipment handling code
    */
   private String specialHandlingCode;
   
   /**
    * shipment handling code
    */
   private ArrayList<String> shcList;
   
   /**
    * to apply hold on entire AWB
    */
   private boolean hold;
   
   /**
    * reason for applying hold on entire AWB.
    */
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REASON_FOR_HOLD)
   private String reasonForHold;
   
   /**
    * Part Shipment
    */
   private boolean partShipment;
   
   /**
    * Shipment Type
    */
   private String shipmentTypeflag;
   
   /**
    * list of individual shipment under this particular AWB
    */
   
   private LocalDateTime lastUpdatedTime;
   
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENT_INVENTORY_LIST)
   private List<ShipmentInventory> shipmentInventories;
   
   /**
    * list of individual shipment under this particular AWB
    */
   @Valid
   private List<FreightOut> freightOutArray;
   
   private List<ArrivalFlightInfo> arrivalFltlist;
   
   private List<SingleShipmentBooking> bookingDetails;
   private List<SingleShipmentBooking> docArrivalInfoList;
   
   private BigInteger bookingPieces;
   private BigDecimal bookingWeight;
   
   private Boolean flagForPartSuffixDropdown;
   
   private List<String> partSuffixList;
   
   private String holdNotifyGroup;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HOLD_UNHOLD_TIME)
   private LocalDateTime hold_unholdTime;
   
   private Boolean isShipmentTargetted = Boolean.FALSE;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLED_BY_MASTER_HOUSE)
   private String handledByHouse;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.AWB_CHARGEABLE_WEIGHT)
   private BigDecimal chargeableWeight;
   
   @NgenCosysAppAnnotation
   private ShipmentHouseInformation houseInformation;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HAWB_NUMBER)
   private String hwbNumber;
   //for audit trail
   @NgenAuditField(fieldName = NgenAuditFieldNameType.HANDLING_CHANGE_DATE)
   private LocalDateTime handlingChangeDate;
   
   private String hawbNumber;
   private String specialHandlingCodeHAWB;
   private ShipmentMasterCustomerInfo consignee;
   private Boolean shipmentUpdateEventFireFlag;
   private String agentCode;
   private String agentName;

}