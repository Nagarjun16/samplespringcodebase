package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.annotation.InjectRequestTime;
import com.ngen.cosys.annotation.InjectSegment;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

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
@NgenAudit(entityFieldName = "flight", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT, repository = NgenAuditEventRepository.FLIGHT)
@NgenAudit(entityFieldName = "uldNumber", entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT, repository = NgenAuditEventRepository.ULD)
public class CargoPreAnnouncementBO extends BaseBO {

   private static final long serialVersionUID = 1L;
   @NotEmpty(message = "ERRRO_FLIGHT_CANNOT_BE_BLANK")
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTKEY)
   private String flight;

   @JsonSerialize(using = LocalDateSerializer.class)
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDate date;

   
   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime sta;

   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime eta;

   @JsonSerialize(using = STDETDDateTimeSerializer.class)
   private LocalDateTime ata;

   private BigInteger flightId;
   
   

   private Boolean finalzeAndunFinalize;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.LASTUPDATEDUSERCODE)
   private String finalizedBy;
   
   @InjectRequestTime
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   @NgenAuditField(fieldName = NgenAuditFieldNameType.LASTUPDATEDON)
   private  LocalDateTime finalizedAt;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PREANNOUNCEMENT_FINALIZED)
   private Boolean preannoucemntFinalized;

   private String status;

  
   private String flightSeg;

   @InjectSegment(flightDate = "date", flightId = "", flightNumber = "flight", flightType = "I", segment = "segment")
   private String segment;
   
   private String screenFunction;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.BULK_INDICATOR)
   private Boolean bulk=Boolean.FALSE;
   
   private BigInteger bulkShipments;
   
   private BigInteger stBulkShipments;

   private Boolean handlinginSystem =Boolean.FALSE;
   
   
   private List<String> shcGropCodes;
   
   @Valid
   @NgenAuditField(fieldName = NgenAuditFieldNameType.ULD_INFO)
   private List<CargoPreAnnouncement> cargoPreAnnouncementList;

   private List<CargoPreAnnouncementSeg> cargoPreAnnouncementSegList;

   private List<CargoPreAnnouncementBulkShipment> cargoPreAnnouncementBulkShipmentList;

   private List<CargoSHCModel> shcListForPHC;

}