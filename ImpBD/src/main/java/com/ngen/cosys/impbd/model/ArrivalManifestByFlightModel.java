package com.ngen.cosys.impbd.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.model.FlightModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
//@NgenAudit(entityFieldName = "flightNo", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ARRIVAL_MANIFEST, repository = NgenAuditEventRepository.FLIGHT)
public class ArrivalManifestByFlightModel extends FlightModel {

   private static final long serialVersionUID = 1L;

   private BigInteger impArrivalManifestByFlightId;
   
   private BigInteger uldCount;
   
   private BigInteger looseCargo;
   
   private BigInteger cargoInULD;
   
   private BigInteger pieceCount;
   
   private BigDecimal weight;
   
   private String flightStatus;
   
   private BigInteger segmentId;
   
   private String flightType;
   
   @NgenAuditField(fieldName = "flightNo")
   private String flightNo;
   
   @Valid
   @NgenCosysAppAnnotation
   private List<FFMCountDetails> ffmReceivedDetails;
   
   @Valid
   @NgenCosysAppAnnotation
   private List<FFMCountDetails> ffmRejectedDetails;
   
   
   @Valid
   @NgenCosysAppAnnotation   
   private List<ArrivalManifestBySegmentModel> segments;
   
   private List<ArrivalManifestShipmentRejectDetails> rejectedShipments;

}