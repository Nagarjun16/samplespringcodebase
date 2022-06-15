package com.ngen.cosys.report.service.poi.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityRef2;
import com.ngen.cosys.audit.NgenAuditEntityRef3;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@NgenCosysAppAnnotation
@NgenAudit(eventName = NgenAuditEventType.SPECIALCARGO_REQUEST, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB number", entityType = NgenAuditEntityType.AWB)
@NgenAudit(eventName = NgenAuditEventType.SPECIALCARGO_HANDOVER, repository = NgenAuditEventRepository.AWB, entityFieldName = "AWB number", entityType = NgenAuditEntityType.AWB)
public class SpecialCargoSearch extends BaseBO {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 @NgenAuditEntityRef2(entityType = NgenAuditEntityType.FLIGHT,  parentEntityType = NgenAuditEntityType.AWB)
	 @NgenAuditEntityValue2(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHT)
	private String flightKey;
	 
	 @NgenAuditEntityRef3(entityType = NgenAuditEntityType.FLIGHT,  parentEntityType = NgenAuditEntityType.AWB)
	 @NgenAuditEntityValue3(entityType = NgenAuditEntityType.FLIGHT, parentEntityType = NgenAuditEntityType.AWB)
	 @NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate flightDate;
     
    private List<SpecialCargoShipment> readyShipment;
    private List<SpecialCargoShipment> notReadyShipment;
    private List<SpecialCargoShipment> shipmentList;
    private List<SpecialCargoShipment> shipmentListFromWorkingList;
    private String segment;
	private String carrierCode;
    private String flightType;
    private String flightBoardPoint;
    private String flightOffPoint;
    private int segmentId;
    private String shcGroup;
    private BigInteger flightId;
    private boolean sqCarrier;
    private String source;
    private boolean fromRequest;
    private String handoverToLoginId;
    private String staffName;
    @NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTINVENTORYLOCATION)
    private String whLocation;
    private String uldBtNumber;
    //delete request
    private BigInteger shipmentInventoryId;
    private BigInteger requestId;
    private BigInteger handoverId;
    
   private String atdetdstd;
   @NgenAuditField(fieldName = NgenAuditFieldNameType.AWBNUMBER)
   private String shipmentNumber;
   
   
   private List<SpecialCargoShipment> specialCargoMoniteringShipmentList;
   private LocalDateTime fromDate;
   private LocalDateTime toDate;
   private String byRequestHandOver;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime atdEtdStdMonitering;
   private String terminal;
   private String carrierGroup;
   private String dlsMismatchYesNo;
   private String notocMismatchYesNo;
   private String requestTerminal;
   private Boolean errorFlagToValidate = Boolean.FALSE;
   //master Setup
   private boolean dLSULDCheck;
   private boolean dLSSHCCheck;
   private boolean nOTOCULDCheck;
   
   //Flight List
   private Integer shipmentCount;
   private BigInteger requestedPiecesCount;
   private BigInteger shipmentPiecesCount;
   private BigInteger handoverPiecesCount;
   private String manifestCompleteSearch;
   private String handoverCompleteSearch;
   private List<SpecialCargoFlight> flightList;
   private boolean monitoringList  = false;
}
