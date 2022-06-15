package com.ngen.cosys.shipment.mail.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue2;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the class for creating a CN 46 Request Details.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@NgenAudit(entityFieldName = NgenAuditFieldNameType.MAIL_NUMBER, entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.CN46_CREATE, repository = NgenAuditEventRepository.MAILBAG)
public class CN46Details extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger manifestId;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.MAIL_NUMBER)
	private String mailNumber;

	@CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ULDNUMBER)
	private String uldNumber;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.GROSSWEIGHT)
	private BigDecimal weight;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ORIGIN_OFFICE_EXCHANGE)
	private String originOfficeExchange;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DESTINATION_OFFICE_EXCHANGE)
	private String destinationOfficeExchange;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRPORT_OF_TRANSHIPEMENT)
	private String airportOfTranshipment;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRPORT_OF_OFFLOADING)
	private String airportOfOffloading;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DATE_OF_DISPATCH)
	private LocalDate dateOfDispactch;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.LETTER_POST)
	private BigInteger letterPost;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CP)
	private BigInteger cp;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.EMS)
	private BigInteger ems;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OTHER_ITEMS)
	private BigInteger otherItems;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARK)
	private String remarks;
	
	private int airmailManifestShipmentId;
	
	
	// For Audit Trail
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTKEY)
	@NgenAuditEntityValue2(parentEntityType = NgenAuditEntityType.MAILBAG, entityType = NgenAuditEntityType.FLIGHT)
	private String flightKey;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLIGHTDATE)
	@NgenAuditEntityValue3(parentEntityType = NgenAuditEntityType.MAILBAG, entityType = NgenAuditEntityType.FLIGHT)
	private LocalDate flightDate;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.SEGMENT)
	private String segment;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.STORE_LOCATION)
	private String trolleyNumber;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BULK)
	private boolean bulkFlag;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OBSERVATIONS)
	private String observations;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ADMINS)
	private String adminOfOriginOfMails;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRPORT_OF_LOADING)
	private String airportOfLoading;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.AIRPORT_OFFLOADING) 
	private String airportOffLoading;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.DEST_OFFICE)
	private String destinationOffice;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OUTGOING_FLIGHT)
	private String outgoingFlightKey;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.OUTGOING_FLTDATE)
	private LocalDate outgoingFlightDate;
	// For Audit Trail


}
