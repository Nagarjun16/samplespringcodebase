package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName="shipmentNumber",entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.UNLOAD_SHIPMENT, repository = NgenAuditEventRepository.AWB)
public class UnloadShipment extends Shipment {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   @NgenAuditField(fieldName = "Unload Shipments Flight")
   private Flight flight;
   private Segment segment;
   private BigInteger assUldTrolleyId;
   private BigInteger loadedShipmentInfoId;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUMBER)
   private String assUldTrolleyNumber;
   private String heightCode;
   private Boolean trolleyInd = Boolean.FALSE;
   @Valid
   private List<UnloadShipmentInventory> shpmtInventoryList;
   private List<String> houseNumbers;
   private String shipmentLocation;
   private String reason;
   private String unloadedBy;
   private boolean flagError;
   private BigDecimal loadWeight;
   
   private boolean isTTCase;
   private boolean unloadEnteredPieceWeightForAmend;
   private Boolean isFromMailUnload = Boolean.FALSE;
   private Boolean isFromMailOffload = Boolean.FALSE;
   private BigInteger manifestShipmentHouseInfoId;
   private BigInteger inboundFLightId;
   private boolean isUnloadFlag;
   private  String trmNumber;
   
}