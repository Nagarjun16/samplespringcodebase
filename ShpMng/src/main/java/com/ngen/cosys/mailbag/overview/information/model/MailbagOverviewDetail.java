package com.ngen.cosys.mailbag.overview.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

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

@ApiModel
@XmlRootElement
@Component
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(eventName = NgenAuditEventType.MAILBAG_OVERVIEW,repository = NgenAuditEventRepository.MAILBAG, entityFieldName = NgenAuditFieldNameType.MAILBAGNUMBER,entityType = NgenAuditEntityType.MAILBAG)
@NgenAudit(eventName = NgenAuditEventType.MAILBAG_OVERVIEW_XRAY,repository = NgenAuditEventRepository.MAILBAG, entityFieldName = NgenAuditFieldNameType.MAILBAGNUMBER,entityType = NgenAuditEntityType.MAILBAG)
@NgenAudit(eventName = NgenAuditEventType.MAILBAG_OVERVIEW_MAILDAMAGE,repository = NgenAuditEventRepository.MAILBAG, entityFieldName = NgenAuditFieldNameType.MAILBAGNUMBER,entityType = NgenAuditEntityType.MAILBAG)
@NgenAudit(eventName = NgenAuditEventType.MAILBAG_OVERVIEW_EMBARGOFAILURE,repository = NgenAuditEventRepository.MAILBAG, entityFieldName = NgenAuditFieldNameType.MAILBAGNUMBER,entityType = NgenAuditEntityType.MAILBAG)
public class MailbagOverviewDetail extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.RSN)
	private String rsn;
	
	
	private BigInteger shipmentId;
	
	
	private BigInteger inventoryId;
	
	
	private BigInteger shipmentHouseId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.STORAGELOCATION)
	private String storeLocation;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WAREHOUSE_LOCAITON)
	private String warehouseLocation;
	
	
	private String existingShipmentLocation;
	
	
	private String existingWarehouseLocation;
	
	
	private BigInteger inventoryFlightId;
	
	
	private BigInteger incomingFlightId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.INCOMING_FLIGHT_KEY)
	private String incomingFlightKey;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.INCOMING_FLIGHT_DATE)
	private LocalDate incomingFlightDate;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOOKED_FLIGHT)
	private String bookedFlight;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BOOKED_FLIGHT_DATE)
	private LocalDate bookedFlightDate;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OUTGOING_FLIGHT)
	private String outgoingFlight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANIFESTED_FLIGHT)
	private String manifestedFlight;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANIFESTED_FLIGHT_DATE)
	private LocalDate manifestedFlightDate;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.STORE_LOCATION_LIST)
	private String storeLocationList;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MANIFESTED_ULD_TROLLEY)
	private String manifestedUldTrolley;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.EMBARGO)
	private String emabrgo;
	
	private Boolean damageFlag;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DSN_REMARK)
	private String dsnRemark;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.HISTORY)
	private String history;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.XRAY_RESULT_FLAG)
	private String xrayResultFlag;
	
	//added for screening
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SC_STATUS)
	private String scStatus;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PIECES)
	private BigInteger pieces;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.WEIGHT)
	private BigDecimal weight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MAILBAGNUMBER)
	private String mailBagNumber;
	
    private String flightOffPoint;
	
    private String outgoingCarrier;
	
    private String nextDestination;
	
    private String incomingCarrier;
	
    private String incomingCountry;
	
    private String incomingCity;
	
    private String agentCode;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN_OFFICE_EXCHANGE)
    private String originOfficeExchange;
	
    private String originCountry;
	
    private String originCity;
	
    private String outgoingCountry;
	
    private String outgoingCity;
    
    @NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION_OFFICE_EXCHANGE)
    private String destinationOfficeExchange;
	
    private String destinationCountry;
	
    private String destinationCity;
	
    private String embargoFlag;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DAMAGED)
    private String damaged;
	
    private AllStatusOfMailBag allStatus;
	
	private boolean destinationCheck;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CONTAINER_DESTINATION)
	private String containerDestination;
	
	private boolean releaseDest;
	
	private String natureOfDamage;
	
	private int DamageInfoId;
	
	private String severity;
	
	private String mssWeight;
	
	private boolean uldPopup;
	
	private boolean checkSHC;
	
	private boolean closedTransit;
	
	private String flightKey;
	
	private BigInteger outHouseInformationId;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;
	
	
	private String screenedMethod;
	
    
}
