package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.model.SegmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@NgenAudit(entityFieldName = "flightId", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ARRIVAL_MANIFEST, repository = NgenAuditEventRepository.FLIGHT)
public class ArrivalManifestBySegmentModel extends SegmentModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1437227932268314632L;
   
   private BigInteger impArrivalManifestByFlightId;
   private BigInteger impArrivalManifestBySegmentId;
   
   private Boolean nilCargo= Boolean.FALSE;
   private int manifestUldCount;
   private int bulkShipmentsCount;
   
   
   private String flightKey;
   
   private LocalDate flightDate;
   
   // ULD List
   @Valid
   @NgenCosysAppAnnotation   
   private List<ArrivalManifestUldModel> manifestedUlds;

   // LooseCargo
   @Valid
   @NgenCosysAppAnnotation  
   private List<ArrivalManifestShipmentInfoModel> bulkShipments;
   
   private List<String> segmentUldGruopDetailsCountList;
   
   private String segmentUldGruopDetailsCount;
}
