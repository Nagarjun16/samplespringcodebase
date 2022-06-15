package com.ngen.cosys.impbd.service.provider.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.service.MaintainSeriviceProviderGroup;

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
@NoArgsConstructor@NgenAudit(entityFieldName = "customerCode", entityType = NgenAuditEntityType.MASTER, eventName = NgenAuditEventType.MAINTAIN_SERVICE_PROVIDER, repository = NgenAuditEventRepository.MASTER)
public class ServiceProviderModel extends BaseBO {
   private static final long serialVersionUID = 1L;

   private BigInteger serviceProviderId;
   private BigInteger terminalId;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.CARRIER)
   private String carrier;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT)
   private String flightKey;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.PAXCAO)
   private String flightType;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
   private String flightKeyType;
   private String flightNo;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.EFFECTIVEFROM)
   @NotBlank(message = "ERROR_EFFECTIVEDATE_REQUIRED" ,groups = { MaintainSeriviceProviderGroup.class })
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate effectiveDateFrom;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.EFFECTIVETO) 
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate effectiveDateTo;
   private String code;
   // private List<ServiceProviderCommunication> manageMail;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
   private LocalDate flightDate;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.NEWSERVICEPRIVIDERNAME) 
   private String serviceProviderCodeOld;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.NEWSERVICEPROVIDER)
   private String serviceProviderCodeNew;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.REASON)
   private String reason;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.TERMINAL)
   private String terminalCode;
   private BigInteger customerId;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICEPROVIDER)
   private String customerCode;
   private String serviceCode;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICEPRIVIDERNAME) 
   private String customerShortName;
   private boolean scInds = false;
   private String flight;
   private String customerCodeId;
   private String customerName;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DLWAIVEAPPLICABLE) 
   private Boolean lDWaiveApplicable;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.DLWAIVEMINUTE)
   private Long  lDWaiveApplicablelimit;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SERVICE_PROVIDER_TYPE)
   private String serviceProviderType;
   private String bodyType;
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime stdFrom;
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime stdTo;
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime staFrom;
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime staTo;
   
}
