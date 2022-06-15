package com.ngen.cosys.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
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

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
@NgenAudit(entityFieldName ="flightKey", entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS, repository = NgenAuditEventRepository.FLIGHT)
public class CustomsMRSModel extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   @NotNull(message = "g.mandatory")
   @NotBlank(message = "g.mandatory")
   private String flightKey;
   @NgenAuditField(fieldName =NgenAuditFieldNameType.FLIGHTDATE)
   @NotNull(message = "g.mandatory")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;
   @NgenAuditField(fieldName =NgenAuditFieldNameType.EXPORTIMPORTINDICATOR)
   @NotNull(message = "g.mandatory")
   private String exportOrImport;
   private String shipmentstatus;
   private String mrsSequenceNo;
   private String acknowledgeCode;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate acknowledgeDate;
   private BigInteger customsFlightId;
   @NgenAuditField(fieldName =NgenAuditFieldNameType.OPENMRS)
   private boolean openMrs;
   private List<MRSModel> mrsModel;
   
   @NgenAuditField(fieldName =NgenAuditFieldNameType.MRS_Sent)
   private String mrssentby;
   
   @NgenAuditField(fieldName = NgenAuditFieldNameType.MRSSentDate)
   private LocalDateTime createmrs;
   
 	@NgenAuditField(fieldName = NgenAuditFieldNameType.ACESDATE)
 	private LocalDateTime date = LocalDateTime.now();
 	
}
