package com.ngen.cosys.impbd.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.model.ULDModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@NgenAudit(entityFieldName = "flightId", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ARRIVAL_MANIFEST, repository = NgenAuditEventRepository.FLIGHT)
public class ArrivalManifestUldModel extends ULDModel {

   private static final long serialVersionUID = 1L;

   private BigInteger impArrivalManifestBySegmentId;

   private BigInteger impArrivalManifestUldId;
   
   private BigInteger shipmentCount;
   
   private BigInteger pieceCount;
   
   private BigDecimal weight;
   
   private String transferType;
   
   private String contourCode;
   
   private String weightUnit;
   
   private BigInteger flightId;
   
   private BigInteger flightSegmentId;
   
   private String flightKey;
   
   private LocalDate flightDate;
   
   private BigInteger bookingFlightId;

   private BigInteger connectingFlightId;
   
   private BigInteger bookingFlightSegmentId;   
   

   // Shipment
   @Valid
   @NgenCosysAppAnnotation
   @NgenAuditField(fieldName = "Manifested Shipments List")
   private List<ArrivalManifestShipmentInfoModel> shipments;

}