package com.ngen.cosys.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
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
@NgenAudit(entityFieldName = "flightId", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ARRIVAL_MANIFEST, repository = NgenAuditEventRepository.FLIGHT)
public class SegmentModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger segmentId;
   
   private String messageStatus;
   
   private BigInteger segmentCopy;
   
   @NgenAuditField(fieldName = "flightId")
   private BigInteger flightId;
   
   @NgenAuditField(fieldName = "boardingPoint")
   private String boardingPoint;

   private String offPoint;

   private BigInteger uldCount;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime flightOriginDate;

   private BigInteger looseCargoCount;

   private BigInteger cargoInULD;

   private BigInteger segmentPieceCount;

   private BigDecimal segmentWeight;
   
   private BigInteger flightSegmentOrder;

}